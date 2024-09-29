package com.vod.msg.exceptions;

import com.vod.exception.BaseException;

public class MsgServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public MsgServiceException(String message)
	{
		super(message);
	}

	public MsgServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
