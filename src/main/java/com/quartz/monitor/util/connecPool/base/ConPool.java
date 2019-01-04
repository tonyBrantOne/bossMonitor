package com.quartz.monitor.util.connecPool.base;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:36
 * @Description:
 */

import java.util.List;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:36
 * @Description:
 */
public abstract class ConPool<T,C> {

    protected static Integer initialSize = 1;

    protected abstract void addDataSources(T t);

    public abstract T getConnection() throws Exception;

    public abstract void destoryCon(T t) throws Exception;


}
