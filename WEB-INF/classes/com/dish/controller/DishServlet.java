package com.dish.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import net.sf.json.JSONArray;

import com.dish.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class DishServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public DishServlet() { // copy from mysamplecode, for what?
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		res.setContentType("text/html; charset=utf-8");

		res.setHeader("Cache-control", "no-cache, no-store");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "-1");

		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("dish_cont");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("不可為空");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/dish/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			String dish_cont = null;
			try {
				dish_cont = str;
			} catch (Exception e) {
				errorMsgs.add("料理名稱不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/dish/select_page.jsp");
				failureView.forward(req, res);
				return;// �{�����_
			}

			/*************************** 2.開始查詢資料 *****************************************/
			DishService dishSvc = new DishService();
			List<DishVO> list = dishSvc.query(dish_cont);

			// 比對
			// EmpService empSvc = new EmpService();
			// EmpVO empVO = empSvc.getOneEmp(empno);
			//
			// byte[] dish_pic = null;
			//
			// InputStream in = new ByteArrayInputStream(dishVO.getDish_pic());
			//
			// ByteArrayOutputStream out = new ByteArrayOutputStream();
			//
			// byte[] read = new byte[4 * 1024]; // 設定一次要read多少Byte
			//
			// int len = 0;
			//
			// while ((len = in.read(read)) != -1) { // =-1 代表獨到array尾端，!=-1則繼續讀
			// out.write(read, 0, len); // 讀到多少(len)就把他 write out
			// }
			// dish_pic = out.toByteArray();
			//
			// out.flush();
			// in.close();
			// out.close();

			// if (dishVO == null) {
			// errorMsgs.add("�d�L���");
			// }
			// // Send the use back to the form, if there were errors
			// if (!errorMsgs.isEmpty()) {
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/front/dish/select_page.jsp");
			// failureView.forward(req, res);
			// return;//�{�����_
			// }

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list); // 資料庫取出的list物件,存入req
			// req.setAttribute("dishVO", dishVO); // 資料庫取出的dishVO物件,存入req
			String url = "/store/dish/listQueryResult.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																			// listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 *************************************/
			// }
			// catch (Exception e) {
			// errorMsgs.add("�L�k��o���:" + e.getMessage());
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/front/dish/select_page.jsp");
			// failureView.forward(req, res);
			// }
		}

		 if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD
		
		 List<String> errorMsgs = new LinkedList<String>();
		 // Store this set in the request scope, in case we need to
		 // send the ErrorPage view.
		 req.setAttribute("errorMsgs", errorMsgs);
		
		 try {
		 /***************************1.�����ШD�Ѽ�****************************************/
		 Integer dish_no = new Integer(req.getParameter("dish_no"));		 
		 
		 /***************************2.�}�l�d�߸��****************************************/
		 DishService dishSvc = new DishService();
		 DishVO dishVO = dishSvc.getOneDish(dish_no);
		 
		 /***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
		 req.setAttribute("dishVO", dishVO); // ��Ʈw��X��dishVO����,�s�Jreq
		 String url = "/store/dish/updateDish.jsp";
		 RequestDispatcher successView = req.getRequestDispatcher(url);//		 ���\��� update_dish_input.jsp
		 successView.forward(req, res);
		 return; // 程式中斷
		 /***************************��L�i�઺��~�B�z**********************************/
		 } catch (Exception e) {
		 errorMsgs.add("�L�k��o�n�ק諸���:" + e.getMessage());
		 RequestDispatcher failureView = req
		 .getRequestDispatcher("/store/dish/listAllDish.jsp");
		 failureView.forward(req, res);
		 return; // 程式中斷
		 }
		 }

		 if ("update".equals(action)) { // �Ӧ�update_dish_input.jsp���ШD
		
		 List<String> errorMsgs = new LinkedList<String>();
		 // Store this set in the request scope, in case we need to
		 // send the ErrorPage view.
		 req.setAttribute("errorMsgs", errorMsgs);
		
//		 try {
		 /***************************1.�����ШD�Ѽ� -		 ��J�榡����~�B�z**********************/
		 Integer dish_no = new Integer(req.getParameter("dish_no").trim());		 
		 //		 Integer rest_no = new Integer(req.getParameter("rest_no").trim());
		 Integer rest_no = 7002;	//之後改成從session 取得rest_no
		 
		 String dish_name = req.getParameter("dish_name").trim();
		 if(dish_name == null || (dish_name.trim()).length() == 0){
				errorMsgs.add("請輸入料理名稱(不得為空)");
			}
		 
		 String dish_cont = req.getParameter("dish_cont").trim();
		 if(dish_cont == null || (dish_cont.trim()).length() == 0){
				errorMsgs.add("請輸入料理描述(不得為空)");
			}
		 
		 String dish_state = req.getParameter("dish_state").trim();
		 
		 Integer dish_price = null;
		 try{dish_price = new Integer(req.getParameter("dish_price").trim());				 
		 }
		 catch (NumberFormatException e) {
				dish_price = 0;
				errorMsgs.add("請輸入正確格式之價錢(正整數)");
			}
		 
		 byte[] dish_pic = null;			
		 DishService dishSrv=new DishService();
		 DishVO dishVO = dishSrv.getOneDish(dish_no);
		  dishVO.setDish_no(dish_no);
		  dishVO.setRest_no(rest_no);
		  dishVO.setDish_name(dish_name);
		  dishVO.setDish_cont(dish_cont);
		  dishVO.setDish_state(dish_state);
		  dishVO.setDish_price(dish_price);
		  
		  Part part = req.getPart("dish_pic");
		  String fileName = getFileNameFromPart(part);
		  if(fileName!=null){
			  InputStream in = part.getInputStream();
			  ByteArrayOutputStream out = new ByteArrayOutputStream();
			  byte[] read = new byte[4 * 1024];
			  int len = 0;
			  while ((len = in.read(read)) != -1) {
				  out.write(read, 0, len);
			  }
			  dish_pic = out.toByteArray();
			  
			  out.flush();
			  in.close();
			  out.close();
		  }else{
			  dish_pic=dishVO.getDish_pic();
		  }
		 // Send the use back to the form, if there were errors
		 if (!errorMsgs.isEmpty()) {
		 req.setAttribute("dishVO", dishVO); //		 �t����J�榡��~��dishVO����,�]�s�Jreq
		 RequestDispatcher failureView = req.getRequestDispatcher("/store/dish/listAllDish.jsp");
		 failureView.forward(req, res);
		 return; //�{�����_
		 }
		
		 /***************************2.�}�l�ק���*****************************************/
		 DishService dishSvc = new DishService();
		 dishVO = dishSvc.updateDish(dish_name, dish_cont, dish_state, dish_price, dish_no, rest_no, dish_pic);
		
		 /***************************3.�ק粒��,�ǳ����(Send the Success		 view)*************/
		 req.setAttribute("dishVO", dishVO); //		 ��Ʈwupdate���\��,���T����dishVO����,�s�Jreq
		 String url = "/store/dish/listAllDish.jsp";
		 RequestDispatcher successView = req.getRequestDispatcher(url); //		 �ק令�\��,���listOneEmp.jsp
		 successView.forward(req, res);
		 return; // 程式中斷
		 /***************************��L�i�઺��~�B�z*************************************/
//		 } catch (Exception e) {
//		 errorMsgs.add("�ק��ƥ���:"+e.getMessage());
//		 RequestDispatcher failureView = req.getRequestDispatcher("/store/dish/listAllDish.jsp");
//		 failureView.forward(req, res);
//		 }
		 }

		if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡����~�B�z *************************/			
				
				String dish_name = req.getParameter("dish_name").trim();
				if(dish_name == null || (dish_name.trim()).length() == 0){
					errorMsgs.add("請輸入料理名稱(不得為空)");
				}
				
				String dish_cont = req.getParameter("dish_cont").trim();
				if(dish_cont == null || (dish_cont.trim()).length() == 0){
					errorMsgs.add("請輸入料理描述(不得為空)");
				}
				
				String dish_state = req.getParameter("dish_state").trim();
				
				Integer dish_price = 0;
				
				try{
					dish_price = new Integer(req.getParameter("dish_price").trim());
				}
				catch (NumberFormatException e) {
					dish_price = 0;
					errorMsgs.add("請輸入正確格式之價錢(正整數)");
				}
				
				Integer rest_no = (Integer)req.getSession().getAttribute("rest_no");
