package com.quartz.monitor.model.postgresqlModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */

import com.quartz.monitor.model.DateSources;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */
public class PostgresqlDataSources extends DateSources {


    @Override
    public String toString() {
        return "PostgresqlDataSources{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
