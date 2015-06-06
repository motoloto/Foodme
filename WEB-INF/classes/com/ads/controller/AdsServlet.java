package com.ads.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ads.model.AdsService;
import com.ads.model.AdsVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");


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
		// 來自update_emp_input.jsp的請求
		List<String> errorMsgs = new LinkedList<String>();
		// get the VO which i want to update
		AdsVO adsVO = (AdsVO) req.getSession().getAttribute("adsVO");
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		Integer ads_no = 0;
		Integer rest_no = 0;
		String ads_title = null;
		byte[] ads_pic = null;
		java.sql.Date ads_dl = null;
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			String adsNostr = req.getParameter("ads_no").trim();
			if (adsNostr.isEmpty()) {
				errorMsgs.add("廣告編號不得為空");
			} else {
				try {
					ads_no = Integer.parseInt(adsNostr);
				} catch (NumberFormatException e) {
					errorMsgs.add("廣告編號格式錯誤");
				}
			}
			String restNostr = req.getParameter("rest_no").trim();
			if (restNostr.isEmpty()) {
				errorMsgs.add("餐廳編號不得為空");
			} else {
				try {
					rest_no = Integer.parseInt(restNostr);
				} catch (NumberFormatException e) {
					errorMsgs.add("餐廳編號格式錯誤");
				}
			}

			ads_title = req.getParameter("ads_title").trim();
			if (ads_title.isEmpty()) {
				errorMsgs.add("標題不可空白");
			}

			try {
				ads_dl = java.sql.Date.valueOf(req.getParameter("ads_dl")
						.trim());
			} catch (Exception e) {
				errorMsgs.add("日期錯誤");
			}
			try {
				Collection<Part> parts = req.getParts();
				Part part = req.getPart("ads_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out.write(read, 0, len);
						}
						ads_pic = out.toByteArray();
						adsVO.setAds_pic(ads_pic);
						out.flush();
						in.close();
						out.close();

					} else {
						errorMsgs.add("圖片格式錯誤");
					}

				} else {
					/****
					 * if there is no new image, get the old one to update
					 ****/
					ads_pic = adsVO.getAds_pic();
				}

			} catch (Exception e) {
				errorMsgs.add("圖片不得為空");
			}

			adsVO.setAds_no(ads_no);
			adsVO.setRest_no(rest_no);
			adsVO.setAds_title(ads_title);
			adsVO.setAds_dl(ads_dl);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adsVO", adsVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/ads/updateAds.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** update 2.開始修改資料 *****************************************/
			AdsService adsSvc = new AdsService();
			adsSvc.updateAds(adsVO);

			/*************************** update 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("adsVO", adsVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/back/ads/be_adsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** update 其他可能的錯誤處理 *************************************/
		} catch (ServletException | IOException e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/ads/updateAds.jsp");
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
			String str = req.getParameter("ads_no");
			Integer ads_no = Integer.parseInt(str);
			/*************************** get one for update 2.開始查詢資料 *****************************************/
			AdsService adsSrv = new AdsService();
			AdsVO adsVO = new AdsVO();
			adsVO = adsSrv.getOneAds(ads_no);

			/*************************** get one for update 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("adsVO", adsVO);

			String url = "/back/ads/updateAds.jsp";
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
					.getRequestDispatcher("/back/ads/be_adsMgr.jsp");
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

	private void insert(HttpServletRequest req, HttpServletResponse res)
			 {

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			AdsVO adsVO = new AdsVO();
			Integer ads_no = 0;
			Integer rest_no = 0;
			String ads_title = null;
			byte[] ads_pic = null;
			java.sql.Date ads_dl = null;
			req.setAttribute("errorMsgs", errorMsgs);

			
			String restNostr = req.getParameter("rest_no").trim();
			if (restNostr.isEmpty()) {
				errorMsgs.add("餐廳編號不得為空");
			} else {
				try {
					rest_no = Integer.parseInt(restNostr);
				} catch (NumberFormatException e) {
					errorMsgs.add("餐廳編號格式錯誤");
				}
			}

			ads_title = req.getParameter("ads_title").trim();
			if (ads_title.isEmpty()) {
				errorMsgs.add("標題不可空白");
			}

			try {
				ads_dl = java.sql.Date.valueOf(req.getParameter("ads_dl")
						.trim());
			} catch (Exception e) {
				errorMsgs.add("日期錯誤");
			}
			try {
				Collection<Part> parts = req.getParts();
				Part part = req.getPart("ads_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out.write(read, 0, len);
						}
						ads_pic = out.toByteArray();

						out.flush();
						in.close();
						out.close();

					} else {
						errorMsgs.add("圖片格式錯誤");
					}

				} else {
					errorMsgs.add("圖片不得為空");
				}

			} catch (Exception e) {
				errorMsgs.add("無法新增圖片");
			}

			adsVO.setAds_no(ads_no);
			adsVO.setRest_no(rest_no);
			adsVO.setAds_title(ads_title);
			adsVO.setAds_dl(ads_dl);
			adsVO.setAds_pic(ads_pic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adsVO", adsVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/ads/addAds.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			AdsService adsSvc = new AdsService();
			adsVO = adsSvc.addAds(rest_no, ads_title, ads_pic, ads_dl);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back/ads/be_adsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (ServletException | IOException e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/ads/be_adsMgr.jsp");
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
		try {
			int ads_no = Integer.parseInt(req.getParameter("ads_no"));
			AdsService adsSrv = new AdsService();
			adsSrv.delete(ads_no);
			String url = "/back/ads/be_adsMgr.jsp";
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
