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
                    <h5>快递管理</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="user_phone" class="col-sm-1 control-label text-right">手机号</label>
                            <div class="col-sm-2">
                                <input type="text" id="user_phone" class="form-control">
                            </div>
                            <label for="express_no" class="col-sm-1 control-label text-right">单号</label>
                            <div class="col-sm-2">
                                <input type="text" id="express_no" class="form-control">
                            </div>
                            <label for="user_type" class="col-sm-1 control-label text-right">APP用户</label>
                            <div class="col-sm-2">
                                <select id="user_type" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">否</option>
                                    <option value="2">是</option>
                                </select>
                            </div>
                            <label for="express_com" class="col-sm-1 control-label text-right">快递公司</label>
                            <div class="col-sm-2">
                                <select id="express_com" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${comMap}" var="com">
                                        <option value="${com.key}">${com.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status" class="col-sm-1 control-label text-right">状态</label>
                            <div class="col-sm-2">
                                <select id="status" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">未签收</option>
                                    <option value="2">已签收</option>
                                </select>
                            </div>
                            <label for="express_info" class="col-sm-1 control-label text-right">地址</label>
                            <div class="col-sm-2">
                                <input type="text" id="express_info" class="form-control">
                            </div>
                            <label class="col-sm-1 control-label text-right">到货时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_to"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                            <div class="col-sm-2">
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
                            快递录入
                        </button>
                        <button type="button" class="btn btn-outline btn-default" onclick="signExpress()">
                            批量签收
                        </button>
                    </div>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title_edit">快递录入</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="express_id_edit" name="express_id">
                    <div class="form-group">
                        <label for="community_id_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <ui:simpleCommunitySelect id="community_id_edit" name="community_id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label text-right">快递公司</label>
                        <div class="col-md-7">
                            <%--<select class="form-control" id="express_com_edit" name="express_com">
                                <option value="">请选择</option>
                                <c:forEach items="${comMap}" var="com">
                                    <option value="${com.key}">${com.value}</option>
                                </c:forEach>
                            </select>--%>
                            <c:forEach items="${comMap}" var="com">
                                <label class="radio-inline">
                                    <input type="radio" name="express_com" value="${com.key}" data-rule="checked;">${com.value}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="express_no_edit" class="col-md-4 control-label text-right">快递单号</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="express_no_edit" name="express_no" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_phone_edit" class="col-md-4 control-label text-right">手机号</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="user_phone_edit" name="user_phone" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="express_info_edit" class="col-md-4 control-label text-right">地址</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="express_info_edit" name="express_info" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group" id="statusDiv">
                        <label class="col-md-4 control-label text-right">是否签收</label>
                        <div class="col-md-5">
                            <label class="radio-inline">
                                <input type="radio" name="status" value="1">未签收
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="status" value="2">已签收
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveExpress()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var express_status = { 2 : "已签收", 1 : "<b class='text-danger'>未签收</b>"};

    var gridColumn = [
        {id:'user_phone', title:'手机号', type:'string', columnClass:'text-center'},
        {id:'express_com_name', title:'快递公司', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'express_no', title:'快递单号', type:'string', columnClass:'text-center', hideType:'xs', resolution : function(value, record) {
            return "<a href='javascript:void(0)' onclick='showPrint(" + record.express_id + ")'>" + value + "</a>";
        }},
        {id:'arrival_time', title:'到货日期', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'express_info', title:'地址', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs',codeTable:express_status},
        {id:'sign_time', title:'签收时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs', resolution : function(value, record) {
            return record.status == 2 ? $.fn.DtGrid.tools.dateFormat(value, 'yyyy-MM-dd hh:mm') : "";
        }},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '';
            if(record.status == 1) {
                content += '<button class="btn btn-primary" onclick="signExpress(' + record.express_id + ')">签收</button>';
            }
            content += '  ';
            content += '<button class="btn btn-primary" onclick="showEditWin(' + record.express_id + ')">编辑</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeExpress(' + record.express_id + ')">删除</button>';
            return content;
        }}
    ];

    var gridOption = {
        check : true,
        ajaxLoad : true,
        loadURL : '${ctx}/express.do?method=searchExpress',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function() {
        grid.load();
        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
    });

    //自定义查询
    function search(){
        var user_phone = $('#user_phone').val();
        var express_no = $('#express_no').val();
        var user_type = $('#user_type').val();
        var express_com = $('#express_com').val();
        var status = $('#status').val();
        var express_info = $('#express_info').val();
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        var community_id = $('#community_id').val();

        grid.parameters = new Object();
        grid.parameters['user_phone'] = user_phone;
        grid.parameters['express_no'] = express_no;
        grid.parameters['user_type'] = user_type;
        grid.parameters['express_com'] = express_com;
        grid.parameters['status'] = status;
        grid.parameters['express_info'] = express_info;
        grid.parameters['time_from'] = time_from;
        grid.parameters['time_to'] = time_to;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
    }

    function showEditWin(express_id) {
        clearEditForm();
        if(express_id) {
            $('#statusDiv').show();
            $('#title_edit').text("编辑快递");
            var i = layer.load();
            $.post('${ctx}/express.do?method=getExpressById', {express_id : express_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#express_id_edit').val(json.express_id);
                        $('#community_id_edit').val(json.community_id);
                        $("input[name=express_com][value='" + json.express_com + "']").prop('checked', true);
                        $('#express_no_edit').val(json.express_no);
                        $('#user_phone_edit').val(json.user_phone);
                        $('#express_info_edit').val(json.express_info);
                        $("#editForm input[name='status'][value=" + json.status + "]").prop("checked",true);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#statusDiv').hide();
        }
        $('#editWin').modal({backdrop: 'static'});
    }

    function saveExpress() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/express.do?method=saveExpress', $('#editForm').serialize(), function(data) {
                    if(data != null && data == 'success') {
                        layer.alert("操作成功！");
                        $('#editWin').modal('hide');
                        grid.load();
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(i);
                });
            });
        }
    }

    function clearEditForm() {
        $('#title_edit').text("录入快递");
        $('#express_id_edit').val('');
        $('input[name=express_com]').prop('checked', false);
        $('#express_no_edit').val('');
        $('#user_phone_edit').val('');
        $('#express_info_edit').val('');
        $("#editForm input[name='status'][value=2]").prop("checked",true);
    }

    function showPrint(express_id) {
        var newWin = window.open ('${ctx}/express.do?method=view&eid=' + express_id,
                'newwindow',
                'height=350, width=350, top=300,left=500,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
        //newWin.print();
    }

    function signExpress(express_id) {
        var ids = '';
        if(!express_id) {
            //批量签收
            var records = grid.getCheckedRecords();
            if(records.length == 0) {
                layer.alert("请先选中需要签收的快递！");
            } else {
                $.each(records, function (i, v) {
                    ids += v.express_id + ',';
                });
                if (ids != '') {
                    ids = ids.substring(0, ids.length - 1);
                }
            }
        } else {
            ids = express_id;
        }

        layer.confirm("确定要签收吗？", function() {
            var i = layer.load();
            $.post('${ctx}/express.do?method=signExpress', {ids : ids}, function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    grid.load();
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }

    function removeExpress(express_id) {
        layer.confirm("确定要删除吗？", function() {
            var i = layer.load();
            $.post('${ctx}/express.do?method=removeExpress', {express_id : express_id}, function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    grid.load();
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }
</script>

</body>

</html>