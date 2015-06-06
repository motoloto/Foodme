<%@page import="com.msg.model.*"%>
<%@page import="com.rest.model.*"%>
<%@page import="com.act.model.*"%>
<%@page import="com.intro.model.*"%>
<%@page import="com.cop.model.*"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file"%></head>

<%
	RestService restSrv = new RestService();
	MsgService msgSrv=new MsgService();
	IntroService introSrv=new IntroService();
	Integer rest_no = Integer.parseInt(request.getParameter("rest_no"));
	RestVO restVO = restSrv.getOneRest(rest_no);
	pageContext.setAttribute("restVO", restVO);
	
	 List<MsgVO> msgVOList= msgSrv.findByforeignKey(rest_no);
	pageContext.setAttribute("msgVOList", msgVOList);
	 List<IntroVO> introVOList= introSrv.getAllByARest(rest_no);
	pageContext.setAttribute("introVOList", introVOList);
	
	CopService copSvc = new CopService();
	CopVO copVO=copSvc.getOneCopOnState(rest_no);
	pageContext.setAttribute("copVO", copVO);
%>
<!-- 取得活動List -->
<%
	ActService actSrv = new ActService();
   List<ActVO> list_act = actSrv.getOnStateActOfRest(restVO.getRest_no());
	pageContext.setAttribute("list_act", list_act);
%>
<!-- 計算分數 -->
<%@ include file="/front/restaurant/scoreCount.file"%>
<script type="text/javascript">

