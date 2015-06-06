<!DOCTYPE html>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>FOOD me 後端管理系統</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	
	<link href="<%=getServletContext().getContextPath()%>/back/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=getServletContext().getContextPath()%>/back/css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="img/favicon.png">
  
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=getServletContext().getContextPath()%>/back/js/scripts.js"></script>
</head>

<body >
<div class="container-fluid back_end">
	<div id="header_space"></div>
	<div class="row clearfix">
		<%@ include file="/back/pageDesign/be_header.jsp" %>
	
		
<!------------------以下內容---col-md-10------------------------> 
<div class="col-md-10  column">
				<div class="row clearfix block">
					<div class="row clearfix">            
  <div class="col-md-12 column">
	<h3 class="text-danger">訂單管理</h3>					
  </div>      
           <div align="center">
  <table border="0">
     <tr >
      <td height="50" colspan="3" align="center" valign="middle">
      <input type = "button" value="全部訂單" onclick="window.location='<%=request.getContextPath() %>/back/order/checkOrder.jsp'"></td>
      <td colspan="5" align="center" valign="middle">
      <form method="post" action="<%=request.getContextPath()%>/odr/odr.do">
      <input type="text" name="odr_no" placeholder="依訂單編號查詢" value="" size="20">
      &nbsp; 
      <input type = "hidden" name = "action" value="listOneOdr">
      <input type = "submit" value="查詢">
      </form>
      </td>
      <td colspan="3" align="center" valign="middle">
      <form method="post" action="<%=request.getContextPath()%>/odr/odr.do">
      <select name= "odr_state">
      <option value="2" selected>依結帳狀態查詢</option>
      <option value="0">未結帳</option>
      <option value="1">已結帳</option>
      </select>&nbsp;
      <input type = "hidden" name = "action" value="listOdrByState">
      <input type = "submit" value="查詢">
      </form>
      </td>
    </tr>
    <tr >
      <td width="7%" height="1" align="center" valign="middle"></td>
      <td width="10%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
      <td width="7%" align="center" valign="middle"></td>
    </tr>
  </table>
  <table class="table table-bordered"  cellspacing="0">
 
   
  </table>

 
  <jsp:include page="listOneOrder.jsp" />
 
</div>
</div>
</div>
</div>
<!------------------以上內容--</div>----------------->   			
		
	</div>
			<div id="bottom_space"></div>
		<div id="bottom_space"></div>
</div>
</body>
</html>
