package com.reserv.model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Reservation_recordJDBCDAO implements Reservation_recordDAO_interface {
	public void insert(Reservation_recordVO Res_record)
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user2","u222");
			PreparedStatement pstmt=con.prepareStatement("insert into reservation_record values"
					+ "(sequence_REC_NO.NEXTVAL,?,?,?,?,?,?,?)");
			pstmt.setInt(1, Res_record.getMem_no());
			pstmt.setInt(2, Res_record.getRest_no());
			pstmt.setInt(3, Res_record.getCount());
			pstmt.setString(4, Res_record.getReservation_day());
			pstmt.setInt(5, Res_record.getPeriod());
			pstmt.setString(6, Res_record.getOdr_seqnum());
			pstmt.setString(7, Res_record.getSeating());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    public int update(Reservation_recordVO Res_record)
    {
    	int i=0;
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user1","u111");
			PreparedStatement pstmt=con.prepareStatement("update reservation_record set MEM_NO=?,REST_NO=?,COUNT=?,"
					+ "Reservation_day=to_date(?,'yyyy-mm-dd'), Period=? ,ODR_SEQNUM=? ,SEATING=? where REC_NO=?");
			
			pstmt.setInt(1,Res_record.getMem_no());
			pstmt.setInt(2,Res_record.getRest_no());
			pstmt.setInt(3,Res_record.getCount());
			pstmt.setString(4,Res_record.getReservation_day());
			pstmt.setInt(5, Res_record.getPeriod());
			pstmt.setString(6, Res_record.getOdr_seqnum());
			pstmt.setString(7, Res_record.getSeating());
			pstmt.setInt(8,Res_record.getRec_no());

			i=pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
    	} 
	    catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return i;	
    }
    public int delete(Integer Rec_no)
    {
    	int i=0;
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user1","u111");
			PreparedStatement pstmt=con.prepareStatement("delete from RES_RECORD WHERE Rec_no=?");
			pstmt.setInt(1, Rec_no);
			i=pstmt.executeUpdate();
            
			pstmt.close();
			con.close();
		} 
    	catch (ClassNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return i;
    }
    public Reservation_recordVO findByPrimaryKey(Integer Rec_no)
    {
        Reservation_recordVO result=new Reservation_recordVO();
        Connection con=null;
        ResultSet rs=null;
        
        try 
        {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user1","u111");
			PreparedStatement pstmt=con.prepareStatement("select * from res_record "
					+ "where rec_no=?");
			pstmt.setInt(1, Rec_no);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
			    result.setRec_no(rs.getInt(1));	
			    result.setMem_no(rs.getInt(2));
			    result.setRest_no(rs.getInt(3));
			    result.setCount(rs.getInt(4));
			    result.setReservation_day(rs.getString(5));
			    result.setPeriod(rs.getInt(6));
			    result.setOdr_seqnum(rs.getString(7));
			    result.setSeating(rs.getString(8));
			}	
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return result;
    }
    public List<Reservation_recordVO> getAll()
    {
    	List<Reservation_recordVO> result=new ArrayList<Reservation_recordVO>();
    	//Res_recordVO vo=new Res_recordVO();
    	Connection con=null;
        ResultSet rs=null;
    	
    	try 
    	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user2","u222");
			//若指令無法執行，顯示ORA-00942: table or view does not exist：檢查自動編譯是否被關閉
			PreparedStatement pstmt=con.prepareStatement("select * from reservation_record ");
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				Reservation_recordVO vo=new Reservation_recordVO();
				vo.setRec_no(rs.getInt(1));
				vo.setMem_no(rs.getInt(2));
				vo.setRest_no(rs.getInt(3));
				vo.setCount(rs.getInt(4));
				vo.setReservation_day(rs.getString(5));
				vo.setPeriod(rs.getInt(6));
				vo.setOdr_seqnum(rs.getString(7));
				vo.setSeating(rs.getString(8));
				result.add(vo);
			}	
			pstmt.close();
			con.close();
		} 
    	catch (Exception e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return result;
    }
    
    
    
	public static void main(String[] args) {
	 
		//System.out.print("Hello World!!");
	    Reservation_recordJDBCDAO dao=new Reservation_recordJDBCDAO();
	    Reservation_recordVO vo=new Reservation_recordVO();
	    
	    //�s�W
	   
//	    vo.setMem_no(10025);
//	    vo.setRest_no(7050);
//	    vo.setCount(7);
//	    vo.setTime(Timestamp.valueOf("2014-12-31 17:30:00"));
//	    vo.setOdr_seqnum("A1111");
//	    vo.setSeating("1");
//	    
//	    dao.insert(vo);
	    
	    //�R��
//	    dao.delete(10001);
	    
	    //�ק�
//	    vo.setRec_no(10010);
//	    vo.setMem_no(10025);
//	    vo.setRest_no(7050);
//	    vo.setCount(13);
//	    vo.setTime(Timestamp.valueOf("2015-01-05 20:17:00"));
//	    vo.setOdr_seqnum("A1113");
//	    vo.setSeating("1");
//	    dao.update(vo);
	    
	    //�d��
//	    vo=dao.findByPrimaryKey(10010);
//	    System.out.println(vo.getRec_no());
//	    System.out.println(vo.getMem_no());
//	    System.out.println(vo.getRest_no());
//	    System.out.println(vo.getCount());
//	    System.out.println(vo.getTime());
//	    System.out.println(vo.getOdr_seqnum());
//	    System.out.println(vo.getSeating());
	    
	    List<Reservation_recordVO> list=new ArrayList<Reservation_recordVO>();
	    list=dao.findRecordByReservationTime("2015-01-22",7001);
//	    List<Reservation_recordVO> result=new ArrayList<>();
//	    result=dao.getAll();
	    System.out.println(list.size());
	    for(Reservation_recordVO vo1:list)
	    {
		    System.out.print(vo1.getRec_no());
		    System.out.print(" ");
		    System.out.print(vo1.getMem_no());
		    System.out.print(" ");
		    System.out.print(vo1.getRest_no());
		    System.out.print(" ");
		    System.out.print(vo1.getCount());
		    System.out.print(" ");
		    System.out.print(vo1.getReservation_day());
		    System.out.print(" ");
		    System.out.print(vo1.getPeriod());
		    System.out.print(" ");
		    System.out.print(vo1.getOdr_seqnum());
		    System.out.print(" ");
		    System.out.print(vo1.getSeating());
		    System.out.print("\n");
	    }
	
	
	}
	@Override
	public List<Reservation_recordVO> findRecordByReservationTime(String str,Integer rest_no)
    {
    	List<Reservation_recordVO> list=new ArrayList<Reservation_recordVO>();
    	
    	Connection con;
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"user2","u222");	
			PreparedStatement pstmt=con.prepareStatement("select * from reservation_record where reservation_day >=to_date(?,'YYYY-mm-dd') and "
					+ "reservation_day<to_date(?,'YYYY-mm-dd') and rest_no=?");
			pstmt.setString(1,str);	
			//取得日期為查詢日期隔天的日期物件
			//先將查詢日期字串轉成日期物件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date query_date = sdf.parse(str);
			//將查詢日期物件的日期加一天
			Date tomorrow=new Date(query_date.getTime()+24*60*60*1000);
			//再將查詢日期物件(已加一天)型別轉成String type
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
			String tomorrow_str	= dateFormat.format(tomorrow);
            System.out.println(str);
			System.out.println(tomorrow_str);
			pstmt.setString(2,tomorrow_str);
			pstmt.setInt(3, rest_no);
            ResultSet rs=pstmt.executeQuery();
			//如果執行後無結果或出來舊結果：檢查指令是否有commit
            while(rs.next())
			{				
				Reservation_recordVO vo=new Reservation_recordVO();
				vo.setRec_no(rs.getInt("rec_no"));
				vo.setMem_no(rs.getInt("mem_no"));
				vo.setRest_no(rs.getInt("rest_no"));
				vo.setCount(rs.getInt("count"));
				vo.setReservation_day(rs.getString("reservation_day"));
				vo.setPeriod(rs.getInt("period"));
				vo.setOdr_seqnum(rs.getString("odr_seqnum"));
				vo.setSeating(rs.getString("seating"));
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
}


