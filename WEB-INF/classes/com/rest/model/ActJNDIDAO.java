package com.rest.model;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ActJNDIDAO implements RestDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO REST ( rest_no, affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset) VALUES (rest_no_seq.NEXTVAL, ? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? ,	? )";
		private static final String GET_ALL_STMT = 
			"SELECT  ,	affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset FROM REST order by rest_no";
		private static final String GET_ONE_STMT = 
			"SELECT rest_no ,	affi_no ,	rest_account ,	rest_psw ,	bus_no ,	rest_name ,	rest_addr ,	rest_tel ,	rest_mail ,	rest_web ,	rest_opentime ,	rest_photo ,	rest_state ,	rest_contra ,	rest_waitmin ,	scor_pri ,	scor_pritms ,	scor_hea ,	scor_heatms ,	scor_cook ,	scor_cooktms ,	scor_envisco ,	scor_envtms ,	scor_serv ,	scor_servtms ,	reserved_mon1 ,	reserved_mon2 ,	reserved_mon3 ,	reserved_mon4 ,	reserved_mon5 ,	reserved_mon6 ,	reserved_tue1 ,	reserved_tue2 ,	reserved_tue3 ,	reserved_tue4 ,	reserved_tue5 ,	reserved_tue6 ,	reserved_wed1 ,	reserved_wed2 ,	reserved_wed3 ,	reserved_wed4 ,	reserved_wed5 ,	reserved_wed6 ,	reserved_thu1 ,	reserved_thu2 ,	reserved_thu3 ,	reserved_thu4 ,	reserved_thu5 ,	reserved_thu6 ,	reserved_fri1 ,	reserved_fri2 ,	reserved_fri3 ,	reserved_fri4 ,	reserved_fri5 ,	reserved_fri6 ,	reserved_sat1 ,	reserved_sat2 ,	reserved_sat3 ,	reserved_sat4 ,	reserved_sat5 ,	reserved_sat6 ,	reserved_sun1 ,	reserved_sun2 ,	reserved_sun3 ,	reserved_sun4 ,	reserved_sun5 ,	reserved_sun6 ,	reserved_totalset FROM REST where rest_no = ?";
		private static final String DELETE = 
			"DELETE FROM REST where rest_no = ?";
		private static final String UPDATE = 
			"UPDATE REST set affi_no =?,	rest_account =?,	rest_psw =?,	bus_no =?,	rest_name =?,	rest_addr =?,	rest_tel =?,	rest_mail =?,	rest_web =?,	rest_opentime =?,	rest_photo =?,	rest_state =?,	rest_contra =?,	rest_waitmin =?,	scor_pri =?,	scor_pritms =?,	scor_hea =?,	scor_heatms =?,	scor_cook =?,	scor_cooktms =?,	scor_envisco =?,	scor_envtms =?,	scor_serv =?,	scor_servtms =?,	reserved_mon1 =?,	reserved_mon2 =?,	reserved_mon3 =?,	reserved_mon4 =?,	reserved_mon5 =?,	reserved_mon6 =?,	reserved_tue1 =?,	reserved_tue2 =?,	reserved_tue3 =?,	reserved_tue4 =?,	reserved_tue5 =?,	reserved_tue6 =?,	reserved_wed1 =?,	reserved_wed2 =?,	reserved_wed3 =?,	reserved_wed4 =?,	reserved_wed5 =?,	reserved_wed6 =?,	reserved_thu1 =?,	reserved_thu2 =?,	reserved_thu3 =?,	reserved_thu4 =?,	reserved_thu5 =?,	reserved_thu6 =?,	reserved_fri1 =?,	reserved_fri2 =?,	reserved_fri3 =?,	reserved_fri4 =?,	reserved_fri5 =?,	reserved_fri6 =?,	reserved_sat1 =?,	reserved_sat2 =?,	reserved_sat3 =?,	reserved_sat4 =?,	reserved_sat5 =?,	reserved_sat6 =?,	reserved_sun1 =?,	reserved_sun2 =?,	reserved_sun3 =?,	reserved_sun4 =?,	reserved_sun5 =?,	reserved_sun6 =?,	reserved_totalset =? where rest_no = ?";

		@Override
		public void insert(RestVO restVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1,restVO.getAffi_no());
				pstmt.setString(2,restVO.getRest_account());
				pstmt.setString(3,restVO.getRest_psw());
				pstmt.setInt(4,restVO.getBus_no());
				pstmt.setString(5,restVO.getRest_name());
				pstmt.setString(6,restVO.getRest_addr());
				pstmt.setString(7,restVO.getRest_tel());
				pstmt.setString(8,restVO.getRest_mail());
				pstmt.setString(9,restVO.getRest_web());
				pstmt.setString(10,restVO.getRest_opentime());
				InputStream myInputStream = new ByteArrayInputStream(restVO.getRest_photo()); 
				pstmt.setBinaryStream(11,myInputStream);
				pstmt.setString(12,restVO.getRest_state());
				pstmt.setString(13,restVO.getRest_contra());
				pstmt.setInt(14,restVO.getRest_waitmin());
				pstmt.setInt(15,restVO.getScor_pri());
				pstmt.setInt(16,restVO.getScor_pritms());
				pstmt.setInt(17,restVO.getScor_hea());
				pstmt.setInt(18,restVO.getScor_heatms());
				pstmt.setInt(19,restVO.getScor_cook());
				pstmt.setInt(20,restVO.getScor_cooktms());
				pstmt.setInt(21,restVO.getScor_envisco());
				pstmt.setInt(22,restVO.getScor_envtms());
				pstmt.setInt(23,restVO.getScor_serv());
				pstmt.setInt(24,restVO.getScor_servtms());
				pstmt.setInt(25,restVO.getReserved_mon1());
				pstmt.setInt(26,restVO.getReserved_mon2());
				pstmt.setInt(27,restVO.getReserved_mon3());
				pstmt.setInt(28,restVO.getReserved_mon4());
				pstmt.setInt(29,restVO.getReserved_mon5());
				pstmt.setInt(30,restVO.getReserved_mon6());
				pstmt.setInt(31,restVO.getReserved_tue1());
				pstmt.setInt(32,restVO.getReserved_tue2());
				pstmt.setInt(33,restVO.getReserved_tue3());
				pstmt.setInt(34,restVO.getReserved_tue4());
				pstmt.setInt(35,restVO.getReserved_tue5());
				pstmt.setInt(36,restVO.getReserved_tue6());
				pstmt.setInt(37,restVO.getReserved_wed1());
				pstmt.setInt(38,restVO.getReserved_wed2());
				pstmt.setInt(39,restVO.getReserved_wed3());
				pstmt.setInt(40,restVO.getReserved_wed4());
				pstmt.setInt(41,restVO.getReserved_wed5());
				pstmt.setInt(42,restVO.getReserved_wed6());
				pstmt.setInt(43,restVO.getReserved_thu1());
				pstmt.setInt(44,restVO.getReserved_thu2());
				pstmt.setInt(45,restVO.getReserved_thu3());
				pstmt.setInt(46,restVO.getReserved_thu4());
				pstmt.setInt(47,restVO.getReserved_thu5());
				pstmt.setInt(48,restVO.getReserved_thu6());
				pstmt.setInt(49,restVO.getReserved_fri1());
				pstmt.setInt(50,restVO.getReserved_fri2());
				pstmt.setInt(51,restVO.getReserved_fri3());
				pstmt.setInt(52,restVO.getReserved_fri4());
				pstmt.setInt(53,restVO.getReserved_fri5());
				pstmt.setInt(54,restVO.getReserved_fri6());
				pstmt.setInt(55,restVO.getReserved_sat1());
				pstmt.setInt(56,restVO.getReserved_sat2());
				pstmt.setInt(57,restVO.getReserved_sat3());
				pstmt.setInt(58,restVO.getReserved_sat4());
				pstmt.setInt(59,restVO.getReserved_sat5());
				pstmt.setInt(60,restVO.getReserved_sat6());
				pstmt.setInt(61,restVO.getReserved_sun1());
				pstmt.setInt(62,restVO.getReserved_sun2());
				pstmt.setInt(63,restVO.getReserved_sun3());
				pstmt.setInt(64,restVO.getReserved_sun4());
				pstmt.setInt(65,restVO.getReserved_sun5());
				pstmt.setInt(66,restVO.getReserved_sun6());
				pstmt.setInt(67,restVO.getReserved_totalset());

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
		public void update(RestVO restVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1,restVO.getAffi_no());
				pstmt.setString(2,restVO.getRest_account());
				pstmt.setString(3,restVO.getRest_psw());
				pstmt.setInt(4,restVO.getBus_no());
				pstmt.setString(5,restVO.getRest_name());
				pstmt.setString(6,restVO.getRest_addr());
				pstmt.setString(7,restVO.getRest_tel());
				pstmt.setString(8,restVO.getRest_mail());
				pstmt.setString(9,restVO.getRest_web());
				pstmt.setString(10,restVO.getRest_opentime());
				InputStream myInputStream = new ByteArrayInputStream(restVO.getRest_photo()); 
				pstmt.setBinaryStream(11,myInputStream);
				pstmt.setString(12,restVO.getRest_state());
				pstmt.setString(13,restVO.getRest_contra());
				pstmt.setInt(14,restVO.getRest_waitmin());
				pstmt.setInt(15,restVO.getScor_pri());
				pstmt.setInt(16,restVO.getScor_pritms());
				pstmt.setInt(17,restVO.getScor_hea());
				pstmt.setInt(18,restVO.getScor_heatms());
				pstmt.setInt(19,restVO.getScor_cook());
				pstmt.setInt(20,restVO.getScor_cooktms());
				pstmt.setInt(21,restVO.getScor_envisco());
				pstmt.setInt(22,restVO.getScor_envtms());
				pstmt.setInt(23,restVO.getScor_serv());
				pstmt.setInt(24,restVO.getScor_servtms());
				pstmt.setInt(25,restVO.getReserved_mon1());
				pstmt.setInt(26,restVO.getReserved_mon2());
				pstmt.setInt(27,restVO.getReserved_mon3());
				pstmt.setInt(28,restVO.getReserved_mon4());
				pstmt.setInt(29,restVO.getReserved_mon5());
				pstmt.setInt(30,restVO.getReserved_mon6());
				pstmt.setInt(31,restVO.getReserved_tue1());
				pstmt.setInt(32,restVO.getReserved_tue2());
				pstmt.setInt(33,restVO.getReserved_tue3());
				pstmt.setInt(34,restVO.getReserved_tue4());
				pstmt.setInt(35,restVO.getReserved_tue5());
				pstmt.setInt(36,restVO.getReserved_tue6());
				pstmt.setInt(37,restVO.getReserved_wed1());
				pstmt.setInt(38,restVO.getReserved_wed2());
				pstmt.setInt(39,restVO.getReserved_wed3());
				pstmt.setInt(40,restVO.getReserved_wed4());
				pstmt.setInt(41,restVO.getReserved_wed5());
				pstmt.setInt(42,restVO.getReserved_wed6());
				pstmt.setInt(43,restVO.getReserved_thu1());
				pstmt.setInt(44,restVO.getReserved_thu2());
				pstmt.setInt(45,restVO.getReserved_thu3());
				pstmt.setInt(46,restVO.getReserved_thu4());
				pstmt.setInt(47,restVO.getReserved_thu5());
				pstmt.setInt(48,restVO.getReserved_thu6());
				pstmt.setInt(49,restVO.getReserved_fri1());
				pstmt.setInt(50,restVO.getReserved_fri2());
				pstmt.setInt(51,restVO.getReserved_fri3());
				pstmt.setInt(52,restVO.getReserved_fri4());
				pstmt.setInt(53,restVO.getReserved_fri5());
				pstmt.setInt(54,restVO.getReserved_fri6());
				pstmt.setInt(55,restVO.getReserved_sat1());
				pstmt.setInt(56,restVO.getReserved_sat2());
				pstmt.setInt(57,restVO.getReserved_sat3());
				pstmt.setInt(58,restVO.getReserved_sat4());
				pstmt.setInt(59,restVO.getReserved_sat5());
				pstmt.setInt(60,restVO.getReserved_sat6());
				pstmt.setInt(61,restVO.getReserved_sun1());
				pstmt.setInt(62,restVO.getReserved_sun2());
				pstmt.setInt(63,restVO.getReserved_sun3());
				pstmt.setInt(64,restVO.getReserved_sun4());
				pstmt.setInt(65,restVO.getReserved_sun5());
				pstmt.setInt(66,restVO.getReserved_sun6());
				pstmt.setInt(67,restVO.getReserved_totalset());
				pstmt.setInt(68,restVO.getRest_no());

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
				restVO.setAffi_no(rs.getInt("affi_no"));
				restVO.setRest_account(rs.getString("rest_account"));
				restVO.setRest_psw(rs.getString("rest_psw"));
				restVO.setBus_no(rs.getInt("bus_no"));
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
				restVO.setBus_no(rs.getInt("bus_no"));
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
}