<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <jsp:include page="../common/resource.jsp"/>
    <jsp:include page="../common/datetimepicker.jsp"/>

    <script type="text/javascript">
        function query() {
            $('#pageIndex').val(1);
            $('#searchForm').submit();
        }

        function removeAd(ad_id) {
            if(confirm("确定要删除该广告吗？")) {
                $.post('${pageContext.request.contextPath }/ad.do?method=removeAd', {ad_id : ad_id}, function(data) {
                    if(data != null && data == 'ok') {
                        alert("删除成功！")
                        location.reload();
                    } else {
                        alert("删除失败！");
                    }
                });
            }
        }
    </script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div id="main" class="c">
    <jsp:include page="../common/menu.jsp"/>

    <input type="hidden" id="curr_li" value="service_1"/>

    <div id="mright">
        <div id="mr_cnt">
            <iframe class="J_iframe" id="iframe0" name="iframe0" width="100%" height="100%" src="${pageContext.request.contextPath }/qxcard.do?method=showRechargePage" frameborder="0"></iframe>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 计算页面的实际高度，iframe自适应会用到
    function calcPageHeight(doc) {
        var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight)
        var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight)
        var height  = Math.max(cHeight, sHeight)
        return height
    }
    var ifr = document.getElementById('iframe0')
    ifr.onload = function() {
        var iDoc = ifr.contentDocument || ifr.document;
        var height = calcPageHeight(iDoc);
        ifr.style.height = height + 'px';
    }
</script>
</body>
</html>
