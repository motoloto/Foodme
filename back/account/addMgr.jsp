<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.manager.model.*"%>
<%! Integer updateResult=0;
	ManagerVO mgrVO=null;%>
<%
      mgrVO = (ManagerVO) request.getAttribute("mgrvo");
	  
%>

<% 		
	//取得進入該頁面時的狀況，以決定如何顯示彈出視窗
	if((String)request.getAttribute("updateResult")==null)
	{
		updateResult=0;	
	}	
	else if(((String)request.getAttribute("updateResult")).equals("管理員未新增"))
	{
		updateResult=1;
	}	
	else if(((String)request.getAttribute("updateResult")).equals("管理員已新增"))
	{
		updateResult=2;
	}
%>
<script>
function checkUpdate()
{
	//檢查輸入密碼是否相同
	var psw=document.getElementsByName("mgr_pwd")[0].value;
	var pswCheck=document.getElementsByName("mgr_pwdCheck")[0].value;
	
	if(psw==pswCheck)
	{
		
		if(confirm("確認新增管理員？"))
		{
			return true;
		}	
		else
		{
			alert("管理員未新增");
			return false;
		}
	}	
	else
	{
		alert("密碼與確認密碼有誤");
		return false;
	}	
		
}

function showUpdateResult()//onload完後根據資料修改狀況決定彈出視窗
{
	var updateResult=document.getElementById("updateResult").value;
	if(updateResult==1)
	{
		alert("管理員未新增");
	}
	else if(updateResult==2)
	{
		alert("管理員已新增");
	}
	else
	{
		
	}	
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 後端管理系統</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- 引入CSS 和 JS設定檔 -->
<%@ include file="/back/pageDesign/designSrc.jsp"%>
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
							<h3 class="text-danger">新增管理員頁面</h3>
							<a href="<%=request.getContextPath()%>/back/account/be_accountMgr2.jsp"><h5
									class="text-success">─回到帳號管理首頁</h5></a>
						</div>
					</div>
					<div class="col-md-12 column">
						<div align="center">
						    <c:if test="${not empty errorMsgs}">
								<font color='red'>請修正以下錯誤:
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</ul>
								</font>
							</c:if>
							<FORM METHOD="POST"
								ACTION="<%=request.getContextPath()%>/manager/manager.do"	>
								<table class="table table-bordered">

									<tr>
										<td>帳號:</td>
										<td>
											<input type="text" name="mgr_account" size="20"
											value="<%=(mgrVO==null)?"":mgrVO.getMgr_account()%>">
										</td>
									</tr>
									<tr>
										<td>密碼:</td>
										<td><input type="password" name="mgr_pwd" size="20"
										value="<%=(mgrVO==null)?"":mgrVO.getMgr_pwd()%>"/></td>
									</tr>
									<tr>
										<td>確認密碼:</td>
										<td><input type="password" name="mgr_pwdCheck" size="20"
										value="<%=(mgrVO==null)?"":mgrVO.getMgr_pwd()%>"/></td>
									</tr>
									<tr>
										<td>姓名:</td>
										<td><input type="text" name="mgr_name" size="20"
										value="<%=(mgrVO==null)?"":mgrVO.getMgr_name()%>"/></td>
									</tr>
									<tr>
										<td>Email:</td>
										<td><input type="TEXT" name="mgr_mail" size="45" 
										value="<%=(mgrVO==null)?"":mgrVO.getMgr_mail()%>"/></td>
									</tr>
									<tr>
										<td>電話：</td>
										<td><input type="TEXT" name="mgr_phone" size="45" 
										value="<%=(mgrVO==null)?"":mgrVO.getMgr_phone()%>"/></td>					
									</tr>
									<tr>
									<td>
									</td>
									<td align="center"><input type="hidden" name="action"
											value="insert"> <input type="submit" value="送出新增" onclick="return checkUpdate()"></td>
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
