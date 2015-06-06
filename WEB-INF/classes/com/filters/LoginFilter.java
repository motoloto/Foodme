package com.filters;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// �i��o session�j
		HttpSession session = req.getSession();
		// �i�q session �P�_��user�O�_�n�J�L�j
		Object account = session.getAttribute("account");
		if ((account == null) || session.getAttribute("certificate")!="YA101G5") {
//			System.out.println("========經過BACKLOGOIN過濾器:"+session.getId());
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/login/be_login.jsp");
			return;
		} else if( session.getAttribute("certificate").equals("YA101G5")){
			chain.doFilter(request, response);
		}
	}
}
