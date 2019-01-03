package com.quartz.monitor.model;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:39
 * @Description:
 */

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:39
 * @Description:
 */
public abstract class DefaultInfo {
    protected Integer currentConnectNum = 0;//当前连接数
    protected Integer maxConnectNum = 0;//最大连接数

    public DefaultInfo() {
    }

    public DefaultInfo(Integer currentConnectNum, Integer maxConnectNum) {
        this.currentConnectNum = currentConnectNum;
        this.maxConnectNum = maxConnectNum;
    }

    public Integer getCurrentConnectNum() {
        return currentConnectNum;
    }

    public void setCurrentConnectNum(Integer currentConnectNum) {
        this.currentConnectNum = currentConnectNum;
    }

    public Integer getMaxConnectNum() {
        return maxConnectNum;
    }

    public void setMaxConnectNum(Integer maxConnectNum) {
        this.maxConnectNum = maxConnectNum;
    }



}
