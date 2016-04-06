<%@ page import="java.util.*" %>
<%@ page import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<%
    List<Map> list = (List<Map>)request.getAttribute("list");
    Integer community_id = null;
    String community_name = "";
    if(list != null && list.size() > 0) {
        community_id = CommonUtil.safeToInteger(((Map) list.get(0).get("product")).get("community_id"), null);
        community_name = CommonUtil.safeToString(((Map) list.get(0).get("product")).get("community_name"), "");
    }
    request.setAttribute("community_id", community_id);
    request.setAttribute("community_name", community_name);
%>
<html>

<head>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>调拨清单详情（<b class="text-danger">${community_name}</b>）</h5>
                </div>
                <div class="ibox-content">
                    <form id="dbForm">
                        <table class="table table-condensed">
                            <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>规格</th>
                                    <th>商品分类</th>
                                    <th>进货单价</th>
                                    <th>库存</th>
                                    <th style="width: 120px;">调拨数量</th>
                                    <th style="width: 100px;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${list}" var="data">
                                    <tr class="active" data-objid="${data.temp.objid}">
                                        <td>${data.product.name}</td>
                                        <td>${data.product.unit}</td>
                                        <td>${data.product.service_name}</td>
                                        <td></td>
                                        <td>
                                            <fmt:formatNumber type="number" value="${data.product.real_stock_sum}" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" value="<fmt:formatNumber type="number" value="${data.temp.amount}" maxFractionDigits="2"/>" style="width: 100px;"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-danger" onclick="removeRow(this, ${data.temp.objid})">删除</button>
                                        </td>
                                    </tr>
                                    <c:forEach items="${data.batchStock}" var="bs">
                                        <tr data-objid="${data.temp.objid}">
                                            <td>
                                                <input type="hidden" name="batch_serial" value="${bs.batch_serial}">
                                                <input type="hidden" name="product_id" value="${bs.product_id}">
                                                <input type="hidden" name="community_id" value="${bs.community_id}">
                                                <input type="hidden" name="is_total" value="${data.temp.is_total}">
                                                <input type="hidden" name="pre_stock_count" value="${bs.order_current_sum}">
                                                <input type="hidden" name="unit_price" value="${bs.incommint_price}">
                                            </td>
                                            <td></td>
                                            <td></td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${bs.incommint_price}" maxFractionDigits="2"/>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${bs.order_current_sum}" maxFractionDigits="2"/>
                                            </td>
                                            <td>
                                                <input type="text" name="change_count" class="form-control" style="width: 100px;">
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-danger" onclick="removeRow(this, ${data.temp.objid})">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                                <tr>
                                    <td colspan="6" class="text-left">
                                        <form role="form" class="form-horizontal">
                                            <div class="form-group">
                                                <label for="to_cid" class="col-sm-1 control-label text-right">调拨至</label>
                                                <div class="col-sm-3">
                                                    <ui:simpleCommunitySelect id="to_cid" name="to_cid"/>
                                                </div>
                                            </div>
                                        </form>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-primary" onclick="submitData()">确认调拨</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function submitData() {
        if($('#to_cid').val() == '${community_id}') {
            layer.msg("不能调拨至同一门店！");
        } else {
            layer.confirm("确认要调拨吗？", function() {
               var i = layer.load();
                $.post('${ctx}/warehouse.do?method=submitDBTemp', $('#dbForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功！", function() {
                                location.reload();
                            });
                        } else {
                            layer.alert(json.msg);
                        }
                    }
                    layer.close(i);
                });
            });
        }
    }

    function removeRow(obj, objid) {
        var deleteflag = false;
        if($(obj).parents('tr').hasClass('active')) {
            //直接删除该条商品记录
            deleteflag = true;
        } else {
            //假如该条商品下面只有这一个批次，则也直接删除该条商品记录，否则只删除该tr
            if($('table tr[data-objid=' + objid + ']').length <= 2) {
                deleteflag = true;
            }
        }

        layer.confirm("确定要删除吗？", function(index) {
            if(deleteflag) {
                var i = layer.load();
                $.post('${ctx}/warehouse.do?method=removeDBTemp', {objid : objid}, function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            $('table tr[data-objid=' + objid + ']').remove();
                            layer.msg("操作成功！");
                        } else {
                            layer.msg("操作失败！");
                        }
                    } else {
                        layer.msg("操作失败!");
                    }
                    layer.close(i);
                });
            } else {
                $(obj).parents('tr').remove();
            }
            layer.close(index);
        });
    }
</script>

</body>

</html>