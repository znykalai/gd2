<%@ page language="java" contentType="text/html;charset=utf-8"%> 
<%
	String path = request.getContextPath();
	String msg = (String)request.getAttribute("msg");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>缓存库系统</title>
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/layer/layer.js"></script>
<script language="JavaScript">
function checkMsg(){
	document.getElementById("UserName").focus();
}
(function(){
	var msg = "<%=msg%>";
	if(msg!=""&&msg!='null'){
		layer.alert(msg);
	}
})();
function onSubmit(){
	if($("#UserName").val()==""){
	 	$("#UserName").focus();
		layer.tips('请填写用户名！', '#UserName');
		return false;
	}else if($("#Password").val()==""){
	 	$("#Password").focus();
		layer.tips('请填写密码！', '#Password');
		return false;
	}
}
</script>
</head>
<body style="overflow-y:hidden;" onload="checkMsg();" background="<%=path%>/images/bg_01.gif">
<form name="login" id="login" method="post" action="<%=path%>/LoginAction.do?operationType=login" onsubmit="return onSubmit();">
  <center>
  <table width='700' height='100%' border='0' cellspacing='0' cellpadding='2'>
    <tr align="center" valign="middle">
      <td align="center" valign="middle" scope="row">
		<table width='566' height='335' border='0' cellspacing='0' cellpadding='2' background="<%=path%>/images/login_bg.gif">
	      <tr align="center" valign="middle">
            <td width='100%' align='center' valign='middle'>
 		      <table border='0'>
				<tr align="center" valign="middle">
				  <td class="hstd5" nowrap style="font-family:微软雅黑;">用户名：</td>
				  <td align="left" nowrap >
					<input type="text" id="UserName" name="UserName" size="20" maxlength="32" />
				  </td>
				</tr>
				<tr align="center" valign="middle">
				  <td class="hstd5" nowrap style="font-family:微软雅黑;">密&nbsp;&nbsp;&nbsp;码：</td>
				  <td align="left" nowrap >
					<input type="password" id="Password" name="Password" size="20" maxlength="32"  onfocus="this.select();";/>
				  </td>
				</tr>
				<tr align="center" valign="middle">
				  <td align="center" nowrap style="font-size:13px;font-family:微软雅黑;" colspan="2">
					<input type="submit" class="subbtn" name="subBtn" value="登录" onclick="this.disabled=false;"/>
				  </td>
				</tr>
		      </table>
	        </td>
	      </tr>
      </td>
    </tr>
  </table>
  </center>
</form>
</body>
</html>