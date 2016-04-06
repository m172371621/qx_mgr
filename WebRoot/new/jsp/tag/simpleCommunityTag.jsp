<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<select id="${simpleCommunityTag_id}" name="${simpleCommunityTag_name}" class="${simpleCommunityTag_css}" onchange="${simpleCommunityTag_event}">
    <c:if test="${!empty simpleCommunityTag_header}">
        <option value="">${simpleCommunityTag_header}</option>
    </c:if>

    <c:forEach items="${simpleCommunityTag_list}" var="community">
        <c:choose>
            <c:when test="${simpleCommunityTag_showTotal != 'true'}">
                <c:if test="${community.org_info_type != 1}">
                    <option value="${community.community_id}"
                            <c:if test="${community.community_id == simpleCommunityTag_value}">selected</c:if>
                            >${community.community_name}</option>
                </c:if>
            </c:when>
            <c:otherwise>
                <option value="${community.community_id}"
                        <c:if test="${community.community_id == simpleCommunityTag_value}">selected</c:if>
                        >${community.community_name}</option>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</select>
