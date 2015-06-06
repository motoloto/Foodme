<%@page import="com.restClass.model.ClassService"%>
<%@page import="com.rest.model.*"%>
<%@page import="com.banner.model.*"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	List<ReservRecord> reservRecordList = (List<ReservRecord>) request.getAttribute("reservRecordList");
	java.sql.Date  reserv_date=(java.sql.Date)request.getAttribute("reserv_date");
	pageContext.setAttribute("reserv_date", reserv_date);
	String rest_addr=(String)request.getAttribute("rest_addr");
	pageContext.setAttribute("rest_addr", rest_addr);
	Integer reserv_count=(Integer)request.getAttribute("reserv_count");
	pageContext.setAttribute("reserv_count", reserv_count);
	ClassService classSrv=new ClassService();
	pageContext.setAttribute("classList",classSrv.getAll());
	
	BannerService bannerSrv = new BannerService();
	List<BannerVO> bannerList = bannerSrv.getAll();
	pageContext.setAttribute("bannerList", bannerList);
%>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file"%></head>
<script>
$(document).ready(function (){
	$("input#dish_cont").autocomplete({
        width: 300,
        max: 10,
        delay: 100,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response) {
        	 $.ajax({
                 url: "<%=request.getContextPath()%>/dish/dish.do",
 															dataType : "json",
 															contentType : "application/x-www-form-urlencoded; charset=UTF-8",
 															data : {
 																term : request.term,
 																action : "autoCommit"
 															},
 															success : function(
 																	data,
 																	textStatus,
 																	jqXHR) {
 																console
 																		.log(data);
 																for (var i = 0; i < data.length; i++) {
 																	data[i] = decodeURI(data[i]); // 在jsp加上 decode
 																}
 																var items = data;
 																response(items);
 															},
 															error : function(
 																	jqXHR,
 																	textStatus,
 																	errorThrown) {
 																console
 																		.log(textStatus);
 															}
 														});
 											}

 										});
	$("#reserv_date").datepicker({
		 maxDate: "+6d",
		 minDate: new Date(),
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

// for(var i=1;i<=peopleMax;i++){
<%-- 	str+="<option value='"+i+" <%=(reserv_count!=null && reserv_count.equals==i)? selected:''%>'>"+i+"</option>"; --%>
// }
// $('#peopleSelecter').html(str);

});
</script>
</head>

<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->
				<ul class="nav nav-tabs">
					<li role="presentation" class="active"><a
						href="fe_searchRs.html">分類推薦</a></li>
					<li role="presentation"><a href="fe_random.html">隨機推薦</a></li>
					<li role="presentation"><a href="fe_LocRecom.html">位置推薦</a></li>
				</ul>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="carousel slide" id="carousel-648895"
							data-interval="3000" data-ride="carousel">
							<ol class="carousel-indicators">
								<c:forEach var="banner" items="${bannerList}" varStatus="status">
									<li ${status.index==0?'class=\"active\" ':'' }
										data-slide-to="${status.index}" data-target="#carousel-648895"></li>

								</c:forEach>
							</ol>
							<div class="carousel-inner">
								<c:forEach var="banner" items="${bannerList}" varStatus="status">
									<div class="item  ${status.index==0?'active':''}">
									<a href="<%=getServletContext().getContextPath()%>/front/restaurant/fe_restInfo.jsp?rest_no=${banner.rest_no}">
										<img
											src="<%=getServletContext().getContextPath()%>/banner/ImgShow?banner_no=${banner.banner_no}"></a>
										<div class="carousel-caption">
											<h3>${banner.banner_title}</h3>
											<p>${banner.banner_cont}</p>


										</div>
									</div>
								</c:forEach>

							</div>
							<a class="left carousel-control" href="#carousel-648895"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left"></span></a> <a
								class="right carousel-control" href="#carousel-648895"
								data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right"></span></a>
						</div>
					</div>

					<div class="row clearfix ">
						<div class="col-md-12 column ">
							<div class="sm_ad_block">

								<div class="table-responsive">
									<table class="table table-bordered">
										<tr>
											<td>
												<form method="post" class="form"
													action="<%=request.getContextPath()%>/reserv/reserv.do">
													<div class="form-group col-sm-3 ">
														<label>時間</label> <input type="hidden" name="action"
															value="getAllReserv">
														<%
															java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
															java.sql.Date after7days = new java.sql.Date(
																	System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000);
														%>
														<input type="text" id="reserv_date" name="reserv_date"
															class="form-control" width="300px"
															value="<%=(reserv_date != null) ? reserv_date : date_SQL%>"
															max="<%=after7days%>">
													</div>
													<div class="form-group col-sm-3">
														<label>地區</label> <select class="form-control"
															name="rest_addr">
															<option value="">請選擇地點</option>
															<option value="台北市"
																<%=rest_addr != null && rest_addr.equals("台北市") ? "selected"
					: ""%>>台北市</option>
															<option value="新北市"
																<%=rest_addr != null && rest_addr.equals("新北市") ? "selected"
					: ""%>>新北市</option>
															<option value="桃園市"
																<%=rest_addr != null && rest_addr.equals("桃園市") ? "selected"
					: ""%>>桃園市</option>
															<option value="新竹縣"
																<%=rest_addr != null && rest_addr.equals("新竹縣") ? "selected"
					: ""%>>新竹縣</option>
															<option value="新竹市"
																<%=rest_addr != null && rest_addr.equals("新竹市") ? "selected"
					: ""%>>新竹市</option>
															<option value="台中市"
																<%=rest_addr != null && rest_addr.equals("台中市") ? "selected"
					: ""%>>台中市</option>
															<option value="台南市"
																<%=rest_addr != null && rest_addr.equals("台南市") ? "selected"
					: ""%>>台南市</option>
															<option value="高雄市"
																<%=rest_addr != null && rest_addr.equals("高雄市") ? "selected"
					: ""%>>高雄市</option>
														</select>
													</div>
													<div class="form-group col-sm-2">
														<label>人數</label> <select id="peopleSelecter"
															name="reserv_count" class="form-control">
															<option value=1
																<%=(reserv_count != null && reserv_count.equals(1)) ? "selected"
					: ""%>>1</option>
															<option value=2
																<%=(reserv_count != null && reserv_count.equals(2)) ? "selected"
					: ""%>>2</option>
															<option value=3
																<%=(reserv_count != null && reserv_count.equals(3)) ? "selected"
					: ""%>>3</option>
															<option value=4
																<%=(reserv_count != null && reserv_count.equals(4)) ? "selected"
					: ""%>>4</option>
															<option value=5
																<%=(reserv_count != null && reserv_count.equals(5)) ? "selected"
					: ""%>>5</option>
															<option value=6
																<%=(reserv_count != null && reserv_count.equals(6)) ? "selected"
					: ""%>>6</option>
															<option value=7
																<%=(reserv_count != null && reserv_count.equals(7)) ? "selected"
					: ""%>>7</option>
															<option value=8
																<%=(reserv_count != null && reserv_count.equals(8)) ? "selected"
					: ""%>>8</option>
															<option value=9
																<%=(reserv_count != null && reserv_count.equals(9)) ? "selected"
					: ""%>>9</option>
															<option value=10
																<%=(reserv_count != null && reserv_count.equals(10)) ? "selected"
					: ""%>>10</option>
															<option value=11
																<%=(reserv_count != null && reserv_count.equals(11)) ? "selected"
					: ""%>>11</option>
															<option value=12
																<%=(reserv_count != null && reserv_count.equals(12)) ? "selected"
					: ""%>>12</option>
															<option value=13
																<%=(reserv_count != null && reserv_count.equals(13)) ? "selected"
					: ""%>>13</option>
															<option value=14
																<%=(reserv_count != null && reserv_count.equals(14)) ? "selected"
					: ""%>>14</option>
															<option value=15
																<%=(reserv_count != null && reserv_count.equals(15)) ? "selected"
					: ""%>>15</option>
															<option value=16
																<%=(reserv_count != null && reserv_count.equals(16)) ? "selected"
					: ""%>>16</option>
															<option value=17
																<%=(reserv_count != null && reserv_count.equals(17)) ? "selected"
					: ""%>>17</option>
															<option value=18
																<%=(reserv_count != null && reserv_count.equals(18)) ? "selected"
					: ""%>>18</option>
															<option value=19
																<%=(reserv_count != null && reserv_count.equals(19)) ? "selected"
					: ""%>>19</option>
															<option value=20
																<%=(reserv_count != null && reserv_count.equals(20)) ? "selected"
					: ""%>>20</option>
														</select>
													</div>
													<div class="form-group col-sm-2 ">
														<label>類別</label> <select name="class_no"
															class="form-control">
															<option value="">不拘</option>
															<c:forEach var="classVO" items="${classList}">
																<option value="${classVO.class_no }">${classVO.class_name}</option>
															</c:forEach>
														</select>
													</div>
													<div class="form-group col-sm-1">
														<button type="submit" class="btn btn-primary ">找餐廳</button>
													</div>
												</form>
											</td>
											<td>

												<FORM METHOD="post" class=""
													ACTION="<%=request.getContextPath()%>/front/dish/searchDish.jsp">
													<div class="form-group col-sm-4">
														<label><b>料理搜尋 ex:牛肉麵:</b></label>
													</div>
													<div class="form-group col-sm-5">
														<input type="text" name="dish_cont" class="form-control"
															id="dish_cont" value="" placeholder="想吃什麼呢?">
													</div>
													<div class="form-group col-sm-3">
														<input type="submit" class="btn btn-primary form-control"
															value="送出">
													</div>
													</div>
												</FORM>
												<div class="col-md-2 column">
													<FORM METHOD="post"
														ACTION="<%=request.getContextPath()%>/rest/rest.do">
														<button type="submit" class="btn btn-danger">好手氣</button>
														<input type="hidden" name="action" value="random">
													</FORM>
													<%-- 			 <jsp:forward page="<%=request.getContextPath()+\"/front/restaurant/fe_restInfo.jsp\"%>"/> --%>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>

					</div>
					<div class="row clearfix">
						<c:forEach var="ReservRecord" items="${reservRecordList}">
							<div class="col-md-4">
								<div class="sm_ad_block">
									<a
										href="<%=getServletContext().getContextPath()%>/front/restaurant/fe_restInfo.jsp?rest_no=${ReservRecord.rest_no}"><img
										width=100%
										src="<%=getServletContext().getContextPath()%>/rest/ShowRestImg?rest_no=${ReservRecord.rest_no}">
									</a>
									<div class="caption">
										<table>
											<tr>
												<td>《 ${ReservRecord.rest_name}》</td>
											</tr>
											<tr>
												<td>地址: ${ReservRecord.rest_addr}</td>
											</tr>
											<tr>
												<td>電話: ${ReservRecord.rest_tel}</td>
											</tr>
											<tr>
												<td>營業時間: ${ReservRecord.rest_opentime}</td>
											</tr>
											<tr>
												<td>候位時間: ${ReservRecord.rest_waitmin}</td>
											</tr>
										</table>
									</div>
									<c:forEach var="perDay" items="${ReservRecord.recordOfWeek}">
										<c:if test="${perDay.recordOfDay.size()==0}">
											<label>抱歉！此時段已無剩餘空位</label>
										</c:if>
										<c:forEach var="perTime" items="${perDay.recordOfDay}">
											<c:if test="${perTime!=null}">
												<a
													href="/YA101G5/front/restaurant/fe_reserv.jsp?reserv_date=${reserv_date}&reserv_count=${reserv_count}&reserv_time=${perTime.time}&rest_no=${ReservRecord.rest_no}">
													<button class='btn btn-danger'>${perTime.time}</button>
												</a>
											</c:if>
										</c:forEach>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>

					<!---------- 以上為網頁內容----------->
					<div class="row clearfix">
						<div class="col-md-12 column"></div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
