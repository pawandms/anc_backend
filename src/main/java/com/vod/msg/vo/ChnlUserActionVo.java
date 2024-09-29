package com.vod.msg.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vod.base.BaseVo;
import com.vod.msg.enums.ChannelActionType;
import com.vod.msg.enums.ChannelType;
import com.vod.msg.enums.UserRoleType;
import com.vod.msg.model.ChannelParticipant;

public class ChnlUserActionVo extends BaseVo {

	private String reqUserID;
	private String userID;
	private String channelID;
	private ChannelType chnlType;
	private ChannelActionType actionType;
	private Date today = new Date();
	
	@JsonIgnore
	private List<UserRoleType> reqUserRoles = new ArrayList<>();
	
	private ChannelParticipant response;
	
	public String getReqUserID() {
		return reqUserID;
	}
	public void setReqUserID(String reqUserID) {
		this.reqUserID = reqUserID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public List<UserRoleType> getReqUserRoles() {
		return reqUserRoles;
	}
	public ChannelType getChnlType() {
		return chnlType;
	}
	public void setChnlType(ChannelType chnlType) {
		this.chnlType = chnlType;
	}
	
	public ChannelActionType getActionType() {
		return actionType;
	}
	public void setActionType(ChannelActionType actionType) {
		this.actionType = actionType;
	}
	public ChannelParticipant getResponse() {
		return response;
	}
	public void setResponse(ChannelParticipant response) {
		this.response = response;
	}
	public Date getToday() {
		return today;
	}
	
	
	
	
}
