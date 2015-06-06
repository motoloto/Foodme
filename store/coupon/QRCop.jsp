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



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>

<!--   QRcode掃描的程式碼 -->
  <style type="text/css">
body{
    width:100%;
    text-align:center;
}
img{
    border:0;
}
#main{
    margin: 15px auto;
    background:white;
    overflow: auto;
	width: 100%;
}
#header{
    background:white;
    margin-bottom:15px;
}
#mainbody{
    background: white;
    width:100%;
	display:none;
}
#footer{
    background:white;
}
#v{text-align: right;
    width:320px;
    height:240px;
}
#qr-canvas{
    display:none;
}
#qrfile{
    width:320px;
    height:240px;
}
#mp1{
    text-align:center;
    font-size:35px;
}
#imghelp{
    position:relative;
    left:0px;
    top:-160px;
    z-index:100;
    font:18px arial,sans-serif;
    background:#f0f0f0;
	margin-left:35px;
	margin-right:35px;
	padding-top:10px;
	padding-bottom:10px;
	border-radius:20px;
}
.selector{
    margin:0;
    padding:0;
    cursor:pointer;
    margin-bottom:-5px;
}
#outdiv
{
    width:320px;
    height:240px;
	border: solid;
	border-width: 3px 3px 3px 3px;
}
#result{
    <!--border: solid;-->
	border-width: 1px 1px 1px 1px;
	padding:5px;
	width:70%;
}

ul{
    margin-bottom:0;
    margin-right:40px;
}
li{
    display:inline;
    padding-right: 0.5em;
    padding-left: 0.5em;
    font-weight: bold;
    border-right: 1px solid #333333;
}
li a{
    text-decoration: none;
    color: black;
}

#footer a{
	color: black;
}
.tsel{
    padding:0;
}

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

<script src="./Web QR_files/cb=gapi.loaded_1" async=""></script><script type="text/javascript" async="" src="./Web QR_files/ga.js"></script><script src="./Web QR_files/cb=gapi.loaded_0" async=""></script><script type="text/javascript" src="./Web QR_files/llqrcode.js"></script>
<script type="text/javascript" src="./Web QR_files/plusone.js" gapi_processed="true"></script>
<script type="text/javascript" src="./Web QR_files/webqr_v1.js"></script>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-24451557-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
	

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
						
						

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h3 class="text-success">
				餐劵掃描
			</h3>
		</div>
	</div>
	
	     


<p id="mp1">

</p>
<ul>

</ul>
</div>
<div id="mainbody" style="display: inline;">
<table class="tsel" border="0" width="100%">
<tbody><tr>
<td valign="top" align="center" width="50%">
<table class="tsel" border="0">
<tbody><tr>
<td><img class="selector" id="webcamimg" src="" onclick="setwebcam()" align="left" style="opacity: 1;"></td>
<td><img class="selector" id="qrimg" src="" onclick="setimg()" align="right" style="opacity: 0.2;"></td></tr>
<tr align="center"><td colspan="2" align="center">
<div id="outdiv" align="center"><video id="v" autoplay=""></video></div></td></tr>
<tr><td>
<div  style="text-align: left; ">
<input type=text id="result" value="- scanning -" style=text-align:center  style="font-size:16px" size="25" maxlength=15>
</div>
</td></tr>
<tr><td colspan="3" align="center" style="font-size:18px">
<div id="remainder_times"  style="text-align: left; "></div>
</td></tr>
<tr><td colspan="3" align="center">
<div  style="text-align: left; ">
<input type="button" id="check_seqnum" value="查詢此序號"> 
</div> 


<tr><td colspan="3" align="center">
<div id=use_seq  style="text-align: left; ">
<p></p>您要使用&nbsp;&nbsp;&nbsp;&nbsp;
<select id=slc></select>張&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='use_seqnum' value='確定使用' >
</div>
</td></tr>
</tbody></table>
</td>
<td valign="top" align="left" width="50%">
<table class="tsel" border="0">
<tbody>

