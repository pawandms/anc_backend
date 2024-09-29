package com.vod.oauth.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.oauth.model.UserVerifyToken;

@Repository
public interface UserVerifyTokenRepository extends MongoRepository<UserVerifyToken, String> {
	
	@Query(value="{ '_id': { $eq: ?0 }, 'verifyToken' : {$eq: ?1 }} ")
	public List<UserVerifyToken> findByIdAndVerifyToken (String id, String verifyToken);


}
