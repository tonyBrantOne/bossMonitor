package com.quartz.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.nosql.es.dao.EsMonitorDao;
import com.quartz.monitor.service.EsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.JsonString;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:53
 * @Description:
 */
@Component
public class EsMonitorServiceImpl implements EsMonitorService {

    @Autowired
    private EsMonitorDao esMonitorDao;

    @Override
    public MonitorDTO insertMonitorToEs(MonitorDTO monitorDTO) throws Exception {
        String s = JSON.toJSONString(monitorDTO);
        Map<String, Object> map = JSONObject.parseObject(s,Map.class);
        esMonitorDao.insert(map);
        return monitorDTO;
    }



}
