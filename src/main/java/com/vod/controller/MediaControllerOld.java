package com.vod.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vod.exception.HlsMediaException;
import com.vod.service.OldMediaService;
import com.vod.util.EnvProp;
import com.vod.util.HlsUtil;
import com.vod.vo.HlsMediaRequest;
import com.vod.vo.HlsMediaResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value= "media")
public class MediaControllerOld {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OldMediaService mediaService;

	@Autowired
	private EnvProp env;

	@Autowired
	private HlsUtil hlsUtil;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addHlsMedia(@RequestBody HlsMediaRequest mediaReq) 
	{
		ResponseEntity<?> response = null;
		HlsMediaResponse hlsresponse = null;
		logger.info("HLS MediaList Processing with id:", mediaReq.getMediaId()+", Path:"+mediaReq.getMediaPath());
        
        try {
      
        	hlsresponse =   mediaService.processHlsMediaDirectory(mediaReq.getMediaId(), mediaReq.getMediaPath());
        	if( null == hlsresponse)
        	{
        		throw new HlsMediaException("Unable to Process Hls Media from Path:"+mediaReq.getMediaPath());
        	}
        	
        	  response =  new ResponseEntity<>(hlsresponse, HttpStatus.OK);
        }
        catch (HlsMediaException e)
        {
        	response =  new ResponseEntity<>("HLS PlayList Processing Failed with Error Msg:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }

	@RequestMapping(value = "/hls/playlist/master/{id:.+}", method = RequestMethod.GET, produces = "application/x-mpegURL")
    public ResponseEntity<?> getHlsMasterPlayList(@PathVariable("id") String id)  
	{	ResponseEntity<?> response = null;
		String mplXML = null;
		try {
			 
	        	if( null != id)
	        	{
	        		mplXML = mediaService.getHlsMasterPlayList(id);
	        		//response =  new ResponseEntity<>(mplXML, HttpStatus.OK);
	        		response = ResponseEntity.ok().cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS )).body(mplXML);
	        	}
		}
		catch (HlsMediaException e)
		{
			response =  new ResponseEntity<>("HLS Master PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
		logger.info(".........................................HLS MasterPlayList Generation Completed for  id:"+id);
		return response;
    }
	
	
	
	/**
	 * get HLS Child PlayList for Respective PlayList ID
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/hls/playlist/child/{id:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> getHlsPlayList(@PathVariable("id") String id)  
	{	ResponseEntity<?> response = null;
		String mplXML = null;
		try {
			 
	        	if( null != id)
	        	{
	        		mplXML = mediaService.getHlschildPlayList(id);
	        		response = ResponseEntity.ok()
	        				.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
	        				.header("access-control-allow-credentials", "true")
	        				.header("accept-ranges", "bytes")
	        				.header("access-control-allow-headers", "Range")
	        				.header("content-type", "application/x-mpegURL")
					.		 body(mplXML);
	        				
	        	}
		}
		catch (HlsMediaException e)
		{
			logger.error(e.getMessage(), e);
			response =  new ResponseEntity<>("HLS Master PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
		logger.info(".........................................HLS PlayList Generation Completed for  id:"+id);
		return response;
    }
	
	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createMediaStream(@PathVariable("id") long id)
	{
		logger.info("HLS Media Processing with id {}", id);
        
        int totalFiles = 0;
        String hlsMediaPath= "F:\\2\\DASH_Test\\Plain1\\beach";
        
        Path videoPath = Paths.get(hlsMediaPath);
        
        if((Files.exists(videoPath)) && (videoPath.toFile().isDirectory()))
        {
        	logger.info("HLS Content Available at "+videoPath.toString());
        	
        	List<File> fileList = Arrays.asList(videoPath.toFile().listFiles());
        	
        	totalFiles = fileList.size();
        	
        	for (File file : fileList)
        	{
        		System.out.println("================ File Name:"+file.getName());
        		if(file.getName().equalsIgnoreCase("playlist.m3u8"))
        		{
        			
        		}
        		logger.info("Files Name:"+file.getName()+",Lenght in Bytes:"+file.length()+",isFile: "+file.isFile()
        		+" ,totalSpeace:", file.getTotalSpace()+" ,UsableSpace: "+file.getUsableSpace());
        	}
        }

 
        return new ResponseEntity<>("Total Files:"+totalFiles, HttpStatus.OK);
    }

	
	/*
	  @GetMapping("/hls/segment/{id:.+}")
	    public void streamVideoPlain(@PathVariable String id, HttpServletResponse response, @RequestHeader(value = "Range", required = false) String range) 
	   {
	      logger.info("Generating Segment Stream for ID:"+id);
		  mediaService.getSegmentData(id, range, response);
	   }
	  
	  */
	  
	  @GetMapping("/hls/segment/{id:.+}")
	    public ResponseEntity<byte[]> getByteVideo(@PathVariable String id, HttpServletResponse response, @RequestHeader(value = "Range", required = false) String httpRangeList) 
	  {
	    	logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Serving HLS Segmenet for id:"+id);
	    			
	      return (mediaService.prepareSegmentContent(id, httpRangeList));
	    }
	    
	    
	    @GetMapping("/hls/segment/stream/{id:.+}")
	    public ResponseEntity<?> getStreamVideo(@PathVariable String id, @RequestHeader(value = "Range", required = false) String httpRangeList) 
	  {
	    	logger.info("...Serving HLS Segmenet Stream for id:"+id);
	    
	    	ResponseEntity<?> response = null;
		    try {
		    	
		    	InputStream stream = mediaService.prepareSegmentStreamContent(id);
		    	InputStreamResource  streamResource = new InputStreamResource(stream);
	        	response = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
                .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
                .body(streamResource);

	        }
	        catch (Exception e)
	        {
	        	logger.error(e.getMessage(), e);
	        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        return response;	
	    
	    
	    }
	    
	    
	    @RequestMapping(value = "/hls/{mediaId}/{playlistName:.+}", method = RequestMethod.GET, produces = "application/x-mpegURL")
	    public ResponseEntity<?> getTestHlsPlayList(@PathVariable("mediaId") String mediaId,
	    		@PathVariable("playlistName") String playListName	)  
		{	ResponseEntity<?> response = null;
			String mplXML = null;
			try {
				 
		        	if( null != mediaId)
		        	{
		        		mplXML = hlsUtil.getHlsPlayList(mediaId, playListName);
		        		//response =  new ResponseEntity<>(mplXML, HttpStatus.OK);
		        		response = ResponseEntity.ok().cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS )).body(mplXML);
		        	}
			}
			catch (HlsMediaException  e)
			{
				response =  new ResponseEntity<>("HLS PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	       
			logger.info(".........................................HLS MasterPlayList Generation Completed for  id:"+playListName);
			return response;
	    }
	    
	    
	    @GetMapping("/hls/{mediaId}/{segLocation}/{segFilename:.+}")
	    public ResponseEntity<?> getTestSegmentStream(@PathVariable ("mediaId") String mediaId,
	    		@PathVariable ("segLocation") String segLocation,
	    		@PathVariable ("segFilename") String segFileName,
	    		@RequestHeader(value = "Range", required = false) String httpRangeList) 
	  {
	    	logger.info("...Serving HLS Segmenet Stream for id:"+segFileName);
	    
	    	ResponseEntity<?> response = null;
		    try {
		    	
		    	InputStream stream = hlsUtil.getSegmentSetream(mediaId, segLocation, segFileName);
		    	InputStreamResource  streamResource = new InputStreamResource(stream);
	        	response = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
                .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
                .body(streamResource);

	        }
	        catch (Exception e)
	        {
	        	logger.error(e.getMessage(), e);
	        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        return response;	
	    
	    
	    }
	    


}
