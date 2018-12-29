package com.quartz.monitor.util;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */
public class ThreadPoolUtil {
    public static ExecutorService executorService = new ThreadPoolExecutor(10, 20, 1000,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));
}
