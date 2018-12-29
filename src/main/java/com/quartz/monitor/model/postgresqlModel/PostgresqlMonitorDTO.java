package com.quartz.monitor.model.postgresqlModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */


import com.quartz.monitor.model.AbstractMonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class PostgresqlMonitorDTO extends AbstractMonitorDTO {
    private PostgresqlConnectDTO connectDTO = new PostgresqlConnectDTO();
    private PostgresqlDataSources dataSources = new PostgresqlDataSources();
}
