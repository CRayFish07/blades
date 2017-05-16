<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${ctx}/resource/sys/js/jquery.js"></script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>


</head>

<body style="background:url(${ctx}/resource/sys/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <a href="main.do" target="_parent"><img src="${ctx}/resource/sys/images/logo.png" title="系统首页" /></a>
    </div>
        
    <ul class="nav">
    <li><a href="default.do" target="rightFrame" class="selected"><img src="${ctx}/resource/sys/images/icon01.png" title="工作台" /><h2>工作台</h2></a></li>
    <li><a href="user_manage.html" target="rightFrame"><img src="${ctx}/resource/sys/images/icon02.png" title="用户管理" />
    <h2>用户管理</h2></a></li>
    <li><a href="imglist.html"  target="rightFrame"><img src="${ctx}/resource/sys/images/icon03.png" title="模块管理" />
    <h2>模块管理</h2></a></li>
    <li><a href="tools.html"  target="rightFrame"><img src="${ctx}/resource/sys/images/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="${ctx}/resource/sys/images/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
    <li><a href="tab.html"  target="rightFrame"><img src="${ctx}/resource/sys/images/icon06.png" title="系统设置" /><h2>系统设置</h2></a></li>
    </ul>
            
    <div class="topright">    
    <ul>
    <li><span><img src="${ctx}/resource/sys/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    <li><a href="${ctx}/logout.do" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
	    <shiro:user>
		    <span><shiro:principal property="name"/></span>
		    <i>消息</i>
		    <b>5</b>
		</shiro:user>
    </div>    
    
    </div>

</body>
</html>
