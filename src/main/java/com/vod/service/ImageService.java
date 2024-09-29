package com.vod.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.vod.enums.EntityType;
import com.vod.enums.ImageType;
import com.vod.exception.ImageException;
import com.vod.exception.ImageServiceException;
import com.vod.exception.PeopleException;
import com.vod.media.model.MediaImage;
import com.vod.model.Image;

public interface ImageService {
	
	/**
	 * Add Image to DB
	 * @param entityType
	 * @param imageType
	 * @param image
	 * @return
	 * @throws ImageException
	 */
	public MediaImage addImage(EntityType entityType,  ImageType imageType, String entityId, MultipartFile image) throws ImageServiceException;

	public MediaImage addImage(EntityType entityType,  ImageType imageType,  String entityId, String name, InputStream  stream) throws ImageServiceException;

	public MediaImage addImage(EntityType entityType,  ImageType imageType,  String entityId, String name, int widht, int height, InputStream  stream) throws ImageServiceException;

	
	/**
	 * Remove Image From Database
	 * @param imageId
	 * @throws ImageException
	 */
	public void removeImage(String imageId) throws ImageServiceException;

	
	/**
	 * Get Image by ID from Database
	 * @param imageID
	 * @return
	 * @throws ImageException
	 */
	public InputStream getImage(String imageID)throws ImageServiceException;
}
