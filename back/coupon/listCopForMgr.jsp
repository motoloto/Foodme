<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rest.model.*"%>
<%@ page import="com.cop.model.*"%>
<%@ page import="com.odr.model.*"%>
<% 
	RestService restSvc = new RestService();
	List<RestVO> list = restSvc.getAll();
	pageContext.setAttribute("rest_list",list);

%>
<jsp:useBean id="copSvc" scope="page" class="com.cop.model.CopService" />
<jsp:useBean id="odrSvc" scope="page" class="com.odr.model.OdrService" />
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>FOOD me 後端管理系統</title>
  
    <br><table>
    <tr align=left>
    <td align=left><div align=left>查詢日期: &nbsp;&nbsp; ${check_date}月</div></td>
    </tr>
    </table>

	<%@ include file="/back/pageDesign/designSrc.jsp"%>
	
	
	  
</head>

<body>

		
<!------------------以下內容---col-md-10------------------------>             
    	<div class="col-md-10  column">
             <div class="row clearfix block">
            	<div class="row clearfix">
                    <div class="col-md-12 column">
                    </div>
				</div>

				  <p align="center">
 

  </p>
				
                <div style="width:100%;" class="col-md-12 column">
                    <div align="center" >
                    
                        <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th width="5%"> <div align="center"></div></th>
                            <th width="11%">
                              <div align="center">店家編號 </div></th>
                            <th width="17%">
                              <div align="center">店家名稱 </div></th>
                           
                            <th width="9%"><div align="center">電話</div></th>
                            <th width="9%"><div align="center">E-mail</div></th>
                            <th width="14%"><div align="center">銷售總金額($NT)</div></th>
                            <th width="9%"><div align="center">手續費($NT)</div></th>
                            
                           
                     
                        <tbody>
                            <%@ include file="page1forCopMgr.file"%>                      
                         <c:forEach var="restVO" items="${rest_list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="s">
                          <tr>
                          
<!--                             此<tr>(餐廳)的專用變數 -->
                                        <%int selpre = 0;
                                          int charge =0;
                                        %>
                                      
<!--                                 開始迴圈計算此家總值 -_-  -->
                                  <c:forEach var="copVO" items="${copSvc.getCopByRest_no(restVO.rest_no)}">
                                              
                                         <c:forEach var="odrVO" items="${listOdr_ByCompositeQuery}">
                                             <c:if test="${copVO.cop_no==odrVO.cop_no}">
	                                            <%
	                                            	OdrVO odrVO = (OdrVO)pageContext.getAttribute("odrVO"); 
	                                                selpre += odrVO.getOdr_toprc();
	                                                charge = selpre /10;
	                                            %>
                                             </c:if>
                                         </c:forEach>
                                 </c:forEach>
                                  
                          
                            <td><div align="center">${s.count } </div></td>
                            <td>
                              <div align="center">${restVO.rest_no}</div></td>
                            <td>
                              <div align="center">${restVO.rest_name}</div></td>
                            <td><div align="center">${restVO.rest_tel}</div></td>
                            <td><div align="center">${restVO.rest_mail}</div></td>
                            <td><div align="center"><%=selpre %></div></td>
                            <td><div align="center" style="color:#F00;"><%=charge %></div></td>
                           
                                  </c:forEach>
                        
                          </tbody>
                        </table>
                                      <%@ include file="page2forCopMgr.file" %>
                  </div>
                </div>
            </div>
            <div class="container" align=center>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                      
                    </div>
                </div>
            </div>
			
		</div>
<!------------------以上內容--</div>----------------->   			
	
</body>
</html>
