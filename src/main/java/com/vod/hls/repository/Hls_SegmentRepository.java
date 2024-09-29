package com.vod.hls.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.hls.model.Hls_PlayList;
import com.vod.hls.model.Hls_Segment;

@Repository
public interface Hls_SegmentRepository extends MongoRepository<Hls_Segment, String> {
	
	@DeleteQuery
	Long deleteByMediaId(String mediaID);     

}
