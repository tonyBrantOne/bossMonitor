package com.quartz.monitor.model.msgCenterModel;



/**
 * 消息中心表实体
 *
 * @author Administrator
 * @time 2018-10-29 15:40:22
 */
//region your codes 1
public class MessageCenter  extends AbstractMsgDTO{

	private static final long serialVersionUID = 1571945643498856288L;
	/**  */
	private String esId;

	private Integer id;
	/** 内容 */
	private String content;
	/** 是否已读 0：未读 1：已读 */
	private Integer readFlag;
	/** 消息二级类型 */
	private String msgTypeCode;
	/** 消息一级类型 */
	private String parentTypeCode;
	/** 通知方式名称 */
	private String noticeWay;


	@Override
	public String toString() {
		return "MessageCenter{" +
				"esId='" + esId + '\'' +
				", id=" + id +
				", content='" + content + '\'' +
				", readFlag=" + readFlag +
				", msgTypeCode='" + msgTypeCode + '\'' +
				", parentTypeCode='" + parentTypeCode + '\'' +
				", noticeWay='" + noticeWay + '\'' +
				", publicTime=" + publicTime +
				", status='" + status + '\'' +
				", warnType='" + warnType + '\'' +
				", serverName='" + serverName + '\'' +
				", content='" + content + '\'' +
				'}';
	}

	public String getEsId() {
		return esId;
	}

	public void setEsId(String esId) {
		this.esId = esId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}

	public String getMsgTypeCode() {
		return msgTypeCode;
	}

	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}

	public String getParentTypeCode() {
		return parentTypeCode;
	}

	public void setParentTypeCode(String parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}

	public String getNoticeWay() {
		return noticeWay;
	}

	public void setNoticeWay(String noticeWay) {
		this.noticeWay = noticeWay;
	}





}