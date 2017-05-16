<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源树</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
<script type=text/javascript>

var load_index;
	var zTree;
	var rightMenu1;
	var rightMenu2;
	var setting = {
		async : {
			enable : true,
			url : "tree.do",
			autoParam : [ "id", "name" ]
		},
		edit: {
			drag: {
				autoExpandTrigger: true,
				prev: dropPrev,
				inner: dropInner,
				next: dropNext
			},
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		data : {
			simpleData : {
				enable : true
			}
		}, 
		view : {
			dblClickExpand : true,
			nameIsHTML: true
		},
		check : {
			enable : true
		},
		callback : {
			onClick : zTreeOnClick,
			beforeDrag: beforeDrag,
			beforeDrop: beforeDrop,
			beforeDragOpen: beforeDragOpen,
			onDrag: onDrag,
			onDrop: onDrop,
			onExpand: onExpand,
			onAsyncSuccess: zTreeOnAsyncSuccess
		}
	};
	var zNodes = [];

	//鼠标单击树时调用的函数
	function zTreeOnClick(event, treeId, treeNode) {
		$(window.parent.document).contents().find("#rightFrame")[0].contentWindow.setModuleInfo(treeNode);
	}

	function dirReloadAsyncCurrentNode(op) {
		var treeNodes = zTree.getSelectedNodes();
		if (treeNodes.length > 0) {
			if (op != null && "add" == op) {
				treeNodes[0].isParent = true; //节点下添加则该节点属性改为父节点
				zTree.reAsyncChildNodes(treeNodes[0], "refresh");
			} else if (op != null && "edit" == op) {
				//刷新父节点
				if (treeNodes[0].level == 0) { //判断是否是顶级节点
					zTree.reAsyncChildNodes(null, "refresh");
				} else {
					zTree.reAsyncChildNodes(treeNodes[0].getParentNode(),
							"refresh");
				}
			} else if (op != null && "delete" == op) {
				zTree.removeNode(treeNodes[0]);
			}
		}else{
			zTree.reAsyncChildNodes(null, "refresh");
		}
	}
	
	
	//拖拽
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
		
		var log, className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes." );
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
			showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"));
			return true;
		}
		function onDrag(event, treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes." );
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			//排序异步更新数据
			var ids="";
			var nodes=treeNodes[0].getParentNode().children;
			for(var i=0;i<nodes.length;i++){
				ids+=nodes[i].id+",";
			}
			doSort(ids);
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
			showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"))
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
				showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
			}
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
		
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			layer.close(load_index);
			layer.msg("加载完成！");
		};
		
		//初始化树
		$(window).ready(function() {
			
			zTree = $.fn.zTree.init($("#ztreeData"), setting, zNodes);
			$("#zTreeContainer").height($(document).height()-15);
			load_index=layer.load();
		});
		
		function updateAll(){
			load_index=layer.load();
			$.ajax({
					type: "POST",
					url:"updateAll.do",
					async: true,
				    error: function(request) {
				    	layer.msg("服务器繁忙！");
				    	layer.close(load_index)
				    },
				    success: function(res) {
						layer.msg("更新成功");
						window.location.reload();
				    }
			});
		}
		
		function doSort(ids){
			load_index=layer.load();
			$.ajax({
					type: "POST",
					url:"sort.do",
					data:"ids="+ids,
					async: true,
				    error: function(request) {
				    	layer.msg("服务器繁忙！");
				    	layer.close(load_index)
				    },
				    success: function(res) {
						layer.msg("排序成功");
				    	layer.close(load_index)
				    }
			});
		}
		
	function recycle(){
		window.parent.window.location.href="${ctx}/sys/module/recycle.do";
	}
</script>
<style type="text/css">
   body{
   overflow: scroll;
   overflow-x:hidden;
   scrollbar-face-color:#e8e7e7;
   scrollbar-highlight-color:#ffffff;
   scrollbar-shadow-color:#ffffff;
   scrollbar-3dlight-color:#cccccc;
   scrollbar-arrow-color:#03b7ec;
   scrollbar-track-coloe:#efefef;
   scrollbar-darkshadow-color:#b2b2b2;
   scrollbar-base-color:#000000;
   min-width: 50px;
   }
   .tree_top{
   		width: 100%;
   		height: 40px;
   		border: 1px #CCC solid;
   		background-color: #3893C8;
   }
   .left-title
   {
   		width:100%;
		height:26px;
		background-image:url(${applicationScope.appRoot}/resource/cms/img/index/left1.jpg);
		background-repeat:no-repeat;
		float:left;
		background-color: #3C96C8;
   }
   .left-title span{
	display:inline-block;
	width:150px;
	height:26px;
	text-align:left;
	color:#FFFFFF;
	font-size:14px;
	font-weight:bold;
	margin-top:14px;
	margin-left:37px;
}
</style>
</head>
<body>
	<div id="zTreeContainer" style="height: 92%;overflow: auto;">
		<ul class="toolbar" style="padding: 10px;padding-right: 0px;">
			<li onclick="updateAll()"><span><img src="${ctx}/resource/sys/images/lc04.png" width="24px" /></span>更新</li>
			<li class="enabled" onclick="recycle()"><span><img src="${ctx}/resource/sys/images/i04.png" width="24px" /></span>回收站</li>
			<#--<li ><span><img src="${ctx}/resource/sys/images/d03.png" width="24px" /></span>帮助</li>-->
		</ul>
		<ul id="ztreeData" class="ztree" style="float: left;"></ul>
	</div>
	<input type="hidden" id="id" name="id" />
	<div id="rightMenu1"
		style="position: absolute; display: none; background-color: #FFFFFF">
		<table width="60" border="1" class="rightMenu">
			<tr onmouseover="a(this)" onmouseout="b(this)">
				<td onclick="javascript:openUrl('add')" style="cursor: pointer;">增&nbsp;&nbsp;加</td>
			</tr>
			<tr onmouseover="a(this)" onmouseout="b(this)">
				<td onclick="javascript:openUrl('edit')" style="cursor: pointer;">修&nbsp;&nbsp;改</td>
			</tr>
			<tr onmouseover="a(this)" onmouseout="b(this)">
				<td onclick="javascript:openUrl('delete')" style="cursor: pointer;">删&nbsp;&nbsp;除</td>
			</tr>
		</table>
	</div>
	<div id="rightMenu2"
		style="position: absolute; display: none; background-color: #FFFFFF">
		<table width="60" border="1" class="rightMenu">
			<tr onmouseover="a(this)" onmouseout="b(this)">
				<td onclick="javascript:openUrl('add')" style="cursor: pointer;">增&nbsp;&nbsp;加</td>
			</tr>
			<tr onmouseover="a(this)" onmouseout="b(this)">
				<td onclick="javascript:openUrl('edit')" style="cursor: pointer;">修&nbsp;&nbsp;改</td>
			</tr>
		</table>
	</div>
</body>
</html>