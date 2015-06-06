package com.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.json.*;

import com.ads.model.AdsService;
import com.ads.model.AdsVO;
import com.affi.model.AffiService;
import com.affi.model.AffiVO;
import com.classOfRest.model.Rest_classService;
import com.google.gson.Gson;
import com.mail.controller.MailService;
import com.rest.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class RestServlet extends HttpServlet {
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
		case "update_State":
			update_State(req, res);
			break;
		case "getOneReserv":
			getOneReserv(req, res);
			break;
		case "getAllReserv":
			getAllReserv(req, res);
			break;
		case "mail":
			mail(req, res);
			break;
		case "return":
			returnAffi(req, res);
			break;
		case "change_table":
			change_table(req, res);
			break;
		case "getOneForMobile":
			getOneForMobile(req, res);
			break;
		case "toScore":
			toScore(req, res);
			break;
		case "random":
			randomSearch(req, res);
			break;
		}
	}

	private void randomSearch(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		int count = 1 ;
		RestService restSvc = new RestService();
	    List<RestVO> list = restSvc.getAll();
//	    pageContext.setAttribute("list",list);
		
	    // *** 以下為使用random 之寫法
	  
	    int rest_noRan =(int) (Math.random()*list.size()); 
	    List<RestVO> restVolist=restSvc.getAll();
	    int rest_no=restVolist.get(rest_noRan).getRest_no();
	    System.out.print("rest_no:::"+rest_no);
		String url = "/front/restaurant/fe_restInfo.jsp?rest_no="+rest_no;
		try {
			res.sendRedirect(req.getContextPath()+url); //查詢成功後,轉交回來源網頁
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		RequestDispatcher successView = req.getRequestDispatcher(url);// 查詢成功後,轉交回來源網頁
//		try {
//			successView.forward(req, res);
//			
//		} catch (ServletException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}			
	}

	/* 將使用者輸入的評分做處理  */
	private void toScore(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
//		 try {
		/*************************** 1.接收請求參數 ***************************************/
		Integer rest_no = new Integer(req.getParameter("rest_no"));
		Integer scor_pri = new Integer(req.getParameter("scor_pri"));
		Integer scor_cook = new Integer(req.getParameter("scor_cook"));
		Integer scor_serv = new Integer(req.getParameter("scor_serv"));
		Integer scor_hea = new Integer(req.getParameter("scor_hea"));
		Integer scor_envisco = new Integer(req.getParameter("scor_envisco"));
		
		RestService restSrv = new RestService();
		RestVO restVO = restSrv.getOneRest(rest_no);
		
		scor_pri += restVO.getScor_pri();
		Integer scor_pritms =restVO.getScor_pritms()+1;
		
		scor_cook += restVO.getScor_cook();
		Integer scor_cooktms = restVO.getScor_cooktms()+1;
		
		scor_serv += restVO.getScor_serv();
		Integer scor_servtms = restVO.getScor_servtms()+1;
		
		scor_hea += restVO.getScor_hea();
		Integer scor_heatms = restVO.getScor_cooktms()+1;
		
		scor_envisco += restVO.getScor_envisco();
		Integer scor_envtms = restVO.getScor_envtms()+1;
		
		restVO.setScor_pri(scor_pri);
		restVO.setScor_pritms(scor_pritms);
		restVO.setScor_cook(scor_cook);
		restVO.setScor_cooktms(scor_cooktms);
		restVO.setScor_hea(scor_hea);
		restVO.setScor_heatms(scor_heatms);
		restVO.setScor_envisco(scor_envisco);
		restVO.setScor_envtms(scor_envtms);
		restVO.setScor_serv(scor_serv);
		restVO.setScor_servtms(scor_servtms);
		
		
		
		/*************************** 2.將處理好評分VO存資料庫 ***************************************/
		
		restSrv.updateRestByScore(restVO);
		
		String url = "/front/restaurant/fe_restInfo.jsp?rest_no="+rest_no;
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		try {
			successView.forward(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		/*************************** 其他可能的錯誤處理 **********************************/

		
	}

	private void getOneForMobile(HttpServletRequest req, HttpServletResponse res) {
		Integer rest_no = Integer.parseInt(req.getParameter("rest_no"));
		RestService restSrv = new RestService();
		RestVO restVO = restSrv.getOneRest(rest_no);
		if ("mobile".equals(req.getParameter("device"))) {
			try {
				Gson gson = new Gson();
				String gsonstr = gson.toJson(restVO);
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/html");
				PrintWriter out = res.getWriter();
				out.write(gsonstr);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}

	}

	private void change_table(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		// 取得餐廳編號
		Integer rest_no = Integer.parseInt(req.getParameter("rest_no"));
		String rest_name = req.getParameter("rest_name");
		Integer[][] reserved = new Integer[7][6];
		String str = null;

		Calendar c = Calendar.getInstance();
		RestService rs = new RestService();
		RestVO vo = rs.getOneRest(rest_no);
		Integer all_seat = vo.getReserved_totalset();// 取得總訂位數

		int day = c.get(Calendar.DAY_OF_WEEK);// 1~7
		day = day - 1;// 0~6
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 5; j++) {
				str = req.getParameter("reserved_" + (i + 1) + (j + 1));
				if (str == null || str.trim().length() == 0) {
					if (day == 0) {
						errorMsgs
								.add("星期" + "日" + "第" + (j + 1) + "個時段可訂位數為空白");
					} else {
						errorMsgs
								.add("星期" + day + "第" + (j + 1) + "個時段可訂位數為空白");
					}
					reserved[i][j] = 0;
				} else {
					int k;
					for (k = 0; k < str.length(); k++) {
						if (str.charAt(k) < 48 || str.charAt(k) > 57) {
							if (day == 0) {
								errorMsgs.add("星期" + "日" + "第" + (j + 1)
										+ "個時段可訂位數格式錯誤");
								reserved[i][j] = 0;
								break;
							} else {
								errorMsgs.add("星期" + day + "第" + (j + 1)
										+ "個時段可訂位數格式錯誤");
								reserved[i][j] = 0;
								break;
							}
						}
					}
					if (k == str.length()) {
						reserved[i][j] = Integer.parseInt(str);

						if (all_seat < reserved[i][j]) {
							if (day == 0) {
								errorMsgs.add("星期" + "日" + "第" + (j + 1)
										+ "個時段可訂位數超過總訂位數");
							} else {
								errorMsgs.add("星期" + day + "第" + (j + 1)
										+ "個時段可訂位數超過總訂位數");
							}
							reserved[i][j] = 0;
						}
					}
				}

			}
			// 準備檢查明天的時段可訂位數
			day = day + 1;
			if (day == 7) {
				day = 0;
			}
		}
		// 將7*6可訂位數包裹在vo裡
		RestVO vo1 = new RestVO();
		int inverted = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			inverted = 0;
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			inverted = 6;
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			inverted = 5;
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			inverted = 4;
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			inverted = 3;
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			inverted = 2;
		} else {
			inverted = 1;
		}

		// 塞入已訂位數
		vo.setReserved_sun1(all_seat - reserved[inverted][0]);
		vo.setReserved_sun2(all_seat - reserved[inverted][1]);
		vo.setReserved_sun3(all_seat - reserved[inverted][2]);
		vo.setReserved_sun4(all_seat - reserved[inverted][3]);
		vo.setReserved_sun5(all_seat - reserved[inverted][4]);
		vo.setReserved_sun6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_mon1(all_seat - reserved[inverted][0]);
		vo.setReserved_mon2(all_seat - reserved[inverted][1]);
		vo.setReserved_mon3(all_seat - reserved[inverted][2]);
		vo.setReserved_mon4(all_seat - reserved[inverted][3]);
		vo.setReserved_mon5(all_seat - reserved[inverted][4]);
		vo.setReserved_mon6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_tue1(all_seat - reserved[inverted][0]);
		vo.setReserved_tue2(all_seat - reserved[inverted][1]);
		vo.setReserved_tue3(all_seat - reserved[inverted][2]);
		vo.setReserved_tue4(all_seat - reserved[inverted][3]);
		vo.setReserved_tue5(all_seat - reserved[inverted][4]);
		vo.setReserved_tue6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_wed1(all_seat - reserved[inverted][0]);
		vo.setReserved_wed2(all_seat - reserved[inverted][1]);
		vo.setReserved_wed3(all_seat - reserved[inverted][2]);
		vo.setReserved_wed4(all_seat - reserved[inverted][3]);
		vo.setReserved_wed5(all_seat - reserved[inverted][4]);
		vo.setReserved_wed6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_thu1(all_seat - reserved[inverted][0]);
		vo.setReserved_thu2(all_seat - reserved[inverted][1]);
		vo.setReserved_thu3(all_seat - reserved[inverted][2]);
		vo.setReserved_thu4(all_seat - reserved[inverted][3]);
		vo.setReserved_thu5(all_seat - reserved[inverted][4]);
		vo.setReserved_thu6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_fri1(all_seat - reserved[inverted][0]);
		vo.setReserved_fri2(all_seat - reserved[inverted][1]);
		vo.setReserved_fri3(all_seat - reserved[inverted][2]);
		vo.setReserved_fri4(all_seat - reserved[inverted][3]);
		vo.setReserved_fri5(all_seat - reserved[inverted][4]);
		vo.setReserved_fri6(all_seat - reserved[inverted][5]);

		inverted++;
		if (inverted == 7) {
			inverted = 0;
		}
		vo.setReserved_sat1(all_seat - reserved[inverted][0]);
		vo.setReserved_sat2(all_seat - reserved[inverted][1]);
		vo.setReserved_sat3(all_seat - reserved[inverted][2]);
		vo.setReserved_sat4(all_seat - reserved[inverted][3]);
		vo.setReserved_sat5(all_seat - reserved[inverted][4]);
		vo.setReserved_sat6(all_seat - reserved[inverted][5]);

		if (!errorMsgs.isEmpty()) {
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("vo", vo);
			RequestDispatcher failureView = req
					.getRequestDispatcher("/store/index.jsp");
			try {
				failureView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;// 程式中斷
		}

		else {
			rs.updateReservation(rest_no, vo.getReserved_mon1(),
					vo.getReserved_mon2(), vo.getReserved_mon3(),
					vo.getReserved_mon4(), vo.getReserved_mon5(),
					vo.getReserved_mon6(), vo.getReserved_tue1(),
					vo.getReserved_tue2(), vo.getReserved_tue3(),
					vo.getReserved_tue4(), vo.getReserved_tue5(),
					vo.getReserved_tue6(), vo.getReserved_wed1(),
					vo.getReserved_wed2(), vo.getReserved_wed3(),
					vo.getReserved_wed4(), vo.getReserved_wed5(),
					vo.getReserved_wed6(), vo.getReserved_thu1(),
					vo.getReserved_thu2(), vo.getReserved_thu3(),
					vo.getReserved_thu4(), vo.getReserved_thu5(),
					vo.getReserved_thu6(), vo.getReserved_fri1(),
					vo.getReserved_fri2(), vo.getReserved_fri3(),
					vo.getReserved_fri4(), vo.getReserved_fri5(),
					vo.getReserved_fri6(), vo.getReserved_sat1(),
					vo.getReserved_sat2(), vo.getReserved_sat3(),
					vo.getReserved_sat4(), vo.getReserved_sat5(),
					vo.getReserved_sat6(), vo.getReserved_sun1(),
					vo.getReserved_sun2(), vo.getReserved_sun3(),
					vo.getReserved_sun4(), vo.getReserved_sun5(),
					vo.getReserved_sun6());

			req.setAttribute("vo", vo);
			RequestDispatcher failureView = req
					.getRequestDispatcher("/store/index.jsp");
			try {
				failureView.forward(req, res);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			return;// 程式中斷
		}

	}

	private void returnAffi(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		// try {
		/*************************** 1.接收請求參數 ***************************************/
		Integer affi_no = new Integer(req.getParameter("affi_no"));

		AffiService affiSvc = new AffiService();
		AffiVO affiVO = affiSvc.getOneAffi(affi_no);

		/*************************** 2.轉跳輸入未通過原因畫面 ***************************************/
		getServletContext().setAttribute("affiVO", affiVO);

		String url = "/back/rest/keyinReason.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
		try {
			successView.forward(req, res);
			return; // 程式中斷
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
		// String url = "/back/affi/listAllAffi.jsp";
		// RequestDispatcher successView = req.getRequestDispatcher(url);//
		// 刪除成功後,轉交回送出刪除的來源網頁
		// successView.forward(req, res);

		/*************************** 其他可能的錯誤處理 **********************************/
		// } catch (Exception e) {
		// errorMsgs.add("刪除資料失敗:"+e.getMessage());
		// RequestDispatcher failureView = req
		// .getRequestDispatcher("/back/affi/listAllAffi.jsp");
		// failureView.forward(req, res);
		// }

	}

	private void mail(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		// try {
		/*************************** 1.接收請求參數 ***************************************/
		AffiVO affiVO = (AffiVO) getServletContext().getAttribute("affiVO");

		Integer affi_no = (affiVO).getAffi_no();

		String rest_name = (affiVO).getRest_name();

		String reset_mobil = req.getParameter("rest_mobil");

		affiVO.setRest_mobil(reset_mobil);

		AffiService affiSvc = new AffiService();
		AffiVO affiVO2 = affiSvc.getOneAffi(affi_no);

		// String rest_reason = req.getParameter("rest_reason").trim();
		// System.out.println(rest_reason);
		/*************************** 2.系統寄出MAIL ***************************************/
		// req.setAttribute("affiVO", affiVO);

		// String ch_name = "test";
		// String passRandom = "123456";

		req.setAttribute("reason", reset_mobil);
		req.setAttribute("rest_name", rest_name);
		String reason = reset_mobil;
		/* 寄發Email 程式碼 */
		MailService mailSrv=new MailService();
		try {
			mailSrv.returnAffiAppli(reason,rest_name);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/

		/*************************** 其他可能的錯誤處理 **********************************/
		// } catch (Exception e) {
		// errorMsgs.add("刪除資料失敗:"+e.getMessage());
		// RequestDispatcher failureView = req
		// .getRequestDispatcher("/back/affi/listAllAffi.jsp");
		// failureView.forward(req, res);
		// }
	}

	private void getAllReserv(HttpServletRequest req, HttpServletResponse res) {
		java.sql.Date dateStr = java.sql.Date.valueOf(req.getParameter(
				"reserv_date").trim()); // 想查的日期
		Integer reserv_count = Integer.parseInt(req
				.getParameter("reserv_count")); // 想訂的人數
		Calendar c = Calendar.getInstance();
		Date date = new Date(dateStr.getTime());
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);// 選擇的那天的星期 1-7

		List<Object> result = new ArrayList<Object>(); // 要回傳的資料
		/* 做成MAP 給DAO */
		RestService restSrv = new RestService();
		List<ReservRecord> reservRecordList = restSrv.getAllReserv(req
				.getParameterMap());
		/* 開始篩選資料 */
		for (ReservRecord reservRecord : reservRecordList) {
			/* 篩選單獨一家餐廳資料 */
			ArrayList<PerDay> recordOfWeek = reservRecord.getRecordOfWeek();// 取得一周的訂位資料
			int removeIndex = 0;
			int size = recordOfWeek.size();
			for (int a = 0; a < 7; a++) {
				if (a == day - 1) {
					/* 對要求的當天人數作判斷 */
					ArrayList<PerTime> perday = recordOfWeek.get(0)
							.getRecordOfDay();
					int timeRemoveIndex = 0;
					for (int j = 0; j < 6; j++) {
						PerTime perTime = perday.get(timeRemoveIndex);
						if ((reservRecord.getMax() - perTime.getResidual()) >= reserv_count) {
							timeRemoveIndex++;
							continue;
						}
						perday.remove(timeRemoveIndex);
					}
					removeIndex++;
					continue;
				}
				recordOfWeek.remove(removeIndex);
			}
		}
		try {
			req.setAttribute("rest_addr", req.getParameter("rest_addr"));
			req.setAttribute("reservRecordList", reservRecordList);
			req.setAttribute("reserv_date", dateStr);
			req.setAttribute("reserv_count", reserv_count);
			if ("mobile".equals(req.getParameter("device"))) {
				/* 回傳給手機的JSON */
				Gson gson = new Gson();
				String outStr = gson.toJson(reservRecordList);
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/plain");
				PrintWriter out = res.getWriter();
				out.println(outStr);
				out.close();
			}
			/* WEB重導 */
			String url = "/front/search/searchRest.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交l
			successView.forward(req, res);
			return; // 程式中斷
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getOneReserv(HttpServletRequest req, HttpServletResponse res) {
		Integer rest_no = Integer.parseInt(req.getParameter("rest_no"));
		java.sql.Date dateStr = java.sql.Date.valueOf(req.getParameter(
				"reserv_date").trim()); // 想查的日期
		Integer amount = Integer.parseInt(req.getParameter("amount")); // 想訂的人數
		Calendar c = Calendar.getInstance();
		Date date = new Date(dateStr.getTime());
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);// 選擇的那天的星期 1-7

		RestService restSrv = new RestService();
		ReservRecord reservRecord = restSrv.getOneReserv(rest_no); // 回傳一周的訂位資訊
		ArrayList<PerDay> recordOfWeek = reservRecord.getRecordOfWeek(); // 取出訂位次數表
		PerDay perDay = recordOfWeek.get(day - 1); // 拿到查詢那天的資料
		List<Object> result = new ArrayList<Object>();
		ArrayList<PerTime> recoredOfADay = perDay.getRecordOfDay();
		for (PerTime perTime : recoredOfADay) {
			if ((reservRecord.getMax() - perTime.getResidual()) >= amount) { // 用最大可訂位數篩選
				String time = perTime.getTime();
				Integer residual = perTime.getResidual();
				PerTime goodTime = new PerTime(time, residual);
				result.add(goodTime);
			}
		}

		JSONArray resultJson = new JSONArray(result);

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

	private void update_State(HttpServletRequest req, HttpServletResponse res) {
		String restNoStr = req.getParameter("rest_no");
		Integer rest_no = Integer.parseInt(restNoStr);
		String rest_state = req.getParameter("rest_state");
		if (rest_state.equals("1")) {
			rest_state = "0";
		} else {
			rest_state = "1";
		}
		RestService restSrv = new RestService();
		restSrv.update_state(rest_no, rest_state);
		String url = "/back/rest/be_restMgr.jsp";
		req.setAttribute("rest_state", rest_state);
		RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交l
		try {
			successView.forward(req, res);
			return; // 程式中斷
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();

		String[] checkedClass=req.getParameterValues("class");
		Integer[] checkedClassNum=new Integer[checkedClass.length];
		
		for(int i=0;i<checkedClass.length;i++)
		{
			checkedClassNum[i]=Integer.parseInt(checkedClass[i]);
			//System.out.println("checkedClassNum:"+checkedClassNum[i]);
		}	
		String restNoStr=req.getParameter("rest_no");//1
		Integer rest_no=Integer.parseInt(restNoStr);
		
		String restAccountStr=req.getParameter("rest_account");//2
		String restPsw=req.getParameter("rest_psw");//3
		
		String busNoStr=req.getParameter("bus_no");//4
		Integer bus_no=null;
		
		if (busNoStr == null || (busNoStr.trim()).length() == 0) {
			errorMsgs.add("請輸入營利事業統一編號(不得為空)");
		}
		if ((busNoStr.trim()).length() <= 7 || (busNoStr.trim()).length() >= 9) {
			
			errorMsgs.add("請輸入正確格式(8位數 ex:06795431) 營利事業統一編號");
		}
		try
		{
			bus_no=Integer.parseInt(busNoStr);
		}
		catch(NumberFormatException e)
		{
			busNoStr = "0";
			System.out.println("busNoStrLen:"+(busNoStr.trim()).length());
			errorMsgs.add("請輸入正確格式 (8位數 ex:ex:06795431) 營利事業統一編號");
		}
		
		String restNameStr=req.getParameter("rest_name");//5
		if (restNameStr == null || (restNameStr.trim()).length() == 0) {
			errorMsgs.add("請輸入餐廳名稱(不得為空)");
		}
		
		String restAddrStr=req.getParameter("rest_addr");//6
		if (restAddrStr == null || (restAddrStr.trim()).length() == 0) {
			errorMsgs.add("請輸入餐廳地址(不得為空)");
		}
		
		String restTelStr=req.getParameter("rest_tel");//7
		if (restTelStr == null || (restTelStr.trim()).length() == 0) {
			errorMsgs.add("請輸入餐廳電話址(不得為空)");
		}
		if ((restTelStr.trim()).length() <= 10) {
			errorMsgs.add("請輸入正確格式 (0x-xxxxxxxx)電話號碼");
		}
		
		String restMailStr=req.getParameter("rest_mail");//8
		
		String restWebStr=req.getParameter("rest_web");//9
		
		String restOpentimeStr=req.getParameter("rest_opentime");//10
		
		String restClosetimeStr=req.getParameter("rest_closetime");	
		restOpentimeStr=restOpentimeStr+"~"+restClosetimeStr;
		Part part;
		InputStream in=null;
		byte[] rest_photo=null;
		try 
		{			
			part = req.getPart("rest_photo");//11
						
			in=part.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] read = new byte[4 * 1024];
			int len = 0;
			int i=0;
			try {
				while ((len = in.read(read)) != -1) { // =-1 代表獨到array尾端，!=-1則繼續讀
					
					out.write(read, 0, len); // 讀到多少(len)就把他 write out
					out.flush();	
					i++;
				}
				in.close();
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			rest_photo = out.toByteArray();
            if(i==0)//都沒吃到圖片的話，就以舊圖片當參數
            {
            	RestService rs=new RestService();
            	RestVO vo=rs.getOneRest(rest_no);
            	rest_photo=vo.getRest_photo();
            }	
            
		} 
		catch (IllegalStateException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ServletException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
				
		String restStateStr=req.getParameter("rest_state");//12
		
		
		String reservedTotalsetStr=req.getParameter("reserved_totalset");//24
		Integer reservedTotalset=null;
		try
		{
			reservedTotalset=Integer.parseInt(reservedTotalsetStr);
		}
		catch(NumberFormatException e)
		{
			errorMsgs.add("時段的可訂位數格式錯誤");
		}
		
		
		if(errorMsgs.size()>0)
		{
			String str="餐廳資料未修改";
			req.setAttribute("updateResult",str);
			req.setAttribute("errorMsgs", errorMsgs);	
			String url = "/store/infomation/restDataManagement.jsp";
			RequestDispatcher failView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			try {
				failView.forward(req, res);
				return; // 程式中斷
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		try {
												
			/*************************** update 2.開始修改資料 *****************************************/
			RestService rs=new RestService();
			Rest_classService rcs=new Rest_classService();
			rcs.updateRest_class(rest_no, checkedClassNum);
			rs.updateRest(restAccountStr, restPsw, busNoStr, restNameStr, 
					restAddrStr, restTelStr, restMailStr, restWebStr, 
					restOpentimeStr, rest_photo, restStateStr, 
					reservedTotalset, rest_no);

			/*************************** update 3.修改完成,準備轉交(Send the Success view) *************/
			RestVO restVO=rs.getOneRest(rest_no);
			String str="餐廳資料已修改";
			req.setAttribute("updateResult",str);
			req.setAttribute("restVO", restVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/store/infomation/restDataManagement.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return; // 程式中斷
			/*************************** update 其他可能的錯誤處理 *************************************/
		} catch (ServletException | IOException e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/store/infomation/restDataManagement.jsp");
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

	private void insert(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		// try {
		/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

		String bus_no = null;

		try {
			bus_no = req.getParameter("bus_no");
			if (bus_no == null || (bus_no.trim()).length() == 0) {
				errorMsgs.add("請輸入營利事業統一編號(不得為空)");
			}
			if ((bus_no.trim()).length() <= 7 || (bus_no.trim()).length() >= 9) {
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

		String rest_mail = req.getParameter("rest_mail").trim();
		String rest_web = req.getParameter("rest_web").trim();
		String rest_intro = req.getParameter("rest_intro").trim();
		if (rest_intro == null || (rest_intro.trim()).length() == 0) {
			errorMsgs.add("請輸入餐廳介紹(不得為空)");
		}
		// String rest_state = req.getParameter("rest_state").trim();

		Integer affi_no = null;
		affi_no =Integer.parseInt(req.getParameter("affi_no"));

		String rest_account = req.getParameter("rest_mail").trim();
		// String rest_psw = req.getParameter("rest_psw").trim();
		// String rest_opentime = req.getParameter("rest_opentime").trim();
		// String affi_state = req.getParameter("affi_state").trim();

		RestVO restVO = new RestVO();

		restVO.setAffi_no(affi_no);
		restVO.setRest_account(rest_account);
		restVO.setBus_no(bus_no);
		restVO.setRest_name(rest_name);
		restVO.setRest_addr(rest_addr);
		restVO.setRest_tel(rest_tel);
		// restVO.setRest_mobil(rest_mobil);
		restVO.setRest_mail(rest_mail);
		restVO.setRest_web(rest_web);
		restVO.setRest_intro(rest_intro);
		// restVO.setAffi_state(rest_state);

		String rest_psw = String.valueOf((int)(Math.random() * 10000000));	//將密碼轉成字串格式(整數)

		// Part part = req.getPart("rest_photo"); // part 是上傳檔案時使用的
		// InputStream in = part.getInputStream();
		byte[] rest_photo = null;

		AffiService affiSvr = new AffiService();

		AffiVO affiVO = affiSvr.getOneAffi(affi_no); // 將資料包給VO

		InputStream in = new ByteArrayInputStream(affiVO.getRest_photo());

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] read = new byte[4 * 1024]; // 設定一次要read多少Byte

		int len = 0;

		try {
			while ((len = in.read(read)) != -1) { // =-1 代表獨到array尾端，!=-1則繼續讀
				out.write(read, 0, len); // 讀到多少(len)就把他 write out
				out.flush();
				in.close();
				out.close();
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rest_photo = out.toByteArray();

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			req.setAttribute("restVO", restVO); // 含有輸入格式錯誤的empVO物件,也存入req
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back/affi/listAllAffi.jsp");
			try {
				failureView.forward(req, res);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		/*************************** 2.開始新增資料 ***************************************/
		RestService restSvc = new RestService();

		// restVO = restSvc.addRest(affi_no);
		restVO = restSvc.addRest(affi_no, rest_account, rest_psw, bus_no,
				rest_name, rest_addr, rest_tel, rest_mail, rest_web,
				rest_intro, rest_photo); // x11
		Integer rest_no=restSvc.checkManager(rest_account).getRest_no();
		Rest_classService rcs=new Rest_classService();
		rcs.updateRest_class(rest_no, new Integer[] {10}); //預設一個不拘的類別
		/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
		MailService test = new MailService();
		try {
			test.sendPassword( rest_name, rest_mail, rest_account, rest_psw);
		} catch (MessagingException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = "/back/rest/be_restMgr.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAffi.jsp
		try {
			successView.forward(req, res);
			return;
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*************************** 其他可能的錯誤處理 **********************************/
		// } catch (Exception e) {
		// errorMsgs.add(e.getMessage());
		// RequestDispatcher failureView = req
		// .getRequestDispatcher("/rest/addAffi.jsp");
		// failureView.forward(req, res);
		// }

	}

	private void delete(HttpServletRequest req, HttpServletResponse res) {
		try {
			int ads_no = Integer.parseInt(req.getParameter("ads_no"));
			AdsService adsSrv = new AdsService();
			adsSrv.delete(ads_no);
			String url = "/back/ads/be_adsMgr.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交l
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
