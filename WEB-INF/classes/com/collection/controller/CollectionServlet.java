package com.collection.controller;

import com.collection.model.*;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.collection.model.CollectionService;
import com.collection.model.CollectionVO;
import com.google.gson.Gson;
import com.rest.model.RestService;
import com.rest.model.RestVO;
import com.ImgResize.model.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

@SuppressWarnings("serial")
public class CollectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		/* 取得action的值 並設定編碼 */
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("listByMobile".equals(action)) {
			String mem_account = req.getParameter("mem_account");
			// String image = req.getParameter("imageSize");

			MemService memberDAO = new MemService();
			MemVO member = memberDAO.getOneMem(mem_account);

			CollectionService CollectionDAO = new CollectionService();
			List<CollectionVO> collectionMen_no = CollectionDAO
					.getOneCollection(member.getMem_no());

			try {

				List<RestVO> listRest = new ArrayList<RestVO>();
				RestService restSrv = new RestService();
				for (int i = 0; i < collectionMen_no.size(); i++) {
					RestVO restVO = restSrv.getOneRest(collectionMen_no.get(i)
							.getRest_no());
					byte[] resizedImage;
					if(restVO.getRest_photo()!=null){
					resizedImage = new ImageUtil().resizeImage(
							restVO.getRest_photo(), 500, 75);
					restVO.setRest_photo(resizedImage);
					listRest.add(restVO);
					}
				}
				//
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/html");
				PrintWriter outStr = res.getWriter();
				Gson gson = new Gson();
				outStr.write(gson.toJson(listRest));
				outStr.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* 以下是世雄的code */
		// String mem_account= req.getParameter("mem_account");
		// //String image = req.getParameter("imageSize");
		//
		// MemService memberDAO = new MemService();
		// MemVO member=memberDAO.getOneMem(mem_account);
		//
		// CollectionService CollectionDAO = new CollectionService();
		// List<CollectionVO>
		// collectionMen_no=CollectionDAO.getOneCollection(member.getMem_no());
		//
		//
		// List<RestVO> listRest = new ArrayList<RestVO>();
		// RestService Rest = new RestService();
		// for(int i=0 ; i<collectionMen_no.size();i++){
		// listRest.add(Rest.getOneRest(collectionMen_no.get(i).getRest_no()));
		// }

		// if (image.equals("small")) {
		// List<Product> products = productDAO.getAll();
		// // 將所有照片縮小
		// for (int i = 0; i < products.size(); i++) {
		// Product product = products.get(i);
		// product.setImage(ImageUtil.shrink(product.getImage(), 100));
		// }
		// outStr = gson.toJson(products);
		// } else if (image.equals("large")) {
		// String spotId = req.getParameter("id");
		// int id = Integer.parseInt(spotId);
		// Product product = productDAO.findById(id);
		// outStr = gson.toJson(product);
		// }
		/* 以上是世雄的code */

		if ("insert".equals(action)) { // 來自fe_shopping.jsp的請求
			String resultMsg = null;
			CollectionService collectionSvc = new CollectionService();
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String mem_no_str = null;
				if ("mobile".equals(req.getParameter("device"))) {
					mem_no_str = new MemService()
							.getOneMem(req.getParameter("mem_account"))
							.getMem_no().toString();
				} else {

					mem_no_str = req.getParameter("mem_no").trim();
				}
				Integer rest_no = new Integer(req.getParameter("rest_no"));

				if (mem_no_str.equals("")) {

					String location = req.getContextPath()
							+ "/front/restaurant/fe_restInfo.jsp?rest_no="
							+ rest_no;

					session.setAttribute("location", location);

					res.sendRedirect(req.getContextPath()
							+ "/front/memLogin.jsp"); // *工作2 :
														// 請該user去登入網頁(login.html)
														// , 進行登入
					return;
				}

				Integer mem_no = Integer.parseInt(mem_no_str);

				CollectionVO clc1VO = collectionSvc.getOneClc(mem_no, rest_no);
				if (clc1VO != null) {
					if ("mobile".equals(req.getParameter("device"))) {
						resultMsg = "已將此餐廳加入收藏";
						res.setCharacterEncoding("UTF-8");
						res.setContentType("text/plain");
						PrintWriter out = res.getWriter();
						Gson gson = new Gson();
						out.write(gson.toJson(resultMsg));
						out.close();
					} else {

						String url = "/front/restaurant/fe_restInfo.jsp?rest_no="+ rest_no;
						RequestDispatcher successView = req
								.getRequestDispatcher(url);
						successView.forward(req, res);
						return;
					}
				}

				/*************************** 2.開始新增資料 ***************************************/
				try {
					CollectionVO collectionVO = collectionSvc.addCollection(
							mem_no, rest_no);
					resultMsg = "已將此餐廳加入收藏";
				} catch (Exception e) {
					resultMsg = "資料輸入錯誤 請稍後再試";
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if ("mobile".equals(req.getParameter("device"))) {
					res.setCharacterEncoding("UTF-8");
					res.setContentType("text/plain");
					PrintWriter out = res.getWriter();
					Gson gson = new Gson();
					out.write(gson.toJson(resultMsg));
					out.close();
				} else {
					String url = "/front/restaurant/fe_restInfo.jsp?rest_no="
							+ rest_no;
					RequestDispatcher successView = req
							.getRequestDispatcher(url);
					successView.forward(req, res);
				}

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				Integer rest_no = new Integer(req.getParameter("rest_no"));
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/restaurant/fe_restInfo.jsp?rest_no="
								+ rest_no);
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllMem.jsp
			String resultMsg = null;
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				String mem_no = null;
				if ("mobile".equals(req.getParameter("device"))) {
					mem_no = new MemService()
							.getOneMem(req.getParameter("mem_account"))
							.getMem_no().toString();
				} else {

					mem_no = req.getParameter("mem_no").trim();
				}
				Integer rest_no = new Integer(req.getParameter("rest_no"));

				/*************************** 2.�}�l�R����� ***************************************/
				try {
					CollectionService clcSvc = new CollectionService();
					clcSvc.deleteCollection(Integer.parseInt(mem_no), rest_no);
					resultMsg = "成功刪除此餐廳";

				} catch (Exception e) {
					e.printStackTrace();
					resultMsg = "操作錯誤，請稍後再試";
				}

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				if ("mobile".equals(req.getParameter("device"))) {
					res.setCharacterEncoding("UTF-8");
					res.setContentType("text/plain");
					PrintWriter out = res.getWriter();
					Gson gson = new Gson();
					out.write(gson.toJson(resultMsg));
					out.close();
				} else {
					String url = "/front/member/centerMem.jsp";
					RequestDispatcher successView = req
							.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
					successView.forward(req, res);
				}
				/*************************** ��L�i�઺��~�B�z **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/member/centerMem.jsp");
				failureView.forward(req, res);
			}
		}

	}

}