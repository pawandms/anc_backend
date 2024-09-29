package com.vod.messaging.model;

import java.io.Serializable;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class UserPushNotifier extends SseEmitter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4618209400980046852L;
	
	
	/**
	 * Identify Device & Other details for User
	 */
	private String appUserID;
	
	public UserPushNotifier(Long timeout) {
		super(timeout);
		
		// TODO Auto-generated constructor stub
	}


	public String getAppUserID() {
		return appUserID;
	}


	public void setAppUserID(String appUserID) {
		this.appUserID = appUserID;
	}
	
	
	
}
