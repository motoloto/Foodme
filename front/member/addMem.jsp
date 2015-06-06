<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%
	int sex = 2;//memVO==null
	MemVO memVO = (MemVO) request.getAttribute("memVO");
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

<html>
<head>
<title>FOOD me 會員註冊</title>
<%@ include file="/front/pageDesign/designSrc.file"%>

<script>
$(document).ready(function(){
	$( "#reg" ).click(function() {
		$('#mem_account').val("foodme01");
		$('#mem_pwd').val("aaaa1111");
		$('#mem_name').val("張大明");
		$('#mem_birthdate').val("1991-04-17");
		$('#mem_mail').val("shine5603@gmail.com");
		$('#mem_phone').val("0953592760");
		$('#mem_adrs').val("台中市烏日區中山路二段56號");
		$("#mem_sex").attr("checked",true);
		$("#mem_clause").attr("checked",true);
		
		 
		});
	});
</script>

<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});
	});
	
</script>
 <SCRIPT type="text/javascript">
       <!-- 此check()函式在最後的「傳送」案鈕會用到 -->
        function reg()
        {
               document.getElementById("mem_account").innerHTML="foodme001";
        }
   </SCRIPT>

</head>

<body>
	<div class="container">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->
				<FORM METHOD="post"
				    name="addMem"
					ACTION="<%=request.getContextPath()%>/mem/mem.do"
					enctype="multipart/form-data">
					<div class="container">
						<div class="row clearfix">
							<div class="col-md-12 column">
								<h3 class="text-danger">會員註冊</h3>
							</div>
						</div>
						<div class="row clearfix">
							<div class="col-md-4 column">
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

								<fieldset>

									<!-- Form Name -->
									<legend></legend>
							</div>
							<div class="col-md-6 column"></div>
						</div>
						<div class="row clearfix">
							<div class="col-md-3 column">

								<!-- Text input-->
								<div><span style="color:red">*</span>為必填欄位</div><br>
								<div class="control-group">
									<span style="color:red">*</span><label class="control-label" for="mem_account">帳號</label>
									<div class="controls">
										<input id="mem_account" name="mem_account" type="text"
											placeholder="" class="input-xlarge" size="20" maxlength="20"
											value="<%=(memVO == null) ? "" : memVO.getMem_account()%>">
                                            <span id="check_account"></span>
									</div>
								</div>

								<!-- Password input-->
								<div class="control-group">
									<br>
									<span style="color:red">*</span><label class="control-label" for="password">密碼</label>
									<div class="controls">
										<input id="mem_pwd" name="mem_pwd" type="password"
											placeholder="" class="input-xlarge" size="20" maxlength="20"
											value="<%=(memVO == null) ? "" : memVO.getMem_pwd()%>">
											<br>(密碼長度不得小於8個字元，<br>並至少包含英數字)

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br>
									<span style="color:red">*</span><label class="control-label" for="name">姓名</label>
									<div class="controls">
										<input id="mem_name" name="mem_name" type="text" placeholder=""
											class="input-large" size="10" maxlength="10"
											value="<%=(memVO == null) ? "" : memVO.getMem_name()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <label class="control-label" for="nickname">暱稱</label>
									<div class="controls">
										<input id="mem_nickname" name="mem_nickname" type="text"
											placeholder="" class="input-small" size="10" maxlength="10"
											value="<%=(memVO == null) ? "" : memVO.getMem_nickname()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="birthday">生日</label>
									<div class="controls">
										<input type="text" id="mem_birthdate" name="mem_birthdate"
											value="<%=(memVO == null) ? "" : memVO.getMem_birthdate()%>">

									</div>
								</div>


								<!-- Text input-->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="mail">電子信箱</label>
									<div class="controls">
										<input id="mem_mail" name="mem_mail" type="email"  placeholder=""
											class="input-xxlarge" size="30" maxlength="30"
											value="<%=(memVO == null) ? "" : memVO.getMem_mail()%>">

									</div>
								</div>





								<!-- Text input-->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="phone">連絡電話</label>
									<div class="controls">
										<input id="mem_phone" name="mem_phone" type="text" placeholder="ex:0988147123"
											class="input-large" size="13" maxlength="13"
											value="<%=(memVO == null) ? "" : memVO.getMem_phone()%>">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="address">地址</label>
									<div class="controls">
										<input id="mem_adrs" name="mem_adrs" type="text" placeholder=""
											class="input-xxlarge" size="40" maxlength="30"
											value="<%=(memVO == null) ? "" : memVO.getMem_adrs()%>">

									</div>
								</div>

								<!-- Multiple Radios -->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="sex">性別</label>
									<div class="controls">

										<input type="radio" id="mem_sex" name="mem_sex" value="1"
											<%=(sex == 1) ? "checked" : ""%>> 男 &nbsp; <input
											type="radio" name="mem_sex" value="0"
											<%=(sex == 0) ? "checked" : ""%>> 女

									</div>
								</div>
								
								<!-- 上傳大頭貼-->
								<div class="control-group">
									<br> <span style="color:red">*</span><label class="control-label" for="nickname">上傳大頭貼</label>
									<div class="controls">
										<input id="pic" name="mem_pic" class="input-file" type="file">

									</div>
								</div>
								

								<!-- 會員條款-->
								<div class="control-group">
									<br> <label class="control-label" for="mem_clause ">會員條款</label>
									<div class="controls">
										<textarea rows="8" cols="120" readonly="readonly">
