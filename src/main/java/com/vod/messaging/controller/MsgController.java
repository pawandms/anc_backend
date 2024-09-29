package com.vod.messaging.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.vod.email.exception.EmailServiceException;
import com.vod.email.service.EmailService;
import com.vod.exception.HlsMediaException;
import com.vod.media.model.Media;
import com.vod.messaging.model.UserPushNotifier;
import com.vod.messaging.model.vo.EmailMsg;
import com.vod.messaging.model.vo.MsgReq;
import com.vod.messaging.repository.DataStream;
import com.vod.messaging.repository.InMemoryDataStreamRepository;
import com.vod.messaging.service.CounterService;
import com.vod.messaging.service.PushMsgService;
import com.vod.model.response.ApiError;
import com.vod.msg.exceptions.MsgServiceException;
import com.vod.msg.model.Message;
import com.vod.msg.service.MsgService;
import com.vod.msg.vo.MessageActionVo;
import com.vod.oauth.model.User;
import com.vod.oauth.service.IAuthenticationFacade;
import com.vod.vo.HlsMediaRequest;
import com.vod.vo.HlsMediaResponse;

@RestController
@RequestMapping(value= "/api/msg")
public class MsgController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IAuthenticationFacade authfacade;

	@Autowired
	private PushMsgService pushMsgService;

	private InMemoryDataStreamRepository dataStreamRepository;

	@Autowired
	private CounterService counterService;
	
	@Autowired
	private EmailService emailService;
	
	private Long defautlTimeOut = 86400000L;

	
	
	
	@RequestMapping(value="/user/{apiUserID}/subscribe", method=RequestMethod.GET, consumes="application/json")
	public ResponseEntity<UserPushNotifier> subscribeMsgNotifications(@PathVariable ("apiUserID") String apiUserID)
	{
		ResponseEntity<UserPushNotifier> response = null;
	
		try {
	
			UserPushNotifier notifier = new UserPushNotifier(defautlTimeOut);
			
			notifier.setAppUserID(apiUserID);
			pushMsgService.addUserNotifier(apiUserID, notifier);

			notifier.onCompletion(() -> pushMsgService.removeUserNotifier(apiUserID));
			notifier.onTimeout(() -> pushMsgService.removeUserNotifier(apiUserID));
			
			response =  ResponseEntity.ok().body(notifier);
			 
		
		}
		catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		return response;	
	}
	
	

	  @PostMapping("/streams")
	  public DataStreamDto createADataStream() {
	    DataStream dataStream = DataStream.create();
	    dataStreamRepository.add(dataStream);

	    return new DataStreamDto(dataStream.getId().toString());
	  }

	  @GetMapping("streams/{id}/data")
	  public SseEmitter getEventStream(@PathVariable final String id) {
	    DataStream dataStream = dataStreamRepository.findById(id);
	    if (dataStream == null) {
	      throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
	    }

	    return dataStream.getEmitter();
	  }

	  @Autowired
	  public void setDataStreamRepository(final InMemoryDataStreamRepository dataStreamRepository) {
	    this.dataStreamRepository = dataStreamRepository;
	  }

	  @PostMapping(value = "/counters/increaseOrder", produces = "text/plain")
	  public void increaseCounter() {
	    counterService.increaseCount();
	  }

}
