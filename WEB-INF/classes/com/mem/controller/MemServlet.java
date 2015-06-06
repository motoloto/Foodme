package com.mem.controller;

import java.io.*;
import java.util.*;

import com.mem.model.*;

import java.io.ByteArrayOutputStream;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemServlet extends HttpServlet {

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition"); // 從前面第一個範例(版本1-基本測試)可得知此head的值
		String filename = header.substring(header.lastIndexOf("=") + 2,
				header.length() - 1);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");

		switch (req.getParameter("action")) {
		case "phoneUpdate_mem":
			phoneUpdate_mem(req, res);
			break;
		case "checkaccountIsSame":
			checkaccountIsSame(req, res);
			break;
		case "delete":
			delete(req, res);
			break;
		case "insert":
			insertMember(req, res);
			break;
		case "update":
			updateMember(req, res);
			break;
		case "getOne_For_Update":
			getOneMember_For_Update(req, res);
			break;
		case "getOne_For_Display":
			getOneMember_For_Display(req, res);
			break;
		case "phoneShow_mem":
			getOneMember_For_Display_By_Phone(req, res);
			break;
		case "UpdateMem_Pwd":
			UpdateMem_Pwd(req, res);
			break;
		}


	}

	private void UpdateMem_Pwd(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
	try{
		String mem_pwd = req.getParameter("mem_pwd"); // 
		String new_pwd1 = req.getParameter("new_pwd1"); // 
		String new_pwd2 = req.getParameter("new_pwd2"); // 
		String mem_account = req.getParameter("mem_account"); // 
		MemVO memVO = null;
		MemService memSvc = new MemService();
		memVO = memSvc.getOneMem(mem_account);
		if(mem_pwd.equals(memVO.getMem_pwd())){
			
			 if(new_pwd1.equals(new_pwd2)){
				 
				 if(new_pwd1.matches("[0-9a-zA-Z]*") == true || mem_pwd.length()>=8){
					 memVO=memSvc.updateMem(memVO.getMem_no(),memVO.getMem_account(), new_pwd1, memVO.getMem_name(), memVO.getMem_mail(), memVO.getMem_phone(), memVO.getMem_adrs(), memVO.getMem_birthdate(), memVO.getMem_sex(), memVO.getMem_nickname(), memVO.getMem_illtms(), memVO.getMem_pic());

						
				 }else{
					 errorMsgs.add("密碼至少需要8個字元，並為英數字混合");
				 }
				 
			 }else{
				 errorMsgs.add("新密碼輸入不一樣");
			 }
			
		}else{
			errorMsgs.add("舊密碼輸入錯誤");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front/member/updateMemPwd.jsp");
			failureView.forward(req, res);
			return;
		}
		HttpSession session=req.getSession();
		session.setAttribute("memVO", memVO);
		RequestDispatcher failureView = req
				.getRequestDispatcher("/front/member/centerMem.jsp");
		failureView.forward(req, res);
		return; // 程式中斷
		
	}
	catch(Exception e) {
		errorMsgs.add(e.getMessage());
		RequestDispatcher failureView = req
				.getRequestDispatcher("/front/member/updateMemPwd.jsp");
		try {
			failureView.forward(req, res);
			return; // 程式中斷
		} catch (ServletException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	}

	private void getOneMember_For_Display_By_Phone(HttpServletRequest req,
			HttpServletResponse res) {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");

		String user = req.getParameter("mem_account");

		String showmember = "";
		Gson gson = new Gson();
		MemService memberDAO = new MemService();
		MemVO member = memberDAO.getOneMem(user);

		member.setMem_birthdate(null);
		showmember = gson.toJson(member);

		res.setContentType("UTF-8");
		try {
			PrintWriter outForMobile = res.getWriter();
			outForMobile.write(showmember);
			outForMobile.flush();
			outForMobile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void getOneMember_For_Display(HttpServletRequest req,
			HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*************************** 1.�����ШD�Ѽ� - ��J�榡����~�B�z **********************/
			String str = req.getParameter("empno");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("�п�J��u�s��");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// �{�����_
			}

			String mem_account = null;
			try {
				mem_account = new String(str);
			} catch (Exception e) {
				errorMsgs.add("��u�s���榡�����T");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// �{�����_
			}

			/*************************** 2.�}�l�d�߸�� *****************************************/
			MemService memSvc = new MemService();
			MemVO MemVO = memSvc.getOneMem(mem_account);
			if (MemVO == null) {
				errorMsgs.add("�d�L���");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// �{�����_
			}

			/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
			req.setAttribute("MemVO", MemVO); // ��Ʈw��X��empVO����,�s�Jreq
			String url = "/emp/listOneMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���
																			// listOneMem.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** ��L�i�઺��~�B�z *************************************/
		} catch (Exception e) {
			errorMsgs.add("�L�k��o���:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/select_page.jsp");
			try {
				failureView.forward(req, res);
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void updateMember(HttpServletRequest req, HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		HttpSession session = req.getSession();
		MemVO memVO = (MemVO)session.getAttribute("memVO"); 
	
		try {
			/***********************1.�����ШD�Ѽ� - ��J�榡����~�B�z*************************/
//			 
			
			
			Integer mem_no = memVO.getMem_no();
			String mem_account = memVO.getMem_account();
			String mem_pwd = memVO.getMem_pwd();
			String mem_name = req.getParameter("mem_name").trim();
			String mem_nickname = req.getParameter("mem_nickname").trim();
			String mem_mail = req.getParameter("mem_mail").trim();
			String mem_phone = req.getParameter("mem_phone").trim();
			String mem_adrs = req.getParameter("mem_adrs").trim();
			String mem_sex = req.getParameter("mem_sex");
			Integer mem_illtms =  memVO.getMem_illtms();
			byte[] mem_pic = null;   //存放會員圖片

                /*Servlet3 上傳圖片*/
			try {
				Collection<Part> parts = req.getParts();
				Part part = req.getPart("mem_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out1 = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out1.write(read, 0, len);
						}
						mem_pic = out1.toByteArray();
						memVO.setMem_pic(mem_pic);
						out1.flush();
						in.close();
						out1.close();

					} else {
						errorMsgs.add("圖片格式錯誤");
					}

				} else {
					/****
					 * if there is no new image, get the old one to update
					 ****/
					mem_pic = memVO.getMem_pic();
				}

			} catch (Exception e) {
				errorMsgs.add("圖片不得為空");
			}
			
			if (mem_name == null || (mem_name.trim()).length() == 0) {
				errorMsgs.add("請輸入姓名");
			}
			
			if (mem_nickname == null || (mem_nickname.trim()).length() == 0) {
				mem_nickname= mem_name;
			}
			
			if (mem_mail == null || (mem_mail.trim()).length() == 0) {
				errorMsgs.add("請輸入信箱");				
			}
			
			Integer phone = null;
			try {
				phone = Integer.parseInt(req.getParameter("mem_phone").trim());
                if (mem_phone == null || (mem_phone.trim()).length() <10) {
					errorMsgs.add("請輸入正確的電話號碼");
				}
			} catch (NumberFormatException e) {
				
				errorMsgs.add("電話請填數字");
			}
			
			if (mem_adrs == null || (mem_adrs.trim()).length() == 0) {
				errorMsgs.add("請輸入地址");
			}
			
			if (mem_sex == null) {
				mem_sex="3";//既不是男也不是女，避免jsp取到空值
				errorMsgs.add("請選擇性別");
			}

			java.sql.Date mem_birthdate = null;
			try {
				mem_birthdate = java.sql.Date.valueOf(req.getParameter("mem_birthdate").trim());
			} catch (IllegalArgumentException e) {
				mem_birthdate=new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入生日");
			}

			memVO.setMem_no(memVO.getMem_no());
			memVO.setMem_account(memVO.getMem_account());
			memVO.setMem_pwd(memVO.getMem_pwd());
			memVO.setMem_name(mem_name);
			memVO.setMem_mail(mem_mail);
			memVO.setMem_phone(mem_phone);
			memVO.setMem_adrs(mem_adrs);
			memVO.setMem_birthdate(mem_birthdate);
			memVO.setMem_sex(mem_sex);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_illtms(memVO.getMem_illtms());
			session.setAttribute("memVO", memVO);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/member/updateMem.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.�}�l�s�W���***************************************/
			MemService memSvc = new MemService();
			memVO = memSvc.updateMem(mem_no,mem_account,mem_pwd,mem_name, mem_mail, mem_phone, mem_adrs, mem_birthdate,mem_sex, mem_nickname,mem_illtms,mem_pic);
			
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			session.setAttribute("memVO", memVO);  
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front/member/centerMem.jsp");
			failureView.forward(req, res);
			return; // 程式中斷
			/***************************��L�i�઺��~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front/member/updateMem.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void getOneMember_For_Update(HttpServletRequest req,
			HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
			/***************************1.�����ШD�Ѽ�****************************************/
			
			
			/***************************2.�}�l�d�߸��****************************************/
//			MemService empSvc = new MemService();
//			MemVO MemVO = empSvc.getOneMem(mem_account);
							
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
//			req.setAttribute("MemVO",memVO);         // ��Ʈw��X��empVO����,�s�Jreq
			String url = "/front/member/updateMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/***************************��L�i�઺��~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add("�L�k��o�n�ק諸���:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/listAllMem.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void insertMember(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		HttpSession session=req.getSession();

		try {
			/***********************1.�����ШD�Ѽ� - ��J�榡����~�B�z*************************/
			String mem_clause = req.getParameter("mem_clause");
			
			String mem_account = req.getParameter("mem_account").trim();
			String mem_pwd = req.getParameter("mem_pwd").trim();
			String mem_name = req.getParameter("mem_name").trim();
			String mem_nickname = req.getParameter("mem_nickname").trim();
			String mem_mail = req.getParameter("mem_mail").trim();
			String mem_phone = req.getParameter("mem_phone").trim();;
			String mem_adrs = req.getParameter("mem_adrs").trim();
			String mem_sex = req.getParameter("mem_sex");
			byte[] mem_pic = null;//用來存放圖片
			/*  Servlet3 上傳圖片*/
			try {
				Collection<Part> parts = req.getParts();
				Part part = req.getPart("mem_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out1 = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out1.write(read, 0, len);
						}
						mem_pic = out1.toByteArray();

						out1.flush();
						in.close();
						out1.close();

					} else {
						errorMsgs.add("圖片格式錯誤");
					}

				} else {
					errorMsgs.add("圖片不得為空");
				}

			} catch (Exception e) {
				errorMsgs.add("無法新增圖片");
				errorMsgs.add(e.getMessage());
			}
			
			
			if (mem_account == null || (mem_account.trim()).length() == 0) {
				errorMsgs.add("請輸入帳號");
			}else{
				MemVO memVO1 = null;
				MemService memSvc = new MemService();
				memVO1 = memSvc.getOneMem(mem_account);
				if(memVO1 != null){
					errorMsgs.add("此帳號已有人註冊");
				}
			}
			
			if (mem_pwd == null || (mem_pwd.trim()).length() == 0) {
				errorMsgs.add("請輸入密碼");
			}else if(mem_pwd.matches("[0-9a-zA-Z]*") == false || mem_pwd.length()<8) {
				errorMsgs.add("密碼至少需要8個字元，並為英數字混合");
			}
			
			if (mem_name == null || (mem_name.trim()).length() == 0) {
				errorMsgs.add("請輸入姓名");
			}
			
			if (mem_nickname == null || (mem_nickname.trim()).length() == 0) {
				mem_nickname= mem_name;
			}
			
			if (mem_mail == null || (mem_mail.trim()).length() == 0) {
				errorMsgs.add("請輸入信箱");				
			}
			
			Integer phone = null;
			try {
				phone = Integer.parseInt(req.getParameter("mem_phone").trim());
                if (mem_phone == null || (mem_phone.trim()).length() <10) {
					errorMsgs.add("請輸入正確的電話號碼");
				}
			} catch (NumberFormatException e) {
				
				errorMsgs.add("電話請填數字");
			}
			
			if (mem_adrs == null || (mem_adrs.trim()).length() == 0) {
				errorMsgs.add("請輸入地址");
			}
			
			if (mem_sex == null) {
				mem_sex="3";//既不是男也不是女，避免jsp取到空值
				errorMsgs.add("請選擇性別");
			}
			
			if (mem_clause == null) {
				errorMsgs.add("是否同意會員條款");
				
			}
			/*如果不同意條款，跳回前端首頁*/
			if(mem_clause.equals("disagree")){
				String url = req.getContextPath()+"/front/index.jsp";
				res.sendRedirect(url);	
				return;
			}
			  
			
			
			java.sql.Date mem_birthdate = null;
			try {
				mem_birthdate = java.sql.Date.valueOf(req.getParameter("mem_birthdate").trim());
			} catch (IllegalArgumentException e) {
				mem_birthdate=new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入生日");
			}

			
			Integer mem_illtms = 0;

			MemVO memVO = new MemVO();
			memVO.setMem_account(mem_account);
			memVO.setMem_pwd(mem_pwd);
			memVO.setMem_name(mem_name);
			memVO.setMem_mail(mem_mail);
			memVO.setMem_phone(mem_phone);
			memVO.setMem_adrs(mem_adrs);
			memVO.setMem_birthdate(mem_birthdate);
			memVO.setMem_sex(mem_sex);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_illtms(mem_illtms);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memVO", memVO); // �t����J�榡��~��empVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/member/addMem.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.�}�l�s�W���***************************************/
			MemService memSvc = new MemService();
			memVO = memSvc.addMem(mem_account,mem_pwd,mem_name, mem_mail, mem_phone, mem_adrs, mem_birthdate,mem_sex, mem_nickname,mem_illtms,mem_pic);
			
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			MemVO memVO_new = memSvc.getOneMem(mem_account);
			if(memVO_new == null){
				session.setAttribute("memVO", memVO); 
			}else{
				session.setAttribute("memVO", memVO_new); 
			}
			/*寄發Email 程式碼*/
			MailServiceForAddMem mailSv = new MailServiceForAddMem();
			mailSv.sendPassword(mem_name, mem_mail);
			
			String location = (String) session.getAttribute("location");
		         if (location != null) {
//		           session.removeAttribute("location");
		           res.sendRedirect(location);            
		           return;
		         }else{
		        	 res.sendRedirect(req.getContextPath()+"/front/index.jsp");            
		        	 return;
		         }
			
			/***************************��L�i�઺��~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front/member/addMem.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*************************** 1.�����ШD�Ѽ� ***************************************/
			Integer mem_no = new Integer(req.getParameter("mem_no"));

			/*************************** 2.�}�l�R����� ***************************************/
			MemService memSvc = new MemService();
			memSvc.deleteMem(mem_no);

			/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
			String url = "/back/member/listAllMem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** ��L�i�઺��~�B�z **********************************/
		} catch (Exception e) {
			errorMsgs.add("�R����ƥ���:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/member/listAllMem.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void checkaccountIsSame(HttpServletRequest req,
			HttpServletResponse res) {

		String isUse = "";
		String mem_account = req.getParameter("mem_account"); //
		MemVO memVO = null;
		MemService memSvc = new MemService();
		memVO = memSvc.getOneMem(mem_account);
		System.out.println(memVO);
		if (memVO == null) {
			isUse += "此帳號可使用";
		} else {
			isUse += "此帳號已有人註冊";
		}
		System.out.println(isUse);
		String result = "{'result':" + isUse + "}";
		JSONObject resultJson = new JSONObject(result);
		res.setContentType("text/plain");
		try {
			PrintWriter out1 = res.getWriter();
			out1.write(resultJson.toString());
			out1.flush();
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void phoneUpdate_mem(HttpServletRequest req, HttpServletResponse res) {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");

		try {

			String mem_account = req.getParameter("mem_account").trim();
			String mem_name = req.getParameter("mem_name").trim();
			String mem_mail = req.getParameter("mem_mail").trim();
			String mem_phone = req.getParameter("mem_phone").trim();
			String mem_adrs = req.getParameter("mem_adrs").trim();
			String mem_passward = req.getParameter("mem_passward").trim();

			MemService member = new MemService();
			MemVO memVo = member.getOneMem(mem_account);

			memVo.setMem_name(mem_name);
			memVo.setMem_mail(mem_mail);
			memVo.setMem_phone(mem_phone);
			memVo.setMem_adrs(mem_adrs);
			memVo.setMem_pwd(mem_passward);

			MemVO updateCount = member.updateMem(memVo.getMem_no(),
					memVo.getMem_account(), memVo.getMem_pwd(),
					memVo.getMem_name(), memVo.getMem_mail(),
					memVo.getMem_phone(), memVo.getMem_adrs(),
					memVo.getMem_birthdate(), memVo.getMem_sex(),
					memVo.getMem_nickname(), memVo.getMem_illtms(),
					memVo.getMem_pic());
			//
			// String memberup = "";
			// Gson gson = new Gson();
			// MemVO memVoUp= member.getOneMem(mem_account);
			// memVoUp.setMem_birthdate(null);
			// memberup = gson.toJson(updateCount);
			//
			//
			res.setContentType(contentType);

			PrintWriter out = res.getWriter();
			// out.write(memberup);
			// out.flush();
			// out.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
