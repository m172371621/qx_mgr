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
                    <h5>菜单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-4">
                            <ul id="menuTree" class="ztree"></ul>
                        </div>
                        <div class="col-sm-8">
                            <input type="hidden" id="parentid" value="0"/>
                            <div class="alert alert-info">
                                上级菜单：<b class="text-danger" id="parentMenu">主菜单</b>
                            </div>
                            <div class="btn-group">
                                <button type="button" class="btn btn-outline btn-default" onclick="showEditWin()">
                                    添加
                                </button>
                                <button type="button" class="btn btn-outline btn-default">
                                    排序
                                </button>
                                <%--<button type="button" class="btn btn-outline btn-default">
                                    <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                </button>--%>
                            </div>
                            <div id="table" class="dt-grid-container"></div>
                            <div id="toolBar" class="dt-grid-toolbar-container"></div>
                        </div>
                    </div>

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
                <h4 class="modal-title" id="title_edit">编辑菜单</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="objid_edit" name="objid">
                    <input type="hidden" id="parentid_edit" name="parentid">
                    <div class="form-group">
                        <label for="name_edit" class="col-md-4 control-label text-right">名称</label>
                        <div class="col-md-5">
                            <input type="text" id="name_edit" name="name" class="form-control" data-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="url_edit" class="col-md-4 control-label text-right">url</label>
                        <div class="col-md-5">
                            <input type="text" id="url_edit" name="url" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type_edit" class="col-md-4 control-label text-right">类型</label>
                        <div class="col-md-5">
                            <select class="form-control" id="type_edit" name="type" data-rule="required">
                                <option value="1">菜单</option>
                                <option value="2">操作</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="logo_edit" class="col-md-4 control-label text-right">图标</label>
                        <div class="col-md-5">
                            <input type="text" id="logo_edit" name="logo" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark_edit" class="col-md-4 control-label text-right">备注</label>
                        <div class="col-md-5">
                            <input type="text" id="remark_edit" name="remark" class="form-control">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveMenu()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var setting = {
        async: {
            enable : true,
            url : "${ctx}/sys.do?method=getMenuTree"
        },
        data: {
            simpleData: {
                enable: true,
                idKey : 'objid',
                pIdKey : 'parentid',
                rootPId : 0
            }
        },
        view: {
            selectedMulti: false
        },
        callback : {
            onClick : reloadMenu,
            onAsyncSuccess : expandNodes
        }
    };

    $(document).ready(function(){
        $.fn.zTree.init($("#menuTree"), setting);
    });

    var gridColumn = [
        {id:'name', title:'名称', type:'string', columnClass:'text-center'},
        {id:'url', title:'url', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.objid + '\')">修改</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeMenu(\'' + record.objid + '\')">删除</button>';
            return content;
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/sys.do?method=searchMenu',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function() {
        search();
        grid.load();
    });

    function search(){
        var parentid = $('#parentid').val();
        grid.parameters = new Object();
        grid.parameters['parentid'] = parentid;
        grid.loadToFirst();
    }

    function reloadMenu(event, treeId, treeNode) {
        $('#parentid').val(treeNode.objid);
        $('#parentMenu').text(treeNode.name);
        search();
    }

    function clearEditForm() {
        $('#objid_edit').val('');
        $('#name_edit').val('');
        $('#logo_edit').val('');
        $('#parentid_edit').val('');
        $('#type_edit').val(1);
        $('#url_edit').val('');
        $('#remark_edit').val('');

        $('#editForm').validator('cleanUp');
    }

    function showEditWin(objid) {
        clearEditForm();
        if(objid) {
            $('#title_edit').text("编辑菜单");
            var i = layer.load();
            $.post('${ctx}/sys.do?method=getMenuById', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#objid_edit').val(json.objid);
                        $('#name_edit').val(json.name);
                        $('#logo_edit').val(json.logo);
                        $('#parentid_edit').val(json.parentid);
                        $('#type_edit').val(json.type);
                        $('#url_edit').val(json.url);
                        $('#remark_edit').val(json.remark);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#parentid_edit').val($('#parentid').val());
            $('#title_edit').text("添加菜单");
        }
        $('#editWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function saveMenu() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/sys.do?method=saveMenu', $('#editForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json) {
                            if(json.result) {
                                layer.alert("操作成功！");
                                $('#editWin').modal('hide');
                                grid.load();
                                reloadTree();
                            } else {
                                layer.alert("操作失败！");
                            }
                        }
                    }
                    layer.close(i);
                });
            });
        }
    }

    function removeMenu(objid) {
        layer.confirm("确定要删除吗？", function() {
            var i = layer.load();
            $.post('${ctx}/sys.do?method=removeMenu', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        if(json.result) {
                            layer.alert("操作成功！");
                            grid.load();
                            reloadTree();
                        } else {
                            layer.alert("操作失败！");
                        }
                    }
                }
                layer.close(i);
            });
        });
    }

    var nodes = new Array();  //展开的节点
    function reloadTree() {
        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        //刷新之前记录节点的展开情况
        nodes.length = 0;   //清空nodes
        nodes = treeObj.getNodesByParam("open", true, null);

        treeObj.reAsyncChildNodes(null, "refresh");
    }

    function expandNodes(event, treeId, treeNode, msg) {
        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        if(nodes.length > 0) {
            $.each(nodes, function(i, v) {
                var node = treeObj.getNodeByParam("objid", v.objid, null);
                console.log(node);
                treeObj.expandNode(node, true);
            });
        }
    }
</script>

</body>

</html>