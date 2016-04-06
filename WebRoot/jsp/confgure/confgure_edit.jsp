<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />
<script type="text/javascript">
$(function() {
	$("input[name='createTime']").datepicker({
        dateFormat: "yy-mm-dd"
    });
});
</script>
<input type="hidden" id="curr_li" value="order_0"/>
	<div id="mright">
		<div id="mr_cnt">
					<div class="mrw_title">配置编辑</div>
					<form action="${pageContext.request.contextPath }/config_communityctrl.do?method=config_CommunityUpdata" method="post" id="form">
						<input type="test" width="180px"  class="i_text_1" name="config_id2" value="${list.config_id }"/>
						<div class="p20 m20">
							<table class="table_t_r">
							<colgroup><col width="30%"/><col/></colgroup>
								<tr>
									<th>名称:</th>		
									<td><input type="text" width="180px"  class="i_text_1" id="config_id" name="config_id"  value="${list.config_id}"/></td>
								</tr>
								<tr>
									<th>值:</th>
									<td><input type="text" width="180px"  class="i_text_1" id="config_value" name="config_value" value="${list.config_value}"/>
									<input type="hidden" name="cid_1" value=""/>
									</td>
								</tr>
								<tr>
									<th>配注:</th>
									<td><input type="text" width="180px"  class="i_text_1" id="config_dec" name="config_dec" value="${list.config_dec}"/></td>
								</tr>
								<tr>
									<th>小区:</th>
									<td>
										<c:if test="${!empty list}">
											<c:if test="${list.community_id ==2}"><input type="hidden"  id="community_id" name="community_id" value="2"/>精锐SOHO</c:if>
											<c:if test="${list.community_id ==3}"><input type="hidden"  id="community_id" name="community_id" value="3"/>泽天大厦 </c:if>
											<c:if test="${list.community_id ==4}"><input type="hidden"  id="community_id" name="community_id" value="4"/>来凤小区</c:if>
											<c:if test="${list.community_id ==5}"><input type="hidden"  id="community_id" name="community_id" value="5"/>恒天财富</c:if>
											<c:if test="${list.community_id ==6}"><input type="hidden"  id="community_id" name="community_id" value="6"/>依云城邦/亚普汽车</c:if>
											<c:if test="${list.community_id ==7}"><input type="hidden"  id="community_id" name="community_id" value="7"/>紫金南苑</c:if>
											<c:if test="${list.community_id ==8}"><input type="hidden"  id="community_id" name="community_id" value="8"/>翠屏国际"</c:if>
											<c:if test="${list.community_id ==9}"><input type="hidden"  id="community_id" name="community_id" value="9"/>水晶城/绿杨新苑</c:if>
											<c:if test="${list.community_id ==10}"><input type="hidden"  id="community_id" name="community_id" value="10"/>保集半岛</c:if>
											<c:if test="${list.community_id ==11}"><input type="hidden" id="community_id" name="community_id" value="11"/>华建雅筑 </c:if>
											<c:if test="${list.community_id ==12}"><input type="hidden" id="community_id" name="community_id" value="12"/>成都锦江区 </c:if>
											<c:if test="${list.community_id ==13}"><input type="hidden" id="community_id" name="community_id" value="13"/>小卫街 </c:if>
										</c:if>
										
										<c:if test="${empty list}">
											<select name="community_id" id="community_id">
				           						<option value="2">精锐SOHO</option>
				           						<option value="3">泽天大厦</option>
				           						<option value="4">来凤小区</option>
				           						<option value="5">恒天财富</option>
				           						<option value="6">依云城邦/智谷</option>
				           						<option value="7">紫金南苑</option>
				           						<option value="8">翠屏国际</option>
				           						<option value="9">水晶城/绿杨新苑</option>
				           						<option value="10">保集半岛</option>
				           						<option value="11">华建雅筑</option>
				           						<option value="12">成都锦江区</option>
				           						<option value="13">小卫街</option>
	           								</select>
										</c:if>
									</td>
								</tr>
								<tr>
									<th>配置1:</th>
									<td><input type="text" width="180px"  class="i_text_1" id="config_ext1" name="config_ext1" value="${list.config_ext1}"/></td>
								</tr>
								<tr>
									<th>配置2:</th>
									<td><input type="text" width="180px"  class="i_text_1" id="config_ext2" name="config_ext2" value="${list.config_ext2}"/></td>
								</tr>
								<tr>
									<th>配置3:</th>
									<td><input type="text" width="180px"  class="i_text_1" id="config_ext3" name="config_ext3"value="${list.config_ext3}"/></td>
								</tr>
								<tr>
									<th>时间:</th>
									<td>
										<input type="text" width="180px"  class="i_text_1" id="createTime" name="createTime" value="${list.createTime}"/>
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
</div>
</body>
</html>
<script type="text/javascript">
	function sub(){
			var config_id = $("#config_id").val();
			var config_value = $("#config_value").val();
			var config_dec = $("#config_dec").val();
			var community_id = $("#community_id").val();
			var config_ext1 = $("#config_ext1").val();
			var config_ext2 = $("#config_ext2").val();
			var config_ext3 = $("#config_ext3").val();
			var createTime = $("#createTime").val();
			if(config_id!=null && config_value != null){
				$("#form").submit();
			}
		}
</script>
