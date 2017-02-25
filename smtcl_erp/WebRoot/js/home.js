var dlInterval = null;
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
   	return null;
}
function allowDrop(event){
	if(winId){
		$("#"+winId).hide(100);
		$("#"+winId+"_home").hide(0);
		$("#"+winId+"_id_").hide(0);
	}
    event.preventDefault();
    return null;
}
function drop(event){
	event.preventDefault();
	var data = event.dataTransfer.getData("Text");
	var html = "用户设置";
	if(data == "xtsz_home"){
		html = "系统设置";
	}
	var y = getY(event);
	var x = getX(event)+5>document.body.clientWidth?getX(event)-100:getX(event);
	var win = layer.open({
		btn:[],
		anim:3,
		type:1,
		shade:0,
		sw:data,
		resize:false,
		area: ['500px', '500px'],
		title:$('#'+data).attr('title'),
		offset:[y,x],
		content: '<div id="'+(data+win)+'">'+html+'</div>',
		success: function(layero){
			winId = null;
		},
		cancel: function(index){
			$("#"+this.sw).show(0);
			$("#"+this.sw.split("_")[0]).fadeToggle(300);
			layer.close(index);
			return false; 
		} 
	});
	return null;
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
	str += d.getDate() + ' 00:00:00';
	str += d.getHours() + ':';
	str += d.getMinutes() + ':';
	str += d.getSeconds();
	return null;
}
var af_Home = {
	arrBtn:null,
	click:function(url_){
		if(af_Home.arrBtn == url_){return null;}
		if(dlInterval){dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);$("#btn_id").val(null);
		readyShow=null;af_Home.arrBtn=url_;
	 	$.ajax({
		    url: url_,
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
				readyShow.load(function(){
					spl = null,body = null,
					home_html = null,home_btn = null;
					return null;
				});
				return null;
		    }
		});
	 	return null;
	},
	load:function(fun){
		//关机
		$('#div_mo_img_close').mouseover(function(){
			var url = getRootPath()+"/images/guanjianniu_mo.png";
			$(this).attr("src",url);
			return (function(){
				return url = null;
			})();
		});
		$('#div_mo_img_close').mouseout(function(){
			var url = getRootPath()+"/images/guanjianniu.png";
			$(this).attr("src",url); 
			return (function(){
				return url = null;
			})();
		});
		//用户设置
		$('#yhsz').mousedown(function(){
			$('#yhsz_id_').show(0);
			$('#yhsz').hide(0);
			return null;
		});
		//系统设置
		$('#xtsz').mousedown(function(){
			$('#xtsz_id_').show(0);
			$('#xtsz').hide(0);
			return null;
		});
		//按钮-主页
		$('#anniuHome').click(function(){
			var a = $("#homeRight").slideToggle(280,function(){
				return a = null;
			});
			return null;
		});
		$('#anniu1').click(function(){
			af_Home.click(getRootPath()+'/html/dddd.html');
			return null;
		});
		//按钮-主页显示
		$('#anniu2').click(function(){
			af_Home.click(getRootPath()+'/html/h_home.html');
			return null;
		});
		//按钮-plc
		$('#anniu3').click(function(){
			af_Home.click(getRootPath()+'/html/plc.html');
			return null;
		});
		//按钮-库房操作
		$('#anniu4').click(function(){
			af_Home.click(getRootPath()+'/html/kfcz.html');
			return null;
		});
		//按钮-基础设置
		$('#anniu5').click(function(){
			af_Home.click(getRootPath()+'/html/jcsz.html');
			return null;
		});
		$(document).keydown(function (event) {
	        if (event.keyCode == 122) {
	        	var a = window.location.reload();
				return a=null;
	        }
	    });
		//显示主页;
		$('#anniu2').click();
		return fun();
	}
};
$(document).ready(function(){
	try{
		var a = af_Home.load(function(){
			return a = null;
		});
		return null;
	}catch (e) {
		return e;
	}
	return null;
});