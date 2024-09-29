package com.vod.event.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.event.model.EventLog;
import com.vod.msg.enums.EventEntityType;
import com.vod.msg.enums.EventLogSubType;
import com.vod.msg.enums.EventLogType;
import com.vod.msg.enums.VisibilityType;
import java.util.List;


@Repository
public interface EventLogRep extends MongoRepository<EventLog, String> {


    @Query(value = "{ 'srcKey'  : ?0, 'logType' : ?2, 'logSubType' : ?3, 'trgValue.userId' : ?1 } ", delete = true)
	void deleteUserConnection(String srcUserID, String trgUserID, EventLogType logType, EventLogSubType logSubType );
   
    @Query(value = "{ 'srcType'  : ?0, 'trgType' : ?1, 'logType' : ?2, 'srcKey' : {'$in' : [?3, ?4]}} ")
    List<EventLog> findUserConnectionBetweenTwoUsers(EventEntityType srcType, EventEntityType trgType, EventLogType logType, String srcUserID, String trgUserID);
    
    
}
