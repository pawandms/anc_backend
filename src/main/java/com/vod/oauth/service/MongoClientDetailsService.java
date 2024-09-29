package com.vod.oauth.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.vod.enums.SequenceType;
import com.vod.exception.SequencerException;
import com.vod.exception.UserException;
import com.vod.oauth.enums.AuthorityType;
import com.vod.oauth.enums.GrantType;
import com.vod.oauth.model.OauthClientDetails;
import com.vod.oauth.repository.OauthClientDetailsRepository;
import com.vod.oauth.vo.ClientDetailsVo;
import com.vod.util.HelperBean;

@Service("mongoClientDetailsService")
public class MongoClientDetailsService implements ClientDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String CONLLECTION_NAME = "oauth_client_details";

    @Autowired
    MongoTemplate mongoTemplate;

	@Autowired
	private HelperBean helper;

    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OauthClientDetailsRepository oauthClientRep;
    
//    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
       
    
    	/*
    	OauthClientDetails oauthclient = oauthClientRep.findByClientId(clientId);
    	
    	if( null == oauthclient)
    	{
    		 throw new RuntimeException("no client information found");
    	}
    	
    	BaseClientDetails client =  convertoAuthClientToBaseClient(oauthclient);
    	*/
    	
    	 
    	BaseClientDetails client = mongoTemplate.findOne(new Query(Criteria.where("clientId").is(clientId)), BaseClientDetails.class, CONLLECTION_NAME);
        if(client==null){
            throw new RuntimeException("no client information found");
        }
        return client;
    }
    
    
    /*
    private BaseClientDetails convertoAuthClientToBaseClient(OauthClientDetails oauthclient)
    {
    	BaseClientDetails bcdl = null;
    	
    	bcdl = new BaseClientDetails();
    
    	bcdl.setClientId(oauthclient.getClientId());
    	bcdl.setClientSecret(oauthclient.getClientSecret());
    	bcdl.getScope().addAll(oauthclient.getScope());
    	bcdl.getResourceIds().add(oauthclient.getResourceIds());
    	if( !oauthclient.getAuthorizedGrantTypes().isEmpty())
    	{
    		bcdl.getAuthorizedGrantTypes().addAll(oauthclient.getAuthorizedGrantTypes());
    	}
    	
    	bcdl.getRegisteredRedirectUri().add(oauthclient.getRegisteredRedirectUris());
    	bcdl.getAutoApproveScopes().add("true");
    	if( !oauthclient.getAuthorities().isEmpty())
    	{
    		List<GrantedAuthority> grantedAuthorities = oauthclient.getAuthorities().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    		
    		bcdl.getAuthorities().addAll(grantedAuthorities);
    	}
    	bcdl.setAccessTokenValiditySeconds(oauthclient.getAccessTokenValidity());
    	bcdl.setRefreshTokenValiditySeconds(oauthclient.getRefreshTokenValidity());
    	bcdl.getAdditionalInformation().putAll(oauthclient.getAdditionalInformation());
    	
    	return bcdl;
    	
    }

*/

    public void addClientDetails(ClientDetails clientDetails) {
        mongoTemplate.insert(clientDetails, CONLLECTION_NAME);
    }

    public void updateClientDetails(ClientDetails clientDetails) {
        Update update = new Update();
        update.set("resourceIds", clientDetails.getResourceIds());
        update.set("clientSecret", clientDetails.getClientSecret());
        update.set("authorizedGrantTypes", clientDetails.getAuthorizedGrantTypes());
        update.set("registeredRedirectUris", clientDetails.getRegisteredRedirectUri());
        update.set("authorities", clientDetails.getAuthorities());
        update.set("accessTokenValiditySeconds", clientDetails.getAccessTokenValiditySeconds());
        update.set("refreshTokenValiditySeconds", clientDetails.getRefreshTokenValiditySeconds());
        update.set("additionalInformation", clientDetails.getAdditionalInformation());
        update.set("scope", clientDetails.getScope());
        mongoTemplate.updateFirst(new Query(Criteria.where("clientId").is(clientDetails.getClientId())), update, CONLLECTION_NAME);
        
        log.info("Update Client Details Method Called................................................... ");
    }

    public void updateClientSecret(String clientId, String secret) {
        Update update = new Update();
        update.set("clientSecret", secret);
        mongoTemplate.updateFirst(new Query(Criteria.where("clientId").is(clientId)), update, CONLLECTION_NAME);
        log.info("Update Client Secret Method Called .....................................................");
        
    }

    public void removeClientDetails(String clientId) {
        mongoTemplate.remove(new Query(Criteria.where("clientId").is(clientId)), CONLLECTION_NAME);
        
        log.info("Remove Client Details Method Called.................................................. ");
    }

    public List<ClientDetails> listClientDetails(){
        return mongoTemplate.findAll(ClientDetails.class, CONLLECTION_NAME);
    }
    
    

}
