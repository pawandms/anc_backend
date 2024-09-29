package com.vod.media.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.vod.exception.PeopleException;
import com.vod.media.model.Person;

public interface PeopleService {

	
	/**
	 * Add People to System where People Json is in the form of Txt
	 * @param people
	 */
	public Person addPeople(Person person, boolean allowDuplicate) throws PeopleException;
	
	/**
	 * Find People with Pagination
	 * @param name
	 * @param pageable
	 * @return
	 * @throws PeopleException
	 */
	Page<Person> findByName(String name, Pageable pageable) throws PeopleException;
	
	
	
}
