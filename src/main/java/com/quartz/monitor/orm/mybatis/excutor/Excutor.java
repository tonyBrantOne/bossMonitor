package com.quartz.monitor.orm.mybatis.excutor;


import com.quartz.monitor.orm.mybatis.config.MappedStatement;

import java.util.List;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public interface Excutor {

    public <E>List<E> query(MappedStatement ms, Object parameter) throws Exception;
}
