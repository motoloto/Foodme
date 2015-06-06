package com.restClass.model;

import java.util.*;
import java.sql.*;

public class ClassJDBCDAO implements ClassDAO_interface {
	
	Connection con=null;
	ResultSet rs=null;
	PreparedStatement pstmt=null;
	
	public int insert(ClassVO vo)
	{
	    int i=0;
		try
	    {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user2","u222");
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user2","u222");
			pstmt=con.prepareStatement("update CLASS set class_name=? where class_no=?");
			pstmt.setString(1, vo.getClass_name());
			pstmt.setInt(2, vo.getClass_no());
			
			i=pstmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return i;	
    }
    public int delete(Integer class_no)
    {
    	int i=0;
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user2","u222");	
			pstmt=con.prepareStatement("delete from CLASS where class_no=?");		
			pstmt.setInt(1, class_no);
			
			/*����e������commit*/
			i=pstmt.executeUpdate();
			
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("----------------------");
    	return i;
    }
    public ClassVO findByPrimaryKey(Integer class_no)
    {
    	ClassVO vo=new ClassVO();
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user2","u222");
		    pstmt=con.prepareStatement("select* from class where class_no=?");
		    pstmt.setInt(1, class_no);
		    
		    rs=pstmt.executeQuery();
		    while(rs.next())
		    {
		    	vo.setClass_no(rs.getInt(1));
		    	vo.setClass_name(rs.getString(2));
		    }	
    	} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return vo;
    }
    public List<ClassVO> getAll()
    {
    	List<ClassVO> list=new ArrayList<>();
    	
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user2","u222");
		    pstmt=con.prepareStatement("select* from class order by class_no asc");
		    
		    
		    rs=pstmt.executeQuery();
		    while(rs.next())
		    {
		    	ClassVO vo=new ClassVO();
		    	vo.setClass_no(rs.getInt(1));
		    	vo.setClass_name(rs.getString(2));
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
        ClassVO vo=new ClassVO();
        ClassJDBCDAO dao=new ClassJDBCDAO();
        List<ClassVO> list=new ArrayList<ClassVO>();
        int i=0;
        
//        vo.setClass_name("�L�׮Ʋz");
//       int result=dao.insert(vo);
//        vo.setClass_name("�馡�Ʋz");
//        dao.insert(vo);
//        vo.setClass_name("�����Ʋz");
//        dao.insert(vo);
//        vo.setClass_name("��Ʋz");
//        dao.insert(vo);
//        vo.setClass_name("�V���Ʋz");
//        dao.insert(vo);
        //System.out.println(result);
        
        //�ק�
//        vo.setClass_no(10);
//        vo.setClass_name("�x���Ʋz");
//        dao.update(vo);
        
        //�R��
//        vo.setClass_no(14);
//        System.out.println(i);
//        i=dao.delete(vo);
//        System.out.println(i);
        
        //�d��
//        vo=dao.findByPrimaryKey(10);
//        System.out.println(vo.getClass_no()+" "+vo.getClass_name());
        
        //�d�ߥ���
        list=dao.getAll();
        for(ClassVO vo1:list)
        {
        	System.out.println(vo1.getClass_no()+" "+vo1.getClass_name());
        }
	}

}
