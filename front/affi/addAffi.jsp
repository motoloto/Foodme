<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.affi.model.*"%>
<%
AffiVO affiVO = (AffiVO) request.getAttribute("affiVO");
%>
 <%@ include file="/front/pageDesign/designSrc.file" %>
 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.1/jquery.mobile-1.2.1.min.css" />
<title>線上加盟申請 - addAffi_feedme.jsp</title>

  <meta charset="utf-8">
  <title>FOOD me 線上餐廳訂位平台</title>

</div>
<script>
$(document).ready(function() {
	$("#btn").click(function() {
		$("#bus_no").val("32156469");
		$("#rest_name").val("海霸王");
		$("#rest_addr").val("新北市板橋區府中路1段1號");
		$("#rest_tel").val("02-2960-3878");
		$("#rest_mobil").val("0925-238467");
		$("#rest_mail").val("d..aily.wu@gmail.com");
		$("#rest_web").val("http://www.hpw.com.tw/ec99/ushop1072");
		$("#rest_intro").val("海霸王餐廳於1975年在高雄成立以來，秉持「清」、「鮮」、「份量足」之原則，強調「色、香、味、形」俱備，用美味與每位顧客搏感情，且不定期到產地嚴格挑選新鮮、美味食材，研發出季節創意料理，以期滿足顧客食的需求。");
	});
});
</script>
</head>

<body>
<div class="container">
	<div class="row clearfix page_container">
    	<div class="col-md-12 column">
        <!--  HEADER的開始-->
			<%@ include file="/front/pageDesign/fe_header.jsp"%>
        <!--HEADER的結束-->    			
			
<!---------- 以下為網頁內容------------->    

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/affi/affi.do" enctype="multipart/form-data" name="form1">
<table align="center"><th><a href="#" data-role="fieldcontain" data-inline="true" data-theme="b" mini="true"><h2>線上加盟申請表	*為必填欄位</h2></a></th></table>

<table align="center" border="0" padding="10">


<tr><td><font color=red><b>*</b></font></td><td>營利事業統一編號:</td><td><input type="TEXT" name="bus_no" id="bus_no" size="45" value="<%= (affiVO==null)? "(8 位數)" : affiVO.getBus_no()%>" /></td><td> ex:06795431</td></tr>
<tr><td><font color=red><b>*</b></font></td><td>餐廳名稱:</td><td><input type="TEXT" name="rest_name" id="rest_name" size="45"	value="<%= (affiVO==null)? "" : affiVO.getRest_name()%>" /></td></tr>
<tr><td><font color=red><b>*</b></font></td><td>餐廳地址:</td><td><input type="TEXT" name="rest_addr" id="rest_addr" size="45"	value="<%= (affiVO==null)? "" : affiVO.getRest_addr()%>" /></td></tr>
<tr><td><font color=red><b>*</b></font></td><td>餐廳電話:</td><td><input type="TEXT" name="rest_tel" id="rest_tel" size="45" 	value="<%= (affiVO==null)? "" : affiVO.getRest_tel()%>" /></td><td> ex:04-36090088</td></tr>
<tr><td><font color=red><b>*</b></td><td>手機號碼:</td><td><input type="TEXT" name="rest_mobil" id="rest_mobil" size="45" value="<%= (affiVO==null)? "" : affiVO.getRest_mobil()%>" /></td><td> ex:0939-743821</td></tr>
<tr><td></td><td>餐廳相片:</td><td><input type="file" name="rest_photo"></td></tr>
<tr><td></td><td>Email:</td><td><input type="TEXT" name="rest_mail" id="rest_mail" size="45" 	value="<%= (affiVO==null)? "" : affiVO.getRest_mail()%>" /></td></tr>
<tr><td></td><td>餐廳網址:</td><td><input type="TEXT" name="rest_web" id="rest_web" size="45" value="<%= (affiVO==null)? "" : affiVO.getRest_web()%>" /></td></tr>

<!--  <tr><td>*</td><td align="top">餐廳介紹</td><td><textarea name="rest_intro" cols="50" rows="10"> </textarea></td></tr>-->
<tr><td><font color=red><b>*</td><td align="top">餐廳簡介</td><td><textarea id="rest_intro" type="TEXT" name="rest_intro" cols="44" rows="12"  placeholder="300 字以內"
			>${affiVO.rest_intro}</textarea></td>
			<td><input data-role="button" type="submit" value="送出新增" data-theme="b"/></td></tr>

<input type="hidden" name="affi_state" size="45" value="<%= (affiVO==null)? "1" : affiVO.getAffi_state()%>" />
<tr><td></td><input type="hidden" name="action" value="insert">
<!-- <tr><td></td><td></td><td><input data-role="button" type="submit" value="送出新增" data-theme="b"/></td></tr> -->
<!-- <tr><td></td><td></td><td><a href="#" data-role="button" data-icon="" data-inline="false" data-mini="true" data-theme="b">新增</a></td></tr>
-->

</table>
<table align="center">
<tr><td>
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
</c:if></td></tr>
<table align="center">
<tr><td>			</td><td><button id="btn">Set Value</button></td></tr>	<!-- 快速小按鈕 -->
</table>
<tr><td><img src="<%=request.getContextPath()%>/front/affi/images/R2.jpg" width="900" style="opacity:0.8" /></td></tr>
</table>
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
