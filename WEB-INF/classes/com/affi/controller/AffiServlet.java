package com.affi.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.affi.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AffiServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("affi_no");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入加盟編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer affi_no = null;
			try {
				affi_no = new Integer(str);
			} catch (Exception e) {
				errorMsgs.add("員工編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			AffiService affiSvc = new AffiService();
			AffiVO affiVO = affiSvc.getOneAffi(affi_no);
			if (affiVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("affiVO", affiVO); // 資料庫取出的empVO物件,存入req
			String url = "/affi/listOneAffi.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���
																			// listOneAffi.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 *************************************/
			// } catch (Exception e) {
			// errorMsgs.add("無法取得資料:" + e.getMessage());
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/affi/select_page.jsp");
			// failureView.forward(req, res);
			// }
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllAffi.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer affi_no = new Integer(req.getParameter("affi_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				AffiService affiSvc = new AffiService();
				AffiVO affiVO = affiSvc.getOneAffi(affi_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				HttpSession session = req.getSession();
				session.setAttribute("affiVO", affiVO); // 資料庫取出的affiVO物件,存入req
														// ??
				String url = "/affi/update_affi_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_affi_input.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/listAllAffi.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

		if ("update".equals(action)) { // 來自update_affi_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer affi_no = new Integer(req.getParameter("affi_no").trim());
			String bus_no = req.getParameter("bus_no").trim();
			String rest_name = req.getParameter("rest_name").trim();
			String rest_addr = req.getParameter("rest_addr").trim();
			String rest_tel = req.getParameter("rest_tel").trim();
			String rest_mobil = req.getParameter("rest_mobil").trim();
			String rest_mail = req.getParameter("rest_mail").trim();
			String rest_web = req.getParameter("rest_web").trim();
			String rest_intro = req.getParameter("rest_intro").trim();
			String affi_state = req.getParameter("affi_state").trim();

			AffiVO affiVO = new AffiVO();
			affiVO.setAffi_no(affi_no);
			affiVO.setBus_no(bus_no);
			affiVO.setRest_name(rest_name);
			affiVO.setRest_addr(rest_addr);
			affiVO.setRest_tel(rest_tel);
			affiVO.setRest_mobil(rest_mobil);
			affiVO.setRest_mail(rest_mail);
			affiVO.setRest_web(rest_web);
			affiVO.setRest_intro(rest_intro);
			affiVO.setAffi_state(affi_state);

			byte[] rest_photo = null;
			// System.out.println(act_photo);

			Part part = req.getPart("rest_photo");
			if (getFileNameFromPart(part) != null) {
				if (part.getContentType().startsWith("image")) {
					InputStream in = part.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] read = new byte[4 * 1024];
					int len = 0;
					while ((len = in.read(read)) != -1) {
						out.write(read, 0, len);
					}
					rest_photo = out.toByteArray();
					affiVO.setRest_photo(rest_photo);
					out.flush();
					in.close();
					out.close();
				}
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("affiVO", affiVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/update_affi_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			AffiService affiSvc = new AffiService();
			if (affiVO.getRest_photo() == null) {
				affiVO = affiSvc.updateAffiNoImg(affi_no, bus_no, rest_name,
						rest_addr, rest_tel, rest_mobil, rest_mail, rest_web,
						rest_intro, affi_state);
			} else {
				affiVO = affiSvc.updateAffi(affi_no, bus_no, rest_name,
						rest_addr, rest_tel, rest_mobil, rest_photo, rest_mail,
						rest_web, rest_intro, affi_state);
			}

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("affiVO", affiVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/affi/listOneAffi.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAffi.jsp
			successView.forward(req, res);
			return; // 程式中斷
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

			// try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			// MultipartRequest multi =
			// new MultipartRequest(req,
			// getServletContext().getRealPath("images") , 5 * 1024 * 1024,
			// "Big5");

			// Enumeration params = multi.getParameterNames();
			// while (params.hasMoreElements()) {
			// String name = (String)params.nextElement();
			// String value = multi.getParameter(name);
			// }

			String bus_no = null;

			try {
				bus_no = req.getParameter("bus_no");
				if (bus_no == null || (bus_no.trim()).length() == 0) {
					errorMsgs.add("請輸入營利事業統一編號(不得為空)");
				}
				if ((bus_no.trim()).length() <= 7||(bus_no.trim()).length() >= 9) {
					errorMsgs.add("請輸入正確格式(8位數 ex:06795431) 營利事業統一編號");
				}
				
				
			} catch (NumberFormatException e) {
				bus_no = "0";
				errorMsgs.add("請輸入正確格式 (8位數 ex:ex:06795431) 營利事業統一編號");
			}

			String rest_name = req.getParameter("rest_name").trim();
			if (rest_name == null || (rest_name.trim()).length() == 0) {
				errorMsgs.add("請輸入餐廳名稱(不得為空)");
			}
			String rest_addr = req.getParameter("rest_addr").trim();
			if (rest_addr == null || (rest_addr.trim()).length() == 0) {
				errorMsgs.add("請輸入餐廳地址(不得為空)");
			}
			String rest_tel = req.getParameter("rest_tel").trim();
			if (rest_tel == null || (rest_tel.trim()).length() == 0) {
				errorMsgs.add("請輸入餐廳電話址(不得為空)");
			}
			if ((rest_tel.trim()).length() <= 10) {
				errorMsgs.add("請輸入正確格式 (0x-xxxxxxxx)電話號碼");
			}

			String rest_mobil = req.getParameter("rest_mobil").trim();
			if (rest_mobil == null || (rest_mobil.trim()).length() == 0) {
				errorMsgs.add("請輸入手機號碼(不得為空)");
			}
			if ((rest_mobil.trim()).length() <= 10) {
				errorMsgs.add("請輸入正確格式 (09xx-xxxxxx)手機號碼");
			}

			String rest_mail = req.getParameter("rest_mail").trim();
			String rest_web = req.getParameter("rest_web").trim();
			String rest_intro = req.getParameter("rest_intro").trim();
			if (rest_intro == null || (rest_intro.trim()).length() == 0) {
				errorMsgs.add("請輸入餐廳介紹(不得為空)");
			}
			String affi_state ="0";
			byte[] rest_photo = null;

			AffiVO affiVO = new AffiVO();

			affiVO.setBus_no(bus_no);
			affiVO.setRest_name(rest_name);
			affiVO.setRest_addr(rest_addr);
			affiVO.setRest_tel(rest_tel);
			affiVO.setRest_mobil(rest_mobil);
			affiVO.setRest_mail(rest_mail);
			affiVO.setRest_web(rest_web);
			affiVO.setRest_intro(rest_intro);
			affiVO.setAffi_state(affi_state);

			Part part = req.getPart("rest_photo");
			
			String fileName = getFileNameFromPart(part);
			if(fileName==null){
				errorMsgs.add("請選擇上傳之餐廳圖片(不得為空)");			
			}
			
			InputStream in = part.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] read = new byte[4 * 1024];
			int len = 0;
			while ((len = in.read(read)) != -1) {
				out.write(read, 0, len);
			}
			rest_photo = out.toByteArray();

			out.flush();
			in.close();
			out.close();

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("affiVO", affiVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/affi/addAffi.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			try{
			AffiService affiSvc = new AffiService();
			affiVO = affiSvc.addAffi(bus_no, rest_name, rest_addr, rest_tel,
					rest_mobil, rest_photo, rest_mail, rest_web, rest_intro,
					affi_state);
			}catch(Exception e){
				errorMsgs.add("此資料已被註冊過");
				// Send the use back to the form, if there were errors
					req.setAttribute("affiVO", affiVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front/affi/addAffi.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
			}
			
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/front/affi/AffiappliOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAffi.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/
			// } catch (Exception e) {
			// errorMsgs.add(e.getMessage());
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/affi/addAffi.jsp");
			// failureView.forward(req, res);
			// }
		}

		if ("delete".equals(action)) { // 來自listAllAffi.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer affi_no = new Integer(req.getParameter("affi_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				AffiService affiSvc = new AffiService();
				affiSvc.deleteAffi(affi_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/affi/listAllAffi.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/affi/listAllAffi.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
	}

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
