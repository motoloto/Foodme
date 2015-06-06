<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file"%>
</head>

<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

<div class="container">
	<div class="row clearfix">
		<div class="col-md-4 column">
		</div>
		<div class="col-md-4 column">
			<h3 class="text-center text-muted">
				會員登入
				
			</h3>
		</div>
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
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-4 column">
		</div>
		<div class="col-md-4 column">
			<form role="form" ACTION="<%=request.getContextPath()%>/memLogin/memLogin.do" METHOD=POST>
				<div class="form-group">
					 <label for="exampleInputEmail1">帳號</label><input type="text" class="form-control" name="mem_account" id="exampleInputEmail1" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">密碼</label><input type="password" class="form-control" NAME="mem_pwd" id="exampleInputPassword1" />
				</div>
				<INPUT TYPE=hidden name="action" VALUE="login">
				<div class="checkbox">
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input type="submit" class="btn btn-info" value="登入"> <br><br><br>
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 </span><input type="button" class="btn btn-danger" value="會員註冊" onclick="window.location='<%=request.getContextPath() %>/front/member/addMem.jsp'" >
				</div>
			</form>
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
</div>
				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
