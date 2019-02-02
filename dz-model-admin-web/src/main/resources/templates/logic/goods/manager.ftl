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
					<li class="permission" permission="bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5">
						<i class="icon-refresh green"></i>
						<a href="javascript:void(0);" onclick="load()">商品信息</a>
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
								<li class="permission" permission="a998c8c2-9e33-4abc-be50-3d636faa1fb0">
									<i class="icon-search orange"></i>
									<a href="javascript:void(0);" onclick="searchAP()">查询</a>
								</li>
								<li>
									<i class="icon-undo bigger-110"></i>
									<a href="javascript:void(0);" onclick="clearSearch()">重置</a>
								</li>
							</ul>
						</div>
						<table id="grid-table" class="permission" permission="a998c8c2-9e33-4abc-be50-3d636faa1fb0"></table>
						<div id="grid-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var Url="${ctx}/logic/goods";
	//初始化查询表单
	init(2,8,'10%,15%,10%,15%,10%,15%,10%,15%');//初始化表格
	addCommon(0,0,"label","用户编码:");
	addCommon(0,1,"txt","user_id");
	addCommon(0,2,"label","商品名称:");
	addCommon(0,3,"txtlike","name");
	addCommon(0,4,"label","是否在线:");
	addCommon(0,5,"selectEnum","online","<option></option><option value = '0' >否</option><option value = '1' >是</option>");
	addCommon(0,6,"label","状态:");
	addCommon(0,7,"selectEnum","status","<option></option><option value = '0' >审核中</option><option value = '1' >已通过</option><option value = '2' >已驳回</option>");
	addCommon(1,0,"label","起始时间:");
	addCommon(1,1,"datastart","create_time");
	addCommon(1,2,"label","结束时间:");
	addCommon(1,3,"dataend","create_time");

	//初始化表格
	if(content("grid-table").style.display != "none"){
		jQuery(grid_selector).jqGrid({
			url: Url+"/list.do",
	        mtype: 'GET',
			datatype: 'json',
			colNames:['用户编码','商品名称','价格(分)','数量','预扣数量','是否在线','状态','审核','创建时间'],
			colModel:[
					{name:'userId',index:'userId',width:50,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'name',index:'name',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'price',index:'price',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'amount',index:'amount',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'withholdAmount',index:'withholdAmount',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'online',index:'online',width:30,align : "center", resizable : true ,sortable:true , editable:true,formatter: function (value, grid, rows, state) {
							if(rows.online == 0){
								return "否";
							}else{
								return "是";
							}
						}
					},
					{name:'status',index:'status',width:40,align : "center", resizable : true ,sortable:true , editable:true,formatter: function (value, grid, rows, state) { 
							if(rows.status == 0){
								return "审核中";
							}else if(rows.status == 1){
								return "已通过";
							}else{
								return "已驳回";
							}
						}
					},
					{name :'id',width : 20,align : "center",formatter: function (value, grid, rows, state) {
							if(rows.status == 0 || rows.status == 2){
								return "<input type='button' class='btn btn-primary permission' permission='253b7ba5-a0b7-49d5-83ee-b41f341c467c' value='通过'  onclick='check(\""+rows.id+"\""+","+1+")'/>";
							}else {
								return "<input type='button' class='btn btn-primary permission' permission='253b7ba5-a0b7-49d5-83ee-b41f341c467c' value='驳回'  onclick='check(\""+rows.id+"\""+","+2+")'/>";
							}
						}
					},
					{name:'createTime',index:'createTime',width:50, align : "center", resizable : true ,sortable:true , editable: true ,formatter: function (value, grid, rows, state) { return toDate('YYYY-MM-DD HH:mm:ss',rows.createTime)}},
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
	/*审核*/
	function check(id,status){
		var cbok = function(msg){
			layer.msg("操作成功");
			window.parent.load();
			closeLayer();
		};
		var options = {
			url: Url+"/check.do",
			data:{id:id,status:status,dateTime: new Date().getTime()}
		};
		http_post(options,cbok);
	}
</script>
</html>