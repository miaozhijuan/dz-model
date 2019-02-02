<#include "/common/includes.ftl">
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>互动深世界</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" href="${ctx}/css/pc/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ace.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/common.css" />
</head>
<body>
	<div class="main-container">
		<form class="form-horizontal" method="post" id="inputFrom">
			<input type="hidden" name="id" value="${sysUser.id?default('')}" />
			<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
				<tr>
					<td>登陆名</td>
					<td>
						<input type="text" name="name" id="name" class="required col-xs-10 col-sm-5" value="${sysUser.name?default('')}" />
					</td>
				<tr>
				<tr>
					<td>密码</td>
					<td>
						<input type="password" name="pwd" id="pwd" class="required col-xs-10 col-sm-5" value="${sysUser.pwd?default('')}" />
					</td>
				<tr>
				<tr>
					<td>用户名</td>
					<td>
						<input type="text" name="realName" id="realName" class="required col-xs-10 col-sm-5" value="${sysUser.realName?default('')}" />
					</td>
				<tr>
				<tr>
					<td>是否启用</td>
					<td>
						<select name="status" id="status" class="required col-xs-10 col-sm-2">
							<option value="0"<#if sysUser ? exists && sysUser.status == 0>selected</#if> >弃用</option>
							<option value="1"<#if sysUser ? exists && sysUser.status == 1>selected</#if> >启用</option>
						</select>
					</td>
				<tr>
				<tr>
					<td>备注</td>
					<td>
						<input type="text" name="remark" id="remark" class="col-xs-10 col-sm-5" value="${sysUser.remark?default('')}" />
					</td>
				<tr>
			</table>
			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info permission" type="button" onclick="submitForm()" permission="b3de2c48-233e-4ac7-b03c-85fca50a5204">
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
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/pc/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/jquery-form.js"></script>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/common.js"></script>
<script type="text/javascript">
	jQuery(function($) {
		var Url = "${ctx}/authority/sysUser";
		var options = {
			url : Url + "/update.do",
			type : 'post',
			beforeSend : function(xhr) {
				layer.load(1, {
					shade : [ 0.5, '#000' ]
				});
			},//保存时页面使用覆盖框    
			success : function(data) {//请求成功
				layer.closeAll();
				if (data.code == 0) {
					layer.msg("操作成功");
					window.parent.load();
					closeLayer();
				} else {
					layer.alert(checkStr(data.msg) ? data.msg : "操作失败");
				}
			},
			complete : function(data) {//请求完成
			},
			error : function(xhr, status, msg) {//请求失败
				layer.msg('玩命加载中..');
			}
		};
		$("#inputFrom").ajaxForm(options);
	});
	function submitForm() {
		if (checkFrom()) {
			$("#inputFrom").submit();
		}
	}
</script>
</html>