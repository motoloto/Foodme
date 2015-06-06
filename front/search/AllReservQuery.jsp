<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.rest.model.*"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	List<ReservRecord> reservRecordList= (List<ReservRecord>)request.getAttribute("reservRecordList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/front/pageDesign/designSrc.file"%></head>

<title>Insert title here</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form method="post" action="<%=request.getContextPath()%>/rest/rest.do">
		<input type="hidden" name="action" value="getAllReserv"> 星期幾<input
			type="text" name="reserv_date" value="4"> 地區<input
			type="text" name="rest_addr" value="西屯"> 人數<input type="text"
			name="amount" value="0"> 類別<input type="text" name="class_no"
			value="11"> <input type="submit">
	</form>
	<c:forEach var="ReservRecord" items="${reservRecordList}">
		<table>
			<tr>
				<td>編號: ${ReservRecord.rest_no}</td>
				<td>店名: ${ReservRecord.rest_name}</td>
				<td>地址: ${ReservRecord.rest_addr}</td>
				<td>電話: ${ReservRecord.rest_tel}</td>
				<td>營業時間: ${ReservRecord.rest_opentime}</td>
				<td>後位時間: ${ReservRecord.rest_waitmin}</td>
			</tr>

			<c:forEach var="perDay" items="${ReservRecord.recordOfWeek}">
				<tr>
					<td>${perDay.day }</td>
				</tr>
				<c:forEach var="perTime" items="${perDay.recordOfDay}">
					<tr>
						<td>${perTime.time}</td>
						<td>${perTime.residual}</td>
					</tr>
				</c:forEach>
			</c:forEach>


		</table>
	</c:forEach>
</body>
</html>