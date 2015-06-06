package com.affi.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AffiJNDIDAO implements AffiDAO_interface {

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

		private static final String INSERT_STMT = 
			"INSERT INTO AFFILIATE (affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mail,rest_web,rest_intro,affi_state) VALUES (affi_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mail,rest_web,rest_intro,affi_state FROM AFFILIATE order by affi_no";
		private static final String GET_ONE_STMT = 
			"SELECT affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mail,rest_web,rest_intro,affi_state FROM AFFILIATE where affi_no = ?";
		private static final String DELETE = 
			"DELETE FROM AFFILIATE where affi_no = ?";
		private static final String UPDATE = 
			"UPDATE AFFILIATE set bus_no=?, rest_name=?, rest_addr=?, rest_tel=?, rest_mail=?, rest_web=?, rest_intro=?, affi_state=? where affi_no = ?";

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
			pstmt.setString(5, affiVO.getRest_mail());
			pstmt.setString(6, affiVO.getRest_web());
			pstmt.setString(7, affiVO.getRest_intro());
			pstmt.setString(8, affiVO.getAffi_state());
			
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
	public void update(AffiVO affiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, affiVO.getBus_no());
			pstmt.setString(2, affiVO.getRest_name());
			pstmt.setString(3, affiVO.getRest_addr());
			pstmt.setString(4, affiVO.getRest_tel());
			pstmt.setString(5, affiVO.getRest_mail());
			pstmt.setString(6, affiVO.getRest_web());
			pstmt.setString(7, affiVO.getRest_intro());
			pstmt.setString(8, affiVO.getAffi_state());
			pstmt.setInt(9, affiVO.getAffi_no());
			
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