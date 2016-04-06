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
                    <h5>微信推荐用户</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="phone" class="col-sm-1 control-label text-right">手机号</label>

                            <div class="col-sm-2">
                                <input type="text" id="phone" class="form-control">
                            </div>
                            <label for="name" class="col-sm-1 control-label text-right">用户名</label>

                            <div class="col-sm-2">
                                <input type="text" id="name" class="form-control">
                            </div>
                            <div class="col-sm-2">
                                <label>
                                    <input type="checkbox" id="my_recommend_code" value="1"> 推荐码不为空
                                </label>
                            </div>

                            <label for="state" class="col-sm-1 control-label text-right">状态</label>

                            <div class="col-sm-2">
                                <select id="state" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">未确认</option>
                                    <option value="2">已确认</option>
                                    <option value="3">驳回</option>
                                </select>
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="search()">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div id="weixinqxxTable" class="dt-grid-container"></div>
                    <div id="weixinqxxToolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id: 'name', title: '用户名', type: 'string', columnClass: 'text-center'},
        {id: 'phone', title: '手机号', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'addr', title: '住址', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'my_recommend_code', title: '我的推荐码', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'other_recommend_code', title: '他人的推荐码', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'recommend_amount', title: '以推荐数量', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'state',
            title: '状态',
            type: 'string',
            columnClass: 'text-center',
            hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                var text = "";
                if (record.state == 1) {
                    text = "未确认";
                }
                if (record.state == 2) {
                    text = "已确认";
                }
                if (record.state == 3) {
                    text = "驳回";
                }
                return text;
            }
        },
        {id: 'buy_count', title: '购买数量', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'img_url', title: '头像', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'nickname', title: '别名', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'create_time',
            title: '注册时间',
            type: 'date',
            format: 'yyyy-MM-dd hh:mm',
            columnClass: 'text-center',
            hideType: 'xs'
        },
        {
            id: 'operation', title: '操作', type: 'string', columnClass: 'text-center', hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                var tg = '<button class="btn btn-primary" onclick="update(' + '\'' + record.openid + '\'' + ',\'' + 2 + '\')">通过</button>';
                var bh = '<button class="btn btn-danger" onclick="update(' + '\'' + record.openid + '\'' + ',\'' + 3 + '\')">驳回</button>';
                if (record.state == 1) {
                    return tg + "&nbsp;&nbsp;" + bh;
                }
                if (record.state == 2) {
                    return '<p>&nbsp;</p>';
                }
                if (record.state == 3) {
                    return tg + "&nbsp;&nbsp;" + bh;
                }
            }
        }

    ];
    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/weixintuijianctrl.do?method=selweixintuijianList',
        columns: gridColumn,
        gridContainer: 'weixinqxxTable',
        toolbarContainer: 'weixinqxxToolBar',
        tools: '',
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function () {
        grid.load();
    });

    //自定义查询
    function search() {
        var name = $('#name').val();
        var phone = $('#phone').val();
        var state = $('#state').val();
        var my_recommend_code = "";
        var temp = $('#my_recommend_code').is(':checked');
        if(temp) {
            my_recommend_code= $('#my_recommend_code').val();
        }
        grid.parameters = new Object();
        grid.parameters['phone'] = phone;
        grid.parameters['name'] = name;
        grid.parameters['state'] = state;
        grid.parameters['my_recommend_code'] = my_recommend_code;
        grid.loadToFirst();
    }

    function update(openid, state) {
        var content = "";
        if (state == 2) content = "确定要通过吗？";
        if (state == 3) content = "确定要驳回吗？";
        if (layer.confirm(content, {icon: 0, title: '提示！'}, function (index) {
                    $.post('${ctx}/weixintuijianctrl.do?method=updateWeixintuijian', {
                        openid: openid,
                        state: state
                    }, function (data) {
                        if (data == 'OK') {
                            layer.msg("成功！");
                        }
                        grid.load();
                    })
                }));
    }
</script>

</body>

</html>