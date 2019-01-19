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
public class ConfigUtil {

    private final String PATH = "config/test";



    /**
     * 初始化配置文件
     * @throws Exception
     */
    public void initPropMap() throws Exception {
        initCacheMap();//解析classpath下的property文件到缓存中
        getRedisSourceByConf();//初始化数据源对象
        /**
         * 初始化枚举对象
         */
        initMsgParentTypeMap();
        initMsgChildrenTypeMap();
    }

    /**
     * 缓存对象初始化
     */
    private void initCacheMap() throws Exception {
        PropertyUtil.readClassPathPropFileToMap(PATH,ConstantParam.CACHE_PROP);
    }

    private void getRedisSourceByConf() throws Exception {
        Map<String,DefaultDateSources> dataSourceHashMap = new HashMap<>();
        Properties properties = ConstantParam.CACHE_PROP.get("db-conf");
        Iterator<Map.Entry<Object, Object>> entryIterator = properties.entrySet().iterator();
        while ( entryIterator.hasNext() ){
            Map.Entry<Object, Object> entry = entryIterator.next();
            String k = (String) entry.getKey();//db.person.host
            String[] arr = k.split("\\.");
            String key = arr[arr.length - 1];//host
            String preKey = k.replaceAll(key,"");//db.person.
            String value = (String) entry.getValue();//192.168.0.12:5432/boss?currentSchema=manager
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


    private void initMsgParentTypeMap(){
        for (MsgParentTypeEnum msgParentTypeEnum : MsgParentTypeEnum.values()){
            ConstantParam.msgParentTypeMap.put(msgParentTypeEnum.getMsgCode(),msgParentTypeEnum.getMsgName());
        }
    }

    private void initMsgChildrenTypeMap(){
        for (MsgChildrenTypeEnum msgChildrenTypeEnum : MsgChildrenTypeEnum.values()){
            Map<String,String> parentMap =  ConstantParam.msgChildrenTypeMap.get(msgChildrenTypeEnum.getParentCode());
            if( null == parentMap ) ConstantParam.msgChildrenTypeMap.put(msgChildrenTypeEnum.getParentCode(),new HashMap<String, String>());
            ConstantParam.msgChildrenTypeMap.get(msgChildrenTypeEnum.getParentCode()).put(msgChildrenTypeEnum.getMsgCode(),msgChildrenTypeEnum.getMsgName());
        }
    }


    private void initPropList(List list, DefaultDateSources dateSources, String key, String value) throws Exception {
        ReflectUtil.invoke(dateSources,"set"+StringUtil.firstPhareToBig(key),value);
        if( !list.contains(dateSources) ) list.add(dateSources);
    }




}
