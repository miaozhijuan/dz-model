<#include "/common/includes.ftl">
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>互动深世界</title>
<meta name="description" content="overview &amp; stats" />
<link rel="stylesheet" href="${ctx}/css/pc/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ace.min.css" />
<link rel="stylesheet" href="${ctx}/zTree/css/zTreeStyle/zTreeStyle.css" />
</head>
<body>
	<div class="main-container">
		<ul id="sysRoleTree" class="ztree permission"  permission="82c05308-1d0a-4131-8c7c-0ba66f2ce49b"></ul>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<button class="btn btn-info permission" type="button" onclick="submitForm()" permission="6a4c3846-e1de-47f8-9a2a-ff7eb0899060">
					<i class="icon-ok bigger-110"></i>
					提交
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="button" onclick="closeLayer()">
					<i class="icon-undo bigger-110"></i>
					关闭
				</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/pc/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${ctx}/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/common.js"></script>
<script type="text/javascript">
	var Url = "${ctx}/authority/sysRole";
	var userId = "${userId}";
	//初始化菜单
	if(content("sysRoleTree").style.display != "none"){
		var cbok = function(data) {
			var setting = {
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			$.fn.zTree.init($("#sysRoleTree"), setting, data);
		};
		var options = {
			data : {
				userId : userId
			},
			url : Url + "/allotRoleTree.do",
		};
		http_get(options, cbok);
	}

	/*保存分配角色*/
	function submitForm() {
		layer.load(1, {
			shade : [ 0.5, '#000' ]
		});
		var checkedNodes = $.fn.zTree.getZTreeObj("sysRoleTree").getCheckedNodes();
		var roleIds = new Array();
		if (checkedNodes.length > 0) {
			for (var i = 0; i < checkedNodes.length; i++) {
				roleIds.push(checkedNodes[i].id);
			}
		}
		var cbok = function(data) {
			layer.msg("操作成功");
			window.parent.load();
			closeLayer();
		};
		var options = {
			url : Url + "/saveRole.do",
			data : {
				roleIds : JSON.stringify(roleIds),
				userId : userId
			},
		};
		http_get(options, cbok);
	}
</script>
</html>