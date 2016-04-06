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
                        <h5>服务信息配置</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="service_name" class="col-sm-1 control-label text-right">名称</label>
                                <div class="col-sm-2">
                                    <input type="text" id="service_name" class="form-control">
                                </div>
                                <label for="status" class="col-sm-1 control-label text-right">可见性</label>
                                <div class="col-sm-2">
                                    <select id="status" class="form-control">
                                        <option value="">全部</option>
                                        <option value="0">隐藏</option>
                                        <option value="1">可见</option>
                                    </select>
                                </div>
                                <label class="col-sm-1 control-label text-right">门店</label>
                                <div class="col-sm-2">
                                    <ui:simpleCommunitySelect id="community_id" header="全部"/>
                                </div>
                                <div class="col-sm-3">
                                    <button class="btn btn-primary" type="button" onclick="search()">查询</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline btn-default" onclick="addService()">
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

    <script type="text/javascript">
        var service_status = { 0 : '隐藏', 1 : "<font class='text-danger'>可见</font>"};

        var gridColumn = [
            {id:'service_dec', title:'内容', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'service_name', title:'名称', type:'string', columnClass:'text-center'},
            {id:'service_order', title:'顺序', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'status', title:'可见性', type:'string', columnClass:'text-center', hideType:'xs', codeTable : service_status},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                content += '<button class="btn btn-primary" onclick="editService(\'' + record.service_id + '\')">修改</button>';
                content += '  ';
                content += '<button class="btn btn-danger" onclick="removeService(\'' + record.service_id + '\')">删除</button>';
                return content;
            }}

        ];
        var gridOption = {
            check : false,
            ajaxLoad : true,
            loadURL : '${ctx}/service.do?method=searchService',
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
            var service_name = $('#service_name').val();
            var community_id = $('#community_id').val();
            var status = $('#status').val();
            grid.parameters = new Object();
            grid.parameters['service_name'] = service_name;
            grid.parameters['community_id'] = community_id;
            grid.parameters['status'] = status;
            grid.loadToFirst();
        }

        function removeService(service_id) {
            layer.confirm("确定要删除该服务吗？", function() {
                var i = layer.load();
                $.post('${ctx}/service.do?method=removeService', {service_id : service_id}, function(data) {
                    if(data != null && data == 'ok') {
                        layer.alert("删除成功！")
                        grid.load();
                    } else {
                        layer.alert("删除失败！");
                    }
                    layer.close(i);
                });
            });
        }

        function editService(service_id) {
            window.parent.openTab("${ctx}/service.do?method=loadEditPageV4&service_id=" + service_id, "修改服务信息");
        }

        function addService() {
            window.parent.openTab("${ctx}/service.do?method=loadEditPageV4", "添加服务信息");
        }
    </script>

</body>

</html>