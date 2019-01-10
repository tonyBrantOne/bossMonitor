package com.quartz.monitor.service;

import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:52
 * @Description:消息发送到消息中心中的服务监控
 */
public interface EsMessageCenterService {

    public MessageCenter insertMsgToEs(MessageCenter messageCenter) throws Exception;
}
