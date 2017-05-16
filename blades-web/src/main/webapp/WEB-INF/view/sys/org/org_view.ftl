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
    
    <div class="formtitle"><span>『${org.name }』</span></div>
    <form id="myForm" method="post" action="save.do">
    <ul class="forminfo">
    <li><label>机构名称:</label><label>${org.name}</label></li>
    <li><label>机构别称:</label><label>${org.alias}</label></li>
    <li><label>机构代码:</label><label>${org.code}</label></li>
    <li><label>外部代码:</label><label>${org.fcode}</label></li>
    <li><label>机构类型:</label><label>${org.orgType}</label></li>
    <li><label>级别:</label><label>${org.levels}</label></li>
    <li><label>排序号码:</label><label>${org.orderNo}</label></li>
    <li><label>图标:</label><label>${org.icon}</label></li>
    </ul>
    </form>
    
    </div>


</body>

</html>

