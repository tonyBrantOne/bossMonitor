package com.quartz.monitor.handle;


import com.quartz.monitor.conf.enums.MsgChildrenTypeEnum;
import com.quartz.monitor.conf.enums.MsgParentTypeEnum;
import com.quartz.monitor.conf.enums.NoticeMediumEnum;
import com.quartz.monitor.handle.base.DefaultDoHandle;
import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;
import com.quartz.monitor.nosql.elasticsearch.dao.impl.ElasticsearchDaoImpl;
import com.quartz.monitor.service.EsMessageCenterService;
import com.quartz.monitor.service.EsMonitorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class EsWatchDoHandle extends DefaultDoHandle<EsMonitorDTO> implements EsWatchHandle<EsMonitorDTO> {
    private Logger logger = LogManager.getLogger(EsWatchDoHandle.class);

    @Autowired
    private EsMonitorService esMonitorService;

    @Autowired
    private EsMessageCenterService esMessageCenterService;


    @Override
    public void connectExcessWarnning(EsMonitorDTO esMonitorDTO) {
        System.out.println("EsRedis连接数超时");
    }

    @Override
    public void connectReject(EsMonitorDTO esMonitorDTO) throws Exception{
        logger.error("es连接数超时");

        logger.info(esMonitorDTO);
        esMonitorService.insertMonitorToEs(esMonitorDTO);
        logger.info("es监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),esMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        logger.info("es消息推送完成");

    }

    @Override
    public void connectSuccess(EsMonitorDTO esMonitorDTO) throws Exception{
        logger.warn("es连接成功");
        logger.info(esMonitorDTO);
        esMonitorService.insertMonitorToEs(esMonitorDTO);
        logger.info("es监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),esMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        logger.info("es消息推送完成");
    }


    @Override
    protected MessageCenter cloneMonitorToMsg(MessageCenter target, EsMonitorDTO esMonitorDTO) {
        target.setParentTypeCode(MsgParentTypeEnum.MONITOR_TYPE.getMsgCode());//监控类型
        target.setMsgTypeCode(esMonitorDTO.getMonitorType().getCode());//数据库类型
        target.setNoticeWay(NoticeMediumEnum.MAIL_TYPE.getCode());//邮件
        return target;
    }
}
