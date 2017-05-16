<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加机构</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/validation-0.3.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
</head>
<body style="min-width: 500px;">

 <div class="formbody">
    
    <div class="formtitle"><span id="title">资源信息</span></div>
    <form id="myForm" method="post" action="save.do">
    <ul class="forminfo">
    <li><label>资源名称<b>*</b></label><input disabled="disabled" name="name" type="text" class="dfinput" /><i id="msg_name"></i></li>
    <li><label>资源路径<b>*</b></label><input disabled="disabled" name="path" type="text" class="dfinput" /><i id="msg_path"></i></li>
    <li><label>权限</label><input disabled="disabled" name="permission" type="text" class="dfinput" /><i id="msg_permission"></i></li>
    <!-- <li><label>排序号码</label><input name="orderNo" type="text" class="dfinput" value="0" style="width: 150px;"/></li> -->
    <li><label>图标</label><input name="icon" type="text" class="dfinput" /><i>图标路径</i></li>
    <li><label>&nbsp;</label><input id="valid_submit" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>
    
    </div>
    
</body>
<script type="text/javascript">
	function setModuleInfo(node){
		$("input[name='name']").val(node.name2);
		$("input[name='name']").attr("title","名称可以在"+node.getParentNode().alias+"的"+node.alias+"方法上修改");
		$("input[name='path']").val(node.url);
		//$("input[name='orderNo']").val(node.orderNo);
		$("#title").html("『"+node.name2+"』");
	}
</script>
</html>