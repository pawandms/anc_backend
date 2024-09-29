package com.vod.email.service;

import com.vod.email.exception.EmailServiceException;
import com.vod.messaging.model.vo.EmailMsg;
import com.vod.oauth.model.UserVerifyToken;

public interface EmailService {
	
	
	/**
	 * Send Email Activation Message to User Post Signup
	 * @throws EmailServiceException
	 */
	public void sendEmailActivationMessage(UserVerifyToken uvt)throws EmailServiceException;

}
