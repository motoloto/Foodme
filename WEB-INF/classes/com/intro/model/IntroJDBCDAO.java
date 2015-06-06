package com.intro.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntroJDBCDAO implements IntroDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "user2";
	String passwd = "u222";
	
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	private static final String INSERT_STMT = "insert into introduction values("
			+ "sequence_intro_no.nextval,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM introduction order by intro_no";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM introduction where intro_no = ?";
		private static final String DELETE = 
			"DELETE FROM introduction where intro_no = ?";
		private static final String UPDATE = 
			"UPDATE introduction set rest_no=?, intro_picname=?, intro_pic=?, intro_cont=?, intro_ord=?, intro_act=? where intro_no = ?";
	public int insert(IntroVO vo)
	{
		int i=0;
		try 
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userid,passwd);	
			pstmt=con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, vo.getRest_no());
			pstmt.setString(2, vo.getIntro_picname());
			pstmt.setBytes(3, vo.getIntro_pic());
			pstmt.setString(4, vo.getIntro_cont());
			pstmt.setInt(5, vo.getIntro_ord());
			pstmt.setString(6, vo.getIntro_act());
			
			i=pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
    public int update(IntroVO vo)
    {
    	int i=0;
		try 
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userid,passwd);	
			pstmt=con.prepareStatement(UPDATE);
			pstmt.setInt(1, vo.getRest_no());
			pstmt.setString(2, vo.getIntro_picname());
			pstmt.setBytes(3, vo.getIntro_pic());
			pstmt.setString(4, vo.getIntro_cont());
			pstmt.setInt(5, vo.getIntro_ord());
			pstmt.setString(6, vo.getIntro_act());
			pstmt.setInt(7, vo.getIntro_no());
			
			i=pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;	
    }
    public int delete(Integer intro_no)
    {
    	try 
    	{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userid,passwd);	
			pstmt=con.prepareStatement(DELETE);
			pstmt.setInt(1, intro_no);
			
			pstmt.executeUpdate();
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return 0;
    }
    public IntroVO findByPrimaryKey(Integer intro_no)
    {
    	IntroVO vo=new IntroVO();
    	try 
    	{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userid,passwd);	
			pstmt=con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, vo.getIntro_no());
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				vo.setIntro_no(rs.getInt(1));
				vo.setRest_no(rs.getInt(2));
				vo.setIntro_picname(rs.getString(3));
				vo.setIntro_pic(rs.getBytes(4));
				vo.setIntro_cont(rs.getString(5));
				vo.setIntro_ord(rs.getInt(6));
				vo.setIntro_act(rs.getString(7));
				
			}	
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return vo;
    }
    public List<IntroVO> getAll()
    {
    	List<IntroVO> list=new ArrayList<IntroVO>();
    	ResultSet rs=null;
    	try 
    	{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userid,passwd);	
			pstmt=con.prepareStatement(GET_ALL_STMT);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				IntroVO vo=new IntroVO();
				vo.setIntro_no(rs.getInt(1));
				vo.setRest_no(rs.getInt(2));
				vo.setIntro_picname(rs.getString(3));
				vo.setIntro_pic(rs.getBytes(4));
				vo.setIntro_cont(rs.getString(5));
				vo.setIntro_ord(rs.getInt(6));
				vo.setIntro_act(rs.getString(7));
				list.add(vo);
			}	
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return list;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntroJDBCDAO dao=new IntroJDBCDAO();
		//IntroductionVO vo=new IntroductionVO();
		List<IntroVO> list=new ArrayList<IntroVO>();
		
		list=dao.getAll();
		for(IntroVO vo:list)
		{
			System.out.println(vo.getIntro_no());
		}	
		System.out.println(list.size());
	}
	@Override
	public List<IntroVO> findByForeignKey(Integer rest_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
