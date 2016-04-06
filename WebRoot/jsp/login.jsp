<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8" />
		<title>登陆-区享</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/jsp/css/manager.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.js"></script>
			<script type="text/javascript">
function check()
{
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;

	if(!/^\S{1,20}$/.test(username))
	{
		alert("请输入正确的用户名");
		return false;
	}

	if(!/^\S{1,20}$/.test(password))
	{
		alert("请输入正确的密码");
		return false;
	}

	return true;
}
</script>
	</head>
	<body id="login" <% if(request.getAttribute("msg")!=null) out.print("onload=\"alert('登陆失败："+request.getAttribute("msg")+"')\"");%>>
		<form action="${pageContext.request.contextPath }/login.do?method=login" method="post" id="form">
		<dl>
			<dt><a href="#"><img src="${pageContext.request.contextPath }/jsp/images/logo_login.png"/></a></dt>
			<dd><input name="username"  id="username" type="text" placeholder="用户名" /></dd>
			<dd><input name="password"   id="password" type="password" placeholder="密码"  /></dd>
			<dd><a href="javascript:if(check()){ $('#form').submit(); } " class="lr_btn">登录</a> <a href="#">忘记密码？</a>  <a href="#">注册</a></dd>
		</dl>
		</form>
		<div id="about_msg">quxiang Copyright©2014 All Rights Reserved <a href="#">关于我们 </a>| <a href="#">微博</a> | <a href="">Blog</a>| <a href="">服务条款</a> | <a href="">隐私政策</a> 京公网安备110000000013号京ICP证6255884号</div>
	</body>

</html>




