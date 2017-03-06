var af_Home = {
	arrBtn:null,
	winId:null,//拖拽windowID
	dlInterval:null,//定时摧毁器
	winHtml:function(fun,type){
		var html = "<div class='col-md-11'>" +
			 "<div class='col-md-7' style='margin-top:25px;'>" +
				"<div class='qfgd' id='A'>" +
					"<span style='font-size:12px;'>不检测数量</span>" +
				"</div>" +
			 "</div>"+
			 "<div class='col-md-2' style='margin-top:25px;'>" +
				"<div class='qfgd' id='B'>" +
					"<span style='font-size:12px;'>不检测动作</span>" +
				"</div>" +
			 "</div>" +
		"</div>" +
		"<div class='col-md-11'>" +
			 "<div class='col-md-7' style='margin-top:10px;'>" +
				"<div class='qfgd' id='C'>" +
					"<span style='font-size:12px;'>RFD自动读取</span>" +
				"</div>" +
			 "</div>"+
			 "<div class='col-md-2' style='margin-top:10px;'>" +
				"<div class='qfgd' id='D'>" +
					"<span style='font-size:12px;'></span>" +
				"</div>" +
			 "</div>" +
		"</div>";
		if(type=='yhsz_home'){
			html="";
		};
		return fun(html),html=null;
	},
	//按钮点击事件
	butClick:function(e,type){
		if(type == true){
			if($(e).attr("class")=="qfgd"){
				$(e).attr("class","qfgdStart");
				return null;
			}
			return null;
		}else{
			if($(e).attr("class")=="qfgdStart"){
				$(e).attr("class","qfgd");
				return null;
			}
			return null;
		}
		return null;
	},
	buttonA:true,buttonB:true,
	buttonC:true,buttonD:true,
	//获取当前操控按钮状态
	action:function(e,type,fun){
		//ajax处理
		var a = $.ajax({
			url: getRootPath()+'/HomeAction.do?operType=getCKButton',
			type: 'get',
			data: "type="+type+
				  "&buttonA="+af_Home.buttonA+
				  "&buttonB="+af_Home.buttonB+
				  "&buttonC="+af_Home.buttonC+
				  "&buttonD="+af_Home.buttonD,
			cache:false,
			success: function (data) {
				var obj = eval("("+data+")");
				//get
				if(type=="get"){
					af_Home.buttonA = obj.A;
					af_Home.buttonB = obj.B;
					af_Home.buttonC = obj.C;
					af_Home.buttonD = obj.D;
					return fun(e.A,obj.A),
						   fun(e.B,obj.B),
						   fun(e.C,obj.C),
						   fun(e.D,obj.D),
						   obj=null;
				//setA
				}else if(type=="A"){
					af_Home.buttonA = obj.type;
				//setB
				}else if(type=="B"){
					af_Home.buttonB = obj.type;
				//setC
				}else if(type=="C"){
					af_Home.buttonC = obj.type;
				//setD
				}else{
					af_Home.buttonD = obj.type;
				};
				return fun(e,obj.type),obj=null;
			}
		});
		return a=null;
	},
	//按钮操作事件
	winHtmlEvent:function(){
		$("#A").click(function(){
			var e=af_Home.action(this,'A',af_Home.butClick);
			return e=null;
		});
		$("#B").click(function(){
			var e=af_Home.action(this,'B',af_Home.butClick);
			return e=null;
		});
		$("#C").click(function(){
			var e=af_Home.action(this,'C',af_Home.butClick);
			return e=null;
		});
		$("#D").click(function(){
			var e=af_Home.action(this,'D',af_Home.butClick);
			return e=null;
		});
		var a=af_Home.action({A:$("#A"),B:$("#B"),C:$("#C"),D:$("#D")},'get',af_Home.butClick);
		return a=null;
	},
	click:function(url_){
		if(af_Home.arrBtn == url_){return null;}
		if(af_Home.dlInterval){af_Home.dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
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
	//获取急停按钮状态&&同时也是set函数
	getState:function(type,e,fun){
		var a = $.ajax({
			url: getRootPath()+'/HomeAction.do?operType=getState',
			type: 'get',
			data: "type="+type,
			cache:false,
			success: function (data){
				if(data==1){
					af_Home.div_mo_img_strat=true;
				}else{
					af_Home.div_mo_img_strat=false;
				};
				return fun(af_Home.div_mo_img_strat,e);
			}
		});
		return a=null;
	},
	//当前急停按钮的状态=默认停止状态
	div_mo_img_strat:true,
	load:function(fun){
		//关机
		$('#div_mo_img_close').mouseover(function(){
			var url = getRootPath()+"/images/guanjianniu_mo.png";
			$(this).attr("src",url);
			return url = null;
		});
		$('#div_mo_img_close').mouseout(function(){
			var url = getRootPath()+"/images/guanjianniu.png";
			$(this).attr("src",url); 
			return url = null;
		});
		//急停按钮
		$('#div_mo_img_strat').click(function(){
			var type=null;
			if(af_Home.div_mo_img_strat){//如果当前是停止状态则改为允许状态
				type=false;
			}else{
				type=true;
			};
			var a=af_Home.getState(type,this,function(r,e){
				var url = getRootPath()+"/images/fanhuianniu_hong.png";
				if(!r){
					url = getRootPath()+"/images/fanhuianniu_lv.png";	
				};
				$(e).attr("src",url);
				return url = null;
			});
			return type=null,a=null;
		});
		//用户设置
		$('#yhsz').mousedown(function(){
			var a=$('#yhsz_id_').show(0);
				a=$('#yhsz').hide(0);
			return a=null;
		});
		//系统设置
		$('#xtsz').mousedown(function(){
			var a=$('#xtsz_id_').show(0);
				a=$('#xtsz').hide(0);
			return a=null;
		});
		//按钮-主页
		$('#anniuHome').click(function(){
			var a = $("#homeRight").slideToggle(280,function(){
				return a = null;
			});
			return null;
		});
		$('#anniu1').click(function(){
			var a=af_Home.click(getRootPath()+'/html/dddd.html');
			return a=null;
		});
		//按钮-主页显示
		$('#anniu2').click(function(){
			var a=af_Home.click(getRootPath()+'/html/h_home.html');
			return a=null;
		});
		//按钮-plc
		$('#anniu3').click(function(){
			var a=af_Home.click(getRootPath()+'/html/plc.html');
			return a=null;
		});
		//按钮-库房操作
		$('#anniu4').click(function(){
			var a=af_Home.click(getRootPath()+'/html/kfcz.html');
			return a=null;
		});
		//按钮-基础设置
		$('#anniu5').click(function(){
			var a=af_Home.click(getRootPath()+'/html/jcsz.html');
			return a=null;
		});
		$(document).keydown(function (event) {
	        if (event.keyCode == 122) {
	        	var a = window.location.reload();
				return a=null;
	        }
	    });
		//显示主页;
		var a=$('#anniu2').click();
		//定时更新急停按钮状态
//		setInterval(function(){
//			var a=af_Home.getState('get',$('#div_mo_img_strat'),function(r,e){
//				var url = getRootPath()+"/images/fanhuianniu_hong.png";
//				if(!r){
//					url = getRootPath()+"/images/fanhuianniu_lv.png";	
//				};
//				$(e).attr("src",url);
//				return url = null;
//			});
//			return a=null;
//		},200);
		return fun(),a=null;
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