//				Integer rest_no = 7002;		//temp				
//				req.getSession().setAttribute("rest_no", rest_no);			

				DishVO dishVO = new DishVO();
				
				dishVO.setDish_name(dish_name);
				dishVO.setDish_cont(dish_cont);				
				dishVO.setDish_state(dish_state);
				dishVO.setDish_price(dish_price);		
				dishVO.setRest_no(rest_no);
				
				byte[] dish_pic = null;				
				Part part = req.getPart("dish_pic");
				
				InputStream in = part.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] read = new byte[4 * 1024];
				int len = 0;
				while ((len = in.read(read)) != -1) {
					out.write(read, 0, len);
				}
				dish_pic = out.toByteArray();

				out.flush();
				in.close();
				out.close();
				
				// Send the use back to the form, if there were errors
				req.setCharacterEncoding("UTF-8");
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dishVO", dishVO); // �t����J�榡��~��dishVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/dish/listAllDish.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				DishService dishSvc = new DishService();
				dishVO = dishSvc.addDish(rest_no, dish_name, dish_cont, dish_price, dish_state,dish_pic);				

				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				String url = "/store/dish/listAllDish.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** ��L�i�઺��~�B�z **********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("store/dish/addDish.jsp");
//				failureView.forward(req, res);
//			}
		}

		 if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp
		
		 List<String> errorMsgs = new LinkedList<String>();
		 // Store this set in the request scope, in case we need to
		 // send the ErrorPage view.
		 req.setAttribute("errorMsgs", errorMsgs);
		
		 try {
		 /***************************1.�����ШD�Ѽ�***************************************/
		 Integer dish_no = new Integer(req.getParameter("dish_no"));
		
		 /***************************2.�}�l�R�����***************************************/
		 DishService dishSvc = new DishService();
		 dishSvc.deleteDish(dish_no);
		
		 /***************************3.�R������,�ǳ����(Send the Success
		 view)***********/
		 String url = "/store/dish/listAllDish.jsp";
		 RequestDispatcher successView = req.getRequestDispatcher(url);//		 �R�����\��,���^�e�X�R�����ӷ�����
		 successView.forward(req, res);
		 return; // 程式中斷
		 /***************************��L�i�઺��~�B�z**********************************/
		 } catch (Exception e) {
		 errorMsgs.add("�R����ƥ���:"+e.getMessage());
		 RequestDispatcher failureView = req
		 .getRequestDispatcher("/store/dish/listAllDish.jsp");
		 failureView.forward(req, res);
		 return; // 程式中斷
		 }
		 }

		if ("autoCommit".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setCharacterEncoding("UTF-8");

			// try {
			/*************************** 1. ***************************************/
			String str = (req.getParameter("term"));
			String dish_cont = new String(str.getBytes("iso8859-1"), "UTF-8");// q为插件默认的参数名，为前台传过来的值

			/*************************** 2. ***************************************/
			DishService dishSvc = new DishService();
			// System.out.println("傳入的參數::::::"+dish_cont);
			List<DishVO> list = dishSvc.query(dish_cont);
			// System.out.println(list.get(0).getDish_name());
			// System.out.println("查詢的結果"+list);
			// String[] resultArray = dishSvc.querytext(dish_cont);
			// String[] query = list.toArray();

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			// Object[] resultArray = list.toArray();
			// req.setAttribute("resultArray", resultArray); //
			// 資料庫取出的resultArray[],存入req
			req.setAttribute("list", list); // 資料庫取出的list物件,存入req
			// req.setAttribute("dishVO", dishVO); // 資料庫取出的dishVO物件,存入req

			// String url = "/front/dish/listQueryResult.jsp";
			// RequestDispatcher successView = req.getRequestDispatcher(url); //
			// 成功轉交
			// successView.forward(req, res);

			JSONArray arrayObj = new JSONArray();

			// String query = req.getParameter("term");
			// System.out.println(query);
			// query = query.toLowerCase();

			for (DishVO dishVO : list) {
				if (dishVO.getDish_name().startsWith(dish_cont)) {
					// out.print(java.net.URLEncoder.encode(dishVO.getDish_name(),"UTF-8")+"\n");
					arrayObj.add(java.net.URLEncoder.encode(
							dishVO.getDish_name(), "UTF-8")); // 處理編碼問題 最後一招
				}
			}
			PrintWriter out = res.getWriter();
			out.println(arrayObj.toString());
			out.close();

		}		
	}
	 		 public String getFileNameFromPart(Part part) {
			 String header = part.getHeader("content-disposition"); //			 從前面第一個範例(版本1-基本測試)可得知此head的值
			 String filename = header.substring(header.lastIndexOf("=") + 2,
			 header.length() - 1);
			 if (filename.length() == 0) {
			 return null;
			 }
			 return filename;
			 }
}
