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
<%@ page import="com.act.model.*"%>
<%
	ActVO actVO = (ActVO) request.getAttribute("actVO");
%>
<%
     String start = "";
     String end = "";
    int state=0;//copVO==null 
    if(actVO != null){
    	String time1=actVO.getAct_time();
    	String time2=actVO.getAct_time();
    	 start = time1.substring(0, 10);
    	 end = time2.substring(11, 21);
    	if(actVO.getAct_state().equals("1"))
    	     {state=1;}
    	else if(actVO.getAct_state().equals("0"))
    	     {state=0;}
    	else 
    	     {state=0;}
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>

   <script>
      function fun() {
        
        	var start = document.getElementById("datepicker").value;
        	var end = document.getElementById("datepicker2").value;
        var	s=document.getElementById("act_time").value = start+"~"+end;
                       
        
      }
    </script>

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

<script>
   $(function() {
    $( "#datepicker" ).datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      changeYear: true
    });
    $( "#datepicker2" ).datepicker({
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
	     <FORM NAME="addAct" ACTION="<%=request.getContextPath()%>/act/act.do" method="post">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h4>
				<b>修改活動</b>
			</h4>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-2 column">
		</div>
		<div class="col-md-6 column" >
			
				<div class="form-group" >
				  <table width="482" border="0" cellspacing="0" >
				    <tr >
				      <td width="96" style='text-align: left;'>活動名稱:</td>
				      <td width="382" style='text-align: left;'><input type = text name="act_name" size=45 value="<%=(actVO == null) ? "" : actVO.getAct_name()%>"></td>
			        </tr>
				     <tr>
				      <td style='text-align: left;'>活動起始時間:</td>
				      <td style='text-align: left;'><input type="text" size="12" id="datepicker" name = "date_start" value="<%=(actVO == null) ? "" : start %>">  
                      </td>
			        </tr>
				    <tr>
				      <td style='text-align: left;'>活動結束時間:</td>
				      <td style='text-align: left;'><input type="text" size="12" id="datepicker2" name = "date_end" value="<%=(actVO == null) ? "" : end %>">  
                      </td>
			        </tr>
			        </tr>
				    <tr>
				      <td style='text-align: left;'>活動狀態:</td>
				      <td style='text-align: left;'>上架<input type = radio name="act_state" value="1" <%= (state ==1)? "checked" : ""%>>
                          &nbsp;
                                                               下架<input type = radio name ="act_state" value="0" <%= (state ==0)? "checked" : ""%>>
                      </td>
			        </tr>
<!-- 			      <tr> -->
<!-- 			        <td style='text-align: left;'>活動照片:</td> -->
<!-- 				      <td style='text-align: left;'><input class="btn btn-default" type="file" name="act_photo"><br></td> -->
<!-- 			     </tr> -->
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
				活動內容:
			</h4>
		</div>
            <div>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <textarea name="act_cont" rows=6 cols=100 wrap = off><%=(actVO == null) ? "" : actVO.getAct_cont()%></textarea>
            </div>
	</div>
	<div class="row clearfix">
		<div class="col-md-2 column">
		</div>
        <br>
		<div class="col-md-6 column">
		  <input type="hidden" name="act_no" value="${actVO.act_no }">
		  <input type="hidden" name="action" value="update">
		  <input type="hidden" name="rest_no" value="${restVO.rest_no}"><%--先寫死，本來應該是由登入的資料取得 --%>
            <table  width="364" border="0" cellspacing="0">
              <tr>
                 <td width="173">
			       <div align="center">
			       
			       			       <input type=hidden name="act_time" id="act_time" value="">
			       
			        <input class="btn btn-info" type="submit" value="送出" onClick="fun()">
	             </div></td>
             
                 <td width="181">
			       <div align="center">
			         <input class="btn btn-danger" type="button" value="取消" onclick="window.location='<%=request.getContextPath()%>/store/act/checkAct.jsp'"/>
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