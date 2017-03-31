var af_Home={
	winId:null,//拖拽windowID
	arrBtn:null,
	dlInterval:null,//定时摧毁器
	administrator:null,	//权限
	winHtml:function(fun,type){
		var html="<div class='col-md-11'><div class='col-md-7' style='margin-top:25px;'><div class='qfgd' id='A'><span style='font-size:12px;'>不检测数量</span></div></div><div class='col-md-2' style='margin-top:25px;'><div class='qfgd' id='B'><span style='font-size:12px;'>不检测动作</span></div></div></div><div class='col-md-11'><div class='col-md-7' style='margin-top:10px;'><div class='qfgd' id='C'><span style='font-size:12px;'>RFD自动读取</span></div></div><div class='col-md-2' style='margin-top:10px;'><div class='qfgd' id='D'><span style='font-size:12px;'>启动库指令</span></div></div></div>";
		if(type=='yhsz_home'){html="";};
		return fun(html),html=null;
	},
	butClick:function(e,type){
		if(type==true){if($(e).attr("class")=="qfgd"){$(e).attr("class","qfgdStart");return null;};return null;}else{if($(e).attr("class")=="qfgdStart"){$(e).attr("class","qfgd");return null;};return null;};
	},
	buttonA:true,buttonB:true,buttonC:true,buttonD:true,
	//获取当前操控按钮状态
	action:function(e,type,fun){
		var a=$.ajax({//ajax处理
			url:getRootPath()+'/HomeAction.do?operType=getCKButton',
			type:'get',data:"type="+type+
				  "&buttonA="+af_Home.buttonA+
				  "&buttonB="+af_Home.buttonB+
				  "&buttonC="+af_Home.buttonC+
				  "&buttonD="+af_Home.buttonD,
			cache:false,success:function(data){
				var obj=eval("("+data+")");
				if(type=="get"){//get
					af_Home.buttonA=obj.A;af_Home.buttonB=obj.B;
					af_Home.buttonC=obj.C;af_Home.buttonD=obj.D;
					return fun(e.A,obj.A),fun(e.B,obj.B),fun(e.C,obj.C),fun(e.D,obj.D),obj=null;
				//setA
				}else if(type=="A"){af_Home.buttonA=obj.type;
				//setB
				}else if(type=="B"){af_Home.buttonB=obj.type;
				//setC
				}else if(type=="C"){af_Home.buttonC=obj.type;
				//setD
				}else{af_Home.buttonD=obj.type;};
				return fun(e,obj.type),obj=null;
			}
		});a=null;
		return null;
	},
	//按钮操作事件
	winHtmlEvent:function(){
		if(this.administrator.不检测数量){var a=$("#A").click(function(){var e=af_Home.action(this,'A',af_Home.butClick);e=null;return null;});a=null;}else{var a=af_Home.cleanQX("A");a=null;};
		if(this.administrator.不检测动作){var a=$("#B").click(function(){var e=af_Home.action(this,'B',af_Home.butClick);e=null;return null;});a=null;}else{var a=af_Home.cleanQX("B");a=null;};
		if(this.administrator.RFD自动读取){var a=$("#C").click(function(){var e=af_Home.action(this,'C',af_Home.butClick);e=null;return null;});a=null;}else{var a=af_Home.cleanQX("C");a=null;};
		if(this.administrator.启动库指令){var a=$("#D").click(function(){var e=af_Home.action(this,'D',af_Home.butClick);e=null;return null;});a=null;}else{var a=af_Home.cleanQX("D");a=null;};
		var a=af_Home.action({A:$("#A"),B:$("#B"),C:$("#C"),D:$("#D")},'get',af_Home.butClick);a=null;
		return null;
	},
	click:function(url_){
		if(af_Home.arrBtn==url_){return null;}
		if(af_Home.dlInterval){af_Home.dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//销毁定时器
		$("#home_div").val(null);$("#btn_id").val(null);readyShow=null;af_Home.arrBtn=url_;
	 	var a=$.ajax({
		    url:url_,type:'get',cache:false,
		    success:function(data){
		    	var spl="<div class=\"col-md-3\" style=\"width:400px;\"></div>";
		    	var body=data.split("<body>");
		    	var html=body[1].split("</body>")[0];
		    	var home_html=html.split(spl)[0];
		    	var home_btn=spl + html.split(spl)[1];
				$("#home_div").html(home_html);
				$("#btn_id").html(home_btn);
				spl=null;body=null;home_html=null;home_btn=null;
				var a=readyShow.load(function(){return null;});a=null;
				return null;
		    }
		});url_=null;a=null;return null;
	},
	//获取急停按钮状态&&同时也是set函数
	getState:function(type,e,fun){
		var a=$.ajax({
			url:getRootPath()+'/HomeAction.do?operType=getState',
			type:'get',data:"type="+type,cache:false,
			success:function(data){
				if(data==1){af_Home.div_mo_img_strat=true;}else{af_Home.div_mo_img_strat=false;};
				return fun(af_Home.div_mo_img_strat,e),e=null,type=null,fun=null,data=null;
			}
		});a=null;
		return null;
	},
	div_mo_img_strat:true,//当前急停按钮的状态=默认停止状态
	getQX:function(fun){//获取权限
		var a=$.ajax({
			url:getRootPath()+'/HomeAction.do?operType=getQX',
			type:'get',cache:false,success:function(data){
				var a=af_Home.administrator=data==''?null:eval("("+data+")");
				data=null;a=null;return fun();
			}
		});a=null;
		return null;
	},
	/***清空权限***/
	cleanQX:function(id){
		var a=$("#"+id).hide();a=null;
		var b=$('#'+id).unbind("click");b=null;
		return null;
	},
	load:function(fun){
		//关机
		$('#div_mo_img_close').mouseover(function(){var url=getRootPath()+"/images/guanjianniu_mo.png";$(this).attr("src",url);url=null;return null;});
		$('#div_mo_img_close').mouseout(function(){var url=getRootPath()+"/images/guanjianniu.png";$(this).attr("src",url);url=null;return null;});
		$('#div_mo_img_strat').click(function(){//急停事件
			var type;if(af_Home.div_mo_img_strat){type=false;}else{type=true;};//如果当前是停止状态则改为允许状态
			var a=af_Home.getState(type,this,function(r,e){var url=getRootPath()+"/images/fanhuianniu_hong.png";if(!r){url=getRootPath()+"/images/fanhuianniu_lv.png";};$(e).attr("src",url);url=null;return null;});a=null;type=null;return null;
		});
		$('#yhsz').mousedown(function(){var a=$('#yhsz_id_').show();a=null;var b=$('#yhsz').hide();b=null;});//用户设置
		$('#xtsz').mousedown(function(){var a=$('#xtsz_id_').show();a=null;var b=$('#xtsz').hide();b=null;});//系统设置
		$('#anniuHome').click(function(){var a=$("#homeRight").slideToggle(300),a=null;return null;});//按钮-显示菜单
		$('#anniu1').click(function(){var a=af_Home.click(getRootPath()+'/html/dddd.html');a=null;return null;});//订单调度
		$('#anniu2').click(function(){var a=af_Home.click(getRootPath()+'/html/h_home.html');a=null;return null;});//按钮-主页显示
		$('#anniu3').click(function(){var a=af_Home.click(getRootPath()+'/html/plc.html');a=null;return null;});//按钮-plc
		$('#anniu4').click(function(){var a=af_Home.click(getRootPath()+'/html/kfcz.html');a=null;return null;});//按钮-库房操作
		$('#anniu5').click(function(){var a=af_Home.click(getRootPath()+'/html/jcsz.html');a=null;return null;});//按钮-基础设置
		$(document).keydown(function(event){if(event.keyCode==122){var a=window.location.reload();a=null;return null;};});//F11
		var b=this.getQX(function(){//获取权限
			var a=$('#anniu2').click();a=null;//显示主页;
		});b=null;
//		setInterval(function(){//定时更新急停按钮状态
//			var a=af_Home.getState('get',$('#div_mo_img_strat'),function(r,e){
//				var url=getRootPath()+"/images/fanhuianniu_hong.png";
//				if(!r){url=getRootPath()+"/images/fanhuianniu_lv.png";};
//				$(e).attr("src",url);url=null;
//				return null;
//			});a=null;return null;
//		},1000);
		return null;
	}
};
$(document).ready(function(){var a=af_Home.load();a=null;});