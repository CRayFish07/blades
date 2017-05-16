<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字典信息</title>
<link rel="stylesheet" href="${ctx}/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery.ztree.all.js"></script>

<script type=text/javascript>
	//var zTree = $.fn.zTree.getZTreeObj('treeId');
	var zTree;
	var rightMenu1;
	var rightMenu2;
	var setting = {
		async : {
			enable : true,
			url : "tree.do?pid=${dic.id}",
			autoParam : [ "id", "name" ]
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : zTreeOnClick,
			onRightClick:rightClick
		}
	};
	var zNodes = [];

	//鼠标单击树时调用的函数
	function zTreeOnClick(event, treeId, treeNode) {	
		var url = "tree_list.do?pid="
				+ treeNode.id;
		parent.document.getElementById("rightFrame").src = url;
	}

	//鼠标右击树时调用的函数
	function rightClick(event, treeId, treeNode) {
		$("#id").val(treeNode.id);
		zTree.selectNode(treeNode);
		if (treeNode.pId == null) {
			rightMenu2.css({
				"top" : event.clientY + "px",
				"left" : event.clientX + 6 + "px",
				"display" : "block"
			});
		} else {
			rightMenu1.css({
				"top" : event.clientY + "px",
				"left" : event.clientX + 6 + "px",
				"display" : "block"
			});
		}
		$("body").bind("mousedown", onBodyMouseDown);

	}

	//鼠标点击树界面的任意一个地方时调用的函数
	function onBodyMouseDown(event) {
		if (!(event.target.id == "rightMenu1" || $(event.target).parents(
				"#rightMenu1").length > 0)) {
			rightMenu1.css({
				"display" : "none"
			});
		}
		if (!(event.target.id == "rightMenu2" || $(event.target).parents(
				"#rightMenu2").length > 0)) {
			rightMenu2.css({
				"display" : "none"
			});
		}
	}

	var unitCh = "树";
	function openUrl(operation) {
		var tId = $("#id").val();
		if (operation == "add") {
			var url = "${applicationScope.appRoot}/fzjc/zyztk/txs/toAdd.xhtml?id="
					+ tId;
			openWin(url, unitCh, 400, 250);
		} else if (operation == "edit") {
			var url = "${applicationScope.appRoot}/fzjc/zyztk/txs/toEdit.xhtml?id="
					+ tId;
			openWin(url, unitCh,400, 250);
		} else if (operation == "delete") {
			var url = "${applicationScope.appRoot}/fzjc/zyztk/txs/doDelete.xhtml";
			doTreeDelete(url, tId);
		}
		rightMenu1.css({
			"display" : "none"
		});
		rightMenu2.css({
			"display" : "none"
		});
	}

	//初始化树
	$(window).ready(function() {
		
		zTree = $.fn.zTree.init($("#ztreeData"), setting, zNodes);
		//zTree.expandAll(true);////////
		//rightMenu1 = $("#rightMenu1");
		//rightMenu2 = $("#rightMenu2");
		
	});

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
	
	function a(obj){
		obj.style.backgroundColor='#F5EDA6';
	}
	
	function b(obj){
		obj.style.backgroundColor='#FFFFFF';
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
		<ul id="ztreeData" class="ztree"></ul>
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