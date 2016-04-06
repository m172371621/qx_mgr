<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <title>区享侠专购</title>
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
  <link type="text/css" href="${ctx}/new/css/smallpage.css" rel="stylesheet"/>
  <script src="${ctx}/new/js/jquery.min.js"></script>
  <script src="${ctx}/new/js/jweixin-1.0.0.js"></script>
  <script src="${ctx}/new/js/weixinqxx.js"></script>
  <script src="${ctx}/new/js/jquery.lazyload.js"></script>
</head>

<body>
  <input type="hidden" id="ctx" value="${ctx}" />
  <div class="container">
    <div class="banner">
      <a href="#">
        <img src="<%=request.getAttribute("product_pic")==null?"":request.getAttribute("product_pic").toString() %>" alt="加载"></a> 
    </div>
    <!-- banner部分-->
    <div class="first_part">
      <div class="title">
        <a href="#" target="_blank"><%=request.getAttribute("product_name")==null?"":request.getAttribute("product_name").toString() %></a>
      </div>

      <div class="price">
        <p class="left_part">
          <span class="rmb">
            ￥
            <a href="#"><%=request.getAttribute("product_price_app")==null?"":request.getAttribute("product_price_app").toString() %></a>
          </span>
          <span class="origin">原价：248</span>
        </p>

        <div class="twenty">
          <span>共<%=request.getAttribute("product_amount")==null?"":request.getAttribute("product_amount").toString() %>台</span>
        </div>

      </div>

    </div>
    <!-- 标题部分结束 -->
    <div class="gray"></div>
    <!--灰色分割线部分 -->

    <div class="main">
      <div class="first" style="display:none"><a href="${ctx}/weixinqxx.do?method=doWeixinQxxNewUrl">
        <div class="my_ma">
          <span>我的推荐码</span>
        </div>

         <div class="click">
          <%=request.getAttribute("my_recommend_code")==null?"点击获取抢购资格":request.getAttribute("my_recommend_code").toString() %>
        </div>

        <div class="line"></div></a>
      </div>
      <!--我的推荐码-->
      <div class="first" style="display:none">
        <div class="my_ma">
          <span>他人推荐码</span>
        </div>

        <div class="click_two1"><%=(request.getAttribute("other_recommend_code")==null || request.getAttribute("other_recommend_code").equals("null"))?"请点击输入":request.getAttribute("other_recommend_code").toString() %></div>

        <div class="line"></div>
      </div>
      <!--他人推荐吗-->
      <div class="first"><a href="${ctx}/weixinqxx.do?method=getRecommendList">
        <div class="my_ma">
          <span>已推荐人数</span>
        </div>

        <div class="click_third">
          
            <%=request.getAttribute("recommend_amount")==null?"":request.getAttribute("recommend_amount").toString() %>
            <span class="big">&#8250;</span>
          
       </div>

        <div class="line"></div></a>
      </div>
      <!--已推荐人数-->

      <div class="gray_two"></div>
      <div class="tit">
        <h2>商品详情</h2>
      </div>
      <!--灰色分割线部分-->
      <div class="detail">
        <img  src="<%=request.getAttribute("product_dec_pic")==null?"":request.getAttribute("product_dec_pic").toString() %>" alt="商品详情"></div>
      <!-- 商品详情页面--> </div>

  </div>

  <!-- 推荐码部分   -->
  <div class="bottom">
    <div class="now">
      <span>推荐满30人即可点击购买</span>
    </div>

    <div class="buy">
        <a href="javascript:getRecommendList()">立即抢购</a>
    </div>
    <div class="line_two"></div>

  </div>

  <!-- 底部固定部分  -->

  <div class="dialog-tu" style="display:none">
    <div class="wrap_zi">
      <div class="close"></div>
      <div class="dialog-con">
        <h2>请输入推荐码</h2>
        <input  class="input-cc" type="text" />
        <div class="clear"></div>
        <div class="sure-no">
          <input type="button" class="sure" value="确定" />
          <input type="button" class="no"  value="取消" />
        </div>
      </div>
    </div>
  </div>

  <div class="wapper"  ></div>
  <input type="hidden" id="my_recommend_code" value="<%=request.getAttribute("my_recommend_code") %>"/>
  <input type="hidden" id="product_name" value="<%=request.getAttribute("product_name") %>"/>
  <input type="hidden" id="appId" value="<%=request.getAttribute("appId") %>"/>
  <input type="hidden" id="timestamp" value="<%=request.getAttribute("timestamp") %>"/>
  <input type="hidden" id="nonceStr" value="<%=request.getAttribute("nonceStr") %>"/>
  <input type="hidden" id="signature" value="<%=request.getAttribute("signature") %>"/>
</body>