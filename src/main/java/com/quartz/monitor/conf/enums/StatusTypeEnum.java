package com.quartz.monitor.conf.enums;


/**
 * @Auther: tony_jaa
 * @Date: 2018/7/28 12:39
 * @Description:
 */
public enum StatusTypeEnum {

    SUCCESS_TYPE( "success_type","服务正常"),
    WARN_TYPE( "warn_type","警告"),
    ERROR_TYPE("error_type","服务异常"),




    ;

    private String msgCode;
    private String msgName;

    StatusTypeEnum(String msgCode, String msgName) {
        this.msgCode = msgCode;
        this.msgName = msgName;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }



    /**
     *
     * @param msgCode
     * @return
     */
    public static String getNameByCode(String msgCode){
        for (StatusTypeEnum msgParentTypeEnum : StatusTypeEnum.values()){
            if (msgParentTypeEnum.getMsgCode().equals(msgCode)){
                return msgParentTypeEnum.getMsgName();
            }
        }
        return null;
    }




}
