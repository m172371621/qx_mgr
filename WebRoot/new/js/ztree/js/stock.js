var setting = {   // 树1
				view: {
					showTitle: true,
					removeHoverDom: removeHoverDom,
					expandSpeed: 200, // 展开树的时间
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
					}
				},
				callback: {
					beforeDrag: beforeDrag,
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
				keep: {
					parent: true
				}
			};
			
			// 第二个树
			var setting2 = {
	        		edit: {
						showRemoveBtn: false,// 添加删除按钮
	        			drag: {
							autoExpandTrigger: true,
							isMove : true,
							prev: false,// 允许向上拖动
							next: false,// 不允许向下拖动
							inner: true// 允许当前层次内进行拖动
						},
	    				enable: true,
	    				showRenameBtn:false
	    			},

	    			data: {
						keep: {
							parent: true
						},
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

		// 父节点与子节点拼接
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
	    
		// 删除父节点 ，子节点 
		function beforeRemove(treeId, treeNode) {
		
			var parentId = "",parentPid = "",level="";
			
			var isParent=treeNode.isParent;
			
			parentId=treeNode.id;
			
			var boolean = confirm("确认删除---" + treeNode.name + "---吗？");
			if(boolean){
				$.ajax({
						type:	"post",
						url:	ctx +"/stock.do?method=del",
						data:	{parentId:parentId,parentPid:parentPid,level:level,isParent:isParent},
						dataType:	"JSON",
						success:function(data){
							//window.location.reload();
						},
						error:function(error){
//							window.location.reload();
//							alert("error");
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
		
		// 拖起2
		function beforeDrag2(treeId, treeNodes,targetNode) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].level == 0) {
					return false;
				}
			}
		};
		
		// 拖下2
		function beforeDrop2(treeId, treeNodes, targetNode, moveType) {
			if(targetNode.isParent == false) return false;
			if(targetNode == treeNodes) return false;
			if(targetNode == null) return false;
			var childrenId = targetNode.id;  // 父节点
			var parentId = zuZhuang(treeNodes);
			$.ajax({
				type:	"post",
				url:	ctx + "/stock.do?method=updata",
				data:	{childrenId:childrenId,parentId:parentId},
				success:	function(data){
					zNodesData2();

				},
					error:	function(err){
				}
			})
			
		};
		
		function zuZhuang(treeNodes) {
			var temp = "";
			for(var i = 0; i < treeNodes.length; i++) {
				temp += treeNodes[i].sId+",";
			};
			return temp;
		};
		
		function onDrag(event, treeId, treeNodes) {
	        className = (className === "dark" ? "" : "dark");
	    }
	    
		// /////////
		// 点击点击操作
		function adds(){
			// 添加一个节点
			var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
			var treeObj2=$.fn.zTree.getZTreeObj("treeDemo2");
            var nodes =treeObj.getCheckedNodes(true); 	
            var retArr=[];
            for(var i=0;i<nodes.length;i++){
            	var retObj={};
            	retObj.isParent=nodes[i].isParent;
            	retObj.id=nodes[i].id;
            	retObj.name=nodes[i].name;
            	retObj.sId=nodes[i].sId;
            	retObj.pId=nodes[i].pId;
            	retArr.push(retObj);
            }
			var index = layer.load(0, {
				shade: [0.5,'#ccc',{time:5*1000}] //0.1透明度的白色背景
			});
			$.post(ctx + '/stock.do?method=adds', {nodes:JSON.stringify(retArr)}, function(data) {
				zNodesData2();
				layer.close(index);
				layer.msg("添加成功!");
			});

			// 此处进行统一的数据库的增加操作
/*
 * for(var x = 0; x <nodes.length; x++) { var node = nodes[x];
 * if(nodes[x].pId==null) {// 表示是父节点 // 1.判断小区是否存在该分类，如果存在则不添加，如果不存在则添加 if
 * (treeObj2.getNodeByParam("name", nodes[x].name, null) == null) { // 添加该分类 var
 * newNode = {name : nodes[x].name, tt : nodes[x].tId}; treeObj2.addNodes(null,
 * newNode); } }else{// 表示是子节点 // 判断右边的树是否存在该商品，如果不存在则添加该商品到分类下面，如果存在，则不添加
 * if(treeObj2.getNodeByParam("name",nodes[x].name,null) == null){ var newNode =
 * {name : nodes[x].name}; treeObj2.addNodes(treeObj2.getNodeByParam("tt",
 * nodes[x].getParentNode().tId), newNode); } } }
 */
		}
		
		
		// ////////
		function zNodesData() {
			var zNodes ;
			$.ajax({
				type:"post",
				url:ctx + "/stock.do?method=list",
				ContentType: "application/json; charset=utf-8",
				success:function(data) {
					zNodes = data;
					 $.fn.zTree.init($("#treeDemo"), setting, eval('(' + zNodes + ')'));
				},
				error:function(msg) {
					alert("error");
				}
			});
		}
		
		function zNodesData2() {
			var zNodes2 ;
			$.ajax({
				type:"post",
				url:ctx + "/stock.do?method=list2",
				ContentType: "application/json; charset=utf-8",
				success:function(data) {
					zNodes2 = eval('(' + data + ')');
					$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
				},
				error:function(msg) {
				}
			});
		}
		
		$(document).ready(function(){
			zNodesData();
			zNodesData2();
		});
		