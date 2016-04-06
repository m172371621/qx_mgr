<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/tool/weigh/notWeigh.jsp" class="back link"> <i
                class="icon icon-back"></i><span>未称重</span></a></div>
        <div class="center sliding">未称重 - 详情</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="notWeighList" class="page no-tabbar">
        <div class="page-content">
            <div class="content-block">
                <div id="notWeighListInfo">
                    <script type="text/template7" id="notWeighLs">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-content-inner">
                                    <P>手机号：{{delivery_phone}}</P>

                                    <P>配送地址：{{delivery_addr}}</P>

                                    <P>配送时间：{{delivery_time}}</P>

                                    <P>订单号：{{order_serial}}</P>
                                </div>
                            </div>
                        </div>
                        <div class="content-block-title">商品清单:</div>
                        <div class="card">
                            <div class="card-content">
                                <div class="card-content-inner">
                                    <div class="card-content">
                                        {{#model}}
                                        <span>{{product_name}}</span>
                                        <span style="text-align: center;">&nbsp;&nbsp;*&nbsp;&nbsp; {{product_amount}}</span>
                                        <span style="float:right;">{{product_price}}</span><br/>
                                        {{/model}}
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer ">
                                <a></a>
                                <span>总计: {{order_price}}</span>
                            </div>
                        </div>
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>