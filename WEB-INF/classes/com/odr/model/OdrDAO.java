package com.odr.model;


import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OdrDAO implements OdrDAO_interface {

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
		"INSERT INTO ORDERS (ODR_NO,MEM_NO,COP_NO,ODR_STATE,ODR_SEQNUM,ODR_USDTMS,ODR_PAYNAME,ODR_PHONE,ODR_MAIL,ODR_BUYAMT,ODR_TOPRC,ODR_PAYMODE,ODR_PAYTIME,ODR_TIME) VALUES (CONCAT(to_char(sysdate,'yyyymmdd'),sequence_ODR_NO.NEXTVAL), ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT ODR_NO,MEM_NO,COP_NO, ODR_STATE,ODR_SEQNUM,ODR_USDTMS,ODR_PAYNAME,ODR_PHONE,ODR_MAIL,ODR_BUYAMT,ODR_TOPRC,ODR_PAYMODE,ODR_PAYTIME,ODR_TIME FROM ORDERS order by ODR_NO DESC";
	private static final String GET_ONE_STMT = 
		"SELECT ODR_NO,MEM_NO,COP_NO, ODR_STATE,ODR_SEQNUM,ODR_USDTMS,ODR_PAYNAME,ODR_PHONE,ODR_MAIL,ODR_BUYAMT,ODR_TOPRC,ODR_PAYMODE,ODR_PAYTIME,ODR_TIME FROM ORDERS where ODR_NO = ?";
	private static final String GET_MEM_ODR = 
			"SELECT ORDERS.ODR_NO,ORDERS.MEM_NO,ORDERS.COP_NO, ORDERS.ODR_STATE,ORDERS.ODR_SEQNUM,REST.REST_NAME,COUPON.COP_DL,COUPON.COP_NAME,ORDERS.ODR_USDTMS,ORDERS.ODR_PAYNAME,ORDERS.ODR_PHONE,ORDERS.ODR_MAIL,ORDERS.ODR_BUYAMT,ORDERS.ODR_TOPRC,ORDERS.ODR_PAYMODE,ORDERS.ODR_PAYTIME,ORDERS.ODR_TIME,COUPON.rest_no FROM COUPON,ORDERS,REST WHERE COUPON.COP_NO=ORDERS.COP_NO AND COUPON.REST_NO=REST.REST_NO AND  ORDERS.MEM_NO=? AND (ORDERS.odr_buyamt - ORDERS.odr_usdtms) > 0  order by ORDERS.ODR_NO DESC ";
	private static final String DELETE = 
		"DELETE FROM ORDERS where ODR_NO = ?";
	private static final String UPDATE = 
		"UPDATE ORDERS set MEM_NO=?, COP_NO=?, ODR_STATE=?, ODR_SEQNUM=?, ODR_USDTMS=?,ODR_PAYNAME=?,ODR_PHONE=?,ODR_MAIL=?,ODR_BUYAMT=?,ODR_TOPRC=?,ODR_PAYMODE=?,ODR_PAYTIME=?,ODR_TIME=? where ODR_NO = ?";

	@Override
	synchronized public int  insert(OdrVO OdrVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, OdrVO.getMem_no());
			pstmt.setInt(2, OdrVO.getCop_no());
			pstmt.setString(3, OdrVO.getOdr_state());
			pstmt.setString(4, OdrVO.getOdr_seqnum());
			pstmt.setInt(5, OdrVO.getOdr_usdtms());
			pstmt.setString(6, OdrVO.getOdr_payname());
			pstmt.setString(7, OdrVO.getOdr_phone());
			pstmt.setString(8, OdrVO.getOdr_mail());
			pstmt.setInt(9, OdrVO.getOdr_buyamt());
			pstmt.setInt(10, OdrVO.getOdr_toprc());
			pstmt.setInt(11, OdrVO.getOdr_paymode());
			pstmt.setTimestamp(12, OdrVO.getOdr_paytime());
			pstmt.setTimestamp(13, OdrVO.getOdr_time());
			pstmt.execute();
			con.commit();

		} catch (SQLException se) {
			
			try {
				System.err.print("Transaction is being ");
				System.err.println("rolled back");
                con.rollback();          //設定於當有exception發生時之catch區塊內     
			} catch(SQLException excep) {
				System.err.print("SQLException: ");
				System.err.println(excep.getMessage());
			}
			
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
	public int update(OdrVO OdrVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, OdrVO.getMem_no());
			pstmt.setInt(2, OdrVO.getCop_no());
			pstmt.setString(3, OdrVO.getOdr_state());
			pstmt.setString(4, OdrVO.getOdr_seqnum());
			pstmt.setInt(5, OdrVO.getOdr_usdtms());
			pstmt.setString(6, OdrVO.getOdr_payname());
			pstmt.setString(7, OdrVO.getOdr_phone());
			pstmt.setString(8, OdrVO.getOdr_mail());
			pstmt.setInt(9, OdrVO.getOdr_buyamt());
			pstmt.setInt(10, OdrVO.getOdr_toprc());
			pstmt.setInt(11, OdrVO.getOdr_paymode());
			pstmt.setTimestamp(12, OdrVO.getOdr_paytime());
			pstmt.setTimestamp(13, OdrVO.getOdr_time());
			pstmt.setString(14, OdrVO.getOdr_no());

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
	public int delete(String ODR_NO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, ODR_NO);
			
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
	public OdrVO findByPrimaryKey(String ODR_NO) {

		OdrVO OdrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ODR_NO);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// OdrVO �]�٬� Domain objects
				OdrVO = new OdrVO();
				OdrVO.setOdr_no(rs.getString("ODR_NO"));
				OdrVO.setMem_no(rs.getInt("MEM_NO"));
				OdrVO.setCop_no(rs.getInt("COP_NO"));
				OdrVO.setOdr_state(rs.getString("ODR_STATE"));
				OdrVO.setOdr_seqnum(rs.getString("ODR_SEQNUM"));
				OdrVO.setOdr_usdtms(rs.getInt("ODR_USDTMS"));
				OdrVO.setOdr_payname(rs.getString("ODR_PAYNAME"));
				OdrVO.setOdr_phone(rs.getString("ODR_PHONE"));
				OdrVO.setOdr_mail(rs.getString("ODR_MAIL"));
				OdrVO.setOdr_buyamt(rs.getInt("ODR_BUYAMT"));
				OdrVO.setOdr_toprc(rs.getInt("ODR_TOPRC"));
				OdrVO.setOdr_paymode(rs.getInt("ODR_PAYMODE"));
				OdrVO.setOdr_paytime(rs.getTimestamp("ODR_PAYTIME"));
				OdrVO.setOdr_time(rs.getTimestamp("ODR_TIME"));
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
		return OdrVO;
	}

	@Override
	public List<OdrVO> getAll() {
		List<OdrVO> list = new ArrayList<OdrVO>();
		OdrVO OdrVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// OdrVO �]�٬� Domain objects
				OdrVO = new OdrVO();
				OdrVO.setOdr_no(rs.getString("ODR_NO"));
				OdrVO.setMem_no(rs.getInt("MEM_NO"));
				OdrVO.setCop_no(rs.getInt("COP_NO"));
				OdrVO.setOdr_state(rs.getString("ODR_STATE"));
				OdrVO.setOdr_seqnum(rs.getString("ODR_SEQNUM"));
				OdrVO.setOdr_usdtms(rs.getInt("ODR_USDTMS"));
				OdrVO.setOdr_payname(rs.getString("ODR_PAYNAME"));
				OdrVO.setOdr_phone(rs.getString("ODR_PHONE"));
				OdrVO.setOdr_mail(rs.getString("ODR_MAIL"));
				OdrVO.setOdr_buyamt(rs.getInt("ODR_BUYAMT"));
				OdrVO.setOdr_toprc(rs.getInt("ODR_TOPRC"));
				OdrVO.setOdr_paymode(rs.getInt("ODR_PAYMODE"));
				OdrVO.setOdr_paytime(rs.getTimestamp("ODR_PAYTIME"));
				OdrVO.setOdr_time(rs.getTimestamp("ODR_TIME"));
				list.add(OdrVO); // Store the row in the vector
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
	public List<OdrVO> getMemOdr(Integer mem_no) {
		List<OdrVO> list = new ArrayList<OdrVO>();
		OdrVO OdrVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_ODR);
			pstmt.setInt(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// OdrVO �]�٬� Domain objects
				OdrVO = new OdrVO();
				OdrVO.setOdr_no(rs.getString("ODR_NO"));
				OdrVO.setMem_no(rs.getInt("MEM_NO"));
				OdrVO.setCop_no(rs.getInt("COP_NO"));
				OdrVO.setOdr_state(rs.getString("ODR_STATE"));
				OdrVO.setOdr_seqnum(rs.getString("ODR_SEQNUM"));
				OdrVO.setOdr_usdtms(rs.getInt("ODR_USDTMS"));
				OdrVO.setOdr_payname(rs.getString("ODR_PAYNAME"));
				OdrVO.setOdr_phone(rs.getString("ODR_PHONE"));
				OdrVO.setOdr_mail(rs.getString("ODR_MAIL"));
				OdrVO.setOdr_buyamt(rs.getInt("ODR_BUYAMT"));
				OdrVO.setOdr_toprc(rs.getInt("ODR_TOPRC"));
				OdrVO.setOdr_paymode(rs.getInt("ODR_PAYMODE"));
				OdrVO.setOdr_paytime(rs.getTimestamp("ODR_PAYTIME"));
				OdrVO.setOdr_time(rs.getTimestamp("ODR_TIME"));
				OdrVO.setCop_name(rs.getString("COP_NAME"));
				OdrVO.setRest_name(rs.getString("REST_NAME"));
				OdrVO.setCop_dl(rs.getDate("COP_DL").toString());
				
				list.add(OdrVO); // Store the row in the vector
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
	public List<OdrVO> getAllPayedOrder(Map<String, String[]> map) {
		List<OdrVO> list = new ArrayList<OdrVO>();
		OdrVO OdrVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * from orders "
			          + jdbcUtil_CompositeQuery_PayedOrder.get_WhereCondition(map)
			          + "order by odr_no DESC";
	System.out.println("����finalSQL = " + finalSQL);
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// OdrVO �]�٬� Domain objects
				OdrVO = new OdrVO();
				OdrVO.setOdr_no(rs.getString("ODR_NO"));
				OdrVO.setMem_no(rs.getInt("MEM_NO"));
				OdrVO.setCop_no(rs.getInt("COP_NO"));
				OdrVO.setOdr_state(rs.getString("ODR_STATE"));
				OdrVO.setOdr_seqnum(rs.getString("ODR_SEQNUM"));
				OdrVO.setOdr_usdtms(rs.getInt("ODR_USDTMS"));
				OdrVO.setOdr_payname(rs.getString("ODR_PAYNAME"));
				OdrVO.setOdr_phone(rs.getString("ODR_PHONE"));
				OdrVO.setOdr_mail(rs.getString("ODR_MAIL"));
				OdrVO.setOdr_buyamt(rs.getInt("ODR_BUYAMT"));
				OdrVO.setOdr_toprc(rs.getInt("ODR_TOPRC"));
				OdrVO.setOdr_paymode(rs.getInt("ODR_PAYMODE"));
				OdrVO.setOdr_paytime(rs.getTimestamp("ODR_PAYTIME"));
				OdrVO.setOdr_time(rs.getTimestamp("ODR_TIME"));
				list.add(OdrVO); // Store the row in the vector
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
	

}
