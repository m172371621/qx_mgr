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

</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

 

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<input type="hidden" id="stockchangeHeaderId" value="<%=request.getAttribute("stockchangeHeaderId") %>"/>
<input type="hidden" id="batch_serial" value="<%=request.getAttribute("batch_serial") %>"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">进货单明细列表</div>
					<div class="sform_wrap c">
					<ul>
					<li>商品名称：<input type="text" style="width:180px" name="product_name" id="product_name" value=""/>
					
					  </li>
								<li><a href="javascript:submitData()" class="btn btn_big btn_y">提交</a>
								</li>
							</ul>
						</div>
						<%
						int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
						 %>
					<script type="text/javascript">
					
							function submitForm()
							{  
							    $('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/incommmingOrder.do?method=list_incomming_detail" method="post" id="searchForm">
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>商品名称</th>
								<th>条码</th>
								<th>参考售价</th>
								<th>数量</th>
								<th>进货单价</th>
								<th></th>
							</tr>
							<% List<IncommingDetailBean> list = (List<IncommingDetailBean>)request.getAttribute("list");
							   if(list!=null){
							   for(IncommingDetailBean detailbean: list) {
							%>
							<tr>
							    <input type="hidden" name="flag" value="1" />
								<td><%= JsonUtil.obj2Str(detailbean.getProduct_name()) %></td>							
								<td><%= JsonUtil.obj2Str(detailbean.getBarcode()) %></td>	
								<td><%= JsonUtil.obj2Str(detailbean.getPrice()) %></td>	
								<td><%= JsonUtil.obj2Str(detailbean.getChange_count()) %></td>
								<td><%= JsonUtil.obj2Str(detailbean.getUnit_price()) %></td>	
								<td>已添加</td>	
								<td><a onclick="delDetail(1,<%=detailbean.getStockchange_detail_id() %>,this)" href="javascript:void(0);" class="btn btn_r">删除</a></td>									
							</tr>
							<% }} %>
						</table>
					</div>
					</form>
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
var products=[];
	function log(event, data, formatted) {
		$("<li>").html( !data ? "No match!" : "Selected: " + formatted).appendTo("#result");
	}
	
          $().ready(function() {

	$("#product_name").autocomplete('${pageContext.request.contextPath }/incommmingOrder.do?method=queryGoods', {
		width: 300,
		multiple: false,
		matchContains: true,
		formatItem: function(row, i, max) {     //显示格式
                    return "<span style='width:140px'>" + row.name + "</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style='width:120px'>" + row.market_price + "</span>";
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
                        rows[i] = { data:datas[i], value: datas[i].name, market_price: datas[i].market_price,result:datas[i].name};
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
			   +'<td>'+data.market_price+'</td>	'
			   +'<td><input type="text" style="width:180px" name="change_count" value="1"/></td>	'
			   +'<td><input type="text" style="width:180px" name="unit_price" value="1"/></td>	'
			   +'<td>待添加</td>	'
			   +'<td><a onclick="delDetail(1,'+data.stockchange_detail_id+',this)" href="javascript:void(0);" class="btn btn_r">删除</a></td></tr>';
	   $(".table_t_t").append(str);
	}
	function submitData(){
	   $.each($(".table_t_t tr:gt(0)"),function(index,item){
	       var obj={};
	       var flag=$(this).find('[name="flag"]').val();
	       if(flag=="2"){
	          obj.product_id=$(this).find('[name="product_id"]').val();
	          obj.change_count=$(this).find('[name="change_count"]').val();
	          obj.unit_price=$(this).find('[name="unit_price"]').val();
	          products.push(obj);
	       }
	   });
	   
	   if(products.length>0){
	      var stockchangeHeaderId=$("#stockchangeHeaderId").val();
	      var batch_serial=$("#batch_serial").val();
	      $.ajax({
             type: 'POST',
             url: "${pageContext.request.contextPath }/incommmingOrder.do?method=modify_incomming_detail",
             data:{products:JSON.stringify(products),stockchangeHeaderId:stockchangeHeaderId,batch_serial:batch_serial},
             success: function(data){
                   var dataObj = eval("("+data+")");
                   if(dataObj.result_code=="0"){
                       location.href="${pageContext.request.contextPath }/incommmingOrder.do?method=list_incomming_head";
                   }
             }});
	   }
	}
	function delDetail(flag,stockchangeDetailId,obj){
	    debugger;
	    var stockchangeHeaderId=$("#stockchangeHeaderId").val();
	    var batch_serial=$("#batch_serial").val();
	    if(flag==1){
	        location.href="${pageContext.request.contextPath }/incommmingOrder.do?method=del_incomming_detail&stockchangeDetailId="+stockchangeDetailId+"&batch_serial="+batch_serial+"&stockchangeHeaderId="+stockchangeHeaderId;
	    }else{
	        $(obj).parents("tr").remove();
	    }
	}

												
</script>	

</div>
</body>
</html>
