<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="notOrder" class="back link"> <i
                class="icon icon-back"></i><span>未接单</span></a></div>
        <div class="center sliding">取货 - 详情</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="quHuoOrderList" class="page no-tabbar page-on-center">
        <div class="page-content">
            <div id="quHuoOrderListInfo">
                <script type="text/template7" id="quHuoOrderXq">
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
                    <div class="card">
                        <div class="card-header">商品清单</div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <div class="card-content">
                                    {{#model}}
                                    <span>{{product_name}}</span>
                                    <span style=" width: 20px;float:right;">{{product_price}}</span>
                                    <span style="float:right;padding-right:30px;">&nbsp;&nbsp;*&nbsp;&nbsp; {{js "this.product_amount-this.refund_amount"}}</span><br/>
                                    {{/model}}
                                </div>
                            </div>
                        </div>
                        <div class="card-footer ">
                            <a></a>
                            <span>总计: <span style="font-size: 20px; color:red;"> ¥{{js "this.order_price-this.refund_money"}}</span></span>
                        </div>
                    </div>
                   {{youhui}}
                    <a class="button button-big button-fill color-red" onclick="quHuo('{{order_serial}}')">
                        取货
                    </a>
                </script>
            </div>
        </div>
    </div>
</div>