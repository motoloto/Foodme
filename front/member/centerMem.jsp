<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.odr.model.*"%>
<%@ page import="com.rest.model.*"%>
<%@ page import="com.reserv.model.*"%>
<%@ page import="com.collection.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="copSvc" scope="page" class="com.cop.model.CopService" />
<jsp:useBean id="rs" scope="page" class="com.rest.model.RestService"></jsp:useBean>
<%!int i = 0;%>
<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");                  // 從 session內取出(key) account的值
	Reservation_recordService reservSrv = new Reservation_recordService();
	List<Reservation_recordVO> reservRecoredList = reservSrv
			.getReservationRecord(memVO.getMem_no());
	pageContext.setAttribute("reservRecoredList", reservRecoredList);
%>
<!-- 取得會員的購買紀錄 -->
<%
	OdrService odrSvc = new OdrService();
	List<OdrVO> mem_shoplist = odrSvc.getMemOdr(memVO.getMem_no());
	pageContext.setAttribute("mem_shoplist",mem_shoplist);
%>
<!-- 取得會員的收藏清單 -->
<%
	CollectionService clcSvc = new CollectionService();
	List<CollectionVO> mem_clclist = clcSvc.getOneCollection(memVO.getMem_no());
	pageContext.setAttribute("mem_clclist",mem_clclist);
%>


<%@ include file="/front/pageDesign/designSrc.file"%>
<style>
</style>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/css/bootrap-datepicker.css"></script> --%>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/js/jquery-1.10.2.js"></script> --%>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/js/jquery.confirm.js"></script> --%>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/js/jquery.confirm.min.js"></script> --%>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/js/jquery-ui.js"></script> --%>
<%-- <script src="<%=getServletContext().getContextPath()%>/front/css/jquery-ui.css"></script> --%>

<script type="text/javascript">
	function checkReservation() {
		if (confirm("是否取消訂位？")) {
			alert("訂位已取消");
			return true;
		} else {
			alert("訂位未取消");
			return false;
		}
	}
