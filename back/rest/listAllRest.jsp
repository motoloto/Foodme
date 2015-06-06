<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rest.model.*"%>

<%
	RestService restSvc = new RestService();
	List<RestVO> list = restSvc.getAll();
	Collections.reverse(list);	 //改為降冪排列
	pageContext.setAttribute("list", list);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->
<%@ include file="/back/pageDesign/designSrc.jsp"%>

<title>所有餐廳資料</title>
</head>
<body bgcolor='white'>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>

			<!------------------以下內容---col-md-10------------------------>

			<table border='1' cellpadding='5' cellspacing='0' width='800'>
				<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
					<td>
						<h3>所有餐廳資料</h3> <a href="test_select_page.jsp"><img
							src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
					</td>
				</tr>
			</table>

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

			<table border='1' bordercolor='#CCCCFF' width=''>
				<tr>
					<th width="150px">餐廳名稱</th>
					<th>餐廳編號</th>
					<th>加盟編號</th>
					<th>營業編號</th>
					<th>餐廳地址</th>
					<th>餐廳電話</th>
					<th>Email</th>
					<th>餐廳網址</th>
					<th width="300px">餐廳介紹</th>
					<th>餐廳相片</th>

				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="restVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<tr align='center' valign='middle'>

						<td>${restVO.rest_name}</td>
						<td>${restVO.rest_no}</td>
						<td>${restVO.affi_no}</td>
						<td>${restVO.bus_no}</td>
						<td>${restVO.rest_addr}</td>
						<td>${restVO.rest_tel}</td>
						<td>${restVO.rest_mail}</td>
						<td>${restVO.rest_web}</td>
						<td>${restVO.rest_intro}</td>

						<td><img class="img-rounded" width=100%
							src="<%=request.getContextPath()%>/rest/ShowRestImg?rest_no=${restVO.rest_no}">
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="page2.file"%>

			<!------------------以上內容--</div>----------------->

		</div>
	</div>

</body>
</html>
