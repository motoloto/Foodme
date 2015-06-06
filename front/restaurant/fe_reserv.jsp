<%@page import="com.rest.model.*"%>
<%@page import="com.mem.model.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
RestService restSrv=new RestService();
%> 
<html>
<head>
<%@ include file="/front/pageDesign/designSrc.file"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<%
	pageContext.setAttribute("rest_no",
			request.getParameter("rest_no"));
	pageContext.setAttribute("reserv_date",
			request.getParameter("reserv_date"));
	pageContext.setAttribute("reserv_count",
			request.getParameter("reserv_count"));
	String reserv_time = request.getParameter("reserv_time");
	pageContext.setAttribute("reserv_time", reserv_time);
RestVO restVO=restSrv.getOneRest(Integer.parseInt(request.getParameter("rest_no")));
pageContext.setAttribute("restVO", restVO);
	Integer reserv_period = 0;
	if (reserv_time.equals("11:00")) {
		reserv_period = 1;
		pageContext.setAttribute("reserv_period", reserv_period);
	}
	if (reserv_time.equals("12:00")) {
		reserv_period = 2;
		pageContext.setAttribute("reserv_period", reserv_period);
	}
	if (reserv_time.equals("13:00")) {
		reserv_period = 3;
		pageContext.setAttribute("reserv_period", reserv_period);
	}
	if (reserv_time.equals("18:00")) {
		reserv_period = 4;
		pageContext.setAttribute("reserv_period", reserv_period);
	}
	if (reserv_time.equals("19:00")) {
		reserv_period = 5;
		pageContext.setAttribute("reserv_period", reserv_period);
	}
	if (reserv_time.equals("20:00")) {
		reserv_period = 6;
		pageContext.setAttribute("reserv_period", reserv_period);
	}

	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>
<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

				<div class="row clearfix">
					<div class="col-md-12 column">
						<h3 id="title">FOODMEX餐廳定位系統</h3>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="progress">
							<div class="progress-bar progress-success"></div>
						</div>
					</div>
				</div>

				<div class="row clearfix">
					<div class="col-md-12 column">
						<h3
							style="text-align: center; background-color: #F90; letter-spacing: 10px">
							請確認您的訂位狀況</h3>

						<div class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-7 column">
									<img width=100% src="<%=request.getContextPath()%>/rest/ShowRestImg?rest_no=${rest_no }" />
								</div>
								<div class="col-md-5 column">
									<h2>${restVO.rest_name}</h2>
									<p>${restVO.rest_intro }</p>
								</div>
							</div>
						</div>


						<form role="form" method="POST"
							action="<%=request.getContextPath()%>/reserv/reserv.do">
							<div class="form-group centerTable">
								<h3>訂位人</h3><br />
								<div align="center">

									<div
										style="border-style: solid; width: 200px; border-style: ridge;">
										${memVO.mem_name}</div>
								</div>
							</div>
							<div class="form-group centerTable">
								<h3>訂位日期</h3><br />
								<div align="center">
									<div
										style="border-style: solid; width: 200px; border-style: ridge;">
										${reserv_date }</div>
								</div>
							</div>
							<div class="form-group centerTable">
								<h3>時間</h3><br />
								<div align="center">
									<div
										style="border-style: solid; width: 200px; border-style: ridge;">
										${reserv_time}</div>
								</div>
							</div>
							<div class="form-group centerTable">
								<h3>人數</h3><br />
								<div align="center">
									<!--不知為何一定要寫這-->
									<div
										style="border-style: solid; width: 200px; border-style: ridge;">
										${reserv_count}人</div>
								</div>
							</div>
							<div class="form-group centerTable">
						
									<input type="hidden" name="mem_no" value="${memVO.mem_no }">
									<input type="hidden" name="reserv_count"
										value="${reserv_count}"> <input type="hidden"
										name="reserv_period" value="${reserv_period}"> <input
										type="hidden" name="rest_no" value="${rest_no}"> <input
										type="hidden" name="reserv_date" value="${reserv_date}">
									<button type="submit" class="btn btn-default">確認訂位</button>
									<input type="hidden" name="action"
										value="insert">
								</div>
							</div>
						</form>
						<!---------- 以上為網頁內容----------->
						<div class="row clearfix">
							<div class="col-md-12 column"></div>
						</div>
					</div>
				</div>
			</div>
</body>
</html>