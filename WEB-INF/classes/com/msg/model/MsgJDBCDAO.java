package com.msg.model;

import java.util.*;
import java.sql.*;

public class MsgJDBCDAO implements MsgDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user1";
	String passwd = "u111";

	private static final String INSERT_STMT = 
		"INSERT INTO MESSAGE (mess_no,rest_no,mess_name,mess_cont) VALUES (mess_no_seq.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT mess_no,rest_no,mess_name,mess_cont FROM MESSAGE order by mess_no";
	private static final String GET_ONE_STMT = 
		"SELECT mess_no,rest_no,mess_name,mess_cont FROM MESSAGE where mess_no = ?";
	private static final String DELETE = 
		"DELETE FROM MESSAGE where mess_no = ?";
	private static final String UPDATE = 
		"UPDATE MESSAGE set rest_no=?, mess_name=?, mess_cont=? where mess_no = ?";

	@Override
	public void insert(MsgVO messVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			//pstmt.setInt(1,messVO.getMsg_no());
			pstmt.setInt(1,messVO.getRest_no());
			pstmt.setString(2,messVO.getMsg_name());
			pstmt.setString(3,messVO.getMsg_cont());

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
	public void update(MsgVO messVO) {	

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,messVO.getRest_no());
			pstmt.setString(2,messVO.getMsg_name());
			pstmt.setString(3,messVO.getMsg_cont());
			pstmt.setInt(4,messVO.getMsg_no());
			
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
	public void delete(Integer mess_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, mess_no);

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
	public MsgVO findByPrimaryKey(Integer mess_no) {

		MsgVO messVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, mess_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// messVO �]�٬� Domain objects
				messVO = new MsgVO();
				messVO.setMsg_no(rs.getInt("mess_no"));
				messVO.setRest_no(rs.getInt("rest_no"));
				messVO.setMsg_name(rs.getString("mess_name"));
				messVO.setMsg_cont(rs.getString("mess_cont"));
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
		return messVO;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO messVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// messVO �]�٬� Domain objects
				messVO = new MsgVO();
				messVO.setMsg_no(rs.getInt("mess_no"));
				messVO.setRest_no(rs.getInt("rest_no"));
				messVO.setMsg_name(rs.getString("mess_name"));
				messVO.setMsg_cont(rs.getString("mess_cont"));
				list.add(messVO); // Store the row in the list
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

		MsgJDBCDAO dao = new MsgJDBCDAO();

		// V�s�W
		MsgVO messVO1 = new MsgVO();
		messVO1.setRest_no(7003);
		messVO1.setMsg_name("peter123");
		messVO1.setMsg_cont("very decilous!!");
		dao.insert(messVO1);

//		// V�ק�
//		MessVO messVO2 = new MessVO();
//		messVO2.setMsg_no(60003);
//		messVO2.setRest_no(7001);
//		messVO2.setMsg_name("bbb");
//		messVO2.setMsg_cont("!!");
//		dao.update(messVO2);
//
//		// V�R��
//		dao.delete(60001);
//
//		// V�d��
//		MessVO messVO3 = dao.findByPrimaryKey(60001);
//		System.out.print(messVO3.getMsg_no() + ",");
//		System.out.print(messVO3.getRest_no() + ",");
//		System.out.print(messVO3.getMsg_name() + ",");
//		System.out.print(messVO3.getMsg_cont() + ",");
//		System.out.println("---------------------");

		// V�d��
		List<MsgVO> list = dao.getAll();
		for (MsgVO aMess : list) {
			System.out.print(aMess.getMsg_no() + ",");
			System.out.print(aMess.getRest_no() + ",");
			System.out.print(aMess.getMsg_name() + ",");
			System.out.print(aMess.getMsg_cont() + ",");
			System.out.println();
		}
	}
}