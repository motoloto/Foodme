package com.affi.model;

import java.util.*;
import java.sql.*;

public class AffiJDBCDAO implements AffiDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = 
		"INSERT INTO AFFILIATE (affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mobil,rest_photo,rest_mail,rest_web,rest_intro,affi_state) VALUES (affi_no_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mobil,rest_photo,rest_mail,rest_web,rest_intro,affi_state FROM AFFILIATE order by affi_no";
	private static final String GET_ONE_STMT = 
		"SELECT affi_no,bus_no,rest_name,rest_addr,rest_tel,rest_mobil,rest_photo,rest_mail,rest_web,rest_intro,affi_state FROM AFFILIATE where affi_no = ?";
	private static final String DELETE = 
		"DELETE FROM AFFILIATE where affi_no = ?";
	private static final String UPDATE = 
		"UPDATE AFFILIATE set bus_no=?, rest_name=?, rest_addr=?, rest_tel=?,rest_mobil,rest_photo, rest_mail=?, rest_web=?, rest_intro=?, affi_state=? where affi_no = ?";

	@Override
	public void insert(AffiVO affiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, affiVO.getBus_no());
			pstmt.setString(2, affiVO.getRest_name());
			pstmt.setString(3, affiVO.getRest_addr());
			pstmt.setString(4, affiVO.getRest_tel());
			pstmt.setString(5, affiVO.getRest_mobil());
						
			pstmt.setString(7, affiVO.getRest_mail());
			pstmt.setString(8, affiVO.getRest_web());
			pstmt.setString(9, affiVO.getRest_intro());
			pstmt.setString(10, affiVO.getAffi_state());
			
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
	public void update(AffiVO affiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, affiVO.getBus_no());
			pstmt.setString(2, affiVO.getRest_name());
			pstmt.setString(3, affiVO.getRest_addr());
			pstmt.setString(4, affiVO.getRest_tel());
			pstmt.setString(5, affiVO.getRest_mail());
			pstmt.setString(6, affiVO.getRest_web());
			pstmt.setString(7, affiVO.getRest_intro());
			pstmt.setString(8, affiVO.getAffi_state());
			pstmt.setInt(9, affiVO.getAffi_no());
			
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
	public void delete(Integer affi_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, affi_no);

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
	public AffiVO findByPrimaryKey(Integer affi_no) {

		AffiVO affiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, affi_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// AffiVO �]�٬� Domain objects
				affiVO = new AffiVO();
				affiVO.setAffi_no(rs.getInt("affi_no"));
				affiVO.setBus_no(rs.getString("bus_no"));
				affiVO.setRest_name(rs.getString("rest_name"));
				affiVO.setRest_addr(rs.getString("rest_addr"));
				affiVO.setRest_tel(rs.getString("rest_tel"));
				affiVO.setRest_mobil(rs.getString("rest_mobil"));				
				affiVO.setRest_mail(rs.getString("rest_mail"));
				affiVO.setRest_web(rs.getString("rest_web"));
				affiVO.setRest_intro(rs.getString("rest_intro"));
				affiVO.setAffi_state(rs.getString("affi_state"));
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
		return affiVO;
	}

	@Override
	public List<AffiVO> getAll() {
		List<AffiVO> list = new ArrayList<AffiVO>();
		AffiVO affiVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// AffiVO �]�٬� Domain objects
				affiVO = new AffiVO();
				affiVO.setAffi_no(rs.getInt("affi_no"));
				affiVO.setBus_no(rs.getString("bus_no"));
				affiVO.setRest_name(rs.getString("rest_name"));
				affiVO.setRest_addr(rs.getString("rest_addr"));
				affiVO.setRest_tel(rs.getString("rest_tel"));
				affiVO.setRest_mobil(rs.getString("rest_mobil"));
				affiVO.setRest_mail(rs.getString("rest_mail"));
				affiVO.setRest_web(rs.getString("rest_web"));
				affiVO.setRest_intro(rs.getString("rest_intro"));
				affiVO.setAffi_state(rs.getString("affi_state"));
				
				list.add(affiVO); // Store the row in the list
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

		AffiJDBCDAO dao = new AffiJDBCDAO();

		// V�s�W
		AffiVO affiVO1 = new AffiVO();
		affiVO1.setBus_no("160004");
		affiVO1.setRest_name("����");
		affiVO1.setRest_addr("�x�����n�ٰϤ��q���G�q1724��");
		affiVO1.setRest_tel("04-2327-2529");
		affiVO1.setRest_mail("4floor@gmai.comm");
		affiVO1.setRest_web("http://www.secondfloorcafe.commmm");
		affiVO1.setRest_intro("2007�~7��ȫ᪺�F�ϡA���O���B�����g�c���A�L�N�a�M��A ���a���Y�ݨ�F��j���ۯ��r���A��M�q���ߪo�M�Ӱ_���İʡA��f�ӥX���O�G�u�o��O�ڷQ�䪺�a��v�C~~");
		affiVO1.setAffi_state("0");
		dao.insert(affiVO1);

//		// V�ק�
//		AffiVO affiVO2 = new AffiVO();
//		affiVO2.setAffi_no(8005);
//		affiVO2.setBus_no("120002");
//		affiVO2.setRest_name("FEEDMEME");
//		affiVO2.setRest_addr("�x�����x������T�q13-2��22");
//		affiVO2.setRest_tel("04-263186877");
//		affiVO2.setRest_mail("FEEDME@gmai.comm");
//		affiVO2.setRest_web("http://www.feedme-tw.comm");
//		affiVO2.setRest_intro("���{���~��C");
//		affiVO2.setAffi_state("0");
//		dao.update(affiVO2);
//		
//
//		// V�R��
//		dao.delete(8004);
//
//		// V�d��	
//		AffiVO affiVO3 = dao.findByPrimaryKey(8001);
//		System.out.print(affiVO3.getBus_no() + ",");
//		System.out.print(affiVO3.getRest_name() + ",");
//		System.out.print(affiVO3.getRest_addr() + ",");
//		System.out.print(affiVO3.getRest_tel() + ",");
//		System.out.print(affiVO3.getRest_mail() + ",");
//		System.out.print(affiVO3.getRest_web() + ",");
//		System.out.print(affiVO3.getRest_intro() + ",");
//		System.out.print(affiVO3.getAffi_state() + ",");
//		System.out.println("---------------------");

		// V�d��
		List<AffiVO> list = dao.getAll();
		for (AffiVO aAffi : list) {
			System.out.print(aAffi.getAffi_no() + ",");
			System.out.print(aAffi.getBus_no() + ",");
			System.out.print(aAffi.getRest_name() + ",");
			System.out.print(aAffi.getRest_addr() + ",");
			System.out.print(aAffi.getRest_tel() + ",");
			System.out.print(aAffi.getRest_mail() + ",");
			System.out.print(aAffi.getRest_web() + ",");
			System.out.print(aAffi.getRest_intro() + ",");
			System.out.print(aAffi.getAffi_state());
			System.out.println();
		}
	}
}