package com.odr.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.odr.model.*;
import com.rest.model.RestVO;
import com.cop.model.*;
import com.google.gson.Gson;
import com.mail.controller.MailService;
import com.mem.model.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class OdrServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	/* 取得系統時間 */
	public static String getDateTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String strDate = sdFormat.format(date);
		return strDate;
	}

	/* 取得亂數序號 */
	public static String randomString(int len) {
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int idx = (int) (Math.random() * str.length());
			sb.append(str.charAt(idx));
		}
		return sb.toString();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		/* 取得action的值 並設定編碼 */
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=UTF-8");

		/* 依會員查詢他所有訂單 */
		if (action.equals("listALLbyMem")) {
			ServletContext context = getServletContext();
			String contentType = context.getInitParameter("contentType");
			String outStr = "";
			String mem_account = req.getParameter("mem_account");
			// String image = req.getParameter("imageSize");

			MemService memberDAO = new MemService();
			MemVO member = memberDAO.getOneMem(mem_account);

			OdrService OdrVODao = new OdrService();
			List<OdrVO> OdrVODaoAll = OdrVODao.getMemOdr(member.getMem_no());

//			List<OdrVO> listodr = new ArrayList<OdrVO>();
//
//			for (int i = 0; i < OdrVODaoAll.size(); i++) {
//
//				if ((member.getMem_no())
//						.equals((OdrVODaoAll.get(i).getMem_no()))) {
//					listodr.add(OdrVODaoAll.get(i));
//				}
//			}
			Gson gson = new Gson();
			outStr = gson.toJson(OdrVODaoAll);
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/plain");
			PrintWriter out = res.getWriter();
			out.println(outStr);
			out.close();
		}

		/* 購買餐劵所需的餐劵資料 */
		if ("listOneCop_ByCompositeQuery".equals(action)) { // 來自rest_info.jsp的複合查詢請求
		// List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			// req.setAttribute("errorMsgs", errorMsgs);

			// try {

			/*************************** 1.將輸入資料轉為Map **********************************/
			// 採用Map<String,String[]> getParameterMap()的方法
			// 注意:an immutable java.util.Map
			// Map<String, String[]> map = req.getParameterMap();
			HttpSession session = req.getSession();
			String rest_no = req.getParameter("rest_no");

			Map<String, String[]> map = (Map<String, String[]>) session
					.getAttribute("map");
			HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
					.getParameterMap();
			HashMap<String, String[]> map2 = new HashMap<String, String[]>();
			map2 = (HashMap<String, String[]>) map1.clone();
			session.setAttribute("map", map2);
			// ServletContext context = getServletContext();
			// context.setAttribute("map",map2);//測試把map放在context

			map = (HashMap<String, String[]>) req.getParameterMap();

			/*************************** 2.開始複合查詢 ***************************************/
			CopService copSvc = new CopService();
			CopVO copVO = copSvc.getOneCopByOdr(map);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			session.setAttribute("copVO", copVO); // 資料庫取出的list物件,存入request
			session.setAttribute("rest_no", rest_no);
			// context.setAttribute("listCop_ByCompositeQuery",
			// list);//測試把list放在客廳(context)
			RequestDispatcher successView = req
					.getRequestDispatcher("/front/coupon/fe_shopping.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** 其他可能的錯誤處理 **********************************/
			// } catch (Exception e) {
			// // errorMsgs.add(e.getMessage());
			// RequestDispatcher failureView = req
			// .getRequestDispatcher("/front/restaurant/fe_restInfo.jsp");
			// failureView.forward(req, res);
			// }
		}

		/* 回傳購買總金額的JSON */
		if ("getOdr_tlprc".equals(action)) { // 來自rest_info.jsp的複合查詢請求

			Integer amount = Integer.parseInt(req.getParameter("amount")); // 想買的數量
			Integer cop_price = Integer.parseInt(req.getParameter("cop_price")); // 餐劵價格
			Integer totalprice = amount * cop_price;
			String ord_tlprc = "{'ord_tlprc':" + totalprice + "}";
			JSONObject resultJson = new JSONObject(ord_tlprc);
			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		/* 處理掃描的QRCode */
		if ("QRCode".equals(action)) { //
			HttpSession session = req.getSession();
			RestVO restVO = (RestVO)session.getAttribute("restVO");
			Map<String, String[]> mapForQR = (HashMap<String, String[]>) req
					.getParameterMap();// QR碼
			OdrService odrSvc = new OdrService();
			List<OdrVO> list = odrSvc.getOdrByState(mapForQR);// 這個查詢用來查序號
			Integer remainder = 0;
			String odr_seqnum = "";
			OdrVO odrVO = null;
			if (list.size() == 0 ) {
				remainder = 0;
			} else {
				odrVO = list.get(0);
				session.setAttribute("odrVOForQR", odrVO);// 把此序號的odrVO放在session，供支付餐劵時使用
				remainder = odrVO.getOdr_buyamt() - odrVO.getOdr_usdtms();
				if(remainder<0){
					remainder=0;
				}

			}

			CopService copSvc = new CopService();
			CopVO copVO = copSvc.getOneCop(odrVO.getCop_no());
			String rest_name = restVO.getRest_name();
			String cop_name = copVO.getCop_name();
			String cop_content = copVO.getCop_content();
			Integer cop_orlprice=copVO.getCop_orlprice();
			if(!copVO.getRest_no().equals(restVO.getRest_no())){
				remainder = 0;
				rest_name="";
				cop_name="";
				cop_orlprice=0;
				cop_content="非本餐廳發行之餐劵";
			}
			// String result = "{'remainder':"+remainder+","+
			// "'cop_name':"+cop_name+","+
			// "'cop_content':"+cop_content +"}";
			// JSONObject resultJson = new JSONObject(result);
			JSONObject resultJson = new JSONObject();
			resultJson.append("remainder", remainder);
			resultJson.append("rest_name", rest_name);
			resultJson.append("cop_name", cop_name);
			resultJson.append("cop_orlprice", cop_orlprice);
			resultJson.append("cop_content", cop_content);
			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* 處理QRCod頁面所要使用的張數 */
		if ("UseSeqByQRCode".equals(action)) { //

			HttpSession session = req.getSession();
			// Map<String, String[]>mapForQR = (Map<String,
			// String[]>)session.getAttribute("mapForQR");
			Integer this_use_amt = Integer
					.parseInt(req.getParameter("odr_tms"));// 此次所要使用的張數
			// Map<String, String[]> map = (HashMap<String,
			// String[]>)req.getParameterMap();// QR碼
			OdrService odrSvc = new OdrService();
			// List<OdrVO> list1 = odrSvc.getOdrByState(mapForQR);//這個查詢用來查序號

			OdrVO odrVO = (OdrVO) session.getAttribute("odrVOForQR");
			Integer odr_usdtms = odrVO.getOdr_usdtms() + this_use_amt;
			Integer remainder = 0;
			remainder = odrVO.getOdr_buyamt() - odr_usdtms;
			if(remainder<0){
				remainder=0;
			}
			odrVO = odrSvc.updateOdr(odrVO.getOdr_no(), odrVO.getMem_no(),
					odrVO.getCop_no(), odrVO.getOdr_state(),
					odrVO.getOdr_seqnum(), odr_usdtms, odrVO.getOdr_payname(),
					odrVO.getOdr_phone(), odrVO.getOdr_mail(),
					odrVO.getOdr_buyamt(), odrVO.getOdr_toprc(),
					odrVO.getOdr_paymode(), odrVO.getOdr_paytime(),
					odrVO.getOdr_time());
			session.setAttribute("odrVOForQR",odrVO);
			CopService copSvc = new CopService();
			CopVO copVO = copSvc.getOneCop(odrVO.getCop_no());
			
			Integer discount =  this_use_amt * copVO.getCop_orlprice();//此次折抵的價錢

//			String result = "{'remainder_afteruse':" + remainder + "}";
			JSONObject resultJson = new JSONObject();
			resultJson.append("remainder_afteruse", remainder);
			resultJson.append("discount", discount);
			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/* 回傳付款方式內容 */
		if ("getPayMode_content".equals(action)) { // 來自rest_info.jsp的複合查詢請求

			String paymode = req.getParameter("paymode"); // 付款方式(1:信用卡 2:ATM
															// 3:現金)
			StringBuilder paymode_content = null;

			if (paymode.equals("1")) {
				paymode_content = new StringBuilder()
						.append("您已選擇一般信用卡方式付款。<br>")
						.append("FOODme消費有保障：<br>")
						.append("1.信託履約帳戶消費絕對有保障。<br>")
						.append("2.交易帳務查詢商品明細隨時查。<br>")
						.append("3.交易過程經過SSL256bit安全加密機制，保障您的刷卡安全。<br>")
						.append("4.刷卡完成，即開始處理您的訂單，便利優惠不用等。<br>")
						.append("5.各商品禮券信託資訊可至商品資訊頁的餐券介紹裡查閱。<br>")
						.append("6.各商品禮券發行人資訊可至商品資訊頁的餐券介紹裡查閱。<br>");

			} else if (paymode.equals("2")) {
				paymode_content = new StringBuilder()
						.append("Description lists 您已選擇ATM轉帳付款 方式付款。<br>")
						.append("本公司匯款轉帳帳號資料如下<br>")
						.append("上海商業儲蓄銀行 中和分行<br>").append("機構代號：011<br>")
						.append("匯款帳號：33-102-000026323<br>")
						.append("戶名：YA101_05<br>")
						.append("ATM匯款採人工對帳，於週一～週五下午2點前完成匯款，餐券將於當日寄送。<br>")
						.append("如下午2點以後完成匯款，餐券將於隔日為您確認寄出。如急需使用餐券，<br>")
						.append("建議採用其它付款方式或歡迎來電：02-2377-8323。<br>");
			} else if (paymode.equals("3")) {
				paymode_content = new StringBuilder()
						.append("1.自取請至FOODme辦公室：中央大學資策會308教室<br>")
						.append("2.到現場只要告知櫃檯人員您訂購時留的手機號碼或訂單編號即可。<br>")
						.append("3.週一至週日，早上10點～晚上7點，中午不休息也可以來自取唷！<br>");
			}

			String content = "{'paymode_content':" + paymode_content + "}";
			JSONObject resultJson = new JSONObject(content);

			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if ("insertByMobile".equals(action)) { // 來自fe_shopping.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String  mem_account = req.getParameter("mem_account").trim();
			MemService memSrv=new MemService();
			MemVO memVO=memSrv.getOneMem(mem_account);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String rest_no = req.getParameter("rest_no");// 返回原來的餐廳頁面用

				Integer cop_no = new Integer(req.getParameter("cop_no").trim());// 餐劵編號，由購買頁面的hidden(cop_no)取得
				Integer cop_price = new Integer(req.getParameter("cop_price")
						.trim());// 餐劵價錢，由購買頁面的hidden(cop_no)取得
				Integer odr_buyamt = new Integer(req.getParameter("odr_buyamt")
						.trim());// 購買數量
				Integer odr_toprc = cop_price * odr_buyamt;
				Integer odr_usdtms = 0;// 由於新買餐劵，所以使用量為0
				Integer odr_paymode = new Integer(req.getParameter(
						"odr_paymode").trim());
				Timestamp odr_paytime = null;// 此時應屬未結帳狀態，所以也無結帳時間
				String odr_state = "0"; // 此時應屬未結帳狀態
				Timestamp odr_time = java.sql.Timestamp.valueOf(getDateTime());// 訂單時間為當下購買時間
				String odr_seqnum = null;// 尚未結帳，所以沒給序號

				String odr_payname = memVO.getMem_name();

				if (odr_payname == null || (odr_payname.trim()).length() == 0) {
					errorMsgs.add("請輸入姓名");
				}

				String odr_mail = memVO.getMem_mail();

				if (odr_mail == null || (odr_mail.trim()).length() == 0) {
					errorMsgs.add("請輸入電子信箱");
				}

				String odr_phone = memVO.getMem_phone();

				if (odr_phone == null || (odr_phone.trim()).length() == 0) {
					errorMsgs.add("請輸入聯絡電話");
				}

				OdrVO odrVO = new OdrVO();
				odrVO.setMem_no(memVO.getMem_no());
				odrVO.setCop_no(cop_no);
				odrVO.setOdr_state(odr_state);
				odrVO.setOdr_usdtms(odr_usdtms);
				odrVO.setOdr_payname(odr_payname);
				odrVO.setOdr_phone(odr_phone);
				odrVO.setOdr_mail(odr_mail);
				odrVO.setOdr_buyamt(odr_buyamt);
				odrVO.setOdr_toprc(odr_toprc);
				odrVO.setOdr_paymode(odr_paymode);
				odrVO.setOdr_paytime(odr_paytime);
				odrVO.setOdr_time(odr_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("odrVO", odrVO); // 含有輸入格式錯誤的copVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front/coupon/fe_shopping.jsp");
//					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				OdrService odrSvc = new OdrService();
				odrVO = odrSvc.addOdr(memVO.getMem_no(), cop_no, odr_state, odr_seqnum,
						odr_usdtms, odr_payname, odr_phone, odr_mail,
						odr_buyamt, odr_toprc, odr_paymode, odr_paytime,
						odr_time);
				/* 寄發Email 程式碼 */
				MailService mailSrv=new MailService();
				mailSrv.sendOdrInfo(odr_mail,odr_payname);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/plain");
				PrintWriter out=res.getWriter();
				out.write("success");
				out.close();

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/plain");
				PrintWriter out=res.getWriter();
				out.write("fail");
				out.close();
			}
		}
		if ("insert".equals(action)) { // 來自fe_shopping.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String rest_no = req.getParameter("rest_no");// 返回原來的餐廳頁面用

				Integer mem_no = new Integer(req.getParameter("mem_no").trim());
				Integer cop_no = new Integer(req.getParameter("cop_no").trim());// 餐劵編號，由購買頁面的hidden(cop_no)取得
				Integer cop_price = new Integer(req.getParameter("cop_price")
						.trim());// 餐劵價錢，由購買頁面的hidden(cop_no)取得
				Integer odr_buyamt = new Integer(req.getParameter("odr_buyamt")
						.trim());// 購買數量
				Integer odr_toprc = cop_price * odr_buyamt;
				Integer odr_usdtms = 0;// 由於新買餐劵，所以使用量為0
				Integer odr_paymode = new Integer(req.getParameter(
						"odr_paymode").trim());
				Timestamp odr_paytime = null;// 此時應屬未結帳狀態，所以也無結帳時間
				String odr_state = "0"; // 此時應屬未結帳狀態
				Timestamp odr_time = java.sql.Timestamp.valueOf(getDateTime());// 訂單時間為當下購買時間
				String odr_seqnum = null;// 尚未結帳，所以沒給序號

				String odr_payname = req.getParameter("odr_payname").trim();

				if (odr_payname == null || (odr_payname.trim()).length() == 0) {
					errorMsgs.add("請輸入姓名");
				}

				String odr_mail = req.getParameter("odr_mail").trim();

				if (odr_mail == null || (odr_mail.trim()).length() == 0) {
					errorMsgs.add("請輸入電子信箱");
				}

				String odr_phone = req.getParameter("odr_phone").trim();

				if (odr_phone == null || (odr_phone.trim()).length() == 0) {
					errorMsgs.add("請輸入聯絡電話");
				}

				OdrVO odrVO = new OdrVO();
				odrVO.setMem_no(mem_no);
				odrVO.setCop_no(cop_no);
				odrVO.setOdr_state(odr_state);
				odrVO.setOdr_usdtms(odr_usdtms);
				odrVO.setOdr_payname(odr_payname);
				odrVO.setOdr_phone(odr_phone);
				odrVO.setOdr_mail(odr_mail);
				odrVO.setOdr_buyamt(odr_buyamt);
				odrVO.setOdr_toprc(odr_toprc);
				odrVO.setOdr_paymode(odr_paymode);
				odrVO.setOdr_paytime(odr_paytime);
				odrVO.setOdr_time(odr_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("odrVO", odrVO); // 含有輸入格式錯誤的copVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front/coupon/fe_shopping.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				OdrService odrSvc = new OdrService();
				odrVO = odrSvc.addOdr(mem_no, cop_no, odr_state, odr_seqnum,
						odr_usdtms, odr_payname, odr_phone, odr_mail,
						odr_buyamt, odr_toprc, odr_paymode, odr_paytime,
						odr_time);
				/* 寄發Email 程式碼 */
			     CopService copSv = new CopService();
					CopVO copVO = copSv.getOneCop(cop_no);
					String cop_name = copVO.getCop_name();
					MailServiceForBuyCoupon mailSv = new MailServiceForBuyCoupon();	
					mailSv.sendPassword(odr_payname, odr_mail, cop_name, odr_buyamt, odr_toprc);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				String url = "/front/restaurant/fe_restInfo.jsp?rest_no="
						+ rest_no;
				res.sendRedirect(req.getContextPath()+url);
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front/coupon/fe_shopping.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

		if ("checkForPayed".equals(action)) { // 來自listAllOrder.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String odr_no = req.getParameter("odr_no");// 用來取得訂單
				Integer cop_no = new Integer(req.getParameter("cop_no"));// 用來取得餐劵資料

				/*************************** 2.開始查詢資料 ****************************************/
				OdrService OdrSvc = new OdrService();
				OdrVO odrVO = OdrSvc.getOneOdr(odr_no);
				CopService copSvc = new CopService();
				CopVO copVO = copSvc.getOneCop(cop_no);
				Integer odr_buyamt = odrVO.getOdr_buyamt();// 訂單的購買數量
				Integer cop_selamt = copVO.getCop_selamt() + odr_buyamt;// 將餐劵的銷售量更新

				/*************************** 確認結帳，修改資料 ****************************************/
				String odr_seqnum = randomString(15);// 取得序號
				String odr_state = "1";// 狀態設為已結帳
				Timestamp odr_paytime = java.sql.Timestamp
						.valueOf(getDateTime());// 設定結帳時間

				odrVO = OdrSvc.updateOdr(odr_no, odrVO.getMem_no(),
						odrVO.getCop_no(), odr_state, odr_seqnum,
						odrVO.getOdr_usdtms(), odrVO.getOdr_payname(),
						odrVO.getOdr_phone(), odrVO.getOdr_mail(),
						odrVO.getOdr_buyamt(), odrVO.getOdr_toprc(),
						odrVO.getOdr_paymode(), odr_paytime,
						odrVO.getOdr_time());
				copVO = copSvc.updateCop(cop_no, copVO.getRest_no(),
						copVO.getCop_name(), copVO.getCop_content(),
						copVO.getCop_orlprice(), copVO.getCop_price(),
						copVO.getCop_dl(), copVO.getCop_state(),
						copVO.getCop_date(), copVO.getCop_circu(), cop_selamt);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("odrVO", odrVO); // 資料庫取出的copVO物件,存入req
				req.setAttribute("copVO", copVO);
				/*寄發Email 程式碼*/
				 MailServiceForCheckOrder mailSv = new MailServiceForCheckOrder();
				 mailSv.sendPassword(odrVO.getOdr_payname(), odrVO.getOdr_mail(), odrVO.getOdr_no(), copVO.getCop_name(), odrVO.getOdr_buyamt(),  odrVO.getOdr_toprc(),  odrVO.getOdr_seqnum());
				 String requestURL = req.getParameter("requestURL");
				/* 發送簡訊程式碼 */
				Send se = new Send();
				String[] tel = { odrVO.getOdr_phone() };
				String message = "您已成功購買商品\n" + "商品名稱: " + copVO.getCop_name()
						+ "\n" + odr_buyamt + "張\n" + "餐卷序號: " + odr_seqnum
						+ "\n" + "FOOODme品牌小組\n";
				se.sendMessage(tel, message);
				if (requestURL.equals("/back/order/checkOrderByState.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session
							.getAttribute("mapForOdr_State");
					List<OdrVO> list = OdrSvc.getOdrByState(map);
					req.setAttribute("listOdr_ByCompositeQuery", list); // 複合查詢,
																		// 資料庫取出的list物件,存入request
				}
				String url = "/store/updateCop.jsp";
				RequestDispatcher successView = req
						.getRequestDispatcher(requestURL);// 成功轉交
															// update_cop_input.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/order/checkOrderByState.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

		if ("listOneOdr".equals(action)) { // 依訂單編號查訂單

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("odr_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("訂單編號輸入錯誤");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back/order/checkOrder.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				OdrService odrSvc = new OdrService();
				OdrVO odrVO = odrSvc.getOneOdr(str);
				if (odrVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back/order/checkOrder.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				if ("mobile".equals(req.getParameter("device"))) {
					Gson gson = new Gson();
					String resultJson = gson.toJson(odrVO);
					res.setContentType("text/plain");
					res.setContentType("UTF-8");
					try {
						PrintWriter out = res.getWriter();
						out.write(resultJson);
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
				req.setAttribute("odrVO", odrVO); // 資料庫取出的odrVO物件,存入req
				String url = "/back/order/checkOneOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);
				return; // 程式中斷
				}
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/order/checkOrder.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}

		if ("listOdrByState".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.將輸入資料轉為Map **********************************/
				// 採用Map<String,String[]> getParameterMap()的方法
				// 注意:an immutable java.util.Map
				// Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();

				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("mapForOdr_State");
				if (req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
							.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>) map1.clone();
					session.setAttribute("mapForOdr_State", map2);
					// ServletContext context = getServletContext();
					// context.setAttribute("map",map2);//測試把map放在context

					map = (HashMap<String, String[]>) req.getParameterMap();
				}

				/*************************** 2.開始複合查詢 ***************************************/
				OdrService odrSvc = new OdrService();
				List<OdrVO> list = odrSvc.getOdrByState(map);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listOdr_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				// context.setAttribute("listCop_ByCompositeQuery",
				// list);//測試把list放在客廳(context)
				RequestDispatcher successView = req
						.getRequestDispatcher("/back/order/checkOrderByState.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/order/checkOrderByState.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		/* 用來查詢想要知道的日期的所有訂單 */
		if ("findOdrByDate".equals(action)) { // 來自be_couponMgr.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.將輸入資料轉為Map **********************************/
				// 採用Map<String,String[]> getParameterMap()的方法
				// 注意:an immutable java.util.Map
				// Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				String check_date = req.getParameter("odr_paytime");

				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("mapbydate");
				if (req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
							.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>) map1.clone();
					session.setAttribute("mapbydate", map2);
					// ServletContext context = getServletContext();
					// context.setAttribute("map",map2);//測試把map放在context
					String odr_paytime = req.getParameter("odr_paytime");
					map = (HashMap<String, String[]>) req.getParameterMap();
				}

				/*************************** 2.開始複合查詢 ***************************************/
				OdrService odrSvc = new OdrService();
				List<OdrVO> list = odrSvc.getOdrByState(map);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				session.setAttribute("listOdr_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				req.setAttribute("check_date", check_date);
				// context.setAttribute("listCop_ByCompositeQuery",
				// list);//測試把list放在客廳(context)
				RequestDispatcher successView = req
						.getRequestDispatcher("/back/coupon/be_couponMgr.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				return; // 程式中斷
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back/coupon/be_couponMgr.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
		}
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求
			String resultMsg = null;
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {
				/***************************1.接收請求參數***************************************/
				String odr_no =  req.getParameter("odr_no");
				/***************************2.開始刪除資料***************************************/
				OdrService odrSvc = new OdrService();
				odrSvc.deleteOdr(odr_no);
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
				
				String url = req.getContextPath()+"/front/member/centerMem.jsp";
				res.sendRedirect(url);
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
						.getRequestDispatcher("/front/member/centerMem.jsp");
				failureView.forward(req, res);
				return;
				}
			}
		}
		/* 處理掃描的QRCode */
		if ("QRCode".equals(action)) { //
			HttpSession session = req.getSession();
			RestVO restVO = (RestVO)session.getAttribute("restVO");
			Map<String, String[]> mapForQR = (HashMap<String, String[]>) req
					.getParameterMap();// QR碼
			OdrService odrSvc = new OdrService();
			List<OdrVO> list = odrSvc.getOdrByState(mapForQR);// 這個查詢用來查序號
			Integer remainder = 0;
			String odr_seqnum = "";
			OdrVO odrVO = null;
			if (list.size() == 0 ) {
				remainder = 0;
			} else {
				odrVO = list.get(0);
				session.setAttribute("odrVOForQR", odrVO);// 把此序號的odrVO放在session，供支付餐劵時使用
				remainder = odrVO.getOdr_buyamt() - odrVO.getOdr_usdtms();

			}

			CopService copSvc = new CopService();
			CopVO copVO = copSvc.getOneCop(odrVO.getCop_no());
			String rest_name = restVO.getRest_name();
			String cop_name = copVO.getCop_name();
			String cop_content = copVO.getCop_content();
			Integer cop_orlprice=copVO.getCop_orlprice();
			if(!copVO.getRest_no().equals(restVO.getRest_no())){
				remainder = 0;
				rest_name="";
				cop_name="";
				cop_orlprice=0;
				cop_content="非本餐廳發行之餐劵";
			}
			// String result = "{'remainder':"+remainder+","+
			// "'cop_name':"+cop_name+","+
			// "'cop_content':"+cop_content +"}";
			// JSONObject resultJson = new JSONObject(result);
			JSONObject resultJson = new JSONObject();
			resultJson.append("remainder", remainder);
			resultJson.append("rest_name", rest_name);
			resultJson.append("cop_name", cop_name);
			resultJson.append("cop_orlprice", cop_orlprice);
			resultJson.append("cop_content", cop_content);
			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* 處理QRCod頁面所要使用的張數 */
		if ("UseSeqByQRCode".equals(action)) { //

			HttpSession session = req.getSession();
			// Map<String, String[]>mapForQR = (Map<String,
			// String[]>)session.getAttribute("mapForQR");
			Integer this_use_amt = Integer
					.parseInt(req.getParameter("odr_tms"));// 此次所要使用的張數
			// Map<String, String[]> map = (HashMap<String,
			// String[]>)req.getParameterMap();// QR碼
			OdrService odrSvc = new OdrService();
			// List<OdrVO> list1 = odrSvc.getOdrByState(mapForQR);//這個查詢用來查序號

			OdrVO odrVO = (OdrVO) session.getAttribute("odrVOForQR");
			Integer odr_usdtms = odrVO.getOdr_usdtms() + this_use_amt;
			Integer remainder = 0;
			remainder = odrVO.getOdr_buyamt() - odr_usdtms;
			odrVO = odrSvc.updateOdr(odrVO.getOdr_no(), odrVO.getMem_no(),
					odrVO.getCop_no(), odrVO.getOdr_state(),
					odrVO.getOdr_seqnum(), odr_usdtms, odrVO.getOdr_payname(),
					odrVO.getOdr_phone(), odrVO.getOdr_mail(),
					odrVO.getOdr_buyamt(), odrVO.getOdr_toprc(),
					odrVO.getOdr_paymode(), odrVO.getOdr_paytime(),
					odrVO.getOdr_time());
			
			CopService copSvc = new CopService();
			CopVO copVO = copSvc.getOneCop(odrVO.getCop_no());
			
			Integer discount =  this_use_amt * copVO.getCop_orlprice();//此次折抵的價錢

//			String result = "{'remainder_afteruse':" + remainder + "}";
			JSONObject resultJson = new JSONObject();
			resultJson.append("remainder_afteruse", remainder);
			resultJson.append("discount", discount);
			res.setContentType("text/plain");
			try {
				PrintWriter out = res.getWriter();
				out.write(resultJson.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	}

	private Integer Integer(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	

}