$(document).ready(function() {
    $("input#dish_cont").autocomplete({
        width: 300,
        max: 10,
        delay: 100,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response) {
            $.ajax({
                url: "<%=request.getContextPath()%>/front/dish/dish.do",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: {
                	 term : request.term,
                	 action : "autoCommit"             
                	},
                success: function( data, textStatus, jqXHR) {
                    console.log( data);
                   for(var i=0;i<data.length;i++){
                    	data[i]=decodeURI(data[i]);		// 在jsp加上 decode
                    }
                    var items = data;
                    response(items);
                },
                error: function(jqXHR, textStatus, errorThrown){
                     console.log( textStatus);
                }
            });
        }
 
    });
	$('#sendScore').click(function() {  
		$('#score_form').submit();
		$('#score_modal').modal('hide');
		});
	$("#reserv_date").datepicker({
		 maxDate: "+6d",
		 minDate: new Date(),
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

	var successMsg='<%=(request.getAttribute("reservInfo")==null) ?"": request.getAttribute("reservInfo")%>';
	if($.trim(successMsg)!="")
	{
		alert(successMsg);
	}
	 sendQuery();
	$('#reserv_date').change(function() {
		sendQuery();
		});
	$('#reserv_count').change(function() {  
		sendQuery();
		});
	$('#sendMsg').click(function(){
		$.post( "<%=getServletContext().getContextPath()%>	/msg/msg.do", {
			action : "insert",
			msg_name : $('#msg_name').val(),
			msg_cont : $('#msg_cont').val(),
			rest_no : "${restVO.rest_no}"
		}, function(status) {
			$('#myModal').modal('hide');
			alert("留言已送出");
			window.location = window.location;
		});
	});
	});
	
	function sendQuery(){
		$.post( "<%=getServletContext().getContextPath()%>/reserv/reserv.do", {
			action : "getOneReserv",
			reserv_date : $('#reserv_date').val(),
			amount : $('#reserv_count').val(),
			rest_no : "${restVO.rest_no}"
		}, function(resultJson, status) {
			//				alert("狀態:" + status + "Data: " + resultJson.toString());
			showResult(resultJson);
		});
	};
	function showResult(resultJson) {
		var parsed = JSON.parse(resultJson);
		var newArray = new Array(parsed.length);
		var i = 0;
		var reservHtml = "";
		var emptystr = "目前無時段可供訂位";
		parsed
				.forEach(function(entry) {
					var time = entry.time;
					reservHtml += "<a href=/YA101G5/front/restaurant/fe_reserv.jsp?reserv_date="
							+ $('#reserv_date').val()
							+ "&reserv_count="
							+ $('#reserv_count').val()
							+ "&reserv_time="
							+ time+ "&rest_no=" + ${restVO.rest_no}+"><button   class='btn btn-danger' >" + time
							+ "</button></a>";
					i++;
				});
		if (reservHtml != "") {
			$('#showPanel').html(reservHtml);
		} else {
			$('#showPanel').html(emptystr);
		}
	}

	function check() {
		var state = document.getElementById("loginState").value;
		if (state == "1") {
			alert("此餐廳已加入您的收藏名單!!");
		}
	}
</script>
<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->
				<div class="row clearfix" id="index_content">
					<div class="col-md-12 column">
						<div class="row clearfix">
							<div class="col-md-3 column">
								<ul class="breadcrumb">
									<li><a
										href="<%=getServletContext().getContextPath()%>/front">Home</a>
										<span class="divider">/</span></li>
									<li><a
										href="<%=getServletContext().getContextPath()%>/front/search/searchRest.jsp">找餐廳</a>
										<span class="divider">/</span></li>
									<li class="active">餐廳資訊</li>
								</ul>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						<div class="carousel-inner">
							<div class="item active ">
								<img src="<%=request.getContextPath()%>/rest/ShowRestImg?rest_no=${restVO.rest_no }" />
							</div>
						</div>
						<div class="row clearfix">
							<div class="col-md-12 column">
								<input type=hidden id="loginState"
									value=<%=(session.getAttribute("memVO") == null) ? "0" : "1"%>>
								<a
									href="<%=request.getContextPath()%>/clc/clc.do?action=insert&mem_no=${memVO.mem_no}&rest_no=<%=rest_no%>"
									onclick="check()"> <img
									src="<%=getServletContext().getContextPath()%>/front/img/love.png">
								</a> <img
									src="<%=getServletContext().getContextPath()%>/front/img/mail.png">
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column ">
						<div class="sm_ad_block">
							<div class="row clearfix">
								<div class="col-md-6 column ">
									<h2>【${restVO.rest_name}】</h2>
									<pre>
										<table class="table table-striped">
									<tr align="center">
												<td>餐廳地址：</td>
												<td>${restVO.rest_addr}</td>
											</tr>
									<tr align="center">
												<td>電   話：</td>
												<td>${restVO.rest_tel}</td>
											</tr>
									<tr align="center">
												<td>信   箱：</td>
												<td>${restVO.rest_mail}</td>
											</tr>
									<tr align="center">
												<td>營業時間：</td>
												<td>${restVO.rest_opentime}</td>
											</tr>
 									</table>			
  								</pre>

								</div>
								<div class="row clearfix">

									<div class="col-md-6 column ">
										<div class="col-md-12 column pre-scrollable">

											<h2>最新活動</h2>
											<c:forEach var="actVO" items="${list_act}">
												<h3>
													<span class="label  label-danger">hot</span>${actVO.act_name }</h3>
													<h5><b>${actVO.act_time}</b></h5>
												<p>${actVO.act_cont }</p>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row clearfix">
					<div class="col-md-6 column">
						<h2>訂位資訊</h2>
						<div class="sm_ad_block">
							<div class="row clearfix">
								<div class="col-md-12 column">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">日期</label>
											<div class="col-sm-10">
												<%
													java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
													java.sql.Date date_SQL7 = new java.sql.Date(
															System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000);
												%>
												<input type="text" id="reserv_date" name="reserv_date"
													value="<%=today%>" max="<%=date_SQL7%>">
											</div>
										</div>
										<div class="form-group">
											<label for="inputPassword3" class="col-sm-2 control-label">人數</label>
											<div class="col-sm-10">
												<select id="reserv_count" class="form-control">
													<option>1</option>
													<option selected>2</option>
													<option>3</option>
													<option>4</option>
													<option>5</option>
													<option>18</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label for="inputPassword3" class="col-sm-2 control-label">時段</label>

										</div>
									</form>
									<div class="col-sm-offset-2 col-sm-10" id="showPanel"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<h2>
							<span>餐券</span>
						</h2>
						<div class="sm_ad_block">
							<b><h3>${copVO.cop_name}</h3></b>
							<p>
								<span>${copVO.cop_content }</span>
							</p>
							<p>
							<form method="post"
								action="<%=request.getContextPath()%>/odr/odr.do">
								<button type="submit" class="btn btn-danger btn-large">購買餐劵</button>
								<br> <br> <input type="hidden" name="rest_no"
									value="${restVO.rest_no }">
								<%-- 理應由登入資料取得來辨識哪家餐廳，測試先寫死  符合查詢的colname是rest_noByodr避免跟餐廳端衝到--%>
								<input type="hidden" name="action"
									value="listOneCop_ByCompositeQuery">
								<%--注意:這是去OdrServlet查詢哪筆餐劵 --%>
							</form>
							</p>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-6 column">
						<h2 class="text-left">評價</h2>
						<div class="sm_ad_block">
							<div class="row clearfix">
								<div class="col-md-6 column">
									<h2 class="text-left">分數：</h2>
									<div class="score">${avg}</div>
									<br> <br>
									<button type="button" class="btn btn-default btn-lg"
										data-toggle="modal" data-target="#score_modal">我要評分</button>
								</div>
								<div class="modal fade bs-example-modal-lg" id="score_modal"
									tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
									aria-hidden="true">
									<div class="modal-dialog modal-lg">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="exampleModalLabel">我要評分</h4>
											</div>
											<div class="modal-body">
												<form id="score_form" method="post"
													action="<%=request.getContextPath()%>/rest/rest.do">
													<div class="form-group">
														<label for="recipient-name" class="control-label"><div
																style="color: #F00;">價格:</div></label><br>1 <input
															type="radio" name="scor_pri" value="1"> &nbsp; <input
															type="radio" name="scor_pri" value="2"> &nbsp; <input
															type="radio" name="scor_pri" value="3"> &nbsp; <input
															type="radio" name="scor_pri" value="4"> &nbsp; <input
															type="radio" name="scor_pri" value="5"> &nbsp; <input
															type="radio" name="scor_pri" value="6" checked>
														&nbsp; <input type="radio" name="scor_pri" value="7">&nbsp;
														<input type="radio" name="scor_pri" value="8">
														&nbsp; <input type="radio" name="scor_pri" value="9">
														&nbsp; <input type="radio" name="scor_pri" value="10">
														&nbsp; 10
													</div>
													<div class="form-group">
														<label for="recipient-name" class="control-label"><div
																style="color: #F00;">料理:</div></label><br>1 <input
															type="radio" name="scor_cook" value="1"> &nbsp; <input
															type="radio" name="scor_cook" value="2"> &nbsp; <input
															type="radio" name="scor_cook" value="3"> &nbsp; <input
															type="radio" name="scor_cook" value="4"> &nbsp; <input
															type="radio" name="scor_cook" value="5"> &nbsp; <input
															type="radio" name="scor_cook" value="6" checked>
														&nbsp; <input type="radio" name="scor_cook" value="7">
														&nbsp; <input type="radio" name="scor_cook" value="8">
														&nbsp; <input type="radio" name="scor_cook" value="9">
														&nbsp; <input type="radio" name="scor_cook" value="10">&nbsp;
														10
													</div>
													<div class="form-group">
														<label for="recipient-name" class="control-label"><div
																style="color: #F00;">衛生:</div></label><br>1 <input
															type="radio" name="scor_hea" value="1"> &nbsp; <input
															type="radio" name="scor_hea" value="2"> &nbsp; <input
															type="radio" name="scor_hea" value="3"> &nbsp; <input
															type="radio" name="scor_hea" value="4"> &nbsp; <input
															type="radio" name="scor_hea" value="5"> &nbsp; <input
															type="radio" name="scor_hea" value="6" checked>&nbsp;
														<input type="radio" name="scor_hea" value="7">
														&nbsp; <input type="radio" name="scor_hea" value="8">
														&nbsp; <input type="radio" name="scor_hea" value="9">
														&nbsp; <input type="radio" name="scor_hea" value="10">
														&nbsp; 10
													</div>
													<div class="form-group">
														<label for="recipient-name" class="control-label"><div
																style="color: #F00;">環境:</div></label><br>1 <input
															type="radio" name="scor_envisco" value="1">
														&nbsp; <input type="radio" name="scor_envisco" value="2">
														&nbsp; <input type="radio" name="scor_envisco" value="3">&nbsp;
														<input type="radio" name="scor_envisco" value="4">
														&nbsp; <input type="radio" name="scor_envisco" value="5">
														&nbsp; <input type="radio" name="scor_envisco" value="6"
															checked> &nbsp; <input type="radio"
															name="scor_envisco" value="7"> &nbsp; <input
															type="radio" name="scor_envisco" value="8">
														&nbsp; <input type="radio" name="scor_envisco" value="9">
														&nbsp; <input type="radio" name="scor_envisco" value="10">&nbsp;
														10
													</div>
													<div class="form-group">
														<label for="recipient-name" class="control-label"><div
																style="color: #F00;">服務:</div></label><br>1 <input
															type="radio" name="scor_serv" value="1"> &nbsp; <input
															type="radio" name="scor_serv" value="2"> &nbsp; <input
															type="radio" name="scor_serv" value="3"> &nbsp; <input
															type="radio" name="scor_serv" value="4"> &nbsp; <input
															type="radio" name="scor_serv" value="5"> &nbsp; <input
															type="radio" name="scor_serv" value="6" checked>
														&nbsp; <input type="radio" name="scor_serv" value="7">&nbsp;
														<input type="radio" name="scor_serv" value="8">
														&nbsp; <input type="radio" name="scor_serv" value="9">
														&nbsp; <input type="radio" name="scor_serv" value="10">
														&nbsp; 10
													</div>
													<input type=hidden name=action value="toScore"> <input
														type=hidden name=rest_no value="${restVO.rest_no }">
												</form>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">取消</button>
												<button type="button" class="btn btn-primary" id="sendScore">送出評分</button>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6 column">

									<span class="label label-primary">價格</span>
									<div class="progress  progress-success">

										<div class="progress-bar progress-bar-success"
											style="width: ${(restVO.scor_pri)/(restVO.scor_pritms)*10}%"></div>
									</div>
									<span class="label label-primary">料理</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success"
											style="width: ${(restVO.scor_cook)/(restVO.scor_cooktms)*10}%"></div>
									</div>
									<span class="label label-primary">衛生</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success"
											style="width: ${(restVO.scor_hea)/(restVO.scor_heatms)*10}%"></div>
									</div>
									<span class="label label-primary">環境</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success"
											style="width: ${(restVO.scor_envisco)/(restVO.scor_envtms)*10}%"></div>
									</div>
									<span class="label label-primary">服務</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success"
											style="width: ${(restVO.scor_serv)/(restVO.scor_servtms)*10}%"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<!-- Button trigger modal -->
						<h2 class="text-left">
							評價留言
							<button type="button" class="btn btn-default btn-lg"
								data-toggle="modal" data-target="#myModal">我要留言</button>
						</h2>

						<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="exampleModalLabel">我要留言</h4>
									</div>
									<div class="modal-body">
										<form>
											<div class="form-group">
												<label for="recipient-name" class="control-label">大名:</label>
												<input type="text" class="form-control" id="msg_name">
											</div>
											<div class="form-group">
												<label for="message-text" class="control-label">留言內容:</label>
												<textarea class="form-control" id="msg_cont"></textarea>
											</div>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">取消</button>
										<button type="button" class="btn btn-primary" id="sendMsg">送出留言</button>
									</div>
								</div>
							</div>
						</div>
						<div class="sm_ad_block ">
							<div class="list-group pre-scrollable ">
								<c:forEach var="msgVO" items="${msgVOList}">
									<div class="list-group-item list-group-item-danger">
										${msgVO.msg_name} 說：</br> ${msgVO.msg_cont}
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<c:forEach var="introVO" items="${introVOList}">
					<div class="row clearfix ">
						<div class="col-md-12 column">
							<div class="info_block">
								<div class="row clearfix ">
									<div class="col-md-7 column">
										<img class="img-rounded" width=100%
											src="<%=request.getContextPath()%>/intro/ShowIntroImg?intro_no=${introVO.intro_no }">
									</div>
									<div class="col-md-4 column ">
										<h4>${introVO.intro_cont }</h4>
										<p>
											<a class="btn" href="#">View details »</a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>

				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

