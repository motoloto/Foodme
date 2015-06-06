<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rest.model.*"%>
<%@ page import="com.cop.model.*"%>
<%@ page import="com.odr.model.*"%>

<jsp:useBean id="copSvc" scope="page" class="com.cop.model.CopService" />
<jsp:useBean id="odrSvc" scope="page" class="com.odr.model.OdrService" />
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>FOOD me 後端管理系統</title>

	<%@ include file="/back/pageDesign/designSrc.jsp"%>
	
	
	  <script>
      function fun() {
        
        	var year = document.getElementById("year").value;
        	var month = document.getElementById("month").value;
        var	s=document.getElementById("odr_paytime").value = year+"-"+month;  
      }
    </script>
	
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
                        <h3 class="text-danger">
                        查詢餐劵資料
                        </h3>
                    </div>
				</div>
				
				  <p align="center">
  
    <div align="center">
  <table border=0><tr>
   <td>
      <select name="year" id="year">
        
　                <option value=""></option>
        <option value="2014">2014</option>
        <option value="2015" selected>2015</option>
      </select> 年
    </td>  
    <td>
      <select name="month" id="month">
        
         <option value=""></option>
        <option value="01" selected>1</option>
        <option value="02">2</option>
        <option value="03">3</option>
        <option value="04">4</option>
        <option value="05">5</option>
        <option value="06">6</option>
        <option value="07">7</option>
        <option value="08">8</option>
        <option value="09">9</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
      </select> 月
     </td>
    <td> 
  <form method="post" name="date_choose" action="<%=request.getContextPath()%>/odr/odr.do">
      <input type = submit value = "查詢" onClick="fun()">
      <input type = hidden name="action" value = "findOdrByDate" >
      <input type = hidden name="odr_paytime" id="odr_paytime" value = "">
  </form>
  </td>
      </tr></table> 
      
      
        <%if (session.getAttribute("listOdr_ByCompositeQuery")!=null){%>
<jsp:include page="listCopForMgr.jsp" />
<%} %> 
    </div>



  </p>
				
                <div class="col-md-12 column">
                    <div align="center" >
                    
            
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
	</div>
</div>


</body>
</html>
