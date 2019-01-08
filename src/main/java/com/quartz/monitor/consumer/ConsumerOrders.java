package com.quartz.monitor.consumer;


import com.quartz.monitor.handle.base.WatchService;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/27
 */
public class ConsumerOrders {

    public static void blindListen(WatchService watchService){
   //        ConstantParam.concurrentHashMap.put(watchService.getClass().getSuperclass().getName(),watchService);
    }

    public static void blindListen(String key){
 //          ConstantParam.concurrentHashMap.put(key, (WatchParentService) ConstantParam.applicationContext.get(key));
    }
}
