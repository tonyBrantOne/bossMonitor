package com.quartz.monitor.publisher;


import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.handle.EsWatchHandle;
import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzEsMonitor extends AbstractQuartzMonitor<EsMonitorDTO> {
    private  Logger LOG = LogManager.getLogger( QuartzRedisMonitor.class );

    private EsWatchHandle esWatchHandle;

    public EsWatchHandle getEsWatchHandle() {
        return esWatchHandle;
    }

    public void setEsWatchHandle(EsWatchHandle esWatchHandle) {
        this.esWatchHandle = ProxyUtil.getInstance(EsWatchHandle.class,esWatchHandle);
    }

    @Override
    protected void checkHeartJump() throws Exception{
        LOG.info("检查es心跳");
        LOG.info("es要检测的数据源为："+ DataSourcesAll.ES_DATA_SOURCES_LIST);
        List<EsMonitorDTO> list = new ArrayList<>(DataSourcesAll.ES_DATA_SOURCES_LIST.size());
        for (int i = 0; i < DataSourcesAll.ES_DATA_SOURCES_LIST.size(); i++) {
            EsMonitorDTO esMonitorDTO = new EsMonitorDTO();
            esMonitorDTO.setDataSources(DataSourcesAll.ES_DATA_SOURCES_LIST.get(i));
            list.add(esMonitorDTO);
        }
        LOG.warn(list);
        judgeExcepType(list);
    }

    @Override
    public EsMonitorDTO judgeExcepType(EsMonitorDTO esMonitorDTO) throws Exception{
        return null;
    }

    @Override
    public void assableMonitor(EsMonitorDTO esMonitorDTO) {

    }


}
