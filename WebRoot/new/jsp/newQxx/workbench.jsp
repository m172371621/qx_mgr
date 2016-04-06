<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="center sliding">工作台</div>
        <div class="right">
            <a href="#" class="link" onclick="javascript:void(mainView.router.refreshPage())">
                 <i class="fa fa-refresh fa-1_3x icon"></i>
            </a>
            <a href="#" onclick="login_out()" data-panel="right" class="link"><i class="fa fa-power-off fa-1_3x"></i></a>
        </div>
    </div>
</div>
<div class="pages navbar-through toolbar-through">
    <div data-page="workbench" class="page no-swipeback">
        <div class="page-content">
            <div id="model"></div>
            <!-- 控制台 模版-->
            <script type="text/template7" id="workbenchTemplate">
                <div class="content-block-title">今日订单</div>
                <div class="main-work content-block inset">
                    <div class="content-block-inner">
                        <div class="row">
                            <!--
                            <div class="i-b col-25">
                                <div class="psy">
                                    <a href="new/jsp/newQxx/tool/order/notOrder.jsp">
                                        <div class="pic"><img src="new/jsp/newQxx/ico/notOrder.png"></div>
                                        {{#js_compare "this.notOrder > 0"}}<i>{{notOrder}}</i>{{/js_compare}}
                                        <span>接单</span></a>
                                </div>
                            </div>
                            -->
                            <div class="i-b col-33">
                                <div class="psy">
                                    <a href="new/jsp/newQxx/tool/quHuo/quHuoOrder.jsp">
                                        <div class="pic"><img src="new/jsp/newQxx/ico/notOrder.png"></div>
                                        {{#js_compare "this.quHuoOrder > 0"}}<i>{{quHuoOrder}}</i>{{/js_compare}}
                                        <span>待取货</span></a>
                                </div>
                            </div>
                            <div class="i-b col-33">
                                <a href="new/jsp/newQxx/tool/order/deliveryOrder.jsp">
                                    <div class="pic"><img src="new/jsp/newQxx/ico/deliveryOrder.png"></div>
                                    {{#js_compare "this.deliveryOrder > 0"}}<i>{{deliveryOrder}}</i>{{/js_compare}}
                                    <span>配送中</span></a>
                            </div>
                            <div class="i-b col-33">
                                <a href="new/jsp/newQxx/tool/order/finishOrder.jsp">
                                    <div class="pic"><img src="new/jsp/newQxx/ico/finishOrder.png"></div>
                                    {{#js_compare "this.finishOrder > 0"}}<i>{{finishOrder}}</i>{{/js_compare}}
                                    <span>已完成</span></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="content-block-title">今日称重</div>
                <div class="main-work content-block inset">
                    <div class="content-block-inner">
                        <div class="row">
                            <div class="i-b col-33">
                                <a href="new/jsp/newQxx/tool/weigh/notWeigh.jsp">
                                    <div class="pic"><img src="new/jsp/newQxx/ico/notWeigh.png"></div>
                                    {{#js_compare "this.notWeigh > 0"}}<i>{{notWeigh}}</i>{{/js_compare}}
                                    <span>未称重</span></a>
                            </div>
                            <div class="i-b col-33">
                                <a href="new/jsp/newQxx/tool/weigh/weighing.jsp">
                                    <div class="pic"><img src="new/jsp/newQxx/ico/weighing.png"></div>
                                    {{#js_compare "this.weighing > 0"}}<i>{{weighing}}</i>{{/js_compare}}
                                    <span>称重中</span></a>
                            </div>
                            <div class="i-b col-33">
                                <a href="new/jsp/newQxx/tool/weigh/finishWeigh.jsp">
                                    <div class="pic"><img src="new/jsp/newQxx/ico/weighFinish.png"></div>
                                    {{#js_compare "this.weighFinish > 0"}}<i>{{weighFinish}}</i>{{/js_compare}}
                                    <span>称重完成</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <%--
                 <div class="content-block-title">服务预约</div>
                 <div class="main-work content-block inset">
                     <div class="content-block-inner">
                         <div class="row">
                             <div class="i-b col-33">
                                 <a href="new/jsp/newQxx/tool/serve/serve.jsp">
                                     <div class="pic"><img src="new/jsp/newQxx/ico/notServer.png"></div>
                                     {{#js_compare "this.finish > 0"}}<i>{{finish}}</i>{{/js_compare}}
                                     <span>未接单</span>
                                 </a>
                             </div>
                             <div class="i-b col-33">
                                 <a href="new/jsp/newQxx/tool/serve/serve.jsp">
                                     <div class="pic"><img src="new/jsp/newQxx/ico/servering.png"></div>
                                     {{#js_compare "this.finish > 0"}}<i>{{finish}}</i>{{/js_compare}}
                                     <span>议价中</span></a>
                             </div>
                             <div class="i-b col-33">
                                 <a href="new/jsp/newQxx/tool/serve/serve.jsp">
                                     <div class="pic"><img src="new/jsp/newQxx/ico/serverFinish.png"></div>
                                     {{#js_compare "this.finish > 0"}}<i>{{finish}}</i>{{/js_compare}}
                                     <span>议价完成</span></a>
                             </div>
                         </div>
                     </div>
                 </div>
                 --%>
                </div>
            </div></script>
        </div>
    </div>
</div>