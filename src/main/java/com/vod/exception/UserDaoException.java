package com.vod.exception;

public class UserDaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public UserDaoException(String message)
	{
		super(message);
	}

	public UserDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
