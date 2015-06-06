package com.restClass.model;

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

public class ClassJNDIDAO implements ClassDAO_interface{

		private static DataSource ds = null;
		static {
			try {
				Context ctx = new javax.naming.InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	    Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		public int insert(ClassVO vo)
		{
		    int i=0;
			try
		    {
				
				con=ds.getConnection();
				pstmt=con.prepareStatement("insert into CLASS values(sequence_CLASS_NO.NEXTVAL,?)");
				pstmt.setString(1, vo.getClass_name());
				
				i=pstmt.executeUpdate();
			} 
		    catch (Exception e) 
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return i;
		}
	    public int update(ClassVO vo)
	    {
			int i=0;
	    	
	    		try 
	    		{
					con=ds.getConnection();
					pstmt=con.prepareStatement("update CLASS set class_name=? where class_no=?");
					pstmt.setString(1, vo.getClass_name());
					pstmt.setInt(2, vo.getClass_no());
					
					i=pstmt.executeUpdate();
				} 
	    		catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
	    	return i;	
	    }
	    public int delete(Integer class_no) throws ClassNotFoundException, SQLException
	    {
	    	int i=0;
	    	
	    	    con=ds.getConnection();	
				pstmt=con.prepareStatement("delete from CLASS where class_no=?");		
				pstmt.setInt(1, class_no);
				
				/*����e������commit*/
				i=pstmt.executeUpdate();
			
	    	return i;
	    }
	    public ClassVO findByPrimaryKey(Integer class_no) throws ClassNotFoundException, SQLException
	    {
	    	ClassVO vo=null;
	    	
	    	    con=ds.getConnection();	
			    pstmt=con.prepareStatement("select* from class where class_no=?");
			    pstmt.setInt(1, class_no);
			    
			    rs=pstmt.executeQuery();
			    while(rs.next())
			    {
			    	vo=new ClassVO();
			    	vo.setClass_no(rs.getInt(1));
			    	vo.setClass_name(rs.getString(2));
			    }	
			
	    	return vo;
	    }
	    public List<ClassVO> getAll() throws ClassNotFoundException, SQLException
	    {
	    	List<ClassVO> list=new ArrayList<>();
	    	
	    	con=ds.getConnection();
		    pstmt=con.prepareStatement("select* from class order by class_no asc");
		    
		    
		    rs=pstmt.executeQuery();
		    while(rs.next())
		    {
		    	ClassVO vo=new ClassVO();
		    	vo.setClass_no(rs.getInt(1));
		    	vo.setClass_name(rs.getString(2));
		    	list.add(vo);
		    }	
	    	
	    	return list;
	    }
 
}
