package com.quartz.monitor.model.redisModel;


import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlInfoDTO;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorTypeDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:51
 * @Description:
 */
public class RedisMonitorDTO extends AbstractMonitorDTO {
    private RedisInfoDTO info = new RedisInfoDTO();
    private RedisDataSources dataSources = new RedisDataSources();
    private RedisMonitorTypeDTO monitorType = new RedisMonitorTypeDTO();

    @Override
    public String toString() {
        return "RedisMonitorDTO{" +
                "info=" + info +
                ", dataSources=" + dataSources +
                ", monitorType=" + monitorType +
                ", publicTime=" + publicTime +
                ", status='" + status + '\'' +
                ", warnType='" + warnType + '\'' +
                ", serverName='" + serverName + '\'' +
                ", content='" + content + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }

    public RedisInfoDTO getInfo() {
        return info;
    }

    public void setInfo(RedisInfoDTO info) {
        this.info = info;
    }

    public RedisDataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(RedisDataSources dataSources) {
        this.dataSources = dataSources;
    }

    public RedisMonitorTypeDTO getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(RedisMonitorTypeDTO monitorType) {
        this.monitorType = monitorType;
    }
}
