var readyShow = {
	deleteSetInterval:null,
	load:function(hdFun){
		try{
			var af = {
				load:function(dsState){
					var win = layer.open({type: 3});
					var winHeight = document.body.clientHeight;
					if(winHeight == window.screen.height){
						winHeight = document.body.clientHeight - 50;
					}
					$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
					$('.table-body').css('height', document.body.clientHeight / 3.5);
					this.readyEvent();
					if(af.hwLoad()&&af.table.load()&&dsState.state){//是否启动定时
						readyShow.deleteSetInterval = setInterval(function(){
							if(af.table.load()&&af.hwLoad());
						},dsState.tim),dlInterval=true;
					}
					var ly=layer.close(win),winHeight=null,win=null,ly=null;
					return null;
				},
				/**
				 * 渲染所有事件
				 */
				readyEvent:function(){
					try{
						var map = {
							//开启&关闭 文本	
							startEnd:function(i,e,readOnly,display,fun){
								while(i<e.length){
									if(e[i]=='fangxiang'){
										$("#"+e[i]).css("display",display);
									}else{
										$("#"+e[i]).attr("readOnly",readOnly);
									}
									if(readOnly){
										$("#"+e[i]).val("");
									}
									i++;
								}
								return fun();
							},
							//货位点击事件
							clickHCK:function(id,hwId,e,fun){
								/***************上货****************/
								if(id=="shanghuo"){
									//去往货位-----只允许选择空货位
									if($(e).attr("class")=="kf_lan yes"){
										return (function(){
											layer.tips('此货位有托盘！', '#'+hwId);
											hwId=null,id = null;
											return null;
										})();
									}else{
										$("#go_huowei").val(hwId);
									}
								/*************下货到输送线*************/
								}else if(id=="xiahuodaoshusongxian"){
									//从货位-------只允许选择有托盘的货位
									if($("#cong_xhdssx_huowei").val()==""){
										if($(e).attr("class")=="kf_hui no"){
											return (function(){
												layer.tips('此货位没有托盘！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}else{
											$("#cong_xhdssx_huowei").val(hwId);
										}
									//到货位-------只能选择输送线货位
									}else{
										if(Number(hwId)>614 || Number(hwId)<501){
											return (function(){
												layer.tips('只能选择输送线货位！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}else if($(e).attr("class")=="kf_lan yes"){
											return (function(){
												layer.tips('此货位有托盘！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}else{
											$("#dao_xhdssx_huowei").val(hwId);
										}
									}
								/*****************从输送线回货架***************/
								}else if(id=="congshusongxianhuihuojia"){
									//从工位
									if($("#cong_ssxhhj_gongwei").val()==""){
										//从工位只能选择“输送线的”
										if(Number(hwId)>614 || Number(hwId)<501){
											return (function(){
												layer.tips('请选择输送线偶数货位！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										//偶数货位
										}else if(Number(hwId)%2==0){
											if($(e).attr("class")=="kf_hui no"){
												return (function(){
													layer.tips('此货位没有托盘！', '#'+hwId);
													hwId=null,id = null;
													return null;
												})();
											}else{
												$("#cong_ssxhhj_gongwei").val(hwId)
											}
										}else{
											return (function(){
												layer.tips('请选择输送线偶数货位！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}
									//到货位
									}else{
										if($(e).attr("class")=="kf_lan yes"){
											return (function(){
												layer.tips('此货位有托盘！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}else{
											$("#dao_ssxhhj_huowei").val(hwId)
										}
									}
								/*************从输送线回大库**************/
								}else if(id=="congshusongxianhuidaku"){
									//从工位只能选择“输送线的”
									if(Number(hwId)>614 || Number(hwId)<501){
										return (function(){
											layer.tips('请选择输送线偶数货位！', '#'+hwId);
											hwId=null,id = null;
											return null;
										})();
									}else{
										//偶数货位
										if(Number(hwId)%2==0){
											if($(e).attr("class")=="kf_hui no"){
												return (function(){
													layer.tips('此货位没有托盘！', '#'+hwId);
													hwId=null,id = null;
													return null;
												})();
											}else{
												$("#cong_ssxhdk_gongwei").val(hwId);
											}
										}else{
											return (function(){
												layer.tips('请选择输送线偶数货位！', '#'+hwId);
												hwId=null,id = null;
												return null;
											})();
										}
									}
								/**************从货架回大库**************/
								}else{
									//从货位-只允许选择1-28货位
									if(Number(hwId) > 28){
										return (function(){
											layer.tips('请选择1-28货位！', '#'+hwId);
											hwId=null,id = null;
											return null;
										})();
									}else if($(e).attr("class")=="kf_hui no"){
										return (function(){
											layer.tips('此货位没有托盘！', '#'+hwId);
											hwId=null,id = null;
											return null;
										})();
									}else{
										$("#cong_hjhdk_huowei").val(hwId);
									}
								}
								return fun();
							},
							//显示物料位置记录仪
							showWlIp:{
								ip:null,
								color:null
							},
							load:function(fun){
								//物料显示位置点击事件
								$('#show_wuliao_name').click(function () {
							        var a = $('#show_wuliao_name ul').toggle();
							        return (function(){
							        	return a = null;
							        })();
							    });
							    $('#show_wuliao_name ul').click(function (event) {
							        var t2 = $(event.target).text();
							        var a = $('#show_wuliao').val(t2);
							        var hwId = $("#"+t2).val();
							        if(map.showWlIp.ip&&map.showWlIp.color){
							        	$("#"+map.showWlIp.ip).attr("class",map.showWlIp.color);
							        	map.showWlIp.ip=null;
							        	map.showWlIp.color=null;
							        }
							        if(hwId){
							        	map.showWlIp.ip = hwId;
							        	map.showWlIp.color = $("#"+hwId).attr("class");
							        	$("#"+hwId).attr("class","kf_lv show");
							        }
							        return (function(){
							        	a = null,t2 = null,hwId=null;
							        	return null;
							        })();
							    });
								//方向点击事件
								$('#fangxiang_name').click(function () {
							        var a = $('#fangxiang_name ul').toggle();
							        return (function(){
							        	return a = null;
							        })();
							    });
							    $('#fangxiang_name ul').click(function (event) {
							        var t2 = $(event.target).text();
							        var a = $('#fangxiang').val(t2);
							        return (function(){
							        	a = null,t2 = null;
							        	return null;
							        })();
							    });
								//上货事件
								$("#shanghuo").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart = ['tp_code','wl_code',
									                 'up_number','go_huowei','fangxiang'];
									map.startEnd(0,arryStart,false,'inline',function(){
										return arryStart = null;
									});
									//关闭文本
									var arryEnd = ['cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd = null;
									});
									return null;
								});
								//下货到输送线事件
								$("#xiahuodaoshusongxian").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart = ['cong_xhdssx_huowei','dao_xhdssx_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart = null;
									});
									//关闭文本
									var arryEnd = ['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd = null;
									});
									return null;
								});
								//从输送线回货架
								$("#congshusongxianhuihuojia").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart = ['cong_ssxhhj_gongwei','dao_ssxhhj_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart = null;
									});
									//关闭文本
									var arryEnd = ['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd = null;
									});
									return null;
								});
								//从输送线回大库
								$("#congshusongxianhuidaku").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart = ['cong_ssxhdk_gongwei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart = null;
									});
									//关闭文本
									var arryEnd = ['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd = null;
									});
									return null;
								});
								//从货架回大库
								$("#conghuojiahuidaku").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart = ['cong_hjhdk_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart = null;
									});
									//关闭文本
									var arryEnd = ['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd = null;
									});
									return null;
								});
								//所有单选按钮事件
								$("input[name='radioName']").click(function(){
									var clk = $(this).parent().click();
									return (function(){
										return clk=null;
									})();
								});
								//货位点击事件
								$("div[name='HCK-NAME']").click(function(){
									var hwId = $(this).html();
									var id = $("input[name='radioName']:checked").parent().attr("id");
									return map.clickHCK(id,hwId,this,function(){
										hwId=null,id=null;
										return null;
									});
								});
								return (function(){
									//默认选择上货
									var a = $("#shanghuo").click();
									return a = null;
								})();
							}
						};
						map.load(function(){
							return null;
						});
					}catch (e) {
						return e;
					}
					return null;
				},
				/**
				 * 渲染表格
				 */
				table:{
					load:function(fun){
						var a = this.getTableValue(this.hckzdd,'getHckzddl');
						var b = this.getTableValue(this.kuCun,'getKuCun');
						return (function(){
							a=null,b=null;
							return true;
						})();
					},
					getTableValue:function(fun,operType){
						//获取输送线 库房状态
						var a = $.ajax({
							url: getRootPath()+'/KuFangAction.do?operType='+operType,
							type:'get',
							cache:false,
							success: function (data) {
								var obj = eval("("+data+")");
								return fun(obj.data);
							}
						});
						return (function(){
							return a = null;
						})();
					},
					/**
					 * 缓存库指定队列
					 */
					hckzdd:function(e){
						$("#hc_table tbody tr").remove();
						var i = 0;
						while(i < e.length){
							$('#hc_table tbody').append('<tr bgcolor="#ffffff" style="height:22px;">' +
								//事件ID
								'<td title="'+e[i].idEvent +'" style="width:35px;padding:0px;font-size:10px;">'+e[i].idEvent +'</td>' +
								//动作
								'<td title="'+e[i].dongzuo +'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].dongzuo.length>4?e[i].dongzuo.substring(0,4)+"...":e[i].dongzuo) +'</td>' +
								//托盘
								'<td title="'+e[i].tp_code +'" style="width:40px;padding:0px;font-size:10px;">'+e[i].tp_code+'</td>' +
								//来源货位
								'<td title="'+e[i].laiyuanhuoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].laiyuanhuoweihao+'</td>' +
								//去往货位
								'<td title="'+e[i].fanghuihuoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].fanghuihuoweihao+'</td>' +
								//状态
								'<td title="'+e[i].zhuangtai +'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].zhuangtai.length>3?e[i].zhuangtai.substring(0,3)+"...":e[i].zhuangtai)+'</td>' +
								//回大库
								'<td title="'+e[i].shifouhuidaku+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].shifouhuidaku+'</td>' +
								//开始时间
								'<td title="'+e[i].fasongshijian +'" style="width:55px;padding:0px;font-size:10px;">'+(e[i].fasongshijian!=""?e[i].fasongshijian.substring(0,10)+"...":"")+'</td>' +
								//完成时间
								'<td title="'+e[i].wanchengshijian +'" style="width:55px;padding:0px;font-size:10px;">'+(e[i].wanchengshijian!=""?e[i].wanchengshijian.substring(0,10)+"...":"")+'</td>' +
							'</tr>');
							i++;
						}
						return (function(){
							return i = null;
						})();
					},
					/**
					 * 库存
					 */
					kuCun:function(e){
						$("#kc_table tbody tr").remove();
						var i = 0;
						//物料显示位置
						var li = "<li></li>";
						while(i < e.length){
							$('#kc_table tbody').append('<tr bgcolor="#ffffff" style="height:22px;">' +
								//托盘
								'<td title="'+e[i].tp_code +'" style="width:40px;padding:0px;font-size:10px;">'+e[i].tp_code +'</td>' +
								//物料
								'<td title="'+e[i].wl_code +'" style="width:50px;padding:0px;font-size:10px;">'+(e[i].wl_code.length>10?e[i].wl_code.substring(0,10)+"...":e[i].wl_code) +'</td>' +
								//数量
								'<td title="'+e[i].number +'" style="width:40px;padding:0px;font-size:10px;">'+e[i].number+'</td>' +
								//货位号
								'<td title="'+e[i].huoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].huoweihao+'</td>' +
								//方向
								'<td title="'+e[i].fangxiang+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].fangxiang+'</td>' +
							'</tr>');
							li+="<li>"+e[i].wl_code+"<input type='hidden' " +
									"id='"+e[i].wl_code+"' " +
									"value='"+e[i].huoweihao+"'></li>";
							i++;
						}
						$('#show_wuliao_name ul').html(li);
						return (function(){
							i = null,li = null;
							return null;
						})();
					}
				},
				/**
				 * 渲染货位
				 */
                arrayHome:['‘501’','‘502’','‘503’','‘504’','‘505’','‘506’','‘507’','‘508’','‘509’','‘510’','‘511’','‘512’','‘513’','‘514’',
                           '‘1’','‘2’','‘3’','‘4’','‘5’','‘6’','‘7’','‘8’','‘9’','‘10’','‘11’','‘12’','‘13’','‘14’','‘15’',
                           '‘16’','‘17’','‘18’','‘19’','‘20’','‘21’','‘22’','‘23’','‘24’','‘25’','‘26’','‘27’','‘28’',
                           '‘601’','‘602’','‘603’','‘604’','‘605’','‘606’','‘607’','‘608’','‘609’','‘610’','‘611’','‘612’','‘613’','‘614’'],
	            removeArry:[],
				hwLoad:function(){
					//获取输送线 库房状态
					var a = $.ajax({
						url: getRootPath()+'/KuFangAction.do?operType=getHw',
						type:'get',
						cache:false,
						success: function (data) {
							var obj = eval("("+data+")");
							if(obj.hckTb.length > 0){
								for(var i=0,j=0,k=af.arrayHome.length;i<k;i++){
									try{
										if(af.arrayHome.toString().indexOf('‘'+obj.hckTb[i][i]+'’') > -1){
											$("#"+obj.hckTb[i][i]).attr("class","kf_lan yes");
											af.removeArry[i] = '‘'+obj.hckTb[i][i]+'’';
											k++;
										}
									}catch (e) {
										if(af.removeArry.toString().indexOf(af.arrayHome[j]) == -1){
											var upId = af.arrayHome[j].split("’")[0].split('‘')[1];
											$("#"+upId).attr("class","kf_hui no");
										}
										j++;
									}
								}
							}else{
								$("div[name='HCK-NAME']").attr("class","kf_hui no");
							}
							return (function(){
								return obj = null;
							})();
						}
					});
					return (function(){
						return a = true;
					})();
				}
			};
			return af.load({state:false,tim:2000});
		}catch (e) {
			return e;
		}
		return hdFun();
	}
};