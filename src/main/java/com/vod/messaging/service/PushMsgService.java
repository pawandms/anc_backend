package com.vod.messaging.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.vod.messaging.model.UserPushNotifier;

@Service
public class PushMsgService {
	
	private Map<String,UserPushNotifier> userEmitterMap;

	public PushMsgService() {
		this.userEmitterMap = new ConcurrentHashMap<String, UserPushNotifier>();
	}
	
	
	public void addUserNotifier(String appUserID,UserPushNotifier userEmitter )
	{
		if( ( null != appUserID) && (null != userEmitter))
		{
			userEmitterMap.put(appUserID, userEmitter);
		}
	}
	
	
	public boolean removeUserNotifier(String appUserID)
	{
		boolean result = false;
		if(userEmitterMap.containsKey(appUserID))
		{
			userEmitterMap.remove(appUserID);
			result = true;
		}
		
		return result;
	}
	
	

}
