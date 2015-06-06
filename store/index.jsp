<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="java.text.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!int identity = 0; //用來分辨該從資料庫取出訂位數還是從request物件取出訂位數%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	//jsp檔一啟動就從資料庫取得登入系統餐廳所有資料，包含時段可訂位數(line 337)
	//但這樣的話，如果是因為輸入錯誤而重導回來，仍然會從資料庫取到舊值，修改但未上傳的資料
	RestService rs = null;
	RestVO rest = null;
	RestVO x = null;
	if (identity == 0)//jsp檔一開始啟動時，時段可訂位數從資料庫取來
	{
		rs = new RestService();
		rest = rs.getOneRest(restVO.getRest_no());//餐廳編號現在是寫死
		x = rest;
		identity++;
	} else//從Servlet轉送回來時，時段可訂位數從使用者輸入值取來
	{
		//rs=new RestService();//test
		//rest=rs.getOneRest(7001);
		identity++;
		rest = (RestVO) request.getAttribute("vo");
		if (rest == null) {
			rs = new RestService();
			rest = rs.getOneRest(restVO.getRest_no());
		}
	}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

	function changeTabs() {

		var curDate_timeStamp = new Date().getTime();//取得本機系統時間，但是從伺服器取(利用JSP執行JAVA code)較好
		//這邊用到getTime()是為了等會要累加天數，呼叫getTime()才方便累加
		for (var i = 1; i <= 7; i++) {
			var tarDate = new Date(curDate_timeStamp
					+ (24 * 60 * 60 * 1000 * (i - 1)));//取得從今天到六天後所有日期
			var y = tarDate.getFullYear();
			var m = tarDate.getMonth() + 1;
			var d = tarDate.getDate();
			var week = tarDate.getDay();//取得星期幾(0~6)
			$("a[href='#tabs-" + i + "']").html(y + "-" + m + "-" + d);//在a tag內，以innerHTML加入年月日
			//設定時段一至時段六可訂位數(非已訂位數)
			var seat_num1 = 0;
			var seat_num2 = 0;
			var seat_num3 = 0;
			var seat_num4 = 0;
			var seat_num5 = 0;
			var seat_num6 = 0;

			if (week == 0) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sun6()%>
	;
			} else if (week == 1) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_mon6()%>
	;
			} else if (week == 2) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_tue6()%>
	;
			} else if (week == 3) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_wed6()%>
	;
			} else if (week == 4) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_thu6()%>
	;
			} else if (week == 5) {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_fri6()%>
	;
			} else {
				seat_num1 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat1()%>
	;
				seat_num2 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat2()%>
	;
				seat_num3 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat3()%>
	;
				seat_num4 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat4()%>
	;
				seat_num5 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat5()%>
	;
				seat_num6 =
<%=rest.getReserved_totalset()%>
	-
<%=rest.getReserved_sat6()%>
	;
			}

			var htmlStr = "<p><div class='row clearfix'><div class='col-md-12 column'>"
					+ "<table border='1px' align='center'>"
					+ "<tr><th>時段</th><td>11:00</td><td>12:00</td><td>13:00</td>"
					+ "<td>18:00</td><td>19:00</td><td>20:00</td></tr>"
					+ "<tr><th>可供訂位數</th><td>"
					+ "<input type='text' name='reserved_"+i+"1' value='"+seat_num1+"' size='3'></td>"
					+ "<td><input type='text' name='reserved_"+i+"2' value='"+seat_num2+"' size='3'></td>"
					+ "<td><input type='text' name='reserved_"+i+"3' value='"+seat_num3+"' size='3'></td>"
					+ "<td><input type='text' name='reserved_"+i+"4' value='"+seat_num4+"' size='3'></td>"
					+ "<td><input type='text' name='reserved_"+i+"5' value='"+seat_num5+"' size='3'></td>"
					+ "<td><input type='text' name='reserved_"+i+"6' value='"+seat_num6+"' size='3'></td>"
					+ "</tr>" + "</table></div></div></p>";
			$("#tabs-" + i).html(htmlStr);
			$("#tabs").tabs();
		}
	}
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
					<%@include file="/store/pageSource/header.jsp" %>

					<div class="col-md-9 column">
						<h3 align="center">餐廳訂位資訊管理頁面</h3>
						<div class="row clearfix" align="center">
							<div class="col-md-6 column">
								<span class="rest">餐廳編號：</span> <span id="rest_no"><%=rest.getRest_no()%></span>
							</div>
							<div class="col-md-6 column">
								<span class="rest">餐廳名稱：</span> <span id="rest_name"><%=rest.getRest_name()%></span>
							</div>
						</div>
						<hr>
						<div class="col-md-12 column" align="center">
							<div class="row clearfix">
								<!-- 餐廳端登入資訊(餐廳編號)要轉送到 queryReservation.jsp-->
								<form method="post"
									action="<%=request.getContextPath()%>/store/reservation/queryReservation.jsp">
									<input type="hidden" name="rest_no"
										value="<%=rest.getRest_no()%>"> <input type="hidden"
										name="rest_name" value="<%=rest.getRest_name()%>">
									<button type="submit" class="btn btn:hover"
										style="width: 250px">查詢訂位資訊</button>
								</form>
								<br>
								<br>
							</div>
						</div>
						<hr>

						<h3 align="center">修改時段可訂位數</h3>
						<hr>
						<div align="center">
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

						<form METHOD="post"
							ACTION="<%=request.getContextPath()%>/rest/rest.do">
							<div id="tabs">
								<ul>
									<li><a href="#tabs-1"></a></li>
									<li><a href="#tabs-2"></a></li>
									<li><a href="#tabs-3"></a></li>
									<li><a href="#tabs-4"></a></li>
									<li><a href="#tabs-5"></a></li>
									<li><a href="#tabs-6"></a></li>
									<li><a href="#tabs-7"></a></li>
								</ul>
								<div id="tabs-1"></div>
								<div id="tabs-2"></div>
								<div id="tabs-3"></div>
								<div id="tabs-4"></div>
								<div id="tabs-5"></div>
								<div id="tabs-6"></div>
								<div id="tabs-7"></div>
							</div>

							<!--修改時段確定按鈕-->
							<hr>
							<div align="center">
								<input type="hidden" name="action" value="change_table">
								<!--如果之後跳轉回該JSP檔是要從request物件取出訂位數，則identity須++-->
								<input type="hidden" name="total_seat"
									value="<%=rest.getReserved_totalset()%>"> <input
									type="hidden" name="rest_no" value="<%=rest.getRest_no()%>">
								<button type="submit" class="btn btn-warning btn-lg active">確認修改</button>
							</div>
						</form>
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