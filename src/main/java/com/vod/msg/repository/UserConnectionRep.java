package com.vod.msg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.vod.msg.model.UserConnection;
import com.vod.msg.enums.UserConnectionStatusType;


@Repository
public interface UserConnectionRep extends MongoRepository<UserConnection, String> {

	 @Query(value = "{$or : [ { 'srcUserId' : ?0, 'trgUserId' : ?1 } , { 'srcUserId' : ?1, 'trgUserId' : ?0 }]}")
	 List<UserConnection> findUserConnectionForUsers(String firstUserID, String secondUserID);
	
	 List<UserConnection> findBySrcUserIdAndTrgUserId(String srcUserId, String trgUserId);
	List<UserConnection> findBySrcUserIdAndTrgUserIdAndAndStatus(String srcUserId, String trgUserId, UserConnectionStatusType status);
	
	void deleteBySrcUserIdAndTrgUserIdAndStatus(String srcUserId, String trgUserId, UserConnectionStatusType status);
	
	void deleteByConnectionId(String connectionId);
}
