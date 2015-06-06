<%@page import="com.news.model.NewsService"%>
<%@page import="com.banner.model.BannerService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banner.model.*"%>
<%@ page import="com.ads.model.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="java.util.*"%>
<%
	BannerService bannerSrv = new BannerService();
	List<BannerVO> bannerList = bannerSrv.getAll();
	pageContext.setAttribute("bannerList", bannerList);

	AdsService adsrSrv = new AdsService();
	List<AdsVO> adsList = adsrSrv.getAll();
	pageContext.setAttribute("adsList", adsList);

	NewsService newsSrv = new NewsService();
	List<NewsVO> newsList = newsSrv.getAll();
	pageContext.setAttribute("newsList", newsList);
%>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<title>FOOD me 線上餐廳訂位平台</title>
<%@ include file="/front/pageDesign/designSrc.file" %>
<% response.setHeader("Cache-Control","no-store");%><!--取消瀏覽器快取，最好每個jsp檔都加-->
<% response.setHeader("Pragma","no-cache");%>
<% response.setDateHeader("Expires",0);%>
</head>
<style>
#text
{
	font-size:14;   
}
#header
{
	color:#FF9D37;
	text-align:center;
}
#footer
{
	background-color:#CCCCCC;
	padding:30px;
	margin-bottom:20px;
	margin-top:20px;
}
.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}
</style>
<body>
	<div class="container index_content">
		<div class="row clearfix page_container">
			<div class="col-md-12 column">
				<!--  HEADER的開始-->
				<%@ include file="/front/pageDesign/fe_header.jsp"%>
				<!--HEADER的結束-->

				<!---------- 以下為網頁內容------------->

				<div class="row clearfix" id="index_content">
					<div class="row clearfix">
						<div class="col-md-12 column" align="center">						
							<h2 id="header">
								常見問題
							</h2>													
						</div>
						<HR width="80%" size="10">
					</div>
					<div class="col-md-2 column">
						

						
					</div>
					<div class="col-md-8 column">
					<p id="text">	
					<section class="ez-card infomation">
					    <h1>
					    	購物常見問題
            			</h1>
            			<hr>			          
			            <ul>
			              <li><a>訂購時一定要加入會員嗎?</a>
			                <p>是的，只有會員可以訂購餐券。
			                </p>
			              </li>
			              <li><a>訂購完成後可以修改數量/取件方式/付費方式?</a>
			                <p>因購買流程作業快速，無法於購買後即時修改餐券數量，且由於不同的付費方式有不同的作業系統，因此也無法更改您的付費方式喔
			                </p>
			              </li>
			              <li><a>如何付費?付費方式?</a>
			                <p>目前提供付款方式有三種：<br>線上刷卡：於付款頁面輸入您的信用卡資訊與授權碼即可，目前暫不支援國民旅遊卡。<br>ATM轉帳：持任一銀行金融卡，至全國ATM（自動櫃員機）轉帳，或使用ATM讀卡機線上轉帳皆可， 註：跨行轉帳會收取15元轉帳手續費。也可至銀行或郵局臨櫃填寫匯款單匯款，註：銀行會收取30元的匯費。
			                  <brb></brb>現金付款(限自取)：持現金至FoodMe領取餐券並付現。<br>(附註) FoodMe為提供更穩定安全的購物環境，刷卡過程您的卡號全程加密，相關卡號資訊也完全不會留存在FoodMe資料庫，確保您的交易安全。
			                </p>
			              </li>
			              <li><a>售完補貨中/貨到通知我</a>
			                <p>如餐券顯示「售完，補貨中」，請您點選該按鈕並填寫您的電子郵件，餐券到貨後系統會發送E-mail通知您
			                </p>
			              </li>
			              <li><a>大量訂購</a>
			                <p>如您欲購買的餐券數量超過100張，並接受現金付款方式，請您再來電來信與我們做進一步的確認喔<br>撥打客服專線: (02) 2377-8323或是來信至taiwan@FoodMe.com
			                </p>
			              </li>
			              <li><a>如何查詢訂單的處理情況</a>
			                <p>歡迎撥打客服專線: (02) 2377-8323或是來信至taiwan@FoodMe.com 將有專人為您服務
			                </p>
			              </li>
			              <li><a>為何收不到訂位/訂購確認信?</a>
			                <p>當您完成購買後，系統會自動發送購買確認信，請務必填寫正確且安全的E-mail信箱，如您使用免費信箱(如yahoo、hotmail、pchome等)，可能會導致以下問題<br>a. 確認信被阻擋。<br>b. 可能需等待數小時才能收到確認信。<br>c. 確認信被誤認為是廣告信而被移到垃圾信件匣中，請檢查您的垃圾信件匣<br>若您購買完成24小時候仍未收到確認信，請撥打客服專線: (02) 2377-8323或是來信至taiwan@FoodMe.com幫您做確認喔
			                </p>
			              </li>
			            </ul>
         			 </section>
         			 <section class="ez-card infomation">
			            <h1>付款常見問題
			            </h1>
			            <hr>
			            <ol>
				            <li>
			                	信用卡刷卡
			                	<ul>
					              <li><a>為什麼刷卡失敗?</a>
					                <p>如欲交易失敗的情形，可能是以下原因:<br>1) VISA金融卡未開通網路消費權限<br>2) 信用卡卡號、有效期限、卡片背面末三碼輸入錯誤<br>3) 信用卡卡片問題(未開卡、額度不足)<br>4) 網路連線繁忙
					                </p>
					              </li>
					            </ul>
				            </li>
				            <li>
				            	ATM轉帳
				            	<ul>
					              <li><a>匯款帳戶資訊</a>
					                <p>上海商業儲蓄銀行 中和分行 機構代號：011<br>戶名：三二三網路科技股份有限公司 匯款帳號：33-102-000026323
					                </p>
					              </li>
					              <li><a>已轉帳，系統仍顯示"未付款"</a>
					                <p>由於銀行作業時間為週一至週五(不含例假日)，早上9:00至下午3:30，且系統於每日下午14：00為統一對帳時間，若您已超過銀行及系統作業時間轉帳，則款項將於次個工作日才會入帳喔。
					                </p>
					              </li>
					            </ul>
				            </li>
				            <li>
				            	自取付款
				            	<ul>
					              <li><a>自取地點/如何前往</a>
					                <p>自取請至FoodMe辦公室：台北市大安區敦化南路二段216號14樓
					                </p>
					                <div><img src="http://cloudfront.FoodMe.com/asset/FoodMe_template/address_map.png" alt="從捷運六張犁站出發。"></div>
					              </li>
					              <li><a>自取需要準備什麼</a>
					                <p>到現場只要告知櫃檯人員您訂購時留的手機號碼或訂單編號即可。
					                </p>
					              </li>
					              <li><a>什麼時候可以到辦公室自取</a>
					                <p>週一至週日，早上10點～晚上7點，中午不休息也可以來自取唷！
					                </p>
					              </li>
					              <li><a>FoodMe辦公室也可以付款</a>
					                <p>FoodMe辦公室有提供刷卡機，現場也可以刷卡付款。
					                </p>
					              </li>
					            </ul>
				            </li>
				            <li>
				            	海外訂購如何付款
				            	<ul>
					              <li><a>目前所接受海外寄送地區/寄送方式/費用</a>
					                <p>僅可寄送至香港，使用中華郵政之國際掛號郵件，需支付90元的郵資費。
					                </p>
					              </li>
					            </ul>
				            </li>
			            </ol>
			            
			        </section>
					</p>
					<hr>
					<p align="center">
						<a href="<%=getServletContext().getContextPath()%>/front/index.jsp" class="btn btn-danger" type="button">回到FoodMe首頁</a>
					</p>
						
					</div>
					
					
					<div class="col-md-2 column">
					
					</div>
			   </div>
				
				<!---------- 以上為網頁內容----------->
				<div class="row clearfix">
					<div class="col-md-12 column nopadding" align="center">

						<div id="footer">					     
						      <span>106 台北市大安區敦化南路二段216號14樓（仁愛圓環，國泰世華大樓）</span>&nbsp;&nbsp;|&nbsp;&nbsp;<span itemprop="telephone">服務專線：02-2377-8323</span>&nbsp;&nbsp;|&nbsp;&nbsp;服務時間：週一至週日 10:00-19:00
					      <div><span>FOOD me 線上餐廳訂位系統</span>© 2014 FOODme.com All Rights Reserved.</div>
					      <div><a href="#" style="padding-right: 10px">使用者條款</a>|&nbsp;&nbsp;<a href="#">隱私權政策</a></div>
					   </div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
