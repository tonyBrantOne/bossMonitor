package com.quartz.monitor.conf;


import com.quartz.monitor.service.WatchParentService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/27
 */
public class ConstantParam {

    public static final LinkedBlockingDeque<WatchParentService> blockingDeque = new LinkedBlockingDeque<>(3);
    public static final ConcurrentHashMap<String,WatchParentService> concurrentHashMap = new ConcurrentHashMap<String, WatchParentService>(3);

    /**
     * 加载配置文件的信息
     */
    public static final Map<String,String> propMap = new LinkedHashMap<>();



    public LinkedBlockingDeque<WatchParentService> getBlockingDeque() {
        return blockingDeque;
    }

}
