<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.exedit-3.5.js"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>经营组织维护</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-5">
                            <ul id="org_info" class="ztree"></ul>
                        </div>
                        <div class="col-sm-7">
                            <form role="form" class="form-horizontal" id="userForm">
                                <input type="hidden" id="community_id" name="community_id">

                                <div class="form-group">
                                    <label for="communityName" class="col-sm-2 control-label text-right">名称</label>

                                    <div class="col-sm-4">
                                        <input type="text" id="communityName" name="communityName" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="communityAddr" class="col-sm-2 control-label text-right">地址</label>

                                    <div class="col-sm-4">
                                        <input type="text" id="communityAddr" name="communityAddr" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="org_info_person" class="col-sm-2 control-label text-right">负责人</label>

                                    <div class="col-sm-4">
                                        <input type="text" id="org_info_person" name="org_info_person"
                                               class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="org_info_phone" class="col-sm-2 control-label text-right">号码</label>

                                    <div class="col-sm-4">
                                        <input type="text" id="org_info_phone" name="org_info_phone"
                                               class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label text-right">组织类型</label>

                                    <div class="col-sm-4">
                                        <input id="org_info_type" name="org_info_type" class="form-control"
                                               disabled="disabled"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-2 col-sm-offset-3">
                                        <button class="btn btn-primary" onclick="javascript:updateDataA();"
                                                type="button">
                                            保存
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增经营组织</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="org_info_pid" name="org_info_pid"
                           class="form-control">

                    <div class="form-group">
                        <label for="loginname_edit" class="col-md-4 control-label text-right">名称：</label>

                        <div class="col-md-5">
                            <input type="text" id="community_name1" name="community_name1"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cname_edit" class="col-md-4 control-label text-right">地址：</label>

                        <div class="col-md-5">
                            <input type="text" id="community_addr1" name="community_addr1"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cname_edit" class="col-md-4 control-label text-right">负责人：</label>

                        <div class="col-md-5">
                            <input type="text" id="org_info_person1" name="org_info_person"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cname_edit" class="col-md-4 control-label text-right">号码：</label>

                        <div class="col-md-5">
                            <input type="text" id="org_info_phone1" name="org_info_phone"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cid_after_edit" class="col-md-4 control-label text-right">组织类型：</label>

                        <div class="col-md-5">
                            <select id="org_info_type1" class="form-control">
                                <option value="2">个体户</option>
                                <option value="3">公司</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addOrg()">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var setting = { // 树1
        view: {
            showTitle: true,
            removeHoverDom: null,
            expandSpeed: 200, // 展开树的时间
            selectedMulti: false
            // 是否允许多选
        },
        edit: {
            enable: true, // 节点是否可编辑
            editNameSelectAll: true,
            showRemoveBtn: false,// 添加删除按钮
            showRenameBtn: false, // 不允许重命名
            drag: {
                isCopy: false,
                isMove: true,
                autoExpandTrigger: true,
                prev: true,// 允许向上拖动
                next: true,// 不允许向下拖动
                inner: true
                // 允许当前层次内进行拖动
            }
        },
        data: {
            key: {
                name: "text"
            },
            simpleData: {
                enable: true,
                idKey: "id", // id编号命名
                pIdKey: "parent", // 父id编号命名
                rootId: 0
            }
        },
        callback: {
            beforeDrag: null,
            beforeRemove: null,
            beforeDragOpen: null,
            onDrag: null,
            onDrop: zTreeOnDrop,
            onCheck: null,
            onClick: f_onDblClick,
            onRightClick: zTreeOnRightClick,
            onExpand: null
        },
        keep: {
            parent: true
        }
    };
    function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
        //alert(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ));
        updateData(treeNodes[0].id, targetNode.id);
    }
    ;
    function zTreeOnRightClick(event, treeId, treeNode) {
        showMod(treeNode.id);
        //alert(treeNode ? treeNode.tId + ", " + treeNode.id : "isRoot");
    }
    ;
    function getData() {
        var zNodes;
        $.ajax({
            type: "post",
            url: ctx + "/communityCtrl.do?method=getOrgList",
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                zNodes = eval('(' + data + ')');
                var z_data = zNodes.data;
                $.fn.zTree.init($("#org_info"), setting, z_data);
            },
            error: function (msg) {
                alert("error");
            }
        });
    }
    function updateData(id, pid) {
        var retObj = {};
        retObj.community_id = id;
        retObj.org_info_pid = pid;
        var retStr = JSON.stringify(retObj);
        $.ajax({
            type: "post",
            url: ctx + "/communityCtrl.do?method=updateCommunity",
            data: {retObj: retStr},
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
            },
            error: function (msg) {
                alert("error");
            }
        });
    }

    function updateDataA() {
        var retObj = {};
        retObj.community_id = $("#community_id").val();
        retObj.org_info_person = $("#org_info_person").val();
        retObj.org_info_phone = $("#org_info_phone").val();
        retObj.org_info_location = $("#org_info_location").val();
        retObj.community_name = $("#communityName").val();
        retObj.community_addr = $("#communityAddr").val();
        var retStr = JSON.stringify(retObj);
        $.ajax({
            type: "post",
            url: ctx + "/communityCtrl.do?method=updateCommunity",
            data: {retObj: retStr},
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                var jsonObj = eval('(' + data + ')');
                //getDetail(retObj.community_id);
                alert(jsonObj.result_dec);
            },
            error: function (msg) {
                alert("error");
            }
        });
    }
    function f_onDblClick(event, treeId, treeNode) {
        var id = treeNode.id;
        $("#community_id").val(id);
        getDetail(id);

    }
    function getDetail(id) {
        $.ajax({
            type: "post",
            url: ctx + "/communityCtrl.do?method=getOrgDetail",
            data: {community_id: id},
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                var jsonObj = eval('(' + data + ')');
                var json = jsonObj.data;
                $("#org_info_person").val(json.org_info_person);
                $("#org_info_phone").val(json.org_info_phone);
                var org_info_type_name = "";
                switch (json.org_info_type) {
                    case 1:
                        org_info_type_name = "总部";
                        break;
                    case 2:
                        org_info_type_name = "个体户";
                        break;
                    case 3:
                        org_info_type_name = "公司";
                        break;
                    case 4:
                        org_info_type_name = "门店";
                        break;
                    default:
                        org_info_type_name = "其他";

                }
                $("#org_info_type").val(org_info_type_name);
                $("#org_info_location").val(json.org_info_location);
                $("#communityName").val(json.community_name);
                $("#communityAddr").val(json.community_addr);

            },
            error: function (msg) {
                alert("error");
            }
        });
    }
    function showMod(id) {
        $("#org_info_type1").val("");
        $("#org_info_location1").val("");
        $("#community_name1").val("");
        $("#org_info_person1").val("");
        $("#community_addr1").val("");
        $("#org_info_phone1").val("");
        $("#org_info_pid").val(id);
        $('#editWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }
    function addOrg(id) {
        var retObj = {};
        retObj.org_info_type = $("#org_info_type1").val();
        retObj.org_info_location = $("#org_info_location1").val();
        retObj.community_name = $("#community_name1").val();
        retObj.org_info_person = $("#org_info_person1").val();
        retObj.community_addr = $("#community_addr1").val();
        retObj.org_info_phone = $("#org_info_phone1").val();
        retObj.org_info_pid = $("#org_info_pid").val();
        var retStr = JSON.stringify(retObj);
        $.ajax({
            type: "post",
            url: ctx + "/communityCtrl.do?method=insertCommunity",
            data: {retObj: retStr},
            ContentType: "application/json; charset=utf-8",
            success: function (data) {
                getData();
                $('#editWin').modal('hide');
            },
            error: function (msg) {
                alert("error");
            }
        });
    }


    $(document).ready(function () {
        getData();
    });
</script>
</body>
</html>