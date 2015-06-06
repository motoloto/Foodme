<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ads.model.*"%>
<%@ page import="com.rest.model.*"%>
<%
	AdsService adsSvc = new AdsService();
	List<AdsVO> list = adsSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%
	RestService restSvc = new RestService();
	List<RestVO> restList = restSvc.getAll();
	pageContext.setAttribute("restList", restList);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bootstrap 3, from LayoutIt!</title>
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
							<h3 class="text-danger">小型廣告管理</h3>
							<a href="<%=request.getContextPath()%>/back/ads/addAds.jsp"><button
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
										<th width="10%">
											<div align="center">餐廳名稱</div>
										</th>
										<th width="10%">
											<div align="center">廣告標題</div>
										</th>
										<th width="10%"><div align="center">廣告圖片</div></th>
										<th width="6%"><div align="center">張貼期限</div></th>
										<th width="5%"><div align="center">修改</div></th>
										<th width="5%"><div align="center">刪除</div></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="adsVO" items="${list}">
										<tr  ${adsVO.ads_no==param.ads_no? 'class=\'danger\'':''}>
											<td>${adsVO.ads_no }</td>
											<td><c:forEach var="restVO" items="${restList}">
													<c:if test="${adsVO.rest_no==restVO.rest_no}">
	                								   【${restVO.rest_name} 】
               									     </c:if>
												</c:forEach></td>
											<td>${adsVO.ads_title }</td>
											<td height="150px"><img class="img-rounded" width="100%"
												height=100%
												src="<%=request.getContextPath()%>/ads/ImgShow?ads_no=${adsVO.ads_no }">

											</td>
											<td>${adsVO.ads_dl}</td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/ads/ads.do">
													<input class="btn btn-lg btn-warning" type="submit"
														value="修改"> <input type="hidden" name="ads_no"
														value="${adsVO.ads_no}"> <input type="hidden"
														name="action" value="getOne_For_Update">
												</FORM>
											</td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/ads/ads.do">
													<input class="btn btn-lg  btn-danger" type="submit"
														value="刪除"> <input type="hidden" name="ads_no"
														value="${adsVO.ads_no}"> <input type="hidden"
														name="action" value="delete">
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
