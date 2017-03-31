<%@ page language="java" contentType="text/html;charset=utf-8"%> 
<% String path = request.getContextPath();String msg = (String)request.getAttribute("msg");%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>缓存库系</title>
<link rel="stylesheet" href="<%=path%>/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=path%>/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/htmleaf-demo.css">
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/layer/layer.js"></script>
<script>
var af={
	checkMsg:function(){
		var msg = "<%=msg%>";
		if(msg!=""&&msg!='null'){
			var a=layer.msg(msg);a=null;
		};
		var a=$("#UserName").focus();a=null;
		return null;
	},
	onSubmit:function(){
		if($("#UserName").val()==""){
		 	var b=$("#UserName").focus();b=null;
			var c=layer.tips('请填写用户名！', '#UserName');c=null;
			return false;
		}else if($("#Password").val()==""){
		 	var b=$("#Password").focus();b=null;lay=null;
			var c=layer.tips('请填写密码！', '#Password');c=null;
			return false;
		};
		af=null;
		return null;
	}
};
</script>
</head>
<body style="overflow-y:hidden;" onload="af.checkMsg();">
	<div class="htmleaf-container">
		<header class="htmleaf-header"></header>
		<div class="demo form-bg" style="padding: 50px 0;">
	        <div class="container">
	            <div class="row">
	                <div class="col-md-offset-3 col-md-6">
	                    <form class="form-horizontal" method="post" action="<%=path%>/LoginAction.do?operationType=login" onsubmit="return af.onSubmit();">
	                        <span class="heading">用户登录</span>
	                        <div class="form-group">
	                            <input type="text" class="form-control" id="UserName" name="UserName" placeholder="用户名">
	                            <i class="fa fa-user"></i>
	                        </div>
	                        <div class="form-group help">
	                            <input type="password" class="form-control" id="Password" name="Password" placeholder="密　码">
	                            <i class="fa fa-lock"></i>
	                            <a href="#" class="fa fa-question-circle"></a>
	                        </div>
	                        <div class="form-group">
	                        	<button type="submit" class="btn btn-default">登录</button> 
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>