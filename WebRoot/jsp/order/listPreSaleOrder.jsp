<%@ page import="com.brilliantreform.sc.product.po.ProductTags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            //$(obj).find('td:last').css('background', '');
        }

    }

    function selectAll() {
        var checked = $('#selectAllCheckBox').prop('checked');
        $('input[name=ids]').each(function() {
            $(this).prop('checked', checked);
        });
    }

    function changeOrder() {
        var ids = "";
        $('input[name=ids]').each(function() {
           if($(this).prop('checked')) {
               ids += $(this).val() + ",";
           }
        });
        if(ids != '') {
            ids = ids.substring(0, ids.length - 1);
        }
        if(ids == '') {
            alert("请选择需要设置到货的订单");
            return;
        }
        if(confirm("确定要设置到货吗？")) {
            $.post('${pageContext.request.contextPath }/order.do?method=updatePreSaleOrder', {ids : ids}, function(data) {
                if(data != null && data == 'success') {
                    alert("操作成功！");
                    location.reload();
                } else {
                    alert("操作失败！");
                }
            });
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
        <div class="mrw_title">商品到货</div>
            <form action="${pageContext.request.contextPath }/order.do?method=listOrder" method="post" id="searchForm">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex}"/>
                <input type="hidden" name="order_status_array" value="5"/>
                <input type="hidden" name="page" value="listPreSaleOrder"/>
                <div class="sform_wrap c">
                    <ul>
                        <li>订单号：<input type="text" class="i_text" name="order_serial" value="${queryParam.order_serial}"/></li>
                        <li>商品名：<input type="text" class="i_text" name="product_name" value="${queryParam.product_name}"/></li>
                        <li>用户名：<input type="text" class="i_text" name="user_name"  value="${queryParam.user_name}"/></li>
                        <li>手机号码：<input type="text" class="i_text" name="order_phone" value="${queryParam.order_phone}"/></li>
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
                            <input type="text" style="width:90px" readonly class="i_text" name="end_time" value="${queryParam.end_time}" />
                        </li>
                        <li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
                    </ul>
                </div>
            </form>
            <div id="order_wrap" class="p20">
                <a href="javascript:changeOrder()" class="btn btn_big btn_y" style="margin-bottom: 5px;">设置到货</a>
                <table class="table_t_t">
                    <tr>
                        <th width="30px"><input type="checkbox" onchange="selectAll()" id="selectAllCheckBox"/></th>
                        <th width="160px">订单号</th>
                        <th width="180px">商品名</th>
                        <th width="100px">用户名</th>
                        <th width="60px">订单状态</th>
                        <th width="70px">支付类型</th>
                        <th width="80px">手机号</th>
                        <th width="60px">商品数量</th>
                        <th width="70px">订单总价</th>
                        <th width="70px">配送状态</th>
                        <th width="130px">订单时间</th>
                    </tr>
                    <c:forEach items="${orderBaseList}" var="orderBase">
                        <tr ondblclick="clickTr('${orderBase.order_serial}', this)">
                            <td><input type="checkbox" name="ids" value="${orderBase.order_serial}"/></td>
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
                        </tr>
                        <c:forEach items="${orderProductMap[orderBase.order_serial]}" var="orderProduct">
                            <tr style="display: none;" order_serial="${orderProduct.order_serial}">
                                <td></td>
                                <td></td>
                                <td>${orderProduct.product_name}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>${orderProduct.product_amount}</td>
                                <td>
                                    <fmt:formatNumber type="number" value="${orderProduct.product_amount * orderProduct.product_price}" maxFractionDigits="2"/>
                                </td>
                                <td></td>
                                <td></td>
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
