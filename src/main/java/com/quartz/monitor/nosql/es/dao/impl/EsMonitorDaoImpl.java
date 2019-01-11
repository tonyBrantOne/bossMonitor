package com.quartz.monitor.nosql.es.dao.impl;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:41
 * @Description:
 */

import com.quartz.monitor.nosql.es.dao.EsMonitorDao;
import com.quartz.monitor.nosql.es.util.ESHelper;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
    private Logger logger = LogManager.getLogger( EsMonitorDaoImpl.class );
    private final String ES_TABLE_NAME_PREFIX = "servermonitor_test";

    @Autowired
    private ESHelper esHelper;

    @Override
    public int insert(Map map) throws Exception {
        String esTableName = DateUtil.getEsTableNameByDate( "servermonitor",new Date(), new Date());
   //     String esTableName = ES_TABLE_NAME_PREFIX;
        logger.info("进入es的insert方法里");
        logger.info( "esTableName===============================" + esTableName  );
        int result = esHelper.putDoc(esTableName, "doc", map );
        return result;
    }

    @Override
    public int update(Map map) throws Exception {
        logger.warn("进入es的update方法里");
        return 0;
    }

    @Override
    public int updateBatch(List<Map<String, Object>> list) throws Exception {
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectList(Map map) {
        return null;
    }

    @Override
    public Long selectCountNum(Map map) {
        logger.info("进入es的selectCountNum方法里");
        String esTableName = ES_TABLE_NAME_PREFIX;
        //    logger.info( "esTableName===============================" + esTableName  );

        if( !esHelper.indexExist(esTableName) ) return 0L;
        //    logger.info( "esTableName===============================" + esTableName + "==表存在" );

        BoolQueryBuilder queryBuilder = getBoolQueryBuilderByMap(map);

        SearchRequestBuilder searchRequestBuilder  = esHelper.getClient().prepareSearch(esTableName).setTypes("doc");
        searchRequestBuilder.setQuery(queryBuilder);//设置查询条件
        searchRequestBuilder.setSize(0);

        /**
         * 远程调用es
         */
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Long num = response.getHits().totalHits;
        return num;
    }

    @Override
    public ESHelper getESHelper() {
        return esHelper;
    }


    private BoolQueryBuilder getBoolQueryBuilderByMap( Map<String,Object> map ){
        logger.warn("ES的查询参数map:"+map);
        String serverName = map.get("serverName") == null? null : (String) map.get("serverName");
        Date createTimeBegin = map.get("createTimeBegin") == null? null : (Date) map.get("createTimeBegin");
        Date createTimeEnd = map.get("createTimeEnd") == null? null : (Date) map.get("createTimeEnd");


        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if( StringUtils.isNotBlank(serverName) ){
            QueryBuilder query = QueryBuilders.termQuery("serverName.keyword",serverName);
            queryBuilder.filter().add(query);
        }

        if( createTimeBegin != null || null !=  createTimeEnd ) {
            QueryBuilder queryCreateTm = QueryBuilders.rangeQuery("publicTime").gte(DateUtil.dateToStr(createTimeBegin,DateUtil.MILLISECOND_PATTERN)).lte(DateUtil.dateToStr(createTimeEnd,DateUtil.MILLISECOND_PATTERN));
            queryBuilder.filter().add(queryCreateTm);
        }

        return queryBuilder;
    }

}
