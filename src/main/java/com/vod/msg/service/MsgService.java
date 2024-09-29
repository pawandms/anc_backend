package com.vod.msg.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vod.messaging.model.vo.MsgReq;
import com.vod.msg.exceptions.MsgServiceException;
import com.vod.msg.model.Attachment;
import com.vod.msg.model.Message;
import com.vod.msg.model.UserMsgView;
import com.vod.msg.vo.MessageActionVo;
import com.vod.msg.vo.MessageVo;

public interface MsgService {
	
	public Message addMessage(MessageActionVo request) throws MsgServiceException;
	
	public Message replyMessage(MessageActionVo request) throws MsgServiceException;
	
	public void performMsgAction(MessageActionVo request) throws MsgServiceException;
	
	@Deprecated
	public 	Page<MessageVo> getChannelMessageForUser(MessageActionVo request, Pageable pageable) throws MsgServiceException;
	
	/**
	 * Get Recent Message for channles for respective User
	 * @param userID
	 * @param channelIds
	 * @return
	 * @throws MsgServiceException
	 */
	@Deprecated
	public 	List<MessageVo> getRecentMessageOfChannelForUser(String userID, Collection channelIds) throws MsgServiceException;
	
	public Message getMessagesByMsgId(String msgId)throws MsgServiceException;
	
	/**
	 * Get Attachment by MsgID and ContentID
	 * @param msgId
	 * @param contentId
	 * @return
	 * @throws MsgServiceException
	 */
	public Attachment getAttachmentById(String msgId, String contentId)throws MsgServiceException;
	
	public List<Message> getMessagesByMsgIds(Collection msgIds)throws MsgServiceException;
	
	/**
	 * Get Message for Channel 
	 * @param chnlID
	 * @param page
	 * @return
	 * @throws MsgServiceException
	 */
	public Page<UserMsgView> getMessageForChannel(String userID, String chnlID, Pageable page)throws MsgServiceException; 
       
}
