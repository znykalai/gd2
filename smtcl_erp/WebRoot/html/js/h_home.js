var readyShow={
	deleteSetInterval:null,
	load:function(hdFun){
		try{
			var af={
				/**
				 * 渲染主界面
				 */	
				load:function(returnFunction,dsState){
					var win=layer.open({type:3});
					var winHeight=document.body.clientHeight;
					if(winHeight==window.screen.height){
						winHeight=document.body.clientHeight-50;
					}
					//设置高度自适应
					$('#xy').css('height',(winHeight-(window.screen.height-winHeight))/0.98);
					$('.table-body').css('height',document.body.clientHeight/5.88);
					this.txload();//图形渲染
					this.loadButton();//按钮事件启动
					//货位渲染
					if(this.getHck()&&dsState.state){//是否启动定时刷新
						readyShow.deleteSetInterval=setInterval(function(){
							var a=af.getHck();a=null;
						},dsState.tim),af_Home.dlInterval=true;
					}
					var ly=layer.close(win),win=null,ly=null,winHeight=null;
					return returnFunction();
				},
				//数组初始化
				arrayInitialization:function(){
					this.removeTop=[],
					this.removeArry=[],
					this.removeBottom=[];
					return null;
				},
				/**
				 * 异步输送线top
				 */
				topHome:['‘TOP-0ST’','‘TOP-1ST’','‘TOP-2ST’','‘TOP-3ST’','‘TOP-4ST’','‘TOP-5ST’','‘TOP-6ST’',
				         '‘TOP-7ST’','‘TOP-8ST’','‘TOP-9ST’','‘TOP-10ST’','‘TOP-11ST’','‘TOP-12ST’','‘TOP-13ST’','‘TOP-14ST’'],
                removeTop:[],
                /**
                 * 缓存库
                 */
                arrayHome:['‘501’','‘502’','‘503’','‘504’','‘505’','‘506’','‘507’','‘508’','‘509’','‘510’','‘511’','‘512’','‘513’','‘514’',
                           '‘1’','‘2’','‘3’','‘4’','‘5’','‘6’','‘7’','‘8’','‘9’','‘10’','‘11’','‘12’','‘13’','‘14’','‘15’',
                           '‘16’','‘17’','‘18’','‘19’','‘20’','‘21’','‘22’','‘23’','‘24’','‘25’','‘26’','‘27’','‘28’',
                           '‘601’','‘602’','‘603’','‘604’','‘605’','‘606’','‘607’','‘608’','‘609’','‘610’','‘611’','‘612’','‘613’','‘614’'],
	            removeArry:[],
	            /**
	             * 异步输送线bottom
	             */
	            bottomHome:['‘BOTTOM-0ST’','‘BOTTOM-1ST’','‘BOTTOM-2ST’','‘BOTTOM-3ST’','‘BOTTOM-4ST’','‘BOTTOM-5ST’','‘BOTTOM-6ST’',
			                  '‘BOTTOM-7ST’','‘BOTTOM-8ST’','‘BOTTOM-9ST’','‘BOTTOM-10ST’','‘BOTTOM-11ST’',
			                  '‘BOTTOM-12ST’','‘BOTTOM-13ST’','‘BOTTOM-14ST’'],
			    removeBottom:[],
			    /**
			     * 更新实时状态
			     */
				getHck:function(){
					this.arrayInitialization();
					//获取输送线 库房状态
					$.ajax({
						url:getRootPath()+'/HomeAction.do?operType=getHckState',
						type:'get',
						cache:false,
						success:function (data) {
							var obj=eval("("+data+")");
							/**
							 * 异步输送线-上层
							 */
							if(obj.hckTop.length>0){
								for(var i=0,j=0,k=af.topHome.length;i<k;i++){
									try{
										if(af.topHome.toString().indexOf('‘'+obj.hckTop[i][i]+'’')>-1){
											if(obj.hckTop[i][i]=="TOP-0ST"||obj.hckTop[i][i]=="TOP-14ST"){
												$("#"+obj.hckTop[i][i]).attr("class","head_lan yes");
											}else{
												$("#"+obj.hckTop[i][i]).attr("class","lan yes");
											}
											af.removeTop[i]='‘'+obj.hckTop[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeTop.toString().indexOf(af.topHome[j])==-1){
											var upId=af.topHome[j].split("’")[0].split('‘')[1];
											if(upId=="TOP-0ST"||upId=="TOP-14ST"){
												$("#"+upId).attr("class","head_hui no");
											}else{
												$("#"+upId).attr("class","hui no");
											}
										}
										j++;
									}
								}
							}else{
								$("div[name='HEAD_TOP-ST']").attr("class","head_hui no");
								$("div[name='TOP-ST']").attr("class","hui no");
							}
							/**
							 * 缓存库中间层
							 */
							if(obj.hckTb.length>0){
								for(var i=0,j=0,k=af.arrayHome.length;i<k;i++){
									try{
										if(af.arrayHome.toString().indexOf('‘'+obj.hckTb[i][i]+'’')>-1){
											$("#"+obj.hckTb[i][i]).attr("class","lan yes");
											af.removeArry[i]='‘'+obj.hckTb[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeArry.toString().indexOf(af.arrayHome[j])==-1){
											var upId=af.arrayHome[j].split("’")[0].split('‘')[1];
											$("#"+upId).attr("class","hui no");
										}
										j++;
									}
								}
							}else{
								$("div[name='HCK-NAME']").attr("class","hui no");
							}
							/**
							 * 缓存库下层
							 */
							if(obj.hckBottom>0){
								for(var i=0,j=0,k=af.bottomHome.length;i<k;i++){
									try{
										if(af.bottomHome.toString().indexOf('‘'+obj.hckBottom[i][i]+'’')>-1){
											if(obj.hckBottom[i][i]=="BOTTOM-0ST"||obj.hckBottom[i][i]=="BOTTOM-14ST"){
												$("#"+obj.hckBottom[i][i]).attr("class","head_lan yes");
											}else{
												$("#"+obj.hckBottom[i][i]).attr("class","lan yes");
											}
											af.removeBottom[i]='‘'+obj.hckBottom[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeBottom.toString().indexOf(af.bottomHome[j])==-1){
											var upId=af.bottomHome[j].split("’")[0].split('‘')[1];
											if(upId=="BOTTOM-0ST"||upId=="BOTTOM-14ST"){
												$("#"+upId).attr("class","head_hui no");
											}else{
												$("#"+upId).attr("class","hui no");
											}
										}
										j++;
									}
								}
							}else{
								$("div[name='HEAD_BOTTOM-ST']").attr("class","head_hui no");
								$("div[name='BOTTOM-ST']").attr("class","hui no");
							}
							/**
							 * 货物使用率,订单完成率；必须是int类型
							 */
							var hwSyl=Number(obj.hwSyl)>0?Number(obj.hwSyl):0;
							var gdWcl=Number(obj.gdWcl)>0?Number(obj.gdWcl):0;
							af.upload(hwSyl,gdWcl);
							return obj=null,hwSyl=null,gdWcl=null;
						}
					});
					return true;
				},
				/**
				 * 图片渲染数据
				 */
				txload:function(){
					var chart={
					   type:'solidgauge'
					};
					var pane={
					   center:['55%','80%'],
					   size:'150%',
					   startAngle:-90,
					   endAngle:90,
					   background:{
					      backgroundColor:(Highcharts.theme && Highcharts.theme.background2) || '#EEE',
					      innerRadius:'60%',
					      outerRadius:'100%',
					      shape:'arc'
					   }
					};
					var tooltip={
					   enabled:false
					};
					var yAxis={
						stops:[
						   [0.1,'#55BF3B'],// green
						   [0.5,'#DDDF0D'],// yellow
						   [0.9,'#DF5353'] // red
						],
						lineWidth:0,
						minorTickInterval:null,
						tickPixelInterval:400,
						tickWidth:0,
						title:{
						   y:-70
						},
						labels:{
						   y:16
						},
						min:0,
						max:100,
						title:{
						   text:''
						}
					};
					var plotOptions={
						solidgauge:{
						   dataLabels:{
						      y:5,
						      borderWidth:0,
						      useHTML:true
						   }
						}
					};
					var credits={
					   enabled:false
					};
					//货位占用率
					var series=[{
					   name:'Speed',
					   data:[0],
					   dataLabels:{
					      format:'<div style="text-align:center;"><span style="font-size:15px;color:red;">{y:.0f}%</span><br/>' +
					      '<span style="font-size:10px;color:silver;">货位使用率</span></div>'
					   }
					}];
					var json={};
					json.chart=chart;
					json.title=null;
					json.pane=pane;
					json.tooltip=tooltip;
					json.yAxis=yAxis;
					json.credits=credits;
					json.series=series;
					$('#container-speed').highcharts(json);
					//订单完成率
					series=[{
					   name:'RPM',
					   data:[0],
					   dataLabels:{
					      format:'<div style="text-align:center;"><span style="font-size:15px;color:red;">{y:.1f}%</span><br/>' +
					      '<span style="font-size:10px;color:silver;">订单完成率</span></div>'
					   }
					}];
					json.yAxis=yAxis; 
					json.series=series;
					$('#container-rpm').highcharts(json);
					return chart=null,pane=null,tooltip=null,yAxis=null,plotOptions=null,credits=null,series=null,json=null;
				},
				/**
				 * 更新图片信息,参数必须是int类型
				 */
				upload:function(newVal1,newVal2){
					 // 货物使用率
				      var chart=$('#container-speed').highcharts();
				      var point;
				      var newVal;
				      var inc;
				      if (chart) {
				         point=chart.series[0].points[0];
				         point.update(newVal1);
				      }
				      //订单完成率
				      chart=$('#container-rpm').highcharts();
				      if (chart) {
				         point=chart.series[0].points[0];
				         point.update(newVal2);
				      }
				      return chart=null,point=null,newVal=null,inc=null;
				},
				/**
				 * 按钮：调度、复位、归零、断点
				 */
				loadButton:function(){
					var but={
						buttonTop:function(){
							//复位点击效果-top
							$("#fuwei_top").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#fuwei_top").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							//归零启动点击效果-top
							$("#guilingqidong_top").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#guilingqidong_top").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							//断点启动点击效果-top
							$("#duandianqidong_top").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#duandianqidong_top").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							return null;
						},
						buttonBottom:function(){
							//复位点击效果-bottom
							$("#fuwei_bottom").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#fuwei_bottom").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							//归零启动点击效果-bottom
							$("#guilingqidong_bottom").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#guilingqidong_bottom").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							//断点启动点击效果-bottom
							$("#duandianqidong_bottom").mousedown(function(){
								$(this).attr("class","qfgdStart");
								return null;
							});
							$("#duandianqidong_bottom").mouseup(function(){
								$(this).attr("class","qfgd");
								return null;
							});
							return null;
						},
						//启动调度是否成功状态标识
						qidongdiaodu_top_type:false,
						qidongdiaodu_bottom_type:false,
						//通用点击事件
						butClick:function(e,type){
							if(type==false){
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
						action:function(e,type,fun){
							//ajax处理
							var a=$.ajax({
								url:getRootPath()+'/HomeAction.do?operType=getHckButton',
								type:'get',
								data:"type="+type+"&" +
										"A="+but.qidongdiaodu_top_type+"&" +
										"B="+but.qidongdiaodu_bottom_type,
								cache:false,
								success:function (data) {
									var obj=eval("("+data+")");
									if(type=="get"){
										but.qidongdiaodu_top_type=obj.A;
										but.qidongdiaodu_bottom_type=obj.B;
										return fun(e.A,obj.A),fun(e.B,obj.B),obj=null;
									}else if(type=='top'){
										but.qidongdiaodu_top_type=obj.type;
									}else if(type=="bottom"){
										but.qidongdiaodu_bottom_type=obj.type;
									}else{
										layer.msg(obj.type);
									}
									return fun(e,obj.type),obj=null;
								}
							});
							return a=null;
						},
						loadEvn:function(){
							/**************启动调度-top***************/
							$("#qidongdiaodu_top").click(function(){
								var e=but.action(this,'top',but.butClick);
								return e=null;
							});
							//复位-top
							$("#fuwei_top").click(function(){
								var e=but.action(this,'fuwei_top',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							//归零启动-top
							$("#guilingqidong_top").click(function(){
								var e=but.action(this,'guilingqidong_top',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							//断点启动-top
							$("#duandianqidong_top").click(function(){
								var e=but.action(this,'duandianqidong_top',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							/**************启动调度-bottom***************/
							$("#qidongdiaodu_bottom").click(function(){
								var e=but.action(this,'bottom',but.butClick);
								return e=null;
							});
							//复位-bottom
							$("#fuwei_bottom").click(function(){
								var e=but.action(this,'fuwei_bottom',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							//归零启动-bottom
							$("#guilingqidong_bottom").click(function(){
								var e=but.action(this,'guilingqidong_bottom',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							//断点启动-bottom
							$("#duandianqidong_bottom").click(function(){
								var e=but.action(this,'duandianqidong_bottom',function(a,b){
									return a=null,b=null;
								});
								return e=null;
							});
							this.buttonTop(),
							this.buttonBottom();
							//获取启动调度按钮状态A,B
							but.action({A:$("#qidongdiaodu_top"),B:$("#qidongdiaodu_bottom")},'get',but.butClick);
							return null;
						}
					};
					return but.loadEvn();
				}
			}
			af.load(function(){
				return null;
			},{state:true,tim:1000});//渲染主页面,function(){}--第一个返回参数,{ds:true--是否为定时刷新、tim:刷新时间毫秒为单位};
		}catch (e) {
			return e;
		}
		return hdFun();
	}
};