<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/tool/weigh/finishWeigh.jsp" class="back link"> <i
                class="icon icon-back"></i><span>称重完成</span></a></div>
        <div class="center sliding">称重完成 - 详情</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="finishWeighList" class="page no-tabbar">
        <div class="page-content">
            <div class="content-block">
                <div id="finishWeighListInfo">
                    <script type="text/template7" id="finishWeighLsQx">
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
                                <span>总计: <span style="font-size: 20px; color:red;"> ¥{{order_price}}</span></span>
                            </div>
                        </div>
                        {{youhui}}
                        <div class="card">
                            <div class="card-content">
                                <div class="card-footer ">
                                    <a></a>
                                    <a class="button button-fill button-bign" onclick="set('{{order_serial}}')">
                                        {{#js_compare "this.delivery_type == 1"}} 自提 {{else}} 提货 {{/js_compare}}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>