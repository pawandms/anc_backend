package com.vod.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vod.model.MovieCollection;

@Repository
public interface MovieCollectionRepository extends MongoRepository<MovieCollection, String>{

	/**
	 * Find All Company withc Matching Tmdb ID
	 * @param id
	 * @return
	 */
	 @Query(value = "{ 'tmdbId' : {'$in' : ?0 } }")
	public List<MovieCollection> findAllByTmdbidIn(Iterable<String> ids);

}