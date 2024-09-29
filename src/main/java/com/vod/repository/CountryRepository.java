package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.Country;

@Repository
public interface CountryRepository extends MongoRepository<Country, String>{
	
}