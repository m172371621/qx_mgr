<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
  <%@ page import="com.brilliantreform.sc.weixin.po.WeixinQxx" %>
  <title>推荐列表</title>
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="format-detection" content="telephone=no, email=no">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Cache-Control" content="no-cache">
  <meta http-equiv="Expires" content="0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <!--ie谷歌适配-->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
  <!--非常重要的文件-->
  <c:set var="ctx" value="${pageContext.request.contextPath}"/>
  <link type="text/css" href="${ctx}/new/css/list-we.css" rel="stylesheet"/>
  <script src="${ctx}/new/js/jquery.min.js"></script>
  <script src="${ctx}/new/js/jweixin-1.0.0.js"></script>
  <script src="${ctx}/new/js/recommendlist.js"></script>
</head>
<body>
 
  <div class="banner">
    <img src="${ctx}/new/images/banner.png" alt="加载中"></div>
  <!--图片展示区域-->
  <div class="content">
  <c:choose>
  
  
  
    <c:when test="${list!= null && fn:length(list) > 0}">
    <c:forEach items="${list}" var="item" varStatus="s">
     <div class="click_two">
      <div class="first_part">

        <div class="left-1">
          <span class="shuzi">${s.index+1 }</span>
          <span class="pic">
            <img src="${ctx}/new/images/toux.jpg" alt=""></span>
          <div class="info">
            <p class="north_font"><c:out value="${item.name}"></c:out></p>
            <p class="qx_clock"><c:out value="${item.create_time}"/></p>
          </div>

        </div>
        <c:if test="${(item.name=='' || item.addr=='' || item.phone=='') && item.state==1}">
        <div class="border_two_first">
          <a href="#">未填信息</a>
        </div>
        <div class="clear"></div>
        </c:if>
        <c:if test="${!(item.name=='' || item.addr=='' || item.phone=='') && item.state==1}">
           <div class="border_two">
             <a href="#">已填信息</a>
           </div>
        </c:if>
        <c:if test="${item.state==2}">
        <div class="border_two_third">
          <a href="#">区享已确认</a>
        </div>
        </c:if>
        <c:if test="${item.state==3}">
           <div class="border_two_forth">
             <a href="#">区享驳回</a>
           </div>
        </c:if>
      </div>
      <input type="hidden" name="name" value="${item.name }"/>
      <input type="hidden" name="phone" value="${item.phone }"/>
      <input type="hidden" name="addr" value="${item.addr }"/>
    </div>
    </c:forEach>
    
    
    </c:when>
    <c:otherwise>
       <div style="text-align:center;margin-top:40px;"><span>无被推荐人信息</span></div>
    </c:otherwise>
    </c:choose>
  </div>

  <!-- 弹出框部分  -->

  <div class="dialog-tu" style="display:none">
    <div class="wrap_zi">
      <div class="no"></div>
      <div class="clear"></div>
      <div class="dialog-con">

        <span>电话：</span><div class="phone" id="phone1"></div><div class="clear"></div> 
        <span>姓名：</span><div class="name" id="name1"></div><div class="clear"></div>
        <span>地址：</span><div class="adress" id="addr1"></div><div class="clear"></div>
      </div>
    </div>
  </div>

</body>