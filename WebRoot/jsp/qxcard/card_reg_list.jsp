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
	$("input[name='time_from1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='time_to1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">区享卡操作列表</div>
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
							   
							   String qxcard_no = JsonUtil.obj2Str(searchBean.get("qxcard_no"));  
							   String seller_name = JsonUtil.obj2Str(searchBean.get("seller_name")); 
							   String op_type  = JsonUtil.obj2Str(searchBean.get("op_type")); 
							   String time_from = JsonUtil.obj2Str(searchBean.get("time_from"));
							   String time_to = JsonUtil.obj2Str(searchBean.get("time_to"));

							%>
								
								<li>区享卡号：<input type="text" class="i_text" name="qxcard_no1" value="<%= qxcard_no %>"/></li>
								<li>销售员：<input type="text" class="i_text" name="seller_name1" value="<%= seller_name %>"/></li>
								<li>类型：<select name="op_type1"  id="" class="i_text">
									<option value="0"  >全部</option>				
									<option value="1" <% if("1".equals(op_type) ) {out.print("selected");  }%> >开卡</option>
									<option value="2" <% if("2".equals(op_type) ) {out.print("selected");  }%> >冻结</option>
									<option value="3" <% if("3".equals(op_type) ) {out.print("selected");  }%> >解冻</option>
								</select>
								</li>			
								<li>到货日期 从：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" value="<%= time_from %>"/></li>
								<li>到：<input type="text" readonly class="i_text" style="width:180px" name="time_to1" value="<%= time_to  %>"/></li>
								
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm()
							{  
								$("input[name='qxcard_no']").val($("input[name='qxcard_no1']").val());
								$("input[name='seller_name']").val($("input[name='seller_name1']").val());
								$("input[name='op_type']").val($("select[name='op_type1']").val());
								$("input[name='time_from']").val($("input[name='time_from1']").val());
								$("input[name='time_to']").val($("input[name='time_to1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/qxcard.do?method=list_reglog" method="post" id="searchForm">
					
					<input type="hidden" name="qxcard_no" value="<%= qxcard_no %>"/>
					<input type="hidden" name="seller_name" value="<%= seller_name %>"/>
					<input type="hidden" name="op_type" value="<%= op_type %>"/>
					<input type="hidden" name="time_from" value="<%= time_from %>"/>
					<input type="hidden" name="time_to" value="<%= time_to %>"/>
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>区享卡号</th>
								<th>面值</th>
								<th>操作类型</th>
								<th>客户名</th>						
								<th>客户号码</th>	
								<th>销售员</th>				
								<th>备注</th>
								<th>操作时间</th>
							</tr>
							<% List<QxcardReglog> list = (List<QxcardReglog>)request.getAttribute("list");
							   if(list!=null){
							   for(QxcardReglog oplog: list) {
								   String str_op_type = "开卡";
								   if(oplog.getOp_type() == 2)
								   {
									   str_op_type = "冻结";
								   }else if(oplog.getOp_type() == 3)
								   {
									   str_op_type = "解冻";
								   }
								   
								String value = "";
								String card_no = oplog.getQxcard_no();
								if(card_no != null && card_no.length() == 10 )
								{
									value =  card_no.substring(4,6);
								}
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_no()) %></td>		
								<td><%= Integer.parseInt(value) * 100 %></td>							
								<td><%= str_op_type %> </td>
								<td><%= JsonUtil.obj2Str(oplog.getCustm_name()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getCustm_phone()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getSeller_name()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getSeller_dec()) %></td>	
								<td><%= CommonUtil.formatTime(oplog.getCreatetime().getTime(),1) %></td>
								<td>									
									<a target="_blank" href="javascript:view('<%= oplog.getId() %>')" class="btn btn_r">详情</a>									
								</td>
							</tr>
							<% }} %>
							<tr>
								<td colspan="8">&nbsp;</td>
								<td><a href="javascript:view('');" class="btn btn_r">开卡/冻结卡</a></td>
							</tr>
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
		function view(id)
		{  
			
			location.href="${pageContext.request.contextPath }/qxcard.do?method=edit_reglog&op_id="+id;
			
		}	
												
</script>	

</div>
</body>
</html>
