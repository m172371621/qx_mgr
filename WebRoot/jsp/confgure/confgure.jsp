<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <jsp:include page="../common/resource.jsp"/>
    <jsp:include page="../common/datetimepicker.jsp"/>

    <script type="text/javascript">
        function query() {
            $('#pageIndex').val(1);
            $('#searchForm').submit();
        }

        function del(config_id,community_id) {
            if(confirm("确定删除！")) {
        	 $.post('${pageContext.request.contextPath }/config_communityctrl.do?method=config_CommunityDel',
        			 {config_id : config_id,community_id : community_id},
                     function(data) {
		                    if(data != null && data == 1) {
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
<jsp:include page="../common/header.jsp"/>
<div id="main" class="c">
    <jsp:include page="../common/menu.jsp"/>

    <input type="hidden" id="curr_li" value="service_1"/>

    <div id="mright">
        <div id="mr_cnt">
            <div class="mrw_title">配置编辑</div>
            <form action="${pageContext.request.contextPath }/config_communityctrl.do?method=getConfig_communityList" method="post" id="searchForm">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex}"/>
                <div class="sform_wrap c">
                    <ul>
                        <li>
                          	  名称：<input name="config_id" id="config_id" class="i_text" value="${requestScope.config_id }"/>
                        </li>
                        <li>
                            <a href="javascript:query()" class="btn btn_big btn_y">搜 索</a>
                            <a target="_blank" href="${pageContext.request.contextPath}/config_communityctrl.do?method=config_communityEdit" class="btn btn_big btn_y">新 增</a>
                        </li>
                    </ul>
                </div>
            </form>
            <div id="order_wrap" class="p20">
               <table class="table_t_t">
                    <tr>
                        <th width="100px">名称</th>
                        <th width="100px">值</th>
                        <th width="100px">配注</th>
                        <th width="60px">小区</th>
                        <th width="70px">配置1</th>
                        <th width="80px">配置2</th>
                        <th width="60px">配置3</th>
                        <th width="100px">时间</th>
                        <th width="80px" class="icf">&#xf013e;</th>
                    </tr>
                    <c:forEach items="${requestScope.config_communityList}" var="list">
                    	<tr>
                    		<td>${list.config_id }</td>
                    		<td>${list.config_value }</td>
                    		<td>${list.config_dec }</td>
                    		<td>
                    			<c:if test="${list.community_id ==2}">精锐SOHO</c:if>
                    			<c:if test="${list.community_id ==3}">泽天大厦 </c:if>
                    			<c:if test="${list.community_id ==4}">来凤小区</c:if>
                    			<c:if test="${list.community_id ==5}">恒天财富</c:if>
                    			<c:if test="${list.community_id ==6}">依云城邦/亚普汽车</c:if>
                    			<c:if test="${list.community_id ==7}">紫金南苑</c:if>
                    			<c:if test="${list.community_id ==8}">翠屏国际</c:if>
                    			<c:if test="${list.community_id ==9}">水晶城/绿杨新苑</c:if>
                    			<c:if test="${list.community_id ==10}">保集半岛</c:if>
                    			<c:if test="${list.community_id ==11}">华建雅筑 </c:if>
                    		</td>
                    		<td>${list.config_ext1 }</td>
                    		<td>${list.config_ext2 }</td>
                    		<td>${list.config_ext3 }</td>
                    		<td>${list.createTime }</td>
                    		<td>
                    			<a href="javascript:del('${list.config_id }',${list.community_id })" class="btn btn_r">删除</a>&nbsp;&nbsp;
                    			<a target="_blank" href="${pageContext.request.contextPath}/config_communityctrl.do?method=config_communityEdit&config_id=${list.config_id }&community_id=${list.community_id }" class="btn btn_r">修改</a>
                    		</td> 
                    	</tr>
                    </c:forEach>
                </table>
            </div>
            <jsp:include page="../common/page.jsp" flush="true">
                <jsp:param name="pageIndex" value="${pageIndex}"/>
                <jsp:param name="pageCount" value="${pageCount}"/>
            </jsp:include>
        </div>
    </div>
</div>
</body>
</html>
