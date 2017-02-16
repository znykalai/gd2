var readyShow = {
	load:function(){
		try{
			var winHeight = document.body.clientHeight;
			if(winHeight == window.screen.height){
				winHeight = document.body.clientHeight - 50;
			}
			$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/1.05);
			$('.table-body').css('height', document.body.clientHeight /5.88);
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
			   data: [70],
			   dataLabels: {
			      format: '<div style="text-align:center;"><span style="font-size:25px;color:' +
			      ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
			      '<span style="font-size:13px;color:silver">货位占用率%</span></div>'
			   },
			   tooltip: {
			      valueSuffix: ' 货位占用率%'
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
			   data: [40],
			   dataLabels: {
			      format: '<div style="text-align:center;"><span style="font-size:25px;color:' +
			      ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
			      '<span style="font-size:13px;color:silver">订单完成率%</span></div>'
			   },
			   tooltip: {
			      valueSuffix: ' 订单完成率%'
			   }
			}];
			json.yAxis = yAxis; 
			json.series = series;
			$('#container-rpm').highcharts(json);
			
			function showHck(){
				
				function updateState(){
					var topHome=['‘TOP-0ST’','‘TOP-1ST’','‘TOP-2ST’','‘TOP-3ST’','‘TOP-4ST’','‘TOP-5ST’','‘TOP-6ST’',
					               '‘TOP-7ST’','‘TOP-8ST’','‘TOP-9ST’','‘TOP-10ST’','‘TOP-11ST’','‘TOP-12ST’','‘TOP-13ST’','‘TOP-14ST’'];
					var removeTop=[];
					var arrayHome=['‘501’','‘502’','‘503’','‘504’','‘505’','‘506’','‘507’','‘508’','‘509’','‘510’','‘511’','‘512’','‘513’','‘514’',
						             '‘1’','‘2’','‘3’','‘4’','‘5’','‘6’','‘7’','‘8’','‘9’','‘10’','‘11’','‘12’','‘13’','‘14’','‘15’',
						             '‘16’','‘17’','‘18’','‘19’','‘20’','‘21’','‘22’','‘23’','‘24’','‘25’','‘26’','‘27’','‘28’',
						             '‘601’','‘602’','‘603’','‘604’','‘605’','‘606’','‘607’','‘608’','‘609’','‘610’','‘611’','‘612’','‘613’','‘614’'];
					var removeArry=[];
					var bottomHome=['‘BOTTOM-0ST’','‘BOTTOM-1ST’','‘BOTTOM-2ST’','‘BOTTOM-3ST’','‘BOTTOM-4ST’','‘BOTTOM-5ST’','‘BOTTOM-6ST’',
					                  '‘BOTTOM-7ST’','‘BOTTOM-8ST’','‘BOTTOM-9ST’','‘BOTTOM-10ST’','‘BOTTOM-11ST’',
					                  '‘BOTTOM-12ST’','‘BOTTOM-13ST’','‘BOTTOM-14ST’'];
					var removeBottom=[];
					//获取输送线 库房状态
					$.ajax({
						url: getRootPath()+'/HomeAction.do?operType=getHckState',
						type: 'get',
						cache:false,
						data: "",
						success: function (data) {
							var obj = eval("("+data+")");
							/**
							 * 缓存库上层
							 */
//							for(var i=0;i<obj.hckTop.length;i++){
//								
//							}
							/**
							 * 缓存库中间层
							 */
							for(var i=0,j=0;i<arrayHome.length;i++){
								try{
									if(arrayHome.toString().indexOf('‘'+obj.hckTb[i][i]+'’') > -1){
										$("#"+obj.hckTb[i][i]).attr("class","lan yes");
										removeArry[i] = '‘'+obj.hckTb[i][i]+'’';
									}
								}catch (e) {
									if(removeArry.toString().indexOf(arrayHome[j]) == -1){
										var upId = arrayHome[j].split("’")[0].split('‘')[1];
										$("#"+upId).attr("class","hui no");
									}
									j++;
								}
							}
							/**
							 * 缓存库下层
							 */
//							for(var i=0;i<obj.hckBottom.length;i++){
//								
//							}
						}
					});
					return true;
				}
				return updateState();
			}
			
			$("#bbbb").click(function(){
				alert(showHck());
			});
			
		}catch (e) {
			alert(e);
			return null;
		}
		return null;
	}
};