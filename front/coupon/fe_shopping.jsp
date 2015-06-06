<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/front/pageDesign/designSrc.file" %>
 <%@ page import="com.mem.model.*"%>
 <%@ page import="com.odr.model.*"%>
<jsp:useBean id="copVO" scope="session" class="com.cop.model.CopVO" />
<%-- <jsp:useBean id="memVO" scope="session" class="com.mem.model.MemVO" /> --%>
 
 <%
    MemVO memVO = (MemVO) session.getAttribute("memVO");                 // 從 session內取出(key) account的值
    if (memVO == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      session.setAttribute("location", request.getRequestURI());       //*工作1 : 順便記下目前位置 , 以便於login.html登入成功後 , 能夠直接導至此網頁(須配合LoginHandler.java)
      response.sendRedirect(request.getContextPath()+"/front/memLogin.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
%> 

<%
	//int sex = 2;//memVO==null
	OdrVO ordVO = (OdrVO) request.getAttribute("odrVO");

%>

 <% 
 	int sex = 2;//memVO==null
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
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8">
  <title>FOOD me 線上餐廳訂位平台</title>
  <%@ include file="/front/pageDesign/designSrc.file"%></head>
 <script>
 $(document).ready(function(){
  $('#amount').change(function() {
	  $.post( "<%=getServletContext().getContextPath()%>/odr/odr.do", {
			action : "getOdr_tlprc",
			amount : $('#amount').val(),
			cop_price: <%=copVO.getCop_price()%>
		}, function(resultJson, status) {
			showOdr_tlprc(resultJson);
		});
		})
		
		$('#paymode').change(function() {
			  $.post( "<%=getServletContext().getContextPath()%>/odr/odr.do", {
					action : "getPayMode_content",
					paymode : $('#paymode').val()
			  }, function(resultJson, status) {
					showPayModeContent(resultJson);
				});
		        })
	 
 }
		 
 );
  function showOdr_tlprc(resultJson) {
		var parsed = JSON.parse(resultJson);
	
		var ord_tlprc=parsed.ord_tlprc;
		
		$('#tlprc').html(ord_tlprc);
	};
  function showPayModeContent(resultJson) {
		var parsed = JSON.parse(resultJson);
	
		var paymode_content=parsed.paymode_content;
		
		$('#paymode_content').html(paymode_content);
	};
  
  </script> 
  </head>

<body>
<div class="container">
	<div class="row clearfix page_container">
    	<div class="col-md-12 column">
        <!--  HEADER的開始-->

			<jsp:include page="/front/pageDesign/fe_header.jsp"  flush="true"/>
        <!--HEADER的結束-->    			
			
<!---------- 以下為網頁內容------------->   
<Form method=Post action="<%=request.getContextPath()%>/odr/odr.do" > 

            <div style= background-color:white;width:900px;>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <h3 class="text-danger text-center">
                            購物明細
                        </h3>
                    </div>
                </div>
                <div  style="border:1px solid #dddddd ;">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <p style=" font-size:20px;">
                          <b>品名:</b> &nbsp&nbsp&nbsp  ${copVO.cop_name}
                        </p>
                        <p>
                            <a class="btn" href="#"></a>
                        </p>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <p style=" font-size:20px;">
                           <b>單價:</b> &nbsp&nbsp&nbsp  ${copVO.cop_price}
                        </p>
                        <p>
                            <a class="btn" href="#"></a>
                        </p>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <p style=" font-size:20px;">
                            <b>數量:</b> &nbsp&nbsp&nbsp
                                <select name="odr_buyamt" id = amount>
                                <option value="1" selected>1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                </select>
                            </p>
                            <p>
                                <a class="btn" href="#"></a>
                           	 </p>
                        </div>
                    </div>
				<div class="row clearfix">
                    <div class="col-md-12 column">
                        <p style=" background-color:#FF3333;width:500;font-size:26px; color:#FFF">
                            <b>合計:</b> &nbsp&nbsp&nbsp  <span id="tlprc">${copVO.cop_price}</span> 
                        </p>
                        <p>
                            <a class="btn" href="#"></a>
                        </p>
						</div>
					</div>
		
				</div>
			</div>
		<div style="background-color:white; width:900px;" >
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <h3 class="text-center text-primary">
                        結帳<hr>
                    </h3>
					</div>
				</div>
				
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
							
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <h3>
                        &nbsp 填寫付款人資料
                    </h3>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
<!--                     <p> -->
<!--                         &nbsp <input type = radio name = memberinfo value=yes>同會員資料 &nbsp &nbsp &nbsp立即登入 -->
<%--                         <button type="button"><img src = "<%=getServletContext().getContextPath() %>/front/img/LOGO.png" width=50 height=50></button> --%>
<%--                         <button type="button"><img src = "<%=request.getContextPath() %>/front/img/fb.jpg" width=50 height=50></button> --%>
<!--                     </p> -->
                    <p>
                        <a class="btn" href="#"></a>
                    </p>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <p>
                        &nbsp 真實姓名: <input type = text name="odr_payname" size=8 value="${memVO.mem_name}"> 
                        <input type = radio name = sex value=male <%=(sex == 1) ? "checked" : ""%>>男 
                        <input type = radio name = sex value=female<%=(sex == 0) ? "checked" : ""%>>女
                    </p>
                    <p>
                        <a class="btn" href="#"></a>
                    </p>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <p>
                        &nbsp 電子信箱:<input type = text name="odr_mail" size=30 value="${memVO.mem_mail}"> 
                    </p>
                    <p>
                        <a class="btn" href="#"></a>
                    </p>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <p>
                        &nbsp 連絡電話:<input type = text name="odr_phone" size=10 value="${memVO.mem_phone}"> 
                    </p>
                    <p>
                        <a class="btn" href="#"></a>
                    </p>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <p>
                        &nbsp 付款方式:
                        <select name = odr_paymode id=paymode>
                        <option value="1">信用卡</option>
                        <option value="2" selected>ATM轉帳</option>
                        <option value="3">現金</option>
                        
                        </select>
                    </p>
                    <p>
                        <a class="btn" href="#"></a>
                        </p>
					&nbsp
                    <div id="paymode_content" style="border:1px solid gray ;background-color:white;width:500px;">
                        <dl>
                            <dt>
                                Description lists
                                您已選擇ATM轉帳付款 方式付款。<br>
                                本公司匯款轉帳帳號資料如下<br>
                                上海商業儲蓄銀行 中和分行<br>
                                機構代號：011<br>
                                匯款帳號：33-111-000026323<br>
                                戶名：YA101_05<br>
             ATM匯款採人工對帳，於週一～週五下午2點前完成匯款，餐券將於當日寄送。<br>
                                如下午2點以後完成匯款，餐券將於隔日為您確認寄出。如急需使用餐券，<br>
                
               建議採用其它付款方式或歡迎來電：02-7788-9563。
                            	</dt>
                      		</dl>
						</div>
					</div>
				<br>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                      
                        &nbsp &nbsp 
                        <input type="hidden" name="action" value="insert"> 
                        <input type="hidden" name="cop_no" value="${copVO.cop_no}"> 
                        <input type="hidden" name="cop_price" value="${copVO.cop_price}"> 
                        <input type="hidden" name="rest_no" value="${rest_no}">
                        <input type="hidden" name="cop_name" value="${copVO.cop_name}">
                        <input type="hidden" name="mem_no" value="${memVO.mem_no}"> 
						<input class="btn btn-info" type="submit" value="送出">
						&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
						<input class="btn btn-danger" type="button" value="取消" onclick="window.location='<%=request.getContextPath() %>/front/restaurant/fe_restInfo.jsp?rest_no=${rest_no}'">
                    <br>
                    <br>
                    </div>
				</div>
			</div> 
		</div>
</Form>
<!---------- 以上為網頁內容----------->    			
			<div class="row clearfix">
				<div class="col-md-12 column">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
