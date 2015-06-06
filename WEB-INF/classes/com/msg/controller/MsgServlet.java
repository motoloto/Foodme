package com.msg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.msg.model.MsgService;
import com.msg.model.MsgVO;

/**
 * Servlet implementation class MsgServlet
 */
@WebServlet("/MsgServlet")
public class MsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MsgServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 判斷指令 */
		switch (request.getParameter("action")) {
		case "insert":
			insert(request, response);
			break;

		}
		
		
	}

	private void insert(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		MsgService msgSrv =new MsgService();
		MsgVO msgVO=new MsgVO();
		msgVO.setRest_no(Integer.parseInt(request.getParameter("rest_no")));
		msgVO.setMsg_name(request.getParameter("msg_name"));
		msgVO.setMsg_cont(request.getParameter("msg_cont"));		
		msgSrv.insert(msgVO);
		
	}

}
