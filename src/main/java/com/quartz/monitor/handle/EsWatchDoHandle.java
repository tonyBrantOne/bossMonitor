package com.quartz.monitor.handle;


import com.quartz.monitor.model.esModel.EsMonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public class EsWatchDoHandle implements EsWatchHandle<EsMonitorDTO> {

    @Override
    public void connectExcessWarnning(EsMonitorDTO esMonitorDTO) {
        System.out.println("EsRedis连接数超时");
    }

    @Override
    public void connectReject(EsMonitorDTO esMonitorDTO) {
        System.out.println("Es连接数超时");
    }
}
