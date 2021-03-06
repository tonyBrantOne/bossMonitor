package com.quartz.monitor.model.postgresqlModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */

import com.quartz.monitor.model.DefaultDateSources;
import com.quartz.monitor.util.connecPool.postgresql.ConnectProxy;

import java.sql.Connection;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */
public class PostgresqlDataSources extends DefaultDateSources<PostgresqlDataSources> {

    private ConnectProxy connectProxy = null;

    public PostgresqlDataSources() {
    }

    public PostgresqlDataSources(String sourceBeanName, String user, String password, String host) {
        super(sourceBeanName, user, password, host);
    }

    public ConnectProxy getConnectProxy() {
        return connectProxy;
    }

    public void setConnectProxy(ConnectProxy connectProxy) {
        this.connectProxy = connectProxy;
    }




    @Override
    public String toString() {
        return "PostgresqlDataSources{" +
                "sourceBeanName='" + sourceBeanName + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }

    public PostgresqlDataSources clone(PostgresqlDataSources target) throws Exception {
        target.setHost(this.getHost());
        target.setUser( this.getUser() );
        target.setPassword( this.getPassword() );
        return target;
    }
}
