package com.quartz.monitor.nosql.es.dao.impl;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:41
 * @Description:
 */

import com.quartz.monitor.nosql.es.dao.EsMonitorDao;
import com.quartz.monitor.nosql.es.util.ESHelper;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:41
 * @Description:
 */
@Component
public class EsMonitorDaoImpl implements EsMonitorDao {
    private Logger logger = LogManager.getLogger( QuartzPostgresqlMonitor.class );

    @Autowired
    private ESHelper esHelper;

    @Override
    public int insert(Map map) throws Exception {
        String esTableName = DateUtil.getEsTableNameByDate( "servermonitor",new Date(), new Date());
        logger.info( "esTableName===============================" + esTableName  );
        int result = esHelper.putDoc(esTableName, "doc", map );
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectList(Map map) {
        return null;
    }


}
