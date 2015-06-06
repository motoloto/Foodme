<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.odr.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	OdrService odrSvc = new OdrService();
	List<OdrVO> list = odrSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="copSvc" scope="page" class="com.cop.model.CopService" />
<html>
<head>
<title>所有訂單資料 - listAllEmp.jsp</title>


	<link href="<%=getServletContext().getContextPath()%>/back/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=getServletContext().getContextPath()%>/back/css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="img/favicon.png">
  
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/scripts.js"></script>
</head>
<body bgcolor='white'>



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


<table  class="table table-bordered"   cellspacing="0">

    <tr border="1" bgcolor="#F0F0F0">
      <td width="7%" height="50" align="center" valign="middle">訂單編號</td>
      <td width="10%" align="center" valign="middle">餐劵名稱</td>
      <td width="6%" align="center" valign="middle">付款人姓名</td>
      <td width="7%" align="center" valign="middle">電子信箱</td>
      <td width="7%" align="center" valign="middle">連絡電話</td>
      <td width="7%" align="center" valign="middle">購買數量</td>
      <td width="7%" align="center" valign="middle">購買金額</td>
      <td width="7%" align="center" valign="middle">結帳狀態</td>
      <td width="7%" align="center" valign="middle">結帳時間</td>
      <td width="7%" align="center" valign="middle">訂單時間</td>
      <td width="7%" align="center" valign="middle">確認付款</td>
    </tr>

	
	<%@ include file="page1.file" %> 
	<c:forEach var="odrVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="s">
		<tr align='center' valign='middle' ${(odrVO.odr_no==param.odr_no) ? 'bgcolor=#CCCCFF':''}>
		
	  <td width="7%" height="50" align="center" valign="middle">${odrVO.odr_no}</td>
	  
      <td width="10%" align="center" valign="middle">
             <c:forEach var="copVO" items="${copSvc.all}">
                    <c:if test="${odrVO.cop_no==copVO.cop_no}">
	                   ${copVO.cop_name}
                    </c:if>
                </c:forEach>
      </td>
      <td width="6%" align="center" valign="middle">${odrVO.odr_payname}</td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_mail}</td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_phone}</td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_buyamt}</td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_toprc}</td>
      <td width="7%" align="center" valign="middle">
              <c:if test="${odrVO.odr_state.equals('1')}">
	                  <img src= "<%=request.getContextPath()%>/back/img/ok.png">
              </c:if>
              <c:if test="${odrVO.odr_state.equals('0')}">
	                  <img src= "<%=request.getContextPath()%>/back/img/not.png">
              </c:if>
      </td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_paytime}</td>
      <td width="7%" align="center" valign="middle">${odrVO.odr_time}</td>
		
			<td  width="7%">
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/odr/odr.do">
			     <input type="submit" class="btn btn-info" value="確認付款"> 
          	     <input type="hidden" name="cop_no" value="${odrVO.cop_no}">
			     <input type="hidden" name="odr_no" value="${odrVO.odr_no}">
			     <input type="hidden" name="odr_payname" value="${odrVO.odr_payname}">
			     <input type="hidden" name="odr_phone" value="${odrVO.odr_phone}">
			     <input type="hidden" name="odr_mail" value="${odrVO.odr_mail}">
			     
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="checkForPayed"></FORM>
			</td>
			
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b> --%>
</body>
</html>
