<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <script src="${ctx}/new/js/plugins/suggest/bootstrap-suggest.min.js"></script>
    <style type="text/css">
        #rkTable th {text-align: center;}
        #rkTable td {text-align: center;}
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>总库商品入库</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="product_id" class="col-sm-1 control-label text-right">商品</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="product_id">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="submitData()">入库</button>
                            </div>
                        </div>
                    </form>

                    <form id="rkForm">
                    <table class="table" id="rkTable">
                        <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>规格</th>
                                <th>条码</th>
                                <th>入库单价</th>
                                <th>入库数量</th>
                                <th>到期时间</th>
                                <th>供应商</th>
                                <th>是否铺货</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 新增商品tr模板 -->
<script id="rowTemplate" type="text/html">
    <tr>
        <td>
            <input type="hidden" name="product_id" value="[:= product.product_id:]"/>
            [:= product.name :]
        </td>
        <td>[:= product.spec :]</td>
        <td>[:= product.barcode :]</td>
        <td>
            <input type="text" class="form-control" value="[:= product.pre_incomming_price :]" style="width: 80px;" name="unit_price"/>
        </td>
        <td><input type="text" class="form-control" value="1" style="width: 80px;" name="change_count"/></td>
        <td><input type="text" class="form-control" name="deadline" style="width: 120px;"/></td>
        <td>
            <select name="supplier_id" class="form-control">
                <option value="">--</option>
            [: for(var i = 0; i < supplier.length; i++) { :]
                <option value="[:= supplier[i].supplier_info_id :]">[:= supplier[i].supplier_info_name :]</option>
            [: } :]
            </select>
        </td>
        <td>
            <input type="hidden" name="puhuo_flag" value="0">
            <label class="checkbox-inline">
                <input type="checkbox" onclick="this.checked ? $(this).parent().prev().val(1) : $(this).parent().prev().val(0)">
            </label>
        </td>
        <td><input type="text" class="form-control" name="remark" style="width: 120px;"/></td>
        <td>
            <button class="btn btn-danger" type="button" onclick="removeRow(this)">删除</button>
        </td>
    </tr>
</script>

<script type="text/javascript">
    $(function() {
        $("#product_id").bsSuggest({
            url : "${ctx}/warehouse.do?method=searchTotalProductByKeyword&keyword=",
            getDataMethod : 'url',  //获取数据的方式，总是从 URL 获取
            allowNoKeyword : false, //关键字为空时不搜索
            showBtn : false,
            autoDropup : true,
            idField : 'product_id',
            keyField : 'name',
            effectiveFieldsAlias : {name : '名称', spec : '规格', barcode : '条码', pre_incomming_price : '进货价'},
            effectiveFields : ['spec', 'name', 'barcode', 'pre_incomming_price']
        }).on('onSetSelectValue', function(e, keyword, data) {
            addRow(data);
        });
    });

    function addRow(data) {
        //如已添加，则无需重复添加
        var flag = true;
        $('#rkTable input[name=product_id]').each(function(i, v) {
           if($(v).val() == data.product_id) {
               layer.msg("该商品已在列表中！");
               flag = false;
               return false;
           }
        });

        if(flag) {
            //获取供应商信息
            var i = layer.load();
            $.post('${ctx}/warehouse.do?method=findSupplier', {product_id : data.product_id}, function(result) {
                var _data = new Object();
                _data.product = data;
                _data.supplier = new Array();
                if(result) {
                    var json = eval('(' + result + ')');
                    if(json) {
                        _data.supplier = json;
                    }
                }

                var html = tppl($('#rowTemplate').html(), _data);
                $("#rkTable tbody").append(html);
                $("input[name=deadline]").datepicker({autoclose : true, todayHighlight : true});

                layer.close(i);
            });
        }
    }

    function removeRow(obj) {
        $(obj).parents("tr").remove();
    }

    function submitData() {
        layer.confirm("提交后不允许修改，请仔细查看是否确认提交！", function() {
            postData();
        });
    }

    function postData() {
        if($('#rkTable tbody tr').length > 0) {
            var ii = layer.load();
            $.ajax({
                type : 'POST',
                url : "${ctx}/warehouse.do?method=intoWarehouse",
                data : $('#rkForm').serialize(),
                success: function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功！", function() {
                                location.reload();
                            });
                        } else {
                            layer.alert("操作失败！");
                        }
                    } else {
                        layer.alert("操作失败！");
                    }
                },
                complete : function() {
                    layer.close(ii);
                }
            });
        } else {
            layer.alert("请先添加商品！");
        }
    }
</script>

</body>

</html>