package com.vod.msg.exceptions;

import com.vod.exception.BaseException;

public class ChannelParticipantDaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073323840744734538L;

	public ChannelParticipantDaoException(String message)
	{
		super(message);
	}

	public ChannelParticipantDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
