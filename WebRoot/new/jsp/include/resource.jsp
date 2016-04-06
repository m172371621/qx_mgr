<%@ page import="com.brilliantreform.sc.utils.SettingUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="qx" uri="/WEB-INF/tag/qxit-tool.tld" %>
<%@ taglib prefix="ui" uri="/WEB-INF/tag/qxit-ui.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<title>区享管理后台</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="keywords" content="区享,社区,服务">
<meta name="description" content="区享管理后台">

<style type="text/css">
    .dropdown-submenu {
        position: relative;
    }
    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }
    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }
    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }
    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }
    .dropdown-submenu.pull-left {
        float: none;
    }
    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>

<script>
    var ctx = '${ctx}';
    var pic_url = '<%=SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST")%>/common/upload/';   //图片上传接口
    document.domain='qxit.com.cn';
</script>
<!--[if lt IE 8]>
<script>
alert('您当前使用的浏览器版本较低，请使用谷歌、火狐等浏览器或360、QQ等国产浏览器的极速模式浏览本页面！');
</script>
<![endif]-->
<!--[if lt IE 9]>
<script src="${ctx}/new/js/plugins/dtgrid/dependents/bootstrap/plugins/ie/html5shiv.js"></script>
<script src="${ctx}/new/js/plugins/dtgrid/dependents/bootstrap/plugins/ie/respond.js"></script>
<![endif]-->
<!--[if lt IE 8]>
<script src="${ctx}/new/js/plugins/dtgrid/dependents/bootstrap/plugins/ie/json2.js"></script>
<![endif]-->

<link type="text/css" href="${ctx}/new/css/plugins/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
<link type="text/css" href="${ctx}/new/css/plugins/Font-Awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet"/>
<link type="text/css" href="${ctx}/new/css/animate.min.css" rel="stylesheet"/>
<link type="text/css" href="${ctx}/new/css/style.min.css" rel="stylesheet"/>

<link rel="stylesheet" type="text/css" href="${ctx}/new/js/plugins/dtgrid/jquery.dtGrid.css" />
<%--<link rel="stylesheet" type="text/css" href="${ctx}/new/js/plugins/dtgrid/dependents/datePicker/skin/WdatePicker.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/new/js/plugins/dtgrid/dependents/datePicker/skin/default/datepicker.css" />--%>

<link type="text/css" href="${ctx}/new/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctx}/new/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<%--<link rel="stylesheet" href="${ctx}/new/js/bootstrap-submenu/css/bootstrap-submenu.min.css" type="text/css">--%>

<!-- 全局js -->
<script src="${ctx}/new/js/jquery.min.js"></script>
<script src="${ctx}/new/css/plugins/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="${ctx}/new/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/new/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/new/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="${ctx}/new/js/hplus.min.js"></script>
<script type="text/javascript" src="${ctx}/new/js/contabs.min.js"></script>
<script src="${ctx}/new/js/content.min.js"></script>
<script src="${ctx}/new/js/common-utils.js"></script>

<!-- 第三方插件 -->
<script src="${ctx}/new/js/plugins/pace/pace.min.js"></script>

<!-- 校验插件 -->
<script type="text/javascript" src="${ctx}/js/nice-validator/jquery.validator.js?local=zh-CN"></script>

<!-- dtGrid -->
<script type="text/javascript" src="${ctx}/new/js/plugins/dtgrid/jquery.dtGrid.js"></script>
<script type="text/javascript" src="${ctx}/new/js/plugins/dtgrid/i18n/zh-cn.js"></script>
<!-- datePicker -->
<%--<script type="text/javascript" src="${ctx}/new/js/plugins/dtgrid/dependents/datePicker/WdatePicker.js" defer="defer"></script>--%>

<script src="${ctx}/new/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<!-- ajaxfileupload -->
<script type="text/javascript" src="${ctx}/new/js/ajaxfileupload.js"></script>
<!-- ztree -->
<script type="text/javascript" src="${ctx}/new/js/ztree/js/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript" src="${ctx}/new/js/tppl.js"></script>
