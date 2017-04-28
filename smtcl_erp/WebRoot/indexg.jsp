<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="javax.servlet.http.HttpSession"%>
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta   http-equiv="Expires"   CONTENT="0">
<meta   http-equiv="Cache-Control"   CONTENT="no-cache">
<meta   http-equiv="Pragma"   CONTENT="no-cache">
<style type="text/css">
.h {
	width:150px;
	height:67px;
	border:1px #ffffff solid;
	background-color:rgb(137, 137, 137);
}
</style>
<!--[if lt IE 9]>
<script src="<%=path%>/js/html5shiv.min.js"></script>
<script src="<%=path%>/js/respond.min.js"></script>
<![endif]-->
<title>缓存库系统</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/bootstrap/css/bootstrap-theme.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap-datetimepicker.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/button.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/general.css"/>
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/highcharts.js"></script>
<script src="<%=path%>/js/highcharts-more.js"></script>
<script src="<%=path%>/js/solid-gauge.js"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=path%>/js/layer/layer.js"></script>
<script src="<%=path%>/js/home.js"></script>
</head>
<body style="margin:0px; padding:0px; overflow:hidden;" oncontextmenu="window.event.returnValue=false" onselectstart="return false">
	<div class="row">
		<table style="height:100%; width:100%;">
			<tr>
				<td style="width:100%;height:100%;">
					<table style="height:100%; width:100%;">
						<!-- home -->
						<tr>
							<td id="homeBody" style="width:100%;background-color:rgb(245,245,245);">
								<div id="home_div" class="home_div"></div>
							</td>
						</tr>
						<!-- home end -->
						<!-- 窗底 -->
						<tr style="height:68px;background-color:rgb( 112 , 131 , 148 ); z-index:999;">
							<td>
								<div id="h_div_mo_img_close" class="h_div_mo_img_close">
									<img id="div_mo_img_close" class="div_mo_img_close" src="<%=path%>/images/guanjianniu.png"/>
									<img id="div_mo_img_strat" class="div_mo_img_strat" style="display:none;" src="<%=path%>/images/fanhuianniu_hong.png" />
									<div class="row" id="btn_id"></div>
								</div>
							</td>
						</tr>
						<!-- 窗底 end -->
					</table>
				</td>
				<!-- 右侧菜单 -->
				<td style="position:relative;height:100%;">
					<div>
						<div id="right_home1" title="按钮操控" x='280px' y='200px' class="right_home1 tz">
							<div class="right_home1_but"></div>
						</div>
						<div id="right_home2" title="物料请求，处理完成后请删除" x='500px' y='430px' class="right_home2 tz">
							<div class="right_home2_but"></div>
						</div>
					</div>
					<div id="homeRight" class="homeRight">
						<div id="anniu1" title="订单调度" class="订单调度"></div>
						<div id="anniu2" title="主页信息" class="主页信息"></div>
						<div id="anniu3" title="PLC" class="PLC"></div>
						<div id="anniu4" title="库房操作" class="库房操作"></div>
						<div id="anniu5" title="基础设置" class="基础设置"></div>
					</div>
					<div id="anniuHome" title="菜单" class="anniuHome"></div>
				</td>
				<!-- 右侧菜单 end-->
			</tr>
		</table>
	</div>
</body>
</html>