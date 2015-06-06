<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mem.model.*"%>
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
						<div class="col-md-12 column">
							<h4>意見回覆</h4>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>請修正以下錯誤:
									<ul>
										<c:forEach var="message" items="${errorMsgs}">
											<li style="list-style-type: none">${message}</li>
										</c:forEach>
									</ul>
								</font>
							</c:if>
							<FORM METHOD="post" class="form"
								ACTION="<%=request.getContextPath()%>/advice/advice.do"
								name="form1">
								<div class="form-group">
									<label>信箱：</label> <input type="email" class="form-control"
										id="exampleInputEmail1" name="cmail" value="${cmail}"
										placeholder="請輸入您的信箱網址">
								</div>
								<div class="form-group">
									<label for="exampleInputEmail1">大名：</label> <input type="text"
										class="form-control" id="exampleInputEmail1" name="cname"
										value="${cname}" placeholder="如何稱呼">
								</div>
								<div class="form-group">
									<label for="exampleInputEmail1">意見主旨：</label> <input
										type="text" class="form-control" id="exampleInputEmail1"
										name="title" value="${title}" placeholder="請輸入意見回覆主旨">
								</div>
								<div class="form-group">
									<label for="exampleInputEmail1">意見內容：</label>
									<textarea type="email" class="form-control"
										id="exampleInputEmail1" rows="12" name="content"
										placeholder="請輸入意見回覆內容">${content}</textarea>
								</div>
								<div class="form-group">
									<input type="hidden" name="action" value="addAdvice">
									<button class="btn btn-lg btn-danger" type="submit" value="送出">送出</button>
									<label>意見送出後我們將會盡快跟您聯繫，您的回覆是我們寶貴的意見，謝謝！</label>
								</div>
							</FORM>
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
