package com.quartz.monitor.orm.mybatis.spring;
import com.quartz.monitor.conf.FileUrlKey;
import com.quartz.monitor.conf.anno.Manualwired;
import com.quartz.monitor.dao.BaseDao;
import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSession;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import com.quartz.monitor.orm.mybatis.util.ReflectMapperUtil;
import com.quartz.monitor.publisher.AbstractQuartzMonitor;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/7 19:24
 * @Description:
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>,InitializingBean,ApplicationContextAware {
    private SqlSessionFactory sqlSessionFactory;
    private ApplicationContext applicationContext;

    @Override
    public SqlSessionFactory getObject() throws Exception {
        if( null == sqlSessionFactory ) sqlSessionFactory = new SqlSessionFactory();
        return sqlSessionFactory;
    }



    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if( null == sqlSessionFactory ) sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        initMapperProxy(sqlSession);
    }

    private void initMapperProxy(SqlSession sqlSession) throws Exception {
        List<Class<?>> listDao =  doScanPackage(FileUrlKey.DAO_PATH);
        List<Class<?>> listMonitor =  doScanPackage(FileUrlKey.MONITOR_PATH);

        for( Class<?> clazzMonitor : listMonitor ){
            if( clazzMonitor.equals(AbstractQuartzMonitor.class) ) continue;
            Object object = this.applicationContext.getBean(clazzMonitor);
            for( Class<?> clazzDao : listDao ){
                if( clazzDao.equals(BaseDao.class) ) continue;
                String daoName =  clazzDao.getSimpleName();
                daoName = daoName.substring(0,1).toLowerCase() + daoName.substring(1,daoName.length());
                Field field = null;
                        try{
                            field = object.getClass().getDeclaredField(daoName);
                        }catch ( Exception e ){
                            continue;
                        }
                if( null == field )continue;
                boolean f = field.isAnnotationPresent(Manualwired.class);
                if( !f ) continue;
                Object dao = sqlSession.getMapper(clazzDao);
                ReflectMapperUtil.invokeSetField(object,daoName,dao);
            }
            System.out.println(object);
        }

//        PostgresqlDao postgresqlDao = sqlSession.getMapper(PostgresqlDao.class);
//        QuartzPostgresqlMonitor quartzPostgresqlMonitor = (QuartzPostgresqlMonitor) this.applicationContext.getBean("quartzPostgresqlMonitor");
//        quartzPostgresqlMonitor.setPostgresqlDao(postgresqlDao);
    }


    private List<Class<?>> doScanPackage( String path){
            List<Class<?>> list = new ArrayList<>();
            URL url = SqlSessionFactory.class.getClassLoader().getResource(path.replaceAll("\\.","/"));
            String packagesUrl = url.getFile();
            File scanFile = new File(packagesUrl);
            if( scanFile.isDirectory() ){
                String[] fileStr =scanFile.list();
                for( String fileName : fileStr ){
                    String classPath = path + "." +fileName.replaceAll(".class","");
                    System.out.println(path + "." +fileName.replaceAll(".class",""));
                    try {
                       Class<?> aClass = Class.forName(classPath);
                       list.add(aClass);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("classLoad解析异常");
                    }
                }
            }
            return list;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
