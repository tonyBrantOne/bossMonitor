package com.quartz.monitor.model;

import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:07
 * @Description:
 */
public abstract class AbstractMonitorDTO extends MonitorDTO {
    protected Date publicTime;//生成时间
    protected String status;//(error,warn,success)
    protected String serverName;//ip + 端口
    protected String content;//消息内容

    public AbstractMonitorDTO() {
    }

    public AbstractMonitorDTO(Date publicTime, String status, String serverName, String content) {
        this.publicTime = publicTime;
        this.status = status;
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
}
