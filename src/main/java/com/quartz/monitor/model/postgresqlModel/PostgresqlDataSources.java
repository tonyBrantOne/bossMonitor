package com.quartz.monitor.model.postgresqlModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */

import com.quartz.monitor.model.DefaultDateSources;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */
public class PostgresqlDataSources extends DefaultDateSources {


    public PostgresqlDataSources() {
    }

    public PostgresqlDataSources(String sourceBeanName, String user, String password, String host) {
        super(sourceBeanName, user, password, host);
    }

    @Override
    public String toString() {
        return "PostgresqlDataSources{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
