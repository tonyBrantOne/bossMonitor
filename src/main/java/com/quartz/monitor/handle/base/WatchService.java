package com.quartz.monitor.handle.base;


import com.quartz.monitor.model.MonitorDTO;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 19:29
 * @Description:
 */
public interface WatchService< T extends MonitorDTO> extends WatchParentService{

    /**
     * 连接过量警告
     */
    void connectExcessWarnning(T t) throws Exception;

    /**
     * 连接拒绝
     */
    void connectReject(T t) throws Exception;

    /**
     * 连接成功
     */
    void connectSuccess(T t) throws Exception;

}
