package com.mem.model;
import java.util.*;
import java.sql.*;

public class MemJDBCDAO implements MemDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "user2";
	String passwd = "u222";

	private static final String INSERT_STMT = 
		"INSERT INTO MEMBER (MEM_NO,MEM_NAME,MEM_ACCOUNT,MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS) VALUES (sequence_MEM_NO.NEXTVAL, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT MEM_NO,MEM_NAME,MEM_ACCOUNT, MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS FROM MEMBER order by MEM_NO";
	private static final String GET_ONE_STMT = 
		"SELECT MEM_NO,MEM_NAME,MEM_ACCOUNT, MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS FROM MEMBER where MEM_ACCOUNT = ?";
	private static final String DELETE = 
		"DELETE FROM MEMBER where MEM_NO = ?";
	private static final String UPDATE = 
		"UPDATE MEMBER set MEM_NAME=?, MEM_ACCOUNT=?, MEM_PWD=?, MEM_MAIL=?, MEM_PHONE=?,MEM_ADRS=?,MEM_BIRDTHDATE=?,MEM_SEX=?,MEM_NICKNAME=?,MEM_ILLTMS=? where MEM_NO = ?";

	@Override
	public int insert(MemVO memVo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memVo.getMem_name());
			pstmt.setString(2, memVo.getMem_account());
			pstmt.setString(3, memVo.getMem_pwd());
			pstmt.setString(4, memVo.getMem_mail());
			pstmt.setString(5, memVo.getMem_phone());
			pstmt.setString(6, memVo.getMem_adrs());
			pstmt.setDate(7, memVo.getMem_birthdate());
			pstmt.setString(8, memVo.getMem_sex());
			pstmt.setString(9, memVo.getMem_nickname());
			pstmt.setInt(10, memVo.getMem_illtms());
			

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int update(MemVO memVo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memVo.getMem_name());
			pstmt.setString(2, memVo.getMem_account());
			pstmt.setString(3, memVo.getMem_pwd());
			pstmt.setString(4, memVo.getMem_mail());
			pstmt.setString(5, memVo.getMem_phone());
			pstmt.setString(6, memVo.getMem_adrs());
			pstmt.setDate(7, memVo.getMem_birthdate());
			pstmt.setString(8, memVo.getMem_sex());
			pstmt.setString(9, memVo.getMem_nickname());
			pstmt.setInt(10, memVo.getMem_illtms());
			pstmt.setInt(11, memVo.getMem_no());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int delete(Integer mem_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, mem_no);
			
			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public MemVO findByPrimaryKey(String mem_account) {

		MemVO memVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mem_account);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// MemVO �]�٬� Domain objects
				memVo = new MemVO();
				memVo.setMem_no(rs.getInt("MEM_NO"));
				memVo.setMem_name(rs.getString("MEM_NAME"));
				memVo.setMem_account(rs.getString("MEM_ACCOUNT"));
				memVo.setMem_pwd(rs.getString("MEM_PWD"));
				memVo.setMem_mail(rs.getString("MEM_MAIL"));
				memVo.setMem_phone(rs.getString("MEM_PHONE"));
				memVo.setMem_adrs(rs.getString("MEM_ADRS"));
				memVo.setMem_birthdate(rs.getDate("MEM_BIRDTHDATE"));
				memVo.setMem_sex(rs.getString("MEM_SEX"));
				memVo.setMem_nickname(rs.getString("MEM_NICKNAME"));
				memVo.setMem_illtms(rs.getInt("MEM_ILLTMS"));
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
		return memVo;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// MemVO �]�٬� Domain objects
				memVo = new MemVO();
				memVo.setMem_no(rs.getInt("MEM_NO"));
				memVo.setMem_name(rs.getString("MEM_NAME"));
				memVo.setMem_account(rs.getString("MEM_ACCOUNT"));
				memVo.setMem_pwd(rs.getString("MEM_PWD"));
				memVo.setMem_mail(rs.getString("MEM_MAIL"));
				memVo.setMem_phone(rs.getString("MEM_PHONE"));
				memVo.setMem_adrs(rs.getString("MEM_ADRS"));
				memVo.setMem_birthdate(rs.getDate("MEM_BIRDTHDATE"));
				memVo.setMem_sex(rs.getString("MEM_SEX"));
				memVo.setMem_nickname(rs.getString("MEM_NICKNAME"));
				memVo.setMem_illtms(rs.getInt("MEM_ILLTMS"));
				list.add(memVo); // Store the row in the vector
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

		MemJDBCDAO dao = new MemJDBCDAO();

		 //�s�W
//		 MemVO memVo1 = new MemVO();
//		 memVo1.setMem_name("test12");
//		 memVo1.setMem_account("test1");
//		 memVo1.setMem_pwd("2005-01-01");
//		 memVo1.setMem_mail("50000@hotmail.com");
//		 memVo1.setMem_phone("0938678158");
//		 memVo1.setMem_adrs("0938678158");
//		 memVo1.setMem_birthdate(null);
//		 memVo1.setMem_sex(null);
//		 memVo1.setMem_nickname("��Ƭ_���F");
//		 memVo1.setMem_illtms(0);
//		 int updateCount_insert = dao.insert(memVo1);
//		 System.out.println(updateCount_insert);
				

//		 �ק�
//		 MemVO memVo2 = new MemVO();
//		 memVo2.setMem_no(10001);
//		 memVo2.setMem_name("jj");
//		 memVo2.setMem_account("MEMBER2");
//		 memVo2.setMem_pwd("2002-01-01");
//		 memVo2.setMem_mail("20000");
//		 memVo2.setMem_phone("0953555777");
//		 memVo2.setMem_adrs("0938678158");
//		 memVo2.setMem_birthdate(java.sql.Date.valueOf("2005-01-01"));
//		 memVo2.setMem_sex("0");
//		 memVo2.setMem_nickname("JJ");
//		 memVo2.setMem_illtms(0);
//		 int updateCount_update = dao.update(memVo2);
//		 System.out.println(updateCount_update);
				

//		 �R��
//		 int updateCount_delete = dao.delete(10002);
//		 System.out.println(updateCount_delete);

		// �d��
		MemVO memVo3 = dao.findByPrimaryKey("tingy0419");
		System.out.print(memVo3.getMem_no() + ",");
		System.out.print(memVo3.getMem_name() + ",");
		System.out.print(memVo3.getMem_account() + ",");
		System.out.print(memVo3.getMem_pwd() + ",");
		System.out.print(memVo3.getMem_mail() + ",");
		System.out.print(memVo3.getMem_phone() + ",");
		System.out.println("---------------------");
//
//		// �d��
//		List<MemVO> list = dao.getAll();
//		for (MemVO aEmp : list) {
//			System.out.print(aEmp.getMem_no() + ",");
//			System.out.print(aEmp.getMem_name() + ",");
//			System.out.print(aEmp.getMem_account() + ",");
//			System.out.print(aEmp.getMem_pwd() + ",");
//			System.out.print(aEmp.getMem_mail() + ",");
//			System.out.print(aEmp.getMem_phone() + ",");
//			
//			System.out.println();
//		}
	}
}
