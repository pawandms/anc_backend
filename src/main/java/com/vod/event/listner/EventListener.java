package com.vod.event.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.collection.ItemEvent;
import com.hazelcast.collection.ItemListener;
import com.vod.event.model.Event;
import com.vod.event.model.EventLog;
import com.vod.event.model.EventLogReq;
import com.vod.msg.enums.EventType;
import com.vod.msg.enums.UserActionType;
import com.vod.msg.service.NatsService;
import com.vod.oauth.model.UserVerifyToken;
import com.vod.util.JsonUtil;

@Service
public class EventListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NatsService natsService;
	
	@RabbitListener(queues = "eventQueue")
	public void listenEvent(Event  event) {
	   
		try {
			procesEvent(event);
		}
		catch(Exception e)
		{
			logger.info(e.getMessage(), e);
			
		}
		
	}
	
	private void procesEvent(Event event)
	{
		try {
			String subject = getSubject(event.getType(), event.getEntityId());
			String payload = JsonUtil.getInstance().pojo2Json(event);
			natsService.sendMessage(subject, payload);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getSubject(EventType event, String entityID)
	{
		String result = null;
		
		StringBuilder sb = new StringBuilder();
		
		switch (event) {
		
		case Chnl_Add_Msg:
				sb.append("chnl");
				sb.append(".");
				sb.append(entityID);
				sb.append(".");
				sb.append("msg");
				result = sb.toString();
				break;
				
		case Chnl_Remove_msg:
			sb.append("chnl");
			sb.append(".");
			sb.append(entityID);
			sb.append(".");
			sb.append("msg");
			result = sb.toString();
			break;
			
				
		case Chnl_Add_User:
			
			sb.append("chnl");
			sb.append(".");
			sb.append(entityID);
			sb.append(".");
			sb.append("user");
			result = sb.toString();
			break;
			
		case Chnl_Remove_User:
			
			sb.append("chnl");
			sb.append(".");
			sb.append(entityID);
			sb.append(".");
			sb.append("user");
			result = sb.toString();
			break;	
		
				
		}
		
		return result;
	}
	



}
