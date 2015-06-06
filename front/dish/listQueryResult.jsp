<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dish.model.*"%>

<%
	String dish_cont = request.getParameter("dish_cont");
	DishService dishSvc = new DishService();
	List<DishVO> list = dishSvc.query(dish_cont);
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="restSvc" scope="page" class="com.rest.model.RestService" /> <%-- useBean �O�o�[�C�Ψ�restVO --%>

<html>
<head>
<title>�Ҧ��ŦX���Ʋz���</title>
</head>
<body bgcolor='white'>
	<table border='1' cellpadding='5' cellspacing='0' width='800'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>�Ҧ��ŦX���Ʋz���</h3> <a href="select_page.jsp"><img
					src="images/back1.gif" width="100" height="32" border="0">�^����</a>
			</td>
		</tr>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>�Эץ��H�U���~:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<table border='1' bordercolor='#CCCCFF' width='600'>
		<tr>
			<th>�Ʋz�Ϥ�</th>
			<th>�Ʋz�W��</th>
			<th>�\�U�W��</th>

		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="dishVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr align='center' valign='middle'>
				<td><img class="img-rounded" width="200px"
					src="<%=request.getContextPath()%>/dish/ShowDishImg?dish_no=${dishVO.dish_no}">
				</td>
				<td>${dishVO.dish_name}</td>
				<td><c:forEach var="restVO" items="${restSvc.all}">
						<c:if test="${dishVO.rest_no==restVO.rest_no}">
	                    	�i${restVO.rest_name}�j
                    </c:if>
					</c:forEach></td>

			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>
