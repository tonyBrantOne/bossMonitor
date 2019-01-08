package com.quartz.monitor.model.esModel;/**
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
public class EsDataSources extends DefaultDateSources {

    private String clustername;
    private String type;
    private String index;


    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "EsDataSources{" +
                "clustername='" + clustername + '\'' +
                ", type='" + type + '\'' +
                ", index='" + index + '\'' +
                ", sourceBeanName='" + sourceBeanName + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
