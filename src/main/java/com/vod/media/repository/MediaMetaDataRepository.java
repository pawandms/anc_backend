package com.vod.media.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.enums.MediaType;
import com.vod.media.model.Media;
import com.vod.media.model.MediaMetaData;

@Repository
public interface MediaMetaDataRepository extends MongoRepository<MediaMetaData, String> {

	
	// Paginate over a full-text search result
	Page<MediaMetaData> findAllBy(TextCriteria criteria, Pageable pageable);
	
	@Query(value="{ mediaId: { $eq: ?0 }}" )
	public List<MediaMetaData> findByMediaId(String mediaId);


	
}
