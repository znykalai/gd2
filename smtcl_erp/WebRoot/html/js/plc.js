var readyShow = {
	load:function(hdFun){
		var winHeight = document.body.clientHeight;
		if(winHeight == window.screen.height){
			winHeight = document.body.clientHeight - 50;
		}
		$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
		return hdFun();
	}
};