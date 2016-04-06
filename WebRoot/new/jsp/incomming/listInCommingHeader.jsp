<%@ page import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<%
    Integer stock_type = CommonUtil.safeToInteger(request.getAttribute("stock_type"), null);
    String stock_name = "";
    if(stock_type != null) {
        if(stock_type.intValue() == 1) {
            stock_name = "入库单";
        } else if(stock_type.intValue() == 2) {
            stock_name = "调入单";
        } else if(stock_type.intValue() == 3) {
            stock_name = "退货单";
        } else if(stock_type.intValue() == 4) {
            stock_name = "损耗单";
        } else if(stock_type.intValue() == 5) {
            stock_name = "调出单";
        }
    }
    request.setAttribute("stock_name", stock_name);
%>
<html>

<head>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>${stock_name}列表</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <input type="hidden" id="stock_type" value="${stock_type}">
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">生成时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_to"/>
                                </div>
                            </div>
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="search()">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default" onclick="addDetail()">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                    </div>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="detailWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">${stock_name}明细</h4>
            </div>
            <div class="modal-body">
                <div id="detailTable" class="dt-grid-container"></div>
                <div id="detailToolBar" class="dt-grid-toolbar-container"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id:'stockchange_header_no', title:'单据编号', type:'string', columnClass:'text-center'},
        {id:'create_by', title:'发起人', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'create_time', title:'发起时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record) {
            return '<button class="btn btn-primary" onclick="showDetailWin(' + record.stockchange_header_id + ')">明细</button>';
        }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/incommmingOrder.do?method=searchInCommingHeader',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function(){
        var stock_type = $('#stock_type').val();
        grid.parameters = new Object();
        grid.parameters['stock_type'] = stock_type;
        grid.load();

        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
    });

    //自定义查询
    function search(){
        var community_id = $('#community_id').val();
        var stock_type = $('#stock_type').val();
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        grid.parameters = new Object();
        grid.parameters['community_id'] = community_id;
        grid.parameters['stock_type'] = stock_type;
        grid.parameters['time_from'] = time_from;
        grid.parameters['time_to'] = time_to;
        grid.loadToFirst();
    }

    function showDetailWin(stockchange_header_id) {
        loadDetailTable(stockchange_header_id);
        $('#detailWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

/////////////////////////////////////////////////////////////////////
    var detailGridColumn = [
        {id:'product_name', title:'商品名称', type:'string', columnClass:'text-center'},
        {id:'barcode', title:'条码', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'price', title:'参考售价', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'change_count', title:'数量', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'unit_price', title:'进货单价', type:'string', columnClass:'text-center', hideType:'xs'}
    ];

    var detailGridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/incommmingOrder.do?method=searchInCommingDetail',
        columns : detailGridColumn,
        gridContainer : 'detailTable',
        toolbarContainer : 'detailToolBar',
        tools : ''
    };

    var detailGrid = $.fn.DtGrid.init(detailGridOption);

    function loadDetailTable(stockchange_header_id) {
        detailGrid.parameters = new Object();
        detailGrid.parameters['stockchange_header_id'] = stockchange_header_id;
        detailGrid.loadToFirst();
    }

    function addDetail() {
        <c:if test="${stock_type == 1}">
        parent.openTab('${ctx}/incommmingOrder.do?method=showAddRKDDetailPage', '添加${stock_name}明细');
        </c:if>
        <c:if test="${stock_type == 2}">
        parent.openTab('${ctx}/incommmingOrder.do?method=showAddDRDDetailPage', '添加${stock_name}明细');
        </c:if>
        <c:if test="${stock_type == 3}">
        parent.openTab('${ctx}/incommmingOrder.do?method=showAddTHDDetailPage', '添加${stock_name}明细');
        </c:if>
        <c:if test="${stock_type == 4}">
        parent.openTab('${ctx}/incommmingOrder.do?method=showAddSHDDetailPage', '添加${stock_name}明细');
        </c:if>
        <c:if test="${stock_type == 5}">
        parent.openTab('${ctx}/incommmingOrder.do?method=showAddDCDDetailPage', '添加${stock_name}明细');
        </c:if>

    }
</script>

</body>

</html>