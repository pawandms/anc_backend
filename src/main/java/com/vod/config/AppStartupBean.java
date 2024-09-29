package com.vod.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.vod.cache.exception.CacheServiceException;
import com.vod.util.EnvProp;

import com.sun.jersey.api.client.Client;
import org.json.simple.parser.JSONParser;


@Service
public class AppStartupBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnvProp env;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private String emailConfirmationQueueName;
	private String eventQueueName;
	private String appEventQueueName;
	private String eventLogQueueName;
	
	@PostConstruct
	public void init() throws CacheServiceException
	{
		logger.error("Application Startup Bean Executed Started.....");
		emailConfirmationQueueName =  env.getActivationEmailQueueName();
		eventQueueName = env.getEventQueueName();
		appEventQueueName = env.getAppEventQueueName();	
		
		// Advance Object Strucute to Replace regid Event Object 
		eventLogQueueName = env.getEventLogQueueName();
		
		logger.error("Application Startup Bean Executed Completed.....");
	}
	
	
	
	@Bean
	Queue emailCofirmationQueue() {
		
		return new Queue(emailConfirmationQueueName, true);
	}
	
	@Bean
	Queue eventQueue() {
		
		return new Queue(eventQueueName, true);
	}
	
	@Bean
	Queue appEventQueue() {
		
		return new Queue(appEventQueueName, true);
	}


	@Bean
	Queue eventLogQueue() {
		
		return new Queue(eventLogQueueName, true);
	}

	
	@Bean
	Client createJerseyClient() {
		return Client.create();
	}
	
	@Bean
	JSONParser createJsonParser() {
		return new JSONParser();
	}
	
}
