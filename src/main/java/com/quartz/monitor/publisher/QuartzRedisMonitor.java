package com.quartz.monitor.publisher;


import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.conf.enums.MsgChildrenTypeEnum;
import com.quartz.monitor.conf.enums.StatusTypeEnum;
import com.quartz.monitor.conf.enums.WarnTypeEnum;
import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.handle.RedisWatchHandle;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.nosql.redis.dao.JedisDao;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzRedisMonitor extends AbstractQuartzMonitor<RedisMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( QuartzRedisMonitor.class );

    @Autowired
    private JedisDao jedisDao;

    private RedisWatchHandle<RedisMonitorDTO> redisWatchHandle;

    public void setRedisWatchHandle(RedisWatchHandle<RedisMonitorDTO> redisWatchHandle) {
        this.redisWatchHandle = ProxyUtil.getInstance(RedisWatchHandle.class,redisWatchHandle);
    }

    @Override
    protected void checkHeartJump() throws Exception{

        LOG.info("检查redis心跳");
        LOG.info("redis要检测的数据源为："+ DataSourcesAll.REDIS_DATA_SOURCES_LIST);
        List<RedisMonitorDTO> list = new ArrayList<>(DataSourcesAll.REDIS_DATA_SOURCES_LIST.size());
        for (int i = 0; i < DataSourcesAll.REDIS_DATA_SOURCES_LIST.size(); i++) {
            RedisMonitorDTO redisMonitorDTO = new RedisMonitorDTO();
            redisMonitorDTO.setDataSources(DataSourcesAll.REDIS_DATA_SOURCES_LIST.get(i));
            list.add(redisMonitorDTO);
        }
        LOG.warn(list);
        judgeExcepType(list);
    }

    @Override
    public RedisMonitorDTO judgeExcepType(RedisMonitorDTO redisMonitorDTO) throws Exception{
        LOG.info("开始判断redis异常类型");
        try {
                String pong = jedisDao.ping(redisMonitorDTO);
                LOG.info("心跳返回："+pong);
                super.assableStatus(redisMonitorDTO,StatusTypeEnum.SUCCESS_TYPE.getMsgCode(), "");
                redisWatchHandle.connectSuccess(redisMonitorDTO);
                return redisMonitorDTO;
        }catch ( Exception e){
            LOG.error("redis监控抛出的异常为："+ e.getClass().getName());
            if( !( e instanceof ConnectionRejectException) ) throw e;
            super.assableStatus(redisMonitorDTO,StatusTypeEnum.ERROR_TYPE.getMsgCode(), "");
            redisWatchHandle.connectReject(redisMonitorDTO);
            return redisMonitorDTO;
        }finally {
            LOG.info("judgeExcepType运行完成");
        }
    }

    @Override
    public void assableMonitor(RedisMonitorDTO redisMonitorDTO) {
        redisMonitorDTO.getMonitorType().setCode( MsgChildrenTypeEnum.REDISERR_TYPE.getMsgCode() );
        redisMonitorDTO.getMonitorType().setName( MsgChildrenTypeEnum.REDISERR_TYPE.getMsgName() );
        if( StringUtils.isBlank(redisMonitorDTO.getWarnType()) ){
            String msg = StatusTypeEnum.getNameByCode(redisMonitorDTO.getStatus());
            redisMonitorDTO.setContent(redisMonitorDTO.getMonitorType().getName()+ msg);
        }else{
            String msg = WarnTypeEnum.getNameByCode(redisMonitorDTO.getWarnType());
            redisMonitorDTO.setContent(redisMonitorDTO.getMonitorType().getName()+ msg);
        }
        String hostHash =  redisMonitorDTO.getDataSources().getHost() + "" ;
        redisMonitorDTO.setServerName(hostHash);//服务主键
    }


}
