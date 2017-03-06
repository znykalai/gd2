function mousePosition(evt){
    evt = evt || window.event;
    //Mozilla
    if(evt.pageX || evt.pageY){
        return { x : evt.pageX,y : evt.pageY}
    }
    //IE
    return {
        x : evt.clientX + document.body.scrollLeft - document.body.clientLeft,
        y : evt.clientY + document.body.scrollTop - document.body.clientTop
    }
}
//获取X轴坐标  
function getX(evt){
    evt = evt || window.event;
    return mousePosition(evt).x;
}
//获取Y轴坐标
function getY(evt){
    evt = evt || window.event;
    return mousePosition(evt).y;
}
function getRootPath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return(prePath+postPath);
}
function current() {
	var d = new Date(), str = '';
	str += d.getFullYear() + '-'; //获取当前年份
	str += d.getMonth() + 1 + '-'; //获取当前月份（0——11）
	str += d.getDate() +" ";
	str += d.getHours() + ':';
	str += d.getMinutes() + ':';
	str += d.getSeconds();
	return str;
}
function dragStart(event){
	af_Home.winId = event.target.id.split("_")[0];
   	event.dataTransfer.setData("Text", event.target.id);
   	return null;
}
function allowDrop(event){
	if(af_Home.winId){
		$("#"+af_Home.winId).hide(100);
		$("#"+af_Home.winId+"_home").hide(0);
		$("#"+af_Home.winId+"_id_").hide(0);
		af_Home.winId=null;
	}
    event.preventDefault();
    return null;
}
function drop(event,a){
	event.preventDefault();
	var data = event.dataTransfer.getData("Text");
	var html;
	var a = af_Home.winHtml(function(val){
		html=val;val=null;
	},data);a=null;
	var y = getY(event);
	var x = getX(event)+5>document.body.clientWidth?getX(event)-100:getX(event);
	var win = layer.open({
		btn:[],
		anim:3,
		type:1,
		shade:0,
		sw:data,
		resize:false,
		area: ['280px', '200px'],
		title:$('#'+data).attr('title'),
		offset:[y,x],
		content: html,
		success: function(layero){
			var ee=af_Home.winHtmlEvent();ee=null;
		},
		cancel: function(index){
			$("#"+this.sw).show(0);
			$("#"+this.sw.split("_")[0]).fadeToggle(300);
			layer.close(index);
			return null;
		} 
	});
	return data=null,html=null,y=null,x=null,win=null;
}