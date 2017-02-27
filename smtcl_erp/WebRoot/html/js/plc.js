var readyShow = {
	load:function(hdFun){
		var winHeight = document.body.clientHeight;
		if(winHeight == window.screen.height){
			winHeight = document.body.clientHeight - 50;
		}
		$('#xy').css('height', (winHeight - (window.screen.height - winHeight))/0.98);
		$('.mainOrder').click(function (e) {
		    var item = $(e.target);
		    $('#mainStatusBar').find('div').removeClass('select');
		    item.addClass('select');
		    $(".plc_show").children().css("display", "none");
		    $("#plc_show" + $(item).attr('data')).css("display", "block");
	        return (function(){
	        	item = null;
	        	return null;
	        })();
		});
		return hdFun();
	}
};