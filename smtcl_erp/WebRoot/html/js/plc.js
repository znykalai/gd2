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
		        return item = null;
			});
			var af = {
				//获取	
				action:function(fun){
					var b=$("table[name='GW'] tbody tr").remove();
					var a = $.ajax({
						url: getRootPath()+'/PLCAction.do?operType=getGw',
						type: 'get',
						cache:false,
						success: function (data) {
							var obj = eval("("+data+")");
							return fun(obj),obj=null;
						}
					});
					return a=null,b=null;
				},
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
				showHtml:function(obj){
					var j=0;
					while(j<obj.data.length){
						var i=0;
						var map = obj.data[j];
						while(i<map.A_LIST.length){
							if(map.A_LIST[i].A.indexOf('-')==-1&&map.A_LIST[i].A!="boolContent"){
								$('#A_GW'+j+' tbody').append('<tr name="newTr" GW="'+j+'" bgcolor="#ffffff" style="height:20px;">' +
									//项目
									'<td id="newTd1_'+j+'_'+i+'" title="'+map.A_LIST[i].A+'" style="width:100px;font-size:12px;">' +
										(map.A_LIST[i].A.length>6?map.A_LIST[i].A.substring(0,7)+'...':map.A_LIST[i].A) +
									'</td>' +
									//列队1
									'<td id="newTd2_'+j+'_'+i+'" oldValue="'+map.A_LIST[i].B+'" style="width:50px;font-size:12px;">'+map.A_LIST[i].B+'</td>' +
									//列队2
									'<td id="newTd3_'+j+'_'+i+'" oldValue="'+map.A_LIST[i].C+'"  style="width:50px;font-size:12px;">'+map.A_LIST[i].C+'</td>' +
								'</tr>');
								$('#newTd2_'+j+'_'+i).dblclick(function(){
									if($(this).text()=="true"){
										$(this).html("false");
									}else if($(this).text()=="false"){
										$(this).html("true");
									}
									var row = $(this).attr("id").split("_");
									var data = {
										cm:'firstST',
										gwType:"A",
										gw:$(this).parent().attr("GW"),
										name:$('#newTd1_'+row[1]+'_'+row[2]).attr("title"),
										value:$(this).text(),
										oldValue:$(this).attr('oldValue')
									};
									var b=af.updateAction(data,function(){
										var c = af.action(function(e){af.showHtml(e);});
										return c=null,data=null,row=null;
									});
									return b=null;
								});
							}
							i++;
						}
						$("#BOOL"+j+"_A").val(map.A_BOLL);
						$("#DIZHI"+j+"_A").val(map.A_SDZ);
						j++,i=null,map=null;
					}
					//A区输送线
					if(obj.A_SSX.length>0){
						j=0;
						$("div[name='ssx']").attr("class","plc_gongwei_hui");
						while(j<obj.A_SSX.length){
							try{
								$("#ssx"+j).html(obj.A_SSX[j][j]);
								$("#ssx"+j).attr("class","plc_gongwei_lan");
							}catch (e) {
								alert(e);
							}
							j++;
						}
					}
					return j=null;
				},
				getTableAGW:function(fun){
					var a = this.action(function(obj){
						af.showHtml(obj);
					});
					return a=null,fun();
				},	
				load:function(fun){
					var win = layer.open({type:3});
					var a = this.getTableAGW(function(){
						return layer.close(win),win=null;
					});
					//刷新按钮事件绑定
					$("#shuxin").click(function(){
						var win = layer.open({type:3});
						var a = af.getTableAGW(function(){
							return layer.close(win),win=null;
						});
					});
					return fun(),a=null;
				}
			};
			return af.load(function(){hdFun();});
		}catch (e) {
			return e;
		}
		return null;
	}
};