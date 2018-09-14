package com.eastcom.socket.mina;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import com.eastcom.socket.msg.Message;
import com.eastcom.socket.msg.MessageBody;
import com.eastcom.socket.msg.MessageHead;
import com.eastcom.util.JsonUtils;
import com.eastcom.util.PropertiesUtil;


public class SendMsg {
	private static final String BUS_TABLES = "PLAT_RLTN_TRAN_RULE,PLAT_TABLE_COLUMN,PLAT_MONITOR_RULE,PLAT_RLTN_TRAN_RULEPARAM";
	private static final String DISPT_TABLES = "PLAT_BIZ_CTRL,PLAT_TRAN_BUFQUEUE,PLAT_TRAN_INFO";
	private static final String CHANEL_TABLES = "PLAT_CHANNEL_INFO,PLAT_ADAPTER_PARAM,PLAT_CHANNEL_CTRL,PLAT_IN_TRANCD_MAPPING,PLAT_TRAN_INFO,PLAT_BIZ_SUBSYS_INFO,PLAT_BIZ_SUBSYS_INFO";

	public static void main(String[] args) throws IOException {
		
		Properties prop = PropertiesUtil.loadProperties("reloadSource.properties");
		String ip = prop.getProperty("socket_ip");
		Integer port = Integer.valueOf(prop.getProperty("socket_port"));
		//String msg = prop.getProperty("sendMsg");
		
		/*
		String synIndex = prop.getProperty("synIndex");*/
		
		Message sendMsg = new Message();
		MessageHead msgHead = initHead();
		MessageBody msgBody = new MessageBody();
		
		SocketClientHelper sc = new SocketClientHelper();
		System.out.println("ip:"+ip+" port:"+port);
		String synType = prop.getProperty("synType");
		
		String synTable = prop.getProperty("synTable");
		String tableList = new String(); 
		if (synTable.trim().length() > 0) {
			String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			msgHead.setTraceNo(uuid);
			msgBody.setSynType("0");

			if (synType.equals("0")){
				msgBody.setSynIndex(synTable);
			}else {
				for (String str : synTable.split(",")) {
					if (str.equals("Channel")) {
						tableList += CHANEL_TABLES + ",";
					}
					if (str.equals("Business")) {
						tableList += BUS_TABLES + ",";
					}
					if (str.equals("Dispatcher")) {
						tableList += DISPT_TABLES + ",";
					}
				}
				msgBody.setSynIndex(tableList);
			}
			sendMsg.setData(msgBody);
			sendMsg.setHeader(msgHead);
			String msg = JsonUtils.object2Json(sendMsg);
			
			
			System.out.println("message is :"+msg);
			sc.sendMessage(ip, port, msg);
		}
		
		String synFile = prop.getProperty("sysFile");
		if (synFile.trim().length() > 0) {
			msgBody.setSynType("1");
			for (String str : synFile.split(",")) {
				String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				msgHead.setTraceNo(uuid);
				
				msgBody.setSynIndex(str);
				
				sendMsg.setData(msgBody);
				sendMsg.setHeader(msgHead);
				String msg = JsonUtils.object2Json(sendMsg);
				
				
				System.out.println("message is :"+msg);
				sc.sendMessage(ip, port, msg);
			}
		}
	}
	
	public String sendMsg(String tables, String files, String ip, Integer port) {
		//System.out.println("ip:"+ip+" port:"+port);
		
		Message sendMsg = new Message();
		MessageHead msgHead = initHead();
		MessageBody msgBody = new MessageBody();
		
		SocketClientHelper sc = new SocketClientHelper();

		String uuid = null;
		if (tables != null && (tables.trim().length() > 0)){
			uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			msgHead.setTraceNo(uuid);
			msgBody.setSynType("0");
			msgBody.setSynIndex(tables);
			sendMsg.setData(msgBody);
			sendMsg.setHeader(msgHead);
			String msg = JsonUtils.object2Json(sendMsg);
			System.out.println("message is :"+msg);
			sc.sendMessage(ip, port, msg);
		}
		if (files != null && (files.trim().length() > 0)){
			
			msgBody.setSynType("1");
			for (String str : files.split(",")) {
				uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				msgHead.setTraceNo(uuid);
				
				msgBody.setSynIndex(str);
				
				sendMsg.setData(msgBody);
				sendMsg.setHeader(msgHead);
				String msg = JsonUtils.object2Json(sendMsg);
				
				System.out.println("message is :"+msg);
				sc.sendMessage(ip, port, msg);
			}
		}
		
		return null;
	}
	
	public static MessageHead initHead() {
		// TODO Auto-generated method stub
		MessageHead msHead = new MessageHead();
		msHead.setLocalId("127.0.0.1");
		msHead.setSystemId("iLink");
		msHead.setMessageType("SYNC_INFO");
		msHead.setUserId("Test");
		return msHead;
	}
}
