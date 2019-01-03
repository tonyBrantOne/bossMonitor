package com.quartz.monitor.model;

import java.util.List;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 15:39
 * @Description:
 */
public abstract class DefaultDateSources {
    protected String sourceBeanName;
    protected String user;
    protected String password;
    protected String host;

    public DefaultDateSources() {
    }

    public DefaultDateSources(String sourceBeanName, String user, String password, String host) {
        this.sourceBeanName = sourceBeanName;
        this.user = user;
        this.password = password;
        this.host = host;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public void setSourceBeanName(String sourceBeanName) {
        this.sourceBeanName = sourceBeanName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
