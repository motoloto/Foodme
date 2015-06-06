package com.ads.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.websocket.Encoder.BinaryStream;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

public class AdsDAO implements AdsDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ads (ads_no, rest_no, ads_title, ads_pic, ads_dl) VALUES (ads_seq.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT  *  FROM ads WHERE ads_dl>  SYSDATE  order by ads_dl  ";
	private static final String GET_ONE_STMT = "SELECT  *  FROM ads   WHERE  ads_no = ? ORDER BY ads_no";
	private static final String DELETE = "DELETE FROM ads WHERE ads_no = ?";
	private static final String UPDATE = "UPDATE ads SET rest_no=?, ads_title=? ,ads_pic=? ,ads_dl=? where ads_no = ?";

	@Override
	public void insert(AdsVO adsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, adsVO.getRest_no());
			pstmt.setString(2, adsVO.getAds_title());
			InputStream myInputStream = new ByteArrayInputStream(adsVO.getAds_pic()); 
			pstmt.setBinaryStream(3, myInputStream);
			pstmt.setDate(4, adsVO.getAds_dl());

			pstmt.executeUpdate();
			myInputStream.close();
			pstmt.close();
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

	@Override
	public void delete(Integer ads_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ads_no);

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
	public void update(AdsVO adsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, adsVO.getRest_no());
			pstmt.setString(2, adsVO.getAds_title());
			InputStream myInputStream = new ByteArrayInputStream(adsVO.getAds_pic()); 
			pstmt.setBinaryStream(3, myInputStream);
			pstmt.setBytes(3, adsVO.getAds_pic());
			pstmt.setDate(4, adsVO.getAds_dl());
			pstmt.setInt(5, adsVO.getAds_no());

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
	public AdsVO findByPrimaryKey(Integer ads_no) {

		AdsVO adsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ads_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adsVO = new AdsVO();
				adsVO.setAds_no(rs.getInt("ads_no"));
				adsVO.setRest_no(rs.getInt("rest_no"));
				adsVO.setAds_title(rs.getString("ads_title"));
				adsVO.setAds_dl(rs.getDate("ads_dl"));
				adsVO.setAds_pic(rs.getBytes("ads_pic"));
			}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				adsVO = new AdsVO();
				adsVO.setAds_no(rs.getInt("ads_no"));
				adsVO.setRest_no(rs.getInt("rest_no"));
				adsVO.setAds_title(rs.getString("ads_title"));

				try {
					if (rs.getBinaryStream("ads_pic") != null) {
						InputStream is = rs.getBinaryStream("ads_pic");
						ByteArrayOutputStream bo = new ByteArrayOutputStream();
						byte[] buffer = new byte[4 * 1024];
						int len = 0;

						while ((len = is.read(buffer)) != -1) {
							bo.write(buffer, 0, len);
						}
						is.close();
						adsVO.setAds_pic(bo.toByteArray());
						bo.flush();
						bo.close();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				adsVO.setAds_dl(rs.getDate("ads_dl"));
				list.add(adsVO); // Store the row in the list
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
