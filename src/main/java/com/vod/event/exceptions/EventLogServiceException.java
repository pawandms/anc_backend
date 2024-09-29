package com.vod.event.exceptions;

import com.vod.exception.BaseException;

public class EventLogServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public EventLogServiceException(String message)
	{
		super(message);
	}

	public EventLogServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
