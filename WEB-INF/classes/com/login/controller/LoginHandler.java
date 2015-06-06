package com.login.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.manager.model.ManagerDAO;
import com.manager.model.ManagerDAO_interface;
import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;

public class LoginHandler extends HttpServlet {
	private String SERTIFIED_ID="YA101G5";
	
	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String mgr_account, String mgr_pwd) {

		ManagerService mgrSrv = new ManagerService();

		ManagerVO managerVO = mgrSrv.checkManager(mgr_account);
		if (managerVO == null) {
			return false;
		} else if (!managerVO.getMgr_pwd().equals(mgr_pwd)) {
			return false;
		} else {
			return true;
		}

	}

	protected void login(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		String mgr_account = req.getParameter("mgr_account");
		String mgr_pwd = req.getParameter("mgr_pwd");
		// 【取得使用者 帳號(account) 密碼(password)】
		if (mgr_account.isEmpty()) {
			errorMsgs.add("請輸入帳號");

		}
		if (mgr_pwd.isEmpty()) {
			errorMsgs.add("請輸入密碼");

		}
		if (errorMsgs.isEmpty()) {
			// 【檢查該帳號 , 密碼是否有效】
			if (!allowUser(mgr_account, mgr_pwd)) { // 【帳號 , 密碼無效時】
				errorMsgs.add("帳號密碼錯誤");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/login/be_login.jsp");
				try {
					failureView.forward(req, res);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else { // 【帳號 , 密碼有效時, 才做以下工作】
				errorMsgs.clear();
				HttpSession session = req.getSession();
				session.setAttribute("account", mgr_account); // *工作1:
				session.setAttribute("certificate", SERTIFIED_ID); // *工作1:
				
				// 才在session內做已經登入過的標識

				try { // *工作2: 看看有無來源網頁 (-如有:則重導之)
					String location = (String) session.getAttribute("location");
					if (location != null
							|| location.equals(req.getContextPath()
									+ "/login/be_login.jsp")) {
						session.removeAttribute("location");
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}

				try {
					res.sendRedirect(req.getContextPath()
							+ "/back/index.jsp");
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
					.getRequestDispatcher("/login/be_login.jsp");
			try {
				failureView.forward(req, res);
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
		// req.setCharacterEncoding("UTF-8");
		// res.setCharacterEncoding("UTF-8");

		// req.setCharacterEncoding("UTF-8");
		// //已由filters.SetCharacterEncodingFilter處理
		// res.setContentType("text/html; charset=UTF-8");
		// //已由filters.SetContentTypeFilter處理 (測試用)
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
			res.sendRedirect(req.getContextPath() + "/login/be_login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
