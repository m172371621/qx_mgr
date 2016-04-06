<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="notOrder" class="back link"> <i
                class="icon icon-back"></i><span>未接单</span></a></div>
        <div class="center sliding">未接单 - 详情</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="notOrderList" class="page no-tabbar page-on-center">
        <div class="page-content">
            <div id="notOrderListInfo">
                <script type="text/template7" id="notOrderXq">
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
                                    <span style="float:right;padding-right:30px;">&nbsp;&nbsp;*&nbsp;&nbsp; {{product_amount}}</span><br/>
                                    {{/model}}
                                </div>
                            </div>
                        </div>
                        <div class="card-footer ">
                            <a></a>
                            <span>总计: <span style="font-size: 20px; color:red;"> ¥{{order_price}}</span></span>
                        </div>
                    </div>
                   {{youhui}}
                    {{#js_compare "this.delivery_type == 1"}}
                    <a class="button button-big button-fill color-red" onclick="set('{{order_serial}},1')">
                        自提
                    </a>
                    {{else}}
                    <a class="button button-big button-fill color-red" onclick="acceptOrder('{{order_serial}}')">
                        送货
                    </a>
                    {{/js_compare}}
                </script>
            </div>
        </div>
    </div>
</div>