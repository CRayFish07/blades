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
			pid:'${pdic.id }'
		},
		rightInfo:"✔",
		success:function(){
			layer.msg("添加成功！");
			if("${tree}"==""){
				window.location.href="list.do?pid=${pdic.id}";
			}else{
				$(window.parent.document).contents().find("#leftFrame")[0].contentWindow.dirReloadAsyncCurrentNode("add");
				window.location.href="tree_list.do?pid=${pdic.id}";
			}
		},
		before:function(){load_index=layer.load()},
		finish:function(){layer.close(load_index)}
	});
	
});

</script>
</head>

<body>

	<#if tree!=null>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li>系统管理</li>
    <li><a href="./list.do">字典管理</a></li>
    <#if pdic!=null>
    <li><a href="list.do?pid=${pdic.id }">${pdic.name }</a></li>
    </#if>
    </ul>
    </div>
    </#if>
    <div class="formbody">
    
    <div class="formtitle"><span>添加<#if pdic!=null>『${pdic.name }』子项</#if>字典</span></div>
    <form id="myForm" method="post" action="save.do">
    <ul class="forminfo">
    <li><label>字典名称<b>*</b></label><input name="name" type="text" class="dfinput" /><i id="msg_name">名称不能超过30个字符</i></li>
    <li><label>字典别称</label><input name="alias" type="text" class="dfinput" /><i id="msg_alias"></i></li>
    <li><label>字典代码<b>*</b></label><input name="code" type="text" class="dfinput" /><i id="msg_code">字典代码是唯一的</i></li>
    <li><label>外部代码</label><input name="fcode" type="text" class="dfinput" /><i id="msg_fcode">外部代码（默认值=代码）</i></li>
    <li><label>采用标准<b>*</b></label>  
	    <div class="vocation">
		    <select class="select1" name="standard">
			    <option value="01">标准</option>
			    <option value="04">自定义</option>
		    </select>
	    </div>
    </li>
    <li><label>字典分类<b>*</b></label>  
	    <div class="vocation">
		    <select class="select1" name="classify">
				<@dict.getOptions type="DIC_TYPE"/>
		    </select>
	    </div>
    </li>

    <li><label>系统启动加载</label><cite><input name="cacheAble" type="radio" value="Y" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;<input name="cacheAble" type="radio" value="N" />否</cite></li>
    <li><label>排序号码</label><input name="orderNo" type="text" class="dfinput" value="0" style="width: 150px;"/></li>
    <li><label>图标</label><input name="icon" type="text" class="dfinput" /><i>图标路径</i></li>
    <li><label>字典描述</label><textarea name="description" cols="" rows="" class="textinput"></textarea></li>
    <li><label>&nbsp;</label><input id="valid_submit" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>
    
    </div>


</body>

</html>

