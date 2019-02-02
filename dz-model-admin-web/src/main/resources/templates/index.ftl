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
<link rel="stylesheet" href="${ctx}/css/pc/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ui.jqgrid.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ace.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ace-rtl.min.css" />
<link rel="stylesheet" href="${ctx}/css/pc/ace-skins.min.css" />
</head>
<body>
	<div class="navbar navbar-default" id="navbar">
		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="${ctx}/authority/main/index.do" class="navbar-brand permission"  permission="3ab05c4c-a6e1-4cc8-ae80-4774809c6840">
					<small>
						<i class="icon-leaf"></i>
						互动深世界
					</small>
				</a>
			</div>
			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<li class="light-blue">
						<a data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
							<span class="user-info">
								<small>欢迎光临:[${user.realName}]</small>
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li class="permission" permission="44a9ff61-953a-4763-87a2-811c32b2ed9f">
								<a href="javascript:;" onclick="updatePwd()">
									<i class="icon-cog"></i>
									修改密码
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:;"  onclick="loginOut()">
									<i class="icon-off"></i>
									退出
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="sidebar" id="sidebar">
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="icon-signal"></i>
						</button>
						<button class="btn btn-info">
							<i class="icon-pencil"></i>
						</button>
						<button class="btn btn-warning">
							<i class="icon-group"></i>
						</button>
						<button class="btn btn-danger">
							<i class="icon-cogs"></i>
						</button>
					</div>
					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>
						<span class="btn btn-info"></span>
						<span class="btn btn-warning"></span>
						<span class="btn btn-danger"></span>
					</div>
				</div>
				<ul class=" nav nav-list permission" permission="bf015f98-cdb3-45cf-b758-2d69817f0edd" id="menu"></ul>
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
				</div>
			</div>
			<div class="main-content">
				<div class="breadcrumbs" >
					<ul class="breadcrumb">
						<li class="permission" permission="3ab05c4c-a6e1-4cc8-ae80-4774809c6840">
							<i class="icon-home home-icon"></i>
							<a href="${ctx}/authority/main/index.do">首页</a>
						</li>
					</ul>
				</div>
				<!-- 右下 -->
				<div id="index_main" ></div>
			</div>
			<div class="ace-settings-container" id="ace-settings-container">
				<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
					<i class="icon-cog bigger-150"></i>
				</div>
				<div class="ace-settings-box" id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="default" value="#438EB9">#438EB9</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-2" value="#C6487E">#C6487E</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
						<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
						<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
						<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
						<label class="lbl" for="ace-settings-rtl">切换到左边</label>
					</div>
					<div>
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
						<label class="lbl" for="ace-settings-add-container"> 切换窄屏 </label>
					</div>
				</div>
			</div>
		</div>
		<a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/pc/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/common.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/typeahead-bs2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/ace.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/ace-extra.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/ace-elements.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/grid.locale-en.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
<script type="text/javascript">
	/*获取权限菜单*/
	var menu = content("menu");
	if(menu.style.display != "none"){
		var cbok = function(data) {
			if (data.length > 0) {
				var html = "";
				data.map(function(item, index, array) {
					html += "<li>";
					if (item.type) {
						html += "<a href='javascript:;' class='dropdown-toggle'>";
							html += "<i class='icon-list'></i>";
							html += "<span class='menu-text'>";
								html += item.name;
							html += "</span>";
							html += "<b class='arrow icon-angle-down'></b>";
						html += "</a>";
						html = tree(html, item.children);
					} else {
						if(item.url != '#'){
							html += "<a href='javascript:;' onclick=jquery_load("+ "'" + '${ctx}' + item.url + "'" + ") >";
						}else{
							html += "<a href='javascript:;'  >";
						}
							html += "<i class='icon-list'></i>";
							html += "<span class='menu-text'>";
								html += item.name;
							html += "</span>";
						html += "</a>";
					}
					html += "</li>";
				});
			}
			if (checkStr(html)) {
				menu.innerHTML = html;
			}
		};
		var options = {
			url : "${ctx}/authority/main/tree.do",
			data : {}
		};
		http_get(options, cbok);
	}
	/*递归渲染树形*/
	function tree(html, data) {
		html += "<ul class='submenu'>";
		if (data.length > 0) {
			data.map(function(item, index, array) {
				html += "<li>";
				if (item.type) {
					html += "<a href='javascript:;' class='dropdown-toggle'>";
						html += "<i class='icon-double-angle-right'></i>";
						html += item.name;
						html += "<b class='arrow icon-angle-down'></b>";
					html += "</a>";
					html = tree(html, item.children);
				} else {
					if(item.url != '#'){
						html += "<a href='javascript:;' onclick=jquery_load("+ "'" + '${ctx}' + item.url + "'" + ") >";
					}else{
						html += "<a href='javascript:;'  >";
					}
						html += "<i class='icon-double-angle-right'></i>";
						html += item.name;
					html += "</a>";
				}
				html += "</li>";
			});
		}
		html += "</ul>";
		return html;
	}
	/*修改密码*/
	function updatePwd() {
		openDialogView("修改密码","${ctx}/authority/main/updatePwd.do",width, height);
	}
	/*退出*/
	function loginOut() {
		sessionStorage.setItem("iList", "");
		window.location.href = "${ctx}/authority/main/loginOut.casual";
	}
</script>
</html>