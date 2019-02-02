<#include "/common/includes.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>互动深世界</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" href="${ctx}/zTree/css/zTreeStyle/zTreeStyle.css" />
</head>
<body>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li class="permission" permission="1bebf3f9-6560-454b-9df8-3ba7f32119e4">
						<i class="icon-refresh green"></i>
						<a href="javascript:void(0);" onclick="load()">权限管理</a>
					</li>
					<li class="permission" permission="09a37704-a41c-46f5-bd69-1f0f34de2896">
						<i class="icon-plus-sign purple"></i>
						<a href="javascript:void(0);" onclick="addPermission()">添加</a>
					</li>
					<li class="permission" permission="98ad6647-6013-4492-9c37-fb0960f9cec1">
						<i class="icon-trash red"></i>
						<a href="javascript:void(0);" onclick="del()">删除</a>
					</li>
				</ul>
			</div>
			<div class="sidebar" id="sidebar">
				<div class="widget-main padding-8">
					<ul id="sysPermissionTree" class="ztree permission" permission="c5f8671e-4bdd-423d-9c4f-58c8be638f97" style="height: 650px; overflow: auto;"></ul>
				</div>
			</div>
			<div class="main-content">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<table class="table" style="width: 100%; text-align: left; border: 0px;" id="searchHeader">
								<tbody id="newbody"></tbody>
							</table>
							<div class="breadcrumbs">
								<ul class="breadcrumb">
									<li class="permission" permission="1a787821-d966-408e-a1dd-746d4fd8c48b">
										<i class="icon-search orange"></i>
										<a href="javascript:void(0);" onclick="searchAP()">查询</a>
									</li>
									<li>
										<i class="icon-undo bigger-110"></i>
										<a href="javascript:void(0);" onclick="clearSearch()">重置</a>
									</li>
								</ul>
							</div>
							<table id="grid-table" class="permission" permission="1a787821-d966-408e-a1dd-746d4fd8c48b"></table>
							<div id="grid-pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
	var Url="${ctx}/authority/sysPermission";
	var parentId = "0";
	//初始化查询表单
	init(1,8,'10%,15%,10%,15%,10%,15%,10%,15%');//初始化表格
	addCommon(0,0,"label","权限名称:");
	addCommon(0,1,"txtlike","name");
	addCommon(0,2,"label","是否启用:");
	addCommon(0,3,"selectEnum","status","<option></option><option value = '0' >弃用</option><option value = '1' >启用</option>");
	addCommon(0,4,"label","起始时间:");
	addCommon(0,5,"datastart","create_time");
	addCommon(0,6,"label","结束时间:");
	addCommon(0,7,"dataend","create_time");
	//初始化表格
	if(content("grid-table").style.display != "none"){
		jQuery(grid_selector).jqGrid({
			url: Url+"/list.do?parentId="+parentId,
            mtype: 'GET',
			datatype: 'json',
			colNames:['编辑','编码', '权限名称','路径','级别','排序','是否启用','备注', '创建时间'],
			colModel:[
					{name : 'id',index:'id', width:20,align : "center",formatter: function (value, grid, rows, state) { return "<input type='button' class='btn btn-primary permission' permission='07ad6360-0ac1-4742-b039-b16023db18c6'  value='编辑'  onclick='edit(\""+ rows.id + "\")'/>"; }},
					{name : 'id',index:'id',width:80,align : "center", resizable : true ,sortable:true ,editable: true},
					{name : 'name',index:'name',width:40,align : "center", resizable : true ,sortable:true ,editable: true},
					{name : 'url',index:'url',width:70,align : "center", resizable : true ,sortable:true , editable:true},
					{name : 'level',index:'level',width:40,align : "center", resizable : true ,sortable:true , editable:true,formatter: function (value, grid, rows, state) { if(rows.level == 0){return "主权限"}else{return "副权限"}}},
					{name : 'sort',index:'sort',width:40,align : "center", resizable : true ,sortable:true , editable:true},
					{name : 'status',index:'status',width:40,align : "center", resizable : true ,sortable:true , editable:true,formatter: function (value, grid, rows, state) { if(rows.status == 0){return "弃用"}else{return "启用"}}},
					{name : 'remark',index:'remark',width:40,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'createTime',index:'createTime',width:40, align : "center", resizable : true ,sortable:true , editable: true ,formatter: function (value, grid, rows, state) { return toDate('YYYY-MM-DD HH:mm:ss',rows.createTime)}},
			], 
			rowNum:10, //每页显示记录数 
		    rowList:[10,30,60], //分页选项，可以下拉选择每页显示记录数 
		    pager: pager_selector,  //表格数据关联的分页条，html元素 
		    autowidth: true, //自动匹配宽度 
		    height:325,   // 设置高度 
		    gridview:true, //加速显示 
		    viewrecords: true,  //显示总记录数 
		    multiselect: true,  //可多选，出现多选框 
		    multiselectWidth: 25, //设置多选列宽度 
		    sortable:true,  //可以排序 
		    sortname: 'sort ',  //排序字段名 
		    sortorder: "asc", //排序方式：倒序，本例中设置默认按dctime倒序排序 
		    loadComplete:function(data){ //完成服务器请求后，回调函数 
		        var table = this;
				setTimeout(function(){
					updatePagerIcons(table);
				}, 0);
				permission();
		    } 
		});  
	}
	//页码动态更新
	function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
			'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
			'ui-icon-seek-next' : 'icon-angle-right bigger-140',
			'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}
	
	/*查询*/
	function searchAP(){			
		searchAchieve(Url+"/list.do")
	}
	//权限添加
	function addPermission() {
		openDialogView("添加",Url + "/toAdd.do?parentId="+parentId,width,height);
	}
	//初始化树形
	if(content("sysPermissionTree").style.display != "none"){
		var cbok = function(data){
			var setting = {
				view: {
					dblClickExpand: false,
					showLine: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick
				}
			};
			$.fn.zTree.init($("#sysPermissionTree"), setting, data);
		};
		var options = {
			url: Url+"/sysPermissionTree.do",
			data: {},
		};
		http_get(options,cbok);
	}
	/*点击展示菜单*/
	function onClick(e,treeId, treeNode) {
		parentId = treeNode.id;
		$(grid_selector).jqGrid('setGridParam',{ 
            url:Url+"/list.do",
            postData:{'parentId':parentId}, 
            page:1 
        }).trigger("reloadGrid");  
		$.fn.zTree.getZTreeObj("sysPermissionTree").expandNode(treeNode);
	}
</script>
</html>