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
    <a href="${pageContext.request.contextPath }/phoneorderctrl/getValidate.do" class="header_back">
        <i class="qx qx-back"></i>
    </a>
    <span class="header_title">订单清单</span>
    <a href="${pageContext.request.contextPath }/phoneorderctrl/loginOut.do" class="fr"><i class="qx qx-exit"></i></a>
    <a href="${pageContext.request.contextPath }/phoneorderctrl/phonePage.do" class="fr"><i
            class="qx qx-refresh"></i></a>
</div>
<div class="tab c tab-c2">
    <a ng-click="toggled(1)" ng-class="{cur:tgld==1}" style="width: 33%"><span>未接单</span></a>
    <a ng-click="toggled(2)" ng-class="{cur:tgld==2}" style="width: 33%"><span>已接单</span></a>
    <a ng-click="toggled(3)" ng-class="{cur:tgld==3}" style="width: 33%"><span>已提货</span></a>
</div>
<div ng-if="tgld==1">
    <div class="txt-c pd fc_gray" ng-if="!deliverlist.length">暂无待接单</div>
    <ul class="bdt_list deliverlist">
        <li class="c" ng-repeat="d in deliverlist">
            <div class="deliver_act pt">
                <a ng-click="acceptorder($index)" class="btn btn_r btn_big" ng-if="d.delivery_type=='2'">接单送货</a>
                <a ng-click="th($index)" class="btn btn_r btn_big" ng-if="d.delivery_type=='1'">提货</a>
            </div>
            <div class="deliver_info" ng-click="orderinfo($index)">
                <p>昵称：{{d.nick}}</p>

                <p>手机号：{{d.phone}}</p>

                <p>订单号：{{d.order_serial}}</p>

                <p ng-if="d.delivery_type=='2'">送货上门</p>

                <p ng-if="d.delivery_type=='1'">自提</p>

                <p>订单总价：{{d.order_price|currency:'￥'}}</p>

                <p>下单时间：{{d.order_time}}</p>
            </div>
        </li>
    </ul>
</div>
<div ng-if="tgld==2">
    <div class="txt-c pd fc_gray" ng-if="!receivelst.length">暂无已接单</div>
    <ul class="bdt_list deliverlist">
        <li class="c" ng-repeat="d in receivelst" ng-click="actorder($index)">
            <div class="deliver_act pt">
                <span class="btn btn_msg_r" ng-if="d.pay_staus=='2'">已支付</span>
                <span class="btn btn_msg_gr" ng-if="d.pay_staus=='1'">现金收费{{d.order_price|currency:'￥'}}</span>
            </div>
            <div class="deliver_info">
                <p>手机号：{{d.delivery_phone}}</p>

                <p>配送地址：{{d.delivery_addr}}</p>

                <p>配送时间：{{d.delivery_time}}</p>

                <p>订单号：{{d.order_serial}} <i class="qx qx-infofill fs_5 fc_gray"></i>
                </p>

                <p>订单总价：{{d.order_price|currency:'￥'}}</p>
            </div>
        </li>
    </ul>
