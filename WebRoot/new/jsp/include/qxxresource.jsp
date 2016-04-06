<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="qx" uri="/WEB-INF/tag/qxit-tool.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<meta charset="utf-8">
<meta name="viewport"
      content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<!-- <meta http-equiv="refresh" content="5"> 自动刷新页面 -->
<script>
    var ctx = '${ctx}';
</script>
<!-- Your app title -->
<title>区享侠</title>
<!-- framework7 iso css -->
<link rel="stylesheet" href="${ctx}/new/jsp/newQxx/css/framework7.ios.css">
<link rel="stylesheet" href="${ctx}/new/jsp/newQxx/css/framework7.ios.colors.css">
<!-- Font Awesome 图标 -->
<link rel="stylesheet" href="${ctx}/new/jsp/newQxx/font-awesome/css/font-awesome.css">
<!-- framework7 js -->
<script type="text/javascript" src="${ctx}/new/jsp/newQxx/js/framework7.js"></script>
<!-- my-app.css -->
<link rel="stylesheet" href="${ctx}/new/jsp/newQxx/css/my-app.css">
<!-- 高德地图 css -->
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
<!-- 高德地位-->
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=72c3ac07182b3ae27ab88d9195459151"></script>
<!-- 自定义定位-->
<script type="text/javascript" src="${ctx}/new/jsp/newQxx/js/diWei.js"></script>
<!-- jquery -->
<script src="${ctx}/new/js/jquery.min.js"></script>
<!-- moment js 时间格式转换-->
<script type="text/javascript" src="${ctx}/new/jsp/newQxx/js/momen.js"></script>
<!-- 自定义ajax -->
<script src="${ctx}/new/jsp/newQxx/js/my-ajax.js"></script>