您同意所登錄或留存之個人資料，本站可以在必要合理範圍或有助於本站服務之提供， 蒐集、處理、保存、傳遞及使用 您的註冊資料（包括但不限於提供予政府機關、司法警察及合作廠商）。 隱私權政策 本站對於您所登錄或留存之個人資料，在未獲得您的同意以前，都僅供本站及本站相關業務合作夥伴於其內部、依照原來所說明的使用目的和範圍加以使用，除非事先說明、或依照相關法律規定，否則本站不會將您的個人資料提供給第三人、或移作其他目的使用。本站會不定時通知會員所在單位的關於本服務之最新消息（包含相關活動、促銷方案等），會員可選擇拒絕。 您同意在 下列的情況下，本站有可能會提供您的個人資料給相關機關，或主張其權利受侵害並提示司法機關正式文件之第三人： 基於法律之規定、或受司法機關與其他有權機關基於法定程序之要求； 在緊急情況下為維護其他會員或其他第三人之合法權益； 為維護會員服務系統的正常運作； 會員透過本服務與租賃商品、購買商品、兌換贈品，因而產生的金流、物流必要資訊； 使用者有任何違反政府法令或本使用條款之情形。 會員帳號、密碼及安全 同意維護您的帳號及密碼的機密安全，您會在每次連線完畢後，登出並停止使用。當您發現您的帳號及密碼遭到盜用或安全問題發生時，您會立即通知本站。如果本站無法辨識使用過程是否為本人使用之情形，本站對此所致之損害，概不負責。 您同意您的帳號、密碼及會員權益均僅供您個人使用，不得轉借、轉讓他人或與他人共用。 使用者規範與行為 您同意不冒用他人名義使用本站。 您同意不參與任何非法目的或以任何非法方式使用本站，並遵守當地相關法規及網際網路使用規範。 您同意不會利用本站進行誹謗、侮辱、威脅、猥褻、干擾、不實、違反道德與公共秩序之文字、圖片或任何形式的檔案於本站。 您同意不傳播任何電腦病毒或破壞性程式碼。 您同意不使用惡意方式進行修改、刪除、干擾本站。 您同意不傳送任何未經許可的商業資料或廣告給本站之其他 使用者。 您同意遵守智慧財產權，不任意使用未經授權的圖片或任何形式的檔案於本站。 您同意不逕自使用、修改、重製、散布、發行、進行還原工程、解編或反向組譯 本站上的程式以及所有內容，包括但不限於著作、圖片、檔案、網站架構、網頁設計等，均由本站或其他權利人依法擁有其智慧財產權，包括但不限於商標權、專利權、著作權與專有技術等。 本站具備引用之功能，您同意您的作品發佈後，即同意他人引用您的作品至他人作品內。當您刪除該作品後，已被引用的作品將會繼續存在於本站。 創作者擁有著作之智慧財產權且擔保著作本身無違反著作權法及其它法規。未得創作者許可，您不得有修改、重製、轉貼之相關情事於營利行為。若您有涉及侵權之情事，本站有權停止您使用 本站，並取消會員資格。 對違反本服務條款行為之處置 您同意本站得依其判斷並認定您已經違反本服務條款之情事，本站有權終止或限制您使用本服務，並取消會員資格。 您同意本站可無條件將您的作品下架。 免責申明 當本站發生系統中斷，包括但不限於保養、施工、升級、故障或天災不可抗力之因素，也許會造成您的資料遺失、使用不便或其他損失，本站將不付任何賠償與責任。請您使用本站時，自行採取備份措施。 本站不保證系統程式不發生錯誤或障礙等情事，使用者自行採取備份措施。 本站於任何時刻，無論是否有公告或是通知使用者，都有權修改或終止任何服務項目，以及停止使用者使用權利。
</textarea>

									</div>
								</div>

								<!-- 同意會員條款 -->
								<div class="control-group">
									<br>
									<div class="controls">

										<input type="radio" id="mem_clause" name="mem_clause" value="agree">
										同意

										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" name="mem_clause" value="disagree">
										不同意

									</div>
								</div>


								<!-- Button (Double) -->
								<div class="control-group">
									<label class="control-label" for="check"></label> <br>
									<br>
									<div class="controls">
										<input type="hidden" name="action" value="insert"> 
										<input class="btn btn-info" type="submit" value="送出">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input class="btn btn-danger" type="button" value="取消"
											onclick="window.location='<%=request.getContextPath() %>/front/index.jsp'" />
										&nbsp;
										<input class="btn btn-warning" id="reg"type="button" value="神奇小按鈕"
											onclick="reg()" />

									</div>
								</div>



							</div>
							
<!-- 							<div class="col-md-8 column"> -->
<!-- 								<img alt="140x140" src="http://lorempixel.com/140/140/" -->
<!-- 									class="img-rounded"><br> -->
<!-- 								<br> -->

								<!-- File Button -->
<!-- 								<div class="control-group"> -->

<!-- 									<div class="controls"> -->
<!-- 										<input id="pic" name="mem_pic" class="input-file" type="file"> -->
<!-- 									</div> -->
<!-- 								</div> -->

							</div>
						</div>
					</div>
					<br> <br>
				</Form>
				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
