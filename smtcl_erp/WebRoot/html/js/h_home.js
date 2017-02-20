var readyShow = {
	deleteSetInterval:null,
	load:function(){
		try{
			var af = {
				/**
				 * 渲染主界面
				 */	
				load:function(returnFunction,dsState){
					var win = layer.open({type: 3});
					var winHeight = document.body.clientHeight;
					if(winHeight == window.screen.height){
						winHeight = document.body.clientHeight - 50;
					}
					$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
					$('.table-body').css('height', document.body.clientHeight /5.88);
					
					//刷新货位状态
					$("#getHck").click(function(){
						af.getHck();
					});
					//显示GDFrame
					$("#showGDFrame").click(function(){
						af.getGDFrame();
					});
					
					if(this.txload()&&this.getHck()&&dsState.state){
						(function(){//定时刷新
							readyShow.deleteSetInterval = setInterval(function(){
								if(af.getHck());
							},dsState.tim);
							dlInterval = true;
						})();
					}
					layer.close(win);
					return returnFunction();
				},
				//数组初始化
				arrayInitialization:function(){
					this.removeTop = [];
					this.removeArry = [];
					this.removeBottom = [];
				},
				/**
				 * 显示GDFrame
				 */
				getGDFrame:function(){
					$.ajax({
						url: getRootPath()+'/HomeAction.do?operType=getGDFrame',
						type: 'get',
						cache:false,
						success: function (data) {}
					});
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
						url: getRootPath()+'/HomeAction.do?operType=getHckState',
						type: 'get',
						cache:false,
						success: function (data) {
							var obj = eval("("+data+")");
							/**
							 * 异步输送线-上层
							 */
							if(obj.hckTop.length > 0){
								for(var i=0,j=0,k=af.topHome.length;i<k;i++){
									try{
										if(af.topHome.toString().indexOf('‘'+obj.hckTop[i][i]+'’') > -1){
											if(obj.hckTop[i][i]=="TOP-0ST"||obj.hckTop[i][i]=="TOP-14ST"){
												$("#"+obj.hckTop[i][i]).attr("class","head_lan yes");
											}else{
												$("#"+obj.hckTop[i][i]).attr("class","lan yes");
											}
											af.removeTop[i] = '‘'+obj.hckTop[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeTop.toString().indexOf(af.topHome[j]) == -1){
											var upId = af.topHome[j].split("’")[0].split('‘')[1];
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
							if(obj.hckTb.length > 0){
								for(var i=0,j=0,k=af.arrayHome.length;i<k;i++){
									try{
										if(af.arrayHome.toString().indexOf('‘'+obj.hckTb[i][i]+'’') > -1){
											$("#"+obj.hckTb[i][i]).attr("class","lan yes");
											af.removeArry[i] = '‘'+obj.hckTb[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeArry.toString().indexOf(af.arrayHome[j]) == -1){
											var upId = af.arrayHome[j].split("’")[0].split('‘')[1];
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
							if(obj.hckBottom > 0){
								for(var i=0,j=0,k=af.bottomHome.length;i<k;i++){
									try{
										if(af.bottomHome.toString().indexOf('‘'+obj.hckBottom[i][i]+'’') > -1){
											if(obj.hckBottom[i][i]=="BOTTOM-0ST"||obj.hckBottom[i][i]=="BOTTOM-14ST"){
												$("#"+obj.hckBottom[i][i]).attr("class","head_lan yes");
											}else{
												$("#"+obj.hckBottom[i][i]).attr("class","lan yes");
											}
											af.removeBottom[i] = '‘'+obj.hckBottom[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeBottom.toString().indexOf(af.bottomHome[j]) == -1){
											var upId = af.bottomHome[j].split("’")[0].split('‘')[1];
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
							if(Number(obj.gdWcl) > 0){
								af.upload(0,Number(obj.gdWcl));
							}
							/**
							 * 缓存库指定队列
							 */
//							if(obj.byjgzddl.length > 0){
//								af.table.loadHckzdd(obj.byjgzddl);
//							}
						}
					});
					return true;
				},
				/**
				 * 图片渲染数据
				 */
				txload:function(){
					var chart = {      
					   type: 'solidgauge'
					};
					var pane = {
					   center: ['55%', '80%'],
					   size: '150%',
					   startAngle: -90,
					   endAngle: 90,
					   background: {
					      backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
					      innerRadius: '60%',
					      outerRadius: '100%',
					      shape: 'arc'
					   }
					};
					var tooltip = {
					   enabled: false
					};
					var yAxis = {
						stops: [
						   [0.1, '#55BF3B'], // green
						   [0.5, '#DDDF0D'], // yellow
						   [0.9, '#DF5353'] // red
						],
						lineWidth: 0,
						minorTickInterval: null,
						tickPixelInterval: 400,
						tickWidth: 0,
						title: {
						   y: -70
						},
						labels: {
						   y: 16
						},
						min: 0,
						max: 100,
						title: {
						   text: ''
						}
					}; 
					var plotOptions = {
						solidgauge: {
						   dataLabels: {
						      y: 5,
						      borderWidth: 0,
						      useHTML: true
						   }
						}
					};
					var credits = {
					   enabled: false
					};
					//货位占用率
					var series = [{
					   name: 'Speed',
					   data: [0],
					   dataLabels: {
					      format: '<div style="text-align:center;"><span style="font-size:25px;color:' +
					      ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y:.1f}</span><br/>' +
					      '<span style="font-size:13px;color:silver">货位使用率%</span></div>'
					   },
					   tooltip: {
					      valueSuffix: ' 货位使用率%'
					   }
					}];
					var json = {};
					json.chart = chart;
					json.title = null;
					json.pane = pane;
					json.tooltip = tooltip;
					json.yAxis = yAxis;
					json.credits = credits;
					json.series = series;
					$('#container-speed').highcharts(json);
					//订单完成率
					series = [{
					   name: 'RPM',
					   data: [0],
					   dataLabels: {
					      format: '<div style="text-align:center;"><span style="font-size:25px;color:' +
					      ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y:.1f}</span><br/>' +
					      '<span style="font-size:13px;color:silver">订单完成率%</span></div>'
					   },
					   tooltip: {
					      valueSuffix: ' 订单完成率%'
					   }
					}];
					json.yAxis = yAxis; 
					json.series = series;
					$('#container-rpm').highcharts(json);
					return true;
				},
				/**
				 * 更新图片信息,参数必须是int类型
				 */
				upload:function(newVal1,newVal2){
					 // 货物使用率
				      var chart = $('#container-speed').highcharts();
				      var point;
				      var newVal;
				      var inc;
				      if (chart) {
				         point = chart.series[0].points[0];
				         point.update(newVal1);
				      }
				      //订单完成率
				      chart = $('#container-rpm').highcharts();
				      if (chart) {
				         point = chart.series[0].points[0];
				         point.update(newVal2);
				      }
				},
				/**
				 * 渲染表格
				 */
				table:{
					//缓存库指定队列
					loadHckzdd:function(e){
						$("#hc_table tbody tr").remove();
						for(var i=0;i<e.length;i++){
							$('#hc_table tbody').append('<tr bgcolor="#ffffff" style="height: 28px;">' +
								//事件ID
								'<td style="width: 35px;">'+e[i].idEvent +'</td>' +
								//动作
								'<td style="width: 50px;">'+e[i].dongzuo +'</td>' +
								//托盘编码
								'<td style="width: 50px;">'+e[i].tp_code+'</td>' +
								//状态
								'<td style="width: 35px;">'+e[i].zhuangtai+'</td>' +
								//开始时间
								'<td style="width: 50px;">'+e[i].fasongshijian+'</td>' +
								//完成时间
								'<td style="width: 50px;">'+e[i].wanchengshijian+'</td>' +
							'</tr>');
						}
					}
				}
			}
			af.load(function(){
				return null;
			},{state:false,tim:1000});//渲染主页面,function(){}--第一个返回参数,{ds:true--是否为定时刷新、tim:刷新时间毫秒为单位};
		}catch (e) {
			return e;
		}
		return null;
	}
};