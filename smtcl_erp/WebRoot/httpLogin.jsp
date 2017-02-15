<%@ page language="java" contentType="text/html;charset=utf-8"%> 
<%--
/**
 * 标题：使用页面的登录方式。
 * 说明：在本页面将使用模态窗口打开用户输入的用户名和密码框，输入完成之后进行解析和认证。
 * 注意：在Windows主机IE上显示，并且必须将服务器设置为信任状态。
 *
 * 修改历史：
 * 1、增加错误信息提示窗口，使用request对象中的msg属性来保存，将struts转向变成此页面；
 * 2、将status=0修改为status=no；
 * 3、增加对windows XP sp2以上浏览器的支持；
 * 
 */
--%>
<%
String path = request.getContextPath();
String msg = (String)request.getAttribute("msg");
String KickMsg = request.getParameter("KickMsg");
if(KickMsg != null){
	KickMsg = new String(KickMsg.getBytes("iso-8859-1"),"utf-8");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="<%=path%>/css/LoginPage/common.css" type="text/css" />
<title>缓存库系统</title>
<script language="JavaScript">
function checkMsg(){
	document.getElementById("UserName").focus();
}

function onSubmit(){
	if((document.getElementById("UserName").value=="")||(document.getElementById("Password").value=="")){
		alert("用户名和密码不能为空！");
		return false;
	}
}
</script>
<style>
	.x-myCls-textfield{
		background:#FFFFCC;
	}
</style>
</head>

<body class="login" onload="checkMsg();" background="<%=path%>/images/bg_01.gif">
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
					<input type="text" id="UserName" name="UserName" value="zj221" size="20" maxlength="32" class="x-myCls-textfield"/>
				  </td>
				</tr>

				<tr align="center" valign="middle">
				  <td class="hstd5" nowrap style="font-family:微软雅黑;">密&nbsp;&nbsp;&nbsp;码：</td>
				  <td align="left" nowrap >
					<input type="password" id="Password" name="Password" value="1" size="20" maxlength="32" class="x-myCls-textfield" onfocus="this.select();";/>
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
		  
		  <tr align="center" valign="bottom">
			<td colspan="2"><font color="red">
			  <script language="javascript">
				if (!navigator.cookieEnabled) {
				  document.write("Browser cookie is not enabled, you will not login WebUI sucessfully, please enable it and try again!");
				}
			  </script>
			</font></td>
		  </tr>
	    </table>
      </td>
    </tr>
  </table>
  </center>
</form>
</body>
</html>