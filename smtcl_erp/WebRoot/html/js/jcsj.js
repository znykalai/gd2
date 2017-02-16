var readyShow = {
	//物料维护显示
	show_wuliao:function(){
		if($("#wuliao").css("display")=="block"){
			return null;
		}
		if($("#mozu").css("display")=="block"){
			$("#mozu").fadeOut(0,function(){
				$("#wuliao").fadeIn(0);
			});
		}else if($("#pack").css("display")=="block"){
			$("#pack").fadeOut(0,function(){
				$("#wuliao").fadeIn(0);
			});
		}
		return null;
	},
	//模组维护显示
	show_mozu:function(){
		if($("#mozu").css("display")=="block"){
			return null;
		}
		if($("#wuliao").css("display")=="block"){
			$("#wuliao").fadeOut(0,function(){
				$("#mozu").fadeIn(0);
			});
		}else if($("#pack").css("display")=="block"){
			$("#pack").fadeOut(0,function(){
				$("#mozu").fadeIn(0);
			});
		}
		return null;
	},
	//pack显示
	show_pack:function(){
		if($("#pack").css("display")=="block"){
			return null;	
		}
		if($("#wuliao").css("display")=="block"){
			$("#wuliao").fadeOut(0,function(){
				$("#pack").fadeIn(0);
			});
		}else if($("#mozu").css("display")=="block"){
			$("#mozu").fadeOut(0,function(){
				$("#pack").fadeIn(0);
			});
		}
		return null;
	},
	//加载主题
	load:function(){
		try{
			layer.config({
			    path: '../../js/lib/layer/'
			});
			var winHeight = document.body.clientHeight;
			if(winHeight == window.screen.height){
				winHeight = document.body.clientHeight - 50;
			}
			$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/1.05);
		/*-------------------------------------物料------------------------------------------*/
			//设置默认时间
		    $('#wl_newDate').val(current());
			//tab切换
			$('.mainOrder').click(function (e) {
			    var item = $(e.target);
			    $('#mainStatusBar').find('div').removeClass('select');
			    item.addClass('select');
			    $(".stock").children().css("display", "none");
			    $("#stock" + $(item).attr('data')).css("display", "block");
			});
			
			//单位复选框加载
			$('#danwei_name').click(function () {
		        $('#danwei_name ul').toggle();
		    });
		    $('#danwei_name ul').click(function (event) {
		        var t2 = $(event.target).text();
		        $('#danwei_id').val(t2);
		    });
			//类别复选框加载
			$('#leibie_name').click(function () {
		        $('#leibie_name ul').toggle();
		    });
		    $('#leibie_name ul').click(function (event) {
		        var t2 = $(event.target).text();
		        $('#leibie_id').val(t2);
		    });
			//类型复选框加载
			$('#leixing_name').click(function () {
		        $('#leixing_name ul').toggle();
		    });
		    $('#leixing_name ul').click(function (event) {
		        var t2 = $(event.target).text();
		        $('#leixing_id').val(t2);
		    });
			//托盘类别复选框加载
			$('#tuopanleibie_name').click(function () {
		        $('#tuopanleibie_name ul').toggle();
		    });
		    $('#tuopanleibie_name ul').click(function (event) {
		        var t2 = $(event.target).text();
		        $('#tuopanleibie_id').val(t2);
		    });
		    
		    //失效复选框加载
			$('#shixiao').click(function () {
		        $('#shixiao ul').toggle();
		    });
		    $('#shixiao ul').click(function (event) {
		        var t2 = $(event.target).text();
		        $('#shixiao_id').val(t2);
		    });
		    //新建
		    $("#wl_newBtn").click(function(){
		    	$('#wl_form')[0].reset();
				//设置默认时间
			    $('#wl_newDate').val(current());
		   		$('#wuliao_code').attr("readOnly",false);
		    });
		    //保存
		    $("#wl_saveBtn").click(function(){
		    	//物料编码不可为空！
		    	if($("#wuliao_code").val()==""){
		    	 	$("#wuliao_code").focus();
		    		layer.tips('请填写物料编码！', '#wuliao_code');
		    		return null;
		    	}
		    	//物料描述不可为空！
		    	if($("#wuliao_miaoshu").val()==""){
		    	 	$("#wuliao_miaoshu").focus();
		    		layer.tips('请填写物料描述！', '#wuliao_miaoshu');
		    		return null;
		    	}
		    	var upload = {
		    		wuliao_code  		: $("#wuliao_code").val(),			//物料编码
		    		wl_newDate   		: $("#wl_newDate").val(),			//新建时间
		    		wuliao_miaoshu		: $("#wuliao_miaoshu").val(),		//物料描述
		    		dier_code			: $("#dier_code").val(),			//第二编码
		    		plc_code			: $("#plc_code").val(),				//plc编码
		    		danwei_id			: $("#danwei_id").val(),			//单位
		    		leibie_id			: $("#leibie_id").val(),			//类别
		    		leixing_id			: $("#leixing_id").val(),			//类型
		    		huiliufazhi_id		: $("#huiliufazhi_id").val(),		//回流阀值
		    		zhuangzaicanshu_id	: $("#zhuangzaicanshu_id").val(),	//装载参数
		    		tuopanleibie_id		: $("#tuopanleibie_id").val(),		//托盘类别
		    		shixiao_id			: $("#shixiao_id").val(),			//是否失效
		    		shanghuoqu_id		: $("#shanghuoqu_id").val(),		//默认上货区
		    		xiahuoqu_id			: $("#xiahuoqu_id").val()			//默认下货区
		    	};
		    	$.ajax({
				    url: getRootPath()+'/BaseDataAction.do?operType=saveWl',
				    type: 'get',
				    data: upload,cache:false, 
				    success: function (data) {
				    	var obj = eval("("+data+")");
						if(obj.success){
							layer.msg('物料'+upload.wuliao_code+'，保存成功！');
		   					$('#wuliao_code').attr("readOnly",true);
						}else{
							layer.msg('物料'+upload.wuliao_code+'，保存失败！');
						};
					}
				});
		    });
		    //删除
		    $("#wl_deleteBtn").click(function(){
		    	//物料编码不可为空！
		    	if($("#wuliao_code").val()==""){
		    		return null;
		    	}else{
					var lay = layer.confirm('是否确认删除此物料？', {
					    btn: ['删除行', '取消'] //按钮
					}, function () {
						$.ajax({
						    url: getRootPath()+'/BaseDataAction.do?operType=deleteWl',
						    type: 'get',cache:false, 
						    data: {wuliao_code:$("#wuliao_code").val()},
						    success: function (data) {
						    	var obj = eval("("+data+")");
								if(obj.success){
									layer.msg('物料'+$("#wuliao_code").val()+'，删除成功！');
							    	$('#wl_form')[0].reset();
									//设置默认时间
								    $('#wl_newDate').val(current());
								}else{
									layer.msg('物料'+upload.wuliao_code+'，删除失败！');
								};
							}
						});
					});
		    	}
		    });
		    //查询
		    $("#wl_selectBtn").click(function(){
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
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=selectWlList',
					type: 'get',
					data: '',cache:false, 
					success: function (data) {
				    	var obj = eval("("+data+")");
						for(var i=0;i<obj.length;i++){
			                $('#searchTable').append('<tr class="commonTr" style="cursor: pointer;"></tr>');
			               	var lastTr = $('#searchTable tbody tr:last');
		                    lastTr.append('<td class="wuliao_code" style="width: 50%;">' + obj[i].wuliao_code + '</td>');
		                    lastTr.append('<td class="wuliao_miaoshu" style="width: 50%;">' + obj[i].wuliao_miaoshu + '</td>');
		                    lastTr.append('<td class="leibie_id" style="display: none;">' + obj[i].leibie_id + '</td>');
		                    lastTr.append('<td class="leixing_id" style="display: none;">' + obj[i].leixing_id + '</td>');
		                    lastTr.append('<td class="danwei_id" style="display: none;">' + obj[i].danwei_id + '</td>');
		                    lastTr.append('<td class="wl_newDate" style="display: none;">' + obj[i].wl_newDate + '</td>');
		                    lastTr.append('<td class="shixiao_id" style="display: none;">' + obj[i].shixiao_id + '</td>');
		                    lastTr.append('<td class="tuopanleibie_id" style="display: none;">' + obj[i].tuopanleibie_id + '</td>');
		                    lastTr.append('<td class="zhuangzaicanshu_id" style="display: none;">' + obj[i].zhuangzaicanshu_id + '</td>');
		                    lastTr.append('<td class="huiliufazhi_id" style="display: none;">' + obj[i].huiliufazhi_id + '</td>');
		                    lastTr.append('<td class="dier_code" style="display: none;">' + obj[i].dier_code + '</td>');
		                    lastTr.append('<td class="plc_code" style="display: none;">' + obj[i].plc_code + '</td>');
		                    lastTr.append('<td class="shanghuoqu_id" style="display: none;">' + obj[i].shanghuoqu_id + '</td>');
		                    lastTr.append('<td class="xiahuoqu_id" style="display: none;">' + obj[i].xiahuoqu_id + '</td>');
						}
						/*绑定双击事件*/
		            	$('.commonTr').bind('dblclick', function (e) {
							var tr = $(e.target).parent();
		            		$('#wuliao_code').val(tr.find('.wuliao_code').text());
		            		$('#wuliao_miaoshu').val(tr.find('.wuliao_miaoshu').text());
		            		$('#leibie_id').val(tr.find('.leibie_id').text());
		            		$('#leixing_id').val(tr.find('.leixing_id').text());
		            		$('#danwei_id').val(tr.find('.danwei_id').text());
		            		$('#wl_newDate').val(tr.find('.wl_newDate').text());
		            		$('#shixiao_id').val(tr.find('.shixiao_id').text());
		            		$('#tuopanleibie_id').val(tr.find('.tuopanleibie_id').text());
		            		$('#zhuangzaicanshu_id').val(tr.find('.zhuangzaicanshu_id').text());
		            		$('#huiliufazhi_id').val(tr.find('.huiliufazhi_id').text());
		            		$('#dier_code').val(tr.find('.dier_code').text());
		            		$('#plc_code').val(tr.find('.plc_code').text());
		            		$('#shanghuoqu_id').val(tr.find('.shanghuoqu_id').text());
		            		$('#xiahuoqu_id').val(tr.find('.xiahuoqu_id').text());
		            		$('#wuliao_code').attr("readOnly",true);
		            		layer.close(win);
		            	});
					}
				});
		    });
			
			/***************************************模组*******************************************/
			//设置默认时间
		    $('#mozu_newDate').val(current());
			$('.table-body').css('height', document.body.clientHeight / 6);
			
			//根据模组编码查询数据
			$("#mozu_code").change(function(){
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=getMzList',
					type: 'get',cache:false, 
					data: 'type=head&where= where a.模组编码 = \''+this.value+'\'',
					success: function (data) {
				    	var obj = eval("("+data+")");
		           		$('#mozu_id').val(obj[0].mozu_id);
		           		$('#mozu_code').val(obj[0].mozu_code);
		           		$('#mozu_leixing').val(obj[0].mozu_leixing);
		           		$('#mozu_dianxinleixing').val(obj[0].mozu_dianxinleixing);
		           		$('#mozu_gongweileibie').val(obj[0].mozu_gongweileibie);
		           		$('#mozu_xingqiangshu').val(obj[0].mozu_xingqiangshu);
		           		$('#mozu_newDate').val(obj[0].mozu_newDate);
		           		$('#mozu_code').attr("readOnly",true);
		           		showMzzj(obj[0].mozu_id);
					}
				});
			});
		    
			//添加载具行
			function addMzZjRow(obj){
				var typeClass = "";
				if(obj.mozu_id==""){
					typeClass = "add";
				}
				$('#mz_zj_table tbody').append('<tr name="newTr" bgcolor="#ffffff" class="'+typeClass+'" style="height: 28px;">' +
					//复选框
					'<td id="newTd1_' + obj.num + '" style="width: 3%;padding:0px;"> ' +
						'<input type="radio" id="trRadio_zj' + obj.num + '" name="trRadio_zj" value="'+obj.zj_id+'"/>' +
						'<input type="hidden" id="zj_id' + obj.num + '" value="'+obj.zj_id+'"/>' +
						'<input type="hidden" id="mozu_id' + obj.num + '" value="'+obj.mozu_id+'"/>' +
					'</td>' +
					//序号
					'<td id="newTd2_' + obj.num + '" style="width: 5%;padding:0px;">'+obj.num+'</td>' +
					//翻面否
					'<td id="newTd3_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zj_fanmianfou+'</td>' +
					//叠装否
					'<td id="newTd4_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zj_diezhuangfou+'</td>' +
					//电芯数
					'<td id="newTd5_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zj_dianxinshu+'</td>' +
					//电芯1
					'<td id="newTd6_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_dianxin1+'</td>' +
					//电芯2
					'<td id="newTd7_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_dianxin2+'</td>'+
					//电芯3
					'<td id="newTd8_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_dianxin3+'</td>'+
					//电芯4
					'<td id="newTd9_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_dianxin4+'</td>'+
					//有效型腔
					'<td id="newTd10_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zj_youxiaoxingqiang+'</td>'+
					//假电芯1
					'<td id="newTd11_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_jiadianxin1+'</td>'+
					//假电芯2
					'<td id="newTd12_' + obj.num + '" style="width: 8%;padding:0px;">'+obj.zj_jiadianxin2+'</td>'+
				'</tr>');
				//单选框
				$("#newTd1_" + obj.num).click(function(){
					var mz_zlh_table = $('#mz_zlh_table tbody tr');
					var showZlh = false;
					for(var i=0;i<mz_zlh_table.length;i++){
			            if ($(mz_zlh_table).parent().children("tr").eq(i).attr("class") == 'add' || 
			            	$(mz_zlh_table).parent().children("tr").eq(i).attr("class") == "update") {
							var lay = layer.confirm('指令行未保存,是否继续？', {
						        btn: ['确定', '取消'] //按钮
						    }, function (){
								$("#newTd1_" + obj.num).find("input:radio").prop("checked",true);
								showZhlRow($("#mozu_id"+obj.num).val(),$("#zj_id"+obj.num).val());
								zlhNum = 1;
								layer.close(lay);
						    });
						    return null;
		            	}
					}
					$("#newTd1_" + obj.num).find("input:radio").prop("checked",true);
					showZhlRow($("#mozu_id"+obj.num).val(),$("#zj_id"+obj.num).val());
					zlhNum = 1;
				});
				//翻面否
				$('#newTd3_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck ){
						return null;
					}else{
						this.ck = true;
					}
					var optionHtml = "<option></option><option>是</option><option>否</option>";
					if($(this).html()=="是"){
						optionHtml = "<option></option><option selected>是</option><option>否</option>";
					}else if($(this).html()=="否"){
						optionHtml = "<option></option><option>是</option><option selected>否</option>";
					}
					$(this).html('<select class="selectpicker"  id="newTd3_text_' + row + '" style="width:100%;height:100%;">'+optionHtml+'</select>');
					$('#newTd3_text_' + row).focus();
			        $('#newTd3_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd3_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//叠装否
				$('#newTd4_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					var optionHtml = "<option></option><option>是</option><option>否</option>";
					if($(this).html()=="是"){
						optionHtml = "<option></option><option selected>是</option><option>否</option>";
					}else if($(this).html()=="否"){
						optionHtml = "<option></option><option>是</option><option selected>否</option>";
					}
					$(this).html('<select class="selectpicker"  id="newTd4_text_' + row + '" style="width:100%;height:100%;">'+optionHtml+'</select>');
					$('#newTd4_text_' + row).focus();
			        $('#newTd4_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd4_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//电芯数
				$('#newTd5_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd5_text_' + row + '" type="number" min="0" class="form-control" value="' + $(this).html() + '">');
					$('#newTd5_text_' + row).focus();
			        $('#newTd5_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd5_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//电芯1
				$('#newTd6_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd6_text_' + row + '" type="text" min="1" class="form-control" value="' + $(this).html() + '">');
					$('#newTd6_text_' + row).focus();
			        $('#newTd6_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd6_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//电芯2
				$('#newTd7_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd7_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#newTd7_text_' + row).focus();
			        $('#newTd7_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd7_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//电芯3
				$('#newTd8_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd8_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#newTd8_text_' + row).focus();
			        $('#newTd8_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd8_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//电芯4
				$('#newTd9_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd9_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#newTd9_text_' + row).focus();
			        $('#newTd9_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd9_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//有效型腔
				$('#newTd10_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd10_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#newTd10_text_' + row).focus();
			        $('#newTd10_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd9_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//假电芯1
				$('#newTd11_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd11_text_' + row + '" type="number" class="form-control" value="' + $(this).html() + '">');
					$('#newTd11_text_' + row).focus();
			        $('#newTd11_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd11_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//假电芯2
				$('#newTd12_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="newTd12_text_' + row + '" type="number" class="form-control" value="' + $(this).html() + '">');
					$('#newTd12_text_' + row).focus();
			        $('#newTd12_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#newTd12_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
			}
			
			var num = 1;//序号
			$('#mz_zj_addTr').click(function(){
				//模组编码不允许为空
				if($("#mozu_code").val()==""){
		    	 	$("#mozu_code").focus();
		    		layer.tips('请填写模组编码！', '#mozu_code');
		    		return null;
				}
				//模组类型不允许为空
				if($("#mozu_leixing").val()==""){
		    	 	$("#mozu_leixing").focus();
		    		layer.tips('请填写模组类型！', '#mozu_leixing');
		    		return null;
				}
				//电芯类型不允许为空
				if($("#mozu_dianxinleixing").val()==""){
		    	 	$("#mozu_dianxinleixing").focus();
		    		layer.tips('请填写电芯类型！', '#mozu_dianxinleixing');
		    		return null;
				}
				var mz_zj_table = $('#mz_zj_table tbody tr');
				if(mz_zj_table.length > 0){
					num = Number(mz_zj_table.eq(mz_zj_table.length-1).children("td").eq(1).html()) + 1;
				}
				var map = {
					num  : num,
					type : 'add',
					mozu_id : '',
					zj_id	: '',
					zj_xuhao: '',
					zj_fanmianfou: '',
					zj_diezhuangfou: '',
					zj_dianxinshu: '',
					zj_dianxin1: '',
					zj_dianxin2: '',
					zj_dianxin3: '',
					zj_dianxin4: '',
					zj_youxiaoxingqiang: '',
					zj_jiadianxin1: '',
					zj_jiadianxin2: ''
				};
				addMzZjRow(map);
				num++;
			});
			
			//显示模组载具行
			function showMzzj(mozu_id){
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=getMzList',
					type: 'get',cache:false, 
					data: 'type=zjRow&mozu_id='+mozu_id,
					success: function (zj_data) {
		  				var zj_obj = eval("("+zj_data+")");
						$('#mz_zj_table tbody tr').remove();
						$('#mz_zlh_table tbody tr').remove();
		  				if(zj_obj.length > 0){
			  				for(var i=0;i < zj_obj.length;i++){
			  					var map = {
								type : 'select',
			  						num : zj_obj[i].zj_xuhao,
			  						mozu_id	: mozu_id,
			  						zj_id	: zj_obj[i].zj_id,
			  						zj_xuhao: zj_obj[i].zj_xuhao,
			  						zj_fanmianfou: zj_obj[i].zj_fanmianfou,
			  						zj_diezhuangfou: zj_obj[i].zj_diezhuangfou,
			  						zj_dianxinshu: zj_obj[i].zj_dianxinshu,
			  						zj_dianxin1: zj_obj[i].zj_dianxin1,
			  						zj_dianxin2: zj_obj[i].zj_dianxin2,
			  						zj_dianxin3: zj_obj[i].zj_dianxin3,
			  						zj_dianxin4: zj_obj[i].zj_dianxin4,
			  						zj_youxiaoxingqiang: zj_obj[i].zj_youxiaoxingqiang,
			  						zj_jiadianxin1: zj_obj[i].zj_jiadianxin1,
			  						zj_jiadianxin2: zj_obj[i].zj_jiadianxin2
			  					};
			  					addMzZjRow(map);
			  				}
			  				//默认选择第一条载具行
							$("#newTd1_" + zj_obj[0].zj_xuhao).find("input:radio").prop("checked",true);
							$("#newTd1_" + zj_obj[0].zj_xuhao).click();
						}else{
							num = 1;
						}
					}
				});
			}
			
			//查询模组
		    $("#mz_selectBtn").click(function(){
				var openWindow = '<div style="width: 100%;margin-right: 0;">' +
						'<div class="margin-top-10">' +
							'<div class="col-md-11" style="margin-left: 20px;">' +
							'<!-- 标题 -->' +
							'<div style="padding-right:17px;">' +
							'<table class="table table-bordered text-center" id="commonSearchTableHead">' +
							'<thead>' +
							'<tr>' +
							'<td style="width: 50%;">模组编号</td>' +
							'<td style="width: 50%;">模组类型</td>' +
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
				    title: '模组查询',
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
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=getMzList',
					type: 'get',cache:false, 
					data: 'type=head',
					success: function (data) {
				    	var obj = eval("("+data+")");
						for(var i=0;i<obj.length;i++){
			                $('#searchTable').append('<tr class="commonTr" style="cursor: pointer;"></tr>');
			               	var lastTr = $('#searchTable tbody tr:last');
		                    lastTr.append('<td class="mozu_code" style="width: 50%;">' + obj[i].mozu_code + '</td>');
		                    lastTr.append('<td class="mozu_leixing" style="width: 50%;">' + obj[i].mozu_leixing + '</td>');
		                    lastTr.append('<td class="mozu_id" style="display: none;">' + obj[i].mozu_id + '</td>');
		                    lastTr.append('<td class="mozu_dianxinleixing" style="display: none;">' + obj[i].mozu_dianxinleixing + '</td>');
		                    lastTr.append('<td class="mozu_gongweileibie" style="display: none;">' + obj[i].mozu_gongweileibie + '</td>');
		                    lastTr.append('<td class="mozu_xingqiangshu" style="display: none;">' + obj[i].mozu_xingqiangshu + '</td>');
		                    lastTr.append('<td class="mozu_newDate" style="display: none;">' + obj[i].mozu_newDate + '</td>');
						}
						/*绑定双击事件*/
		            	$('.commonTr').bind('dblclick', function (e) {
							var tr = $(e.target).parent();
		            		$('#mozu_id').val(tr.find('.mozu_id').text());
		            		$('#mozu_code').val(tr.find('.mozu_code').text());
		            		$('#mozu_leixing').val(tr.find('.mozu_leixing').text());
		            		$('#mozu_dianxinleixing').val(tr.find('.mozu_dianxinleixing').text());
		            		$('#mozu_gongweileibie').val(tr.find('.mozu_gongweileibie').text());
		            		$('#mozu_xingqiangshu').val(tr.find('.mozu_xingqiangshu').text());
		            		$('#mozu_newDate').val(tr.find('.mozu_newDate').text());
		            		$('#mozu_code').attr("readOnly",true);
							showMzzj(tr.find('.mozu_id').text());//显示模组载具行
							zlhNum = 1;
		            		layer.close(win);
		            	});
					}
				});
		    });
			
			//新建模组事件
			$('#mz_newBtn').click(function(){
				num = 1;
				zlhNum = 1;
		    	$('#mz_form')[0].reset();
				//设置默认时间
		    	$('#mozu_newDate').val(current());
		   		$('#mozu_code').attr("readOnly",false);
				$('#mz_zj_table tbody tr').remove();
				$('#mz_zlh_table tbody tr').remove();
			});
			
			//载具行上调
			$("#mz_zj_up").click(function(){
				var cheBoolean = $("input[name='trRadio_zj']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
					var mz_zj_table = $('#mz_zj_table tbody tr');//载具table
					var zj_xuhao = $("input[name='trRadio_zj']:checked").parent().parent().children("td").eq(1).html();
					if(mz_zj_table.length==1){
						layer.msg("当前只有一行载具，无需调整！");
					}else if(mz_zj_table.length > 1){
						var row = mz_zj_table.eq(0).find('td').eq(1).text();
						if(zj_xuhao==row){
							layer.msg("此载具已经是第一行，无需向上调整！");
						}else{
							var zj_id_up = $("#mz_zj_table tbody").children("tr").eq(zj_xuhao-2).children("td").eq(0).find("input:radio").val();
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=mz_zj_up',
								type: 'post',cache:false, 
								data: 'mozu_id='+mozu_id+'&zj_id='+zj_id+'&zj_xuhao='+zj_xuhao+'&zj_id_up='+zj_id_up,
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("上调成功！");
		                    			showMzzj(mozu_id);//显示模组载具行
						  			}else{
						  				layer.msg("上调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要上调的载具行！");
				}
			});
			
			//载具行下调
			$("#mz_zj_bottom").click(function(){
				var cheBoolean = $("input[name='trRadio_zj']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
					var mz_zj_table = $('#mz_zj_table tbody tr');//载具table
					var zj_xuhao = $("input[name='trRadio_zj']:checked").parent().parent().children("td").eq(1).html();
					if(mz_zj_table.length==1){
						layer.msg("当前只有一行载具，无需调整！");
					}else if(mz_zj_table.length > 1){
						var row = mz_zj_table.eq(mz_zj_table.length-1).find('td').eq(1).text();
						if(zj_xuhao==row){
							layer.msg("此载具已经是最后一行，无需向下调整！");
						}else{
							var zj_id_up = $("#mz_zj_table tbody").children("tr").eq(zj_xuhao-2).children("td").eq(0).find("input:radio").val();
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=mz_zj_bottom',
								type: 'post',cache:false, 
								data: 'mozu_id='+mozu_id+'&zj_id='+zj_id+'&zj_xuhao='+zj_xuhao+'&zj_id_up='+zj_id_up,
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("下调成功！");
		                    			showMzzj(mozu_id);//显示模组载具行
						  			}else{
						  				layer.msg("下调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要下调的载具行！");
				}
			});
			
			//模组-载具保存
			$("#mz_zj_saveBtn").click(function(){
				var head = {
					mozu_code 			: $("#mozu_code").val(),
					mozu_leixing		: $("#mozu_leixing").val(),
					mozu_dianxinleixing	: $("#mozu_dianxinleixing").val(),
					mozu_gongweileibie	: $("#mozu_gongweileibie").val(),
					mozu_xingqiangshu	: $("#mozu_xingqiangshu").val(),
					mozu_newDate		: $("#mozu_newDate").val()
				};
				//模组编码不允许为空
				if(head.mozu_code==""){
		    	 	$("#mozu_code").focus();
		    		layer.tips('请填写模组编码！', '#mozu_code');
		    		return null;
				}
				//模组类型不允许为空
				if(head.mozu_leixing==""){
		    	 	$("#mozu_leixing").focus();
		    		layer.tips('请填写模组类型！', '#mozu_leixing');
		    		return null;
				}
				//电芯类型不允许为空
				if(head.mozu_dianxinleixing==""){
		    	 	$("#mozu_dianxinleixing").focus();
		    		layer.tips('请填写电芯类型！', '#mozu_dianxinleixing');
		    		return null;
				}
				//新增
				var add = new Array;
				//修改
				var update = new Array;
				//载具行
				var mz_zj_table = $('#mz_zj_table tbody tr');
				for(var i=0; i < mz_zj_table.length; i++){
					var zjRowMap = {
						zj_id	: $("#zj_id"+mz_zj_table.eq(i).children("td").eq(1).html()).val(),
						mozu_xuhao : mz_zj_table.eq(i).children("td").eq(1).html(),
						mozu_fanmianfou : mz_zj_table.eq(i).children("td").eq(2).html(),
						mozu_diezhuangfou : mz_zj_table.eq(i).children("td").eq(3).html(),
						mozu_dianxinshu : mz_zj_table.eq(i).children("td").eq(4).html()==''?0:mz_zj_table.eq(i).children("td").eq(4).html(),
						mozu_dianxin1 : mz_zj_table.eq(i).children("td").eq(5).html(),
						mozu_dianxin2 : mz_zj_table.eq(i).children("td").eq(6).html(),
						mozu_dianxin3 : mz_zj_table.eq(i).children("td").eq(7).html(),
						mozu_dianxin4 : mz_zj_table.eq(i).children("td").eq(8).html(),
						mozu_youxiaoxingqiang : mz_zj_table.eq(i).children("td").eq(9).html()==''?0:mz_zj_table.eq(i).children("td").eq(9).html(),
						mozu_jiadianxin1 : mz_zj_table.eq(i).children("td").eq(10).html()==''?0:mz_zj_table.eq(i).children("td").eq(10).html(),
						mozu_jiadianxin2 : mz_zj_table.eq(i).children("td").eq(11).html()==''?0:mz_zj_table.eq(i).children("td").eq(11).html()
					};
					//判断当前行,修改还是新增
		            if ($(mz_zj_table).parent().children("tr").eq(i).attr("class") == 'add') {
		                add.push(zjRowMap);//新增
		            } else if ($(mz_zj_table).parent().children("tr").eq(i).attr("class") == "update") {
		                update.push(zjRowMap);//修改数据
		            }
				}
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=saveMz',
					type: 'post',cache:false, 
					data: 'head='+JSON.stringify(head)+'&add='+JSON.stringify(add)+'&update='+JSON.stringify(update),
					success: function (data) {
			  			var obj = eval("("+data+")");
			  			if(obj.success){
		                    //将所有行重置class变为老数据
		                    mz_zj_table.attr("class", "");
		                    //模组编码不可编辑
		                    $('#mozu_code').attr("readonly", "true");
		                    $("#mozu_id").val(obj.mz_id);
		                    showMzzj(obj.mz_id);//显示模组载具行
			  				layer.msg("保存模组成功！");
			  			}
					}
				});
			});
			//模组指令行显示
		    function showZhlRow(mozu_id,zj_id){
		    	$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=getMzList',
					type: 'get',cache:false, 
					data: 'type=zlhRow&mozu_id='+mozu_id+'&zj_id='+zj_id,
					success: function (zlh_data) {
		  				var zlh_obj = eval("("+zlh_data+")");
						$('#mz_zlh_table tbody tr').remove();
		  				for(var i=0;i < zlh_obj.length;i++){
		  					var map = {
		  						mozu_id	: mozu_id,
		  						zj_id	: zj_id,
		  						num : zlh_obj[i].zlh_xuhao,
		  						zlh_xuhao: zlh_obj[i].zlh_xuhao,
		  						zlh_wuliao: zlh_obj[i].zlh_wuliao,
		  						zlh_wuliaomiaoshu: zlh_obj[i].zlh_wuliaomiaoshu,
		  						zlh_shuliang: zlh_obj[i].zlh_shuliang,
		  						zlh_gongwei: zlh_obj[i].zlh_gongwei
		  					};
		  					addMzZlhRow(map);
		  					zlhNum = Number(map.num) + 1;
		  				}
					}
				});
		    }
			//添加指令行函数
			function addMzZlhRow(obj){
				var typeClass = "";
				if(obj.mozu_id==""&&obj.zj_id==""){
					typeClass = "add";
				}
				$('#mz_zlh_table tbody').append('<tr name="newTr" bgcolor="#ffffff" class="'+typeClass+'" style="height: 28px;">' +
					//复选框
					'<td id="zlhNewTd1_' + obj.num + '" style="width: 3%;padding:0px;"> ' +
						'<input type="radio" id="trRadio_zlh' + obj.num + '" name="trRadio_zlh" value="'+obj.num+'"/>' +
						'<input type="hidden" id="mozu_id' + obj.num + '" value="'+obj.mozu_id+'"/>' +
						'<input type="hidden" id="zj_id' + obj.num + '" value="'+obj.zj_id+'"/>' +
					'</td>' +
					//序号
					'<td id="zlhNewTd2_' + obj.num + '" style="width: 5%;padding:0px;">'+obj.num+'</td>' +
					//物料
					'<td id="zlhNewTd3_' + obj.num + '" style="width: 25%;padding:0px;">'+obj.zlh_wuliao+'</td>' +
					//物料描述
					'<td id="zlhNewTd4_' + obj.num + '" style="width: 35%;padding:0px;">'+obj.zlh_wuliaomiaoshu+'</td>' +
					//数量
					'<td id="zlhNewTd5_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zlh_shuliang+'</td>' +
					//工位
					'<td id="zlhNewTd6_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.zlh_gongwei+'</td>' +
				'</tr>');
				//单选框
				$("#zlhNewTd1_" + obj.num).click(function(){
					$("#zlhNewTd1_" + obj.num).find("input:radio").prop("checked",true);
				});
				//物料编码
				$('#zlhNewTd3_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="zlhNewTd3_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#zlhNewTd3_text_' + row).focus();
			        $('#zlhNewTd3_text_' + row).blur(function (){
			            var node = this.parentNode;
			            var e = {id:this.value};
			            if(e.id==""){
			            	$(node).html("");
			            	ck.ck?ck.ck=false:null;
			            	return null;
			            }
			        	$.ajax({
							url: getRootPath()+'/BaseDataAction.do?operType=selectWlList',
							type: 'get',cache:false, 
							data: e,
							success: function (data) {
				    			var obj = eval("("+data+")");
								if(obj.length > 0){
						            $(node).html(e.id);
						            $('#zlhNewTd4_'+row).html(obj[0].wuliao_miaoshu);
						            ck.ck?ck.ck=false:null;
								}else{
						            $(node).html("");
			            			ck.ck?ck.ck=false:null;
						    		layer.tips(e.id+'物料不存在！', '#zlhNewTd3_' + row);
								}
							}
						});
			        });
		            //判断当前行 修改还是新增
		            $('#zlhNewTd3_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//数量
				$('#zlhNewTd5_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="zlhNewTd5_text_' + row + '" type="number" class="form-control" value="' + $(this).html() + '">');
					$('#zlhNewTd5_text_' + row).focus();
			        $('#zlhNewTd5_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#zlhNewTd5_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//工位
				$('#zlhNewTd6_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="zlhNewTd6_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#zlhNewTd6_text_' + row).focus();
			        $('#zlhNewTd6_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#zlhNewTd6_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
			};
			
			//添加指令行
			var zlhNum = 1;
			$("#mz_zlh_addTr").click(function(){
				var zj_id = $("input[name='trRadio_zj']:checked").val();
				if($("#mozu_id").val()==""){
					layer.msg("没有可用模组，无法添加指令行！");
					return null;
				}
				if(zj_id==undefined){
					layer.msg('请选择有效载具行！');
					return null;
				}
				if(zj_id==""){
					layer.msg('请先保存载具，否则无效！');
					return null;
				}
				var map = {
					mozu_id : '',
					zj_id : '',
					num : zlhNum,
					zlh_wuliao : '',
					zlh_wuliaomiaoshu : '',
					zlh_shuliang : '',
					zlh_gongwei : ''
				};
				addMzZlhRow(map);
				zlhNum++;
			});
			
			//保存指令行
			$("#mz_zlh_saveBtn").click(function(){
				var cheBoolean = $("input[name='trRadio_zj']").is(':checked');
				if (!cheBoolean) {
					return null;
				}
				var mozu_id = $("#mozu_id").val();//模组ID
				var zj_id = $("input[name='trRadio_zj']:checked").val();//载具ID
				var add = new Array;//新增
				var update = new Array;//修改
				var mz_zlh_table = $('#mz_zlh_table tbody tr');//指令行
				for(var i=0; i < mz_zlh_table.length; i++){
					var zjRowMap = {
						mozu_id	: mozu_id,
						zj_id	: zj_id,
						zlh_xuhao : mz_zlh_table.eq(i).children("td").eq(1).html(),
						zlh_wuliao : mz_zlh_table.eq(i).children("td").eq(2).html(),
						zlh_wuliaomiaoshu : mz_zlh_table.eq(i).children("td").eq(3).html(),
						zlh_shuliang : mz_zlh_table.eq(i).children("td").eq(4).html()==''?0:mz_zlh_table.eq(i).children("td").eq(4).html(),
						zlh_gongwei : mz_zlh_table.eq(i).children("td").eq(5).html()
					};
					if(mz_zlh_table.eq(i).children("td").eq(5).html()==""){
						mz_zlh_table.eq(i).children("td").eq(5).click();
			    		layer.tips('指令行工位不允许为空行！', '#zlhNewTd6_' + (i+1));
						return null;
					}
					//判断当前行,修改还是新增
		            if ($(mz_zlh_table).parent().children("tr").eq(i).attr("class") == 'add') {
		                add.push(zjRowMap);//新增数据
		            } else if ($(mz_zlh_table).parent().children("tr").eq(i).attr("class") == "update") {
		                update.push(zjRowMap);//修改数据
		            }
				}
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=saveZlh',
					type: 'post',cache:false, 
					data: 'add='+JSON.stringify(add)+'&update='+JSON.stringify(update),
					success: function (data) {
			  			var obj = eval("("+data+")");
			  			if(obj.success){
		                    mz_zlh_table.attr("class", ""); //将所有行重置class变为老数据
			  				layer.msg("保存指令行成功！");
		  					showZhlRow(mozu_id,zj_id);//显示模组指令行
		                }
		            }
				});
			});
			
			//删除模组函数
			function deleteMz(title,mozu_id,zj_id,zlh_xuhao){
				var lay = layer.confirm('是否确认删除'+title+'？', {
			        btn: ['确认', '取消'] //按钮
			    }, function () {
			        try {
						$.ajax({
							url: getRootPath()+'/BaseDataAction.do?operType=mz_delete',
							type: 'post',cache:false, 
							data: 'deleteType='+title+'&mozu_id='+mozu_id+'&zj_id='+zj_id+'&zlh_xuhao='+zlh_xuhao+'',
							success: function (data) {
					  			var obj = eval("("+data+")");
					  			if(obj.success){
					  				if(title=="模组"){
					  					$('#mz_newBtn').click();
					  				}else if(title=="载具行"){
										showMzzj(mozu_id);//显示模组载具行
					  				}else{
					  					showZhlRow(mozu_id,zj_id);//显示模组指令行
					  				}
					  				layer.msg("删除成功！");
				                }else{
									layer.msg("删除失败！");
				                }
				            }
						});
			        } catch (e) {
			            layer.msg("An exception occured in the script.Error name: " + e.name
			                + " script.Error message: " + e.message);
			        }
			    });
			};
			//删除模组
			$("#mz_deleteBtn").click(function(){
				var mozu_id = $("#mozu_id").val();
				if(mozu_id==""){
					layer.msg("请选择需要删除的模组！");
					return null;
				}
			    deleteMz('模组',mozu_id,'','');
			});
			//删除载具
			$("#mz_zj_deleteBtn").click(function(){
				var cheBoolean = $("input[name='trRadio_zj']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
			    	deleteMz('载具行',mozu_id,zj_id,'');
				}else{
					layer.msg("请选择需要删除的载具！");
				}
			});
			//删除指令行
			$("#mz_zlh_deleteBtn").click(function(){
				var cheBoolean = $("input[name='trRadio_zlh']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
					var zlh_xuhao = $("input[name='trRadio_zlh']:checked").val();
			    	deleteMz('指令行',mozu_id,zj_id,zlh_xuhao);
				}else{
					layer.msg("请选择需要删除的指令！");
				}
			});
			
			//指令行上调
			$("#mz_zlh_up").click(function(){
				var cheBoolean = $("input[name='trRadio_zlh']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
					var zlh_xuhao = $("input[name='trRadio_zlh']:checked").val();
					var mz_zlh_table = $('#mz_zlh_table tbody tr');//指令行
					if(mz_zlh_table.length==1){
						layer.msg("当前只有一条指令，无需调整！");
					}else if(mz_zlh_table.length > 1){
						var row = mz_zlh_table.eq(0).find('td').eq(1).text();
						if(zlh_xuhao==row){
							layer.msg("此指令已经是第一行，无需向上调整！");
						}else{
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=mz_zlh_up',
								type: 'post',cache:false, 
								data: 'mozu_id='+mozu_id+'&zj_id='+zj_id+'&zlh_xuhao='+zlh_xuhao+'',
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("上调成功！");
		                    			showZhlRow(mozu_id,zj_id);//显示模组指令行
						  			}else{
						  				layer.msg("上调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要上调的指令行！");
				}
			});
			//指令行下调
			$("#mz_zlh_bottom").click(function(){
				var cheBoolean = $("input[name='trRadio_zlh']").is(':checked');
				if (cheBoolean) {
					var mozu_id = $("#mozu_id").val();
					var zj_id = $("input[name='trRadio_zj']:checked").val();
					var zlh_xuhao = $("input[name='trRadio_zlh']:checked").val();
					var mz_zlh_table = $('#mz_zlh_table tbody tr');//指令行
					if(mz_zlh_table.length==1){
						layer.msg("当前只有一条指令，无需调整！");
					}else if(mz_zlh_table.length > 1){
						var row = mz_zlh_table.eq(mz_zlh_table.length-1).find('td').eq(1).text();
						if(zlh_xuhao==row){
							layer.msg("此指令已经是最后一行，无需向下调整！");
						}else{
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=mz_zlh_bottom',
								type: 'post',cache:false, 
								data: 'mozu_id='+mozu_id+'&zj_id='+zj_id+'&zlh_xuhao='+zlh_xuhao+'',
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("下调成功！");
		                    			showZhlRow(mozu_id,zj_id);//显示模组指令行
						  			}else{
						  				layer.msg("下调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要下调的指令行！");
				}
			});
			
			/***************************************pack******************************************/
			//设置默认时间
		    $('#pack_newDate').val(current());
			$('#pack_table_id').css('height', document.body.clientHeight / 3);
			
			//新建pack
			var rowIndex = 1;
			$('#pack_newBtn').click(function(){
				rowIndex = 1;
		    	$('#pack_form')[0].reset();
		    	$('#pack_id').val('');
				//设置默认时间
		    	$('#pack_newDate').val(current());
		   		$('#pack_code').attr("readOnly",false);
				$('#pack_table tbody tr').remove();
			});
			
			//添加pack行函数
			function addShowPack(obj){
				var typeClass = "";
				if(obj.pack_id==""){
					typeClass = "add";
				}
				$('#pack_table tbody').append('<tr name="pack_newTr" bgcolor="#ffffff" class="'+typeClass+'" style="height: 28px;">' +
					//复选框
					'<td id="packNewTd1_' + obj.num + '" style="width: 3%;padding:0px;"> ' +
						'<input type="radio" id="trRadio_pack' + obj.num + '" name="trRadio_pack" value="'+obj.num+'"/>' +
					'</td>' +
					//序号
					'<td id="packNewTd2_' + obj.num + '" style="width: 5%;padding:0px;">'+obj.num+'</td>' +
					//模组编码
					'<td id="packNewTd3_' + obj.num + '" style="width: 20%;padding:0px;">'+obj.pack_mozu_code+'</td>' +
					//模组类型
					'<td id="packNewTd4_' + obj.num + '" style="width: 20%;padding:0px;">'+obj.pack_mozuleixing+'</td>' +
					//数量
					'<td id="packNewTd5_' + obj.num + '" style="width: 10%;padding:0px;">'+obj.pack_shuliang+'</td>' +
				'</tr>');
				//单选框
				$("#packNewTd1_" + obj.num).click(function(){
					$("#packNewTd1_" + obj.num).find("input:radio").prop("checked",true);
				});
				//模组编码
				$('#packNewTd3_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="packNewTd3_text_' + row + '" type="text" class="form-control" value="' + $(this).html() + '">');
					$('#packNewTd3_text_' + row).focus();
			        $('#packNewTd3_text_' + row).blur(function (){
			            var node = this.parentNode;
			            var rowMzCode = this;
			            if(rowMzCode.value==""){
			            	$(node).html("");
			            	ck.ck?ck.ck=false:null;
			            	return null;
			            }
			        	$.ajax({
							url: getRootPath()+'/BaseDataAction.do?operType=getMzList',
							type: 'get',cache:false, 
							data: 'type=head&where= where a.模组编码 = \''+rowMzCode.value+'\'',
							success: function (data) {
				    			var obj = eval("("+data+")");
								if(obj.length > 0){
						            $(node).html(rowMzCode.value);
						            $('#packNewTd4_'+row).html(obj[0].mozu_leixing);
						            ck.ck?ck.ck=false:null;
		                            var pack_table = $('#pack_table tbody tr');
		                            for (var j = 0; j < pack_table.length; j++) {
			                            var pack_mz_code = pack_table.eq(j).children("td").eq(2).html();
			                            for(var k=0;k < pack_table.length;k++){
			                            	if(j!=k && pack_mz_code==pack_table.eq(k).children("td").eq(2).html()){
						    					layer.tips(pack_mz_code+'模组重复！', '#packNewTd3_' + row);
						    					return null;
			                            	}
			                            }
		                            }
								}else{
						            $(node).html("");
			            			ck.ck?ck.ck=false:null;
						    		layer.tips(rowMzCode.value+'模组不存在！', '#packNewTd3_' + row);
								}
							}
						});
			        });
		            //判断当前行 修改还是新增
		            $('#packNewTd3_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
				//数量
				$('#packNewTd5_'+obj.num).click(function(){
					var row = this.id.split("_")[1];
					var ck = this;
					if(this.ck){
						return null;
					}else{
						this.ck = true;
					}
					$(this).html('<input id="packNewTd5_text_' + row + '" type="number" min="0" class="form-control" value="' + $(this).html() + '">');
					$('#packNewTd5_text_' + row).focus();
			        $('#packNewTd5_text_' + row).blur(function (){
			            var node = this.parentNode;
			            $(node).html(this.value);
			            ck.ck?ck.ck=false:null;
			        });
		            //判断当前行 修改还是新增
		            $('#packNewTd5_text_' + row + '').change(function () {
		                if ($(ck).parent().attr("class") == "") {
		                    $(ck).parent().attr("class", "update");
		                }
		            });
				});
			}
			
			//显示pack行AJAX
			function packShowAction(pack_id,pack_code){
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=showPackRow',
					type: 'post',cache:false, 
					data: 'pack_id='+pack_id+'&pack_code='+pack_code,
					success: function (data) {
		  				var obj = eval("("+data+")");
						$('#pack_table tbody tr').remove();
		  				for(var i=0;i < obj.length;i++){
							var map = {
								num : obj[i].pack_xuhao,
								pack_id : pack_id,
								pack_code : pack_code,
								pack_mozu_code : obj[i].pack_mozu_code,
								pack_mozuleixing : obj[i].pack_mozuleixing,
								pack_shuliang : obj[i].pack_shuliang
							};
							addShowPack(map);
		  					rowIndex = Number(map.num) + 1;
						}
					}
				});
			}
			
			//添加pack行
			$('#pack_addTr').click(function(){
				var pack_id = $('#pack_id').val();
				var pack_code = $('#pack_code').val();
				if(pack_code==""){
		    	 	$("#pack_code").focus();
		    		layer.tips('请填写PACK编码！', '#pack_code');
		    		return null;
				}
				if($("#pack_leixing").val()==""){
		    	 	$("#pack_leixing").focus();
		    		layer.tips('请填写PACK类型！', '#pack_leixing');
		    		return null;
				}
				if($("#pack_morenshengchanxian").val()==""){
		    	 	$("#pack_morenshengchanxian").focus();
		    		layer.tips('请填写默认生产线！', '#pack_morenshengchanxian');
		    		return null;
				}
				if($("#pack_dianxinleixing").val()==""){
		    	 	$("#pack_dianxinleixing").focus();
		    		layer.tips('请填写电芯类型！', '#pack_dianxinleixing');
		    		return null;
				}
				var map = {
					num : rowIndex,
					pack_id : '',
					pack_code : '',
					pack_mozu_code : '',
					pack_mozuleixing : '',
					pack_shuliang : ''
				};
				addShowPack(map);
				rowIndex++;
			});
			//pack保存
			$('#pack_saveBtn').click(function(){
				if($("#pack_code").val()==""){
		    	 	$("#pack_code").focus();
		    		layer.tips('请填写PACK编码！', '#pack_code');
		    		return null;
				}
				if($("#pack_leixing").val()==""){
		    	 	$("#pack_leixing").focus();
		    		layer.tips('请填写PACK类型！', '#pack_leixing');
		    		return null;
				}
				if($("#pack_morenshengchanxian").val()==""){
		    	 	$("#pack_morenshengchanxian").focus();
		    		layer.tips('请填写默认生产线！', '#pack_morenshengchanxian');
		    		return null;
				}
				if($("#pack_dianxinleixing").val()==""){
		    	 	$("#pack_dianxinleixing").focus();
		    		layer.tips('请填写电芯类型！', '#pack_dianxinleixing');
		    		return null;
				}
				//题头
				var head = {
					pack_id : $("#pack_id").val(),
					pack_code : $("#pack_code").val(),
					pack_leixing : $("#pack_leixing").val(),
					pack_morenshengchanxian : $("#pack_morenshengchanxian").val(),
					pack_dianxinleixing : $("#pack_dianxinleixing").val(),
					pack_newDate : $("#pack_newDate").val()
				};
				var add = new Array;//新增
				var update = new Array;//修改
				var pack_table = $('#pack_table tbody tr');//指令行
				for(var i=0; i < pack_table.length; i++){
					var packRowMap = {
						pack_xuhao : pack_table.eq(i).children("td").eq(1).html(),
						pack_mozu_code : pack_table.eq(i).children("td").eq(2).html(),
						pack_shuliang : pack_table.eq(i).children("td").eq(4).html()
					};
					if(pack_table.eq(i).children("td").eq(2).html()==""){
						pack_table.eq(i).children("td").eq(2).click();
			    		layer.tips('请填写模组编码！', '#packNewTd3_' + (i+1));
						return null;
					}
					if(pack_table.eq(i).children("td").eq(4).html()==""){
						pack_table.eq(i).children("td").eq(4).click();
			    		layer.tips('请填写模组数量！', '#packNewTd5_' + (i+1));
						return null;
					}
					//判断当前行,修改还是新增
		            if ($(pack_table).parent().children("tr").eq(i).attr("class") == 'add') {
		                add.push(packRowMap);//新增数据
		            } else if ($(pack_table).parent().children("tr").eq(i).attr("class") == "update") {
		                update.push(packRowMap);//修改数据
		            }
				}
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=savePack',
					type: 'post',cache:false, 
					data: 'head='+JSON.stringify(head)+'&add='+JSON.stringify(add)+'&update='+JSON.stringify(update),
					success: function (data) {
			  			var obj = eval("("+data+")");
			  			if(obj.success){
		                    pack_table.attr("class", ""); //将所有行重置class变为老数据
		                    var pack_id = $("#pack_id").val(obj.pack_id);
		                    var pack_code = $("#pack_code").val();
			  				rowIndex = 1;
		                    packShowAction(obj.pack_id,pack_code);
			  				layer.msg("pack保存成功！");
		                }
		            }
				});
			});
			
			//pack查询按钮
			$("#pack_selectBtn").click(function(){
				var openWindow = '<div style="width: 100%;margin-right: 0;">' +
						'<div class="margin-top-10">' +
							'<div class="col-md-11" style="margin-left: 20px;">' +
							'<!-- 标题 -->' +
							'<div style="padding-right:17px;">' +
							'<table class="table table-bordered text-center" id="commonSearchTableHead_pack">' +
							'<thead>' +
							'<tr>' +
							'<td style="width: 50%;">pack编号</td>' +
							'<td style="width: 50%;">pack类型</td>' +
							'</tr>' +
							'</thead>' +
							'</table>' +
							'</div>' +
							'<!-- 标题 end-->' +
							'<!-- 内容 -->' +
							'<div class="table-body" id="commonSearchTableBody_pack">' +
							'<table class="table table-bordered text-center table-hover" id="searchTable_pack">' +
							'</table>' +
							'</div>' +
							'<!-- 内容 end-->' +
							'</div>' +
						'</div>' +
					'</div>';
				var win = layer.open({
				    type: 1,
				    title: 'pack查询',
				    shadeClose: false,
				    scrollbar:false,
				    anim:5,
				    move: false,
				    shade: [0.5, '#393D49'],
				    area: ['45%', '50%'],
				    content: openWindow
				});
				/*设置table高度*/
				$('#commonSearchTableBody_pack').css('height', document.body.clientHeight / 3);
				$.ajax({
					url: getRootPath()+'/BaseDataAction.do?operType=getPackHead',
					type: 'get',cache:false, 
					success: function (data) {
				    	var obj = eval("("+data+")");
						for(var i=0;i<obj.length;i++){
			                $('#searchTable_pack').append('<tr class="commonTr" style="cursor: pointer;"></tr>');
			               	var lastTr = $('#searchTable_pack tbody tr:last');
		                    lastTr.append('<td class="pack_code" style="width: 50%;">' + obj[i].pack_code + '</td>');
		                    lastTr.append('<td class="pack_leixing" style="width: 50%;">' + obj[i].pack_leixing + '</td>');
		                    lastTr.append('<td class="pack_id" style="display: none;">' + obj[i].pack_id + '</td>');
		                    lastTr.append('<td class="pack_morenshengchanxian" style="display: none;">' + obj[i].pack_morenshengchanxian + '</td>');
		                    lastTr.append('<td class="pack_dianxinleixing" style="display: none;">' + obj[i].pack_dianxinleixing + '</td>');
		                    lastTr.append('<td class="pack_newDate" style="display: none;">' + obj[i].pack_newDate + '</td>');
						}
						/*绑定双击事件*/
		            	$('.commonTr').bind('dblclick', function (e) {
							var tr = $(e.target).parent();
		            		$('#pack_id').val(tr.find('.pack_id').text());
		            		$('#pack_code').val(tr.find('.pack_code').text());
		            		$('#pack_leixing').val(tr.find('.pack_leixing').text());
		            		$('#pack_morenshengchanxian').val(tr.find('.pack_morenshengchanxian').text());
		            		$('#pack_dianxinleixing').val(tr.find('.pack_dianxinleixing').text());
		            		$('#pack_newDate').val(tr.find('.pack_newDate').text());
							packShowAction(tr.find('.pack_id').text(),tr.find('.pack_code').text());//显示pack行
		            		$('#pack_code').attr("readOnly",true);
							rowIndex = 1;
		            		layer.close(win);
		            	});
					}
				});
			});
			//删除pack函数
			function deletePack(title,pack_id,pack_code,pack_xuhao){
				var lay = layer.confirm('是否确认删除'+title+'？', {
			        btn: ['确认', '取消'] //按钮
			    }, function () {
			        try {
						$.ajax({
							url: getRootPath()+'/BaseDataAction.do?operType=deletePack',
							type: 'post',cache:false, 
							data: 'deleteType='+title+'&pack_id='+pack_id+'&pack_code='+pack_code+'&pack_xuhao='+pack_xuhao+'',
							success: function (data) {
					  			var obj = eval("("+data+")");
					  			if(obj.success){
					  				if(title=="pack题头"){
					  					$('#pack_newBtn').click();
					  				}else{
					  					packShowAction(pack_id,pack_code);//显示pack行
					  				}
					  				layer.msg("删除成功！");
				                }else{
					  				layer.msg("删除失败！");
				                }
				            }
						});
			        } catch (e) {
			            layer.msg("An exception occured in the script.Error name: " + e.name
			                + " script.Error message: " + e.message);
			        }
			    });
			};
			//删除pack
			$("#pack_deleteBtn").click(function(){
				var pack_id = $("#pack_id").val();
				var pack_code = $("#pack_code").val();
				if(pack_id==""||pack_code==""){
					layer.msg("请选择需要删除的PACK！");
					return null;
				}
			    deletePack('pack题头',pack_id,pack_code,'');
			});
			//删除pack行
			$("#pack_row_deleteBtn").click(function(){
				var cheBoolean = $("input[name='trRadio_pack']").is(':checked');
				if (cheBoolean) {
					var pack_id = $("#pack_id").val();
					var pack_code = $("#pack_code").val();
					var pack_xuhao = $("input[name='trRadio_pack']:checked").val();
			    	deletePack('pack行',pack_id,pack_code,pack_xuhao);
				}else{
					layer.msg("请选择需要删除的载具！");
				}
			});
			
			//上调pack行
			$("#pack_up").click(function(){
				var cheBoolean = $("input[name='trRadio_pack']").is(':checked');
				if (cheBoolean) {
					var pack_id = $("#pack_id").val();
					var pack_code = $("#pack_code").val();
					var pack_xuhao = $("input[name='trRadio_pack']:checked").val();
					var pack_table = $('#pack_table tbody tr');//pack table
					if(pack_table.length==1){
						layer.msg("当前只有一行pack，无需调整！");
					}else if(pack_table.length > 1){
						var row = pack_table.eq(0).find('td').eq(1).text();
						if(pack_xuhao==row){
							layer.msg("此pack已经是第一行，无需向上调整！");
						}else{
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=pack_up',
								type: 'post',cache:false, 
								data: 'pack_id='+pack_id+'&pack_code='+pack_code+'&pack_xuhao='+pack_xuhao,
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("上调成功！");
					  					packShowAction(pack_id,pack_code);//显示pack行
						  			}else{
						  				layer.msg("上调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要上调的pack行！");
				}
			});
			//下调pack行
			$("#pack_bottom").click(function(){
				var cheBoolean = $("input[name='trRadio_pack']").is(':checked');
				if (cheBoolean) {
					var pack_id = $("#pack_id").val();
					var pack_code = $("#pack_code").val();
					var pack_xuhao = $("input[name='trRadio_pack']:checked").val();
					var pack_table = $('#pack_table tbody tr');//pack table
					if(pack_table.length==1){
						layer.msg("当前只有一行pack，无需调整！");
					}else if(pack_table.length > 1){
						var row = pack_table.eq(pack_table.length-1).find('td').eq(1).text();
						if(pack_xuhao==row){
							layer.msg("此pack已经是最后一行，无需向下调整！");
						}else{
							$.ajax({
								url: getRootPath()+'/BaseDataAction.do?operType=pack_bottom',
								type: 'post',cache:false, 
								data: 'pack_id='+pack_id+'&pack_code='+pack_code+'&pack_xuhao='+pack_xuhao,
								success: function (data) {
						  			var obj = eval("("+data+")");
						  			if(obj.success){
						  				layer.msg("下调成功！");
					  					packShowAction(pack_id,pack_code);//显示pack行
						  			}else{
						  				layer.msg("下调失败！");
					                }
					            }
							});
						}
					}
				}else{
					layer.msg("请选择需要下调的pack行！");
				}
			});
		}catch (e) {
			return e;
		}
		return null;
	}
};