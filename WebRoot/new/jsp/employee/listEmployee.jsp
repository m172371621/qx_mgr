<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>员工查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="order_serial" class="col-sm-1 control-label text-right">经营组织</label>

                            <div class="col-sm-2">
                                <select class="form-control" id="community_id">
                                    <option value="">全部</option>
                                    <c:forEach items="${queryChildrenList}" var="queryChildren">
                                        <option value="${queryChildren.communityId}">${queryChildren.communityName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="user" class="col-sm-1 control-label text-right">状态</label>

                            <div class="col-sm-2">
                                <select class="form-control" id="status">
                                    <option value="">全部</option>
                                    <option value="1">正常</option>
                                    <option value="0">离职</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" id="selEmployee">搜索</button>
                                &nbsp;&nbsp;
                                <button class="btn btn-primary" type="button" onclick="addEmployee()">添加</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div id="employeeTable" class="dt-grid-container"></div>
                    <div id="employeeToolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var gridColumn = [
        {id: 'communityName', title: '经营组织', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'personName', title: '员工姓名', type: 'string', columnClass: 'text-center'},
        {
            id: 'loginname', title: '登录账号', type: 'date',
            columnClass: 'text-center',
            type: 'string'
        },
        {id: 'password', title: '登录密码', type: 'string', columnClass: 'text-center'},
        {id: 'phone', title: '联系方式', type: 'string', columnClass: 'text-center'},
        {id: 'role_name', title: '职位', type: 'string', columnClass: 'text-center'},
        {id: 'age', title: '年龄', type: 'string', columnClass: 'text-center'},
        {
            id: 'sex',
            title: '性别',
            type: 'string',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                if (record.sex == 0)
                    return "男";
                if (record.sex == 1)
                    return "女";
            }
        },
        {id: 'touxiang', title: '头像', type: 'string', columnClass: 'text-center'},//
        {
            id: 'authenticate',
            title: '状态',
            type: 'string',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                var value = "正常";
                if (record.authenticate == 0) {
                    value = "<span style='color:red';>离职</span>";
                }
                return value;
            }
        },
        {
            id: '',
            title: '操作',
            type: 'string',
            columnClass: 'text-left',
            resolution: function (value, record, column, grid, dataNo, columnNo) {

                if (record.authenticate == 0) {
                    return "<button class='btn btn-primary' onclick='selEmployee(" + record.user_id + ")'>查看</button>";
                } else {
                    return "<button class='btn btn-primary' onclick='selEmployee(" + record.user_id + ")'>查看</button>" +
                            "<button class='btn btn-primary' onclick='editEmployee(" + record.user_id + ")'>修改</button>" +
                            "<button class='btn' onclick='liZhiEmployee(" + record.user_id + ")'>离职</button>";
                }
            }
        }
    ];

    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/employee.do?method=listEmployee',
        columns: gridColumn,
        gridContainer: 'employeeTable',
        toolbarContainer: 'employeeToolBar',
        tools: ''
    };

    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        grid.load();
        $("#selEmployee").click(search);
        $("#community_id").change(search);
        $("#status").change(search);
    });
    function search() {
        var community_id = $('#community_id').val();
        var status = $('#status').val();
        grid.parameters = new Object();
        grid.parameters['communiyt_id'] = community_id;
        grid.parameters['authenticate'] = status;
        grid.refresh(true);
    }
    function addEmployee() {
        window.parent.openTab('${ctx}/employee.do?method=editEmployeePage&type=1', '新增员工');
    }

    function selEmployee(user_id) {
        window.parent.openTab('${ctx}/employee.do?method=editEmployeePage&type=2&user_id=' + user_id, '查看员工');
    }

    function editEmployee(user_id) {
        window.parent.openTab('${ctx}/employee.do?method=editEmployeePage&type=3&user_id=' + user_id, '修改员工');
    }
    function liZhiEmployee(user_id) {
        if (layer.confirm("确定要离职吗？", {icon:0,title:'提示！'},function (index) {
                    $.post('${ctx}/employee.do?method=liZhiEmployee', {user_id: user_id}, function (data) {
                        layer.msg("成功！");
                        grid.load();
                    })
                }));
    }
</script>
</html>
