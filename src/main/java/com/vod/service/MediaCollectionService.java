package com.vod.service;

import com.vod.exception.CollectionException;
import com.vod.model.MovieCollection;

public interface MediaCollectionService {
	
	/**
	 * Add Collection to DB
	 * @param col
	 * @return
	 * @throws CollectionException
	 */
	public MovieCollection addCollection(MovieCollection col) throws CollectionException; 

}
