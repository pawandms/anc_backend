package com.vod.messaging.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailMsg implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -265050711011992792L;
	private String from;
	private String to;
	private String subject;
	private String body;
	
	private List<Object> attachments;
    private Map<String, Object> props;
	
	
	
	public EmailMsg() {
		super();
		this.from = "admin@videohub.live";
		this.attachments = new ArrayList<Object>();
		this.props = new HashMap<String, Object>();
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<Object> getAttachments() {
		return attachments;
	}
	public Map<String, Object> getProps() {
		return props;
	}
	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	
	
	
}
