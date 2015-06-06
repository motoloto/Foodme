<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="java.text.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cop.model.*"%>
<%@ page import="java.util.*"%>
<%!int identity = 0; //用來分辨該從資料庫取出訂位數還是從request物件取出訂位數%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>



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
								<span class="rest">餐廳編號：</span> <span id="rest_no"><%=restVO.getRest_no()%></span>
							</div>
							<div class="col-md-6 column">
								<span class="rest">餐廳名稱：</span> <span id="rest_name"><%=restVO.getRest_name()%></span>
							</div>
						</div>
						<hr>
						
						 <p align="center">

    <div align="center">
    </div>
      <input type = button class="btn btn-danger" value="新增餐劵" onclick="window.location='<%=request.getContextPath()%>/store/coupon/addCop.jsp'">
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type = button class="btn btn-warning" value="餐劵掃描" onclick="window.location='<%=request.getContextPath()%>/store/coupon/QRCop.jsp'">
<!--   </form> -->

  </p>
  
  
  <%if (application.getAttribute("listCop_ByCompositeQuery")!=null){%>
<jsp:include page="listCop_ByCompositeQuery.jsp" />
<%} %>
  

					
						
						

						
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