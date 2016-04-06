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
                    <h5>编辑广告</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="adForm">
                        <input type="hidden" value="${ad.ad_id}" name="ad_id"/>
                        <input type="hidden" name="status" value="0" id="status"/>

                        <div class="form-group">
                            <label for="community_id" class="col-md-4 control-label text-right">门店</label>

                            <div class="col-md-3">
                                <ui:simpleCommunitySelect id="community_id" name="community_id" value="${ad.community_id}" event="initServiceList()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ad_name" class="col-md-4 control-label text-right">名称</label>

                            <div class="col-md-3">
                                <input type="text" class="form-control" name="ad_name" id="ad_name" value="${ad.ad_name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ad_dec" class="col-md-4 control-label text-right">描述</label>

                            <div class="col-md-3">
                                <textarea class="form-control" name="ad_dec" id="ad_dec">${ad.ad_dec}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="service_type" class="col-md-4 control-label text-right">类型</label>

                            <div class="col-md-3">
                                <select id="service_type" name="service_type" class="form-control">
                                    <option value="11" <c:if test="${ad.service_type == 11}">selected</c:if> >商品广告</option>
                                    <option value="12" <c:if test="${ad.service_type == 12}">selected</c:if> >订单广告</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="service_id" class="col-md-4 control-label text-right">关联服务</label>

                            <div class="col-md-3">
                                <select id="service_id" name="service_id" class="form-control" onchange="setAdUrl()">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ad_url" class="col-md-4 control-label text-right">链接</label>

                            <div class="col-md-3">
                                <input type="text" class="form-control" name="ad_url" id="ad_url" value="${ad.ad_url}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ad_order" class="col-md-4 control-label text-right">顺序</label>

                            <div class="col-md-3">
                                <input type="text" class="form-control" name="ad_order" id="ad_order" value="${ad.ad_order}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-5">
                                <button class="btn btn-primary" type="button" id="saveBtn" onclick="saveAd()">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function saveAd() {
        var community_id = $('#community_id').val();
        var ad_name = $('#ad_name').val();
        var service_type = $('#service_type').val();
        var ad_order = $('#ad_order').val();
        var status = $('#status').val();
        if(community_id != null && $.trim(community_id) != '' && ad_name != null && $.trim(ad_name) != ''
                && service_type != null && $.trim(service_type) != '' && ad_order != null && $.trim(ad_order) != ''
                && status != null && $.trim(status) != '') {
            layer.confirm("确定要保存吗？", function(i) {
                var o = layer.load();
                $.post('${ctx}/ad.do?method=saveAdV4', $('#adForm').serialize(), function(data) {
                   if(data != null && Number(data) > 0) {
                       layer.alert("操作成功！", function(m) {
                          location.href = '${ctx}/ad.do?method=showAdV4&ad_id=' + data;
                       });
                   } else {
                       layer.alert("操作失败!");
                   }
                    layer.close(o);
                });
            });
        } else {
            layer.alert("表单输入不完整！");
        }

    }

    function setAdUrl() {
        var url = $('#service_id :selected').attr('url');
        $('#ad_url').val(url);
    }

    function initServiceList() {
        var community_id = $('#community_id').val();
        var i = layer.load();
        $.post('${ctx}/ad.do?method=findServiceInfo', {community_id : community_id}, function(data) {
            var html = "<option value=''>不关联</option>";
            if(data) {
                var json = eval('(' + data + ')');
                if(json) {
                    $.each(json, function(index, v) {
                        var selected = "";
                        if('${ad.service_id}' == v.service_id) {
                            selected = "selected";
                        }
                        html += "<option value='" + v.service_id + "' url='" + v.service_url + "' " + selected + " >" + v.service_name + "</option>";
                    });
                }
            }

            $('#service_id').html(html);
            layer.close(i);
        });
    }

    $(function() {
        initServiceList();
    });
</script>

</body>

</html>