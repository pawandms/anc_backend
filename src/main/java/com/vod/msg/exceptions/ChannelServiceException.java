package com.vod.msg.exceptions;

import com.vod.exception.BaseException;

public class ChannelServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public ChannelServiceException(String message)
	{
		super(message);
	}

	public ChannelServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
