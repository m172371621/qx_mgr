<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改后台用户</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="userForm">
                        <input type="hidden" name="user_id" id="user_id" value="${user.user_id}">

                        <div class="form-group">
                            <label class="col-md-4 control-label text-right">登录名</label>

                            <div class="col-md-3">
                                <p class="form-control-static">${user.loginname}</p>
                                <input type="hidden" id="loginname" name="loginname" value="${user.loginname}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-4 control-label text-right">密码</label>

                            <div class="col-md-3">
                                <input type="text" id="password" name="password" class="form-control"
                                       value="${user.password}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="col-md-4 control-label text-right">手机号</label>

                            <div class="col-md-3">
                                <input type="text" id="phone" name="phone" class="form-control" value="${user.phone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-md-4 control-label text-right">邮箱</label>

                            <div class="col-md-3">
                                <input type="text" id="email" name="email" class="form-control" value="${user.email}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="role_id" class="col-md-4 control-label text-right">角色</label>

                            <div class="col-md-3">
                                <select id="role_id" name="role_id" class="form-control">
                                    <c:forEach items="${rlist}" var="role">
                                        <option value="${role.role_id}"
                                                <c:if test="${user_list_role[0].role_id == role.role_id}">selected</c:if> >${role.role_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cid" class="col-md-4 control-label text-right">所属门店</label>

                            <div class="col-md-3">
                                <ui:simpleCommunitySelect id="cid" name="cid"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-2 col-md-offset-5">
                                <button class="btn btn-primary" type="button" id="saveBtn" onclick="save()">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function save() {
        layer.confirm("确定要保存吗？", function (index) {
            var i = layer.load();
            $.ajax({
                type: 'post',
                url: "${ctx}/user.do?method=editV4",
                data: $('#userForm').serialize(),
                success: function (data) {
                    layer.close(i);
                    if (data != null && data == 'success') {
                        layer.alert("修改成功！", function(index) {
                            location.reload();
                        });
                    } else {
                        layer.alert("修改失败！");
                    }
                }
            });
        });
    }
</script>

</body>

</html>