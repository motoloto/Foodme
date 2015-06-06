package com.reserv.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.collections.bag.SynchronizedBag;

import com.rest.model.JdbcUtil_CompositeQuery_rest;
import com.rest.model.PerDay;
import com.rest.model.PerTime;
import com.rest.model.ReservRecord;
import com.rest.model.RestService;
import com.rest.model.RestVO;

public class Reservation_recordDAO implements Reservation_recordDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String GET_RESERV_FROM_ONE_REST = "select RESERVED_TOTALSET,reserved_sun1, reserved_sun2,reserved_sun3, reserved_sun4,reserved_sun5, reserved_sun6, "
			+ "reserved_mon1, reserved_mon2,reserved_mon3,reserved_mon4,reserved_mon5,reserved_mon6, "
			+ "reserved_tue1, reserved_tue2, reserved_tue3, reserved_tue4, reserved_tue5, reserved_tue6, "
			+ "reserved_wed1, reserved_wed2, reserved_wed3, reserved_wed4, reserved_wed5, reserved_wed6,"
			+ " reserved_thu1, reserved_thu2, reserved_thu3, reserved_thu4, reserved_thu5, reserved_thu6, "
			+ " reserved_fri1, reserved_fri2, reserved_fri3, reserved_fri4, reserved_fri5, reserved_fri6, "
			+ "reserved_sat1, reserved_sat2, reserved_sat3, reserved_sat4, reserved_sat5, reserved_sat6 "
			+ "FROM REST WHERE REST_NO=?";
	private static final String UPDATE_RESERV_FROM_ONE_REST = "UPDATE rest SET reserved_sun1=?, reserved_sun2=?,reserved_sun3=?, reserved_sun4=?,reserved_sun5=?, reserved_sun6=?, "
			+ "reserved_mon1=?, reserved_mon2=?,reserved_mon3=?,reserved_mon4=?,reserved_mon5=?,reserved_mon6=?, "
			+ "reserved_tue1=?, reserved_tue2=?, reserved_tue3=?, reserved_tue4=?, reserved_tue5=?, reserved_tue6=?, "
			+ "reserved_wed1=?, reserved_wed2=?, reserved_wed3=?, reserved_wed4=?, reserved_wed5=?, reserved_wed6=?,"
			+ " reserved_thu1=?, reserved_thu2=?, reserved_thu3=?, reserved_thu4=?, reserved_thu5=?, reserved_thu6=?, "
			+ " reserved_fri1=?, reserved_fri2=?, reserved_fri3=?, reserved_fri4=?, reserved_fri5=?, reserved_fri6=?, "
			+ "reserved_sat1=?, reserved_sat2=?, reserved_sat3=?, reserved_sat4=?, reserved_sat5=?, reserved_sat6=? "
			+ " WHERE REST_NO=?";

	synchronized public void insertReservation_record(
			Reservation_recordVO reserv_recordVO, ReservRecord rest_reserv) {
		Connection con = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			PreparedStatement insertRecordpstmt = con
					.prepareStatement("insert into reservation_record values"
							+ "(sequence_REC_NO.NEXTVAL,?,?,?,?,?,?,?)");
			insertRecordpstmt.setInt(1, reserv_recordVO.getMem_no());
			insertRecordpstmt.setInt(2, reserv_recordVO.getRest_no());
			insertRecordpstmt.setInt(3, reserv_recordVO.getCount());
			String dateStr = reserv_recordVO.getReservation_day().trim();
			java.sql.Date date = java.sql.Date.valueOf(dateStr);
			insertRecordpstmt.setDate(4, date);
			insertRecordpstmt.setInt(5, reserv_recordVO.getPeriod());
			insertRecordpstmt.setString(6, reserv_recordVO.getOdr_seqnum());
			insertRecordpstmt.setString(7, reserv_recordVO.getSeating());
			insertRecordpstmt.executeUpdate();

			PreparedStatement updateRespstmt = con
					.prepareStatement(UPDATE_RESERV_FROM_ONE_REST);
			List<PerDay> recordOfWeek = rest_reserv.getRecordOfWeek();
			int i = 1;
			for (PerDay perDay : recordOfWeek) {
				List<PerTime> recordOfDay = perDay.getRecordOfDay();
				for (PerTime perTime : recordOfDay) {
					updateRespstmt.setInt(i, perTime.getResidual());
					i++;
				}
			}
			updateRespstmt.setInt(43, rest_reserv.getRest_no());
			updateRespstmt.execute();
			con.commit();
			con.close();
			insertRecordpstmt.close();
			updateRespstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int update(Reservation_recordVO Res_record) {
		int i = 0;
		try {

			Connection con = ds.getConnection();

			PreparedStatement pstmt = con
					.prepareStatement("update reservation_record set MEM_NO=?,REST_NO=?,COUNT=?,"
							+ "Reservation_day=to_date(?,'yyyy-mm-dd'), Period=? ,ODR_SEQNUM=? ,SEATING=? where REC_NO=?");

			pstmt.setInt(1, Res_record.getMem_no());
			pstmt.setInt(2, Res_record.getRest_no());
			pstmt.setInt(3, Res_record.getCount());
			pstmt.setString(4, Res_record.getReservation_day());
			pstmt.setInt(5, Res_record.getPeriod());
			pstmt.setString(6, Res_record.getOdr_seqnum());
			pstmt.setString(7, Res_record.getSeating());
			pstmt.setInt(8, Res_record.getRec_no());

			i = pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public int delete(Integer Rec_no) {
		int i = 0;
		try {
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("delete from reservation_record WHERE Rec_no=?");
			pstmt.setInt(1, Rec_no);
			i = pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Reservation_recordVO findByPrimaryKey(Integer Rec_no) {
		Reservation_recordVO result = new Reservation_recordVO();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record "
							+ "where rec_no=?");
			pstmt.setInt(1, Rec_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.setRec_no(rs.getInt(1));
				result.setMem_no(rs.getInt(2));
				result.setRest_no(rs.getInt(3));
				result.setCount(rs.getInt(4));
				result.setReservation_day(rs.getString(5).substring(0, 10));
				result.setPeriod(rs.getInt(6));
				result.setOdr_seqnum(rs.getString(7));
				result.setSeating(rs.getString(8));
			}
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// 根據輸入日期查詢當天訂位紀錄
	public List<Reservation_recordVO> findRecordByReservationTime(String str,
			Integer rest_no) {
		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

		Connection con;
		try {
			con = ds.getConnection();
			// SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record where RESERVATION_DAY >=to_date(?,'YYYY-mm-dd') and reservation_day<to_date(?,'YYYY-mm-dd') and rest_no=?");

			pstmt.setString(1, str);
			// 取得日期為查詢日期隔天的日期物件
			// 先將查詢日期字串轉成日期物件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date query_date = sdf.parse(str);
			// 將查詢日期物件的日期加一天
			Date tomorrow = new Date(query_date.getTime() + 24 * 60 * 60 * 1000);
			// 再將查詢日期物件(已加一天)型別轉成String type
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String tomorrow_str = dateFormat.format(tomorrow);
			// System.out.println(str);
			// System.out.println(tomorrow_str);
			pstmt.setString(2, tomorrow_str);
			pstmt.setInt(3, rest_no);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				vo.setMem_no(rs.getInt("mem_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
				list.add(vo);
			}
			// System.out.println(list.size());
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Reservation_recordVO> getAll() {
		List<Reservation_recordVO> result = new ArrayList<Reservation_recordVO>();
		// Res_recordVO vo=new Res_recordVO();
		try {
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt(1));
				vo.setMem_no(rs.getInt(2));
				vo.setRest_no(rs.getInt(3));
				vo.setCount(rs.getInt(4));
				vo.setReservation_day(rs.getString(5));
				vo.setPeriod(rs.getInt(6));
				vo.setOdr_seqnum(rs.getString(7));
				vo.setSeating(rs.getString(8));
				result.add(vo);
			}
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
	}

	@Override
	public List<Reservation_recordVO> getAllByMem(Integer mem_no) {
		// TODO Auto-generated method stub
		return null;
	}

	public ReservRecord getReservFromARest(Integer rest_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservRecord reservRecord = new ReservRecord(); // 要回傳的資料
		ArrayList<PerDay> recordOfWeek = new ArrayList<PerDay>(); // 要回傳的一周的訂位紀錄
		PerDay recordOfaDay = new PerDay(); // 一天的訂位紀錄

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RESERV_FROM_ONE_REST);
			pstmt.setInt(1, rest_no);
			rs = pstmt.executeQuery();

			ArrayList<PerTime> aday = new ArrayList<PerTime>(); // 用來暫存一天的紀錄
			PerTime perTime = null;

			int max = 0; // 最大可定位數
			String[] times = { "11:00", "12:00", "13:00", "18:00", "19:00",
					"20:00" };
			String[] days = { "Monday", "TuesDay", "Wednesday", "ThursDay",
					"Friday", "Saturday", "Sunday" };
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (i == 1) {
						max = rs.getInt(i);
						reservRecord.setMax(max);
					} else {
						String b = times[(i - 2) % 6]; // 時段名稱
						int a = rs.getInt(i); // 時段的訂位數
						perTime = new PerTime(b, a); // 做成單一時間
						aday.add(perTime); // 加進暫存一天的資料夾

						if ((i - 1) % 6 == 0) {
							recordOfaDay.setRecordOfDay(aday); // 每六個就把一天的資料夾放進去
							recordOfaDay.setDay(days[(i - 2) % 6]); // 並寫上星期
							recordOfWeek.add(recordOfaDay); // 將一天的紀錄放進要回傳的資料夾中
							recordOfaDay = new PerDay(); // 重置
							aday = new ArrayList<PerTime>(); // 重置
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		reservRecord.setRecordOfWeek(recordOfWeek);
		reservRecord.setRest_no(rest_no);
		;
		return reservRecord;
	}

	public List<ReservRecord> getReservFromAllRest(Map<String, String[]> map) {
		String[] times = { "11:00", "12:00", "13:00", "18:00", "19:00", "20:00" };
		String[] days = { "Sunday", "Monday", "TuesDay", "Wednesday",
				"ThursDay", "Friday", "Saturday" };
		ArrayList<PerTime> aday = new ArrayList<PerTime>(); // 用來暫存一天的紀錄
		PerTime perTime = null;
		List<ReservRecord> reservRecordList = new ArrayList<ReservRecord>();
		ArrayList<PerDay> recordOfWeek = new ArrayList<PerDay>(); // 要回傳的一周的訂位紀錄
		PerDay recordOfaDay = new PerDay(); // 一天的訂位紀錄
		String finalSQL = "select  distinct   "
				+ " rest.rest_no, rest_name, rest_addr, rest_tel, rest_opentime, rest_waitmin, RESERVED_TOTALSET,   "
				+ "           reserved_sun1, reserved_sun2, reserved_sun3, reserved_sun4, reserved_sun5, reserved_sun6,"
				+ "			reserved_mon1, reserved_mon2, reserved_mon3, reserved_mon4, reserved_mon5, reserved_mon6, "
				+ "			reserved_tue1, reserved_tue2, reserved_tue3, reserved_tue4, reserved_tue5, reserved_tue6, "
				+ "			reserved_wed1, reserved_wed2, reserved_wed3, reserved_wed4, reserved_wed5, reserved_wed6,"
				+ "	    	reserved_thu1, reserved_thu2, reserved_thu3, reserved_thu4, reserved_thu5, reserved_thu6, "
				+ "			reserved_fri1, reserved_fri2, reserved_fri3, reserved_fri4, reserved_fri5, reserved_fri6, "
				+ "			reserved_sat1, reserved_sat2, reserved_sat3, reserved_sat4, reserved_sat5, reserved_sat6 "
				+ "			from   REST,REST_CLASS   where rest.rest_no=rest_class.rest_no"
				+ JdbcUtil_CompositeQuery_rest.get_WhereCondition(map)
				+ "order by rest.rest_no";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
//			System.out.print(finalSQL);
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReservRecord reservRecord = new ReservRecord(); // 抓取單獨一家的資料
				recordOfWeek = new ArrayList<PerDay>(); // 重置一周訂位紀錄的暫存
				int timeCount = 0;
				int dayCount = 0;
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if ("REST_NO".equals(rs.getMetaData().getColumnName(i))) {
						int rest_no = rs.getInt(i);
						reservRecord.setRest_no(rest_no);
					} else if ("REST_NAME".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setRest_name(rs.getString(i));
					} else if ("REST_ADDR".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setRest_addr((rs.getString(i)));
					} else if ("REST_TEL".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setRest_tel((rs.getString(i)));
					} else if ("REST_OPENTIME".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setRest_opentime((rs.getString(i)));
					} else if ("REST_WAITMIN".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setRest_waitmin((rs.getInt(i)));
					} else if ("RESERVED_TOTALSET".equals(rs.getMetaData()
							.getColumnName(i))) {
						reservRecord.setMax(rs.getInt(i));
					} else {
						/* 訂位資料的處理 */
						String timeLabel = times[(timeCount) % 6]; // 時段名稱
						int residual = rs.getInt(i); // 時段的訂位數
						perTime = new PerTime(timeLabel, residual); // 做成單一時間
						aday.add(perTime); // 加進暫存一天的資料夾

						if ((timeCount + 1) % 6 == 0) {

							recordOfaDay.setRecordOfDay(aday); // 每六個就把一天的資料夾放進去
							recordOfaDay.setDay(days[dayCount]); // 並寫上星期幾
							recordOfWeek.add(recordOfaDay); // 將一天的紀錄放進要一周的紀錄中
							recordOfaDay = new PerDay(); // 重置
							aday = new ArrayList<PerTime>(); // 重置
							dayCount++;
							if (recordOfWeek.size() == 7) { // 七天的資料拿到後存入
								reservRecord.setRecordOfWeek(recordOfWeek); // 一家餐廳的全部資料
							}
						}
						timeCount++;
					}
				}
				reservRecordList.add(reservRecord); // 放進餐廳清單中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return reservRecordList;
	}

	public void updateReservFromARest(ReservRecord rest_reserv) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_RESERV_FROM_ONE_REST);
			List<PerDay> recordOfWeek = rest_reserv.getRecordOfWeek();
			int i = 1;
			for (PerDay perDay : recordOfWeek) {
				List<PerTime> recordOfDay = perDay.getRecordOfDay();
				for (PerTime perTime : recordOfDay) {
					pstmt.setInt(i, perTime.getResidual());
					i++;
				}
			}
			pstmt.setInt(43, rest_reserv.getRest_no());
			pstmt.executeQuery();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Reservation_recordVO> findRecordByMemNo(Integer mem_no,
			Integer rest_no) {
		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

		Connection con;
		try {
			con = ds.getConnection();
			// SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record where mem_no=? and rest_no=? and reservation_day>=to_date(?,'yyyy-mm-dd')");

			pstmt.setInt(1, mem_no);
			pstmt.setInt(2, rest_no);
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// MM不能小寫
			String str = df.format(today);
			pstmt.setString(3, str);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				vo.setMem_no(rs.getInt("mem_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
				list.add(vo);
			}
			// System.out.println(list.size());
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 後端利用會員編號查詢訂位紀錄
	public List<Reservation_recordVO> findRecordByMemNo(Integer mem_no) {
		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

		Connection con;
		try {
			con = ds.getConnection();
			// SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record where mem_no=? and reservation_day>=to_date(?,'yyyy-mm-dd')");

			pstmt.setInt(1, mem_no);
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// MM不能小寫
			String str = df.format(today);
			pstmt.setString(2, str);
			ResultSet rs = pstmt.executeQuery();
			RestService restSrv =new RestService();
			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				RestVO restVO=restSrv.getOneRest(rs.getInt("rest_no"));
				vo.setRest_name(restVO.getRest_name()); 
				vo.setMem_no(rs.getInt("mem_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
				list.add(vo);
			}
			// System.out.println(list.size());
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Reservation_recordVO> findRecordByMemNoAndTime(String date,
			Integer mem_no, Integer rest_no) {
		List<Reservation_recordVO> list = new ArrayList<Reservation_recordVO>();

		Connection con;
		try {
			con = ds.getConnection();
			// SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題
			PreparedStatement pstmt = con
					.prepareStatement("select * from reservation_record where mem_no=? and rest_no=? "
							+ "and reservation_day>=to_date(?,'yyyy-mm-dd') and reservation_day<to_date(?,'yyyy-mm-dd')");

			pstmt.setInt(1, mem_no);
			pstmt.setInt(2, rest_no);
			pstmt.setString(3, date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 將查詢日期字串轉成日期物件
			Date query_date = sdf.parse(date);
			// 將查詢日期物件的日期加一天
			Date tomorrow = new Date(query_date.getTime() + 24 * 60 * 60 * 1000);
			// 再將查詢日期物件(已加一天)型別轉成String type
			String tomorrow_str = sdf.format(tomorrow);
			pstmt.setString(4, tomorrow_str);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				vo.setMem_no(rs.getInt("mem_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
				list.add(vo);
			}
			// System.out.println(list.size());
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Reservation_recordVO> findRecordByMemAccount(String mem_account) {
		List<Reservation_recordVO> result = new ArrayList<Reservation_recordVO>();
		Connection con;
		try {
			con = ds.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("select rec_no, rest_no,count,reservation_day, "
							+ "period,odr_seqnum,seating from reservation_record "
							+ "where mem_no=(select mem_no from member where mem_account=?)"
							+ "and reservation_day>=to_date(?,'yyyy-mm-dd')");

			pstmt.setString(1, mem_account);
			// 查詢到的訂位紀錄要從今天開始起算
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// MM不能小寫
			String str = df.format(today);
			pstmt.setString(2, str);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reservation_recordVO vo = new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
				result.add(vo);
			}
			// System.out.println(result.size());
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 前端、後端利用餐廳編號取得餐廳名稱
	public String findRestNameByRestNo(Integer rest_no) {
		String result = null;
		Connection con;

		try {
			con = ds.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("select rest_name from rest where rest_no=?");
			pstmt.setInt(1, rest_no);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getString(1);
			}
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public List<Reservation_recordVO> findRecordByRestNo(Integer rest_no) {
		List<Reservation_recordVO> list=new ArrayList<Reservation_recordVO>();
		    	
		    	Connection con;
				try 
				{
					con = ds.getConnection();
					//SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題		
					PreparedStatement pstmt=con.prepareStatement("select * from reservation_record where rest_no=? and reservation_day>=to_date(?,'yyyy-mm-dd')");
					
					pstmt.setInt(1,rest_no);		
					Date today=new Date();
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd");//MM不能小寫
					String str=df.format(today);
					pstmt.setString(2, str);
					ResultSet rs=pstmt.executeQuery();
					
					while(rs.next())
					{				
						Reservation_recordVO vo=new Reservation_recordVO();
						vo.setRec_no(rs.getInt("rec_no"));
						vo.setMem_no(rs.getInt("mem_no"));
						vo.setRest_no(rs.getInt("rest_no"));
						vo.setCount(rs.getInt("count"));
						vo.setReservation_day(rs.getString("reservation_day").substring(0, 10));
						vo.setPeriod(rs.getInt("period"));
						vo.setOdr_seqnum(rs.getString("odr_seqnum"));
						vo.setSeating(rs.getString("seating"));
						list.add(vo);
					}
					//System.out.println(list.size());
					pstmt.close();
					con.close();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    	return list;		
			}

	@Override
	//餐廳端修改是否入座的方法(根據訂位日期、時段、會員編號)
		public Integer changeSeating(String seating,String date,Integer period,Integer mem_no)
		{
			Connection con;
			int result=0;
			try 
			{			
				con = ds.getConnection();
				
				//SQL指令遇到字串要轉日期，不要用強制轉型，用函數去轉，不然驅動程式解析指令會有問題		
				PreparedStatement pstmt=con.prepareStatement("update reservation_record set "
						+ "seating=? where reservation_day=to_date(?,'yyyy-MM-dd') and period=? and mem_no=?");
				if(seating.equals("1"))
				{
					pstmt.setString(1,"0");	
				}	
				else
				{
					pstmt.setString(1,"1");	
				}
				pstmt.setString(2, date.substring(0, 10));			
				pstmt.setInt(3, period);
				pstmt.setInt(4, mem_no);
				
				result=pstmt.executeUpdate();//執行到這停止並出現wait for localhost：開debugger模式找出執行到哪個專案			
				pstmt.close();
				con.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return result;
		}
}
