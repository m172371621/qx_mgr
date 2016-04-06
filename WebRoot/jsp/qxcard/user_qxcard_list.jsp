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
		
					<div class="mrw_title">用户区享卡查询</div>
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
								<li>手机号：<input type="text" class="i_text" name="phone1" value="<%=JsonUtil.obj2Str(searchBean.get("phone"))   %>"/></li>
								<li>区享卡号：<input type="text" class="i_text" name="qxcard_no1" value="<%=JsonUtil.obj2Str(searchBean.get("qxcard_no"))   %>"/></li>
								<li><select name="status" size="1" id="status">
								                    <option value="0">所有</option>
                                                    <option value="3">正常</option>
								                    <option value="1">未制卡</option>
                                                    <option value="2">未激活</option>
                                                    <option value="4">已使用</option>
                                                    <option value="5">已过期</option>
                                                    <option value="6">已冻结</option>
                                                    <option value="9">不可用</option>
                                </select></li>
                                <li>激活日期 从：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/></li>
								<li>到：<input type="text" readonly class="i_text" style="width:180px" name="time_to1" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm()
							{  
								$("input[name='qxcard_no']").val($("input[name='qxcard_no1']").val());
								$("input[name='phone']").val($("input[name='phone1']").val());
								$("input[name='time_from']").val($("input[name='time_from1']").val());
								$("input[name='time_to']").val($("input[name='time_to1']").val());
								$("#status1").val($("#status").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/qxcard.do?method=userQxCardlist" method="post" id="searchForm">
					
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<input type="hidden" name="qxcard_no" value="<%= JsonUtil.obj2Str(searchBean.get("qxcard_no"))   %>"/>
					<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(searchBean.get("phone"))   %>"/>
					<input type="hidden" name="time_from" value="<%= JsonUtil.obj2Str(searchBean.get("time_from")) %>"/>
					<input type="hidden" name="time_to" value="<%= JsonUtil.obj2Str(searchBean.get("time_to"))  %>"/>
					<div id="order_wrap" class="p20">
						<input type="hidden" id="status1" name="status" value="<%= searchBean.get("status").toString() %>"/>
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>区享卡号</th>
								<th>面值</th>
								<th>余额</th>						
								<th>状态</th>	
								<th>失效日期</th>				
								<th>激活日期</th>
								<th>更改日期</th>
							</tr>
							<% List<UserQxCard> list = (List<UserQxCard>)request.getAttribute("list");
							   if(list!=null){
							   for(UserQxCard oplog: list) {
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_no()) %></td>							
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_balance()) %></td>	
								<td><%= JsonUtil.obj2Str(oplog.getQxcard_value()) %></td>	
								<td><%= 
								      JsonUtil.obj2Str(oplog.getQxcard_status()).equals("3")?"正常"
								      :JsonUtil.obj2Str(oplog.getQxcard_status()).equals("4")?"已使用"
								      :JsonUtil.obj2Str(oplog.getQxcard_status()).equals("5")?"已过期"
								      :JsonUtil.obj2Str(oplog.getQxcard_status()).equals("6")?"已冻结"
								      :JsonUtil.obj2Str(oplog.getQxcard_status()).equals("9")?"不可用":""
								      %></td>	
								<td><%= CommonUtil.formatTime(oplog.getExpire_time().getTime(),1) %></td>	
								<td><%= CommonUtil.formatTime(oplog.getCreatetime().getTime(),1) %></td>
								<td>									
									<%= CommonUtil.formatTime(oplog.getUpdatetime().getTime(),1) %>							
								</td>
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
					
					$("#status").val($("#status1").val());
		});
		function view(id)
		{  
			
			location.href="${pageContext.request.contextPath }/qxcard.do?method=edit_reglog&op_id="+id;
			
		}	
												
</script>	

</div>
</body>
</html>
