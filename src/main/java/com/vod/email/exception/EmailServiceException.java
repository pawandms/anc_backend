package com.vod.email.exception;

import com.vod.exception.BaseException;

public class EmailServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public EmailServiceException(String message)
	{
		super(message);
	}

	public EmailServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
