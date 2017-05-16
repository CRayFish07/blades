<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/sys/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/sys.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
<!-- 时间控件 -->
<script type="text/javascript" src="${ctx}/resource/plugins/calendar/WdatePicker.js"></script>
<style type="">
body {
	min-width: 820px;
}
</style>
<script type="text/javascript">
	$(document).ready(function(e) {

		$(".select3").uedSelect({
			width : 152
		});

		//全选、全不选
		allCheckOrNot("all", "del");

		//初始化已选中的checkbox
		initCheckBox("idValues", "del");

		//提示层
		layer.msg('加载完成');
	});

	function toAdd() {
		window.location.href = "./save.do?pid=${pdic.id}";
	}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var checkVal = [];
		$(".click").click(function() {
			//跨页选中
			selectBoxs("idValues", "del");

			if ($("input[name='idValues']").val() == "") {
				layer.msg('请选择要删除的对象!');
				return;
			}

			checkVal = $("input[name='idValues']").val().split(",");

			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {

			var url = "${ctx }/sys/user/removes.do";
			//批量删除
			doDeletes(checkVal, url);

			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

		//回收站
		$(".enabled").click(function() {
			window.location.href = "${ctx}/sys/user/list.do?enabled=0";
		});

		//返回
		$(".toList1").click(function() {
			window.location.href = "${ctx}/sys/user/list.do?enabled=1";
		});

	});

	//切换分页
	function page(pageNo, pageSize, comment) {
		//跨页选中
		selectBoxs("idValues", "del");

		$("#queryForm").attr(
				"action",
				$("#queryForm").attr("action") + "?pageNo=" + pageNo
						+ "&pageSize=" + pageSize);
		$("#queryForm").submit();
	}

	function doRemove(id, name) {
		//询问框
		layer.confirm('你确定要删除『' + name + '』吗？', {
			btn : [ '确定', '取消' ], //按钮
			title : '友情提醒'
		}, function() {
			$.ajax({
				type : "POST",
				url : "${ctx }/sys/user/remove/" + id + ".do?",
				async : false,
				error : function(request) {
					layer.msg("服务器繁忙！");
				},
				success : function(res) {
					layer.msg("删除成功");
					jumpPageNum();
				}
			});
		}, function() {

		});
	}

	//恢复
	function recovery(id, name) {
		//询问框
		layer.confirm('你确定要恢复『' + name + '』吗？', {
			btn : [ '确定', '取消' ], //按钮
			title : '友情提醒'
		}, function() {
			$.ajax({
				type : "POST",
				url : "${ctx }/sys/user/recovery/" + id + ".do",
				async : false,
				error : function(request) {
					alert("服务器繁忙！");
				},
				success : function(res) {
					layer.msg("恢复成功!");
					jumpPageNum();
				}
			});
		}, function() {

		});
	}

	function toView(id, name) {
		layer.open({
			type : 2,
			title : '『' + name + '』',
			shade : false,
			area : [ '550px', '500px' ],
			maxmin : true,
			content : '${ctx }/sys/user/view.do?id=' + id,
			zIndex : layer.zIndex, //重点1
			success : function(layero) {
				layer.setTop(layero); //重点2
			}
		});
	}
</script>


</head>


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li>系统管理</li>
			<li><a href="${ctx }/sys/user/list.do">用户管理</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<form id="queryForm" action="list.do" method="post">
			<input name="enabled" value="${map.enabled }" type="hidden" /> <input id="idValues" name="idValues" type="hidden"
				value="${map.idValues}" />
			<div id="usual1" class="usual">
				<ul class="seachform">
					<li><label>账号</label><input name="username" type="text" class="scinput" value="${map.username }" /></li>
					<li><label>姓名</label><input name="name" type="text" class="scinput" value="${map.name }" /></li>
					<li><label>身份证号</label><input name="idNo" type="text" class="scinput" value="${map.idNo }" /></li>
					<li><label>警号</label><input name="code" type="text" class="scinput" value="${map.code }" /></li>
				</ul>
				<!-- <div class="formtitle"><span>用户列表</span></div>-->
			</div>

			<div class="tools">

				<ul class="toolbar">
				
					<li onclick="toAdd()"><span><img src="${ctx}/resource/sys/images/t01.png" /></span>添加</li>
				
					<li class="click"><span><img src="${ctx}/resource/sys/images/t03.png" /></span>删除</li>
					<li><span><img src="${ctx}/resource/sys/images/ico05.png" width="24px" /></span>授权</li>
					<li class="enabled"><span><img src="${ctx}/resource/sys/images/icon11.png" width="24px" /></span>调岗</li>
					<#--<li class="toList1"><span><img src="${ctx}/resource/sys/images/lc00.png" width="24px" /></span>返回</li>
					<li class="enabled"><span><img src="${ctx}/resource/sys/images/i04.png" width="24px" /></span>回收站</li>
					<li><span><img src="${ctx}/resource/sys/images/d03.png" width="24px" /></span>帮助</li>-->
				</ul>

				<ul style="float: right;">
					<li><input name="" type="submit" class="scbtn" value="查询" /></li>
				</ul>

			</div>
		</form>

		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="all" type="checkbox" value="" /></th>
					<th>序号</th>
					<th>账号</th>
					<th>姓名</th>
					<th>工号</th>
					<th>更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>

				<#list page.list as user>
					<tr>
						<td><input name="del" type="checkbox" value="${user.ID}" /></td>
						<td>${page.startIndex + user_index +1}</td>
						<td>${user.username }</td>
						<td>${user.name }</td>
						<td>${user.code }</td>
						<td>
							<@date.convertStrDate dateStr=user.update_time mask="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							<a href="#" onclick="toView('${user.id}','${user.name }')" class="tablelink">查看</a>
                            <a href="${ctx }/sys/user/update/${user.id }.do" class="tablelink"> 编辑</a>
							<a href="#" class="tablelink" onclick="doRemove('${user.id}','${user.name }')"> 删除</a>
							 <#if user.enabled=='0' >
								<a href="#" class="tablelink" onclick="recovery('${user.id}','${user.name}')"> 恢复</a>
							</#if>
						</td>
					</tr>
				</#list>

			</tbody>
		</table>


		<div class="page blue">${page.html2 }</div>

		<div>
			shiro标签：
			<@shiro.hasPermission name="/sys/user/add">
				用户[<@shiro.principal property="name"/>]拥有权限/sys/user/add<br/>
			</@shiro.hasPermission>
			<br/>
			<@shiro.lacksPermission name="/sys/org">
				用户[<@shiro.principal property="name" />]没有权限/sys/org<br/>
			</@shiro.lacksPermission>&nbsp;
			<br/>
		</div>

		<div class="tip">
			<div class="tiptop">
				<span>提示信息</span><a></a>
			</div>

			<div class="tipinfo">
				<span><img src="${ctx}/resource/sys/images/ticon.png" /></span>
				<div class="tipright">
					<p>是否确认对字典的批量删除？</p>
					<cite>如果是请点击确定按钮 ，否则请点取消。</cite>
				</div>
			</div>

			<div class="tipbtn">
				<input name="" type="button" class="sure" value="确定" />&nbsp; <input name="" type="button" class="cancel"
					value="取消" />
			</div>

		</div>


	</div>

	<script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
