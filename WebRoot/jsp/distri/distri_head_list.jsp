<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.distri.po.DistriOrderBean" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
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
								
								<li>生成日期：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" id="time_from1" value="<%=request.getAttribute("createTime").toString() %>"/></li>
								<li>配送员：<select name="distri_worker_id1" size="1" id="distri_worker_id1">
                                </select></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
								<li><a href="javascript:detail()" class="btn btn_big btn_y">维护配送单</a></li>
								<li><a href="javascript:addDistri()" class="btn btn_big btn_y">创建配送单</a></li>
							</ul>
							
						
						</div>
						<%
						int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
						 %>
					<script type="text/javascript">
					
							function submitForm()
							{  
							    $("#time").val($("#time_from1").val());
							    $("#distri_worker_id").val($("#distri_worker_id1").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/distri.do?method=list_distri_head" method="post" id="searchForm">
					<div id="order_wrap" class="p20">
						<input type="hidden" id="time" name="time_from1" value="<%=request.getAttribute("createTime").toString() %>"/>
					<input type="hidden" id="distri_worker_id" name="distri_worker_id" value="<%=request.getAttribute("distri_worker_id")==null?0:request.getAttribute("distri_worker_id") %>"/>
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
							    <th></th>
								<th>配送单号</th>
								<th>生成时间</th>
								<th>订单数量</th>
							</tr>
							<% List<DistriOrderBean> list = (List<DistriOrderBean>)request.getAttribute("list");
							   if(list!=null){
							   for(DistriOrderBean distribean: list) {
							%>
							<tr>
							    <td><input type="checkbox"/></td>
								<td><%= JsonUtil.obj2Str(distribean.getDistri_num()) %></td>							
								<td><%= JsonUtil.obj2Str(distribean.getCreate_time()) %></td>	
								<td><%= JsonUtil.obj2Str(distribean.getProductCount()) %></td>	
							</tr>
							<% }} %>
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
        function getDistriWorker(){
           $.ajax({
                  type: 'POST',
                  url: "${pageContext.request.contextPath }/distri.do?method=getDistriWorker",
                  success: function(data){
                      var dataObj = eval("("+data+")");
                      if(dataObj.result_code=="0"){
                          var obj=dataObj.data;
                              $("#distri_worker_id1").html("");
                              $("#distri_worker_id1").append('<option value="0">所有</option>');
                          $.each(obj,function(index,item){
                              $("#distri_worker_id1").append('<option value="'+item.distri_worker_id+'">'+item.distri_worker_name+'</option>');
                          });
                          $("#distri_worker_id1").val($("#distri_worker_id").val());
                      }
                  }
                });
        }
        $(function(){
					
			  var mydate = new Date();
              var str = "" + mydate.getFullYear() + "-";
              str += (mydate.getMonth()+1>9?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1))+"-";
              str += mydate.getDate();
              //$("#time_from1").val(str);
              getDistriWorker();
		});
		function view(id)
		{  
			
			location.href="${pageContext.request.contextPath }/qxcard.do?method=edit_reglog&op_id="+id;
			
		}	
		function detail(){
		    var distri_worker_id=$("#distri_worker_id1").val();
		   var check=$("input[type='checkbox']:checked");
		   var distri_num=check.parent().next().html();
		   location.href="${pageContext.request.contextPath }/distri.do?method=list_distri_detail&distri_num="+distri_num+"&view_status="+0+"&distri_worker_id="+distri_worker_id;
		}
		function addDistri(){
		   var distri_worker_id=$("#distri_worker_id1").val();
		   if(distri_worker_id=="0"){
		      alert("请选择配送员！");
		      return;
		   }
		   location.href="${pageContext.request.contextPath }/distri.do?method=add_distri"+"&distri_worker_id="+distri_worker_id+"&view_status="+1;
		}
												
</script>	

</div>
</body>
</html>
