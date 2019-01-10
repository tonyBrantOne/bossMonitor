package com.quartz.monitor.conf.enums;


/**
 * @Auther: tony_jaa
 * @Date: 2018/7/28 12:39
 * @Description:
 */
public enum NoticeMediumEnum {

    MAIL_TYPE( "1","mail_type","邮箱"),




    ;

    private String code;
    private String name;
    private String desc;


    NoticeMediumEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @param msgCode
     * @return
     */
    public static String getNameByCode(String msgCode){
        for (NoticeMediumEnum msgParentTypeEnum : NoticeMediumEnum.values()){
            if (msgParentTypeEnum.getCode().equals(msgCode)){
                return msgParentTypeEnum.getName();
            }
        }
        return null;
    }




}
