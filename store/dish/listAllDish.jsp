<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="java.text.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dish.model.*"%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	session.setAttribute("rest_no", restVO.getRest_no()); //之後改成從session 取得rest_no
%>
<%
	DishService dishSvc = new DishService();
	Integer rest_no = (Integer) session.getAttribute("rest_no");
	List<DishVO> list = dishSvc.getOneRest(rest_no);
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>
<script>

	//時間每秒都更新
	function disptime() {
		var t = new Date();
		var y = t.getFullYear();
		var m = t.getMonth() + 1;
		var d = t.getDate();
		var h = t.getHours();
		var mi = t.getMinutes();
		var s = t.getSeconds();
		var mydate = y + "-" + m + "-" + d + "  " + h + "-" + mi + "-" + s;
		document.getElementById("myclock").innerHTML = mydate;

		var myTime = setTimeout("disptime()", 1000);
	}

</script>

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
						<h3 align="center">所有料理資料</h3>
						<a href="<%=request.getContextPath()%>/store/dish/addDish.jsp"><button
								class="btn btn-default">新增菜單</button></a>
						<hr>
						<div class="col-md-12 column" align="center">
							<div class="row clearfix"></div>
						</div>
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
							src="<%=request.getContextPath()%>/dish/ShowDishImg?dish_no=${dishVO.dish_no}">
						</td>
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

						<!--執行disptime();-->
						<script>
							disptime();
						</script>
					</div>

				</div>

			</div>
		</div>
	</div>
</body>
</html>