<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.brilliantreform.sc.task.po.PropertyInfo"%>
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
					<div class="mrw_title">物业公告</div> 
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
									List<PropertyInfo> list = (List<PropertyInfo>)request.getAttribute("propertyInfoList") == null? new ArrayList<PropertyInfo>():(List<PropertyInfo>)request.getAttribute("propertyInfoList");
								%>
								<li>开始时间：<input type="text" style="width:180px" readonly class="i_text" name="start_time1" value="<%=JsonUtil.obj2Str(request.getAttribute("start_time")==null?null:request.getAttribute("start_time"))%>" /></li>
								<li>结束时间：<input type="text" style="width:180px" readonly class="i_text" name="end_time1" value="<%=JsonUtil.obj2Str(request.getAttribute("end_time")==null?null:request.getAttribute("end_time"))%>" /></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
								<li><a href="javascript:jump()" class="btn btn_big btn_y">新增</a></li>
							</ul>
						</div>
						
					
							<form action="${pageContext.request.contextPath }/task.do?method=propertyInfoList" method="post" id="searchForm">
							<input type="hidden" name="start_time" value="<%= JsonUtil.obj2Str(request.getAttribute("start_time")==null?null:request.getAttribute("start_time"))%>"/>
							<input type="hidden" name="end_time" value="<%= JsonUtil.obj2Str(request.getAttribute("end_time")==null?null:request.getAttribute("end_time"))%>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
							</form>
							
							<%-- http://localhost:8080/qx_mgr/task.do?method=propertyInfo_Jump--%>
							<form action="${pageContext.request.contextPath }/task.do?method=propertyInfo_Jump" method="post" id="propertyInfo_Jump">
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
						function jump()
						{
							$('#propertyInfo_Jump').submit();
						}
						</script>
						
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="60px"/><col width="200px"/><col width="80px"/><col width="100px"/><col width="100px"/><col width="180px"/><col width="80px"/><col width="80px"/></colgroup>
							<tr>
								<th>标题</th>
								<th>内容</th>
								<th>服务站地址 </th>
								<th>手机 号码</th>
								<th>电话号码</th>
								<th>备注</th>
								<th>时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<c:forEach var="list" items="${requestScope.propertyInfoList}">
								<tr>
									<td name="title">${list. title}</td>
									<td name="content">${list. content}</td>
									<td name="address">${list. address}</td>
									<td name="phone">${list. phone}</td>
									<td name="landline">${list. landline}</td>
									<td name="remarks">${list. remarks}</td>
									<td name="createTime">${list. createTime}</td>
									<td>
										<a href="${pageContext.request.contextPath }/task.do?method=editPropertyInfo&property_information_id=${list.property_information_id}" class="btn btn_r">修改</a>
										<a href="javascript:if(confirm('确定删除吗？')){location.href='${pageContext.request.contextPath }/task.do?method=delPropertyInfo&property_information_id=${list.property_information_id}'}"class="btn btn_r">删除</a>
									</td>
								</tr>
							</c:forEach>
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
