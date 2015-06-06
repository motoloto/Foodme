<!-- jsp檔名一定要用英文，否則會404錯誤 -->
<!-- 遇到Could not publish to the server java.lang.NullPointerException問題：eclipse重開即可-->
<%@page import="javax.print.attribute.standard.PageRanges"%>
<%@page import="com.intro.model.IntroVO"%>
<%@page import="com.intro.model.IntroService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!-- 不加的話頁面會亂碼 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%> 
<%@ page import="com.rest.model.*"%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	IntroVO introVO = (IntroVO) request.getAttribute("introVO");
	session.setAttribute("introVO", introVO);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

font {
	color: red;
	font-size: 20px;
}
</style>
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
				<div id="title">新增餐廳介紹</div>
				<hr>
				<div class="row clearfix">
					<%@include file="/store/pageSource/header.jsp" %>
					<div class="col-md-9 column"></div>
					<table>
						<FORM METHOD="POST"
							ACTION="<%=request.getContextPath()%>/intro/intro.do"
							enctype="multipart/form-data">
						<tr>
							<td>圖片</td>
							<td><input type="file" name="intro_pic"></td>
						</tr>
						<tr>
							<td>介紹內文</td>
							<td><textarea type="TEXT" name="intro_cont" rows="20" cols="50"><%=(introVO != null) ? introVO.getIntro_cont() : ""%></textArea></td>
						</tr>
						<tr>
								<td align="right"><input type="hidden" name="action"
								value="insert"> <input type="hidden" name="rest_no"
								value="${restVO.rest_no }"> <input type="submit" value="送出新增"></td>
							
						</tr>
						<tr>
							<td>
								<%-- 錯誤表列 --%> <c:if test="${not empty errorMsgs}">
									<font color='red'>請修正以下錯誤:
										<ul>
											<c:forEach var="message" items="${errorMsgs}">
												<li>${message}</li>
											</c:forEach>
										</ul>
									</font>
								</c:if>
							</td>
						
						</tr>
						</form>
					</table>
				</div>
						<!--執行disptime();-->
						<script>
							disptime();
						</script>

			</div>
		</div>
	</div>
</body>
</html>
