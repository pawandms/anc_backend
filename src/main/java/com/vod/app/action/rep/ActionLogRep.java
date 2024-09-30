package com.vod.app.action.rep;

import com.vod.app.action.model.ActionLog;
import com.vod.event.model.EventLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionLogRep extends MongoRepository<ActionLog, String>
{



}
