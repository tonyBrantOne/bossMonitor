package com.quartz.monitor.listen;


import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.service.WatchService;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:34
 * @Description:
 */
public abstract class AbstactWatchListen< T extends MonitorDTO> implements WatchService<T> {

}
