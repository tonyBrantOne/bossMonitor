package com.quartz.monitor.publisher;

import com.quartz.monitor.test.BootstrapMain;
import com.quartz.monitor.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/28 11:24
 * @Description:
 */
public abstract class AbstractQuartzMonitor implements Runnable {
    private static Logger LOG = LogManager.getLogger(AbstractQuartzMonitor.class);

    @Override
    public void run() {
        LOG.info("---------------------------------------");
        LOG.info("---------------------------------------");
        LOG.info("---------------------------------------");
        LOG.info("当前线程："+Thread.currentThread().getName()+", 当前作业任务名称："+this.getClass().getName()+", 当前任务启动时间："+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        this.checkHeartJump();
        LOG.info("当前线程："+Thread.currentThread().getName()+", 当前作业任务名称："+this.getClass().getName()+", 当前任务结束时间："+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        LOG.info("=============================================================================");
        LOG.info("=============================================================================");
        LOG.info("=============================================================================");
    }

    /**
     * 检查服务器心跳
     */
    protected abstract void checkHeartJump();



}
