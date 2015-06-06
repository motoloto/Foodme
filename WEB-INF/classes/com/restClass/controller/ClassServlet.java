package com.restClass.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.restClass.model.*;


public class ClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ClassServlet() {
        super();
     
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getAll".equals(action)) { // 來自class.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {				
				/***************************2.開始查詢資料*****************************************/
				ClassService classSvc = new ClassService();//利用service呼叫DAO method step 3
				List<ClassVO> classVOList = classSvc.getAll();
				if (classVOList == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req//利用RequestDispatcher介面將請求退回
							.getRequestDispatcher("/back/class/class.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("classVOList", classVOList); // 資料庫取出的classVO物件,存入req//step 5
				/*轉成JSON給手機*/
				if("mobile".equals(req.getParameter("device"))){
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/plain");
				PrintWriter outForMobile = res.getWriter();
				outForMobile.write(new Gson().toJson(classVOList));
				outForMobile.flush();
				outForMobile.close();
				}
//				String url = "/class/listOneEmp.jsp";//step 6
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//				successView.forward(req, res);//step 7

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/class/class.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		
		
		if ("update_list".equals(action)) { // 來自listAllClass.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer class_no = new Integer(req.getParameter("class_no"));//step 1
				
				/***************************2.開始查詢資料****************************************/
				ClassService classSvc = new ClassService();
				ClassVO classVO = classSvc.getOneEmp(class_no);
												
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("classVO", classVO);         // 資料庫取出的classVO物件,存入req
				String url = "/back/class/update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/class/be_classMgr.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		
		
		if ("update".equals(action)) { // 來自update.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				Integer class_no = new Integer(req.getParameter("class_no").trim());
//				String class_name = req.getParameter("class_name").trim();
				
				Integer class_no = new Integer(req.getParameter("class_no"));			
				String class_name=req.getParameter("class_name");
				
				/***************************2.開始修改資料*****************************************/
				ClassService classSvc = new ClassService();
				ClassVO classVO=new ClassVO();
				classVO = classSvc.updateClass(class_no, class_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("classVO", classVO); // 資料庫update成功後,正確的的ClassVO物件,存入req
				String url = "/back/class/be_classMgr.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/class/update.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String class_name = req.getParameter("class_name").trim();
				if (class_name == null || (class_name.trim()).length() == 0) 
				{
					errorMsgs.add("請輸入分類名稱");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty())
				{
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back/class/be_classMgr.jsp");//路徑從WebApp起算就好
					failureView.forward(req, res);
					return;//程式中斷
				}				
				// Send the use back to the form, if there were errors
				
				/***************************2.開始新增資料***************************************/
				ClassService classSvc = new ClassService();
				ClassVO classVO = new ClassVO();
				classVO = classSvc.addClass(class_name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back/class/be_classMgr.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllClass.jsp
				successView.forward(req, res);				
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/class/be_classMgr.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllClass.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer class_no = new Integer(req.getParameter("class_no"));
				
				/***************************2.開始刪除資料***************************************/
				ClassService classSvc = new ClassService();
				classSvc.deleteClass(class_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back/class/be_classMgr.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				return; // 程式中斷
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/class/be_classMgr.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
	}

}
