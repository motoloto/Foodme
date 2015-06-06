package com.advice.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mail.controller.MailService;

@WebServlet("/AdviceServlet")
public class AdviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdviceServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		switch(action){
		case "addAdvice":
		addAdvice(request,response);
		break;
		}
	}
	private void addAdvice(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> errorMsgs = new LinkedList<String>();
		String cname=request.getParameter("cname");
		String cmail=request.getParameter("cmail");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		if(cname.isEmpty()){
			errorMsgs.add("名字不得為空");
		}
		if(cmail.isEmpty()){
			errorMsgs.add("信箱位址不得為空");
		}
		if(title.isEmpty()){
			errorMsgs.add("主旨不得為空");
		}
		if(content.isEmpty()){
			errorMsgs.add("內容不得為空");
		}
		if(!errorMsgs.isEmpty()){
			try {
			request.setAttribute("cname", cname); // 含有輸入格式錯誤的empVO物件,也存入req
			request.setAttribute("cmail", cmail); // 含有輸入格式錯誤的empVO物件,也存入req
			request.setAttribute("title", title); // 含有輸入格式錯誤的empVO物件,也存入req
			request.setAttribute("content", content); // 含有輸入格式錯誤的empVO物件,也存入req
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher failView = request.getRequestDispatcher("/front/advice/addAdvice.jsp");
			failView.forward(request, response);
				return;
			} catch (ServletException | IOException e) {			
				e.printStackTrace();
			}
		}
		
		try {
			MailService mailSrv=new MailService();
			mailSrv.sendAdvice(cname,cmail, title,content); //發送信件
			String url="/front/index.jsp";
			RequestDispatcher successView = request.getRequestDispatcher("/front/index.jsp");
			successView.forward(request, response);
			return; // 程式中斷
		} catch (MessagingException e) {	
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

}
