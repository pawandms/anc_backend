package com.vod.msg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.msg.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

}
