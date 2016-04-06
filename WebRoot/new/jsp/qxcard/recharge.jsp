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
                        <h5>区享卡充值</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal" id="czForm">
                            <div class="form-group">
                                <label for="community_id" class="col-md-1 control-label text-right col-md-offset-3">门店</label>
                                <div class="col-md-3">
                                    <ui:simpleCommunitySelect id="community_id" name="community_id" event="initRechargeCardValue()"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-md-1 control-label text-right col-md-offset-3">手机号</label>
                                <div class="col-md-3">
                                    <input type="text" id="phone" name="phone" class="form-control" placeholder="请输入手机号"
                                           data-rule="手机号:required;remote(${ctx}/qxcard.do?method=checkUser)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label text-right col-md-offset-3">充值金额</label>
                                <div class="col-md-2">
                                    <select class="form-control" id="facevalue" name="facevalue" onchange="calcPayPrce()">
                                    </select>
                                </div>
                                <div class="col-md-1">
                                    <input type="text" id="amount" name="amount" class="form-control text-center" placeholder="数量"
                                           data-rule="数量:required;integer(+)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="money" class="col-md-1 control-label text-right col-md-offset-3">应付金额</label>
                                <div class="col-md-3">
                                    <p class="form-control-static" id="money"></p>
                                    <input type="hidden" name="pay_price" id="pay_price">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-3 col-md-offset-4">
                                    <c:if test="${alipay}">
                                        <button class="btn btn-primary" type="button" id="czbtn" disabled onclick="rechargeByAlipay()">充值</button>
                                    </c:if>
                                    <c:if test="${!alipay}">
                                        <button class="btn btn-primary" type="button" id="czbtn" disabled onclick="recharge()">充值</button>
                                    </c:if>
                                </div>
                            </div>
                        </form>

                        <div class="row" id="msg">
                            <div class="col-sm-5 col-sm-offset-3">

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var objid;  //上一笔充值的记录id

        function checkAmount() {
            var flag = true;
            var community_id = $('#community_id').val();
            var phone = $('#phone').val();
            var facevalue = $('#facevalue').val();
            var amount = $('#amount').val();

            if(community_id && facevalue && amount) {
                $.ajax({
                    type : 'post',
                    url : '${pageContext.request.contextPath }/qxcard.do?method=checkRule',
                    async : false,
                    data : {community_id : community_id, card_value : facevalue, amount : amount},
                    success : function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json.result != 'ok') {
                                flag = false;
                                var html = "<div class='alert alert-danger alert-dismissable'>"
                                        + "<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button>"
                                        + json.msg + "</div>";
                                $('#msg div').html(html);
                            } else {
                                $('#msg div').html('');
                            }
                        }
                    }
                });
            }

            if(flag && phone && facevalue && amount && facevalue == 118) {
                $.ajax({
                    type : 'POST',
                    url : '${pageContext.request.contextPath }/qxcard.do?method=getCzAmountByCardValue',
                    async : false,  //设置成同步的方式
                    cache : false,
                    data : {phone : phone, facevalue : facevalue},
                    success : function(data) {
                        if((Number(data) + Number(amount)) > 2) {
                            var html = "<div class='alert alert-danger alert-dismissable'>"
                                    + "<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button>"
                                    + "118元区享卡每人至多充值 2 张！（该用户已经充值" + Number(data) + "张）</div>";
                            $('#msg div').html(html);
                            //$('#czbtn').attr('disabled', true);
                            flag = false;
                        } else {
                            $('#msg div').html("");
                        }
                    }
                });
            }

            if(flag) {
                $('#msg div').html("");
            }
            return flag;
        }


        $("#czForm").on("validation", function(e, current) {
            if(this.isValid) {
                checkAmount();
                $('#czbtn').attr('disabled', false);
            } else {
                $('#money').text('');
                $('#pay_price').val('');
                $('#czbtn').attr('disabled', true);
            }
        });

        function calcPayPrce() {
            checkAmount();

            var community_id = $('#community_id').val();
            var facevalue = $('#facevalue').val();
            var amount = $('#amount').val();
            var money = mul(facevalue, amount);
            if(money > 0) {
                var i = layer.load(0, {shade: [0.5, 'gray']})
                $.post('${ctx}/qxcard.do?method=getRestPrice', {community_id : community_id, card_value : facevalue, amount : amount}, function (data) {
                    if (data != null && data != '') {
                        var pay_price = sub(money, data);
                        $('#money').html("<strong class='text-danger'>" + pay_price + "</strong> 元（节省" + data + "元）");
                        $('#pay_price').val(pay_price);
                        layer.close(i);
                    }
                });
            }
        }

        //数量在验证通过的时候计算应付金额
        $('#amount').on('valid.field', function(e, result) {
            calcPayPrce();
        });

        function recharge() {
            var phone = $('#phone').val();
            var facevalue = $('#facevalue').val();
            var amount = $('#amount').val();
            var money = facevalue * amount;

            if(checkAmount()) {
                layer.confirm("确定要充值<strong class='text-danger'>" + money + "</strong>元吗?", function(index) {
                    var i = layer.load(0, {shade: [0.5, 'gray']});
                    $.post('${pageContext.request.contextPath }/qxcard.do?method=rechargeQxcard', $('#czForm').serialize(), function (data) {
                        if (data != null && data != '') {
                            clearForm();
                            data = eval('(' + data + ')');
                            layer.open({
                                title: "充值成功",
                                content: "<table class='table'><thead><tr><th>手机</th><td>" + data.phone + "</td></tr></thead><tbody><tr><th>卡号</th><td>" + data.cardno + "</td></tr><tr><th>余额</th><td>" + data.value + "</td></tr></tbody></table>"
                            });
                        } else {
                            layer.alert("充值失败！");
                        }
                        layer.close(i);
                    });
                });
            }
        }

        function clearForm() {
            $('#phone').val('');
            $('#amount').val('');
            $('#money').text('');
            $('#pay_price').val('');
            $('#czForm').data('validator').cleanUp();
        }

        function rechargeByAlipay() {
            var phone = $('#phone').val();
            var facevalue = $('#facevalue').val();
            var amount = $('#amount').val();
            var money = facevalue * amount;

            if(checkAmount()) {
                layer.confirm("确定要充值<strong class='text-danger'>" + money + "</strong>元吗?", function(index) {
                    var i = layer.load(0, {shade: [0.5, 'gray']});
                    $.ajax({
                        type : 'post',
                        url : '${pageContext.request.contextPath }/qxcard.do?method=rechargeQxcardByAlipay',
                        data : $('#czForm').serialize(),
                        async : false,
                        success : function(data) {
                            if (data != null && data != '') {
                                var json = eval('(' + data + ')');
                                if(json) {
                                    clearForm();
                                    objid = json.objid;
                                    layer.alert("支付宝页面已在新窗口打开，请在新窗口中进行支付！", {btn: ['已支付','关闭']}, function(index) {
                                        $.post('${pageContext.request.contextPath }/qxcard.do?method=getQxcardCzLogById', {objid : objid}, function(data) {
                                            if(data) {
                                                var json = eval('(' + data + ')');
                                                if(json && json.status == 1) {
                                                    layer.open({
                                                        title: "充值成功",
                                                        content: "<table class='table'><thead><tr><th>手机</th><td>" + json.phone + "</td></tr></thead><tbody><tr><th>卡号</th><td>" + json.card_no + "</td></tr><tr><th>余额</th><td>" + json.card_value + "</td></tr></tbody></table>"
                                                    });
                                                } else {
                                                    layer.alert("充值失败！");
                                                }
                                            }
                                        });
                                    });
                                    window.open(json.url);
                                }
                            } else {
                                layer.alert("充值失败！");
                            }
                        },
                        complete : function() {
                            layer.close(i);
                        }
                    });
                });
            }
        }

       //获取该门店的充值面值
       function initRechargeCardValue() {
           var community_id = $('#community_id').val();
           if(community_id) {
               var i = layer.load();
               $.post('${ctx}/qxcard.do?method=findRechargeCardValue', {community_id : community_id}, function(data) {
                   var html = "";
                   if(data) {
                       var json = eval('(' + data + ')');
                       if(json) {
                           $.each(json, function(index, v) {
                              html += "<option value='" + v.card_value + "'>" + v.card_value + "</option>";
                           });
                       }
                   }
                   $('#facevalue').html(html);
                   layer.close(i);
               });
           } else {
               $('#facevalue').html('');
           }
       }

        $(function() {
            initRechargeCardValue();
        });
    </script>

</body>

</html>