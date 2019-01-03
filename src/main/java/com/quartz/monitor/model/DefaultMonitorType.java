package com.quartz.monitor.model;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */
public abstract class DefaultMonitorType {
    protected String code;//监控类型编码(es、postgresql、redis)
    protected String name;//监控类型名称（es搜索引擎、postgresql数据库、redis缓存）

    public DefaultMonitorType() {
    }

    public DefaultMonitorType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
