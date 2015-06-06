package com.reserv.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.reserv.model.*;
import com.rest.model.PerDay;
import com.rest.model.PerTime;
import com.rest.model.ReservRecord;
import com.rest.model.RestService;

@WebServlet("/ReservationRecordServlet")
public class ReservationRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReservationRecordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");// 得知請求的名稱
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		switch (action) {
		case "insert":
			insert(request, response);
			break;
		case "getRecordOfOneMember_fromMobile":
			getRecordOfOneMember_fromMobile(request, response);
			break;
		case "query":
			query(request, response);
			break;
		case "getOneReserv":
			getReservFromARest(request, response);
			break;
		case "getAllReserv":
			getReservFromAllRest(request, response);
			break;
		case "store_query":
			getStore_query(request, response);
			break;
		case "back_query":
			getStore_query_from_back(request, response);
			break;
		case "change_seating":
			updateSeating(request, response);
			break;
		case "cancel_reservation":
			cancelReservation(request, response);
			break;

		}

	}

	private void getRecordOfOneMember_fromMobile(HttpServletRequest request,
			HttpServletResponse response) {
		String outStr = "";
		Gson gson = new Gson();
		String mem_account = request.getParameter("mem_account");
		MemService memSrv = new MemService();
		MemVO memVO = memSrv.getOneMem(mem_account);
		Reservation_recordService recordService = new Reservation_recordService();
		List<Reservation_recordVO> recordList = recordService
				.getReservationRecord(memVO.getMem_no());
		outStr = gson.toJson(recordList);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(outStr);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cancelReservation(HttpServletRequest request,
			HttpServletResponse response) {
		String rec_no_str = request.getParameter("rec_no");
		String mem_account = request.getParameter("mem_account");
		Integer rec_no = Integer.parseInt(rec_no_str);

		Reservation_recordService rs = new Reservation_recordService();

		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
		// 刪除訂位紀錄
		PrintWriter out = null;
		rs.deleteReservation_record(rec_no);
		if("mobile".equals(request.getParameter("device"))){
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				out.write("success");
				out.close();
			} catch (Exception e) {
				out.write("fail");
				out.close();
			}
		}
		// 刪除後重新查詢訂位紀錄
		list = rs.getReservationRecordByMemAccount(mem_account);
		request.setAttribute("list", list);
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/member/centerMem.jsp");
		try {
			rd.forward(request, response);
			return; // 程式中斷
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateSeating(HttpServletRequest request,
			HttpServletResponse response) {
		String seating = request.getParameter("seating");
		String restNoStr = request.getParameter("rest_no");
		String memNoStr = request.getParameter("mem_no");
		String reservationDayStr = request.getParameter("reservation_day");
		String periodStr = request.getParameter("period");
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		Integer memNo = null;
		Integer period = null;
		Integer restNo = null;

		// 字串轉數字
		memNo = Integer.parseInt(memNoStr);
		period = Integer.parseInt(periodStr);
		restNo = Integer.parseInt(restNoStr);

		Reservation_recordService rs = new Reservation_recordService();
		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

		// 修改入座狀態
		rs.changeSeating(seating, reservationDayStr, period, memNo);
		// 重新查詢修改後的結果
		list = rs.getReservationRecord(memNo, restNo);
		request.setAttribute("list", list);
		RequestDispatcher rd = request
				.getRequestDispatcher("/store/reservation/queryReservationRecordByTime.jsp");
		try {
			rd.forward(request, response);
			return; // 程式中斷
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getStore_query_from_back(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		// System.out.println("aaaaa");
		// 取得會員編號(字串型別)
		String mem_no_str = request.getParameter("mem_no");
		// 取得餐廳編號(字串型別)
		String rest_no_str = request.getParameter("rest_no");
		// 取得當前日期物件
		Date current_date = new Date();

		// 如果完全沒有輸入
		if (mem_no_str.length() == 0 && rest_no_str.length() == 0) {
			errorMsgs.add("請輸入會員編號或餐廳編號");
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
			try {
				dispatcher.forward(request, response);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 如果有會員編號無餐廳編號
		else if (mem_no_str.length() != 0 && rest_no_str.length() == 0) {
			Integer mem_no = null;
			try {
				mem_no = Integer.parseInt(mem_no_str);
			} catch (NumberFormatException e) {
				errorMsgs.add("輸入會員編號格式錯誤");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
			Reservation_recordService rs = new Reservation_recordService();
			list = rs.getReservationRecordByMemNo(mem_no);
			// 如果沒找到訂位紀錄
			if (list.size() == 0) {

				errorMsgs.add("輸入會員編號無訂位紀錄");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 如果會員有訂位紀錄
			else {
				// System.out.println(list.size());
				request.setAttribute("list", list);// 準備轉送至查詢結果頁面
				RequestDispatcher rd = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					rd.forward(request, response);
					return; // 程式中斷
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// 如果無會員編號有餐廳編號
		else if (mem_no_str.length() == 0 && rest_no_str.length() != 0) {
			Integer rest_no = null;
			try {
				rest_no = Integer.parseInt(rest_no_str);
			} catch (NumberFormatException e) {
				errorMsgs.add("輸入餐廳編號格式錯誤");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
			Reservation_recordService rs = new Reservation_recordService();
			list = rs.getReservationRecordByRestNo(rest_no);
			// 如果沒找到訂位紀錄
			if (list.size() == 0) {
				errorMsgs.add("輸入餐廳編號無訂位紀錄");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 如果會員有訂位紀錄
			else {
				// System.out.println(list.size());
				request.setAttribute("list", list);// 準備轉送至查詢結果頁面

				// RequestDispatcher
				// rd=request.getRequestDispatcher("/back/reserv/query_reservation_by_mem.jsp");
				RequestDispatcher rd = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");// 準備將查詢結果顯示在同一頁面
				try {
					rd.forward(request, response);
					return; // 程式中斷
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// 如果會員編號、餐廳編號都有輸入
		else if (mem_no_str.length() != 0 && rest_no_str.length() != 0) {
			Reservation_recordService rs = new Reservation_recordService();
			Integer mem_no = null;
			Integer rest_no = null;
			List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

			try {
				mem_no = Integer.parseInt(mem_no_str);
			}
			// 如果會員編號出現非數字
			catch (NumberFormatException e) {
				errorMsgs.add("輸入會員編號格式錯誤");
				request.setAttribute("errorMsgs", errorMsgs);
			}
			try {
				rest_no = Integer.parseInt(rest_no_str);
			}
			// 如果餐廳編號出現非數字
			catch (NumberFormatException e) {
				errorMsgs.add("輸入餐廳編號格式錯誤");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			list = rs.getReservationRecord(mem_no, rest_no);
			// 如果沒找到訂位紀錄
			if (list.size() == 0) {
				errorMsgs.add("輸入會員編號無訂位紀錄");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 如果會員有訂位紀錄
			else {
				// System.out.println(list.size());
				request.setAttribute("list", list);
				RequestDispatcher rd = request
						.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
				try {
					rd.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void getStore_query(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		String action = request.getParameter("action");// 得知請求的名稱
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器

		// 如果是餐廳端頁面進行查詢
		if (action.equals("store_query")) {
			// 取得餐廳編號
			Integer rest_no = Integer.parseInt(request.getParameter("rest_no"));
			String rest_name = request.getParameter("rest_name");
			// 取得會員編號(字串型別)
			String mem_no_str = request.getParameter("mem_no");
			// 取得欲查詢日期(字串型別)
			String date = request.getParameter("calendar");
			// 取得當前日期物件
			Date current_date = new Date();

			// 如果完全沒有輸入
			if (mem_no_str.length() == 0 && date.length() == 0) {
				errorMsgs.add("請輸入會員編號或日期");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/store/reservation/queryReservation.jsp");
				try {
					dispatcher.forward(request, response);
					return; // 程式中斷
				} catch (ServletException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// 如果只輸入欲查詢日期而無會員編號
			else if (date.length() != 0 && mem_no_str.length() == 0) {
				// 調整欲查詢日期型別成Date type
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date query_date = null;
				try {
					query_date = dateFormat.parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// test
				// System.out.print(query_date);
				// 欲查詢時間與當前時間比較
				if (query_date.getTime() - current_date.getTime() > 6 * 24 * 60
						* 60 * 1000
						|| query_date.getTime() - current_date.getTime() < -1
								* 24 * 60 * 60 * 1000) {
					errorMsgs.add("輸入日期超過可查詢範圍，請重新輸入");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 以日期字串做查詢
				else {

					List<Reservation_recordVO> list = null;
					Reservation_recordService rs = new Reservation_recordService();
					list = rs.getReservationRecord(date, rest_no);
					if (list.size() == 0) {
						errorMsgs.add("輸入日期無訂位紀錄");
						request.setAttribute("errorMsgs", errorMsgs);
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("/store/reservation/queryReservation.jsp");
						try {
							dispatcher.forward(request, response);
							return; // 程式中斷
						} catch (ServletException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						// System.out.println(list.size());
						// 查詢結果轉送給查詢結果頁面
						request.setAttribute("list", list);
						// rest_no原本就在request裡面，為何要再set一次？不用set
						// request.setAttribute("rest_no", rest_no);
						// request.setAttribute("rest_name", rest_name);
						// request.setAttribute("date", date);
						RequestDispatcher rd = request
								.getRequestDispatcher("/store/reservation/queryReservationRecordByTime.jsp");
						try {
							rd.forward(request, response);
							return; // 程式中斷
						} catch (ServletException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			// 如果沒輸入欲查詢日期而有輸入會員編號
			else if (date.length() == 0 && mem_no_str.length() != 0) {

				// 取得會員編號
				Integer mem_no = null;
				try {
					mem_no = Integer.parseInt(mem_no_str);
				}
				// 如果會員編號出現非數字
				catch (NumberFormatException e) {
					errorMsgs.add("輸入會員編號格式錯誤");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// return;//中斷程式
				}
				List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
				Reservation_recordService rs = new Reservation_recordService();
				list = rs.getReservationRecord(mem_no, rest_no);
				// 如果沒找到訂位紀錄
				if (list.size() == 0) {
					errorMsgs.add("輸入會員編號無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果會員有訂位紀錄
				else {
					// System.out.println(list.size());
					request.setAttribute("list", list);
					// String today=request.getParameter("today");
					// String future_day=request.getParameter("future_day");
					// request.setAttribute("today", today);
					// request.setAttribute("future_day", future_day);
					RequestDispatcher rd = request
							.getRequestDispatcher("/store/reservation/queryReservationByMem.jsp");
					try {
						rd.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			// 如果有輸入欲查詢日期且有輸入會員編號
			else if (date.length() != 0 && mem_no_str.length() != 0) {
				Reservation_recordService rs = new Reservation_recordService();
				Integer mem_no = null;
				List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
				try {
					mem_no = Integer.parseInt(mem_no_str);
				}
				// 如果會員編號出現非數字
				catch (NumberFormatException e) {
					errorMsgs.add("輸入會員編號格式錯誤");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// return;//中斷程式
				}
				list = rs.getReservationRecord(date, mem_no, rest_no);
				// 如果沒找到訂位紀錄
				if (list.size() == 0) {
					errorMsgs.add("輸入會員編號無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果會員有訂位紀錄
				else {
					// System.out.println(list.size());
					request.setAttribute("list", list);
					RequestDispatcher rd = request
							.getRequestDispatcher("/store/reservation/queryReservationByMem.jsp");
					try {
						rd.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		// 如果是前端頁面查詢訂位紀錄
		else if (action.equals("login")) {
			// 取得會員帳號(字串型別)
			String mem_account = request.getParameter("mem_account");
			// 取得欲查詢日期(字串型別)
			// String date=request.getParameter("calendar");
			// 取得當前日期物件
			Date current_date = new Date();

			// 如果有會員帳號
			if (mem_account.length() != 0) {

				List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
				Reservation_recordService rs = new Reservation_recordService();
				list = rs.getReservationRecordByMemAccount(mem_account);
				// 如果沒找到訂位紀錄
				if (list.size() == 0) {
					errorMsgs.add("輸入會員編號無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果會員有訂位紀錄
				else {
					// System.out.println(list.size());
					request.setAttribute("list", list);
					List<String> restNameList = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						restNameList.add(rs.getRestNameByRestNo(list.get(i)
								.getRest_no()));
					}
					session.setAttribute("list", list);// 準備重導回會員頁面
					session.setAttribute("restNameList", restNameList);
					String location = (String) session.getAttribute("location");
					// System.out.println(list.size());
					try {// 重導回去
						response.sendRedirect(location);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		// 如果是後端頁面查詢訂位紀錄
		else if (action.equals("back_query")) {
			// System.out.println("aaaaa");
			// 取得會員編號(字串型別)
			String mem_no_str = request.getParameter("mem_no");
			// 取得餐廳編號(字串型別)
			String rest_no_str = request.getParameter("rest_no");
			// 取得當前日期物件
			Date current_date = new Date();

			// 如果有會員編號無餐廳編號
			if (mem_no_str.length() != 0 && rest_no_str.length() == 0) {
				Integer mem_no = Integer.parseInt(mem_no_str);
				List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
				Reservation_recordService rs = new Reservation_recordService();
				list = rs.getReservationRecord(mem_no);
				// 如果沒找到訂位紀錄
				if (list.size() == 0) {
					errorMsgs.add("輸入會員編號無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果會員有訂位紀錄
				else {
					// System.out.println(list.size());
					request.setAttribute("list", list);
					List<String> restNameList = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						restNameList.add(rs.getRestNameByRestNo(list.get(i)
								.getRest_no()));
					}

					request.setAttribute("list", list);// 準備轉送至查詢結果頁面
					request.setAttribute("restNameList", restNameList);
					RequestDispatcher rd = request
							.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
					try {
						rd.forward(request, response);
						return; // 程式中斷
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			// 如果無會員編號有餐廳編號
			else if (mem_no_str.length() == 0 && rest_no_str.length() != 0) {
				Integer mem_no = Integer.parseInt(mem_no_str);
				List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();
				Reservation_recordService rs = new Reservation_recordService();
				list = rs.getReservationRecord(mem_no);
				// 如果沒找到訂位紀錄
				if (list.size() == 0) {
					errorMsgs.add("輸入餐廳編號無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");
					try {
						dispatcher.forward(request, response);
						return; // 程式中斷
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果會員有訂位紀錄
				else {
					// System.out.println(list.size());
					request.setAttribute("list", list);
					List<String> restNameList = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						restNameList.add(rs.getRestNameByRestNo(list.get(i)
								.getRest_no()));
					}
					RequestDispatcher rd = request
							.getRequestDispatcher("/back/reserv/be_reservMgr.jsp");// 準備將查詢結果顯示在同一頁面
					try {
						rd.forward(request, response);
						return; // 程式中斷
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}
	}

	private void getReservFromAllRest(HttpServletRequest req,
			HttpServletResponse res) {
		java.sql.Date dateStr = java.sql.Date.valueOf(req.getParameter(
				"reserv_date").trim()); // 想查的日期
		Integer reserv_count = Integer.parseInt(req
				.getParameter("reserv_count")); // 想訂的人數
		Calendar c = Calendar.getInstance();
		Date date = new Date(dateStr.getTime());
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);// 選擇的那天的星期 1-7
		int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);// 選擇的當天的星期
																		// 1-7
		int now = checkTime(); // 現在的時段
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
					if (day == today) {
						for (int i = 0; i < now; i++) {
							perday.remove(0);
						}
					}
					if (perday.size() != 0) {
						int timeRemoveIndex = 0;
						for (int j = 0; j < perday.size(); j++) {
							PerTime perTime = perday.get(timeRemoveIndex);
							if ((reservRecord.getMax() - perTime.getResidual()) >= reserv_count) {
								timeRemoveIndex++;
								continue;
							}
							perday.remove(timeRemoveIndex);
						}
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
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.println(outStr);
				out.close();
			} else {
				/* WEB重導 */
				String url = "/front/search/searchRest.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交l
				successView.forward(req, res);

				return; // 程式中斷
			}
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getReservFromARest(HttpServletRequest req,
			HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		List<Object> result = null;
		try {
			Integer rest_no = Integer.parseInt(req.getParameter("rest_no"));
			java.sql.Date dateStr = java.sql.Date.valueOf(req.getParameter(
					"reserv_date").trim()); // 想查的日期
			Integer amount = Integer.parseInt(req.getParameter("amount")); // 想訂的人數
			Calendar c = Calendar.getInstance();
			Date date = new Date(dateStr.getTime());
			c.setTime(date);
			int day = c.get(Calendar.DAY_OF_WEEK);// 選擇的那天的星期 1-7
			int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);// 選擇的當天的星期
																			// 1-7
			int now = checkTime(); // 現在的時段
			RestService restSrv = new RestService();
			ReservRecord reservRecord = restSrv.getOneReserv(rest_no); // 回傳一周的訂位資訊
			ArrayList<PerDay> recordOfWeek = reservRecord.getRecordOfWeek(); // 取出訂位次數表
			PerDay perDay = recordOfWeek.get(day - 1); // 拿到查詢那天的資料
			result = new ArrayList<Object>();
			ArrayList<PerTime> recoredOfADay = perDay.getRecordOfDay();
			if (day == today) {
				for (int i = 0; i < now; i++) {
					recoredOfADay.remove(0);
				}
			}
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
				e.printStackTrace();
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			errorMsgs.add("資料輸入有誤，請再試一次");
			try {
				RequestDispatcher rd = req
						.getRequestDispatcher("/front/restaurant/fe_restInfo.jsp");
				rd.forward(req, res);
				return; // 程式中斷
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			errorMsgs.add("資料遺失，請再試一次");
		}

	}

	private void query(HttpServletRequest request, HttpServletResponse response) {

		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		// 取得餐廳編號
		Integer rest_no = Integer.parseInt(request.getParameter("rest_no"));
		// System.out.print(rest_no);
		// 取得欲查詢日期(字串型別)
		String date = request.getParameter("calendar");
		// 取得當前日期
		Date current_date = new Date();
		// 調整欲查詢日期型別成Date type
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
		Date query_date = null;
		try {
			query_date = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// test
		// System.out.print(query_date);
		// 欲查詢時間與當前時間比較
		try {
			if (query_date.getTime() - current_date.getTime() > 6 * 24 * 60
					* 60 * 1000
					|| query_date.getTime() - current_date.getTime() < -1 * 24
							* 60 * 60 * 1000) {
				errorMsgs.add("輸入日期超過可查詢範圍，請重新輸入");
				request.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/store/reservation/queryReservation.jsp");
				dispatcher.forward(request, response);
				return; // 程式中斷
			}
			// 以日期字串做查詢
			else {

				List<Reservation_recordVO> list = null;
				Reservation_recordService rs = new Reservation_recordService();
				list = rs.getReservationRecord(date, rest_no);
				if (list.size() == 0) {
					errorMsgs.add("輸入日期無訂位紀錄");
					request.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/store/reservation/queryReservation.jsp");
					dispatcher.forward(request, response);
					return; // 程式中斷
				} else {
//					System.out.println(list.size());
					// 查詢結果轉送給查詢結果頁面
					request.setAttribute("list", list);
					RequestDispatcher rd = request
							.getRequestDispatcher("/store/reservation/queryReservationRecordByDate.jsp");
					rd.forward(request, response);
					return; // 程式中斷
				}
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}

	}

	private void insert(HttpServletRequest request, HttpServletResponse response) {
		List<String> errorMsgs = new LinkedList<String>();// 儲存錯誤訊息的容器
		try {
			Reservation_recordService reservation_recordService = new Reservation_recordService();
			Integer mem_no = null;
			if ("mobile".equals(request.getParameter("device"))) {
				String mem_account = request.getParameter("mem_account");
				MemService memSrv = new MemService();
				mem_no = memSrv.getOneMem(mem_account).getMem_no();
			} else {

				mem_no = Integer.parseInt(request.getParameter("mem_no"));
			}
			Integer rest_no = Integer.parseInt(request.getParameter("rest_no"));
			Integer reserv_count = Integer.parseInt(request
					.getParameter("reserv_count")); // 訂位人數
			Integer reserv_period = Integer.parseInt(request
					.getParameter("reserv_period")); // 時段: 1~6
			String odr_seqnum = request.getParameter("odr_seqnum"); // 餐券序號
			String reserv_date = request.getParameter("reserv_date"); // "2015-01-20"
			Date date = new Date(java.sql.Date.valueOf(reserv_date).getTime());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int day = c.get(Calendar.DAY_OF_WEEK) - 1;// 選擇的那天的星期 1-7 改成0-6
			ReservRecord reservRecord = reservation_recordService
					.getReservFromARest(rest_no);
			PerDay perDay = reservRecord.getRecordOfWeek().get(day);
			Integer res = perDay.getRecordOfDay().get(reserv_period - 1)
					.getResidual();
			reservRecord.getRecordOfWeek().get(day).getRecordOfDay()
					.get(reserv_period - 1).setResidual(res + reserv_count);

			try {
				reservation_recordService.insertReservation_record(mem_no,
						rest_no, reserv_count, reserv_date, reserv_period,
						odr_seqnum, reservRecord);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("訂位失敗，請稍後再試");
				RequestDispatcher failureView = request
						.getRequestDispatcher("/front/restaurant/fe_reserv.jsp");
			}

			if (errorMsgs != null) {
				HttpSession session = request.getSession();
				List<String> reservInfo = new ArrayList<String>();
				reservInfo.add("恭喜您已成功完成訂位 !");
				reservInfo.add("訂位日期" + reserv_date);
				if ("mobile".equals(request.getParameter("device"))) {

					Gson gson = new Gson();
					String resultGson = gson.toJson(reservInfo);
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					try {
						PrintWriter out = response.getWriter();
						out.write(resultGson);
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					request.setAttribute("reservInfo", reservInfo);
					RequestDispatcher rd = request
							.getRequestDispatcher("/front/restaurant/fe_restInfo.jsp");
					rd.forward(request, response);
					return; // 程式中斷
					// response.setCharacterEncoding("UTF-8");
					// response.sendRedirect(request.getContextPath()
					// + "/front/restaurant/fe_restInfo.jsp?rest_no="
					// + rest_no);
				}
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.add("資料輸入有誤，請稍後再試");
		}
	}

	public Integer checkTime() {
		java.util.Date now = new java.util.Date();
		int nowHour = now.getHours();
		if (nowHour >= 20) {
			return 6;
		} else if (nowHour >= 19) {
			return 5;
		} else if (nowHour >= 18) {
			return 4;

		} else if (nowHour >= 13) {
			return 3;

		} else if (nowHour >= 12) {
			return 2;

		} else if (nowHour >= 11) {
			return 1;

		} else {
			return 0;
		}
	}
}
