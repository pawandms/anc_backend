package com.vod.msg.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vod.base.BaseVo;
import com.vod.msg.enums.ChannelSubType;
import com.vod.msg.enums.ChannelSubscriptionType;
import com.vod.msg.enums.ChannelType;
import com.vod.msg.enums.ChannelVisibility;
import com.vod.msg.enums.VisibilityType;
import com.vod.msg.model.Channel;
import com.vod.msg.model.ChannelParticipant;

public class CreateChannelVo extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 17391777918799888L;

	private String name;
	private String description;
	private ChannelType type;
	private ChannelSubType subType;
	private VisibilityType visibility;
	private ChannelSubscriptionType subscriptionType;
	
	private Channel response;
	// Request UserID 
	private String userID;
	private boolean valid;
	private List<ChannelParticipant> channelUsers = new ArrayList<>();
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ChannelType getType() {
		return type;
	}
	public void setType(ChannelType type) {
		this.type = type;
	}
	
	public ChannelSubType getSubType() {
		return subType;
	}
	public void setSubType(ChannelSubType subType) {
		this.subType = subType;
	}
	
	
	
	public VisibilityType getVisibility() {
		return visibility;
	}
	public void setVisibility(VisibilityType visibility) {
		this.visibility = visibility;
	}
	public ChannelSubscriptionType getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(ChannelSubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	
	public Channel getResponse() {
		return response;
	}
	public void setResponse(Channel response) {
		this.response = response;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public List<ChannelParticipant> getChannelUsers() {
		return channelUsers;
	}
	
	
	
		
}
