<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cop.model.*"%>
<%@ page import="java.util.*"%>

<%-- 萬用複合查詢-可由客戶端select_page.jsp隨意增減任何想查詢的欄位 --%>
<%-- 此頁只作為複合查詢時之結果練習，可視需要再增加分頁、送出修改、刪除之功能--%>

<!--          本來是都用session取得list(搜尋結果)，現在測試把session換成context -->
<%-- <jsp:useBean id="listCop_ByCompositeQuery" scope="session" type="java.util.List" /> --%>

<jsp:useBean id="listCop_ByCompositeQuery" scope="application" type="java.util.List" />


<%-- <% List<CopVO> listCop_ByCompositeQuery=(List<CopVO>)	request.getParameter("listCop_ByCompositeQuery"); %>  --%>

<%-- <% --%>
<!-- 	CopService copSvc = new CopService(); -->
<!-- 	List<CopVO> list = copSvc.getAll(); -->
<!-- 	pageContext.setAttribute("list",list); -->
<%-- %> --%>

<html>
<head>


<title>FOOD me 查詢餐劵資料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/store/pageSource/styleSource.jsp"%>
<style type="text/css"> 
.btn_delet {
	font-family: Arial;

	text-decoration: none;
	
}
</style>

<SCRIPT type="text/javascript">
       <!-- 此check()函式在最後的「傳送」案鈕會用到 -->
        function to_OnState()
        {
                
                	confirm("※同時間只能有一種餐劵上架，是否確定將此餐劵上架?");
               
        }

        function to_OffState()
        {
               
                	confirm("是否確定將此餐劵下架?");
                
        }
   </SCRIPT>
  
</head>
<body bgcolor='white'>
<p></p>
	<table width="935" class="table table-bordered"
		Style="vertical-align: middle">
		<tr >
			<th bgcolor="#F0F0F0" width="45" height="46"><div align="center">
					<strong>編號</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="190"><div align="center">
					<strong>餐劵名稱</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="74"><div align="center">
					<strong>發行數量(張)</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="74"><div align="center">
					<strong>銷售數量(張)</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="61"><div align="center">
					<strong>單價(張)</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="92"><div align="center">
					<strong>銷售總金額(NT)</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="92"><div align="center">
					<strong>應付手續費(10%)(NT)</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="92"><div align="center">
					<strong>上架狀態</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="106"><div align="center">
					<strong>上架日期</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="60"><div align="center">
					<strong>修改</strong>
				</div></td>
			<th bgcolor="#F0F0F0" width="90"><div align="center">
					<strong>上/下架</strong>
				</div></td>
		</tr>
		<c:forEach var="copVO" items="${listCop_ByCompositeQuery}"
			varStatus="s">
			<tr ${(copVO.cop_no==param.cop_no) ? 'bgcolor=#FFB7DD':''}>
				<td><div align="center">${s.count}</div></td>
				<td><div align="center">${copVO.cop_name}</div></td>
				<td><div align="center">${copVO.cop_circu}</div></td>
				<td><div align="center">${copVO.cop_selamt}</div></td>
				<td><div align="center">${copVO.cop_price}</div></td>
				<td><div align="center">${copVO.cop_selamt*copVO.cop_price}</div>
				<td><div align="center">${copVO.cop_selamt*copVO.cop_price*0.1}</div>
				<td><div align="center">
					<c:if test="${copVO.cop_state.equals('1')}">
	                      <img src= <%=request.getContextPath()%>/store/img/ok.png>
                    </c:if>
                    <c:if test="${copVO.cop_state.equals('0')}">
	                      <img src= <%=request.getContextPath()%>/store/img/not.png>
                    </c:if>

					</div></td>
				<td><div align="center">${copVO.cop_date}</div></td>
				<td><div align="center">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/cop/cop.do">
							<input class="btn_delet" type="submit" value="修改"> 
							<input type="hidden" name="cop_no" value="${copVO.cop_no}"> 
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</div></td>
				<td><div align="center">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/cop/cop.do">
							<c:if test="${copVO.cop_state.equals('0')}">
							<input class="btn_delet" type="submit" value="上架" onClick="to_OnState()"> 
							<input type="hidden" name="cop_state" value="1"> 
							</c:if>
							<c:if test="${copVO.cop_state.equals('1')}">
							<input class="btn_delet" type="submit" value="下架" onClick="to_OffState()"> 
							<input type="hidden" name="cop_state" value="0"> 
							</c:if>
							<input type="hidden" name="cop_no" value="${copVO.cop_no}"> 
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
							<input type="hidden" name="action" value="Set_State">
						</FORM>
					</div></td>

			</tr>
		</c:forEach>
	</table>
</body>
</html>
