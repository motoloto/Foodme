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
	DishVO dishVO = (DishVO) request.getAttribute("dishVO");
	session.setAttribute("rest_no", restVO.getRest_no()); //之後改成從session 取得rest_no
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
						<h3 align="center">餐廳訂位資訊管理頁面</h3>

						<hr>
						<div class="col-md-12 column" align="center">
							<div class="row clearfix">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/dish/dish.do"
									enctype="multipart/form-data" name="form1">
									<table align="center">
										<th><a href="#" data-role="fieldcontain"
											data-inline="true" data-theme="b" mini="true"><h2>料理更新</h2></a></th>
									</table>
									<table align="center" border="0" padding="10">


										<%-- <tr><td></font></td><td>料理編號:</td><td><input type="TEXT" name="dish_no" size="45"		value="<%= (dishVO==null)? "" : dishVO.getDish_no()%>" /></td></tr> --%>

										<tr>

											<td width="10%">料理編號:</td>
											<td><%=dishVO.getDish_no()%></td>
										</tr>

										<%-- <tr><td></font></td><td>餐廳編號:</td><td><input type="TEXT" name="reset_no" size="45"	value="<%= (dishVO==null)? "" : dishVO.getRest_no()%>" /></td></tr> --%>
										<tr>

											<td width="10%">料理名稱:</td>
											<td><input type="TEXT" name="dish_name" size="45"
												value="<%=(dishVO == null) ? "" : dishVO.getDish_name()%>" /></td>
										</tr>
										<tr>

											<td width="10%">料理描述:</td>
											<td><input type="TEXT" name="dish_cont" size="45"
												value="<%=(dishVO == null) ? "" : dishVO.getDish_cont()%>" /></td>
										</tr>

										<tr>
											<td width="10%">上架狀態:</td>
											<td><select id="dish_state" name="dish_state">

													<option value="0"
														<%=(dishVO.getDish_state().equals("0")) ? "selected" : ""%>>下架</option>
													<option value="1"
														<%=(dishVO.getDish_state().equals("1")) ? "selected" : ""%>>上架</option>

											</select></td>
										</tr>
										<tr>

											<td width="10%">料理價格:</td>
											<td><input type="TEXT" name="dish_price" size="45"
												value="<%=(dishVO == null) ? "" : dishVO.getDish_price()%>" /></td>
										</tr>
										<tr>
											<td></td>
											<td width="10%">料理相片:</td>
											<td><img class="img-rounded" width="200px"
												src="<%=request.getContextPath()%>/dish/ShowDishImg?dish_no=${dishVO.dish_no}">
											</td>
											<td><input type="file" name="dish_pic" value="重新上傳相片" /></td>
										</tr>

										<tr>
											<td>
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="dish_no" value="${dishVO.dish_no}">
											<input type="hidden" name="dish_name" value="${dishVO.dish_name}">
											<input type="hidden" name="dish_cont" value="${dishVO.dish_cont}">
											<input type="hidden" name="dish_state" value="${dishVO.dish_state}">
											<input type="hidden" name="dish_price" value="${dishVO.dish_price}">
											</td>
										<tr>
											<td></td>
											<td></td>
											<td><input type="submit" value="送出更新" /></td>
										</tr>
									</table>
								</FORM>
							</div>
						</div>
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