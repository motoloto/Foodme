<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.restClass.model.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	ClassService classSvc = new ClassService();//step 1
	List<ClassVO> list = classSvc.getAll();// step 2 該list在下面可直接利用JSP程式存取，但不可用EL存取，因不是用隱含物件或標籤函式庫產生變數
	//pageContext.setAttribute("list",list);//step 3：設定list的scope，讓下面的JSP可以找到；不設就可能找不到
	//第10行的寫法是搭配EL在做的，因為EL不能直接存取在JSP裡宣告的變數，必須先利用JSP提供的隱含變數，EL才可存取JSP裡的變數
%>
<!DOCTYPE html>
<html>
<head>
<title>FOOD me 後端管理系統</title>
<%@ include file="/back/pageDesign/designSrc.jsp"%>
</head>

<body>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>

			<!------------------以下內容---col-md-10------------------------>
			<div class="col-md-10  column">
				<div class="row clearfix block">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<h3 class="text-danger">主題推薦管理</h3>

						</div>

					</div>
					<div class="col-md-12 column">
						<form class="form-inline"
							action="<%=request.getContextPath()%>/class/class.do"
							METHOD="POST">
							<div class="form-group">
								<input name="class_name" class="form-control"
									placeholder="輸入所要新增的主題">
							</div>
							<div class="form-group">
								<input type="hidden" name="action" value="insert">
								<button class="btn btn-large btn-primary" type="submit">新增主題</button>
							</div>
						</form>
						<div align="center">
							<table class="table table-bordered">
								<thead>
									<tr class="success">
										<th width="10%">
											<div align="center">類別編號</div>
										</th>
										<th width="20%">
											<div align="center">類別名稱</div>
										</th>
										<th width="20%" colspan="2">
											<div align="center">操作</div>
										</th>
									</tr>
								</thead>
								<tbody>

									<!--在標籤函式庫裡宣告的變數，可直接為EL使用-->
									<c:forEach var="classVO" items="<%=list%>">

										<tr>
											<td><div align="center" >${classVO.class_no}</div></td>

											<td><div align="center" id="class_${classVO.class_no}">${classVO.class_name}</div></td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/class/class.do">
													<input type="submit" value="修改"> <input
														type="hidden" name="class_no" value="${classVO.class_no}">
													<input type="hidden" name="class_name"
														value="${classVO.class_name}"> <input
														type="hidden" name="action" value="update_list">
												</FORM>
											</td>
											<td>
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/class/class.do">
													<input type="submit" value="刪除"> <input
														type="hidden" name="class_no" value="${classVO.class_no}">
													<input type="hidden" name="class_name"
														value="${classVO.class_name}"> <input
														type="hidden" name="action" value="delete">
												</FORM>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
			<!------------------以上內容--</div>----------------->

		</div>
	</div>
</body>
<script type="text/javascript">
	$(document.ready)
</script>
</html>

