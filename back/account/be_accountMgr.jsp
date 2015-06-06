<%@page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.manager.model.*" %>
<%! Integer updateResult=0;
%>
<%
	ManagerService rs = new ManagerService();
	List<ManagerVO> list = rs.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->

<%@ include file="/back/pageDesign/designSrc.jsp"%>
<% 		
	//取得進入該頁面時的狀況，以決定如何顯示彈出視窗
	if((String)request.getAttribute("updateResult")==null)
	{
		updateResult=0;	
	}	
	else if(((String)request.getAttribute("updateResult")).equals("管理員資料未刪除"))
	{
		updateResult=1;
	}	
	else if(((String)request.getAttribute("updateResult")).equals("管理員資料已刪除"))
	{
		updateResult=2;
	}
	else if(((String)request.getAttribute("updateResult")).equals("管理員admin不可刪除"))
	{
		updateResult=3;
	}
	else if(((String)request.getAttribute("updateResult")).equals("管理員資料未修改"))
	{
		updateResult=4;
	}	
	else if(((String)request.getAttribute("updateResult")).equals("管理員資料已修改"))
	{
		updateResult=5;
	}
	else if(((String)request.getAttribute("updateResult")).equals("管理員已新增"))
	{
		updateResult=6;
	}
	else if(((String)request.getAttribute("updateResult")).equals("管理員未新增"))
	{
		updateResult=7;
	}
%>
<script>
function checkDelete()
{		
	if(confirm("確認刪除管理員帳號？"))
	{
		return true;
	}	
	else
	{
		alert("管理員帳號未刪除");
		return false;
	}
		
}

function showUpdateResult()//onload完後根據資料修改狀況決定彈出視窗
{
	var updateResult=document.getElementById("updateResult").value;
	if(updateResult==1)
	{
		alert("管理員帳號未刪除");
	}
	else if(updateResult==2)
	{
		alert("管理員帳號已刪除");
	}
	else if(updateResult==3)
	{
		alert("管理員admin不可刪除");
	}	
	else if(updateResult==4)
	{
		alert("管理員資料未修改");
	}
	else if(updateResult==5)
	{
		alert("管理員資料已修改");
	}
	else if(updateResult==6)
	{
		alert("管理員已新增");
	}
	else if(updateResult==7)
	{
		alert("管理員未新增");
	}
}
</script>
</head>

<body onload="showUpdateResult()">
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>

			<!------------------以下內容---col-md-10------------------------>
			<div class="col-md-10  column">
				<div class="row clearfix block">
					<div class="row clearfix">
						<div class="col-md-12 column">
						<input type="hidden" id="updateResult" value="<%=updateResult%>">
							<h3 class="text-danger">管理員帳號管理</h3>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="7%">
											<div align="center">管理員編號</div>
										</th>
										<th width="11%">
											<div align="center">管理員帳號</div>
										</th>
										<th width="10%">
											<div align="center">管理員姓名</div>
										</th>
										<th width="10%">
											<div align="center">管理員信箱</div>
										</th>
										<th width="10%">
											<div align="center">管理員電話</div>
										</th>									

									</tr>
								</thead>  
								<tbody>
									<%@ include file="pages/page1.file" %> 
									<c:forEach var="ManagerVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">								
										<tr>
											<td><div align="center" style="height:50px;line-height:50px;">${ManagerVO.mgr_no}</div></td>
											<td>
												<div align="center" style="height:50px;line-height:50px;">${ManagerVO.mgr_account}<div>
											</td>											
											<td><div align="center" style="height:50px;line-height:50px;">${ManagerVO.mgr_name}</div></td>
											<td><div align="center" style="height:50px;line-height:50px;">${ManagerVO.mgr_mail}</div></td>
											<td><div align="center" style="height:50px;line-height:50px;">${ManagerVO.mgr_phone}</div></td>
											<td width="5%" align="center">
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/back/account/updateMgr.jsp">
													<input class="btn btn-lg btn-warning" type="submit"
														value="修改"> <input type="hidden" name="mgr_no"
														value="${ManagerVO.mgr_no}"> <input type="hidden"
														name="action" value="update">
												</FORM>
											</td>
											<td width="5%" align="center">
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/manager/manager.do">
													<input class="btn btn-lg  btn-danger" type="submit"
														value="刪除" onclick="return checkDelete()"> <input type="hidden" name="mgr_no"
														value="${ManagerVO.mgr_no}"> <input type="hidden"
														name="action" value="delete">
												</FORM>	
											</td>											
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<a href="<%=request.getContextPath()%>/back/account/addMgr.jsp"><button
									class="btn btn-success in-line">新增管理員</button></a>
						</div>
					</div>
				</div>
				<%@ include file="pages/page2.file" %>
				

			</div>
		</div>
	</div>
</body>
</html>
