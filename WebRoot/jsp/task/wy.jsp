<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*"%>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<input type="hidden" id="curr_li" value="product_1" />
			<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">
						<%
							int pageIndex = request.getAttribute("pageIndex") == null
									? 0
									: (Integer) request.getAttribute("pageIndex");
							if ((Integer) request.getAttribute("addProPerty") == 1) {
						%>
						物业公告新增
						<%
							} else {
						%>
						物业公告编辑
						<%
							}
						%>

					</div>
					<form action="" method="post" id="form">
						<input type="hidden" name="pageIndex" id="pageIndex"
							value="<%=pageIndex%>" />
						<input type="hidden" id="property_information_id"
							value="<%=request.getAttribute("property_information_id")%>" />
						<div class="p20 m20">
							<table class="table_t_r">
								<colgroup>
									<col width="30%" />
									<col />
								</colgroup>
								<tr>
									<th>
										标题：
									</th>
									<td>
										<input type="text" width="180px" name="title" class="title"
											value="${requestScope.info.title }" />
									</td>
								</tr>
								<tr>
									<th>
										内容：
									</th>
									<td>
										<textarea rows="8" cols="50" style="resize: none;"
											id="content">${requestScope.info.content }</textarea>
									</td>
								</tr>
								<tr>
									<th>
										手机号码：
									</th>
									<td>
										<input type="text" width="180px" name="phone" class="phone"
											value="${requestScope.info.phone }" />
										&nbsp;
										<b style="color: AAAAAA">如：（13888888888）</b>
										<span style="color: red; display: none;">手机号码错误</span>
									</td>
								</tr>
								<tr>
									<th>
										座机号码：
									</th>
									<td>
										<input type="text" width="180px" name="landline"
											class="landline" value="${requestScope.info.landline }" />
										&nbsp;
										<b style="color: AAAAAA">如：（025-8888888或8888888）</b>
										<span style="color: red; display: none;">座机号码错误</span>
									</td>
								</tr>
								<tr>
									<th>
										服务地址：
									</th>
									<td>
										<input type="text" width="180px" name="address"
											class="address" value="${requestScope.info.address }" />
									</td>
								</tr>
								<tr>
									<th>
										备注：
									</th>
									<td>
										<input type="text" width="180px" name="remarks"
											value="${requestScope.info.remarks }" />
									</td>
								</tr>
								<tr>
									<th>
										时间：
									</th>
										<td>
											<input type="text" style="width: 180px" readonly
											class="i_text" name="end_time1" id="createTime"
											value="<%=request.getAttribute("createTime")==null?new SimpleDateFormat("yyyy-MM-dd").format(new Date()):request.getAttribute("createTime")%>"/>
										</td>
								</tr>
							</table>
						</div>
						<div class="mrw_btn_wrap">
							<%
								if ((Integer) request.getAttribute("addProPerty") == 1) {
							%>
							<a href="javascript:addInfo()" class="btn btn_big btn_r">确定</a>
							<%
								} else {
							%>
							<a href="javascript:editInfo()" class="btn btn_big btn_r">确定</a>
							<%
								}
							%>
						</div>
					</form>
				</div>
			</div>

			<script type="text/javascript">
	var flag = 0;
	$('.phone').blur(function() {
		var phone = $(this).val();
		var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;

		if (phone != "" && !reg.test(phone)) {
			$(this).siblings("span").css("display", "block");
			flag = 1;
		} else {
			$(this).siblings("span").css("display", "none");
			flag = 0;
		}
	});

	$('.landline').blur(function() {
		var landline = $(this).val();
		var reg = /^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
		if (landline != "" && !reg.test(landline)) {
			$(this).siblings("span").css("display", "block");
			flag = 1;
		} else {
			$(this).siblings("span").css("display", "none");
			flag = 0;
		}
	});

	function addInfo() {
		var phone2 = $('.phone').val();
		var title = $('.title').val();
		var content = $('#content').val();
		var address = $('.address').val();
		if (flag == 1)
			return;
		var title = $("input[name='title']").val();
		var content = $("#content").val();
		var phone = $("input[name='phone']").val();
		var landline = $("input[name='landline']").val();
		var address = $("input[name='address']").val();
		var createTime = $("#createTime").val();
		var remarks = $("input[name='remarks']").val();
		debugger;
		$
				.ajax( {
					type : 'POST',
					url : "${pageContext.request.contextPath }/task.do?method=propertyInfo_ADD",
					data : {
						title : title,
						content : content,
						phone : phone,
						landline : landline,
						address : address,
						createTime : createTime,
						remarks : remarks
					},
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						var title = $("input[name='title']").val(null);
						var content = $("#content").val(null);
						var phone = $("input[name='phone']").val(null);
						var landline = $("input[name='landline']").val(null);
						var address = $("input[name='address']").val(null);
						var remarks = $("input[name='remarks']").val(null);
						alert(dataObj.result_dec);
						location.href = "${pageContext.request.contextPath }/task.do?method=propertyInfoList";
					}
				});
	}

	function editInfo() {
		debugger;
		var title = $("input[name='title']").val();
		var content = $("#content").val();
		var phone = $("input[name='phone']").val();
		var landline = $("input[name='landline']").val();
		var address = $("input[name='address']").val();
		var createTime = $("#createTime").val();
		var remarks = $("input[name='remarks']").val();
		var property_information_id = $("#property_information_id").val();
		$
				.ajax( {
					type : "post",
					url : "${pageContext.request.contextPath }/task.do?method=editProperty",
					data : {
						property_information_id : property_information_id,
						title : title,
						content : content,
						phone : phone,
						landline : landline,
						address : address,
						createTime : createTime,
						remarks : remarks
					},
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						alert(dataObj.result_dec);
						location.href = "${pageContext.request.contextPath }/task.do?method=propertyInfoList";
					}
				});
	}
</script>
			<!-- content end -->
			<script type="text/javascript">
	
</script>
		</div>
	</body>
</html>
