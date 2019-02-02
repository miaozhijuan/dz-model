<#include "/common/includes.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>互动深世界</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
</head>
<body >
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li class="permission" permission="0897e2ff-3c07-4932-8009-bf9615f022c6">
						<i class="icon-refresh green"></i>
						<a href="javascript:void(0);" onclick="load()">角色管理</a>
					</li>
					<li class="permission" permission="173125e2-83a0-4028-9b80-9c35632d1b2c">
						<i class="icon-plus-sign purple"></i>
						<a href="javascript:void(0);" onclick="add()">添加</a>
					</li>
					<li class="permission" permission="d1f58cbd-c790-44a7-b19b-389d48b0a77d">
						<i class="icon-trash red"></i>
						<a href="javascript:void(0);" onclick="del()">删除</a>
					</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<table class="table" style="width: 100%; text-align: left; border: 0px;" id="searchHeader">
							<tbody id="newbody"></tbody>
						</table>
						<div class="breadcrumbs">
							<ul class="breadcrumb">
								<li class="permission" permission="cdf10886-c135-49ae-993d-693bdd273baa">
									<i class="icon-search orange"></i>
									<a href="javascript:void(0);" onclick="searchAP()">查询</a>
								</li>
								<li>
									<i class="icon-undo bigger-110"></i>
									<a href="javascript:void(0);" onclick="clearSearch()">重置</a>
								</li>
							</ul>
						</div>
						<table id="grid-table" class="permission" permission="cdf10886-c135-49ae-993d-693bdd273baa"></table>
						<div id="grid-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var Url="${ctx}/authority/sysRole";
	//初始化查询表单
	init(1,8,'10%,15%,10%,15%,10%,15%,10%,15%');//初始化表格
	addCommon(0,0,"label","角色名称:");
	addCommon(0,1,"txtlike","name");
	addCommon(0,2,"label","起始时间:");
	addCommon(0,3,"datastart","create_time");
	addCommon(0,4,"label","结束时间:");
	addCommon(0,5,"dataend","create_time");
	
	//初始化表格
	if(content("grid-table").style.display != "none"){
		jQuery(grid_selector).jqGrid({
			url: Url+"/list.do",
            mtype: 'GET',
			datatype: 'json',
			colNames:['编辑','分配权限', '角色名称','备注','创建时间'],
			colModel:[
					{name : 'id',index:'id',width:10,align : "center",formatter: function (value, grid, rows, state) { return "<input type='button' class='btn btn-primary permission' permission='023c47e1-1cee-4332-a1b1-46fb47dc2781' value='编辑'  onclick='edit(\""+ rows.id + "\")'/>"; }},
					{name : 'id',index:'id',width:20,align : "center",formatter: function (value, grid, rows, state) { return "<input type='button' class='btn btn-primary permission' permission='40dad7f9-32ff-47b1-93a5-da474b79a46f' value='分配权限'  onclick='allotPermission(\""+ rows.id + "\")'/>"; }},
					{name : 'name',index:'name',width:50,align : "center", resizable : true ,sortable:true ,editable: true},
					{name : 'remark',index:'remark',width:50,align : "center", resizable : true ,sortable:true ,editable: true},
					{name : 'createTime',index:'createTime',width:50, align : "center", resizable : true ,sortable:true , editable: true ,formatter: function (value, grid, rows, state) { return toDate('YYYY-MM-DD HH:mm:ss',rows.createTime)}},
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
		    sortname: 'create_time ',  //排序字段名 
		    sortorder: "desc", //排序方式：倒序，本例中设置默认按dctime倒序排序 
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
	//分配权限
	function allotPermission(id){
		var url = '${ctx}/authority/sysPermission/allotPermission.do?roleId='+id;
		openDialogView("分配权限",url,width,height);
	}
</script>
</html>