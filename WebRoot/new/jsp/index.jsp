<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <link type="text/css" href="${ctx}/new/css/plugins/toastr/toastr.min.css" rel="stylesheet"/>
    <style type="text/css">
        /*.navbar-top-links li:last-child {
            margin-right: 30px;
        }*/
    </style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <jsp:include page="/new/jsp/include/left.jsp" />
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                        <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="javascript:void(0)">
                            <i class="fa fa-bars"></i>
                        </a>
                        <%--<form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>--%>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <!-- 未读通知 -->
                        <%--<li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                                <li class="m-t-xs">
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="${ctx}/new/img/a7.jpg">
                                        </a>
                                        <div class="media-body">
                                            <small class="pull-right">46小时前</small>
                                            <strong>小四</strong> 这个在日本投降书上签字的军官，建国后一定是个不小的干部吧？
                                            <br>
                                            <small class="text-muted">3天前 2014.11.8</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="${ctx}/new/img/a4.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right text-navy">25小时前</small>
                                            <strong>国民岳父</strong> 如何看待“男子不满自己爱犬被称为狗，刺伤路人”？——这人比犬还凶
                                            <br>
                                            <small class="text-muted">昨天</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="mailbox.html">
                                            <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>--%>
                        <li title="技术支持">
                            <a href="javascript:void(0)" onclick="showSupportWin()">
                                <i class="fa fa-support"></i>
                            </a>
                        </li>
                        <li title="帮助">
                            <a href="${ctx}/explain.htm" target="_blank">
                                <i class="fa fa-question-circle"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:void(0)" class="active J_menuTab" data-id="first.jsp">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="${ctx}/login.do?method=logoutV4" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/new/jsp/first.jsp" frameborder="0" data-id="first.jsp" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2016 <a href="http://www.qxit.com.cn/" target="_blank">区享科技</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
        <jsp:include page="/new/jsp/include/sidebar.jsp" />
        <!--右侧边栏结束-->
    </div>

    <script id="supportTemplate" type="text/html">
        <dl class="dl-horizontal">
            <dt>周一到周五 9:00-18:00</dt>
            <dd>史文晨  15715154286</dd>
            <dt>周一 晚18:00-21:00</dt>
            <dd>尚文强  18951630791</dd>
            <dt>周二 晚18:00-21:00</dt>
            <dd>卢成  13675170252</dd>
            <dt>周三 晚18:00-21:00</dt>
            <dd>吴畏  18651601740</dd>
            <dt>周四 晚18:00-21:00</dt>
            <dd>李明  15951976786</dd>
            <dt>周五 晚18:00-21:00</dt>
            <dd>尚文强  18951630791</dd>
            <dt>周六 全天9:00-21:00</dt>
            <dd>李明  15951976786</dd>
            <dt>周日 全天9:00-21:00</dt>
            <dd>吴畏  18651601740</dd>
        </dl>
    </script>

    <audio id="newOrderAudio" src="${ctx}/audio/newOrder.mp3"/>
    <script type="text/javascript" src="${ctx}/new/js/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript">
        function showSupportWin() {
            var html = $('#supportTemplate').html();
            layer.open({
                title: '技术支持',
                type: 1,
                area: ['350px', 'auto'],
                content: html,
                btn: ['关闭']
            });
        }

        //定时获取新订单
        $(function () {
            toastr.options = {
                closeButton: false, //是否显示关闭按钮
                /*debug: false, //是否使用debug模式
                positionClass: "toast-top-full-width",//弹出窗的位置
                showDuration: "300",//显示的动画时间
                hideDuration: "1000",//消失的动画时间
                timeOut: "5000", //展现时间
                extendedTimeOut: "1000",//加长展示时间
                showEasing: "swing",//显示时的动画缓冲方式
                hideEasing: "linear",//消失时的动画缓冲方式
                showMethod: "fadeIn",//显示时的动画方式
                hideMethod: "fadeOut", //消失时的动画方式*/
                onclick : null
            };

            setInterval(function() {
                $.ajax({
                    type : "post",
                    url : "${ctx}/order.do?method=getNewOrderCount",
                    ContentType : "application/json; charset=utf-8",
                    success : function(data) {
                        var dataObj = eval("("+data+")");
                        if("0"==dataObj.result_code) {
                            var obj=dataObj.data;
                            if(parseInt(obj)>0) {
                                toastr.info('您有新的订单，请注意处理！');
                                $('#newOrderAudio')[0].play();
                                //自动刷新订单页面的grid数据
                                var orderFrame = $(".J_iframe[data-id='/order.do?method=showListOrderPage']");
                                if(orderFrame.length > 0) {
                                    orderFrame.each(function(i, v) {
                                        window.frames[$(v).attr('name')].search();
                                    });
                                }
                            }
                        }
                    }
                });
            },5000);  //每隔5s请求一次
        });
    </script>

</body>

</html>