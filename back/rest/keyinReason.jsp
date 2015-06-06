<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.affi.model.*"%>

<%-- 執行時要從listAllAffi開始執行，才會帶到加盟資訊 --%>

<%
	AffiService affiSvc = new AffiService();
	List<AffiVO> list = affiSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<%
	AffiVO affiVO = (AffiVO) request.getAttribute("affiVO");
%>

<html>
<head>
<title>輸入加盟申請未通過之原因</title>
</head>

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

<body bgcolor='white'>
	<table border='1' cellpadding='5' cellspacing='0' width='370px'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>請在下方輸入 加盟申請未通過之原因</h3>
			</td>			
		</tr>
	</table>
	<form METHOD="post" ACTION="<%=request.getContextPath()%>/rest/rest.do">
		<table border='1' bordercolor='#CCCCFF' width=''>
			<tr>
				<textarea type="TEXT" name="rest_mobil" cols="50" rows="10"> </textarea>
			</tr>
			<tr>
				<td><input data-role="button" type="submit" value="確認送出" /></td>
			</tr>
			<input type="hidden" name="action" value="mail">
		</table>
	</form>	

<%-- <jsp:include page="/back/rest/JavaMailProccess.jsp"/> --%>
	
</body>
</html>
