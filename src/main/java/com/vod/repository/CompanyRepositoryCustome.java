package com.vod.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.vod.model.Company;

public interface CompanyRepositoryCustome {
	
	@Query(value = "{ 'tmdb_id' : {'$in' : ?0 } }")
	List<Company> getAllByTmdbids(List<String> tmdb_ids);

}
