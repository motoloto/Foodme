package com.act.controller;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.act.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ActServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	/*取得系統時間*/
	public static String getDateTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis() );
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("act_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/act/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer act_no = null;
				try {
					act_no = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/act/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ActService actSvc = new ActService();
				ActVO actVO = actSvc.getOneAct(act_no);
				if (actVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/act/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("actVO", actVO); // 資料庫取出的actVO物件,存入req
				String url = "/store/act/listOneAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneAct.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺��~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k��o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllAct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer act_no = new Integer(req.getParameter("act_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				ActService actSvc = new ActService();
				ActVO actVO = actSvc.getOneAct(act_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				HttpSession session = req.getSession();
				req.setAttribute("actVO", actVO); // 資料庫取出的actVO物件,存入req ??
				String url = "/store/act/updateAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_act_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/updateAct.jsp");
				failureView.forward(req, res);
			}
		}
		if ("listALLCopByRest_no".equals(action)) { // 來自listAllAct.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer rest_no = new Integer(req.getParameter("rest_no"));
				
				/*************************** 2.開始查詢資料 ****************************************/
				ActService actSvc = new ActService();
				List<ActVO> list= actSvc.getActOfRest(rest_no);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				HttpSession session = req.getSession();
				session.setAttribute("actListOfRest", list); // 資料庫取出的actVO物件,存入req ??
				String url = "/store/act/checkAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
				// update_act_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/checkAct.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_act_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			Integer act_no = new Integer(req.getParameter("act_no").trim());
			Integer rest_no = new Integer(req.getParameter("rest_no").trim());
			String act_name = req.getParameter("act_name").trim();
			String act_cont = req.getParameter("act_cont").trim();
			String act_time = req.getParameter("act_time").trim();
			String act_state = req.getParameter("act_state").trim();
			// byte[] act_photo = new Byte[](req.getParameter("act_photo"));
			// byte[] act_photo = new
			// ByteArrayInputStream(req.getParameter("act_photo"));

			// InputStream in = new ByteArrayInputStream(actVO.getAct_photo());
			// byte[] act_photo = null;
			
			if (act_name == null || (act_name.trim()).length() == 0) {
				errorMsgs.add("請輸入活動名稱");
			}
			if (act_cont == null || (act_cont.trim()).length() == 0) {
				errorMsgs.add("請輸入活動內容");
			}
			if (act_time == null || (act_time.trim()).length() <21) {
				errorMsgs.add("請輸入活動時間");
				
				act_time = getDateTime()+"-"+getDateTime();
			}

			ActVO actVO = new ActVO();
			actVO.setAct_no(act_no);
			actVO.setRest_no(rest_no);
			actVO.setAct_name(act_name);
			actVO.setAct_cont(act_cont);
			actVO.setAct_time(act_time);
			actVO.setAct_state(act_state);

			byte[] act_photo = null;
			// System.out.println(act_photo);

//			if (act_photo != null) {
//				Part part = req.getPart("act_photo");
//				if (getFileNameFromPart(part) != null) {
//					if (part.getContentType().startsWith("image")) {
//						InputStream in = part.getInputStream();
//						ByteArrayOutputStream out = new ByteArrayOutputStream();
//						byte[] read = new byte[4 * 1024];
//						int len = 0;
//						while ((len = in.read(read)) != -1) {
//							out.write(read, 0, len);
//						}
//						act_photo = out.toByteArray();
//						actVO.setAct_photo(act_photo);
//						out.flush();
//						in.close();
//						out.close();
//					}
//				}
//			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("actVO", actVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/updateAct.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			ActService actSvc = new ActService();
			//System.out.println(act_photo);
			
				actVO = actSvc.updateAct(act_no, rest_no, act_name, act_cont,
						act_time, act_state, act_photo);
				
				List<ActVO> list= actSvc.getActOfRest(rest_no);
				HttpSession session = req.getSession();
				session.setAttribute("actListOfRest", list); 
//			}else{
//			actVO = actSvc.updateAct(act_no, rest_no, act_name, act_cont,
//					act_time, act_state, act_photo);
//			}

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("actVO", actVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/store/act/checkAct.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAct.jsp
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 *************************************/
			// } catch (Exception e) {
			// errorMsgs.add("修改資料失敗:"+e.getMessage());
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/act/update_act_input.jsp");
			// failureView.forward(req, res);
			// }
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer rest_no = new Integer(req.getParameter("rest_no")
						.trim());
				String act_name = req.getParameter("act_name").trim();
				String act_cont = req.getParameter("act_cont").trim();
				String act_time = req.getParameter("act_time").trim();
				String act_state = req.getParameter("act_state").trim();
				byte[] act_photo = null;
				
				if (act_name == null || (act_name.trim()).length() == 0) {
					errorMsgs.add("請輸入活動名稱");
				}
				if (act_cont == null || (act_cont.trim()).length() == 0) {
					errorMsgs.add("請輸入活動內容");
				}
				if (act_time == null || (act_time.trim()).length() <21) {
					errorMsgs.add("請輸入活動時間");
					
					act_time = getDateTime()+"-"+getDateTime();
				}
					

				ActVO actVO = new ActVO();

				actVO.setRest_no(rest_no);
				actVO.setAct_name(act_name);
				actVO.setAct_cont(act_cont);
				actVO.setAct_time(act_time);
				actVO.setAct_state(act_state);

				
				/*圖片上傳程式碼*/
//				Part part = req.getPart("act_photo");
//
//				InputStream in = part.getInputStream();
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				byte[] read = new byte[4 * 1024];
//				int len = 0;
//				while ((len = in.read(read)) != -1) {
//					out.write(read, 0, len);
//				}
//				act_photo = out.toByteArray();
//
//				out.flush();
//				in.close();
//				out.close();

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("actVO", actVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store/act/addAct.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ActService actSvc = new ActService();
				actVO = actSvc.addAct(rest_no, act_name, act_cont, act_time,
						act_state, act_photo);
				List<ActVO> list= actSvc.getActOfRest(rest_no);
				HttpSession session = req.getSession();
				session.setAttribute("actListOfRest", list); 

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/store/act/checkAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAct.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/addAct.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllAct.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer act_no = new Integer(req.getParameter("act_no"));
				Integer rest_no = new Integer(req.getParameter("rest_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				ActService actSvc = new ActService();
				actSvc.deleteAct(act_no);
				List<ActVO> list= actSvc.getActOfRest(rest_no);
				HttpSession session = req.getSession();
				session.setAttribute("actListOfRest", list); 

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/store/act/checkAct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store/act/checkAct.jsp");
				failureView.forward(req, res);
			}
		}
	}

	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition"); // 從前面第一個範例(版本1-基本測試)可得知此head的值
		String filename = header.substring(header.lastIndexOf("=") + 2,
				header.length() - 1);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
