<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <script src="${ctx}/new/js/plugins/My97DatePicker/WdatePicker.js"></script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>红包组管理</h5>
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
                <h4 class="modal-title" id="title_edit">编辑红包组</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="group_id_edit" name="group_id">
                    <div class="form-group">
                        <label for="name_edit" class="col-sm-4 control-label text-right">名称</label>
                        <div class="col-sm-5">
                            <input class="form-control" id="name_edit" name="name" data-rule="required;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="expiretime_edit" class="col-sm-4 control-label text-right">过期时间</label>
                        <div class="col-sm-5">
                            <input class="form-control" id="expiretime_edit" name="expiretime" data-rule="required;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="couponSelect" class="col-sm-4 control-label text-right">红包</label>
                        <div class="col-sm-5">
                            <select class="form-control" onchange="addRow()" id="couponSelect">
                                <option value="">请选择</option>
                                <c:forEach items="${couponInfoList}" var="couponInfo">
                                    <option value="${couponInfo.coupon_id}">
                                        满
                                        <fmt:formatNumber type="number" value="${couponInfo.use_price}" maxFractionDigits="2"/>
                                        减
                                        <fmt:formatNumber type="number" value="${couponInfo.off_price}" maxFractionDigits="2"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-2">
                            <table class="table table-condensed">
                                <tbody id="couponBody">
                                </tbody>
                            </table>
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

<script type="text/html" id="rowTemplate">
    <tr>
        <td>
            [:= name :]
            <input type="hidden" name="coupon_id" value="[:= coupon_id :]"/>
        </td>
        <td><input name="amount" type="text" class="form-control text-center" value="[:= amount :]" placeholder="请输入红包数量" style="width: 130px;" data-rule="required; integer[+0];"/></td>
        <td>
            <button type="button" class="btn btn-outline btn-default" onclick="$(this).parents('tr').remove()">
                <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
            </button>
        </td>
    </tr>
</script>

<script type="text/javascript">
    var group_status = {0 : "<b class='text-danger'>有效</b>", 1 : '无效'};

    var gridColumn = [
        {id:'name', title:'名称', type:'string', columnClass:'text-center'},
        {id:'expiretime', title:'过期时间', type:'date', format : 'yyyy-MM-dd hh:mm:ss' ,columnClass:'text-center', hideType:'xs'},
        {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs', codeTable : group_status},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record) {
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.group_id + '\')">修改</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeCoupon(\'' + record.group_id + '\')">下线</button>';
            return content;
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/coupon.do?method=searchCouponGroup',
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
        $('#group_id_edit').val('');
        $('#name_edit').val('');
        $('#expiretime_edit').val('');
        $("#couponSelect option:first").prop("selected", 'selected');
        $('#couponBody').html('');
        $('#editForm').validator('cleanUp');
    }

    function showEditWin(group_id) {
        clearEditForm();
        if(group_id) {
            $('#title_edit').text("修改红包组");
            var i = layer.load();
            $.post('${ctx}/coupon.do?method=getCouponGroupInfo', {group_id : group_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#group_id_edit').val(json.group.group_id);
                        $('#name_edit').val(json.group.name);
                        $('#expiretime_edit').val($.fn.DtGrid.tools.dateFormat(new Date(json.group.expiretime), 'yyyy-MM-dd hh:mm:ss'));
                        $.each(json.couponList, function(index, v) {
                            var name = "满" + v.use_price + "减" + v.off_price;
                            var obj = {coupon_id: v.coupon_id, name: name, amount: v.amount};
                            $('#couponBody').append(tppl($('#rowTemplate').html(), obj));
                        });
                    }
                }
                layer.close(i);
            });
        } else {
            $('#title_edit').text("添加红包组");
        }

        $('#editWin').modal({ backdrop: 'static' });
    }

    function saveCoupon() {
        if($('#editForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i =layer.load();
                $.post('${ctx}/coupon.do?method=saveCouponGroup', $('#editForm').serialize(), function(data) {
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

    function removeCoupon(group_id) {
        layer.confirm("确定要下线吗？", function() {
            var i =layer.load();
            $.post('${ctx}/coupon.do?method=removeCouponGroup', {group_id : group_id}, function(data) {
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

    function addRow() {
        var coupon_id = $('#couponSelect').val();

        var flag = true;
        $('#couponBody input[name=coupon_id]').each(function(i, v) {
            if($(v).val() == coupon_id) {
                flag = false;
                layer.msg("已经添加该红包");
                return false;
            }
        });
        if(flag) {
            var name = $('#couponSelect option:selected').text();
            var obj = {coupon_id: coupon_id, name: name, amount: ''};
            $('#couponBody').append(tppl($('#rowTemplate').html(), obj));
        }
    }
</script>

</body>

</html>