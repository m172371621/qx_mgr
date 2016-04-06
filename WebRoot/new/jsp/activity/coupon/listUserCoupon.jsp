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
                    <h5>用户红包查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="loginname" class="col-sm-1 control-label text-right">用户名</label>
                            <div class="col-sm-2">
                                <input id="loginname" type="text" class="form-control">
                            </div>
                            <label for="status" class="col-sm-1 control-label text-right">状态</label>
                            <div class="col-sm-2">
                                <qx:getEnumList clazz="com.brilliantreform.sc.activity.enumerate.UserCouponStatusEnum" enumList="statusList"/>
                                <select id="status" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${statusList}" var="status">
                                        <option value="${status.value}">${status.name}</option>
                                    </c:forEach>
                                </select>
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
    var userCouponStatus = {1 : '未使用', 2 : '已过期', 3 : '已使用'};

    var gridColumn = [
        {id:'loginname', title:'用户名', type:'string', columnClass:'text-center'},
        {id:'use_price', title:'红包', type:'string', columnClass:'text-center', resolution : function(value, record) {
            return "满 <b class='text-danger'>" + record.use_price + "</b> 减 <b class='text-danger'>" + record.off_price + "</b>";
        }},
        {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs', codeTable : userCouponStatus},
        {id:'createtime', title:'领取时间', type:'date', format : 'yyyy-MM-dd hh:mm:ss' ,columnClass:'text-center', hideType:'xs'},
        {id:'expiretime', title:'过期时间', type:'date', format : 'yyyy-MM-dd hh:mm:ss' ,columnClass:'text-center', hideType:'xs'}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/coupon.do?method=searchUserCoupon',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function(){
        grid.load();
    });

    function search() {
        var loginname = $('#loginname').val();
        var status = $('#status').val();

        grid.parameters = new Object();
        grid.parameters['loginname'] = loginname;
        grid.parameters['status'] = status;

        grid.loadToFirst();
    }

</script>

</body>

</html>