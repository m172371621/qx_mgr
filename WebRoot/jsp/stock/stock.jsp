<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<link rel="stylesheet" href="./css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="./css/demo.css" type="text/css">
<script type="text/javascript" src="./js/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="./js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="./js/ztree/jquery.ztree.exedit-3.5.js"></script>

<title>总库</title>
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<div id="main" class="c">

		<jsp:include page="../common/menu.jsp" />

		<!-- content -->
		<div id="mright">
			<div id="mr_cnt" style="background:#362c2c">
				<div class="mrw_title">总库管理</div>
				<div class="content_wrap" style="margin: 0 auto; width: 1100px; height:autopx;">
					<div class="zTreeDemoBackground left">
						<h2 style="font-size: 22px; color:white; font-weight: bold;">总库</h2>
						<!--  搜索:<input type="text" id="sch"> -->
						<ul id="treeDemo" class="ztree"></ul>
					</div>
					<div style="position:relative;height: 10px;width: 10px;top: 80%;margin-left: 550px;">
						<input type="button" value="添加" onclick="javascript:aa()">
					</div>
					<div class="right" id="right">
						<h2 style="font-size: 22px; color:white;font-weight: bold;">小区商品库</h2>
						<ul id="treeDemo2" class="ztree"></ul>
					</div>
					<div style="position:absolute;right:680px; top:350px;"></div>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>
