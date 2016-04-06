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
        $("input[name='start_create_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
        $("input[name='end_create_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
    });

    function submitForm() {
        $('#pageIndex').val(1);
        $('#searchForm').submit();
    }

    function myconfirm(del_id)
    {
        if(confirm("确认删除该订单？"))
        {
            $('#del_id').val(del_id);
            submitForm();
        }else  {
            return;
        };
    }
</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<input type="hidden" id="curr_li" value="order_3"/>
<div id="mright">
	<div id="mr_cnt">
        <div class="mrw_title">退款订单查询</div>
        <form action="${pageContext.request.contextPath }/orderRefund.do?method=listReturnMoney" method="post" id="searchForm">
            <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex}"/>
            <div class="sform_wrap c">
                <ul>
                    <li>订单号：<input type="text" class="i_text" name="order_serial" value="${queryParam.order_serial}"/></li>
                    <li>用户名：<input type="text" class="i_text" name="username" value="${queryParam.username}"/></li>
                    <li>商品名: <input type="text" class="i_text" name="product_name" value="${queryParam.product_name}"/></li>
                    <%--<li>订单状态：<select name="order_status" class="i_text">
                        <option value="0" >全部</option>
                        <option value="1" <c:if test="${queryParam.order_status == 1}">selected="selected"</c:if> >订单提交</option>
                        <option value="2" <c:if test="${queryParam.order_status == 2}">selected="selected"</c:if> >已到货</option>
                        <option value="3" <c:if test="${queryParam.order_status == 3}">selected="selected"</c:if> >已提货</option>
                        <option value="11" <c:if test="${queryParam.order_status == 11}">selected="selected"</c:if> >待定价</option>
                        <option value="12" <c:if test="${queryParam.order_status == 12}">selected="selected"</c:if> >已定价</option>
                        <option value="21" <c:if test="${queryParam.order_status == 21}">selected="selected"</c:if> >未付款</option>
                        <option value="22" <c:if test="${queryParam.order_status == 22}">selected="selected"</c:if> >已付款</option>
                        <option value="23" <c:if test="${queryParam.order_status == 23}">selected="selected"</c:if> >已取消</option>
                    </select>
                    </li>
                    <li>支付类型：<select name="pay_type" class="i_text">
                        <option value="0" >全部</option>
                        <option value="1" <c:if test="${queryParam.pay_type == 1}">selected="selected"</c:if> >现金</option>
                        <option value="2" <c:if test="${queryParam.pay_type == 2}">selected="selected"</c:if> >线上</option>
                    </select>
                    </li>--%>
                    <li>退货状态：
                        <select name="status" class="i_text">
                            <option value="">全部</option>
                            <option value="0" <c:if test="${queryParam.status == 0}">selected="selected"</c:if> >待处理</option>
                            <option value="1" <c:if test="${queryParam.status == 1}">selected="selected"</c:if> >完成</option>
                            <option value="-1" <c:if test="${queryParam.status == -1}">selected="selected"</c:if> >驳回</option>
                        </select>
                    </li>
                    <li>退款状态：
                        <select name="refund_status" class="i_text">
                            <option value="">全部</option>
                            <option value="0" <c:if test="${queryParam.refund_status == 0}">selected="selected"</c:if> >待处理</option>
                            <option value="1" <c:if test="${queryParam.refund_status == 1}">selected="selected"</c:if> >处理中</option>
                            <option value="2" <c:if test="${queryParam.refund_status == 2}">selected="selected"</c:if> >完成</option>
                            <option value="-1" <c:if test="${queryParam.refund_status == -1}">selected="selected"</c:if> >失败</option>
                            <option value="-2" <c:if test="${queryParam.refund_status == -2}">selected="selected"</c:if> >无需退款</option>
                        </select>
                    </li>
                    <li></li>
                    <li>退货申请时间：<input type="text" style="width:90px" readonly class="i_text" name="start_create_time"  value="${queryParam.start_create_time}"/></li>
                    <li>-&nbsp;&nbsp;&nbsp;<input type="text" style="width:90px" readonly class="i_text" name="end_create_time" value="${queryParam.end_create_time}"/></li>
                    <li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
                </ul>
            </div>
        </form>
        <div id="order_wrap" class="p20">
            <table class="table_t_t">
                <tr>
                    <th width="150px">订单号</th>
                    <th width="100px">用户名</th>
                    <th width="150px">商品名</th>
                    <th width="80px">退款类型</th>
                    <th width="70px">退货数量</th>
                    <th width="80px">退款金额</th>
                    <th width="100px">申请时间</th>
                    <th width="80px">退货状态</th>
                    <th width="80px">退款状态</th>
                    <th  width="80px" class="icf">&#xf013e;</th>
                </tr>
                <c:forEach items="${list}" var="refund">
                    <tr>
                        <td>${refund.order_serial}</td>
                        <td>${refund.loginname}</td>
                        <td>${refund.product_name}</td>
                        <td>
                            <c:if test="${refund.refund_type == 1}">现金</c:if>
                            <c:if test="${refund.refund_type == 2}">区享卡</c:if>
                            <c:if test="${refund.refund_type == 3}">支付宝</c:if>
                            <c:if test="${refund.refund_type == 4}">微信</c:if>
                        </td>
                        <td>${refund.refund_amount}</td>
                        <td>${refund.refund_price}</td>
                        <td title="<fmt:formatDate value='${refund.create_time}' pattern='yyyy-MM-dd HH:mm'/> ">
                            <fmt:formatDate value='${refund.create_time}' pattern='yyyy-MM-dd HH:mm'/>
                        </td>
                        <td>
                            <c:if test="${refund.status == 0}">待处理</c:if>
                            <c:if test="${refund.status == 1}">完成</c:if>
                            <c:if test="${refund.status == -1}">驳回</c:if>
                        </td>
                        <td>
                            <c:if test="${refund.refund_status == 0}">待处理</c:if>
                            <c:if test="${refund.refund_status == 1}">处理中</c:if>
                            <c:if test="${refund.refund_status == 2}">完成</c:if>
                            <c:if test="${refund.refund_status == -1}">失败</c:if>
                            <c:if test="${refund.refund_status == -2}">无需退款</c:if>
                        </td>
                        <td>
                            <c:if test="${refund.status == 1 && refund.refund_status == 0}">
                                <a target="_blank" href="${pageContext.request.contextPath }/orderRefund.do?method=returnMoney&objid=${refund.objid}" class="btn btn_r">退款</a>
                            </c:if>
                        </td>
                    </tr>
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
