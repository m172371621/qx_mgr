<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='rule_begin_time']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='rule_end_time']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>
</head>
<body <% if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%ProductRule rule =  (ProductRule)request.getAttribute("rule");

%>

<!-- content -->
<input type="hidden" id="curr_li" value="product_1"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">编辑规则</div>
					<form action="${pageContext.request.contextPath }/product.do?method=editRule" method="post" id="form">
					<input type="hidden" width="180px"  class="i_text_1" name="rule_id" value="<%= rule.getRule_id() %>"/>
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>名称</th>
								<td><input type="text" width="180px"  class="i_text_1" name="rule_name" value="<%= JsonUtil.obj2Str(rule.getRule_name()) %>"/></td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td><input type="text" width="180px"  class="i_text_1" name="cid" disabled value="<%= JsonUtil.obj2Str(rule.getCommunityName()) %>"/></td>
							</tr>
							<tr>
								<th>类型</th>
								<td><input type="text" width="180px"  class="i_text_1" name="rtype" disabled value="限量商品"/></td>
							</tr>
							<tr>
								<th>用户限量</th>
								<td><input type="text" width="180px"  class="i_text_1" name="user_limit" value="<%= JsonUtil.obj2Str(rule.getUser_limit()) %>"/></td>
							</tr>
							<tr>
								<th>总量</th>
								<td><input type="text" width="180px"  class="i_text_1" name="amount_limit" value="<%= JsonUtil.obj2Str(rule.getAmount_limit()) %>"/></td>
							</tr>
							<tr>
								<th>描述</th>
								<td><input type="text" width="180px"  class="i_text_1" name="rule_dec" value="<%= JsonUtil.obj2Str(rule.getRule_dec()) %>"/></td>
							</tr>
							<tr>
								<th>开始时间</th>
								<td><input type="text" width="180px"  readonly class="i_text_1" name="rule_begin_time" value="<%= JsonUtil.obj2Str(rule.getRule_begin_time()) %>"/></td>
							</tr>
							<tr>
								<th>结束时间</th>
								<td><input type="text" width="180px"  readonly class="i_text_1" name="rule_end_time" value="<%= JsonUtil.obj2Str(rule.getRule_end_time()) %>"/></td>
							</tr>							
							<tr>
								<th>规则创建时间</th>
								<td><input type="text" width="180px"  class="i_text_1" name="cdate" disabled value="<%= CommonUtil.formatTimestamp(rule.getCreatetime(),1) %>"/></td>
							</tr>
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					
						<a href="javascript:$('#form').submit();" class="btn btn_big btn_r">确定</a>
						
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
<script type="text/javascript">

</script>
</div>
</body>
</html>
