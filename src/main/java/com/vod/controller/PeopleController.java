package com.vod.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vod.exception.PeopleException;
import com.vod.media.model.Person;
import com.vod.media.service.PeopleService;

@RestController
@RequestMapping(value= "/api/people")
public class PeopleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PeopleService peopleService;

	private String movieDB_URL = "https://api.themoviedb.org/3/";
	private String movieDB_apiKey = "e169b86bd6c6a69ffadb436e42d94a70";
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@Valid @NotBlank @PathVariable String id) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	
	    	getEmployeesFromMovieDB(id);
	    	response =  new ResponseEntity<>("Proxy Response", HttpStatus.OK);
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }
	
	private void getEmployeesFromMovieDB(String id)
	{
		  final String url = movieDB_URL+"person/{id}";
		  HttpHeaders headers = new HttpHeaders();
		  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		  Map<String, String> params = new HashMap<String, String>();
		    params.put("id", id); 
		    
		  URI uri = UriComponentsBuilder.fromUriString(url)
			        .buildAndExpand(params)
			        .toUri();
			uri = UriComponentsBuilder
			        .fromUri(uri)
			        .queryParam("api_key", movieDB_apiKey)
			        .build()
			        .toUri();
		  
	    RestTemplate restTemplate = new RestTemplate();
	    
	     HttpEntity<String> entity = new HttpEntity<String>(headers);
	     
	     HttpEntity<String> response = restTemplate.exchange(
	    	        uri, 
	    	        HttpMethod.GET, 
	    	        entity, 
	    	        String.class);
	     
	    
	     
	    logger.info("Person Response:"+response.getBody());
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addPerson(@RequestBody Person person) 
	{
		ResponseEntity<?> response = null;
		
		try {
			Person pep = peopleService.addPeople(person, false);
			response =  new ResponseEntity<>(pep, HttpStatus.OK);	
		}
		catch(PeopleException e)
		{
			logger.error(e.getMessage(), e);
			response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return response;	
        
    }
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getPeopleList(
    		@RequestParam(required = false) String name,
    	      @RequestParam(defaultValue = "0") int page,
    	      @RequestParam(defaultValue = "1") int size
    		) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	List<Person> peopleList = new ArrayList<Person>();
	    	 Pageable paging = PageRequest.of(page, size);
	    	 Page<Person> pepPage;
	    	 	
	    	 pepPage = peopleService.findByName(name, paging);
	    	 peopleList = pepPage.getContent();
	    	 
	    	 Map<String, Object> Pepresponse = new HashMap<>();
	    	 Pepresponse.put("people", peopleList);
	    	 Pepresponse.put("currentPage", pepPage.getNumber());
	    	 Pepresponse.put("totalItems", pepPage.getTotalElements());
	    	 Pepresponse.put("totalPages", pepPage.getTotalPages());
        	
        	  response =  new ResponseEntity<>(Pepresponse, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }


}
