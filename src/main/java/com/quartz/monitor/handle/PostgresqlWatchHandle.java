package com.quartz.monitor.handle;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.service.WatchService;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public interface PostgresqlWatchHandle < T extends MonitorDTO> extends WatchService<T> {


}
