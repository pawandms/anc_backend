package com.vod.msg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.msg.model.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

	@Query(value = "{ '_id' : {'$in' : ?0 } }")
	List<Message> findAllByIdsIn(Iterable<String> msgIds);
	
}