</script>

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
							<table class="table table-bordered ">
								<thead>
									<tr>
										<th height="73" colspan="4" bgcolor="#F0F0F0"><h2>會員資料</h2></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td width="32%" rowspan="2"><p>&nbsp;</p>
											<p align="center">
												<img
													src="<%=getServletContext().getContextPath()%>/mem/mem.showImg?mem_account=${memVO.getMem_account()}"
													width="160" height="145" align="absmiddle">
											</p></td>
										<td height="131" colspan="3"><p>
												姓名： <label id="userName"><%=memVO.getMem_name()%></label>
											</p>
											<p>
												暱稱： <label id="nickName"><%=memVO.getMem_nickname()%></label>
											</p>
											<p>
												電話： <label id="msisdn"><%=memVO.getMem_phone()%></label>
											</p>
											<p>
												Email： <label id="email"><%=memVO.getMem_mail()%></label>
											</p></td>
									</tr>
									<tr>
										<td align="center" valign="middle">
											<div align="center">
												<form method="post"
													action="<%=request.getContextPath()%>/front/member/updateMem.jsp">

													<input type=hidden name="action" value="getOne_For_Update">
													<input type=submit value="修改會員資料">
											</form>
											</div>
											<div align="center"></div></td>
										<td align="center" valign="middle">
										<div align="center">
										<a href="<%=request.getContextPath()%>/front/member/updateMemPwd.jsp">密碼變更</a></div>	<div align="center"></div></td>
		

										<td align="center" valign="middle"><div align="center">
												<form method="post"
													action="<%=getServletContext().getContextPath()%>/memLogin/memLogin.do">

													<input type=hidden name="action" value="logout"> <input
														type=submit value="登出">
										</form>
											</div>
											<div align="center"></div></td>
									</tr>
								</tbody>
							</table>

						</div>
						<p>&nbsp;</p>
					</div>
					<%
						i = 1;
					%>

					<div class="row clearfix">
						<div class="col-md-12 column">
							<div align="center">
								<table class="table table-bordered ">
									<thead>
										<tr>
											<th colspan="6" bgcolor="#F0F0F0"><h2>
													<a href=>訂位紀錄</a>
												</h2></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><div align="center"></div></td>
											<td>
												<div align="center">訂位餐廳</div>
											</td>
											<td>
												<div align="center">訂位時間</div>
											</td>
											<td>
												<div align="center">訂位時段</div>
											</td>
											<td>
												<div align="center">訂位人數</div>
											</td>
											<td>
												<div align="center">取消訂位</div>
											</td>
										</tr>
										<c:forEach var="reservation_recordVO"
											items="${reservRecoredList}">
											<tr>
												<td>
													<div align="center"><%=i%></div>
												</td>
												<td><c:forEach var="restVO" items="${rs.all}">
														<c:if
															test="${reservation_recordVO.rest_no==restVO.rest_no}">
															<div align="center">${restVO.rest_name}</div>
														</c:if>
													</c:forEach></td>
												<td>
													<div align="center">${reservation_recordVO.reservation_day}</div>
												</td>
												<td>
												    <c:if test="${reservation_recordVO.period==1}">
													<div align="center">11:00</div>
													</c:if>
													<c:if test="${reservation_recordVO.period==2}">
													<div align="center">12:00</div>
													</c:if>
													<c:if test="${reservation_recordVO.period==3}">
													<div align="center">13:00</div>
													</c:if>
													<c:if test="${reservation_recordVO.period==4}">
													<div align="center">18:00</div>
													</c:if>
													<c:if test="${reservation_recordVO.period==5}">
													<div align="center">19:00</div>
													</c:if>
													<c:if test="${reservation_recordVO.period==6}">
													<div align="center">20:00</div>
													</c:if>	
												</td>
												<td>
													<div align="center">${reservation_recordVO.count}</div>
												</td>
												<td>
													<form method="post"
														action="<%=request.getContextPath()%>/reserv/reserv.do">
														<input type="hidden" name="rec_no"
															value="${reservation_recordVO.rec_no}"> <input
															type="hidden" name="mem_account"
															value="${memVO.mem_account}"> <input
															type="hidden" name="action" value="cancel_reservation">
														<div align="center">
															<input type="submit" class="confirm" value="取消訂位"
																onclick="return checkReservation()">
														</div>
													</form>

												</td>
											</tr><%i++; %>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<p>&nbsp;</p>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<table class="table table-bordered ">
								<thead>
									<tr>
										<th colspan="10" bgcolor="#F0F0F0"><h2>
												<a href=>購買紀錄</a>
											</h2></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td width="13%"><div align="center">訂單編號</div></td>
										<td width="9%"><div align="center">購買人姓名</div></td>
										<td width="11%"><div align="center">發行餐廳</div></td>
										<td width="15%"><div align="center">餐劵名稱</div></td>
										<td width="9%"><div align="center">購買數量</div></td>
										<td width="6%"><div align="center">金額</div></td>
										<td width="8%"><div align="center">使用次數</div></td>
										<td width="8%"><div align="center">結帳狀態</div></td>
										<td width="12%"><div align="center">訂單日期</div></td>
										<td width="12%"><div align="center">取消訂單</div></td>
										
									</tr>

									<c:forEach var="odrVO" items="${mem_shoplist}" varStatus="s">

										<tr>
											<td>
												<div align="center">${odrVO.odr_no}</div>
											</td>
											<td>
												<div align="center">${odrVO.odr_payname}</div>
											</td>
											<td>
												<div align="center">${odrVO.rest_name}</div>
											</td>
											<td><div align="center">
													<c:forEach var="copVO" items="${copSvc.all}">
														<c:if test="${odrVO.cop_no==copVO.cop_no}">
	                                ${copVO.cop_name}
                                </c:if>
													</c:forEach>
												</div></td>
											<td><div align="center">${odrVO.odr_buyamt}</div></td>
											<td><div align="center">${odrVO.odr_toprc}</div></td>
											<td><div align="center">${odrVO.odr_usdtms}</div></td>
											<td><div align="center">
													<c:if test="${odrVO.odr_state.equals('1')}">
														<img src="<%=request.getContextPath()%>/front/img/ok.png">
													</c:if>
													<c:if test="${odrVO.odr_state.equals('0')}">
														<img src="<%=request.getContextPath()%>/front/img/not.png">
													</c:if>
												</div></td>
											
											<td><div align="center">${odrVO.odr_time}</div></td>
											<td><div align="center">
											   <c:if test="${odrVO.odr_state.equals('0')}">
											       <form method="post" action="<%=request.getContextPath()%>/odr/odr.do">
											       <input type="submit" value="取消訂單">
											       <input type=hidden  name="odr_no" value="${odrVO.odr_no}">
											       <input type=hidden name=action value="delete">
											       </form>
											    </c:if>
											</div></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p>&nbsp;</p>
							<table class="table table-bordered ">
								<thead>
									<tr>
										<th colspan="4" bgcolor="#F0F0F0"><h2>
												<a href=>餐廳收藏</a>
											</h2></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<div align="center"></div>
										</td>
										<td><div align="center">餐廳名稱</div></td>
										<td>
											<div align="center">平均評分</div>
										</td>
										<td>
											<div align="center"></div>
										</td>
									</tr>
									<c:forEach var="clcVO" items="${mem_clclist}" varStatus="s">
										<tr>
											<td>
												<div align="center">${s.count}</div>
											</td>
											<td>
												<div align="center">
													<a
														href="<%=request.getContextPath()%>/front/restaurant/fe_restInfo.jsp?rest_no=${clcVO.rest_no}">
														<c:forEach var="restVO" items="${rs.all}">
															<c:if test="${clcVO.rest_no==restVO.rest_no}">
																<div align="center">${restVO.rest_name}</div>
															</c:if>
														</c:forEach>
													</a>
												</div>
											</td>

											<td>
												<div align="center">
													<%
														CollectionVO clcVO = (CollectionVO) pageContext
																	.getAttribute("clcVO");
															int rest_no = clcVO.getRest_no();
															RestVO restVO = (RestVO) rs.getOneRest(rest_no);
															float avg = restVO.getScor_cook() + restVO.getScor_envisco()
																	+ restVO.getScor_hea() + restVO.getScor_pri()
																	+ restVO.getScor_serv();
															int tims = restVO.getScor_cooktms() + restVO.getScor_envtms()
																	+ restVO.getScor_heatms() + restVO.getScor_pritms()
																	+ restVO.getScor_servtms();
															avg = avg / tims;
													%>
													<%=avg%>
												</div>
											</td>

											<td>
												<div align="center">
													<form method="post"
														action="<%=request.getContextPath()%>/clc/clc.do">
														<input type=submit value="移除"> <input
															type="hidden" name="action" value="delete"> <input
															type=hidden name="mem_no" value="${memVO.mem_no }">
														<input type=hidden name="rest_no"
															value="${clcVO.rest_no }">
													</form>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p>&nbsp;</p>
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
