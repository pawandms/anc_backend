package com.vod.messaging.model.vo;

import java.io.Serializable;

import com.vod.base.BaseVo;
import com.vod.msg.enums.MsgActionType;
import com.vod.msg.model.Message;

public class MsgReq extends BaseVo implements Serializable {
	
	private String senderId;
	private String recipentId;
	private Integer chnlId;
	private Message message;
	private String parentMsgID;
	private MsgActionType actionType;
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipentId() {
		return recipentId;
	}
	public void setRecipentId(String recipentId) {
		this.recipentId = recipentId;
	}
	public Integer getChnlId() {
		return chnlId;
	}
	public void setChnlId(Integer chnlId) {
		this.chnlId = chnlId;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public MsgActionType getActionType() {
		return actionType;
	}
	public void setActionType(MsgActionType actionType) {
		this.actionType = actionType;
	}
	public String getParentMsgID() {
		return parentMsgID;
	}
	public void setParentMsgID(String parentMsgID) {
		this.parentMsgID = parentMsgID;
	}
	
	
	
	

}
