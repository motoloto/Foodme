<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.affi.model.*"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->
<%@ include file="/back/pageDesign/designSrc.jsp"%>


<%
	AffiService affiSvc = new AffiService();
	List<AffiVO> list = affiSvc.getAll();
	Collections.reverse(list);	 //改為降冪排列
	pageContext.setAttribute("list", list);
%>


<title>所有加盟資料 - listAllAffi.jsp</title>
</head>

<body bgcolor='white'>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>


			<!------------------以下內容---col-md-10------------------------>

			<div class="col-md-10 column">
				<div class="block">
				
							<h3>所有加盟餐廳資料</h3>
					
				

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
					<tr align='center' valign='middle'>
						<th width="8%">餐廳名稱</th>
						<th width="5%">加盟編號</th>
						<th>營業編號</th>
						<th width="10%">餐廳地址</th>
						<th>餐廳電話</th>
						<th>手機</th>
						<th >Email</th>
						<th width="6%">餐廳網址</th>
						<th width="15%">餐廳介紹</th>
						<th>餐廳相片</th>
						<th width="5%">審查通過</th>
						<th width="5%">審查不過</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="affiVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<tr align='center' valign='middle'>
							<td>${affiVO.rest_name}</td>
							<td>${affiVO.affi_no}</td>
							<td>${affiVO.bus_no}</td>

							<td>${affiVO.rest_addr}</td>
							<td>${affiVO.rest_tel}</td>
							<td>${affiVO.rest_mobil}</td>
							<td>${affiVO.rest_mail}</td>
							<td>${affiVO.rest_web}</td>
							<td >${affiVO.rest_intro}</td>
							<td><img class="img-rounded" width="200px"
								src="<%=request.getContextPath()%>/affi/ShowAffiImg?affi_no=${affiVO.affi_no}">
							</td>
							<td>
								<c:if test="${affiVO.affi_state==0}">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/rest/rest.do"
									enctype="multipart/form-data">
			     					<input class="btn btn-info" type="submit" value="通過"><input type="hidden"
										name="affi_no" value="${affiVO.affi_no}">
			    					 <input
										type="hidden" name="rest_account" value="${affiVO.rest_mail}">
									<input type="hidden" name="bus_no" value="${affiVO.bus_no}">
									<input type="hidden" name="rest_name"
										value="${affiVO.rest_name}"> <input type="hidden"
										name="rest_addr" value="${affiVO.rest_addr}"> <input
										type="hidden" name="rest_tel" value="${affiVO.rest_tel}">
									<input type="hidden" name="rest_mail"
										value="${affiVO.rest_mail}"> <input type="hidden"
										name="rest_web" value="${affiVO.rest_web}"> <input
										type="hidden" name="rest_intro" value="${affiVO.rest_intro}">
									<input type="hidden" name="rest_photo"
										value="${affiVO.rest_photo}"> <input type="hidden"
										name="action" value="insert">
								</FORM>
								</c:if>
								</td>
								<td>
								<c:if test="${affiVO.affi_state==0}">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/rest/rest.do">
									<input class="btn btn-info" type="submit" value="不通過"> <input type="hidden"
										name="affi_no" value="${affiVO.affi_no}"> <input
										type="hidden" name="action" value="return">
								</FORM>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="page2.file"%>

			</div>
</div>
			<!------------------以上內容--</div>----------------->
		</div>
			<div id="bottom_space"></div>
			<div id="bottom_space"></div>
	</div>
</body>
</html>
