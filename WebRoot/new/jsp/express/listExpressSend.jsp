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
                    <h5>寄件管理</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-sm-1 control-label text-right">寄件人</label>
                            <div class="col-sm-2">
                                <input type="text" id="name" class="form-control">
                            </div>
                            <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
                            <div class="col-sm-2">
                                <input type="text" id="phone" class="form-control">
                            </div>
                            <label for="express_no" class="col-sm-1 control-label text-right">快递单号</label>
                            <div class="col-sm-2">
                                <input type="text" id="express_no" class="form-control">
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
                            <label class="col-sm-1 control-label text-right">寄件时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_from"/>
                                </div>
                            </div>
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
                            寄件
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
                <h4 class="modal-title" id="title_edit">寄件信息编辑</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="objid_edit" name="objid">
                    <div class="form-group">
                        <label for="community_id_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <ui:simpleCommunitySelect id="community_id_edit" name="community_id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label text-right">快递公司</label>
                        <div class="col-md-7">
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
                        <label for="name_edit" class="col-md-4 control-label text-right">寄件人</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="name_edit" name="name" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone_edit" class="col-md-4 control-label text-right">手机号</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="phone_edit" name="phone" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="express_price_edit" class="col-md-4 control-label text-right">金额</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="express_price_edit" name="express_price" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark_edit" class="col-md-4 control-label text-right">备注</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="remark_edit" name="remark">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveExpressSend()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id:'express_no', title:'快递单号', type:'string', columnClass:'text-center'},
        {id:'name', title:'寄件人', type:'string', columnClass:'text-center'},
        {id:'phone', title:'手机号', type:'string', columnClass:'text-center'},
        {id:'express_com_name', title:'快递公司', type:'string', columnClass:'text-center'},
        {id:'express_price', title:'金额', type:'string', columnClass:'text-center'},
        {id:'send_time', title:'寄件时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(' + record.objid + ')">编辑</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeExpressSend(' + record.objid + ')">删除</button>';
            return content;
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/express.do?method=searchExpressSend',
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
        var name = $('#name').val();
        var phone = $('#phone').val();
        var express_no = $('#express_no').val();
        var express_com = $('#express_com').val();
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        var community_id = $('#community_id').val();

        grid.parameters = new Object();
        grid.parameters['name'] = name;
        grid.parameters['phone'] = phone;
        grid.parameters['express_no'] = express_no;
        grid.parameters['express_com'] = express_com;
        grid.parameters['time_from'] = time_from;
        grid.parameters['time_to'] = time_to;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
    }

    function showEditWin(objid) {
        clearEditForm();
        if(objid) {
            $('#title_edit').text("录入寄件信息");
            var i = layer.load();
            $.post('${ctx}/express.do?method=getExpressSendById', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#objid_edit').val(json.objid);
                        $('#community_id_edit').val(json.community_id);
                        $("input[name=express_com][value='" + json.express_com + "']").prop('checked', true);
                        $('#express_no_edit').val(json.express_no);
                        $('#name_edit').val(json.name);
                        $('#phone_edit').val(json.phone);
                        $('#express_price_edit').val(json.express_price);
                        $('#remark_edit').val(json.remark);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#title_edit').text("编辑寄件信息");
        }
        $('#editWin').modal({backdrop: 'static'});
    }

    function saveExpressSend() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/express.do?method=saveExpressSend', $('#editForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功！");
                            $('#editWin').modal('hide');
                            grid.load();
                        } else {
                            layer.alert("操作失败！");
                        }
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(i);
                });
            });
        }
    }

    function clearEditForm() {
        $('#objid_edit').val('');
        $('input[name=express_com]').prop('checked', false);
        $('#express_no_edit').val('');
        $('#name_edit').val('');
        $('#phone_edit').val('');
        $('#express_price_edit').val('');
        $('#remark_edit').val('');
    }

    function removeExpressSend(objid) {
        layer.confirm("确定要删除吗？", function() {
            var i = layer.load();
            $.post('${ctx}/express.do?method=removeExpressSend', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("操作成功！");
                        grid.load();
                    } else {
                        layer.alert("操作失败！");
                    }
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