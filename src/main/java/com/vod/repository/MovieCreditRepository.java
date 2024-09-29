package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.MovieCredit;

@Repository
public interface MovieCreditRepository extends MongoRepository<MovieCredit, String>{


	
}