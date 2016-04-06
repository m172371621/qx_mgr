<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="new/css/formValidation.css"/>
    <link rel="stylesheet" type="text/css" href="new/css/default.css">
    <link href="new/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="new/css/plugins/steps/jquery.steps.css" rel="stylesheet">
    <link href="new/css/animate.min.css" rel="stylesheet">
    <link href="new/css/style.min.css?v=3.2.0" rel="stylesheet">
    <link href="new/css/cxSelect.css"/>
    <style type="text/css">
        .wizard > .content > .body {
            position: relative;
        }

        .wizard > .actions {
            text-align: center;
        }

        .cache {
            text_algin: center;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <c:choose>
                        <c:when test="${objid==0}">
                            <h5>门店更新</h5>
                        </c:when>
                        <c:when test="${objid==1}">
                            <h5>门店查看(审核中)</h5>
                        </c:when>
                        <c:when test="${objid==2}">
                            <h5>门店查看(审核通过)</h5>
                        </c:when>
                        <c:when test="${objid==3}">
                            <h5></h5>
                        </c:when>
                        <c:when test="${objid==4}">
                            <h5>门店审核</h5>
                        </c:when>
                        <c:otherwise>
                            <h5>门店新增</h5>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="ibox-content">
                    <form id="form" method="post" class="form-horizontal m-t"
                          data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                        <input type="hidden" value="${obj }"/>
                        <fieldset>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">经营组织 * ：</label>

                                <div class="col-sm-3">
                                    <select class="form-control" name="country" id="country">
                                        <c:forEach items="${user_community}" var="list">
                                            <option value="${list.community_id}">${list.community_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">门店名称* ：</label>

                                <div class="col-sm-3">
                                    <input id="storename" name="storename" class="form-control" type="text"
                                           value="${store.storename }" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">地区 * ：</label>

                                <div class="col-sm-3 " id="dised">
                                    <p id="city_china_val">
                                        <select class="form-control province" name="province" data-value="${province }"
                                                data-first-title="选择省" disabled="disabled"
                                                data-rule="required"></select>
                                        <select class="form-control city" name="city" data-value="${city }"
                                                data-first-title="选择市" disabled="disabled"
                                                data-rule="required"></select>
                                        <select class="form-control area" name="area" data-value="${area }"
                                                data-first-title="选择地区" disabled="disabled"
                                                data-rule="required"></select>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">门店位置 * ：</label>

                                <div class="col-sm-3">
                                    <input id="address" name="address" class="form-control" type="text"
                                           value="${address }" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">定位坐标 * ：</label>

                                <div class="col-sm-3">
                                    <input id="coordinate" name="coordinate" class="form-control " type="text"
                                           value="${store.coordinate}" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">服务电话* ：</label>

                                <div class="col-sm-3">
                                    <input id="phone" name="phone" class="form-control " type="text"
                                           value="${store.phone}" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">是否送货上门 * ：</label>

                                <div class="col-sm-3">
                                    <label class="radio-inline"> <input type="radio" name="isdoor" id="inlineRadio1"
                                                                        <c:if test="${store.isdoor==1}">checked="checked"</c:if>
                                                                        checked="checked" value="1"> 是 </label> <label
                                        class="radio-inline"> <input type="radio" name="isdoor" id="inlineRadio2"
                                                                     <c:if test="${store.isdoor==0}">checked="checked"</c:if>
                                                                     value="0"> 否 </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">送货半径 * （公里）：</label>

                                <div class="col-sm-3">
                                    <input id="delivery_range" name="delivery_range" class="form-control " type="text"
                                           value="${store.delivery_range}" data-rule="required;integer[+]">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">送货费用 * ：</label>

                                <div class="col-sm-3">
                                    <input id="delivery_price" name="delivery_price" class="form-control " type="text"
                                           value="0" data-rule="required;integer[+]">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">上门条件 * （购物满多少元）：</label>

                                <div class="col-sm-3">
                                    <input id="door_howmuch" name="door_howmuch" class="form-control " type="text"
                                           value="30" data-rule="required;integer[+]">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">上门时间 * ：</label>

                                <div class="col-lg-1">
                                    <select class="form-control" name="updoortime" id="updoortime">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9" selected="selected">9</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                        <option value="13">13</option>
                                        <option value="14">14</option>
                                        <option value="15">15</option>
                                        <option value="16">16</option>
                                        <option value="17">17</option>
                                        <option value="18">18</option>
                                        <option value="19">19</option>
                                        <option value="20">20</option>
                                        <option value="21">21</option>
                                        <option value="22">22</option>
                                        <option value="23">23</option>
                                    </select>
                                </div>
                                <div class="col-lg-1">
                                    <select class="form-control" name="updoortime" id="updoortime2">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                        <option value="13">13</option>
                                        <option value="14">14</option>
                                        <option value="15">15</option>
                                        <option value="16">16</option>
                                        <option value="17">17</option>
                                        <option value="18">18</option>
                                        <option value="19">19</option>
                                        <option value="20">20</option>
                                        <option value="21" selected="selected">21</option>
                                        <option value="22">22</option>
                                        <option value="23">23</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">营业时间 * ：</label>

                                <div class="col-lg-1">
                                    <select class="form-control" name="worktime" id="worktime">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9" selected="selected">9</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                        <option value="13">13</option>
                                        <option value="14">14</option>
                                        <option value="15">15</option>
                                        <option value="16">16</option>
                                        <option value="17">17</option>
                                        <option value="18">18</option>
                                        <option value="19">19</option>
                                        <option value="20">20</option>
                                        <option value="21">21</option>
                                        <option value="22">22</option>
                                        <option value="23">23</option>
                                    </select>
                                </div>
                                <div class="col-lg-1">
                                    <select class="form-control" name="worktime" id="worktime2">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                        <option value="13">13</option>
                                        <option value="14">14</option>
                                        <option value="15">15</option>
                                        <option value="16">16</option>
                                        <option value="17">17</option>
                                        <option value="18">18</option>
                                        <option value="19">19</option>
                                        <option value="20">20</option>
                                        <option value="21" selected="selected">21</option>
                                        <option value="22">22</option>
                                        <option value="23">23</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">是否首单减免 * ：</label>

                                <div class="col-sm-3">
                                    <label class="radio-inline"> <input type="radio" name="isfirst_manjan"
                                                                        <c:if test="${store.isfirst_manjan==1}">checked="checked"</c:if>
                                                                        id="inlineRadio1" checked="checked" value="1"> 是
                                    </label> <label
                                        class="radio-inline"> <input type="radio" name="isfirst_manjan"
                                                                     <c:if test="${store.isfirst_manjan==0}">checked="checked"</c:if>
                                                                     id="inlineRadio2" value="0"> 否 </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">首单减免额度 * ：</label>

                                <div class="col-sm-3">
                                    <input id="manjian_price" name="manjian_price" class="form-control " type="text"
                                           value="0" data-rule="required;integer[+]">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">服负责人 * ：</label>

                                <div class="col-sm-3">
                                    <input id="personincharge" name="personincharge" class="form-control " type="text"
                                           value="${store.personincharge }" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">负责人电话 * ：</label>

                                <div class="col-sm-3">
                                    <input id="personincharge_phone" name="personincharge_phone" class="form-control "
                                           type="text" value="${store.personincharge_phone }" data-rule="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <c:choose>
                                    <c:when test="${objid == 0}">
                                        <button class="btn btn-primary col-sm-1 col-md-offset-5" type="button" id="tj"
                                                onclick="tijiao(${obj})">提交门店
                                        </button>
                                        <button id="update" class="btn btn-primary col-sm-1 col-md-offset-1"
                                                type="button">更新
                                        </button>
                                        <input type="hidden" id="obj_id" name="obj_id" value="${obj}"/>
                                    </c:when>
                                    <c:when test="${objid == 1}">
                                    </c:when>
                                    <c:when test="${objid == 2}">
                                        <button id="update" class="btn btn-primary col-sm-1 col-md-offset-5"
                                                type="button">更新
                                        </button>
                                    </c:when>
                                    <c:when test="${objid == 3}">
                                    </c:when>
                                    <c:when test="${objid == 4}">
                                        <button id="yes" class="btn btn-primary col-sm-1 col-md-offset-5"
                                                <c:if test="${cache_id != 1}">disabled</c:if>
                                                type="button" value="${obj}" >审核通过
                                        </button>
                                        <button id="no" class="btn btn-danger col-sm-1 col-md-offset-1"
                                                type="button" value="${obj}" >审核不通过
                                        </button>
                                        <input type="hidden" id="obj_id" name="obj_id" value="${obj}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-primary col-sm-1 col-md-offset-5" type="button"
                                                id="main1"
                                                onclick="save(1)">生成门店
                                        </button>
                                        <button class="btn btn-default col-sm-1 col-md-offset-1" type="button"
                                                id="main2"
                                                onclick="save(0)">暂存
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </fieldset>
                    </form>
                    <div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- cxSelect  -->


<script>
    $(function () {

        $.cxSelect.defaults.url = 'new/js/cxSelect/cityData.min.json';

        $('#city_china').cxSelect({
            selects: ['province', 'city', 'area']
        });

        $('#city_china_val').cxSelect({
            selects: ['province', 'city', 'area'],
            nodata: 'none'
        });

        $('#global_location').cxSelect({
            url: 'new/js/cxSelect/globalData.min.json',
            selects: ['country', 'state', 'city', 'region'],
            nodata: 'none'
        });

        disable();
        if (${!empty store.updoortime}) {
            var temp = "${store.updoortime}";
            var v1 = temp.split("_");
            $("#updoortime").val(v1[0]);
            $("#updoortime2").val(v1[1]);

        }
        if (${ !empty store.worktime}) {
            var temp2 = "${store.worktime}";
            var v2 = temp2.split("_");
            $("#worktime").val(v2[0]);
            $("#worktime2").val(v2[1]);
        }

        $("#update").on('click', function () {
            var obj_id = $("#obj_id").val();
            var store = $('#form').serialize();
            store = decodeURIComponent(store, true);
            alert("更新门店...")
            return;
            $.post('${ctx}/store.do?method=updateStore', store, function (data) {
                if (data == 'ok') {
                    layer.msg("更新成功！");
                } else {
                    layer.msg("更新失败！")
                }
            });
        });

        $("#yes").on('click', function () {
            alert("yes");
            var obj_id = $("#yes").val();
            $.post('${ctx}/store.do?method=addCommunity_base', {obj_id: obj_id}, function (data) {
                alert(data);
            })
        });

        $("#no").on('click', function () {
            alert("no");
            var objid = $("#no").val();
            layer.prompt({
                formType: 2,
                value: '',
                title: '请输入原因 :'
            }, function (value, index, elem) {
                alert(value); //得到value
                $.post('${ctx}/store.do?method=updateStoreQuestion', {objid: objid, value: value}, function (data) {
                    if (data == 'ok') {
                        $("#yes").hide();
                        $("#no").attr('class', "col-md-offset-5 btn btn-danger disabled");
                        layer.msg("提交成功！");
                    } else {
                        layer.msg("提交失败！");
                    }
                });
                layer.close(index);
            });
        });
    });

    function disable() {
        if (${cache_id==1}) {
            $("input").attr("disabled", true);
            $("select").attr("disabled", true);
            $("#update").hide();
            $("province").attr("disabled", true);
        }

        if (${cache_id==2}) {
            $("#address").attr("disabled", true);
            $("#coordinate").attr("disabled", true);
            $("#storename").attr("disabled", true);
            $("#city_china_val>select").attr("disabled", true);
            $("#dised").attr("disabled", true);
        }
    }

    function save(cache_id) {
        debugger;
        if ($('#form').isValid()) {
            var store = $('#form').serialize();
            store = decodeURIComponent(store, true);
            var index = layer.load(0, {//0.1透明度的白色背景
                shade: [0.5, '#ccc', {time: 5 * 1000}]
            });
            $.ajax({
                type: "post",
                url: ctx + "/store.do?method=saveCache_Store&cache_id=" + cache_id,
                data: store,
                success: function (data) {
                    $("#main1").hide();
                    $("#main2").hide();
                    layer.close(index);
                    if(cache_id == 1) {
                        layer.msg("门店生成成功！");
                    }else {
                        layer.msg("已暂存！");
                    }
                },
                error: function () {
                    layer.close(index);
                    layer.msg("门店生成失败！");
                }
            })
        }
    }

    function tijiao(obj_id) {
        alert(obj_id);
        $.post('${ctx}/store.do?method=tijiao', {obj_id: obj_id}, function (data) {
            if (data == 'ok') {
                layer.msg("提交成功！");
            } else {
                layer.msg("提交失败！");
            }
        })
    }
</script>
</body>
</html>