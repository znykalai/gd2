var readyShow = function(){
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
	
	//获取输送线 库房状态
	$.ajax({
		url: getRootPath()+'/HomeAction.do?operType=getHckState',
		type: 'get',
		cache:false, 
		data: "",
		success: function (data) {
			var obj = eval("("+data+")");
			for(var i=0;i<obj.hckTb.length;i++){
			}
		}
	});
};