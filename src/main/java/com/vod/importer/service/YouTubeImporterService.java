package com.vod.importer.service;

import com.vod.exception.ImporterException;
import com.vod.importer.model.YouTubeImportRequest;

public interface YouTubeImporterService {

	public YouTubeImportRequest importYouTubeMedia(YouTubeImportRequest yimpReq) throws ImporterException;
	
	/**
	 * Process YouTubeImport Request to verify if video is already download 
	 * @return
	 * @throws ImporterException
	 */
	public int processYouTubeImportRequest()throws ImporterException;
	
	public int processImportedYouTubeMedia() throws ImporterException;
	
	
}
