package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.model.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

}
