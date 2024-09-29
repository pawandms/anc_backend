package com.vod.oauth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.oauth.model.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends MongoRepository<OauthClientDetails, String> {

	public OauthClientDetails findByClientId(String clientId);
}
