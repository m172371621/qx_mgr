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
                        <h5>中奖记录查询</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="phone" class="form-control">
                                </div>
                                <label for="card_name" class="col-sm-1 control-label text-right">奖项</label>
                                <div class="col-sm-2">
                                    <select id="card_name" class="form-control">
                                        <option value="">全部</option>
                                        <option value="一等奖">一等奖</option>
                                        <option value="二等奖">二等奖</option>
                                        <option value="三等奖">三等奖</option>
                                    </select>
                                </div>
                                <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                                <div class="col-sm-2">
                                    <ui:simpleCommunitySelect id="community_id" header="全部"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">中奖时间</label>
                                <div class="col-sm-4">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="form-control" id="start_time"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="form-control" id="end_time"/>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <button class="btn btn-primary" type="button" id="custom_search_2_2_2" onclick="search()">查询</button>
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
        var awardStatus = { 1 : "<b class='text-danger'>未领取</b>", 2 : "已领取"};

        var gridColumn = [
            {id:'phone', title:'手机号', type:'string', columnClass:'text-center'},
            {id:'card_name', title:'中奖类型', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'createTime', title:'中奖时间', type:'date', format : 'yyyy-MM-dd hh:mm:ss', columnClass:'text-center', hideType:'xs'},
            {id:'awardStatus', title:'是否领取', type:'string', columnClass:'text-center', hideType:'xs', codeTable : awardStatus},
            {id:'awardTime', title:'领取时间', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operatorName', title:'操作人员', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs',
                resolution:function(value, record, column, grid, dataNo, columnNo){
                    var content = "";
                    if(record.awardStatus == 1) {
                        content = "<button class='btn btn-primary' onclick='receiveAward(\"" + record.order_id + "\",\"" + record.userid + "\")'>领取</button>";
                    }
                    return content;
            }}

        ];
        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/card.do?method=listAwardLog',
            columns : gridColumn,
            gridContainer : 'table',
            toolbarContainer : 'toolBar',
            tools : ''
        };
        var grid = $.fn.DtGrid.init(gridOption);
        $(function(){
            $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
            grid.load();
        });

        //自定义查询
        function search(){
            var community_id = $('#community_id').val();
            var phone = $('#phone').val();
            var card_name = $('#card_name').val();
            var start_time = $('#start_time').val();
            var end_time = $('#end_time').val();
            grid.parameters = new Object();
            grid.parameters['community_id'] = community_id;
            grid.parameters['phone'] = phone;
            grid.parameters['card_name'] = card_name;
            grid.parameters['start_time'] = start_time;
            grid.parameters['end_time'] = end_time;
            grid.loadToFirst();
        }

        function receiveAward(orderID,userid){
            layer.confirm("确定要领取吗？", function() {
                var i = layer.load();
                $.ajax({
                    type:"POST",
                    url: "${ctx}/order.do?method=awardUpdate",
                    data:{loginName:'${sessionScope.user_info.loginname}',orderID:orderID,userid:userid},
                    success: function(data){
                        if(data != null && data != '') {
                            layer.alert("领取成功！");
                            grid.load();
                        } else {
                            layer.alert("领取失败！");
                        }
                        layer.close(i);
                    }
                });
            });
        }
    </script>

</body>

</html>