package com.vod.media.service;

import java.util.List;

import com.vod.exception.CountryServiceException;
import com.vod.model.Country;

public interface CountryService {
	
	public List<Country> getAllCountry() throws CountryServiceException;

}
