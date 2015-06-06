package com.news.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.news.model.NewsService;
import com.news.model.NewsVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class NewsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		/* 判斷指令 */
		switch (req.getParameter("action")) {
		case "update":
			update(req, res);
			break;
		case "getOne_For_Update":
			findByPrimaryKey(req, res);
			break;
		case "insert":
			insert(req, res);
			break;
		case "delete":
			delete(req, res);
			break;
		}
	}

	private void update(HttpServletRequest req, HttpServletResponse res) {
		// request from updateNews.jsp
		List<String> errorMsgs = new LinkedList<String>();
		// get the VO which i want to update
		NewsVO newsVO = (NewsVO) req.getSession().getAttribute("newsVO");
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		Integer news_no = 0;
		java.sql.Date news_time = null;
		String news_title = null;
		String news_cont = null;

		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String newsNostr = req.getParameter("news_no").trim();
			if (newsNostr.isEmpty()) {
				errorMsgs.add("消息編號不得為空");
			} else {
				try {
					news_no = Integer.parseInt(newsNostr);
				} catch (NumberFormatException e) {
					errorMsgs.add("消息編號格式錯誤");
				}
			}

			news_title = req.getParameter("news_title").trim();
			if (news_title.isEmpty()) {
				errorMsgs.add("標題不可空白");
			}
			news_cont = req.getParameter("news_cont").trim();
			if (news_cont.isEmpty()) {
				errorMsgs.add("內容不可空白");
			}

			try {
				news_time = java.sql.Date.valueOf(req.getParameter("news_time")
						.trim());
			} catch (Exception e) {
				errorMsgs.add("日期錯誤");
			}

			newsVO.setNews_no(news_no);
			newsVO.setNews_title(news_title);
			newsVO.setNews_cont(news_cont);
			newsVO.setNews_time(news_time);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/news/updateNews.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** update 2.開始修改資料 *****************************************/
			NewsService newsSvc = new NewsService();
			newsSvc.updateNews(newsVO);

			/*************************** update 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/back/news/be_newsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** update 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/news/updateNews.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void findByPrimaryKey(HttpServletRequest req,
			HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/*************************** get one for update 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("news_no");
			Integer news_no = Integer.parseInt(str);
			/*************************** get one for update 2.開始查詢資料 *****************************************/
			NewsService newsSrv = new NewsService();
			NewsVO newsVO = new NewsVO();
			newsVO = newsSrv.getOneForUpdate(news_no);

			/*************************** get one for update 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("newsVO", newsVO);

			String url = "/back/news/updateNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																			// listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** get one for update 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
			// PrintWriter out=res.getWriter();
			// out.print("<script >window.alert(\"ERROR\");</script>");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/news/be_newsMgr.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void insert(HttpServletRequest req, HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			NewsVO newsVO = new NewsVO();
			Integer news_no = 0;
			String news_title = null;
			String news_cont = null;
			java.sql.Date news_time = null;

			req.setAttribute("errorMsgs", errorMsgs);

			news_title = req.getParameter("news_title").trim();
			if (news_title.isEmpty()) {
				errorMsgs.add("標題不可空白");
			}
			news_cont = req.getParameter("news_cont").trim();
			if (news_cont.isEmpty()) {
				errorMsgs.add("內容不可空白");
			}

			try {
				news_time = java.sql.Date.valueOf(req.getParameter("news_time")
						.trim());
			} catch (Exception e) {
				errorMsgs.add("日期錯誤");
			}

			newsVO.setNews_title(news_title);
			newsVO.setNews_cont(news_cont);
			newsVO.setNews_time(news_time);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/news/addNews.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsVO = newsSvc.addNews(news_time, news_title, news_cont);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back/news/be_newsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/

		} catch (IllegalArgumentException e) {
			errorMsgs.add("請輸入日期!");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/news/addNews.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (ServletException e) {
			errorMsgs.add("無法新增");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/news/addNews.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse res) {
		try {
			int news_no = Integer.parseInt(req.getParameter("news_no"));
			NewsService NewsSrv = new NewsService();
			NewsSrv.deleteNews(news_no);
			String url = "/back/news/be_newsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
