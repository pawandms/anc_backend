package com.vod.msg.exceptions;

import com.vod.exception.BaseException;

public class ChannelDaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public ChannelDaoException(String message)
	{
		super(message);
	}

	public ChannelDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
