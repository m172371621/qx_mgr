<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <jsp:include page="../common/resource.jsp"/>
    <jsp:include page="../common/datetimepicker.jsp"/>
    
    <script type="text/javascript">
	
    	function addCard() {
    		var trNode = $("<tr><td><input name='order_price' id='order_price' style='text-align: center;' type='text'/></td><td><input name='card_num' id='card_num' style='text-align: center;' type='text'/></td><td><a class='btn btn_r' href='javascript:void(0)' onclick='dd(this)'>删除</a></td></tr>");
			$("#tbl tbody").append(trNode);	
        }
        
        function dd(e,objid) {
       		e.parentNode.parentNode.remove();  //清空当前行   
        	if(typeof(objid) != "undefined") {  
	        	$.post('${pageContext.request.contextPath}/card.do?method=delCardNum',{objid:objid},function(data) {
	            		if(null != data && data == 'ok') {
	                		alert("删除成功！");
		            		}
	            		else
	                		alert("删除失败！");
	            	});
        	}
			//另一种删除 传入参数必须为 event
        	//var target = e.target || e.srcElement;
        	//var aNode = target;
        	//var trNode = aNode.parentNode.parentNode;
        	//var tableNode = trNode.parentNode;
        	//tableNode.removeChild(trNode);
        }

        function saveRate() {
            var order_price = $("#order_price").val();
          	var card_num =  $("#card_num").val();
            if(null != order_price && ''!=order_price && null !=card_num && '' != card_num ) {
    			if(card_num >= 1) {
    				$("#rateForm").submit();
        		}else {
        			alert("必须大于0");
            	}
            }else {
           		alert("不能为空");
            }
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
            <div class="mrw_title">卡牌数量配置</div>
                <div class="sform_wrap c">
                    <ul>
                        <li>
                            <a href="javascript:addCard()" class="btn btn_big btn_y">新 增</a>
                            <a href="javascript:saveRate()" class="btn btn_big btn_y">保 存</a>
                        </li>
                    </ul>
                </div>
            <div id="order_wrap" class="p20">
                <form action="${pageContext.request.contextPath }/card.do?method=saveCardNum" method="post" id="rateForm">
                    <input type="hidden" name="community_id" value="${community_id}"/>
                    <table class="table_t_t" id="tbl">
	                        <tr>
	                            <th width="100px">订单总价</th><span>例子:</span>
	                            <th width="150px">卡牌数量</th>
	                            <th width="100px"></th>
	                        </tr>
	                        <c:forEach var="num" items="${list}">
	                        	<tr>
	                        		<td><input name="order_price" style="text-align: center;" type="text" id="order_price" value="${num.order_price }"/></td>
	                        		<td><input name="card_num" style="text-align: center;" type="text" id="card_num" value="${num.card_num }"/></td>
	                        		<td>
	                        			<a class="btn btn_r" href="javascript:void(0)" onclick="if(confirm('确定删除吗？')){ dd(this,${num.objid})}">删除</a>
	                        			<input type="hidden" name="objid" value="${num.objid }" />
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
