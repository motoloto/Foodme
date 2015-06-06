<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="col-md-3 column"
						style="border-right: #E4E4E4 1px solid">
						<a href="<%=request.getContextPath()%>/store/dish/listAllDish.jsp">
						<button type="button" class="btn btn:hover">菜單管理</button>
						</a>
						<br>
						<br>
						<a href="<%=request.getContextPath()%>/store/reservation/queryReservation.jsp">
						<button type="button" class="btn btn:hover">訂位狀態管理</button>
						</a>
						<br>
						<br>
						<a href="<%=request.getContextPath()%>/store/infomation/restDataManagement.jsp">
						<button type="button" class="btn btn:hover">餐廳資訊管理</button>
						</a>
						<br>
						<br>
						<a href="<%=request.getContextPath()%>/store/intro/introMgr.jsp">
						<button type="button" class="btn btn:hover">餐廳介紹管理</button>
						</a>
						<br>
						<br>
						<form method="post" action="<%=request.getContextPath()%>/act/act.do">
                     <button type="submit" class="btn btn:hover">活動管理</button>
    <input type="hidden" name="rest_no" value="${restVO.rest_no}"> <%-- 理應由登入資料取得來辨識哪家餐廳，測試先寫死 --%>
    <input type="hidden" name="action" value="listALLCopByRest_no">
                     </form>	
						<br><br>
						<form method="post" action="<%=request.getContextPath()%>/cop/cop.do">
                     <button type="submit" class="btn btn:hover">餐券管理</button><br><br>
    <input type="hidden" name="rest_no" value="${restVO.rest_no}"> 
    <input type="hidden" name="action" value="listCop_ByCompositeQuery">
    <input type="hidden" name="cop_date" >
                     </form>	
						<br>
						<br>
						<a href="<%=request.getContextPath()%>/store/index.jsp">
						<button type="button" class="btn btn:hover">首頁</button>
						</a>
						<br>
						<br> <a
										href="<%=getServletContext().getContextPath()%>/storeLogin/storeLogin.do?action=logout"><button
											type='button' class='btn btn-default'>登出</button></a>
						<div>
							現在時間：<br>
							
						<!--執行disptime();-->

							<span id="myclock"></span>
						</div>

					</div>