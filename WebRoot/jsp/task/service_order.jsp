<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
<script type="text/javascript">
$(function() {
	$(function() {
        $("input[name='start_create_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
        $("input[name='end_create_time']").datepicker({
            dateFormat: "yy-mm-dd"
        });
    });
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
					<div class="mrw_title">预约管理</div> 
					<form action="${pageContext.request.contextPath }/task.do?method=getService_order" method="post" id="searchForm">
					<div class="sform_wrap c">
					<%
					   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
					   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
					%>
							<ul>
								<li>用户名：<input type="text" style="width:180px"  class="i_text" name="user_name" value="<%=request.getAttribute("user_name")==null?"":request.getAttribute("user_name")%>" /></li>
								<li>手机：<input type="text" style="width:180px"  class="i_text" name="user_phone" value="<%=request.getAttribute("user_phone")==null?"":request.getAttribute("user_phone")%>" /></li>
								<li>服务名称：<input type="text" style="width:180px"  class="i_text" name="service_order_name" value="<%=request.getAttribute("service_order_name")==null?"":request.getAttribute("service_order_name")%>" /></li>
								<li>时间：<input type="text" style="width:180px" readonly class="i_text" name="start_create_time" value="<%=request.getAttribute("beginTime")==null?"":request.getAttribute("beginTime")%>" /></li>
								
								<li>结束时间：<input type="text" style="width:180px" readonly class="i_text" name="end_create_time" value="<%=request.getAttribute("endTime")==null?"":request.getAttribute("endTime")%>" /></li>
								<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
						
						
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="60px"/><col width="200px"/><col width="80px"/><col width="100px"/><col width="100px"/><col width="100px"/><col width="180px"/></colgroup>
							<tr>
								<th>用戶姓名</th>
								<th>手机</th>
								<th>服务名称</th>
								<th>配注 </th>
								<th>状态</th>
								<th>时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<c:forEach var="list" items="${requestScope.service_order_Map}">
								<tr>
									<td id="user_id">${list.username}</td>
									<td id="user_phone">${list.phone}</td>
									<td>${list.service_name}</td>
									<td>${list.order_dec}</td>
									<td>
										<c:if test="${list.order_status==1}">
											预约
										</c:if>
										<c:if test="${list.order_status==2}">
											接受
										</c:if>
										<c:if test="${list.order_status==3}">
											完成
										</c:if>
									</td>
									<td>${list.order_time}</td>
									<td>
										<a class="btn btn_r" href="${pageContext.request.contextPath}/task.do?method=service_order_edit&order_id=${list.order_id}">编辑</a>
										<a class="btn btn_r" href="javascript:;"  onclick='del(${list.order_id},${list.user_id})'>删除</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</form>
<jsp:include  page="../common/page.jsp"  flush="true">   
     <jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/>  
</jsp:include>	
					 
	</div>
</div>
<script type="text/javascript">
		function del(oid,uid){
			if(confirm("确定删除！")){
				$.ajax({
					type: "post",
					url: "${pageContext.request.contextPath}/task.do?method=delService_order",
					data: {oid:oid,uid:uid},
					dataType: "json",
					success: function(data) {
						if(data != 0){
							alert("删除成功！");
							window.location.reload();
						}
					},
					error: function(erro) {
						alert("error");
					}
				});
			}
		}
		function submitForm(){
			$("#searchForm").submit();
		}
</script>
</div>
</body>
</html>
