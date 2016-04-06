function getCookie(c_name) {
    if (document.cookie.length>0) {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}

function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

$(document).ready(
        function (){
            //给菜单li绑定点击事件
            $('#mnav li').on("click", function() {
                var li_index = $(this).index();
                var dl_index = $(this).parents('dl').index();
                document.cookie = "curr_menu=" + dl_index + "_" + li_index;
            });

           // 载入菜单
            var curr_menu = getCookie("curr_menu");
            if(curr_menu != '') {
                var dl_index = curr_menu.split("_")[0];
                var li_index = curr_menu.split("_")[1];
                var curr_li = $("#mnav dl").eq(dl_index).find('li').eq(li_index);

                $(".sn_cur").removeClass("sn_cur");
                curr_li.addClass("sn_cur");

                $(".n_cur").removeClass("n_cur");
                curr_li.parent().parent().parent().addClass("n_cur");
            }
        }
  );
 document.domain='qxit.com.cn';
 function jq_upload(path,file_id,hidden_id){

	 //var url = 'http://localhost:8080/scframe/common/upload/';
	 
	 var url = 'http://www.qxit.com.cn/scframe/common/upload/';
	 
	 
	 
     $.ajaxFileUpload({  
             url:url,            //需要链接到服务器地址  
             secureuri:url,
             fileElementId:file_id,  //文件选择框的id属性  
             data:{to_path:path},
             
             success: function(data, status){    
            	 
                 var results = $(data).text(); 
                 results = results.substring(results.indexOf('{'));
                 var obj = eval("("+results+")");  
                 
                 $("#"+hidden_id).val(obj.data.new_file);  

                 alert('文件上传成功！');  
                 
             },
             error: function (data, status, e){  
            	 
                     alert('文件上传失败！');  
             }  
         });  
 }  