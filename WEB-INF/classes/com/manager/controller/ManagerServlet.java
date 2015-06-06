package com.manager.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;

/**
 * Servlet implementation class ManagerServlet
 */
@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<String> errorMsgs=new LinkedList<String>();
		String action=request.getParameter("action");
		
		String account=null;
		String psw=null;
		String name=null;
		String email=null;
		String phone=null;
		String mgr_no_str=null;
		Integer mgr_no=null;
		ManagerService ms=new ManagerService();
		ManagerVO mgrvo=new ManagerVO();
		
		if(action.equals("insert"))
		{
			account=request.getParameter("mgr_account");
			if(account==null||account.trim().length()==0)
			{
				errorMsgs.add("管理員帳號不能為空");
			}	
			psw=request.getParameter("mgr_pwd");
			if(psw==null||psw.trim().length()==0)
			{
				errorMsgs.add("密碼不能為空");
			}
			name=request.getParameter("mgr_name");
			if(name==null||name.trim().length()==0)
			{
				errorMsgs.add("管理員姓名不能為空");
			}
			email=request.getParameter("mgr_mail");
			if(email==null||email.trim().length()==0)
			{
				errorMsgs.add("管理員信箱不能為空");
			}
			phone=request.getParameter("mgr_phone");
			if(phone==null||phone.trim().length()==0)
			{
				errorMsgs.add("管理員電話不能為空");
			}
			if ((phone.trim()).length() < 10||(phone.trim()).length() > 10) {
				errorMsgs.add("請輸入正確格式 (09xxxxxxxx)電話號碼");
			}
			
			if(errorMsgs.size()>0)
			{
				mgrvo.setMgr_account(account);
				mgrvo.setMgr_mail(email);
				mgrvo.setMgr_name(name);
				mgrvo.setMgr_phone(phone);
				mgrvo.setMgr_pwd(psw);
				
				request.setAttribute("mgrvo", mgrvo);
				request.setAttribute("updateResult", "管理員未新增");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher rd=request.getRequestDispatcher("/back/account/addMgr.jsp");
				rd.forward(request, response);
				return; // 程式中斷
			}	
			
			
			mgrvo=ms.addMgr(account, psw, name, email, phone);
			
			request.setAttribute("updateResult", "管理員已新增");
			request.setAttribute("mgrvo", mgrvo);
			RequestDispatcher rd=request.getRequestDispatcher("/back/account/be_accountMgr.jsp");
			rd.forward(request, response);
			return; // 程式中斷
			
		}	
		
		else if(action.equals("update"))
		{			
			mgr_no_str=request.getParameter("mgr_no");
			
			mgr_no=Integer.parseInt(mgr_no_str);
			
			psw=request.getParameter("mgr_pwd");
			if(psw==null||psw.trim().length()==0)
			{
				errorMsgs.add("密碼不能為空");
			}
			name=request.getParameter("mgr_name");
			if(name==null||name.trim().length()==0)
			{
				errorMsgs.add("管理員姓名不能為空");
			}
			email=request.getParameter("mgr_mail");
			if(email==null||email.trim().length()==0)
			{
				errorMsgs.add("管理員信箱不能為空");
			}
			phone=request.getParameter("mgr_phone");
			if(phone==null||phone.trim().length()==0)
			{
				errorMsgs.add("管理員電話不能為空");
			}
			if ((phone.trim()).length() < 10||(phone.trim()).length() > 10) {
				errorMsgs.add("請輸入正確格式 (09xxxxxxxx)電話號碼");
			}
			
			if(errorMsgs.size()>0)
			{
				//將使用者輸入內容原封不動轉送回原jsp檔
				mgrvo.setMgr_account(account);
				mgrvo.setMgr_mail(email);
				mgrvo.setMgr_name(name);
				mgrvo.setMgr_phone(phone);
				mgrvo.setMgr_pwd(psw);
				mgrvo.setMgr_no(mgr_no);
				request.setAttribute("mgrvo", mgrvo);
				
				request.setAttribute("updateResult", "管理員資料未修改");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher rd=request.getRequestDispatcher("/back/account/updateMgr.jsp");
				rd.forward(request, response);
				return; // 程式中斷
			}	
			
			
			ms.updateManager(psw, name, email, phone,mgr_no);
			
			//將使用者輸入內容原封不動轉送回原jsp檔
			mgrvo.setMgr_account(account);
			mgrvo.setMgr_mail(email);
			mgrvo.setMgr_name(name);
			mgrvo.setMgr_phone(phone);
			mgrvo.setMgr_pwd(psw);
			mgrvo.setMgr_no(mgr_no);
			request.setAttribute("mgrvo", mgrvo);
			request.setAttribute("updateResult", "管理員資料已修改");
			request.setAttribute("mgrvo", mgrvo);
			RequestDispatcher rd=request.getRequestDispatcher("/back/account/be_accountMgr.jsp");
			rd.forward(request, response);
			return; // 程式中斷
		}
		else if(action.equals("delete"))
		{
			mgr_no_str=request.getParameter("mgr_no");
			mgr_no=Integer.parseInt(mgr_no_str);
			
			mgrvo=ms.getOneManager(mgr_no);
			if(mgrvo.getMgr_account().equals("admin"))
			{
				request.setAttribute("updateResult", "管理員admin不可刪除");
				RequestDispatcher rd=request.getRequestDispatcher("/back/account/be_accountMgr.jsp");
				rd.forward(request, response);	
				return; // 程式中斷
			}
			else
			{
				ms.delete(mgr_no);
				request.setAttribute("updateResult", "管理員資料已刪除");
				RequestDispatcher rd=request.getRequestDispatcher("/back/account/be_accountMgr.jsp");
				rd.forward(request, response);
				return; // 程式中斷
			}	
			
		}
	}

}
