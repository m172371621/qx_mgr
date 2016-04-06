<!DOCTYPE html>
<html lang="en">

    <head>
		<meta charset="utf-8">
		<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<title>成为区享侠</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no, email=no">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  <!--ie谷歌适配-->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /><!--非常重要的文件-->
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
        <script src="${ctx}/new/js/jquery.min.js"></script>
       <link type="text/css" href="${ctx}/new/css/new_qxx.css" rel="stylesheet"/>
        <script type="text/javascript" src="${ctx}/new/js/new_qxx.js"></script>
      </head>
    <body>
    
     <div class="banner"><a href="#"><img src="<%=request.getAttribute("product_pic")==null?"":request.getAttribute("product_pic").toString() %>" alt="加载"></a></div>
     <!-- banner部分-->
    
     <div class="msg">
       <div class="detail">
         <div class="qg_gl"><a href="#"><input class="btn" type="button" name="" value="抢购攻略&nbsp;!"></a></div>
       <p class="gray_font">提交信息后分享给20个小伙伴，即可抢购！还可成为区享侠，
每月优先获得1元抢购机会，数量有限，先到先得！</p>
       <p class="red_font">请填写收货地址，请勿乱填，以免无法正常收货！</p>
       
      </div>
  <!-- 文字部分区域  -->
     <form action="${ctx}/weixinqxx.do" id="qxxform">
      <input type="hidden" name="method" value="doWeixinQxxNew" />
      <div class="mobile">
          <span class="qx_input"><input class="qx_phone" type="text" name="phone" placeholder="&nbsp;&nbsp;请输入手机号码" value="${phone }"></span>
      
         <!-- <div class="line"></div>-->
      </div>
     <!--手机-->
     
       <div class="name">
         <span class="qx_input"><input class="qx_phone" type="text" name="name" placeholder="&nbsp;&nbsp;请输入姓名" value="${name }"></span>
       
         <!--<div class="line"></div>-->
       
       </div>
    <!-- 姓名-->
     
     <div class="address">
         
         <span class="qx_input"><input class="qx_phone" type="text" name="addr" placeholder="&nbsp;&nbsp;请输入住址" value="${addr }"></span>
        <!-- <div class="line"></div>-->
     
     
     
     
     </div>
    </form>     
     
     
     </div>
   <!--用户信息部分-->
    <div class="qx_xia">
     <a href="#" target="_blank">区享侠是什么</a>
     <span class="arrow"><img src="${ctx}/new/images/arrow.png" alt="加载中"></span>
    </div>
   <!-- 区享侠是什么-->
    
    <div class="qx_banner"><img src="${ctx}/new/images/detail.png" alt="加载中"></div>
    <!-- banner区域-->
    
     <div id="qx_step">
       <img src="${ctx}/new/images/step.png" alt="">
         <div id="rockets"><a href=""><img src="${ctx}/new/images/rockets.png"></a></div>
     </div>
     <!-- 步骤介绍图-->
    <div class="bottom">
      <a href="javascript:submitData()">提交成为区享侠</a>
    </div>
   <!-- 浮层部分-->
    
    
    
    
    </body>