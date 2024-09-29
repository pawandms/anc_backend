package com.vod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.vod.model.Customer;
import com.vod.model.Genre;
import com.vod.oauth.model.OauthClientDetails;
import com.vod.oauth.model.User;
import com.vod.oauth.model.UserAuth;
import com.vod.repository.CustomerRepository;

@Component
@Order(1)
public class CreateUser implements CommandLineRunner {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
	CustomerRepository customerRepository;
	
    
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" Create Use Activity Started.....");
		//createCustomer();
		//populateData();
		System.out.println(" Create Use Activity Completed.....");
		
	}
	
	
	private void populateData()
	{
		//createClientDetails();
		createUser();
		createUserAuth();
		
	}

	/* Not wokr some structrue change in OauthClientDetails on 15-08-21
	private void createClientDetails()
	{
		final String CONLLECTION_NAME = "oauth_client_details";

		OauthClientDetails ocd = new OauthClientDetails();
		ocd.setClientId("oauth2-jwt-client");
		ocd.setResourceIds("resource-server-rest-api");
		ocd.setClientSecret(passwordEncoder.encode("admin1234"));
		ocd.setAuthorizedGrantTypes("password,authorization_code,refresh_token,client_credentials,implicit");
		ocd.setRegisteredRedirectUris("http://localhost:9001/base/login");
		ocd.setAuthorities("");
		ocd.setAccessTokenValidity(7200);
		ocd.setRefreshTokenValidity(2592000);
		ocd.setAutoapprove(1);
		ocd.setAdditionalInformation(null);
		ocd.setScope("all");
		
		mongoTemplate.insert(ocd, CONLLECTION_NAME);
		
	}
	
	*/
	
	private void createUser()
	{
		
		final String CONLLECTION_NAME = "user";
		User user = new User();
		user.setUid("5e7d56c9b03e9a046ab26ca9");
		user.setUserName("admin");
		user.setAdmin(false);
		user.setEmail("admin@videoApp.com");
		Long emaibindTime = Long.parseLong("1585272521646");
		user.setEmailBindTime(emaibindTime);
		user.setStatus(2);
		user.setType(1);
		
		mongoTemplate.insert(user, CONLLECTION_NAME);
		
		
	}
	
	private void createUserAuth()
	{
		
		final String CONLLECTION_NAME = "userAuth";
		
		// UserAuth for Email
		UserAuth eauth = new UserAuth();
		eauth.setUid("5e7d56c9b03e9a046ab26ca9");
		eauth.setIdentityType(2);
		eauth.setIdentifier("admin@videoApp.com");
		eauth.setCertificate(passwordEncoder.encode("admin1234"));
		eauth.getRoles().add("ROLE_USER");
		
		
		// UserAuth for UserID
		UserAuth uauth = new UserAuth();
		uauth.setUid("5e7d56c9b03e9a046ab26ca9");
		uauth.setIdentityType(3);
		uauth.setIdentifier("admin");
		uauth.setCertificate(passwordEncoder.encode("admin1234"));
		uauth.getRoles().add("ROLE_USER");
		
		
		mongoTemplate.insert(eauth, CONLLECTION_NAME);
		mongoTemplate.insert(uauth, CONLLECTION_NAME);
		
	}
	

	
	
}
