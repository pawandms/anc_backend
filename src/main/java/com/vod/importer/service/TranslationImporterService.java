package com.vod.importer.service;

import com.vod.exception.ImporterException;
import com.vod.importer.model.MovieTranslationImport;

public interface TranslationImporterService {
	
	
	public MovieTranslationImport getMovieTranslationData(String id) throws ImporterException;

}
