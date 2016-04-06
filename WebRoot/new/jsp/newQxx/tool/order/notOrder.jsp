<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/workbench.jsp" class="back link"> <i
                class="icon icon-back"></i><span>工作台</span></a></div>
        <div class="center sliding">未接单</div>
        <!--
        <div class="right sliding">
            <a href="#" class="open-popup"><i class="fa fa-map fa-1_3x icon open-about"></i></a>
        </div>
        -->
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="notOrder" class="page no-tabbar page-on-center">
        <div class="page-content pull-to-refresh-content infinite-scroll" data-distance="50">
            <div class="pull-to-refresh-layer">
                <div class="preloader"></div>
                <div class="pull-to-refresh-arrow"></div>
            </div>
            <div id="notOrderls"></div>
            <!-- 今日订单 详情模版-->
            <script type="text/template7" id="notOrder">
                {{#model}}
                <div class="card">
                    <div class="card-header">{{num}} 订单号：{{order_serial}} <span
                            class="item-after">{{pay_type_ext}}</span></div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            <P>昵称：{{user_name}}</P>

                            <P>手机号：{{user_phone}}</P>

                            <P>订单总价：<span class="color-red">¥{{order_price}}</span>&nbsp;/&nbsp;元</P>

                            <P>所属小区：{{communityName}} </P>

                            <P>下单时间：{{order_time}}</P>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="list-block">
                            <div class="accordion-item-toggle">
                                <a href="new/jsp/newQxx/tool/order/notOrderList.jsp?order_serial={{order_serial}}"
                                   class="link color-red">查看详情</a>
                            </div>
                        </div>
                    </div>
                </div>
                {{/model}}
            </script>
        </div>
    </div>
</div>