<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <%if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
	<jsp:include page="../common/header.jsp" />
	<div id="main" class="c">
		<jsp:include page="../common/menu.jsp" />

		<!-- content -->
		<input type="hidden" id="curr_li" value="product_0" />
		<div id="mright">
			<div id="mr_cnt">
				<div class="mrw_title">新增商品</div>
				<form action="${pageContext.request.contextPath }/product.do?method=add" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
							<colgroup>
								<col width="30%" />
								<col />
							</colgroup>
							<tr>
								<th>名称</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="name" value="" />
									<label id="nameError"></label>
								</td>

							</tr>
							<tr>
								<th>所属服务</th>
								<td>
									<select name="sid" id="" class="i_text_1">
										<%
											List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
																									   for(ServiceVo s : slist){
										%>
										<option value="<%=s.getService_id()%>"><%=s.getService_name()%></option>

										<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td>
									<select name="cid" id="" class="i_text_1">
										<%
											Community c =(Community)request.getSession().getAttribute("selectCommunity");
										%>
										<option value="<%=c.getCommunity_id()%>"><%=c.getCommunity_name()%></option>
									</select>
								</td>
							</tr>
							<tr>
								<th>商品二维码</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="barcode" value="" />
									<label id="barcodeError"></label>
								</td>

							</tr>
							<tr>
								<th>商品价格</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="price" value="" />
									<label id="priceError"></label>
								</td>

							</tr>
							<tr>
								<th>App专享价</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="market_price" value="" />
									<label id="market_priceError"></label>
								</td>

							</tr>
							<tr>
								<th>商品单位</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="unit" value="" />
									<label id="unitError"></label>
								</td>

							</tr>
							<tr>
								<th>商品剩余数量</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="amount" value="0" disabled="true" 　readOnly="true" />
									<label id="amountError"></label>
								</td>

							</tr>
							<tr>
								<th>是否送货上门</th>
								<td>
									<input type="radio" name=delivery_type value="1" onclick="$('#delivery_price').attr('disabled',false)" checked />
									上门
									<input type="radio" name="delivery_type" value="2" onclick="$('#delivery_price').attr('disabled',true)" />
									不上门
								</td>
							</tr>
							<tr>
								<th>营销类型</th>
								<td>
									<!-- 无货 预售 特价 不支持区享卡 满减 APP专享价 称重 -->
									<input type="checkbox" name="tags"  value="0" />	称重								
									<input type="checkbox" name="tags" value="1"  />	APP专享价								
									<input type="checkbox" class="checked" name="tags" value="2" />	满减  								
								    <input type="checkbox" name="tags" value="3"  />	不支持区享卡								
									<input type="checkbox" name="tags" value="4"  />	特价								
									<input type="checkbox" name="tags" value="5"  />	预售								
									
								</td>
							</tr>
							<tr id="manJian_show" style="display:none;">
								<th></th>
								<td>
									满：<input style="margin-bottom:5px;" name="man" value="" onblur="_man()" /><span class="manMess" style="display:none; color:red;">请输入有效数字</span><br/>
									减：<input name="jian" value="" onblur="_jian()"/><span class="jianMess" style="display:none; color:red;">请输入有效数字</span><br/>
								</td>
							</tr>
							<tr>
								<th>送货上门价格</th>
								<td>
									<input type="text" width="180px" class="i_text_1" name="delivery_price" id="delivery_price" value="" />
									<label id="delivery_priceError"></label>
								</td>

							</tr>
							<tr>
								<th>商品图片</th>
								<td>
									<input type="file" name="file" id="picture_file" value="" />
									<input type="button" name="file_upload" value="上传图片" onclick="jq_upload('temp','picture_file','picture')" />
									<input type="hidden" id="picture" name="picture" value="" />
								</td>
							</tr>
							<!-- tr>
								<th>商品缩略图</th>
								<td><input type="text"   class="i_text_1" style="width:480px" name="thumbnail" value=""/></td>
							</tr> -->

							<tr>
								<th>商品描述url</th>
								<td>
									<input type="text" class="i_text_1" style="width:480px" name="description_url" value="http://www.qxit.com.cn/scframe/upload/product/default/1.htm" />
								</td>
							</tr>
							<tr>
								<th>商品状态</th>
								<td>
									<select name="status" id="" class="i_text_1">
										<option value="1">正常</option>
										<option value="2">下架</option>
									</select>
								</td>
							</tr>
						</table>
					</div>

					<div class="mrw_btn_wrap">
						<a href="javascript:p_submit()" class="btn btn_big btn_r">确定</a>
					</div>
				</form>
			</div>
		</div>
		<!-- content end -->

		<script type="text/javascript">
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
		$(".i_text_1").focus(function() {
			var lableName = $(this).attr("name") + "Error";
			$("#"+lableName).text("");
			showError($("#"+lableName));
		})
		$(".i_text_1").blur(function() {
			var id = $(this).attr("name");//获取当前输入框的name
			var funName = "validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
			eval(funName);//执行方法
		})

		
	});
			function showError(ele) {
				var text = ele.val(); //获取元素的内容
				ele.css("color", "red");
				if (text) {//如果没有内容
					ele.css("display", "none")//隐藏内容
				} else { //如果有内容
					ele.css("display", "")//显示内容
				}
			}
			//提交
			function p_submit() {
				var flag = true;
				if (!validateName()) {
					flag = false;
				}
				if (!validateBarcode()) {
					flag = false;
				}
				if (!validatePrice()) {
					flag = false;
				}
				if (!validateMarket_price()) {
					flag = false;
				}
				if (!validateUnit()) {
					flag = false;
				}
				if (!validateAmount()) {
					flag = false;
				}
				if (!validateDelivery_price()) {
					flag = false;
				}
				if (flag != false) {
					$("#form").submit()
				}
			}
			//商品名校验	请输入正确的名称
			function validateName() {
				var value = $("input[name=name]").val();
				//为空
				if (!value) {
					$("#nameError").text(" 商品名称不能为空");
					showError($("#nameError"));
					return false;
				}
				//中文  /^[0-9A-Za-z\u4e00-\u9fa5]{2,14}$/   --   /^[\u4e00-\u9fa5]+$/
				//if(!/^[0-9A-Za-z\u4e00-\u9fa5]{2,14}$/.test(value)){
				//	$("#nameError").text(" 请输入正确的商品名称");
				//	showError($("#nameError"));
				//	return false;
				//}
				return true;
			}
			//商品二维码验证校验	 请输入正确商品二维码
			function validateBarcode() {
				var value = $("input[name=barcode]").val();
				//为空
				//if(!value){
				//	$("#barcodeError").text(" 二维码不能为空");
				//	showError($("#barcodeError"));
				//	return false;
				//}
				//数字
				//if(!/^(\+|-)?\d+$/.test(value)){
				if (isNaN(value)) {
					$("#barcodeError").text(" 请输入正确商品二维码");
					showError($("#barcodeError"));
					return false;
				}
				return true;
			}
			//商品价格校验	 请输入正确商品价格
			function validatePrice() {
				var value = $("input[name=price]").val();
				//为空
				if (!value) {
					$("#priceError").text(" 商品价格不能为空");
					showError($("#priceError"));
					return false;
				}
				//数字
				if (!/^\d+(?=\.{0,1}\d+$|$)/.test(value)) {
					$("#priceError").text(" 请输入正确商品价格");
					showError($("#priceError"));
					return false;
				}
				return true;
			}
			//商品市场校验	请输入正确商品市场
			function validateMarket_price() {
				var value = $("input[name=market_price]").val();
				//为空
				if (!value) {
					$("#market_priceError").text(" 商品市场不能为空");
					showError($("#market_priceError"));
					return false;
				}
				//数字
				if (!/^\d+(?=\.{0,1}\d+$|$)/.test(value)) {
					$("#market_priceError").text(" 请输入正确商品市场");
					showError($("#market_priceError"));
					return false;
				}
				return true;
			}
			//商品单位校验	请输入正确商品单位
			function validateUnit() {
				var value = $("input[name=unit]").val();
				//为空
				if (!value) {
					$("#unitError").text("	商品单位不能为空");
					showError($("#unitError"));
					return false;
				}
				//数字
				if (!/^[\u4e00-\u9fa5a-zA-Z]+$/.test(value)) {
					$("#unitError").text(" 请输入正确商品单位");
					showError($("#unitError"));
					return false;
				}
				return true;
			}
			//商品剩余数量校验	请输入正确商品剩余数量
			function validateAmount() {
				var value = $("input[name=amount]").val();
				//为空
				if (!value) {
					$("#amountError").text(" 商品剩余数量不能为空");
					showError($("#amountError"));
					return false;
				}
				//数字
				if (!/^(\+|-)?\d+$/.test(value)) {
					$("#amountError").text(" 请输入正确商品剩余数量");
					showError($("#amountError"));
					return false;
				}
				return true;
			}
			//送货上门价校验		请输入正确送货上门价格
			function validateDelivery_price() {
				var value = $("input[name=delivery_price]").val();
				//为空
				if (!value) {
					$("#delivery_priceError").text(" 送货上门价格不能为空");
					showError($("#delivery_priceError"));
					return false;
				}
				//数字
				if (!/^\d+(?=\.{0,1}\d+$|$)/.test(value)) {
					$("#delivery_priceError").text(" 请输入正确送货上门价格");
					showError($("#delivery_priceError"));
					return false;
				}
				return true;
			}

			function _man() {
				var num = /^[0-9]*[1-9][0-9]*$/;
				var man = $('input[name=man]').val();
				if (!num.test(man) && num.length != 0) {
					$('.manMess').show();
				} else {
					$('.manMess').hide();
				}
			};

			function _jian() {
				var num = /^[0-9]*[1-9][0-9]*$/;
				var man = $('input[name=man]').val() - 0;
				var jian = $('input[name=jian]').val() - 0;
				if (!num.test(jian)) {
					$('.jianMess').show();
					return;
				} else {
					$('.jianMess').hide();
				}
				if (jian.length == 0) {
					$('.jianMess').show();
					return;
				} else {
					$('.jianMess').hide();
				}
				if (jian > man) {
					$('.jianMess').show();
					return;
				} else {
					$('.jianMess').hide();
				}
				if (man == jian) {
					$('.jianMess').show();
					return;
				} else {
					$('.jianMess').hide();
				}
			};

			//所属服务  不做校验
			function validateSid() {
			}
			//所属小区 不做校验
			function validateCid() {
			}
			//商品描述url 不做校验
			function validateDescription_url() {
			}
			//商品状态 不做校验
			function validateStatus() {
			}
		</script>
	</div>
</body>
</html>
