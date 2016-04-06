<%@ page import="com.brilliantreform.sc.utils.SettingUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <script type="text/javascript" src="${ctx}/new/js/jquery.validate.js"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>
                        修改员工
                    </h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="elementForm">
                        <input type="hidden" value="${employee.user_id}" name="user_id"/>

                        <div class="form-group">
                            <label class="col-md-4 control-label text-right">经营组织</label>

                            <div class="col-md-2">
                                <select class="form-control" name="community_id">
                                    <c:forEach items="${queryChildrenList}" var="queryChildren">
                                        <option value="${queryChildren.communityId}" <c:if
                                                test="${employee.community_id==queryChildren.communityId}"> selected="selected"</c:if>>${queryChildren.communityName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="personname" class="col-md-4 control-label text-right">员工姓名</label>

                            <div class="col-md-2">
                                <input type="text" id="personName" name="personName" class="form-control"
                                       disabled="disabled"
                                       value="${employee.personName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="col-md-4 control-label text-right">员工联系方式</label>

                            <div class="col-md-2">
                                <input type="text" id="phone" name="phone" class="form-control"
                                       value="${employee.phone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="role_name" class="col-md-4 control-label text-right">职位</label>

                            <div class="col-md-2">
                                <c:forEach items="${list}" var="ls">
                                    <c:choose>
                                        <c:when test="${fn:contains(mgrur,ls.role_id)}">
                                            <input id="zhiwei" name="zhiwei" type="checkbox" checked="checked"
                                                   value="${ls.role_id}"/> ${ls.role_name}<br/>
                                        </c:when>
                                        <c:otherwise>
                                            <input id="zhiwei" name="zhiwei" type="checkbox"
                                                   value="${ls.role_id}"/> ${ls.role_name}<br/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="age" class="col-md-4 control-label text-right">员工年龄</label>

                            <div class="col-md-2">
                                <input type="text" id="age" name="age" class="form-control" value="${employee.age}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sex" class="col-md-4 control-label text-right">员工性别</label>

                            <div class="col-md-2">
                                <select id="sex" name="sex" class="form-control">
                                    <option value="0" <c:if test="${employee.sex == 0}">selected="selected"</c:if>>男
                                    </option>
                                    <option value="1" <c:if test="${employee.sex == 1}">selected="selected"</c:if>>女
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="touxiang" class="col-md-4 control-label text-right">员工头像</label>

                            <div class="col-md-2">
                                <input type="file" class="form-control" id="touxiang">
                                <input type="button" name="file_upload1" value="上传图片"
                                       onclick="jq_upload('touxiang','touxiang','picture')"/>
                                <input type="hidden" id="picture" name="picture" value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="loginname" class="col-md-4 control-label text-right">平台账号</label>

                            <div class="col-md-2">
                                <input type="text" class="form-control" value="${employee.loginname}" id="loginname"
                                       name="loginname" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-4 control-label text-right">平台密码</label>

                            <div class="col-md-2">
                                <input type="text" class="form-control" value="${employee.password}" id="password"
                                       name="password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-2 col-md-offset-4">
                                <button class="btn btn-primary" type="submit" id="saveBtn">
                                    保存
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<script type="text/javascript">
    $(function () {
        $("#elementForm").validate({
            debug: true,
            rules: {
                personName: "required",
                phone: {
                    required: true,
                    digits: true,
                    rangelength: [11, 11]
                },
                age: {
                    digits: true,
                    min: 1,
                    max: 120
                },
                loginname: {
                    required: true
                },
                password: {
                    required: true,
                    rangelength: [6, 12]
                },
                zhiwei: "required"
            },
            messages: {
                personName: "姓名不能为空！",
                phone: {
                    required: "手机号码不能为空个！",
                    digits: "必须为数字",
                    rangelength: "手机号码为11位"
                },
                age: {
                    digits: "必须为数字",
                    min: "输入合法的年龄",
                    max: "输入合法的年龄"
                },
                loginname: "账号不能为空！！！",
                password: {
                    required: "密码不能为空！",
                    rangelength: "长度为6~12"
                },
                zhiwei: "至少选择一个"
            },
            submitHandler: function () {
                var update = $("#elementForm").serialize();
                update = decodeURIComponent(update, true);
                $.ajax({
                    type: "post",
                    data: update,
                    url: ctx + "/employee.do?method=updateEmployee",
                    success: function (data) {
                        layer.alert("修改成功！", {icon: 1, skin: 'layer-ext-moon'});
                    },
                    error: function (error) {

                    }
                });
            }
        })
    });

    function jq_upload(path, file_id, hidden_id) {
        var url = '<%=SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST")%>/common/upload/';
        $.ajaxFileUpload({
            url: url,            //需要链接到服务器地址
            secureuri: url,
            fileElementId: file_id,  //文件选择框的id属性
            data: {to_path: path},
            success: function (data, status) {
                var results = $(data).text();
                results = results.substring(results.indexOf('{'));
                var obj = eval("(" + results + ")");
                $("#" + picture).val(obj.data.new_file);
                alert('文件上传成功！');
            },
            error: function (data, status, e) {
                alert('文件上传失败！');
            }
        });
    }
</script>
</body>
</html>