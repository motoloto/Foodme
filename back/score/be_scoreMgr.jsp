<%@page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%
	RestService restSrv = new RestService();
	List<RestVO> list = restSrv.getAllScore();
	pageContext.setAttribute("list", list);
%>>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->

<%@ include file="/back/pageDesign/designSrc.jsp"%>>
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
							<h3 class="text-danger">評價資料查詢</h3>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="5%">
											<div align="center">餐廳編號</div>
										</th>
										<th width="11%">
											<div align="center">店名</div>
										</th>
										<th width="10%">
											<div align="center">總平均</div>
										</th>
										<th width="10%">
											<div align="center">價格平均分數</div>
										</th>
										<th width="10%">
											<div align="center">料理平均分數</div>
										</th>
										<th width="10%">
											<div align="center">衛生平均分數</div>
										</th>
										<th width="10%">
											<div align="center">環境平均分數</div>
										</th>
										<th width="10%">
											<div align="center">服務平均分數</div>
										</th>
										<th width="9%">
											<div align="center">上架狀態</div>
										</th>

									</tr>
								</thead>  
								<tbody>
									<%@ include file="pages/page1.file" %> 
									<c:forEach var="restVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<%
											RestVO restVO = (RestVO) pageContext.getAttribute("restVO");
												double av_scor_pri = restVO.getScor_pri()/ restVO.getScor_pritms();
												double av_scor_hea = restVO.getScor_hea()/ restVO.getScor_heatms();
												double av_scor_cook = restVO.getScor_cook()/ restVO.getScor_cooktms();
												double av_scor_envisco = restVO.getScor_envisco()/ restVO.getScor_envtms();
												double av_scor_serv = restVO.getScor_serv()/ restVO.getScor_servtms();
												double avg = (av_scor_pri + av_scor_hea + av_scor_cook+ av_scor_envisco + av_scor_serv) / 5;
												pageContext.setAttribute("avg", avg);
										%>
										<tr>
											<td><div align="center">${restVO.rest_no}</div></td>
											<td>
												<div align="center">${restVO.rest_name}<div>
											</td>
											<td>
												<div align="center">${avg}</div>
											</td>
											<td><div align="center">${restVO.scor_pri /restVO.scor_pritms}</div></td>
											<td><div align="center">${restVO.scor_cook/restVO.scor_cooktms}</div></td>
											<td><div align="center">${restVO.scor_hea/restVO.scor_heatms}</div></td>
											<td><div align="center">${restVO.scor_envisco/restVO.scor_envtms}</div></td>
											<td><div align="center">${restVO.scor_serv/restVO.scor_servtms}</div></td>
											<td><div align="center">
													<img
														src="<%=getServletContext().getContextPath()%>/back/img/ok.png"
														width="24" height="24" alt="上架中">
												</div></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<%@ include file="pages/page2.file" %>
				

			</div>
		</div>
	</div>
</body>
</html>
