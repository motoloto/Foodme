package com.rest.model;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

public class RestJDBCDAO implements RestDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = "INSERT INTO REST ( rest_no, affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset) VALUES (rest_no_seq.NEXTVAL, ? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? )";
	private static final String GET_ALL_STMT = "SELECT  rest_no ,affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset FROM REST order by rest_no";
	private static final String GET_ONE_STMT = "SELECT rest_no ,	affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset FROM REST where rest_no = ?";
	private static final String DELETE = "DELETE FROM REST where rest_no = ?";
	private static final String UPDATE = "UPDATE REST set affi_no =?,	rest_account =?,	rest_psw =?,	bus_no =?,	rest_name =?,	rest_addr =?,	rest_tel =?,	rest_mail =?,	rest_web =?,	rest_opentime =?,	rest_photo =?,	rest_state =?,	rest_contra =?,	rest_waitmin =?,	scor_pri =?,	scor_pritms =?,	scor_hea =?,	scor_heatms =?,	scor_cook =?,	scor_cooktms =?,	scor_envisco =?,	scor_envtms =?,	scor_serv =?,	scor_servtms =?,	reserved_mon1 =?,	reserved_mon2 =?,	reserved_mon3 =?,	reserved_mon4 =?,	reserved_mon5 =?,	reserved_mon6 =?,	reserved_tue1 =?,	reserved_tue2 =?,	reserved_tue3 =?,	reserved_tue4 =?,	reserved_tue5 =?,	reserved_tue6 =?,	reserved_wed1 =?,	reserved_wed2 =?,	reserved_wed3 =?,	reserved_wed4 =?,	reserved_wed5 =?,	reserved_wed6 =?,	reserved_thu1 =?,	reserved_thu2 =?,	reserved_thu3 =?,	reserved_thu4 =?,	reserved_thu5 =?,	reserved_thu6 =?,	reserved_fri1 =?,	reserved_fri2 =?,	reserved_fri3 =?,	reserved_fri4 =?,	reserved_fri5 =?,	reserved_fri6 =?,	reserved_sat1 =?,	reserved_sat2 =?,	reserved_sat3 =?,	reserved_sat4 =?,	reserved_sat5 =?,	reserved_sat6 =?,	reserved_sun1 =?,	reserved_sun2 =?,	reserved_sun3 =?,	reserved_sun4 =?,	reserved_sun5 =?,	reserved_sun6 =?,	reserved_totalset =? where rest_no = ?";
	private static final String UPDATE_STATE = "UPDATE REST SET rest_state=? where rest_no=? ";
	private static final String GET_ALL_SCORE = "SELECT rest_no,rest_name,  scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms FROM rest  ORDER BY rest_no";

	@Override
	public void insert(RestVO restVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			pstmt.setString(10, restVO.getRest_opentime());
			InputStream myInputStream = new ByteArrayInputStream(
					restVO.getRest_photo());
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

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

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
					restVO.getRest_photo());
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

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(Integer rest_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, rest_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public RestVO findByPrimaryKey(Integer rest_no) {

		RestVO restVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, rest_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// RestVO �]�٬� Domain objects
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
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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

	public static void main(String[] args) {

		RestJDBCDAO dao = new RestJDBCDAO();

		// // V�s�W
		// RestVO restVO1 = new RestVO();
		// restVO1.setAffi_no(8001);
		// restVO1.setRest_account("toponepot");
		// restVO1.setRest_psw("111");
		// restVO1.setBus_no(110001);
		// restVO1.setRest_name("�o�@��");
		// restVO1.setRest_addr("�x������ٰϴ´I��36��");
		// restVO1.setRest_tel("04-36090088");
		// restVO1.setRest_mail("toponepot@gmai.com");
		// restVO1.setRest_web("http://www.toponepot.com");
		// restVO1.setRest_opentime("11:00~21:00");
		// restVO1.setRest_photo("");
		// restVO1.setRest_state("1");
		// restVO1.setRest_contra("�X��e�ݭq");
		// restVO1.setRest_waitmin(30);
		// restVO1.setScor_pri(5);
		// restVO1.setScor_pritms(10);
		// restVO1.setScor_hea(5);
		// restVO1.setScor_heatms(10);
		// restVO1.setScor_cook(5);
		// restVO1.setScor_cooktms(10);
		// restVO1.setScor_envisco(5);
		// restVO1.setScor_envtms(10);
		// restVO1.setScor_serv(5);
		// restVO1.setScor_servtms(10);
		// restVO1.setReserved_mon1(10);
		// restVO1.setReserved_mon2(11);
		// restVO1.setReserved_mon3(12);
		// restVO1.setReserved_mon4(13);
		// restVO1.setReserved_mon5(14);
		// restVO1.setReserved_mon6(15);
		// restVO1.setReserved_tue1(16);
		// restVO1.setReserved_tue2(17);
		// restVO1.setReserved_tue3(18);
		// restVO1.setReserved_tue4(19);
		// restVO1.setReserved_tue5(20);
		// restVO1.setReserved_tue6(19);
		// restVO1.setReserved_wed1(18);
		// restVO1.setReserved_wed2(17);
		// restVO1.setReserved_wed3(16);
		// restVO1.setReserved_wed4(0);
		// restVO1.setReserved_wed5(1);
		// restVO1.setReserved_wed6(2);
		// restVO1.setReserved_thu1(3);
		// restVO1.setReserved_thu2(4);
		// restVO1.setReserved_thu3(5);
		// restVO1.setReserved_thu4(6);
		// restVO1.setReserved_thu5(7);
		// restVO1.setReserved_thu6(8);
		// restVO1.setReserved_fri1(9);
		// restVO1.setReserved_fri2(10);
		// restVO1.setReserved_fri3(11);
		// restVO1.setReserved_fri4(12);
		// restVO1.setReserved_fri5(9);
		// restVO1.setReserved_fri6(8);
		// restVO1.setReserved_sat1(7);
		// restVO1.setReserved_sat2(6);
		// restVO1.setReserved_sat3(5);
		// restVO1.setReserved_sat4(4);
		// restVO1.setReserved_sat5(3);
		// restVO1.setReserved_sat6(2);
		// restVO1.setReserved_sun1(1);
		// restVO1.setReserved_sun2(0);
		// restVO1.setReserved_sun3(0);
		// restVO1.setReserved_sun4(0);
		// restVO1.setReserved_sun5(1);
		// restVO1.setReserved_sun6(0);
		// restVO1.setReserved_totalset(20);
		//
		// dao.insert(restVO1);
		//
		// // V�ק�
		// RestVO restVO2 = new RestVO();
		// restVO2.setRest_no(7003);
		// restVO2.setAffi_no(8001);
		// restVO2.setRest_account("toponepot");
		// restVO2.setRest_psw("111");
		// restVO2.setBus_no(110001);
		// restVO2.setRest_name("�o�@��");
		// restVO2.setRest_addr("�x������ٰϴ´I��36��");
		// restVO2.setRest_tel("04-36090088");
		// restVO2.setRest_mail("toponepot@gmai.com");
		// restVO2.setRest_web("http://www.toponepot.com");
		// restVO2.setRest_opentime("11:00~21:00");
		// restVO2.setRest_photo("");
		// restVO2.setRest_state("1");
		// restVO2.setRest_contra("�X��e�ݭq");
		// restVO2.setRest_waitmin(30);
		// restVO2.setScor_pri(5);
		// restVO2.setScor_pritms(10);
		// restVO2.setScor_hea(5);
		// restVO2.setScor_heatms(10);
		// restVO2.setScor_cook(5);
		// restVO2.setScor_cooktms(10);
		// restVO2.setScor_envisco(5);
		// restVO2.setScor_envtms(10);
		// restVO2.setScor_serv(5);
		// restVO2.setScor_servtms(10);
		// restVO2.setReserved_mon1(10);
		// restVO2.setReserved_mon2(11);
		// restVO2.setReserved_mon3(12);
		// restVO2.setReserved_mon4(13);
		// restVO2.setReserved_mon5(14);
		// restVO2.setReserved_mon6(15);
		// restVO2.setReserved_tue1(16);
		// restVO2.setReserved_tue2(17);
		// restVO2.setReserved_tue3(18);
		// restVO2.setReserved_tue4(19);
		// restVO2.setReserved_tue5(20);
		// restVO2.setReserved_tue6(19);
		// restVO2.setReserved_wed1(18);
		// restVO2.setReserved_wed2(17);
		// restVO2.setReserved_wed3(16);
		// restVO2.setReserved_wed4(0);
		// restVO2.setReserved_wed5(1);
		// restVO2.setReserved_wed6(2);
		// restVO2.setReserved_thu1(3);
		// restVO2.setReserved_thu2(4);
		// restVO2.setReserved_thu3(5);
		// restVO2.setReserved_thu4(6);
		// restVO2.setReserved_thu5(7);
		// restVO2.setReserved_thu6(8);
		// restVO2.setReserved_fri1(9);
		// restVO2.setReserved_fri2(10);
		// restVO2.setReserved_fri3(11);
		// restVO2.setReserved_fri4(12);
		// restVO2.setReserved_fri5(9);
		// restVO2.setReserved_fri6(8);
		// restVO2.setReserved_sat1(7);
		// restVO2.setReserved_sat2(6);
		// restVO2.setReserved_sat3(5);
		// restVO2.setReserved_sat4(4);
		// restVO2.setReserved_sat5(3);
		// restVO2.setReserved_sat6(2);
		// restVO2.setReserved_sun1(1);
		// restVO2.setReserved_sun2(0);
		// restVO2.setReserved_sun3(0);
		// restVO2.setReserved_sun4(0);
		// restVO2.setReserved_sun5(1);
		// restVO2.setReserved_sun6(0);
		// restVO2.setReserved_totalset(100);
		//
		// dao.update(restVO2);

		// // V�R��
		// dao.delete(7001);
		//
		// // V�d��
		// RestVO restVO3 = dao.findByPrimaryKey(7001);
		// System.out.print(restVO3.getRest_no()+",");
		// System.out.print(restVO3.getAffi_no()+",");
		// System.out.print(restVO3.getRest_account()+",");
		// System.out.print(restVO3.getRest_psw()+",");
		// System.out.print(restVO3.getBus_no()+",");
		// System.out.print(restVO3.getRest_name()+",");
		// System.out.print(restVO3.getRest_addr()+",");
		// System.out.print(restVO3.getRest_tel()+",");
		// System.out.print(restVO3.getRest_mail()+",");
		// System.out.print(restVO3.getRest_web()+",");
		// System.out.print(restVO3.getRest_opentime()+",");
		// System.out.print(restVO3.getRest_photo()+",");
		// System.out.print(restVO3.getRest_state()+",");
		// System.out.print(restVO3.getRest_contra()+",");
		// System.out.print(restVO3.getRest_waitmin()+",");
		// System.out.print(restVO3.getScor_pri()+",");
		// System.out.print(restVO3.getScor_pritms()+",");
		// System.out.print(restVO3.getScor_hea()+",");
		// System.out.print(restVO3.getScor_heatms()+",");
		// System.out.print(restVO3.getScor_cook()+",");
		// System.out.print(restVO3.getScor_cooktms()+",");
		// System.out.print(restVO3.getScor_envisco()+",");
		// System.out.print(restVO3.getScor_envtms()+",");
		// System.out.print(restVO3.getScor_serv()+",");
		// System.out.print(restVO3.getScor_servtms()+",");
		// System.out.print(restVO3.getReserved_mon1()+",");
		// System.out.print(restVO3.getReserved_mon2()+",");
		// System.out.print(restVO3.getReserved_mon3()+",");
		// System.out.print(restVO3.getReserved_mon4()+",");
		// System.out.print(restVO3.getReserved_mon5()+",");
		// System.out.print(restVO3.getReserved_mon6()+",");
		// System.out.print(restVO3.getReserved_tue1()+",");
		// System.out.print(restVO3.getReserved_tue2()+",");
		// System.out.print(restVO3.getReserved_tue3()+",");
		// System.out.print(restVO3.getReserved_tue4()+",");
		// System.out.print(restVO3.getReserved_tue5()+",");
		// System.out.print(restVO3.getReserved_tue6()+",");
		// System.out.print(restVO3.getReserved_wed1()+",");
		// System.out.print(restVO3.getReserved_wed2()+",");
		// System.out.print(restVO3.getReserved_wed3()+",");
		// System.out.print(restVO3.getReserved_wed4()+",");
		// System.out.print(restVO3.getReserved_wed5()+",");
		// System.out.print(restVO3.getReserved_wed6()+",");
		// System.out.print(restVO3.getReserved_thu1()+",");
		// System.out.print(restVO3.getReserved_thu2()+",");
		// System.out.print(restVO3.getReserved_thu3()+",");
		// System.out.print(restVO3.getReserved_thu4()+",");
		// System.out.print(restVO3.getReserved_thu5()+",");
		// System.out.print(restVO3.getReserved_thu6()+",");
		// System.out.print(restVO3.getReserved_fri1()+",");
		// System.out.print(restVO3.getReserved_fri2()+",");
		// System.out.print(restVO3.getReserved_fri3()+",");
		// System.out.print(restVO3.getReserved_fri4()+",");
		// System.out.print(restVO3.getReserved_fri5()+",");
		// System.out.print(restVO3.getReserved_fri6()+",");
		// System.out.print(restVO3.getReserved_sat1()+",");
		// System.out.print(restVO3.getReserved_sat2()+",");
		// System.out.print(restVO3.getReserved_sat3()+",");
		// System.out.print(restVO3.getReserved_sat4()+",");
		// System.out.print(restVO3.getReserved_sat5()+",");
		// System.out.print(restVO3.getReserved_sat6()+",");
		// System.out.print(restVO3.getReserved_sun1()+",");
		// System.out.print(restVO3.getReserved_sun2()+",");
		// System.out.print(restVO3.getReserved_sun3()+",");
		// System.out.print(restVO3.getReserved_sun4()+",");
		// System.out.print(restVO3.getReserved_sun5()+",");
		// System.out.print(restVO3.getReserved_sun6()+",");
		// System.out.print(restVO3.getReserved_totalset()+",");
		// System.out.println("---------------------");
		//
		// // v�d��
		// List<RestVO> list = dao.getAll();
		// for (RestVO aRest : list) {
		// System.out.print(aRest.getRest_no()+",");
		// System.out.print(aRest.getAffi_no()+",");
		// System.out.print(aRest.getRest_account()+",");
		// System.out.print(aRest.getRest_psw()+",");
		// System.out.print(aRest.getBus_no()+",");
		// System.out.print(aRest.getRest_name()+",");
		// System.out.print(aRest.getRest_addr()+",");
		// System.out.print(aRest.getRest_tel()+",");
		// System.out.print(aRest.getRest_mail()+",");
		// System.out.print(aRest.getRest_web()+",");
		// System.out.print(aRest.getRest_opentime()+",");
		// System.out.print(aRest.getRest_photo()+",");
		// System.out.print(aRest.getRest_state()+",");
		// System.out.print(aRest.getRest_contra()+",");
		// System.out.print(aRest.getRest_waitmin()+",");
		// System.out.print(aRest.getScor_pri()+",");
		// System.out.print(aRest.getScor_pritms()+",");
		// System.out.print(aRest.getScor_hea()+",");
		// System.out.print(aRest.getScor_heatms()+",");
		// System.out.print(aRest.getScor_cook()+",");
		// System.out.print(aRest.getScor_cooktms()+",");
		// System.out.print(aRest.getScor_envisco()+",");
		// System.out.print(aRest.getScor_envtms()+",");
		// System.out.print(aRest.getScor_serv()+",");
		// System.out.print(aRest.getScor_servtms()+",");
		// System.out.print(aRest.getReserved_mon1()+",");
		// System.out.print(aRest.getReserved_mon2()+",");
		// System.out.print(aRest.getReserved_mon3()+",");
		// System.out.print(aRest.getReserved_mon4()+",");
		// System.out.print(aRest.getReserved_mon5()+",");
		// System.out.print(aRest.getReserved_mon6()+",");
		// System.out.print(aRest.getReserved_tue1()+",");
		// System.out.print(aRest.getReserved_tue2()+",");
		// System.out.print(aRest.getReserved_tue3()+",");
		// System.out.print(aRest.getReserved_tue4()+",");
		// System.out.print(aRest.getReserved_tue5()+",");
		// System.out.print(aRest.getReserved_tue6()+",");
		// System.out.print(aRest.getReserved_wed1()+",");
		// System.out.print(aRest.getReserved_wed2()+",");
		// System.out.print(aRest.getReserved_wed3()+",");
		// System.out.print(aRest.getReserved_wed4()+",");
		// System.out.print(aRest.getReserved_wed5()+",");
		// System.out.print(aRest.getReserved_wed6()+",");
		// System.out.print(aRest.getReserved_thu1()+",");
		// System.out.print(aRest.getReserved_thu2()+",");
		// System.out.print(aRest.getReserved_thu3()+",");
		// System.out.print(aRest.getReserved_thu4()+",");
		// System.out.print(aRest.getReserved_thu5()+",");
		// System.out.print(aRest.getReserved_thu6()+",");
		// System.out.print(aRest.getReserved_fri1()+",");
		// System.out.print(aRest.getReserved_fri2()+",");
		// System.out.print(aRest.getReserved_fri3()+",");
		// System.out.print(aRest.getReserved_fri4()+",");
		// System.out.print(aRest.getReserved_fri5()+",");
		// System.out.print(aRest.getReserved_fri6()+",");
		// System.out.print(aRest.getReserved_sat1()+",");
		// System.out.print(aRest.getReserved_sat2()+",");
		// System.out.print(aRest.getReserved_sat3()+",");
		// System.out.print(aRest.getReserved_sat4()+",");
		// System.out.print(aRest.getReserved_sat5()+",");
		// System.out.print(aRest.getReserved_sat6()+",");
		// System.out.print(aRest.getReserved_sun1()+",");
		// System.out.print(aRest.getReserved_sun2()+",");
		// System.out.print(aRest.getReserved_sun3()+",");
		// System.out.print(aRest.getReserved_sun4()+",");
		// System.out.print(aRest.getReserved_sun5()+",");
		// System.out.print(aRest.getReserved_sun6()+",");
		// System.out.print(aRest.getReserved_totalset()+",");
		//
		// System.out.println();
		// }
		
		//查詢評價資料
		List<RestVO>list= dao.getAllScore();
		for (RestVO aRest : list) {
			System.out.print(aRest.getRest_no()+",");
			System.out.print(aRest.getRest_name()+",");
			System.out.print(aRest.getScor_pri()+",");
			System.out.print(aRest.getScor_pritms()+",");
			System.out.print(aRest.getScor_cook()+",");
			System.out.print(aRest.getScor_cooktms()+",");
			System.out.print(aRest.getScor_hea()+",");
			System.out.print(aRest.getScor_heatms()+",");
			System.out.print(aRest.getScor_envisco()+",");
			System.out.print(aRest.getScor_envtms()+",");
			System.out.print(aRest.getScor_serv()+",");
			System.out.print(aRest.getScor_servtms()+",");
			 System.out.println();
		}
		
	}

	@Override
	public void update_state(Integer rest_no, String rest_state) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(UPDATE_STATE);

			pstmt.setString(1, rest_state);
			pstmt.setInt(2, rest_no);

			pstmt.executeUpdate();
			// Handle any driver errors
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
		} catch (SQLException | ClassNotFoundException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
		}

	}

	public List<RestVO> getAllScore() {
		List<RestVO> list = new ArrayList<RestVO>();
		RestVO restVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// restVO �]�٬� Domain objects
				restVO = new RestVO();

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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public ReservRecord getOneReserv(Integer rest_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservRecord> getAllReserv(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}
}