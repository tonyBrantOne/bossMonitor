package com.quartz.monitor.model.esModel;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */


import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.redisModel.RedisDataSources;
import com.quartz.monitor.model.redisModel.RedisInfoDTO;
import com.quartz.monitor.model.redisModel.RedisMonitorTypeDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class EsMonitorDTO extends AbstractMonitorDTO {

    private EsInfoDTO info = new EsInfoDTO();
    private EsDataSources dataSources = new EsDataSources();
    private RedisMonitorTypeDTO monitorType = new RedisMonitorTypeDTO();


    @Override
    public String toString() {
        return "EsMonitorDTO{" +
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

    public EsInfoDTO getInfo() {
        return info;
    }

    public void setInfo(EsInfoDTO info) {
        this.info = info;
    }

    public EsDataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(EsDataSources dataSources) {
        this.dataSources = dataSources;
    }

    public RedisMonitorTypeDTO getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(RedisMonitorTypeDTO monitorType) {
        this.monitorType = monitorType;
    }
}
