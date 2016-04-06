<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String goods_path = request.getContextPath();
String goods_basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+goods_path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />  
<title>tk</title>
<link href="<%=goods_basePath%>jsp/css/css.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div class="more">
  <form id="form1" method="post" action="">
    <p>
      <label>
      <div align="center"> <br />
        <input type="text" name="textfield" id="textfield" />
        <span class="more1"><span class="btn"><a href="*">查询</a></span></span></div>
      </label>
      <div align="center"></div>
    </p>
  </form>
  <table width="600" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
    <tr>
      <td width="25%" height="44" bgcolor="#E4E4E4"><div align="center">商品名称</div></td>
      <td width="17%" bgcolor="#E4E4E4"><div align="center">条码</div></td>
      <td width="19%" bgcolor="#E4E4E4"><div align="center">售价</div></td>
      <td width="39%" bgcolor="#E4E4E4"><div align="center">操作</div></td>
    </tr>
    <tr>
      <td height="44" class="border_bottom"><div align="center">苹果1</div></td>
      <td class="border_bottom"><div align="center"></div></td>
      <td class="border_bottom"><div align="center">12</div></td>
      <td class="border_bottom"><div align="center"><span class="btn1"><a href="*">添加到单据</a></span></div></td>
    </tr>
    <tr>
      <td height="47" class="border_bottom"><div align="center">苹果1</div></td>
      <td class="border_bottom"><div align="center"></div></td>
      <td class="border_bottom"><div align="center">11</div></td>
      <td class="border_bottom"><div align="center"><span class="btn1"><a href="*">添加到单据</a></span></div></td>
    </tr>
    <tr>
      <td height="45" class="border_bottom"><div align="center">苹果1</div></td>
      <td class="border_bottom"><div align="center"></div></td>
      <td class="border_bottom"><div align="center">13</div></td>
      <td class="border_bottom"><div align="center" class="STYLE1">已添加</div></td>
    </tr>
  </table>
  
  <p>&nbsp;</p>
  <div align="center"></div>
</div>
</body>
</html>