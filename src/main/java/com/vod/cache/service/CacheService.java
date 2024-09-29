package com.vod.cache.service;

import com.vod.cache.exception.CacheServiceException;
import com.vod.messaging.model.vo.EmailMsg;
import com.vod.oauth.model.UserVerifyToken;

public interface CacheService {
	
	/**
	 * Send Confirmation Email Message to User
	 * @param confirmationMsg
	 */
	public void sendSingupVerificationEmailMsg(UserVerifyToken confirmationMsg) throws CacheServiceException;

}
