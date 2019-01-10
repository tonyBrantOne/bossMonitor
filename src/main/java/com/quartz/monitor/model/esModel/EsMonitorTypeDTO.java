package com.quartz.monitor.model.esModel;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */

import com.quartz.monitor.model.DefaultMonitorType;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 10:40
 * @Description:
 */
public class EsMonitorTypeDTO extends DefaultMonitorType {


    public EsMonitorTypeDTO() {
    }

    public EsMonitorTypeDTO(String code, String name) {
        super(code, name);
    }

    @Override
    public String toString() {
        return "EsMonitorTypeDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
