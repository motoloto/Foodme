<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="java.text.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="com.rest.model.*"%>
<%@ page import="com.act.model.*"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!int identity = 0; //用來分辨該從資料庫取出訂位數還是從request物件取出訂位數%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	
%>

<jsp:useBean id="actListOfRest" scope="session" type="java.util.List" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>


 <style type="text/css"> 
   
.btn_delet {
	font-family: Arial;
	
	
	
	text-decoration: none;
	
}

.btn_update {
	font-family: Arial;
	
	
	
	text-decoration: none;
	
}
 </style> 
 
 <script>
 $(document).ready(function(){
		$('.btn_delete').click(function() {  
			confirm("是否確定刪除此活動");
			});
	});
 </script>
 
</head>
<body>
	
						
						
<p></p>

	<table width="845" class="table table-bordered"
		Style="vertical-align: middle">
		<tr >
			<th bgcolor="#F0F0F0" width="74" height="46"><div align="center">
					<strong>活動編號</strong>
				</div></th>
			<th bgcolor="#F0F0F0" width="74"><div align="center">
					<strong>活動名稱</strong>
				</div></th>
			<th bgcolor="#F0F0F0" width="300"><div align="center">
					<strong>活動內容</strong>
				</div></th>
			<th bgcolor="#F0F0F0" width="80"><div align="center">
					<strong>活動時間</strong>
				</div></th>
			<th bgcolor="#F0F0F0" width="60"><div align="center">
					<strong>活動狀態</strong>
				</div></th>
			
			<th bgcolor="#F0F0F0" width="40"><div align="center">
					<strong>修改</strong>
				</div></th>
			<th bgcolor="#F0F0F0" width="40"><div align="center">
					<strong>刪除</strong>
				</div></th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="actVO" items="${actListOfRest}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"
			varStatus="s">
			<tr ${(actVO.act_no==param.act_no) ? 'bgcolor=#FFB7DD':''}>
				<td><div align="center">${actVO.act_no}</div></td>
				<td><div align="center">${actVO.act_name}</div></td>
				<td><div align="center">${actVO.act_cont}</div></td>
				<td><div align="center">${actVO.act_time}</div>
				<td><div align="center">
					<c:if test="${actVO.act_state.equals('1')}">
	                      <img src= <%=request.getContextPath()%>/store/img/ok.png>
                    </c:if>
                    <c:if test="${actVO.act_state.equals('0')}">
	                      <img src= <%=request.getContextPath()%>/store/img/not.png>
                    </c:if>

					</div></td>
				<td><div align="center">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/act/act.do">
							<input class="btn_update" type="submit" value="修改"> 
							<input type="hidden" name="act_no" value="${actVO.act_no}"> 
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</div></td>
				<td><div align="center">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/act/act.do">
							<input  class="btn_delete" type="submit" value="刪除"> 
							<input type="hidden" name="act_no" value="${actVO.act_no}"> 
							<input type="hidden" name="rest_no" value="${restVO.rest_no}"> 
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
							<input type="hidden" name="action" value="delete">
						</FORM>
					</div></td>

			</tr>
		</c:forEach>
	</table>
<%@ include file="page2.file"%>
					
						
						

						
						<!--執行disptime();-->
						<script>
							disptime();
						</script>
					</div>

				</div>

			</div>
		</div>

	</div>
</body>
</html>