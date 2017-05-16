<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/sys/css/select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/sys.js"></script>
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
		//提示层
		layer.msg('加载完成');

		//全选、全不选
		allCheckOrNot("all", "del");

		//初始化已选中的checkbox
		initCheckBox("idValues", "del");

	});

	function toAdd() {
		window.location.href = "${ctx }/sys/role/save.do";
	}

	//排序用
	function sortByOrder() {
		var $sort = '${sort}';
		if ($sort == "0") {
			$sort = "1";
		} else {
			$sort = "0";
		}
		window.location.href = "${ctx }/sys/role/list/" + $sort
				+ ".do?enable=${enable}";
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

			var url = "${ctx }/sys/role/removes.do";
			//批量删除
			doDeletes(checkVal, url);

			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

		//回收站
		$(".enabled").click(function() {
			window.location.href = "${ctx}/sys/role/list/${sort}.do?enable=0";
		});

		//返回
		$(".toList1").click(function() {
			window.location.href = "${ctx}/sys/role/list/${sort}.do?enable=1";
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
				url : "${ctx }/sys/role/remove/" + id + ".do",
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
				url : "${ctx }/sys/role/recovery/" + id + ".do",
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

	function toView(id) {
		layer.open({
			type : 2,
			title : "角色信息",
			shade : false,
			area : [ '550px', '500px' ],
			maxmin : true,
			content : '${ctx }/sys/role/view/' + id + '.do',
			zIndex : layer.zIndex, //重点1
			success : function(layero) {
				layer.setTop(layero); //重点2
			}
		});
	}
	
	function toAuth(roleId,roleName) {
        layer.open({
            type : 2,
            title : "『"+roleName+"』角色绑定权限",
            shade : false,
            area : [ '550px', '500px' ],
            maxmin : true,
            content : '${ctx }/sys/auth/tree?roleId=' + roleId,
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
			<li><a href="${ctx }/sys/role/list/${sort}.do">角色信息</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<form id="queryForm" action="${ctx }/sys/role/list/${sort}.do" method="post">

			<input id="idValues" name="idValues" type="hidden" value="${idValues}" /> <input id="enable" name="enable"
				type="hidden" value="${enable}" />

			<div id="usual1" class="usual">
				<ul class="seachform">
					<li><label>角色名称</label><input name="name" type="text" class="scinput" value="${role.name }" /></li>
					<li><label>角色代码</label><input name="code" type="text" class="scinput" value="${role.code }" /></li>
					<li><label>角色别名</label><input name="alias" type="text" class="scinput" value="${role.alias }" /></li>
				</ul>
				<!-- <div class="formtitle"><span>用户列表</span></div>-->
			</div>

			<div class="tools">

				<ul class="toolbar">
					<li onclick="toAdd()"><span><img src="${ctx}/resource/sys/images/t01.png" /></span>添加</li>
					<li class="click"><span><img src="${ctx}/resource/sys/images/t03.png" /></span>删除</li>
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
					<th>角色名称</th>
					<th>角色别称</th>
					<th>角色代码</th>
					<th>有效性</th>
					<th>排序号码<i class="sort" onclick="sortByOrder()"><img src="${ctx}/resource/sys/images/px.gif" /></i></th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>

				<#list page.list as role>
					<tr>
						<td><input name="del" type="checkbox" value="${role.id}" /></td>
						<td>${page.startIndex+role_index+1 }</td>
						<td>${role.name }</td>
						<td>${role.alias }</td>
						<td>${role.code }</td>
						<td>${(role.enbaled =='1') ?string('是' , '否')}</td>
						<td>${role.orderNo }</td>
						<td><a href="#" onclick="toView('${role.id}')" class="tablelink">查看</a> 
							<#if role.enbaled=='1'>
								<a href="${ctx }/sys/role/update/${role.id }.do" class="tablelink"> 编辑</a>
							</#if>
								<a href="#" class="tablelink" onclick="doRemove('${role.id}','${role.name }')"> 删除</a> 
							<#if role.enbaled=='0' >
								<a href="#" class="tablelink" onclick="recovery('${role.id}','${role.name }')"> 恢复</a>
							</#if>
                            <a href="javascript:void(0)" onclick="toAuth('${role.id}','${role.name}')" class="tablelink"> 权限</a>
						</td>
					</tr>
				</#list>

			</tbody>
		</table>


		<div class="page blue">${page.html2 }</div>


		<div class="tip">
			<div class="tiptop">
				<span>提示信息</span><a></a>
			</div>

			<div class="tipinfo">
				<span><img src="${ctx}/resource/sys/images/ticon.png" /></span>
				<div class="tipright">
					<p>是否确认删除？</p>
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
