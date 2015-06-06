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
 
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
<!--script src="js/less-1.3.3.min.js"></script-->
<!--append ‘#!watch’ to the browser URL, then refresh the page. -->

<link href="<%=request.getContextPath()%>/store/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="img/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="img/favicon.png">

<script type="text/javascript"
	src="<%=getServletContext().getContextPath()%>/store/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=getServletContext().getContextPath()%>/store/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=getServletContext().getContextPath()%>/store/js/scripts.js"></script>

<link rel="stylesheet"
	href="<%=getServletContext().getContextPath()%>/store/css/jquery-ui.css">
<script
	src="<%=getServletContext().getContextPath()%>/store/js/jquery-1.10.2.js"></script>
<script
	src="<%=getServletContext().getContextPath()%>/store/js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="//cdn.jsdelivr.net/webshim/1.14.5/polyfiller.js"></script>

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
	
</script>
</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="title">修改餐廳介紹</div>
				<hr>
				<div class="row clearfix">
					<div class="col-md-3 column"
						style="border-right: #E4E4E4 1px solid">
						<button type="button" class="btn btn:hover">菜單管理</button>
						<br> <br>
						<button type="button" class="btn btn:hover">訂位狀態管理</button>
						<br> <br>
						<button type="button" class="btn btn:hover">餐廳資訊管理</button>
						<br> <br>
						<button type="button" class="btn btn:hover">訂單管理</button>
						<br> <br> <a href="#" class="btn" type="button">登出</a> <br>

					</div>
					<div class="col-md-9 column"></div>
					<form METHOD="POST"
						ACTION="<%=request.getContextPath()%>/intro/intro.do"
						enctype="multipart/form-data">
						<table>
							<tr>
								<td>圖片</td><td>重新上傳<input type="file" name="intro_pic"></td>
								</tr>
								<tr>
								<td colspan="2"><img class="img-rounded" width=100% 
												src="<%=request.getContextPath()%>/intro/ShowIntroImg?intro_no=${introVO.intro_no }"></td>
								
							</tr>
							<tr>
								<td colspan="2">介紹內文 ${introVO.intro_no }</td>
							</tr>
							<tr>
								<td colspan="2"><textarea type="TEXT" name="intro_cont" rows="20" cols="50"><%=(introVO != null) ? introVO.getIntro_cont() : ""%></textArea></td>
							</tr>
							<tr>
								<td align="right"><input type="hidden" name="action"
									value="update"> <input type="hidden" name="intro_no"
									value="${introVO.intro_no }"> <input type="hidden"
									name="rest_no" value="${restVO.rest_no }"> <input
									type="submit" value="送出修改"></td>

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
						</table>
					</form>
				</div>


			</div>
		</div>
	</div>
</body>
</html>
