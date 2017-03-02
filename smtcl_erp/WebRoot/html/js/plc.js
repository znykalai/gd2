var readyShow = {
	load:function(hdFun){
		try{
			var winHeight = document.body.clientHeight;
			if(winHeight == window.screen.height){
				winHeight = document.body.clientHeight - 50;
			}
			$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
			winHeight=null;
			$('.mainOrder').click(function (e) {
			    var item = $(e.target);
			    $('#mainStatusBar').find('div').removeClass('select');
			    item.addClass('select');
			    $(".plc_show").children().css("display", "none");
			    $("#plc_show" + $(item).attr('data')).css("display", "block");
			    var gw = null;
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
					gw = $("#returnPlcA").val();
			    }else{
			    	af.showType="D";	//渲染D区，PLC选择返回工位
					gw = $("#returnPlcB").val();
			    };
				var a = af.getTableGW(function(){//调用渲染html函数
					return null;
				},af.showType,gw),a=null,gw=null;
				return item=null;
			});
			var af = {
				//默认渲染A区条件
				showType:"A",
				//物料查询窗体
				getWuliao:function(fun){
					try{
						var openWindow = '<div style="width: 100%;margin-right: 0;">' +
							'<div class="margin-top-10">' +
								'<div class="col-md-11" style="margin-left: 20px;">' +
								'<!-- 标题 -->' +
								'<div style="padding-right:17px;">' +
								'<table class="table table-bordered text-center" id="commonSearchTableHead">' +
								'<thead>' +
								'<tr>' +
								'<td style="width: 50%;">编号</td>' +
								'<td style="width: 50%;">描述</td>' +
								'</tr>' +
								'</thead>' +
								'</table>' +
								'</div>' +
								'<!-- 标题 end-->' +
								'<!-- 内容 -->' +
								'<div class="table-body" id="commonSearchTableBody">' +
								'<table class="table table-bordered text-center table-hover" id="searchTable">' +
								'</table>' +
								'</div>' +
								'<!-- 内容 end-->' +
								'</div>' +
							'</div>' +
						'</div>';
						var win = layer.open({
						    type: 1,
						    title: '物料查询',
						    shadeClose: false,
						    scrollbar:false,
						    anim:5,
						    move: false,
						    shade: [0.5, '#393D49'],
						    area: ['45%', '50%'],
						    content: openWindow
						});
						/*设置table高度*/
						$('#commonSearchTableBody').css('height', document.body.clientHeight / 3);
						var a = $.ajax({
							url: getRootPath()+'/BaseDataAction.do?operType=selectWlList',
							type: 'get',
							data: 'leibie=配方',
							cache:false,
							success: function (data) {
						    	var obj = eval("("+data+")");
								for(var i=0;i<obj.length;i++){
					                $('#searchTable').append('<tr class="commonTr" style="cursor: pointer;"></tr>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_code" style="width: 50%;">' + obj[i].wuliao_code + '</td>');
					                $('#searchTable tbody tr:last').
					                append('<td class="wuliao_miaoshu" style="width: 50%;">' + obj[i].wuliao_miaoshu + '</td>');
								}
								/*绑定双击事件*/
				            	$('.commonTr').bind('dblclick', function (e) {
									var tr = $(e.target).parent();
									fun(tr.find('.wuliao_code').text());
				            		layer.close(win);
				            		return openWindow=null,win=null,obj=null,tr=null,a=null;
				            	});
							}
						});
						return null;
					}catch (e) {
						return e;
					}
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
					var a = $.ajax({
						url: getRootPath()+'/PLCAction.do?operType=getGw',
						type: 'get',
						cache:false,
						data:"type="+type+"&gw="+gw,
						success: function (data) {
							var obj = eval("("+data+")");
							return fun(obj),obj=null;
						}
					});
					return a=null,b=null;
				},
				//更新数据函数
				updateAction:function(map,fun){
					var a = $.ajax({
						url: getRootPath()+'/PLCAction.do?operType=updateGw',
						type: 'get',
						data: map,
						cache:false,
						success: function (data) {
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
					var data = {
						cm:map.cm,
						gwType:map.gwType,
						gw:$(e).parent().attr("GW"),
						name:$('#'+map.name).attr("title"),
						value:$(e).attr("title"),
						oldValue:$(e).attr('oldValue')
					};
					var b=af.updateAction(data,function(){
						var a = $("#shuxin").click();
						var timename=setTimeout(function(){
							var a=$("#shuxin").click();a=null;//刷新数据
							clearTimeout(timename);timename=null;
						},888);
						return a=null;
					});
					return b=null,map=null,e=null,data=null;
				},
				//双击事件
				dblclick:function(e,map){
					if($(e).text()=="true"){
						$(e).attr("title",false);
						var a = af.setValue(e,map);//update
						return map=null,a=null;
					}else if($(e).text()=="false"){
						$(e).attr("title",true);
						var a = af.setValue(e,map);//update
						return map=null,a=null;
					}else{
						if(e.ck==false||e.ck==undefined){
							e.ck = true;
							//只有物料编码可输入string
							if($(e).parent().find('td').eq(0).html()=="物料编码"){
								af.getWuliao(function(d){
						            $(e).html(d.length>6?d.substring(0,6)+'..':d);
						            $(e).attr("title",d);
						            e.ck?e.ck=false:null;
									var a = af.setValue(e,map);//update
									return map=null,a=null;
								});
							//只允许输入数字
							}else{
								//如果是machineID 不做任何改变
								if($('#'+map.name).attr('title')=="machineID"){
									return null;
								};
								$(e).html('<input id="td_input" style="padding:0;font-size:10px;width:100%;height:19px;" type="number" min="0" class="form-control" value="' + $(e).attr('title') + '">');
								$("#td_input").focus();
								$("#td_input").blur(function (){
									if(this.value==""){
										this.value=0;
									};
						            $(e).html(this.value.length>6?this.value.substring(0,6)+'..':this.value);
						            $(e).attr("title",this.value);
						            e.ck?e.ck=false:null;
									var a = af.setValue(e,map);//update
									return map=null,a=null,node=null;
						        });
								$("#td_input").keydown(function (event){
						            if(event.keyCode==13){
										if(this.value==""){
											this.value=0;
										};
							            $(e).html(this.value.length>6?this.value.substring(0,6)+'..':this.value);
							            $(e).attr("title",this.value);
							            e.ck?e.ck=false:null;
										var a = af.setValue(e,map);//update
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
							}catch (e) {
								alert(e);
							}
							j++;
						}
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
							var i=0;
							var map = obj.data[j];
							while(i<map.A_LIST.length){
								if(map.A_LIST[i].A.indexOf('-')==-1&&map.A_LIST[i].A!="boolContent"){
									$('#'+mm.tableId+''+j+' tbody').append('<tr name="'+mm.trId+'" GW="'+j+'" bgcolor="#ffffff" style="height:20px;">' +
										//项目
										'<td id="'+mm.tdId1+''+j+'_'+i+'" title="'+map.A_LIST[i].A+'" style="width:100px;font-size:12px;">' +
											(map.A_LIST[i].A.length>6?map.A_LIST[i].A.substring(0,7)+'..':map.A_LIST[i].A) +
										'</td>' +
										//列队1
										'<td id="'+mm.tdId2+''+j+'_'+i+'" title="'+map.A_LIST[i].B+'" oldValue="'+map.A_LIST[i].B+'" style="width:50px;font-size:12px;">' +
											(map.A_LIST[i].B.toString().length>6?map.A_LIST[i].B.toString().substring(0,6)+'..':map.A_LIST[i].B) +
										'</td>' +
										//列队2
										'<td id="'+mm.tdId3+''+j+'_'+i+'" title="'+map.A_LIST[i].C+'" oldValue="'+map.A_LIST[i].C+'"  style="width:50px;font-size:12px;">' +
											(map.A_LIST[i].C.toString().length>6?map.A_LIST[i].C.toString().substring(0,6)+'..':map.A_LIST[i].C) +
										'</td>' +
									'</tr>');
									//队列1
									$('#'+mm.tdId2+''+j+'_'+i).dblclick(function(){
										var row = $(this).attr("id").split("_");
										var map = {
											cm:"firstST",
											name:mm.tdId1+''+row[1]+'_'+row[2],
											gwType:mm.gwType
										};
										var a=af.dblclick(this,map);
										return map=null,a=null,row=null;
									});
									//队列2
									$('#'+mm.tdId3+''+j+'_'+i).dblclick(function(){
										var row = $(this).attr("id").split("_");
										var map = {
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
						var map = obj.data[0];
						var i = 0;
						while(i<map.A_LIST.length){
							$('#'+mm.tableId+' tbody').append('<tr name="'+mm.trId+'" GW="'+mm.gw+'" bgcolor="#ffffff" style="height:20px;">' +
								//项目
								'<td id="'+mm.tdId1+'_'+i+'" title="'+map.A_LIST[i].A+'" style="width:100px;font-size:12px;">' +
									(map.A_LIST[i].A.length>15?map.A_LIST[i].A.substring(0,14)+'..':map.A_LIST[i].A) +
								'</td>' +
								//列队1
								'<td id="'+mm.tdId2+'_'+i+'" title="'+map.A_LIST[i].B+'" oldValue="'+map.A_LIST[i].B+'" style="width:50px;font-size:12px;">' +
									(map.A_LIST[i].B.toString().length>8?map.A_LIST[i].B.substring(0,8)+'..':map.A_LIST[i].B) +
								'</td>' +
								//列队2
								'<td id="'+mm.tdId3+'_'+i+'" title="'+map.A_LIST[i].C+'" oldValue="'+map.A_LIST[i].C+'"  style="width:50px;font-size:12px;">' +
									(map.A_LIST[i].C.toString().length>8?map.A_LIST[i].C.substring(0,8)+'..':map.A_LIST[i].C) +
								'</td>' +
							'</tr>');
							//队列1
							$('#'+mm.tdId2+'_'+i).dblclick(function(){
								var row = $(this).attr("id").split("_");
								var map = {
									cm:'',
									name:mm.tdId1+'_'+row[1],
									gwType:mm.gwType
								};
								var a=af.dblclick(this,map);
								return map=null,a=null,row=null;
							});
							//队列2
							$('#'+mm.tdId3+'_'+i).dblclick(function(){
								var row = $(this).attr("id").split("_");
								var map = {
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
					var map = {};
					if(type=="A"){//装配区A
						map = {
							type:"A",gw:null,
							tableId:"A_GW",trId:"newTr_A",
							tdId1:"newTd1_",tdId2:"newTd2_",
							tdId3:"newTd3_",gwType:"A"
						};
					}else if(type=="B"){//装配区B
						map = {
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
					var a = this.action(function(obj){
						var map = af.map(type,gw);
						var b=af.showHtml(obj,map);
						b=null,map=null,type=null,gw=null;
					},type,gw),a=null;
					return fun();
				},
				//渲染界面函数
				load:function(fun){
					//刷新按钮事件绑定
					$("#shuxin").click(function(){
						var win = layer.open({type:3});
						var gw = null;
						if(af.showType=='C'){
							//光大返回A
							gw = $("#returnPlcA").val();
						}else if(af.showType=='D'){
							//光大返回B
							gw = $("#returnPlcB").val();
						};
						var a = af.getTableGW(function(){
							return layer.close(win);win=null;
						},af.showType,gw),gw=null;//默认渲染A区
						return null;
					});
					$("#shuxin").click();
					//选择PLC返回工位
					$(".selectpicker").change(function(){
						var win = layer.open({type:3});
						var gw = $(this).val();
						var a = af.getTableGW(function(){
							return layer.close(win),win=null;
						},af.showType,gw),gw=null;
						return null;
					});
					return fun();
				}
			};
			return af.load(function(){hdFun();});
		}catch (e) {
			return e;
		}
		return null;
	}
};