</body>
<script type="text/javascript">
			var setting = {
				view: {
					// addHoverDom: addHoverDom,
					showTitle: true,
					removeHoverDom: removeHoverDom,
					expandSpeed: 200, // 展开树的时间
					// showIcon: showIconForTree, //是否显示图标
					selectedMulti: false  // 是否允许多选
				},
				edit: {
					enable: true, // 节点是否可编辑
					editNameSelectAll: true,
					showRemoveBtn: false,// 添加删除按钮
					showRenameBtn: false, // 不允许重命名
					drag: {
						isCopy : true,
						isMove : false,
						autoExpandTrigger: true,
						prev: false,// 允许向上拖动
						next: false,// 不允许向下拖动
						inner: false// 允许当前层次内进行拖动
						}
				},
				data: {
					simpleData: {
						enable: true,
						idKey : "id", // id编号命名
		                pIdKey : "pId", // 父id编号命名
		                rootId : 0  	
					},
					key: {
						title: "s"// 这里的s表示节点内的s属性
						}
				},
				callback: {
					beforeDrag: beforeDrag,
    				beforeDrop: beforeDrop,
    				beforeRemove:beforeRemove,
    				beforeDragOpen: beforeDragOpen,
    				onDrag: onDrag,
    				onDrop: onDrop,
    				onCheck:onCheck,
    				onExpand: onExpand
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "s" },
				},
				async : {    
		            enable : true,
		            url : "/qx_mgr/stock.do?method=list", // Ajax
																						// 获取数据的
																						// URL
																						// 地址
		            autoParam:["id", "name=n", "level=lv"],
		        }, 
			};

			// 第二个树
			var setting2 = {
					async : {
	            enable : true,
	            url : "/qx_mgr/stock.do?method=list2", // Ajax
																					// 获取数据的
																					// URL地址
	            autoParam:["id", "name=n", "level=lv"],
	        		}, 
	        		edit: {
	        			drag: {
							autoExpandTrigger: true,
							isMove : true,
							prev: false,// 允许向上拖动
							next: false,// 不允许向下拖动
							// inner: false
						},
	    				enable: true,
	    				// showRemoveBtn: false
	    				showRenameBtn:false
	    			},
	    			callback:{
	    				beforeDrag: beforeDrag2,
	    				beforeDrop: beforeDrop2,
	    				beforeRemove:beforeRemove,
	    				beforeDragOpen: beforeDragOpen,
	    				beforeCheck: beforeCheck
		    			}
				}

			function beforeCheck(treeId, treeNode) {
				className = (className === "dark" ? "":"dark");
				showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
				return (treeNode.doCheck !== false);
			}
			function onCheck2(e, treeId, treeNode,treeNodes) {
				alert(treeNode.name +" , "+treeNode.id+" , ");
			}
			
		var element = "";
		function onCheck(e,treeId,treeNode){
			var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
            nodes=treeObj.getCheckedNodes(true);
            var tempA = "";
            var tempB = "";
            var tempC = "";
            for(var x = 0 ; x < nodes.length ; x++){
            	tempA = "";
            	tempB = "";
            	if(nodes[x].isParent) {
            		tempA = nodes[x].name+":"+nodes[x].id;
            		if(nodes[x].children != null) {
            			for(var y = 0 ; y < nodes[x].children.length ; y++) {
            				if(nodes[x].children[y].checked)
            					tempB += append(nodes,x,y,nodes[x].children.length);
            			}
            		}
	        		tempC += tempA+"{"+tempB+"};";
            	}
	        }
	        element = "";
	        element += tempC;
		}
			
		//父节点与子节点拼接
		function append(nodes,x,y,l) {
			if(y == l - 1) 
				return nodes[x].children[y].name+"="+nodes[x].children[y].id;
			else 
				return nodes[x].children[y].name+"="+nodes[x].children[y].id+"_";
        }
        
 		function zTreeOnDrop2(event, treeId, treeNodes, targetNode, moveType) {
				alert(treeNodes[0].id);
			   alert(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ));
		}
		
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
				showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
			}
		}
		
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
			showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"))
		}
		
		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		
		function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType){
			var id = treeNodes[0].id;// 获得被拖拽的节点id
			var targetId = targetNode.id;
			var parentId = treeNode.getParentNode(); 
			alert(id);
			alert(targetId);
			alert(parentId);
		}
		
		var log, className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function beforeDragOpen(treeId, treeNode) {
	        autoExpandNode = treeNode;
	        return true;
	    }
	    
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
		}
		
		// 删除父节点 ，子节点 
		function beforeRemove(treeId, treeNode) {
		
			var parentId = "",parentPid = "",level="";
			
			var isParent=treeNode.isParent;
			
			parentId=treeNode.sId;
			alert(parentId);
			
			var boolean = confirm("确认删除---" + treeNode.name + "---吗？");
			if(boolean){
				$.ajax({
						type:	"post",
						url:	"${pageContext.request.contextPath}/stock.do?method=del",
						data:	{parentId:parentId,parentPid:parentPid,level:level,isParent:isParent},
						dataType:	"JSON",
						success:function(data){
							window.location.reload();
						},
						error:function(error){
							window.location.reload();
							alert("error");
						},
					});
				}else{
					return false;
				}
		}
		
		function onRemove(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		
		function onRename(e, treeId, treeNode, isCancel) {
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		
		function showRemoveBtn(treeId, treeNode) {
			return !treeNode.isFirstNode;
		}
		
		function showRenameBtn(treeId, treeNode) {
			return !treeNode.isLastNode;
		}
		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
				return false;
			});
		};
		
		function removeHoverDom(treeId, treeNode) {
		 	$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		};
		
		// 拖起1
		function beforeDrag(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		};
		
		// 拖下1
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {
			var id = treeNodes[0].id;
			$.ajax({
					type: "post",
					url:	"${pageContext.request.contextPath}/stock.do?method=add",
					data:	{id:id},
					dataType:	"json",
					success:	function(data){
							window.location.reload();
						},
					error:	function(error){
							window.location.reload();
							alert("error");	
						}
				})
		};
		
		// 拖起2
		function beforeDrag2(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		};
		
		// 拖下2
		function beforeDrop2(treeId, treeNodes, targetNode, moveType) {
			debugger;
			var id = "";
			if(null == targetNode ) return false;
			if(targetNode.level == 1) {//判断拖拽的节点为字节点。
				id = targetNode.getParentNode(targetNode.parentTId).id; //根据子节点找到父节点id编号
			} else {
				id = targetNode.id;
			} 
			var pid = targetNode.pid
			var childrenNodes = "";	 // 被选中的childrenId  -> 商品编号
			
			for (var i=0 ; i < treeNodes.length ; i++) {
				if(treeNodes[i].level == 1){
					childrenNodes += treeNodes[i].id+",";
				}
			 }
			 
			 if(childrenNodes.endsWith(",")) {
				childrenNodes = childrenNodes.substr(0,childrenNodes.length-1);
			 }
			 
			 $.ajax({
				type:	"post",
				url:	"${pageContext.request.contextPath}/stock.do?method=updata",
				data:	{id:id , pid:pid , childrenNodes:childrenNodes},
				success:	function(data){
					window.location.reload();
				},
					error:	function(err){
					window.location.reload();
				}
			})
		};
		
		function onDrag(event, treeId, treeNodes) {
	        className = (className === "dark" ? "" : "dark");
	    }
	    
		// /////////
		//点击点击操作
		function aa(){
			//var end = element.replace(/^(.*[\n])*.*(.|\n)$/g, "$2");  //截取最后以为","号。
			var temp ;
			if( element.trim().endsWith(";")) {
				temp =  element.substr(0,element.length - 1);
			}
			if(temp != null){
				$.post("${pageContext.request.contextPath}/stock.do?method=adds",{temp:temp},function(data) {
					if(data == "ok") {
						window.location.reload();
					}
				});
			}	
		}
		// ////////
		
		var zNodes;
		var zNodes2;
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
		});
		
</script>
</html>
