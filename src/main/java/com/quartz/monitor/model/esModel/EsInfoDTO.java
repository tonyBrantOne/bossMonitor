package com.quartz.monitor.model.esModel;/**
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
public class EsInfoDTO extends DefaultInfo {

    public EsInfoDTO() {
    }

    public EsInfoDTO(Integer currentConnectNum, Integer maxConnectNum) {
        super(currentConnectNum, maxConnectNum);
    }


    @Override
    public String toString() {
        return "EsInfoDTO{" +
                "currentConnectNum=" + currentConnectNum +
                ", maxConnectNum=" + maxConnectNum +
                '}';
    }
}
