package com.banner.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.banner.model.BannerVO;
import com.banner.model.BannerService;


/**
 * Servlet implementation class BannerServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BannerServlet extends HttpServlet {
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
		//  request from updateBanner.jsp
				List<String> errorMsgs = new LinkedList<String>();
				// get the VO which i want to update
				BannerVO bannerVO = (BannerVO) req.getSession().getAttribute("bannerVO");
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				Integer banner_no = 0;
				Integer rest_no = 0;
				String banner_title = null;
				String banner_cont = null;
				byte[] banner_pic = null;
				java.sql.Date banner_dl = null;
				req.setAttribute("errorMsgs", errorMsgs);
				try {
					String bannerNostr = req.getParameter("banner_no").trim();
					if (bannerNostr.isEmpty()) {
						errorMsgs.add("廣告編號不得為空");
					} else {
						try {
							banner_no = Integer.parseInt(bannerNostr);
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

					banner_title = req.getParameter("banner_title").trim();
					if (banner_title.isEmpty()) {
						errorMsgs.add("標題不可空白");
					}
					banner_cont = req.getParameter("banner_cont").trim();
					if (banner_cont.isEmpty()) {
						errorMsgs.add("內容不可空白");
					}

					try {
						banner_dl = java.sql.Date.valueOf(req.getParameter("banner_dl")
								.trim());
					} catch (Exception e) {
						errorMsgs.add("日期錯誤");
					}
					try {

						Part part = req.getPart("banner_pic");
						if (getFileNameFromPart(part) != null) {
							if (part.getContentType().startsWith("image")) {
								InputStream in = part.getInputStream();
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								byte[] read = new byte[4 * 1024];
								int len = 0;
								while ((len = in.read(read)) != -1) {
									out.write(read, 0, len);
								}
								banner_pic = out.toByteArray();
								bannerVO.setBanner_pic(banner_pic);
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
							banner_pic = bannerVO.getBanner_pic();
						}

					} catch (Exception e) {
						errorMsgs.add("圖片不得為空");
					}

					bannerVO.setBanner_no(banner_no);
					bannerVO.setRest_no(rest_no);
					bannerVO.setBanner_title(banner_title);
					bannerVO.setBanner_cont(banner_cont);
					bannerVO.setBanner_dl(banner_dl);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("bannerVO", bannerVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back/banner/updateBanner.jsp");
						failureView.forward(req, res);
						return;
					}
			/*************************** update 2.開始修改資料 *****************************************/
			BannerService bannerSvc = new BannerService();
			bannerSvc.updateBanner(bannerVO);

			/*************************** update 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("bannerVO", bannerVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/back/banner/be_bannerMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** update 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/banner/updateBanner.jsp");
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
			String str = req.getParameter("banner_no");
			Integer banner_no = Integer.parseInt(str);
			/*************************** get one for update 2.開始查詢資料 *****************************************/
			BannerService bannerSrv = new BannerService();
			BannerVO bannerVO = new BannerVO();
			bannerVO = bannerSrv.getOneBannerVO(banner_no);

			/*************************** get one for update 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("bannerVO", bannerVO);

			String url = "/back/banner/updateBanner.jsp";
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
					.getRequestDispatcher("/back/banner/be_bannerMgr.jsp");
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
			BannerVO bannerVO = new BannerVO();
			Integer banner_no = 0;
			Integer rest_no = 0;
			String banner_title = null;
			String banner_cont= null;
			byte[] bannner_pic = null;
			java.sql.Date banner_dl = null;
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

			banner_title = req.getParameter("banner_title").trim();
			if (banner_title.isEmpty()) {
				errorMsgs.add("標題不可空白");
			}
			banner_cont = req.getParameter("banner_cont").trim();
			if (banner_cont.isEmpty()) {
				errorMsgs.add("內容不可空白");
			}

			try {
				banner_dl = java.sql.Date.valueOf(req.getParameter("banner_dl")
						.trim());
			} catch (Exception e) {
				errorMsgs.add("日期錯誤");
			}
			try {
				Collection<Part> parts = req.getParts();
				Part part = req.getPart("banner_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out.write(read, 0, len);
						}
						bannner_pic = out.toByteArray();

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

			bannerVO.setBanner_no(banner_no);
			bannerVO.setRest_no(rest_no);
			bannerVO.setBanner_title(banner_title);
			bannerVO.setBanner_cont(banner_cont);
			bannerVO.setBanner_dl(banner_dl);
			bannerVO.setBanner_pic(bannner_pic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("bannerVO", bannerVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/banner/addBanner.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			BannerService bannerSvc = new BannerService();
			bannerVO = bannerSvc.addBanner(rest_no, banner_title,banner_cont , bannner_pic, banner_dl);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back/banner/be_bannerMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/

		} catch (IllegalArgumentException e) {
			errorMsgs.add("請輸入日期!");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/banner/addBanner.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			errorMsgs.add("圖片格式錯誤!");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/banner/addBanner.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (ServletException e) {
			errorMsgs.add("無法新增");
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/banner/addBanner.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse res) {
		try {
			int banner_no = Integer.parseInt(req.getParameter("banner_no"));
			BannerService BannerSrv = new BannerService();
			BannerSrv.deleteBanner(banner_no);
			String url = "/back/banner/be_bannerMgr.jsp";
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
