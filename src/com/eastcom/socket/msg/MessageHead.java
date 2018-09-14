package com.eastcom.socket.msg;

public class MessageHead {
private String domainId; // 域主机ID/IP
	
	private String localId; // 运行主机ID/IP
	
	private String systemId; // 系统ID--iLink：渠道/业务 进程
	
	/*
	 * 目标进程类型 TO_OPS：运维；
	 * TO_MON：监控;
	 * TO_ILINK_CHANNEL：ilink渠道进程; 
	 * TO_ILINK_BIZ:ilink核心业务进程
	 * TO_AGETNT：代理进程
	 * TO_OTHER: 第三方
	 * */
	private String targetType; 
	
	private String userId; // 操作员ID
	
	private String messageType; // 交易类型---task class  
	
	private String traceNo; // 系统跟踪号
	
	private String errorCode; // 响应码
	
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
