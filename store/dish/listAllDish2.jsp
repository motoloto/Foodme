<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dish.model.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->
<%@ include file="/store/pageSource/styleSource.jsp"%>


<%
	DishService dishSvc = new DishService();
	Integer rest_no = (Integer) session.getAttribute("rest_no");
	List<DishVO> list = dishSvc.getOneRest(7001);
	pageContext.setAttribute("list", list);
%>

</head>
<body bgcolor='white'>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/store/pageSource/header.jsp"%>

			<!------------------以下內容---col-md-10------------------------>

			<table border='1' cellpadding='5' cellspacing='0' width='800'>
				<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
					<td>
						<h3>所有料理資料</h3>
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
				<tr valign='middle'>
					<!-- 					<th>選取修改項目</th> -->
					<th>料理編號</th>
					<th>餐廳編號</th>
					<th>料理名稱</th>
					<th>料理描述</th>
					<th>上架狀態</th>
					<th>料理價格</th>
					<th>料理相片</th>
					<th>修改</th>
					<th>刪除</th>
				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="dishVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<tr align='center' valign='middle'>
						<%-- 						<td><input type="radio" name="dish_no" value="${DishVO.dish_no}" /></td> --%>
						<td>${dishVO.dish_no}</td>
						<td>${dishVO.rest_no}</td>
						<td>${dishVO.dish_name}</td>
						<td>${dishVO.dish_cont}</td>
						<td><c:if test="${dishVO.dish_state==0}">
								下架
							</c:if> <c:if test="${dishVO.dish_state==1}">
								上架
							</c:if></td>
						<td>${dishVO.dish_price}</td>

						<td><img class="img-rounded" width="200px"
							src="<%=request.getContextPath()%>/store/dish/ShowDishImg?dish_no=${dishVO.dish_no}">
						</td>

<!-- 						<td> -->
<%-- 							<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/rest/rest.do"	enctype="multipart/form-data"> --%>
<!-- 								<input type="hidden" name="action" value="insert"> -->
<!-- 							</FORM> -->
<!-- 						</td> -->
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/dish/dish.do ">
								<input type="submit" value="修改"> <input type="hidden"
									name="dish_no" value="${dishVO.dish_no}">
								<%-- 								<input type="hidden" name="requestURL" value="<%request.getServletPath()%>"> --%>
								<%-- 								<input type="hidden" name="whichPage" value="<%whichPage %>"> --%>
								<input type="hidden" name="dish_no" value="${dishVO.dish_no}">
								<input type="hidden" name="rest_no" value="${dishVO.rest_no}">
								<input type="hidden" name="dish_name"
									value="${dishVO.dish_name}"> <input type="hidden"
									name="dish_cont" value="${dishVO.dish_cont}"> <input
									type="hidden" name="dish_state" value="${dishVO.dish_state}">
								<input type="hidden" name="dish_price"
									value="${dishVO.dish_price}"> <input type="hidden"
									name="action" value="getOne_For_Update">
							</FORM>
						</td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/dish/dish.do ">
								<input type="submit" value="刪除"> <input type="hidden"
									name="dish_no" value="${dishVO.dish_no}">
								<%-- 								<input type="hidden" name="requestURL" value="<%request.getServletPath()%>"> --%>
								<%-- 								<input type="hidden" name="whichPage" value="<%whichPage %>"> --%>
								<input type="hidden" name="action" value="delete">
							</FORM>
						</td>
					</tr>
				</c:forEach>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/store/dish/updateDish.jsp">
					<!-- 					<td><input type="submit" value="提交修改" /></td>					 -->
					<input type="hidden" name="dish_no" value="${dishVO.dish_no}">
					<input type="hidden" name="rest_no" value="${dishVO.rest_no}">
					<input type="hidden" name="dish_name" value="${dishVO.dish_name}">
					<input type="hidden" name="dish_cont" value="${dishVO.dish_cont}">
					<input type="hidden" name="dish_state" value="${dishVO.dish_state}">
					<input type="hidden" name="dish_price" value="${dishVO.dish_price}">
					<input type="hidden" name="action" value="update">
				</FORM>
			</table>
			<%@ include file="page2.file"%>

			<!------------------以上內容--</div>----------------->

		</div>
	</div>
</body>
</html>
