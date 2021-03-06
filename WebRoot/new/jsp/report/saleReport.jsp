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
                    <h5>销售日报表</h5>
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
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">所属服务</label>
                            <div class="col-sm-2">
                                <ui:simplePsTag id="sid" communitySelect="community_id" header="全部"/>
                            </div>
                            <label for="hasczpro" class="col-sm-1 control-label text-right">统计选项</label>
                            <div class="col-sm-2">
                                <select id="hasczpro" class="form-control">
                                    <option value="0">全部</option>
                                    <option value="1">不统计称重商品</option>
                                    <option value="2">只统计称重商品</option>
                                </select>
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
        var community_id = $('#community_id').val();
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        if(community_id && time_from && time_to) {
            var str_url = "http://" + window.location.host + "/qxitreport/reportviewer?file=date_sale_report.xml&community_id=" + $("#community_id").val()
                    + "&time_from=" + $("#time_from").val()
                    + "&time_to=" + $("#time_to").val();
            if ($("#sid").val() != "0") {
                str_url += "&sid=" + $("#sid").val();
            }
            if ($('#hasczpro').val() == 1) {
                str_url += "&noscpro=y";
            }
            if ($('#hasczpro').val() == 2) {
                str_url += "&onlyscpro=y";
            }
            parent.openTab(str_url, '销售日报表');
        } else {
            layer.msg("请选择门店和日期");
        }
    }
</script>

</body>

</html>