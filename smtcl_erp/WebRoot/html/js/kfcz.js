var readyShow={
	load:function(){
		try{
			var af={
				load:function(dsState,fun){
					var win=layer.open({type:3});
					var winHeight=document.body.clientHeight;
					if(winHeight == window.screen.height){
						winHeight=document.body.clientHeight-50;
					};
					$('#xy').css('height',(winHeight -(window.screen.height-winHeight))/0.98);
					$('.table-body').css('height',document.body.clientHeight / 3.5);
					//显示GDFrame
					$("#showGDFrame").click(function(){
						var a=af.getGDFrame();
						a=null;return null;
					});
					var a=this.readyEvent();a=null;
					var nude=af_Home.getFx(function(fx){
						if(fx=='1,2'){fx=1;};
						var a=$("#fangxiang").val(fx);a=null;
						var b=$("#fangxiang").attr("oval",fx);b=null;fx=null;
						if(af.table.load()&&dsState.state){//是否启动定时
							af_Home.dlInterval=setInterval(function(){
								if(af.table.load());
							},dsState.tim);
						};
						return null;
					});nude=null;
					var ly=layer.close(win),winHeight=null,win=null,ly=null;
					return fun();
				},
				/**
				 * 显示GDFrame
				 */
				getGDFrame:function(){
					$.ajax({
						url:getRootPath()+'/HomeAction.do?operType=getGDFrame',
						type:'get',cache:false,
						success:function(data){}
					});
					return null;
				},
				/**
				 * 显示物料位置记录仪
				 */
				showWlIp:{ip:[],oip:[],show:function(){
					$.each(af.showWlIp.ip,function(i,val){
			        	if(val){
				        	var wl=val.split("&");
				        	if($('#show_wuliao').val()==wl[1]&&wl[0]!='60001'&&wl[0]!='60002'){
				        		$("#"+wl[0]).attr("class","kf_red yes");
				        	}else{
				        		if(wl[2]==1){
				        			$("#"+wl[0]).attr("class","kf_lan yes");
				        		}else{
					        		$("#"+wl[0]).attr("class","kf_lv yes");
				        		};
				        	};
				        	wl=null;
			        	};val=null;
			        });
			        return null;
				}},
				/**
				 * 物料查询窗体
				 */
				getWuliao:function(fun){
					try{
						var openWindow='<div style="width:100%;margin-right:0;">'+
							'<div class="margin-top-10">'+
								'<div class="col-md-11" style="margin-left:20px;">'+
								'<!-- 标题 -->'+
								'<div style="padding-right:17px;">'+
								'<table class="table table-bordered text-center" id="commonSearchTableHead">'+
								'<thead>'+
								'<tr>'+
								'<td style="width:50%;">编号</td>'+
								'<td style="width:50%;">描述</td>'+
								'</tr>'+
								'</thead>'+
								'</table>'+
								'</div>'+
								'<!-- 标题 end-->'+
								'<!-- 内容 -->'+
								'<div class="table-body" id="commonSearchTableBody">'+
								'<table class="table table-bordered text-center table-hover" id="searchTable">'+
								'</table>'+
								'</div>'+
								'<!-- 内容 end-->'+
								'</div>'+
							'</div>'+
						'</div>';
						var win=layer.open({
						    type:1,
						    title:'物料查询',
						    shadeClose:false,
						    scrollbar:false,
						    anim:5,
						    move:false,
						    shade:[0.5,'#393D49'],
						    area:['45%','50%'],
						    content:openWindow
						});
						/*设置table高度*/
						$('#commonSearchTableBody').css('height',document.body.clientHeight / 3);
						var a=$.ajax({
							url:getRootPath()+'/BaseDataAction.do?operType=selectWlList',
							type:'get',
							data:'leibie=配方',
							cache:false,
							success:function(data){
						    	var obj=eval("("+data+")");
								for(var i=0;i<obj.length;i++){
					                $('#searchTable').append('<tr class="commonTr" style="cursor:pointer;"></tr>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_code" style="width:50%;">'+ obj[i].wuliao_code+ '</td>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_miaoshu" style="width:50%;">'+ obj[i].wuliao_miaoshu+ '</td>');
								}
								/*绑定双击事件*/
				            	$('.commonTr').bind('dblclick',function(e){
									var tr=$(e.target).parent();
									fun(tr.find('.wuliao_code').text());
				            		layer.close(win);
				            		return openWindow=null,win=null,obj=null,tr=null,a=null;
				            	});
							}
						});
						return null;
					}catch(e){
						return e;
					}
					return null;
				},
				/**
				 * 读取RFID
				 */
				getTpCode:function(fun_){
		    		var a=$.ajax({
						url:getRootPath()+'/KuFangAction.do?operType=getTp',
						type:'get',cache:false,
						data:'fx='+$("#fangxiang").val(),
						success:function(data){
							var obj=eval("("+data+")");data=null;
							return fun_(obj.tp_code,obj.wl_code);obj=null;
						}
					});a=null;
					return null;
				},
				/**
				 * 渲染库房操作界面事件
				 */
				readyEvent:function(){
					try{
						var map={
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
									if($(e).attr("class")=="kf_lan yes"||
										$(e).attr("class")=="kf_lv yes"||
										$(e).attr("class")=="kf_red yes"){
										return(function(){
											layer.tips('此货位有托盘！','#'+hwId);
											hwId=null,id=null;
											return null;
										})();
									}else{
										$("#go_huowei").val(hwId);
									}
								/*************下货到输送线*************/
								}else if(id=="xiahuodaoshusongxian"){
									//从货位-------只允许选择有托盘的货位
									if($("#cong_xhdssx_huowei").val()==""){
										if(Number(hwId)>28){
											return(function(){
												layer.tips('请选择1-28货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}else if($(e).attr("class")=="kf_hui no"){
											return(function(){
												layer.tips('此货位没有托盘！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}else{
											$("#cong_xhdssx_huowei").val(hwId);
										}
									//到货位-------只能选择输送线货位
									}else{
										if(Number(hwId)>614 || Number(hwId)<501){
											return(function(){
												layer.tips('只能选择输送线货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}else if($(e).attr("class")=="kf_lan yes"||
												$(e).attr("class")=="kf_lv yes"||
												$(e).attr("class")=="kf_red yes"){
											return(function(){
												layer.tips('此货位有托盘！','#'+hwId);
												hwId=null,id=null;
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
											return(function(){
												layer.tips('请选择输送线偶数货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										//偶数货位
										}else if(Number(hwId)%2==0){
											if($(e).attr("class")=="kf_hui no"){
												return(function(){
													layer.tips('此货位没有托盘！','#'+hwId);
													hwId=null,id=null;
													return null;
												})();
											}else{
												$("#cong_ssxhhj_gongwei").val(hwId)
											}
										}else{
											return(function(){
												layer.tips('请选择输送线偶数货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}
									//到货位
									}else{
										if(Number(hwId)>28){
											return(function(){
												layer.tips('请选择1-28货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}else if($(e).attr("class")=="kf_lan yes"||
												$(e).attr("class")=="kf_lv yes"||
												$(e).attr("class")=="kf_red yes"){
											return(function(){
												layer.tips('此货位有托盘！','#'+hwId);
												hwId=null,id=null;
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
										return(function(){
											layer.tips('请选择输送线偶数货位！','#'+hwId);
											hwId=null,id=null;
											return null;
										})();
									}else{
										//偶数货位
										if(Number(hwId)%2==0){
											if($(e).attr("class")=="kf_hui no"){
												return(function(){
													layer.tips('此货位没有托盘！','#'+hwId);
													hwId=null,id=null;
													return null;
												})();
											}else{
												$("#cong_ssxhdk_gongwei").val(hwId);
											}
										}else{
											return(function(){
												layer.tips('请选择输送线偶数货位！','#'+hwId);
												hwId=null,id=null;
												return null;
											})();
										}
									}
								/**************从货架回大库**************/
								}else{
									//从货位-只允许选择1-28货位
									if(Number(hwId)>28){
										return(function(){
											layer.tips('请选择1-28货位！','#'+hwId);
											hwId=null,id=null;
											return null;
										})();
									}else if($(e).attr("class")=="kf_hui no"){
										return(function(){
											layer.tips('此货位没有托盘！','#'+hwId);
											hwId=null,id=null;
											return null;
										})();
									}else{
										$("#cong_hjhdk_huowei").val(hwId);
									}
								}
								return fun();
							},
							load:function(fun){
								//物料显示位置点击事件
								$('#show_wuliao_name').click(function(){
							        var a=$('#show_wuliao_name ul').toggle();
							        return a=null;
							    });
							    $('#show_wuliao_name ul').click(function(event){
							        var t2=$(event.target).text();
							        var a=$('#show_wuliao').val(t2);
							        if(t2!=""){
							        	var b=af.showWlIp.show();b=null;
							        };
							        return a=null,t2=null;
							    });
							    //托盘选择事件
							    $("#tp_code_click").click(function(e){
							    	var a=af.getTpCode(function(tpCode,wlCode){
										var b=$("#tp_code").val(tpCode);b=null;
										var c=$("#wl_code").val(wlCode);c=null;
							    	});a=null;return null;
							    });
							    //物料选择事件
							    $("#wl_code_click").click(function(e){
						    		var show=af.getWuliao(function(data){
						    			var wlShow=$("#wl_code").val(data);
						    			return wlShow=null;
						    		});
						    		return show=null;
							    });
								//方向点击事件
								$('#fangxiang_name').click(function(){
							        var a=$('#fangxiang_name ul').toggle();
							        return a=null;
							    });
							    $('#fangxiang_name ul').click(function(event){
							        var t2=$(event.target).text();
							        var a=$('#fangxiang').val(t2);
							        return a=null,t2=null;
							    });
								//上货事件
								$("#shanghuo").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart=['tp_code','up_number','go_huowei','fangxiang'];
									map.startEnd(0,arryStart,false,'inline',function(){
										return arryStart=null;
									});
									//关闭文本
									var arryEnd=['cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd=null;
									});
									var a=$("#fangxiang").val($("#fangxiang").attr("oval"));a=null;a=$("#up_number").val(30);a=null;
									return null;
								});
								//下货到输送线事件
								$("#xiahuodaoshusongxian").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart=['cong_xhdssx_huowei','dao_xhdssx_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart=null;
									});
									//关闭文本
									var arryEnd=['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd=null;
									});
									return null;
								});
								//从输送线回货架
								$("#congshusongxianhuihuojia").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart=['cong_ssxhhj_gongwei','dao_ssxhhj_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart=null;
									});
									//关闭文本
									var arryEnd=['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhdk_gongwei','cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd=null;
									});
									return null;
								});
								//从输送线回大库
								$("#congshusongxianhuidaku").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart=['cong_ssxhdk_gongwei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart=null;
									});
									//关闭文本
									var arryEnd=['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_hjhdk_huowei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd=null;
									});
									return null;
								});
								//从货架回大库
								/*$("#conghuojiahuidaku").click(function(){
									$(this).find("input:radio").prop("checked",true);
									//开启文本
									var arryStart=['cong_hjhdk_huowei'];
									map.startEnd(0,arryStart,false,'none',function(){
										return arryStart=null;
									});
									//关闭文本
									var arryEnd=['tp_code','wl_code',
									               'up_number','go_huowei','fangxiang',
									               'cong_xhdssx_huowei','dao_xhdssx_huowei',
									               'cong_ssxhhj_gongwei','dao_ssxhhj_huowei',
									               'cong_ssxhdk_gongwei'];
									map.startEnd(0,arryEnd,true,'none',function(){
										return arryEnd=null;
									});
									return null;
								});*/
								//所有单选按钮事件
								$("input[name='radioName']").click(function(){
									var clk=$(this).parent().click();
									return clk=null;
								});
								//货位点击事件
								$("div[name='HCK-NAME']").click(function(){
									var hwId=$(this).html();
									var id=$("input[name='radioName']:checked").parent().attr("id");
									return map.clickHCK(id,hwId,this,function(){
										hwId=null,id=null;
										return null;
									});
								});
								//发送按钮事件办绑定
								$("#kcfsBtn").click(function(){
									try{
										var type=$("input[name='radioName']:checked").parent().attr("id");
										var map;
										if(type=="shanghuo"){
											if($("#tp_code").val()==""){
									    	 	$("#tp_code").focus();
									    		layer.tips('请读取托盘编号！','#tp_code_click');
									    		return null;
											}
											if($("#wl_code").val()==""){
									    	 	$("#wl_code").focus();
									    		layer.tips('请选择物料！','#wl_code_click');
									    		return null;
											}
											if($("#up_number").val()==""){
									    	 	$("#up_number").focus();
									    		layer.tips('请输入数量！','#up_number');
									    		return null;
											}
											/*if($("#go_huowei").val()==""){
									    	 	$("#go_huowei").focus();
									    		layer.tips('请选择去往货位！','.huowei_id');
									    		return null;
											}*/
											if($("#fangxiang").val()==""){
									    	 	$("#fangxiang").focus();
									    		layer.tips('请选择方向！','#fangxiang');
									    		return null;
											}
											map={
												title:'上货',	
												tp:$("#tp_code").val(),
												wuliao:$("#wl_code").val(),
												num:$("#up_number").val(),
												toID:$("#go_huowei").val(),
												machineID:$("#fangxiang").val()
											};
										}else if(type=="xiahuodaoshusongxian"){
											if($("#cong_xhdssx_huowei").val()==""){
									    	 	$("#cong_xhdssx_huowei").focus();
									    		layer.tips('请选择货位！','.huowei_id');
									    		return null;
											}
											if($("#dao_xhdssx_huowei").val()==""){
									    	 	$("#dao_xhdssx_huowei").focus();
									    		layer.tips('请选择货位！','.huowei_id');
									    		return null;
											}
											map={
												title:'下货到输送线',
												tp:'',
												machineID:'',
												fromID:$("#cong_xhdssx_huowei").val(),
												toID:$("#dao_xhdssx_huowei").val(),
												type:'下货',
												todaku:0
											}
										}else if(type=="congshusongxianhuihuojia"){
											if($("#cong_ssxhhj_gongwei").val()==""){
									    	 	$("#cong_ssxhhj_gongwei").focus();
									    		layer.tips('请选择工位！','.huowei_id');
									    		return null;
											}
											if($("#dao_ssxhhj_huowei").val()==""){
									    	 	$("#dao_ssxhhj_huowei").focus();
									    		layer.tips('请选择货位！','.huowei_id');
									    		return null;
											}
											map={
												title:'从输送线回货架',
												tp:'',
												machineID:'',
												fromID:$("#cong_ssxhhj_gongwei").val(),
												toID:$("#dao_ssxhhj_huowei").val(),
												type:'输送线回流',
												todaku:0
											}
										}else if(type=="congshusongxianhuidaku"){
											if($("#cong_ssxhdk_gongwei").val()==""){
									    	 	$("#cong_ssxhdk_gongwei").focus();
									    		layer.tips('请选择工位！','.huowei_id');
									    		return null;
											}
											map={
												title:'从输送线回大库',
												tp:'',
												machineID:'',
												fromID:$("#cong_ssxhdk_gongwei").val(),
												toID:"60002",
												type:'输送线回流',
												todaku:1
											}
										}else if(type=="conghuojiahuidaku"){
											if($("#cong_hjhdk_huowei").val()==""){
									    	 	$("#cong_hjhdk_huowei").focus();
									    		layer.tips('请选择货位！','.huowei_id');
									    		return null;
											}
											map={
												title:'从货架回大库',
												tp:'',
												machineID:'',
												fromID:$("#cong_hjhdk_huowei").val(),
												toID:"60002",
												type:'下货',
												todaku:1
											}
										};
										var a=$.ajax({
											url:getRootPath()+'/KuFangAction.do?operType=fsMingLing',
											type:'get',
											data:map,
											cache:false,
											success:function(data){
												if(data.indexOf("成功")==-1){
													layer.msg(data);
												};
												var a=$("input[name='radioName']:checked").parent();
										    	var b=$('#kfcz_id')[0].reset();
										    	af.showWlIp.ip=[];
												var c=a.click();
												return a=null,b=null,c=null,data=null;
											}
										});
										return type=null,map=null,a=null;
									}catch(e){
										return e;
									}
									return null;
								});
								return(function(){
									//默认选择上货
									var a=$("#shanghuo").click();a=null
									return null;
								})();
							}
						};
						map.load(function(){return null;});
					}catch(e){return e;}
					return null;
				},
				/**
				 * 渲染表格
				 */
				table:{
					load:function(fun){
						var a=this.getTableValue(this.hckzdd,'getHckzddl');a=null;
						var b=this.getTableValue(this.kuCun,'getKuCun');b=null;
						return true;
					},
					getTableValue:function(fun,operType){
						//获取输送线 库房状态
						var a=$.ajax({
							url:getRootPath()+'/KuFangAction.do?operType='+operType,
							type:'get',
							cache:false,
							success:function(data){
								var obj=eval("("+data+")");data=null;
								return fun(obj.data);obj=null;
							}
						});a=null
						return null;
					},
					/**
					 * 缓存库指定队列
					 */
					delEvent:function(id){
						layer.confirm('是否删除此事件？', {
							btn: ['yes', 'no']
						},function(index, layero){
							var a=$.ajax({
								url:getRootPath()+'/KuFangAction.do?operType=delEvent',
								type:'get',data:'id='+id,cache:false,
								success:function(data){var a=layer.msg(data);data=null;a=null;return null;}
							});a=null;
						});
						return null;
					},
					hcTableLoad:function(e,tpCode,fun_){
						var zdDQ=false,i=0;$("#hc_table tbody tr").remove();
						while(i<e.length){
							$('#hc_table tbody').append('<tr bgcolor="#ffffff" style="height:22px;">'+
								//事件ID
								'<td title="'+e[i].idEvent+'" style="width:35px;padding:0px;font-size:10px;">'+e[i].idEvent+'</td>'+
								//动作
								'<td title="'+e[i].dongzuo+'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].dongzuo.length>4?e[i].dongzuo.substring(0,4)+"...":e[i].dongzuo)+'</td>'+
								//托盘
								'<td title="'+e[i].tp_code+'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].tp_code.length>7?e[i].tp_code.substring(0,7)+"...":e[i].tp_code)+'</td>'+
								//来源货位
								'<td title="'+e[i].laiyuanhuoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].laiyuanhuoweihao+'</td>'+
								//去往货位
								'<td title="'+e[i].fanghuihuoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].fanghuihuoweihao+'</td>'+
								//状态
								'<td title="'+e[i].zhuangtai+'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].zhuangtai.length>3?e[i].zhuangtai.substring(0,3)+"...":e[i].zhuangtai)+'</td>'+
								//回大库
								'<td title="'+e[i].shifouhuidaku+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].shifouhuidaku+'</td>'+
								//开始时间
								'<td title="'+e[i].fasongshijian+'" style="width:55px;padding:0px;font-size:10px;">'+(e[i].fasongshijian!=""?e[i].fasongshijian.substring(0,10)+"...":"")+'</td>'+
								//完成时间
								'<td title="'+e[i].wanchengshijian+'" style="width:55px;padding:0px;font-size:10px;">'+(e[i].wanchengshijian!=""?e[i].wanchengshijian.substring(0,10)+"...":"")+'</td>'+
								//删除
								'<td style="width:20px;padding:0px;">'+(e[i].zhuangtai=="排队"&&af_Home.administrator.发送命令?"<span class='del' title="+e[i].idEvent+" style='cursor:pointer;' aria-hidden='true'>&times;</span>":"")+'</td>'+
							'</tr>');if(tpCode==e[i].tp_code){zdDQ=true;};i++;
						};i=null;e=null;
						if(af_Home.administrator.发送命令){
							var a=$('.del').unbind("click");a=null;
							$('.del').click(function(){
								var id=$(this).attr("title");
								var a=af.table.delEvent(id);id=null;a=null;
								return null;
							});
						};
						return fun_(zdDQ);zdDQ=null;
					},
					hckzdd:function(e){
						/*var type=$("input[name='radioName']:checked").parent().attr("id");
						if(type=="shanghuo"){
							var a=af.getTpCode(function(tpCode,wlCode){//自动读取RFID
								var a=af.table.hcTableLoad(e,tpCode,function(type){
									if(type==false){
										var b=$("#tp_code").val(tpCode);b=null;
										var c=$("#wl_code").val(wlCode);c=null;
										//var a=$("#kcfsBtn").click();a=null;//自动发送
									}else{
										var b=$("#tp_code").val('');b=null;
										var c=$("#wl_code").val('');c=null;
									};zdDQ=null;e=null;
								});a=null;
							});a=null;
						}else{*/var a=af.table.hcTableLoad(e,null,function(b){e=null;b=null;});a=null;//};
						/*type=null;*/return null;
					},
					/**
					 * 库存
					 */
					kuCun:function(e){
						var i=0;
                    	var li="<li></li>";
                    	af.showWlIp.ip=[];//清空库存记录地址
						$("div[name='HCK-NAME']").attr("class","kf_hui no");//清空货位状态
						$("#kc_table tbody tr").remove();
						while(i<e.length){
							$('#kc_table tbody').append('<tr bgcolor="#ffffff" style="height:22px;">'+
								//托盘
								'<td title="'+e[i].tp_code+'" style="width:40px;padding:0px;font-size:10px;">'+(e[i].tp_code.length>12?e[i].tp_code.substring(0,12)+"...":e[i].tp_code)+'</td>'+
								//物料
								'<td title="'+e[i].wl_code+'" style="width:50px;padding:0px;font-size:10px;">'+(e[i].wl_code.length>10?e[i].wl_code.substring(0,10)+"...":e[i].wl_code)+'</td>'+
								//数量
								'<td title="'+e[i].number+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].number+'</td>'+
								//货位号
								'<td title="'+e[i].huoweihao+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].huoweihao+'</td>'+
								//方向
								'<td title="'+e[i].fangxiang+'" style="width:40px;padding:0px;font-size:10px;">'+e[i].fangxiang+'</td>'+
							'</tr>');
							//获取当前库存中的所有物料
							if(li.indexOf("<li>"+e[i].wl_code+"</li>")==-1){
								li+="<li>"+e[i].wl_code+"</li>";
							};
							//获取每个物料对应的库位号
							if(e[i].huoweihao!=""){
								af.showWlIp.ip[i]=e[i].huoweihao+"&"+e[i].wl_code+"&"+e[i].fangxiang;
							}else if(af.showWlIp.ip[i]!=e[i].huoweihao+"&"+e[i].wl_code+"&"+e[i].fangxiang){
								af.showWlIp.ip[i]=null;
							};
							i++;
						};i=null;e=null;
                    	var a=$('#show_wuliao_name ul').html(li);li=null;a=null;
                    	var b=af.showWlIp.show();b=null;return null;
					}
				}
			};
			var a=af.load({state:true,tim:1000},function(){
				if(af_Home.administrator.发送命令==false){var a=af_Home.cleanQX("kcfsBtn");a=null;};
			});a=null;
			return null;
		}catch(e){
			return e;
		};
		return null;
	}
};