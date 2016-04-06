// JavaScript Document
(function(win){
 $.fn.searchInit=function(obj){
 $(this).click(function(){
      $(".wapper").css("display","block");
  $(".dialog").show();
});
}

 $.fn.searchtuInit=function(obj){
 $(this).click(function(){
      $(".wapper").css("display","block");
  $(".dialog-tu").show();
});
}
 $.fn.noInit=function(obj){
 $(this).click(function(){
    $(".wapper").css("display","none");
  $(".dialog").hide();
  $(".dialog-tu").hide();
});
}
 
$(function(){
    
    $('.click_two').searchInit($('.wapper'),$('.dialog-tu'));
    $('.click_two').searchtuInit($('.wapper'),$('.dialog-tu'));
    $('.click_two').searchtuInit($('.wapper'),$('.dialog-tu'));
    $('.no').noInit($('.dialog'),$('.dialog'));
    $('.yes').noInit($('.dialog'),$('.dialog'));
    
  })

})(window)
var timestamp;
var noncestr;
var appId;
var signType;
var package_str;
var paySign;
var order_serial;
function jsApiCall()
{
WeixinJSBridge.invoke(
   'getBrandWCPayRequest',
   {
    "appId" : appId,
     "timeStamp" : timestamp,
     "nonceStr" : noncestr,
     "package" :package_str,
     "signType" : signType,
     "paySign" : paySign
   },
   function(res){
       if(res.err_msg == "get_brand_wcpay_request:ok" ) {
    	   alert("支付成功");
    	   window.location.href=ctx+"/weixinqxx.do?method=doWeixinQxxMain";
       }else if(res.err_msg == "get_brand_wcpay_request:cancel" ){
           alert("已取消支付");
       }else if(res.err_msg == "get_brand_wcpay_request:fail" ){
           alert("支付失败"+res.err_msg);
       }
                                  });
   
}
function callpay()
{
	if (typeof WeixinJSBridge == "undefined"){
		if( document.addEventListener ){
			document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
		}else if (document.attachEvent){
			document.attachEvent('WeixinJSBridgeReady', jsApiCall);
			document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
		}
	}else{
		jsApiCall();
	}
}

function doSumitOrder(){
	var ctx=$("#ctx").val();
	$.ajax({
		type: 'POST',
		url: ctx+"/weixinqxx.do?method=doOrderSubmit",
		dataType:'json',
		success: function(data){
		if(data.result_code=="0"){
			var datait=data.data;
            var dataitem=datait;
            timestamp=dataitem.timeStamp+"";
            noncestr=dataitem.nonceStr+"";
            console.log(noncestr);
            appId=dataitem.appId;
            signType=dataitem.signType;
            package_str=dataitem["package"];
            paySign=dataitem.paySign;
            callpay();
		}else{
			alert(data.result_dec);
		}
	}
	});
}
	
wx.ready(function () {
	checkApi();
});
$(document).ready(function(){
	wx.config({
		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: $("#appId").val(), // 必填，公众号的唯一标识
		timestamp: $("#timestamp").val(), // 必填，生成签名的时间戳
		nonceStr: $("#nonceStr").val(), // 必填，生成签名的随机串
		signature: $("#signature").val(),// 必填，签名，见附录1
		jsApiList: ["onMenuShareAppMessage","onMenuShareTimeline"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
});
wx.error(function(res){

	console.log(res);
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

});
function checkApi(){
	var code=$("#my_recommend_code").val()=='null'?"":$("#my_recommend_code").val();
	var url="";
	if($("#my_recommend_code").val()=='null'){
		url=encodeURIComponent("http://www.qxit.com.cn/qx_mgr/doWeixinInterative.do");
	}else{
		url=encodeURIComponent("http://www.qxit.com.cn/qx_mgr/doWeixinInterative.do?other_recommend_code="+$("#my_recommend_code").val());
	}
	var p_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx400adb553bf0425e&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect";
	p_url=p_url.replace("REDIRECTURL",url);
	var product_name=$("#product_name").val();
	//alert(product_name);
	 wx.onMenuShareAppMessage({
		  title: "区享一元抢购儿童原汁机",
		  desc:  "分享30个以上朋友即可抢购！",
	      link: p_url,
	      imgUrl: $(".banner img").attr("src")
	    });
	 wx.onMenuShareTimeline({
		  title: "区享一元抢购儿童原汁机",
		  desc:  "分享30个以上朋友即可抢购！",
	      link: p_url,
	      imgUrl: $(".banner img").attr("src")
	    });
}
function getRecommendList(){
	var ctx=$("#ctx").val();
	window.location.href=ctx+"/weixinqxx.do?method=getRecommendList";
	
}


