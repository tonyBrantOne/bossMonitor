package com.quartz.monitor.model.postgresqlModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */

import com.quartz.monitor.model.DefaultInfo;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class PostgresqlInfoDTO extends DefaultInfo {

    public PostgresqlInfoDTO() {
    }

    public PostgresqlInfoDTO(Integer currentConnectNum, Integer maxConnectNum) {
        super(currentConnectNum, maxConnectNum);
    }

    @Override
    public String toString() {
        return "PostgresqlInfoDTO{" +
                "currentConnectNum=" + currentConnectNum +
                ", maxConnectNum=" + maxConnectNum +
                '}';
    }
}
