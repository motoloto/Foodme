package com.mem.model;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemDAO implements MemDAO_interface {

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
		"INSERT INTO MEMBER (MEM_NO,MEM_NAME,MEM_ACCOUNT,MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS,MEM_PIC) VALUES (sequence_MEM_NO.NEXTVAL, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = 
		"SELECT MEM_NO,MEM_NAME,MEM_ACCOUNT, MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS, MEM_PIC FROM MEMBER order by MEM_NO";
	private static final String GET_ONE_STMT = 
		"SELECT MEM_NO,MEM_NAME,MEM_ACCOUNT, MEM_PWD,MEM_MAIL,MEM_PHONE,MEM_ADRS,MEM_BIRDTHDATE,MEM_SEX,MEM_NICKNAME,MEM_ILLTMS, MEM_PIC FROM MEMBER where MEM_ACCOUNT = ?";
	private static final String DELETE = 
		"DELETE FROM MEMBER where MEM_NO = ?";
	private static final String UPDATE = 
		"UPDATE MEMBER set MEM_NAME=?, MEM_ACCOUNT=?, MEM_PWD=?, MEM_MAIL=?, MEM_PHONE=?,MEM_ADRS=?,MEM_BIRDTHDATE=?,MEM_SEX=?,MEM_NICKNAME=?,MEM_ILLTMS=?, MEM_PIC=? where MEM_NO = ?";

	@Override
	public int insert(MemVO memVo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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
			pstmt.setBytes(11, memVo.getMem_pic());
			

			updateCount = pstmt.executeUpdate();

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

			con = ds.getConnection();
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
			pstmt.setBytes(11, memVo.getMem_pic());
			pstmt.setInt(12, memVo.getMem_no());

			updateCount = pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, mem_no);
			
			updateCount = pstmt.executeUpdate();

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

		MemVO MemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mem_account);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// MemVO �]�٬� Domain objects
				MemVO = new MemVO();
				MemVO.setMem_no(rs.getInt("MEM_NO"));
				MemVO.setMem_name(rs.getString("MEM_NAME"));
				MemVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				MemVO.setMem_pwd(rs.getString("MEM_PWD"));
				MemVO.setMem_mail(rs.getString("MEM_MAIL"));
				MemVO.setMem_phone(rs.getString("MEM_PHONE"));
				MemVO.setMem_adrs(rs.getString("MEM_ADRS"));
				MemVO.setMem_birthdate(rs.getDate("MEM_BIRDTHDATE"));
				MemVO.setMem_sex(rs.getString("MEM_SEX"));
				MemVO.setMem_nickname(rs.getString("MEM_NICKNAME"));
				MemVO.setMem_illtms(rs.getInt("MEM_ILLTMS"));
				MemVO.setMem_pic(rs.getBytes("MEM_PIC"));
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
		return MemVO;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO MemVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// MemVO �]�٬� Domain objects
				MemVO = new MemVO();
				MemVO.setMem_no(rs.getInt("MEM_NO"));
				MemVO.setMem_name(rs.getString("MEM_NAME"));
				MemVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				MemVO.setMem_pwd(rs.getString("MEM_PWD"));
				MemVO.setMem_mail(rs.getString("MEM_MAIL"));
				MemVO.setMem_phone(rs.getString("MEM_PHONE"));
				MemVO.setMem_adrs(rs.getString("MEM_ADRS"));
				MemVO.setMem_birthdate(rs.getDate("MEM_BIRDTHDATE"));
				MemVO.setMem_sex(rs.getString("MEM_SEX"));
				MemVO.setMem_nickname(rs.getString("MEM_NICKNAME"));
				MemVO.setMem_illtms(rs.getInt("MEM_ILLTMS"));
				MemVO.setMem_pic(rs.getBytes("MEM_PIC"));

				list.add(MemVO); // Store the row in the vector
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
		return list;
	}

	public static void main(String[] args) {

		MemJDBCDAO dao = new MemJDBCDAO();

		 //�s�W
//		 MemVO MemVO1 = new MemVO();
//		 MemVO1.setMem_name("�d�ç�1");
//		 MemVO1.setMem_account("test1");
//		 MemVO1.setMem_pwd("2005-01-01");
//		 MemVO1.setMem_mail("50000@hotmail.com");
//		 MemVO1.setMem_phone("0938678158");
//		 MemVO1.setMem_adrs("0938678158");
//		 MemVO1.setMem_birthdate(java.sql.Date.valueOf("2005-01-01"));
//		 MemVO1.setMem_sex("1");
//		 MemVO1.setMem_nickname("��Ƭ_���F");
//		 MemVO1.setMem_illtms(0);
//		 int updateCount_insert = dao.insert(MemVO1);
//		 System.out.println(updateCount_insert);
				

//		 �ק�
//		 MemVO MemVO2 = new MemVO();
//		 MemVO2.setMem_no(10001);
//		 MemVO2.setMem_name("jj");
//		 MemVO2.setMem_account("MEMBER2");
//		 MemVO2.setMem_pwd("2002-01-01");
//		 MemVO2.setMem_mail("20000");
//		 MemVO2.setMem_phone("0953555777");
//		 MemVO2.setMem_adrs("0938678158");
//		 MemVO2.setMem_birthdate(java.sql.Date.valueOf("2005-01-01"));
//		 MemVO2.setMem_sex("0");
//		 MemVO2.setMem_nickname("JJ");
//		 MemVO2.setMem_illtms(0);
//		 int updateCount_update = dao.update(MemVO2);
//		 System.out.println(updateCount_update);
				

//		 �R��
//		 int updateCount_delete = dao.delete(10002);
//		 System.out.println(updateCount_delete);

		// �d��
//		MemVO MemVO3 = dao.findByPrimaryKey("tingy0419");
//		if (MemVO3 == null) System.out.print("I am null");
//		System.out.print(MemVO3.getMem_no() + ",");
//		System.out.print(MemVO3.getMem_name() + ",");
//		System.out.print(MemVO3.getMem_account() + ",");
//		System.out.print(MemVO3.getMem_pwd() + ",");
//		System.out.print(MemVO3.getMem_mail() + ",");
//		System.out.print(MemVO3.getMem_phone() + ",");
//		System.out.println("---------------------");
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
