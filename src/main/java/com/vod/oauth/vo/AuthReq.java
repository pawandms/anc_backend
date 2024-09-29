package com.vod.oauth.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vod.msg.enums.PermissionType;
import com.vod.msg.enums.UserRoleType;

public class AuthReq {
	
	// User on whcih action needs to be perform
	private String reqUserID;
	
	// User who is performing action
	private String userID;
	private PermissionType reqPermission;
	private Set<UserRoleType> reqUserRoles = new HashSet<>();
	private String chnlID;
	
	private boolean valid;

	
	
	public AuthReq(String reqUserID, String userID, String chnlID, PermissionType reqPermission, List<UserRoleType> reqUserRoles) {
		super();
		this.reqUserID = reqUserID;
		this.userID = userID;
		this.chnlID = chnlID;
		this.reqPermission = reqPermission;
		this.reqUserRoles.addAll(reqUserRoles) ;
	}
	
	public AuthReq(String reqUserID, String userID, PermissionType reqPermission) 
	{
		super();
		this.reqUserID = reqUserID;
		this.userID = userID;
		this.reqPermission = reqPermission;
		
	}

	

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

	public PermissionType getReqPermission() {
		return reqPermission;
	}

	public void setReqPermission(PermissionType reqPermission) {
		this.reqPermission = reqPermission;
	}

	
	public Set<UserRoleType> getReqUserRoles() {
		return reqUserRoles;
	}


	public String getChnlID() {
		return chnlID;
	}

	public void setChnlID(String chnlID) {
		this.chnlID = chnlID;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	

}
