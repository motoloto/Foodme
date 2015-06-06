<div class="col-md-1 column sideNav block">
	<nav class="navbar navbar-default navbar-fixed-top " role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
					class="icon-bar"></span><span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">FOOD me 線上訂位後端管理系統</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown">Hi ! Peter<strong class="caret"></strong></a>
					<ul class="dropdown-menu">
						<li><a href="#">Pter</a></li>
						<li><a href="#">帳戶資訊</a></li>
						<li class="divider"></li>
						<li><a
							href="<%=getServletContext().getContextPath()%>/login/login.do?action=logout">登出</a>
						</li>
					</ul></li>
			</ul>
		</div>

	</nav>
	<nav class="navbar navbar-default navbar-fixed-bottom navbar-inverse">
		<div class="container">
			<P>02-2377-8323 週一至週日 10:00-19:00 106 台北市大安區 敦化南路二段216號14樓</P>
		</div>
	</nav>
	<div style="float: left;">
		<img width=100%
			src="<%=getServletContext().getContextPath()%>/back/img/LOGO.png">
		<ul class="nav nav-list" id="sideNav">
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/index.jsp"
				id="sideNav_li">資訊面板</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/news/be_newsMgr.jsp"
				id="sideNav_li">最新消息管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/rest/be_restMgr.jsp"
				id="sideNav_li">餐廳管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/score/be_scoreMgr.jsp"
				id="sideNav_li">評價資料查詢</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/reserv/be_reservMgr.jsp"
				id="sideNav_li">訂位資訊管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/coupon/be_couponMgr.jsp"
				id="sideNav_li">餐券銷售管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/order/checkOrder.jsp"
				id="sideNav_li">訂單管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/banner/be_bannerMgr.jsp"
				id="sideNav_li">橫幅廣告管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/ads/be_adsMgr.jsp"
				id="sideNav_li">小型廣告管理</a></li>
			<li class="active"><a
				href="<%=getServletContext().getContextPath()%>/back/class/be_classMgr.jsp"
				id="sideNav_li">主題推薦管理</a></li>
			<li class="active"><a 
			href="<%=getServletContext().getContextPath()%>/back/member/be_memMgr.jsp" id="sideNav_li">會員資料查詢</a>
			</li>
			<li class="active"><a href="<%=getServletContext().getContextPath()%>/back/affi/listAllAffi.jsp" id="sideNav_li">加盟資料審查</a>
			</li>
			<li class="active"><a href="<%=getServletContext().getContextPath()%>/back/account/be_accountMgr.jsp" id="sideNav_li">帳號管理</a>
			</li>
		</ul>
	</div>
</div>