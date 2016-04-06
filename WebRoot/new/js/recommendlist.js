// JavaScript Document
(function(win){


 $.fn.searchtuInit=function(obj){
 $(this).click(function(){
      $(".wapper").css("display","block");
      $("#name1").html($(this).find("[name='name']").val());
      $("#phone1").html($(this).find("[name='phone']").val());
      $("#addr1").html($(this).find("[name='addr']").val());
      $(".dialog-tu").css("display","block");
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
    
   
    $('.click_two').searchtuInit($('.wapper'),$('.dialog-tu'));
    $('.no').noInit($('.dialog'),$('.dialog'));
    
  })

})(window)