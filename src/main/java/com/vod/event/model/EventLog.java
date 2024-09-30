package com.vod.event.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vod.msg.enums.EventEntityType;
import com.vod.msg.enums.EventLogSubType;
import com.vod.msg.enums.EventLogType;
import com.vod.msg.enums.UserActionStatusType;
import com.vod.msg.enums.UserActionType;

@Document(collection= "eventLog")
public class EventLog implements Serializable {
	
	@Id
	private String id;
	private EventEntityType srcType;
	private String srcKey;
	private EventEntityType trgType;
	private Meta trgValue;
	private EventLogType logType;
	private EventLogSubType logSubType;

	private String trgMsg;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crDate = new Date();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modDate = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EventEntityType getSrcType() {
		return srcType;
	}

	public void setSrcType(EventEntityType srcType) {
		this.srcType = srcType;
	}

	public String getSrcKey() {
		return srcKey;
	}

	public void setSrcKey(String srcKey) {
		this.srcKey = srcKey;
	}

	public EventEntityType getTrgType() {
		return trgType;
	}

	public void setTrgType(EventEntityType trgType) {
		this.trgType = trgType;
	}

	public EventLogType getLogType() {
		return logType;
	}

	public void setLogType(EventLogType logType) {
		this.logType = logType;
	}

	public EventLogSubType getLogSubType() {
		return logSubType;
	}

	public void setLogSubType(EventLogSubType logSubType) {
		this.logSubType = logSubType;
	}

	public String getTrgMsg() {
		return trgMsg;
	}

	public void setTrgMsg(String trgMsg) {
		this.trgMsg = trgMsg;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public Meta getTrgValue() {
		return trgValue;
	}

	public void setTrgValue(Meta trgValue) {
		this.trgValue = trgValue;
	}

}
