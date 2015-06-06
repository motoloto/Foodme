package com.login.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.manager.model.ManagerDAO;
import com.manager.model.ManagerDAO_interface;
import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;
import com.rest.model.*;

public class storeLoginHandler extends HttpServlet {
	private String SERTIFIED_ID="YA101G5";
	RestVO restVO ;
	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String rest_account, String rest_psw) {

		RestService restSrv = new RestService();

		restVO = restSrv.checkManager(rest_account);
		if (restVO == null) {
			return false;
		} else if (!restVO.getRest_psw().equals(rest_psw)) {
			return false;
		} else {
			
			return true;
		}

	}

	protected void login(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		String rest_account = req.getParameter("rest_account");
		String rest_psw = req.getParameter("rest_psw");
		// 【取得餐廳 帳號(account) 密碼(password)】
		if (rest_account.isEmpty()) {
			errorMsgs.add("請輸入帳號");

		}
		if (rest_psw.isEmpty()) {
			errorMsgs.add("請輸入密碼");

		}
		if (errorMsgs.isEmpty()) {
			// 【檢查該帳號 , 密碼是否有效】
			if (!allowUser(rest_account, rest_psw)) { // 【帳號 , 密碼無效時】
				errorMsgs.add("帳號密碼錯誤");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/login/store_login.jsp");
				try {
					failureView.forward(req, res);
					return; // 程式中斷
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else { // 【帳號 , 密碼有效時, 才做以下工作】
				errorMsgs.clear();
				HttpSession session = req.getSession();
				session.setAttribute("rest_account", rest_account); // *工作1:
				session.setAttribute("restVO", restVO); // *工作1:
				session.setAttribute("certificate", SERTIFIED_ID); // *工作1:
				
				// 才在session內做已經登入過的標識

				try { // *工作2: 看看有無來源網頁 (-如有:則重導之)
					String location = (String) session.getAttribute("location");
					if (location != null
							|| location.equals(req.getContextPath()
									+ "/login/store_login.jsp")) {
						session.removeAttribute("location");
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}

				try {
					res.sendRedirect(req.getContextPath()
							+ "/store/index.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // *工作3:
					// (-如無:
					// 無來源網頁,
				// 則重導至index.jsp網頁)
				// res.sendRedirect(req.getContextPath()+"/index.jsp");
				// //額外測試
			}
		} else {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/login/store_login.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		switch (action) {
		case "login":
			login(req, res);
			break;
		case "logout":
			logout(req, res);
			break;
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse res) {
		req.getSession().removeAttribute("account");
		req.getSession().removeAttribute("certificate");
		
		try {
			res.sendRedirect(req.getContextPath() + "/login/store_login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
