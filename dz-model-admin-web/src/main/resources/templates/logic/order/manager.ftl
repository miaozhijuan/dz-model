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
					<li class="permission" permission="f93fa7a8-6915-4f27-bc4d-0710ff44bd05">
						<i class="icon-refresh green"></i>
						<a href="javascript:void(0);" onclick="load()">订单信息</a>
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
								<li class="permission" permission="ef036b7e-fe59-4fdc-8f7d-3214e487c706">
									<i class="icon-search orange"></i>
									<a href="javascript:void(0);" onclick="searchAP()">查询</a>
								</li>
								<li>
									<i class="icon-undo bigger-110"></i>
									<a href="javascript:void(0);" onclick="clearSearch()">重置</a>
								</li>
							</ul>
						</div>
						<table id="grid-table" class="permission" permission="ef036b7e-fe59-4fdc-8f7d-3214e487c706"></table>
						<div id="grid-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var Url="${ctx}/logic/order";
	//初始化查询表单
	init(2,8,'10%,15%,10%,15%,10%,15%,10%,15%');//初始化表格
	addCommon(0,0,"label","用户编码:");
	addCommon(0,1,"txt","user_id");
	addCommon(0,2,"label","用户编码:");
	addCommon(0,3,"txt","seller_id");
	addCommon(0,4,"label","操作IP:");
	addCommon(0,5,"txtlike","seller_id");
	addCommon(0,6,"label","状态:");
	addCommon(0,7,"selectEnum","status","<option></option><option value = '0' >待付款</option><option value = '1' >待发货</option><option value = '2' >待收货</option><option value = '3' >待评价</option><option value = '4' >已完成</option>");
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
			colNames:['用户编码','商家编码','操作IP','优惠金额(分)','总金额(分)','状态','下单时间'],
			colModel:[
					{name:'userId',index:'userId',width:50,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'sellerId',index:'sellerId',width:50,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'ipAddress',index:'ipAddress',width:40,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'discountAmount',index:'discountAmount',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'totalAmount',index:'totalAmount',width:30,align : "center", resizable : true ,sortable:true , editable:true},
					{name:'status',index:'status',width:30,align : "center", resizable : true ,sortable:true , editable:true,formatter: function (value, grid, rows, state) {
						switch(rows.status){
							case 0:
							 	return "待付款";
							  break;
							case 1:
								return "待发货";
							  break;
							case 2:
								return "待收货";
							  break;
							case 3:
								return "待评价";
							  break;
							case 4:
								return "已完成";
							  break;
							default:
								return "";
							}
						}
					},
					{name:'createTime',index:'createTime',width:50, align : "center", resizable : true ,sortable:true , editable: true ,formatter: function (value, grid, rows, state) { return toDate('YYYY-MM-DD HH:mm:ss',rows.createTime)}},
			], 
			rowNum:10, //每页显示记录数 
		    rowList:[10,30,60], //分页选项，可以下拉选择每页显示记录数 
		    pager: pager_selector,  //表格数据关联的分页条，html元素 
		    autowidth: true, //自动匹配宽度 
		    height:325,   //设置高度 
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