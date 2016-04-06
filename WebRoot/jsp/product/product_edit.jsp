<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%Product product =  (Product)request.getAttribute("product");
	int rid = product.getRule_id() == null ? 0 : product.getRule_id();
	
%>

<!-- content -->
<input type="hidden" id="curr_li" value="product_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">编辑商品</div>
					<form action="${pageContext.request.contextPath }/product.do?method=edit" method="post" id="form">
					<input type="hidden" width="180px"  class="i_text_1" name="product_id" value="<%= product.getProduct_id() %>"/>
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>名称</th>
								<td><input type="text" width="180px"  class="i_text_1" name="name" value="<%= JsonUtil.obj2Str(product.getName()) %>"/></td>
							</tr>
							<tr>
								<th>所属服务</th>
								<td><select name="sid"  id="" class="i_text_1">
									<%
									   List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
									   for(ServiceVo s : slist){
									%>
									<option value="<%= s.getService_id()%>" <%if(s.getService_id() + 0 == product.getService_id()) out.print("selected"); %>><%= s.getService_name() %></option>
								
									<% } %>
								</select>
								</td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td><input type="text" width="180px"  class="i_text_1" name="cid" disabled value="<%= JsonUtil.obj2Str(product.getCommunityName()) %>"/>
								<input type="hidden" name="cid_1" value="<%= JsonUtil.obj2Str(product.getCommunity_id()) %>"/>
								</td>
							</tr>
							<tr>
								<th>商品二维码</th>
								<td><input type="text" width="180px"  class="i_text_1" name="barcode" value="<%= JsonUtil.obj2Str(product.getBarcode()) %>"/></td>
							</tr>
							<tr>
								<th>商品价格</th>
								<td><input type="text" width="180px"  class="i_text_1" name="price" value="<%= JsonUtil.obj2Str(product.getPrice()) %>"/></td>
							</tr>
							<tr>
								<th>App专享价</th>
								<td><input type="text" width="180px"  class="i_text_1" name="market_price" value="<%= JsonUtil.obj2Str(product.getMarket_price()) %>"/></td>
							</tr>
							<tr>
								<th>商品单位</th>
								<td><input type="text" width="180px"  class="i_text_1" name="unit" value="<%= JsonUtil.obj2Str(product.getUnit()) %>"/></td>
							</tr>
							<tr>
								<th>营销类型</th>
								<td>
									<!-- 无货 预售 特价 不支持区享卡 满减 APP专享价 称重 -->
									
									<input type="checkbox" name="tags"  value="0" <%if(ProductTags.checkTag(product.getTags(),ProductTags.WEIGHT)) out.print("checked");%>/>	称重								
									<input type="checkbox" name="tags" value="1"  <%if(ProductTags.checkTag(product.getTags(),ProductTags.APPONLY)) out.print("checked");%>/>	APP专享价								
									<input type="checkbox" class="checked" name="tags" value="2"<%if(ProductTags.checkTag(product.getTags(),ProductTags.WHOLESALE)) out.print("checked");%>/>	满减  								
								    <input type="checkbox" name="tags" value="3"  <%if(ProductTags.checkTag(product.getTags(),ProductTags.NOQXCARD)) out.print("checked");%>/>	不支持区享卡								
									<input type="checkbox" name="tags" value="4"  <%if(ProductTags.checkTag(product.getTags(),ProductTags.NOSALE)) out.print("checked");%>/>	特价								
									<input type="checkbox" name="tags" value="5"  <%if(ProductTags.checkTag(product.getTags(),ProductTags.PRESALE)) out.print("checked");%>/>	预售								
									
								</td>
							</tr>
							<tr id="manJian_show" style="display:none;">
								<th></th>
								<td>
									<%	
										String man ="";
										String jian ="";
										if(product.getWholesale_price()!=null){
											int index = product.getWholesale_price().indexOf('|');
											int length = product.getWholesale_price().length();
											man = product.getWholesale_price().substring(0,index);
											jian = product.getWholesale_price().substring(index+1,length);
										}
									%>
									满：<input style="margin-bottom:5px;" name="man" value="<%=man==null?"":man%>" onblur="_man()" /><span class="manMess" style="display:none; color:red;">请输入有效数字</span><br/>
									减：<input name="jian" value="<%=jian==null?"":jian%>" onblur="_jian()"/><span class="jianMess" style="display:none; color:red;">请输入有效数字</span><br/>
								</td>
							</tr>
							<tr>
								<th>商品剩余数量</th>
								<td><input type="text" width="180px"  class="i_text_1" readonly name="amount" value="<%= JsonUtil.obj2Str(product.getAmount()) %>"/></td>
							</tr>
							<tr>
								<th>是否送货上门</th>
								<td>	
											<input type="radio" name=delivery_type value="2" onclick="$('#delivery_price').attr('disabled',false)" <% if(product.getDelivery_type()==2) out.print("checked");%>/>上门										
											<input type="radio" name="delivery_type" value="1"  onclick="$('#delivery_price').attr('disabled',true)" <% if(product.getDelivery_type()==1) out.print("checked");%>/>不上门
								</td>
							</tr>
							<tr>
								<th>送货上门价格</th>
								<td><input type="text" width="180px"  <% if(product.getDelivery_type()==1) out.print("disabled");%> class="i_text_1" name="delivery_price" id="delivery_price" value="<%= JsonUtil.obj2Str(product.getDelivery_price()) %>"/></td>
							</tr>
							<tr>
								<th>商品图片</th>
								<td>原图：<a target="_blank" href="<%= JsonUtil.obj2Str(product.getPicture()) %>"/><%= JsonUtil.obj2Str(product.getPicture()) %></a><br/>
								<input type="file" name="file" id="picture_file" value=""/>
								<input type="button" name="file_upload" value="上传图片" onclick="jq_upload('temp','picture_file','picture')"/><br/>
								<input type="hidden" id ="picture" name="picture" value=""/></td>
							</tr>
							<tr>
								<th>促销标题</th>
								<td><input type="text" width="180px"  class="i_text_1" style="width:480px" name="dec_tag" value="<%= JsonUtil.obj2Str(product.getDec_tag()) %>"/></td>
							</tr>
							
							<tr>
								<th>是否促销商品</th>
								<td><select name="product_type"  id="product_type" class="i_text_1">
									<option value="1" <% if(product.getProduct_type()==1) out.print("selected");%> >正常商品</option>
									<option value="2" <% if(product.getProduct_type()!=1) out.print("selected");%> >促销商品</option>
								</select></td>
							</tr>
							<tr>
								<th>商品促销图片</th>
								<td>原图：<a target="_blank" href="<%= JsonUtil.obj2Str(product.getDescription_pic()) %>"/><%= JsonUtil.obj2Str(product.getDescription_pic()) %></a><br/>								
								<input type="file" name="file" id="picture_file1" value=""/>
								<input type="button" name="file_upload1" value="上传图片" onclick="jq_upload('temp','picture_file1','description_pic')"/><br/>
								<input type="hidden" id ="description_pic" name="description_pic" value=""/></td>
							</tr>
							<tr>
								<th>商品描述url</th>
								<td><input type="text" width="180px"  style="width:480px" class="i_text_1" name="description_url" value="<%= JsonUtil.obj2Str(product.getDescription_url()) %>"/></td>
							</tr>
							<tr>
								<th>商品状态</th>
								<td><select name="status"  id="" class="i_text_1">
									<option value="1" <% if(product.getStatus()==1) out.print("selected");%> >正常</option>
									<option value="2" <% if(product.getStatus()!=1) out.print("selected");%> >下架</option>
								</select></td>
							</tr>
							<tr>
								<th>商品营销规则</th>
								<td><select name="rule_id"  id="" class="i_text_1">
									<option value="0" >--</option>
									<%
										List<ProductRule> plist = (List<ProductRule>)request.getAttribute("rules");
									   for(ProductRule r : plist){
									%>
									<option value="<%= r.getRule_id()%>" <% if(r.getRule_id() == rid) {out.print("selected");  }%>><%= r.getRule_name() %></option>								
									<% } %>	
								</select></td>
							</tr>
							<tr>
								<th>更新时间</th>
								<td><input type="text" width="180px"  class="i_text_1" name="udate" disabled value="<%= CommonUtil.formatTimestamp(product.getUpdateTime(),1) %>"/></td>
							</tr>
							<tr>
								<th>创建时间</th>
								<td><input type="text" width="180px"  class="i_text_1" name="cdate" disabled value="<%= CommonUtil.formatTimestamp(product.getCreateTime(),1) %>"/></td>
							</tr>
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					
						<a href="javascript:sub();" class="btn btn_big btn_r">确定</a>
						
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
<script type="text/javascript">
		function sub(){
			debugger;
			if($('.checked').is(':checked')){
				if($('input[name=man]').val().length == 0){
						return ;
					}
				if($('input[name=jian]').val().length == 0){
					return;
					}
				if($('input[name=man]').val()-0 <= $('input[name=jian]').val()-0){
					return;
					}
				$('#form').submit();
			}else{
				$('#form').submit();
			}
			
		}
	
		function _man(){
			var num =  /^[0-9]*[1-9][0-9]*$/;
			var man = $('input[name=man]').val();
			if(!num.test(man) && num.length!=0){ 
				$('.manMess').show();
			}else{
				$('.manMess').hide();
				}
		};

		function _jian(){
			var num =  /^[0-9]*[1-9][0-9]*$/;
			var man = $('input[name=man]').val()-0;
			var jian = $('input[name=jian]').val()-0;
			if(!num.test(jian)){ 
				$('.jianMess').show();
				return;
			}else{
				$('.jianMess').hide();
				}
			if(jian.length == 0){
				$('.jianMess').show();
				return;
			}else{
				$('.jianMess').hide();
				}
			if(jian > man ){
				$('.jianMess').show();
				return;
			}else{	
				$('.jianMess').hide();
				}
			if(man == jian ){
				$('.jianMess').show();
				return;
			}else{	
				$('.jianMess').hide();
				}
			};
	$(document).ready(function(){
		if($('.checked').is(':checked')){$('#manJian_show').show();};
		if(!$('.checked').is(':checked')){
				$('input[name=man]').attr('value',null);
				$('input[name=jian]').attr('value',null);
			};
		var man = $('input[name=man]').val();
		var jian = $('input[name=jian]').val();
				
			$('.checked').click(function(){
					if($('.checked').is(':checked')){
						$('input[name=man]').attr('value',man);
						$('input[name=jian]').attr('value',jian);
						$('#manJian_show').show();
						};
					if(!$('.checked').is(':checked')){
						$('input[name=man]').attr('value',null);
						$('input[name=jian]').attr('value',null);
						$('#manJian_show').hide();
					};
				});
		});
</script>
</div>
</body>
</html>
