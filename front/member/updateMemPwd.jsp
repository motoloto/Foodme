
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>

<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

<div class="container">
	<div class="row clearfix">
		<div class="col-md-4 column">
		</div>
		<div class="col-md-4 column">
			<h3 class="text-center text-danger">
				修改密碼
			</h3>
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-4 column">
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
			<form role="form" method="post" action="<%=request.getContextPath() %>/mem/mem.do">
				<div class="form-group">
					 <label for="exampleInputEmail1">舊密碼</label><input type="password" name=mem_pwd class="form-control" id="exampleInputEmail1" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">新密碼</label><input type="password" name=new_pwd1 class="form-control" id="exampleInputPassword1" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">請再輸入一次新密碼</label><input type="password" name=new_pwd2 class="form-control" id="exampleInputPassword1" />
				</div>
				<input type=hidden name=action value="UpdateMem_Pwd">
				<input type=hidden name=mem_account value="${memVO.mem_account }">
				
				<button type="submit" class="btn btn-info">確定更改</button>
				<button type="button" class="btn btn-danger" onclick="window.location='<%=request.getContextPath() %>/front/member/centerMem.jsp'">取消</button>
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
