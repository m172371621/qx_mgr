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
                        <h5>用户区享卡查询</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="phone" class="form-control">
                                </div>
                                <label for="qxcard_no" class="col-sm-1 control-label text-right">卡号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="qxcard_no" class="form-control">
                                </div>
                                <label for="status" class="col-sm-1 control-label text-right">状态</label>
                                <div class="col-sm-2">
                                    <select class="form-control" id="status">
                                        <option value="">全部</option>
                                        <option value="3">正常</option>
                                        <option value="1">未制卡</option>
                                        <option value="2">未激活</option>
                                        <option value="4">已使用</option>
                                        <option value="5">已过期</option>
                                        <option value="6">已冻结</option>
                                        <option value="9">不可用</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">激活日期</label>
                                <div class="col-sm-4">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="form-control" id="time_from"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="form-control" id="time_to"/>
                                    </div>
                                </div>
                                <label class="col-sm-1 control-label text-right">门店</label>
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
        var qxcard_status = {3 : '正常', 4 :'已使用', 5 : '已过期', 6 : '已冻结', 9 : '不可用'};
        var disabled = {1 : '未冻结', 2 : "<font color='red'>已冻结</font>"};

        var gridColumn = [
            {id:'qxcard_no', title:'区享卡号', type:'string', columnClass:'text-center'},
            {id:'qxcard_value', title:'面值', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'qxcard_balance', title:'余额', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'qxcard_status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs', codeTable : qxcard_status},
            {id:'disabled', title:'是否冻结', type:'string', columnClass:'text-center', hideType:'xs', codeTable : disabled},
            {id:'createtime', title:'激活日期', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
            {id:'expire_time', title:'失效日期', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record) {
                if(record.disabled == 1) {
                    return "<button class='btn btn-primary' onclick='updateQxcardDisabled(\"" + record.qxcard_no + "\", \"" + record.community_id + "\", 2)'>冻结</button>";
                } else if(record.disabled == 2) {
                    return "<button class='btn btn-danger' onclick='updateQxcardDisabled(\"" + record.qxcard_no + "\", \"" + record.community_id + "\", 1)'>解冻</button>";
                }
            }}
        ];

        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/qxcard.do?method=searchUserQxcard',
            columns : gridColumn,
            gridContainer : 'table',
            toolbarContainer : 'toolBar',
            tools : ''
        };

        var grid = $.fn.DtGrid.init(gridOption);
        $(function(){
            grid.load();
            $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
        });

        //自定义查询
        function search(){
            var qxcard_no = $('#qxcard_no').val();
            var phone = $('#phone').val();
            var status = $('#status').val();
            var time_from = $('#time_from').val();
            var time_to = $('#time_to').val();
            var community_id = $('#community_id').val();
            grid.parameters = new Object();
            grid.parameters['qxcard_no'] = qxcard_no;
            grid.parameters['phone'] = phone;
            grid.parameters['status'] = status;
            grid.parameters['time_from'] = time_from;
            grid.parameters['time_to'] = time_to;
            grid.parameters['community_id'] = community_id;
            grid.loadToFirst();
        }

        function updateQxcardDisabled(qxcard_no, community_id, disabled) {
            layer.confirm("确定要" + (disabled == 1 ? "解冻" : "冻结") + "吗？", function() {
                var i = layer.load();
                $.post('${ctx}/qxcard.do?method=updateQxcardDisabled', {qxcard_no : qxcard_no, community_id : community_id, disabled : disabled}, function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功！");
                            grid.load();
                        } else {
                            layer.alert("操作失败！");
                        }
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