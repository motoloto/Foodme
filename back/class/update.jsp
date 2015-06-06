<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.restClass.model.*"%>
<%
	ClassVO classVO = (ClassVO) request.getAttribute("classVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<% request.setCharacterEncoding("UTF-8");%>
<html>
<head>
<title>餐廳分類資料修改 - update.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>餐廳分類資料修改 - update.jsp</h3>
		<a href="class.jsp">回首頁</a></td>
	</tr>
</table>

<h3>資料修改:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/class/class.do" name="form1">
<table border="0">
	<tr>
		<td>分類編號:<font color=red><b>*</b></font></td>
		<td><%=classVO.getClass_no()%></td>
	</tr>
	<tr>
		<td>分類名稱:</td>
		<td><input type="TEXT" name="class_name" size="45" value="<%=classVO.getClass_name()%>" /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="class_no" value="<%=classVO.getClass_no()%>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
