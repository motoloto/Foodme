<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.affi.model.*"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FOOD me ��ݺ޲z�t��</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- �ޤJCSS �M JS�]�w�� -->
<%@ include file="/back/pageDesign/designSrc.jsp"%>


<%
	AffiService affiSvc = new AffiService();
	List<AffiVO> list = affiSvc.getAll();
	Collections.reverse(list);	 //�אּ�����ƦC
	pageContext.setAttribute("list", list);
%>


<title>�Ҧ��[����� - listAllAffi.jsp</title>
</head>

<body bgcolor='white'>
	<div class="container-fluid back_end">
		<div id="header_space"></div>
		<div class="row clearfix">
			<%@ include file="/back/pageDesign/be_header.jsp"%>


			<!------------------�H�U���e---col-md-10------------------------>

			<div class="col-md-10 column">
				<div class="block">
				
							<h3>�Ҧ��[���\�U���</h3>
					
				

				<%-- ���~��C --%>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>�Эץ��H�U���~:
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li>${message}</li>
							</c:forEach>
						</ul>
					</font>
				</c:if>

				<table border='1' bordercolor='#CCCCFF' width=''>
					<tr align='center' valign='middle'>
						<th width="8%">�\�U�W��</th>
						<th width="5%">�[���s��</th>
						<th>��~�s��</th>
						<th width="10%">�\�U�a�}</th>
						<th>�\�U�q��</th>
						<th>���</th>
						<th >Email</th>
						<th width="6%">�\�U���}</th>
						<th width="15%">�\�U����</th>
						<th>�\�U�ۤ�</th>
						<th width="5%">�f�d�q�L</th>
						<th width="5%">�f�d���L</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="affiVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<tr align='center' valign='middle'>
							<td>${affiVO.rest_name}</td>
							<td>${affiVO.affi_no}</td>
							<td>${affiVO.bus_no}</td>

							<td>${affiVO.rest_addr}</td>
							<td>${affiVO.rest_tel}</td>
							<td>${affiVO.rest_mobil}</td>
							<td>${affiVO.rest_mail}</td>
							<td>${affiVO.rest_web}</td>
							<td >${affiVO.rest_intro}</td>
							<td><img class="img-rounded" width="200px"
								src="<%=request.getContextPath()%>/affi/ShowAffiImg?affi_no=${affiVO.affi_no}">
							</td>
							<td>
								<c:if test="${affiVO.affi_state==0}">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/rest/rest.do"
									enctype="multipart/form-data">
			     					<input class="btn btn-info" type="submit" value="�q�L"><input type="hidden"
										name="affi_no" value="${affiVO.affi_no}">
			    					 <input
										type="hidden" name="rest_account" value="${affiVO.rest_mail}">
									<input type="hidden" name="bus_no" value="${affiVO.bus_no}">
									<input type="hidden" name="rest_name"
										value="${affiVO.rest_name}"> <input type="hidden"
										name="rest_addr" value="${affiVO.rest_addr}"> <input
										type="hidden" name="rest_tel" value="${affiVO.rest_tel}">
									<input type="hidden" name="rest_mail"
										value="${affiVO.rest_mail}"> <input type="hidden"
										name="rest_web" value="${affiVO.rest_web}"> <input
										type="hidden" name="rest_intro" value="${affiVO.rest_intro}">
									<input type="hidden" name="rest_photo"
										value="${affiVO.rest_photo}"> <input type="hidden"
										name="action" value="insert">
								</FORM>
								</c:if>
								</td>
								<td>
								<c:if test="${affiVO.affi_state==0}">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/rest/rest.do">
									<input class="btn btn-info" type="submit" value="���q�L"> <input type="hidden"
										name="affi_no" value="${affiVO.affi_no}"> <input
										type="hidden" name="action" value="return">
								</FORM>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="page2.file"%>

			</div>
</div>
			<!------------------�H�W���e--</div>----------------->
		</div>
			<div id="bottom_space"></div>
			<div id="bottom_space"></div>
	</div>
</body>
</html>
