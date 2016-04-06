<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/tool/order/finishOrder.jsp" class="back link"> <i
                class="icon icon-back"></i><span>已完成</span></a></div>
        <div class="center sliding">已完成 - 详情</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="finishOrderList" class="page no-tabbar page-on-center">
        <div class="page-content">
            <div id="finishOrderListInfo"></div>
            <script type="text/template7" id="finishOrderXq">
                <div class="card">
                    <div class="card-content">
                        <div class="card-content-inner">
                            <P>手机号：{{delivery_phone}}</P>

                            <P>配送地址：{{delivery_addr}}</P>

                            <P>完成时间：{{delivery_time}}</P>

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
                                <span style="width: 20px;float:right;">{{product_price}}</span>
                                <span style="float:right;padding-right:30px;">* {{js "this.product_amount-this.refund_amount"}}</span><br/>
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
            </script>
        </div>
    </div>
</div>