package com.vod.messaging.exceptions;

import com.vod.exception.BaseException;

public class MsgChannelServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public MsgChannelServiceException(String message)
	{
		super(message);
	}

	public MsgChannelServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
