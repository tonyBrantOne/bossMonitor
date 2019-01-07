package com.quartz.monitor.orm.mybatis.sqlSession;

import java.util.List;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public interface SqlSession {

    public <T>T selectOne(String statement, Object parametre) throws Exception;

    public <T>List<T> selectList(String statement, Object parametre) throws Exception;

    public <T>T getMapper(Class<T> type);

}
