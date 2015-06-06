package com.dish.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DishDAO implements DishDAO_interface {

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
			"INSERT INTO dish (dish_no, rest_no, dish_name, dish_cont, dish_price, dish_state,dish_pic) VALUES (dish_seq.NEXTVAL, ?, ?, ?, ?, ?,?)";
//		private static final String GET_ALL_STMT = "SELECT dish_no,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 order by dish_no";
		private static final String DELETE = "DELETE FROM dish where dish_no = ?";
		private static final String UPDATE = "UPDATE dish set dish_name=?, dish_cont=?, dish_state=?, dish_price=?, dish_pic=? where dish_no = ?";
//		private static final String GET_ALL_STMT = "SELECT dish_no,rest_no ,dish_name FROM dish  order by dish_no where dish_name LIKE %?%";
		private static final String GET_ALL_STMT = "SELECT * FROM dish order by dish_no";
		private static final String GET_ONE_STMT = "SELECT dish_no, rest_no, dish_name, dish_pic FROM dish  where dish_name LIKE ?";
		private static final String GET_IMG = "SELECT dish_no, rest_no, dish_name, dish_cont, dish_state, dish_price, dish_pic FROM dish  where dish_no = ?";
		private static final String GET_REST_DISH = "SELECT dish_no, rest_no, dish_name, dish_cont, dish_state, dish_price FROM dish  where rest_no = ?";

	@Override
	public void insert(DishVO dishVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

//			pstmt.setInt(1, dishVO.getDish_no());
			pstmt.setInt(1, dishVO.getRest_no());
			pstmt.setString(2, dishVO.getDish_name());
			pstmt.setString(3, dishVO.getDish_cont());
			pstmt.setInt(4, dishVO.getDish_price());
			pstmt.setString(5, dishVO.getDish_state());
			pstmt.setBytes(6, dishVO.getDish_pic());

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
	public void update(DishVO dishVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, dishVO.getDish_name());
			pstmt.setString(2, dishVO.getDish_cont());
			pstmt.setString(3, dishVO.getDish_state());
			pstmt.setInt(4, dishVO.getDish_price());
			pstmt.setBytes(5, dishVO.getDish_pic());
			pstmt.setInt(6, dishVO.getDish_no());

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
	public void delete(Integer dish_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, dish_no);

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
	public DishVO findByPrimaryKey(Integer dish_no) {

		DishVO dishVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DishVO> list = new ArrayList<DishVO>();

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_IMG);

			pstmt.setInt(1, dish_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				dishVO = new DishVO();
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_cont(rs.getString("dish_cont"));
				dishVO.setDish_state(rs.getString("dish_state"));
				dishVO.setDish_price(rs.getInt("dish_price"));
				dishVO.setDish_pic(rs.getBytes("dish_pic"));	
				//clob skip				
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
		return dishVO;
	}
	
	@Override
	public List<DishVO> findByRest_no(Integer rest_no) {

		DishVO dishVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DishVO> list = new ArrayList<DishVO>();

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REST_DISH);

			pstmt.setInt(1, rest_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				dishVO = new DishVO();
				
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_cont(rs.getString("dish_cont"));
				dishVO.setDish_state(rs.getString("dish_state"));
				dishVO.setDish_price(rs.getInt("dish_price"));	
				
				list.add(dishVO);

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
	public List<DishVO> getAll() {
		List<DishVO> list = new ArrayList<DishVO>();
		DishVO dishVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);			
//			pstmt.setString(1, dishVO.getDish_name());
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// dishVO �]�٬� Domain objects
				dishVO = new DishVO();
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_pic(rs.getBytes("dish_pic"));
				dishVO.setDish_state(rs.getString("dish_state"));
				dishVO.setDish_price(rs.getInt("dish_price"));
				//clob skip
				
				list.add(dishVO);
			}
			
//			System.out.println(dish_no);

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
	public List<DishVO> query(String dish_cont) {
		List<DishVO> list = new ArrayList<DishVO>();
//		DishVO dishVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);	
									
//			pstmt.setString(1, "%dish_cont%");		'%?%' //字串包含"變數"要用"+"串接 如下
			pstmt.setString(1, "%"+dish_cont+"%");	
						
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// dishVO �]�٬� Domain objects
				DishVO dishVO = null;
				dishVO = new DishVO();
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_pic(rs.getBytes("dish_pic"));
				list.add(dishVO);
			}
			
//			System.out.println(dish_no);

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
	public String[] querytext(String dish_cont) {	//未完 寫一半..
		List<DishVO> list = new ArrayList<DishVO>();


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		
		
		return null;
	}
}