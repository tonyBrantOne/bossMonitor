package com.quartz.monitor.handle;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class PostgresqlWatchDoHandle implements PostgresqlWatchHandle<PostgresqlMonitorDTO> {


    @Override
    public void connectExcessWarnning(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        System.out.println("数据库连接数过多");
    }

    @Override
    public void connectReject(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        System.out.println("数据库连接数超时");
    }
}
