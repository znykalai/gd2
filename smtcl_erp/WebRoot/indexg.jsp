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
	width: 150px;
	height: 67px;
	border: 1px #ffffff solid;
	background-color: rgb(137, 137, 137);
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
<body draggable="false" style="margin: 0px; padding: 0px; overflow: hidden;"
	oncontextmenu="window.event.returnValue=false"
	onselectstart="return false"
	ondrop="drop(event)"
	ondragover="allowDrop(event)">
	<div class="row">
		<table style="height:100%; width:100%;">
			<tr>
				<td style="width:100%;height:100%;">
					<table style="height: 100%; width: 100%;">
						<!-- home -->
						<tr>
							<td id="homeBody" style="width:100%;background-color: rgb(245,245,245);">
								<div id="home_div" style="position:relative;left:18px;width:92%;height:100%;font-family:'微软雅黑';"></div>
							</td>
						</tr>
						<!-- home end -->
						<!-- 窗底 -->
						<tr style="height:68px;background-color: rgb( 75 , 176 , 249 ); z-index:999;">
							<td>
								<div style="position: relative; height: 65px; width: 100%;
										background-image: url(<%=path%>/images/heitiao.png); background-repeat: no-repeat; background-position: -4 -2">
									<img id="div_mo_img_close" draggable="false"
										style="cursor: pointer; position: absolute; top: 0px; left: 45px; width: 67px; height: 63px;"
										src="<%=path%>/images/guanjianniu.png"/>
									<img id="div_mo_img_strat" draggable="false"
										style="cursor: pointer; position: absolute; top: 0px; left: 150px; width: 67px; height: 63px;"
										src="<%=path%>/images/fanhuianniu.png" />
									<div class="row" id="btn_id"></div>
								</div>
							</td>
						</tr>
						<!-- 窗底 end -->
					</table>
				</td>
				<!-- 右侧菜单 -->
				<td style="position: relative;height:100%;">
					<div>
						<div ondragstart="dragStart(event)" draggable="true" id="xtsz_home" title="系统信息" 
							style="width: 500px; height: 100px; position: absolute; bottom: 480px; left:-82px;">
							<div id="xtsz" style="cursor: pointer;width: 100px; height: 100px;background-image: url(<%=path%>/images/xitongshezhi_huise.png);"></div>
							<div id="xtsz_id_" style="position: absolute; z-index:999;width:500px;height:500px;display:none;widh:500px;height:500px;background-color: #fff;">
								<div class="layui-layer-title" style="cursor: move;">
									系统信息
								</div>
								<div id="" class="layui-layer-content" style="height: 436px;">
									<div id="系统信息"></div>
								</div>
							</div>
						</div>
						<div ondragstart="dragStart(event)" draggable="true" id="yhsz_home" title="用户信息"
							style="width: 500px; height: 100px; position: absolute; bottom: 325px; left:-82px;">
							<div id="yhsz" style="cursor: pointer;width: 100px; height: 100px;background-image: url(<%=path%>/images/yonghushezhi_huise.png);"></div>
							<div id="yhsz_id_" style="position: absolute; z-index:999;width:500px;height:500px;display:none;widh:500px;height:500px;background-color: #fff;">
								<div class="layui-layer-title" style="cursor: move;">
									用户信息
								</div>
								<div id="" class="layui-layer-content" style="height: 436px;">
									<div id="用户信息"></div>
								</div>
							</div>
						</div>
					</div>
					<div id="homeRight"
						style="position: absolute; bottom: -51px; right: -108px; height: 350px; width: 480px; 
								background-image: url(<%=path%>/images/sanjiaoxing385x300.png); background-repeat: no-repeat; z-index:0;">
						<div id="anniu1" title="订单调度"
							style="width: 66px; height: 50px; cursor: pointer; position: relative; top: 245px; left: 59px; 
								background-image: url(<%=path%>/images/wuliaodiaodu66x55_huise.png); background-repeat: no-repeat;"></div>
						<div id="anniu2" title="查询信息"
							style="width: 66px; height: 50px; cursor: pointer; position: relative; top: 153px; left: 118px; 
								background-image: url(<%=path%>/images/xinxiguanli66x55_huise.png); background-repeat: no-repeat;"></div>
						<div id="anniu3" title="订单下载"
							style="width: 66px; height: 50px; cursor: pointer; position: relative; top: 58px; left: 174px; 
								background-image: url(<%=path%>/images/dingdanguanli66x55_huise.png); background-repeat: no-repeat;"></div>
						<div id="anniu4" title="库房操作"
							style="width: 75px; height: 50px; cursor: pointer; position: relative; top: -40px; left: 228px; 
								background-image: url(<%=path%>/images/kufangcaozuo66x55_huise.png); background-repeat: no-repeat;"></div>
						<div id="anniu5" title="基础设置"
							style="width: 66px; height: 53px; cursor: pointer; position: relative; top: -123px; left: 287px; 
								background-image: url(<%=path%>/images/shezhizhongxin66x55_huise.png); background-repeat: no-repeat;"></div>
					</div>
					<div id="anniuHome" title="主页" style="width: 100px; height: 80px; cursor: pointer; position: absolute; bottom: -13px; right: -10px; 
						background-image: url(<%=path%>/images/zhuyeanniu_huise.png); background-repeat: no-repeat; z-index: 100;"></div>
				</td>
				<!-- 右侧菜单 end-->
			</tr>
		</table>
	</div>
</body>
</html>