<tr><td align="left">
<div id=error style="font-size:24px;color:#F00;text-align: left; "></div>
</td></tr>
<tr><td  align="left" style="font-size:24px;">
<div id="restname" style="text-align: left; "></div>
</td></tr>
<tr><td  align="left" >
<div id="cop_name" style="font-size:18px;text-align: left; "></div>
</td></tr>
<tr><td  align="left" >
<div id="cop_orlprice" style="font-size:18px;text-align: left; "></div>
</td></tr>
<tr><td  align="left" >
<div id="cop_content" style="width:500px;text-align: left;font-size:18px;"></div>
</td></tr>
<tr><td colspan="3" align="center">
<div id=afteruse style="font-size:18px;color:#F00;text-align: left;"></div>
</td></tr>
<tr><td colspan="3" align="center">
<div id=discount style="font-size:18px;color:#F00;text-align: left;"></div>
</td></tr>
</tbody></table>
</td>





</tbody></table>
</div>&nbsp;
<div id="footer">
<br>

</div>
</div>
<canvas id="qr-canvas" width="800" height="600" style="width: 800px; height: 600px;"></canvas>
<script type="text/javascript">load();
$('#check_seqnum').click(function() {
	$('#remainder_times').val("");
	 $.post( "<%=getServletContext().getContextPath()%>/odr/odr.do", {
			action : "QRCode",
			odr_seqnum : $('#result').val(),
			
		}, function(resultJson, status) {
			showRemainder(resultJson);
		});
		})
function showRemainder(resultJson) {
	 var i=1;
		var parsed = JSON.parse(resultJson);
	
		var remainder=parsed.remainder;
		var rest_name=parsed.rest_name;
		var cop_name=parsed.cop_name;
		var cop_content=parsed.cop_content;
		var cop_orlprice=parsed.cop_orlprice;
		
<%--		var str = "<p></p>您要使用&nbsp;&nbsp;&nbsp;&nbsp;"
		         +"<select id=slc></select>張&nbsp;&nbsp;&nbsp;&nbsp;"
		         +"<input type='button' id='use_seqnum' value='確定使用' >";
--%>	
<%--		$('#slcdiv').html(str);
--%>
        $('#restname').html("");
        $('#cop_name').html("");
        $('#cop_orlprice').html("");
        $('#cop_content').html("");
        $('#afteruse').html("");
        $('#discount').html("");
        $('#error').html("");
        if(cop_content=="非本餐廳發行之餐劵"){
        	$('#error').html("非本餐廳發行之餐劵");
        	$('#remainder_times').html("剩餘張數: &nbsp;&nbsp;&nbsp;"+remainder);
        }else{
		$('#cop_content').html("餐劵內容:<br> &nbsp;&nbsp;&nbsp;"+cop_content);
        $('#afteruse').html("");
		$('#restname').html("發行餐廳: &nbsp;&nbsp;&nbsp;"+rest_name);
		$('#cop_name').html("餐劵名稱: &nbsp;&nbsp;&nbsp;"+cop_name);
		$('#cop_orlprice').html("可折抵價格/張: &nbsp;&nbsp;&nbsp;"+cop_orlprice+"元");
		$('#remainder_times').html("剩餘張數: &nbsp;&nbsp;&nbsp;"+remainder);
            }
		$("#slc option").remove();
		for(i=1; i<=remainder ; i++){
		$("#slc").append($("<option></option>").attr("value", i).text(i));
		}
	};

	
	$('#use_seqnum').click(function() {
		 $.post( "<%=getServletContext().getContextPath()%>/odr/odr.do", {
				action : "UseSeqByQRCode",
				odr_tms : $('#slc').val(),
				<%-- odr_seqnum : $('#result').val()  --%>
				
			}, function(resultJson, status) {
				showResult(resultJson);
			});
			})
	function showResult(resultJson) {
		 
			var parsed = JSON.parse(resultJson);
		
			var remainder_afteruse = parsed.remainder_afteruse;
			var discount = parsed.discount;
			
			$('#remainder_times').html("剩餘張數: &nbsp;&nbsp;&nbsp;"+remainder_afteruse);
			$('#afteruse').html("已完成餐劵支付，一共折抵"+discount+"元");
			$('#discount').html("");
			$("#slc option").remove();
			for(i=1; i<=remainder_afteruse ; i++){
			$("#slc").append($("<option></option>").attr("value", i).text(i));
			}
		};

	
	function check()
    {
            
            	confirm("是否確定使用餐劵");
            
    }
</script>


<iframe name="oauth2relay362982505" id="oauth2relay362982505" src="./Web QR_files/postmessageRelay.html" tabindex="-1" style="width: 1px; height: 1px; position: absolute; top: -100px;"></iframe>
					
						
						

						
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