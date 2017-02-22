var readyShow = {
	load:function(){
		try{
			var af = {
				load:function(){
					var winHeight = document.body.clientHeight;
					if(winHeight == window.screen.height){
						winHeight = document.body.clientHeight - 50;
					}
					$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
					$('.table-body').css('height', document.body.clientHeight / 3.5);
					af.table.load();
					return null;
				},
				/**
				 * 渲染表格
				 */
				table:{
					load:function(){
						//获取输送线 库房状态
						$.ajax({
							url: getRootPath()+'/KuFangAction.do?operType=getHckzddl',
							type: 'get',
							cache:false,
							success: function (data) {
								var obj = eval("("+data+")");
								af.table.hckzdd(obj.byjgzddl);
								return null;
							}
						});
						return null;
					},
					//缓存库指定队列
					hckzdd:function(e){
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
								'<td style="width: 50px;white-space:nowrap;overflow:hidden;">'+e[i].fasongshijian+'</td>' +
								//完成时间
								'<td style="width: 50px;white-space:nowrap;overflow:hidden;">'+e[i].wanchengshijian+'</td>' +
							'</tr>');
						}
						return null;
					}
				}
			};
			af.load();
		}catch (e) {
			return e;
		}
		return null;
	}
};