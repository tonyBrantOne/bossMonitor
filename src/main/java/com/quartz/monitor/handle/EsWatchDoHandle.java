package com.quartz.monitor.handle;


import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.nosql.elasticsearch.dao.impl.ElasticsearchDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class EsWatchDoHandle implements EsWatchHandle<EsMonitorDTO> {
    private Logger logger = LogManager.getLogger(EsWatchDoHandle.class);

    @Override
    public void connectExcessWarnning(EsMonitorDTO esMonitorDTO) {
        System.out.println("EsRedis连接数超时");
    }

    @Override
    public void connectReject(EsMonitorDTO esMonitorDTO) throws Exception{
        logger.error("es连接数超时");
    }

    @Override
    public void connectSuccess(EsMonitorDTO esMonitorDTO) {
        logger.warn("es连接成功");
    }
}
