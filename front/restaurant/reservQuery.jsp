<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/front/pageDesign/designSrc.file"%></head>

<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function() {

	$('#datepicker').datepicker();
	$('#amount').change(function() {
		$.post( "<%=getServletContext().getContextPath()%>	/rest/rest.do", {
				action : "getOneReserv",
				reserv_date : $('#reserv_date').val(),
				amount : $('#amount').val(),
				rest_no : '7001'
			}, function(resultJson, status) {
				alert("狀態:" + status + "Data: " + resultJson.toString());
				// 			    alert("狀態:"+resultJson[1].time+"Data: " +resultJson[1].rest_residual);
				showResult(resultJson);
			});

		});
	});
	function showResult(resultJson) {
		var parsed = JSON.parse(resultJson);
		var newArray = new Array(parsed.length);
		var i = 0;
		var htmlstr = "";
		parsed
				.forEach(function(entry) {
					var time = entry.time;
					htmlstr += "<button  id=''"+time+"'  type=''submit' class='btn btn-danger' >"
							+ time + "</button>";
					i++;

				});
		$('#showPanel').html(htmlstr);
		alert(htmlstr);
	}
</script>
</head>
<body>
	<input type="text" id="datepicker1" />
	<div class="col-md-6 column">
		<h2>7001的訂位資訊</h2>
		<div class="sm_ad_block">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">日期</label>
							<div class="col-sm-10">
								<%
									java.sql.Date date_SQL = new java.sql.Date(
											System.currentTimeMillis());
									java.sql.Date date_SQL7 = new java.sql.Date(
											System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000);
								%>
							</div>
								<input id="reserv_date" type="date" name="reserv_date"
									value="<%=date_SQL%>" />
									 <input data-provide="datepicker"><input type="text"  id="datepicker">
									 <div id="datetimepicker"></div>
						</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">人數</label>
					<div class="col-sm-10">
						<select id="amount" class="form-control">
							<option>1</option>
							<option selected>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
							<option>6</option>
							<option>7</option>
							<option>8</option>
							<option>9</option>
							<option>10</option>
							<option>18</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">時段</label>
					<div class="col-sm-offset-2 col-sm-10" id="showPanel"></div>
				</div>

				</form>
			</div>
		</div>
	</div>
	</div>
</body>
</html>