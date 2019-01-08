package com.quartz.monitor.conf.enums;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 13:55
 * @Description:
 */

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 13:55
 * @Description:
 */
public enum WarnTypeEnum {

    CONNECT_EXCESS( "connect_excess","连接超负荷"),

    ;

    private String code;
    private String name;

    WarnTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param code
     * @return
     */
    public static String getNameByCode(String code){
        for (WarnTypeEnum msgParentTypeEnum : WarnTypeEnum.values()){
            if (msgParentTypeEnum.code.equals(code)){
                return msgParentTypeEnum.name;
            }
        }
        return null;
    }

}
