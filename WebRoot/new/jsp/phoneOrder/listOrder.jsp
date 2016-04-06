<%@ page language="java" import="com.brilliantreform.sc.phoneOrder.po.DistriOrderBean" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" ng-app="deliverapp">

<head>
    <meta charset="utf-8">
    <title>订单清单</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no, email=no">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/qxv3.css"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/dialog.css"/>
    <script src="../new/jsp/saleplugins/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/angular.js" type="text/javascript" charset="utf-8"></script>
</head>

<body ng-controller="dlController">
<div id="header">
    <a href="${pageContext.request.contextPath }/phoneorderctrl/getValidate.do" class="header_back"><i
            class="qx qx-back"></i></a>
    </a>
    <span class="header_title">商品到货列表</span>
    <a href="${pageContext.request.contextPath }/phoneorderctrl/loginOut.do" class="fr"><i class="qx qx-exit"></i></a>
    <a href="${pageContext.request.contextPath }/phoneorderctrl/listOrderPage.do" class="fr"><i class="qx qx-refresh"></i></a>
</div>
<div ng-if="tgld==1">
    <div class="txt-c pd fc_gray" ng-if="!deliverlist.length">暂无到货商品</div>
    <ul class="bdt_list deliverlist">
        <li class="c" ng-repeat="d in deliverlist">
            <div class="deliver_act pt">
                <a ng-click="daohuo($index)" class="btn btn_r btn_big">设置到货</a>
            </div>
            <div class="deliver_info" ng-click="orderinfo($index)">
                <p>昵称：{{d.nick}}</p>

                <p>手机号：{{d.delivery_phone}}</p>

                <p>订单号：{{d.order_serial}}</p>

                <p>订单总价：{{d.order_price|currency:'￥'}}</p>

                <p>下单时间：{{d.order_time}}</p>
            </div>
        </li>
    </ul>
</div>
<div id="loading" ng-show="loading"></div>
<!-- 订单详情 -->
<script type="text/html" id="odetail_tpl">
    <ul class="bdt_list">
        <li class="pd">
            <p class="ng-binding">手机号：
                {{delivery_phone}}
            </p>

            <p class="ng-binding">配送地址：
                {{delivery_addr}}
            </p>

            <p class="ng-binding">订单号：
                {{order_serial}}
            </p>
        </li>
        <li class="pd">
            <p class="fc_r">商品清单</p>

            <div>
                {{each subList as value index}}
                <p class='c'>
                    {{value.product_name}}*
                    {{value.product_amount}}<span class='fr fc_r'>￥{{value.product_price*value.product_amount}}</span>
                </p>
                {{/each}}
            </div>
        </li>
    </ul>
</script>
<script src="../new/jsp/saleplugins/js/artDialog.source.js" type="text/javascript" charset="utf-8"></script>
<script src="../new/jsp/saleplugins/js/iframeTools.source.js" type="text/javascript" charset="utf-8"></script>
<script src="../new/jsp/saleplugins/js/template.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var deliverapp = angular.module('deliverapp', []);
    init();
    function init() {
        var dlController = deliverapp.controller('dlController',
                function ($scope, $http) {
                    $scope.loading = false;//ajax loading遮罩
                    $scope.tgld = 1;
                    $scope.toggled = function (t) {
                        $scope.tgld = t;
                    };
                    //待接单
                    $scope.deliverlist = [];

                    $http({
                        method: 'get',
                        url: '${pageContext.request.contextPath }/phoneorderctrl/listOrder.do'
                    }).success(function (data) {
                        $scope.deliverlist = data.data;
                    });

                    //订单详情
                    $scope.orderinfo = function (i) {
                        var d = $scope.deliverlist[i];
                        template.helper("math_fix", function (data) {
                            return data.toFixed(2);
                        });
                        var detail = template('odetail_tpl', d);
                        artDialog({
                            title: '订单详情',
                            width: '92%',
                            maxHeight: '80%',
                            padding: 0,
                            content: detail
                        })
                    };

                    $scope.daohuo = function(i) {
                        $scope.loading = true;
                        $http({
                            method:'get',
                            url:'${pageContext.request.contextPath }/phoneorderctrl/updateOrderStatus.do',
                            params: {
                                order_serial: $scope.deliverlist[i].order_serial,
                                type:2
                            }
                        }).success(function() {
                            $scope.loading = false;
                            artDialog.tips('设置成功', 1);
                            $scope.deliverlist.splice(i, 1);
                            $http({
                                method: 'get',
                                url: '${pageContext.request.contextPath }/phoneorderctrl/listOrder.do'
                            }).success(function (data) {
                                $scope.tihuo = data.data;
                            });
                        })
                    }
                });
    }
</script>
</body>

</html>