package com.vod.importer.service;

import com.vod.exception.YouTubeDownloadException;
import com.vod.importer.model.YouTubeImportRequest;
import com.vod.importer.model.YouTubeMedia;

public interface YouTubeDownloadService {

	/**
	 * Download YouTube Media into YouTube Import Folder by Creating folder with YouTubeMedia ID
	 * @param ytm
	 * @throws YouTubeDownloadException
	 */
	public void downloadYouTubeMedia(YouTubeMedia ytm) throws YouTubeDownloadException;
	
	/**
	 * Download YouTube ID of Requested URL 
	 * and if downlaodChanngel = true 
	 * then download All Id's of this Channel belongs to this Request Video URL
	 * @param ytReq
	 * @throws YouTubeDownloadException
	 */
	public void downlaodYouTubeRequestData(YouTubeImportRequest ytReq)throws YouTubeDownloadException;
}
