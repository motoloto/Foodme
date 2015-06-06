package com.banner.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.news.model.NewsVO;

public class BannerJDBCDAO implements BannerDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = "INSERT INTO banner (banner_no,rest_no,banner_title,banner_cont,banner_pic,banner_dl) VALUES (banner_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM banner WHERE banner_dl>  SYSDATE  order by banner_dl  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM banner  WHERE banner_no=?";
	private static final String DELETE = "DELETE FROM banner where banner_no = ?";
	private static final String UPDATE = "UPDATE banner set rest_no=?, banner_title=?, banner_cont=?, banner_pic=? ,banner_dl=? where banner_no = ?";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BannerJDBCDAO dao = new BannerJDBCDAO();

		// 新增
		BannerVO bannerVO1 = new BannerVO();
		bannerVO1.setRest_no(7001);
		bannerVO1.setBanner_title("橫幅標題");
		bannerVO1.setBanner_cont("橫幅內容");
		bannerVO1.setBanner_dl(java.sql.Date.valueOf("2015-03-06"));
		dao.insert(bannerVO1);

		// 修改
		BannerVO bannerVO2 = new BannerVO();
		bannerVO2.setBanner_no(40002);
		bannerVO2.setBanner_title("橫幅標題");
		bannerVO2.setRest_no(7001);
		bannerVO2.setBanner_cont("橫幅內容");
		bannerVO2.setBanner_dl(java.sql.Date.valueOf("2015-02-28"));
		dao.update(bannerVO2);

		// 刪除
		// dao.delete(40007);

		// GET ALL BANNER
		// List<BannerVO> list = dao.getAll();
		// for (BannerVO aBanner : list) {
		// System.out.print(aBanner.getBanner_no() + ",");
		// System.out.print(aBanner.getBanner_title() + ",");
		// System.out.print(aBanner.getBanner_cont() + ",");
		// System.out.print(aBanner.getBanner_dl() + ",");
		// System.out.print(aBanner.getRest_no() + ",");
		// System.out.println();
		// }

		//GET ONE BANNER
		BannerVO bannerVO3 = dao.findByPrimaryKey(40001);
		System.out.print(bannerVO3.getBanner_no() + ",");
		System.out.print(bannerVO3.getBanner_title() + ",");
		System.out.print(bannerVO3.getBanner_cont() + ",");
		System.out.print(bannerVO3.getBanner_dl() + ",");
		System.out.print(bannerVO3.getRest_no() + ",");

	}

	@Override
	public void insert(BannerVO bannerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public void delete(Integer banner_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, banner_no);

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
	public void update(BannerVO bannerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, bannerVO.getRest_no());
			pstmt.setString(2, bannerVO.getBanner_title());
			pstmt.setString(3, bannerVO.getBanner_cont());
			pstmt.setBytes(4, bannerVO.getBanner_pic());
			pstmt.setDate(5, bannerVO.getBanner_dl());
			pstmt.setInt(6, bannerVO.getBanner_no());
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
	public BannerVO findByPrimaryKey(Integer banner_no) {

		BannerVO bannerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, banner_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				bannerVO = new BannerVO();
				bannerVO.setBanner_no(rs.getInt("banner_no"));
				bannerVO.setRest_no(rs.getInt("rest_no"));
				bannerVO.setBanner_title(rs.getString("banner_title"));
				bannerVO.setBanner_cont(rs.getString("banner_cont"));
				bannerVO.setBanner_dl(rs.getDate("banner_dl"));

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
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
