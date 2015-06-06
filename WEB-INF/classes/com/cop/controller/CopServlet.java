package com.cop.controller;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import com.cop.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.api.policy.PolicyResolver.ServerContext;

public class CopServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		 res.setContentType("text/html; charset=UTF-8");
		
	if("getOne_By_Rest".equals(action)){
		Integer rest_no=Integer.parseInt(req.getParameter("rest_no"));
		CopService copSrv=new CopService();
		Gson gson=new Gson();
		CopVO copVO=copSrv.getOneCopOnState(rest_no);
		CopVOinJson copVOinJson=new CopVOinJson();
		if(copVO!=null){
		copVOinJson.setCop_no(copVO.getCop_no());
		copVOinJson.setRest_no(copVO.getRest_no());
		copVOinJson.setCop_name(copVO.getCop_name());
		copVOinJson.setCop_content(copVO.getCop_content());
		copVOinJson.setCop_dl(copVO.getCop_dl().toString());
		copVOinJson.setCop_price(copVO.getCop_price());
		copVOinJson.setCop_orlprice(copVO.getCop_orlprice());
		}
		String gsonStr=gson.toJson(copVOinJson);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain");
		PrintWriter out=res.getWriter();
		out.write(gsonStr);
		out.close();
	}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllCop.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer cop_no = new Integer(req.getParameter("cop_no"));
				
				/***************************2.開始查詢資料****************************************/
				CopService copSvc = new CopService();
				CopVO copVO = copSvc.getOneCop(cop_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("copVO", copVO);         // 資料庫取出的copVO物件,存入req
				String url = "/store/coupon/updateCop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_cop_input.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/coupon/update_cop_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		
		
		if ("update".equals(action)) { // 來自updateCop.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				Integer cop_no = new Integer(req.getParameter("cop_no").trim());
				Integer rest_no = new Integer(req.getParameter("rest_no").trim());
				Integer cop_selamt = new Integer(req.getParameter("cop_selamt").trim());
				
				String cop_name = req.getParameter("cop_name").trim();
				
				if (cop_name == null || (cop_name.trim()).length() == 0) {
					errorMsgs.add("請輸入餐劵名稱");
				}
				
				String cop_content = req.getParameter("cop_content").trim();

				if (cop_content == null || (cop_content.trim()).length() == 0) {
					errorMsgs.add("請輸入餐劵內容");
				}
				Date cop_date = new java.sql.Date(System.currentTimeMillis());
				
				java.sql.Date cop_dl = null;
				try {
					cop_dl = java.sql.Date.valueOf(req.getParameter("cop_dl").trim());
				} catch (IllegalArgumentException e) {
					cop_dl=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入使用期限");
				}
				
				Integer cop_price = null;
				try {
					cop_price = new Integer(req.getParameter("cop_price").trim());
				} catch (NumberFormatException e) {
					cop_price = 0;
					errorMsgs.add("請輸入餐劵價格");
				}
				
				Integer cop_orlprice = null;
				try {
					cop_orlprice = new Integer(req.getParameter("cop_orlprice").trim());
				} catch (NumberFormatException e) {
					cop_orlprice = 0;
					errorMsgs.add("請輸入餐劵原價");
				}
				
				Integer cop_circu = 100;
				
				
				String cop_state = req.getParameter("cop_state").trim();
				if (cop_state == null) {
					cop_state="3";//既不是"是"也不是"否"，避免jsp取到空值
					errorMsgs.add("請選擇是否上架");
				}
				
				
				/*如果使用者要將狀態從0改成1，則先把此餐廳之全部餐劵全設0，等再將此餐劵設1*/
				if(cop_state.equals("1")){
					ServletContext context = getServletContext();
					List<CopVO> listCop_ByCompositeQuery = (List<CopVO>)context.getAttribute("listCop_ByCompositeQuery");
					for(CopVO vo: listCop_ByCompositeQuery){
						Integer tmp_cop_no = vo.getCop_no();
						Integer tmp_rest_no = vo.getRest_no();
						String  tmp_cop_name = vo.getCop_name();
						String  tmp_cop_content = vo.getCop_content();
						Integer tmp_cop_selamt = vo.getCop_selamt();
						Date    tmp_cop_dl = vo.getCop_dl();
						Integer tmp_cop_price = vo.getCop_price();
						Integer tmp_cop_orlprice = vo.getCop_orlprice();
						Integer tmp_cop_circu = vo.getCop_circu();
						Date    tmp_cop_date= null;
						String tmp_cop_state = "0";  //先將此物件的狀態設為下架
						CopService copSvc = new CopService();
						vo = copSvc.updateCop(tmp_cop_no,tmp_rest_no,tmp_cop_name,tmp_cop_content, tmp_cop_orlprice, tmp_cop_price, tmp_cop_dl, tmp_cop_state,tmp_cop_date, tmp_cop_circu,tmp_cop_selamt);
					}
				}
				
				/* 判斷是否上架，決定上架日期 */
				if (cop_state.equals("0") ) {
					cop_date = null;//如果選擇不上架，則不需上假日期
				}else if(cop_state.equals("1")){
					cop_date= new java.sql.Date(System.currentTimeMillis());
				}
				

				CopVO copVO = new CopVO();
				copVO.setRest_no(rest_no);
				copVO.setCop_name(cop_name);
				copVO.setCop_content(cop_content);
				copVO.setCop_orlprice(cop_orlprice);
				copVO.setCop_price(cop_orlprice);
				copVO.setCop_dl(cop_dl);
				copVO.setCop_state(cop_state);
				copVO.setCop_date(cop_date);
				copVO.setCop_circu(cop_circu);
				copVO.setCop_selamt(cop_selamt);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("copVO", copVO); // 含有輸入格式錯誤的copVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/coupon/updateCop.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				CopService copSvc = new CopService();
				copVO = copSvc.updateCop(cop_no,rest_no,cop_name,cop_content, cop_orlprice, cop_price, cop_dl, cop_state,cop_date, cop_circu,cop_selamt);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				HttpSession session = req.getSession();
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
				System.out.println(session.getId());				
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
//				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				ServletContext context = getServletContext();//這兩行是測試從context取得map
				Map<String, String[]> map = (Map<String, String[]>)context.getAttribute("map");
				List<CopVO> list  =copSvc.getAll(map);
//				session.setAttribute("listCop_ByCompositeQuery",list);本來是用session存list
				context.setAttribute("listCop_ByCompositeQuery",list);
				
				
				
//				String url = "/store/listCopforREST.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(requestURL); // 新增成功後轉交listAllCop.jsp
				successView.forward(req, res);				
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/coupon/updateCop.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

        if ("insert".equals(action)) { // 來自addCop.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				Integer rest_no = new Integer(req.getParameter("rest_no").trim());//餐廳編號，由餐廳首頁的hidden(rest_no)取得
				Integer cop_selamt = 0;//由於新建餐劵，所以銷售量為0
				
				String cop_name = req.getParameter("cop_name").trim();
				
				if (cop_name == null || (cop_name.trim()).length() == 0) {
					errorMsgs.add("請輸入餐劵名稱");
				}
				
				String cop_content = req.getParameter("cop_content").trim();

				if (cop_content == null || (cop_content.trim()).length() == 0) {
					errorMsgs.add("請輸入餐劵內容");
				}
				Date cop_date = new java.sql.Date(System.currentTimeMillis());
				
				java.sql.Date cop_dl = null;
				try {
					cop_dl = java.sql.Date.valueOf(req.getParameter("cop_dl").trim());
				} catch (IllegalArgumentException e) {
					cop_dl=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入使用期限");
				}
				
				Integer cop_price = null;
				try {
					cop_price = new Integer(req.getParameter("cop_price").trim());
				} catch (NumberFormatException e) {
					cop_price = 0;
					errorMsgs.add("請輸入餐劵價格");
				}
				
				Integer cop_orlprice = null;
				try {
					cop_orlprice = new Integer(req.getParameter("cop_orlprice").trim());
				} catch (NumberFormatException e) {
					cop_orlprice = 0;
					errorMsgs.add("請輸入餐劵原價");
				}
				
				Integer cop_circu = 100;
				
				
				String cop_state = req.getParameter("cop_state").trim();
				if (cop_state == null) {
					cop_state="3";//既不是"是"也不是"否"，避免jsp取到空值
					errorMsgs.add("請選擇是否上架");
				}
				
				
				/*如果使用者要將狀態從0改成1，則先把此餐廳之全部餐劵全設0，等再將此餐劵設1*/
				if(cop_state.equals("1")){
					ServletContext context = getServletContext();
					List<CopVO> listCop_ByCompositeQuery = (List<CopVO>)context.getAttribute("listCop_ByCompositeQuery");
					for(CopVO vo: listCop_ByCompositeQuery){
						Integer tmp_cop_no = vo.getCop_no();
						Integer tmp_rest_no = vo.getRest_no();
						String  tmp_cop_name = vo.getCop_name();
						String  tmp_cop_content = vo.getCop_content();
						Integer tmp_cop_selamt = vo.getCop_selamt();
						Date    tmp_cop_dl = vo.getCop_dl();
						Integer tmp_cop_price = vo.getCop_price();
						Integer tmp_cop_orlprice = vo.getCop_orlprice();
						Integer tmp_cop_circu = vo.getCop_circu();
						Date    tmp_cop_date= null;
						String tmp_cop_state = "0";  //先將此物件的狀態設為下架
						CopService copSvc = new CopService();
						vo = copSvc.updateCop(tmp_cop_no,tmp_rest_no,tmp_cop_name,tmp_cop_content, tmp_cop_orlprice, tmp_cop_price, tmp_cop_dl, tmp_cop_state,tmp_cop_date, tmp_cop_circu,tmp_cop_selamt);
					}
				}
				
				/* 判斷是否上架，決定上架日期 */
				if (cop_state.equals("0") ) {
					cop_date = null;//如果選擇不上架，則不需上假日期
				}else if(cop_state.equals("1")){
					cop_date= new java.sql.Date(System.currentTimeMillis());
				}
				

				CopVO copVO = new CopVO();
				copVO.setRest_no(rest_no);
				copVO.setCop_name(cop_name);
				copVO.setCop_content(cop_content);
				copVO.setCop_orlprice(cop_orlprice);
				copVO.setCop_price(cop_orlprice);
				copVO.setCop_dl(cop_dl);
				copVO.setCop_state(cop_state);
				copVO.setCop_date(cop_date);
				copVO.setCop_circu(cop_circu);
				copVO.setCop_selamt(cop_selamt);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("copVO", copVO); // 含有輸入格式錯誤的copVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/coupon/addCop.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				CopService copSvc = new CopService();
				copVO = copSvc.addCop(rest_no,cop_name,cop_content, cop_orlprice, cop_price, cop_dl, cop_state,cop_date, cop_circu,cop_selamt);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				ServletContext context = getServletContext();//這兩行是測試從context取得map
				Map<String, String[]> map = (Map<String, String[]>)context.getAttribute("map");
				List<CopVO> list  =copSvc.getAll(map);
				context.setAttribute("listCop_ByCompositeQuery",list);
				
				
				String url = "/store/coupon/listCopforREST.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCop.jsp
				successView.forward(req, res);					
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/store/addCop.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllCop.jsp
			String resultMsg = null;
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer copno = new Integer(req.getParameter("copno"));
				
				/***************************2.開始刪除資料***************************************/
				CopService copSvc = new CopService();
				copSvc.deleteCop(copno);
				resultMsg = "成功刪除此餐廳";
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				if ("mobile".equals(req.getParameter("device"))) {
					res.setCharacterEncoding("UTF-8");
					res.setContentType("text/plain");
					PrintWriter out = res.getWriter();
					Gson gson = new Gson();
					out.write(gson.toJson(resultMsg));
					out.close();
				}else{
				String url = "/store/coupon/listAllCop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				return; // 程式中斷
				}
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				if ("mobile".equals(req.getParameter("device"))) {
					resultMsg = "操作錯誤，請稍後再試";
					res.setCharacterEncoding("UTF-8");
					res.setContentType("text/plain");
					PrintWriter out = res.getWriter();
					Gson gson = new Gson();
					out.write(gson.toJson(resultMsg));
					out.close();
				} else {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/coupon/listAllCop.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
				}
			}
		}
		
		if ("listCop_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				//Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>)map1.clone();
//					session.setAttribute("map",map2);
					ServletContext context = getServletContext();
					context.setAttribute("map",map2);//測試把map放在context
					
					map = (HashMap<String, String[]>)req.getParameterMap();

				/***************************2.開始複合查詢***************************************/
				CopService copSvc = new CopService();
				List<CopVO> list  = copSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				session.setAttribute("listCop_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				context.setAttribute("listCop_ByCompositeQuery", list);//測試把list放在客廳(context)
				RequestDispatcher successView = req.getRequestDispatcher("/store/coupon/listCopforREST.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/coupon/listCopforREST.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
if ("Set_State".equals(action)) { // 來自updateCop.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
//			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				Integer cop_no = new Integer(req.getParameter("cop_no").trim());
				String cop_state =req.getParameter("cop_state").trim();
				java.sql.Date cop_date=null;
				
				
				
				/*如果使用者要將狀態從0改成1，則先把此餐廳之全部餐劵全設0，等再將此餐劵設1*/
				if(cop_state.equals("1")){
					ServletContext context = getServletContext();
					List<CopVO> listCop_ByCompositeQuery = (List<CopVO>)context.getAttribute("listCop_ByCompositeQuery");
					for(CopVO vo: listCop_ByCompositeQuery){
						Integer tmp_cop_no = vo.getCop_no();
						Integer tmp_rest_no = vo.getRest_no();
						String  tmp_cop_name = vo.getCop_name();
						String  tmp_cop_content = vo.getCop_content();
						Integer tmp_cop_selamt = vo.getCop_selamt();
						Date    tmp_cop_dl = vo.getCop_dl();
						Integer tmp_cop_price = vo.getCop_price();
						Integer tmp_cop_orlprice = vo.getCop_orlprice();
						Integer tmp_cop_circu = vo.getCop_circu();
						Date    tmp_cop_date= null;
						String tmp_cop_state = "0";  //先將此物件的狀態設為下架
						CopService copSvc = new CopService();
						vo = copSvc.updateCop(tmp_cop_no,tmp_rest_no,tmp_cop_name,tmp_cop_content, tmp_cop_orlprice, tmp_cop_price, tmp_cop_dl, tmp_cop_state,tmp_cop_date, tmp_cop_circu,tmp_cop_selamt);
					}
				}
				
				/* 判斷是否上架，決定上架日期 */
				if (cop_state.equals("0") ) {
					cop_date = null;//如果選擇不上架，則不需上假日期
				}else if(cop_state.equals("1")){
					cop_date= new java.sql.Date(System.currentTimeMillis());
				}
				CopService copSvc = new CopService();
				CopVO copVO = copSvc.getOneCop(cop_no);
				copVO.setCop_state(cop_state);
                copVO.setCop_date(cop_date);
				
				
				
				// Send the use back to the form, if there were errors
				
				
				/***************************2.開始新增資料***************************************/
                copVO=copSvc.updateCop(copVO);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				HttpSession session = req.getSession();
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
				System.out.println(session.getId());				
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
//				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				ServletContext context = getServletContext();//這兩行是測試從context取得map
				Map<String, String[]> map = (Map<String, String[]>)context.getAttribute("map");
				List<CopVO> list  =copSvc.getAll(map);
//				session.setAttribute("listCop_ByCompositeQuery",list);本來是用session存list
				context.setAttribute("listCop_ByCompositeQuery",list);
				
				
				
//				String url = "/store/listCopforREST.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(requestURL); // 新增成功後轉交listAllCop.jsp
				successView.forward(req, res);				
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/store/coupon/updateCop.jsp");
//				failureView.forward(req, res);
//				return; // 程式中斷
//			}
		}
	}
}
