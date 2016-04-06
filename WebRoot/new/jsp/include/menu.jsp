<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${sessionScope.user_menu}" var="menu">
    <c:choose>
        <c:when test="${fn:length(menu.child) > 0}">
            <li>
                <a href="#">
                    <i class="<c:if test="${!empty menu.logo}">${menu.logo}</c:if><c:if test="${empty menu.logo}">fa fa-folder</c:if>"></i>
                    <span class="nav-label">${menu.name}</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <c:forEach items="${menu.child}" var="child">
                        <li>
                            <a class="J_menuItem" href="${child.url}">${child.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:when>
        <c:otherwise>
            <li>
                <a class="J_menuItem" href="${menu.url}">
                    <i class="<c:if test="${!empty menu.logo}">${menu.logo}</c:if><c:if test="${empty menu.logo}">fa fa-folder</c:if>"></i>
                    <span class="nav-label">${menu.name}</span>
                </a>
            </li>
        </c:otherwise>
    </c:choose>
</c:forEach>