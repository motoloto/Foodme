<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%! Integer rest_no=0; 
	String rest_name=null;
	Date current_date=new Date();//產生今天日期物件，準備轉成日期字串
	Date future_date=new Date(current_date.getTime()+6*24*60*60*1000);//產生六天後日期物件    
	DateFormat df=null;
	String today=null;
	String six_day=null;%>
<%  
	
	df=DateFormat.getDateInstance(DateFormat.SHORT,Locale.TAIWAN);
    today=df.format(current_date);//simpleDateFormat不是所有格式都能轉
    six_day=df.format(future_date);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/back/pageDesign/designSrc.jsp"%></head>
<title>Insert title here</title>
<style>
#query_result
{
	margin-left:50px;
	margin-right:50px;
	margin-bottom:100px;
}
</style>
</head>
<body>
<div class="container-fluid back_end">
	<div id="header_space"></div>
	<div class="row clearfix ">
		<%@ include file="/back/pageDesign/be_header.jsp"%>
		
					<div class="col-md-10 column block">
					<form method="POST" action="<%=request.getContextPath()%>/reserv/reserv.do">
					<h3 align="center">
							後端訂位資訊管理頁面
	                   </h3>
	                   <hr>
	                   
	                   <div align="center">
	                        <c:if test="${not empty errorMsgs}">
								<font>請修正以下錯誤:
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</ul>
								</font>
							</c:if>
                       </div>
	                   
	                   <div align="center" style="font-size:20px">
	                      	 請輸入欲查詢會員之編號：
	                   </div>
	                   <br><br>
	                    <div class="col-md-12 column" align="center">
	                        <input type="text" name="mem_no">
	                    </div>
	                    <HR>
	                    <div align="center" style="font-size:20px">
	                   		 或輸入欲查詢餐廳之編號：
	                    </div>
	                    <br><br>
	                    <div class="col-md-12 column" align="center">
	                        <input type="text" name="rest_no">
	                    </div><!-- input不可用id命名 -->
	                    <br><br>
	                    <br><br>                    
	                    <div class="col-md-12 column" align="center">
	                        <input TYPE="hidden" name="action" value="back_query"><!--表示是後端的查詢-->
	                        <input type="hidden" name="today" value="<%=today %>">
	                        <input type="hidden" name="future_day" value="<%=six_day%>">	                        
	                        <button type="submit" class="btn btn-warning btn-lg active">確定</button>
	                    </div>
	               </form><!-- type一定要用submit，不可用button，否則表單不會提交 -->  
	                    <!-- 準備顯示request包含的結果(參考範例0403) -->
	                    <div id="query_result">
						<%if (request.getAttribute("list")!=null){%>
						      <jsp:include page="query_reservation.jsp" />
						<%} %>
						</div>
					</div>
			 
						
	</div>
</div>
				
				
</div>
	
</div>
</body>
</html>