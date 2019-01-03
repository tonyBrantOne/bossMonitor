package com.quartz.monitor.model.postgresqlModel;/**
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
public class PostgresqlMonitorTypeDTO extends DefaultMonitorType {


    public PostgresqlMonitorTypeDTO() {
    }

    public PostgresqlMonitorTypeDTO(String code, String name) {
        super(code, name);
    }

    @Override
    public String toString() {
        return "PostgresqlMonitorTypeDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
