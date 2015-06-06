package com.login.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mem.model.MemService;
import com.mem.model.MemVO;

public class MemLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		List<String> errorMsgs = new LinkedList<String>();
		String isMember = "false";

		String action = req.getParameter("action");
		if ("login".equals(action)) {
			String mem_account = req.getParameter("mem_account");
			String mem_pwd = req.getParameter("mem_pwd");

			MemVO memVO = null;

			MemService memSvc = new MemService();

			memVO = memSvc.getOneMem(mem_account);

			if (memVO == null || (!memVO.getMem_pwd().equals(mem_pwd))) {
				/* 傳給手機的回應 */
				errorMsgs.add("帳號密碼錯誤");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/memLogin.jsp");

				if ("mobile".equals(req.getParameter("device"))) {
					/* 傳給手機的回應 */
					PrintWriter out = res.getWriter();
					isMember = "false";
					out.println(isMember);
					out.close();
				}else{
					req.setAttribute("errorMsgs", errorMsgs);

					failureView.forward(req, res);
					return;//�{�����_
				}
			} else {
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);
				if ("mobile".equals(req.getParameter("device"))) {
					/* 傳給手機的回應 */
					PrintWriter out = res.getWriter();
					isMember = "true";
					out.println(isMember);
					out.close();
				} else {
					try {
						String location = (String) session
								.getAttribute("location");
						if (location != null) {
							session.removeAttribute("location");
							res.sendRedirect(location);
							return;
						}
					} catch (Exception ignored) {
					}

					res.sendRedirect(req.getContextPath() + "/front/index.jsp");

				}
				//

			}
		}
		if ("logout".equals(action)) {
			String url = req.getContextPath() + "/front/index.jsp";

			HttpSession session = req.getSession();
			session.removeAttribute("memVO");
			res.sendRedirect(req.getContextPath() + "/front/index.jsp");
		}
	}
}
