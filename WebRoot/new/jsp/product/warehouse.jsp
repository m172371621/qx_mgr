<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="margin-right:13px;">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>仓库商品一览</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="product_name_q" class="col-sm-1 control-label text-right">商品名称</label>
                            <div class="col-sm-2">
                                <input type="text" id="product_name_q" class="form-control"/>
                            </div>
                            <label for="community_id_q" class="col-sm-1 control-label text-right">仓库</label>
                            <div class="col-sm-2">
                                <select id="community_id_q" class="form-control" onchange="changeCommunity()">
                                    <c:forEach items="${communityList}" var="community">
                                        <option value="${community.community_id}"
                                                <c:if test="${community.org_info_type == 1}">data-is_total="1"</c:if>
                                                <c:if test="${community.org_info_type != 1}">data-is_total="0"</c:if>
                                            >${community.community_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="reloadTree()">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default">
                            商品入库
                        </button>
                    </div>

                    <div class="row">
                        <div class="col-sm-4">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                        <div class="col-sm-8">
                            <form role="form" class="form-horizontal" id="productForm">
                                <input type="hidden" id="product_id" name="product_id">
                                <input type="hidden" id="community_id" name="community_id">
                                <!-- 是否是总库商品 -->
                                <input type="hidden" id="is_total" name="is_total">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">商品名</label>
                                    <div class="col-sm-5">
                                        <p class="form-control-static" id="product_name"></p>
                                    </div>
                                    <div class="col-sm-3 col-sm-offset-1">
                                        <img id="picture" style='max-width: 100px;max-height: 100px;'/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">规格</label>
                                    <div class="col-sm-4">
                                        <p class="form-control-static" id="spec"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">商品进价</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" id="in_price"></p>
                                    </div>
                                    <label class="col-sm-3 control-label text-right">参考售价</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" id="price"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">商品分类</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" id="product_service"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">库存数量</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" id="stock_num"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label text-right">调拨数量</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="amount" name="amount" data-rule="required;range[0~]">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary">编辑商品</button>
                                        <button type="button" class="btn btn-primary" onclick="addDBTemp()">加入调拨清单</button>
                                        <button type="button" class="btn btn-primary" onclick="gotoDBTempPage()">查看调拨清单</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="display:none;position:fixed; right:0px; bottom:10px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="toTop" onclick="toTop()">
    返回顶部
</div>

<script type="text/javascript">
    function toTop() {
        $('body,html').animate({scrollTop: 0}, 100);
    }

    var setting = {
        async: {
            enable : true,
            url : "${ctx}/warehouse.do?method=loadWarehouseProduct"
        },
        data: {
            simpleData: {
                enable: true,
                rootPId : 's_0'
            }
        },
        view: {
            selectedMulti: false,
            nameIsHTML : true,
            showTitle : false
        },
        callback : {
            onClick : getProductInfo
            //onAsyncSuccess : expandNodes
        }
    };

    function reloadTree() {
        var community = $('#community_id_q option:selected');
        var is_total = community.data('is_total');
        var community_id = community.val();
        var param = {"is_total" : is_total, "community_id" : community_id};
        setting.async.otherParam = param;
        $.fn.zTree.init($("#tree"), setting);
    }

    function clearForm() {
        $('#product_id').val('');
        $('#community_id').val('');
        $('#is_total').val('');
        $('#product_name').text('');
        $('#picture').attr('src', '');
        $('#in_price').text('');
        $('#price').text('');
        $('#product_service').text('');
        $('#stock_num').text('');
        $('#amount').val('');
    }

    function getProductInfo(event, treeId, treeNode) {
        if(treeNode.id.indexOf('p_') == 0) {
            clearForm();
            var product_id = treeNode.id.replace('p_', '');
            var i = layer.load();
            $.post('${ctx}/warehouse.do?method=getProductById', {product_id : product_id, is_total : treeNode.is_total}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        //获取该节点的父节点
                        var product_service = "";
                        var p_node_1 = treeNode.getParentNode();
                        if(p_node_1) {
                            var p_node_2 = p_node_1.getParentNode();
                            if(p_node_2) {
                                product_service = p_node_2.name + " - " + p_node_1.name;
                            } else {
                                product_service = p_node_1.name;
                            }
                        }

                        $('#product_id').val(json.product_id);
                        $('#community_id').val(json.community_id);
                        $('#is_total').val(json.is_total);
                        $('#product_name').text(json.product_name);
                        $('#picture').attr('src', json.picture);
                        $('#in_price').text(json.in_price);
                        $('#price').text(json.price);
                        $('#product_service').text(product_service);
                        $('#stock_num').text(json.stock_num);
                        $('#amount').val(1);
                    }
                }
                layer.close(i);
            });
        }
    }

    $(function() {
        reloadTree();

        var community = $('#community_id_q option:selected');
        var is_total = community.data('is_total');
        if(is_total == 1) {
            $('.btn-group').show();
        } else {
            $('.btn-group').hide();
        }

        $(window).scroll(function () {
            if ($(this).scrollTop() != 0) {
                $('#toTop').fadeIn();
            } else {
                $('#toTop').fadeOut();
            }
        });
    });

    function changeCommunity() {
        var community = $('#community_id_q option:selected');
        var is_total = community.data('is_total');
        if(is_total == 1) {
            $('.btn-group').show();
        } else {
            $('.btn-group').hide();
        }

        reloadTree();
    }

    //加入调拨清单
    function addDBTemp() {
        if($('#productForm').isValid()) {
            var amount = $('#amount').val();
            if(amount > 0 && amount <= Number($('#stock_num').text())) {
                var i = layer.load();
                $.post('${ctx}/warehouse.do?method=addDBTemp', $('#productForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            var stock_num = json.data ? (json.data.realStock - json.data.tempCount) : 0;
                            $('#stock_num').text(stock_num);
                            layer.msg("操作成功！");
                        } else {
                            if(json.msg) {
                                layer.msg(json.msg);
                            } else {
                                layer.msg("操作失败！");
                            }
                        }
                    } else {
                        layer.msg("操作失败！");
                    }
                    layer.close(i);
                });
            } else {
                layer.msg("请输入正确的调拨数量！");
            }
        }
    }

    function gotoDBTempPage() {
        parent.openTab('${ctx}/warehouse.do?method=showListDBTempPage', '调拨清单详情');
    }
</script>

</body>

</html>