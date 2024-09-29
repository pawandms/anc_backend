package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface CollectionImporterService {

	/**
	 * Import MovieCollection Data Extracted from Json File
	 * @param localPath
	 * @throws ImporterException
	 */
	public int importMovieCollectionData(String localPath) throws ImporterException;
	
	

}
