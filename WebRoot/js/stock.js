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
			
		// 选中checkbox 的id
		var node_id = []; 	// 父节点id
		var node_pid = [];	// 子节点id
		var name_id = [];	// 父节点name
		var name_pid = [];	// 子节点name
		function onCheck(e,treeId,treeNode){
				// debugger;
				node_id = [];
				node_pid = [];
				name_id = [];
				name_pid = [];
				var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
	            nodes=treeObj.getCheckedNodes(true),
	            v="";
	            for(var i=0;i<nodes.length;i++){
		            if(nodes[i].isParent){
			            if(nodes.length-1 == i){
			            	 // 获取选中节点的值
			            	if(nodes[i].isParent){
			            		node_id += nodes[i].id;
			            		v+=nodes[i].name+"_"+nodes[i].id;
			            		name_id = v;
							}else if(!nodes[i].isParent){
								node_pid += nodes[i].id;
								name_pid += nodes[i].name;
							}
			            }else{
			            	if(nodes[0].isParent){
			            		node_id += nodes[i].id+",";
			            		v+=nodes[i].name+"_"+nodes[i].id+",";
			            		name_id = v;
							}else if(!nodes[i].isParent){
								node_pid += nodes[i].id;
								v+=nodes[i].name;
								name_pid = v;
							}
			            }
	        		}else{
	        			if(nodes.length-1 == i){
		        			node_pid += nodes[i].id+"_"+nodes[i].pId;
							name_pid += nodes[i].name+"_"+nodes[i].pId;
	        			}else{
	        				node_pid += nodes[i].id+"_"+nodes[i].pId+",";
							name_pid += nodes[i].name+"_"+nodes[i].pId+",";
		        		}
			        }
				}
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
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var id = treeNode.id;
			var pid = treeNode.pid;
			zTree.selectNode(treeNode);
			var boolean = confirm("确认删除---" + treeNode.name + "---吗？");
			if(boolean){
				$.ajax({
						type:	"post",
						url:	"${pageContext.request.contextPath}/stock.do?method=del",
						data:	{id:id,pid:pid},
						dataType:	"JSON",
						success:function(data){
							// window.location.reload();
							// var num = data;
							// if(num != 1) alert("删除失败！")
						},
						error:	function(error){
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
			// debugger;
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		};
		// 拖下2
		function beforeDrop2(treeId, treeNodes, targetNode, moveType) {
			// debugger;
			var treeNodesId = treeNodes[0].id; // 自己的id
			if(null == targetNode ) return false;
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if(targetNode.level != 0){
					return false;
				}else{
					var targetNodeId = targetNode.id; // 父类的id
					$.ajax({
						type:	"post",
						url:	"${pageContext.request.contextPath}/stock.do?method=updata",
						data:	{targetNodeId:targetNodeId,treeNodesId:treeNodesId},
						success:	function(data){
							window.location.reload();
						},
							error:	function(err){
							window.location.reload();
						}
					})
				}
			 }
			
		};
		function onDrag(event, treeId, treeNodes) {
	        className = (className === "dark" ? "" : "dark");
	    }
		// /////////
		function aa(){
			if(node_id != 0){
				$.ajax({
					type:	"post",
					data:	{node_id,node_pid,name_id,name_pid},
					url:	"${pageContext.request.contextPath}/stock.do?method=adds",
					success:	function(data){
							window.location.reload();
						},
					error:	function(err){
							alert("error");
							window.location.reload();
						},
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