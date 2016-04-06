<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        dl {
            margin-top: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="margin-right:13px;">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>购物车</h5>
                </div>
                <div class="ibox-content">

                    <c:if test="${fn:length(cartList) == 0}">
                        <tr>
                            <td colspan="8">
                                <p class="text-center">
                                    购物车空空的哦~，去看看心仪的商品吧~
                                </p>
                            </td>
                        </tr>
                    </c:if>


                    <c:if test="${fn:length(cartList) > 0}">
                        <div class="table-responsive">
                            <table class="table table-striped text-center">
                                <thead>
                                <tr>
                                    <th>
                                        <%--<input type="checkbox" class="i-checks" onchange="checkAll(this)">--%>
                                    </th>
                                    <th colspan="3" class="text-center">商品</th>
                                    <th class="text-center">单价（元）</th>
                                    <th class="text-center">数量</th>
                                    <th class="text-center">小计（元）</th>
                                    <th class="text-center">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${cartList}" var="cart">
                                    <tr data-cartid="${cart.purchase_cart_id}">
                                        <td>
                                            <%--<input type="checkbox" class="i-checks">--%>
                                            <input type="hidden" name="product_id" value="${cart.product_id}" />
                                        </td>
                                        <td>
                                            <img src="${cart.thumbnail}" style="max-width: 100px;max-height: 100px;"/>
                                        </td>
                                        <td>
                                            ${cart.product_name}
                                        </td>
                                        <td>
                                            <dl class='dl-horizontal'>
                                                <dt>单位</dt><dd class="text-left">${cart.unit}</dd>
                                                <dt>规格</dt><dd class="text-left">${cart.spec}</dd>
                                                <dt>预期进价</dt><dd class="text-left">${cart.price}</dd>
                                                <dt>指导售价</dt><dd class="text-left">${cart.market_price}</dd>
                                                <dt>产地</dt><dd class="text-left">${cart.place}</dd>
                                                <dt>预期利润率</dt><dd class="text-left"></dd>
                                                <dt>近一个月销量</dt><dd class="text-left"></dd>
                                            </dl>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${cart.product_price}" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control text-center" style="width: 100px;"
                                                   value="<fmt:formatNumber value="${cart.product_amount}" maxFractionDigits="2"/>"
                                                   name="product_amount" oninput="calcPrice(this)"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${cart.product_price * cart.product_amount}" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-outline btn-default"
                                                    onclick="removeCart('${cart.purchase_cart_id}')">
                                                <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="6" class="text-left">
                                        <form role="form" class="form-horizontal">
                                            <div class="form-group">
                                                <label for="community_id" class="col-sm-1 control-label text-right">送货至</label>
                                                <div class="col-sm-3">
                                                    <ui:simpleCommunitySelect id="community_id"/>
                                                </div>
                                            </div>
                                        </form>
                                    </td>
                                    <td></td>
                                    <td>
                                        <button type="button" class="btn btn-primary" onclick="submitCart()">提交订单</button>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
</div>
<div style="display:none;position:fixed; right:0px; bottom:10px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="toTop" onclick="toTop()">
    返回顶部
</div>
<div style="position:fixed; right:0px; bottom:90px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="newTab" onclick="newTab()">
    新开窗口
</div>

<script type="text/javascript">
    function toTop() {
        $('body,html').animate({scrollTop: 0}, 100);
    }

    function newTab() {
        window.parent.open(location.href);
    }

    $(function() {
        calcTotalPrice();

        $(window).scroll(function () {
            if ($(this).scrollTop() != 0) {
                $('#toTop').fadeIn();
            } else {
                $('#toTop').fadeOut();
            }
        });

        if(window.top == window.self){
            $('#newTab').hide();
        }
    });

    function calcPrice(obj) {
        var product_amount = $(obj).val();
        var product_price = $(obj).parent().prev().text();
        var price = mul(product_amount, Number(product_price));
        $(obj).parent().next().text(price);
        calcTotalPrice();
    }

    /**
     * 计算并显示所有商品总价之和
     * */
    function calcTotalPrice() {
        var total_price = 0;
        $('table tr[data-cartid]').each(function(i,v){
            total_price = add(total_price, Number($(v).find('td').eq(-2).text()));
        });
        $('table tr').eq(-1).find('td').eq(-2).html("<b class='text-danger'>" + total_price + "</b>");
    }

    function checkAll(obj) {
        $('table input[type=checkbox]').prop('checked', obj.checked);
    }

    function removeCartBatch() {
        var ids = '';
        $('table input[type=checkbox]:gt(0)').each(function (i, v) {
            ids += $(this).parent().parent().data('cart_id') + ',';
        });
        if (ids == '') {
            layer.alert("请至少选中一件商品！");
        } else {
            ids = ids.substring(0, ids.length - 1);
            layer.confirm("确定要删除吗？", function () {
                removeCart(ids);
            });
        }
    }

    function removeCart(ids) {
        var i = layer.load();
        $.post('${ctx}/purchase.do?method=delCartInfo', {ids: ids}, function (data) {
            var json = eval('(' + data + ')');
            if (json && json.result_code == 0) {
                layer.msg("删除成功！");
                $.each(ids.split(','), function (index, id) {
                    $('tr[data-cartid=' + id + ']').remove();
                });
                calcTotalPrice();
            } else {
                layer.alert("删除失败！");
            }
            layer.close(i);
        });
    }

    function submitCart() {
        var community_id = $('#community_id').val();
        if(!community_id) {
            layer.alert("请选择送货门店！");
            return;
        }

        var product_id = '';
        var product_amount = '';

        $('input[name=product_id]').each(function(i, v) {
           product_id += $(v).val() + ',';
        });
        $('input[name=product_amount]').each(function(i, v) {
           product_amount += $(v).val() + ',';
        });
        if(product_id != '') {
            product_id = product_id.substring(0, product_id.length - 1);
        }
        if(product_amount != '') {
            product_amount = product_amount.substring(0, product_amount.length - 1);
        }

        if(product_id != '' && product_amount != '') {
            layer.confirm("确定要提交订单吗？", function() {
                var i = layer.load();
                $.post('${ctx}/purchase.do?method=submitCart', {community_id : community_id, product_id : product_id, product_amount : product_amount}, function(data) {
                    if(data != null && data == 'success') {
                        layer.alert("操作成功！", function() {
                            location.href = '${ctx}/purchase.do?method=showListPurchasePage';
                        });
                    } else {
                        layer.alert("操作失败！", function() {
                           location.reload();
                        });
                    }
                    layer.close(i);
                });
            });
        } else {
            layer.alert("购物中没有商品，无法提交！");
        }
    }
</script>

</body>

</html>