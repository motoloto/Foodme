<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.news.model.*"%>
<%
	NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
	session.setAttribute("newsVO", newsVO);
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
							<h3 class="text-danger">更新最新消息</h3>
							<a href="<%=request.getContextPath()%>/back/news/be_newsMgr.jsp">
								<h5 class="text-success">─回到最新消息管理</h5>
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
								ACTION="<%=request.getContextPath()%>/news/news.do"
								enctype="multipart/form-data">
								<table class="table table-bordered">
									<tr>
										<td>消息編號:</td>
										<td><input name="news_no" size="45" readonly
											value="<%=(newsVO != null) ? newsVO.getNews_no() : ""%>" /></td>
										<td>發布期限:</td>
										<td>
											<%
												java.sql.Date date_SQL = new java.sql.Date(
														System.currentTimeMillis());
											%><input type="date" name="news_time" class="form-control"
											size=20%
											value="<%=(newsVO == null) ? date_SQL : newsVO.getNews_time()%>">
										</td>
									</tr>

									<tr>
										<td>最新消息標題</td>
										<td colspan="3"><input type="TEXT" name="news_title"
											size="45" class="form-control"
											value="<%=(newsVO != null) ? newsVO.getNews_title() : ""%>" /></td>
									</tr>
										<tr>
										<td>消息內容</td>
										<td colspan="3"><textarea rows="6" name="news_cont"
												class="form-control">${newsVO.getNews_cont() }</textarea></td>
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
