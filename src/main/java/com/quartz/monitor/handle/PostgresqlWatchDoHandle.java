package com.quartz.monitor.handle;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.service.EsMonitorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class PostgresqlWatchDoHandle implements PostgresqlWatchHandle<PostgresqlMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( PostgresqlWatchDoHandle.class );

    @Autowired
    private EsMonitorService esMonitorService;


    @Override
    public void connectExcessWarnning(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        System.out.println("数据库连接数过多");
    }

    @Override
    public void connectReject(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception {
        LOG.error("数据库连接数超时");
        LOG.warn(postgresqlMonitorDTO);
        esMonitorService.insertMonitorToEs(postgresqlMonitorDTO);
    }

    @Override
    public void connectSuccess(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception{
        LOG.error("数据库连接成功");
        LOG.warn(postgresqlMonitorDTO);
        esMonitorService.insertMonitorToEs(postgresqlMonitorDTO);
    }
}
