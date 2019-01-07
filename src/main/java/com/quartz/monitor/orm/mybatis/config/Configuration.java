package com.quartz.monitor.orm.mybatis.config;

import java.util.HashMap;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class Configuration {

    private Map<String,MappedStatement> mappedStatements = new HashMap<>();

    @Override
    public String toString() {
        return "Configuration{" +
                "mappedStatements=" + mappedStatements +
                '}';
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }
}
