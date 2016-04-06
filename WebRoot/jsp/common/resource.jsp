<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

    <base href="<%=basePath%>">    
    <title>区享管理后台</title>   
    <meta charset="utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="区享,社区,服务">
	<meta http-equiv="description" content="区享管理后台">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>jsp/css/manager.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/public.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/autocomplete/jquery.autocomplete.js"></script>
  <link rel="stylesheet" href="<%=basePath%>js/autocomplete/jquery.autocomplete.css" />

<%--<script src="${pageContext.request.contextPath}/js/jquery.jplayer.min.js" type="text/javascript" charset="utf-8"></script>
<div id="jquery_jplayer"></div>
<script type="text/javascript">
    $(function () {
        $("#jquery_jplayer").jPlayer({
            ready: function () {
                $(this).jPlayer("setMedia", {mp3: "${pageContext.request.contextPath}/audio/sms.mp3"}); },
            swfPath: "${pageContext.request.contextPath}/audio",
            supplied: "mp3",
            volume:1.8,
            preload: 'metadata',
            solution: 'flash,html '
        })
        setInterval("myInterval()",5000);//1000为1禾R轒~_
    });

    function myInterval()
    {

        $.ajax( {
            type : "post",
            url : "${pageContext.request.contextPath}/order.do?method=getNewOrderCount",
            ContentType : "application/json; charset=utf-8",
            success : function(data) {
                var dataObj = eval("("+data+")");
                if("0"==dataObj.result_code){
                    var obj=dataObj.data;
                    if(parseInt(obj)>0){
                        $("#jquery_jplayer").jPlayer("play");

                    }
                }
            },
            error : function(msg) {
            }
        });
    }

</script>--%>
	

