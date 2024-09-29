package com.vod.cache.listners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vod.email.service.EmailService;
import com.vod.oauth.model.UserVerifyToken;

@Service
public class RabbitMqListner {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailService emailService;
	
	@RabbitListener(queues = "activationEmail")
	public void listen(UserVerifyToken  activtionToken) {
	   
		try {
			emailService.sendEmailActivationMessage(activtionToken);
		}
		catch(Exception e)
		{
			logger.info(e.getMessage(), e);
			
		}
		
	}

}
