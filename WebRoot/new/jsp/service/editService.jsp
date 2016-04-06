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
                        <h5>编辑服务信息</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal" id="serviceForm">
                            <input type="hidden" value="${service.service_id}" name="service_id"/>
                            <c:if test="${!empty service.service_id}">
                                <input type="hidden" name="service_type" value="${service.service_type }" id="service_type"/>
                            </c:if>
                            <c:if test="${empty service.service_id}">
                                <input type="hidden" name="service_type" value="3" id="service_type"/>
                            </c:if>

                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">门店</label>
                                <div class="col-sm-3">
                                    <ui:simpleCommunitySelect id="community_id" name="community_id" value="${service.community_id}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">内容</label>
                                <div class="col-sm-3">
                                    <textarea class="form-control" name="service_dec" id="service_dec" style="height: 100px;" data-rule="required;">${service.service_dec}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" name="service_name" id="service_name" value="${service.service_name}" data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">图片链接</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" name="service_img" id="service_img" value="${service.service_img}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">我的预约图片链接</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" name="service_title_img" id="service_title_img" value="${service.service_title_img}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">服务链接</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" name="service_url" id="service_url" value="${service.service_url}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">顺序</label>
                                <div class="col-sm-3">
                                    <input type="number" class="form-control" name="service_order" id="service_order" value="${service.service_order}" data-rule="required;"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-2 control-label text-right">可见性</label>
                                <div class="col-sm-3">
                                    <select id="status" name="status" class="form-control" data-rule="required;">
                                        <option value="1" <c:if test="${service.status == 1}">selected</c:if> >可见</option>
                                        <option value="0" <c:if test="${service.status == 0}">selected</c:if> >隐藏</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-3 col-sm-offset-5">
                                    <button class="btn btn-primary" type="button" id="saveBtn" onclick="saveService()">保存</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function saveService() {
            if ($('#serviceForm').isValid()) {
                layer.confirm("确定要保存吗？", function (index) {
                    var i = layer.load();
                    $.post('${ctx}/service.do?method=saveServiceV4', $('#serviceForm').serialize(), function (data) {
                        if (data != null && data == 'success') {
                            layer.alert("保存成功！", function(index) {
                                location.reload();
                            });
                        } else {
                            layer.alert("保存失败！");
                        }
                        layer.close(i);
                    });
                });
            }
        }
    </script>

</body>

</html>