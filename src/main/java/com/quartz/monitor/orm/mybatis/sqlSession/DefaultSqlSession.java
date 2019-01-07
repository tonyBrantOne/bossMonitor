package com.quartz.monitor.orm.mybatis.sqlSession;


import com.quartz.monitor.orm.mybatis.config.Configuration;
import com.quartz.monitor.orm.mybatis.config.MappedStatement;
import com.quartz.monitor.orm.mybatis.excutor.DefaultExcutor;
import com.quartz.monitor.orm.mybatis.excutor.Excutor;
import com.quartz.monitor.orm.mybatis.util.ProxyMapperUtil;
import com.quartz.monitor.util.ProxyUtil;

import java.util.List;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Excutor excutor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.excutor = new DefaultExcutor();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement, Object parametre) throws Exception{
        List<Object> list = this.selectList(statement,parametre);
        if( null == list || 0 == list.size() ) {
            return null;
        }
        if( list.size() > 1 ) {
            throw new RuntimeException("");
        }
        return (T) list.get(0);
    }

    @Override
    public <T> List<T> selectList(String statement, Object parametre) throws Exception{
        MappedStatement ms =  configuration.getMappedStatements().get(statement);
        return excutor.query(ms,parametre);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return ProxyMapperUtil.getInstance(type,this);
    }
}
