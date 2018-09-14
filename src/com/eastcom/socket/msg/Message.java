package com.eastcom.socket.msg;

public class Message {
	private MessageBody data;
	private MessageHead header;
	
	public MessageBody getData() {
		return data;
	}
	public void setData(MessageBody data) {
		this.data = data;
	}
	
	public MessageHead getHeader() {
		return header;
	}
	public void setHeader(MessageHead header) {
		this.header = header;
	}
}
