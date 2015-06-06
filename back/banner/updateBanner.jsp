<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banner.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rest.model.*"%>
<%
	BannerVO bannerVO = (BannerVO) request.getAttribute("bannerVO");
	session.setAttribute("bannerVO", bannerVO);
	RestService restSrv=new RestService();
	List <RestVO> restList=restSrv.getAll();
	pageContext.setAttribute("restList", restList);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
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


			<!------------------以下內容---col-md-10------------------------>
			<div class="col-md-10  column">
				<div class="row clearfix block">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<h3 class="text-danger">更新橫幅廣告</h3>
							<a
								href="<%=request.getContextPath()%>/back/banner/be_bannerMgr.jsp">
								<h5 class="text-success">─回到橫幅廣告管理</h5>
							</a>
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
					<div class="col-md-12 column">
						<div align="center">
							<FORM METHOD="POST"
								ACTION="<%=request.getContextPath()%>/banner/banner.do"
								enctype="multipart/form-data">
								<table class="table table-bordered">
									<tr>
										<td>廣告編號:</td>
										<td><input name="banner_no" size="45" readonly
											value="<%=(bannerVO != null) ? bannerVO.getBanner_no() : ""%>" /></td>
										<td>刊登期限:</td>
										<td>
											<%
												java.sql.Date date_SQL = new java.sql.Date(
														System.currentTimeMillis());
											%><input type="date" name="banner_dl" class="form-control"
											size=20%
											value="<%=(bannerVO == null) ? date_SQL : bannerVO.getBanner_dl()%>">
										</td>
									</tr>
									<tr>
										<td>餐廳編號:</td>
										<td>
										<select autofocus name="rest_no">
												<c:forEach var="restVO" items="${restList }">
													<option value="${restVO.rest_no }"
														${restVO.rest_no==bannerVO.rest_no? " selected=\"selected\"":""}>${restVO.rest_name}</option>
												</c:forEach>
										</select>
<!-- 										<input type="TEXT" name="rest_no" size="45" -->
<!-- 											class="form-control" -->
<%-- 											value="<%=(bannerVO != null && bannerVO.getRest_no() != 0) ? bannerVO --%>
<%-- 					.getRest_no() : ""%>" /> --%>
					</td>
										<td>餐廳列表</td> ,
										<td></td>
									</tr>
									<tr>
										<td>橫幅廣告標題:</td>
										<td colspan="3"><input type="TEXT" name="banner_title"
											size="45" class="form-control"
											value="<%=(bannerVO != null) ? bannerVO.getBanner_title() : ""%>" /></td>
									</tr>
									<tr>
										<td>橫幅廣告內容:</td>
										<td colspan="3"><textarea rows="6" name="banner_cont"
												class="form-control">${bannerVO.banner_cont }</textarea></td>
									</tr>
									<tr>
									</tr>
									<tr>
										<td>廣告圖片:</td>
										<td colspan="3"><input class="btn btn-default"
											type="file" name="banner_pic"> <br> <img
											class="img-rounded" width=100%
											src="<%=request.getContextPath()%>/banner/ImgShow?banner_no=${bannerVO.banner_no }"></td>
									</tr>
									<tr>

										<td align="right"><input type="hidden" name="action"
											value="update">
											<button type="submit" class="btn btn-default btn-lg ">取
												消</button></td>
										<td><input type="submit" class="btn btn-primary btn-lg "
											value="確認修改"></td>
									</tr>


								</table>
							</FORM>
						</div>
					</div>
				</div>
				<!------------------以上內容--</div>----------------->

			</div>
		</div>
</body>
</html>
