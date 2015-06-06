<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rest.model.*"%>
<%
	RestService restSvc = new RestService();
	List<RestVO> list = restSvc.getAll();
	Collections.reverse(list);					//改為降冪排列
	pageContext.setAttribute("list", list);
%>

<html>
<head>
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
							<h3 class="text-danger">餐廳資訊管理</h3>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
							<table class="table table-bordered" >
								<thead>
									<tr>
										<th width=6%>
											<div align="center">餐廳編號</div>
										</th>
										<th width=6%>
											<div align="center">營業編號</div>
										</th>
										<th width=6%>
											<div align="center">店名</div>
										</th>
										<th width=10%>電話</th>
										<th width=10%><div align="center">地址</div></th>
										<th width=9%><div align="center">信箱</div></th>
										<th width=9%><div align="center">加盟申請編號</div></th>
										<th width=15%><div align="center">網址</div></th>
										<th width=15%><div align="center">餐廳照片</div></th>
										<th width=7%><div align="center">上架狀態</div></th>
										<th width=9%><div align="center">操作</div></th>
									</tr>
								</thead>
								<tbody>
		<%@ include file="page1.file"%>
									<c:forEach var="restVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
										<tr ${restVO.rest_no==param.rest_no? 'class=\'danger\'':''}>
											<td><div align="center">${restVO.rest_no }</div></td>
											<td>
												<div align="center">${restVO.bus_no }</div>
											</td>
											<td>
												<div align="center">${restVO.rest_name }</div>
											</td>
											<td><div align="center">${restVO.rest_tel }</div></td>
											<td><div align="center">${restVO.rest_addr }</div></td>
											<td><div align="center">${restVO.rest_mail }</div></td>
											<td><div align="center">${restVO.affi_no }</div></td>
											<td><div align="center">${restVO.rest_web }</div></td>
												<td><img class="img-rounded" width=100%
							src="<%=request.getContextPath()%>/rest/ShowRestImg?rest_no=${restVO.rest_no}">
						</td>
											<td>
												<div align="center">
													<img
														src="<%=getServletContext().getContextPath()%>/back/img/${(restVO.rest_state=='1') ? 'ok':'not'}.png"
														width="24" height="24" alt="上架中">
												</div>
											</td>
											<td>
												<div align="center">
													<form id="update_state"
														action="<%=getServletContext().getContextPath()%>/rest/rest.do"
														METHOD="post">
														<button id="submit" type="submit"
															class="btn btn-lg ${(restVO.rest_state=='1') ? 'btn-danger':'btn-success'}"
															onClick="return confirm('確定要將【${restVO.rest_name}】${(restVO.rest_state=='1') ? '下架':'上架'}嗎？');">
															${(restVO.rest_state=='1') ? '下架':'上架'}</button>
														<input type="hidden" name="rest_state"
															value="${restVO.rest_state}"> <input
															type="hidden" name="rest_no" value="${restVO.rest_no }">
														<input type="hidden" name="action" value="update_State">
													</form>
												</div>
											</td>
										</tr>
									</c:forEach>


								</tbody>
							</table>
							<%@ include file="page2.file"%>
						</div>
					</div>
				</div>
				<div class="container" align=center>
					<div class="row clearfix">
						<div class="col-md-12 column">
						
						</div>
					</div>
				</div>

			</div>
			<!------------------以上內容--</div>----------------->

		</div>
	</div>
</body>
</html>
