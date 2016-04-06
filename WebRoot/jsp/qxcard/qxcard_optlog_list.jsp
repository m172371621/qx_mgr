<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.qxcard.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


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
					<div class="mrw_title">区享卡操作查询</div>
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
								<li>区享卡号：<input type="text" class="i_text" name="qxcard_no1" value="<%=JsonUtil.obj2Str(searchBean.get("qxcard_no"))   %>"/></li>
								<li>订单号：<input type="text" class="i_text" name="order_serial1" value="<%=JsonUtil.obj2Str(searchBean.get("order_serial"))   %>"/></li>
								<li>手机号：<input type="text" class="i_text" name="phone1" value="<%=JsonUtil.obj2Str(searchBean.get("phone"))   %>"/></li>
                                <li>查询日期 从：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/></li>
								<li>到：<input type="text" readonly class="i_text" style="width:180px" name="time_to1" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
					<script type="text/javascript">
							function submitForm()
							{  
								$("input[name='qxcard_no']").val($("input[name='qxcard_no1']").val());
								$("input[name='order_serial']").val($("input[name='order_serial1']").val());
								$("input[name='phone']").val($("input[name='phone1']").val());
								$("input[name='time_from']").val($("input[name='time_from1']").val());
								$("input[name='time_to']").val($("input[name='time_to1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/qxcard.do?method=qxcard_optlog_list" method="post" id="searchForm">
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<input type="hidden" name="qxcard_no" value="<%= JsonUtil.obj2Str(searchBean.get("qxcard_no"))   %>"/>
					<input type="hidden" name="order_serial" value="<%= JsonUtil.obj2Str(searchBean.get("order_serial"))   %>"/>
					<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(searchBean.get("phone"))   %>"/>
					<input type="hidden" name="time_from" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/>
					<input type="hidden" name="time_to" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/>
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="140px"/><col width="300px"/><col width="80px"/><col width="60px"/><col width="100px"/> <col width="60px"/><col width="80px"/><col width="140px"/></colgroup>
							<tr>
								<th>订单编号</th>
								<th>操作结果描述</th>
								<th>区享卡号</th>
								<th>余额</th>		
								<th>手机号码</th>				
								<th>订单金额</th>	
								<th>操作类型</th>
								<th>操作描述</th>	
								<th>创建日期</th>
							</tr>
							<% List<QxCardLog> list = (List<QxCardLog>)request.getAttribute("list");
							   if(list!=null){
							   for(QxCardLog oplog: list) {
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(oplog.getOrder_serial()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getOp_result_dec()) %></td>
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_no()) %></td>							
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_balance()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getPhone()) %></td>
								<td><%= JsonUtil.obj2Str(oplog.getOp_price()) %></td>	
								<td><%= oplog.getOp_type()==1001?"开卡":oplog.getOp_type()==1002?"激活":oplog.getOp_type()==1003?"冻结":
								        oplog.getOp_type()==1004?"解冻":oplog.getOp_type()==2001?"消费":oplog.getOp_type()==2002?"退款":
								        oplog.getOp_type()==3001?"过期":oplog.getOp_type()==3002?"删除":oplog.getOp_type()==3003?"作废":""%></td>
								<td><%= JsonUtil.obj2Str(oplog.getOp_dec()) %></td>
								<td><%= CommonUtil.formatTime(oplog.getCreatetime().getTime(),1) %></td>
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
		function view(id)
		{  
			location.href="${pageContext.request.contextPath }/qxcard.do?method=edit_reglog&op_id="+id;
		}	
												
</script>	

</div>
</body>
</html>
