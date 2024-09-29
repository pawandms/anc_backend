package com.vod.media.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.media.model.AppUser;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
		
}
