package com.vod.oauth.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.stereotype.Service;


import com.vod.exception.AuthServiceException;
import com.vod.oauth.model.User;
import com.vod.oauth.service.IAuthenticationFacade;
import com.vod.oauth.service.UserService;

@Service
public class IAuthenticationFacadeImpl implements IAuthenticationFacade {

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public User getApiAuthenticationDetails() throws AuthServiceException {
		
		User key = null;

	       try {
	           Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
	           
	           String principal = (String) authentication.getPrincipal();

	           if( null != principal)
	           {
	        	 key=  userService.getUserDetails(principal);
	           }

	       }
	       catch(Exception e)
	       {
	            throw new AuthServiceException(e.getMessage(), e);
	       }

	        return key;
	}

	@Override
	public String getJti() throws AuthServiceException {
		
		String result = null;
	
		
		return result;
		
	}	
	

	
}
