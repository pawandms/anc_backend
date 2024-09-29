package com.vod.oauth.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.github.f4b6a3.uuid.UuidCreator;
import com.vod.enums.SequenceType;
import com.vod.exception.UserServiceException;
import com.vod.oauth.model.User;
import com.vod.oauth.service.UserService;
import com.vod.util.HelperBean;

@Configuration
public class JWTTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HelperBean helper;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication authentication) {
	
		Map<String, Object> additionalInfo = new HashMap<>();
		
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getUserAuthentication().getAuthorities();
        
		org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) authentication.getUserAuthentication().getPrincipal();
		
		if( null != user.getUsername())
		{
			try {
				User loggedInUser = userService.getUserDetails(user.getUsername());
				if( null != loggedInUser)
				{
					
				}
				else {
					
					loggedInUser = new User();
					loggedInUser.setFirstName("NA");
					loggedInUser.setLastName("NA");
					loggedInUser.setAdmin(false);
					loggedInUser.setUserName(user.getUsername());
					
				}
				
				additionalInfo.put("user", loggedInUser);
				String uuidSequence = helper.getSequanceNo(SequenceType.UUID);
				UUID uuid = UuidCreator.getNameBasedMd5(uuidSequence);
				additionalInfo.put("uuid", uuid);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Getting Auth User Details for UserName"+user.getUsername());
			}
		}
		
		additionalInfo.put("Role", authorities.toString());

        //All additional properties are placed in the HashMap and set on the accesstoken variable passed in to the method
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
	}

}
