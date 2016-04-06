<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <title>线下销售辅助工具</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/qxv3.css"/>
    <script src="../new/jsp/saleplugins/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/jquery.jplayer.min.js" type="text/javascript" charset="utf-8"></script>
</head>

<body ng-app="pwapp" ng-controller="pwController">
<div id="header">
    <span class="header_title">线下销售辅助工具</span>
    <a href="${pageContext.request.contextPath}/phoneorderctrl/loginOut.do" class="fr"><b
            class="btn btn_msg_f btn_small">注销</b></a>
</div>
<ul class="icon_navlist" id="service_nav">
    <!--
   <li>
        <a href="${pageContext.request.contextPath }/distriWorkerWebCtrl/transf_order_detail.do">
            <dl>
                <dt class="nav_icon"><i class="qx qx-pifa icon_bg_azure"></i></dt>
                <dd class="fr pt"><i class="qx qx-right fc_gray"></i></dd>
                <dd class="nav_name mt">快递配送</dd>
            </dl>
        </a>
    </li>
    -->
    <li>
        <a href="${pageContext.request.contextPath}/weighProduct/pwmanager.do">
            <dl>
                <dt class="nav_icon"><i class="qx qx-chengzhong icon_bg_orange"></i></dt>
                <dd class="fr pt"><i class="qx qx-right fc_gray"></i></dd>
                <dd class="fr pt"><i class="qx qx-righ"></i></dd>
                <dd class="fr pt hide" id="hongdian">
                    <div style="display: inline-block; width:22px; height:22px; background-color:#F00; border-radius:25px;">
                        <span id="hongdianText"
                              style="height:22px; line-height:22px; display:block; color:#FFF; text-align:center;"></span>
                    </div>
                </dd>
                <dd class="nav_name pt">称重商品销售</dd>
            </dl>
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/postSan/seachPostScanBease.do">
            <dl>
                <dt class="nav_icon"><i class="qx qx-pifa icon_bg_orange"></i></dt>
                <dd class="fr pt"><i class="qx qx-right fc_gray"> </i></dd>
                <dd class="nav_name pt">扫码闪购</dd>
            </dl>
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/phoneorderctrl/phonePage.do">
            <dl>
                <dt class="nav_icon"><i class="qx qx-order icon_bg_green"></i></dt>
                <dd class="fr pt"><i class="qx qx-right fc_gray"></i></dd>
                <dd class="fr pt hide" id="hongdian2">
                    <div style="display: inline-block; width:22px; height:22px; background-color:#F00; border-radius:25px;">
                        <span id="hongdianText2"
                              style="height:22px; line-height:22px; display:block; color:#FFF; text-align:center;"></span>
                    </div>
                </dd>
                <dd class="nav_name pt"> 订单清单</dd>

            </dl>
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/phoneorderctrl/listOrderPage.do">
            <dl>
                <dt class="nav_icon"><i class="qx qx-deliver icon_bg_azure"></i></dt>
                <dd class="fr pt"><i class="qx qx-right fc_gray"></i></dd>
                <dd class="nav_name pt">
                    商品到货
                </dd>

            </dl>
        </a>
    </li>
</ul>
</div>
<div id="jquery_jplayer_1"></div>
<div id="jquery_jplayer_2"></div>
<script type="text/javascript">
    $(function () {
        $("#jquery_jplayer_1").jPlayer({
            ready: function () {
                $(this).jPlayer("setMedia", {mp3: "../new/jsp/saleplugins/audio/sms.mp3"});
            },
            ended: function () {

            },
            swfPath: "../new/jsp/saleplugins/audio/",
            supplied: "mp3",
            volume: 1.8,
            preload: 'metadata',
            solution: 'flash,html '
        });
        $("#jquery_jplayer_2").jPlayer({
            ready: function () {
                $(this).jPlayer("setMedia", {mp3: "../new/jsp/saleplugins/audio/chengzhong.mp3"});
            },
            swfPath: "../new/jsp/saleplugins/audio/",
            supplied: "mp3",
            volume: 1.8,
            preload: 'metadata',
            solution: 'flash,html '
        });
        setInterval("myInterval()", 5000);//1000为1秒钟
        setInterval("chengzhong()", 5000);//1000为1秒钟
    });

    function myInterval() {
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/phoneorderctrl/getNewOrderCount.do",
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                var dataObj = eval("(" + data + ")");
                if (dataObj.data > 0) {
                    $("#hongdian2").removeClass("hide");
                    $("#hongdianText2").html(dataObj.data);
                    if (parseInt(dataObj.result_code) > 0) {
                        $("#jquery_jplayer_1").jPlayer("play");
                    }
                } else {
                    $("#hongdian2").addClass("hide")
                }
            },
            error: function (msg) {
                // alert("error");
            }
        });
    }

    function chengzhong() {
        $.ajax({
            type: "post",
            url: '${pageContext.request.contextPath }/weighProduct/pwmanagerListcount.do',
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                var dataObj = eval("(" + data + ")");
                if (dataObj.data > 0) {
                    $("#hongdian").removeClass("hide");
                    $("#hongdianText").html(dataObj.data);
                    if (parseInt(dataObj.result_code) > 0) {
                        $("#jquery_jplayer_2").jPlayer("play");
                    }
                } else {
                    $("#hongdian").addClass("hide")
                }
            },
            error: function (msg) {
                //alert("error");
            }
        });
    }
</script>
</body>
</html>