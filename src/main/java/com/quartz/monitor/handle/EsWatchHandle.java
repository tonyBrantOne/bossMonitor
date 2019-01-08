package com.quartz.monitor.handle;
/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */


import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.handle.base.WatchService;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:36
 * @Description:
 */
public interface EsWatchHandle< T extends MonitorDTO> extends WatchService<T> {


}
