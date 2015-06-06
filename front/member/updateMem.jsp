<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%
	int sex = 2;//memVO==null
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	if (memVO != null) {
		if (memVO.getMem_sex().equals("1")) {
			sex = 1;
		} else if (memVO.getMem_sex().equals("0")) {
			sex = 0;
		} else {
			sex = 2;
		}
	}
%>
<html>
<head>
<title>FOOD me 會員註冊</title>
<%@ include file="/front/pageDesign/designSrc.file"%>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});
	});
</script>
<link rel="stylesheet"
	href="<%=getServletContext().getContextPath()%>/store/css/jquery-ui.css">
<script
	src="<%=getServletContext().getContextPath()%>/store/js/jquery-1.10.2.js"></script>
<script
	src="<%=getServletContext().getContextPath()%>/store/js/jquery-ui.js"></script>
</head>

<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/mem/mem.do"
					enctype="multipart/form-data">
					<div class="container">
						<div class="row clearfix">
							<div class="col-md-12 column">
								<h3 class="text-danger">修改會員資料</h3>
							</div>
						</div>
						<div class="row clearfix">
							<div class="col-md-4 column">





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

								<fieldset>

									<!-- Form Name -->
									<legend></legend>
							</div>
							<div class="col-md-6 column"></div>
						</div>
						<div class="row clearfix">
							<div class="col-md-3 column">

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="mem_account">帳號</label>
									<div class="controls">
										<input id="account" name="mem_account" type="text" disabled
											placeholder="" class="input-xlarge" size="20" maxlength="20"
											value="<%=(memVO == null) ? "" : memVO.getMem_account()%>">

									</div>
								</div>



								<!-- Text input-->
								<div class="control-group">
									<br>
									<label class="control-label" for="name">姓名</label>
									<div class="controls">
										<input id="name" name="mem_name" type="text" placeholder=""
											class="input-small" size="5" maxlength="5"
											value="<%=(memVO == null) ? "" : memVO.getMem_name()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="nickname">暱稱</label>
									<div class="controls">
										<input id="nickname" name="mem_nickname" type="text"
											placeholder="" class="input-small" size="10" maxlength="10"
											value="<%=(memVO == null) ? "" : memVO.getMem_nickname()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="birthday">生日</label>
									<div class="controls">
										<input type="text" id="datepicker" name="mem_birthdate"
											value="<%=(memVO == null) ? "" : memVO.getMem_birthdate()%>">

									</div>
								</div>


								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="mail">電子信箱</label>
									<div class="controls">
										<input id="mail" name="mem_mail" type="email" placeholder=""
											class="input-xxlarge" size="30" maxlength="30"
											value="<%=(memVO == null) ? "" : memVO.getMem_mail()%>">

									</div>
								</div>





								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="phone">連絡電話</label>
									<div class="controls">
										<input id="phone" name="mem_phone" type="text" placeholder=""
											class="input-medium" size="10" maxlength="10"
											value="<%=(memVO == null) ? "" : memVO.getMem_phone()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="address">地址</label>
									<div class="controls">
										<input id="address" name="mem_adrs" type="text" placeholder=""
											class="input-xxlarge" size="40" maxlength="30"
											value="<%=(memVO == null) ? "" : memVO.getMem_adrs()%>">

									</div>
								</div>

								<!-- Multiple Radios -->
								<div class="control-group">
									<br> <label class="control-label" for="sex">性別</label>
									<div class="controls">

										<input type="radio" name="mem_sex" value="1"
											<%=(sex == 1) ? "checked" : ""%>> 男 &nbsp; <input
											type="radio" name="mem_sex" value="0"
											<%=(sex == 0) ? "checked" : ""%>> 女

									</div>
								</div>
								<div class="control-group">
									<br>
									<label class="control-label" for="sex">更換大頭照</label>
									<div class="controls">
										<input id="pic" name="mem_pic" class="input-file" type="file">
									</div>
								</div>				<!-- Button (Double) -->
								<div class="control-group">
									<label class="control-label" for="check"></label> <br>
									<br>
									<div class="controls">
										<input type="hidden" name="action" value="update"> <input
											class="btn btn-info" type="submit" value="送出">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input class="btn btn-danger" type="button" value="取消"
											onclick="window.location='<%=request.getContextPath()%>/front/member/centerMem.jsp'" />

									</div>
								</div>



							</div>
							<div class="col-md-8 column">
								<br>
								<br>

								<!-- File Button -->


							</div>
						</div>
					</div>
					<br> <br>
				</Form>
				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
