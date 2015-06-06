package com.collection.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CollectionJNDIDAO implements CollectionDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	private static final String INSERT_STMT = 
		"INSERT INTO COLLECTION (MEM_NO,REST_NO) VALUES ( ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT MEM_NO,REST_NO FROM COLLECTION order by MEM_NO";
	private static final String GET_ONE_STMT = 
		"SELECT MEM_NO,REST_NO FROM COLLECTION where MEM_NO = ?";
	private static final String DELETE = 
		"DELETE FROM COLLECTION where MEM_NO = ? AND REST_NO=?";
	private static final String UPDATE = 
		"UPDATE COLLECTION set REST_NO=? where MEM_NO = ? AND REST_NO=?";

	@Override
	public int insert(CollectionVO CollectionVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, CollectionVO.getMem_no());
			pstmt.setInt(2, CollectionVO.getRest_no());
			

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
	public int update(CollectionVO CollectionVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, CollectionVO.getRest_no());
			pstmt.setInt(2, CollectionVO.getMem_no());
			pstmt.setInt(3, CollectionVO.getRest_no());

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
	public int delete(Integer MEM_NO, Integer REST_NO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, MEM_NO);
			pstmt.setInt(2, REST_NO);
			
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
	public List<CollectionVO> findByPrimaryKey(Integer MEM_NO) {

		
		List<CollectionVO> list = new ArrayList<CollectionVO>();
		CollectionVO CollectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, MEM_NO);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// CollectionVO �]�٬� Domain objects
				CollectionVO = new CollectionVO();
				
				CollectionVO.setMem_no(rs.getInt("MEM_NO"));
				CollectionVO.setRest_no(rs.getInt("REST_NO"));
				
				list.add(CollectionVO); // Store the row in the vector
				
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
	public List<CollectionVO> getAll() {
		List<CollectionVO> list = new ArrayList<CollectionVO>();
		CollectionVO CollectionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// CollectionVO �]�٬� Domain objects
				CollectionVO = new CollectionVO();
				CollectionVO.setMem_no(rs.getInt("MEM_NO"));
				CollectionVO.setRest_no(rs.getInt("REST_NO"));
				
				list.add(CollectionVO); // Store the row in the vector
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

	public static void main(String[] args) {

		CollectionJDBCDAO dao = new CollectionJDBCDAO();

		 //�s�W
//		 CollectionVO CollectionVO1 = new CollectionVO();
//		 CollectionVO1.setMem_no(10004);
//		 CollectionVO1.setRest_no(7001);
//		 int updateCount_insert = dao.insert(CollectionVO1);
//		 System.out.println(updateCount_insert);
				

//		 �ק�
//		 CollectionVO CollectionVO2 = new CollectionVO();
//		 CollectionVO2.setRest_no(7002);
//		 CollectionVO2.setMem_no(10001);
//		 
//		 int updateCount_update = dao.update(CollectionVO2);
//		 System.out.println(updateCount_update);
				

//		 �R��
//		 int updateCount_delete = dao.delete(10003,7001);
//		 System.out.println(updateCount_delete);

		// �d��
		List<CollectionVO> list = dao.findByPrimaryKey(10003);
		for (CollectionVO aEmp : list) {
			System.out.print(aEmp.getMem_no() + ",");
			System.out.print(aEmp.getRest_no() );
			

			System.out.println();
		}
            System.out.println("-----------------------");
//		// �d��
		List<CollectionVO> list1 = dao.getAll();
		for (CollectionVO aEmp : list1) {
			System.out.print(aEmp.getMem_no() + ",");
			System.out.print(aEmp.getRest_no() );
			
			
			System.out.println();
		}
	}

	@Override
	public CollectionVO findOneClc(Integer mem_no, Integer rest_no) {
		// TODO Auto-generated method stub
		return null;
	}
}
