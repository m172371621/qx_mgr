<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@ page language="java" pageEncoding="UTF-8" %>
    <title>扫码闪购</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/qxv3.css"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/dialog.css"/>
    <script src="../new/jsp/saleplugins/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/jquery.qrcode.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/utf.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var intDiff = parseInt(2);//倒计时总秒数量
        function timer(intDiff) {
            window.setInterval(function () {
                var day = 0,
                        hour = 0,
                        minute = 0,
                        second = 0;//时间默认值
                if (intDiff > 0) {
                    day = Math.floor(intDiff / (60 * 60 * 24));
                    hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                    minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                    second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
                }
                if (minute <= 9) minute = '0' + minute;
                if (second <= 9) second = '0' + second;
                $('#day_show').html(day + "天");
                $('#hour_show').html('<s id="h"></s>' + hour + '时');
                $('#minute_show').html('<s></s>' + minute + ' 分');
                $('#second_show').html('<s></s>' + second + ' 秒');
                intDiff--;
                if (intDiff < 0 && second == 0) {
                    $.post('${pageContext.request.contextPath}/postSan/seachPostScanBeaseList.do', function (data) {

                        if (null != data) {
                            $("#price").html("");                           //先清空总价格
                            $("#price").append(data.data.order_price);      // 在设置新的总价格
                            var pos_baseid = data.data.order_temp_base_id;  // pos_order_temp_base -> order_temp_base_id
                            $("#qrcodeCanvas").html(""); //先清空二维码
                            $("#qrcodeCanvas").qrcode({  //在设置新的二维码
                                render: "canvas",         //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
                                text: data.data.url,     //扫码后的内容，可是URL,扫码后跳转到URL页面
                                height: "200",            //二维码宽度
                                width: "200", 		      //二维码高度
                                background: "#ffffff",    //二维码背景色
                                foreground: "#000000",    //二维码前景色
                                src: "../new/jsp/scancodeshpping/images/quxiang.jpg" //二维码中的图片
                            });
                            $.post('${pageContext.request.contextPath}/postSan/seachPostScanproductList.do', {pos_baseid: pos_baseid}, function (data) {
                                $("#product_info tbody").html(""); //清空商品的信息
                                for (var v in data.data) {
                                    var tr = $("<tr style='margin-bottom:2px;'></tr>");
                                    var td = $("<td></td>").text(data.data[v].product_name);    //商品名称
                                    var td2 = $("<td></td>").text("× " + data.data[v].product_amount); //商品单位
                                    var td3 = $("<td></td>").text("￥ " + data.data[v].product_price);  //商品价格
                                    $("#product_info tbody").append(tr, td, td2, td3);             //添加pos机添加的新商品
                                }
                            }, "json");
                        }
                    }, "json")

                    intDiff = parseInt(2);
                }
            }, 1000);
        }
        $(function () {
            timer(intDiff);
        });
    </script>
</head>
<body ng-app="pwlistapp" ng-controller="pwlistController">
<input type="hidden" value="" id="ic"/>

<div id="header">
    <a href="${pageContext.request.contextPath }/phoneorderctrl/getValidate.do" class="header_back"><i
            class="qx qx-back"></i> </a>
    <span class="header_title">扫码闪购</span>
    <a href="/phoneorderctrl/loginOut.do" class="fr">
        <i class="qx qx-exit"></i> </a>
    <a href="/postSan/seachPostScanBease.do" class="fr">
        <i class="qx qx-refresh"></i> </a>
</div>
<div style="height: auto;">
    <div style="font-size: 30px; font-family: 黑体; font-weight: bold; margin-top: 15px; text-align: center">
        共计:<span id="price" style="font-size: 35px; color:red;">0</span>元
    </div>
    <div style="font-size: 25px; font-family: 黑体; font-weight: bold; margin-top: 10px; text-align: center">
        <div class="time-item">
            <!-- <span id="day_show">0天</span> -->
            <!-- <strong id="hour_show">0时</strong> -->
            <strong id="minute_show">0 分</strong>
            <strong id="second_show">0 秒</strong>
            <br/>

            <div id="qrcodeCanvas" style="margin-top: 5px;margin-bottom: 5px;"></div>
            <!-- 二维码显示区 -->
        </div>
        <div style="text-align:left; border-bottom: 1px solid #cccccc; width: 100%; font-size:20px; margin-bottom:10px;">
            付款清单:
        </div>

        <table id="product_info" border="0px" style="font-size: 18px;font-weight: normal; text-align: left;">
            <thead>

            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>
</body>
</html>