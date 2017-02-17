var dlInterval = false;
function mousePosition(evt){
    evt = evt || window.event;
    //Mozilla
    if(evt.pageX || evt.pageY){
        return { x : evt.pageX,y : evt.pageY}
    }
    //IE
    return {
        x : evt.clientX + document.body.scrollLeft - document.body.clientLeft,
        y : evt.clientY + document.body.scrollTop - document.body.clientTop
    }
}
//获取X轴坐标  
function getX(evt){
    evt = evt || window.event;
    return mousePosition(evt).x;
}
//获取Y轴坐标
function getY(evt){
    evt = evt || window.event;
    return mousePosition(evt).y;
}
var winId = null;
function dragStart(event){
	winId = event.target.id.split("_")[0];
   	event.dataTransfer.setData("Text", event.target.id);
}
function allowDrop(event){
	if(winId){
		$("#"+winId).hide(100);
		$("#"+winId+"_home").hide(0);
		$("#"+winId+"_id_").hide(0);
	}
    event.preventDefault();
}
function drop(event){
	event.preventDefault();
	var data = event.dataTransfer.getData("Text");
	var html = "用户设置";
	if(data == "xtsz_home"){
		html = "系统设置";
	}
	var win = layer.open({
		btn:[],
		anim:3,
		type:1,
		shade:0,
		sw:data,
		resize:false,
		area: ['500px', '500px'],
		title:$('#'+data).attr('title'),
		offset:[getY(event),getX(event)],
		content: '<div id="'+(data+win)+'">'+html+'</div>',
		success: function(layero){
			winId = null;
		},
		cancel: function(index){
			$("#"+this.sw).show(0);
			$("#"+this.sw.split("_")[0]).fadeToggle(1000);
			layer.close(index);
			return false; 
		} 
	});
}
function getRootPath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return(prePath+postPath);
}
function current() {
	var d = new Date(), str = '';
	str += d.getFullYear() + '-'; //获取当前年份
	str += d.getMonth() + 1 + '-'; //获取当前月份（0——11）
	str += d.getDate() + ' ';
	str += d.getHours() + ':';
	str += d.getMinutes() + ':';
	str += d.getSeconds();
	return str;
}
$(document).ready(function(){
	var arrBtn;
	//关机
	$('#div_mo_img_close').mouseover(function(){this.src=""+getRootPath()+"/images/guanjianniu_mo.png";});
	$('#div_mo_img_close').mouseout(function(){this.src=""+getRootPath()+"/images/guanjianniu.png";});
	$('#div_mo_img_close').click(function(){});
	//主页
	$('#div_mo_img_strat').mouseover(function(){this.src=""+getRootPath()+"/images/fanhuianniu_mo.png";});
	$('#div_mo_img_strat').mouseout(function(){this.src=""+getRootPath()+"/images/fanhuianniu.png";});
	$('#div_mo_img_strat').click(function(){
	 	//window.location.reload();
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
	 	$.ajax({
		    url: getRootPath()+'/html/h_home.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
	$('#div_mo_img_strat').click();
	//用户设置
	$('#yhsz').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/yonghushezhi_huangse.png)";});
	$('#yhsz').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/yonghushezhi_huise.png)";});
	$('#yhsz').mousedown(function(){
		$('#yhsz_id_').show(0);
		$('#yhsz').hide(0);
	});
	//系统设置
	$('#xtsz').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xitongshezhi_huangse.png)";});
	$('#xtsz').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xitongshezhi_huise.png)";});
	$('#xtsz').mousedown(function(){
		$('#xtsz_id_').show(0);
		$('#xtsz').hide(0);
	});
	//按钮-主页
	$('#anniuHome').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/zhuyeanniu_huangse.png)";});
	$('#anniuHome').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/zhuyeanniu_huise.png)";});
	$('#anniuHome').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/zhuyeanniu_xiao.png)";});
	$('#anniuHome').mouseup(function(){this.style.backgroundImage="url("+getRootPath()+"/images/zhuyeanniu_huangse.png)";});
	$('#anniuHome').click(function(){
		$("#homeRight").slideToggle(300);
	});
	//按钮-订单调度
	$('#anniu1').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/wuliaodiaodu66x55_huangse.png)";});
	$('#anniu1').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/wuliaodiaodu66x55_huise.png)";});
	$('#anniu1').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/wuliaodiaodu56x47_xiao.png)";});
	$('#anniu1').mouseup(function(){this.style.backgroundImage="url("+getRootPath()+"/images/wuliaodiaodu66x55_huangse.png)";});
	$('#anniu1').click(function(){
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
		$.ajax({
		    url: getRootPath()+'/html/dddd.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
	//按钮-信息查询
	$('#anniu2').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xinxiguanli66x55_huangse.png)";});
	$('#anniu2').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xinxiguanli66x55_huise.png)";});
	$('#anniu2').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xinxiguanli56x47_xiao.png)";});
	$('#anniu2').mouseup(function(){this.style.backgroundImage="url("+getRootPath()+"/images/xinxiguanli66x55_huangse.png)";});
	$('#anniu2').click(function(){
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
		$.ajax({
		    url: getRootPath()+'/html/xxcx.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
	//按钮-订单管理
	$('#anniu3').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/dingdanguanli66x55_huangse.png)";});
	$('#anniu3').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/dingdanguanli66x55_huise.png)";});
	$('#anniu3').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/dingdanguanli56x47_xiao.png)";});
	$('#anniu3').mouseup(function(){this.style.backgroundImage="url("+getRootPath()+"/images/dingdanguanli66x55_huangse.png)";});
	$('#anniu3').click(function(){
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
		$.ajax({
		    url: getRootPath()+'/html/ddgl.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
	//按钮-库房操作
	$('#anniu4').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/kufangcaozuo66x50_huangse.png)";});
	$('#anniu4').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/kufangcaozuo66x55_huise.png)";});
	$('#anniu4').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/kufangcaozuo56x47_xiao.png)";});
	$('#anniu4').mouseup(function(){this.style.backgroundImage="url("+getRootPath()+"/images/kufangcaozuo66x50_huangse.png)";});
	$('#anniu4').click(function(){
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
		$.ajax({
		    url: getRootPath()+'/html/kfcz.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
	//按钮-基础设置
	$('#anniu5').mouseover(function(){this.style.backgroundImage="url("+getRootPath()+"/images/shezhizhongxin66x55_huangse.png)";});
	$('#anniu5').mouseout(function(){this.style.backgroundImage="url("+getRootPath()+"/images/shezhizhongxin66x55_huise.png)";});
	$('#anniu5').mousedown(function(){this.style.backgroundImage="url("+getRootPath()+"/images/shezhizhongxin56x47_xiao.png)";});
	$('#anniu5').mouseup(function(){this.style.backgroundImage = "url("+getRootPath()+"/images/shezhizhongxin66x55_huangse.png)";});
	$('#anniu5').click(function(){
		if(arrBtn == this){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);
		$("#btn_id").val(null);
		readyShow = null;
		arrBtn = this;
		$.ajax({
		    url: getRootPath()+'/html/jcsz.html',
		    type: 'get',
		    cache:false, 
		    success: function (data) {
		    	var spl = "<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body = data.split("<body>");
		    	var html = body[1].split("</body>")[0];
		    	var home_html = html.split(spl)[0];
		    	var home_btn = spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				readyShow.load();
		    }
		});
	});
});