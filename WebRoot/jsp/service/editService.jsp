<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/resource.jsp" />
	<script type="text/javascript">
		function saveService() {
			var community_id = $('#community_id').val();
			var service_name = $('#service_name').val();
			var service_type = $('#service_type').val();
			var service_order = $('#service_order').val();
			var status = $('#status').val();
			if(community_id != null && $.trim(community_id) != '' && service_name != null && $.trim(service_name) != ''
				&& service_type != null && $.trim(service_type) != '' && service_order != null && $.trim(service_order) != ''
				&& status != null && $.trim(status) != '') {
				$('#serviceForm').submit();
			} else {
				alert("表单输入不完整！");
			}

		}
	</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
	<jsp:include page="../common/menu.jsp" />
	<input type="hidden" id="curr_li" value="service_0"/>
	<div id="mright">
		<div id="mr_cnt">
			<div class="mrw_title">编辑服务信息</div>
			<form action="${pageContext.request.contextPath }/service.do?method=saveService" method="post" id="serviceForm">
				<input type="hidden" value="${service.service_id}" name="service_id"/>
				<c:if test="${!empty service.service_id}">
					<input type="hidden" name="service_type" value="${service.service_type }" id="service_type"/>
				</c:if>
				<c:if test="${empty service.service_id}">
					<input type="hidden" name="service_type" value="3" id="service_type"/>
				</c:if>
				<div class="p20 m20">
					<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
						<tr>
							<th>小区</th>
							<td>
								<input type="hidden" name="community_id" id="community_id" value="${sessionScope.selectCommunity.community_id}"/>
								${sessionScope.selectCommunity.community_name}
							</td>
						</tr>
						<tr>
							<th>名称</th>
							<td><input type="text" class="i_text_1" name="service_name" id="service_name" value="${service.service_name}"/></td>
						</tr>
						<tr>
							<th>描述</th>
							<td><textarea class="i_text_1" name="service_dec" id="service_dec" style="height: 100px;">${service.service_dec}</textarea></td>
						</tr>
						<tr>
							<th>图片链接</th>
							<td><input type="text" class="i_text_1" name="service_img" id="service_img" value="${service.service_img}"/></td>
						</tr>
						<tr>
							<th>我的预约图片链接</th>
							<td><input type="text" class="i_text_1" name="service_title_img" id="service_title_img" value="${service.service_title_img}"/></td>
						</tr>
						<tr>
							<th>服务链接</th>
							<td><input type="text" class="i_text_1" name="service_url" id="service_url" value="${service.service_url}"/></td>
						</tr>
						<tr>
							<th>顺序</th>
							<td><input type="text" class="i_text_1" name="service_order" id="service_order" value="${service.service_order}"/></td>
						</tr>
						<tr>
							<th>可见性</th>
							<td>
								<select id="status" name="status" class="i_text_1">
									<option value="1" <c:if test="${service.status == 1}">selected</c:if> >可见</option>
									<option value="0" <c:if test="${service.status == 0}">selected</c:if> >隐藏</option>
								</select>
							</td>
						</tr>
					</table>

				</div>

				<div class="mrw_btn_wrap">
					<a href="javascript:saveService();" class="btn btn_big btn_r">保存</a>
					<a href="${pageContext.request.contextPath }/jsp/order/order_search.jsp" class="btn btn_big btn_r">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
