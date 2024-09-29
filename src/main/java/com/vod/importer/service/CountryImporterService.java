package com.vod.importer.service;

import com.vod.exception.ImporterException;

public interface CountryImporterService {
	
	/**
	 * Import Country Data from Temp MongoDB Table import_country
	 * @return
	 * @throws ImporterException
	 */
	public int importCountryData() throws ImporterException;

}
