package com.act.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ActDAO implements ActDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO ACTIVITY (act_no,rest_no,act_name,act_cont,act_time,act_state,act_photo) VALUES (act_no_seq.NEXTVAL, ?, ?, ?, ?,?, ?)";
	private static final String GET_ALL_STMT = "SELECT act_no,rest_no,act_name,act_cont,act_time,act_state FROM ACTIVITY order by act_no";
	private static final String GET_ONE_STMT = "SELECT act_no,rest_no,act_name,act_cont,act_time,act_state,act_photo FROM ACTIVITY where act_no = ?";
	private static final String GET_LIST_OF_REST = "SELECT act_no,rest_no,act_name,act_cont,act_time,act_state,act_photo FROM ACTIVITY where rest_no = ?";
	private static final String GET_LIST_ONSTATE_OF_REST = "SELECT act_no,rest_no,act_name,act_cont,act_time,act_state,act_photo FROM ACTIVITY where rest_no = ? AND ACT_STATE='1' order by act_no DESC";
	private static final String DELETE = "DELETE FROM ACTIVITY where act_no = ?";
	private static final String UPDATE = "UPDATE ACTIVITY set rest_no=?, act_name=?, act_cont=?, act_time=?, act_state=?, act_photo=? where act_no = ?";
	private static final String UPDATENoImg = "UPDATE ACTIVITY set rest_no=?, act_name=?, act_cont=?, act_time=?, act_state=? where act_no = ?";

	@Override
	public void insert(ActVO actVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, actVO.getRest_no());
			pstmt.setString(2, actVO.getAct_name());
			pstmt.setString(3, actVO.getAct_cont());
			pstmt.setString(4, actVO.getAct_time());
			pstmt.setString(5, actVO.getAct_state());
			pstmt.setBytes(6, actVO.getAct_photo());

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
	public void update(ActVO actVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			byte[] act_photo = (actVO.getAct_photo());
			if (act_photo != null) {

				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, actVO.getRest_no());
				pstmt.setString(2, actVO.getAct_name());
				pstmt.setString(3, actVO.getAct_cont());
				pstmt.setString(4, actVO.getAct_time());
				pstmt.setString(5, actVO.getAct_state());
				pstmt.setBytes(6, actVO.getAct_photo());
				pstmt.setInt(7, actVO.getAct_no());

				pstmt.executeUpdate();
			} else {
				pstmt = con.prepareStatement(UPDATENoImg);

				pstmt.setInt(1, actVO.getRest_no());
				pstmt.setString(2, actVO.getAct_name());
				pstmt.setString(3, actVO.getAct_cont());
				pstmt.setString(4, actVO.getAct_time());
				pstmt.setString(5, actVO.getAct_state());
				pstmt.setInt(6, actVO.getAct_no());

				pstmt.executeUpdate();
			}

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
	public void delete(Integer act_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, act_no);

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
	public ActVO findByPrimaryKey(Integer act_no) {

		ActVO actVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, act_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				actVO = new ActVO();
				actVO.setAct_no(rs.getInt("act_no"));
				actVO.setRest_no(rs.getInt("rest_no"));
				actVO.setAct_name(rs.getString("act_name"));
				actVO.setAct_cont(rs.getString("act_cont"));
				actVO.setAct_time(rs.getString("act_time"));
				actVO.setAct_state(rs.getString("act_state"));
				actVO.setAct_photo(rs.getBytes("act_photo"));
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
		return actVO;
	}
	@Override
	public List<ActVO> findLisActByRest_no(Integer rest_no) {
		
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO actVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_OF_REST );
			
			pstmt.setInt(1, rest_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				actVO = new ActVO();
				actVO.setAct_no(rs.getInt("act_no"));
				actVO.setRest_no(rs.getInt("rest_no"));
				actVO.setAct_name(rs.getString("act_name"));
				actVO.setAct_cont(rs.getString("act_cont"));
				actVO.setAct_time(rs.getString("act_time"));
				actVO.setAct_state(rs.getString("act_state"));
				actVO.setAct_photo(rs.getBytes("act_photo"));
				list.add(actVO);
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
	public List<ActVO> findListOnStateOfRest(Integer rest_no) {
		
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO actVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_ONSTATE_OF_REST );
			
			pstmt.setInt(1, rest_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				actVO = new ActVO();
				actVO.setAct_no(rs.getInt("act_no"));
				actVO.setRest_no(rs.getInt("rest_no"));
				actVO.setAct_name(rs.getString("act_name"));
				actVO.setAct_cont(rs.getString("act_cont"));
				actVO.setAct_time(rs.getString("act_time"));
				actVO.setAct_state(rs.getString("act_state"));
				actVO.setAct_photo(rs.getBytes("act_photo"));
				list.add(actVO);
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
	public List<ActVO> getAll() {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO actVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// actVO �]�٬� Domain objects
				actVO = new ActVO();
				actVO.setAct_no(rs.getInt("act_no"));
				actVO.setRest_no(rs.getInt("rest_no"));
				actVO.setAct_name(rs.getString("act_name"));
				actVO.setAct_cont(rs.getString("act_cont"));
				actVO.setAct_time(rs.getString("act_time"));
				actVO.setAct_state(rs.getString("act_state"));

				list.add(actVO); // Store the row in the list
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