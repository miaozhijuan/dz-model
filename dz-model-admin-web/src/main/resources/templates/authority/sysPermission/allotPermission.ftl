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
		<ul id="sysPermissionTree" class="ztree permission"  permission="d6efd7a1-6a2a-4b91-adb3-9a5728ebe51b"></ul>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<button class="btn btn-info permission" type="button" onclick="submitForm()" permission="c8f6e779-0db5-4d17-8367-b0522f81f9e6">
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
	var Url = "${ctx}/authority/sysPermission";
	var roleId = "${roleId}";
	//初始化菜单
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
		$.fn.zTree.init($("#sysPermissionTree"), setting, data);
	};
	var options = {
		data : {
			roleId : roleId
		},
		url : Url + "/allotPermissionTree.do",
	};
	http_get(options, cbok);
	/*保存分配权限*/
	function submitForm() {
		layer.load(1, {
			shade : [ 0.5, '#000' ]
		});
		var checkedNodes = $.fn.zTree.getZTreeObj("sysPermissionTree").getCheckedNodes();
		var permissionIds = new Array();
		if (checkedNodes.length > 0) {
			for (var i = 0; i < checkedNodes.length; i++) {
				permissionIds.push(checkedNodes[i].id);
			}
		}
		var cbok = function(data) {
			layer.msg("操作成功");
			window.parent.load();
			closeLayer();
		};
		var options = {
			url : Url + "/savePermission.do",
			data : {
				permissionIds : JSON.stringify(permissionIds),
				roleId : roleId
			},
		};
		http_get(options, cbok);
	}
</script>
</html>