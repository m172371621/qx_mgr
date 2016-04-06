$(function(){	
	$(window).scroll(function() {		
		if($(window).scrollTop() >= 200){ //向下滚动像素大于这个值时，即出现小火箭~
			$('.rockets').fadeIn(200); //火箭淡入的时间，越小出现的越快~
		}else{    
			$('.rockets').fadeOut(200); //火箭淡出的时间，越小消失的越快~
		}  
	});
	$('#rockets').click(function(){$('html,body').animate({scrollTop: '0px'}, 1000);}); //火箭动画停留时间，越小消失的越快~
	
	$(".btn").click(function(){
		$('html,body').animate({scrollTop:$('#qx_step').offset().top}, 800);  //缓冲滚动效果
		
		});
		
});
function submitData(){
	if($("input[name='phone']").val()==''){
		alert("请填写手机号！");
		return;
	}
	if($("input[name='name']").val()==''){
		alert("请填写姓名！");
		return;
	}
	if($("input[name='addr']").val()==''){
		alert("请填写地址！");
		return;
	}
	$("#qxxform").submit();
}