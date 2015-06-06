<!-- jsp檔名一定要用英文，否則會404錯誤 -->
<!-- 遇到Could not publish to the server java.lang.NullPointerException問題：eclipse重開即可-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!-- 不加的話頁面會亂碼 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.rest.model.*" %>
<%@ page import="com.classOfRest.model.*" %>
<%@ page import="com.restClass.model.*" %>
<%! Integer rest_no=0; 
	String rest_name=null;
	Date current_date=new Date();//產生今天日期物件，準備轉成日期字串
	Date future_date=new Date(current_date.getTime()+6*24*60*60*1000);//產生六天後日期物件
	DateFormat df=null;
	String today=null;
	String six_day=null;
	RestVO vo=null;
	RestService rs=new RestService();
	Integer updateResult=0;
	ClassService cs=null;
	List<ClassVO> clist=null;
	ClassVO classVO=null;
	Rest_classService rcs=null;
	
	%>
	
<% 	
List<Rest_classVO> rclist=new ArrayList<Rest_classVO>();
List<Integer> class_no_list=new ArrayList<Integer>();
	vo=(RestVO)session.getAttribute("restVO");	
	rest_no=vo.getRest_no();
	vo=rs.getOneRest(rest_no);
	//pageContext.setAttribute("vo",vo);
	cs=new ClassService();//從資料庫取得所有餐廳分類
	clist=cs.getAll();
	pageContext.setAttribute("clist", clist);//為了用EL表示
	
	rcs=new Rest_classService();//從資料庫取得餐廳所屬分類
	rclist.clear();//清空list內所有舊的餐廳所屬分類
	rclist=rcs.getRest_class(rest_no);
	for(Rest_classVO vo:rclist)
	{
		class_no_list.add(vo.getClass_no());//餐廳所屬的分類編號存在list裡
	}
	pageContext.setAttribute("class_no_list", class_no_list);
	
	//取得進入該頁面時的狀況，以決定如何顯示彈出視窗
	if((String)request.getAttribute("updateResult")==null)
	{
		updateResult=0;	
	}	
	else if(((String)request.getAttribute("updateResult")).equals("餐廳資料未修改"))
	{
		updateResult=1;
	}	
	else if(((String)request.getAttribute("updateResult")).equals("餐廳資料已修改"))
	{
		updateResult=2;
	}
%>
	
<%  
    df=new SimpleDateFormat("yyyy-MM-DD");
    today=df.format(current_date);
    six_day=df.format(future_date);%>
    
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
  <title>FOODME店家管理頁面</title>
  <%@ include file="/store/pageSource/styleSource.jsp"%>
	<script>
	webshims.setOptions('forms-ext', {types: 'date'});
	webshims.polyfill('forms forms-ext');
	</script>
    <script>
		jQuery(function($){
	  $('#example1').datepicker();
	});
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
	
	function checkUpdate()
	{
		//檢查輸入密碼是否相同
		var psw=document.getElementsByName("rest_psw")[0].value;
		var pswCheck=document.getElementsByName("rest_pswCheck")[0].value;
		
		if(psw==pswCheck)
		{
			
			if(confirm("確認修改餐廳資料？"))
			{
				return true;
			}	
			else
			{
				alert("餐廳資料未修改");
				return false;
			}
		}	
		else
		{
			alert("輸入密碼與確認密碼有誤");
			return false;
		}	
			
	}
	function showUpdateResult()//onload完後根據資料修改狀況決定彈出視窗
	{
		var updateResult=document.getElementById("updateResult").value;
		if(updateResult==1)
		{
			alert("餐廳資料未修改");
		}
		else if(updateResult==2)
		{
			alert("餐廳資料已修改");
		}
		else
		{
			
		}	
	}
	function setCheckedClass()
	{
		for(var i=0;i<document.getElementsByName("class").length;i++)
		{
			document.getElementsByName("class")[i].checked=false;	
		}
		
		//拿所有餐廳分類列表一一跟被選取餐廳分類比較
		for(var i=0;i<document.getElementsByName("class").length;i++)
		{
			for(var j=0;j<document.getElementsByName("class_selected").length;j++)
			{
								
				if(document.getElementsByName("class")[i].value==
					document.getElementsByName("class_selected")[j].value)
					{
					
							document.getElementsByName("class")[i].checked=true;	
							break;
					}
			}	
		}	
	}
	
	function onloadRun()
	{
		disptime();
		showUpdateResult();
		setCheckedClass();
	}
</script>
<style>
#checkButton
{
	text-align:center;
}
#returnHomePage
{
	font-size:24px;
	text-align:center;
}
.rest_class
{
	
	float:left;
	margin-right:20px;
}


</style>
</head>

