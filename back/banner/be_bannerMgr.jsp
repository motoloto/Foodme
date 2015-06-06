<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.banner.model.*"%>

<%
	BannerService bannerSvc = new BannerService();
	List<BannerVO> list = bannerSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 訂位平台 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->
<%@ include file="/back/pageDesign/designSrc.jsp"%>
</head>

<body>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>
			<!------------------以下內容--------------------------->
			<div class="col-md-10  column">
				<div class="row clearfix block">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<h3 class="text-danger">橫幅廣告管理</h3>
							<a href="<%=request.getContextPath()%>/back/banner/addBanner.jsp"><button
									class="btn btn-success in-line">新增</button></a>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="5%">
											<div align="center">廣告編號</div>
										</th>
										<th width="5%">
											<div align="center">餐廳名稱</div>
										</th>
										<th width="10%">
											<div align="center">廣告標題</div>
										</th>
										<th width="20%">廣告內容</th>
										<th width="10%"><div align="center">廣告圖片</div></th>
										<th width="7%"><div align="center">張貼期限</div></th>
										<th width="3%"><div align="center">修改</div></th>
										<th width="3%"><div align="center">刪除</div></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="bannerVO" items="${list}">
										<tr
											${bannerVO.banner_no==param.banner_no? 'class=\'danger\'':''}>
											<td>${bannerVO.banner_no }</td>
											<td>${bannerVO.rest_no }</td>
											<td>${bannerVO.banner_title }</td>
											<td>${bannerVO.banner_cont }</td>
											<td><img width=100% class="img-rounded"
												src="<%=request.getContextPath()%>/banner/ImgShow?banner_no=${bannerVO.banner_no }"></td>
											<td>${bannerVO.banner_dl}</td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/banner/banner.do">
													<input class="btn btn-warning" type="submit" value="修改">
													<input type="hidden" name="banner_no"
														value="${bannerVO.banner_no }"> <input
														type="hidden" name="action" value="getOne_For_Update">
												</FORM>
											</td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/banner/banner.do">
													<input class="btn btn-danger" type="submit" value="刪除">
													<input type="hidden" name="banner_no"
														value="${bannerVO.banner_no }"> <input
														type="hidden" name="action" value="delete">
												</FORM>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="container" align=center>
					<div class="row clearfix">
						<div class="col-md-12 column">
							
						</div>
					</div>
				</div>
				<!------------------以上內容--------------------------->
			</div>
		</div>
		<div id="bottom_space"></div>
		<div id="bottom_space"></div>
	</div>
</body>
</html>
