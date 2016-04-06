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
								<li><a href="javascript:addDistri()" class="btn btn_big btn_y">创建配送单</a></li>
							</ul>
							
						
						</div>
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
                          $("#distri_worker_id1").val(0);
                      }
                  }
                });
        }
        $(function(){
					
              getDistriWorker();
		});
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
