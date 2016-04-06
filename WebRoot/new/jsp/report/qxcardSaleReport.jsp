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
                    <h5>售卡统计表</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">开卡日期</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from" value="${time_from}"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_to" value="${time_to}"/>
                                </div>
                            </div>
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
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
        if(time_from && time_to) {
            if(!community_id) {
                //选择了全部门店
                $('#community_id option').each(function(i, v) {
                    if(i > 0) {
                        community_id += $(v).val() + ',';
                    }
                });
                if(community_id && community_id.endsWith(",")) {
                    community_id = community_id.substring(0, community_id.length - 1);
                }
            }
            var str_url = "http://" + window.location.host + "/qxitreport/reportviewer?file=qxcard_sumeric.xml&community_id=" + community_id
                    + "&time_from=" + $("#time_from").val()
                    + "&time_to=" + $("#time_to").val();
            parent.openTab(str_url, '售卡统计表');
        } else {
            layer.msg("请选择日期");
        }
    }
</script>

</body>

</html>