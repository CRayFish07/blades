<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加用户</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/validation-0.3.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/Organization.js"></script>
<!-- 时间控件 -->
<script type="text/javascript" src="${ctx}/resource/plugins/calendar/WdatePicker.js"></script>
<script type="text/javascript">
var load_index;
var choose_index;
$(document).ready(function(e) {
	$(".select1").uedSelect({
		width : 150
	});
	
	//表单一(单属性校验)
	new Validation().init({
		formId:"myForm",
		submitId:"valid_submit",
		singleName:["username","password","passwordConfirm:password","name","idNo","gender","mobile","email","phone","officePhone"],
		rightInfo:"✔",
		success:function(){
			layer.msg("添加成功！");
			window.location.href="list.do";
		},
		before:function(){load_index=layer.load()},
		finish:function(p){layer.close(load_index)}
	});
	
	//组织机构选择器
	new Organization().init({
		chooseId : 'affiliation',
		labelId : 'label_affiliation',
		valueId : 'input_affiliation',
		root:'${ctx}',
		title : "请选择隶属单位",
		valueProperty : "id"
	});
	
	new Organization().init({
		chooseId : 'partTime',
		labelId : 'label_partTime',
		valueId : 'input_partTime',
		root:'${ctx}',
		title : "请选择兼职单位",
		checkbox:true,
		valueProperty : "id"
	});
	
	new Organization().init({
		chooseId : 'manage',
		labelId : 'label_manage',
		valueId : 'input_manage',
		root:'${ctx}',
		title : "请选择分管单位",
		checkbox:true,
		valueProperty : "id"
	});

});


</script>
</head>

<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li>系统管理</li>
			<li><a href="./list.do">用户管理</a></li>
		</ul>
	</div>
	<div class="formbody">

		<div class="formtitle">
			<span>添加用户</span>
		</div>
		<form id="myForm" method="post" action="save.do">
			<ul class="forminfo">
				<div class="formtitle">
					<span>1.基本资料</span>
				</div>
				<li><label>账号<b>*</b></label><input name="username" type="text" class="dfinput" /><i id="msg_username">默认为身份证号码</i></li>
				<li><label>密码<b>*</b></label><input name="password" type="password" class="dfinput" /><i id="msg_password">默认为123456</i></li>
				<li><label>确认密码<b>*</b></label><input name="passwordConfirm:password" type="password" class="dfinput" /><i
					id="msg_passwordConfirm"></i></li>
				<li><label>手机号码</label><input name="mobile" type="text" class="dfinput" /><i id="msg_mobile"></i></li>
				<li><label>邮箱</label><input name="email" type="text" class="dfinput" /><i id="msg_email"></i></li>

				<div class="formtitle">
					<span>2.详细资料</span>
				</div>
				<li><label>姓名<b>*</b></label><input name="name" type="text" class="dfinput" /><i id="msg_name"></i></li>
				<li><label>性别<b>*</b></label>
				<div class="vocation">
						<select class="select1" name="gender">
							<option value="">请选择</option>
							<option value="01">男</option>
							<option value="02">女</option>
							<option value="03">其它</option>
						</select>
					</div> <i id="msg_gender" style="line-height: 35px;"></i></li>
				<li><label>工号</label><input name="code" type="text" class="dfinput" style="width: 150px;" /><i id="msg_code"></i></li>
				<li><label>身份证号</label><input name="idNo" type="text" class="dfinput" /><i id="msg_idNo"></i></li>
				<li><label>出生日期</label><input name="birthdate" type="text" class="dfinput"
					onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width: 150px;" /><i id="msg_code"></i></li>
				<li><label>办公电话</label><input name="officePhone" type="text" class="dfinput" /><i id="msg_officePhone"></i></li>
				<li><label>家庭电话</label><input name="phone" type="text" class="dfinput" /><i id="msg_phone"></i></li>
				<li><label>家庭住址</label><input name="address" type="text" class="dfinput" /></li>
				<li><label>备注</label> <textarea name="remark" cols="" rows="" class="textinput"></textarea></li>

				<div class="formtitle">
					<span>3.用户调岗（选填）</span>
				</div>
				<li><label>隶属</label> <label id="label_affiliation" style="width: auto;"></label> <input type="hidden"
					name="affiliation" id="input_affiliation" /> <a href="javascript:void(0)" id="affiliation"
					style="line-height: 35px; color: #00a4ac;">选择</a></li>
				<li><label>兼职</label> <label id="label_partTime" style="width: auto;"></label> <input type="hidden"
					name="partTime" id="input_partTime" /> <a href="javascript:void(0)" id="partTime"
					style="line-height: 35px; color: #00a4ac;">选择</a></li>
				<li><label>分管</label> <label id="label_manage" style="width: auto;"></label> <input type="hidden" name="manage"
					id="input_manage" /> <a href="javascript:void(0)" id="manage" style="line-height: 35px; color: #00a4ac;">选择</a></li>
				<li><label>&nbsp;</label><input id="valid_submit" name="" type="button" class="btn" value="确认保存" /></li>
			</ul>
		</form>

	</div>


</body>

</html>

