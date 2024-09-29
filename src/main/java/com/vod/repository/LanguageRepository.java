package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.Language;

@Repository
public interface LanguageRepository extends MongoRepository<Language, String>{
	
}