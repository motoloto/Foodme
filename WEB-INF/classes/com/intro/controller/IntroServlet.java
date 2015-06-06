package com.intro.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

import org.apache.catalina.ant.FindLeaksTask;

import com.ads.model.AdsService;
import com.ads.model.AdsVO;
import com.intro.model.IntroService;
import com.intro.model.IntroVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class IntroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IntroServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "getIntroByARest":
			getIntroByARest(request, response);
			break;
		case "getOne_For_Update":
			findByPrimaryKey(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
		case "insert":
			insert(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "sortIntro":
			sortIntro(request, response);
			break;

		}
	}

	private void sortIntro(HttpServletRequest request,
			HttpServletResponse response) {
		Integer rest_no=Integer.parseInt(request.getParameter("rest_no"));
		String  intro_noList=request.getParameter("intro_no");
		IntroService introSrv=new IntroService();
		String[] intro_nos=intro_noList.split(",");
		int count=0;
		for(String intro_no:intro_nos){
			System.out.println( Integer.parseInt(intro_nos[count]));
			introSrv.updateOrder(rest_no, Integer.parseInt(intro_nos[count]),count+1);
			count++;
		}
		System.out.println(rest_no);
		System.out.println(intro_noList);
	}

	private void findByPrimaryKey(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		request.setAttribute("errorMsgs", errorMsgs);
		try {
			/*************************** get one for update 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = request.getParameter("intro_no");
			Integer intro_no = Integer.parseInt(str);
			/*************************** get one for update 2.開始查詢資料 *****************************************/
			IntroService introSrv = new IntroService();
			IntroVO introVO = new IntroVO();
			introVO = introSrv.getOneIntroduction(intro_no);

			/*************************** get one for update 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("introVO", introVO);

			String url = "/store/intro/updateIntro.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交
																			// listOneEmp.jsp
			successView.forward(request, response);
			return; // 程式中斷
			/*************************** get one for update 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
			// PrintWriter out=res.getWriter();
			// out.print("<script >window.alert(\"ERROR\");</script>");
			RequestDispatcher failureView = request
					.getRequestDispatcher("/store/intro/instroMgr.jsp");
			try {
				failureView.forward(request, response);
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

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// 來自update_emp_input.jsp的請求
				List<String> errorMsgs = new LinkedList<String>();
				IntroVO introVO =  (IntroVO) request.getSession().getAttribute("introVO");
				// get the VO which i want to update
				Integer rest_no =0;
				Integer intro_no =0;
				String intro_cont = null;
				byte[] intro_pic = null;

				request.setAttribute("errorMsgs", errorMsgs);
				try {
					String restNostr = request.getParameter("rest_no").trim();
					if (restNostr.isEmpty()) {
						errorMsgs.add("餐廳編號不得為空");
					} else {
						try {
							rest_no = Integer.parseInt(restNostr);
						} catch (NumberFormatException e) {
							errorMsgs.add("餐廳編號格式錯誤");
						}
					}
					String intro_noNostr = request.getParameter("intro_no").trim();
					if (intro_noNostr.isEmpty()) {
						errorMsgs.add("文章編號不得為空");
					} else {
						try {
							intro_no = Integer.parseInt(intro_noNostr);
						} catch (NumberFormatException e) {
							errorMsgs.add("文章編號格式錯誤");
						}
					}
					intro_cont = request.getParameter("intro_cont").trim();
					if (intro_cont.isEmpty()) {
						errorMsgs.add("文章內容不可空白");
					}

					try {
						Collection<Part> parts = request.getParts();
						Part part = request.getPart("intro_pic");
						if (getFileNameFromPart(part) != null) {
							if (part.getContentType().startsWith("image")) {
								InputStream in = part.getInputStream();
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								byte[] read = new byte[4 * 1024];
								int len = 0;
								while ((len = in.read(read)) != -1) {
									out.write(read, 0, len);
								}
								intro_pic = out.toByteArray();
								introVO.setIntro_pic(intro_pic);
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
							intro_pic = introVO.getIntro_pic();
						}

					} catch (Exception e) {
						errorMsgs.add("圖片不得為空");
					}

					introVO.setRest_no(rest_no);
					introVO.setIntro_no(intro_no);
					introVO.setIntro_pic(intro_pic);
					introVO.setIntro_cont(intro_cont);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						request.setAttribute("adsVO", introVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = request
								.getRequestDispatcher("/store/intro/updateIntro.jsp");
						failureView.forward(request, response);
						return;
					}

					/*************************** update 2.開始修改資料 *****************************************/
					IntroService introSvc = new IntroService();
					introSvc.updateIntroduction(introVO);
					/*************************** update 3.修改完成,準備轉交(Send the Success view) *************/
					request.setAttribute("adsVO", introVO); // 資料庫update成功後,正確的的empVO物件,存入req
					String url = "/store/intro/introMgr.jsp";
					RequestDispatcher successView = request.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(request, response);
					return; // 程式中斷
					/*************************** update 其他可能的錯誤處理 *************************************/
				} catch (ServletException | IOException e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
					RequestDispatcher failureView = request
							.getRequestDispatcher("/store/intro/introMgr.jsp");
					try {
						failureView.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		
	}

	private void insert(HttpServletRequest request, HttpServletResponse response) {
		List<String> errorMsgs = new LinkedList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			IntroVO introVO = new IntroVO();
			byte[] intro_pic = null;
			Integer rest_no=Integer.parseInt(request.getParameter("rest_no"));
			String intro_cont=request.getParameter("intro_cont");
			request.setAttribute("errorMsgs", errorMsgs);
			String restNostr = request.getParameter("rest_no").trim();


			intro_cont = request.getParameter("intro_cont").trim();
			if (intro_cont.isEmpty()) {
				errorMsgs.add("內容不可空白");
			}

			try {
				Collection<Part> parts = request.getParts();
				Part part = request.getPart("intro_pic");
				if (getFileNameFromPart(part) != null) {
					if (part.getContentType().startsWith("image")) {
						InputStream in = part.getInputStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] read = new byte[4 * 1024];
						int len = 0;
						while ((len = in.read(read)) != -1) {
							out.write(read, 0, len);
						}
						intro_pic = out.toByteArray();

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

			introVO.setIntro_cont(intro_cont);;
			introVO.setRest_no(rest_no);
			introVO.setIntro_pic(intro_pic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				request.setAttribute("introVO", introVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = request
						.getRequestDispatcher("/store/intro/addIntro.jsp");
				failureView.forward(request, response);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			IntroService introSvc = new IntroService();
			introVO = introSvc.addIntroduction(rest_no, intro_cont, intro_pic,0);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/store/intro/introMgr.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(request, response);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (ServletException | IOException e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = request
					.getRequestDispatcher("/store/intro/addIntro.jsp");
			try {
				failureView.forward(request, response);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		Integer intro_no = Integer.parseInt(request.getParameter("intro_no"));
		try {
			IntroService introService = new IntroService();

			introService.deleteIntroduction(intro_no);
			String url = "/store/intro/introMgr.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(request, response);
			return; // 程式中斷
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getIntroByARest(HttpServletRequest request,
			HttpServletResponse response) {
		Integer rest_no = Integer.parseInt(request.getParameter("rest_no"));
		IntroService introSrv = new IntroService();
		List<IntroVO> introVO = introSrv.getAllByARest(rest_no);
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
