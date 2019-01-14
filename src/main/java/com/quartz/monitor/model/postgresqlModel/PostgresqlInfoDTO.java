package com.quartz.monitor.model.postgresqlModel;

import com.quartz.monitor.model.DefaultInfo;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class PostgresqlInfoDTO extends DefaultInfo {

    private String chargePercent;//负荷提醒

    public String getChargePercent() {
        return chargePercent;
    }

    public void setChargePercent(String chargePercent) {
        this.chargePercent = chargePercent;
    }

    public PostgresqlInfoDTO() {
    }

    public PostgresqlInfoDTO(Integer currentConnectNum, Integer maxConnectNum) {
        super(currentConnectNum, maxConnectNum);
    }

    @Override
    public String toString() {
        return "PostgresqlInfoDTO{" +
                "chargePercent='" + chargePercent + '\'' +
                ", currentConnectNum=" + currentConnectNum +
                ", maxConnectNum=" + maxConnectNum +
                '}';
    }
}
