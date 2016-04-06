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
                        <h5>商品管理</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="name" class="col-sm-1 control-label text-right">名称</label>
                                <div class="col-sm-2">
                                    <input type="text" id="name" class="form-control">
                                </div>
                                <label for="status" class="col-sm-1 control-label text-right">状态</label>
                                <div class="col-sm-2">
                                    <select id="status" class="form-control">
                                        <option value="">全部</option>
                                        <option value="1">上架</option>
                                        <option value="2">下架</option>
                                    </select>
                                </div>
                                <label for="product_type" class="col-sm-1 control-label text-right">类型</label>
                                <div class="col-sm-2">
                                    <select id="product_type" class="form-control">
                                        <option value="">全部</option>
                                        <option value="1">普通</option>
                                        <option value="2">促销</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                                <div class="col-sm-2">
                                    <ui:simpleCommunitySelect id="community_id" header="全部"/>
                                </div>
                                <label for="sid" class="col-sm-1 control-label text-right">所属品类</label>
                                <div class="col-sm-2">
                                    <ui:simplePsTag communitySelect="community_id" id="sid" header="全部"/>
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
                            <button type="button" class="btn btn-outline btn-default" onclick="addProduct()">
                                新增商品
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="changeStatus(1)">
                                批量上架
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="changeStatus(2)">
                                批量下架
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="showChangeSidWin()">
                                切换品类
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="showSidWin()">
                                品类操作
                            </button>
                        </div>
                        <div id="table" class="dt-grid-container"></div>
                        <div id="toolBar" class="dt-grid-toolbar-container"></div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 切换品类模态窗口 -->
    <div class="modal fade" id="changeSidWin" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">切换品类</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="changeSidForm">
                        <div class="form-group">
                            <label for="change_to_sid" class="col-md-4 control-label text-right">切换到</label>
                            <div class="col-md-5">
                                <ui:simplePsTag id="change_to_sid" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="changeSid()">确定</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 品类操作模态窗口 -->
    <div class="modal fade" id="sidWin" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">品类操作</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="sidForm">
                        <div class="form-group">
                            <label for="sid_community_id" class="col-md-4 control-label text-right">门店</label>
                            <div class="col-md-5">
                                <ui:simpleCommunitySelect id="sid_community_id"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sid_from" class="col-md-4 control-label text-right">商品类别</label>
                            <div class="col-md-5">
                                <ui:simplePsTag id="sid_from" communitySelect="sid_community_id"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sid_to" class="col-md-4 control-label text-right">切换到</label>
                            <div class="col-md-5">
                                <ui:simplePsTag id="sid_to" communitySelect="sid_community_id"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <button type="button" class="btn btn-primary" onclick="operateSid(1)">上架</button>
                    <button type="button" class="btn btn-primary" onclick="operateSid(2)">下架</button>
                    <button type="button" class="btn btn-primary" onclick="operateSid(3)">切换</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var product_status = {1 : '上架', 2 : '下架'};
        var product_type = { 1 : '普通', 2 : '促销'};

        var gridColumn = [
            {id:'name', title:'名称', type:'string', columnClass:'text-center'},
            {id:'service_name', title:'所属品类', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'product_type', title:'类型', type:'string', columnClass:'text-center', hideType:'xs', codeTable : product_type},
            {id:'price', title:'价格', type:'number', format : '#.##', columnClass:'text-center', hideType:'xs'},
            {id:'real_amount', title:'剩余数量', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs',codeTable:product_status},
            {id:'communityName', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                content += '<button class="btn btn-primary" onclick="editProduct(\'' + record.product_id + '\')">修改</button>';
                content += '  ';
                content += '<button class="btn btn-danger" onclick="removeProduct(\'' + record.product_id + '\')">删除</button>';
                return content;
            }}

        ];
        var gridOption = {
            check : true,
            ajaxLoad : true,
            loadURL : '${ctx}/product.do?method=searchProduct',
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
            var name = $('#name').val();
            var status = $('#status').val();
            var product_type = $('#product_type').val();
            var sid = $('#sid').val();
            var community_id = $('#community_id').val();
            grid.parameters = new Object();
            grid.parameters['name'] = name;
            grid.parameters['status'] = status;
            grid.parameters['product_type'] = product_type;
            grid.parameters['community_id'] = community_id;
            //防止bug出现 sid="null"
            if(sid) {
                grid.parameters['sid'] = sid;
            }
            grid.loadToFirst();
        }

        function removeProduct(product_id) {
            layer.confirm("确定要删除吗？", function(index) {
                var i = layer.load();
                $.ajax({
                    type: "post",
                    url: "${ctx}/product.do?method=removeProduct",
                    data: {product_id : product_id},
                    success: function(data) {
                        layer.alert("删除成功！");
                        grid.load();
                    },
                    error: function(err) {
                        layer.alert("删除失败！");
                    },
                    complete : function() {
                        layer.close(i);
                    }
                });
            });
        }

        //商品上下架  1.上架  2.下架
        function changeStatus(status) {
            var recodes = grid.getCheckedRecords();
            if(recodes.length == 0) {
                layer.alert("请先选择商品！");
            } else {
                layer.confirm("确定要" + (status == 1 ? "上架" : "下架") + "吗？", function() {
                    var ids = rtnCheckedProIds();
                    operateProduct(ids, null, null, status);
                })
            }
        }

        function rtnCheckedProIds() {
            var ids = '';
            var recodes = grid.getCheckedRecords();
            if(recodes.length > 0) {
                $.each(recodes, function(i, v) {
                    ids += v.product_id + ',';
                });
                if(ids != '') {
                    ids = ids.substring(0, ids.length - 1);
                }
            }
            return ids;
        }

        function operateProduct(ids, sid_from, sid_to, status) {
            var i = layer.load();
            $.post("${ctx}/product.do?method=operateProduct", {ids : ids, sid_from : sid_from, sid_to : sid_to, status : status}, function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    grid.load();
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        }

        function showChangeSidWin() {
            var recodes = grid.getCheckedRecords();
            if(recodes.length == 0) {
                layer.alert("请先选择商品！");
            } else {
                var community_id;
                var isSame = true;
                $.each(recodes, function(i, v) {
                    if(!community_id) {
                        community_id = v.community_id;
                    } else {
                        if(community_id != v.community_id) {
                            isSame = false;
                            return false;
                        }
                    }
                });

                if(isSame) {
                    loadSimplePs_change_to_sid(community_id);
                    $('#changeSidWin').modal({
                        backdrop: 'static'  //点击窗体外不会关闭窗口
                    });
                } else {
                    layer.alert("请选择同一门店的商品！");
                }
            }
        }

        function changeSid() {
            layer.confirm("确定要切换吗？", function() {
                var sid = $('#change_to_sid').val();
                var ids = rtnCheckedProIds();
                operateProduct(ids, null, sid, null);
                $('#changeSidWin').modal('hide');
            });
        }

        function showSidWin() {
            changePs();
            $('#sidWin').modal({
                backdrop: 'static'  //点击窗体外不会关闭窗口
            });
        }

        function changePs() {
            //初始化品类操作modal
            var community_id = $('#sid_community_id').val();
            //initProductService(community_id, 'sid_from');
            //initProductService(community_id, 'sid_to');
        }

        function editProduct(product_id) {
            window.parent.openTab('${ctx}/product.do?method=viewProduct&product_id=' + product_id, '编辑商品');
        }

        function addProduct() {
            window.parent.openTab('${ctx}/product.do?method=viewProduct', '新增商品');
        }

        function initProductService(community_id, pid_select, header, value) {
            var i = layer.load();
            $.post('${ctx}/product.do?method=productServiceSelect', {community_id : community_id}, function (data) {
                if(data) {
                    if(header) {
                        data = "<option value=''>" + header + "</option>" + data;
                    }
                    $('#' + pid_select).html(data);
                    if(value) {
                        $('#' + pid_select).val(value);
                    }
                }
                layer.close(i);
            });
        }

        /**
         * 品类操作  type：1、上架  2、下架  3、切换
         * */
        function operateSid(type) {
            var community_id = $('#sid_community_id').val();
            var sid_from = $('#sid_from').val();
            var sid_to = $('#sid_to').val();
            if(type == 1 || type == 2) {
                if(community_id && sid_from) {
                    layer.confirm("确定要" + (type == 1 ? "上架" : "下架") + "吗？", function() {
                        operateProduct(null, sid_from, null, type);
                    });
                }
            } else if(type == 3) {
                if(community_id && sid_from && sid_to) {
                    layer.confirm("确定要切换吗？", function() {
                        operateProduct(null, sid_from, sid_to, null);
                    });
                }
            }
        }
    </script>

</body>

</html>