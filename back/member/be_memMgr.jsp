<%@page import="com.mem.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%
	MemService memSrv = new MemService();
	List<MemVO> list = memSrv.getAll();
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
							<h3 class="text-danger">會員資料查詢</h3>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="5%">
											<div align="center">會員編號</div>
										</th>
										<th width="11%">
											<div align="center">帳號</div>
										</th>
										<th width="10%">
											<div align="center">密碼</div>
										</th>
										<th width="5%">
											<div align="center">姓名</div>
										</th>
										<th width="10%">
											<div align="center">暱稱</div>
										</th>
										<th width="1%">
											<div align="center">性別</div>
										</th>
										<th width="10%">
											<div align="center">生日</div>
										</th>
										<th width="10%">
											<div align="center">電話</div>
										</th>
										<th width="15%">
											<div align="center">信箱	</div>
										</th>
										<th width="15%">
											<div align="center">地址</div>
										</th>


									</tr>
								</thead>  
								<tbody>
									<%@ include file="pages/page1.file" %> 
									<c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										
										<tr>
											<td><div align="center">${memVO.mem_no}</div></td>
											<td>
												<div align="center">${memVO.mem_account}<div>
											</td>
											<td>
												<div align="center">${memVO.mem_pwd}</div>
											</td>
											<td><div align="center">${memVO.mem_name}</div></td>
											<td><div align="center">${memVO.mem_nickname}</div></td>
											<td><div align="center">${memVO.mem_sex==1?'男':'女'}</div></td>
											<td><div align="center">${memVO.mem_birthdate}</div></td>
											<td><div align="center">${memVO.mem_phone}</div></td>
											<td><div align="center">${memVO.mem_mail}</div></td>
											<td><div align="center">${memVO.mem_adrs}</div></td>
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
