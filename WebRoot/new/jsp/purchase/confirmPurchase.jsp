<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
  <style type="text/css">
    dl {
      margin-top: 10px;
      margin-bottom: 10px;
    }

    .dl-horizontal dt {
      width: 100px;
    }

    .dl-horizontal dd {
      margin-left: 120px;
    }
  </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="margin-right:13px;">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>订单到货确认</h5>
        </div>
        <div class="ibox-content">

          <div class="row">
            <div class="col-sm-12">
              <div class="alert alert-info">
                订货单号：<b class="text-navy">${requestScope.header.purchase_header_no}</b>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                门店：<b class="text-navy">${requestScope.header.community_name}</b>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                提交时间：<b class="text-navy"><fmt:formatDate value="${requestScope.header.createTime}" pattern="yyyy-MM-dd HH:mm"/></b>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                预计付款：<b class="text-navy">${requestScope.header.purchase_price}</b>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                实际付款：<b class="text-navy">${requestScope.header.purchase_real_price}</b>
              </div>
            </div>
          </div>

          <c:if test="${fn:length(detailList) == 0}">
                暂无商品
          </c:if>


          <c:if test="${fn:length(detailList) > 0}">
            <div class="table-responsive">
              <table class="table text-center">
                <%--<thead>
                <tr>
                  <th colspan="3" class="text-center">商品</th>
                  <th class="text-center">实际</th>
                  <th class="text-center">状态</th>
                  <th class="text-center">操作</th>
                </tr>
                </thead>--%>
                <tbody>
                <c:forEach items="${detailList}" var="details">
                  <c:forEach items="${details}" var="detail" varStatus="status">
                    <c:if test="${status.index == 0}">
                      <tr>
                        <td colspan="8" class="text-left">
                          <c:if test="${!empty detail.supplier_id}">
                              <b>供应商：</b>${detail.supplier_info_name}
                              &nbsp;&nbsp;&nbsp;&nbsp;
                              <b>联系人：</b>${detail.contact_name}
                              &nbsp;&nbsp;&nbsp;&nbsp;
                              <b>电话：</b>${detail.contact_phone}
                          </c:if>
                        </td>
                      </tr>
                    </c:if>
                  <tr data-detailid="${detail.purchase_detail_id}">
                    <td>
                      <img src="${detail.thumbnail}" style="max-width: 100px;max-height: 100px;"/>
                    </td>
                    <td>
                        ${detail.product_name}
                    </td>
                    <td>
                      <dl class='dl-horizontal'>
                        <dt>单位</dt><dd class="text-left">${detail.unit}</dd>
                        <dt>规格</dt><dd class="text-left">${detail.spec}</dd>
                        <dt>预期进价</dt><dd class="text-left">${detail.price}</dd>
                        <dt>指导售价</dt><dd class="text-left">${detail.market_price}</dd>
                        <dt>产地</dt><dd class="text-left">${detail.place}</dd>
                        <dt>预期利润率</dt><dd class="text-left"></dd>
                        <dt>近一个月销量</dt><dd class="text-left"></dd>
                      </dl>
                    </td>
                    <td>
                      <dl class='dl-horizontal'>
                        <dt>实际进价</dt>
                        <dd class="text-left">
                            <fmt:formatNumber var="price" value="${empty detail.product_real_price ? detail.product_price : detail.product_real_price}" maxFractionDigits="2"/>
                            ${price}
                        </dd>
                        <dt>订购数量</dt>
                        <dd class="text-left">
                            <fmt:formatNumber var="amount" value="${empty detail.product_real_amount ? detail.product_amount : detail.product_real_amount}" maxFractionDigits="2"/>
                            ${amount}
                        </dd>
                        <dt>小计</dt>
                        <dd class="text-left">
                            <fmt:formatNumber value="${price * amount}" maxFractionDigits="2"/>
                        </dd>
                      </dl>
                    </td>
                    <td>
                        <qx:getEnumName clazz="com.brilliantreform.sc.purchase.enumerate.PurchaseStatus" value="${detail.status}" name="status_name"/>
                        ${status_name}
                    </td>
                    <td>
                      <c:if test="${detail.status == 2}">
                        <button type="button" class="btn btn-primary" onclick="confirm('${detail.purchase_detail_id}')">确认收货</button>
                      </c:if>
                    </td>
                  </tr>
                  </c:forEach>
                </c:forEach>

                </tbody>
              </table>
            </div>
          </c:if>

        </div>
      </div>
    </div>
  </div>
</div>
<div style="display:none;position:fixed; right:0px; bottom:10px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="toTop" onclick="toTop()">
  返回顶部
</div>
<div style="position:fixed; right:0px; bottom:90px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="newTab" onclick="newTab()">
  新开窗口
</div>

<script type="text/javascript">
  function toTop() {
    $('body,html').animate({scrollTop: 0}, 100);
  }

  function newTab() {
    window.parent.open(location.href);
  }

  $(function() {
    $(window).scroll(function () {
      if ($(this).scrollTop() != 0) {
        $('#toTop').fadeIn();
      } else {
        $('#toTop').fadeOut();
      }
    });

    if(window.top == window.self){
      $('#newTab').hide();
    }
  });

  function confirm(detail_id) {
    layer.confirm("确认要收货吗？", function() {
      var i = layer.load();
      $.post('${ctx}/purchase.do?method=goodsArrive', {purchase_detail_id : detail_id}, function(data) {
        if(data != null && data == 'success') {
          layer.alert("操作成功！", function() {
            location.reload();
          });
        } else {
          layer.alert("操作失败！");
        }
        layer.close(i);
      });
    });
  }
</script>

</body>

</html>