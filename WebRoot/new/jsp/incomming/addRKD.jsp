<%@ page import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<%
    Integer stock_type = CommonUtil.safeToInteger(request.getAttribute("stock_type"), null);
    String stock_name = "";
    if(stock_type != null) {
        if(stock_type.intValue() == 1) {
            stock_name = "入库单";
        } else if(stock_type.intValue() == 2) {
            stock_name = "调入单";
        }
    }
    request.setAttribute("stock_name", stock_name);
%>
<html>

<head>
    <style type="text/css">
        #rkTable th {text-align: center;}
        #rkTable td {text-align: center;}
    </style>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/autocomplete/jquery.autocomplete.css"/>
    <script type='text/javascript' src='${ctx}/js/autocomplete/jquery.autocomplete.js'></script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>添加${stock_name}明细</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <input type="hidden" id="stock_type" value="${stock_type}"/>
                        <div class="form-group">
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="请选择"/>
                            </div>
                            <label for="product_name" class="col-sm-1 control-label text-right">商品</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="product_name" id="product_name" placeholder="请先选择门店"/>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="submitData()">提交</button>
                            </div>
                        </div>
                    </form>

                    <table class="table" id="rkTable">
                        <thead>
                        <tr>
                            <th>商品名称</th>
                            <th>条码</th>
                            <th>参考售价</th>
                            <th>门店</th>
                            <th style="width: 100px;">数量</th>
                            <th style="width: 120px;">进货单价</th>
                            <th style="width: 120px;">到期时间</th>
                            <th style="width: 80px;">是否铺货</th>
                            <th>供应商</th>
                            <th style="width: 150px;">备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#product_name").autocomplete('${ctx}/incommmingOrder.do?method=searchProductByKeyword', {
            width: 500,
            multiple: false,
            matchContains: true,
            cacheLength : 0,
            extraParams : {community_id : function() {
               return $('#community_id').val();
            }},
            formatItem: function (row, i, max) {     //显示格式
                return "<span style='width:300px'>名称：" + row.name + "</span>&nbsp;&nbsp;" +
                        "<span style='width:100px'>条码：" + row.barcode + "</span>&nbsp;&nbsp;" +
                        "<span style='width:50px'>售价：" + row.price + "</span>&nbsp;&nbsp;" +
                        "<span style='width:50px'>门店：" + row.communityName + "</span>";
            },
            formatMatch: function (row, i, max) {    //以什么数据作为搜索关键词,可包括中文,
                return row.value;
            },
            formatResult: function (row) {      //返回结果
                return row.value;
            },
            parse: function (data) {
                var rows = new Array();
                var dataObj = eval("(" + data + ")");
                var datas = dataObj.data;
                for (var i = 0; i < datas.length; i++) {
                    rows[i] = {
                        data: datas[i],
                        value: datas[i].name,
                        price: datas[i].price,
                        result: datas[i].name,
                        product_id: datas[i].product_id
                    };
                }
                return rows;
            }
        }).result(function (event, data, formatted) {
            addGoods(data);
        });
    });

    function addGoods(data) {
        var community_id = data.community_id;
        var product_id = data.product_id;
        var msg = '';
        $('table input[name=community_id]').each(function(i, v) {
            if(community_id != $(v).val()) {
                msg = '只能对一个门店的商品进行操作！';
                return false;
            }
        });

        if(msg == '') {
            $('table input[name=product_id]').each(function (i, v) {
                if (product_id == $(v).val()) {
                    msg = '该商品已在列表中！';
                    return false;
                }
            });
        }

        if(msg != '') {
            layer.alert(msg);
        } else {
            var str = '<tr>'
                    + '<input type="hidden" name="flag" value="2" />'
                    + '<input type="hidden" name="community_id" value="' + data.community_id + '" />'
                    + '<input type="hidden" name="product_id" value="' + data.product_id + '" />'
                    + '<td>' + data.name + '</td>	'
                    + '<td>' + data.barcode + '</td>'
                    + '<td>' + data.price + '</td>	'
                    + '<td>' + data.communityName + '</td>'
                    + '<td><input type="text" class="form-control" name="change_count" value="1" onkeyup="checkCount(this)"/></td>	'
                    + '<td><input type="text" class="form-control"  name="unit_price" value="1"/></td>	'
                    + '<td><input type="text" class="form-control"  name="deadline" /></td>	'

                    + '<td>'
                    + '<input type="hidden" name="puhuo_flag" value="0">'
                    + '<label class="checkbox-inline">'
                    + '<input type="checkbox" onclick="this.checked ? $(this).parent().prev().val(1) : $(this).parent().prev().val(0)">'
                    + '</label>'
                    + '</td>'

                    + '<td>'
                    + '<select class="form-control" name="supplier_id">'
                    + '<option value="">--</option>'
                    <c:forEach items="${suppliers}" var="supplier">
                        + '<option value="${supplier.value}">${supplier.key}</option>'
                    </c:forEach>
                    + '</select>'
                    + '</td>'

                    + '<td><input type="text" class="form-control"  name="remark" /></td>	'
                    + '<td><button type="button" onclick="del(this)" class="btn btn-primary">删除</button></td></tr>';
            $("table tbody").append(str);
            $("input[name=deadline]").datepicker({autoclose : true, todayHighlight : true});
            $("#product_name").val("");
            $("#product_name").focus();
        }
    }

    function del(obj) {
        $(obj).parents("tr").remove();
    }

    function checkCount(obj) {
        if(parseInt($(obj).val())<0){
            layer.alert("不允许输入负数，请修改数量！");
            $(obj).val("0");
        }
    }

    function submitData() {
        layer.confirm("提交后不允许修改，请仔细查看是否确认提交！", function() {
            postData();
        });
    }

    function postData() {
        var msg = "";
        var stock_type = $("#stock_type").val();
        var community_id = $('input[name=community_id]').eq(0).val();
        var products=[];
        $.each($('tbody tr'),function(index,item) {
            var obj={};
            var flag=$(this).find('[name="flag"]').val();
            if(flag=="2"){
                obj.product_id=$(this).find('[name="product_id"]').val();
                obj.change_count=$(this).find('[name="change_count"]').val();
                obj.unit_price=$(this).find('[name="unit_price"]').val();
                obj.deadline = $(this).find('[name="deadline"]').val();
                obj.puhuo_flag = $(this).find('[name="puhuo_flag"]').val();
                obj.supplier_id = $(this).find('[name="supplier_id"]').val();
                obj.remark = $(this).find('[name="remark"]').val();
                products.push(obj);
                if(obj.puhuo_flag == 1 && (obj.supplier_id == null || obj.supplier_id == '')) {
                    msg = "铺货的商品必须选择供应商！";
                    return false;
                }
            }
        });

        if(products.length>0) {
            if(msg != '') {
                layer.alert(msg);
                return;
            }
            var ii = layer.load();
            $.ajax({
                type: 'POST',
                url: "${ctx}/incommmingOrder.do?method=add_incomming_detailV4",
                data:{products:JSON.stringify(products),stock_type:stock_type,community_id : community_id},
                success: function(data) {
                    var dataObj = eval("("+data+")");
                    if(dataObj.result_code=="0"){
                        layer.alert("操作成功！", function() {
                            location.reload();
                        });
                    } else {
                        layer.alert("操作失败！");
                    }
                },
                complete : function() {
                    layer.close(ii);
                }
            });
        } else {
            layer.alert("请选择入库商品");
        }
    }
</script>

</body>

</html>