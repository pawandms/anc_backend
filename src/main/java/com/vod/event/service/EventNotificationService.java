package com.vod.event.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vod.event.model.EventLog;
import com.vod.exception.UserServiceException;
import com.vod.msg.enums.EventEntityType;
import com.vod.msg.model.Channel;
import com.vod.msg.model.EventNotification;
import com.vod.msg.repository.EventNotificationRep;
import com.vod.msg.service.ChannelService;
import com.vod.msg.vo.SearchChannelVo;
import com.vod.msg.vo.SearchResp;
import com.vod.msg.vo.SearchUserVo;
import com.vod.msg.vo.SearchVo;
import com.vod.msg.vo.UserNotificationResp;
import com.vod.oauth.model.User;
import com.vod.oauth.service.UserService;
import com.vod.util.HelperBean;

@Service
public class EventNotificationService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EventNotificationRep eventNotificationRep;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService chnlService;
	

	@Autowired
	private HelperBean helper;
	
	public UserNotificationResp getUserNotification(String reqUserID, String userID, int page, int size) throws UserServiceException
	{
		UserNotificationResp result = null;
		try {
			
			result = new UserNotificationResp();
			result.setReqUserId(userID);
			
			Sort sort = Sort.by(Sort.Direction.ASC, "processFlag", "actionDate");
    		Pageable pageable = PageRequest.of(page, size, sort);
    	
			
			Page<EventNotification> eventPage = eventNotificationRep.getUserNotification(userID, pageable);
			result.setResult(eventPage);
			
			if(!eventPage.isEmpty())
			{
				processUserNotificaitonResult(result, eventPage);
				
			}
			
			
    	
   		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new UserServiceException(e.getMessage(), e);	
		}
		
		return result;
	}
	
	private void processUserNotificaitonResult(UserNotificationResp result, Page<EventNotification> eventPage) throws UserServiceException
	{
		try {
			
			// Extract User and Chnl Part of Notification
			helper.extractEntityIdsFromUserNotificaiton(result, eventPage.getContent());
			
			if(!result.getSearchUserIds().isEmpty())
			{
				List<User> users = userService.getUserForUserIds(result.getSearchUserIds());
				
				
				users.stream().forEach(user-> {
					try {
						SearchUserVo userVo = helper.getSearchUserFromUser(user);
						result.getUsers().add(userVo);
						
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				});
			}
			
			if(!result.getSearchMsgChnlIds().isEmpty())
			{
				List<Channel> chnls = chnlService.getChannelByIds(result.getSearchMsgChnlIds());
				
				chnls.stream().forEach(chnl-> {
					try {
						
						SearchChannelVo chnlVo = helper.getSearchChnlFromChnl(chnl);
						result.getChnls().add(chnlVo);
					}
					catch(Exception e)
					{
						throw new RuntimeException(e.getMessage(), e);
					}
				});
				
			}
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new UserServiceException(e.getMessage(), e);
		}
		
	}
	
	


}
