<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色信息</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<style type="">
body {
	min-width: 500px;
}
</style>
</head>

<body>

	<div class="formbody">

		<div class="formtitle">
			<span>『${role.name }』的资料</span>
		</div>
		<form id="myForm" method="post" action="save.do">
			<ul class="forminfo">
				<li><label>角色名称:</label><label>${role.name}</label></li>
				<li><label>角色别称:</label><label>${role.alias}</label></li>
				<li><label>角色代码:</label><label>${role.code}</label></li>
				<li><label>是否有效:</label><label>${role.enbaled =='1'? '是' : '否'}</label></li>
				<li><label>排序号码:</label><label>${role.orderNo}</label></li>
				<li><label>更新时间:</label><label>${role.updateTime}</label></li>
				<li><label>描述:</label><label>${role.description}</label></li>
			</ul>
		</form>

	</div>


</body>

</html>

