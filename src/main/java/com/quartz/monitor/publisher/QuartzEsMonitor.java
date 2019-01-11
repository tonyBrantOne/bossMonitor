package com.quartz.monitor.publisher;


import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.conf.enums.StatusTypeEnum;
import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.handle.EsWatchHandle;
import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.nosql.elasticsearch.dao.ElasticsearchDao;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzEsMonitor extends AbstractQuartzMonitor<EsMonitorDTO> {
    private  Logger LOG = LogManager.getLogger( QuartzRedisMonitor.class );

    private EsWatchHandle esWatchHandle;

    @Autowired
    private ElasticsearchDao elasticsearchDao;

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
        LOG.info("开始判断es异常类型");
        try {
            Map<String, Object> resultMap = elasticsearchDao.ping(esMonitorDTO);
            LOG.info("心跳返回："+resultMap);
            super.assableStatus(esMonitorDTO,StatusTypeEnum.SUCCESS_TYPE.getMsgCode(), "");
            esWatchHandle.connectSuccess(esMonitorDTO);
            return esMonitorDTO;
        }catch ( Exception e){
            LOG.error("es监控抛出的异常为："+ e.getClass().getName());
            if( !( e instanceof ConnectionRejectException) ) throw e;
            super.assableStatus(esMonitorDTO,StatusTypeEnum.ERROR_TYPE.getMsgCode(), "");
            esWatchHandle.connectReject(esMonitorDTO);
            return esMonitorDTO;
        }finally {
            LOG.info("judgeExcepType运行完成");
        }
    }

    @Override
    public void assableMonitor(EsMonitorDTO esMonitorDTO) {

    }



}
