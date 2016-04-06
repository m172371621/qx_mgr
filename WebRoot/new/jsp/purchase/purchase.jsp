<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        dl {
            margin-top: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="margin-right:13px;">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>商品进货</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-12">
                            <ol class="breadcrumb">
                                <li>
                                    <a href="${ctx}/purchase.do?method=showPurchasePage">所有分类</a>
                                </li>
                                <c:if test="${!empty parent_service}">
                                    <li>${parent_service.service_name}</li>
                                </c:if>
                                <c:if test="${!empty service}">
                                    <li><strong>${service.service_name}</strong></li>
                                </c:if>
                                <li>共${product_count}件商品</li>
                            </ol>
                        </div>
                    </div>

                    <label style="margin-top: 10px;">相关分类</label>
                    <ul class="nav nav-pills" style="border: 1px solid #e8e8e8;">
                        <c:forEach items="${serviceList}" var="s">
                            <li role="presentation"><a href="javascript:reloadPage('${s.service_id}')">${s.service_name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">

                    <div class="row">
                        <div class="col-sm-7">
                            <div class="btn-group">
                                <%--<button type="button" class="btn btn-outline btn-default" onclick="sort('amountBtn')" id="amountBtn">
                                    销量<li class="fa fa-sort-desc"></li>
                                </button>--%>
                                <button type="button" class="btn btn-outline btn-default" onclick="sort('priceBtn')" id="priceBtn">
                                    价格<li class="fa"></li>
                                </button>
                            </div>
                        </div>
                        <div class="col-sm-4 col-sm-offset-1">
                            <div class="input-group">
                                <input type="hidden" id="colnum" value="${colnum}"/>
                                <input type="hidden" id="rownum" value="${rownum}"/>
                                <input type="hidden" id="service_id" value="${service.service_id}"/>
                                <input type="text" placeholder="商品名" class="input-sm form-control" id="keyword">
                                <span class="input-group-btn">
                                  <button type="button" class="btn btn-sm btn-primary" onclick="search()"><i
                                          class="fa fa-search"></i></button>
                                  <button type="button" class="btn btn-sm btn-outline btn-default" onclick="gotoCart()">进入购物车
                                      <span class="label label-warning" id="cart_num">${cart_count}</span>
                                  </button>
                                </span>
                            </div>
                        </div>
                    </div>

                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>

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

    function reloadPage(service_id) {
        location.href = '${ctx}/purchase.do?method=showPurchasePage&service_id=' + service_id;
    }

    function sort(btnid) {
        var li = $('#' + btnid + " li");
        var css = li.attr('class');
        if (css.indexOf("desc") < 0 && css.indexOf("asc") < 0) {
            //初始状态，点击一次变成asc
            li.addClass("fa-sort-asc");
        } else {
            li.toggleClass('fa-sort-desc').toggleClass('fa-sort-asc');
        }
        search();
    }

    function showForm(obj) {
        $('#queryForm').slideToggle(200);
        $(obj).find("span").toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"),
                setTimeout(function () {
                    $('.ibox-content').resize();
                }, 50);
    }

    /**
     * 解析商品信息
     * */
     function parseProduct(record, columnNo) {
        var product = record['pro_' + columnNo];
        if(product) {
            var content = "<img src='" + product.thumbnail + "' style='max-width: 100px;max-height: 100px;'/>"
                    + "<dl class='dl-horizontal'>"
                    + "<dt>商品名称</dt>" + "<dd class='text-left'>" + (product.name ? product.name : "") + "</dd>"
                    + "<dt>单位</dt>" + "<dd class='text-left'>" + (product.unit ? product.unit : "") + "</dd>"
                    + "<dt>规格</dt>" + "<dd class='text-left'>" + (product.spec ? product.spec : "") + "</dd>"
                    + "<dt>预期进价</dt>" + "<dd class='text-left'>" + (product.price ? product.price : "") + "</dd>"
                    + "<dt>指导售价</dt>" + "<dd class='text-left'>" + (product.market_price ? product.market_price : "") + "</dd>"
                    + "<dt>产地</dt>" + "<dd class='text-left'>" + (product.place ? product.place : "") + "</dd>"
                    + "<dt>预期利润率</dt>" + "<dd class='text-left'>" + (product.yqlrl ? product.yqlrl : "") + "</dd>"
                    + "<dt>近一个月销量</dt>" + "<dd class='text-left'>" + (product.jygyxl ? product.jygyxl : "") + "</dd>"
                    + "</dl>"
                    + "<button class='btn btn-primary' onclick='addCart(\"" + product.product_id + "\")'>加入购物车</button>";
            return content;
        } else {
            return "";
        }
    }

    var gridColumn = [
    <c:forEach var="num" begin="0" end="${colnum - 1}" varStatus="status">
        {
            id: 'pro_${num}', title: '商品${num}', type: 'string', columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseProduct(record, columnNo);
            }
        }
        <c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ];
    var gridOption = {
        showHeader: false,
        ajaxLoad: true,
        loadURL: '${ctx}/purchase.do?method=listProduct',
        columns: gridColumn,
        gridContainer: 'table',
        toolbarContainer: 'toolBar',
        tools: '',
        tableClass: 'table table-responsive',
        pageSize: '${colnum}' * '${rownum}',   //每页显示'${rownum}'*'${num}'个商品
        pageSizeLimit: ''
    };
    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        loadTable();

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

    function getOrderBy() {
        //var amount_class = $('#amountBtn li').attr('class');
        var price_class = $('#priceBtn li').attr('class');
        var orderby = '';
        /*if(amount_class) {
            orderby += amount_class.indexOf('desc') > 0 ? 'amount desc' : 'amount asc';
            orderby += ',';
        }*/
        if(price_class.indexOf('fa-') > 0) {
            orderby = price_class.indexOf('desc') > 0 ? 't1.price desc' : 't1.price asc';
        }
        if(orderby.indexOf(',') > 0) {
            orderby = orderby.substr(0, orderby.length - 1);
        }
        return orderby;
    }

    //自定义查询
    function search() {
        var colnum = $('#colnum').val();
        var rownum = $('#rownum').val();
        var orderby = getOrderBy();
        var keyword = $('#keyword').val();
        var service_id = $('#service_id').val();
        grid.parameters = new Object();
        grid.parameters['colnum'] = colnum;
        grid.parameters['rownum'] = rownum;
        grid.parameters['orderby'] = orderby;
        grid.parameters['keyword'] = keyword;
        grid.parameters['service_id'] = service_id;
        grid.loadToFirst();
    }

    function loadTable() {
        var colnum = $('#colnum').val();
        var rownum = $('#rownum').val();
        var orderby = getOrderBy();
        var service_id = $('#service_id').val();
        grid.parameters = new Object();
        grid.parameters['colnum'] = colnum;
        grid.parameters['rownum'] = rownum;
        grid.parameters['orderby'] = orderby;
        grid.parameters['service_id'] = service_id;
        grid.load();
    }


    /**
     * 将商品加入购物车
     * */
    function addCart(product_id) {
        var i = layer.load();
        $.post('${ctx}/purchase.do?method=addCartInfo', {product_id : product_id}, function(data) {
            if(data != null) {
                $('#cart_num').text(data);
                layer.msg("加入购物车成功！");
            } else {
                layer.alert("加入购物车失败！");
            }
            layer.close(i);
        });
    }

    function gotoCart() {
        if(window.top == window.self){
            window.open("${ctx}/purchase.do?method=listCart");
        } else {
            window.parent.openTab("${ctx}/purchase.do?method=listCart", '购物车');
        }
    }

    /**
     * 获取并显示购物车数量
     * */
    function getCartCount() {
        var i = layer.load();
        $.post('${ctx}/purchase.do?method=getCartCount', {}, function(data) {
            if(data != null) {
                $('#cart_num').text(data);
            }
            layer.close(i);
        });
    }
</script>

</body>

</html>