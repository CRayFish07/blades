<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>资源树</title>
    <link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/resource/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/resource/plugins/zTree_v3/js/jquery.ztree.all.js"></script>
    <script type="text/javascript" src="${ctx}/resource/plugins/layer-v2.1/layer/layer.js"></script>
    <script type=text/javascript>

        var load_index;
        var zTree;
        var setting = {
            async: {
                enable: true,
                url: "tree.do?roleId=${roleId}",
                autoParam: ["id", "name"]
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                dblClickExpand: true,
                nameIsHTML: true
            },
            check: {
                enable: true
            },
            callback: {
                onClick: zTreeOnClick,
                onExpand: onExpand,
                onAsyncSuccess: zTreeOnAsyncSuccess
            }
        };
        var zNodes = [];

        //鼠标单击树时调用的函数
        function zTreeOnClick(event, treeId, treeNode) {
            $(window.parent.document).contents().find("#rightFrame")[0].contentWindow.setModuleInfo(treeNode);
        }

        function dirReloadAsyncCurrentNode(op) {
            var treeNodes = zTree.getSelectedNodes();
            if (treeNodes.length > 0) {
                if (op != null && "add" == op) {
                    treeNodes[0].isParent = true; //节点下添加则该节点属性改为父节点
                    zTree.reAsyncChildNodes(treeNodes[0], "refresh");
                } else if (op != null && "edit" == op) {
                    //刷新父节点
                    if (treeNodes[0].level == 0) { //判断是否是顶级节点
                        zTree.reAsyncChildNodes(null, "refresh");
                    } else {
                        zTree.reAsyncChildNodes(treeNodes[0].getParentNode(),
                                "refresh");
                    }
                } else if (op != null && "delete" == op) {
                    zTree.removeNode(treeNodes[0]);
                }
            } else {
                zTree.reAsyncChildNodes(null, "refresh");
            }
        }


        var log, className = "dark", curDragNodes, autoExpandNode;
        function onExpand(event, treeId, treeNode) {
            if (treeNode === autoExpandNode) {
                className = (className === "dark" ? "" : "dark");
                showLog("[ " + getTime() + " onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
            }
        }

        function showLog(str) {
            if (!log) log = $("#log");
            log.append("<li class='" + className + "'>" + str + "</li>");
            if (log.children("li").length > 8) {
                log.get(0).removeChild(log.children("li")[0]);
            }
        }

        function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
            //数据默认选择无效bug
            var nodes = zTree.getNodesByParam("checked", true, null);
            for (var i = 0; i < nodes.length; i++) {
                zTree.checkNode(nodes[i], true, true);
            }
            layer.close(load_index);
            layer.msg("加载完成！");
        };

        //初始化树
        $(window).ready(function () {
            zTree = $.fn.zTree.init($("#ztreeData"), setting, zNodes);
            //$("#zTreeContainer").height($(document).height()-15);
            load_index = layer.load();
        });

        function save() {
            var ids = new Array();
            var nodes = zTree.getNodesByParam("checked", true, null);
            var n = 0;
            for (var i = 0; i < nodes.length; i++) {
                if (!nodes[i].isParent) {
                    ids[n] = nodes[i].id;
                    n++;
                }
            }
            console.log(ids);
            if (ids.length > 0) {
                $.ajax({
                    type: "POST",
                    url: "roleAuth",
                    data: {"roleId": '${roleId}', "resourceIds": ids},
                    traditional:true,
                    error: function (e) {
                        layer.msg("服务器繁忙！");
                    },
                    success: function (res) {
                        layer.msg("保存成功");
                    }
                });
            }
        }

    </script>
    <style type="text/css">
        body {
            overflow: scroll;
            overflow-x: hidden;
            scrollbar-face-color: #e8e7e7;
            scrollbar-highlight-color: #ffffff;
            scrollbar-shadow-color: #ffffff;
            scrollbar-3dlight-color: #cccccc;
            scrollbar-arrow-color: #03b7ec;
            scrollbar-track-coloe: #efefef;
            scrollbar-darkshadow-color: #b2b2b2;
            scrollbar-base-color: #000000;
            min-width: 50px;
        }

        .tree_top {
            width: 100%;
            height: 40px;
            border: 1px #CCC solid;
            background-color: #3893C8;
        }

        .left-title {
            width: 100%;
            height: 26px;
            background-image: url(${applicationScope.appRoot}/resource/cms/img/index/left1.jpg);
            background-repeat: no-repeat;
            float: left;
            background-color: #3C96C8;
        }

        .left-title span {
            display: inline-block;
            width: 150px;
            height: 26px;
            text-align: left;
            color: #FFFFFF;
            font-size: 14px;
            font-weight: bold;
            margin-top: 14px;
            margin-left: 37px;
        }
        .ztree span {
            display: inline-block;
        }
    </style>
</head>
<body>
<ul class="toolbar" style="padding-left: 20px;padding-top: 10px;padding-bottom: 10px;float: left;width: 80%;">
    <li onclick="save()"><span><img src="${ctx}/resource/sys/images/lc04.png" width="24px"/></span>确认</li>
    <li><span><img src="${ctx}/resource/sys/images/d03.png" width="24px"/></span>取消</li>
</ul>
<div id="zTreeContainer" style="height: auto;float: left;width: 90%;">
    <ul id="ztreeData" class="ztree" style="float: left;padding-left: 20px;"></ul>
</div>
<input type="hidden" id="id" name="id"/>
</body>
</html>