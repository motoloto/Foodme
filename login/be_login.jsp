<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>Bootstrap 3, from LayoutIt!</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
<!--script src="js/less-1.3.3.min.js"></script-->
<!--append ‘#!watch’ to the browser URL, then refresh the page. -->

<link
	href="<%=getServletContext().getContextPath()%>/login/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=getServletContext().getContextPath()%>/login/css/style.css"
	rel="stylesheet">

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
	src="<%=getServletContext().getContextPath()%>/login/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=getServletContext().getContextPath()%>/login/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=getServletContext().getContextPath()%>/login/js/scripts.js"></script>
<style type="text/css">
#be_login_container {
	margin: 200px auto;
	width: 400px;
	background-color: #FFF;
	border-radius: 10px;
}
</style>
</head>
<body	style="background-image:url('<%=getServletContext().getContextPath()%>/login/img/background.png'); ">
	<div class=" container-fluid container"
		id="be_login_container">

		<div class="row clearfix block">
			<div class="col-md-12 column">
				<h3>FOOD me 後端管理系統</h3>
				<FORM class="form-horizontal"  ACTION="<%=request.getContextPath()%>/login/login.do" METHOD=POST>
	
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">帳號</label>
						<div class="col-sm-10">
							<input NAME="mgr_account"  class="form-control" >
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密碼</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword3" NAME="mgr_pwd" autocomplete="off">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
						<INPUT TYPE="hidden" name="action" VALUE="login">
							<button type="submit" class="btn btn-default">Sign in</button>
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
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
