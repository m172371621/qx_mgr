<%@ page language="java" import="com.brilliantreform.sc.order.po.OrderSearch" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.QueryBatch" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.JsonUtil" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page language="java" import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">批次查询</div> 
					<div class="sform_wrap c">
							<ul>
							<% 
							 
							   OrderSearch searchBean = request.getAttribute("searchBean") == null? new OrderSearch(): (OrderSearch)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   int order_status = searchBean.getOrder_status() == null ? 0 : searchBean.getOrder_status();	
							   int pay_type = searchBean.getPay_type() == null ? 0 : searchBean.getPay_type();	
							   int cid = searchBean.getCid() == null ? 0 : searchBean.getCid();	
							   int sid = searchBean.getSid() == null ? 0 : searchBean.getSid();	 
							%>	
								<% 
							//	OrderSearch searchBean = request.getAttribute("searchBean") == null? new OrderSearch(): (OrderSearch)request.getAttribute("searchBean");
								//Order od = request.getAttribute("orderList") == null? new Order():(Order)request.getAttribute("order");	
List<QueryBatch> batchs = (List<QueryBatch>)request.getAttribute("queryBatch") == null ? new ArrayList<QueryBatch>():(List<QueryBatch>)request.getAttribute("queryBatch");
								%>
								<%--<%= JsonUtil.obj2Str(searchBean.getOrder_serial()) %>--%>
								<li>商品名:<input type="text" class="i_text" name="order_serial1" id="order_serial1" value="<%= JsonUtil.obj2Str(request.getAttribute("service_name")==null?null:request.getAttribute("service_name")) %>"/></li>
								<li>
									<a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a>
									<%--<a href="javascript:save()" class="btn btn_big btn_y">保 存</a>--%>
								</li>
							</ul>
						</div>
						
					
							<form action="${pageContext.request.contextPath }/order.do?method=queryBatchList" method="post" id="searchForm">
							<input type="hidden" name="service_name" value="<%= JsonUtil.obj2Str(request.getAttribute("service_name")) %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
							</form> 
						<script type="text/javascript">
						function submitForm()
						{
							$("input[name='service_name']").val($("input[name='order_serial1']").val());
							$('#searchForm').submit();
						}	
						</script>
						
						
					<div id="order_wrap" class="p20">
						<form action="${pageContext.request.contextPath }/order.do?method=saveBatchStock" method="post" id="stockForm">
							<table class="table_t_t">
							<colgroup><col width="160px"/><col width="180px"/><col width="130px"/><col width="160px"/><col width="160px"/></colgroup>
								<tr>
									<th>商品名</th>
									<th>商品分类</th>
									<th>进价 </th>
									<th>批次数量</th>
									<th>批次号 </th>
								</tr>

									<%for(QueryBatch q : batchs) {%>
									<tr>
										<td><%=q.getName() %></td>
										<td><%=q.getService_name() %></td>
										<td><%=q.getIncommint_price() %></td>
										<td>
											<input type="hidden" name="batch_serial" value="<%=q.getBatch_serial()%>"/>
											<input type="hidden" name="product_id" value="<%=q.getProduct_id()%>"/>
											<input type="hidden" name="old_stock_num" value="<%=q.getStock_sum() %>"/>
											<input type="text" class="i_text" name="stock_num" value="<%=q.getStock_sum() %>" onblur="check(this, '<%=q.getStock_sum() %>')"/>
										</td>
										<td><%=q.getBatch_serial() %></td>
									</tr>
									<%} %>




							</table>
						</form>
					</div>
					
<jsp:include  page="../common/page.jsp"  flush="true">   
     <jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/>  
</jsp:include>	
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
	function check(obj, old_num) {
		var num = $(obj).val();
		if(num == null || $.trim(num) == '') {
			//没有填的话默认为0
			$(obj).val(0);
		} else {
			if(!/^\d+$/.test(num)) {
				alert("请输入正确的数字");
				$(obj).val(old_num);
				$(obj).focus();
			}
		}
	}

	function save() {
		$('#stockForm').submit();
	}
</script>
</div>
</body>
</html>
