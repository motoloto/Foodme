<%@ page language="java" import="java.util.*,java.io.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<%@ page import="com.odr.model.*"%>
<%@ page import="com.cop.model.*"%>

<%!
	InternetAddress[] address = null ;
%>

<%
    OdrVO odrVO = (OdrVO)request.getAttribute("odrVO");
    CopVO copVO = (CopVO)request.getAttribute("copVO");
    String odr_no = odrVO.getOdr_no();
	String odr_payname = request.getParameter("odr_payname");
	String odr_seqnum = odrVO.getOdr_seqnum();
    String cop_name = copVO.getCop_name();
    Integer odr_buyamt = odrVO.getOdr_buyamt();
    Integer toprc = odrVO.getOdr_toprc();
    
// 	String passRandom = request.getParameter("passRandom");
	
	
	String mailserver   = "140.115.236.9";
	String From         = "service@FOODme.com.tw";
	String to           =  request.getParameter("odr_mail");
	String Subject      = "FOODme線上餐廳訂位系統，您購買的商品已經完成結帳！";
  String messageText  = "Hello! " + odr_payname + " ,您已成功購買商品\n"+
	                    
	                          "訂單編號: "+odr_no+"\n"+
		                      "商品名稱: "+cop_name+"\n"+
		                      "購買數量: "+odr_buyamt+"張\n"+
		                      "購買金額: "+toprc+"元(NT)\n\n"+
		                      "餐卷序號: "+odr_seqnum+"\n\n"+
		                      "祝您用餐愉快。\n"+
		                      "用餐資訊查詢 http://localhost:8081/YA101G5/front/indexXXX.jsp\n"+
		                      "免付費意見專線： 0800-3308000\n"+
		                      "Email: service@FOODME.com.tw\n"+
		                      "FOOODme品牌小組\n";        

        boolean sessionDebug = false;

try {

  // 設定所要用的Mail 伺服器和所使用的傳送協定
  java.util.Properties props = System.getProperties();
  props.put("mail.host",mailserver);
  props.put("mail.transport.protocol","smtp");
  
  // 產生新的Session 服務
  javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props,null);
  mailSession.setDebug(sessionDebug);
	
  Message msg = new MimeMessage(mailSession);
  
  // 設定傳送郵件的發信人
  msg.setFrom(new InternetAddress(From));
  
  // 設定傳送郵件至收信人的信箱
  address = InternetAddress.parse(to,false);
  msg.setRecipients(Message.RecipientType.TO, address);
  
  // 設定信中的主題 
  msg.setSubject(Subject);
  // 設定送信的時間
  msg.setSentDate(new Date());
  
  // 設定傳送信的MIME Type
  msg.setText(messageText);
  
  // 送信
  Transport.send(msg);

      //response.sendRedirect("emp_select.jsp?msg=Y");
    System.out.println("傳送成功!");
//     out.println("<script >document.open('mail_ok.jsp', '' ,'height=110,width=390,left=200,top=120,resizable=no')</script>");	
}
    catch (MessagingException mex) {
      //response.sendRedirect("emp_select.jsp?msg=N");
    System.out.println("傳送失敗!");
//     out.println("<script >document.open('mail_error.jsp', '' ,'height=110,width=390,left=200,top=120,resizable=no')</script>");  
      //mex.printStackTrace();
    }
 
%>