package com.quartz.monitor.publisher;

import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.dao.PostgresqlDaoImpl;
import com.quartz.monitor.handle.PostgresqlWatchHandle;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzPostgresqlMonitor extends AbstractQuartzMonitor<PostgresqlMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( QuartzPostgresqlMonitor.class );

    private PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle;

    public PostgresqlWatchHandle<PostgresqlMonitorDTO> getPostgresqlWatchHandle() {
        return postgresqlWatchHandle;
    }

    public void setPostgresqlWatchHandle(PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle) {
        this.postgresqlWatchHandle = ProxyUtil.getInstance(PostgresqlWatchHandle.class,postgresqlWatchHandle);
    }

    @Override
    protected void checkHeartJump() throws Exception {
        LOG.info("检查postgresql心跳");
        LOG.info("postgresql要检测的数据源为："+ DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST);
        List<PostgresqlMonitorDTO> list = new ArrayList<>(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.size());
        for (int i = 0; i < DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.size(); i++) {
            PostgresqlMonitorDTO postgresqlMonitorDTO = new PostgresqlMonitorDTO();
            postgresqlMonitorDTO.setDataSources(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.get(i));
            list.add(postgresqlMonitorDTO);
        }
        judgeExcepType(list);
  //      postgresqlWatchHandle.connectExcessWarnning(new PostgresqlMonitorDTO());
    }

    @Override
    public PostgresqlMonitorDTO judgeExcepType(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception{
        LOG.info("开始判断postgresql异常类型");
        PostgresqlDao postgresqlDao = new PostgresqlDaoImpl();
        List<Map<String, Object>> list = postgresqlDao.selectCurrentConnections(postgresqlMonitorDTO);
        if( list == null || list.size() == 0 ) {
            postgresqlWatchHandle.connectReject(postgresqlMonitorDTO);
        }else{
            postgresqlWatchHandle.connectSuccess(postgresqlMonitorDTO);
        }
        return postgresqlMonitorDTO;
    }




}
