package com.dish.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.news.model.NewsJDBCDAO;
import com.news.model.NewsVO;

public class DishJDBCDAO implements DishDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = "INSERT INTO dish (dish_no,rest_no,dish_name,dish_cont,dish_price,dish_state,dish_pic) VALUES (dish_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM dish order by dish_no";
	private static final String GET_ONE_STMT = "SELECT  *  FROM dish where dish_no = ?  order by dish_no";
	private static final String DELETE = "DELETE FROM dish where dish_no = ?";
	private static final String UPDATE = "UPDATE dish SET rest_no=?, dish_name=?, dish_cont=?, dish_price=? ,dish_state=? ,dish_pic=? where dish_no = ?";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DishJDBCDAO dao = new DishJDBCDAO();

		//  INSERT
		DishVO dishVO1 = new DishVO();
		dishVO1.setRest_no(7001);
		dishVO1.setDish_name("滷肉飯");
		// dishVO1.setDish_pic(dish_pic);
		dishVO1.setDish_price(1280);
		dishVO1.setDish_state(1);
		dishVO1.setDish_cont("醬油+肉+飯");
		dao.insert(dishVO1);

		// UPDATE
		DishVO dishVO2 = new DishVO();
		dishVO2.setDish_no(5002);
		dishVO2.setRest_no(7001);
		dishVO2.setDish_name("滷肉飯加2飯");
		// dishVO2.setDish_pic(dish_pic);
		dishVO2.setDish_price(1280);
		dishVO2.setDish_state(1);
		dishVO2.setDish_cont("醬油+肉+飯");
		dao.update(dishVO2);

		// DELETE
		// dao.delete(30001);

		// FIND ONE
		DishVO dishVO3 = dao.findByPrimaryKey(5002);
		System.out.print(dishVO3.getDish_no() + ",");
		System.out.print(dishVO3.getRest_no() + ",");
		System.out.print(dishVO3.getDish_name() + ",");
		System.out.print(dishVO3.getDish_state() + ",");
		System.out.print(dishVO3.getDish_cont() + ",");
		System.out.print(dishVO3.getDish_price() + ",");
		// System.out.print(dishVO3.getDish_pic() + ",");
		System.out.println("---------------------");

		// GET ALL
		List<DishVO> list = dao.getAll();
		for (DishVO adish : list) {
			System.out.print(adish.getDish_no() + ",");
			System.out.print(adish.getRest_no() + ",");
			System.out.print(adish.getDish_name() + ",");
			System.out.print(adish.getDish_state() + ",");
			System.out.print(adish.getDish_cont() + ",");
			System.out.print(adish.getDish_price() + ",");
			// System.out.print(adish.getDish_pic() + ",");
			System.out.println();
		}

	}

	@Override
	public void insert(DishVO dishVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, dishVO.getRest_no());
			pstmt.setString(2, dishVO.getDish_name());
			pstmt.setString(3, dishVO.getDish_cont());
			pstmt.setDouble(4, dishVO.getDish_price());
			pstmt.setInt(5, dishVO.getDish_state());
			pstmt.setBytes(6, dishVO.getDish_pic());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, dish_no);

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
	public void update(DishVO dishVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, dishVO.getRest_no());
			pstmt.setString(2, dishVO.getDish_name());
			pstmt.setString(3, dishVO.getDish_cont());
			pstmt.setDouble(4, dishVO.getDish_price());
			pstmt.setInt(5, dishVO.getDish_state());
			pstmt.setBytes(6, dishVO.getDish_pic());
			pstmt.setInt(7, dishVO.getDish_no());
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
	public DishVO findByPrimaryKey(Integer dish_no) {

		DishVO dishVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, dish_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				dishVO = new DishVO();
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				 dishVO.setDish_name(rs.getString("dish_name"));
//				 dishVO.setDish_pic(rs.getBytes("dish_pic"));
				 dishVO.setDish_price(rs.getDouble("dish_price"));
				 dishVO.setDish_state(rs.getInt("dish_state"));

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
		return dishVO;
	}

	@Override
	public List<DishVO> getAll() {
		List<DishVO> list = new ArrayList<DishVO>();
		DishVO dishVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				dishVO = new DishVO();
				dishVO.setDish_no(rs.getInt("dish_no"));
				dishVO.setRest_no(rs.getInt("rest_no"));
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_cont(rs.getString("dish_cont"));
				dishVO.setDish_pic(rs.getBytes("dish_pic"));
				dishVO.setDish_price(rs.getDouble("dish_price"));
				dishVO.setDish_state(rs.getInt("dish_state"));
				list.add(dishVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public List<DishVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DishVO> getList(Integer dish_no) {
		List<DishVO> list = new ArrayList<DishVO>();
		DishVO dishVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				dishVO = new DishVO();
				dishVO.setDish_name(rs.getString("dish_name"));
				dishVO.setDish_cont(rs.getString("dish_cont"));
				dishVO.setDish_pic(rs.getBytes("dish_pic"));
				dishVO.setDish_price(rs.getDouble("dish_price"));
				dishVO.setDish_state(rs.getInt("dish_state"));
				list.add(dishVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
