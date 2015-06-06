<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%! int i=1; %>
<jsp:useBean id="ms" scope="page" class="com.mem.model.MemService"></jsp:useBean>
<jsp:useBean id="rs" scope="page" class="com.rest.model.RestService"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<title>Insert title here</title>
<!--修改處-->
<style>
.color
{
	background-color:#CCEEFF;
}
</style>
</head>

<body>
<div class="container-fluid back_end">
	<div id="header_space"></div>
	<div class="row clearfix block">
	    
		<div class="col-md-12 column">
					<h3 align="center" class="color"><!--修改處-->
                        	訂位資訊查詢結果
                    </h3>
                    <hr>
                    <div class="row clearfix" align="center">
                        <div class="col-md-4 column color"><!--修改處-->
                           	會員編號：${param.mem_no}<!-- 在request裡的物件要取出，如果沒特別setAtrribute的，用類似getParameter的方式取出 -->
                        </div>
                        <div class="col-md-4 column color"><!--修改處-->
                          	 餐廳編號：${param.rest_no} 
                        </div>
                        <div class="col-md-4 column color"><!--修改處-->
                           	 訂位查詢時段：${param.today}~${param.future_day}
                        </div>
                    </div>
                    <hr>
                    <table class="table">
                        <thead>
                            <tr class="danger">
                                <th>
                                     	訂位紀錄編號
                                </th>
                                <th>
                                		會員姓名
                                </th>
                                <th>
                                    	餐廳名稱
                                </th>                          
                                <th>
                                    	人數
                                </th>
                                
                                <th>
                                   	 是否入座
                                </th>
                                <th>
                                	訂位日期
                                </th>
                                <th>
                                	訂位時段
                                </th>
                                <th>
                                    	餐券序號
                                </th>
                                
                            </tr>
                        </thead>
                        <tbody>
                        <!--查詢結果顯示-->
	                        <c:forEach var="vo" items="${list}">
	                        <%if(i%2==1){ %>
	                            <tr  class="success">
	                        <%}
	                        else{ %><!-- 明明沒寫錯卻跳錯：改打其他程式碼，再打回原本程式碼 -->
	                        <tr class="warning">    
	                        <% }%>        <td>
	                                   ${vo.rec_no}
	                                </td>
	                                <td>
	                                <c:forEach var="memVO" items="${ms.all}">
	                                	<c:if test="${memVO.mem_no==vo.mem_no}">
	                                		${memVO.mem_name}
	                                	</c:if>
	                                </c:forEach>
	                                </td>
	                                <td>
	                                <c:forEach var="restVO" items="${rs.all}">
	                                	<c:if test="${vo.rest_no==restVO.rest_no}">
	                                		${restVO.rest_name}
	                                	</c:if>
	                                </c:forEach>
	                                </td>
	                                <td>
	                                   ${vo.count}
	                                </td>
	                                <td>
	                                	${vo.seating}
	                                </td>
	                                <td>
	                                   ${vo.reservation_day} 
	                                </td>
	                                <td>
	                                	${vo.period}
	                                </td>
	                                <td>
	                                   ${vo.odr_seqnum}
	                                </td>	                                	                                                              
	                            </tr>
	                            <%i++; %>
	                        </c:forEach>
                        </tbody>
                    </table>
                    
				</div>												
			</div>
			
</div>
</body>
</html>