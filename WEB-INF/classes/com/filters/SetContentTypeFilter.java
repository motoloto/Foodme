package com.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SetContentTypeFilter implements Filter {
	
	private FilterConfig config;
	private String charset = null;
	
	public void init(FilterConfig config) throws ServletException {
	 this.config = config;
	 this.charset = config.getInitParameter("charset");
	}
	
	public void destroy() {
      config = null;
      charset = null;
  }
  
  public void doFilter(ServletRequest req, 
                       ServletResponse res, 
                       FilterChain chain) throws IOException, ServletException {
     res.setContentType("text/html; charset="+charset);
     chain.doFilter(req, res);
  }
} 