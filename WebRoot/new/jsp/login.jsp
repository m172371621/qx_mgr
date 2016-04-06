<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <link href="${ctx}/new/css/login.min.css" rel="stylesheet">
    <script type="text/javascript">
        <c:if test="${!empty sessionScope.user_info}">
        if(window.top !== window.self) {
            window.top.location = "${ctx}/new/jsp/index.jsp";
        } else {
            location.href = "${ctx}/new/jsp/index.jsp";
        }
        </c:if>
        if(window.top !== window.self){ window.top.location = window.location;}
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1>区享</h1>
                </div>
                <div class="m-b"></div>
                <h4>欢迎使用 <strong>区享管理后台</strong></h4>
                <ul class="m-b">
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 区域共赢</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 享你所想</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-5">
            <form role="form" action="${ctx}/login.do?method=loginNew" method="post">
                <h4 class="no-margins">登录：</h4>
                <p class="m-t-md">登录到区享管理后台</p>
                <input type="text" id="username" name="username" class="form-control uname" placeholder="用户名" />
                <input type="password" id="password" name="password" class="form-control pword m-b" placeholder="密码" />
                <%--<a href="">忘记密码了？</a>--%>
                <button class="btn btn-success btn-block">登录</button>

                <c:if test="${!empty msg}">
                    <br>
                    <div class="alert alert-warning alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        ${msg}
                    </div>
                </c:if>
            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2016 All Rights Reserved. 南京区享科技
        </div>
    </div>
</div>
</body>

</html>