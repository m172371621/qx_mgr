<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />
<!-- content -->
<input type="hidden" id="curr_li" value="product_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">预约管理编辑</div>
					<form action="${pageContext.request.contextPath }/task.do?method=updataService_order" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>用户编号:</th>
								<td><input type="text" width="180px"  class="i_text_1" name="user_id" disabled="true" value="${requestScope.service_order.username}"/></td>
								<input type="hidden" name="user_id_value" value="${requestScope.service_order.user_id}"/>
								<!-- order_id --> 
								<input type="hidden" name="order_id" value="${requestScope.service_order.order_id }"/>
							</tr>
							<tr>
								<th>手机:</th>
								<td><input type="text" width="180px"  class="i_text_1" name="user_phone" disabled="true" value="${requestScope.service_order.phone}"/></td>
							</tr>
							<tr>
								<th>服务名称:</th>
								<td><input type="text" width="180px"  class="i_text_1" name="service_name" disabled="true" value="${requestScope.service_order.service_name}"/>
									<input type="hidden" name="service_name_value" value="${requestScope.service_order.service_name}"/>
								</td>
							</tr>
							<tr>
								<th>配注 :</th>
								<td><input type="text" width="180px"  class="i_text_1" name="order_dec" value="${requestScope.service_order.order_dec}"/></td>
							</tr>
							<tr>
								<th>状态:</th>
								<td>
									<select name="order_status">
										<c:if test="${requestScope.service_order.order_status == 1}">
											<option value="1"  selected = "selected">预约</option>
											<option value="2" >接受</option>
											<option value="3" >完成</option>
										</c:if>
										<c:if test="${requestScope.service_order.order_status == 2}">
											<option value="1" >预约</option>
											<option value="2"  selected = "selected">接受</option>
											<option value="3" >完成</option>
										</c:if>
										<c:if test="${requestScope.service_order.order_status == 3}">
											<option value="1" >预约</option>
											<option value="2" >接受</option>
											<option value="3"  selected = "selected">完成</option>
										</c:if>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<div class="mrw_btn_wrap">
						<a href="javascript:sub();" class="btn btn_big btn_r">确定</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
</div>
<script type="text/javascript">
	function sub(){
		$("#form").submit();
	}
</script>
</body>
</html>
