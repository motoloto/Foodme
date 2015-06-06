package com.msg.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MsgDAO implements MsgDAO_interface {

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
		"INSERT INTO MESSAGE (msg_no,rest_no,msg_name,msg_cont) VALUES (SEQUENCE_MSG_NO.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT msg_no,rest_no,msg_name,msg_cont FROM MESSAGE order by msg_no";
	private static final String GET_ONE_STMT = 
		"SELECT msg_no,rest_no,msg_name,msg_cont FROM MESSAGE where msg_no = ?";
	private static final String DELETE = 
		"DELETE FROM MESSAGE where msg_no = ?";
	private static final String UPDATE = 
		"UPDATE MESSAGE set rest_no=?, msg_name=?, msg_cont=? where msg_no = ?";
	private static final String GET_ONE_STMT_BY_REST = 
			"SELECT msg_no,rest_no,msg_name,msg_cont FROM MESSAGE where rest_no = ? order by msg_no DESC";

	@Override
	public void insert(MsgVO msgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,msgVO.getRest_no());
			pstmt.setString(2,msgVO.getMsg_name());
			pstmt.setString(3,msgVO.getMsg_cont());

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
	public void update(MsgVO msgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,msgVO.getMsg_no());
			pstmt.setInt(2,msgVO.getRest_no());
			pstmt.setString(3,msgVO.getMsg_name());
			pstmt.setString(4,msgVO.getMsg_cont());

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
	public void delete(Integer msg_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, msg_no);

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
	public MsgVO findByPrimaryKey(Integer msg_no) {

		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, msg_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// msgVO �]�٬� Domain objects
				msgVO = new MsgVO();
				msgVO.setMsg_no(rs.getInt("msg_no"));
				msgVO.setRest_no(rs.getInt("rest_no"));
				msgVO.setMsg_name(rs.getString("msg_name"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));
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
		return msgVO;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO msgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// msgVO �]�٬� Domain objects
				msgVO.setMsg_no(rs.getInt("msg_no"));
				msgVO.setRest_no(rs.getInt("rest_no"));
				msgVO.setMsg_name(rs.getString("msg_name"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));
				list.add(msgVO); // Store the row in the list
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

	public List<MsgVO> findByforeignKey(Integer rest_no) {
		MsgVO msgVO = null;
		List<MsgVO> msgVOList=new ArrayList<MsgVO>() ;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_REST);

			pstmt.setInt(1, rest_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// msgVO �]�٬� Domain objects
				msgVO = new MsgVO();
				msgVO.setMsg_no(rs.getInt("msg_no"));
				msgVO.setRest_no(rs.getInt("rest_no"));
				msgVO.setMsg_name(rs.getString("msg_name"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));
				msgVOList.add(msgVO);
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
		return msgVOList;
	}
}