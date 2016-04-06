<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/resource.jsp" />
	<script type="text/javascript">
		function saveAd() {
			var community_id = $('#community_id').val();
			var ad_name = $('#ad_name').val();
			var service_type = $('#service_type').val();
			var ad_order = $('#ad_order').val();
			var status = $('#status').val();
			if(community_id != null && $.trim(community_id) != '' && ad_name != null && $.trim(ad_name) != ''
				&& service_type != null && $.trim(service_type) != '' && ad_order != null && $.trim(ad_order) != ''
				&& status != null && $.trim(status) != '') {
				$('#adForm').submit();
			} else {
				alert("表单输入不完整！");
			}

		}

		function setAdUrl() {
			var url = $('#service_id :selected').attr('url');
			$('#ad_url').val(url);
		}
	</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
	<jsp:include page="../common/menu.jsp" />
	<input type="hidden" id="curr_li" value="service_1"/>
	<div id="mright">
		<div id="mr_cnt">
			<div class="mrw_title">编辑广告</div>
			<form action="${pageContext.request.contextPath }/ad.do?method=saveAd" method="post" id="adForm">
				<input type="hidden" value="${ad.ad_id}" name="ad_id"/>
				<input type="hidden" name="status" value="0" id="status"/>
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
							<td><input type="text" class="i_text_1" name="ad_name" id="ad_name" value="${ad.ad_name}"/></td>
						</tr>
						<tr>
							<th>描述</th>
							<td><textarea class="i_text_1" name="ad_dec" id="ad_dec" style="height: 100px;">${ad.ad_dec}</textarea></td>
						</tr>
						<tr>
							<th>类型</th>
							<td>
								<select id="service_type" name="service_type" class="i_text_1">
									<option value="11" <c:if test="${ad.service_type == 11}">selected</c:if> >商品广告</option>
									<option value="12" <c:if test="${ad.service_type == 12}">selected</c:if> >订单广告</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>关联服务</th>
							<td>
								<select id="service_id" name="service_id" class="i_text_1" onchange="setAdUrl()">
									<option value="">不关联</option>
									<c:forEach items="${service_list}" var="service">
										<option value="${service.service_id}" url="${service.service_url}" <c:if test="${ad.service_id == service.service_id}">selected</c:if> >${service.service_name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>链接</th>
							<td><input type="text" class="i_text_1" name="ad_url" id="ad_url" value="${ad.ad_url}"/></td>
						</tr>
						<tr>
							<th>顺序</th>
							<td><input type="text" class="i_text_1" name="ad_order" id="ad_order" value="${ad.ad_order}"/></td>
						</tr>
						<%--<tr>
							<th>可见性</th>
							<td>
								<select id="status" name="status" class="i_text_1">
									<option value="1" <c:if test="${ad.status == 1}">selected</c:if> >可见</option>
									<option value="2" <c:if test="${ad.status == 2}">selected</c:if> >隐藏</option>
								</select>
							</td>
						</tr>--%>
					</table>

				</div>

				<div class="mrw_btn_wrap">
					<a href="javascript:saveAd();" class="btn btn_big btn_r">保存</a>
					<a href="${pageContext.request.contextPath }/jsp/order/order_search.jsp" class="btn btn_big btn_r">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
