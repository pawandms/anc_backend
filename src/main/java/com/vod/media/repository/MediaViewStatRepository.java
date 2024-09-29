package com.vod.media.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.media.model.MediaViewStat;

@Repository
public interface MediaViewStatRepository extends MongoRepository<MediaViewStat, String> {
		
}
