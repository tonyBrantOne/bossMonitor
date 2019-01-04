package com.quartz.monitor.publisher;

import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.util.DateUtil;
import com.quartz.monitor.util.ThreadPoolUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/28 11:24
 * @Description:
 */
public abstract class AbstractQuartzMonitor<T> implements Runnable {
    private static Logger LOG = LogManager.getLogger(AbstractQuartzMonitor.class);

    @Override
    public void run() {
        LOG.info("---------------------------------------");
        LOG.info("---------------------------------------");
        LOG.info("当前线程："+Thread.currentThread().getName()+", 当前作业任务名称："+this.getClass().getName()+", 当前任务启动时间："+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        try {
            this.checkHeartJump();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.info("当前线程："+Thread.currentThread().getName()+", 当前作业任务名称："+this.getClass().getName()+", 当前任务结束时间："+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        LOG.info("=============================================================================");
        LOG.info("=============================================================================");
    }

    /**
     * 检查服务器心跳
     */
    protected abstract void checkHeartJump() throws Exception;

    public void judgeExcepType(  List<T> list ) throws Exception {
        List<FutureTask<? extends MonitorDTO>> linkedList = new ArrayList<>();
        for( T currentMonitor : list ){
            MonitorDTO monitorDTO = (MonitorDTO) currentMonitor;
            FutureTask<? extends MonitorDTO> futureTask = ThreadPoolUtil.execute(this,Thread.currentThread().getStackTrace()[1].getMethodName(),monitorDTO,monitorDTO.getClass());
            linkedList.add(futureTask);
        }
        LOG.info("检测之后打印所有的MonitorDTO");
        for( FutureTask<? extends MonitorDTO> futureTask : linkedList ){
            MonitorDTO dto = futureTask.get();
            LOG.info("当前DTO的: "+dto);
        }
        LOG.info("监控数据检查完毕");
    };

    public abstract T judgeExcepType( T t ) throws Exception;

//    public MonitorDTO sendExepToEs( MonitorDTO monitorDTO ){
//        LOG.info("进入发送监控数据的方法中"+monitorDTO);
//        return monitorDTO;
//    };



}
