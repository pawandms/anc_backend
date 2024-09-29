package com.vod.msg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.msg.model.ChannelMsgRelation;

@Repository
public interface ChannelMsgRelationRep extends MongoRepository<ChannelMsgRelation, String> {

}
