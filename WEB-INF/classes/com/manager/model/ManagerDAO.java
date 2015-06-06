package com.manager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ads.model.AdsVO;

public class ManagerDAO implements ManagerDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO manager (mgr_no, mgr_account , mgr_pwd, mgr_name, mgr_mail, mgr_phone) VALUES (sequence_mgr_no.NEXTVAL, ?, ?,?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM manager    order by mgr_no  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM manager   WHERE  mgr_no = ? ORDER BY mgr_no";
	private static final String CHECK_ONE_STMT = "SELECT  *  FROM manager   WHERE  mgr_account = ? ORDER BY mgr_no";
	private static final String DELETE = "DELETE FROM manager WHERE mgr_no = ?";
	private static final String UPDATE = "UPDATE manager  SET mgr_pwd=? ,mgr_name=? ,mgr_mail=?,mgr_phone = ? WHERE mgr_no=? ";
 
	public ManagerVO checkAccount(String mgr_account) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_ONE_STMT);

			pstmt.setString(1, mgr_account);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				managerVO = new ManagerVO();
				
				managerVO.setMgr_account(rs.getString("mgr_account"));
				managerVO.setMgr_pwd(rs.getString("mgr_pwd"));
				managerVO.setMgr_name(rs.getString("mgr_name"));
				managerVO.setMgr_mail(rs.getString("mgr_mail"));
				managerVO.setMgr_phone(rs.getString("mgr_phone"));

			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public void insert(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, mgr_no);

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
	public void update(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

		
			pstmt.setString(1, managerVO.getMgr_pwd());
			pstmt.setString(2, managerVO.getMgr_name());
			pstmt.setString(3, managerVO.getMgr_mail());
			pstmt.setString(4, managerVO.getMgr_phone());
			pstmt.setInt(5, managerVO.getMgr_no());
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
	public ManagerVO findByPrimaryKey(Integer mgr_no) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
