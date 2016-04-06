<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.incomming.po.IncommingDetailBean" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<%
String goods_path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+goods_path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<script type="text/javascript" src="<%=basePath%>js/autocomplete/lib/jquery.js"></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/jquery.autocomplete.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/localdata.js'></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/autocomplete/lib/thickbox.css" />
<link type="text/css" href="<%=basePath%>js/artDialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/artDialog/artDialog.js"></script>

</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

 

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<input type="hidden" id="stock_type" value="<%=request.getParameter("stock_type") %>"/>

<div id="mright">
	<div id="mr_cnt">
		<%
		  Integer stock_type=Integer.parseInt(request.getParameter("stock_type"));
		if(stock_type==3){ 
		%>
					<div class="mrw_title">添加退货单明细</div>
		<%}else if(stock_type==4){ %>
		            <div class="mrw_title">添加损耗单明细</div>
		
		<%}else if(stock_type==5){ %>
		           <div class="mrw_title">添加调出单明细</div>
		<%} %>
					
					<div class="sform_wrap c">
					<ul>
					<li>商品名称：<input type="text" style="width:500px" name="product_name" id="product_name" value=""/>
					
					  </li>
								<li><a href="javascript:submitData()" class="btn btn_big btn_y">提交</a>
								</li>
							</ul>
						</div>
					<form id="searchForm">
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>商品名称</th>
								<th>条码</th>
								<th>参考售价</th>
								<th>本批次库存</th>
								<th>数量</th>
								<th></th>
							</tr>
						</table>
					</div>
					</form>
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">

	function log(event, data, formatted) {
		$("<li>").html( !data ? "No match!" : "Selected: " + formatted).appendTo("#result");
	}
	
          $().ready(function() {

	$("#product_name").autocomplete('${pageContext.request.contextPath }/outgoingOrder.do?method=queryGoods', {
		width: 300,
		multiple: false,
		matchContains: true,
		formatItem: function(row, i, max) {     //显示格式
                    return "<span style='width:100px'>编码："+row.product_id+"</span>&nbsp;&nbsp;<span style='width:300px'>名称：" + row.name + "</span>&nbsp;&nbsp;<span style='width:100px'>售价：" + row.price + "</span>"
                           + "</span>&nbsp;&nbsp;<span style='width:100px'>本批次库存：" + row.order_current_sum + "</span>";
                },
                formatMatch: function(row, i, max) {    //以什么数据作为搜索关键词,可包括中文,
                    return row.value;

                },
                formatResult: function(row) {      //返回结果
                    return row.value;
                },
		parse: function(data) {       
		                 
                    var rows = new Array();
                    var dataObj = eval("("+data+")");
                    var datas = dataObj.data;
                    for (var i = 0; i < datas.length; i++) {
                        rows[i] = { data:datas[i], value: datas[i].name, price: datas[i].price,result:datas[i].name,product_id:datas[i].product_id};
                    }
                    return rows;
                }
	}).result(function(event, data, formatted){
	    addGoods(data);
    });
	

});
	function addGoods(data){
	   var str='<tr>'
	           +'<input type="hidden" name="flag" value="2" /><input type="hidden" name="product_id" value="'+data.product_id+'" />'
			   +'<td>'+data.name+'</td>	'
			   +'<td>'+data.barcode+'</td>'	
			   +'<td>'+data.price+'</td>	'
			   +'<td>'+data.order_current_sum+'</td>	'
			   +'<td><input type="text" style="width:180px" onkeyup="checkCount('+data.order_current_sum+',this)" name="change_count" value="1"/></td>	'
			   +'<td>待添加</td>	'
			   +'<td><a onclick="del(this)" href="javascript:void(0)" class="btn btn_r">删除</a></td></tr>';
	   $(".table_t_t").append(str);
	   $("#product_name").val("");
	   $("#product_name").focus();
	}
	function checkCount(order_current_sum,obj){
	    if(parseInt($(obj).val())<0){
	        alert("不允许输入负数，请修改数量！");
	        $(obj).val("0");
	        return;
	    }
	    if(parseInt($(obj).val())>order_current_sum){
	        alert("不允许大于本批次库存，请修改数量！");
	        $(obj).val("0");
	        return;
	    }
	}
	function submitData(){
	   art.dialog('提交后不允许修改，请仔细查看是否确认提交！', 
          function () {
             postData();
          }, 
          function () {
          });
	   
	}
	function postData(){
	   var stock_type=$("#stock_type").val();
	   var products=[];
	   $.each($(".table_t_t tr:gt(0)"),function(index,item){
	       var obj={};
	       var flag=$(this).find('[name="flag"]').val();
	       if(flag=="2"){
	          obj.product_id=$(this).find('[name="product_id"]').val();
	          obj.change_count=$(this).find('[name="change_count"]').val();
	          products.push(obj);
	       }
	   });
	   
	   if(products.length>0){
	      $.ajax({
             type: 'POST',
             url: "${pageContext.request.contextPath }/outgoingOrder.do?method=add_incomming_detail",
             data:{products:JSON.stringify(products),stock_type:stock_type},
             success: function(data){
                   var dataObj = eval("("+data+")");
                   if(dataObj.result_code=="0"){
                       location.href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_head&stock_type="+stock_type;
                   }
             }});
	   }
	}
	function del(obj){
	   $(obj).parents("tr").remove();
	}

												
</script>	

</div>
</body>
</html>
