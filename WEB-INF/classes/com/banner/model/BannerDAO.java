package com.banner.model;

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

public class BannerDAO implements BannerDAO_interface {
	/*   Dats source*/
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO banner (banner_no,rest_no,banner_title,banner_cont,banner_pic,banner_dl) VALUES (banner_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM banner WHERE banner_dl>  SYSDATE  order by banner_dl  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM banner  WHERE banner_no=?";
	private static final String DELETE = "DELETE FROM banner where banner_no = ?";
	private static final String UPDATE = "UPDATE banner set rest_no=?, banner_title=?, banner_cont=?, banner_pic=? ,banner_dl=? where banner_no = ?";


	@Override
	public void insert(BannerVO bannerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, bannerVO.getRest_no());
			pstmt.setString(2, bannerVO.getBanner_title());
			pstmt.setString(3, bannerVO.getBanner_cont());
			pstmt.setBytes(4, bannerVO.getBanner_pic());
			pstmt.setDate(5, bannerVO.getBanner_dl());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}  finally {
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
	public void delete(Integer banner_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, banner_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public void update(BannerVO bannerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, bannerVO.getRest_no());
			pstmt.setString(2, bannerVO.getBanner_title());
			pstmt.setString(3, bannerVO.getBanner_cont());
			pstmt.setBytes(4, bannerVO.getBanner_pic());
			pstmt.setDate(5, bannerVO.getBanner_dl());
			pstmt.setInt(6, bannerVO.getBanner_no());
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
	public BannerVO findByPrimaryKey(Integer banner_no) {
		BannerVO bannerVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, banner_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo  Domain objects
				bannerVO = new BannerVO();
				bannerVO.setBanner_no(rs.getInt("banner_no"));
				bannerVO.setRest_no(rs.getInt("rest_no"));
				bannerVO.setBanner_title(rs.getString("banner_title"));
				bannerVO.setBanner_pic(rs.getBytes("banner_pic"));
				bannerVO.setBanner_cont(rs.getString("banner_cont"));
				bannerVO.setBanner_dl(rs.getDate("banner_dl"));

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
		return bannerVO;
	}

	@Override
	public List<BannerVO> getAll() {

		List<BannerVO> list = new ArrayList<BannerVO>();
		BannerVO bannerVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO Domain objects
				bannerVO = new BannerVO();
				bannerVO.setBanner_no(rs.getInt("banner_no"));
				bannerVO.setRest_no(rs.getInt("rest_no"));
				bannerVO.setBanner_title(rs.getString("banner_title"));
				bannerVO.setBanner_cont(rs.getString("banner_cont"));
				bannerVO.setBanner_pic(rs.getBytes("banner_pic"));
				bannerVO.setBanner_dl(rs.getDate("banner_dl"));
				list.add(bannerVO); // Store the row in the list
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
