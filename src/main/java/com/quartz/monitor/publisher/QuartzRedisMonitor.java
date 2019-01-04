package com.quartz.monitor.publisher;


import com.quartz.monitor.handle.RedisWatchHandle;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzRedisMonitor extends AbstractQuartzMonitor<RedisMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( QuartzRedisMonitor.class );

    private RedisWatchHandle<RedisMonitorDTO> redisWatchHandle;

    public void setRedisWatchHandle(RedisWatchHandle<RedisMonitorDTO> redisWatchHandle) {
        this.redisWatchHandle = ProxyUtil.getInstance(RedisWatchHandle.class,redisWatchHandle);
    }

    @Override
    protected void checkHeartJump() {

        LOG.info("检查redis的心跳");
  //      redisWatchHandle.connectExcessWarnning(new RedisMonitorDTO());
        LOG.error("redis连接不上");
        redisWatchHandle.connectReject(new RedisMonitorDTO());
    }

    @Override
    public RedisMonitorDTO judgeExcepType(RedisMonitorDTO redisMonitorDTO) throws Exception{
        return null;
    }



}
