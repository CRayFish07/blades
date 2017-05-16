<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
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
	min-width: 520px;
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
		window.location.href = "./save.do?pid=${porg.id}&tree=true";
	}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		
		var checkVal = [];
		$(".click").click(function() {
			//跨页选中
			selectBoxs("idValues","del");
			
			if($("input[name='idValues']").val() ==""){
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
			
			var url = "${ctx }/sys/org/removes.do";
			//批量删除
			doDeletes(checkVal,url);
			
			$(".tip").fadeOut(100);
			$(window.parent.document).contents().find("#leftFrame")[0].contentWindow.dirReloadAsyncCurrentNode("add");
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});

	//切换分页
	function page(pageNo, pageSize, comment) {
		
		//跨页选中
		selectBoxs("idValues","del");
		
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
			$
					.ajax({
						type : "POST",
						url : "remove.do?id=" + id,
						async : false,
						error : function(request) {
							layer.msg("服务器繁忙！");
						},
						success : function() {
							layer.msg("删除成功");
							$(window.parent.document).contents().find(
									"#leftFrame")[0].contentWindow
									.dirReloadAsyncCurrentNode("add");
							jumpPageNum();
						}
					});
		}, function() {

		});
	}

	function toView(id) {
		layer.open({
			type : 2,
			title : "组织机构信息",
			shade : false,
			area : [ '550px', '450px' ],
			maxmin : true,
			content : 'view.do?id=' + id,
			zIndex : layer.zIndex, //重点1
			success : function(layero) {
				layer.setTop(layero); //重点2
			}
		});
	}

	function toDM() {
		parent.window.location.href = "list.do";
	}
</script>


</head>


<body>

	<div class="rightinfo">
		<form id="queryForm" action="list.do" method="post">
			<input name="pid" type="hidden" value="${porg.id }" />
			<input id="idValues" name="idValues" type="hidden" value="${idValues}" />
			<div id="usual1" class="usual">
				<ul class="seachform">
					<li><label>机构名称</label><input name="name" type="text" class="scinput" value="${org.name }" /></li>
					<li><label>机构代码</label><input name="code" type="text" class="scinput" value="${org.code }" /></li>
					<li><label>外部代码</label><input name="fcode" type="text" class="scinput" value="${org.fcode }" /></li>
				</ul>
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
					<th><input name="all" type="checkbox" value=""/></th>
					<th>序号</i></th>
					<th>机构名称</th>
					<th>机构代码</th>
					<th>外部代码</th>
					<th>更新时间</th>
					<th>排序号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<#if page.list?? && (page.list?size> 0)>
					
				<#list page.list as org >
					<tr>
						<td><input name="del" type="checkbox" value="${org.id}" /></td>
						<td>${page.startIndex+org_index+1 }</td>
						<td>${org.name }</td>
						<td>${org.code }</td>
						<td>${org.fcode }</td>
						<td>${org.updateTime }</td>
						<td>${org.orderNo }</td>
						<td><a href="#" onclick="toView('${org.id}')" class="tablelink">查看</a> <a
							href="update.do?id=${org.id }&tree=true" class="tablelink"> 编辑</a> <a href="#" class="tablelink"
							onclick="doRemove('${org.id}','${org.name }')"> 删除</a></td>
					</tr>
				</#list>
				<#else>
                <tr>
                    <td colspan="8">未查找到机构数据</td>
                </tr>
				</#if>

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
					<p>是否确认对机构的批量删除？</p>
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
