package com.intro.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IntroDAO implements IntroDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into introduction values("
			+ "sequence_intro_no.nextval,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM introduction order by intro_no";
	private static final String GET_ONE_STMT = "SELECT * FROM introduction where intro_no = ?";
	private static final String DELETE = "DELETE FROM introduction where intro_no = ?";
	private static final String UPDATE = "UPDATE introduction set rest_no=?,  intro_pic=?, intro_cont=? where intro_no = ?";
	private static final String GET_ALL_FROM_A_REST = "SELECT * FROM introduction where rest_no = ? ORDER BY intro_ord";
	private static final String UPDATE_ORDER = "UPDATE introduction set rest_no=?,  intro_ord=? where intro_no = ?";

	public void insert(IntroVO introVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, introVO.getRest_no());
			InputStream myInputStream = new ByteArrayInputStream(
					introVO.getIntro_pic());
			pstmt.setBinaryStream(2, myInputStream);
			pstmt.setString(3, introVO.getIntro_cont());
			pstmt.setInt(4, introVO.getIntro_ord());
			pstmt.executeUpdate();
			myInputStream.close();

			// Handle any SQL errors
		} catch (SQLException | IOException se) {
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

	public void update(IntroVO vo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, vo.getRest_no());
			pstmt.setBytes(2, vo.getIntro_pic());
			pstmt.setString(3, vo.getIntro_cont());
			pstmt.setInt(4, vo.getIntro_no());

			pstmt.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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

	public int delete(Integer intro_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, intro_no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		return intro_no;

	}

	public IntroVO findByPrimaryKey(Integer intro_no) {
		IntroVO vo = new IntroVO();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, intro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo.setIntro_no(rs.getInt("intro_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setIntro_pic(rs.getBytes("intro_pic"));
				vo.setIntro_cont(rs.getString("intro_cont"));
				vo.setIntro_ord(rs.getInt("intro_ord"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return vo;
	}

	public List<IntroVO> getAll() {
		List<IntroVO> list = new ArrayList<IntroVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				IntroVO vo = new IntroVO();
				vo.setIntro_no(rs.getInt(1));
				vo.setRest_no(rs.getInt(2));
				vo.setIntro_pic(rs.getBytes(4));
				vo.setIntro_cont(rs.getString(5));
				vo.setIntro_ord(rs.getInt(6));

				list.add(vo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public List<IntroVO> findByForeignKey(Integer rest_no) {
		List<IntroVO> list = new ArrayList<IntroVO>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Connection con =null;
		try {
			 con = ds.getConnection();
			 pstmt = con.prepareStatement(GET_ALL_FROM_A_REST);
			pstmt.setInt(1, rest_no);
			 rs = pstmt.executeQuery();
			while (rs.next()) {
				IntroVO introVO = new IntroVO();
				introVO.setIntro_no(rs.getInt("INTRO_NO"));
				introVO.setRest_no(rs.getInt("REST_NO"));
				introVO.setIntro_cont(rs.getString("INTRO_CONT"));
				introVO.setIntro_ord(rs.getInt("INTRO_ORD"));
				list.add(introVO);
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void updateOrder(Integer rest_no, Integer intro_no, Integer order_no) {
		Connection con =null;
		PreparedStatement stmt =null;
		ResultSet rs=null;
		try {
			 con = ds.getConnection();
			stmt = con.prepareStatement(UPDATE_ORDER);
			stmt.setInt(1, rest_no);
			stmt.setInt(2, order_no);
			stmt.setInt(3, intro_no);
			stmt.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
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
}
