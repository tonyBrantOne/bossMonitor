package com.quartz.monitor.listen;




import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.publisher.AbstractQuartzMonitor;
import com.quartz.monitor.util.DateUtil;
import com.quartz.monitor.util.ReflectUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/12 15:08
 * @Description:
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private static Logger LOG = LogManager.getLogger(ProxyInvocationHandler.class);

    public Object target;

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.info("executed Thread name: "+Thread.currentThread().getName()+", executed Listen name："+target.getClass().getName()+", executed listen beginDate: "+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        LOG.info("executed listen param: " + args[0].toString());
        Object object = ReflectUtil.invoke(target,method.getName(), (MonitorDTO) args[0]);
        LOG.info("executed Thread name: "+Thread.currentThread().getName()+", executed Listen name："+target.getClass().getName()+", executed listen endDate: "+ DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_PATTERN));
        return object;
    }

}
