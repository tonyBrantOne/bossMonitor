package com.quartz.monitor.publisher;

import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.handle.PostgresqlWatchHandle;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
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
public class QuartzPostgresqlMonitor extends AbstractQuartzMonitor {
    private static Logger LOG = LogManager.getLogger( QuartzPostgresqlMonitor.class );

    private PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle;

    public PostgresqlWatchHandle<PostgresqlMonitorDTO> getPostgresqlWatchHandle() {
        return postgresqlWatchHandle;
    }

    public void setPostgresqlWatchHandle(PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle) {
        this.postgresqlWatchHandle = ProxyUtil.getInstance(PostgresqlWatchHandle.class,postgresqlWatchHandle);
    }

    @Override
    protected void checkHeartJump() {
        System.out.println("检查数据库心跳=========================================================");
        LOG.info("postgresql要检测的数据源为："+ DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST);
        LOG.error("postgresql连接异常");
  //      postgresqlWatchHandle.connectExcessWarnning(new PostgresqlMonitorDTO());
        postgresqlWatchHandle.connectReject(new PostgresqlMonitorDTO());
    }
}
