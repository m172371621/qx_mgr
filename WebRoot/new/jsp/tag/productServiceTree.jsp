<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    ul.ztree {
        margin-top: 10px;
        border: 1px solid #617775;
        background: #f0f6e4;
        height: 360px;
        overflow-y: scroll;
        overflow-x: auto;
    }
</style>

<input id="${productServiceTreeTag_id}_text" type="text" readonly value="" class="form-control" onclick="showMenu(); return false;"/>
<input id="${productServiceTreeTag_id}" name="${productServiceTreeTag_name}" type="hidden">
<div id="${productServiceTreeTag_id}_treeContent" style="display:none; position: absolute; z-index: 1;">
    <ul id="${productServiceTreeTag_id}_tree" class="ztree" style="margin-top:0;"></ul>
</div>

<script type="text/javascript">
    var setting = {
        view: {
            dblClickExpand: false,
            selectedMulti : false
        },
        data: {
            simpleData: {
                enable: true,
                rootPId: 'c0'
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick
        }
    };

    var zNodes = ${productServiceTreeTag_list};

    function beforeClick(treeId, treeNode) {
        var check = (treeNode && "cid" in treeNode);
        return check;
    }

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("${productServiceTreeTag_id}_tree"),
                nodes = zTree.getSelectedNodes(),
                text = ""
                value = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            text += nodes[i].name + ",";
            value += nodes[i].id.replace('s', '') + ',';
        }
        if (text.length > 0 ) text = text.substring(0, text.length-1);
        if (value.length > 0 ) value = value.substring(0, value.length-1);
        $("#${productServiceTreeTag_id}_text").attr("value", text);
        $("#${productServiceTreeTag_id}").attr("value", value);
        hideMenu();
        <c:if test="${!empty productServiceTreeTag_event}">
            eval('${communitySelectTag_event}');
        </c:if>
    }

    function showMenu() {
        //var treeObj = $("#${productServiceTreeTag_id}_text");
        //var treeOffset = $("#${productServiceTreeTag_id}_text").offset();
        $("#${productServiceTreeTag_id}_treeContent").slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#${productServiceTreeTag_id}_treeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "${productServiceTreeTag_id}_treeContent" || $(event.target).parents("#${productServiceTreeTag_id}_treeContent").length>0)) {
            hideMenu();
        }
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#${productServiceTreeTag_id}_tree"), setting, zNodes);

        //var zTree = $.fn.zTree.getZTreeObj("${productServiceTreeTag_id}_tree");
        //过滤权限外的门店商品类别

    });
</script>