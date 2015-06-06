<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dish.model.*"%>
<%
DishVO dishVO = (DishVO) request.getAttribute("dishVO");
session.setAttribute("rest_no", 7002);	//之後改成從session 取得rest_no
%>
 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.css" />
<title>料理更新</title>

  <meta charset="utf-8">
  <title>FOOD me 線上餐廳訂位平台</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	<link rel="stylesheet" type="text/css" href="js/calendar.css">
	<script language="JavaScript" src="<%=getServletContext().getContextPath()%>/front/js/calendarcode.js"></script>
	<script>$("#dish_state option[value=<%=(dishVO.getDish_state())%>]").attr("selected",true);</script>
	<link href="<%=getServletContext().getContextPath()%>/front/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=getServletContext().getContextPath()%>/front/css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=getServletContext().getContextPath()%>/front/img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=getServletContext().getContextPath()%>/front/img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=getServletContext().getContextPath()%>/front/img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="<%=getServletContext().getContextPath()%>/front/img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="<%=getServletContext().getContextPath()%>/front/img/favicon.png">
  
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/front/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/front/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/front/js/scripts.js"></script>

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
<%-- 			<%@ include file="fe_header.jsp"%> --%>
        <!--HEADER的結束-->    			
			
<!---------- 以下為網頁內容------------->    

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dish/dish.do" enctype="multipart/form-data" name="form1">
<table align="center"><th><a href="#" data-role="fieldcontain" data-inline="true" data-theme="b" mini="true"><h2>料理更新</h2></a></th></table>
<table align="center" border="0" padding="10">


<%-- <tr><td></font></td><td>料理編號:</td><td><input type="TEXT" name="dish_no" size="45"		value="<%= (dishVO==null)? "" : dishVO.getDish_no()%>" /></td></tr> --%>

<tr><td></font></td><td>料理編號:</td><td><%=dishVO.getDish_no()%></td></tr>

<%-- <tr><td></font></td><td>餐廳編號:</td><td><input type="TEXT" name="reset_no" size="45"	value="<%= (dishVO==null)? "" : dishVO.getRest_no()%>" /></td></tr> --%>
<tr><td></font></td><td>料理名稱:</td><td><input type="TEXT" name="dish_name" size="45"	value="<%= (dishVO==null)? "" : dishVO.getDish_name()%>" /></td></tr>
<tr><td></font></td><td>料理描述:</td><td><input type="TEXT" name="dish_cont" size="45"	value="<%= (dishVO==null)? "" : dishVO.getDish_cont()%>" /></td></tr>

<td></font></td><td>上架狀態:</td>
<td><select id="dish_state" name="dish_state">							
	
	<option value="0" <%=(dishVO.getDish_state().equals("0"))? "selected": ""%>>下架</option>	
	<option value="1" <%=(dishVO.getDish_state().equals("1"))? "selected": ""%>>上架</option>	 

</select></td></tr>
<tr><td></td><td>料理價格:</td><td><input type="TEXT" name="dish_price" size="45" value="<%= (dishVO==null)? "" : dishVO.getDish_price()%>" /></td></tr>

<tr><td></td><td>料理相片:</td><td><img class="img-rounded" width="200px"
							src="<%=request.getContextPath()%>/dish/ShowDishImg?dish_no=${dishVO.dish_no}">
						</td><td><input type="file" name="dish_pic" value="重新上傳相片"/></td>
						</tr>

<!--  <tr><td>*</td><td align="top">餐廳介紹</td><td><textarea name="rest_intro" cols="50" rows="10"> </textarea></td></tr>
-->

<tr><td></td><input type="hidden" name="action" value="update">
<!-- <tr><td></td><td></td><td><input data-role="button" type="submit" value="送出新增" data-theme="b"/></td></tr> -->
<!-- <tr><td></td><td></td><td><a href="#" data-role="button" data-icon="" data-inline="false" data-mini="true" data-theme="b">新增</a></td></tr>
-->



<input type="hidden" name="dish_no" value="${dishVO.dish_no}">
<input type="hidden" name="dish_name" value="${dishVO.dish_name}">
<input type="hidden" name="dish_cont" value="${dishVO.dish_cont}">
<input type="hidden" name="dish_state" value="${dishVO.dish_state}">
<input type="hidden" name="dish_price" value="${dishVO.dish_price}">
<tr><td></td><td></td><td><input type="submit" value="送出更新"/></td></tr>
</FORM>

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
