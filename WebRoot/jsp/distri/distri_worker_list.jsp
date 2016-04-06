<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<jsp:include page="../common/resource.jsp" />
		<jsp:include page="../common/datetimepicker.jsp" />
		<script type="text/javascript">
    function query() {
    	$('#pageIndex').val(1);
        $('#searchForm').submit();
    }
    
    function del_worker(worker_id) {
        if(confirm("确定要删除吗？")) {
            $.post('${pageContext.request.contextPath }/distri.do?method=delete', {worker_id : worker_id}, function(data) {
                if(data != null && data == 'ok') {
                    alert("删除成功！");
                    location.reload();
                } else {
                    alert("删除失败！");
                }
            });
        }
    }
    
    </script>
	</head>
	<body>
		<jsp:include page="../common/header.jsp" />
		<div id="main" class="c">
			<jsp:include page="../common/menu.jsp" />
			<input type="hidden" id="curr_li" value="service_0" />
			<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">
						配送员
					</div>
					<form
						action="${pageContext.request.contextPath }/distri.do?method=distr_work_list"
						method="post" id="searchForm">
						<input type="hidden" name="pageIndex" id="pageIndex"
							value="${pageIndex}" />
						<div class="sform_wrap c">
							<ul>
								<li>
									姓名：
									<input name="worker_name" id="service_name" class="i_text"
										value="${queryParam.worker_name}" />
								</li>
								<li>
									<a href="javascript:query()" class="btn btn_big btn_y">搜 索</a>
									<a target="_blank"
										href="${pageContext.request.contextPath}/distri.do?method=distr_work_edit"
										class="btn btn_big btn_y">新 增</a>
								</li>
							</ul>
						</div>
					</form>
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
							<tr>
								<th width="150px">
									姓名
								</th>
								<th width="100px">
									手机
								</th>
								<th width="70px">
									小区
								</th>
								<th width="70px">
									配送状态
								</th>
								<th width="80px" class="icf">
									&#xf013e;
								</th>
							</tr>
							<c:forEach items="${list}" var="dirtri_worker">
								<tr>
									<td>
										${dirtri_worker.distri_worker_name }
									</td>
									<td>
										${dirtri_worker.distri_worker_phone }
									</td>
									<td>
										<c:choose>
											<c:when test="${dirtri_worker.distri_community_id == 2}">精锐SOHO</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 3}">泽天大厦</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 4}">来凤小区</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 5}">恒天财富 </c:when>
											<c:when test="${dirtri_worker.distri_community_id == 6}">依云城邦/智谷</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 7}">紫金南苑</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 8}">翠屏国际</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 9}">水晶城/绿杨新苑</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 10}">保集半岛</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 11}">华建雅筑</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 12}">成都锦江区</c:when>
											<c:when test="${dirtri_worker.distri_community_id == 13}">小卫街 </c:when>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${dirtri_worker.distri_worker_status == 0}">待配送</c:when>
											<c:when test="${dirtri_worker.distri_worker_status == 0}">完成</c:when>
											<c:when test="${dirtri_worker.distri_worker_status == 0}">暂时删除</c:when>
											<c:when test="${dirtri_worker.distri_worker_status == 0}">永久删除</c:when>
										</c:choose>
									</td>
									<td>
										<a class="btn btn_r"
											href="javascript:del_worker(${dirtri_worker.distri_worker_id})">
											删除</a>
										<a class="btn btn_r"
											href="${pageContext.request.contextPath}/distri.do?method=distr_work_edit&worker_id=${dirtri_worker.distri_worker_id}">修改</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<jsp:include page="../common/page.jsp" flush="true">
						<jsp:param name="pageIndex" value="${pageIndex}" />
						<jsp:param name="pageCount" value="${pageCount}" />
					</jsp:include>
				</div>
			</div>
		</div>
	</body>
</html>