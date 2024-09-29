package com.vod.oauth.service;

import org.springframework.security.core.Authentication;

import com.vod.exception.AuthServiceException;
import com.vod.oauth.model.User;

public interface IAuthenticationFacade {
	
	Authentication getAuthentication();
	
	/**
     * Get AuthenticationDetails from Authentication Token
     * @return
     */
    public User getApiAuthenticationDetails() throws AuthServiceException;
    
    public String getJti()throws AuthServiceException;

}
