<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link type="text/css" href="${ctx}/new/css/plugins/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
<html>

<head>
    <style type="text/css">
        .container-fluid {
            padding-right: 0;
            padding-left: 0;
        }

        body {
            font-family: "SimHei";
            font-size: 10px;
        }

        .nowrap {
            white-space:nowrap;
        }

        .table>tbody>tr>td {
            border-top: 0px;
        }

        .form-control-static {
            min-height: 0px;
            padding-top: 0px;
            padding-bottom: 0px;
        }

        .table-condensed>tbody>tr>td,
        .table-condensed>tbody>tr>th,
        .table-condensed>tfoot>tr>td,
        .table-condensed>tfoot>tr>th,
        .table-condensed>thead>tr>td,
        .table-condensed>thead>tr>th {
            padding: 1px;
        }

        .table>thead>tr>th {
            border-bottom: 1px  #333 dashed;
        }
    </style>
</head>

<body>
    <div class="container-fluid" style="overflow-x: hidden;">
        <%--<h1 class="text-center">区享</h1>--%>
        <div class="text-center">
            <img src="${ctx}/img/logo_100.png"/>
        </div>
        <table class="table table-condensed">
            <thead>
                <tr>
                    <th></th>
                    <th class="nowrap text-left"></th>
                    <%--<th class="nowrap">数量</th>--%>
                    <th class="nowrap text-right"></th>
                </tr>
            </thead>
            <tbody>
            <c:set var="total_price" value="0"/>
            <c:forEach items="${productList}" var="product">
                <tr>
                    <td>${product.product_name}</td>
                    <td class="text-left"><fmt:formatNumber type="number" value="${product.product_price}" maxFractionDigits="2"/>*<fmt:formatNumber type="number" value="${product.product_amount}" maxFractionDigits="2"/></td>
                    <%--<td>${product.product_amount}</td>--%>
                    <td class="text-right">
                        <fmt:formatNumber type="number" value="${product.product_amount * product.product_price}" maxFractionDigits="2" var="price"/>
                        <c:set var="total_price" value="${total_price + price}"/>
                        ${price}
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td colspan="3"></td>
            </tr>

            <tr>
                <td colspan="2">合计</td>
                <td class="text-right">${total_price}</td>
            </tr>
            <tr>
                <td colspan="2">优惠</td>
                <td class="text-right"><fmt:formatNumber type="number" value="${total_price - order.order_price}" maxFractionDigits="2"/></td>
            </tr>
            <tr>
                <td colspan="3">
                    <h4>
                    折后合计
                    <fmt:formatNumber type="number" value="${order.order_price}" maxFractionDigits="2"/>
                        <c:choose>
                            <c:when test="${order.order_status == 22 || order.order_status == 3}">(已付款)</c:when>
                            <c:otherwise>(未付款)</c:otherwise>
                        </c:choose>
                    </h4>
                </td>
            </tr>
            </tbody>
        </table>

        <hr style="border:1px  #333 dashed;"/>

        <div class="row">
            <label class="col-xs-3 control-label text-left nowrap">时间</label>
            <p class="col-xs-9 form-control-static"><fmt:formatDate value="${order.order_time}" pattern="yyyy-MM-dd HH:mm"/></p>
        </div>
        <div class="row">
            <label class="col-xs-3 control-label text-left nowrap">电话</label>
            <p class="col-xs-9 form-control-static">${order.delivery_phone}</p>
        </div>
        <div class="row">
            <label class="col-xs-3 control-label text-left nowrap">地址</label>
            <p class="col-xs-9 form-control-static">${order.delivery_addr}</p>
        </div>
        <c:if test="${!empty order.delivery_dec}">
            <div class="row">
                <label class="col-xs-3 control-label text-left nowrap">备注</label>
                <p class="col-xs-9 form-control-static">${order.delivery_dec}</p>
            </div>
        </c:if>
        <br><br>
        <div class="row">
            <p class="col-xs-12 form-control-static">
                区享O2O服务平台，改变你我的生活方式！扫此二维码下载区享客户端。
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12 text-center">
                <img src="http://qxit.com.cn/images/qrcode.gif" width="100px" height="100px"/>
                <%--<img src="http://qxit.com.cn/images/wx_qrcode.jpg" width="80px" height="80px"/>--%>
            </div>
        </div>
    </div>

    <script src="${ctx}/new/js/jquery.min.js"></script>
    <script src="${ctx}/new/css/plugins/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
           window.print();
        });
    </script>

</body>
</html>