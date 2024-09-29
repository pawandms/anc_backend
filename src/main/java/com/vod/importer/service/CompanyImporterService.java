package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface CompanyImporterService {

	/**
	 * Import Person Data from MovieDB Extracted Person Json File
	 * @param localPath
	 * @throws ImporterException
	 */
	public int importCompanyData(String localPath) throws ImporterException;
	
	

}
