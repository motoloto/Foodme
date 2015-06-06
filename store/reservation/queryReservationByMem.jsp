<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.rest.*"%>
<%@ page import="com.reserv.*"%>
<%@ page import="java.util.*"%>

<%! int i=0; %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>FOODME店家管理頁面</title>
 <%@ include file="/store/pageSource/styleSource.jsp"%>

</head>
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
	var reservation_day=new Array();
	var newReservationDay=new Array();
</script>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
            <div id="title">		
				FOODME店家管理頁面
            </div>
            <hr>
            
			<div class="row clearfix">
				<%@include file="/store/pageSource/header.jsp" %>
				<div class="col-md-9 column">
					<h3 align="center">
                        	餐廳訂位紀錄管理頁面(依會員編號查詢)
                    </h3>
                    <hr>
                    <div class="row clearfix" align="center">
                        <div class="col-md-3 column">
                           	餐廳編號：${param.rest_no}<!-- 在request裡的物件要取出，如果沒特別setAtrribute的，用類似getParameter的方式取出 -->
                        </div>
                        <div class="col-md-3 column">
                           <!--餐廳名稱：${rest_name}--> 
                        </div>
                        <div class="col-md-3 column">
                           	 訂位查詢時段：${param.today}~${param.future_day}
                        </div>
                    </div>
                    
                    <hr>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>
                                     	訂位紀錄編號
                                </th>
                                <th>
                                     	會員編號
                                </th>
                                <th>
                                    	餐廳編號
                                </th>
                                <th>
                                    	人數
                                </th>
                                
                                <th>
                                   	 是否入座
                                </th>
                                <th>
                                	訂位日期
                                </th>
                                <th>
                                	訂位時段
                                </th>
<!--                                 <th> -->
<!--                                     	餐券序號 -->
<!--                                 </th>                               -->
                            </tr>
                        </thead>
                        <tbody>
                        <!--查詢結果顯示-->
	                        <c:forEach var="vo" items="${list}">
	                        <%if(i%2==1){ %>
	                            <tr  class="success">
	                        <%}
	                        else{ %><!-- 明明沒寫錯卻跳錯：改打其他程式碼，再打回原本程式碼 -->
	                        <tr>    
	                        <% }%>        <td>
	                                   ${vo.rec_no}
	                                </td>
	                                <td>
	                                   ${vo.mem_no}
	                                </td>
	                                <td>
	                                   ${vo.rest_no} 
	                                </td>
	                                <td>
	                                   ${vo.count}
	                                </td>
	                                <td width="120px">
	                                    <c:if test="${vo.seating==1}">
	                                       	 未入座
	                                    </c:if>
	                                	<c:if test="${vo.seating==0}">
	                                       	 已入座
	                                    </c:if>

	                                	<form method="post" action="<%=request.getContextPath()%>/reserv/reserv.do">		                                	
		                                	<input type="hidden" name="seating" value="${vo.seating}">
		                                	<input type="hidden" name="rest_no" value="${vo.rest_no}">
		                                	<input type="hidden" name="mem_no" value="${vo.mem_no}">
		                                	<input type="hidden" name="reservation_day" value="${vo.reservation_day}" >
		                                	<input type="hidden" name="period" value="${vo.period}">
		                                	<input type="hidden" name="action" value="change_seating"/>
		                                	<input type="hidden" name="today" value="${param.today}"/>
		                                	<input type="hidden" name="future_day" value="${param.future_day}"/>
		                                	<button type="submit" id="<%=i%>">修改</button>
	                                	</form>	                                		                                		                                	
	                                </td>
	                                <td>
	                                   ${vo.reservation_day} 
	                                </td>
	                                <td>
	                                	${vo.period}
	                                </td>
<!-- 	                                <td> -->
<%-- 	                                   ${vo.odr_seqnum} --%>
<!-- 	                                </td>	                                	                                                                -->
	                            </tr>
	                            
	                            <%i++; %>
	                        </c:forEach>
	                        <%i=0; %>
                        </tbody>
                    </table>
                    <script type="text/javascript">	                    
	              	    reservation_day=document.getElementsByName("reservation_day");	
	              	 
	              	    for(var i=0;i<reservation_day.length;i++)
              	    	{
	              	    	
	              	    	newReservationDay[i]=reservation_day[i].value.substring(0,10);
              	    	}	
	              	  
	              	    var date = new Date();//取得從今天日期
	              		var y=date.getFullYear();
	              		var m=("0"+(date.getMonth()+1)).slice(-2);                           	
	              		var d=("0"+(date.getDate())).slice(-2);	//修改部分		
	              		var today=y+"-"+m+"-"+d;
	              		
	              		 for(var i=0;i<reservation_day.length;i++)
              	    	 {	              			
	              			if(today!=newReservationDay[i])
           			 		{
            			 		document.getElementById(i).disabled="disabled";
           			 		}             			 	
              	    	 }
	              	</script>
				</div>
				<div id="backToHomepage" align="center">
				<a href="<%=request.getContextPath() %>/store/index.jsp">回首頁</a>
				</div>
			</div>
            						<!--執行disptime();-->
						<script>
							disptime();
						</script>
		</div>
	</div>
</div>
</body>
</html>