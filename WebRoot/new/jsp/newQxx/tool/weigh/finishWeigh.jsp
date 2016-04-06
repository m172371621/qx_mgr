<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/index.jsp" class="back link"> <i
                class="icon icon-back"></i><span>工作台</span></a></div>
        <div class="center sliding">称重完成</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="finishWeigh" class="page no-tabbar">
        <div class="page-content pull-to-refresh-content infinite-scroll" data-distance="50">
            <div class="pull-to-refresh-layer">
                <div class="preloader"></div>
                <div class="pull-to-refresh-arrow"></div>
            </div>
            <div id="finishWeighls">
                <script type="text/template7" id="finishWeigh">
                    {{#model}}
                    <div class="card">
                        <div class="card-header">{{num}} 商品名称：{{name}}</div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <P>用户名：{{username}}</P>

                                <P>手机号：{{phone}}</P>

                                <P>单位：¥{{price}}&nbsp;/&nbsp;{{unit}}</P>

                                <P>下单数量：{{f_amount}}</P>

                                <P>所属小区：{{communityName}} </P>

                                <P>下单时间：{{createTime}}</P>
                            </div>
                        </div>
                        <!--
                        <div class="card-footer">
                            <a href="/new/jsp/newQxx/tool/order/notOrderList.jsp?order_serial={{order_serial}}"
                               class="link "></a>
                        </div>
                        -->
                    </div>
                    {{/model}}
                </script>
            </div>
        </div>
    </div>
</div>