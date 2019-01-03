package com.quartz.monitor.model.redisModel;


import com.quartz.monitor.model.AbstractMonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:51
 * @Description:
 */
public class RedisMonitorDTO extends AbstractMonitorDTO {
    private RedisInfoDTO connectDTO = new RedisInfoDTO();
    private RedisDataSources dataSources = new RedisDataSources();


    public RedisInfoDTO getConnectDTO() {
        return connectDTO;
    }

    public void setConnectDTO(RedisInfoDTO connectDTO) {
        this.connectDTO = connectDTO;
    }

    public RedisDataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(RedisDataSources dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public String toString() {
        return "RedisMonitorDTO{" +
                "connectDTO=" + connectDTO +
                ", dataSources=" + dataSources +
                '}';
    }
}
