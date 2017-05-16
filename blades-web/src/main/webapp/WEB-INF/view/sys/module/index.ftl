<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>权限管理</title>
<link rel="stylesheet"
	href="${ctx }/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">

<script type="text/javascript"
	src="${ctx }/resource/plugins/zTree_v3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resource/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${ctx }/resource/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="${ctx }/resource/plugins/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<SCRIPT type="text/javascript">
	var setting = {
		async:{
			enable:true,
			url:"${ctx }/module/getModuleTreeJson.do",
			autoParam:["id","name"]
		}
		,  
        data: {  
            simpleData: {  
                enable: true  
            }  
        } , 
		view : {
			dblClickExpand : true,
			nameIsHTML: true
		},
		check : {
			enable : true
		},
		callback : {
			onRightClick : OnRightClick,
			onClick : OnClick
		}
	};

	var zNodes = [];

	function OnRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button"
				&& $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}
	
	function OnClick(event, treeId, treeNode){
		mainIframe.window.setModuleInfo(treeNode.name2,treeNode.path);
	}

	function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type == "root") {
			$("#m_del").hide();
			$("#m_check").hide();
			$("#m_unCheck").hide();
		} else {
			$("#m_del").show();
			$("#m_check").show();
			$("#m_unCheck").show();
		}
		rMenu.css({
			"top" : y + "px",
			"left" : x + "px",
			"visibility" : "visible"
		});

		$("body").bind("mousedown", onBodyMouseDown);
	}
	function hideRMenu() {
		if (rMenu)
			rMenu.css({
				"visibility" : "hidden"
			});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event) {
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
			rMenu.css({
				"visibility" : "hidden"
			});
		}
	}
	var addCount = 1;
	function addTreeNode() {
		hideRMenu();
		var newNode = {
			name : "增加" + (addCount++)
		};
		if (zTree.getSelectedNodes()[0]) {
			newNode.checked = zTree.getSelectedNodes()[0].checked;
			zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
	function removeTreeNode() {
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length > 0) {
			if (nodes[0].children && nodes[0].children.length > 0) {
				var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
				if (confirm(msg) == true) {
					zTree.removeNode(nodes[0]);
				}
			} else {
				zTree.removeNode(nodes[0]);
			}
		}
	}
	function checkTreeNode(checked) {
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length > 0) {
			zTree.checkNode(nodes[0], checked, true);
		}
		hideRMenu();
	}
	function resetTree() {
		hideRMenu();
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}

	var zTree, rMenu;
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		rMenu = $("#rMenu");
	});
</SCRIPT>
<style type="text/css">
.module_left {
	width: 350px;
	height: 600px;
	border: 1px solid #CCC;
	float: left;
	overflow: auto;
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #555;
	text-align: left;
	padding: 2px;
}

div#rMenu ul {
	padding: 0px;
	margin: 0px;
}

div#rMenu ul li {
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
div#main{
	border: 1px #CCC solid;
	width: 1000px;
	height: 600px;
	float: left;
}
</style>
</head>
<body>
	<div class="module_left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div id="rMenu">
		<ul>
			<li id="m_add" onclick="addTreeNode();">增加节点</li>
			<li id="m_del" onclick="removeTreeNode();">删除节点</li>
			<li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
			<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>
			<li id="m_reset" onclick="resetTree();">恢复zTree</li>
		</ul>
	</div>
	<div id="main">
		<iframe name="mainIframe" width="100%" height="100%" frameborder="0" src="${ctx }/module/edit.do"></iframe>
	</div>
</body>
</html>