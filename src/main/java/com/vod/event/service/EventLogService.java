package com.vod.event.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vod.enums.SequenceType;
import com.vod.event.exceptions.EventLogServiceException;
import com.vod.event.model.EventLog;
import com.vod.event.model.EventLogReq;
import com.vod.event.repository.EventLogDao;
import com.vod.event.repository.EventLogRep;
import com.vod.exception.UserServiceException;
import com.vod.msg.enums.EventEntityType;
import com.vod.msg.enums.EventLogSubType;
import com.vod.msg.enums.EventLogType;
import com.vod.msg.enums.UserActionType;
import com.vod.msg.enums.VisibilityType;
import com.vod.msg.service.NatsService;
import com.vod.util.EnvProp;
import com.vod.util.HelperBean;
import com.vod.util.JsonUtil;

@Service
public class EventLogService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnvProp env;

	@Autowired
	private HelperBean helper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private EventLogRep eventLogRep;

	@Autowired
	private EventLogDao eventLogDao;
	
	@Autowired
	private EventLogUtil eventLogUtil;
	
	@Autowired
	private NatsService natsService;
	
	
	@Async
	public void sendEventLogToQueue(EventLogReq req) throws EventLogServiceException
	{
		try {
			// Step 1: Send App Event to Query
			String appEventQueueName = env.getAppEventQueueName();
			rabbitTemplate.convertAndSend(appEventQueueName, req);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
		}
	}
	
	
	public void processEventLog(EventLogReq event) throws EventLogServiceException
	{
		try {
			
			if( null == event.getActionType())
			{
				throw new EventLogServiceException("Action type can not be null for EventLogReq");
			}
			
			if((event.getActionType().equals(UserActionType.Add_Friend_Request))
				|| (event.getActionType().equals(UserActionType.Block_User_Request))
				|| (event.getActionType().equals(UserActionType.Create_User))
				|| (event.getActionType().equals(UserActionType.Create_Msg_Chnl))
					)
			{
				if(!event.getLogs().isEmpty())
				{
					event.getLogs().stream().forEach(log -> {
						
						try {
							String eventLogId = helper.getSequanceNo(SequenceType.EventLog);
							log.setId(eventLogId);
						}
						catch(Exception e)
						{
							logger.error(e.getMessage(), e);
							throw new RuntimeException(e.getMessage(), e);
							
						}
						
						
					});
				}
				eventLogRep.saveAll(event.getLogs());
			}
			else if (event.getActionType().equals(UserActionType.Remove_Friend_Request))
			{
				if(!event.getLogs().isEmpty())
				{
					event.getLogs().stream().forEach(log -> {
						
						try {
						
							eventLogRep.deleteUserConnection(log.getSrcKey(), log.getTrgValue().getUserId(), EventLogType.UC, EventLogSubType.UC_ADD );
						}
						catch(Exception e)
						{
							logger.error(e.getMessage(), e);
							throw new RuntimeException(e.getMessage(), e);
							
						}
						
						
					});
				}
				
			
			}
			else if (event.getActionType().equals(UserActionType.Change_ProfileType))
			{
				if(!event.getLogs().isEmpty())
				{
					event.getLogs().stream().forEach(log -> {
						
						try {
							eventLogDao.updateUserProfileType(log.getSrcKey(), log.getTrgValue().getVisibility());
							
						}
						catch(Exception e)
						{
							logger.error(e.getMessage(), e);
							throw new RuntimeException(e.getMessage(), e);
							
						}
						
						
					});
				}
				
			
			}
			else if (event.getActionType().equals(UserActionType.Add_Msg_Reaction))
			{
				
				// Add to  event_log_queue to so it will get Send to All respective nats client
				sendEventLogToNats(event);
			
			}
			
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
		}
		
	}
	
	
	private void sendEventLogToNats(EventLogReq eventLogReq) throws EventLogServiceException
	{
		try {
			
			eventLogReq.getLogs().forEach(elog-> {
				
				try {

					String subject = eventLogUtil.getNatsSubject(eventLogReq.getActionType(), elog);
					String payload = JsonUtil.getInstance().pojo2Json(elog);
					natsService.sendMessage(subject, payload);
				}
				catch(Exception e )
				{
					throw new RuntimeException(e.getMessage(), e);
				}
				
			});
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
		}
	}
	
	
	
	@Async
	public void createAddUserConnectionEvent(String reqUserID, String firstUserID, String secondUserID) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.createAddUserConnectionEventLogs(reqUserID, firstUserID, secondUserID);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
	}

	
	@Async
	public void createRemoveUserConnectionEvent(String reqUserID, String firstUserID, String secondUserID) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.createRemoveUserConnectionEventLogs(reqUserID, firstUserID, secondUserID);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
		}
	}
	
	
	@Async
	public void createBlockUserConnectionEvent(String reqUserID, String firstUserID, String secondUserID) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.createBlockUserConnectionEventLogs(reqUserID, firstUserID, secondUserID);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
	}
	
	@Async
	public void createCreateUserEvent(String reqUserID, String userID, String firstName, String lastName, VisibilityType visibility) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.creatUserCreateEventLogs(reqUserID, userID, firstName, lastName, visibility);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
	}
	
	
	@Async
	public void createChangeUserProfileEvent(String reqUserID, String userID, VisibilityType profileType) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.createChangeUserProfileEventLogs(reqUserID, userID, profileType);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
	}
	
	public Page<EventLog> searchUsersAndMsgChnls(String reqUserID, String searchKey, Pageable pageable) throws UserServiceException
	{
		Page<EventLog> resultPage = null;
		try {
			
    	resultPage =  eventLogDao.searchUsersAndMsgChnls(reqUserID, searchKey, pageable);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new UserServiceException(e.getMessage(), e);	
		}
		
		return resultPage;
	}
	
	@Async
	public void createMsgAddReactionEvent(String reqUserID, String userID, String chnlId, String msgID, String reactionStr) throws EventLogServiceException
	{
		try {
		
			EventLogReq req = eventLogUtil.createMsgAddReactionEventLogs(reqUserID, userID, chnlId,msgID,reactionStr);
			sendEventLogToQueue(req);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
	}


	/**
	 * Get Available UserConnection between Two Users
	 * @param srcUserID
	 * @param trgUserID
	 * @return
	 * @throws EventLogServiceException
	 */
	public List<EventLog> getConnectionBetweenTwoUser(String srcUserID, String trgUserID)throws EventLogServiceException
	{
		List<EventLog> result = null;
		
		try {
		
			result = eventLogRep.findUserConnectionBetweenTwoUsers(EventEntityType.User, EventEntityType.User, EventLogType.User_CN, srcUserID, trgUserID);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
		
		return result;
	}
	
	public List<EventLog> getPendingNotificaitonBetweenTwoUsers(String srcUserID, String trgUserID)throws EventLogServiceException
	{
		List<EventLog> result = null;
		
		try {
		
			result = eventLogRep.findUserConnectionBetweenTwoUsers(EventEntityType.User, EventEntityType.User, EventLogType.User_CN, srcUserID, trgUserID);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new EventLogServiceException(e.getMessage(), e);
			
		}
		
		return result;
	}

}
