<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加字典</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/validation-0.3.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
<script type="text/javascript">
var load_index;
$(document).ready(function(e) {
	$(".select1").uedSelect({
		width : 150
	});
	
	//表单一(单属性校验)
	new Validation().init({
		formId:"myForm",
		submitId:"valid_submit",
		singleName:["name","code"],
		param:{
			id:'${org.id}'
		},
		rightInfo:"✔",
		success:function(){
			layer.msg("添加成功！");
			$(window.parent.document).contents().find("#leftFrame")[0].contentWindow.dirReloadAsyncCurrentNode("add");
			window.location.href="list.do?pid=${porg.id}";
		},
		before:function(){load_index=layer.load()},
		finish:function(){layer.close(load_index)}
	});
});

function isOk(){
	layer.msg("保存成功！");
	$(window.parent.document).contents().find("#leftFrame")[0].contentWindow.dirReloadAsyncCurrentNode("add");
	window.location.href="list.do?pid=${org.pid}";
}
</script>
</head>

<body>
    
    <div class="formbody">
    
    <div class="formtitle"><span>『${org.name }』信息编辑</span></div>
    <form id="myForm" method="post" action="update.do">
    <ul class="forminfo">
   	<li><label>机构名称<b>*</b></label><input name="name" type="text" class="dfinput" value="${org.name }" /><i id="msg_name">名称不能超过30个字符</i></li>
    <li><label>机构别称</label><input name="alias" type="text" class="dfinput" value="${org.alias }" /><i id="msg_alias"></i></li>
    <li><label>机构代码<b>*</b></label><input name="code" type="text" class="dfinput" value="${org.code }" /><i id="msg_code">代码是唯一的</i></li>
    <li><label>外部代码</label><input name="fcode" type="text" class="dfinput" value="${org.fcode }" /><i id="msg_fcode">外部代码（默认值=代码）</i></li>
    <li><label>机构类型<b>*</b></label>  
	    <div class="vocation">
		    <select class="select1" name="orgType">
			    <option value="">请选择</option>
			    <option value="0">机构</option>
			    <option value="1">单位</option>
			    <option value="2">部门</option>
			    <option value="3">岗位</option>
			    <option value="4">人员</option>
			    <option value="9">分组</option>
		    </select>
	    </div>
	    <i id="msg_orgType" style="line-height: 35px;"></i>
    </li>

    <li><label>排序号码</label><input name="orderNo" type="text" class="dfinput" value="${org.orderNo }" style="width: 150px;"/></li>
    <li><label>图标</label><input name="icon" type="text" class="dfinput" value="${org.icon }"/><i>图标路径</i></li>
    <li><label>&nbsp;</label><input id="valid_submit" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>
    
    </div>


</body>

</html>

