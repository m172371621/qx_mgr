<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="left"><a href="new/jsp/newQxx/index.jsp" class="back link"> <i
                class="icon icon-back"></i><span>工作台</span></a></div>
        <div class="center sliding">称重中</div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="weighing" class="page no-tabbar">
        <div class="page-content pull-to-refresh-content infinite-scroll" data-distance="50">
            <div class="pull-to-refresh-layer">
                <div class="preloader"></div>
                <div class="pull-to-refresh-arrow"></div>
            </div>
            <div id="weighingls">
                <script type="text/template7" id="weighing">
                    {{#model}}
                    <div class="card">
                        <div class="card-header">{{num}} 商品名称：{{name}}</div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <P>用户名：{{username}}</P>

                                <P>手机号：{{phone}}</P>

                                <P>单位：<span class="color-red">¥{{product_price}}</span>&nbsp;/&nbsp;{{unit}}</P>

                                <P>所属小区：{{communityName}} </P>

                                <P>下单时间：{{createTime}}</P>
                            </div>
                        </div>
                        <div class="card-footer">
                            <div class="list-block">
                                <div class="content-block accordion-list custom-accordion">
                                    <div class="accordion-item">
                                        <div class="accordion-item-toggle">
                                            <i class="fa fa-sort-asc icon-minus"></i>
                                            <i class="fa fa-sort-desc icon-plus"></i>
                                            <span>去称重</span>
                                        </div>
                                        <div class="accordion-item-content">
                                            <div class="item-content">
                                                <div class="item-title label">称重斤两：</div>
                                                <div class="item-input">
                                                    <input id="size" type="tel" placeholder="请输入"
                                                           onchange="price(this,'{{product_price}}')">
                                                </div>
                                                {{unit}}
                                            </div>
                                            <div class="item-content">
                                                <a></a>

                                                <div>
                                                    总价:<strong id="zj" style="font-size: 20px; color:red;"></strong>
                                                </div>
                                            </div>
                                            <div class="item-content">
                                                <button class="button color-red"
                                                        onclick="weighCancel('{{user_id}},{{product_id}}')">取消称重
                                                </button>
                                                <button class="button color-red"
                                                        onclick="weighOk('{{user_id}},{{product_id}}')">确定
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    {{/model}}
                </script>
            </div>
        </div>
    </div>
</div>