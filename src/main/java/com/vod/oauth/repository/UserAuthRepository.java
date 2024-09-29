package com.vod.oauth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.oauth.model.UserAuth;

@Repository
public interface UserAuthRepository extends MongoRepository<UserAuth, String> {
	
	 @Query(value = "{ 'identifier' : ?0 }")
	 public UserAuth findByUserName(String userName);
	

}
