<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@ page language="java" pageEncoding="UTF-8" %>
    <title>称重商品销售</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/qxv3.css"/>
    <link rel="stylesheet" type="text/css" href="../new/jsp/saleplugins/css/dialog.css"/>
    <script src="../new/jsp/saleplugins/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/angular.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/angular-touch.js" type="text/javascript" charset="utf-8"></script>
    <script src="../new/jsp/saleplugins/js/jquery.jplayer.min.js" type="text/javascript" charset="utf-8"></script>
</head>

<body ng-app="pwlistapp" ng-controller="pwlistController">
<div id="header">
    <a href="${pageContext.request.contextPath }/phoneorderctrl/getValidate.do" class="header_back"><i
            class="qx qx-back"></i></a>
    </a>
    <span class="header_title">称重商品管理</span>
    <a href="${pageContext.request.contextPath }/phoneorderctrl/loginOut.do" class="fr"><i class="qx qx-exit"></i></a>
    <a href="${pageContext.request.contextPath }/weighProduct/pwmanager.do" class="fr"><i class="qx qx-refresh"></i></a>
</div>
<div class="pd">
    <ul class="bdt_list deliverlist">
        <li class="c" ng-repeat="d in pwlist" ng-click="toweight($index,d)">
            <div class="deliver_act">
                <span class="btn btn_r btn_big">称重</span>
            </div>
            <div class="deliver_info">
                <p>{{d.product_name}}<span class="pl">{{d.pronum}}</span>
                </p>

                <p>用户名：{{d.username}}</p>

                <p>手机：{{d.phone}}</p>

                <p>下单时间：{{d.createTime|date:'yyyy-MM-dd hh:mm:ss'}}</p>
                </p>
            </div>
        </li>
    </ul>
</div>
<!-- 搜索结果  -->
<div id="pwsearchwrap" class="hide">
    <p>用户名：{{pw.username}}</p>

    <p>商品：{{pw.product_name}}</p>

    <p>数量：{{pw.real_amount}}</p>

    <p>单价：{{pw.product_prict|currency:'￥'}}/{{pw.unit}}</p>

    <p>称重斤两：
        <input type="number" ng-model="size"
               oninput="if(!/^(([0-9]+[\.]?[0-9]{0,2})|[1-9])$/.test(this.value)){this.value=''}"/> {{pw.unit}}
    </p>

    <p>总价：<span class="fc_r">{{pw.product_prict*size|currency:'￥'}}</span>
        <input id="uid" type="hidden" value="{{pw.user_id}}"/>
        <input id="pid" type="hidden" value="{{pw.produc_tid}}"/>
        <span onclick="qx()"><input
                style="float:right; color:#ff1e17; background-color:#fff1f1; border:1px solid #ff1e17;" name="quxiao"
                type="button" value="取消称重"/></span>
    </p>
</div>
<div id="jquery_jplayer"></div>
<script src="../new/jsp/saleplugins/js/common/commservice.js" type="text/javascript" charset="utf-8"></script>
<script src="../new/jsp/saleplugins/js/artDialog.source.js" type="text/javascript" charset="utf-8"></script>
<script src="../new/jsp/saleplugins/js/iframeTools.source.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(function () {
        $("#jquery_jplayer").jPlayer({
            ready: function () {
                $(this).jPlayer("setMedia", {mp3: "../new/jsp/saleplugins/audio/chengzhong.mp3"});
            },
            swfPath: "../new/jsp/saleplugins/audio",
            supplied: "mp3",
            volume: 1.8,
            preload: 'metadata',
            solution: 'flash,html '
        })
        setInterval("myInterval()", 10000);//1000为1秒钟
    });

    function qx() {
        var uid = $("#uid").val();
        var pid = $("#pid").val();
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/weighProduct/pwmanagerCancel.do",
            data: {userid: uid, proid: pid},
            success: function (data) {
                alert("取消成功！");
                window.location.reload();
            },
            error: function (err) {
                alert("error");
            }
        })
    }

    function myInterval() {
        $.ajax({
            type: "post",
            url: '${pageContext.request.contextPath }/weighProduct/pwmanagerListcount.do',
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                var dataObj = eval("(" + data + ")");
                if (dataObj.data) {
                    if(parseInt(dataObj.result_code)>0){
                        $("#jquery_jplayer").jPlayer("play");
                    }
                }
            },
            error: function (msg) {
                alert("error");
            }
        });
    }
</script>
<script type="text/javascript">
    var pwlistapp = angular.module('pwlistapp', []);
    var pwlistController = pwlistapp.controller('pwlistController', ['$scope', '$http',
        function ($scope, $http) {
            //称重列表
            $http({
                method: 'post',
                url: '${pageContext.request.contextPath }/weighProduct/pwmanagerList.do',
            }).success(function (data) {
                data = {
                    list: data.data
                }
                $scope.pwlist = data.list;
            });
            $scope.toweight = function (i, pw) {
                $scope.pw = pw;
                $scope.size = 1;
                artDialog({
                    title: '',
                    width: '92%',
                    padding: '5px 10px',
                    content: document.getElementById("pwsearchwrap"),
                    ok: function () {
                        if (!/^(([0-9]+[\.]?[0-9]{1,2})|[1-9])$/.test($scope.size) || $scope.size <= 0) {
                            artDialog.alert('请准确输入称重斤两');
                            return;
                        }
                        $http({
                            method: 'get', //post
                            url: '${pageContext.request.contextPath}/weighProduct/pwmanagerUp.do',
                            params: {
                                userid: pw.user_id,
                                proid: pw.produc_tid,
                                size: $scope.size
                            }
                        }).success(function (data) {
                            if (data.result_code == 0) {
                                artDialog.alert('提交成功')
                            }
                            $scope.pwlist.splice(i, 1);
                        })
                    },
                    cancel: function () {
                    }
                })
            }
        }
    ])
</script>
</body>

</html>
