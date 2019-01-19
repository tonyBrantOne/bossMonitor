package com.quartz.monitor.conf;


import com.quartz.monitor.handle.base.WatchParentService;
import com.quartz.monitor.model.MonitorDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
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
     * 邮件主题队列Map,以服务名称为key,队列为value
     */
    public static final Map<String,LinkedBlockingDeque<MonitorDTO>> mapMainTopicQeque = new HashMap<>();

    /**
     * 异常父类型
     */
    public static final Map<String,String> msgParentTypeMap = new HashMap<>();

    /**
     * 异常类型
     */
    public static final Map<String, Map<String,String>> msgChildrenTypeMap = new HashMap<>();


    /**
     * properties的文件缓存
     */
    public static final Map< String,Properties> CACHE_PROP = new HashMap<>();



}
