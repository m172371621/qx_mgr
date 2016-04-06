<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>批次查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="product_name" class="col-sm-1 control-label text-right">商品名称</label>
                            <div class="col-sm-2">
                                <input type="text" id="product_name" class="form-control">
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
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id:'batch_serial', title:'批次号', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'product_name', title:'商品名', type:'string', columnClass:'text-center'},
        {id:'service_name', title:'商品分类', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'incommint_price', title:'进价', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'order_current_sum', title:'批次数量', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'create_time', title:'时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'}
    ];
    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/order.do?method=searchProductBatch',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };
    var grid = $.fn.DtGrid.init(gridOption);
    $(function(){
        grid.load();
    });

    //自定义查询
    function search(){
        var product_name = $('#product_name').val();
        var community_id = $('#community_id').val();
        grid.parameters = new Object();
        grid.parameters['product_name'] = product_name;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
    }
</script>

</body>

</html>