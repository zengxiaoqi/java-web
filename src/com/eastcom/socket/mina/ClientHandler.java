package com.eastcom.socket.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {


    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
    	System.out.println("respones:" +message);
        session.close(false);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.err.println("连接异常报错，关闭连接！"+ cause);
        session.close(true);
    }

}
