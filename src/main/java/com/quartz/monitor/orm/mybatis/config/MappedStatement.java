package com.quartz.monitor.orm.mybatis.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class MappedStatement {
    private String namespace;
    private String methodId;
    private String sourceId;
    private String resultType;
    private String parameterType;
    private String sql;
    private String handelType;//超做类型
    private Map<String,String> paramSortMap = new LinkedHashMap<>();//参数问号顺序

    @Override
    public String toString() {
        return "MappedStatement{" +
                "namespace='" + namespace + '\'' +
                ", methodId='" + methodId + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", resultType='" + resultType + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", sql='" + sql + '\'' +
                ", handelType='" + handelType + '\'' +
                ", paramSortMap=" + paramSortMap +
                '}';
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getHandelType() {
        return handelType;
    }

    public void setHandelType(String handelType) {
        this.handelType = handelType;
    }

    public Map<String, String> getParamSortMap() {
        return paramSortMap;
    }

    public void setParamSortMap(Map<String, String> paramSortMap) {
        this.paramSortMap = paramSortMap;
    }
}
