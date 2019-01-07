package com.quartz.monitor.test;

import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSession;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.ConfigUtil;
import com.quartz.monitor.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/28 19:50
 * @Description:
 */
public class BootstrapMain implements InitializingBean,ApplicationContextAware {
    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(BootstrapMain.class);

    @Autowired
    private ConfigUtil configUtil;


    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        initProp();
  //      initMapper();
        Date date = new Date();
        LOG.info("BootstrapMain加载成功=================================当前时间："+ DateUtil.dateToStr(date,DateUtil.DATE_TIME_PATTERN));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }

    public void initProp(){
        configUtil.getRedisSourceByConf();
        configUtil.initPropMap();
    }

    public void initMapper(){
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PostgresqlDao postgresqlDao = sqlSession.getMapper(PostgresqlDao.class);
        QuartzPostgresqlMonitor quartzPostgresqlMonitor = (QuartzPostgresqlMonitor) this.applicationContext.getBean("quartzPostgresqlMonitor");
        quartzPostgresqlMonitor.setPostgresqlDao(postgresqlDao);
    }

}
