<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/qxxresource.jsp" %>
<html>
<head>
</head>
<body>
<!-- Status bar overlay for fullscreen mode-->
<div class="statusbar-overlay"></div>
<!-- Panels overlay-->
<div class="panel-overlay"></div>
<!-- Left panel with reveal effect-->
<!--
<div class="panel panel-left panel-reveal">
    <div class="content-block">
        <p>Left panel content goes here</p>
    </div>
</div>
-->
<!-- Right panel with cover effect-->
<!--
<div class="panel panel-right panel-reveal layout-dark">
    <div class="view view-right" data-page="panel-right1">
        <div class="navbar">
            <div class="navbar-inner navbar-on-center">
                <div class="center sliding" style="left: 78.5px; transform: translate3d(0px, 0px, 0px);">Right Panel
                </div>
            </div>
        </div>
        <div class="pages navbar-through">
            <div data-page="panel-right1" class="page page-on-center">
                <div class="page-content">
                    <div class="content-block">
                        <p class="">This is a right side panel. You can close it by clicking outsite or on this link: <a
                                href="#" class="close-panel">close me</a>. You can put here anything, even another
                            isolated view, try it:</p>
                    </div>
                    <div class="list-block">
                        <ul>
                            <li><a href="#" class="item-link smart-select" data-searchbar-cancel="取消"
                                   data-open-in="popup" data-back-on-select="true" data-virtual-list="true"
                                   data-virtual-list-height="100" data-navbar-theme="red" data-form-theme="green">
                                <select name="fruits">
                                    <option value="apple" selected="">Apple</option>
                                    <option value="pineapple">Pineapple</option>
                                    <option value="pear">Pear</option>
                                    <option value="orange">Orange</option>
                                    <option value="melon">Melon</option>
                                    <option value="peach">Peach</option>
                                    <option value="banana">Banana</option>
                                </select>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title">Fruit</div>
                                    </div>
                                </div>
                            </a></li>
                            <li><a href="#" class="item-link smart-select" data-navbar-theme="red"
                                   data-form-theme="red">
                                <select name="car">
                                    <option value="mercedes">Mercedes</option>
                                    <option value="bmw">BMW</option>
                                    <option value="volvo">Volvo</option>
                                    <option value="vw" selected="">Volkswagen</option>
                                    <option value="toyota">Toyota</option>
                                    <option value="cadillac">Cadillac</option>
                                    <option value="nissan">Nissan</option>
                                    <option value="mazda">Mazda</option>
                                    <option value="ford">Ford</option>
                                    <option value="chrysler">Chrysler</option>
                                    <option value="dodge">Dodge</option>
                                </select>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title">Car</div>
                                    </div>
                                </div>
                            </a></li>
                            <li><a href="#" class="item-link smart-select">
                                <select name="mac-windows">
                                    <option value="mac" selected="">Mac</option>
                                    <option value="windows">Windows</option>
                                </select>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title">Mac or Windows</div>
                                    </div>
                                </div>
                            </a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
-->
<!-- 登陆屏    开始-->
<div class="login-screen modal-in" style="display: block;">
    <div class="view">
        <div class="page">
            <div class="page-content login-screen-content">
                <div class="login-screen-title">区享侠 登陆</div>
                <form id="login" class="ajax-submit">
                    <div class="list-block">
                        <ul>
                            <li class="item-content">
                                <div class="item-media"><i class="fa fa-user fa-1_8x"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">
                                    </div>
                                    <div class="item-input">
                                        <input type="text" name="username" placeholder="登陆名" value="admin">
                                        <input type="hidden" id="code" value="${code}">
                                    </div>
                                    <div class="item-input">
                                        <span id="msg-username" class="msg-box n-right" style="position:static;"
                                              for="username"></span>
                                    </div>
                                </div>
                            </li>
                            <li class="item-content">
                                <div class="item-media"><i class="fa fa-lock fa-1_8x"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label"></div>
                                    <div class="item-input">
                                        <input type="password" name="password" placeholder="登陆密码" value="qxit_123456">
                                    </div>
                                    <div class="item-input">
                                        <span id="msg-password" class="msg-box n-right" style="position:static;"
                                              for="password"></span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="list-block">
                        <ul>
                            <li><a href="#" class="button button-big button-fill color-red" id="loginqxx">登陆</a></li>
                        </ul>
                        <div class="list-block-label">
                            在这里，区享的每一位同仁每天都在<br/>为改变人类的小区生活方式而努力，<br/>每天的工作都充满创造力和乐趣， 为了一个简单的梦想而努力，<br/>享受每一次创造带来的快乐，我们寻找这样的你：<br/>无论你精通技术、
                            产品还是掌握其他出色的技能，<br/>或者只是和我们一样，为了一个梦想一直在奔跑。<br/>加入我们，正是最简单的开始！
                            <br/>联系地址： 南京市瞻园路21号瞻园铭楼负一层 <br/>联系邮箱：xulinkun@qxit.com.cn
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 登陆屏    结束-->

<!-- start Popup -->
<div class="popup orderMap">
    <div style="position: absolute; width: auto; height: auto; z-index: 1; right: 0">
        <a href="#" class="close-popup"><i style="color: red;" class="fa fa-close fa-2x"></i></a>
    </div>
    <div id="container"></div>
</div>
<!-- end Popup -->

<!-- Views, and they are tabs-->
<!-- We need to set "toolbar-through" class on it to keep space for our tab bar-->
<div class="views">
    <!-- Default view-page layout -->
    <div class="view view-main theme-red">
        <!-- 顶部导航栏      开始-->
        <div class="navbar theme-red">
            <div class="navbar-inner">
                <div class="left"></div>
                <div class="center">工作台</div>
                <div class="right"></div>
            </div>
        </div>
        <!-- 顶部导航栏      结束-->

        <!-- Pages start -->
        <div class="pages navbar-through toolbar-through">
            <div class="page">
                <div class="page-content">

                </div>
            </div>
        </div>
        <!-- Pages end -->
    </div>

    <!-- 底部导航栏      开始-->
    <div class="toolbar tabbar tabbar-labels" id="bottomTool">
        <div class="toolbar-inner">
            <a id="workbench" href="${ctx}/new/jsp/newQxx/workbench.jsp" class="link"><i
                    class="fa fa-desktop fa-1_8x icon"></i>
                <span class="tabbar-label">工作台</span>
            </a>
            <a id="services" href="${ctx}/new/jsp/newQxx/services.jsp" class="link"><i
                    class="fa fa-briefcase fa-1_8x icon"></i>
                <span class="tabbar-label">工具</span>
            </a>
            <a id="crm" href="${ctx}/new/jsp/newQxx/crm.jsp" class="link"><i class="fa fa-cloud fa-1_8x icon"></i>
                <span class="tabbar-label">CRM</span>
            </a>
            <a id="set" href="${ctx}/new/jsp/newQxx/set.jsp" class="link"><i class="fa fa-wrench fa-1_8x icon"></i>
                <span class="tabbar-label">设置</span>
            </a>
        </div>
    </div>
</div>
<!-- 底部导航栏      结束-->
</body>
<!-- My js-->
<script type="text/javascript" src="${ctx}/new/jsp/newQxx/js/my-app.js"></script>
</html>