package com.vod.importer.service;

import com.vod.exception.HlsProcessingException;
import com.vod.importer.model.ImportMediaRequest;
import com.vod.importer.model.YouTubeMedia;

/**
 * Upload Generated HLS Encoding files to DB
 * @author pawan
 *
 */
public interface HlsUploaderService {
	
	
	/**
	 * Uploade YouTube Media Generated HLS Stream to DB
	 * @param ytm
	 * @throws HlsProcessingException
	 */
	public void uploadYouTubeGeneratedHls(YouTubeMedia ytm) throws HlsProcessingException;

	/**
	 * Upload Import Media Generated HLS Stream to Object Store
	 * @param imr
	 * @throws HlsProcessingException
	 */
	public void uploadImportMediaGeneratedHls(ImportMediaRequest imr) throws HlsProcessingException;

}
