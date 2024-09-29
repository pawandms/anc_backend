package com.vod.msg.vo;

import java.io.Serializable;

import com.vod.base.BaseVo;

public class NatsMsg extends BaseVo implements Serializable {
	
	private String subject;
	private String msg;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
