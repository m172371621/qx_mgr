<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<script type="text/javascript" src="../js/jquery.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />
<!-- content -->
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">录入信息</div>
					<% 
							 
					OrderSearch searchBean = request.getAttribute("searchBean") == null? new OrderSearch(): (OrderSearch)request.getAttribute("searchBean");
					int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
					int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
					int order_status = searchBean.getOrder_status() == null ? 0 : searchBean.getOrder_status();	
				    int pay_type = searchBean.getPay_type() == null ? 0 : searchBean.getPay_type();	
					int cid = searchBean.getCid() == null ? 0 : searchBean.getCid();	
					int sid = searchBean.getSid() == null ? 0 : searchBean.getSid();	 
					%>	
					<form action="${pageContext.request.contextPath }/order.do?method=addOrderInfo" method="post" id="form">
						<div class="p20 m20"><input type="hidden" value="" name="editId"/>
						<input  type="hidden" name="cid" value="<%=((Community) session.getAttribute("selectCommunity")).getCommunity_id()  %>"/>
						<table class="table_t_r">
							
						<colgroup><col width="30%"/><col/><col width="30%"/><col/></colgroup>
							<tr>
								<th></th>
								<td style="font-size:18px;">金额</td>
								<td style="font-size:18px;">备注</td>
							</tr>
							<tr>
								<th>干洗：</th>
								<td>
									<input type="text" width="180px" class="i_text_1 monery" name="gPrice" value=""  /><br/>
									<span name="show" style="color:red; display:none;">请输入正确金额</span>
								</td>
								<td><input type="text" width="80px" class="i_text_1" name="gRemarks" value="" /></td>
							</tr>
							<tr>
								<th>快递：</th>
								<td>
									<input type="text" width="180px"  class="i_text_1 monery" name="kPrice" value="" /><br/>
									<span name="show" style="color:red; display:none;">请输入正确金额</span>
								</td>
								<td><input type="text" width="80px" class="i_text_1" name="kRemarks" value="" /></td>
							</tr>
							<tr>
								<th>皮具：</th>
								<td>
									<input type="text" class="i_text_1 monery"  name="pPrice" value="" /><br/>
									<span name="show" style="color:red; display:none;">请输入正确金额</span>
								</td>
								<td><input type="text" width="80px" class="i_text_1" name="pRemarks" value="" /></td>
							</tr>							
							<tr>
								<th>摇摇车：</th>
								<td>
									<input type="text" class="i_text_1 monery"  name="yPrice" value="" /><br/>
									<span name="show" style="color:red; display:none;">请输入正确金额</span>							
								</td>
								<td><input type="text" width="80px" class="i_text_1" name="yRemarks" value="" /></td>
							</tr>
						</table>
						<script type="text/javascript">
						var flag=0;
							 $('.monery').blur(function(){
									var  monery = $(this).val();
									
									 var s = /^-?\d+(\.\d+)?$/; // 验证数字和负数
									var s2 =  /^[A-Za-z]+$/;	//验证为字符串
									if(monery!="" && !s.test(monery)){
										$(this).siblings("span").css("display","block");
										flag=1;
									}else{
										$(this).siblings("span").css("display","none");
										flag=0;
										}
								 });
							 function addInfo(){
								 if(flag==1) return;
								 var gPrice=$("input[name='gPrice']").val();
								 var gRemarks=$("input[name='gRemarks']").val();
								 var kPrice=$("input[name='kPrice']").val();
								 var kRemarks=$("input[name='kRemarks']").val();
								 var pPrice=$("input[name='pPrice']").val();
								 var pRemarks=$("input[name='pRemarks']").val();
								 var yPrice=$("input[name='yPrice']").val();
								 var yRemarks=$("input[name='yRemarks']").val();
								 $.ajax({
								     type: 'POST',
								     url: "${pageContext.request.contextPath }/order.do?method=addOrderInfo",
								     data:{gPrice:gPrice,gRemarks:gRemarks,kPrice:kPrice,kRemarks:kRemarks,pPrice:pPrice,pRemarks:pRemarks,yPrice:yPrice,yRemarks:yRemarks},
								    success: function(data){
								        var dataObj = eval("("+data+")");
								        alert(dataObj.result_dec);
								        $("input").val("");
								     }
							     });
							 }
							
						</script>
					</div>
					<div class="mrw_btn_wrap">
						<a href="javascript:addInfo();" class="btn btn_big btn_r">提 交</a>
						<%--<a href="javascript:$('#form').submit()" class="btn btn_big btn_r">提 交</a>--%>
						</div>
					</form>
				</div>
			</div>
<!-- content end -->

</div>
</body>
</html>
