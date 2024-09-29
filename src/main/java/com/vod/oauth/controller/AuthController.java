package com.vod.oauth.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vod.exception.UserException;
import com.vod.model.response.ApiError;
import com.vod.oauth.model.OauthClientDetails;
import com.vod.oauth.repository.OauthClientDetailsRepository;
import com.vod.oauth.service.UserService;
import com.vod.oauth.vo.ClientDetailsVo;
import com.vod.oauth.vo.UserVo;

@RestController
@RequestMapping(value= "/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthClient(@RequestBody ClientDetailsVo request) 
	{
		ResponseEntity<?> response = null;
	    
        try {
        		
        	if( null == request)
        	{
        		throw new UserException("Request can not be null");
        	}
        	
        	OauthClientDetails result = userService.saveClientDetails(request);
        	
        	response =  new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	if(!request.getErrors().isEmpty())
        	{
        		response =  new ResponseEntity<>(request.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	else {
        		response =  new ResponseEntity<>("User creation Error Msg:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
        	}
        	
        	
        }
        
        return response;	
        
    }
	

	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody UserVo request) 
	{
		ResponseEntity<?> response = null;
	    
        try {
        		
        	if( null == request)
        	{
        		throw new UserException("Request can not be null");
        	}
        	
        	UserVo result = userService.createUser(request);
        	
        	response =  new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	if(!request.getErrors().isEmpty())
        	{
        		
        		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, 
        				request.getErrorMessage() , request.getErrors());
        		response =  new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	else {
        		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, 
        				"Unable to create User");
        		
        		response =  new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);	
        	}
        	
        	
        }
        
        return response;	
        
    }
	


}
