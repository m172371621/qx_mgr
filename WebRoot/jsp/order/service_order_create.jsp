<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='start_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='end_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">增加服务订单</div>
					<form action="${pageContext.request.contextPath }/serviceorder.do?method=createServiceOrder" method="post" id="searchForm">
					<div class="sform_wrap c">
					      <input type="hidden" id="product_id" name="product_id"/>
					       <ul>
								<li>手机号码：<input type="text" name="phone"/></li>
								<li style="display:none">所属服务：<select name="sel_service_id"  id="sel_service_id" class="i_text">
									<%
									   List<ServiceVo> slist = (List<ServiceVo>)request.getAttribute("qxit_service");
									   System.out.println(slist);
									   if(slist!=null){
									   for(ServiceVo s : slist){
									   System.out.println(s.getService_id());
									%>
									<option value="<%= s.getService_id()%>"> <%= s.getService_name() %></option>
									<% }} %>
								</select>
								</li>
								<li>服务项：<select name="sel_product_id"  id="sel_product_id" class="i_text"></select></li>
								<li>服务总价：<input type="text" name="product_price"/></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">提交订单</a></li>
							</ul>
						</div>
				  </form>
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
   function submitForm(){
      $("#product_id").val($("#sel_product_id").val());
      $("#searchForm").submit();
   }
    $(function(){
       $("#sel_service_id").get(0).selectedIndex = 0;
       $("#sel_service_id").change(function(){
           getProductList($(this).val());
       
       });
       $("#sel_service_id").trigger("change");
		});
    function getProductList(serviceId){
        $.ajax({
                  type: 'POST',
                  url: "${pageContext.request.contextPath }/serviceorder.do?method=getProductList",
                  data:{serviceId:serviceId},
                  success: function(data){
                      var dataObj = eval("("+data+")");
                      var selObj=$("#sel_product_id");
                      if(dataObj.result_code=="0"){
                           var data=dataObj.data;
                           $.each(data,function(index,item){
                               selObj.append('<option value="'+item.product_id+'">'+item.name+'</option>');
                           });
                      }
                 }
              });
    }

</script>

</div>
</body>
</html>
