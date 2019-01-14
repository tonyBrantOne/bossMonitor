package com.quartz.monitor.handle;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.conf.enums.MsgChildrenTypeEnum;
import com.quartz.monitor.conf.enums.MsgParentTypeEnum;
import com.quartz.monitor.conf.enums.NoticeMediumEnum;
import com.quartz.monitor.handle.base.DefaultDoHandle;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.service.EsMessageCenterService;
import com.quartz.monitor.service.EsMonitorService;
import com.quartz.monitor.util.MapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class PostgresqlWatchDoHandle extends DefaultDoHandle<PostgresqlMonitorDTO> implements PostgresqlWatchHandle<PostgresqlMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( PostgresqlWatchDoHandle.class );

    @Autowired
    private EsMonitorService esMonitorService;

    @Autowired
    private EsMessageCenterService esMessageCenterService;

    @Override
    public void connectExcessWarnning(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        System.out.println("数据库连接数过多");
        LOG.warn(postgresqlMonitorDTO);

    }

    @Override
    public void connectReject(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception {
        LOG.error("数据库连接数超时");
        LOG.warn(postgresqlMonitorDTO);
        esMonitorService.insertMonitorToEs(postgresqlMonitorDTO);
        LOG.warn("监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),postgresqlMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        LOG.warn("消息推送完成");
    }

    @Override
    public void connectSuccess(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception{
        LOG.warn("数据库连接成功");
        LOG.info(postgresqlMonitorDTO);
        esMonitorService.insertMonitorToEs(postgresqlMonitorDTO);
        LOG.info("监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),postgresqlMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        LOG.info("消息推送完成");
    }

    @Override
    protected MessageCenter cloneMonitorToMsg(MessageCenter target, PostgresqlMonitorDTO postgresqlMonitorDTO) {
        target.setParentTypeCode(MsgParentTypeEnum.MONITOR_TYPE.getMsgCode());//监控类型
        target.setMsgTypeCode(postgresqlMonitorDTO.getMonitorType().getCode());//数据库类型
        target.setNoticeWay(NoticeMediumEnum.MAIL_TYPE.getCode());//邮件
        return target;
    }
}
