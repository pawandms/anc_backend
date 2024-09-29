package com.vod.oauth.service;

import java.util.Map;

import com.vod.exception.AuthServiceException;
import com.vod.oauth.vo.AuthReq;

public interface AuthService {
	
	/**
	 * Verify User Having Permission for given Action
	 * @param authReq
	 * @return
	 * @throws AuthServiceException
	 */
	public boolean hasPersmission(AuthReq authReq) throws AuthServiceException;
	
	public Map<String, Object> decodeToken(String token)throws AuthServiceException;

}