</div>
<div ng-if="tgld==3">
    <div class="txt-c pd fc_gray" ng-if="!tihuo.length">暂无提货</div>
    <ul class="bdt_list deliverlist">
        <li class="c" ng-repeat="d in tihuo">
            <div class="deliver_act pt">
                <span class="txt-l">接单时间:<br/>{{d.create_time}}</span><br/>
                <span class="txt-r">提货时间:<br/>{{d.finish_time}}</span>
            </div>
            <div class="deliver_info" ng-click="tihuoinfo($index)">
                <p>昵称：{{d.nick}}</p>

                <p>手机号：{{d.order_phone}}</p>

                <p>订单号：{{d.order_serial}}</p>

                <p ng-if="d.delivery_type=='2'">送货上门</p>

                <p ng-if="d.delivery_type=='1'">自提</p>

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

            <p class="ng-binding">配送时间：
                {{delivery_time}}
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
<!-- 提货详情 -->
<script type="text/html" id="tihuoinfo">
    <ul class="bdt_list">
        <li class="pd">
            <p class="ng-binding">手机号：
                {{delivery_phone}}
            </p>

            <p class="ng-binding">配送地址：
                {{delivery_addr}}
            </p>

            <p class="ng-binding">配送时间：
                {{delivery_time}}
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
            <p class="fc_r"></p>
        </li>
        <li class="pd">
            <p class="ng-binding">满减：
                {{}}
            </p>

            <p class="ng-binding">首单减：
                {{}}
            </p>

            <p class="ng-binding">随机减：
                {{}}
            </p>
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
                        url: '${pageContext.request.contextPath }/phoneorderctrl/getOrderList.do'
                    }).success(function (data) {
                        $scope.deliverlist = data.data;
                    });

                    //已接单
                    $scope.receivelst = [];
                    $http({
                        method: 'get',
                        url: '${pageContext.request.contextPath }/phoneorderctrl/getDeliveryList.do'
                    }).success(function (data) {
                        $scope.receivelst = data.data;
                    });

                    //提货
                    $scope.tihuo = [];
                    $http({
                        method: 'get',
                        url: '${pageContext.request.contextPath }/phoneorderctrl/getBaseProduct.do'
                    }).success(function (data) {
                        $scope.tihuo = data.data;
                    });

                    //订单详情
                    $scope.orderinfo = function (i) {
                        var d = $scope.deliverlist[i];
                        template.helper("math_fix", function (data) {
                            return data.toFixed(2);
                        });
                        var detail = template('tihuoinfo', d);
                        artDialog({
                            title: '订单详情',
                            width: '92%',
                            maxHeight: '80%',
                            padding: 0,
                            content: detail
                        })
                    };
                    //提货详情
                    $scope.tihuoinfo = function (i) {
                        var d = $scope.tihuo[i];
                        template.helper("math_fix", function (data) {
                            return data.toFixed(2);
                        });
                        var detail = template('tihuoinfo', d);
                        artDialog({
                            title: '提货详情',
                            width: '92%',
                            maxHeight: '80%',
                            padding: 0,
                            content: detail
                        })
                    };
                    //接单操作
                    $scope.acceptorder = function (i) {
                        $scope.loading = true;
                        $http({
                            method: 'get',
                            url: '${pageContext.request.contextPath }/phoneorderctrl/acceptOrder.do',
                            params: {
                                order_serial: $scope.deliverlist[i].order_serial
                            }
                        }).success(function (data) {
                            $scope.loading = false;
                            artDialog.tips('接单成功', 1);
                            $scope.deliverlist.splice(i, 1);
                            $http({
                                method: 'get',
                                url: '${pageContext.request.contextPath }/phoneorderctrl/getDeliveryList.do'
                            }).success(function (data) {
                                $scope.receivelst = data.data;
                            });
                        }).error(function () {
                            $scope.loading = false;
                            artDialog.tips('接单失败', 1);
                        })
                    };

                    //配送操作
                    $scope.actorder = function (i) {
                        var d = $scope.receivelst[i];
                        var detail = template('odetail_tpl', d);
                        artDialog({
                            title: '订单详情',
                            width: '92%',
                            max_Height: '50%',
                            padding: 0,
                            content: detail,
                            button: [{
                                name: '配送完成',
                                className: 'btn_bd_r',
                                callback: function () {
                                    $scope.loading = true;
                                    $http({
                                        method: 'get',//post
                                        url: '${pageContext.request.contextPath }/phoneorderctrl/finishOrder.do',
                                        params: {
                                            distriDetailId: d.distri_detail_id,
                                            user_id: d.user_id
                                        }
                                    }).success(function () {
                                        $scope.loading = false;
                                        artDialog.tips('修改配送状态成功', 1)
                                        $http({
                                            method: 'get',
                                            url: '${pageContext.request.contextPath }/phoneorderctrl/getBaseProduct.do'
                                        }).success(function (data) {
                                            $scope.tihuo = data.data;
                                        });
                                        $scope.receivelst.splice(i, 1);
                                    }).error(function () {
                                        $scope.loading = false;
                                        artDialog.tips('修改配送状态失败', 1)
                                    })
                                }
                            }]
                        })
                    };

                    //提货操作
                    $scope.th = function (i) {
                        $scope.loading = true;
                        $http({
                            method: 'get',
                            url: '${pageContext.request.contextPath }/phoneorderctrl/updateOrderStatus.do',
                            params: {
                                order_serial: $scope.deliverlist[i].order_serial,
                                type: 1
                            }
                        }).success(function (data) {
                            $scope.loading = false;
                            artDialog.tips('提货成功', 1);
                            $scope.deliverlist.splice(i, 1);
                            $http({
                                method: 'get',
                                url: '${pageContext.request.contextPath }/phoneorderctrl/getBaseProduct.do'
                            }).success(function (data) {
                                $scope.tihuo = data.data;
                            });
                        }).error(function () {
                            $scope.loading = false;
                            artDialog.tips('提货失败', 1);
                        })
                    };
                });
    }
</script>
</body>

</html>