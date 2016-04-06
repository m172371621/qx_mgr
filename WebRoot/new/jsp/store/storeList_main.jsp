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
                    <h5>门店查询(总部)</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="order_serial" class="col-sm-1 control-label text-right">状态</label>

                            <div class="col-sm-2">
                                <select class="form-control" id="cache_id">
                                    <option value="">全部</option>
                                    <option value="0">暂存</option>
                                    <option value="1">审核中</option>
                                    <option value="2">审核通过</option>
                                    <option value="3">审核驳回</option>

                                </select>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" id="selStore">搜索</button>
                            </div>
                        </div>
                    </form>
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
                    return "<b style='color: #e4c929;'>审核中</b>";
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
                if(record.cache_id == 1) {
                    return "<button class='btn' onclick='shenhe(" + record.obj_id +","+record.cache_id+")'>审核</button>";
                }
                return "";
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

    function shenhe(obj_id) {
       /* alert(obj_id);*/
        window.parent.openTab('${ctx}/store.do?method=storePage&type=1&obj_id='+obj_id, '门店信息审核');
    }
</script>
</html>
