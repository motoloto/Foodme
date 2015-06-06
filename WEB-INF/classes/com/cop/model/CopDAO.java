package com.cop.model;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.odr.model.jdbcUtil_CompositeQuery_OneCopByOdr;



public class CopDAO implements CopDAO_interface {
	
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
		"INSERT INTO COUPON (COP_NO,REST_NO,COP_NAME,COP_CONTENT,COP_ORLPRICE,COP_PRICE,COP_DL,COP_STATE,COP_DATE,COP_CIRCU,COP_SELAMT) VALUES (sequence_COP_NO.NEXTVAL, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT COP_NO,REST_NO,COP_NAME, COP_CONTENT,COP_ORLPRICE,COP_PRICE,COP_DL,COP_STATE,COP_DATE,COP_CIRCU,COP_SELAMT FROM COUPON order by COP_NO";
	private static final String GET_ONE_STMT = 
		"SELECT COP_NO,REST_NO,COP_NAME, COP_CONTENT,COP_ORLPRICE,COP_PRICE,COP_DL,COP_STATE,COP_DATE,COP_CIRCU,COP_SELAMT FROM COUPON where COP_NO = ?";
	private static final String GET_COP_BY_REST_NO = 
			"SELECT COP_NO,REST_NO,COP_NAME, COP_CONTENT,COP_ORLPRICE,COP_PRICE,COP_DL,COP_STATE,COP_DATE,COP_CIRCU,COP_SELAMT FROM COUPON where REST_NO = ? AND COP_STATE='1'";
	private static final String GetCopByRest_no = 
			"SELECT COP_NO,REST_NO,COP_NAME, COP_CONTENT,COP_ORLPRICE,COP_PRICE,COP_DL,COP_STATE,COP_DATE,COP_CIRCU,COP_SELAMT FROM COUPON where REST_NO = ?";
	private static final String DELETE = 
		"DELETE FROM COUPON where COP_NO = ?";
	private static final String UPDATE = 
		"UPDATE COUPON set REST_NO=?, COP_NAME=?, COP_CONTENT=?, COP_ORLPRICE=?, COP_PRICE=?,COP_DL=?,COP_STATE=?,COP_DATE=?,COP_CIRCU=?,COP_SELAMT=? where COP_NO = ?";
	private static final String GET_HOTSALE_COP = 
			"select * from coupon,(select max(cop_selamt) as max_selamt from coupon) where cop_selamt = max_selamt and cop_state='1'" ;
	private static final String GET_Latest_COP = 
			"select * from coupon,(select max(cop_date) as max_date from coupon) where cop_date = max_date  and cop_state='1'";
	@Override
	public CopVO findHotSaleCop( ) {
		
		CopVO CopVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_HOTSALE_COP);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
			}
			
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
		return CopVo;
	}
	@Override
	public CopVO findLatestCop( ) {
		
		CopVO CopVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Latest_COP);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
			}
			
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
		return CopVo;
	}
	@Override
	public int insert(CopVO copVo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, copVo.getRest_no());
			pstmt.setString(2, copVo.getCop_name());
			pstmt.setString(3, copVo.getCop_content());
			pstmt.setInt(4, copVo.getCop_orlprice());
			pstmt.setInt(5, copVo.getCop_price());
			pstmt.setDate(6, copVo.getCop_dl());
			pstmt.setString(7, copVo.getCop_state());
			pstmt.setDate(8, copVo.getCop_date());
			pstmt.setInt(9, copVo.getCop_circu());
			pstmt.setInt(10, copVo.getCop_selamt());
			

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int update(CopVO copVo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, copVo.getRest_no());
			pstmt.setString(2, copVo.getCop_name());
			pstmt.setString(3, copVo.getCop_content());
			pstmt.setInt(4, copVo.getCop_orlprice());
			pstmt.setInt(5, copVo.getCop_price());
			pstmt.setDate(6, copVo.getCop_dl());
			pstmt.setString(7, copVo.getCop_state());
			pstmt.setDate(8, copVo.getCop_date());
			pstmt.setInt(9, copVo.getCop_circu());
			pstmt.setInt(10, copVo.getCop_selamt());
			pstmt.setInt(11, copVo.getCop_no());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int delete(Integer cop_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, cop_no);
			
			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public CopVO findByPrimaryKey(Integer cop_no) {

		CopVO CopVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, cop_no);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
			}

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
		return CopVo;
	}
	@Override
	public CopVO findCopByRest_no(Integer rest_no) {
		
		CopVO CopVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COP_BY_REST_NO);
			
			pstmt.setInt(1, rest_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
			}
			
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
		return CopVo;
	}

	@Override
	public List<CopVO> getAll() {
		List<CopVO> list = new ArrayList<CopVO>();
		CopVO CopVo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
				list.add(CopVo); // Store the row in the vector
			}

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
	
	//廠商結款用:用餐廳編號找到他發行的餐劵 
	@Override
	public List<CopVO> getCopByRest_no(Integer rest_no) {
		List<CopVO> list = new ArrayList<CopVO>();
		CopVO CopVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GetCopByRest_no);
			pstmt.setInt(1, rest_no);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
				list.add(CopVo); // Store the row in the vector
			}
			
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
	public List<CopVO> getAll(Map<String, String[]> map) {
		List<CopVO> list = new ArrayList<CopVO>();
		CopVO CopVo = null;

		Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {

			con = ds.getConnection();
			
			String finalSQL = "select * from COUPON "
			          + jdbcUtil_CompositeQuery_Cop.get_WhereCondition(map)
			          + "order by cop_no";
				pstmt = con.prepareStatement(finalSQL);
				rs = pstmt.executeQuery();
				System.out.println("●●finalSQL(by DAO) = "+finalSQL);
				System.out.println("wherecondition: "+jdbcUtil_CompositeQuery_Cop.get_WhereCondition(map));
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
				list.add(CopVo); // Store the row in the vector
			}

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
	public CopVO getOneCopByOdr(Map<String, String[]> map) {
		
		CopVO CopVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			System.out.println(GET_ALL_STMT);
			String finalSQL = "select * from COUPON "
					+ jdbcUtil_CompositeQuery_OneCopByOdr.get_WhereCondition(map)
					+ "order by cop_no";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			while (rs.next()) {
				// CopVo �]�٬� Domain objects
				CopVo = new CopVO();
				CopVo.setCop_no(rs.getInt("COP_NO"));
				CopVo.setRest_no(rs.getInt("REST_NO"));
				CopVo.setCop_name(rs.getString("COP_NAME"));
				CopVo.setCop_content(rs.getString("COP_CONTENT"));
				CopVo.setCop_orlprice(rs.getInt("COP_ORLPRICE"));
				CopVo.setCop_price(rs.getInt("COP_PRICE"));
				CopVo.setCop_dl(rs.getDate("COP_DL"));
				CopVo.setCop_state(rs.getString("COP_STATE"));
				CopVo.setCop_date(rs.getDate("COP_DATE"));
				CopVo.setCop_circu(rs.getInt("COP_CIRCU"));
				CopVo.setCop_selamt(rs.getInt("COP_SELAMT"));
				
			}
			
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
		return CopVo;
	}

//	public static void main(String[] args) {
//
//		CopJDBCDAO dao = new CopJDBCDAO();
//
//		 //�s�W
//		 CopVO copVo1 = new CopVO();
//		 copVo1.setRest_no(7002);
//		 copVo1.setCop_name("test1");
//		 copVo1.setCop_content("2005-01-01");
//		 copVo1.setCop_orlprice(600);
//		 copVo1.setCop_price(480);
//		 copVo1.setCop_dl(java.sql.Date.valueOf("2005-01-01"));
//		 copVo1.setCop_state("0");
//		 copVo1.setCop_date(java.sql.Date.valueOf("2005-01-01"));
//		 copVo1.setCop_circu(800);
//		 copVo1.setCop_selamt(0);
//		 int updateCount_insert = dao.insert(copVo1);
//		 System.out.println(updateCount_insert);
//				
//
////		 �ק�
//		 CopVO copVo2 = new CopVO();
//		 copVo2.setCop_no(30007);
//		 copVo2.setRest_no(7002);
//		 copVo2.setCop_name("COUPON2");
//		 copVo2.setCop_content("2002-01-01");
//		 copVo2.setCop_orlprice(2000);
//		 copVo2.setCop_price(1200);
//		 copVo2.setCop_dl(java.sql.Date.valueOf("2005-01-01"));
//		 copVo2.setCop_state("1");
//		 copVo2.setCop_date(java.sql.Date.valueOf("2005-01-01"));
//		 copVo2.setCop_circu(800);
//		 copVo2.setCop_selamt(10);
//		 int updateCount_update = dao.update(copVo2);
//		 System.out.println(updateCount_update);
//				
//
////		 �R��
//		 int updateCount_delete = dao.delete(30008);
//		 System.out.println(updateCount_delete);
//
////		 �d��
//		CopVO copVo3 = dao.findByPrimaryKey(30010);
//		System.out.print(copVo3.getCop_no() + ",");
//		System.out.print(copVo3.getRest_no() + ",");
//		System.out.print(copVo3.getCop_name() + ",");
//		System.out.print(copVo3.getCop_content() + ",");
//		System.out.print(copVo3.getCop_orlprice() + ",");
//		System.out.println(copVo3.getCop_price() + ",");
//		System.out.println("---------------------");
////
////		// �d��
//		List<CopVO> list = dao.getAll();
//		for (CopVO aEmp : list) {
//			System.out.print(aEmp.getCop_no() + ",");
//			System.out.print(aEmp.getRest_no() + ",");
//			System.out.print(aEmp.getCop_name() + ",");
//			System.out.print(aEmp.getCop_content() + ",");
//			System.out.print(aEmp.getCop_orlprice() + ",");
//			System.out.print(aEmp.getCop_price() + ",");
//			
//			System.out.println();
//			System.out.println("---------------------");
//		}
//	}
}
