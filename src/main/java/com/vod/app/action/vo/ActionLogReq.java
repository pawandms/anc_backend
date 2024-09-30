package com.vod.app.action.vo;

import com.vod.app.action.model.ActionLog;
import com.vod.event.model.EventLog;
import com.vod.msg.enums.UserActionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionLogReq implements Serializable {

    private String reqUserID;
    private UserActionType actionType;
    private List<ActionLog> logs = new ArrayList<>();

    public String getReqUserID() {
        return reqUserID;
    }

    public void setReqUserID(String reqUserID) {
        this.reqUserID = reqUserID;
    }

    public UserActionType getActionType() {
        return actionType;
    }

    public void setActionType(UserActionType actionType) {
        this.actionType = actionType;
    }

    public List<ActionLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ActionLog> logs) {
        this.logs = logs;
    }
}
