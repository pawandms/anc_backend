package com.vod.service;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.vod.exception.HlsMediaException;
import com.vod.exception.MediaException;
import com.vod.vo.HlsMediaResponse;

public interface OldMediaService {
	
	/**
	 * Process Directory where Video Converted into HLS MediaPlylist and Segment files on Server
	 * @param path
	 * @throws HlsMediaException
	 */
	public HlsMediaResponse processHlsMediaDirectory(String movieId, String path) throws HlsMediaException;

	/**
	 * Generate HLSMasterPlayList for Given movieID in XML format and return as String
	 * @param movieId
	 * @return
	 * @throws HlsMediaException
	 */
	public String getHlsMasterPlayList(String movieId) throws HlsMediaException;
	
	/**
	 * Generate HLS Child PlayList for Multiple Resolution for given Movie ID
	 * @param moviedId
	 * @param playListId
	 * @return
	 * @throws HlsMediaException
	 */
	public String getHlschildPlayList(String playListId)throws HlsMediaException;
	
	/**
	 * Get HLS Segment Row Data for respective Segment ID
	 * @param id
	 * @param range
	 * @param response
	 */
	public void getSegmentData(String id, String range, HttpServletResponse response);
	
	/**
	 * Return Segment Data as ByteArray
	 * @param id
	 * @param range
	 * @return
	 */
	public ResponseEntity<byte[]> prepareSegmentContent(String id, String range);

	/**
	 * Get Input Stream of HLS Segment
	 * @param id
	 * @return
	 */
	public InputStream prepareSegmentStreamContent(String id) throws MediaException;
	
}
