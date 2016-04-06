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

        function removeAd(ad_id) {
            if(confirm("确定要删除该广告吗？")) {
                $.post('${pageContext.request.contextPath }/ad.do?method=removeAd', {ad_id : ad_id}, function(data) {
                    if(data != null && data == 'ok') {
                        alert("删除成功！")
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
            <div class="mrw_title">广告管理</div>
            <form action="${pageContext.request.contextPath }/ad.do?method=listAd" method="post" id="searchForm">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex}"/>
                <div class="sform_wrap c">
                    <ul>
                        <li>
                            名称：<input name="ad_name" id="ad_name" class="i_text" value="${queryParam.ad_name}"/>
                        </li>
                        <li>
                            类型：
                            <select id="service_type" name="service_type" class="i_text">
                                <option value="">全部</option>
                                <option value="11" <c:if test="${queryParam.service_type == 11}">selected</c:if> >商品广告</option>
                                <option value="12" <c:if test="${queryParam.service_type == 12}">selected</c:if> >订单广告</option>
                            </select>
                        </li>
                        <%--<li>
                            可见性：
                            <select id="status" name="status" class="i_text">
                                <option value="1">可见</option>
                                <option value="2">隐藏</option>
                            </select>
                        </li>--%>
                        <li>
                            <a href="javascript:query()" class="btn btn_big btn_y">搜 索</a>
                            <a target="_blank" href="${pageContext.request.contextPath}/ad.do?method=showAd" class="btn btn_big btn_y">新 增</a>
                        </li>
                    </ul>
                </div>
            </form>
            <div id="order_wrap" class="p20">
                <table class="table_t_t">
                    <tr>
                        <th width="150px">名称</th>
                        <th width="100px">类型</th>
                        <th width="100px">描述</th>
                        <th width="70px">顺序</th>
                        <%--<th width="70px">可见性</th>--%>
                        <th width="80px" class="icf">&#xf013e;</th>
                    </tr>
                    <c:forEach items="${list}" var="ad">
                        <tr>
                            <td>${ad.ad_name}</td>
                            <td>
                                <c:if test="${ad.service_type == 11}">商品广告</c:if>
                                <c:if test="${ad.service_type == 12}">订单广告</c:if>
                            </td>
                            <td>${ad.ad_dec}</td>
                            <td>${ad.ad_order}</td>
                            <%--<td>
                                <c:if test="${ad.status == 1}"><font color="red">可见</font></c:if>
                                <c:if test="${ad.status == 2}">隐藏</c:if>
                            </td>--%>
                            <td>
                                <a target="_blank" href="${pageContext.request.contextPath}/ad.do?method=showAd&ad_id=${ad.ad_id}" class="btn btn_r">修改</a>
                                <a href="javascript:removeAd(${ad.ad_id})" class="btn btn_r">删除</a>
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
