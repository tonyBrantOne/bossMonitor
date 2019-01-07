package com.quartz.monitor.orm.mybatis.sqlSession;


import com.quartz.monitor.orm.mybatis.config.Configuration;
import com.quartz.monitor.orm.mybatis.config.MappedStatement;
import com.quartz.monitor.orm.mybatis.util.ParasXmlUtil;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class SqlSessionFactory {
    private static Logger LOG = LogManager.getLogger( SqlSessionFactory.class );
    private static final String MAPPER_CONFIG = "mappers";
    private Configuration configuration = new Configuration();

    public SqlSessionFactory() {
        readXmlFile();
    }

    public void readXmlFile(){
        URL url = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG);
        String packagesUrl = url.getFile();
        File scanFile = new File(packagesUrl);
        if( scanFile.isDirectory() ){
            //    String[] fileStr =scanFile.list();
            File[] files = scanFile.listFiles();
            for( File file : files ){
                List<Map<String, String>> list = this.praseXml(file);
                this.loadMapperInfo(list);
            }
        }
    }

    private List<Map<String,String>> praseXml(File file ) {
        try {
            List<Map<String, String>> list = ParasXmlUtil.loadXml(file);
            return list;
        } catch (Exception e) {
            LOG.error("xml文件解析出错");
            throw new RuntimeException(e);
        }
    }

    private void loadMapperInfo( List<Map<String, String>> list ){
        Map<String, MappedStatement> mappedStatements = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setHandelType(map.get("handelType"));

            mappedStatement.setNamespace("com.quartz.monitor.dao.PostgresqlDao");
            mappedStatement.setMethodId(map.get("id"));
            mappedStatement.setSourceId(mappedStatement.getNamespace()+"." + mappedStatement.getMethodId());
            mappedStatement.setResultType(map.get("resultType"));
            mappedStatement.setParameterType(map.get("parameterType"));

            String sql = map.get("sql");
            mappedStatement.setSql(sql);//sql需要对#{id}参数改为?。
            mappedStatement.setParamSortMap( getParamSort(sql));//

            mappedStatements.put(mappedStatement.getSourceId(),mappedStatement);
            configuration.setMappedStatements(mappedStatements);
        }
    }


    private Map<String,String>  getParamSort( String sql ){
        Map<String,String> map = new LinkedHashMap<>();
//        map.put("1","name");
//        map.put("2","id");
        return map;
    }

    public SqlSession openSession(){
        return new DefaultSqlSession(configuration);
    }
}
