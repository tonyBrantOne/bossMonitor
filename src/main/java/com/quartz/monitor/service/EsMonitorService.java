package com.quartz.monitor.service;

import com.quartz.monitor.model.MonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:52
 * @Description:消息发送到监控ES索引中的服务接口
 */
public interface EsMonitorService {


    public MonitorDTO insertMonitorToEs( MonitorDTO monitorDTO) throws Exception;


}
