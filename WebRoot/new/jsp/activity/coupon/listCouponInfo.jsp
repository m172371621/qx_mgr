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
                    <h5>红包管理</h5>
                </div>
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
                <h4 class="modal-title" id="title_edit">编辑红包</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="coupon_id_edit" name="coupon_id">
                    <div class="form-group">
                        <label for="use_price_edit" class="col-sm-3 control-label text-right">满</label>
                        <div class="col-md-2">
                            <input class="form-control" id="use_price_edit" name="use_price" data-rule="required;range(0~);"/>
                        </div>
                        <label for="off_price_edit" class="col-sm-2 control-label text-right">减</label>
                        <div class="col-md-2">
                            <input class="form-control" id="off_price_edit" name="off_price" data-rule="required;range(0~);match(lte, use_price)"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveCoupon()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id:'use_price', title:'内容', type:'string', columnClass:'text-center', resolution : function(value, record) {
            return "满 <b class='text-danger'>" + record.use_price + "</b> 减 <b class='text-danger'>" + record.off_price + "</b>";
        }},
        {id:'createtime', title:'创建时间', type:'date', format : 'yyyy-MM-dd hh:mm:ss' ,columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record) {
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.coupon_id + '\')">修改</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeCoupon(\'' + record.coupon_id + '\')">删除</button>';
            return content;
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/coupon.do?method=searchCouponInfo',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function(){
        grid.load();
    });

    function search(){
        grid.loadToFirst();
    }

    function clearEditForm() {
        $('#coupon_id_edit').val('');
        $('#off_price_edit').val('');
        $('#use_price_edit').val('');
        $('#editForm').validator('cleanUp');
    }

    function showEditWin(coupon_id) {
        clearEditForm();
        if(coupon_id) {
            $('#title_edit').text("修改红包");
            var i = layer.load();
            $.post('${ctx}/coupon.do?method=getCouponInfoById', {coupon_id : coupon_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#coupon_id_edit').val(json.coupon_id);
                        $('#off_price_edit').val(json.off_price);
                        $('#use_price_edit').val(json.use_price);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#title_edit').text("添加红包");
        }

        $('#editWin').modal({ backdrop: 'static' });
    }

    function saveCoupon() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i =layer.load();
                $.post('${ctx}/coupon.do?method=saveCouponInfo', $('#editForm').serialize(), function(data) {
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

    function removeCoupon(coupon_id) {
        layer.confirm("确定要删除吗？", function() {
            var i =layer.load();
            $.post('${ctx}/coupon.do?method=removeCoupon', {coupon_id : coupon_id}, function(data) {
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