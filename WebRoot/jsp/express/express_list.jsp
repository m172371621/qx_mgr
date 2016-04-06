<%@ page language="java" import="com.brilliantreform.sc.express.po.Express" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.Dicti" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.JsonUtil" %>
<%@ page language="java" import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>


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
<input type="hidden" id="curr_li" value="express_1"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">快递列表</div>
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
							   
							   String express_no = JsonUtil.obj2Str(searchBean.get("express_no")); 
							   String express_com = JsonUtil.obj2Str(searchBean.get("express_com"));
							   String user_phone = JsonUtil.obj2Str(searchBean.get("user_phone")); 
							   String status = JsonUtil.obj2Str(searchBean.get("status"));
							   String user_type = JsonUtil.obj2Str(searchBean.get("user_type")); 
							   String express_info = JsonUtil.obj2Str(searchBean.get("express_info")); 
							   String time_from = JsonUtil.obj2Str(searchBean.get("time_from")); 
							   String time_to = JsonUtil.obj2Str(searchBean.get("time_to")); 

							%>
								
								<li>手机号：<input type="text" class="i_text" name="user_phone1" value="<%= user_phone %>"/></li>
								<li>单号：<input type="text" class="i_text" name="express_no1" value="<%= express_no %>"/></li>
								<li>APP用户：<select name="user_type1"  id="" class="i_text">
									<option value="0"  >全部</option>				
									<option value="1" <% if("1".equals(user_type) ) {out.print("selected");  }%> >否</option>
									<option value="2" <% if("2".equals(user_type) ) {out.print("selected");  }%> >是</option>
									
								</select>
								</li>
								<li>快递公司：<select name="express_com1"  id="" class="i_text">
									<option value="0"  >全部</option>	
									<% Map commap =  Dicti.get(Dicti.get("快递公司")); 
									for (Object key : commap.keySet()) {
									%>	
											
									<option value="<%= key %>" <% if(express_com.equals(key+"") ) {out.print("selected");  }%> ><%= commap.get(key) %></option>
									
									<% } %>
								</select>
								</li>
								<li>状态：<select name="status1"  id="" class="i_text">
									<option value="0"  >全部</option>				
									<option value="1" <% if("1".equals(status) ) {out.print("selected");  }%> >未签收</option>
									<option value="2" <% if("2".equals(status) ) {out.print("selected");  }%> >已签收</option>
									
								</select>
								</li>
								<li>地址 ：<input  type="text" class="i_text" name="express_info1" value="<%=  express_info%>"/></li>
								<li>到货日期 从：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" value="<%= time_from %>"/></li>
								<li>到：<input type="text" readonly class="i_text" style="width:180px" name="time_to1" value="<%= time_to  %>"/></li>
								
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm()
							{  
								$("input[name='express_no']").val($("input[name='express_no1']").val());
								$("input[name='user_phone']").val($("input[name='user_phone1']").val());
								$("input[name='express_com']").val($("select[name='express_com1']").val());
								$("input[name='user_type']").val($("select[name='user_type1']").val());
								$("input[name='status']").val($("select[name='status1']").val());

								$("input[name='express_info']").val($("input[name='express_info1']").val());
								$("input[name='time_from']").val($("input[name='time_from1']").val());
								$("input[name='time_to']").val($("input[name='time_to1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/express.do?method=list" method="post" id="searchForm">
					
					<input type="hidden" name="express_no" value="<%= express_no %>"/>
					<input type="hidden" name="user_phone" value="<%= user_phone %>"/>
					<input type="hidden" name="user_type" value="<%= user_type %>"/>
					<input type="hidden" name="express_com" value="<%= express_com %>"/>
					<input type="hidden" name="status" value="<%= status %>"/>
					<input type="hidden" id="isSet" name="isSet" value="0"/>
					
					<input type="hidden" name="express_info" value="<%= express_info %>"/>
					<input type="hidden" name="time_from" value="<%= time_from %>"/>
					<input type="hidden" name="time_to" value="<%= time_to %>"/>
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
							<tr>
								<th width="35px"></th>
								<th width="100px">手机号</th>
								<th width="90px">快递公司</th>
								<th width="120px">快递单号</th>
								<th width="150px">到货日期</th>
								<th width="150px">签收日期</th>
								<th width="70px">状态</th>
								<th >地址</th>
								<th width="180px">操作</th>
							</tr>
							<% List<Express> list = (List<Express>)request.getAttribute("list");
							   if(list!=null){
							   for(Express express: list) {
								   
							%>
							<tr>
								<td><input type="checkbox" name="ids" id="cb<%=  express.getExpress_id() %>" value="<%=  express.getExpress_id() %>" <% if(express.getStatus()==2) out.print("disabled"); %>/></td>
								<td><%= JsonUtil.obj2Str(express.getUser_phone()) %></td>							
								<td><%= JsonUtil.obj2Str(commap.get(Integer.parseInt(express.getExpress_com()))) %> </td>
								<td><a href="javascript:showPrint('<%=  express.getExpress_id() %>')" style="text-decoration: underline"><%= JsonUtil.obj2Str(express.getExpress_no()) %></a></td>
								<td><%= CommonUtil.formatTime(express.getArrival_time().getTime(),1) %></td>
								<td><%= express.getStatus()==2 ? (express.getSign_time() != null ? CommonUtil.formatDate(express.getSign_time(), "yyyy-MM-dd HH:mm") : "") : "" %></td>
								<td><%= express.getStatus()==2?"已签收":"<font color='red'>未签收</font>" %></td>
								<td><%= JsonUtil.obj2Str(express.getExpress_info()) %></td>
								<td>
									<% if(express.getStatus()!=2){ %>
									<a href="javascript:toSign('cb<%=  express.getExpress_id() %>')" class="btn btn_r">签收</a>
									<% }else{ %>
									&nbsp;
									<% } %>
									<a href="javascript:edit('<%=  express.getExpress_id() %>')" class="btn btn_r">编辑</a>
									<a href="javascript:del('<%=  express.getExpress_id() %>')" class="btn btn_r">删除</a>
								</td>
							</tr>
							<% }} %>
							<tr>
								<td colspan="8">&nbsp;</td>
								<td><a href="javascript:toSign();" class="btn btn_r">批量签收</a></td>
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
							function toSign(cb_id)
							{  
								$('#isSet').val('1');
								if(cb_id)
									$('#'+cb_id).attr('checked',true);
								$('#searchForm').submit();
							}	
							function del(cb_id)
							{  
								if(confirm('确定删除吗？'))
								{
									 location.href="${pageContext.request.contextPath }/express.do?method=del&id="+cb_id;
								}
							}
							function edit(cb_id)
							{  
								
									 location.href="${pageContext.request.contextPath }/express.do?method=view&type=edit&eid="+cb_id;
								
							}
							function showPrint(id)
							{  
								var newWin = window.open ('${pageContext.request.contextPath }/express.do?method=view&eid='+id,
										 'newwindow', 
										 'height=350, width=350, top=300,left=500,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no'); 
								//newWin.print();
							}					
</script>	

</div>
</body>
</html>
