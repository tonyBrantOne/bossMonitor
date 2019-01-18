package com.quartz.monitor.service.impl;

import com.google.common.collect.Queues;
import com.quartz.monitor.conf.ConstantParam;
import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.service.MailMqService;
import com.quartz.monitor.util.ThreadPoolUtil;
import com.quartz.monitor.util.mail.MailTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/17 20:12
 * @Description:
 */

@Component
public class MailMqServiceImpl implements MailMqService {

    private static Logger logger = LogManager.getLogger( MailMqServiceImpl.class );
    private static CountDownLatch countDownLatchTopicNum;

    @Autowired
    private MailTemplate mailTemplate;


    @Override
    public void produceMessage(MonitorDTO monitorDTO) {
        if( !(monitorDTO instanceof AbstractMonitorDTO) ) throw new RuntimeException("monitorDTO类型必须为AbstractMonitorDTO");
        this.pushMsgToQeque( (AbstractMonitorDTO) monitorDTO );
    }


    /**
     * 将生产的消息推送到对应主题队列中
     * @param abstractMonitorDTO
     */
    private void pushMsgToQeque( AbstractMonitorDTO abstractMonitorDTO ){
        if( null == ConstantParam.mapMainTopicQeque.get(abstractMonitorDTO.getServerName().hashCode()+"") ){
            /**
             * 以服务名作为分段锁
             */
     //       synchronized ( abstractMonitorDTO.getServerName() ){
            synchronized ( this ){
                if( null == ConstantParam.mapMainTopicQeque.get(abstractMonitorDTO.getServerName().hashCode()+"") ){
                    logger.warn("初始化服务名为：" + abstractMonitorDTO.getServerName() + "的邮件主题队列！" );
                    ConstantParam.mapMainTopicQeque.put(abstractMonitorDTO.getServerName().hashCode() + "",new LinkedBlockingDeque<MonitorDTO>());
                }
            }
        }
        logger.warn("往服务名为：" +abstractMonitorDTO.getServerName()+"的阻塞队列压数据");
        ConstantParam.mapMainTopicQeque.get(abstractMonitorDTO.getServerName().hashCode() + "").offer(abstractMonitorDTO);
        logger.warn("服务名为：" +abstractMonitorDTO.getServerName()+"的阻塞队列数据压成功");
    }

    @Override
    public void consumeMessage() throws Exception {
        while ( true ){
            logger.error("消息消费循环开始");
            if( ConstantParam.mapMainTopicQeque.size() == 0 ) {
                logger.warn("消息队列不存在，休眠5s");
                Thread.sleep(5000);
                continue;
            }
            countDownLatchTopicNum = new CountDownLatch(ConstantParam.mapMainTopicQeque.size());
            for( String key : ConstantParam.mapMainTopicQeque.keySet() ){
                logger.warn("邮件发送主题key:"+key);
                FutureTask<? extends MonitorDTO> futureTask = ThreadPoolUtil.execute(this,Thread.currentThread().getStackTrace()[1].getMethodName(),key,AbstractMonitorDTO.class);
            }
            logger.warn("所有主题消息已发送完消息，等待阻塞结束,大约30s时间");
            countDownLatchTopicNum.await();
            logger.error("阻塞结束，休眠30s，进入下次循环中");
            Thread.sleep( 1*30*1000);
            System.out.println("");
        }
    }


    public MonitorDTO consumeMessage(String key ) throws Exception {
        String hashcode = key;
        List<MonitorDTO> list = new ArrayList<>();
        if( ConstantParam.mapMainTopicQeque.get(hashcode).isEmpty() ){
            logger.warn("消息主题"+hashcode+"队列中没有任何消息");
            countDownLatchTopicNum.countDown();
            return new MonitorDTO();
        }
        logger.warn("消息主题"+hashcode+"的队列批量获取阻塞30s");
        Queues.drain(ConstantParam.mapMainTopicQeque.get(hashcode), list, 4, 1 * 30 * 1000, TimeUnit.MILLISECONDS);
        MonitorDTO monitorDTO = list.get( list.size() - 1 );
        if( !(monitorDTO instanceof AbstractMonitorDTO) ) {
            countDownLatchTopicNum.countDown();
            throw new RuntimeException("monitorDTO类型必须为AbstractMonitorDTO");
        }
        AbstractMonitorDTO abstractMonitorDTO = (AbstractMonitorDTO) monitorDTO;
        mailTemplate.sendMailMsg(abstractMonitorDTO);
        countDownLatchTopicNum.countDown();
        /**
         * 开始调用发送邮件的服务
         */
        return abstractMonitorDTO;
    }

    /**
     * 发送邮件异步任务类
     */
    public class ConsumeMailRunnable implements Runnable{

        @Override
        public void run() {
            try {
                MailMqServiceImpl.this.consumeMessage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
