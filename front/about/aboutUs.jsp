<%@page import="com.news.model.NewsService"%>
<%@page import="com.banner.model.BannerService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banner.model.*"%>
<%@ page import="com.ads.model.*"%>
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
%>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file" %>
<% response.setHeader("Cache-Control","no-store");%><!--取消瀏覽器快取，最好每個jsp檔都加-->
<% response.setHeader("Pragma","no-cache");%>
<% response.setDateHeader("Expires",0);%>
</head>
<style>
#text
{
	font-size:14;   
}
#header
{
	color:#FF9D37;
	text-align:center;
}
#footer
{
	background-color:#CCCCCC;
	padding:30px;
	margin-bottom:20px;
	margin-top:20px;
}
.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}
</style>
<body>
	<div class="container index_content">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

				<div class="row clearfix" id="index_content">
					<div class="row clearfix">
						<div class="col-md-12 column" align="center">						
							<h2 id="header">
								關於我們
							</h2>													
						</div>
						<HR width="80%" size="10">
					</div>
					<div class="col-md-2 column">
						

						
					</div>
					<div class="col-md-8 column">
					<p id="text">	
					身在美食王國的台灣，各式的傳統料理、各國美食以及精緻的飲食文化，都深深的影響著每個人。 懂得吃，哪裡吃，如何吃，已成為全民追求的生活品質，更是一種流行的趨勢。
					<br><br>
					FOOD me 線上餐廳訂位系統對消費者提供了一個快捷、便利、24小時不打烊的網路訂位平台，無論是三五好友聚會、家人聚餐、重要節日慶祝等，都可讓消費者在最快、
					最有效率的方式下，輕鬆解決人擠人、現場排隊或打電話一位難求的困擾。
					<br><br>
					而對於餐廳經營者，FOOD me 線上餐廳訂位系統創造了新的訂位與行銷方式，讓各餐廳在 E化的訂位系統幫助下，達成更有效率的餐廳管理。
					<br><br>
					做為消費者和餐廳間的溝通橋樑， FOOD me 人性的個人化選項，也讓餐廳經營者瞭解了消費者的想法，而有了改善的方向。
					<br><br>
					在不斷進步、追求更高生活水準的理念下，FOOD me 線上餐廳訂位系統將持續努力與全國各地的美食餐廳合作，將各地的特色料理，更迅捷的提供給大家。
					也希望如果有任何值得推荐的餐廳，一定要告訴我們，讓美食能分享給更多的消費朋友。
					</p>
					<hr>
					<p align="center">
						<a href="<%=getServletContext().getContextPath()%>/front/indexXXX.jsp" class="btn btn-danger" type="button">回到FoodMe首頁</a>
					</p>
						
					</div>
					
					
					<div class="col-md-2 column">
					
					</div>
			   </div>
				
				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column nopadding" align="center">

						<div id="footer">					     
						      <span>106 台北市大安區敦化南路二段216號14樓（仁愛圓環，國泰世華大樓）</span>&nbsp;&nbsp;|&nbsp;&nbsp;<span itemprop="telephone">服務專線：02-2377-8323</span>&nbsp;&nbsp;|&nbsp;&nbsp;服務時間：週一至週日 10:00-19:00
					      <div><span>FOOD me 線上餐廳訂位系統</span>© 2014 FOODme.com All Rights Reserved.</div>
					      <div><a href="#" style="padding-right: 10px">使用者條款</a>|&nbsp;&nbsp;<a href="#">隱私權政策</a></div>
					   </div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
