<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/new/jsp/include/resource.jsp"%>
<html>
<head>
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="css/demo.css" type="text/css">
<script type="text/javascript" src="js/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="js/ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/new/js/ztree/js/stock.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div>
					<div style="float: left;margin-left:5%;">
						<div>
							<h2 style="font-size: 22px; font-weight: bold;">总库</h2>
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
					<div style="position:absolute;left:48%;top:50%;">
						<button class="btn btn-primary" onclick="javascript:adds()">添加</button>
					</div>
					<div style="float:right;margin-right:5%">
						<div>
							<h2 style="font-size: 22px;font-weight: bold;">小区商品库</h2>
							<ul id="treeDemo2" class="ztree"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>