package com.quartz.monitor.handle;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class PostgresqlWatchDoHandle implements PostgresqlWatchHandle<PostgresqlMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( PostgresqlWatchDoHandle.class );


    @Override
    public void connectExcessWarnning(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        System.out.println("数据库连接数过多");
    }

    @Override
    public void connectReject(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        LOG.error("数据库连接数超时");
    }

    @Override
    public void connectSuccess(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        LOG.error("数据库连接成功");
    }
}
