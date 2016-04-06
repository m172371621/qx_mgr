<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="other_0"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">签到活动领奖</div>
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
							   
							   String name = searchBean.get("name") == null ? "" : (String)searchBean.get("name");
							   String phone = searchBean.get("phone") == null ? "" : (String)searchBean.get("phone");
							   int stat = searchBean.get("stat") == null ? 0:(Integer)searchBean.get("stat");
							   
							%>
								<li>状态：<select name="stat1"  id="" class="i_text">
									<option value="0"  >全部</option>				
									<option value="1" <% if(stat == 1 ) {out.print("selected");  }%> >未领奖</option>
									<option value="2" <% if(stat == 2 ) {out.print("selected");  }%> >已领奖</option>
									
								</select>
								</li>
								<li>用户名：<input type="text" class="i_text" name="name1" value="<%= JsonUtil.obj2Str(name) %>"/></li>
								<li>手机号：<input type="text" class="i_text" name="phone1" value="<%= JsonUtil.obj2Str(phone) %>"/>
								</li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm()
							{
								$("input[name='name']").val($("input[name='name1']").val());
								$("input[name='phone']").val($("input[name='phone1']").val());
								$("input[name='stat']").val($("select[name='stat1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/active.do?method=list" method="post" id="searchForm">
					
					<input type="hidden" name="name" value="<%= JsonUtil.obj2Str(name) %>"/>
					<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(phone) %>"/>
					<input type="hidden" name="stat" value="<%= stat %>"/>
					<input type="hidden" id="isSet" name="isSet" value="0"/>
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>用户名 </th>
								<th>手机号</th>						
								<th>奖品</th>					
								<th>状态</th>
								<th>领奖日期</th>
								<th>选择</th>
							</tr>
							<% List<Map> list = (List<Map>)request.getAttribute("list");
							   if(list!=null){
							   for(Map map: list) {
								   Integer prize = (Integer)map.get("prize_name");
								   String prize_name = "";
								   if(1 == prize)
								   {
									   prize_name = "一等奖";
								   }else if(2 == prize)
								   {
									   prize_name = "二等奖";
								   }else if(3 == prize)
								   {
									   prize_name = "三等奖";
								   }
								   
								   Integer status = (Integer)map.get("prize_status");
								   String prize_status = "";
								   if(1 == status)
								   {
									   prize_status = "未领奖";
								   }else if(2 == status)
								   {
									   prize_status = "已领奖";
								   }
								   
								   String prize_date =  "";
								   Timestamp ts = (Timestamp)map.get("prize_date");
								   if(ts != null)
								   {
									   prize_date = CommonUtil.formatTime(ts.getTime(),0);
								   }
							%>
							<tr>
								<td><%= map.get("user_name") %></td>							
								<td><%=  map.get("user_phone") %> </td>
								<td><%=  prize_name %></td>
								<td><%=  prize_status %></td>
								<td><%=  prize_date %></td>
								<td><input type="checkbox" name="ids" value="<%=  map.get("sign_id") %>" <%if(status == 2){out.print("disabled");} %>/></td>
							</tr>
							<% }} %>
							<tr>
								<td colspan="5">&nbsp;</td>
								<td><a href="javascript:$('#isSet').val('1');$('#searchForm').submit();" class="btn btn_r">领奖</a></td>
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

</div>
</body>
</html>
