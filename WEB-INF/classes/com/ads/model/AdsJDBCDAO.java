package com.ads.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.banner.model.BannerJDBCDAO;
import com.banner.model.BannerVO;

public class AdsJDBCDAO implements AdsDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = "INSERT INTO ads (ads_no, rest_no, ads_title, ads_pic, ads_dl) VALUES (ads_seq.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM ads WHERE ads_dl>  SYSDATE  order by ads_dl  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM ads   WHERE  ads_no = ? ORDER BY ads_no";
	private static final String DELETE = "DELETE FROM ads WHERE ads_no = ?";
	private static final String UPDATE = "UPDATE ads SET rest_no=?, ads_title=? ,ads_pic=? ,ads_dl=? where ads_no = ?";

	public static void main(String[] args) {
		AdsJDBCDAO dao = new AdsJDBCDAO();

		// �s�W
//		AdsVO adsVO1 = new AdsVO();
//		 adsVO1.setRest_no(7001);
//		 adsVO1.setAds_title("�p���s�i6");
//		 adsVO1.setAds_dl(java.sql.Date.valueOf("2015-01-07"));
//		 dao.insert(adsVO1);

		// �ק�
		 AdsVO adsVO2 = new AdsVO();
		adsVO2.setAds_no(50002);
		adsVO2.setAds_title("標題");
		adsVO2.setRest_no(7001);
		adsVO2.setAds_dl(java.sql.Date.valueOf("2015-02-28"));
		dao.update(adsVO2);
		
		// �R��
//		dao.delete(50007);
		
		// �d�ߥثe���L����ADS
		List<AdsVO> list = dao.getAll();
		for (AdsVO aads : list) {
			System.out.print(aads.getAds_no() + ",");
			System.out.print(aads.getAds_title() + ",");
			System.out.print(aads.getAds_dl() + ",");
			System.out.print(aads.getRest_no() + ",");
			System.out.println();   
		}
		
		//GET ONE BANNER
		AdsVO adsVO3 = dao.findByPrimaryKey(50002);
				System.out.print(adsVO3.getAds_no() + ",");
				System.out.print(adsVO3.getAds_title() + ",");
				System.out.print(adsVO3.getAds_dl() + ",");
				System.out.print(adsVO3.getRest_no() + ",");

	}

	@Override
	public void insert(AdsVO adsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, adsVO.getRest_no());
			pstmt.setString(2, adsVO.getAds_title());
			pstmt.setBytes(3, adsVO.getAds_pic());
			pstmt.setDate(4, adsVO.getAds_dl());

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
	public void delete(Integer ads_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ads_no);

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
	public void update(AdsVO adsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, adsVO.getRest_no());
			pstmt.setString(2, adsVO.getAds_title());
			pstmt.setBytes(3, adsVO.getAds_pic());
			pstmt.setDate(4, adsVO.getAds_dl());
			pstmt.setInt(5, adsVO.getAds_no());
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
	public AdsVO findByPrimaryKey(Integer ads_no) {
		AdsVO adsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ads_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				adsVO = new AdsVO();
				adsVO.setAds_no(rs.getInt("Ads_no"));
				adsVO.setRest_no(rs.getInt("rest_no"));
				adsVO.setAds_title(rs.getString("Ads_title"));
				adsVO.setAds_dl(rs.getDate("Ads_dl"));

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
		return adsVO;
	}

	@Override
	public List<AdsVO> getAll() {
		List<AdsVO> list = new ArrayList<AdsVO>();
		AdsVO adsVO = null;

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
				adsVO = new AdsVO();
				adsVO.setAds_no(rs.getInt("ads_no"));
				adsVO.setRest_no(rs.getInt("rest_no"));
				adsVO.setAds_title(rs.getString("ads_title"));
				adsVO.setAds_pic(rs.getBytes("ads_pic"));
				adsVO.setAds_dl(rs.getDate("ads_dl"));
				list.add(adsVO); // Store the row in the list
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
