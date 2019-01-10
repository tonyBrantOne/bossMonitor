package com.quartz.monitor.handle.base;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 11:10
 * @Description:
 */

import com.quartz.monitor.conf.enums.StateEnum;
import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.msgCenterModel.MessageCenter;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 11:10
 * @Description:
 */
public abstract class DefaultDoHandle<T extends AbstractMonitorDTO>{


    protected MessageCenter cloneMonitorToMsg( MessageCenter target, T t ){
        target.setPublicTime(t.getPublicTime());
        target.setReadFlag( Integer.valueOf(StateEnum.INFO_STATE.getMsgCode() ));
        target.setServerName(t.getServerName());
        target.setContent(t.getContent());
        target.setStatus(t.getStatus());
        target.setWarnType(t.getWarnType());
        return this.cloneMonitorToMsg(target,t);
    }

}
