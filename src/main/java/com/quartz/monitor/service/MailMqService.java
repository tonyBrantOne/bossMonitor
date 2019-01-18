package com.quartz.monitor.service;

import com.quartz.monitor.model.MonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/17 20:11
 * @Description:
 */
public interface MailMqService {

    /**
     * 生产预警消息
     * @param monitorDTO
     */
    public void produceMessage(MonitorDTO monitorDTO) throws Exception;


    public void consumeMessage() throws Exception;

}
