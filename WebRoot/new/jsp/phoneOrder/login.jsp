<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<title>登录</title>
		<link rel="stylesheet" href="../new/jsp/saleplugins/css/qx_wx_transf.css" />
		<link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/default.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="format-detection" content="telephone=no" />
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
	</head>
	<body>
		<div class="main_cnt" style="margin-top: 35%;">
			<form action="${pageContext.request.contextPath }/phoneorderctrl/loginMgrUser_validate.do" method="post" id="loginform">
				<div id="login_form">
					<ul>
						<li><input type="text" placeholder="用户名" name="distri_worker_login_name"/></li>
						<li><input type="password" placeholder="密码" name="distri_worker_login_pwd" />
						</li>
						<li class="txt_center"><span style="color:red; text-align:inherit;">${error }</span><p>&nbsp;</p><button type="button" class="btn btn_o" onclick="btnsubmit()">　登录　</button></li>
					</ul>
				</div>
			</form>
		</div>
		<script src="../new/jsp/saleplugins/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="../new/jsp/saleplugins/js/artDialog.source.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
             function btnsubmit(){
                $("#loginform").submit();
             }
		</script>
	</body>
<script>
	if(${type == 1}) {
		alert("网页过期！请登录。");
	}
</script>
</html>
