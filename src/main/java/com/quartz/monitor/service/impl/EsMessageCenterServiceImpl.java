package com.quartz.monitor.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;
import com.quartz.monitor.nosql.es.dao.EsMessageCenterDao;
import com.quartz.monitor.nosql.es.dao.EsMonitorDao;
import com.quartz.monitor.service.EsMessageCenterService;
import com.quartz.monitor.util.DateUtil;
import com.quartz.monitor.util.MapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:54
 * @Description:
 */
@Component
public class EsMessageCenterServiceImpl implements EsMessageCenterService {

    private Logger logger = LogManager.getLogger(EsMessageCenterServiceImpl.class);


    @Autowired
    private EsMessageCenterDao esMessageCenterDao;

    @Override
    public MessageCenter insertMsgToEs(MessageCenter messageCenter) throws Exception {

        String dateStr = DateUtil.dateToStr(new Date(),DateUtil.MILLISECOND_PATTERN);
        Map<String, Object> queryMap = MapUtil.getMapByArr(new String[]{"serverName"},new Object[]{messageCenter.getServerName()});
        Long num = esMessageCenterDao.selectCountNum(queryMap);
        if( Long.valueOf(0).equals(num) ){
            logger.info("es里未查到消息中心数据，进行添加操作");
            String s = JSON.toJSONString(messageCenter);
            Map<String, Object> map = JSONObject.parseObject(s,Map.class);
            map.put("publicTime",dateStr);
            esMessageCenterDao.insert(map);
            logger.info("添加消息成功");
            return messageCenter;
        }
        logger.info("es里有查到消息中心数据，进行修改操作");
//        Map<String, Object> queryUpdateMap = MapUtil.getMapByArr(new String[]{"serverName","status"},new Object[]{messageCenter.getServerName(),messageCenter.getStatus()});
//        queryUpdateMap.put("warnType",messageCenter.getWarnType());
//        queryUpdateMap.put("content",messageCenter.getContent());
        Map<String, Object> queryUpdateMap = JSONObject.parseObject(JSON.toJSONString(messageCenter),Map.class);
        queryUpdateMap.put("publicTime",dateStr);
        esMessageCenterDao.update(queryUpdateMap);
        logger.warn("修改消息成功");
        return messageCenter;
    }


}
