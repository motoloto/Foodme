<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.affi.model.*"%>
<%
AffiVO affiVO = (AffiVO) request.getAttribute("affiVO");
%>
 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.css" />
<title>線上加盟申請 - addAffi_feedme.jsp</title>

  <meta charset="utf-8">
  <title>FOOD me 線上餐廳訂位平台</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <%@ include file="/front/pageDesign/designSrc.file" %>
<%-- 錯誤表列 --%>
<div align="center">
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="list-style-type :none">${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
</div>
</head>

<body>
<div class="container">
	<div class="row clearfix page_container">
    	<div class="col-md-12 column">
        <!--  HEADER的開始-->
			<%@ include file="/front/pageDesign/fe_header.jsp"%>
        <!--HEADER的結束-->    			
			
<!---------- 以下為網頁內容------------->    
<table align="center">
	<tr><td><font color=red><h1>加盟資料已送出，敬請耐心等候審核結果。</h1></font></td></tr>	
<div>

















</div>	
</table>

<!---------- 以上為網頁內容----------->    			
			<div class="row clearfix">
				<div class="col-md-12 column">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
