package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface MovieCreditImporterService {

	/**
	 * Import MovieCreditData for All Movies available in System
	 * @param localPath
	 * @throws ImporterException
	 */
	public int importMovieCreditData() throws ImporterException;
	
	

}
