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
import com.quartz.monitor.listen.ProxyInvocationHandler;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
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
public class RedisWatchDoHandle extends DefaultDoHandle<RedisMonitorDTO> implements RedisWatchHandle<RedisMonitorDTO> {
    private static Logger LOG = LogManager.getLogger(RedisWatchDoHandle.class);

    @Autowired
    private EsMonitorService esMonitorService;

    @Autowired
    private EsMessageCenterService esMessageCenterService;

    @Override
    public void connectExcessWarnning(RedisMonitorDTO redisMonitorDTO) {
        LOG.info("Redis连接数负荷预警");
    }

    @Override
    public void connectReject(RedisMonitorDTO redisMonitorDTO) throws Exception {
        LOG.error("redis连接超时");

        LOG.info(redisMonitorDTO);
        esMonitorService.insertMonitorToEs(redisMonitorDTO);
        LOG.info("redis监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),redisMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        LOG.info("redis消息推送完成");
    }

    @Override
    public void connectSuccess(RedisMonitorDTO redisMonitorDTO) throws Exception{
        LOG.warn("redis连接成功");
        LOG.info(redisMonitorDTO);
        esMonitorService.insertMonitorToEs(redisMonitorDTO);
        LOG.info("redis监控数据保存完毕,开始推送消息");
        MessageCenter messageCenter = super.cloneMonitorToMsg(new MessageCenter(),redisMonitorDTO);
        esMessageCenterService.insertMsgToEs(messageCenter);
        LOG.info("redis消息推送完成");
    }


    @Override
    protected MessageCenter cloneMonitorToMsg(MessageCenter target, RedisMonitorDTO redisMonitorDTO) {
        target.setParentTypeCode(MsgParentTypeEnum.MONITOR_TYPE.getMsgCode());//监控类型
        target.setMsgTypeCode(redisMonitorDTO.getMonitorType().getCode());//数据库类型
        target.setNoticeWay(NoticeMediumEnum.MAIL_TYPE.getCode());//邮件
        return target;
    }
}
