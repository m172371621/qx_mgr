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
                    <h5>支付总表</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">统计日期</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from" value="${time_from}"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_to" value="${time_to}"/>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="query()">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
    });

    function query() {
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        if(time_from && time_to) {
            var str_url = "http://" + window.location.host + "/qxitreport/reportviewer?file=fee_sumery_sum.xml"
                    + "&time_from=" + $("#time_from").val()
                    + "&time_to=" + $("#time_to").val();
            parent.openTab(str_url, '支付总表');
        } else {
            layer.msg("请选择日期");
        }
    }
</script>

</body>

</html>