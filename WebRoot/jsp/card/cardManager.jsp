<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <jsp:include page="../common/resource.jsp"/>
    <jsp:include page="../common/datetimepicker.jsp"/>

    <script type="text/javascript">
        function saveRate() {
            //校验中奖率之和是否为100%
            var sum = 0;
            $('input[name=rate]').each(function(i, v) {
                sum += Number($(v).val());
            });
            if(sum != 100) {
                alert("中奖率之和不为100%");
                return;
            }
            if(confirm("确定要保存吗？")) {
                $('#rateForm').submit();
            }
        }

        /**
         * 设置未中奖概率
         * */
        function setUnWinning() {
            var winning = 0;    //中奖概率
            $('input[winning=true]').each(function(i, v) {
                winning += Number($(v).val());
            });
            $('input[winning=false]').val(100 - winning);
        }

        function query() {
            $('#searchForm').submit();
        }
    </script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div id="main" class="c">
    <jsp:include page="../common/menu.jsp"/>

    <input type="hidden" id="curr_li" value="order_20"/>

    <div id="mright">
        <div id="mr_cnt">
            <div class="mrw_title">卡牌中奖设置</div>
            <form action="${pageContext.request.contextPath }/card.do?method=cardManager" method="post" id="searchForm">
                <div class="sform_wrap c">
                    <ul>
                        <li>小区：
                            <select name="community_id" class="i_text">
                                <c:forEach items="${community_list}" var="community">
                                    <option value="${community.community_id}"
                                            <c:if test="${community_id == community.community_id}">selected</c:if> >${community.community_name}</option>
                                </c:forEach>
                            </select>
                        </li>
                        <li>
                            <a href="javascript:query()" class="btn btn_big btn_y">搜 索</a>
                            <a href="javascript:saveRate()" class="btn btn_big btn_y">保 存</a>
                        </li>
                    </ul>
                </div>
            </form>
            <div id="order_wrap" class="p20">
                <form action="${pageContext.request.contextPath }/card.do?method=saveRate" method="post" id="rateForm">
                    <input type="hidden" name="community_id" value="${community_id}"/>
                    <table class="table_t_t">
                        <tr>
                            <th width="100px">奖项</th>
                            <th width="150px">奖品</th>
                            <th width="100px">概率</th>
                            <th width="100px">剩余数量</th>
                        </tr>
                        <c:forEach items="${list}" var="card">
                            <tr>
                                <td>${card.card_name}</td>
                                <td><input type="text" name="award" value="${card.award}" class="i_text_1"/></td>
                                <td>
                                    <input type="hidden" name="level_id" value="${card.level_id}"/>
                                    <input type="text" name="rate" value="${card.rate}" class="i_text"
                                            winning="${card.probability != 1000000}" <c:if test="${card.probability == 1000000}">readonly</c:if> onblur="setUnWinning()"  /> %
                                </td>
                                <td>
                                    <input type="text" name="amount" value="${card.amount}" class="i_text" <c:if test="${card.probability == 1000000}">readonly</c:if>/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>

        </div>
    </div>
</div>
</body>
</html>
