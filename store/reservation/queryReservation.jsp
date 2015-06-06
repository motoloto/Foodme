<!-- jsp檔名一定要用英文，否則會404錯誤 -->
<!-- 遇到Could not publish to the server java.lang.NullPointerException問題：eclipse重開即可-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!-- 不加的話頁面會亂碼 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.rest.model.*"%>
<%!Integer rest_no = 0;
	String rest_name = null;
	Date current_date = new Date();//產生今天日期物件，準備轉成日期字串
	Date future_date = new Date(current_date.getTime() + 6 * 24 * 60 * 60
			* 1000);//產生六天後日期物件
	DateFormat df = null;
	String today = null;
	String six_day = null;%>


<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	rest_no = restVO.getRest_no();
	rest_name = restVO.getRest_name();
%>

<%
	df = new SimpleDateFormat("yyyy-MM-dd");
	today = df.format(current_date);
	six_day = df.format(future_date);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>
<script>
	webshims.setOptions('forms-ext', {
		types : 'date'
	});
	webshims.polyfill('forms forms-ext');
</script>
<script>
	jQuery(function($) {
		$('#example1').datepicker();
	});
</script>
<script type="text/javascript">
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
					<form method="POST"
						action="<%=request.getContextPath()%>/reserv/reserv.do">
						<div class="col-md-9 column">
							<h3 align="center">餐廳訂位資訊管理頁面</h3>
							<hr>
							<div class="row clearfix" align="center">
								<div class="col-md-6 column">
									<span class="rest">餐廳編號：</span> <span id="rest_no"><%=rest_no%></span>
								</div>
								<!--<div class="col-md-6 column">
                            <span class="rest">餐廳名稱：</span>
                            <span id="rest_name"><%=rest_name%></span>
                        </div>-->
							</div>
							<div align="center">
								<c:if test="${not empty errorMsgs}">
									<font>請修正以下錯誤:
										<ul>
											<c:forEach var="message" items="${errorMsgs}">
												<li>${message}</li>
											</c:forEach>
										</ul>
									</font>
								</c:if>
							</div>

							<div align="center" style="font-size: 20px">請輸入欲查詢會員之編號：</div>
							<br>
							<br>
							<div class="col-md-12 column" align="center">
								<input type="text" name="mem_no">
							</div>
							<HR>
							<div align="center" style="font-size: 20px">或輸入欲查詢日期：</div>
							<br>
							<br>
							<div class="col-md-12 column" align="center">
								<input type="date" name="calendar" value="" min="<%=today%>"
									max="<%=six_day%>">
							</div>
							<!-- input不可用id命名 -->
							<br>
							<br> <br>
							<br>
							<div class="col-md-12 column" align="center">
								<input TYPE="hidden" name="action" value="store_query">
								<!--表示是餐廳端的查詢-->
								<input type="hidden" name="rest_no" value="<%=rest_no%>">
								<input type="hidden" name="rest_name" value="<%=rest_name%>">
								<input type="hidden" name="today" value="<%=today%>"> <input
									type="hidden" name="future_day" value="<%=six_day%>">
								<button type="submit" class="btn btn-warning btn-lg active">確定</button>
							</div>
							<!-- type一定要用submit，不可用button，否則表單不會提交 -->
						</div>
					</form>

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
