<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<select id="${simplePsTag_id}" name="${simplePsTag_name}" class="${simplePsTag_css}" onchange="${simplePsTag_event}">
    <c:if test="${!empty simplePsTag_header}">
        <option value='${simplePsTag_headerValue}'>${simplePsTag_header}</option>
    </c:if>
</select>

<script type="text/javascript">
    function initSimplePs_${simplePsTag_id} (cid) {
        var community_id = $('#${simplePsTag_communitySelect}').val();
        if(cid) {
            community_id = cid;
        }
        if(community_id) {
            var i = layer.load();
            $.post('${pageContext.request.contextPath}/product.do?method=simpleProductServiceOptions', {community_id : community_id}, function (data) {
                var html = "";
                <c:if test="${!empty simplePsTag_header}">
                    html = "<option value='${simplePsTag_headerValue}'>${simplePsTag_header}</option>";
                </c:if>
                if(data) {
                    html += data;
                }

                $('#${simplePsTag_id}').html(html);
                <c:if test="${!empty simplePsTag_value}">
                    $('#${simplePsTag_id}').val(${simplePsTag_value});
                </c:if>
                layer.close(i);
            });
        } else {
            var html = "";
            <c:if test="${!empty simplePsTag_header}">
                html = "<option value='${simplePsTag_headerValue}'>${simplePsTag_header}</option>";
            </c:if>
            $('#${simplePsTag_id}').html(html);
        }
    }

    function loadSimplePs_${simplePsTag_id} (community_id, value) {
        if(community_id) {
            var i = layer.load();
            $.post('${pageContext.request.contextPath}/product.do?method=simpleProductServiceOptions', {community_id : community_id}, function (data) {
                var html = "";
                <c:if test="${!empty simplePsTag_header}">
                    html = "<option value='${simplePsTag_headerValue}'>${simplePsTag_header}</option>";
                </c:if>
                if(data) {
                    html += data;
                }

                $('#${simplePsTag_id}').html(html);
                <c:if test="${!empty simplePsTag_value}">
                    $('#${simplePsTag_id}').val(${simplePsTag_value});
                </c:if>
                layer.close(i);
            });
        }
    }

    $(function() {
        <c:if test="${!empty simplePsTag_communitySelect}">
            var community_id = $('#${simplePsTag_communitySelect}').val();
            if($("#${simplePsTag_communitySelect} option[value!='']").length == 1) {
                //当门店下拉框只有一个的时候，商品类别默认初始化该门店的，无需选择该门店在进行初始化
                community_id = $("#${simplePsTag_communitySelect} option[value!='']").eq(0).val();
            }

            if(community_id) {
                initSimplePs_${simplePsTag_id}(community_id);
            }
            $("#${simplePsTag_communitySelect}").on("change",function(){
                initSimplePs_${simplePsTag_id}();
            });
        </c:if>
    });
</script>
