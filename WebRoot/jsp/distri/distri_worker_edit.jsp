<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/resource.jsp" />
	<script type="text/javascript">
		function saveAd() {
			var distri_community_id = $("#community_id").val();
			var distri_worker_name = $("#name").val();
			var distri_worker_loginpwd = $("#loginpwd").val();
			var distri_worker_phone = $("#phone").val();
			$('#adForm').submit();
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
			<form action="${pageContext.request.contextPath }/distri.do?method=save_worker" method="post" id="adForm">
				 <input type="hidden" name="distri_worker_id" value="${map.distri_worker_id }"/>
				<div class="p20 m20">
					<table class="table_t_r">
	           			<tr>
	           				<th>姓名：</th>
	           				<td>
	           					<input type="text" name="distri_worker_name" id="name" value="${map.distri_worker_name }"/>
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>登录名：</th>
	           				<td>
           						<input type="text" name="distri_worker_login_name" id="loginname" <c:if test="${map != null }">disabled="true"</c:if> value="${map.distri_worker_login_name }"/>
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>密码：</th>
	           				<td>
	           					<input type="text" name="distri_worker_loginpwd" id="loginpwd" value="${map.distri_worker_login_pwd }"/>
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>手机号码：</th>
	           				<td>
	           					<input type="text" name="distri_worker_phone" id="phone" value="${map.distri_worker_phone }"/>
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>小区：</th>
	           				<td>
	           					<select name="distri_community_id" id="community_id">
	           						<option value="2" <c:if test="${map.distri_community_id == 2}">selected = "selected"</c:if>>精锐SOHO</option>
	           						<option value="3" <c:if test="${map.distri_community_id == 3}">selected = "selected"</c:if>>泽天大厦</option>
	           						<option value="4" <c:if test="${map.distri_community_id == 4}">selected = "selected"</c:if>>来凤小区</option>
	           						<option value="5" <c:if test="${map.distri_community_id == 5}">selected = "selected"</c:if>>恒天财富</option>
	           						<option value="6" <c:if test="${map.distri_community_id == 6}">selected = "selected"</c:if>>依云城邦/智谷</option>
	           						<option value="7" <c:if test="${map.distri_community_id == 7}">selected = "selected"</c:if>>紫金南苑</option>
	           						<option value="8" <c:if test="${map.distri_community_id == 8}">selected = "selected"</c:if>>翠屏国际</option>
	           						<option value="9" <c:if test="${map.distri_community_id == 9}">selected = "selected"</c:if>>水晶城/绿杨新苑</option>
	           						<option value="10" <c:if test="${map.distri_community_id == 10}">selected = "selected"</c:if>>保集半岛</option>
	           						<option value="11" <c:if test="${map.distri_community_id == 11}">selected = "selected"</c:if>>华建雅筑</option>
	           						<option value="12" <c:if test="${map.distri_community_id == 12}">selected = "selected"</c:if>>成都锦江区</option>
	           						<option value="13" <c:if test="${map.distri_community_id == 13}">selected = "selected"</c:if>>小卫街</option>
	           					</select>
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>头像</th>
	           				<td>
	           					<input type="file" name="distri_worker_img" id="picture_file"/>
	           					<input type="button" name="file_upload" value="上传图片" onclick="jq_upload('temp','picture_file','distri_worker_img')"/>
								<!-- <input type="hidden" id ="distri_worker_img" name="distri_worker_img" value=""/>-->
								</td> 
	           				</td>
	           			</tr>
	           			<tr>
	           				<th>头像地址：</th>
	           				<td><input type="text" style="width:450px;" value="${map.distri_worker_img }"/></td>
	           			</tr>
	           		</table>
				</div>
				<div class="mrw_btn_wrap">
					<a href="javascript:saveAd();" class="btn btn_big btn_r">保存</a>
					<a href="${pageContext.request.contextPath }/distri.do?method=distr_work_list" class="btn btn_big btn_r">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
