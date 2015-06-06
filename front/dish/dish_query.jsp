<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IBM Emp: Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/jquery-1.7.js"
    type="text/javascript"></script>
<script
    src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
    type="text/javascript"></script>
<link
    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
    rel="stylesheet" type="text/css" />
<script>



$(document).ready(function() {
    $("input#dish_cont").autocomplete({
        width: 300,
        max: 10,
        delay: 100,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response) {
            $.ajax({
                url: "<%=request.getContextPath()%>/front/dish/dish.do",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: {
                	 term : request.term,
                	 action : "autoCommit"             
                	},
                success: function( data, textStatus, jqXHR) {
                    console.log( data);
                   for(var i=0;i<data.length;i++){
                    	data[i]=decodeURI(data[i]);		// 在jsp加上 decode
                    }
                    var items = data;
                    response(items);
                },
                error: function(jqXHR, textStatus, errorThrown){
                     console.log( textStatus);
                }
            });
        }
 
    });
});
</script>

</head>
<body bgcolor='white'>

	<h3>料理查詢:</h3>
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

	<ul>		
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/front/dish/listQueryResult.jsp">
				<b>輸入欲查詢之料理名稱 (如"牛肉麵"):</b> 
				<input type="text" name="dish_cont"	id="dish_cont" value="">				 
				<input type="submit" value="送出">
			</FORM> 
</body>
</html>
