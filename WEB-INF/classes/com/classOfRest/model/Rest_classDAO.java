package com.classOfRest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Rest_classDAO implements Rest_classDAO_interface{
    
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
	
	public int insert(Rest_classVO vo)
	{
		int i=0;
		try
	    {
			con=ds.getConnection();
			pstmt=con.prepareStatement("insert into REST_CLASS values(?,?)");
			pstmt.setInt(1, vo.getRest_no());
			pstmt.setInt(2, vo.getClass_no());
			
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
    public int update(Rest_classVO vo)
    {
    	//�ק�H�R���A�s�W�N���N�n
    	return 0;
    }
    public int delete(Rest_classVO vo)
    {
    	int i=0;
    	try 
    	{
    		con=ds.getConnection();	
			pstmt=con.prepareStatement("delete from REST_CLASS where rest_no=? and class_no=?");
			pstmt.setInt(1, vo.getRest_no());
			pstmt.setInt(2, vo.getClass_no());
			
			/*����e������commit*/
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
    //一家餐廳可能有多種餐廳分類，故要用list去接
    @Override
	public List<Rest_classVO> findByPrimaryKey(Integer rest_no)
    {
    	List<Rest_classVO> list=new ArrayList<Rest_classVO>();
    	
    	try 
    	{
    		con=ds.getConnection();
		    pstmt=con.prepareStatement("select * from rest_class where rest_no=?");
		    pstmt.setInt(1, rest_no);
		    
		    
		    rs=pstmt.executeQuery();
		    while(rs.next())
		    {
		    	Rest_classVO vo=new Rest_classVO();
		    	vo.setRest_no(rs.getInt(1));
		    	vo.setClass_no(rs.getInt(2));
		    	list.add(vo);
		    }
		    pstmt.close();
			con.close();
    	} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return list;	
    }
    public List<Rest_classVO> getAll()
    {
        List<Rest_classVO> list=new ArrayList<>();
    	
    	try 
    	{
    		con=ds.getConnection();
		    pstmt=con.prepareStatement("select* from rest_class order by rest_no asc");
		    
		    
		    rs=pstmt.executeQuery();
		    while(rs.next())
		    {
		    	Rest_classVO vo=new Rest_classVO();
		    	vo.setRest_no(rs.getInt(1));
		    	vo.setClass_no(rs.getInt(2));
		    	list.add(vo);
		    }	
		    pstmt.close();
			con.close();
    	} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return list;
    }
	//餐廳分類要更新時先刪除表格內餐廳所屬分類
    public void deleteAll(Rest_classVO vo)
    {
    	try 
    	{
    		con=ds.getConnection();	
			pstmt=con.prepareStatement("delete from REST_CLASS where rest_no=?");
			pstmt.setInt(1, vo.getRest_no());
			
			/*����e������commit*/
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	public static void main(String[] args) {
		Rest_classVO vo=new Rest_classVO();
    	Rest_classDAO dao=new Rest_classDAO();
        List<Rest_classVO> list=new ArrayList<Rest_classVO>();
        int i=0;
        
        //�s�W
//        vo.setRest_no(7050);
//        vo.setClass_no(12);
//        dao.insert(vo);
        
        
        //�R��
//        vo.setRest_no(7050);
//        vo.setClass_no(12);
//        i=dao.delete(vo);
//        System.out.println(i);
        
        //�d��
        list=dao.findByPrimaryKey(7052);
        for(Rest_classVO vo1:list)
        {
      	   System.out.println(vo1.getRest_no()+" "+vo1.getClass_no());
        }
        
        //�d�ߥ���
//        list=dao.getAll();
//        for(Rest_classVO vo1:list)
//        {
//        	System.out.println(vo1.getRest_no()+" "+vo1.getClass_no());
//        }

	}

}
