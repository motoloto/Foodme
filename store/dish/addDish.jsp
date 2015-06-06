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
	DishVO dishVO = (DishVO) request.getAttribute("dishVO");
	session.setAttribute("rest_no", rest_no);	//之後改成從session 取得rest_no

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>
<script>
	//JQuery語法：onload時執行{}內函數
	$(function() {
		changeTabs();

	});

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
						<h3 align="center">所有料理資料</h3><a href="<%=request.getContextPath()%>/store/dish/addDish.jsp"><button class="btn btn-default">新增菜單</button></a>
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
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do" enctype="multipart/form-data" name="form1">
<table align="center"><th><a href="#" data-role="fieldcontain" data-inline="true" data-theme="b" mini="true"><h2>新增料理	<font color=red><b>*</b></font>為必填欄位</h2></a></th></table>

<table align="center" border="0" padding="10">


<tr><td><font color=red><b>*</b></font></td><td>料理名稱:</td><td><input type="TEXT" name="dish_name" size="45"	value="<%= (dishVO==null)? "" : dishVO.getDish_name()%>" /></td></tr>
<tr><td><font color=red><b>*</b></font></td><td>料理描述:</td><td><input type="TEXT" name="dish_cont" size="45"	value="<%= (dishVO==null)? "" : dishVO.getDish_cont()%>" /></td></tr>
<tr><td><font color=red><b>*</b></font></td><td>上架狀態:</td><td>


<select id="dish_state" name="dish_state">							
	
	<option value="0" >下架</option>	
	<option value="1" >上架</option>	 

</select>

<tr><td><font color=red><b>*</b></td><td>料理價格:</td><td><input type="TEXT" name="dish_price" size="45" value="<%= (dishVO==null)? "" : dishVO.getDish_price()%>" /></td></tr>

<tr><td></td><td>料理相片:</td><td><input type="file" name="dish_pic"></td></tr>

<!-- <tr><td>*</td><td align="top">餐廳介紹</td><td><textarea name="rest_intro" cols="50" rows="10"> </textarea></td></tr> -->


<tr><td></td><input type="hidden" name="action" value="insert">
<!-- <tr><td></td><td></td><td><input data-role="button" type="submit" value="送出新增" data-theme="b"/></td></tr> -->
<!-- <tr><td></td><td></td><td><a href="#" data-role="button" data-icon="" data-inline="false" data-mini="true" data-theme="b">新增</a></td></tr>
-->

<input type="hidden" name="dish_name" value="${dishVO.rest_name}">
<input type="hidden" name="dish_cont" value="${dishVO.dish_cont}">
<input type="hidden" name="dish_state" value="${dishVO.dish_state}">
<input type="hidden" name="dish_price" value="${dishVO.dish_price}">
<tr><td></td><td></td><td><input type="submit" value="送出新增"/></td></tr>
</FORM>

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