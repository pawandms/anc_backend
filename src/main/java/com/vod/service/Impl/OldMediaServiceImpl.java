package com.vod.service.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vod.exception.HlsMediaException;
import com.vod.exception.HlsProcessingException;
import com.vod.exception.InvalidMasterPlayListException;
import com.vod.exception.InvalidMediaPathException;
import com.vod.exception.MediaException;
import com.vod.hls.model.Hls_PlayList;
import com.vod.model.hls.HlsMasterPlayList;
import com.vod.model.hls.HlsPlayList;
import com.vod.model.hls.HlsSegment;
import com.vod.model.hls.HlsVideo;
import com.vod.repository.HlsMasterPlayListRepository;
import com.vod.repository.HlsSegmentRepository;
import com.vod.service.OldMediaService;
import com.vod.util.EnvProp;
import com.vod.util.HelperBean;
import com.vod.vo.HlsMediaResponse;

@Service
public class OldMediaServiceImpl implements OldMediaService {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Logger logger = LoggerFactory.getLogger(OldMediaServiceImpl.class);
	
	@Autowired
	private HelperBean helper;

	@Autowired
	private EnvProp env;
	
	@Autowired
	private HlsMasterPlayListRepository mstplaylistRepo;
	
	@Autowired
	private HlsSegmentRepository segRepo;
	
	@Autowired
    private GridFsTemplate gridFsTemplate;


	@Override
	//@Transactional
	public HlsMediaResponse processHlsMediaDirectory(String movieId, String path) throws HlsMediaException {
		
		logger.info("HLS PlayList Processing start for Path:"+path);
		HlsMediaResponse response = null;
		HlsVideo hlsvideo = null;
		try {
			Path hlspath = Paths.get(path);
			
			 if((!Files.exists(hlspath)) || (!hlspath.toFile().isDirectory()))
		        {
				  throw new InvalidMediaPathException("HLS Media Path not exits or not a directory");
		        }
			 
			 hlsvideo = helper.processHlsMediaFiles(movieId, hlspath);
			 
			 if(null != hlsvideo)
			 {
				 logger.info("SegmentList Count :"+hlsvideo.getSegment().size());
				
				
				 for (HlsSegment seg : hlsvideo.getSegment())
				 {
					 // Save HLS Segment into DB
					 saveHlsSegment(seg);
				 }
				 
				 mstplaylistRepo.save(hlsvideo.getMstPlayList()); 
				 
				 
			 }
			
			 
			 response = new HlsMediaResponse(hlsvideo.getId());
			 response.setHlsPlayList(hlsvideo.getMstPlayList());
			 
		    	
		}
		catch(InvalidMediaPathException | HlsProcessingException | IOException   e)
		{
			
			logger.error(e.getMessage(), e);
		}
		
		return response;
	}
	
	private void saveHlsSegment(HlsSegment seg) throws IOException
	{
		 seg.setData(new Binary(BsonBinarySubType.BINARY, FileUtils.readFileToByteArray(seg.getSegFile())));
		 segRepo.save(seg);
		 logger.info("Segment Save to DB with ID:"+seg.getId());
		 
		 seg = null;
		
	}

	@Override
	public String getHlsMasterPlayList(String movieId) throws HlsMediaException
	{
		String result = null;	
		
		if( null != movieId)
		{
			Optional<HlsMasterPlayList> optmstPlayList = 	mstplaylistRepo.findById(movieId);
			
			if(optmstPlayList.isPresent())
			{
				HlsMasterPlayList mstPlayList = optmstPlayList.get();
				
				result = helper.prepareHlsMasterPlayListXML(mstPlayList);
				
			}
		}
		
		return result;
	}

	@Override
	public String getHlschildPlayList(String playListId) throws HlsMediaException {

		String result = null;	
		try {
			if( null != playListId)
			{
				HlsMasterPlayList mplList = 	mstplaylistRepo.findByplayListId(playListId);
				
				if(null == mplList)
				{
					throw new HlsMediaException("Master PlayList Not found for PLId"+playListId);
				}	
					
						
				HlsPlayList playList = helper.getPlayListById(mplList, playListId);
					if(null ==  playList)
					{
						throw new HlsMediaException("PL not found in MPL for ID:"+playListId);
					}
					
					List<HlsSegment> segList = segRepo.findByplayListId(playListId);
					
					result = helper.prepareHlsPlayListXML(playList, segList);
				}
		}
		catch(HlsMediaException | InvalidMasterPlayListException e)
		{
			logger.error(e.getMessage(), e);
			throw new HlsMediaException(e.getMessage(), e);
		}
		
		
		return result;

	}

