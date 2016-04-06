<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<jsp:include page="../common/resource.jsp" />
	</head>
	<body>
		<jsp:include page="../common/header.jsp" />
		<div id="main" class="c">
			<jsp:include page="../common/menu.jsp" />
			<!-- content -->
			<input type="hidden" id="curr_li" value="product_0" />
			<div id="mright">
				<div id="mr_cnt">
					<c:if test="${num == 1}">
						<script type="text/javascript">
							alert("添加成功！");
						</script>
					</c:if>
					<div class="mrw_title">
						商品管理
					</div>
					<div class="sform_wrap c">
						<ul>
							<% 
							   ProductSearchBean searchBean = request.getAttribute("searchBean") == null? new ProductSearchBean(): (ProductSearchBean)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   int status = searchBean.getStatus()== null ? 0 : searchBean.getStatus();	
							   int cid = searchBean.getCid()== null ? 0 : searchBean.getCid();	
							   int sid = searchBean.getSid()== null ? 0 : searchBean.getSid();
							   int product_type = searchBean.getProduct_type()== null ? 0 : searchBean.getProduct_type();
							   
							%>
							<li>
								商品名称：
								<input type="text" class="i_text" name="name1"
									value="<%= JsonUtil.obj2Str(searchBean.getName()) %>" />
							</li>
							<li>
								商品状态：
								<select name="status1" id="" class="i_text">
									<option value="0">
										全部
									</option>
									<option value="1" <% if(status==1) out.print("selected");%>>
										正常
									</option>
									<option value="2" <% if(status==2) out.print("selected");%>>
										下架
									</option>
								</select>
							</li>
							<li>
								商品类型：
								<select name="product_type1" id="" class="i_text">
									<option value="0">
										全部
									</option>
									<option value="1" <% if(status==1) out.print("selected");%>>
										普通
									</option>
									<option value="2" <% if(status==2) out.print("selected");%>>
										促销
									</option>
								</select>
							</li>
							<li>
								所属品类：
								<select name="sid1" id="" class="i_text">
									<option value="0">
										全部
									</option>
									<%
									List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
									   for(ServiceVo s : slist){
									%>
									<option value="<%= s.getService_id()%>"
										<% if(s.getService_id() == sid) {out.print("selected");  }%>><%= s.getService_name() %></option>
									<% } %>
								</select>
							</li>
							<li>
								所属小区：
								<select name="cid1" id="" class="i_text">
									<% Community c =(Community)request.getSession().getAttribute("selectCommunity");%>
									<option value="<%= c.getCommunity_id()%>"><%= c.getCommunity_name() %></option>
								</select>
							</li>
							<li>
								<a href="javascript:submitForm()" class="btn btn_big btn_y">搜
									索</a>
							</li>
						</ul>
					</div>
					<form
						action="${pageContext.request.contextPath }/product.do?method=list"
						method="post" id="searchForm">
						<input type="hidden" name="name"
							value="<%= JsonUtil.obj2Str(searchBean.getName()) %>" />
						<input type="hidden" name="sid" value="<%= sid %>" />
						<input type="hidden" name="cid" value="<%= cid %>" />
						<input type="hidden" name="status" value="<%= status %>" />
						<input type="hidden" name="product_type"
							value="<%= product_type %>" />
						<input type="hidden" name="pageIndex" id="pageIndex"
							value="<%= pageIndex %>" />
					</form>
					<script type="text/javascript">
							function submitForm()
							{
								$("input[name='name']").val($("input[name='name1']").val());
								$("input[name='status']").val($("select[name='status1']").val());
								$("input[name='product_type']").val($("select[name='product_type1']").val());
								$("input[name='cid']").val($("select[name='cid1']").val());
								$("input[name='sid']").val($("select[name='sid1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						<form
							action="${pageContext.request.contextPath }/product.do?method=bat"
							method="post" id="batForm">
							<table class="table_t_t">
								<colgroup>
									<col width="40px" />
									<col width="40px" />
									<col width="160px" />
									<col width="120px" />
									<col width="80px" />
									<col width="120px" />
									<col width="80px" />
									<col width="80px" />
									<col width="80px" />
									<col width="160px" />
								</colgroup>
								<tr>
									<td>
										<input type="checkbox" id="checkAll" name="checkAll" value=""
											onclick="selectAll()" />
									</td>
									<th>
										商品ID
									</th>
									<th>
										商品名称
									</th>
									<th>
										所属品类
									</th>
									<th>
										商品类型
									</th>
									<th>
										所属小区
									</th>
									<th>
										商品价格
									</th>
									<th>
										剩余数量
									</th>
									<th>
										商品状态
									</th>
									<th class="icf">
										&#xf013e;
									</th>
								</tr>
								<% List<Product> list = (List<Product>)request.getAttribute("list");
							   if(list!=null){
							   for(Product product: list) {
							%>
								<tr>
									<td>
										<input type="checkbox" name="pids"
											value="<%= product.getProduct_id() %>" />
									</td>
									<td><%= JsonUtil.obj2Str(product.getProduct_id()) %></td>
									<td><%= JsonUtil.obj2Str(product.getName()) %></td>
									<td><%= JsonUtil.obj2Str(product.getService_name()) %>
									</td>
									<td><%= JsonUtil.obj2Str( product.getProduct_type() == 1? "普通" : "促销" ) %></td>
									<td><%= JsonUtil.obj2Str(product.getCommunityName()) %>
									</td>
									<td><%= JsonUtil.obj2Str(product.getPrice()) %></td>
									<td><%= JsonUtil.obj2Str(product.getReal_amount()) %></td>
									<td><%= JsonUtil.obj2Str( product.getStatus() == 1? "上架" : "下架" ) %></td>
									<td>
									    <a href="javascript:del(<%= product.getProduct_id() %>)"
											class="btn btn_r">删除</a>&nbsp;&nbsp;
										<a target="_blank"
											href="${pageContext.request.contextPath }/product.do?method=edit&type=1&viewId=<%= product.getProduct_id() %>"
											class="btn btn_r">修改</a>
									</td>
								</tr>
								<% }} %>
								<tr>
									<td colspan='9'>
										<a
											href="${pageContext.request.contextPath }/product.do?method=add"
											class="btn btn_r">新增商品</a>
										<a href="javascript:doBat(1)" class="btn btn_r">批量上架</a>
										<a href="javascript:doBat(2)" class="btn btn_r">批量下架</a>
										<a href="javascript:doBat(3)" class="btn btn_r">品类切换</a>
										<a href="javascript:doBat(4)" class="btn btn_r">本品类全部上架</a>
										<a href="javascript:doBat(5)" class="btn btn_r">本品类全部下架</a>
										<a href="javascript:doBat(6)" class="btn btn_r">本品类全部切换</a>
										切换到：
										<select name="sid_to" id="" class="i_text">
											<option value="0">
												请选择
											</option>
											<%
									for(ServiceVo s : slist){
									%>
											<option value="<%= s.getService_id()%>"><%= s.getService_name() %></option>
											<% } %>
										</select>
										<input type="hidden" name="bat_type" value="" />
										<input type="hidden" name="sid_from" value="<%= sid %>" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					<jsp:include page="../common/page.jsp" flush="true">
						<jsp:param name="pageIndex" value="<%= pageIndex %>" />
						<jsp:param name="pageCount" value="<%= pageCount %>" />
					</jsp:include>
				</div>
			</div>
			<!-- content end -->
		</div>
		<script type="text/javascript">
		function doBat(type)
		{  
			var sid_from = $("input[name='sid_from']").val();
			var sid_to = $("select[name='sid_to']").val();
			if(type == 6)
			{
				
				if('0' == sid_from)
				{
					alert("请先查询要切换的品类！");
					return;
				}

				if('0' == sid_to)
				{
					alert("请选择要切换到的品类！");
					return;
				}

				if(sid_from == sid_to)
				{
					alert("目前品类和要切换到的品类是一样的！");
					return;
				}
				
			}else if(type == 4 || type == 5)
			{
				if('0' == sid_from)
				{
					alert("请先查询要下/上架的品类！");
					return;
				}

			}else if(type == 3 )
			{

				if(!$("input[name='pids']").is(':checked'))
				{
					alert("请先勾选要操作的商品！");
					return;
				}

				
				if('0' == sid_to)
				{
					alert("请选择要切换到的品类！");
					return;
				}

			}else
			{
				if(!$("input[name='pids']").is(':checked'))
				{
					alert("请先勾选要操作的商品！");
					return;
				}
			
			}

			if(confirm("批量操作请谨慎！是否继续？"))  
			{
				$("input[name='bat_type']").val(type);
				$("#batForm").submit();
			}
			
		}	
		
		function selectAll()
		{	
			if($("#checkAll").is(':checked'))
			{
				$("input[name='pids']").attr("checked",true); 
			}else
			{
				$("input[name='pids']").attr("checked",false); 
			}
		}

		function del(pid) {
			if(confirm('确实领取?')){	
					$.ajax({
						type: "post",
						url: "${pageContext.request.contextPath }/product.do?method=del",
						data: {pId:pid},
						success: function(data) {
								alert("删除成功！");
								window.location.reload();				
							},
						error: function(err) {
								alert("error");
							}
						})
				}
		}									
</script>
	</body>
</html>
