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
                        <h5>广告管理</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="ad_name" class="col-sm-1 control-label text-right">名称</label>
                                <div class="col-sm-2">
                                    <input type="text" id="ad_name" class="form-control">
                                </div>
                                <label for="service_type" class="col-sm-1 control-label text-right">类型</label>
                                <div class="col-sm-2">
                                    <select id="service_type" class="form-control">
                                        <option value="">全部</option>
                                        <option value="11">商品广告</option>
                                        <option value="12">订单广告</option>
                                    </select>
                                </div>
                                <label class="col-sm-1 control-label text-right">门店</label>
                                <div class="col-sm-2">
                                    <ui:simpleCommunitySelect id="community_id" header="全部"/>
                                </div>
                                <div class="col-sm-2">
                                    <button class="btn btn-primary" type="button" id="queryBtn" onclick="search()">查询</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline btn-default" onclick="addAd()">
                                <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="removeAdBatch()">
                                <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                            </button>
                        </div>
                        <div id="table" class="dt-grid-container"></div>
                        <div id="toolBar" class="dt-grid-toolbar-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var ad_type = {11 : '商品广告', 12 : '订单广告'};

        var gridColumn = [
            {id:'ad_name', title:'名称', type:'string', columnClass:'text-center'},
            {id:'service_type', title:'类型', type:'string', columnClass:'text-center', hideType:'xs', codeTable : ad_type},
            {id:'ad_dec', title:'描述', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'ad_order', title:'顺序', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs',
                resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '<button class="btn btn-primary" onclick="editAd(\''+ record.ad_id + '\');">编辑</button>'
                                + ' '
                    + '<button class="btn btn-danger" onclick="removeAd(\'' + record.ad_id + '\');">删除</button>';
                return content;
            }}

        ];
        var gridOption = {
            check : true,
            ajaxLoad : true,
            loadURL : '${ctx}/ad.do?method=listAdV4',
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
            var ad_name = $('#ad_name').val();
            var service_type = $('#service_type').val();
            var community_id = $('#community_id').val();
            grid.parameters = new Object();
            grid.parameters['ad_name'] = ad_name;
            grid.parameters['service_type'] = service_type;
            grid.parameters['community_id'] = community_id;
            grid.loadToFirst();
        }

        function editAd(ad_id) {
            window.parent.openTab('${ctx}/ad.do?method=showAdV4&ad_id=' + ad_id, '编辑广告');
        }

        function addAd() {
            window.parent.openTab('${ctx}/ad.do?method=showAdV4', '新增广告');
        }

        function removeAd(ad_id) {
            layer.confirm("确定要删除该广告吗？", function(i) {
                $.post('${ctx}/ad.do?method=removeAd', {ad_id : ad_id}, function(data) {
                    if(data != null && data == 'ok') {
                        layer.alert("删除成功！")
                        grid.load();
                    } else {
                        layer.alert("删除失败！");
                    }
                });
            });
        }

        function removeAdBatch() {
            var recodes = grid.getCheckedRecords();
            if(recodes.length == 0) {
                layer.alert("请先选择需要删除的广告！");
            } else {
                var ids = '';
                $.each(recodes, function(i, v) {
                    ids += v.ad_id + ",";
                });
                if(ids != '') {
                    ids = ids.substr(0, ids.length - 1);
                }
                layer.confirm("确定要删除吗？", function() {
                    var o = layer.load();
                    $.post('${ctx}/ad.do?method=removeAdBatch', {ids : ids}, function(data) {
                        if(data != null && data == 'ok') {
                            layer.alert("删除成功！");
                            grid.load();
                        } else {
                            layer.alert("删除失败！");
                        }
                        layer.close(o);
                    });
                });
            }
        }
    </script>

</body>

</html>