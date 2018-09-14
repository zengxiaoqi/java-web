package com.eastcom.socket.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


@SuppressWarnings("deprecation")
public class SocketClientHelper {

    private static IoConnector ioConnector;

    private Integer remotePort;

    static{
    	ioConnector = new NioSocketConnector();
    	ioConnector.getFilterChain().addLast( "logger", new LoggingFilter() );
    	ioConnector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "GBK" ))));
////     connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ServerCodeFactory( )));   
    	ioConnector.setConnectTimeout(10000); 
    	ioConnector.setHandler(new ClientHandler());
    	
    }
    public void sendMessage(String ip, Object message) {
        sendMessage(ip, remotePort, message);
    }

    public void sendMessage(String ip, Integer port, Object message) {
        IoSession session = ioConnector.connect(new InetSocketAddress(ip, port)).awaitUninterruptibly().getSession();
        session.write(message);
        session.getCloseFuture().awaitUninterruptibly();
    }


    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

}
