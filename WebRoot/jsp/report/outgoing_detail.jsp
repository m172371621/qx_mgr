<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.Community" %>

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
	$("input[name='time_from']").datetimepicker({
	    timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd" 
    });
    $("input[name='time_to']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>
<!-- content -->
<div id="mright">
	<div id="mr_cnt">
		            <div class="mrw_title">商品分类销售日报表</div>
					<div class="sform_wrap c">
				
							<ul>
								<li>统计日期：<input readonly type="text" class="i_text" style="width:180px" name="time_from" id="time_from"  value="<%=request.getAttribute("time_from") %>"/></li>
								<li>到：<input readonly type="text" class="i_text" style="width:180px" name="time_to" id="time_to" value="<%=request.getAttribute("time_to") %>"/></li>
								<li>小区：<select name="cid"  id="cid" class="i_text">
									<option value="0"  >全部</option>
									<%
									   List<Community> community_list = (List<Community>)request.getSession().getAttribute("user_community");
									   for(Community c : community_list){
									%>
									<option value="<%=c.getCommunity_id()%>"><%= c.getCommunity_name() %></option>
									<% } %>
								</select>
								<li><a href="javascript:query()" class="btn btn_big btn_y">查询</a></li>
							</ul>
							
						
						</div>
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
	function query(){
	    var str_url="http://"+window.location.host+"/qxitreport/reportviewer?file=outgoing_detail.xml&community_id="+$("#cid").val()
	                 +"&time_from="+$("#time_from").val()
	                 +"&time_to="+$("#time_to").val();
	    window.open(str_url);
	}											
</script>	
   
</div>
</body>
</html>
