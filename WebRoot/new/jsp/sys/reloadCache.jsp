<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
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
                    <h5>缓存刷新</h5>
                </div>
                <div class="ibox-content">
                    <form id="cacheForm">
                        <label class="checkbox-inline">
                            <input type="checkbox" value="setting" name="type">参数
                        </label><br>
                        <label class="checkbox-inline">
                            <input type="checkbox" value="dict" name="type">数据字典
                        </label><br>
                        <label class="checkbox-inline">
                            <input type="checkbox" value="community" name="type">门店
                        </label><br>
                        <br><br><br>
                        <button type="button" class="btn btn-primary" onclick="reloadCache()">刷新</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function reloadCache() {
        var i = layer.load();
        $.post('${ctx}/sys.do?method=reloadCache', $('#cacheForm').serialize(), function(data) {
            if(data) {
                var json = eval('(' + data + ')');
                if(json && json.result) {
                    layer.alert("操作成功！");
                } else {
                    layer.alert("操作失败！");
                }
            } else {
                layer.alert("操作失败！");
            }
            layer.close(i);
        });
    }
</script>

</body>

</html>