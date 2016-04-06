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
                    <h5>商品类别管理</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <input type="hidden" id="service_type" value="1">
                            <label for="service_name" class="col-sm-1 control-label text-right">名称</label>
                            <div class="col-sm-2">
                                <input type="text" id="service_name" class="form-control">
                            </div>
                            <label for="status" class="col-sm-1 control-label text-right">状态</label>
                            <div class="col-sm-2">
                                <select id="status" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">上架</option>
                                    <option value="2">下架</option>
                                </select>
                            </div>
                            <label for="ad_type" class="col-sm-1 control-label text-right">门店</label>
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
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
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
                <h4 class="modal-title" id="title_edit">编辑商品类别</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="service_id_edit" name="service_id">
                    <div class="form-group">
                        <label for="service_name_edit" class="col-md-4 control-label text-right">名称</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="service_name_edit" name="service_name" data-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="community_id_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <ui:simpleCommunitySelect id="community_id_edit" name="community_id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="parent_id_edit" class="col-md-4 control-label text-right">父类别</label>
                        <div class="col-md-5">
                            <ui:simplePsTag id="parent_id_edit" name="parent_id" communitySelect="community_id_edit" header="无" headerValue="0"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status_edit" class="col-md-4 control-label text-right">状态</label>
                        <div class="col-md-5">
                            <select id="status_edit" name="status" class="form-control">
                                <option value="1">上架</option>
                                <option value="2">下架</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="service_dec_edit" class="col-md-4 control-label text-right">描述</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="service_dec_edit" name="service_dec">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="service_order_edit" class="col-md-4 control-label text-right">排序</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="service_order_edit" name="service_order" data-rule="required;integer;">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveService()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var service_status = { 1 : '上架', 2 : '下架'};

    var gridColumn = [
        {id:'service_name', title:'名称', type:'string', columnClass:'text-center'},
        {id:'parent_name', title:'父类别', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs', codeTable : service_status},
        {id:'service_order', title:'排序', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(' + record.service_id + ')">修改</button>';
            content += '  ';
            //content += '<button class="btn btn-danger" onclick="removeService(' + record.service_id + ')">删除</button>';
            return content;
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/product.do?method=searchService',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function() {
        var service_type = $('#service_type').val();
        grid.parameters = new Object();
        grid.parameters['service_type'] = service_type;
        grid.load();
    });

    //自定义查询
    function search(){
        var service_name = $('#service_name').val();
        var community_id = $('#community_id').val();
        var service_type = $('#service_type').val();
        var status = $('#status').val();
        grid.parameters = new Object();
        grid.parameters['service_name'] = service_name;
        grid.parameters['community_id'] = community_id;
        grid.parameters['service_type'] = service_type;
        grid.parameters['status'] = status;
        grid.loadToFirst();
    }

    function clearEditForm() {
        $('#service_id_edit').val('');
        $('#service_name_edit').val('');
        //$('#community_id_edit').val('');
        //$('#parent_id_edit').val('');
        //$('#status_edit').val('');
        $('#service_dec_edit').val('');
        $('#service_order_edit').val('');
        $('#editForm').validator('cleanUp');
    }

    function showEditWin(service_id) {
        clearEditForm();
        if(service_id) {
            $('#title_edit').text('编辑商品类别');
            var i = layer.load();
            $.post('${ctx}/product.do?method=getServiceById', {service_id : service_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#service_id_edit').val(json.service_id);
                        $('#service_name_edit').val(json.service_name);
                        $('#community_id_edit').val(json.community_id);
                        $('#parent_id_edit').val(json.parent_id);
                        $('#status_edit').val(json.status);
                        $('#service_dec_edit').val(json.service_dec);
                        $('#service_order_edit').val(json.service_order);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#title_edit').text('新增商品类别');
        }

        $('#editWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function saveService() {
        if($('#editForm').isValid()) {
            layer.confirm("确定保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/product.do?method=saveService', $('#editForm').serialize(), function(data) {
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

    function removeService(service_id) {
        layer.confirm("确定要删除吗？", function() {

        });
    }
</script>

</body>

</html>