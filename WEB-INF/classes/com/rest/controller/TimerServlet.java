package com.rest.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rest.model.RestService;
import com.rest.model.RestVO;

/**
 * Servlet implementation class TimerServlet
 */

@WebServlet(name="TimerServlet",urlPatterns={"/TimerServlet"}, loadOnStartup=1)
public class TimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Timer timer;
    int i=0;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
    public void init()
    {               
    	  final RestService rs=new RestService();
    	  final List<RestVO> list=rs.getAll();//取得資料庫內所有餐廳編號
    	     	         
          Calendar c=Calendar.getInstance();
          final int day=c.get(Calendar.DAY_OF_WEEK);
    	  TimerTask task = new TimerTask()
          { 
    	        public void run() 
    	        {    	        	
    	        	for(RestVO vo:list)//時間到就修改時段已訂位數
		      	    {		      		  		      	   
	    	        	System.out.println(vo.getRest_no());
    	        		if(day==1)//星期日
	                    {
	                    	rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	            			vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	            			vo.getReserved_mon6(), vo.getReserved_tue1(), vo.getReserved_tue2(), 
	            			vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	            			vo.getReserved_tue6(), vo.getReserved_wed1(), vo.getReserved_wed2(), 
	            			vo.getReserved_wed3(), vo.getReserved_wed4(), vo.getReserved_wed5(), 
	            			vo.getReserved_wed6(), vo.getReserved_thu1(), vo.getReserved_thu2(), 
	            			vo.getReserved_thu3(), vo.getReserved_thu4(), vo.getReserved_thu5(), 
	            			vo.getReserved_thu6(), vo.getReserved_fri1(), vo.getReserved_fri2(), 
	            			vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	            			vo.getReserved_fri6(), 0,0,0,0,0,0, 
	            			vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	                    }	
	    	        	else if(day==2)
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	    	            	vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	    	            	vo.getReserved_mon6(), vo.getReserved_tue1(), vo.getReserved_tue2(), 
	        				vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	        				vo.getReserved_tue6(), vo.getReserved_wed1(), vo.getReserved_wed2(), 
	        				vo.getReserved_wed3(), vo.getReserved_wed4(), vo.getReserved_wed5(), 
	        				vo.getReserved_wed6(), vo.getReserved_thu1(), vo.getReserved_thu2(), 
	        				vo.getReserved_thu3(), vo.getReserved_thu4(), vo.getReserved_thu5(), 
	        				vo.getReserved_thu6(), vo.getReserved_fri1(), vo.getReserved_fri2(), 
	        				vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	        				vo.getReserved_fri6(), vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6(), 0,0,0,0,0,0);
	    	        	}	
	    	        	else if(day==3)
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),0,0,0,0,0,0, vo.getReserved_tue1(), vo.getReserved_tue2(), 
	    	        		vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	    	        		vo.getReserved_tue6(), vo.getReserved_wed1(), vo.getReserved_wed2(), 
	        				vo.getReserved_wed3(), vo.getReserved_wed4(), vo.getReserved_wed5(), 
	        				vo.getReserved_wed6(), vo.getReserved_thu1(), vo.getReserved_thu2(), 
	        				vo.getReserved_thu3(), vo.getReserved_thu4(), vo.getReserved_thu5(), 
	        				vo.getReserved_thu6(), vo.getReserved_fri1(), vo.getReserved_fri2(), 
	        				vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	        				vo.getReserved_fri6(), vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6(), vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	    	        	}	
	    	        	else if(day==4)
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	        				vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	        				vo.getReserved_mon6(),0,0,0,0,0,0 , 
	        				vo.getReserved_wed1(), vo.getReserved_wed2(), vo.getReserved_wed3(), 
	        				vo.getReserved_wed4(), vo.getReserved_wed5(), 
	        				vo.getReserved_wed6(), vo.getReserved_thu1(), vo.getReserved_thu2(), 
	        				vo.getReserved_thu3(), vo.getReserved_thu4(), vo.getReserved_thu5(), 
	        				vo.getReserved_thu6(), vo.getReserved_fri1(), vo.getReserved_fri2(), 
	        				vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	        				vo.getReserved_fri6(), vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6(), vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	    	        	}
	    	        	else if(day==5)
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	        				vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	        				vo.getReserved_mon6(), vo.getReserved_tue1(), vo.getReserved_tue2(), 
	        				vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	        				vo.getReserved_tue6(), 0,0,0,0,0,0,
	        				vo.getReserved_thu1(), vo.getReserved_thu2(), vo.getReserved_thu3(),
	        				vo.getReserved_thu4(), vo.getReserved_thu5(), 
	        				vo.getReserved_thu6() ,vo.getReserved_fri1(), vo.getReserved_fri2(), 
	        				vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	        				vo.getReserved_fri6(), vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6(), vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	    	        	}	
	    	        	else if(day==6)
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	        				vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	        				vo.getReserved_mon6(), vo.getReserved_tue1(), vo.getReserved_tue2(), 
	        				vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	        				vo.getReserved_tue6(), vo.getReserved_wed1(), vo.getReserved_wed2(), 
	        				vo.getReserved_wed3(), vo.getReserved_wed4(), vo.getReserved_wed5(), 
	        				vo.getReserved_wed6(), 0,0,0,0,0,0,
	        				vo.getReserved_fri1(), vo.getReserved_fri2(), 
	        				vo.getReserved_fri3(), vo.getReserved_fri4(), vo.getReserved_fri5(), 
	        				vo.getReserved_fri6(), vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6(), vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	    	        	}	
	    	        	else
	    	        	{
	    	        		rs.updateReservation(vo.getRest_no(),vo.getReserved_mon1(),vo.getReserved_mon2(), 
	        				vo.getReserved_mon3(), vo.getReserved_mon4(), vo.getReserved_mon5(), 
	        				vo.getReserved_mon6(), vo.getReserved_tue1(), vo.getReserved_tue2(), 
	        				vo.getReserved_tue3(), vo.getReserved_tue4(), vo.getReserved_tue5(), 
	        				vo.getReserved_tue6(), vo.getReserved_wed1(), vo.getReserved_wed2(), 
	        				vo.getReserved_wed3(), vo.getReserved_wed4(), vo.getReserved_wed5(), 
	        				vo.getReserved_wed6(), vo.getReserved_thu1(), vo.getReserved_thu2(), 
	        				vo.getReserved_thu3(), vo.getReserved_thu4(), vo.getReserved_thu5(), 
	        				vo.getReserved_thu6(), 0,0,0,0,0,0,
	        				vo.getReserved_sat1(), vo.getReserved_sat2(), 
	        				vo.getReserved_sat3(), vo.getReserved_sat4(), vo.getReserved_sat5(), 
	        				vo.getReserved_sat6() ,vo.getReserved_sun1(), vo.getReserved_sun2(), 
	        				vo.getReserved_sun3(), vo.getReserved_sun4(), vo.getReserved_sun5(), 
	        				vo.getReserved_sun6());
	    	        	}
		      	  } 
    	        } 
          };
          timer = new Timer(); 
          Calendar cal = new GregorianCalendar(2015, Calendar.FEBRUARY, 9, 0, 0, 0);
          timer.scheduleAtFixedRate(task, cal.getTime(),24*60*60*1000);
             	
    }
    
       
    public void destroy() {
        timer.cancel();
        System.out.println("已移除排程!");
    }
}
