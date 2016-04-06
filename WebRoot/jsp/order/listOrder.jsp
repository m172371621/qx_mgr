<%@ page import="java.util.Map" %>
<%@ page import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.apache.commons.lang.time.DateUtils" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    Object obj = request.getAttribute("queryParam");
    String end_time = "";
    String pickup_end_time = "";
    if(obj != null) {
        Map queryParam = (Map) obj;
        Date end_date = CommonUtil.safeToDate(queryParam.get("end_time"), "yyyy-MM-dd");
        Date pickup_end_date = CommonUtil.safeToDate(queryParam.get("pickup_end_time"), "yyyy-MM-dd");
        if(end_date != null) {
            end_time = CommonUtil.formatDate(DateUtils.addDays(end_date, -1), "yyyy-MM-dd");
            request.setAttribute("end_time", end_time);
        }
        if(pickup_end_date != null) {
            pickup_end_time = CommonUtil.formatDate(DateUtils.addDays(pickup_end_date, -1), "yyyy-MM-dd");
            request.setAttribute("pickup_end_time", pickup_end_time);
        }
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />

<script type="text/javascript">
    $(function() {
        $("input[name='start_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
        $("input[name='end_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
        $("input[name='pickup_start_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
        $("input[name='pickup_end_time']").datepicker({
            dateFormat: "yy-mm-dd"
        })
    });

    function submitForm() {
        $('#pageIndex').val(1);
        $('#searchForm').submit();
    }

    function clickTr(order_serial, obj) {
        var productTrs = $('tr[order_serial=' + order_serial + ']');
        if(productTrs.is(':visible')) {
            $('tr[order_serial=' + order_serial + ']').hide();
            $(obj).find('td').css('background', '');
        } else {
            $('tr[order_serial=' + order_serial + ']').show();
            $(obj).find('td').css('background', 'orange');
            $(obj).find('td:last').css('background', '');
        }

    }
</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
	<div id="mr_cnt">
        <div class="mrw_title">订单查询</div>
            <form action="${pageContext.request.contextPath }/order.do?method=listOrder" method="post" id="searchForm">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex}"/>
                <div class="sform_wrap c">
                    <ul>
                        <li>订单号：<input type="text" class="i_text" name="order_serial" value="${queryParam.order_serial}"/></li>
                        <li>商品名：<input type="text" class="i_text" name="product_name" value="${queryParam.product_name}"/></li>
                        <li>用户名：<input type="text" class="i_text" name="user_name"  value="${queryParam.user_name}"/></li>
                        <li>手机号码：<input type="text" class="i_text" name="order_phone" value="${queryParam.order_phone}"/></li>
                        <li>订单状态：<select name="order_status_array" class="i_text">
                            <option value="">全部</option>
                            <option value="5" <c:if test="${order_status_array == '5'}">selected</c:if> >未到货</option>
                            <option value="21" <c:if test="${order_status_array == '21'}">selected</c:if> >待支付</option>
                            <option value="1,2,22" <c:if test="${order_status_array == '1,2,22'}">selected</c:if> >待收货</option>
                            <option value="3" <c:if test="${order_status_array == '3'}">selected</c:if> >已提货</option>
                            <option value="4,23" <c:if test="${order_status_array == '4,23'}">selected</c:if> >已取消</option>
                        </select>
                        </li>
                        <li>付款方式：<select name="pay_type_ext" class="i_text">
                            <option value="" >全部</option>
                            <option value="11" <c:if test="${queryParam.pay_type_ext == 11}">selected</c:if> >现金</option>
                            <option value="12" <c:if test="${queryParam.pay_type_ext == 12}">selected</c:if> >刷卡</option>
                            <option value="13" <c:if test="${queryParam.pay_type_ext == 13}">selected</c:if> >混合</option>
                            <option value="21" <c:if test="${queryParam.pay_type_ext == 21}">selected</c:if> >区享卡</option>
                            <option value="22" <c:if test="${queryParam.pay_type_ext == 22}">selected</c:if> >支付宝</option>
                            <option value="23" <c:if test="${queryParam.pay_type_ext == 23}">selected</c:if> >微信</option>
                        </select>
                        </li>
                        <li></li>
                        <li>商品类别：<select name="sid" class="i_text">
                            <option value="">全部</option>
                            <c:forEach items="${sessionScope.product_service}" var="service">
                                <option value="${service.service_id}" <c:if test="${service.service_id == queryParam.sid}">selected</c:if> >${service.service_name}</option>
                            </c:forEach>
                        </select>
                        </li>
                        <li>订单时间：
                            <input type="text" style="width:90px" readonly class="i_text" name="start_time"  value="${queryParam.start_time}"/>
                            &nbsp;-&nbsp;
                            <input type="text" style="width:90px" readonly class="i_text" name="end_time" value="${end_time}" /></li>
                        <li>提货时间：
                            <input type="text" style="width:90px" readonly class="i_text" name="pickup_start_time"  value="${queryParam.pickup_start_time}"/>
                            &nbsp;-&nbsp;
                            <input type="text" style="width:90px" readonly class="i_text" name="pickup_end_time" value="${pickup_end_time}" /></li>
                        <li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
                    </ul>
                </div>
            </form>
            <div id="order_wrap" class="p20">
                <table class="table_t_t">
                    <tr>
                        <th width="160px">订单号</th>
                        <th width="180px">商品名</th>
                        <th width="100px">用户名</th>
                        <th width="60px">订单状态</th>
                        <th width="70px">付款方式</th>
                        <th width="100px">手机号</th>
                        <th width="60px">商品数量</th>
                        <th width="70px">订单总价</th>
                        <th width="70px">配送状态</th>
                        <th width="130px">订单时间</th>
                        <th width="80px" class="icf">&#xf013e;</th>
                    </tr>
                    <c:forEach items="${orderBaseList}" var="orderBase">
                        <tr ondblclick="clickTr('${orderBase.order_serial}', this)">
                            <td>
                                <font <c:if test="${orderBase.order_status == 1 || orderBase.order_status == 2 || orderBase.order_status == 22}">color="red"</c:if> >${orderBase.order_serial}</font>
                            </td>
                            <td></td>
                            <td>${orderBase.username}</td>
                            <td>
                                <c:if test="${orderBase.order_status == 5}">未到货</c:if>
                                <c:if test="${orderBase.order_status == 21}">待支付</c:if>
                                <c:if test="${orderBase.order_status == 1 || orderBase.order_status == 2 || orderBase.order_status == 22}">待收货</c:if>
                                <c:if test="${orderBase.order_status == 3}">已提货</c:if>
                                <c:if test="${orderBase.order_status == 4 || orderBase.order_status == 23}">已取消</c:if>
                            </td>
                            <td>
                                <c:if test="${orderBase.pay_type_ext == 11}">现金</c:if>
                                <c:if test="${orderBase.pay_type_ext == 12}">刷卡</c:if>
                                <c:if test="${orderBase.pay_type_ext == 13}">混合</c:if>
                                <c:if test="${orderBase.pay_type_ext == 21}">区享卡</c:if>
                                <c:if test="${orderBase.pay_type_ext == 22}">支付宝</c:if>
                                <c:if test="${orderBase.pay_type_ext == 23}">微信</c:if>
                            </td>
                            <td title="${orderBase.delivery_phone}">${orderBase.delivery_phone}</td>
                            <td></td>
                            <td>${orderBase.order_price}</td>
                            <td>
                                <c:if test="${orderBase.distri_staus == -1}">未接单</c:if>
                                <c:if test="${orderBase.distri_staus == 0}">配送中</c:if>
                                <c:if test="${orderBase.distri_staus == 1}">配送完成</c:if>
                            </td>
                            <td><fmt:formatDate value="${orderBase.order_time}" pattern="yyyy-MM-dd HH:mm"/> </td>
                            <td>
                                <a target="_blank" href="${pageContext.request.contextPath }/order.do?method=showOrder&order_serial=${orderBase.order_serial}" class="btn btn_r">修改</a>
                            </td>
                        </tr>
                        <c:forEach items="${orderProductMap[orderBase.order_serial]}" var="orderProduct">
                            <tr style="display: none;" order_serial="${orderProduct.order_serial}">
                                <td></td>
                                <td title="${orderProduct.product_name}">${orderProduct.product_name}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <fmt:formatNumber type="number" value="${orderProduct.product_amount}" maxFractionDigits="2"/>
                                </td>
                                <td>
                                    <fmt:formatNumber type="number" value="${orderProduct.product_amount * orderProduct.product_price}" maxFractionDigits="2"/>
                                </td>
                                <td></td>
                                <td></td>
                                <td>
                                    <c:if test="${orderBase.order_status != 23 && orderBase.order_status != 4}">
                                        <a target="_blank" href="${pageContext.request.contextPath }/orderRefund.do?method=showRefund&orderid=${orderProduct.order_id}" class="btn btn_r" style="background-color:rgba(28, 187, 111, 0.98);">退货</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </table>
            </div>

	<jsp:include  page="../common/page.jsp"  flush="true">
		 <jsp:param  name="pageIndex"  value="${pageIndex}"/>
		 <jsp:param  name="pageCount"  value="${pageCount}"/>
	</jsp:include>

	</div>
</div>

</div>
</body>
</html>
