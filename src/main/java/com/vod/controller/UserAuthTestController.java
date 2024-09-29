package com.vod.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vod.oauth.model.User;

@Controller
@RequestMapping("user")
public class UserAuthTestController {

	public static final String ADMIN_USER = "ADMIN_USER";
    public static final String NORMAL_USER = "NORMAL_USER";
	
	@RequestMapping("/profile")
	//@PreAuthorize("#oauth2.hasAnyScope('read')")
	//@PreAuthorize("#oauth2.hasAnyScope('read') or #oauth2.hasAnyScope('write')")
	public ResponseEntity<?> getOauth2Principal(OAuth2Authentication auth) {
		ResponseEntity<?> response = null;
		User user = new User();
		String clientID = auth.getOAuth2Request().getClientId();
		String grantType = auth.getOAuth2Request().getGrantType();
		String name = auth.getName();
		user.setUserName(name);
		response = ResponseEntity.ok().body(user);
		return response;
		//return "Access granted for " + auth.getPrincipal();
		
	}
	
	
	

	//@PreAuthorize("#oauth2.hasAnyScope('read')")
	//@PreAuthorize("hasRole('ADMIN_USER')")
	@Secured({ADMIN_USER, NORMAL_USER})
	//@PreAuthorize("hasRole('ADMIN_USER')")
	@RequestMapping("/info")
	public @ResponseBody String getUserInfo() {
		return "Access granted for for User Info";
	}
	
}
