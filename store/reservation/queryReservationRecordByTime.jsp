<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rest.*"%>
<%!int i = 1;%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>

<style>
.btn {
	font-family: Arial;
	color: #ffffff;
	font-size: 20px;
	background: #f05118;
	text-decoration: none;
	width: 200px;
}

.btn:hover {
	-webkit-box-shadow: 0px 0px 15px #eb8b15;
	-moz-box-shadow: 0px 0px 15px #eb8b15;
	box-shadow: 0px 0px 15px #eb8b15;
	background: #fa8e3c;
	text-decoration: none;
	width: 200px;
}

#title {
	width: 1140px;
	height: 100px;
	vertical-align: middle;
	text-align: center;
	line-height: 100px; /*方法五*/
	font-family: Tahoma, Geneva, sans-serif;
	font-size: 36px;
}

#img {
	text-align: right;
	opacity: 0.5;
}

#img1 {
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
}

.col-md-3 column /*這行無效*/ {
	border: #0000FF 5px solid;
}
</style>
</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="title">FOODME店家管理頁面</div>
				<hr>
				<div class="row clearfix">
					<%@include file="/store/pageSource/header.jsp"%>
					<div class="col-md-9 column">
						<h3 align="center">餐廳訂位紀錄管理頁面(依訂位時段查詢)</h3>
						<hr>
						<div class="row clearfix" align="center">
							<div class="col-md-4 column">餐廳編號：${param.rest_no}</div>
							<div class="col-md-4 column">
								<!--餐廳名稱：${rest_name}-->
							</div>
							<div class="col-md-4 column">訂位查詢時段：${param.calendar}</div>
						</div>
						<hr>
						<table class="table">
							<thead>
								<tr>
									<th>訂位紀錄編號</th>
									<th>會員編號</th>
									<th>餐廳編號</th>
									<th>人數</th>

									<th>是否入座</th>
									<th>訂位日期</th>
									<th>訂位時段</th>
<!-- 									<th>餐券序號</th> -->
<!-- 									<th>訂位處理</th> -->
								</tr>
							</thead>
							<tbody>
								<!--查詢結果顯示-->
								<c:forEach var="vo" items="${list}">
									<%
										if (i % 2 == 1) {
									%>
									<tr class="success">
										<%
											} else {
										%><!-- 明明沒寫錯卻跳錯：改打其他程式碼，再打回原本程式碼 -->
									<tr>
										<%
											}
										%>
										<td>${vo.rec_no}</td>
										<td>${vo.mem_no}</td>
										<td>${vo.rest_no}</td>
										<td>${vo.count}</td>
										 <td width="120px">
	                                    <c:if test="${vo.seating==1}">
	                                       	 未入座
	                                    </c:if>
	                                	<c:if test="${vo.seating==0}">
	                                       	 已入座
	                                    </c:if>

	                                	<form method="post" action="<%=request.getContextPath()%>/reserv/reserv.do">		                                	
		                                	<input type="hidden" name="seating" value="${vo.seating}">
		                                	<input type="hidden" name="rest_no" value="${vo.rest_no}">
		                                	<input type="hidden" name="mem_no" value="${vo.mem_no}">
		                                	<input type="hidden" name="reservation_day" value="${vo.reservation_day}" >
		                                	<input type="hidden" name="period" value="${vo.period}">
		                                	<input type="hidden" name="action" value="change_seating"/>
		                                	<input type="hidden" name="today" value="${param.today}"/>
		                                	<input type="hidden" name="future_day" value="${param.future_day}"/>
		                                	<button type="submit" id="<%=i%>">確認入座</button>
	                                	</form>	                                		                                		                                	
	                                </td>
										<td>${vo.reservation_day}</td>
										<td>${vo.period}</td>
<%-- 										<td>${vo.odr_seqnum}</td> --%>

<!-- 										<td> -->
<!-- 									<button type="button" class="">修改</button> -->
<!-- 											<button type="button" class="">刪除</button> -->
<!-- 										</td> -->
									</tr>
									<%
										i++;
									%>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="backToHomepage" align="center">
						<a
							href="<%=request.getContextPath()%>/store/index.jsp">回首頁</a>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>