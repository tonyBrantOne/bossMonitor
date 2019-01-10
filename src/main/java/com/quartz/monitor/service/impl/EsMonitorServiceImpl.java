package com.quartz.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.nosql.es.dao.EsMonitorDao;
import com.quartz.monitor.nosql.es.util.ESHelper;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import com.quartz.monitor.publisher.AbstractQuartzMonitor;
import com.quartz.monitor.service.EsMonitorService;
import com.quartz.monitor.util.DateUtil;
import com.quartz.monitor.util.FastJsonListObjectUtil;
import com.quartz.monitor.util.FileHandleUtil;
import com.quartz.monitor.util.MapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.JsonString;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:53
 * @Description:
 */
@Component
public class EsMonitorServiceImpl implements EsMonitorService {
    private  Logger logger = LogManager.getLogger(EsMonitorServiceImpl.class);
    private  final String FILE = "file/";


    @Autowired
    private EsMonitorDao esMonitorDao;

    @Override
    public MonitorDTO insertMonitorToEs(MonitorDTO monitorDTO) throws Exception {
        AbstractMonitorDTO abstractMonitorDTO = (AbstractMonitorDTO) monitorDTO;
        String fileUrl = FileHandleUtil.getClassPathUrl(FILE  +  abstractMonitorDTO.getServerName().hashCode() + ".txt");
        logger.warn("服务名称：abstractMonitorDTO.getServerName()"+abstractMonitorDTO.getServerName()+ "文件名称"+abstractMonitorDTO.getServerName().hashCode());
        ESHelper esHelper = esMonitorDao.getESHelper();
        String fileJson = getJsonByFile(abstractMonitorDTO);
        List<JSONObject> list = FastJsonListObjectUtil.toListObject(fileJson,JSONObject.class);
        JSONObject jsonObject = JSONObject.parseObject( JSON.toJSONString(abstractMonitorDTO),JSONObject.class);
        list.add(jsonObject);
        if( esHelper == null ){
            logger.info("ES链接断开，保存数据到文本中");
            commitToFile(list,fileUrl);
            logger.info("添加文本成功");
        }else{
            logger.info("ES里未查到数据，进行添加操作");
            commitToEs(list);
            logger.info("添加ES成功");
            FileHandleUtil.deleteFile(fileUrl);
            logger.info("文件删除成功");
        }
        return monitorDTO;
    }

    public void commitToEs( List<JSONObject> list ) throws Exception {
       saveMonitor(list);
    }

    /**
     * 通过序列化保存到本地文件
     * @param list
     * @throws Exception
     */
    public void commitToFile( List<JSONObject> list,String path ) throws Exception {
        String jsonString = JSONArray.toJSONString(list);
        FileHandleUtil.bufferedStrWriter(jsonString,path);
        logger.info("文件保存成功");
    }



    private String getJsonByFile( AbstractMonitorDTO abstractMonitorDTO ) throws Exception {
        String filePath = FileHandleUtil.getClassPathUrl(FILE);
        FileHandleUtil.judgeFoldExist(filePath);
        filePath +=  abstractMonitorDTO.getServerName().hashCode() + ".txt";
        Boolean b = FileHandleUtil.judgeFileisExist(filePath);
        if( !b ) return null;
        String jsonList = FileHandleUtil.bufferedStrRead(filePath);
        return jsonList;
    }

    private void saveMonitor( List<JSONObject> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            JSONObject monitorDTO = list.get(i);
            String s = JSON.toJSONString(monitorDTO);
            Map<String, Object> map = JSONObject.parseObject(s,Map.class);
            String dateStr = DateUtil.dateToStr(new Date(),DateUtil.MILLISECOND_PATTERN);
            map.put("publicTime",dateStr);
            esMonitorDao.insert(map);
        }
    }





}
