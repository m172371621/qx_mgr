<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.distri.po.DistriOrderBean" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<style type="text/css">
td 
{
	padding:5px 0;
}

</style>
</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='time_from1']").datepicker({
        dateFormat: "yy-mm-dd"
    });
});
</script>

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">区享卡操作列表</div>
					<div class="sform_wrap c">
							<ul>
								<li>生成日期：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" id="time_from1" /></li>
                                
								<% if(request.getAttribute("view_status").equals("1")){%>
								<li>订单：<select name="ordertop" size="1" id="ordertop">
								                    <option value="10">前10</option>
                                                    <option value="20">前20</option>
                                                    <option value="50">前50</option>
                                                    <option value="100">前100</option>
                                </select></li>
								
								<li><a href="javascript:submitForm(2)" class="btn btn_big btn_y">生成配送清单</a></li>
								<%}%>
								<li><a href="javascript:optStatus(1);" class="btn btn_big btn_y">设置配送</a></li>
								<li><a href="javascript:exportExcel()" class="btn btn_big btn_y">导出清单</a></li>
								<% if(!request.getAttribute("view_status").equals("1")){%>
								<li><a href="javascript:optStatus(2);" class="btn btn_big btn_y">设置完成</a></li>
								<li><a href="javascript:optStatus(3)" class="btn btn_big btn_y">暂时删除</a></li>
								<%}%>
							</ul>
							
						
						</div>
					<script type="text/javascript">
							function submitForm(type)
							{  
							    $("#input_ordertop").val($("#ordertop").val());
							    
								$('#pageIndex').val(1);
								$("#type").val(type);
								$("#time").val($("#time_from1").val());
								$('#searchForm').submit();
							}						
					</script>	
					<% 
					int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
					%>
					<form action="${pageContext.request.contextPath }/distri.do?method=list_distri_detail" method="post" id="searchForm">
				      <input type="hidden" id="view_status" name="view_status" value="<%=request.getAttribute("view_status") %>"/>
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
<input type="hidden" id="distri_num" name="distri_num" value="<%=request.getAttribute("distri_num") %>"/>
<input type="hidden" id="type" name="type" value="<%=request.getAttribute("type") %>"/>
<input type="hidden" id="input_ordertop" name="input_ordertop" value="10"/>
<input type="hidden" id="time" name="time_from1" value="10"/>
<input type="hidden" id="distri_worker_id" name="distri_worker_id" value="<%=request.getAttribute("distri_worker_id")==null?0:request.getAttribute("distri_worker_id") %>"/>
					
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t" align=center>
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
							   <th><input type="checkbox" name="header" onclick="checkChange();"/></th>
							    <th>用户信息</th>
								<th>收款信息</th>
								<th>状态</th>
								<th>商品信息</th>
							</tr>
							<% 
							    
							   List<DistriOrderBean> list = (List<DistriOrderBean>)request.getAttribute("list");
							   if(list!=null){
							   for(DistriOrderBean distribean: list){
							       List<DistriOrderBean> detaillist=distribean.getDetailList();
							       for(int j=0;j<detaillist.size();j++) {
							           List<DistriOrderBean> sublist=detaillist.get(j).getSubList();
							           if(j==0){
							           %>
							           <tr>
							           <td rowspan="<%=distribean.getCount() %>" style="padding:5px 0;"><input type="checkbox"/>
							                        <input type="hidden" name="phone" value="<%=JsonUtil.obj2Str(distribean.getPhone()) %>"/></td>
							                     <td rowspan="<%=distribean.getCount() %>"  style="padding:5px 0;"><%= "昵称："+JsonUtil.obj2Str(distribean.getNickName())+"<br/>"
								                      +"手机号： "+JsonUtil.obj2Str(distribean.getPhone())+"<br/>"
								                      +"地址： "+JsonUtil.obj2Str(distribean.getAddr())+"<br/>"%>
								                 </td>
							           <%}
							           for(int i=0;i<sublist.size();i++){
							               if(i==0){%>
							                 
								                 <td rowspan="<%=sublist.size() %>"  style="padding:5px 0;"><%= (JsonUtil.obj2Str(detaillist.get(j).getPay_staus()).equals("1")?"未支付商品总价":JsonUtil.obj2Str(detaillist.get(j).getPay_staus()).equals("2")?"已支付商品总价":"")+(JsonUtil.obj2Str(detaillist.get(j).getOrder_price())) %></td>
								                 <td rowspan="<%=sublist.size() %>"><%=JsonUtil.obj2Str(detaillist.get(j).getDistri_staus()).equals("0")
								           ?"待配送":JsonUtil.obj2Str(detaillist.get(j).getDistri_staus()).equals("1")
								           ?"配送中":JsonUtil.obj2Str(detaillist.get(j).getDistri_staus()).equals("2")
								           ?"完成":JsonUtil.obj2Str(detaillist.get(j).getDistri_staus()).equals("3")
								           ?"暂时删除":""%></td>
								                 <td  style="padding:5px 0;"><%= JsonUtil.obj2Str(sublist.get(i).getProduct_name())+"*"+JsonUtil.obj2Str(sublist.get(i).getProduct_amount())+"  "+JsonUtil.obj2Str(sublist.get(i).getProduct_price())+"元" %></td>
								              </tr>
							                  
							               <%}else{%>
							                  <tr>
							                   <td  style="padding:5px 0;"><%= JsonUtil.obj2Str(sublist.get(i).getProduct_name())+"*"+JsonUtil.obj2Str(sublist.get(i).getProduct_amount())+"  "+JsonUtil.obj2Str(sublist.get(i).getProduct_price())+"元" %></td>
							                  </tr>
							               <%}
							           }}
							       }
							  }
							%>
								 
								 
						</table>
					</div>
					</form>
<jsp:include  page="../common/page.jsp"  flush="true">
<jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/> 
</jsp:include>	
					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
        function checkChange(obj){
           $("#searchForm input[type='checkbox']:not([name='header'])").each(function(){
                 $(this).trigger("click");
             });
        }
        $(function(){
					
			  var mydate = new Date();
              var str = "" + mydate.getFullYear() + "-";
              str += (mydate.getMonth()+1>9?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1))+"-";
              str += mydate.getDate();
              $("#time_from1").val(str);
              
		});
		function view(id)
		{  
			
			location.href="${pageContext.request.contextPath }/distri.do?method=export_excel";
			
		}	
		function exportExcel(){
		   window.open("${pageContext.request.contextPath }/distri.do?method=export_excel&distri_num="+$("#distri_num").val());
		}
		function optStatus(status){
		   var check=$("input[type='checkbox']:checked");
		   var phone="";
		   var distri_num=$("#distri_num").val();
		   $.each(check,function(){
		      phone+=$(this).parent().find("input[name='phone']").val()+",";
		   });
		   $.ajax({
                  type: 'POST',
                  url: "${pageContext.request.contextPath }/distri.do?method=opt_status",
                  data:{distri_num:distri_num,phone:phone,status:status},
                  success: function(data){
                       submitForm(1);
                  }
                });
		}
		function delStatus(obj){
		   var check=$(obj);
		   var distri_product_id=$(obj).parents("tr").find("input[name='distri_product_id']").val();
		   var order_id=$(obj).parents("tr").find("input[name='order_id']").val();
		   $.ajax({
                  type: 'POST',
                  url: "${pageContext.request.contextPath }/distri.do?method=opt_status",
                  data:{distri_product_id:distri_product_id,order_id:order_id,status:3},
                  success: function(data){
                       submitForm(1);
                  }
                });
		}
												
</script>	

</div>
</body>
</html>
