package com.vod.msg.exceptions;

import com.vod.exception.BaseException;

public class MessageDaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public MessageDaoException(String message)
	{
		super(message);
	}

	public MessageDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
