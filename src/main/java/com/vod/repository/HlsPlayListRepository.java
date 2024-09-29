package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.hls.HlsPlayList;

@Repository
public interface HlsPlayListRepository extends MongoRepository<HlsPlayList, String> {

}
