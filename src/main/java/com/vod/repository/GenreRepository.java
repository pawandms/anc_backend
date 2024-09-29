package com.vod.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.model.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
	
	@Query(value="{ 'name' : ?0 }")
	Genre findByName(String name);

	Genre findByTmdbid(String tmdbid);

}
