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
<body <% if( request.getAttribute("SUCCESS")!=null )
		{
			if( (Boolean)request.getAttribute("SUCCESS") ) 
			{out.print("onload=\"alert('操作成功')\"");}
			else
			{out.print("onload=\"alert('操作失败，卡号不存在或已开卡！')\"");}
		 }%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%

QxcardReglog bean = (QxcardReglog)request.getAttribute("obj");
int edit_type = 1;
if(bean == null)
{
	 bean = new QxcardReglog();
}else
{
	edit_type = 2;
}
int op_type = bean.getOp_type() == null ? 1 : bean.getOp_type() ;
long op_time = bean.getCreatetime() == null ? 0l : bean.getCreatetime().getTime() ;
%>

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">区享卡激活/冻结</div>
					<form action="${pageContext.request.contextPath }/qxcard.do?method=edit_reglog" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>区享卡号</th>
								<td>		
									<input type="hidden" width="180px"  class="i_text_1" name="op_id" value="<%= JsonUtil.obj2Str(bean.getId()) %>"/>
									<input type="hidden" width="180px"  class="i_text_1" name="type" value="update"/>
									<input type="text" width="180px"  <% if(edit_type == 2) out.print("disabled"); %> class="i_text_1" name="qxcard_no" value="<%= JsonUtil.obj2Str(bean.getQxcard_no()) %>"/>							
								</td>
							</tr>
							<tr>
								<th>客户名</th>
								<td><input type="text" width="180px"  class="i_text_1" name="custm_name" value="<%= JsonUtil.obj2Str(bean.getCustm_name()) %>"/></td>
							</tr>
							<tr>
								<th>客户手机号</th>
								<td><input type="text" width="180px"  class="i_text_1" name="custm_phone" value="<%= JsonUtil.obj2Str(bean.getCustm_phone()) %>"/></td>
							</tr>
							<tr>
								<th>客户备注</th>
								<td><input type="text" width="180px"  class="i_text_1" name="custm_dec" value="<%= JsonUtil.obj2Str(bean.getCustm_dec()) %>"/></td>
							</tr>
							
							<tr>
								<th>操作备注</th>
								<td><input type="text" width="180px"  class="i_text_1" name="seller_dec" value="<%= JsonUtil.obj2Str(bean.getSeller_dec()) %>"/></td>
							</tr>
							<tr>
								<th>操作类型</th>
								<td><select name="op_type" <% if(edit_type == 2) out.print("disabled"); %> class="i_text_1" >
									<option value="1" <% if(op_type==1) out.print("selected");%> >开卡</option>
									<option value="2" <% if(op_type==2) out.print("selected");%> >冻结</option>
									<option value="3" <% if(op_type==3) out.print("selected");%> >解冻</option>
									</select>
								</td>
							</tr>
							
							<% if(edit_type == 2){ %>	
							<tr>
								<th>操作员</th>
								<td><input type="text" width="180px" disabled class="i_text_1" name="express_info" value="<%= JsonUtil.obj2Str(bean.getSeller_name()) %>"/></td>
							</tr>
							<tr>
								<th>操作IP</th>
								<td><input type="text" width="180px"  disabled class="i_text_1" name="express_info" value="<%= JsonUtil.obj2Str(bean.getSeller_ip()) %>"/></td>
							</tr>
							<tr>
								<th>操作时间</th>
								<td><input type="text" width="180px"  disabled class="i_text_1" name="express_info" value="<%=  CommonUtil.formatTime(op_time,1) %>"/></td>
							</tr>
							<% } %>
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					
						<a href="javascript:submit();" class="btn btn_big btn_r">确定</a>
						
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
<script type="text/javascript">

function submit()
{

	if(!/\d{10}/.test($('input[name=qxcard_no]').val()))
	{
		alert("区享卡号不正确");
		return;
	}

	
	$('#form').submit();
}

</script>
</div>
</body>
</html>
