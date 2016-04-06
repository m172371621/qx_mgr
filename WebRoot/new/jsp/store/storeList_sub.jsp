<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>门店查询</h5>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div id="storeTable" class="dt-grid-container"></div>
                    <div id="storeToolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var gridColumn = [
        {id: 'storename', title: '门店名称', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'address', title: '门店位置', type: 'string', columnClass: 'text-center'},
        {
            id: 'coordinate', title: '定位坐标', type: 'date', columnClass: 'text-center', type: 'string'
        },
        {id: 'password', title: '归属单位', type: 'string', columnClass: 'text-center'},
        {id: 'phone', title: '服务电话', type: 'string', columnClass: 'text-center'},
        {id: 'personincharge', title: '负责人', type: 'string', columnClass: 'text-center'},
        {id: 'personincharge_phone', title: '联系方式', type: 'string', columnClass: 'text-center'},
        {
            id: 'cache_id',
            title: '状态',
            type: 'string',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                if (record.cache_id == 0)
                    return "<b style='color: #cccccc);'>暂存</b>";
                if (record.cache_id == 1)
                    return "<b style='color: #e4c929;'>审核中...</b>";
                if (record.cache_id == 2)
                    return "<b style='color: #0be400;'>审核通过</b>";
                if (record.cache_id == 3)
                    return "<b style='color: #e41610;'>审核驳回</b>";
            }
        },
        {
            id: '',
            title: '操作',
            type: 'String',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                if (record.cache_id == 0)
                    return "<button class='btn btn-primary' onclick='shenhe("+record.obj_id+","+record.cache_id+")'>完善信息</button>";
                if (record.cache_id == 1)
                    return "<button class='btn btn-success' onclick='shenhe("+record.obj_id+","+record.cache_id+")'>查看</button>";
                if (record.cache_id == 2)
                    return "<button class='btn btn-success' onclick='shenhe("+record.obj_id+","+record.cache_id+")'>修改信息</button>";
                if (record.cache_id == 3)
                    return "<button class='btn btn-danger' onclick='shenhe("+record.obj_id+","+record.cache_id+")'>查看原因</button>";
            }
        }
    ];

    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/store.do?method=storeList',
        columns: gridColumn,
        gridContainer: 'storeTable',
        toolbarContainer: 'storeToolBar',
        tools: ''
    };

    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        grid.load();
        $("#selStore").click(search);
        $("#cache_id").change(search);
    });

    function search() {
        var cache_id = $('#cache_id').val();
        grid.parameters = new Object();
        grid.parameters['cache_id'] = cache_id;
        grid.refresh(true);
    }

    function shenhe(obj_id,cache_id) {
        if (cache_id == 0) {//暂存
            /*alert('暂存'+obj_id);*/
            window.parent.openTab('${ctx}/store.do?method=storePage&cache_id=0&obj_id='+obj_id, '完善门店信息');
        }
        if (cache_id == 1) {//审核中
            /*alert('审核中'+obj_id);*/
            window.parent.openTab('${ctx}/store.do?method=storePage&cache_id=1&obj_id='+obj_id, '查看门店信息(审核中...)');
        }
        if (cache_id == 2) {//审核通过
            /*alert('审核通过'+obj_id);*/
            window.parent.openTab('${ctx}/store.do?method=storePage&cache_id=2&obj_id='+obj_id, '查看门店信息(审核通过)');
        }
        if (cache_id == 3) {//审核驳回
            $.post('${ctx}/store.do?method=seachStore', {obj_id:obj_id,cache_id:cache_id}, function(data){
                layer.open({
                    type: 1,
                    title:'原因 :',
                    area: ['400px', '300px'],
                    content: data //注意，如果str是object，那么需要字符拼接。
                });
            });
        }
    }
</script>
</html>