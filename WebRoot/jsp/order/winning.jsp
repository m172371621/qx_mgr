<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.brilliantreform.sc.user.mgrpo.MgrUser"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
<script type="text/javascript">
$(function() {
	$("input[name='start_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='end_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='pickup_start_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='pickup_end_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    })
});
</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">中奖记录查询</div> 
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
							   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
							%>	
								<% 
									List<WinningInfo> winningInfo = (List<WinningInfo>)request.getAttribute("winningInfo") == null ? new ArrayList<WinningInfo>():(List<WinningInfo>)request.getAttribute("winningInfo");
								%>
								<li>手机号：<input type="text" class="i_text" name="phone1" value="<%= JsonUtil.obj2Str(request.getAttribute("phone")==null?null:request.getAttribute("phone"))%>"/></li>
								<li>中奖名称：<input type="text" class="i_text" name=card_name1 value="<%=JsonUtil.obj2Str(request.getAttribute("card_name")==null?null:request.getAttribute("card_name"))%>"/></li>
								<li>开始时间：<input type="text" style="width:180px" readonly class="i_text" name="start_time1"  value="<%=JsonUtil.obj2Str(request.getAttribute("start_time")==null?null:request.getAttribute("start_time"))%>"/></li>
								<li>结束时间：<input type="text" style="width:180px" readonly class="i_text" name="end_time1" value="<%=JsonUtil.obj2Str(request.getAttribute("end_time")==null?null:request.getAttribute("end_time"))%>" /></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
						
					
							<form action="${pageContext.request.contextPath }/order.do?method=winning" method="post" id="searchForm">
							<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(request.getAttribute("phone")==null?null:request.getAttribute("phone"))%>"/>
							<input type="hidden" name="card_name" value="<%= JsonUtil.obj2Str(request.getAttribute("card_name")==null?null:request.getAttribute("card_name"))%>"/>
							<input type="hidden" name="start_time" value="<%= JsonUtil.obj2Str(request.getAttribute("start_time")==null?null:request.getAttribute("start_time"))%>"/>
							<input type="hidden" name="end_time" value="<%= JsonUtil.obj2Str(request.getAttribute("end_time")==null?null:request.getAttribute("end_time"))%>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
							</form> 
							
						<script type="text/javascript">
						function submitForm()
						{
							$("input[name='phone']").val($("input[name='phone1']").val());
							$("input[name='card_name']").val($("input[name='card_name1']").val());
							$("input[name='start_time']").val($("input[name='start_time1']").val());
							$("input[name='end_time']").val($("input[name='end_time1']").val());
							$('#pageIndex').val(1);
							$('#searchForm').submit();
						}	

						function award(orderID,userid){
							if(confirm('确实领取?')){			
							var loginName = "<%= ((MgrUser)session.getAttribute("user_info")).getLoginname() %>";
							$.ajax({
								type:"POST",
								url: "${pageContext.request.contextPath }/order.do?method=awardUpdate",
								data:{loginName:loginName,orderID:orderID,userid:userid},
								success: function(data){
									var dataObj = eval("(" + data + ")");
									alert("领取成功！");
									submitForm();
									}
								});
							}
						}
						</script>
						
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="100px"/><col width="100px"/><col width="100px"/><col width="80px"/><col width="100px"/><col width="50px"/><col width="50px"/></colgroup>
							<tr>
								<th>手机号码</th>
								<th>中奖类型</th>
								<th>中奖时间 </th>
								<th>是/否领取</th>
								<th>领取时间</th>
								<th>操作人员</th>
								<th>操作</th>
							</tr>
							<%for(WinningInfo w : winningInfo) {%>
								<tr>
									<td><%=w.getPhone()%></td>
									<td><%=w.getCard_name()%></td>
									<td><%=format.format(w.getCreateTime())%></td>
									<td><%=w.getAwardStatus()==0?"未知":w.getAwardStatus()==1?"<strong style='color:red'>未领取</strong>":"已领取"%></td>
									<td><%=w.getAwardTime()==null?"":w.getAwardTime()%></td>
									<td><%=w.getOperatorName()==null?"":w.getOperatorName() %></td>
									<td>
									<%if(w.getAwardStatus()==2){%>
										<a  href="javascript: void()" class="btn btn_r">已领取</a>
									<%}else if (w.getAwardStatus()==1){%>
										<a  href="javascript: award(<%=w.getOrder_id()%>,<%=w.getUserid()%>)" class="btn btn_r"  style="background:green;">领取</a>
									<%} else if(w.getAwardStatus()==0){%>
										
									<%} %>	
									
									</td>
								</tr>
								<%} %>
						</table>
					</div>
					
<jsp:include  page="../common/page.jsp"  flush="true">   
     <jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/>  
</jsp:include>	
					 
	</div>
</div>
<!-- content end -->

</div>
</body>
</html>
