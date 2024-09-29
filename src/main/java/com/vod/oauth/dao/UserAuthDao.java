package com.vod.oauth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.vod.exception.UserAuthDaoException;
import com.vod.oauth.model.UserAuth;
import com.vod.oauth.repository.UserAuthRepository;

@Component
public class UserAuthDao {

	@Autowired
	private UserAuthRepository userAuthRep;
	
	
	  @CachePut(value="UserAuthCache")
		public void saveUserAuth(UserAuth uAuth) throws UserAuthDaoException
		{
			try {
				
				userAuthRep.save(uAuth);
				}
			catch(Exception e)
			{
				throw new UserAuthDaoException(e.getMessage(), e);
			}
			
		}
		
	
	 @Cacheable(value="UserAuthCache", key="#userName")
	public UserAuth getUserAuthByUserName(String userName) throws UserAuthDaoException
	{
		UserAuth result = null;
		
		try {
			result = userAuthRep.findByUserName(userName);
		}
		catch(Exception e)
		{
			throw new UserAuthDaoException(e.getMessage(), e);
		}
		
		return result;
	}

	
}
