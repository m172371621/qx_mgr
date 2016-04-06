<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="input-group-btn" id="communityTagDiv_${communitySelectTag_id}">
    <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button" id="communityTagBtn_${communitySelectTag_id}"><span class="caret"></span></button>
    <input type="hidden" id="${communitySelectTag_id}" name="${communitySelectTag_name}">
    <ul class="dropdown-menu">
        <c:if test="${!empty communitySelectTag_header}">
            <li><a href="javascript:void(0)" onclick="setCommunityTagValue(this)">${communitySelectTag_header}</a></li>
            <li class="divider"></li>
        </c:if>
        <c:forEach items="${communitySelectTag_list}" var="c" varStatus="status">
            <li class="dropdown-submenu <c:if test="${!communitySelectTag_selectParent}">disabled</c:if>">
                <a href="javascript:void(0)" onclick="setCommunityTagValue(this)" data-cid="${c.parent.community_id}"
                   <c:if test="${communitySelectTag_value == c.parent.community_id}">data-select="select"</c:if>
                        >${c.parent.community_name}</a>
                <ul class="dropdown-menu">
                    <c:forEach items="${c.children}" var="child">
                        <li><a href="javascript:void(0)" onclick="setCommunityTagValue(this)" data-cid="${child.community_id}"
                               <c:if test="${communitySelectTag_value == child.community_id}">data-select="select"</c:if>
                                >${child.community_name}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
</div>

<script type="text/javascript">
    $(function() {
        //初始化选中的值
        if($('#communityTagDiv_${communitySelectTag_id} a[data-select=select]').length == 0) {
            setCommunityTagValue($('#communityTagDiv_${communitySelectTag_id} a').eq(0));
        } else {
            setCommunityTagValue($('#communityTagDiv_${communitySelectTag_id} a[data-select=select]'));
        }
    });

    function setCommunityTagValue(obj) {
        $('#communityTagBtn_${communitySelectTag_id}').html($(obj).text() + "&nbsp;&nbsp;<span class='caret'></span>");
        $('#${communitySelectTag_id}').val($(obj).data("cid"));
        <c:if test="${!empty communitySelectTag_event}">
            eval('${communitySelectTag_event}');
        </c:if>

    }
</script>