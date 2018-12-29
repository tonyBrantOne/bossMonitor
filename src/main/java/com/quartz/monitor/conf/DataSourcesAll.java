package com.quartz.monitor.conf;

import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.redisModel.RedisDataSources;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 15:48
 * @Description:
 */
public class DataSourcesAll {
    public static final List<RedisDataSources> REDIS_DATA_SOURCES_LIST = new ArrayList<>();
    public static final List<PostgresqlDataSources> POSTGRESQL_DATA_SOURCES_LIST = new ArrayList<>();
    public static final List<EsDataSources> ES_DATA_SOURCES_LIST = new ArrayList<>();

    public static List<RedisDataSources> getRedisDataSourcesList() {
        return REDIS_DATA_SOURCES_LIST;
    }

    public static List<PostgresqlDataSources> getPostgresqlDataSourcesList() {
        return POSTGRESQL_DATA_SOURCES_LIST;
    }

    public static List<EsDataSources> getEsDataSourcesList() {
        return ES_DATA_SOURCES_LIST;
    }


}
