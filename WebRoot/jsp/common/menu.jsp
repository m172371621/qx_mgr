<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.brilliantreform.sc.user.mgrpo.Role" %>
<% List<Role> list_role = (List<Role>) session.getAttribute("user_role");
    int role_id = list_role.get(0).getRole_id();
    if(role_id == 3) {
%>
<dl>
    <dt><i class="icf">&#xf0138;</i>物业管理</dt>
    <dd>
        <ul id="msg">
            <li><a href="${pageContext.request.contextPath }/task.do?method=propertyInfoList">物业公告</a></li>
            <li><a href="${pageContext.request.contextPath }/task.do?method=searchPropertyTask">物业任务</a></li>
        </ul>
    </dd>
</dl>
<%
    } else if (role_id == 1 || role_id == 2) {
%>
<div class="fl" id="mnav">
    <dl>
        <dt><i class="icf">&#xf012d;</i>用户管理</dt>
        <dd>
            <ul id="user">
                <li><a href="${pageContext.request.contextPath }/qxuser.do?method=list">区享用户管理</a></li>
                <li><a href="${pageContext.request.contextPath }/qxuser.do?method=createUser">新增区享用户</a></li>
                <li><a href="${pageContext.request.contextPath }/user.do?method=list">后台用户管理</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf01be;</i>订单及预约</dt>
        <dd>
            <ul id="order">
                <li>
                    <a href="${pageContext.request.contextPath }/order.do?method=listOrder">订单查询</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/order.do?method=listOrder&page=listPickUpOrder&order_status_array=22,2,1&delivery_type=1">订单提货</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/order.do?method=listOrder&page=listPreSaleOrder&order_status_array=5">订单到货</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/task.do?method=getService_order">预约服务产品</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/orderRefund.do?method=listReturnMoney">退款管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/orderRefund.do?method=listReturnGoods">退货管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/payLogCtrl.do?method=list_weixinpaylog">微信支付记录查询</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/alipayLogCtrl.do?method=alipaylog_list">支付宝支付记录查询</a>
                </li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00c6;</i>快递管理</dt>
        <dd>
            <ul id="express">
                <li><a href="${pageContext.request.contextPath }/express.do?method=add">快递录入</a></li>
                <li><a href="${pageContext.request.contextPath }/express.do?method=list">快递签收</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00c6;</i>商品管理</dt>
        <dd>
            <ul id="product">
                <li><a href="${pageContext.request.contextPath }/stock.do?method=stockPage">总库</a></li>
                <li><a href="${pageContext.request.contextPath }/product.do?method=list">商品管理</a></li>
                <li><a href="${pageContext.request.contextPath }/product.do?method=listService">商品类别管理</a></li>
                <li><a href="${pageContext.request.contextPath }/product.do?method=listRule">营销规则管理</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>区享卡</dt>
        <dd>
            <ul id="qxcard">
                <li><a href="${pageContext.request.contextPath }/qxcard.do?method=list_reglog">区享卡管理</a></li>
                <li><a href="${pageContext.request.contextPath }/qxcard.do?method=userQxCardlist">用户区享卡查询</a></li>
                <li><a href="${pageContext.request.contextPath }/jsp/qxcard/recharge.jsp">区享卡充值</a></li>
                <li><a href="${pageContext.request.contextPath }/qxcard.do?method=showSearchQxcardCzLogPage" target="_blank">区享卡充值查询</a></li>
                <li><a href="${pageContext.request.contextPath }/qxcard.do?method=qxcard_optlog_list">区享卡操作查询</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>卡牌抽奖</dt>
        <dd>
            <ul id="qxcard_award">
                <li><a href="${pageContext.request.contextPath }/order.do?method=winning">中奖记录查询</a></li>
                <li><a href="${pageContext.request.contextPath }/card.do?method=cardManager">卡牌中奖设置</a></li>
                <li><a href="${pageContext.request.contextPath}/card.do?method=cardNum">卡牌中奖数量设置</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>服务管理</dt>
        <dd>
            <ul id="service">
                <li><a href="${pageContext.request.contextPath }/service.do?method=listService">服务信息配置</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>广告管理</dt>
        <dd>
            <ul id="ad">
                <li><a href="${pageContext.request.contextPath }/ad.do?method=listAd">广告管理</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>库存管理</dt>
        <dd>
            <ul id="stock">
                <li>
                    <a href="${pageContext.request.contextPath}/purchase.do?method=showPurchasePage" target="_blank">商品进货</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/purchase.do?method=showListPurchasePage" target="_blank">进货单</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/incommmingOrder.do?method=list_incomming_head&stock_type=1">入库单管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_head&stock_type=3">退货单管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/incommmingOrder.do?method=list_incomming_head&stock_type=2">调入单管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_head&stock_type=5">调出单管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_head&stock_type=4">损耗单管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/order.do?method=queryBatchList">批次查询</a>
                </li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf0138;</i>物业管理</dt>
        <dd>
            <ul id="msg">
                <li><a href="${pageContext.request.contextPath }/task.do?method=propertyInfoList">物业公告</a></li>
                <li><a href="${pageContext.request.contextPath }/task.do?method=searchPropertyTask">物业任务</a></li>
            </ul>
        </dd>
    </dl>
    <dl>
        <dt><i class="icf">&#xf00d0;</i>日常统计</dt>
        <dd>
            <ul id="statistics">
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=date_sale_report">销售日报表</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=fee_sumery">支付日报表</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=qxcard_sumeric">区享卡售卖统计</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=newadd_sumeric">新增用户统计表</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=saleprofit">商品销售利润表</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=orderps_sumery">订单配送统计表</a></li>
                <li><a href="${pageContext.request.contextPath }/reportmain.do?method=service_sale_report">商品分类销售日报表</a>
                </li>
            </ul>
        </dd>
    </dl>
</div>
<%} else if (role_id == 4) {%>
<div class="fl" id="mnav">

    <dl>
        <dt><i class="icf">&#xf01be;</i>订单管理</dt>
        <dd>
            <ul id="order">
                <li><a href="${pageContext.request.contextPath }/order.do?method=orderPrice">订单定价</a></li>
            </ul>
        </dd>
    </dl>

</div>
<%}%>
 