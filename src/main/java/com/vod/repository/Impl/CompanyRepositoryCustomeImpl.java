package com.vod.repository.Impl;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.vod.model.Company;
import com.vod.repository.CompanyRepositoryCustome;

public class CompanyRepositoryCustomeImpl implements CompanyRepositoryCustome {

	@Override
	 @Query(value = "{ 'tmdb_id' : {'$in' : ?0 } }")
	 public List<Company> getAllByTmdbids(List<String> tmdb_ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
