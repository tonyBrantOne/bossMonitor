package com.quartz.monitor.model.redisModel;

import com.quartz.monitor.model.DefaultDateSources;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:14
 * @Description:
 */
public class RedisDataSources extends DefaultDateSources {


    @Override
    public String toString() {
        return "RedisDataSources{" +
                "sourceBeanName='" + sourceBeanName + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
