package com.manager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ads.model.AdsVO;

public class ManagerJDBCDAO implements ManagerDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = "INSERT INTO manager (mgr_no, mgr_account , mgr_pwd, mgr_name, mgr_mail, mgr_phone) VALUES (sequence_mem_no.NEXTVAL, ?, ?,?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM manager    order by mgr_no  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM manager   WHERE  mgr_no = ? ORDER BY mgr_no";
	private static final String CHECK_ONE_STMT = "SELECT  *  FROM manager   WHERE  mgr_account = ? ORDER BY mgr_no";
	private static final String DELETE = "DELETE FROM manager WHERE mgr_no = ?";
	private static final String UPDATE = "UPDATE manager  SET mgr_account=?, mgr_pwd=? ,mgr_name=? ,mgr_mail=?,mgr_phone = ? WHERE mgr_no=? ";

	public static void main(String[] args) {
		ManagerJDBCDAO  dao =new ManagerJDBCDAO();
		// 新增帳號
		ManagerVO managerVO1 = new ManagerVO();
		 managerVO1.setMgr_account("admin");
		 managerVO1.setMgr_pwd("admin");
		 managerVO1.setMgr_name("超級管理員");
		 managerVO1.setMgr_mail("motoloto@livemail.tw");
		 managerVO1.setMgr_phone("123-1234");
		 dao.insert(managerVO1);
		
		//修改帳號
//		ManagerVO managerVO1 = new ManagerVO();
//		managerVO1.setMgr_no(50064); 
//		managerVO1.setMgr_account("admin");
//		 managerVO1.setMgr_pwd("admin");
//		 managerVO1.setMgr_name("超級管理員3");
//		 managerVO1.setMgr_mail("motoloto@livemail.tw");
//		 managerVO1.setMgr_phone("123-1234");
//		 dao.update(managerVO1);
		 
		 //刪除帳號
//		 dao.delete(50064);
		 
		 //GET ALL
//		 List<ManagerVO> list = dao.getAll();
//			for (ManagerVO aMgr : list) {
//				System.out.print(aMgr.getMgr_no() + ",");
//				System.out.print(aMgr.getMgr_account() + ",");
//				System.out.print(aMgr.getMgr_pwd() + ",");
//				System.out.print(aMgr.getMgr_name() + ",");
//				System.out.print(aMgr.getMgr_phone()+ ",");
//				System.out.print(aMgr.getMgr_mail()+ ",");
//				System.out.println();   
//			}
		
		 //查詢帳號
//		ManagerVO managerVO=dao.findByPrimaryKey(10003);
//		System.out.print(managerVO.getMgr_no() + ",");
//		System.out.print(managerVO.getMgr_account() + ",");
//		System.out.print(managerVO.getMgr_pwd() + ",");
//		System.out.print(managerVO.getMgr_name() + ",");
//		System.out.print(managerVO.getMgr_phone()+ ",");
//		System.out.print(managerVO.getMgr_mail()+ ",");
		
		//檢查帳號
		ManagerVO managerVO=dao.checkAccount("admin");
		System.out.print(managerVO.getMgr_no() + ",");
		System.out.print(managerVO.getMgr_account() + ",");
		System.out.print(managerVO.getMgr_pwd() + ",");
		System.out.print(managerVO.getMgr_name() + ",");
		System.out.print(managerVO.getMgr_phone()+ ",");
		System.out.print(managerVO.getMgr_mail()+ ",");
	
	}



	@Override
	public void insert(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, managerVO.getMgr_account());
			pstmt.setString(2, managerVO.getMgr_pwd());
			pstmt.setString(3, managerVO.getMgr_name());
			pstmt.setString(4, managerVO.getMgr_mail());
			pstmt.setString(5, managerVO.getMgr_phone());

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
	public void delete(Integer mgr_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, mgr_no);

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
	public void update(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, managerVO.getMgr_account());
			pstmt.setString(2, managerVO.getMgr_pwd());
			pstmt.setString(3, managerVO.getMgr_name());
			pstmt.setString(4, managerVO.getMgr_mail());
			pstmt.setString(5, managerVO.getMgr_phone());
			pstmt.setInt(6, managerVO.getMgr_no());
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
	public ManagerVO findByPrimaryKey(Integer mgr_no) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, mgr_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				managerVO = new ManagerVO();
				managerVO.setMgr_no(rs.getInt("mgr_no"));
				managerVO.setMgr_account(rs.getString("mgr_account"));
				managerVO.setMgr_pwd(rs.getString("mgr_pwd"));
				managerVO.setMgr_name(rs.getString("mgr_name"));
				managerVO.setMgr_mail(rs.getString("mgr_mail"));
				managerVO.setMgr_phone(rs.getString("mgr_phone"));

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
		return managerVO;
	}

	@Override
	public List<ManagerVO> getAll() {
		List<ManagerVO> list = new ArrayList<ManagerVO>();
		ManagerVO managerVO = null;

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
				managerVO = new ManagerVO();
				managerVO.setMgr_no(rs.getInt("mgr_no"));
				managerVO.setMgr_account(rs.getString("mgr_account"));
				managerVO.setMgr_pwd(rs.getString("mgr_pwd"));
				managerVO.setMgr_name(rs.getString("mgr_name"));
				managerVO.setMgr_mail(rs.getString("mgr_mail"));
				managerVO.setMgr_phone(rs.getString("mgr_phone"));
				list.add(managerVO); // Store the row in the list
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
	public ManagerVO checkAccount(String mgr_account) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CHECK_ONE_STMT);

			pstmt.setString(1, mgr_account);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				managerVO = new ManagerVO();
				managerVO.setMgr_no(rs.getInt("mgr_no"));
				managerVO.setMgr_account(rs.getString("mgr_account"));
				managerVO.setMgr_pwd(rs.getString("mgr_pwd"));
				managerVO.setMgr_name(rs.getString("mgr_name"));
				managerVO.setMgr_mail(rs.getString("mgr_mail"));
				managerVO.setMgr_phone(rs.getString("mgr_phone"));

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
		return managerVO;
	}
}
