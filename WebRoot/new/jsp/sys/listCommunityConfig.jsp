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
                        <h5>配置项管理</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="config_id" class="col-sm-1 control-label text-right">名称</label>
                                <div class="col-sm-2">
                                    <input type="text" id="config_id" class="form-control">
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
                    <h4 class="modal-title" id="title_edit">编辑配置项</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="editForm">
                        <input type="hidden" id="community_id_old_edit" name="community_id_old"/>
                        <input type="hidden" id="config_id_old_edit" name="config_id_old"/>
                        <div class="form-group">
                            <label for="community_id_edit" class="col-md-4 control-label text-right">门店</label>
                            <div class="col-md-5">
                                <ui:simpleCommunitySelect id="community_id_edit" name="community_id"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_id_edit" class="col-md-4 control-label text-right">名称</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_id_edit" name="config_id" data-rule="required;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_value_edit" class="col-md-4 control-label text-right">值</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_value_edit" name="config_value" data-rule="required;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_dec_edit" class="col-md-4 control-label text-right">备注</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_dec_edit" name="config_dec">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_ext1_edit" class="col-md-4 control-label text-right">配置1</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_ext1_edit" name="config_ext1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_ext2_edit" class="col-md-4 control-label text-right">配置2</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_ext2_edit" name="config_ext2">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="config_ext3_edit" class="col-md-4 control-label text-right">配置3</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="config_ext3_edit" name="config_ext3">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveConfig()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var gridColumn = [
            {id:'config_id', title:'名称', type:'string', columnClass:'text-center'},
            {id:'config_value', title:'值', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'config_dec', title:'备注', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'config_ext1', title:'配置1', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'config_ext2', title:'配置2', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'config_ext3', title:'配置3', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record) {
                var content = '';
                content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.community_id + '\', \'' + record.config_id + '\')">修改</button>';
                content += '  ';
                content += '<button class="btn btn-danger" onclick="removeConfig(\'' + record.community_id + '\', \'' + record.config_id + '\')">删除</button>';
                return content;
            }}

        ];
        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/sys.do?method=searchCommunityConfig',
            columns : gridColumn,
            gridContainer : 'table',
            toolbarContainer : 'toolBar',
            tools : ''
        };
        var grid = $.fn.DtGrid.init(gridOption);
        $(function(){
            grid.load();
        });

        //自定义查询
        function search(){
            var config_id = $('#config_id').val();
            var community_id = $('#community_id').val();
            grid.parameters = new Object();
            grid.parameters['config_id'] = config_id;
            grid.parameters['community_id'] = community_id;
            grid.loadToFirst();
        }

        function clearEditForm() {
            $('#community_id_old_edit').val('');
            $('#config_id_old_edit').val('');

            $('#config_id_edit').val('');
            $('#config_value_edit').val('');
            $('#config_dec_edit').val('');
            $('#config_ext1_edit').val('');
            $('#config_ext2_edit').val('');
            $('#config_ext3_edit').val('');
            $('#editForm').validator("cleanUp");
        }

        function showEditWin(community_id, config_id) {
            clearEditForm();
            if(config_id && community_id) {
                $('#title_edit').text('修改配置项');
                var i = layer.load();
                $.post('${ctx}/sys.do?method=getCommunityConfigById', {config_id : config_id, community_id : community_id}, function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json) {
                            $('#community_id_old_edit').val(json.community_id);
                            $('#config_id_old_edit').val(json.config_id);
                            $('#community_id_edit').val(json.community_id);
                            $('#config_id_edit').val(json.config_id);
                            $('#config_value_edit').val(json.config_value);
                            $('#config_dec_edit').val(json.config_dec);
                            $('#config_ext1_edit').val(json.config_ext1);
                            $('#config_ext2_edit').val(json.config_ext2);
                            $('#config_ext3_edit').val(json.config_ext3);
                        }
                    }
                    layer.close(i);
                })
            } else {
                $('#title_edit').text('添加配置项');
            }

            $('#editWin').modal({
                backdrop: 'static'  //点击窗体外不会关闭窗口
            });
        }

        function saveConfig() {
            if($('#editForm').isValid()) {
                layer.confirm("确定要保存吗？", function() {
                    var i = layer.load();
                    $.post('${ctx}/sys.do?method=saveCommunityConfig', $('#editForm').serialize(), function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功！");
                                $('#editWin').modal('hide');
                                grid.load();
                            } else {
                                layer.alert("操作失败！");
                            }
                        }
                        layer.close(i);
                    });
                });
            }
        }

        function removeConfig(community_id, config_id) {
            if(community_id && config_id) {
                layer.confirm("确定要删除吗？", function() {
                    var i = layer.load();
                    $.post('${ctx}/sys.do?method=removeCommunityConfig', {community_id : community_id, config_id : config_id}, function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功！");
                                grid.load();
                            } else {
                                layer.alert("操作失败！");
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