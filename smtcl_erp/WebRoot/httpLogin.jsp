<%@ page language="java" contentType="text/html;charset=utf-8"%> 
<% String path = request.getContextPath();String msg = (String)request.getAttribute("msg");%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>缓存库系</title>
<link rel="stylesheet" href="<%=path%>/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/login.css">
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/layer/layer.js"></script>
<script src="<%=path%>/js/jquery.backstretch.min.js"></script>
<script>
var af={
	checkMsg:function(){
		var msg = "<%=msg%>";
		if(msg!=""&&msg!='null'){
			var a=layer.msg(msg);a=null;
		};
		var a=$("#UserName").focus();a=null;
        $.backstretch("images/bg.png");
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
<body style="overflow-y:hidden;overflow-x:hidden;" onload="af.checkMsg();">
<div class="top-content">
    <div class="row">
        <div class="col-md-1 col-lg-offset-1"></div>
    </div>
    <div class="inner-bg">
        <div class="row">
            <div class="col-md-6 col-md-offset-3 form-box" style="background-color: transparent; ">
                <div class="form-top">
                    <div class="form-top-left">
                        <h2 style="color: #006dcc;font-weight: bold;">用户登录</h2>
                    </div>
                    <div class="form-top-right">
                        <i class="fa fa-lock"></i>
                    </div>
                </div>
                <div class="form-bottom">
                    <div class="row">
                    	<form method="post" action="<%=path%>/LoginAction.do?operationType=login" onsubmit="return af.onSubmit();">
                        <div class="col-md-8">
                            <div class="form-group row">
                                <div class="col-md-3"
                                     style="align: center center;padding-left: 25px;margin-top: 8px;padding-right: 0;">
                                    <span style="font-weight: bold;">用户名:</span>
                                </div>
                                <div class="col-md-9" style="padding-left: 0;">
                                    <input type="text" placeholder="用户名..."
                                           class="form-username form-control" id="UserName" name="UserName">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="row" style="padding-left: 14px;padding-right: 14px;">
                                    <div class="col-md-3"
                                         style="align:center center;padding-left: 40px;margin-top: 8px;padding-right: 0;">
                                        <span style="font-weight: bold;">密码:</span>
                                    </div>
                                    <div class="col-md-9" style="padding-left: 0;">
                                        <input type="password" placeholder="密码..."
                                               class="form-password form-control" id="Password" name="Password">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="row">
                                <div class="col-md-7" style="padding-left: 0;">
                                    <button type="submit" class="btn"
                                            style="height: 115px;width: 115px;font-weight: bold;font-size: 20px;">登&nbsp;&nbsp;录
                                    </button>
                                </div>
                            </div>
                        </div>
						</form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>