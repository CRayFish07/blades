<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择组织机构</title>
<link rel="stylesheet" href="${ctx}/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>

<script type=text/javascript>
	//var zTree = $.fn.zTree.getZTreeObj('treeId');
	var zTree;
	var rightMenu1;
	var rightMenu2;
	var setting = {
		async : {
			enable : true,
			url : "tree.do?pid=${org.id}",
			autoParam : [ "id", "name" ]
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : zTreeOnClick,
		}
	};
	var zNodes = [];

	//鼠标单击树时调用的函数
	function zTreeOnClick(event, treeId, treeNode) {
		if(treeNode.type=="3"||treeNode.type=="4"||treeNode.type=="9"){
			$("#label_affiliation",window.parent.document).html(treeNode.getParentNode().name+"["+treeNode.name+"]");
		}
	}


	//初始化树
	$(window).ready(function() {
		
		zTree = $.fn.zTree.init($("#ztreeData"), setting, zNodes);
		
	});

	
</script>
<style type="text/css">
</style>
</head>
<body>
	<div id="zTreeContainer" style="height: 92%;overflow: auto;">
		<ul id="ztreeData" class="ztree"></ul>
	</div>
</body>
</html>