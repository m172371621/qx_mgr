<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
  <script src="${ctx}/new/js/plugins/suggest/bootstrap-suggest.min.js"></script>
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
      margin-bottom: 2px;
    }
  </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="margin-right:13px;">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>订货单明细</h5>
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
                预计付款：<b class="text-navy"><fmt:formatNumber value="${requestScope.header.purchase_price}" maxFractionDigits="2"/></b>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                实际付款：<b class="text-navy"><fmt:formatNumber value="${requestScope.header.purchase_real_price}" maxFractionDigits="2"/></b>
              </div>
            </div>
          </div>

          <c:if test="${fn:length(detailList) == 0}">
                暂无商品
          </c:if>


          <c:if test="${fn:length(detailList) > 0}">
            <div class="btn-group">
              <button type="button" class="btn btn-outline btn-default" onclick="showAddProductWin()"
                      data-toggle="tooltip" data-placement="top" title="新增商品">
                <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
              </button>
              <%--<button type="button" class="btn btn-outline btn-default">
                <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
              </button>--%>
              <button type="button" class="btn btn-outline btn-default" onclick="saveDetail()"
                      data-toggle="tooltip" data-placement="top" title="保存修改">
                <i class="fa fa-save" aria-hidden="true"></i>
              </button>
            </div>

            <div class="table-responsive">
              <table class="table table-striped text-center" id="purchaseTable">
                <thead>
                <tr>
                  <th>
                    <%--<input type="checkbox" class="i-checks" onchange="checkAll(this)">--%>
                  </th>
                  <th colspan="3" class="text-center">商品</th>
                  <th class="text-center">预计</th>
                  <th class="text-center">实际</th>
                  <th class="text-center">供应商</th>
                  <th class="text-center">状态</th>
                  <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${detailList}" var="detail">
                  <tr data-detailid="${detail.purchase_detail_id}" data-productid="${detail.product_id}">
                    <td>
                      <%--<input type="checkbox" class="i-checks">--%>
                    </td>
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
                        <dt>预计进价</dt><dd class="text-left"><fmt:formatNumber value="${detail.product_price}" maxFractionDigits="2"/></dd>
                        <dt>订购数量</dt><dd class="text-left"><fmt:formatNumber value="${detail.product_amount}" maxFractionDigits="2"/></dd>
                        <dt>预计总价</dt>
                        <dd class="text-left">
                          <fmt:formatNumber value="${detail.product_price * detail.product_amount}" maxFractionDigits="2"/>
                        </dd>
                      </dl>
                    </td>
                    <td>
                      <fmt:formatNumber value="${empty detail.product_real_price ? detail.product_price : detail.product_real_price}" maxFractionDigits="2" var="real_price"/>
                      <fmt:formatNumber value="${empty detail.product_real_amount ? detail.product_amount : detail.product_real_amount}" maxFractionDigits="2" var="real_amount"/>
                      <fmt:formatNumber value="${real_price * real_amount}" maxFractionDigits="2" var="real_total_price"/>

                      <c:if test="${detail.status == 1}">
                        <dl class='dl-horizontal'>
                          <dt>实际进价</dt>
                          <dd class="text-left">
                            <input type="number" class="form-control text-center" style="width: 100px;"
                                   value="${real_price}" name="product_real_price" onkeyup="calcPrice(this)"/>
                          </dd>
                          <dt>订购数量</dt>
                          <dd class="text-left">
                            <input type="number" class="form-control text-center" style="width: 100px;"
                                   value="${real_amount}" name="product_real_amount" onkeyup="calcPrice(this)"/>
                          </dd>
                          <dt>小计</dt>
                          <dd style="margin-left: 50px;">
                            ${real_total_price}
                          </dd>
                        </dl>
                      </c:if>
                      <c:if test="${detail.status != 1}">
                        <dl class='dl-horizontal'>
                          <dt>实际进价</dt>
                          <dd class="text-left">
                            ${real_price}
                          </dd>
                          <dt>订购数量</dt>
                          <dd class="text-left">
                            ${real_amount}
                          </dd>
                          <dt>小计</dt>
                          <dd class="text-left">
                            ${real_total_price}
                          </dd>
                        </dl>
                      </c:if>
                    </td>
                    <td>
                      <c:if test="${detail.status == 1}">
                        <select class="form-control" name="supplier_id" data-supplierid="${detail.supplier_id}" style="width:120px;"></select>
                      </c:if>
                      <c:if test="${detail.status != 1}">
                        ${detail.supplier_info_name}
                      </c:if>
                    </td>
                    <td>
                      <qx:getEnumName clazz="com.brilliantreform.sc.purchase.enumerate.PurchaseStatus" value="${detail.status}" name="status_name"/>
                      ${status_name}
                    </td>
                    <td>
                      <c:if test="${detail.status == 1}">
                        <button type="button" class="btn btn-primary" onclick="sendGoods(this)">
                          发货
                        </button>
                        <button type="button" class="btn btn-danger"
                                onclick="removeDetail(this)">
                          删除
                        </button>
                      </c:if>
                    </td>
                  </tr>
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

