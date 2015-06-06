<!-- jsp檔名一定要用英文，否則會404錯誤 -->
<!-- 遇到Could not publish to the server java.lang.NullPointerException問題：eclipse重開即可-->
<%@page import="javax.print.attribute.standard.PageRanges"%>
<%@page import="com.intro.model.IntroVO"%>
<%@page import="com.intro.model.IntroService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!-- 不加的話頁面會亂碼 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%> 
<%@ page import="com.rest.model.*"%>
<%
	RestVO restVO=(RestVO)session.getAttribute("restVO");
IntroService introSrv=new IntroService();
List<IntroVO> introVOList=introSrv.getAllByARest(restVO.getRest_no());
pageContext.setAttribute("introVOList", introVOList);
%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>FOODME店家管理頁面</title>
<%@ include file="/store/pageSource/styleSource.jsp"%>
<script>
	$(function() {
		$(".sortable").sortable({
			cursor : "move",
			opacity : 0.6, //拖动时，透明度为0.6
			revert: true,   

			update : function(event, ui) { //更新排序之后
				var intro_noList=new Array;
				  $(".sortable .introBlock").each(function(){
					  intro_noList.push($(this).children("input.intro_no").val());
			        })
// 				alert(intro_noList);
					$.post( "<%=getServletContext().getContextPath()%>/intro/intro.do",
												{
													action : "sortIntro",
													rest_no : "${restVO.rest_no}",
													intro_no : intro_noList
															.toString()
												},
												function(resultJson, status) {
													// 										alert("狀態:" + status + "Data: " + resultJson.toString());

												});
							}
						});
	});
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
						<div>
							<a href="<%=request.getContextPath()%>/store/intro/addIntro.jsp"><button>新增介紹文章</button></a>
						</div>
						<div>
							<a><label>按住文章區塊拖曳可修改文章排序</label> <a>
						</div>
						<div class="sortable">
							<c:forEach var="introVO" items="${introVOList}">
								<div class="ui-state-default introBlock">
									<input type="hidden" class="intro_no"
										value="${introVO.intro_no}">
									<div class="row clearfix">
										<div class="col-md-7 column">
											<img class="img-rounded" width=100%
												src="<%=request.getContextPath()%>/intro/ShowIntroImg?intro_no=${introVO.intro_no }">
										</div>
										<div class="col-md-4 column">${introVO.intro_cont }</div>
										<div class="col-md-1 column">
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/intro/intro.do">
												<input type="hidden" name="rest_no"
													value="${restVO.rest_no}">
												<button class="btn-sm  btn-success" type="submit" value="修改">修改內容</button>
												<input type="hidden" name="intro_no"
													value="${introVO.intro_no}"> <input type="hidden"
													name="action" value="getOne_For_Update">
											</form>
											<hr>
											<form method="POST"
												action="<%=request.getContextPath()%>/intro/intro.do">
												<input type="hidden" name="rest_no"
													value="${restVO.rest_no}"> <input type="hidden"
													name="action" value="delete"> <input type="hidden"
													name="intro_no" value="${introVO.intro_no }">
												<button class="btn-sm  btn-warning" type="submit">刪除文章</button>
											</form>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<!-- 							<ul class="sortable"> -->
						<!-- 								<li class="ui-state-default">Item 1--ifxoxo.com</li> -->
						<!-- 								<li class="ui-state-default">Item 2--ifxoxo.com</li> -->
						<!-- 								<li class="ui-state-default">Item 3--ifxoxo.com</li> -->
						<!-- 							</ul> -->

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
