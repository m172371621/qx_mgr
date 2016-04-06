<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.weixin.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>
<%@page import="com.brilliantreform.sc.alipay.po.AliPayLogBean"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='time_from1']").datepicker({
        dateFormat: "yy-mm-dd"
    });
	$("input[name='time_to1']").datepicker({
        dateFormat: "yy-mm-dd"
    });
});
</script>

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">支付宝付款记录查询</div>
					<div class="sform_wrap c">
				
							<ul>
							<% 
							   Map searchBean = (Map)request.getAttribute("searchBean") ;
							   if(searchBean == null)
							   {
								   searchBean = new HashMap();
							   }
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");

							%>
								
								<li>订单号：<input type="text" class="i_text" name="out_trade_no1" value="<%=JsonUtil.obj2Str(searchBean.get("order_serial"))   %>"/></li>
								<li>手机号码：<input type="text" class="i_text" name="user1" value="<%=JsonUtil.obj2Str(searchBean.get("user"))   %>"/></li>
                                <li>付款日期 从：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/></li>
								<li>到：<input type="text" readonly class="i_text" style="width:180px" name="time_to1" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm()
							{  
								$("input[name='order_serial']").val($("input[name='out_trade_no1']").val());
								$("input[name='time_from']").val($("input[name='time_from1']").val());
								$("input[name='time_to']").val($("input[name='time_to1']").val());
								$("input[name='user']").val($("input[name='user1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/alipayLogCtrl.do?method=alipaylog_list"" method="post" id="searchForm">
					
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<input type="hidden" name="order_serial" value="<%= JsonUtil.obj2Str(searchBean.get("order_serial"))   %>"/>
					<input type="hidden" name="time_from" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/>
					<input type="hidden" name="time_to" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/>
						<input type="hidden" id="user" name="user" value="<%= JsonUtil.obj2Str(searchBean.get("user")) %>"/>
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
							    <th>手机号码</th>
								<th>订单号</th>
								<th>订单总额</th>
								<th>支付宝支付金额</th>
								<th>区享卡支付金额</th>						
								<th>支付日期</th>	
							</tr>
							<% List<AliPayLogBean> list = (List<AliPayLogBean>)request.getAttribute("list");
							   if(list!=null){
							   for(AliPayLogBean oplog: list) {
							%>
							<tr>
							    <td><%= JsonUtil.obj2Str(oplog.getPhone()) %></td>
								<td><%= JsonUtil.obj2Str(oplog.getOut_trade_no()) %></td>							
								<td><%= JsonUtil.obj2Str(oplog.getOrder_price()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getPay_price()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_pay_price()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getBind_time()) %></td>
							</tr>
							<% }} %>
						</table>
					</div>
					</form>
<jsp:include  page="../common/page.jsp"  flush="true">   
     <jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/>  
</jsp:include>	
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
        $(function(){
					
		});
												
</script>	

</div>
</body>
</html>
