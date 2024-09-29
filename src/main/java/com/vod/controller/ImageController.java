package com.vod.controller;

import java.io.InputStream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vod.enums.EntityType;
import com.vod.enums.ImageType;
import com.vod.exception.ImageException;
import com.vod.exception.ImageServiceException;
import com.vod.media.model.MediaImage;
import com.vod.model.Image;
import com.vod.service.ImageService;

@RestController
@RequestMapping(value= "/api/image")
public class ImageController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private ImageService imgService;

	@Deprecated
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addImage(@RequestParam("image") MultipartFile image,
    		@Valid @NotNull @RequestParam("entityType") EntityType entityType,
    		@Valid @NotNull @RequestParam("entityId") String entityID,
    		@Valid @NotNull @RequestParam("imageType") ImageType imageType) 
	{
		ResponseEntity<?> response = null;
		
		try {
			MediaImage img = imgService.addImage(entityType, imageType, entityID, image);	
			response =  new ResponseEntity<>(img, HttpStatus.OK);	
		}
		catch(  ImageServiceException e)
		{
			logger.error(e.getMessage(), e);
			response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return response;	
        
    }
	
	
	@RequestMapping(value = "/get/{imageID:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> getImage(@Valid @NotBlank @PathVariable String imageID) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	
	    	InputStream stream = imgService.getImage(imageID);
	    	if( null != stream)
	    	{
	    		InputStreamResource  streamResource = new InputStreamResource(stream);
	        	  //response =  new ResponseEntity<>(inputStreamResource, HttpStatus.OK);
		    	response = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(streamResource);
	    	}
	    	else {
	    		
	    	}
	    
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }
	
	@RequestMapping(value = "/remove/{imageID:.+}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeImage(@Valid @NotBlank @PathVariable String imageID) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	
	    	imgService.removeImage(imageID);
	    	String msg = "Image Removed with ID:"+imageID;
	    	response = ResponseEntity.ok().body(msg);
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }

	
	
	
}
