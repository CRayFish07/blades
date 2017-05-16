<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑角色</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/validation-0.3.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
<script type="text/javascript">
	var load_index;
	$(document).ready(function(e) {

		//表单一(单属性校验)
		new Validation().init({
			formId : "myForm",
			submitId : "valid_submit",
			singleName : [ "name", "code" ],
			param : {
				id : '${role.id }'
			},
			rightInfo : "✔",
			success : function() {
				layer.msg("修改成功！");
				window.location.href = "${ctx }/sys/role/list/${sort}.do?enable=${role.enbaled}";
			},
			before : function() {
				load_index = layer.load()
			},
			finish : function() {
				layer.close(load_index)
			}
		});

	});
</script>
</head>

<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="${ctx }/sys/role/list/${sort}.do">角色管理</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>『${role.name }』信息编辑</span>
		</div>
		<form id="myForm" method="post" action="${ctx }/sys/role/update.do">
			<ul class="forminfo">
				<li><label>角色名称<b>*</b></label><input name="name" type="text" class="dfinput" value="${role.name }"/><i id="msg_name">名称不能超过30个字符</i></li>
				<li><label>角色别称</label><input name="alias" type="text" class="dfinput" value="${role.alias }"/><i id="msg_alias">昵称</i></li>
				<li><label>角色代码<b>*</b></label><input name="code" type="text" class="dfinput" value="${role.code }"/><i id="msg_code">角色代码是唯一的</i></li>
				<li><label>排序号码</label><input name="orderNo" type="text" class="dfinput" value="${role.orderNo }"/><i id="msg_orderNo">排序号码</i></li>
				<li><label>描述</label><input name="description" type="text" class="dfinput" value="${role.description }"/><i id="msg_description">角色描述</i></li>
				<li><label>&nbsp;</label><input id="valid_submit" name="" type="button" class="btn" value="确认保存" /></li>
			</ul>
		</form>

	</div>

</body>

</html>

