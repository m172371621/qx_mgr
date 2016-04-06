<%@ page import="com.brilliantreform.sc.product.po.Product" %>
<%@ page import="com.brilliantreform.sc.product.po.ProductTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<%
    Product product =  (Product)request.getAttribute("product");
%>
<html>

<head>
    <style type="text/css">
        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio] {
            margin-top: 4px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><c:if test="${empty product}">新增</c:if><c:if test="${!empty product}">编辑</c:if>商品</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="productForm">
                        <input type="hidden" name="product_id" id="product_id" value="${product.product_id}">
                        <div class="form-group">
                            <label for="name" class="col-sm-4 control-label text-right">名称<b class="text-danger">*</b></label>
                            <div class="col-sm-3">
                                <input type="text" id="name" name="name" class="form-control" value="${product.name}" data-rule="required;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="community_id" class="col-sm-4 control-label text-right">门店<b class="text-danger">*</b></label>
                            <div class="col-sm-3">
                                <ui:simpleCommunitySelect id="community_id" name="community_id" value="${product.community_id}" event="initProductRule()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="service_id" class="col-sm-4 control-label text-right">类别<b class="text-danger">*</b></label>
                            <div class="col-sm-3">
                                <ui:simplePsTag id="service_id" name="service_id" communitySelect="community_id" value="${product.service_id}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="unit" class="col-sm-4 control-label text-right">单位</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="unit" name="unit" value="${product.unit}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="price" class="col-sm-4 control-label text-right">价格<b class="text-danger">*</b></label>
                            <div class="col-sm-3">
                                <input type="text" id="price" name="price" class="form-control" value="${product.price}" data-rule="required;range[0~];">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="market_price" class="col-sm-4 control-label text-right">APP专享价</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="market_price" name="market_price" value="${product.market_price}" data-rule="range[0~];">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label text-right">营销类型</label>
                            <div class="col-sm-8">
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_0" name="tags" value="0" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.WEIGHT)) out.print("checked");%> >称重
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_1" name="tags" value="1" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.APPONLY)) out.print("checked");%> >APP专享价
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_2" name="tags" value="2" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.WHOLESALE)) out.print("checked");%> onclick="toggleManJian()">满减
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_3" name="tags" value="3" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.NOQXCARD)) out.print("checked");%> >不支持区享卡
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_4" name="tags" value="4" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.NOSALE)) out.print("checked");%> >特价
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="tags_5" name="tags" value="5" <%if(product != null && ProductTags.checkTag(product.getTags(), ProductTags.PRESALE)) out.print("checked");%> >预售
                                </label>
                            </div>
                        </div>
                        <div class="form-group" id="manjianDiv" <%if(product == null || !ProductTags.checkTag(product.getTags(), ProductTags.WHOLESALE)) out.print("style='display:none;'");%>>
                            <label class="col-sm-4"></label>
                            <div class="col-sm-8">
                                <div class="row">
                                    <label class="col-sm-1 control-label">满</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" id="man" name="man" value="${fn:split(product.wholesale_price, '|')[0]}"/>
                                    </div>
                                    <label class="col-sm-1 control-label">减</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" id="jian" name="jian" value="${fn:split(product.wholesale_price, '|')[1]}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label text-right">送货上门</label>
                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" id="delivery_type2" name="delivery_type" value="2" <c:if test="${product.delivery_type == 2}">checked</c:if> onclick="toggleShsm(true)">上门
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="delivery_type1" name="delivery_type" value="1" <c:if test="${empty product || product.delivery_type == 1}">checked</c:if> onclick="toggleShsm(false)">不上门
                                </label>
                            </div>
                        </div>
                        <div class="form-group" id="shsmDiv" <c:if test="${empty product || product.delivery_type == 1}">style="display: none;" </c:if> >
                            <label for="delivery_price" class="col-sm-4 control-label text-right">送货上门价格</label>
                            <div class="col-sm-3">
                                <input type="text" id="delivery_price" name="delivery_price" class="form-control" value="${product.delivery_price}" data-rule="range[0~];">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label text-right">商品图片</label>
                            <div class="col-sm-8">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <input type="hidden" name="old_picture" value="${product.picture}"/>
                                        <input type="hidden" id="picture" name="picture" value="${product.picture}"/>
                                        <input type="file" class="form-control" name="file" id="picture_file"/>
                                    </div>
                                    <div class="col-sm-4">
                                        <button class="btn btn-danger btn-sm" type="button" onclick="jq_upload('temp','picture_file','picture')" data-toggle="tooltip" data-placement="top" title="上传之后需要手动保存才能生效">上传</button>
                                        <button class="btn btn-primary btn-sm" type="button" onclick="viewPic('picture')" data-toggle="tooltip" data-placement="right" title="预览当前商品所生效的图片">预览原图</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description_url" class="col-sm-4 control-label text-right">描述链接</label>
                            <div class="col-sm-3">
                                <input type="text" id="description_url" name="description_url" class="form-control" value="${product.description_url}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="barcode" class="col-sm-4 control-label text-right">条码</label>
                            <div class="col-sm-3">
                                <input type="text" id="barcode" name="barcode" class="form-control" value="${product.barcode}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label text-right">商品状态</label>
                            <div class="col-sm-3">
                                <label class="radio-inline">
                                    <input type="radio" id="status1" name="status" value="1" <c:if test="${empty product || product.status == 1}">checked</c:if> >正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="status2" name="status" value="2" <c:if test="${product.status == 2}">checked</c:if> >下架
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="rule_id" class="col-sm-4 control-label text-right">商品营销规则</label>
                            <div class="col-sm-3">
                                <select class="form-control" id="rule_id" name="rule_id" data-ruleid="${product.rule_id}">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label text-right">是否促销商品</label>
                            <div class="col-sm-3">
                                <label class="radio-inline">
                                    <input type="radio" id="product_type1" name="product_type" value="1" onclick="toggleCxPro(false)" <c:if test="${empty product || product.product_type == 1}">checked</c:if> >正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="product_type2" name="product_type" value="2" onclick="toggleCxPro(true)" <c:if test="${product.product_type == 2}">checked</c:if> >促销
                                </label>
                            </div>
                        </div>
                        <div class="form-group" data-div="cxpro" <c:if test="${empty product || product.product_type == 1}">style="display:none;" </c:if> >
                            <label for="dec_tag" class="col-sm-4 control-label text-right">促销标题</label>
                            <div class="col-sm-3">
                                <input type="text" id="dec_tag" name="dec_tag" class="form-control" value="${product.dec_tag}">
                            </div>
                        </div>
                        <div class="form-group" data-div="cxpro" <c:if test="${product.product_type == 1}">style="display:none;" </c:if> >
                            <label class="col-sm-4 control-label text-right">促销图片</label>
                            <div class="col-sm-8">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <input type="hidden" name="old_description_pic" value="${product.description_pic}"/>
                                        <input type="hidden" id="description_pic" name="description_pic" value="${product.description_pic}"/>
                                        <input type="file" class="form-control" name="file" id="description_pic_file"/>
                                    </div>
                                    <div class="col-sm-4">
                                        <button class="btn btn-danger btn-sm" type="button" onclick="jq_upload('temp','description_pic_file','description_pic')">上传</button>
                                        <button class="btn btn-primary btn-sm" type="button" onclick="viewPic('description_pic')">预览原图</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-3 col-sm-offset-4">
                                <button class="btn btn-primary" type="button" id="saveBtn" onclick="saveProduct()">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/new/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
<script type="text/javascript">
    $(function() {
        //selectCommunity();
        initProductRule();
        //initProductService();

        $('input[type="file"]').prettyFile();
        $('[data-toggle="tooltip"]').tooltip();
    });

    function selectCommunity() {
        var community_id = $('#community_id').val();
        $('#service_id optgroup').each(function(i, v) {
            if($(v).data('cid') != community_id) {
                $(v).hide();
            } else {
                $(v).show();
            }
        });

        initProductService();
        initProductRule();
    }

    function initProductService() {
        var community_id = $('#community_id').val();
        var i = layer.load();
        $.post('${ctx}/product.do?method=productServiceSelect', {community_id : community_id}, function(data) {
            if(data) {
                $('#service_id').html(data);
            }
            layer.close(i);
        });
    }

    function initProductRule() {
        var community_id = $('#community_id').val();
        var rule_id = $('#rule_id').attr('data-ruleid');
        var i = layer.load();
        $.post('${ctx}/product.do?method=findProductRule', {community_id : community_id}, function(data) {
            var html = "<option value=''>--</option>";
            if(data) {
                var json = eval('(' + data + ')');
                if(json) {
                    $.each(json, function(m, n) {
                        if(rule_id && n.rule_id == rule_id) {
                            html += "<option value='" + n.rule_id + "' selected>" + n.rule_name + "</option>";
                        } else {
                            html += "<option value='" + n.rule_id + "'>" + n.rule_name + "</option>";
                        }
                    });
                }
            }
            $('#rule_id').html(html);
            layer.close(i);
        });
    }

    function toggleManJian() {
        $('#manjianDiv').toggle('fast');
    }

    function toggleShsm(isShow) {
        isShow ? $('#shsmDiv').show('fast') : $('#shsmDiv').hide('fast');
    }

    function toggleCxPro(isShow) {
        isShow ? $('div[data-div=cxpro]').show('fast') : $('div[data-div=cxpro]').hide('fast');
    }

    function viewPic(id) {
        var pic_url = $('#' + id).val();
        if(pic_url) {
            var json = "{'data' : [{'src' : '" + pic_url + "'}]}";
            layer.photos({photos : eval('(' + json + ')'), move : false});
        } else {
            layer.msg("该图片无法预览！");
        }
    }

    function saveProduct() {
        if($('#productForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/product.do?method=saveProduct', $('#productForm').serialize(), function(data) {
                    if(data != null) {
                        var json = eval('(' + data + ')');
                        if(json && json.result == 'success') {
                            layer.alert("操作成功！", function() {
                                location.href = "${ctx}/product.do?method=viewProduct&product_id=" + json.product_id;
                            });
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
</script>

</body>

</html>