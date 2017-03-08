var af_Home = {
	arrBtn:null,
	winId:null,//鎷栨嫿windowID
	dlInterval:null,//瀹氭椂鎽ф瘉鍣�
	winHtml:function(fun,type){
		var html = "<div class='col-md-11'>" +
			 "<div class='col-md-7' style='margin-top:25px;'>" +
				"<div class='qfgd' id='A'>" +
					"<span style='font-size:12px;'>涓嶆娴嬫暟閲�</span>" +
				"</div>" +
			 "</div>"+
			 "<div class='col-md-2' style='margin-top:25px;'>" +
				"<div class='qfgd' id='B'>" +
					"<span style='font-size:12px;'>涓嶆娴嬪姩浣�</span>" +
				"</div>" +
			 "</div>" +
		"</div>" +
		"<div class='col-md-11'>" +
			 "<div class='col-md-7' style='margin-top:10px;'>" +
				"<div class='qfgd' id='C'>" +
					"<span style='font-size:12px;'>RFD鑷姩璇诲彇</span>" +
				"</div>" +
			 "</div>"+
			 "<div class='col-md-2' style='margin-top:10px;'>" +
				"<div class='qfgd' id='D'>" +
					"<span style='font-size:12px;'>鍚姩搴撴寚浠�</span>" +
				"</div>" +
			 "</div>" +
		"</div>";
		if(type=='yhsz_home'){
			html="";
		};
		return fun(html),html=null;
	},
	//鎸夐挳鐐瑰嚮浜嬩欢
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
	//鑾峰彇褰撳墠鎿嶆帶鎸夐挳鐘舵��
	action:function(e,type,fun){
		//ajax澶勭悊
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
	//鎸夐挳鎿嶄綔浜嬩欢
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
		if(af_Home.dlInterval){af_Home.dlInterval=null;clearInterval(readyShow.deleteSetInterval);}//閿�姣佸畾鏃跺櫒
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
	//鑾峰彇鎬ュ仠鎸夐挳鐘舵��&&鍚屾椂涔熸槸set鍑芥暟
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
	//褰撳墠鎬ュ仠鎸夐挳鐨勭姸鎬�=榛樿鍋滄鐘舵��
	div_mo_img_strat:true,
	load:function(fun){
		//鍏虫満
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
		//鎬ュ仠鎸夐挳
		$('#div_mo_img_strat').click(function(){
			var type=null;
			if(af_Home.div_mo_img_strat){//濡傛灉褰撳墠鏄仠姝㈢姸鎬佸垯鏀逛负鍏佽鐘舵��
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
		//鐢ㄦ埛璁剧疆
		$('#yhsz').mousedown(function(){
			var a=$('#yhsz_id_').show(0);
				a=$('#yhsz').hide(0);
			return a=null;
		});
		//绯荤粺璁剧疆
		$('#xtsz').mousedown(function(){
			var a=$('#xtsz_id_').show(0);
				a=$('#xtsz').hide(0);
			return a=null;
		});
		//鎸夐挳-涓婚〉
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
		//鎸夐挳-涓婚〉鏄剧ず
		$('#anniu2').click(function(){
			var a=af_Home.click(getRootPath()+'/html/h_home.html');
			return a=null;
		});
		//鎸夐挳-plc
		$('#anniu3').click(function(){
			var a=af_Home.click(getRootPath()+'/html/plc.html');
			return a=null;
		});
		//鎸夐挳-搴撴埧鎿嶄綔
		$('#anniu4').click(function(){
			var a=af_Home.click(getRootPath()+'/html/kfcz.html');
			return a=null;
		});
		//鎸夐挳-鍩虹璁剧疆
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
		//鏄剧ず涓婚〉;
		var a=$('#anniu2').click();
		//瀹氭椂鏇存柊鎬ュ仠鎸夐挳鐘舵��
		setInterval(function(){
			var a=af_Home.getState('get',$('#div_mo_img_strat'),function(r,e){
				var url = getRootPath()+"/images/fanhuianniu_hong.png";
				if(!r){
					url = getRootPath()+"/images/fanhuianniu_lv.png";	
				};
				$(e).attr("src",url);
				return url = null;
			});
			return a=null;
		},400);
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