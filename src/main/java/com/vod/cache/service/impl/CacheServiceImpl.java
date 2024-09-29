package com.vod.cache.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vod.cache.exception.CacheServiceException;
import com.vod.cache.service.CacheService;
import com.vod.oauth.model.UserVerifyToken;
import com.vod.util.EnvProp;

@Service
public class CacheServiceImpl implements CacheService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	//private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private EnvProp env;
	
	private String emailConfirmationQueueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
		
	/**
	 * Create RabbitMQ queue Bean
	 * @return
	 */
	/*
	@Bean
	Queue queue() {
		
		return new Queue(emailConfirmationQueueName, true);
	}
	*/
	
	
	@Override
	public void sendSingupVerificationEmailMsg(UserVerifyToken confirmationMsg) throws CacheServiceException {
		// TODO Auto-generated method stub
		
		logger.info("Sending Message to RabbitMQ");
		
		try {
			// Send Message to RabbitMq
			//rabbitTemplate.convertAndSend(emailConfirmationQueueName, confirmationMsg);	
			
			logger.info("Disable Email Sending for Email Configuraiton for Payload:"+confirmationMsg.toString());
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new CacheServiceException(e.getMessage(), e);
		}
		
		
	
		
	}


}
