package com.quartz.monitor.test;

import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSession;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.service.MailMqService;
import com.quartz.monitor.service.impl.MailMqServiceImpl;
import com.quartz.monitor.util.ConfigUtil;
import com.quartz.monitor.util.DateUtil;
import com.quartz.monitor.util.ThreadPoolUtil;
import com.quartz.monitor.util.mail.MailTemplate;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;
import java.util.TimeZone;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/28 19:50
 * @Description:
 */
public class BootstrapMain implements InitializingBean,ApplicationContextAware {
    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(BootstrapMain.class);

    @Autowired
    private ConfigUtil configUtil;
    @Autowired
    private MailMqService sendMailService;
    @Autowired
    private MailTemplate mailTemplate;
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        initProp();//初始化枚举及配置文件的缓存
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-04:00")); // 设置JVM默认时区为西4时区
        mailTemplate.getSessionInstance();//获取邮件session
        startMailMsgConsume();//启动邮件消费服务
        LOG.info("BootstrapMain加载成功=================================当前时间："+ DateUtil.dateToStr( new Date(),DateUtil.DATE_TIME_PATTERN));
    }



    public void initProp() throws Exception {
        configUtil.initPropMap();
    }

    public void initMapper(){
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PostgresqlDao postgresqlDao = sqlSession.getMapper(PostgresqlDao.class);
        QuartzPostgresqlMonitor quartzPostgresqlMonitor = (QuartzPostgresqlMonitor) this.applicationContext.getBean("quartzPostgresqlMonitor");
        quartzPostgresqlMonitor.setPostgresqlDao(postgresqlDao);
    }

    private void startMailMsgConsume(){
        ThreadPoolUtil.executorService.execute(((MailMqServiceImpl)sendMailService).new ConsumeMailRunnable());
    }

}
