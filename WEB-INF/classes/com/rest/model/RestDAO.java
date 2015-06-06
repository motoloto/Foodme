package com.rest.model;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.manager.model.ManagerVO;
import com.rest.model.PerTime;

public class RestDAO implements RestDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String UP_STMT	= "UPDATE AFFILIATE set affi_state=1 where affi_no = ?";
	private static final String INSERT_STMT = "INSERT INTO REST ( rest_no, affi_no, rest_account ,	rest_psw ,	bus_no , rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web , rest_intro ,	rest_photo) VALUES (rest_no_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM REST order by rest_no";
	private static final String GET_ONE_STMT = "SELECT rest_no ,	affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,rest_intro,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset FROM REST where rest_no = ?";
	private static final String DELETE = "DELETE FROM REST where rest_no = ?";
	private static final String CHECK_ONE_STMT = "SELECT  *  FROM rest  WHERE  rest_account = ? ";
	private static final String UPDATE = "UPDATE REST set affi_no =?,	rest_account =?,	rest_psw =?,	bus_no =?,	rest_name =?,	rest_addr =?,	rest_tel =?,	rest_mail =?,	rest_web =?,	rest_opentime =?,	rest_photo =?,	rest_state =?,	rest_contra =?,	rest_waitmin =?,	scor_pri =?,	scor_pritms =?,	scor_hea =?,	scor_heatms =?,	scor_cook =?,	scor_cooktms =?,	scor_envisco =?,	scor_envtms =?,	scor_serv =?,	scor_servtms =?,	reserved_mon1 =?,	reserved_mon2 =?,	reserved_mon3 =?,	reserved_mon4 =?,	reserved_mon5 =?,	reserved_mon6 =?,	reserved_tue1 =?,	reserved_tue2 =?,	reserved_tue3 =?,	reserved_tue4 =?,	reserved_tue5 =?,	reserved_tue6 =?,	reserved_wed1 =?,	reserved_wed2 =?,	reserved_wed3 =?,	reserved_wed4 =?,	reserved_wed5 =?,	reserved_wed6 =?,	reserved_thu1 =?,	reserved_thu2 =?,	reserved_thu3 =?,	reserved_thu4 =?,	reserved_thu5 =?,	reserved_thu6 =?,	reserved_fri1 =?,	reserved_fri2 =?,	reserved_fri3 =?,	reserved_fri4 =?,	reserved_fri5 =?,	reserved_fri6 =?,	reserved_sat1 =?,	reserved_sat2 =?,	reserved_sat3 =?,	reserved_sat4 =?,	reserved_sat5 =?,	reserved_sat6 =?,	reserved_sun1 =?,	reserved_sun2 =?,	reserved_sun3 =?,	reserved_sun4 =?,	reserved_sun5 =?,	reserved_sun6 =?,	reserved_totalset =? where rest_no = ?";
	private static final String UPDATE_STATE = "UPDATE REST SET rest_state=? where rest_no=? ";
	private static final String GET_RESERV_FROM_ONE = "select RESERVED_TOTALSET,reserved_sun1, reserved_sun2,reserved_sun3, reserved_sun4,reserved_sun5, reserved_sun6, "
			+ "reserved_mon1, reserved_mon2,reserved_mon3,reserved_mon4,reserved_mon5,reserved_mon6, "
			+ "reserved_tue1, reserved_tue2, reserved_tue3, reserved_tue4, reserved_tue5, reserved_tue6, "
			+ "reserved_wed1, reserved_wed2, reserved_wed3, reserved_wed4, reserved_wed5, reserved_wed6,"
			+ " reserved_thu1, reserved_thu2, reserved_thu3, reserved_thu4, reserved_thu5, reserved_thu6, "
			+ " reserved_fri1, reserved_fri2, reserved_fri3, reserved_fri4, reserved_fri5, reserved_fri6, "
			+ "reserved_sat1, reserved_sat2, reserved_sat3, reserved_sat4, reserved_sat5, reserved_sat6 "
			+ "FROM REST WHERE REST_NO=?";
	private static final String UPDATE_RESERV_FROM_ONE = "UPDATE rest SET RESERVED_TOTALSET=?,reserved_sun1=?, reserved_sun2=?,reserved_sun3=?, reserved_sun4=?,reserved_sun5=?, reserved_sun6=?, "
			+ "reserved_mon1=?, reserved_mon2=?,reserved_mon3=?,reserved_mon4=?,reserved_mon5=?,reserved_mon6=?, "
			+ "reserved_tue1=?, reserved_tue2=?, reserved_tue3=?, reserved_tue4=?, reserved_tue5=?, reserved_tue6=?, "
			+ "reserved_wed1=?, reserved_wed2=?, reserved_wed3=?, reserved_wed4=?, reserved_wed5=?, reserved_wed6=?,"
			+ " reserved_thu1=?, reserved_thu2=?, reserved_thu3=?, reserved_thu4=?, reserved_thu5=?, reserved_thu6=?, "
			+ " reserved_fri1=?, reserved_fri2=?, reserved_fri3=?, reserved_fri4=?, reserved_fri5=?, reserved_fri6=?, "
			+ "reserved_sat1=?, reserved_sat2=?, reserved_sat3=?, reserved_sat4=?, reserved_sat5=?, reserved_sat6=? "
			+ " WHERE REST_NO=?";
	private static final String GET_ALL_SCORE = "SELECT rest_no,rest_name,  scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms FROM rest  ORDER BY rest_no";
	 private static final String UPDATE_RES="UPDATE REST set reserved_mon1=?, reserved_mon2=?, reserved_mon3=?,"
	    		+ "reserved_mon4=?, reserved_mon5=?, reserved_mon6=?, reserved_tue1=?, reserved_tue2=?, "
	    		+ "reserved_tue3=?, reserved_tue4=?, reserved_tue5=?, reserved_tue6=?,"
	    		+ "reserved_wed1=?, reserved_wed2=?, reserved_wed3=?, reserved_wed4=?, reserved_wed5=?, reserved_wed6=?,"
	    		+ "reserved_thu1 =?, reserved_thu2 =?, reserved_thu3 =?, reserved_thu4 =?, reserved_thu5 =?, reserved_thu6 =?,"
	    		+ "reserved_fri1=?, reserved_fri2=?, reserved_fri3=?, reserved_fri4=?, reserved_fri5=?, reserved_fri6=?,"
	    		+ "reserved_sat1 =?, reserved_sat2 =?, reserved_sat3 =?, reserved_sat4 =?, reserved_sat5 =?, reserved_sat6 =?,"
	    		+ "reserved_sun1 =?, reserved_sun2 =?, reserved_sun3 =?, reserved_sun4 =?, reserved_sun5 =?, reserved_sun6 =?"
	    		+ "where rest_no=?";
	
	public ReservRecord getOneReserv(Integer rest_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservRecord reservRecord = new ReservRecord(); // 要回傳的資料
		ArrayList<PerDay> recordOfWeek = new ArrayList<PerDay>(); // 要回傳的一周的訂位紀錄
		PerDay recordOfaDay = new PerDay(); // 一天的訂位紀錄

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RESERV_FROM_ONE);
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
		return reservRecord;
	}

	@Override
	public void insert(RestVO restVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);			
			
			pstmt.setInt(1, restVO.getAffi_no());
			pstmt.setString(2, restVO.getRest_account());
			pstmt.setString(3, restVO.getRest_psw());
			pstmt.setString(4, restVO.getBus_no());
			pstmt.setString(5, restVO.getRest_name());
			pstmt.setString(6, restVO.getRest_addr());
			pstmt.setString(7, restVO.getRest_tel());
			pstmt.setString(8, restVO.getRest_mail());
			pstmt.setString(9, restVO.getRest_web());
			pstmt.setString(10, restVO.getRest_intro());
			pstmt.setBytes(11, restVO.getRest_photo());
			
			pstmt.executeUpdate();
			
			pstmt2 = con.prepareStatement(UP_STMT);			//通過加盟申請，讓AFFI table 中的狀態改為1(通過)
			pstmt2.setInt(1, restVO.getAffi_no());
			pstmt2.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public void update(RestVO restVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try
		{
			con=ds.getConnection();
			pstmt=con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, restVO.getAffi_no());
			pstmt.setString(2, restVO.getRest_account());
			pstmt.setString(3, restVO.getRest_psw());
			pstmt.setString(4, restVO.getBus_no());
			pstmt.setString(5, restVO.getRest_name());
			pstmt.setString(6, restVO.getRest_addr());
			pstmt.setString(7, restVO.getRest_tel());
			pstmt.setString(8, restVO.getRest_mail());
			pstmt.setString(9, restVO.getRest_web());
			pstmt.setString(10, restVO.getRest_opentime());
			InputStream myInputStream = new ByteArrayInputStream(
					restVO.getRest_photo());//圖檔型別是byte array 故用bytearrayInputstream來new物件
			pstmt.setBinaryStream(11, myInputStream);
			pstmt.setString(12, restVO.getRest_state());
			pstmt.setString(13, restVO.getRest_contra());
			pstmt.setInt(14, restVO.getRest_waitmin());
			pstmt.setInt(15, restVO.getScor_pri());
			pstmt.setInt(16, restVO.getScor_pritms());
			pstmt.setInt(17, restVO.getScor_hea());
			pstmt.setInt(18, restVO.getScor_heatms());
			pstmt.setInt(19, restVO.getScor_cook());
			pstmt.setInt(20, restVO.getScor_cooktms());
			pstmt.setInt(21, restVO.getScor_envisco());
			pstmt.setInt(22, restVO.getScor_envtms());
			pstmt.setInt(23, restVO.getScor_serv());
			pstmt.setInt(24, restVO.getScor_servtms());
			pstmt.setInt(25, restVO.getReserved_mon1());
			pstmt.setInt(26, restVO.getReserved_mon2());
			pstmt.setInt(27, restVO.getReserved_mon3());
			pstmt.setInt(28, restVO.getReserved_mon4());
			pstmt.setInt(29, restVO.getReserved_mon5());
			pstmt.setInt(30, restVO.getReserved_mon6());
			pstmt.setInt(31, restVO.getReserved_tue1());
			pstmt.setInt(32, restVO.getReserved_tue2());
			pstmt.setInt(33, restVO.getReserved_tue3());
			pstmt.setInt(34, restVO.getReserved_tue4());
			pstmt.setInt(35, restVO.getReserved_tue5());
			pstmt.setInt(36, restVO.getReserved_tue6());
			pstmt.setInt(37, restVO.getReserved_wed1());
			pstmt.setInt(38, restVO.getReserved_wed2());
			pstmt.setInt(39, restVO.getReserved_wed3());
			pstmt.setInt(40, restVO.getReserved_wed4());
			pstmt.setInt(41, restVO.getReserved_wed5());
			pstmt.setInt(42, restVO.getReserved_wed6());
			pstmt.setInt(43, restVO.getReserved_thu1());
			pstmt.setInt(44, restVO.getReserved_thu2());
			pstmt.setInt(45, restVO.getReserved_thu3());
			pstmt.setInt(46, restVO.getReserved_thu4());
			pstmt.setInt(47, restVO.getReserved_thu5());
			pstmt.setInt(48, restVO.getReserved_thu6());
			pstmt.setInt(49, restVO.getReserved_fri1());
			pstmt.setInt(50, restVO.getReserved_fri2());
			pstmt.setInt(51, restVO.getReserved_fri3());
			pstmt.setInt(52, restVO.getReserved_fri4());
			pstmt.setInt(53, restVO.getReserved_fri5());
			pstmt.setInt(54, restVO.getReserved_fri6());
			pstmt.setInt(55, restVO.getReserved_sat1());
			pstmt.setInt(56, restVO.getReserved_sat2());
			pstmt.setInt(57, restVO.getReserved_sat3());
			pstmt.setInt(58, restVO.getReserved_sat4());
			pstmt.setInt(59, restVO.getReserved_sat5());
			pstmt.setInt(60, restVO.getReserved_sat6());
			pstmt.setInt(61, restVO.getReserved_sun1());
			pstmt.setInt(62, restVO.getReserved_sun2());
			pstmt.setInt(63, restVO.getReserved_sun3());
			pstmt.setInt(64, restVO.getReserved_sun4());
			pstmt.setInt(65, restVO.getReserved_sun5());
			pstmt.setInt(66, restVO.getReserved_sun6());
			pstmt.setInt(67, restVO.getReserved_totalset());
			pstmt.setInt(68, restVO.getRest_no());
			
			pstmt.executeUpdate();
			
		} 
		catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	@Override
	public void delete(Integer rest_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, rest_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public RestVO findByPrimaryKey(Integer rest_no) {

		RestVO restVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, rest_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				restVO = new RestVO();
				restVO.setRest_no(rs.getInt("rest_no"));
				restVO.setRest_intro(rs.getString("rest_intro"));
				restVO.setAffi_no(rs.getInt("affi_no"));
				restVO.setRest_account(rs.getString("rest_account"));
				restVO.setRest_psw(rs.getString("rest_psw"));
				restVO.setBus_no(rs.getString("bus_no"));
				restVO.setRest_name(rs.getString("rest_name"));
				restVO.setRest_addr(rs.getString("rest_addr"));
				restVO.setRest_tel(rs.getString("rest_tel"));
				restVO.setRest_mail(rs.getString("rest_mail"));
				restVO.setRest_web(rs.getString("rest_web"));
				restVO.setRest_opentime(rs.getString("rest_opentime"));
				restVO.setRest_photo(rs.getBytes("rest_photo"));
				restVO.setRest_state(rs.getString("rest_state"));
				restVO.setRest_contra(rs.getString("rest_contra"));
				restVO.setRest_waitmin(rs.getInt("rest_waitmin"));
				restVO.setScor_pri(rs.getInt("scor_pri"));
				restVO.setScor_pritms(rs.getInt("scor_pritms"));
				restVO.setScor_hea(rs.getInt("scor_hea"));
				restVO.setScor_heatms(rs.getInt("scor_heatms"));
				restVO.setScor_cook(rs.getInt("scor_cook"));
				restVO.setScor_cooktms(rs.getInt("scor_cooktms"));
				restVO.setScor_envisco(rs.getInt("scor_envisco"));
				restVO.setScor_envtms(rs.getInt("scor_envtms"));
				restVO.setScor_serv(rs.getInt("scor_serv"));
				restVO.setScor_servtms(rs.getInt("scor_servtms"));
				restVO.setReserved_mon1(rs.getInt("reserved_mon1"));
				restVO.setReserved_mon2(rs.getInt("reserved_mon2"));
				restVO.setReserved_mon3(rs.getInt("reserved_mon3"));
				restVO.setReserved_mon4(rs.getInt("reserved_mon4"));
				restVO.setReserved_mon5(rs.getInt("reserved_mon5"));
				restVO.setReserved_mon6(rs.getInt("reserved_mon6"));
				restVO.setReserved_tue1(rs.getInt("reserved_tue1"));
				restVO.setReserved_tue2(rs.getInt("reserved_tue2"));
				restVO.setReserved_tue3(rs.getInt("reserved_tue3"));
				restVO.setReserved_tue4(rs.getInt("reserved_tue4"));
				restVO.setReserved_tue5(rs.getInt("reserved_tue5"));
				restVO.setReserved_tue6(rs.getInt("reserved_tue6"));
				restVO.setReserved_wed1(rs.getInt("reserved_wed1"));
				restVO.setReserved_wed2(rs.getInt("reserved_wed2"));
				restVO.setReserved_wed3(rs.getInt("reserved_wed3"));
				restVO.setReserved_wed4(rs.getInt("reserved_wed4"));
				restVO.setReserved_wed5(rs.getInt("reserved_wed5"));
				restVO.setReserved_wed6(rs.getInt("reserved_wed6"));
				restVO.setReserved_thu1(rs.getInt("reserved_thu1"));
				restVO.setReserved_thu2(rs.getInt("reserved_thu2"));
				restVO.setReserved_thu3(rs.getInt("reserved_thu3"));
				restVO.setReserved_thu4(rs.getInt("reserved_thu4"));
				restVO.setReserved_thu5(rs.getInt("reserved_thu5"));
				restVO.setReserved_thu6(rs.getInt("reserved_thu6"));
				restVO.setReserved_fri1(rs.getInt("reserved_fri1"));
				restVO.setReserved_fri2(rs.getInt("reserved_fri2"));
				restVO.setReserved_fri3(rs.getInt("reserved_fri3"));
				restVO.setReserved_fri4(rs.getInt("reserved_fri4"));
				restVO.setReserved_fri5(rs.getInt("reserved_fri5"));
				restVO.setReserved_fri6(rs.getInt("reserved_fri6"));
				restVO.setReserved_sat1(rs.getInt("reserved_sat1"));
				restVO.setReserved_sat2(rs.getInt("reserved_sat2"));
				restVO.setReserved_sat3(rs.getInt("reserved_sat3"));
				restVO.setReserved_sat4(rs.getInt("reserved_sat4"));
				restVO.setReserved_sat5(rs.getInt("reserved_sat5"));
				restVO.setReserved_sat6(rs.getInt("reserved_sat6"));
				restVO.setReserved_sun1(rs.getInt("reserved_sun1"));
				restVO.setReserved_sun2(rs.getInt("reserved_sun2"));
				restVO.setReserved_sun3(rs.getInt("reserved_sun3"));
				restVO.setReserved_sun4(rs.getInt("reserved_sun4"));
				restVO.setReserved_sun5(rs.getInt("reserved_sun5"));
				restVO.setReserved_sun6(rs.getInt("reserved_sun6"));
				restVO.setReserved_totalset(rs.getInt("reserved_totalset"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return restVO;
	}

	@Override
	public List<RestVO> getAll() {
		List<RestVO> list = new ArrayList<RestVO>();
		RestVO restVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// restVO �]�٬� Domain objects
				restVO = new RestVO();

				restVO.setRest_no(rs.getInt("rest_no"));
				restVO.setAffi_no(rs.getInt("affi_no"));
				restVO.setRest_account(rs.getString("rest_account"));
				restVO.setRest_psw(rs.getString("rest_psw"));
				restVO.setBus_no(rs.getString("bus_no"));
				restVO.setRest_name(rs.getString("rest_name"));
				restVO.setRest_addr(rs.getString("rest_addr"));
				restVO.setRest_tel(rs.getString("rest_tel"));
				restVO.setRest_mail(rs.getString("rest_mail"));
				restVO.setRest_web(rs.getString("rest_web"));
				restVO.setRest_opentime(rs.getString("rest_opentime"));
				restVO.setRest_photo(rs.getBytes("rest_photo"));
				restVO.setRest_state(rs.getString("rest_state"));
				restVO.setRest_contra(rs.getString("rest_contra"));
				restVO.setRest_waitmin(rs.getInt("rest_waitmin"));
				restVO.setScor_pri(rs.getInt("scor_pri"));
				restVO.setScor_pritms(rs.getInt("scor_pritms"));
				restVO.setScor_hea(rs.getInt("scor_hea"));
				restVO.setScor_heatms(rs.getInt("scor_heatms"));
				restVO.setScor_cook(rs.getInt("scor_cook"));
				restVO.setScor_cooktms(rs.getInt("scor_cooktms"));
				restVO.setScor_envisco(rs.getInt("scor_envisco"));
				restVO.setScor_envtms(rs.getInt("scor_envtms"));
				restVO.setScor_serv(rs.getInt("scor_serv"));
				restVO.setScor_servtms(rs.getInt("scor_servtms"));
				restVO.setReserved_mon1(rs.getInt("reserved_mon1"));
				restVO.setReserved_mon2(rs.getInt("reserved_mon2"));
				restVO.setReserved_mon3(rs.getInt("reserved_mon3"));
				restVO.setReserved_mon4(rs.getInt("reserved_mon4"));
				restVO.setReserved_mon5(rs.getInt("reserved_mon5"));
				restVO.setReserved_mon6(rs.getInt("reserved_mon6"));
				restVO.setReserved_tue1(rs.getInt("reserved_tue1"));
				restVO.setReserved_tue2(rs.getInt("reserved_tue2"));
				restVO.setReserved_tue3(rs.getInt("reserved_tue3"));
				restVO.setReserved_tue4(rs.getInt("reserved_tue4"));
				restVO.setReserved_tue5(rs.getInt("reserved_tue5"));
				restVO.setReserved_tue6(rs.getInt("reserved_tue6"));
				restVO.setReserved_wed1(rs.getInt("reserved_wed1"));
				restVO.setReserved_wed2(rs.getInt("reserved_wed2"));
				restVO.setReserved_wed3(rs.getInt("reserved_wed3"));
				restVO.setReserved_wed4(rs.getInt("reserved_wed4"));
				restVO.setReserved_wed5(rs.getInt("reserved_wed5"));
				restVO.setReserved_wed6(rs.getInt("reserved_wed6"));
				restVO.setReserved_thu1(rs.getInt("reserved_thu1"));
				restVO.setReserved_thu2(rs.getInt("reserved_thu2"));
				restVO.setReserved_thu3(rs.getInt("reserved_thu3"));
				restVO.setReserved_thu4(rs.getInt("reserved_thu4"));
				restVO.setReserved_thu5(rs.getInt("reserved_thu5"));
				restVO.setReserved_thu6(rs.getInt("reserved_thu6"));
				restVO.setReserved_fri1(rs.getInt("reserved_fri1"));
				restVO.setReserved_fri2(rs.getInt("reserved_fri2"));
				restVO.setReserved_fri3(rs.getInt("reserved_fri3"));
				restVO.setReserved_fri4(rs.getInt("reserved_fri4"));
				restVO.setReserved_fri5(rs.getInt("reserved_fri5"));
				restVO.setReserved_fri6(rs.getInt("reserved_fri6"));
				restVO.setReserved_sat1(rs.getInt("reserved_sat1"));
				restVO.setReserved_sat2(rs.getInt("reserved_sat2"));
				restVO.setReserved_sat3(rs.getInt("reserved_sat3"));
				restVO.setReserved_sat4(rs.getInt("reserved_sat4"));
				restVO.setReserved_sat5(rs.getInt("reserved_sat5"));
				restVO.setReserved_sat6(rs.getInt("reserved_sat6"));
				restVO.setReserved_sun1(rs.getInt("reserved_sun1"));
				restVO.setReserved_sun2(rs.getInt("reserved_sun2"));
				restVO.setReserved_sun3(rs.getInt("reserved_sun3"));
				restVO.setReserved_sun4(rs.getInt("reserved_sun4"));
				restVO.setReserved_sun5(rs.getInt("reserved_sun5"));
				restVO.setReserved_sun6(rs.getInt("reserved_sun6"));
				restVO.setReserved_totalset(rs.getInt("reserved_totalset"));

				list.add(restVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return list;
	}

	@Override
	public void update_state(Integer rest_no, String rest_state) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATE);

			pstmt.setString(1, rest_state);
			pstmt.setInt(2, rest_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	public List<RestVO> getAllScore() {
		List<RestVO> list = new ArrayList<RestVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_SCORE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// restVO �]�٬� Domain objects
				RestVO restVO = new RestVO();

				restVO.setRest_no(rs.getInt("rest_no"));
				restVO.setRest_name(rs.getString("rest_name"));
				restVO.setScor_pri(rs.getInt("scor_pri"));
				restVO.setScor_pritms(rs.getInt("scor_pritms"));
				restVO.setScor_hea(rs.getInt("scor_hea"));
				restVO.setScor_heatms(rs.getInt("scor_heatms"));
				restVO.setScor_cook(rs.getInt("scor_cook"));
				restVO.setScor_cooktms(rs.getInt("scor_cooktms"));
				restVO.setScor_envisco(rs.getInt("scor_envisco"));
				restVO.setScor_envtms(rs.getInt("scor_envtms"));
				restVO.setScor_serv(rs.getInt("scor_serv"));
				restVO.setScor_servtms(rs.getInt("scor_servtms"));
				list.add(restVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return list;

	}

	@Override
	public List<ReservRecord> getAllReserv(Map<String, String[]> map) {
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
				+ "			from   REST,REST_CLASS   where rest.rest_no=rest_class.rest_no and rest_state='1'"
				+ JdbcUtil_CompositeQuery_rest.get_WhereCondition(map)
				+ "order by rest.rest_no";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
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

	@Override
	public void  updateOneReserv(ReservRecord rest_reserv) {
		
	}
	public RestVO updateResSeat(RestVO vo)
	{
		RestVO result=new RestVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try 
		{
			con=ds.getConnection();
			pstmt=con.prepareStatement(UPDATE_RES);
			pstmt.setInt(1, vo.getReserved_mon1());
			pstmt.setInt(2, vo.getReserved_mon2());
			pstmt.setInt(3, vo.getReserved_mon3());
			pstmt.setInt(4, vo.getReserved_mon4());
			pstmt.setInt(5, vo.getReserved_mon5());
			pstmt.setInt(6, vo.getReserved_mon6());
			pstmt.setInt(7, vo.getReserved_tue1());
			pstmt.setInt(8, vo.getReserved_tue2());
			pstmt.setInt(9, vo.getReserved_tue3());
			pstmt.setInt(10, vo.getReserved_tue4());
			pstmt.setInt(11, vo.getReserved_tue5());
			pstmt.setInt(12, vo.getReserved_tue6());
			pstmt.setInt(13, vo.getReserved_wed1());
			pstmt.setInt(14, vo.getReserved_wed2());
			pstmt.setInt(15, vo.getReserved_wed3());
			pstmt.setInt(16, vo.getReserved_wed4());
			pstmt.setInt(17, vo.getReserved_wed5());
			pstmt.setInt(18, vo.getReserved_wed6());
			pstmt.setInt(19, vo.getReserved_thu1());
			pstmt.setInt(20, vo.getReserved_thu2());
			pstmt.setInt(21, vo.getReserved_thu3());
			pstmt.setInt(22, vo.getReserved_thu4());
			pstmt.setInt(23, vo.getReserved_thu5());
			pstmt.setInt(24, vo.getReserved_thu6());
			pstmt.setInt(25, vo.getReserved_fri1());
			pstmt.setInt(26, vo.getReserved_fri2());
			pstmt.setInt(27, vo.getReserved_fri3());
			pstmt.setInt(28, vo.getReserved_fri4());
			pstmt.setInt(29, vo.getReserved_fri5());
			pstmt.setInt(30, vo.getReserved_fri6());
			pstmt.setInt(31, vo.getReserved_sat1());
			pstmt.setInt(32, vo.getReserved_sat2());
			pstmt.setInt(33, vo.getReserved_sat3());
			pstmt.setInt(34, vo.getReserved_sat4());
			pstmt.setInt(35, vo.getReserved_sat5());
			pstmt.setInt(36, vo.getReserved_sat6());
			pstmt.setInt(37, vo.getReserved_sun1());
			pstmt.setInt(38, vo.getReserved_sun2());
			pstmt.setInt(39, vo.getReserved_sun3());
			pstmt.setInt(40, vo.getReserved_sun4());
			pstmt.setInt(41, vo.getReserved_sun5());
			pstmt.setInt(42, vo.getReserved_sun6());
			pstmt.setInt(43, vo.getRest_no());
			
			pstmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{			
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
		return result;
	}

	@Override
	public RestVO checkAccount(String rest_account) {
		RestVO restVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_ONE_STMT);

			pstmt.setString(1, rest_account);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				restVO = new RestVO();
				restVO.setRest_no(rs.getInt("rest_no"));
				restVO.setRest_account(rs.getString("rest_account"));
				restVO.setRest_name(rs.getString("rest_name"));
				restVO.setRest_psw(rs.getString("rest_psw"));

			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return restVO;
	}
	
}