package com.quartz.monitor.model.msgCenterModel;

import com.quartz.monitor.model.MonitorDTO;

import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:07
 * @Description:
 */
public abstract class AbstractMsgDTO {
    protected Date publicTime;//生成时间
    protected String status;//(error,warn,success)
    protected String warnType;//提醒类型
    protected String serverName;//ip + 端口
    protected String content;//消息内容

    public AbstractMsgDTO() {
    }

    public AbstractMsgDTO(Date publicTime, String status, String warnType, String serverName, String content) {
        this.publicTime = publicTime;
        this.status = status;
        this.warnType = warnType;
        this.serverName = serverName;
        this.content = content;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }
}