<body onload="onloadRun()">
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
            <div id="title">		
				FOODME店家管理頁面
            </div>
            <hr>
			<div class="row clearfix">
				<%@include file="/store/pageSource/header.jsp" %>
                               
				<div class="col-md-9 column" style="border-right: #E4E4E4 1px solid">
				    <input type="hidden" id="updateResult" value="<%=updateResult%>">				 
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
				    
				    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/rest/rest.do" enctype="multipart/form-data" name="form1">
					<table align="center"><th><a href="#" data-role="fieldcontain" data-inline="true" data-theme="b" mini="true"><h2>餐廳資訊管理表格(可直接修改)</h2></a></th></table>
					
					<table align="center" border="0" padding="10">
					<tr><td width="150">餐廳分類：</td>
					<td>	
					<!-- 所有餐廳分類列表(供使用者選取分類之處) -->				
					<c:forEach var="classVO" items="${clist}">
					<div class="rest_class">
					<input type="checkbox" name="class" size="10" value="${classVO.class_no}">${classVO.class_name}
					</div>
					
					</c:forEach>					
					</td>
					</tr>										
					
					<!-- 將class_no_list內之class_no一一取出 -->
					<c:forEach var="class_no" items="${class_no_list}">
					<!-- class_no存入html元素裡 -->
					<!-- 被選取的餐廳分類列表-->
					<input type="hidden" name="class_selected" value="${class_no}" id="${class_no}">
					</c:forEach>
					
					<tr><td><input type="hidden" name="rest_account" size="45"	value="<%= (vo==null)? "" : vo.getRest_account()%>" /></td></tr>
					<tr><td>密碼:</td><td><input type="password" name="rest_psw" size="45"	value="<%= (vo==null)? "" : vo.getRest_psw()%>" /></td></tr>
					<tr><td>確認密碼:</td><td><input type="password" name="rest_pswCheck" size="45"	value="<%= (vo==null)? "" : vo.getRest_psw()%>" /></td></tr>
					<tr><td>營利事業統一編號:</td><td><input type="TEXT" name="bus_no" size="45" value="<%= (vo==null)? "(8 位數)" : vo.getBus_no()%>" /></td><td> ex:06795431</td></tr>
					<tr><td>餐廳名稱:</td><td><input type="TEXT" name="rest_name" size="45"	value="<%= (vo==null)? "" : vo.getRest_name()%>" /></td></tr>
					<tr><td>餐廳地址:</td><td><input type="TEXT" name="rest_addr" size="45"	value="<%= (vo==null)? "" : vo.getRest_addr()%>" /></td></tr>
					<tr><td>餐廳電話:</td><td><input type="TEXT" name="rest_tel" size="45" 	value="<%= (vo==null)? "" : vo.getRest_tel()%>" /></td><td> ex:04-36090088</td></tr>
					
					<tr><td>餐廳相片:</td><td><input type="file" name="rest_photo" ></td></tr>
					<tr><td></td><td><img src="<%=request.getContextPath()%>/rest/ShowRestImg?rest_no=<%=vo.getRest_no()%>" width="500" height="250" align="middle"></td></tr>
					<tr><td>Email:</td><td><input type="TEXT" name="rest_mail" size="45" 	value="<%= (vo==null)? "" : vo.getRest_mail()%>" /></td></tr>
					<tr><td>餐廳網址:</td><td><input type="TEXT" name="rest_web" size="45" value="<%= (vo==null)? "" : vo.getRest_web()%>" /></td></tr>
					<tr><td>餐廳營業時間:</td><td>
					<select name="rest_opentime" ><!-- 修改部分 -->					
					<option value="1000" <%= ("1000".equals(vo.getRest_opentime().substring(0,4)))? "selected" :""%>>10:00</option>
					<option value="1100" <%= ("1100".equals(vo.getRest_opentime().substring(0,4)))? "selected" :""%>>11:00</option>
					<option value="1200" <%= ("1200".equals(vo.getRest_opentime().substring(0,4)))? "selected" :""%>>12:00</option>
					<option value="1300" <%= ("1300".equals(vo.getRest_opentime().substring(0,4)))? "selected" :""%>>13:00</option>
					</select>~
					<select name="rest_closetime">					
					<option value="2000" <%= ("2000".equals(vo.getRest_opentime().substring(5,9)))? "selected" :""%>>20:00</option>
					<option value="2100" <%= ("2100".equals(vo.getRest_opentime().substring(5,9)))? "selected" :""%>>21:00</option>
					<option value="2200" <%= ("2200".equals(vo.getRest_opentime().substring(5,9)))? "selected" :""%>>22:00</option>
					<option value="2300" <%= ("2300".equals(vo.getRest_opentime().substring(5,9)))? "selected" :""%>>23:00</option>
					</select>
					</td></tr>
				
					<tr><td>時段的總訂位數:</td><td><input type="TEXT" name="reserved_totalset" size="45" value="<%= (vo==null)? "" : vo.getReserved_totalset()%>" /></td></tr>
					<tr><td></td><td><input data-role="button" type="submit" value="送出修改" data-theme="b" onclick="return checkUpdate()" class="btn"/></td></tr>

					
					</table>
					<table align="center">
					
					</table>
					<input type="hidden" name="action" value="update">
					<input type="hidden" name="rest_no" value="<%=vo.getRest_no()%>">
					<input type="hidden" name="rest_state" size="45" value="<%= (vo==null)? "1" : vo.getRest_state()%>" />
					</FORM>
					<div id="returnHomePage"><!-- 用以調整區塊內文字大小及對齊方式 -->
						<a href="<%=request.getContextPath()%>/store/index.jsp" >回首頁</a>
					</div>					
				</div>	
			</div>
            
            
		</div>
	</div>
</div>
</body>
</html>


