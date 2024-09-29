package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface LanguageImporterService {
	
	/**
	 * Import Language Data from Temp MongoDB Table import_language
	 * @return
	 * @throws ImporterException
	 */
	public int importLanguageData() throws ImporterException;

}
