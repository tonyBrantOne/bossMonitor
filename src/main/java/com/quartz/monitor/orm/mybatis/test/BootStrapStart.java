package com.quartz.monitor.orm.mybatis.test;


import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.orm.mybatis.dao.UserDao;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSession;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class BootStrapStart {

    public static void main(String[] args) throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PostgresqlDao postgresqlDao = sqlSession.getMapper(PostgresqlDao.class);
        Map<String,Object> map = new HashMap<>();
        map.put("id","1");
        map.put("name","tony");
        List<Map<String, Object>> resultMap = postgresqlDao.selectCurrentConnections(new PostgresqlMonitorDTO());
    //    Map<String, Object> resultMap = userDao.selectByPrimaryKey(map);
        System.out.println("返回值"+resultMap);
    }
}
