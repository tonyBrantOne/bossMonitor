package com.quartz.monitor.model.esModel;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */


import com.quartz.monitor.model.AbstractMonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:13
 * @Description:
 */
public class EsMonitorDTO extends AbstractMonitorDTO {
    private EsConnectDTO connectDTO = new EsConnectDTO();
    private EsDataSources dataSources = new EsDataSources();
}
