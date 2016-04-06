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
                        <h5>预约管理</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="user_name" class="col-sm-1 control-label text-right">用户名</label>
                                <div class="col-sm-2">
                                    <input type="text" id="user_name" class="form-control">
                                </div>
                                <label for="user_phone" class="col-sm-1 control-label text-right">手机</label>
                                <div class="col-sm-2">
                                    <input type="text" id="user_phone" class="form-control">
                                </div>
                                <label for="service_name" class="col-sm-1 control-label text-right">服务名称</label>
                                <div class="col-sm-2">
                                    <input type="text" id="service_name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">时间</label>
                                <div class="col-sm-4">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="form-control" id="beginTime"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="form-control" id="endTime"/>
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
                        <div id="table" class="dt-grid-container"></div>
                        <div id="toolBar" class="dt-grid-toolbar-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 编辑预约模态窗口 -->
    <div class="modal fade" id="editWin" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">预约管理编辑</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="editForm">
                        <input type="hidden" id="order_id_edit" name="order_id">
                        <div class="form-group">
                            <label for="community_name_edit" class="col-md-4 control-label text-right">门店</label>
                            <div class="col-md-5">
                                <p class="form-control-static" id="community_name_edit"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="service_name_edit" class="col-md-4 control-label text-right">服务名称</label>
                            <div class="col-md-5">
                                <p class="form-control-static" id="service_name_edit"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="user_name_edit" class="col-md-4 control-label text-right">用户名</label>
                            <div class="col-md-5">
                                <p class="form-control-static" id="user_name_edit"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="user_phone_edit" class="col-md-4 control-label text-right">手机</label>
                            <div class="col-md-5">
                                <p class="form-control-static" id="user_phone_edit"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="order_dec_edit" class="col-md-4 control-label text-right">备注</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="order_dec_edit" name="order_dec"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="order_status_edit" class="col-md-4 control-label text-right">状态</label>
                            <div class="col-md-5">
                                <select id="order_status_edit" name="order_status" class="form-control">
                                    <option value="1">预约</option>
                                    <option value="2">接受</option>
                                    <option value="3">完成</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="editServiceOrder()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var order_status = { 1 : '预约', 2 : "接受", 3 : "完成"};

        var gridColumn = [
            {id:'username', title:'用户名', type:'string', columnClass:'text-center'},
            {id:'phone', title:'手机', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'service_name', title:'服务名称', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'order_dec', title:'备注', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'order_status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs', codeTable : order_status},
            {id:'order_time', title:'时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.order_id + '\')">编辑</button>';
                content += '  ';
                content += '<button class="btn btn-danger" onclick="removeOrder(\'' + record.order_id + '\', \'' + record.user_id +  '\')">删除</button>';
                return content;
            }}

        ];
        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/task.do?method=searchServiceOrder',
            columns : gridColumn,
            gridContainer : 'table',
            toolbarContainer : 'toolBar',
            tools : ''
        };
        var grid = $.fn.DtGrid.init(gridOption);
        $(function(){
            grid.load();
            $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
        });

        //自定义查询
        function search(){
            var user_name = $('#user_name').val();
            var community_id = $('#community_id').val();
            var user_phone = $('#user_phone').val();
            var service_name = $('#service_name').val();
            var beginTime = $('#beginTime').val();
            var endTime = $('#endTime').val();
            grid.parameters = new Object();
            grid.parameters['user_name'] = user_name;
            grid.parameters['community_id'] = community_id;
            grid.parameters['user_phone'] = user_phone;
            grid.parameters['service_name'] = service_name;
            grid.parameters['beginTime'] = beginTime;
            grid.parameters['endTime'] = endTime;
            grid.loadToFirst();
        }

        function removeOrder(order_id, user_id) {
            layer.confirm("确定要删除吗？", function() {
                var i = layer.load();
                $.post('${ctx}/task.do?method=delService_order', {oid : order_id, uid : user_id}, function(data) {
                    if(data != null && Number(data) > 0) {
                        layer.alert("操作成功！");
                        grid.load();
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(i);
                });
            });
        }

        function showEditWin(order_id) {
            var i = layer.load();
            $.post('${ctx}/task.do?method=getServiceOrderById', {order_id : order_id}, function(data) {
                if(data != null && data != '') {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#order_id_edit').val(json.order_id);
                        $('#community_name_edit').text(json.community_name);
                        $('#service_name_edit').text(json.service_name);
                        $('#user_name_edit').text(json.username);
                        $('#user_phone_edit').text(json.phone);
                        $('#order_dec_edit').val(json.order_dec);
                        $('#order_status_edit').val(json.order_status);

                        $('#editWin').modal({
                            backdrop: 'static'  //点击窗体外不会关闭窗口
                        });
                    } else {
                        layer.alert("获取信息失败！");
                    }
                }
                layer.close(i);
            });
        }

        function editServiceOrder() {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/task.do?method=editServiceOrder', $('#editForm').serialize(), function(data) {
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
    </script>

</body>

</html>