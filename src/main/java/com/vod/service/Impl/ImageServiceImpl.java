package com.vod.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vod.enums.EntityType;
import com.vod.enums.ImageType;
import com.vod.exception.ImageException;
import com.vod.exception.ImageServiceException;
import com.vod.exception.MinioServiceException;
import com.vod.media.model.Media;
import com.vod.media.model.MediaImage;
import com.vod.media.service.MediaService;
import com.vod.minio.MinioService;
import com.vod.model.Image;
import com.vod.model.vo.ImageInfo;
import com.vod.repository.ImageRepository;
import com.vod.service.ImageService;
import com.vod.util.HelperBean;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imgRep;
	
	@Autowired
	private HelperBean helper;

	@Autowired
	private MinioService minioService;
	
	@Autowired
	private MediaService mediaService;
	
	@Override
	public MediaImage addImage(EntityType entityType, ImageType imageType,  String entityId,MultipartFile image) throws ImageServiceException {
		
		MediaImage result = null;
		Image img = null;
		
		try {
			// getFileInfo
			if(entityType.equals(EntityType.Media))
			{
				ImageInfo imgInfo = helper.getImageInfo(image);
				
				// Get Respective Media's Existing Image Details
				Media media = mediaService.getMedia(entityId);
				
				if( null == media)
				{
					throw new ImageServiceException("Media not available for entity ID:"+entityId);
				}
				
				if(!media.getImageList().isEmpty())
				{
					// Verify its updating Existing Media or Insert New One
					result = media.getImageList().stream()
											.filter(mi -> imageType.equals(mi.getImageType()))
											.findAny()
											.orElse(null);
				
					if(( null != result))
					{
						if( null == result.getEntityId())
						{
							result.setEntityId(media.getId());
							
						}
						if (result.getId().equalsIgnoreCase("1.jpg"))
						{
							media.getImageList().remove(result);
							img = helper.createImageObject(imgInfo, entityType, imageType, entityId, null);
							result.setId(img.getId());
						}
						else {
							img = helper.createImageObject(imgInfo, entityType, imageType, entityId, result.getId());

						}
							
						
					}
										
					
				}
				
				if ( null == result)
				{ 	
					img = helper.createImageObject(imgInfo, entityType, imageType, entityId, null);
					result = new MediaImage(img.getId(), img.getEntityType(), img.getId());
					
				}
				// Save Image to DB
				
				saveImage(img,image);
				
				// Update Media Object with Updated Media
				
				result.setHeight(img.getHeight());
				result.setWidth(img.getWidth());
				result.setImageType(img.getImageType());
				media.getImageList().add(result);
				
				mediaService.saveMedia(media);
				
				
			}
			else {
				throw new ImageServiceException("Implementation not available for entity type:"+entityType.name());
			}
					
		}
		catch(Exception e)
		{
			throw new ImageServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	private void saveImage(Image img, MultipartFile image) throws ImageServiceException
	{
		
		try {
			minioService.addImage(img, image.getInputStream());
			// img.setData(new Binary(BsonBinarySubType.BINARY, IOUtils.toByteArray(image.getInputStream())));
			 imgRep.save(img);
			 img =null;
				
		}
		catch(Exception e)
		{
			throw new ImageServiceException("Image Upload Error:"+e.getMessage(), e);
		}
		
	}

	@Override
	public InputStream getImage(String imageID) throws ImageServiceException {
		
		InputStream imgStream = null;
		 try {
		        
	        	if( null == imageID)
	        	{
	        		throw new ImageException("Image ID can not be null");
	        	}
	        	
	        	imgStream = minioService.getImage(imageID);
	          
	        } catch ( ImageException | MinioServiceException e) {
	          
	        	throw new ImageServiceException(e.getMessage(), e);
	        }
		 	
		 return imgStream;
		
	}

	@Override
	public void removeImage(String imageId) throws ImageServiceException {
		
		imgRep.deleteById(imageId);
		
	}

	@Override
	public MediaImage addImage(EntityType entityType, ImageType imageType,  String entityId, String name, InputStream stream) throws ImageServiceException 
	{

		MediaImage result = null;
		
		try {
			// getFileInfo
			ImageInfo imgInfo = helper.getImageInfo(name, stream);
			Image img = helper.createImageObject(imgInfo, entityType, imageType, entityId, null);
		//	result = (Image) SerializationUtils.clone(img);

			
			// Save Image to DB
			//saveImage(img,stream);
		
			// Save Image to Minio Object Store
			minioService.addImage(img, stream);
			imgRep.save(img);
			
			result = new MediaImage(img.getId(), img.getEntityType(), img.getId());
			result.setHeight(img.getHeight());
			result.setWidth(img.getWidth());
			result.setImageType(img.getImageType());
			
		
		}
		catch( ImageException | MinioServiceException e)
		{
			throw new ImageServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	@Override
	public MediaImage addImage(EntityType entityType, ImageType imageType, String entityId, String name, int width,
			int height, InputStream stream) throws ImageServiceException {

		MediaImage result = null;
		
		try {
			// getFileInfo
		
			//ImageInfo imgInfo = helper.getImageInfo(name, stream);
			ImageInfo imgInfo = new ImageInfo();
			imgInfo.setWidth(width);
			imgInfo.setHeight(height);
			String nameStr = helper.getNameofFileName(name);
			String extnStr = helper.getExtensionofFileName(name);
			imgInfo.setName(nameStr);
			imgInfo.setExtn(extnStr);
			
			Image img = helper.createImageObject(imgInfo, entityType, imageType, entityId, null);

			// Save Image to Minio Object Store
			
			minioService.addImage(img, stream);
			//imgRep.save(img);
			
			result = new MediaImage(img.getId(), img.getEntityType(), img.getId());
			result.setHeight(img.getHeight());
			result.setWidth(img.getWidth());
			result.setImageType(img.getImageType());
			
		
		}
		catch( ImageException | MinioServiceException e)
		{
			throw new ImageServiceException(e.getMessage(), e);
		}
		
		return result;

	}

}
