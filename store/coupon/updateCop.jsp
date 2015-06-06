<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="java.text.*"%>
<!--获取系统时间必须导入的-->
<%@ page import="com.rest.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!int identity = 0; //用來分辨該從資料庫取出訂位數還是從request物件取出訂位數%>
<%
	RestVO restVO = (RestVO) session.getAttribute("restVO");
	
%>
<%@ page import="com.cop.model.*"%>
<%
    int state=3;//copVO==null 
	CopVO copVO = (CopVO) request.getAttribute("copVO");
    if(copVO != null){
    	
    	if(copVO.getCop_state().equals("1"))
    	     {state=1;}
    	else if(copVO.getCop_state().equals("0"))
    	     {state=0;}
    }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>

<style type="text/css">

.radio_state {  background-color: #FF9900; border: #000000; border-style: dotted; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}

</style>

<script type="text/javascript">
	//時間每秒都更新
	function disptime() {
		var t = new Date();
		var y = t.getFullYear();
		var m = t.getMonth() + 1;
		var d = t.getDate();
		var h = t.getHours();
		var mi = t.getMinutes();
		var s = t.getSeconds();
		var mydate = y + "-" + m + "-" + d + "  " + h + "-" + mi + "-" + s;
		document.getElementById("myclock").innerHTML = mydate;

		var myTime = setTimeout("disptime()", 1000);
	}
</script>

<SCRIPT type="text/javascript">
       <!-- 此check()函式在最後的「傳送」案鈕會用到 -->
        function check()
        {
                if(updateform.cop_state.value == "1") 
                {
                	confirm("※同時間只能有一種餐劵上架，是否確定將此餐劵上架?");
                }else{
                	
                	updateform.submit();
                }
        }
   </SCRIPT>
  
  <script>
   $(function() {
    $( "#datepicker" ).datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      changeYear: true
    });
  });
  </script>

</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="title">FOODME店家管理頁面</div>
				<hr>
				<div class="row clearfix">
					<%@include file="/store/pageSource/header.jsp" %>

					<div class="col-md-9 column">
						<h3 align="center">餐廳訂位資訊管理頁面</h3>
						<div class="row clearfix" align="center">
							<div class="col-md-6 column">
								<span class="rest">餐廳編號：</span> <span id="rest_no"><%=restVO.getRest_no()%></span>
							</div>
							<div class="col-md-6 column">
								<span class="rest">餐廳名稱：</span> <span id="rest_name"><%=restVO.getRest_name()%></span>
							</div>
						</div>
						<hr>
						
						
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
	     <FORM  NAME="updateform" ACTION="<%=request.getContextPath()%>/cop/cop.do" method="post">
	     <input type="hidden" name="cop_no" value="${copVO.cop_no}">
	     <input type="hidden" name="rest_no" value="${copVO.rest_no}">
	     <input type="hidden" name="cop_selamt" value="${copVO.cop_selamt}">
	     
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h4>
				<b>修改餐劵</b>
			</h4>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-2 column">
		</div>
		<div class="col-md-6 column">
			
				<div class="form-group">
				  <table width="407" border="0" cellspacing="0">
				    <tr>
				      <td width="123" style='text-align: left;'>商品名稱:</td>
				      <td width="280" style='text-align: left;'><input type = text name="cop_name" size=30  value="<%= (copVO==null)? "" : copVO.getCop_name()%>"></td>
			        </tr>
				    <tr>
				      <td style='text-align: left;'>原價:</td>
				      <td style='text-align: left;'><input type = text name="cop_orlprice" size=5 value="<%= (copVO==null)? "" : copVO.getCop_orlprice()%>">NT$</td>
			        </tr>
				    <tr>
				      <td style='text-align: left;'>價格:</td>
				      <td style='text-align: left;'><input type = text name="cop_price" size=5 value="<%= (copVO==null)? "" : copVO.getCop_price()%>">NT$</td>
			        </tr>
				    
				    <tr>
				      <td style='text-align: left;'>使用期限:</td>
				      <td style='text-align: left;'><input type="text" size="12" id="datepicker" name = "cop_dl" value="<%= (copVO==null)? "" : copVO.getCop_dl()%>">  
                      </td>
			        </tr>
				    <tr>
				      <td style='text-align: left;'>上架:</td>
				      <td style='text-align: left;'>是<input type = radio class="radio_state" name="cop_state" value="1" <%= (state ==1)? "checked" : ""%>>
                          &nbsp;
                                                               否<input type = radio class="radio_state" name ="cop_state" value="0" <%= (state ==0)? "checked" : ""%>>
                      </td>
			        </tr>
			      </table>
				  
				</div>
				<div class="form-group"></div>
				 
			
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h4>
				商品描述
			</h4>
		</div>
            <div>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <textarea name="cop_content" rows=6 cols=100 wrap = off  ><%= (copVO==null)? "" : copVO.getCop_content()%></textarea>
            </div>
	</div>
	<div class="row clearfix">
		<div class="col-md-2 column">
		</div>
        <br>
		<div class="col-md-6 column">
		  <input type="hidden" name="action" value="update">
		  <input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
            <table  width="364" border="0" cellspacing="0">
              <tr>
                 <td width="173">
			       <div align="center">
			         <input class="btn btn-info" type="submit" value="送出" onClick="check()" >
	             </div></td>
             
                 <td width="181">
			       <div align="center">
			       <input class="btn btn-danger" type="button" value="取消" onclick="window.location='<%=request.getContextPath()%>/store/coupon/listCopforREST.jsp'"/>
	             </div></td>
              </tr>
            </table>
             
	  </div>
		<div class="col-md-4 column">
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
		</div>
	</div>
	</FORM> 
					
						
						

						
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