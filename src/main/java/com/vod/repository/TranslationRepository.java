package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.Translation;

@Repository
public interface TranslationRepository extends MongoRepository<Translation, String>{
	
}