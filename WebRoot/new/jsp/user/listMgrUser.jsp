<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio] {
            margin-top: 4px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>后台用户查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="user_id" class="col-sm-1 control-label text-right">用户ID</label>

                            <div class="col-sm-2">
                                <input type="text" id="user_id" class="form-control">
                            </div>
                            <label for="loginname" class="col-sm-1 control-label text-right">用户名</label>

                            <div class="col-sm-2">
                                <input type="text" id="loginname" class="form-control">
                            </div>
                            <div class="col-sm-6">
                                <button class="btn btn-primary" type="button" onclick="search()">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default" onclick="showEditWin()">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                    </div>
                    <div id="mgrUserTable" class="dt-grid-container"></div>
                    <div id="mgrUserToolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title_edit">编辑用户</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="user_id_edit" name="user_id">

                    <div class="form-group">
                        <label for="loginname_edit" class="col-md-4 control-label text-right">登录名 <span class="text-danger">*</span></label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="loginname_edit" name="loginname" data-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password_edit" class="col-md-4 control-label text-right">密码 <span class="text-danger">*</span></label>
                        <div class="col-md-5">
                            <!-- 第一个password是为了防止浏览自动填充帐号密码 -->
                            <input type="password" style="display: none;">
                            <input type="password" class="form-control" id="password_edit" name="password" data-rule="required;length(6~)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="personname_edit" class="col-md-4 control-label text-right">姓名</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="personname_edit" name="personname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone_edit" class="col-md-4 control-label text-right">手机号</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="phone_edit" name="phone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_edit" class="col-md-4 control-label text-right">邮箱</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="email_edit" name="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label text-right">角色 <span class="text-danger">*</span></label>
                        <div class="col-md-5">
                            <c:forEach items="${roleList}" var="role">
                                <c:if test="${role.role_id == 1}">
                                    <c:if test="${sessionScope.user_isAdmin}">
                                        <label class="checkbox-inline">
                                            <input type="checkbox" value="${role.role_id}" name="role_id" data-rule="checked">${role.role_name}
                                        </label>
                                    </c:if>
                                </c:if>
                                <c:if test="${role.role_id != 1}">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="${role.role_id}" name="role_id" data-rule="checked">${role.role_name}
                                    </label>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label text-right">所属门店 <span class="text-danger">*</span></label>
                        <div class="col-md-7">
                            <c:forEach items="${communityList}" var="community">
                                <label class="checkbox-inline">
                                    <input type="checkbox" value="${community.community_id}" name="community_id" data-rule="checked">${community.community_name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveUser()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id: 'user_id', title: '用户ID', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'loginname', title: '用户名', type: 'string', columnClass: 'text-center'},
        {id: 'personName', title: '姓名', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'phone', title: '手机号', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'email', title: '邮箱', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'createTime',
            title: '创建时间',
            type: 'date',
            format: 'yyyy-MM-dd hh:mm',
            columnClass: 'text-center',
            hideType: 'xs'
        },
        {
            id: 'operation', title: '操作', type: 'string', columnClass: 'text-center', hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                var accountBtn = "<button class='btn btn-danger' onclick='accountDisable(" + record.user_id + ")'>冻结</button>";
                if (record.activation != 1) {
                    accountBtn = "<button class='btn btn-warning' onclick='accountEnable(" + record.user_id + ")'>解冻</button>";
                }
                var html = '<button class="btn btn-primary" onclick="showEditWin(' + record.user_id + ')">修改</button>'
                        + '&nbsp;&nbsp;' + accountBtn;
                return html;
            }
        }

    ];
    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/user.do?method=listV4',
        columns: gridColumn,
        gridContainer: 'mgrUserTable',
        toolbarContainer: 'mgrUserToolBar',
        tools: ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function () {
        grid.load();
    });

    //自定义查询
    function search() {
        var user_id = $('#user_id').val();
        var loginname = $('#loginname').val();
        grid.parameters = new Object();
        grid.parameters['user_id'] = user_id;
        grid.parameters['loginname'] = loginname;
        grid.loadToFirst();
    }

    function accountDisable(user_id) {
        layer.confirm("确定要冻结吗？", function (index) {
            var i = layer.load();
            $.ajax({
                type: 'post',
                url: "${ctx}/user.do?method=accountDisable",
                dataType: 'json',
                data: {user_id: user_id},
                success: function (data) {
                    layer.close(i);
                    if (data.result_code == 0) {
                        layer.alert(data.result_dec);
                        grid.load();
                    }
                }
            });
        });
    }

    function accountEnable(user_id) {
        layer.confirm("确定要解冻吗？", function (index) {
            var i = layer.load();
            $.ajax({
                type: 'post',
                url: "${pageContext.request.contextPath }/user.do?method=accountEnable",
                dataType: 'json',
                data: {user_id: user_id},
                success: function (data) {
                    layer.close(i);
                    if (data.result_code == 0) {
                        layer.alert(data.result_dec);
                        grid.load();
                    }
                }
            });
        });
    }

    function clearEditForm() {
        $('#user_id_edit').val('');
        $('#loginname_edit').val('');
        $('#password_edit').val('');
        $('#personname_edit').val('');
        $('#phone_edit').val('');
        $('#email_edit').val('');
        $("input[name=role_id]").prop("checked", false);
        $("input[name=community_id]").prop("checked", false);
        $('#editForm').validator("cleanUp");
    }

    function showEditWin(user_id) {
        clearEditForm();
        if(user_id) {
            $('#title_edit').text('修改用户');
            $('#loginname_edit').attr('readOnly', true);
            var i = layer.load();
            $.post('${ctx}/user.do?method=getMgrUserById', {user_id : user_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#user_id_edit').val(json.mgrUser.user_id);
                        $('#loginname_edit').val(json.mgrUser.loginname);
                        $('#password_edit').val(json.mgrUser.password);
                        $('#personname_edit').val(json.mgrUser.personName);
                        $('#phone_edit').val(json.mgrUser.phone);
                        $('#email_edit').val(json.mgrUser.email);
                        var roleList = json.roleList;
                        if(roleList) {
                            $.each(roleList, function(index, v) {
                                $('input[name=role_id][value=' + v.role_id + ']').prop('checked', true);
                            });
                        }
                        var communityList = json.communityList;
                        if(communityList) {
                            $.each(communityList, function(index, v) {
                                $('input[name=community_id][value=' + v.community_id + ']').prop('checked', true);
                            });
                        }
                    }
                }
                layer.close(i);
            });
        } else {
            //新增用户
            $('#title_edit').text('新增用户');
            $('#loginname_edit').attr('readOnly', false);
        }

        $('#editWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function saveUser() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/user.do?method=saveMgrUser', $('#editForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json) {
                            if(json.result) {
                                layer.alert("操作成功！");
                                $('#editWin').modal('hide');
                                grid.load();
                            } else {
                                if(json.msg) {
                                    layer.alert(json.msg);
                                } else {
                                    layer.alert("操作失败！");
                                }
                            }
                        }
                    }
                    layer.close(i);
                });
            });
        }
    }

</script>

</body>

</html>