<!-- 新增商品模态窗口 -->
<div class="modal fade" id="productWin" tabindex="-1" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增商品</h4>
      </div>
      <div class="modal-body">
        <form role="form" class="form-horizontal">
          <div class="form-group">
            <label for="product_add" class="col-md-3 control-label text-right">商品名称</label>
            <div class="col-md-6">
              <div class="input-group">
                <input type="text" class="form-control" id="product_add">
                <div class="input-group-btn">
                  <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu dropdown-menu-right" role="menu">
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="addProduct()">新增</button>
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

  function calcPrice(obj) {
    var product_price = $(obj).parent().parent().find('dd').eq(0).find('input').val();
    var product_amount = $(obj).parent().parent().find('dd').eq(1).find('input').val();
    var price = mul(product_price, product_amount);
    $(obj).parent().parent().find('dd').eq(2).text(price);
  }


  function sendGoods(obj) {
    var tr = $(obj).parents('tr');
    var header_id = '${requestScope.header.purchase_header_id}';
    var detail_id = tr.data('detailid');  //如果是在该页面添加的商品，detail_id可能为空
    var product_id = tr.data('productid');
    var product_price = tr.find('input[name=product_real_price]').val();
    var product_amount = tr.find('input[name=product_real_amount]').val();
    var supplier_id = tr.find('select[name=supplier_id]').val();

    if(header_id && product_id && product_price && product_amount && supplier_id) {
      layer.confirm("确定要发货吗？", function() {
        var i = layer.load();
        $.post('${ctx}/purchase.do?method=sendGoods', {detail_id : detail_id, header_id : header_id,
          product_id : product_id, product_price : product_price,
          product_amount : product_amount, supplier_id : supplier_id}, function(data) {
          if(data && data == 'success') {
            layer.alert("操作成功！", function() {
              location.reload();
            });
          } else {
            layer.alert("操作失败！", function() {
              location.reload();
            });
          }
        });
      });
    } else {
      layer.alert("参数不全！");
    }
  }

  /**
   * 初始化商品的供应商列表
   * */
  function initSupplier() {
    $('select[name=supplier_id]').each(function(i, v) {
      var product_id = $(v).parents('tr').data('productid');
      var supplier_id = $(v).data('supplierid');
      $.post('${ctx}/purchase.do?method=findSupplierByProduct', {product_id : product_id}, function(data) {
        if(data) {
          var json = eval('(' + data + ')');
          if (json) {
            var html = '';
            $.each(json, function(m, n) {
              var id = n.supplier_info_id;
              var name = n.supplier_info_name;
              html += "<option value='" + id + "'>" + name + "</option>";
            });
            $(v).html(html);
            if(supplier_id) {
              $(v).val(supplier_id);
            }
          }
        }
      });
    });
  }

  function initProductSuggest() {
    $("#product_add").bsSuggest({
      url : "${ctx}/purchase.do?method=findProductByKeyword&header_id=${requestScope.header.purchase_header_id}&keyword=",
      getDataMethod : 'url',  //获取数据的方式，总是从 URL 获取
      allowNoKeyword : false, //关键字为空时不搜索
      showBtn : false,
      autoDropup : true,
      idField : 'product_id',
      keyField : 'name',
      effectiveFields : ['name']
    });
  }

  function showAddProductWin() {
    $('#product_add').val('');
    $('#productWin').modal({
      backdrop: 'static'  //点击窗体外不会关闭窗口
    })
  }

  function addProduct() {
    var product_id = $('#product_add').attr('data-id');
    var product_name = $('#product_add').val();
    if(product_id && product_name) {
        var exist = false;
        $('#purchaseTable tr[data-productid]').each(function(i, v) {
            var _pro_id = $(v).data('productid');
            if(_pro_id == product_id) {
                exist = true;
                return false;
            }
        });
        if(exist) {
            layer.alert("该商品已存在");
            return;
        }

      var i = layer.load();
      $.post('${ctx}/purchase.do?method=getProductById', {product_id : product_id}, function(data) {
        if(data) {
          var json = eval('(' + data + ')');
          var product = json.product;
          var supplierList = json.supplierList;
          if (product) {
            //首先构建供应商html
            var supplier_html = "<select class='form-control' name='supplier_id' data-supplierid=''>";
            if(supplierList) {
              $.each(supplierList, function(m, n) {
                supplier_html += "<option value='" + n.supplier_info_id + "'>" + n.supplier_info_name + "</option>";
              });
            }
            supplier_html += "</select>";

            //tr html
            var html = "<tr data-detailid='' data-productid='" + product.product_id + "'>"
                    + "<td></td>"
                    + "<td>"
                    + "<img src='" + product.thumbnail + "' style='max-width: 100px;max-height: 100px;'>"
                    + "</td>"
                    + "<td>" + product.name + "</td>"
                    + "<td>"
                    + "<dl class='dl-horizontal'>"
                    + "<dt>单位</dt><dd class='text-left'>" + product.unit + "</dd>"
                    + "<dt>规格</dt><dd class='text-left'>" + product.spec + "</dd>"
                    + "<dt>预期进价</dt><dd class='text-left'>" + product.price + "</dd>"
                    + "<dt>指导售价</dt><dd class='text-left'>" + product.market_price + "</dd>"
                    + "<dt>产地</dt><dd class='text-left'>" + product.place + "</dd>"
                    + "<dt>预期利润率</dt><dd class='text-left'></dd>"
                    + "<dt>近一个月销量</dt><dd class='text-left'></dd>"
                    + "</dl>"
                    + "</td>"
                    + "<td>"
                    + "<dl class='dl-horizontal'>"
                    + "<dt>预计进价</dt><dd class='text-left'>" + product.price + "</dd>"
                    + "<dt>订购数量</dt><dd class='text-left'>1</dd>"
                    + "<dt>预计总价</dt>"
                    + "<dd class='text-left'>" + product.price + "</dd>"
                    + "</dl>"
                    + "</td>"
                    + "<td>"
                    + "<dl class='dl-horizontal'>"
                    + "<dt>实际进价</dt>"
                    + "<dd class='text-left'>"
                    + "<input type='number' class='form-control text-center' style='width: 100px;' value='" + product.price + "' name='product_real_price' onkeyup='calcPrice(this)'>"
                    + "</dd>"
                    + "<dt>订购数量</dt>"
                    + "<dd class='text-left'>"
                    + "<input type='number' class='form-control text-center' style='width: 100px;' value='1' name='product_real_amount' onkeyup='calcPrice(this)'>"
                    + "</dd>"
                    + "<dt>小计</dt>"
                    + "<dd style='margin-left: 50px;'>" + product.price + "</dd>"
                    + "</dl>"
                    + "</td>"
                    + "<td>"
                    + supplier_html
                    + "</td>"
                    + "<td>未发货</td>"
                    + "<td>"
                    + "<button type='button' class='btn btn-primary' onclick='sendGoods(this)'>发货</button> "
                    + "<button type='button' class='btn btn-danger' onclick='removeDetail(this)'>删除</button>"
                    + "</td>"
                    + "</tr>";
            $('#purchaseTable tbody').prepend(html);
            $('#productWin').modal('hide');
          }
          layer.close(i);
        }
      });
    } else {
      layer.alert("请选择商品");
    }
  }

  $(function() {
    initSupplier();
    initProductSuggest();
    $('[data-toggle="tooltip"]').tooltip();

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

  function checkAll(obj) {
    $('table input[type=checkbox]').prop('checked', obj.checked);
  }

  function removeDetail(obj) {
      var detail_id = $(obj).parents('tr').data('detailid');
      var header_id = '${requestScope.header.purchase_header_id}';
      layer.confirm("删除前要保存当前内容吗？", {btn : ['删除并保存', '仅删除', '取消'],
        btn1 : function(ii) {
            //删除并保存
            $(obj).parents('tr').remove();

            var details = '';
            $('#purchaseTable tr[data-productid]').each(function(i, v) {
                if($(v).find('input').length > 0) {
                    var detail_id = $(v).data('detailid');
                    var product_id = $(v).data('productid');
                    var product_price = $(v).find('input[name=product_real_price]').val();
                    var product_amount = $(v).find('input[name=product_real_amount]').val();
                    var supplier_id = $(v).find('select[name=supplier_id]').val();
                    details += detail_id + ',' + product_id + ',' + product_price + ',' + product_amount + ',' + supplier_id + '|';
                }
            });
            if(details != '') {
                details = details.substring(0, details.length - 1);
            }
            var o = layer.load();
            $.post('${ctx}/purchase.do?method=saveDetail', {header_id : '${requestScope.header.purchase_header_id}', details : details, remove_id : detail_id}, function(data) {
                if(data && data == 'ok') {
                    layer.alert("操作成功！", function(data) {
                        location.reload();
                    });
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(o);
            });
        },
        btn2 : function(ii) {
            //仅删除
            if(detail_id) {
                var i = layer.load();
                $.post('${ctx}/purchase.do?method=removeDetail', {detail_id : detail_id, header_id : header_id}, function(data) {
                    layer.close(i);
                    layer.alert("操作成功！", function() {
                        location.reload();
                    });
                });
            } else {
                //该商品未保存到数据库，直接删除该tr即可
                $(obj).parents('tr').remove();
                layer.alert("操作成功！");
            }
        },
        btn3 : function(ii) {
            layer.close(ii);
      }});

  }

    function saveDetail() {
        layer.confirm("确定要保存吗？", function(index) {
            var details = '';
            $('#purchaseTable tr[data-productid]').each(function(i, v) {
                if($(v).find('input').length > 0) {
                    var detail_id = $(v).data('detailid');
                    var product_id = $(v).data('productid');
                    var product_price = $(v).find('input[name=product_real_price]').val();
                    var product_amount = $(v).find('input[name=product_real_amount]').val();
                    var supplier_id = $(v).find('select[name=supplier_id]').val();
                    details += detail_id + ',' + product_id + ',' + product_price + ',' + product_amount + ',' + supplier_id + '|';
                }
            });

            if(details != '') {
                details = details.substring(0, details.length - 1);
            }
            var o = layer.load();
            $.post('${ctx}/purchase.do?method=saveDetail', {header_id : '${requestScope.header.purchase_header_id}', details : details}, function(data) {
                if(data && data == 'ok') {
                    layer.alert("操作成功！", function(data) {
                        location.reload();
                    });
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(o);
            });
            layer.close(index);
        });
    }

</script>

</body>

</html>