var readyShow={
	showThis:function(e){
		if($(e).css("display")=="block"){
			return null;
		};
		var a=$(".showWindow").fadeOut(0,function(){
			var a=$(e).fadeIn(0);a=null;
			return null;
		});a=null;
		if(e.id=="dingDanUpdate"){
			var b=$(".anNiuSelect").click();
			b=null;
		};
		return null;
	},
	load:function(){
		try{
			var winHeight=document.body.clientHeight;
			if(winHeight==window.screen.height){
				winHeight=document.body.clientHeight-50;
			};
			$('#xy').css('height',(winHeight-(window.screen.height-winHeight))/0.98);
			var a=$('#table-body1').css('height', document.body.clientHeight/8);a=null;
			var b=$('#table-body2').css('height', document.body.clientHeight/8);b=null;
			var c=$('#table-body3').css('height', document.body.clientHeight/2.8);c=null;
			winHeight=null;
			/*绑定日历选择器*/
			$('#Gdfenjieriqi').datetimepicker({
			    format:'yyyy-mm-dd',
			    autoclose:true,
			    pickerPosition:'bottom-left',
			    todayBtn:1,
			    linkFormat:'yyyy-mm-dd',
			    minView:'month'
			});
			//PLC区域点击事件
			$('.mainOrder').click(function(e){
			    var item=$(e.target);
			    $('#mainStatusBar').find('div').removeClass('select');
			    item.addClass('select');
			    $(".plc_show").children().css("display","none");
			    $("#plc_show"+ $(item).attr('data')).css("display","block");
			    var gw=null;
			    //如果当前是装配区A 不需要重复调用刷新事件
			    if($(item).attr('data')==1&&af.showType=="A"){
			    	return item=null;
			    //如果当前是装配区B 不需要重复调用刷新事件
			    }else if($(item).attr('data')==2&&af.showType=="B"){
			    	return item=null;
			    //如果当前是返回PLC工位A 则不需要重复调用刷新事件
			    }else if($(item).attr('data')==3&&af.showType=="C"){
			    	return item=null;
			    //如果当前是返回PLC工位B 则不需要重复调用刷新事件
			    }else if($(item).attr('data')==4&&af.showType=="D"){
			    	return item=null;
			    }else if($(item).attr('data')==1){
			    	af.showType="A";	//渲染A区
			    }else if($(item).attr('data')==2){
			    	af.showType="B";	//渲染B区
			    }else if($(item).attr('data')==3){
			    	af.showType="C";	//渲染C区，PLC选择返回工位
					gw=$("#returnPlcA").val();
			    }else{
			    	af.showType="D";	//渲染D区，PLC选择返回工位
					gw=$("#returnPlcB").val();
			    };
				var a=af.getTableGW(function(){//调用渲染html函数
					return null;
				},af.showType,gw),a=null,gw=null;
				return item=null;
			});
			var af={
				//默认渲染A区条件
				showType:"A",
				//物料查询窗体
				getWuliao:function(fun){
					try{
						var openWindow='<div style="width:100%;margin-right:0;"><div class="margin-top-10"><div class="col-md-11" style="margin-left:20px;"><!-- 标题 --><div style="padding-right:17px;"><table class="table table-bordered text-center" id="commonSearchTableHead"><thead><tr><td style="width:50%;">编号</td><td style="width:50%;">描述</td></tr></thead></table></div><!-- 标题 end--><!-- 内容 --><div class="table-body" id="commonSearchTableBody"><table class="table table-bordered text-center table-hover" id="searchTable"></table></div><!-- 内容 end--></div></div></div>';
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
						});openWindow=null;
						/*设置table高度*/
						$('#commonSearchTableBody').css('height',document.body.clientHeight / 3);
						var a=$.ajax({
							url:getRootPath()+'/BaseDataAction.do?operType=selectWlList',
							type:'get',
							data:'leibie=配方',
							cache:false,
							success:function(data){
						    	var obj=eval("("+data+")");data=null;
								for(var i=0;i<obj.length;i++){
					                $('#searchTable').append('<tr class="commonTr" style="cursor:pointer;"></tr>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_code" style="width:50%;">'+ obj[i].wuliao_code+ '</td>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_miaoshu" style="width:50%;">'+ obj[i].wuliao_miaoshu+ '</td>');
								};obj=null;
								/*绑定双击事件*/
				            	$('.commonTr').bind('dblclick',function(e){
									var tr=$(e.target).parent();
									fun(tr.find('.wuliao_code').text());
				            		layer.close(win),win=null,tr=null;
				            		return null;
				            	});
							}
						});a=null;
						return null;
					}catch(e){
						return e;
					};
					return null;
				},
				//获取所有工位信息
				action:function(fun,type,gw){
					var b;
					if(type=="A"){
						b=$("table[name='GW'] tbody tr").remove();
					}else{
						b=$("table[name='GW_B'] tbody tr").remove();
					};
					var a=$.ajax({
						url:getRootPath()+'/PLCAction.do?operType=getGw',
						type:'get',
						cache:false,
						data:"type="+type+"&gw="+gw,
						success:function(data){
							var obj=eval("("+data+")");
							return fun(obj),obj=null;
						}
					});
					return a=null,b=null;
				},
				//更新数据函数
				updateAction:function(map,fun){
					var a=$.ajax({
						url:getRootPath()+'/PLCAction.do?operType=updateGw',
						type:'get',
						data:map,
						cache:false,
						success:function(data){
							if(data.indexOf("成功")==-1){
								layer.msg("数据已同步，请重新设置");
							}
							return fun();
						}
					});
					return a=null;
				},
				//获取当前工位所修改的值并且提交;
				setValue:function(e,map){
					var data={
						cm:map.cm,
						gwType:map.gwType,
						gw:$(e).parent().attr("GW"),
						name:$('#'+map.name).attr("title"),
						value:$(e).attr("title"),
						oldValue:$(e).attr('oldValue')
					};
					var type=map.gwType;
					var b=af.updateAction(data,function(){
						var a=$("#PLC").click();
						if(type=="C"||type=="D"){
							var timename=setTimeout(function(){
								var a=$("#PLC").click();a=null;//刷新数据
								clearTimeout(timename);timename=null;
							},1000);
						};a=null;type=null;
						return null;
					});
					return b=null,map=null,e=null,data=null;
				},
				//双击事件
				dblclick:function(e,map){
					if($(e).text()=="true"){
						$(e).attr("title",false);
						var a=af.setValue(e,map);//update
						return map=null,a=null;
					}else if($(e).text()=="false"){
						$(e).attr("title",true);
						var a=af.setValue(e,map);//update
						return map=null,a=null;
					}else{
						if(e.ck==false||e.ck==undefined){
							e.ck=true;
							//只有物料编码可输入string
							if($(e).parent().find('td').eq(0).html()=="物料编码"){
								af.getWuliao(function(d){
						            $(e).html(d.length>6?d.substring(0,6)+'..':d);
						            $(e).attr("title",d);
						            e.ck?e.ck=false:null;
									var a=af.setValue(e,map);//update
									return map=null,a=null;
								});
							//只允许输入数字
							}else{
								//如果是machineID 不做任何改变
								if($('#'+map.name).attr('title')=="machineID"){
									return null;
								};
								$(e).html('<input id="td_input" style="padding:0;font-size:10px;width:100%;height:19px;" type="number" min="0" class="form-control" value="'+ $(e).attr('title')+ '">');
								$("#td_input").focus();
								$("#td_input").blur(function(){
									if(this.value==""){
										this.value=0;
									};
						            $(e).html(this.value.length>6?this.value.substring(0,6)+'..':this.value);
						            $(e).attr("title",this.value);
						            e.ck?e.ck=false:null;
									var a=af.setValue(e,map);//update
									return map=null,a=null,node=null;
						        });
								$("#td_input").keydown(function(event){
						            if(event.keyCode==13){
										if(this.value==""){
											this.value=0;
										};
							            $(e).html(this.value.length>6?this.value.substring(0,6)+'..':this.value);
							            $(e).attr("title",this.value);
							            e.ck?e.ck=false:null;
										var a=af.setValue(e,map);//update
										return map=null,a=null,node=null;
						            }
							        return null;
						        });
							}
						}
					};
					return null;
				},
				//更新输送线状态
				ssxUpdate:function(obj){
					if(obj.A_SSX.length>0){
						var j=0;
						$("div[name='ssx']").attr("class","plc_gongwei_hui");
						while(j<obj.A_SSX.length){
							try{
								if(obj.A_SSX[j][j]&&obj.A_SSX[j][j]!=""){
									$("#ssx"+j).html(obj.A_SSX[j][j]);
									$("#ssx"+j).attr("class","plc_gongwei_lan");
								}else{
									$("#ssx"+j).html("");
								}
							}catch(e){
								alert(e);
							};
							j++;
						};
						j=null;
					}else{
						$("div[name='ssx']").attr("class","plc_gongwei_hui");
						$("div[name='ssx']").html("");
					}
					return null;
				},
				//A区需要被渲染的html,obj=后台返回数据，mm=当前的装配区以及各个元素的ID
				showHtml:function(obj,mm){
					if(mm.type=="A"||mm.type=="B"){
						var j=0;
						while(j<obj.data.length){
							var i=0,map=obj.data[j];
							while(i<map.A_LIST.length){
								if(map.A_LIST[i].A.indexOf('-')==-1&&map.A_LIST[i].A!="boolContent"){
									$('#'+mm.tableId+''+j+' tbody').append('<tr name="'+mm.trId+'" GW="'+j+'" bgcolor="#ffffff" style="height:20px;">'+
										//项目
										'<td id="'+mm.tdId1+''+j+'_'+i+'" title="'+map.A_LIST[i].A+'" style="width:100px;font-size:12px;">'+
											(map.A_LIST[i].A.length>6?map.A_LIST[i].A.substring(0,7)+'..':map.A_LIST[i].A)+
										'</td>'+
										//列队1
										'<td id="'+mm.tdId2+''+j+'_'+i+'" title="'+map.A_LIST[i].B+'" oldValue="'+map.A_LIST[i].B+'" style="width:50px;font-size:12px;">'+
											(map.A_LIST[i].B.toString().length>6?map.A_LIST[i].B.toString().substring(0,6)+'..':map.A_LIST[i].B)+
										'</td>'+
										//列队2
										'<td id="'+mm.tdId3+''+j+'_'+i+'" title="'+map.A_LIST[i].C+'" oldValue="'+map.A_LIST[i].C+'"  style="width:50px;font-size:12px;">'+
											(map.A_LIST[i].C.toString().length>6?map.A_LIST[i].C.toString().substring(0,6)+'..':map.A_LIST[i].C)+
										'</td>'+
									'</tr>');
									//队列1
									$('#'+mm.tdId2+''+j+'_'+i).dblclick(function(){
										if(af_Home.administrator.PLC==false){return null;};
										var row=$(this).attr("id").split("_");
										var map={
											cm:"firstST",
											name:mm.tdId1+''+row[1]+'_'+row[2],
											gwType:mm.gwType
										};
										var a=af.dblclick(this,map);
										return map=null,a=null,row=null;
									});
									//队列2
									$('#'+mm.tdId3+''+j+'_'+i).dblclick(function(){
										if(af_Home.administrator.PLC==false){return null;};
										var row=$(this).attr("id").split("_");
										var map={
											cm:"secondST",
											name:mm.tdId1+''+row[1]+'_'+row[2],
											gwType:mm.gwType
										};
										var a=af.dblclick(this,map);
										return map=null,a=null,row=null;
									});
								}
								i++;
							}
							$("#BOOL"+j+"_"+mm.type).val(map.A_BOLL);
							$("#DIZHI"+j+"_"+mm.type).val(map.A_SDZ);
							j++,i=null,map=null;
						}
						//A区输送线&&B区输送线
						af.ssxUpdate(obj);
						return j=null;
					}else{
						//当前标签不在AB区时，清空AB区的数据，
						var a=$("table[name='GW'] tbody tr").remove(),a=null;
						var b=$("table[name='GW_B'] tbody tr").remove(),b=null;
						var c=$("table[name='returnTablePlc'] tbody tr").remove(),c=null;
						var map=obj.data[0];
						var i=0;
						while(i<map.A_LIST.length){
							$('#'+mm.tableId+' tbody').append('<tr name="'+mm.trId+'" GW="'+mm.gw+'" bgcolor="#ffffff" style="height:20px;">'+
								//项目
								'<td id="'+mm.tdId1+'_'+i+'" title="'+map.A_LIST[i].A+'" style="width:100px;font-size:12px;">'+
									(map.A_LIST[i].A.length>15?map.A_LIST[i].A.substring(0,14)+'..':map.A_LIST[i].A)+
								'</td>'+
								//列队1
								'<td id="'+mm.tdId2+'_'+i+'" title="'+map.A_LIST[i].B+'" oldValue="'+map.A_LIST[i].B+'" style="width:50px;font-size:12px;">'+
									(map.A_LIST[i].B.toString().length>8?map.A_LIST[i].B.substring(0,8)+'..':map.A_LIST[i].B)+
								'</td>'+
								//列队2
								'<td id="'+mm.tdId3+'_'+i+'" title="'+map.A_LIST[i].C+'" oldValue="'+map.A_LIST[i].C+'"  style="width:50px;font-size:12px;">'+
									(map.A_LIST[i].C.toString().length>8?map.A_LIST[i].C.substring(0,8)+'..':map.A_LIST[i].C)+
								'</td>'+
							'</tr>');
							//队列1
							$('#'+mm.tdId2+'_'+i).dblclick(function(){
								if(af_Home.administrator.PLC==false){return null;};
								var row=$(this).attr("id").split("_");
								var map={
									cm:'',
									name:mm.tdId1+'_'+row[1],
									gwType:mm.gwType
								};
								var a=af.dblclick(this,map);
								return map=null,a=null,row=null;
							});
							//队列2
							$('#'+mm.tdId3+'_'+i).dblclick(function(){
								if(af_Home.administrator.PLC==false){return null;};
								var row=$(this).attr("id").split("_");
								var map={
									cm:'',
									name:mm.tdId1+'_'+row[1],
									gwType:mm.gwType
								};
								var a=af.dblclick(this,map);
								return map=null,a=null,row=null;
							});
							i++;
						};
						$("#BOOL0_"+mm.type).val(map.A_BOLL);
						$("#DIZHI0_"+mm.type).val(map.A_SDZ);
						//A区输送线&&B区输送线
						af.ssxUpdate(obj);
						return null;
					};
					return null;
				},
				//所以元素ID结合map
				map:function(type,gw){
					var map={};
					if(type=="A"){//装配区A
						map={
							type:"A",gw:null,
							tableId:"A_GW",trId:"newTr_A",
							tdId1:"newTd1_",tdId2:"newTd2_",
							tdId3:"newTd3_",gwType:"A"
						};
					}else if(type=="B"){//装配区B
						map={
							type:"B",gw:null,
							tableId:"B_GW",trId:"newTr_B",
							tdId1:"BnewTd1_",tdId2:"BnewTd2_",
							tdId3:"BnewTd3_",gwType:"B"
						};
					}else if(type=="C"){//PLC选择返回工位A
						map={
							gw:gw,
							type:"C",
							tableId:"returnTablePlcA",
							trId:"returnPlcTrA",
							tdId1:"returnPlcTd1A",tdId2:"returnPlcTd2A",
							tdId3:"returnPlcTd3A",gwType:"C"
						};
					}else if(type=="D"){//PLC选择返回工位B
						map={
							gw:gw,
							type:"D",
							tableId:"returnTablePlcB",
							trId:"returnPlcTrB",
							tdId1:"returnPlcTd1B",tdId2:"returnPlcTd2B",
							tdId3:"returnPlcTd3B",gwType:"D"
						};
					};
					return map;
				},
				//获取所有工位信息启动函数 fun=回调函数，type=当前那个装配区,gw=当前第几个工位
				getTableGW:function(fun,type,gw){
					var a=this.action(function(obj){
						var map=af.map(type,gw);
						var b=af.showHtml(obj,map);
						b=null,map=null,type=null,gw=null;
					},type,gw),a=null;
					return fun();
				},
				/**
				 * 故障恢复
				 */
				GdId:null,//工单ID
				MzId:null,//模组序ID
				//工单显示
				showGdList:function(GdId,Gdfenjieriqi,PackeCode,fun){
					var a=$.ajax({
						url:getRootPath()+'/OrderOperAction.do?operType=getDdList',
						type:'post',cache:false,
						data:"getGdId="+GdId+"&getGdfenjieriqi="+Gdfenjieriqi+"&getPackeCode="+PackeCode,
						success:function(data){
							var a=$('#dd_table tbody tr').remove();a=null;//工单table
			  				var obj=eval("("+data+")"),i=0;
							while(i<obj.length){
			  					$('#dd_table tbody').append('<tr id="gd_row'+i+'" bgcolor="#ffffff" style="height:20px;">'+
			  						//单选
									'<td id="radio_id_'+i+'" style="width:3%;">'+
										'<input type="radio" id="trRadio_dd'+obj[i].id+'" name="trGdRadio" value="'+obj[i].id+'"/>'+
									'</td>'+
									//工单序号
									'<td style="width:8%;">'+obj[i].dd_xuhao+
									'<input type="hidden" id="dd_code'+i+'" value="'+obj[i].dd_code+'"></td>'+
									//状态
									'<td style="width:7%;">'+obj[i].dd_zhuangtai+'</td>'+
									//分解日期
									'<td style="width:10%;">'+obj[i].dd_fenjieriqi+'</td>'+
									//PACK编码
									'<td style="width:10%;">'+obj[i].pack_code+'</td>'+
									//PACK类型
									'<td style="width:8%;">'+obj[i].pack_leixing+'</td>'+
									//装配区
									'<td style="width:8%;">'+obj[i].dd_zhuangpeiqu+'</td>'+
									//计划数量
									'<td style="width:8%;">'+obj[i].dd_jihuashuliang+'</td>'+
									//完成进度
									'<td style="width:5%;padding:1px;">'+
										'<div class="progress progress-striped active" style="margin-bottom:0px;">'+
											'<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:'+obj[i].dd_jindu+'%;"></div>'+
										'</div>'+
									'</td>'+
								'</tr>');
								$("#gd_row"+i).click(function(){
									var rowIndex=this.id.split("row")[1];
									var b=$(this).children("td").eq(0).find("input:radio").prop("checked",true);
									var gd_id=$(this).children("td").eq(0).find("input:radio").val();
									var pack_code=$(this).children("td").eq(4).html();
									var line=$(this).children("td").eq(6).html();
									var a=$.ajax({
										url:getRootPath()+'/OrderOperAction.do?operType=getZlmzList',
										type:'post',
										cache:false, 
										data:"dd_id="+gd_id+"&pack_code="+pack_code,
										success:function(data){
											var a=af.showGdMzList(eval("("+data+")"),line,fun);
											a=null;return null;
										}
									});a=null;gd_id=null;pack_code=null;rowIndex=null;b=null;
									return null;
								});
								i++;
							};
			  				if(obj.length>0){
								$('#dd_table tbody tr').eq(0).click();
			  				}else{
								var a=$('#mz_table tbody tr').remove();a=null;//模组table
								var c=$('#pf_table1 tbody tr').remove();c=null;//配方table
								var d=$('#pf_table2 tbody tr').remove();d=null;//配方table
			  					var a=fun();a=null;fun=null;
			  				};obj=null;
			  				return null;
						}
					});a=null;
					return null;
				},
				//模组显示
				showGdMzList:function(obj,line,fun){
					var a=$('#mz_table tbody tr').remove();a=null;//模组table
					for(var i=0;i<obj.length;i++){
						$('#mz_table tbody').append('<tr id="mz_row'+i+'" bgcolor="#ffffff" style="height:20px;">'+
						//单选
						'<td style="width:2%;">'+
							'<input type="radio" name="trMzRadio"/>'+
							'<input type="hidden" id="mz_xuId'+i+'" value="'+obj[i].mz_xuId+'">'+
						'</td>'+
						//模组类型
						'<td style="width:8%;">'+obj[i].mz_leixing+
						'<input type="hidden" id="dd_id'+i+'" value="'+obj[i].dd_id+'"></td>'+
						//模组编码
						'<td style="width:7%;">'+obj[i].mz_code+'</td>'+
						//数量
						'<td style="width:10%;">'+obj[i].mz_shuliang+'</td>'+
						//完成进度
						'<td style="width:3%;padding:1px;">'+
							'<div class="progress progress-striped active" style="margin-bottom:0px;">'+
								'<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:'+obj[i].mz_jindu+'%;"></div>'+
							'</div>'+
						'</td>'+
						'</tr>');
						$('#mz_row'+i).click(function(){
							var a=$(this).children("td").eq(0).find("input:radio").prop("checked",true);
							var dd_id=$(this).children("td").eq(1).find("input:hidden").val();
							var mz_xuId=$(this).children("td").eq(0).find("input:hidden").val();
							var b=af.showGdMzPfList(dd_id,mz_xuId,line,fun),dd_id=null,mz_xuId=null,a=null;b=null;
							return null;
						});
					};
					if(obj.length>0){
						$('#mz_row0').click();
					}else{
						var c=$('#pf_table1 tbody tr').remove();c=null;//配方table
						var d=$('#pf_table2 tbody tr').remove();d=null;//配方table
						var a=fun();a=null;fun=null;
					};obj=null;
					return null;
				},
				//模组配方显示
				showGdMzPfList:function(GdId,MzId,line,fun){
					af.GdId=GdId;af.MzId=MzId;//赋值
					var a=$('#pf_table1 tbody tr').remove();a=null;//配方table
					var b=$('#pf_table2 tbody tr').remove();b=null;//配方table
					var a=$.ajax({
						url:getRootPath()+'/OrderOperAction.do?operType=getZlpfList',
						type:'post',cache:false, 
						data:"dd_id="+GdId+"&mz_xuId="+MzId+"&type=GuzhangHuifu&line="+line,
						success:function(data){
			  				var obj=eval("("+data+")");
			  				for(var i=0;i<obj.A.length;i++){
			  					$('#pf_table1 tbody').append('<tr bgcolor="#ffffff" line="'+line+'" style="height:'+obj.A[i].row+'px;">'+
									'<td style="width:5%;">'+obj.A[i].dd_zaijuxuhao+'</td>'+//载具号
									'<td class="dbClick" style="width:10%;">'+obj.A[i].dd_qianshengdubiaozhi+'</td>'+//前升读标志
									'<td class="ybShuSx" style="width:10%;border-right:0px;">'+obj.A[i].shusongxian+'</td>'+//异步输送线位置
									'<td style="display:none;">'+obj.A[i].fenJh+'</td>'+
			  					'</tr>');
			  				};obj.A=null;
			  				for(var i=0;i<obj.B.length;i++){
								$('#pf_table2 tbody').append('<tr bgcolor="#ffffff" line="'+line+'" style="height:24px;">'+
									'<td style="width:5%;">'+obj.B[i].dd_gdId+'</td>'+//工单ID
									'<td style="width:5%;">'+obj.B[i].dd_mzxId+'</td>'+//模组序ID
									'<td style="width:5%;">'+obj.B[i].dd_fenjiehao+'</td>'+//模组序ID
									'<td style="width:5%;" title="'+obj.B[i].dd_wuliao+'">'+(obj.B[i].dd_wuliao.length>9?obj.B[i].dd_wuliao.substring(0,7)+'..':obj.B[i].dd_wuliao)+'</td>'+//物料编码
									'<td style="width:5%;">'+obj.B[i].dd_xuqiushuliang+'</td>'+//需求数量
									'<td class="wanChengSL" style="width:5%;">'+obj.B[i].dd_wanchengshuliang+'</td>'+//完成数量
									'<td style="width:5%;">'+obj.B[i].dd_gongwei+'</td>'+//工位
									'<td class="dbClick" style="width:10%;">'+obj.B[i].dd_stduqubiaozhi+'</td>'+//ST预读
									'<td style="display:none;">'+obj.B[i].dd_zaijuxuhao+'</td>'+
								'</tr>');
							};obj.B=null;
							//完成数量点击事件
							$(".wanChengSL").click(function(){
								if(af_Home.administrator.PLC==false){return null;};
								var e=this;
								if(this.ck==false||this.ck==undefined){
									e.ck=true;
									var c=$(e).html('<input id="wanChengSL" style="padding:0;font-size:10px;width:100%;height:23px;" type="number" min="0" class="form-control" value="'+ $(e).html()+ '">');
									var a=$("#wanChengSL").focus();a=null;c=null;
									var b=$("#wanChengSL").blur(function(){
										if(this.value==""){this.value=0;};
							            var a=$(e).html(this.value);a=null;
							            e.ck?e.ck=false:null;e=null;
							            return null;
							        });b=null;
							        $('#wanChengSL').change(function(){
					                    var a=$(e).parent().attr("class","update");a=null;
								        return null;
						            });
								};
								return null;
							});
							$(".ybShuSx").click(function(){
								if(af_Home.administrator.PLC==false){return null;};
								var e=this;//selected
								if(this.ck==false||this.ck==undefined){
									e.ck=true;
									var height=$(this).parent().height();
									var c=$(e).html('<select class="selectpicker" id="ybShuSx" style="height:'+(height-1)+';font-size:14px;width:100%;"><option value=""></option><option value="-1" '+($(this).html()=="-1"?"selected":"")+'>-1</option><option value="0" '+($(this).html()=="0"?"selected":"")+'>0</option><option value="1" '+($(this).html()=="1"?"selected":"")+'>1</option><option value="2" '+($(this).html()=="2"?"selected":"")+'>2</option><option value="3" '+($(this).html()=="3"?"selected":"")+'>3</option><option value="4" '+($(this).html()=="4"?"selected":"")+'>4</option><option value="5" '+($(this).html()=="5"?"selected":"")+'>5</option><option value="6" '+($(this).html()=="6"?"selected":"")+'>6</option><option value="7" '+($(this).html()=="7"?"selected":"")+'>7</option><option value="8" '+($(this).html()=="8"?"selected":"")+'>8</option><option value="9" '+($(this).html()=="9"?"selected":"")+'>9</option><option value="10" '+($(this).html()=="10"?"selected":"")+'>10</option><option value="11" '+($(this).html()=="11"?"selected":"")+'>11</option><option value="12" '+($(this).html()=="12"?"selected":"")+'>12</option><option value="13" '+($(this).html()=="13"?"selected":"")+'>13</option><option value="14" '+($(this).html()=="14"?"selected":"")+'>14</option><option value="15" '+($(this).html()=="15"?"selected":"")+'>15</option></select>');
									c=null;height=null;
									var a=$("#ybShuSx").focus();a=null;
									var b=$("#ybShuSx").blur(function(){
							            var a=$(e).html(this.value);a=null;
							            e.ck?e.ck=false:null;e=null;
							            return null;
							        });b=null;
							        $('#ybShuSx').change(function(){
					                    var a=$(e).parent().attr("class","update");a=null;
								        return null;
						            });
								};
								return null;
							});
							//前升预读&ST预读
							$(".dbClick").dblclick(function(){
								if(af_Home.administrator.PLC==false){return null;};
								if($(this).html()=='已读'){
									var a=$(this).html('');a=null;
								}else{
									var a=$(this).html('已读');a=null;
								};
			                    var a=$(this).parent().attr("class","update");a=null;
								return null;
							});
							obj=null;
							return obj;
						}
					});a=null;
					return fun();
				},
				/**
				 * 故障恢复保存
				 */
				anNiuSave:function(function_){
					var update1=new Array;
					var pf_table1=$('#pf_table1 tbody tr');
					var line=$('#pf_table1 tbody tr').attr("line");
					for(var i=0;i<pf_table1.length;i++){
			            if($(pf_table1).parent().children("tr").eq(i).attr("class")=="update"){
			            	var zaiJh=pf_table1.eq(i).children("td").eq(0).html();
			            	var zaiJydbz=pf_table1.eq(i).children("td").eq(1).html();
			            	var yiBssx=pf_table1.eq(i).children("td").eq(2).html();
			            	var fenJh=pf_table1.eq(i).children("td").eq(3).html();
							var map={
								GdId:af.GdId,
								MzId:af.MzId,
								zaiJh:zaiJh,
								zaiJydbz:zaiJydbz=='已读'?'1':'',
								yiBssx:yiBssx==''?'-2':yiBssx,
								fenJh:fenJh
							};zaiJh=null;zaiJydbz=null;yiBssx=null;fenJh=null;
			                update1.push(map);//修改数据
			                map=null;
			            };
					};pf_table1=null;
					var update2=new Array;
					var pf_table2=$('#pf_table2 tbody tr');
					for(var i=0;i<pf_table2.length;i++){
			            if($(pf_table2).parent().children("tr").eq(i).attr("class")=="update"){
			            	var fenJh=pf_table2.eq(i).children("td").eq(2).html();
			            	var zaiJh=pf_table2.eq(i).children("td").eq(8).html();
			            	var wanCsl=pf_table2.eq(i).children("td").eq(5).html();
			            	var stGwydbz=pf_table2.eq(i).children("td").eq(7).html();
			            	var gw=pf_table2.eq(i).children("td").eq(6).html();
							var map={
								gw:gw,
								GdId:af.GdId,
								MzId:af.MzId,
								zaiJh:zaiJh,
								fenJh:fenJh,
								wanCsl:wanCsl,
								stGwydbz:stGwydbz=='已读'?'1':''
							};fenJh=null;zaiJh=null;wanCsl=null;stGwydbz=null;
							update2.push(map);//修改数据
			                map=null;
			            };
					};pf_table2=null;
					if(update1.length==0&&update2.length==0){
						layer.msg("没有可恢复的数据！");
						return null;
					};
					var a=$.ajax({
						url:getRootPath()+'/PLCAction.do?operType=gwGzUpdate',
						type:'post',cache:false,
						data:'update1='+JSON.stringify(update1)+'&update2='+JSON.stringify(update2)+"&line="+line,
						success:function(data){
			  				var obj=eval("("+data+")");data=null;
			  				if(obj.plcDd==false){
								layer.msg("请先停止调度！");
			  				}else if(obj.result==true&&obj.setCarryAt=='成功'){
								layer.msg("故障已恢复！");
								var a=$(".anNiuSelect").click();a=null;
			  				}else if(obj.update==false){
			  					layer.msg("输送线上还有未完成的载具，请完成后操作。或者与系统管理员联系！");
			  				}else{
								layer.msg("故障恢复失败或者异步输送线位置恢复失败！");
			  				};obj=null;
			  				return function_(),function_=null;
						}
					});a=null;update1=null;update2=null;line=null;
					return null;
				},
				/**
				 * 一键还原数据清除函数
				 */
				updateAllTable:function(){
					var line=$('#pf_table1 tbody tr').attr("line");
					var a=$.ajax({
						url:getRootPath()+'/PLCAction.do?operType=updateAll',
						type:'post',cache:false,
						data:'GdId='+af.GdId+"&line="+line,
						success:function(data){
			  				var obj=eval("("+data+")");data=null;
			  				if(obj.plcDd==false){
								layer.msg("请先停止调度！");
			  				}else if(obj.result==true&&obj.setCarryAt=='成功'){
								var a=$(".anNiuSelect").click();a=null;
								layer.msg("一键还原成功！");
			  				}else if(obj.update==false){
			  					layer.msg("输送线上还有未完成的载具，请完成后操作。或者与系统管理员联系！");
			  				}else{
								layer.msg("故障恢复失败或者异步输送线位置恢复失败！");
			  				};obj=null;line=null;
			  				return null;
						}
					});a=null;
					return null;
				},
				/***
				 * 渲染界面函数
				 */
				load:function(fun){
					//选择PLC返回工位
					$(".selectpicker").change(function(){
						var win=layer.open({type:3});
						var gw=$(this).val();
						var a=af.getTableGW(function(){
							return layer.close(win),win=null;
						},af.showType,gw),gw=null;
						return null;
					});
					//刷新按钮事件绑定
					$("#PLC").click(function(){
						var win=layer.open({type:3}),gw;
						if(af.showType=='C'){
							gw=$("#returnPlcA").val();//光大返回A
						}else if(af.showType=='D'){
							gw=$("#returnPlcB").val();//光大返回B
						};
						var a=af.getTableGW(function(){
							var b=layer.close(win);
							win=null;b=null;
							return null;
						},af.showType,gw);gw=null;//默认渲染A区
						return null;
					});
					//权限
					var a=af_Home.getFx(function(fx){
						if(fx=="1,2"){$("#PLC").click();return null;};
						var a=$(".mainOrder").each(function(){
							if(fx==1){
								if($(this).attr('data')==2||$(this).attr('data')==4){
									$(this).css("display","none");
								};
							}else{
								if($(this).attr('data')==2){
								    $('#mainStatusBar').find('div').removeClass('select');
									$(this).addClass('select');
								    $("#plc_show1").css("display","none");
								    $("#plc_show2").css("display","block");
									af.showType="B";
								};
								if($(this).attr('data')==1||$(this).attr('data')==3){
									$(this).css("display","none");
								};
							};
						});$("#PLC").click();a=null;
					});a=null;
					//故障恢复查询按钮点击事件
					$(".anNiuSelect").click(function(){
						var win=layer.open({type:3});
						var GdId=$("#GdId").val();
						var Gdfenjieriqi=$("#Gdfenjieriqi").val();
						var PackeCode=$("#PackeCode").val();
						var a=af.showGdList(GdId,Gdfenjieriqi,PackeCode,function(){
							var b=layer.close(win);win=null;b=null;
							return null;
						});a=null;
						GdId=null;Gdfenjieriqi=null;PackeCode=null;
						return null;
					});
					//故障恢复保存按钮
					$(".anNiuSave").click(function(){
						var a=af.anNiuSave(function(){
							return null;
						});a=null;
						return null;
					});
					//清除PLC指令
					$(".anNiuDelete").click(function(){
						var line=$('#pf_table1 tbody tr').attr("line");
						if(!line){
							line=null;
							return null;
						};
						var a=$.ajax({
							url:getRootPath()+'/PLCAction.do?operType=gwGzDelete',
							type:'post',cache:false,
							data:"line="+line,
							success:function(data){
				  				var obj=eval("("+data+")");
				  				if(obj.plcDd==false){
									layer.msg("请先停止调度！");
				  				}else if(obj.result==false){
				  					layer.msg("输送线上还有未完成的载具，请完成后操作。或者与系统管理员联系！");
				  				}else{
									layer.msg("清除PLC指令成功！");
				  				};obj=null;
				  				return null;
							}
						});a=null;
						return null;
					});
					//一键还原
					$(".anNiuYJHY").click(function(){
						var radioID=$("input[name='trGdRadio']:checked");
						if(radioID.prop("checked")){
							if(radioID.parent().parent().children('td').eq(2).text()=='正在处理'){
								var lay=layer.confirm('请确认是否已经生产完成？',{
									title:'<span style="color:red;">安全提示</span>',
								    btn:['确定','取消']
								},function(){
									var a=af.updateAllTable();a=null;lay=null;
								});
							}else{
								var a=af.updateAllTable();a=null;
							};
						};radioID=null;
						return null;
					});
					return fun();
				}
			};
			var a=af.load(function(){
				if(af_Home.administrator.PLC==false){var c=af_Home.cleanQX("anNiuSave");c=null;var e=af_Home.cleanQX("anNiuDelete");e=null;};
				return null;
			});a=null;
			return null;
		}catch(e){
			return e;
		};
		return null;
	}
};