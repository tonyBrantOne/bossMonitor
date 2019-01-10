package com.quartz.monitor.model.redisModel;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */

import com.quartz.monitor.model.DefaultMonitorType;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */
public class RedisMonitorTypeDTO extends DefaultMonitorType {


    public RedisMonitorTypeDTO() {
    }

    public RedisMonitorTypeDTO(String code, String name) {
        super(code, name);
    }

    @Override
    public String toString() {
        return "RedisMonitorTypeDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
