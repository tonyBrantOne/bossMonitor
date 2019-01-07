package com.quartz.monitor.orm.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public interface UserDao {

    Map<String,Object> selectByPrimaryKey(Map map);

    List<Map<String,Object>> selectList(Map map);
}
