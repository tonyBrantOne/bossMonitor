package com.quartz.monitor.conf.enums;


/**
 * @Auther: tony_jaa
 * @Date: 2018/7/28 12:39
 * @Description:
 */
public enum StateEnum {

    INFO_STATE( "0","初始化状态"),
    FINSHED_STATE( "1","结束状态"),
    ;

    private String msgCode;
    private String msgName;

    StateEnum(String msgCode, String msgName) {
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
        for (StateEnum msgParentTypeEnum : StateEnum.values()){
            if (msgParentTypeEnum.getMsgCode().equals(msgCode)){
                return msgParentTypeEnum.getMsgName();
            }
        }
        return null;
    }




}
