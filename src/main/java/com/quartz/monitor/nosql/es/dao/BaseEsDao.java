package com.quartz.monitor.nosql.es.dao;

import com.quartz.monitor.nosql.es.util.ESHelper;

import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/9 12:33
 * @Description:
 */
public interface BaseEsDao {

    /**
     * 插入es中
     * @param map
     * @return
     * @throws Exception
     */
    int insert(Map map) throws Exception;

    /**
     * 修改es中
     * @param map
     * @return
     * @throws Exception
     */
    int update(Map map) throws Exception;

    /**
     * 修改es中
     * @param list
     * @return
     * @throws Exception
     */
    int updateBatch(List<Map<String,Object>> list) throws Exception;

    /**
     * 从ES中查询
     * @param map
     * @return
     */
    List<Map<String,Object>> selectList(Map map);


    /**
     * 从ES中查询满足条件的条数
     * @param map
     * @return
     */
    Long selectCountNum(Map map);

    ESHelper getESHelper();
}
