<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row clearfix  hearder" id="hearder_bar">
	<div class="col-md-12 column" id="hearder_bar">
		<div class="row clearfix">
			<div class="col-md-3 column">
				<a href="<%=getServletContext().getContextPath()%>/front/index.jsp"><img
					width="250" height="150" alt="140x140"
					src="<%=getServletContext().getContextPath()%>/front/img/LOGO.png"
					class="img-circle" id="logo"></a>
			</div> 
			<div class="col-md-6 column">
				<div class="page-title">
					<h1>
						線上餐廳訂位系統 <span></span>
					</h1>
				</div>
			</div>
			<div class="col-md-3 column">
				<div class="form-group">

					<div class="col-sm-10 " align="right">
						<form class=" form-inline">
							<div class="form-group" align="right">
								<!-- 							<input type="text" class="form-search" id="searchInput" -->
								<!-- 								placeholder="搜尋餐廳的名稱"><span -->
								<!-- 								class="glyphicon glyphicon-search"></span></input> -->

								<c:if test="${sessionScope.memVO==null }">
									<button type='button' class='btn btn-default'
										data-toggle='modal' data-target='#loginModal'>會員登入</button>
									<a href="<%=request.getContextPath() %>/front/member/addMem.jsp"><button type='button' class='btn  btn-default '>會員註冊</button></a>
								</c:if>
								<c:if test="${sessionScope.memVO!=null }">
									<label >歡迎! 	${sessionScope.memVO.mem_nickname}</label>
									<a
										href="<%=getServletContext().getContextPath()%>/memLogin/memLogin.do?action=logout"><button
											type='button' class='btn btn-default'>登出</button></a>
								</c:if>
							</div>
						</form>

					</div>
				</div>
				<div class="form-group"></div>
			</div>
		</div>
		<div class="row clearfix" id="navbar">

			<div class="col-md-2 column headerbutton">
				<a href="<%=getServletContext().getContextPath()%>/front/about/aboutUs.jsp" class="btn btn-danger btn-block" type="button">關於</a>
			</div>
			<div class="col-md-2 column headerbutton">
			<form method="post" class="form-inline" 	action="<%=request.getContextPath()%>/rest/rest.do">
					<%
						java.sql.Date date_SQL = new java.sql.Date(	System.currentTimeMillis());
												%>
				<input type="hidden" name="reserv_date" value="<%=date_SQL%>">
				<input type="hidden" name="reserv_count" value="2">
				<input type="hidden" name="action" value="getAllReserv">
				<button  class="btn btn-danger btn-block" type="submit" >餐廳訂位</button>
			</form>
			</div>
			<div class="col-md-2 column headerbutton">
				<a
					href="<%=getServletContext().getContextPath()%>/front/member/centerMem.jsp"
					class="btn btn-danger btn-block" type="button">會員專區</a>
			</div>
			<div class="col-md-2 column headerbutton">
				<a href="<%=getServletContext().getContextPath()%>/front/affi/addAffi.jsp" class="btn btn-danger btn-block" type="button">加盟申請</a>
			</div>
			<div class="col-md-2 column headerbutton">
				<a href="<%=getServletContext().getContextPath()%>/front/qna/commonQuestion.jsp" class="btn btn-danger btn-block" type="button">常見問題</a>
			</div>
			<div class="col-md-2 column headerbutton">
				<a href="<%=getServletContext().getContextPath()%>/front/advice/addAdvice.jsp" class="btn btn-danger btn-block" type="button">意見回饋</a>
			</div>
		</div>

	</div>
</div>
<div class="modal fade modal-sm " id="loginModal" tabindex="-1" role="dialog" 
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm"  style="width:300px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">會員登入</h4>
			</div>
			<div class="modal-body ">
				<form method="POST"
					action="<%=getServletContext().getContextPath()%>/memLogin/memLogin.do">
					<div class="form-group">
						<label for="recipient-name" class="control-label">帳號：</label> <input
							type="text" class="form-control" name="mem_account">
					</div>
					<div class="form-group">
						<label for="message-text" class="control-label ">密碼：</label> <input
							type="password" class="form-control" name="mem_pwd">
					</div>
					<div class="modal-footer">
						<input type="hidden" name="action" value="login">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary" id="login">登入</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
