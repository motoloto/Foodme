<%@page import="com.cop.model.CopService"%>
<%@page import="com.news.model.NewsService"%>
<%@page import="com.banner.model.BannerService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banner.model.*"%>
<%@ page import="com.ads.model.*"%>
<%@ page import="com.cop.model.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="java.util.*"%>
<%
	BannerService bannerSrv = new BannerService();
	List<BannerVO> bannerList = bannerSrv.getAll();
	pageContext.setAttribute("bannerList", bannerList);

	AdsService adsrSrv = new AdsService();
	List<AdsVO> adsList = adsrSrv.getAll();
	pageContext.setAttribute("adsList", adsList);

	NewsService newsSrv = new NewsService();
	List<NewsVO> newsList = newsSrv.getAll();
	pageContext.setAttribute("newsList", newsList);
	
	CopService copSrv=new CopService();
	CopVO hotcopVO=copSrv.getHotSaleCop();
	CopVO latestcopVO=copSrv.getLatestCop();
	pageContext.setAttribute("hotcopVO", hotcopVO);
	pageContext.setAttribute("latestcopVO", latestcopVO);
%>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file"%>
<script>
	$(document).ready(
			function() {
				for (var i = 1; i <= 20; i++) {
					$("#reserv_count").append(
							"<option value='"+i+"'>" + i + "</option>");
				}
				$("#reserv_date").datepicker({
					maxDate : "+6d",
					minDate : new Date(),
					dateFormat : 'yy-mm-dd',
					changeMonth : true,
					changeYear : true
				});
			});
</script>
</head>

<body>
	<div class="container index_content">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

				<div class="row clearfix">
					<div class="col-md-12 column">
						<ul class="breadcrumb">
							<label>現在位置：</label>
							<li><a href="#">Home</a>
						</ul>
					</div>
				</div>
				<div class="row clearfix" id="index_content">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="carousel slide" id="carousel-648895"
								data-interval="3000" data-ride="carousel">
								<ol class="carousel-indicators">
									<c:forEach var="banner" items="${bannerList}"
										varStatus="status">
										<li ${status.index==0?'class=\"active\" ':'' }
											data-slide-to="${status.index}"
											data-target="#carousel-648895"></li>

									</c:forEach>
								</ol>
								<div class="carousel-inner">
									<c:forEach var="banner" items="${bannerList}"
										varStatus="status">
										<div class="item  ${status.index==0?'active':''}">
										<a href="<%=getServletContext().getContextPath()%>/front/restaurant/fe_restInfo.jsp?rest_no=${banner.rest_no}">
											<img
												src="<%=getServletContext().getContextPath()%>/banner/ImgShow?banner_no=${banner.banner_no}">
										</a>	
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
					</div>
					<div class="col-md-2 column">
						<div id="quick_search_form">
							<label for="exampleInputEmail1">快速搜尋</label></br>
							<form method="post" class="form-inline"
								action="<%=request.getContextPath()%>/rest/rest.do">
								<input type="hidden" name="action" value="getAllReserv">
								<div class="form-group">
									<label>日期</label></br>
									<%
										java.sql.Date today = new java.sql.Date(
																						System.currentTimeMillis());
																				java.sql.Date after7days = new java.sql.Date(
																						System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000);
									%>
									<input type="text" id="reserv_date" name="reserv_date"
										class="form-control" width="300px" value="<%=date_SQL%>"
										max="<%=after7days%>"></br>
								</div>
								<div class="form-group">
									<label >地點</label> <select
										class="form-control span2" name="rest_addr">
										<option value="">請選擇地點</option>
										<option value="台北市">台北市</option>
										<option value="新北市">新北市</option>
										<option value="桃園市">桃園市</option>
										<option value="新竹縣">新竹縣</option>
										<option value="新竹市">新竹市</option>
										<option value="台中市">台中市</option>
										<option value="台南市">台南市</option>
										<option value="高雄市">高雄市</option>
									</select>
								</div>
								<div class="form-group">
									<label for="exampleInputPassword1">訂位人數</label>
									 <select	class="span2 form-control" id="reserv_count" name="reserv_count">
										<option value=1>1</option>
									</select>
								</div>
								<div class="form-group">
								<label for="exampleInputPassword1"></label>
									<button type="submit" class="btn btn-default form-control">搜尋</button>
								</div>
							</form>
						</div>

						<div class="col-md-12 column ">
							<div class="tag">最新上架</div>
							<h2 class="visible-lg">
								<span class="h2"><span class="h3"> ${latestcopVO.cop_name } </span></span>
							</h2>
							<p>${latestcopVO.cop_content}</p>
							<p>
								<a class="btn" href="#">View details »</a>
							</p>
						</div>
					</div>
					<div class="col-md-7 column">
						<c:forEach var="ads" items="${adsList}">
							<div class="row clearfix">
								<div class="col-md-12 column  info_block">
									<a href="<%=getServletContext().getContextPath()%>/front/restaurant/fe_restInfo.jsp?rest_no=${ads.rest_no}">
									<img alt="140x140"
										src="<%=getServletContext().getContextPath()%>/ads/ImgShow?ads_no=${ads.ads_no}">
									</a>
								</div>
							</div>
						</c:forEach>

					</div>
					<div class="col-md-3 column">
						<script>
							$('#exampleModal').on(
									'show.bs.modal',
									function(event) {
										var button = $(event.relatedTarget) // Button that triggered the modal
										var recipient = button.data('whatever') // Extract info from data-* attributes
										// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
										// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
										var modal = $(this)
										modal.find('.modal-title').text(
												'New message to ' + recipient)
										modal.find('.modal-body input').val(
												recipient)
									})
						</script>
						<h3>
							最新消息 <span class="label label-warning">New</span>
						</h3>
						<div class="row clearfix sm_ad_block pre-scrollable">

							<div id="news">
								<c:forEach var="newsVO" items="${newsList}">
									<h6>${newsVO.news_time}</h6>
									<h5>
										<b>${newsVO.news_title}</b>
									</h5>

									<button type="button" class="btn btn-xs  btn-success"
										data-toggle="modal" data-target="#Modal_${newsVO.news_no }"
										data-whatever="@mdo">了解詳情</button>

									<div class="modal fade" id="Modal_${newsVO.news_no }"
										tabindex="-1" role="dialog"
										aria-labelledby="exampleModalLabel" aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<h6 class="modal-title" id="exampleModalLabel">${newsVO.news_time}</h6>
													<h4 class="modal-title" id="exampleModalLabel">
														<b>${newsVO.news_title}</b>
													</h4>
												</div>
												<div class="modal-body">
													<h4 class="modal-title" id="exampleModalLabel">${newsVO.news_cont}</h4>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														data-dismiss="modal">關閉</button>
												</div>
											</div>
										</div>
									</div>
									<hr>
								</c:forEach>
							</div>
						</div>
						<div class="row clearfix sm_ad_block ">

							<div class="col-md-12 column ">
								<div class="tag">熱賣餐券</div>
								<h3 class="caption">${hotcopVO.cop_name } </h3>
								<p>${hotcopVO.cop_content } </p>
								<p>
									<a class="btn" href="#">View details »</a>
								</p>
							</div>
						</div>
					</div>
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
