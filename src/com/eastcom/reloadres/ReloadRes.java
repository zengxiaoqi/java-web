package com.eastcom.reloadres;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eastcom.socket.mina.SendMsg;

/**
 * Servlet implementation class ReloadRes
 */
//@WebServlet("/ReloadRes")
public class ReloadRes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReloadRes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tableList= request.getParameter("tableList");
		String fileList= request.getParameter("fileList");
		String ip= request.getParameter("IP");
		Integer port= Integer.valueOf(request.getParameter("PORT"));
		System.out.println("tableList:"+tableList);
		System.out.println("fileList:"+fileList);
		System.out.println("ip:"+ip);
		System.out.println("port:"+port);
		
		SendMsg sendMsg = new SendMsg();
		sendMsg.sendMsg(tableList, fileList, ip, port);
	}

}
