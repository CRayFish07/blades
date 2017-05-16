<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字典信息</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<style type="">
body
{
	min-width: 500px;
}
</style>
</head>

<body>

    <div class="formbody">
    
    <div class="formtitle"><span>『${dic.name }』</span></div>
    <form id="myForm" method="post" action="save.do">
    <ul class="forminfo">
    <li><label>字典名称:</label><label>${dic.name}</label></li>
    <li><label>字典别称:</label><label>${dic.alias}</label></li>
    <li><label>字典代码:</label><label>${dic.code}</label></li>
    <li><label>外部代码:</label><label>${dic.fcode}</label></li>
    <li><label>采用标准:</label><label>${dic.standard}</label></li>
    <li><label>字典分类:</label><label>${dic.classify}</label></li>
    <li><label>系统启动加载:</label><label>${dic.cacheAble}</label></li>
    <li><label>排序号码:</label><label>${dic.orderNo}</label></li>
    <li><label>图标:</label><label>${dic.icon}</label></li>
    <li><label>字典描述:</label><label>${dic.description }</label></li>
    </ul>
    </form>
    
    </div>


</body>

</html>

