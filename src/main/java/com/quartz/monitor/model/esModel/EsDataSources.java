package com.quartz.monitor.model.esModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */

import com.quartz.monitor.model.DefaultDateSources;
import com.quartz.monitor.util.connecPool.elasticsearch.HttpClientConnection;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */
public class EsDataSources extends DefaultDateSources<EsDataSources> {

    private String clustername;
    private String bulkactions;
    private String bulksize;
    private String flushinterval;
    private HttpClientConnection connectProxy;


    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getBulkactions() {
        return bulkactions;
    }

    public void setBulkactions(String bulkactions) {
        this.bulkactions = bulkactions;
    }

    public String getBulksize() {
        return bulksize;
    }

    public void setBulksize(String bulksize) {
        this.bulksize = bulksize;
    }

    public String getFlushinterval() {
        return flushinterval;
    }

    public void setFlushinterval(String flushinterval) {
        this.flushinterval = flushinterval;
    }

    @Override
    public String toString() {
        return "EsDataSources{" +
                "clustername='" + clustername + '\'' +
                ", bulkactions='" + bulkactions + '\'' +
                ", bulksize='" + bulksize + '\'' +
                ", flushinterval='" + flushinterval + '\'' +
                ", sourceBeanName='" + sourceBeanName + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }


    public HttpClientConnection getConnectProxy() {
        return connectProxy;
    }

    public void setConnectProxy(HttpClientConnection connectProxy) {
        this.connectProxy = connectProxy;
    }

    @Override
    public EsDataSources clone(EsDataSources target) throws Exception {
        target.setHost(this.getHost());
        target.setUser( this.getUser() );
        target.setPassword( this.getPassword() );
        return target;
    }
}
