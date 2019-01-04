package com.quartz.monitor.handle;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.listen.ProxyInvocationHandler;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class RedisWatchDoHandle implements RedisWatchHandle<RedisMonitorDTO> {
    private static Logger LOG = LogManager.getLogger(RedisWatchDoHandle.class);


    @Override
    public void connectExcessWarnning(RedisMonitorDTO redisMonitorDTO) {
        LOG.info("Redis连接数负荷预警");
    }

    @Override
    public void connectReject(RedisMonitorDTO redisMonitorDTO) {
        LOG.info("Redis连接超时");
    }

    @Override
    public void connectSuccess(RedisMonitorDTO redisMonitorDTO) {

    }
}