	@Override
	public void getSegmentData(String id, String range, HttpServletResponse response) {
		
		logger.info("Preparing Content for Segment ID:"+id+", Range:"+range);
        long rangeStart = 0;
        long rangeEnd = 0;
        Long fileSize;
        String fileType =null;
        
        try {
        	if( null == id)
        	{
        		throw new HlsMediaException("Segment ID can not be null");
        	}
        	Optional<HlsSegment> seg = segRepo.findById(id);
        	
        	if(!seg.isPresent())
        	{
        		throw new HlsMediaException("Segment Data not present for ID:"+id);
        	}
        	
        	fileSize = (long) seg.get().getData().length();
        	fileType = seg.get().getExtension().getValue();
            if ( null != range)
            {
    	        Matcher matcher = env.RANGE_PATTERN.matcher(range);
    	        
    	        if (matcher.matches()) {
    	          String startGroup = matcher.group("start");
    	          rangeStart = startGroup.isEmpty() ? rangeStart : Integer.valueOf(startGroup);
    	          rangeStart = rangeStart < 0 ? 0 : rangeStart;

    	          String endGroup = matcher.group("end");
    	          rangeEnd = endGroup.isEmpty() ? fileSize : Integer.valueOf(endGroup);
    	          rangeEnd = rangeEnd > fileSize - 1 ? fileSize - 1 : rangeEnd;
    	        }
            }
            else {
            	rangeEnd = fileSize -1;
            }
            

	        int contentLength = (int) (rangeEnd - rangeStart + 1);

	       // response.reset();
	        response.setBufferSize(env.BUFFER_LENGTH);
	       // response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", video.getTitle()));
	        response.setHeader(env.ACCEPT_RANGES,env.BYTES);
	       // response.setDateHeader("Last-Modified", video.getUploadDate().getTime());
	       //response.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
	        response.setHeader(env.CACHE_CONTROL_HEADER, env.CACHE_CONTROL_VALUE);
	        response.setHeader(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts");
	        response.setHeader(env.CONTENT_RANGE, String.format("bytes %s-%s/%s", rangeStart, rangeEnd, fileSize));
	        response.setHeader(env.CONTENT_LENGTH, String.format("%s", contentLength));
	        response.setHeader("access-control-allow-credentials", "true");
	        response.setHeader("access-control-allow-headers", "Range");
	        
	        response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
	        	
	        InputStream segStream = new ByteArrayInputStream(seg.get().getData().getData());	
	        copy(segStream, response.getOutputStream(), fileSize, rangeStart, rangeEnd); 	
           
        } catch (IOException | HlsMediaException e) {
            logger.error("Exception while reading the file From Video Sercice:", e.getMessage());
            
          //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

		
	}
	
    private  void copy(InputStream input, OutputStream output, long inputSize, long start, long length) throws IOException {
        
    	logger.info("Streaming Media for Start:"+start+" , lenght:"+length+", Total Size:"+inputSize);
    	
    	byte[] buffer = new byte[env.DEFAULT_BUFFER_SIZE];
        int read;
        
        if(input.markSupported())
		{
        	//input.reset();	
		}

        if (inputSize == length) {
            // Write full range.
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
                output.flush();
            }
        } else {
            input.skip(start);
            long toRead = length;

            while ((read = input.read(buffer)) > 0) {
                if ((toRead -= read) > 0) {
                    output.write(buffer, 0, read);
                    output.flush();
                } else {
                    output.write(buffer, 0, (int) toRead + read);
                    output.flush();
                    break;
                }
            }
        }
    }

	@Override
	public ResponseEntity<byte[]> prepareSegmentContent(String id, String range) {
	
	 	logger.info("Preparing Content for Segmenet ID:"+id+", Range:"+range);
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
     //   String fileType ="mp4";
        
        try {
        
        	if( null == id)
        	{
        		throw new HlsMediaException("Segment ID can not be null");
        	}
        	Optional<HlsSegment> seg = segRepo.findById(id);
        	
        	if(!seg.isPresent())
        	{
        		throw new HlsMediaException("Segment Data not present for ID:"+id);
        	}

        	fileSize = (long) seg.get().getData().length();
        	 InputStream segStream = new ByteArrayInputStream(seg.get().getData().getData());	
  	       
            if (range == null) {
                return ResponseEntity.ok()
                		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
                        .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
                        .header(env.CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(readByteRange(segStream, rangeStart, fileSize - 1)); // Read the object and convert it as bytes
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }
            data = readByteRange(segStream, rangeStart, rangeEnd);
        } catch (IOException | HlsMediaException e) {
            logger.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
                .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
                .header(env.ACCEPT_RANGES, env.BYTES)
                .header(env.CONTENT_LENGTH, contentLength)
                .header(env.CONTENT_RANGE, env.BYTES + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);

		
	}
	
	public byte[] readByteRange(InputStream inputStream, long start, long end) throws IOException {

    	 byte[] result  = null;
    		
    		if (null == inputStream)
    		{
    			throw new IOException("Invalid Input Stream");
    		}
    		if(inputStream.markSupported())
    		{
    		inputStream.reset();	
    		}
    	
    	    ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[env.BYTE_RANGE];
                int nRead;
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    bufferedOutputStream.write(data, 0, nRead);
                   
                }
                bufferedOutputStream.flush();
                result = new byte[(int) (end - start) + 1];
                System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
                return result;
            
    		
    	
    	
    	
    }

	@Override
	public InputStream prepareSegmentStreamContent(String id) throws MediaException
	{
		
		InputStream stream = null;
		 try {
		        
	        	if( null == id)
	        	{
	        		throw new MediaException("Segment ID can not be null");
	        	}
	        	Optional<HlsSegment> seg = segRepo.findById(id);
	            	        	
	        	if(!seg.isPresent())
	        	{
	        		throw new MediaException("Segment not present for ID:"+id);
	        	}
	        	
	        	 stream = new ByteArrayInputStream(seg.get().getData().getData());	
	  	       
	          
	        } catch ( MediaException e) {
	          
	        	throw e;
	        }
		 	
		 return stream;

	}

	

}
