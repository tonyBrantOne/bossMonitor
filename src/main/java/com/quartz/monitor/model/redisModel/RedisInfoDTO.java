package com.quartz.monitor.model.redisModel;

import com.quartz.monitor.model.DefaultInfo;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class RedisInfoDTO extends DefaultInfo {


    public RedisInfoDTO() {
    }

    public RedisInfoDTO(Integer currentConnectNum, Integer maxConnectNum) {
        super(currentConnectNum, maxConnectNum);
    }

    @Override
    public String toString() {
        return "RedisInfoDTO{" +
                "currentConnectNum=" + currentConnectNum +
                ", maxConnectNum=" + maxConnectNum +
                '}';
    }
}
