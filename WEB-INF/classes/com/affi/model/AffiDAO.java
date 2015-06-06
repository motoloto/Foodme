package com.affi.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AffiDAO implements AffiDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO AFFILIATE (AFFI_NO,BUS_NO,REST_NAME,REST_ADDR,REST_TEL,REST_MOBIL,REST_PHOTO,REST_MAIL,REST_WEB,REST_INTRO,AFFI_STATE) VALUES (affi_no_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // �f�d���A0,�N�?�q�L
	private static final String GET_ALL_STMT = "SELECT AFFI_NO,BUS_NO,REST_NAME,REST_ADDR,REST_TEL,REST_MOBIL,REST_PHOTO,REST_MAIL,REST_WEB,REST_INTRO,AFFI_STATE FROM AFFILIATE order by AFFI_NO";
	private static final String GET_ONE_STMT = "SELECT AFFI_NO,BUS_NO,REST_NAME,REST_ADDR,REST_TEL,REST_MOBIL,REST_PHOTO,REST_MAIL,REST_WEB,REST_INTRO,AFFI_STATE FROM AFFILIATE where AFFI_NO = ?";
	private static final String DELETE = "DELETE FROM AFFILIATE where AFFI_NO = ?";
	// SQL UPDATE 沒有PK, 直接從第二項開始
	private static final String UPDATE = "UPDATE AFFILIATE set REST_NAME=?, REST_ADDR=?, REST_TEL=?, REST_MOBIL=?, REST_PHOTO=?, REST_MAIL=?, REST_WEB=?, REST_INTRO=?, AFFI_STATE=? where affi_no = ?";
	private static final String UPDATENoImg = "UPDATE AFFILIATE set REST_NAME=?, REST_ADDR=?, REST_TEL=?, REST_MOBIL=?, REST_MAIL=?, REST_WEB=?, REST_INTRO=?, AFFI_STATE=? where affi_no = ?";

	@Override
	public void insert(AffiVO affiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, affiVO.getBus_no());
			pstmt.setString(2, affiVO.getRest_name());
			pstmt.setString(3, affiVO.getRest_addr());
			pstmt.setString(4, affiVO.getRest_tel());
			pstmt.setString(5, affiVO.getRest_mobil());
			pstmt.setBytes(6, affiVO.getRest_photo());
			pstmt.setString(7, affiVO.getRest_mail());
			pstmt.setString(8, affiVO.getRest_web());
			pstmt.setString(9, affiVO.getRest_intro());
			pstmt.setString(10, affiVO.getAffi_state());

			pstmt.executeUpdate();

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
	public void update(AffiVO affiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			byte[] rest_photo = (affiVO.getRest_photo());
			if (rest_photo != null) {
				
				pstmt = con.prepareStatement(UPDATE);
				
				//pstmt.setDouble(1, affiVO.getBus_no());
				pstmt.setString(1, affiVO.getRest_name());
				pstmt.setString(2, affiVO.getRest_addr());
				pstmt.setString(3, affiVO.getRest_tel());
				pstmt.setString(4, affiVO.getRest_mobil());
				pstmt.setBytes(5, affiVO.getRest_photo());
				pstmt.setString(6, affiVO.getRest_mail());
				pstmt.setString(7, affiVO.getRest_web());
				pstmt.setString(8, affiVO.getRest_intro());
				pstmt.setString(9, affiVO.getAffi_state());
				pstmt.setInt(10, affiVO.getAffi_no());

				pstmt.executeUpdate();
			} else {
				pstmt = con.prepareStatement(UPDATENoImg);
				pstmt.setString(1, affiVO.getRest_name());
				pstmt.setString(2, affiVO.getRest_addr());
				pstmt.setString(3, affiVO.getRest_tel());
				pstmt.setString(4, affiVO.getRest_mobil());
				//pstmt.setBytes(5, affiVO.getRest_photo());
				pstmt.setString(5, affiVO.getRest_mail());
				pstmt.setString(6, affiVO.getRest_web());
				pstmt.setString(7, affiVO.getRest_intro());
				pstmt.setString(8, affiVO.getAffi_state());
				pstmt.setInt(9, affiVO.getAffi_no());

				pstmt.executeUpdate();
				
			}

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
	public void delete(Integer affi_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, affi_no);

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
	public AffiVO findByPrimaryKey(Integer affi_no) {

		AffiVO affiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, affi_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				affiVO = new AffiVO();
				affiVO.setAffi_no(rs.getInt("affi_no"));
				affiVO.setBus_no(rs.getString("bus_no"));
				affiVO.setRest_name(rs.getString("rest_name"));
				affiVO.setRest_addr(rs.getString("rest_addr"));
				affiVO.setRest_tel(rs.getString("rest_tel"));
				affiVO.setRest_mobil(rs.getString("rest_mobil"));
				affiVO.setRest_photo(rs.getBytes("rest_photo"));
				affiVO.setRest_mail(rs.getString("rest_mail"));
				affiVO.setRest_web(rs.getString("rest_web"));
				affiVO.setRest_intro(rs.getString("rest_intro"));
				affiVO.setAffi_state(rs.getString("affi_state"));

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
		return affiVO;
	}

	@Override
	public List<AffiVO> getAll() {
		List<AffiVO> list = new ArrayList<AffiVO>();
		AffiVO affiVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				affiVO = new AffiVO();
				affiVO.setAffi_no(rs.getInt("affi_no"));
				affiVO.setBus_no(rs.getString("bus_no"));
				affiVO.setRest_name(rs.getString("rest_name"));
				affiVO.setRest_addr(rs.getString("rest_addr"));
				affiVO.setRest_tel(rs.getString("rest_tel"));
				affiVO.setRest_mobil(rs.getString("rest_mobil"));
				affiVO.setRest_mail(rs.getString("rest_mail"));
				affiVO.setRest_web(rs.getString("rest_web"));
				affiVO.setRest_intro(rs.getString("rest_intro"));
				affiVO.setAffi_state(rs.getString("affi_state"));

				list.add(affiVO); // Store the row in the list
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