package com.quartz.monitor.model;

import java.util.List;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 15:39
 * @Description:
 */
public abstract class DateSources {
    protected String user;
    protected String password;
    protected String host;

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
