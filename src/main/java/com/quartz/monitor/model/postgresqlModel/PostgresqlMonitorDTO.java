package com.quartz.monitor.model.postgresqlModel;


import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.DefaultMonitorType;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class PostgresqlMonitorDTO extends AbstractMonitorDTO {
    private PostgresqlDataSources dataSources = new PostgresqlDataSources();
    private PostgresqlInfoDTO info = new PostgresqlInfoDTO();
    private PostgresqlMonitorTypeDTO monitorType = new PostgresqlMonitorTypeDTO();

    public PostgresqlMonitorTypeDTO getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(PostgresqlMonitorTypeDTO monitorType) {
        this.monitorType = monitorType;
    }

    public PostgresqlDataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(PostgresqlDataSources dataSources) {
        this.dataSources = dataSources;
    }

    public PostgresqlInfoDTO getInfo() {
        return info;
    }

    public void setInfo(PostgresqlInfoDTO info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PostgresqlMonitorDTO{" +
                "dataSources=" + dataSources +
                ", info=" + info +
                ", monitorType=" + monitorType +
                ", publicTime=" + publicTime +
                ", status='" + status + '\'' +
                ", serverName='" + serverName + '\'' +
                ", content='" + content + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
