var readyShow = {
	deleteSetInterval:null,
	load:function (){
		try{
			var af = {
				load:function(returnFunction,dsState){
					this.show(dsState);
					return returnFunction();
				},
				show:function(dsState){
					layer.config({
					    path: '../../js/lib/layer/'
					});
					var winLayer = layer;
					/*绑定日历选择器*/
					$('#getGdfenjieriqi').datetimepicker({
					    format: 'yyyy-mm-dd',
					    autoclose: true,
					    pickerPosition: 'bottom-left',
					    todayBtn: 1,
					    linkFormat: 'yyyy-mm-dd',
					    minView: 'month'
					});
					//清空当前选择行的记录，防止报错
					$('#getGdfenjieriqi').on('change',function(){
						arrayGd=[],arrayMz=[];
					});
					var winHeight = document.body.clientHeight;
					if(winHeight == window.screen.height){
						winHeight = document.body.clientHeight - 50;
					}
					$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/1.05);
					var dd_id,mz_xuhao,arrayGd=[],arrayMz=[];
					$('.table-body').css('height', document.body.clientHeight /5.88);
					//工单模组-配方显示
					function showGdMzPfList(dd_id,mz_xuId){
						$('#pf_table tbody tr').remove();//配方table
						$.ajax({
							url: getRootPath()+'/OrderOperAction.do?operType=getZlpfList',
							type: 'post',cache:false, 
							data: "dd_id="+dd_id+"&mz_xuId="+mz_xuId,
							success: function (data) {
				  				var obj = eval("("+data+")");
								for(var i=0;i < obj.length;i++){
									$('#pf_table tbody').append('<tr id="mz_row'+i+'" bgcolor="#ffffff" style="height: 28px;">' +
										//工单序号
										'<td style="width: 5%;padding:2px;">'+obj[i].dd_gdxuhao +'</td>' +
										//载具号
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_zaijuxuhao +'</td>' +
										//物料编码
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_wuliao+'</td>' +
										//物料描述
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_wuliaomiaoshu+'</td>' +
										//需求数量
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_xuqiushuliang+'</td>' +
										//完成数量
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_wanchengshuliang+'</td>' +
										//电芯1
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_dianxin1+'</td>' +
										//电芯2
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_dianxin2+'</td>' +
										//电芯3
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_dianxin3+'</td>' +
										//电芯3
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_dianxin4+'</td>' +
									'</tr>');
								}
							}
						});
						return null;
					}
					//工单模组显示
					function showGdMzList(obj,win){
						dd_id="",mz_xuId="";//清空条件
						$('#mz_table tbody tr').remove();//模组table
						$('#pf_table tbody tr').remove();//配方table
						for(var i=0;i < obj.length;i++){
							$('#mz_table tbody').append('<tr id="mz_row'+i+'" bgcolor="#ffffff" style="height: 28px;">' +
							//单选
							'<td style="width: 2%;padding:2px;">'+
								'<input type="radio" name="trMzRadio"/>' +
								'<input type="hidden" id="mz_xuId'+i+'" value="'+obj[i].mz_xuId+'">'+
							'</td>' +
							//模组类型
							'<td style="width: 8%;padding:2px;">'+obj[i].mz_leixing +
							'<input type="hidden" id="dd_id'+i+'" value="'+obj[i].dd_id+'"></td>' +
							//模组编码
							'<td style="width: 7%;padding:2px;">'+obj[i].mz_code+'</td>' +
							//数量
							'<td style="width: 10%;padding:2px;">'+obj[i].mz_shuliang+'</td>' +
							//完成进度
							'<td style="width: 3%;padding:3px;">'+
								'<div class="progress progress-striped active" style="margin-bottom:0px;">'+
									'<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:'+obj[i].mz_jindu+'%;"></div>'+
								'</div>'+
							'</td>' +
							'</tr>');
							$('#mz_row'+i).click(function(){
								$(this).children("td").eq(0).find("input:radio").prop("checked",true);
								dd_id = $(this).children("td").eq(1).find("input:hidden").val();
								mz_xuId = $(this).children("td").eq(0).find("input:hidden").val();
				 				arrayMz[arrayGd[0]] = [this.id.split("row")[1]];
								if(win){
									layer.close(win);
								};
								return showGdMzPfList(dd_id,mz_xuId,win);
							});
						};
						if(win){
							layer.close(win);
						};
						if(obj.length>0){
							if(arrayMz[arrayGd[0]]&&arrayMz[arrayGd[0]].length > 0){
								$('#mz_row'+arrayMz[arrayGd[0]][0]).click();
							}else{
								arrayMz[arrayGd[0]]= [0];
								$('#mz_row0').click();
							}
						}
						return null;
					}
					//工单
					function showGdList(getGdId,getPackeCode,getGdfenjieriqi,deleteType,win){
						$.ajax({
							url: getRootPath()+'/OrderOperAction.do?operType=getDdList',
							type: 'post',cache:false, 
							data: "getGdId="+getGdId+"&getGdfenjieriqi="+getGdfenjieriqi+"&getPackeCode="+getPackeCode,
							success: function (data) {
				  				var obj = eval("("+data+")");
								$('#dd_table tbody tr').remove();
								var i = 0;
								var objLength = obj.length;
								while(i < objLength){
				  					$('#dd_table tbody').append('<tr id="gd_row'+i+'" bgcolor="#ffffff" style="height: 28px;">' +
				  						//单选
										'<td id="radio_id_'+i+'" style="width: 3%;padding:2px;">'+
											'<input type="radio" id="trRadio_dd' + obj[i].id + '" name="trGdRadio" value="'+obj[i].id+'"/>' +
										'</td>' +
										//工单序号
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_xuhao +
										'<input type="hidden" id="dd_code'+i+'" value="'+obj[i].dd_code+'"></td>' +
										//状态
										'<td style="width: 7%;padding:2px;">'+obj[i].dd_zhuangtai+'</td>' +
										//分解日期
										'<td style="width: 10%;padding:2px;">'+obj[i].dd_fenjieriqi+'</td>' +
										//PACK编码
										'<td style="width: 10%;padding:2px;">'+obj[i].pack_code+'</td>' +
										//PACK类型
										'<td style="width: 8%;padding:2px;">'+obj[i].pack_leixing+'</td>' +
										//装配区
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_zhuangpeiqu+'</td>' +
										//计划数量
										'<td style="width: 8%;padding:2px;">'+obj[i].dd_jihuashuliang+'</td>' +
										//完成进度
										'<td style="width: 5%;padding:3px;">'+
											'<div class="progress progress-striped active" style="margin-bottom:0px;">'+
												'<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:'+obj[i].dd_jindu+'%;"></div>'+
											'</div>'+
										'</td>'+
									'</tr>');
									$("#gd_row" + i).click(function(){
										var rowIndex = this.id.split("row")[1];
										$(this).children("td").eq(0).find("input:radio").prop("checked",true);
										var gd_id = $(this).children("td").eq(0).find("input:radio").val();
										var pack_code = $(this).children("td").eq(4).html();
										$.ajax({
											url: getRootPath()+'/OrderOperAction.do?operType=getZlmzList',
											type: 'post',cache:false, 
											data: "dd_id="+gd_id+"&pack_code="+pack_code,
											success: function (data) {
								  				arrayGd[0] = rowIndex;
								  				return showGdMzList(eval("("+data+")"),win);
											}
										});
										return null;
									});
									i++;
								}
				  				if(obj.length > 0){
				  					if(arrayGd.length > 0 && deleteType==false){
										$("#gd_row"+arrayGd[0]).click();
									}else{
										arrayGd[0] = $('#dd_table tbody tr').eq(0).attr("id");
										$('#dd_table tbody tr').eq(0).click();
									}
				  				}else{
									if(win){
										layer.close(win);
									};
									dd_id="",mz_xuId="";//清空条件
									$('#mz_table tbody tr').remove();//模组table
									$('#pf_table tbody tr').remove();//配方table
				  				}
							}
						});
						return true;
					}
					//查询
					$("#dd_selectBtn").click(function(){
						var getGdId = $("#getGdId").val();
						var getPackeCode = $("#getPackeCode").val();
						var getGdfenjieriqi = $("#getGdfenjieriqi").val();
						return showGdList(getGdId,getPackeCode,getGdfenjieriqi,false,winLayer.open({type: 3}));
					});
					//选择分解
					$("#dd_fenjieRadioBtn").click(function(){
						var cheBoolean = $("input[name='trGdRadio']").is(':checked');
						if (cheBoolean) {
							var DdType = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(2).html();
							if(DdType=="初始化"){
								var dd_table = $('#dd_table tbody tr');//工单行
								var thisRow = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(1).text();
								var yesFenjie = "";
								for(var i=0; i < dd_table.length; i++){
									if(dd_table.eq(i).children("td").eq(2).text()=="初始化"){
										yesFenjie = dd_table.eq(i).children("td").eq(1).text();
										break;
									}
								}
								if(yesFenjie!=""&&yesFenjie!=thisRow){
									layer.msg("按照顺序分解，请先分解第"+yesFenjie+"行工单！");
									return null;
								}
								var gd_id = $("input[name='trGdRadio']:checked").val();
								var pack_code = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(4).html();
								$.ajax({
									url: getRootPath()+'/OrderOperAction.do?operType=fenjieRadioBtn',
									type: 'post',cache:false, 
									data: "dd_id="+gd_id+"&pack_code="+pack_code,
									success: function (data) {
										showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}));
				  						layer.msg("分解成功！");
									}
								});
							}else{
				  				layer.msg("非初始化数据不可分解！");
							}
						}
						return null;
					});
					//分解全部
					$("#dd_fenjieAllBtn").click(function(){
						$.ajax({
							url: getRootPath()+'/OrderOperAction.do?operType=fenjieAllBtn',
							type: 'post',cache:false, 
							success: function (data) {
								showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}));
								layer.msg("分解成功！");
							}
						});
						return null;
					});
					//下载
					$("#dd_dowBtn").click(function(){
						$.ajax({
							url: getRootPath()+'/OrderOperAction.do?operType=downloadBtn',
							type: 'post',cache:false, 
							success: function (data) {
								showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}));
								layer.msg("下载成功！");
							}
						});
						return null;
					});
					//删除
					$("#dd_delBtn").click(function(){
						var cheBoolean = $("input[name='trGdRadio']").is(':checked');
						if (cheBoolean) {
							var DdType = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(2).html();
							if(DdType=="初始化"||DdType=="已分解"){
								var gd_id = $("input[name='trGdRadio']:checked").val();
								$.ajax({
									url: getRootPath()+'/OrderOperAction.do?operType=delBtn',
									type: 'post',cache:false,
									data: 'gd_id='+gd_id,
									success: function (data) {
										var obj = eval("("+data+")");
										showGdList('','',$('#getGdfenjieriqi').val(),true,winLayer.open({type: 3}));
				  						layer.msg(obj.body);
									}
								});
							}else{
				  				layer.msg("此数据正在处理，不允许删除！");
							}
						}
						return null;
					});
					//上调序
					$("#dd_upBtn").click(function(){
						var cheBoolean = $("input[name='trGdRadio']").is(':checked');
						if (cheBoolean) {
							var DdType = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(2).html();
							if(DdType=="初始化"){
								var gd_xuhao = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(1).text();
								var dd_table = $('#dd_table tbody tr');//dd_table
								if(dd_table.length==1){
									layer.msg("当前只有一行工单，无需调整！");
								}else if(dd_table.length > 1){
									var row = dd_table.eq(0).find('td').eq(1).text();
									if(gd_xuhao==row){
										layer.msg("此工单已经是第一行，无需调整！");
										return null;
									}else{
										var rowId = $("input[name='trGdRadio']:checked").parent().parent().attr("id").split("row")[1];
										if($("#gd_row"+(rowId-1)).find('td').eq(2).text()=="已分解"||
											$("#gd_row"+(rowId-1)).find('td').eq(2).text()=="正在处理"){
											layer.msg("第"+$("#gd_row"+(rowId-1)).find('td').eq(1).text()+"行工单已分解，无法调整！");
											return null;
										}
										var gd_id = $("input[name='trGdRadio']:checked").val();
										var gd_xuhao = $("#gd_row"+rowId).find('td').eq(1).text();
										var up_gd_id = $("#gd_row"+(rowId-1)).find('td').eq(0).find("input:radio").val();
										var up_gd_xuhao = $("#gd_row"+(rowId-1)).find('td').eq(1).text();
										$.ajax({
											url: getRootPath()+'/OrderOperAction.do?operType=upGdBtn',
											type: 'post',cache:false,
											data: 'gd_id='+gd_id+'&up_gd_id='+up_gd_id+'&gd_xuhao='+gd_xuhao+'&up_gd_xuhao='+up_gd_xuhao,
											success: function (data) {
												var obj = eval("("+data+")");
												showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}));
						  						layer.msg(obj.body);
											}
										});
									}
								}
							}else{
				  				layer.msg("分解后的工单，不允许调序！");
							}
						}
						return null;
					});
					//工单下调
					$("#dd_bomBtn").click(function(){
						var cheBoolean = $("input[name='trGdRadio']").is(':checked');
						if (cheBoolean) {
							var DdType = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(2).html();
							if(DdType=="初始化"){
								var gd_xuhao = $("input[name='trGdRadio']:checked").parent().parent().children("td").eq(1).text();
								var dd_table = $('#dd_table tbody tr');//dd_table
								if(dd_table.length==1){
									layer.msg("当前只有一行工单，无需调整！");
								}else if(dd_table.length > 1){
									var row = dd_table.eq(dd_table.length-1).find('td').eq(1).text();
									if(gd_xuhao==row){
										layer.msg("此工单已经是最后一行，无需调整！");
										return null;
									}else{
										var rowId = $("input[name='trGdRadio']:checked").parent().parent().attr("id").split("row")[1];
										if($("#gd_row"+rowId).find('td').eq(2).text()=="已分解"||
											$("#gd_row"+rowId).find('td').eq(2).text()=="正在处理"){
											layer.msg("第"+gd_xuhao+"行工单已分解，无法调整！");
											return null;
										}
										var gd_id = $("input[name='trGdRadio']:checked").val();
										var gd_xuhao = $("#gd_row"+rowId).find('td').eq(1).text();
										var up_gd_id = $("#gd_row"+(Number(rowId)+1)).find('td').eq(0).find("input:radio").val();
										var up_gd_xuhao = $("#gd_row"+(Number(rowId)+1)).find('td').eq(1).text();
										$.ajax({
											url: getRootPath()+'/OrderOperAction.do?operType=bomGdBtn',
											type: 'post',cache:false,
											data: 'gd_id='+gd_id+'&up_gd_id='+up_gd_id+'&gd_xuhao='+gd_xuhao+'&up_gd_xuhao='+up_gd_xuhao,
											success: function (data) {
												var obj = eval("("+data+")");
												showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}));
						  						layer.msg(obj.body);
											}
										});
									}
								}
							}else{
				  				layer.msg("分解后的工单，不允许调序！");
								return null;
							}
						}
						return null;
					});
					if(showGdList('','',$('#getGdfenjieriqi').val(),false,winLayer.open({type: 3}))&&dsState.state){
						(function(){
							//定时刷新
							readyShow.deleteSetInterval = setInterval(function(){
								showGdList('','','',false);
							},dsState.tim);
							dlInterval = true;
						})();
					};
					//工单显示
					return null;
				}
			}
			af.load(function(){
				return null;
			},{state:false,tim:5000});//渲染主页面,function(){}--第一个返回参数,{ds:true--是否为定时刷新、tim:刷新时间毫秒为单位};
			return null;
		}catch (e) {
			return e;
		}
		return null;
	}
};