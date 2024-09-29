package com.vod.event.exceptions;

import com.vod.exception.BaseException;

public class EventServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public EventServiceException(String message)
	{
		super(message);
	}

	public EventServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
