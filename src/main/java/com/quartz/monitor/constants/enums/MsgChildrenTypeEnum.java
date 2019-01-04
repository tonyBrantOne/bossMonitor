package com.quartz.monitor.constants.enums;


/**
 * @Auther: tony_jaa
 * @Date: 2018/7/28 12:39
 * @Description:
 */
public enum MsgChildrenTypeEnum {

    MAINTENICE_TYPE( "mainteniceCode","维护设置", MsgParentTypeEnum.SYSTEM_TYPE.getMsgCode() ),

    GAMELOGS_TYPE( "gameLogsCode","玩家异常", MsgParentTypeEnum.MONITOR_TYPE.getMsgCode() ),

    SERVERERR_TYPE( "serverCode","服务器", MsgParentTypeEnum.MONITOR_TYPE.getMsgCode() ),


    DBERR_TYPE( "dbCode","数据库", MsgParentTypeEnum.MONITOR_TYPE.getMsgCode() ),


    ESERR_TYPE( "esCode","es", MsgParentTypeEnum.MONITOR_TYPE.getMsgCode() ),

    REDISERR_TYPE( "redisCode","redis", MsgParentTypeEnum.MONITOR_TYPE.getMsgCode() ),



    ;

    private String msgCode;
    private String msgName;
    private String parentCode;

    MsgChildrenTypeEnum(String msgCode, String msgName, String parentCode) {
        this.msgCode = msgCode;
        this.msgName = msgName;
        this.parentCode = parentCode;
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


    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     *
     * @param msgCode
     * @return
     */
    public static String getNameByCode(String msgCode){
        for (MsgChildrenTypeEnum msgParentTypeEnum : MsgChildrenTypeEnum.values()){
            if (msgParentTypeEnum.getMsgCode().equals(msgCode)){
                return msgParentTypeEnum.getMsgName();
            }
        }
        return null;
    }




}
