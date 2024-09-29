package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface MovieImporterService {

	/**
	 * Import Person Data from MovieDB Extracted Person Json File
	 * @param localPath
	 * @throws ImporterException
	 */
	public int importMovieData(String localPath) throws ImporterException;
	
	

}
