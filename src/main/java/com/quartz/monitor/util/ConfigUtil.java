package com.quartz.monitor.util;

import com.quartz.monitor.conf.ConstantParam;
import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.conf.enums.MsgChildrenTypeEnum;
import com.quartz.monitor.conf.enums.MsgParentTypeEnum;
import com.quartz.monitor.model.DefaultDateSources;
import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.redisModel.RedisDataSources;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import com.quartz.monitor.orm.mybatis.util.ParasXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 15:32
 * @Description:
 */
@Component
@PropertySource(value = "classpath:config/test/db-conf.properties")
//@PropertySource(value = "classpath:config/product/db-conf.properties")
public class ConfigUtil {

    private final String PATH = "config/test";
    @Autowired
    private Environment environment;

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
    public static final Map< String,Properties > CACHE_PROP = new HashMap<>();


    public void getRedisSourceByConf() {
        Map<String,DefaultDateSources> dataSourceHashMap = new HashMap<>();
        StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) environment;
        ResourcePropertySource resourcePropertySource =(ResourcePropertySource)  standardServletEnvironment.getPropertySources().get( "class path resource [config/test/db-conf.properties]" );

        Map<String, Object> map = resourcePropertySource.getSource();
        for( String k : map.keySet() ){
            String[] arr = k.split("\\.");
            String key = arr[arr.length - 1];
            Object v = map.get(k);
            String value = (String) v;
            ConstantParam.propMap.put(k,value);
            String preKey = k.replaceAll(key,"");
            System.out.println("preKey:"+preKey);
            if ( k.contains("db.")) {
                DefaultDateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new PostgresqlDataSources());
                initPropList(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else if( k.contains("redis.") ){
                DefaultDateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new RedisDataSources());
                initPropList(DataSourcesAll.REDIS_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else if( k.contains("es.") ){
                DefaultDateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new EsDataSources());
                initPropList(DataSourcesAll.ES_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else{
                System.out.println("无参数");
            }
        };
    }

    private void initPropList(List list, DefaultDateSources dateSources, String key, String value){
        try {
            ReflectUtil.invoke(dateSources,"set"+firstPhareToBig(key),value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if( !list.contains(dateSources) ) list.add(dateSources);
    }

    public void initPropMap() throws Exception {
        initMsgParentTypeMap();
        initMsgChildrenTypeMap();
        initCacheMap();
    }

    public String firstPhareToBig( String name ){
       return name.substring(0,1).toUpperCase() + name.substring(1,name.length());
    }

    private void initMsgParentTypeMap(){
        for (MsgParentTypeEnum msgParentTypeEnum : MsgParentTypeEnum.values()){
            msgParentTypeMap.put(msgParentTypeEnum.getMsgCode(),msgParentTypeEnum.getMsgName());
        }
    }

    private void initMsgChildrenTypeMap(){
        for (MsgChildrenTypeEnum msgChildrenTypeEnum : MsgChildrenTypeEnum.values()){
            Map<String,String> parentMap =  msgChildrenTypeMap.get(msgChildrenTypeEnum.getParentCode());
            if( null == parentMap ) msgChildrenTypeMap.put(msgChildrenTypeEnum.getParentCode(),new HashMap<String, String>());
            msgChildrenTypeMap.get(msgChildrenTypeEnum.getParentCode()).put(msgChildrenTypeEnum.getMsgCode(),msgChildrenTypeEnum.getMsgName());
        }
    }

    /**
     * 缓存对象初始化
     */
    private void initCacheMap() throws Exception {
        this.readClassPathPropFile();
    }

    /**
     * 读取classPath路径下的配置文件
     */
    public void readClassPathPropFile() throws Exception {
        URL url = ConfigUtil.class.getClassLoader().getResource(PATH);
        String packagesUrl = url.getFile();
        File scanFile = new File(packagesUrl);
        if( scanFile.isDirectory() ){
            File[] files = scanFile.listFiles();
            for( File file : files ){
                String key = file.getName().replaceAll(".properties","");
                Properties properties = prasePropByFile(file);
                CACHE_PROP.put(key,properties);
            }
        }
    }

    /**
     *
     * @param file 把文件转化为property对象
     * @return
     * @throws Exception
     */
    private Properties prasePropByFile( File file ) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        return props;
    }
}
