package com.vod.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vod.enums.SequenceType;
import com.vod.exception.GenreException;
import com.vod.exception.SequencerException;
import com.vod.model.Genre;
import com.vod.sequencer.service.Sequencer;
import com.vod.service.GenreService;
import com.vod.util.HelperBean;

@RestController
@RequestMapping(value= "/api/genre")
public class GenreController {

	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenreService genreService;

	@Autowired
	private HelperBean helper;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addGenre(@RequestBody Genre genre) 
	{
		ResponseEntity<?> response = null;
		Genre genRes;
        try {
      
        	// Check if Genere with Same Name is Exists
        	
        	Genre gen = genreService.getGenrebyByName(genre.getName());
        	if (null != gen)
        	{
        		throw new GenreException("Genre with Name:"+genre.getName()+" Already Exists with ID:"+gen.getId());
        	}
        	
        	String id = helper.getSequanceNo(SequenceType.GENRE);
        	genre.setId(id);	
        	genRes =   genreService.addGenre(genre);
        	if( null == genRes)
        	{
        		throw new GenreException("Unable to Save Genere with Name:"+genre.getName());
        	}
        	
        	  response =  new ResponseEntity<>(genRes, HttpStatus.OK);
        }
        catch (GenreException | SequencerException e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }

	//@Secured({ROLE_ADMIN, ROLE_USER})
	//@PreAuthorize("#oauth2.hasScope('read')")
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGenre() 
	{
		ResponseEntity<?> response = null;
	    try {
      
        	List<Genre> genList =   genreService.getAllGenre();
    		
        	  response =  new ResponseEntity<>(genList, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
        
    }
}
