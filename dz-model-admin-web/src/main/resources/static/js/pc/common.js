permission();

/*权限处理*/
function permission() {
	var iList = sessionStorage.getItem("iList");
	if (checkStr(iList)) {
		iList = iList.split(",");
		$(".permission").each(function(i) {
			if ($.inArray($(this).attr("permission"), iList) > 0) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	}
}

/* 根据id获取节点 */
function content(obj) {
	return document.getElementById(obj);
};

/* 加载页面 */
function jquery_load(url) {
	$("#index_main").load(url, function() {
		permission();
	});
};

/* 刷新页面 */
function load(obj) {
	if (!checkStr(obj)) {
		$("#index_main").load(Url + "/manager.do", function() {});
	} else {
		$("#index_main").load(Url + "/" + obj, function() {});
	}
};

/* 判断字符串是否可用 */
function checkStr(obj) {
	if (null != obj && "" != obj && undefined != obj) {
		return true;
	} else {
		return false;
	}
};

/* 添加 */
function add() {
	var url = Url + "/toAdd.do";
	openDialogView("添加", url, width, height);
}

/* 删除 */
function del() {
	var rowData = jQuery(grid_selector).jqGrid('getGridParam', 'selarrrow');
	if (rowData.length == 0) {
		layer.alert('请选择要删除的行');
	} else {
		layer.confirm('确定删除吗？', {btn : [ '确定', '取消' ]}, function() {
			layer.load(1, {
				shade : [ 0.5, '#000' ]
			});
			var cbok = function(data) {
				window.parent.load();
				layer.alert('删除成功');
			};
			var options = {
				url : Url + "/delete.do",
				data : {
					ids : JSON.stringify(rowData)
				},
			};
			http_get(options, cbok);
		});
	}
}

/* 编辑 */
function edit(id) {
	var url = Url + "/toUpdate.do?id="+id;
	openDialogView("编辑", url, width, height);
}

/* 格式化时间戳 */
function toDate(format, timestamp) {
	if (checkStr(timestamp)) {
		var t = new Date(timestamp);
		var tf = function(i) {
			return (i < 10 ? '0' : '') + i
		};
		return format.replace(/YYYY|MM|DD|HH|mm|ss/g, function(a) {
			switch (a) {
			case 'YYYY':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'DD':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
			}
		})
	} else {
		return "";
	}
}

/**
 * 动态生成查询表格
 * 
 * @param row生成table的行数
 * @param cell生成table的列数
 * @param tdWidth生成table的每个td的width（可选：像素，百分比）
 */
function init(row, cell, tdWidth) {
	var _table = content("searchHeader");// 获取表格
	var i_width = {};// 申明td宽度数组
	if (checkStr(tdWidth)) {
		i_width = tdWidth.split(",");
	}
	// 生成表格
	for (var i = 0; i < row; i++) {
		var tr = document.createElement("tr");
		tr.setAttribute("id", i);
		for (var j = 0; j < cell; j++) {
			var tdwidth = "";
			if (i_width.length > j) {
				tdwidth = i_width[j].replace(/(^\s*)|(\s*$)/, "");// 去掉两边空格
				if (false == isNaN(tdwidth)) {
					tdwidth += "px";
				}
				if ("" != tdwidth) {
					tdwidth = "width:" + tdwidth + ";";
				}
			}
			var td = document.createElement("td");
			td.setAttribute("id", i + "_" + j);
			td.style.cssText = "padding-top:2px;padding-right:2px;" + tdwidth;
			tr.appendChild(td);
		}
		content("newbody").appendChild(tr);
	}
}

/**
 * 动态给表格赋值
 * 
 * @param row插入的行
 * @param cell插入的列
 * @param obj插入的标签类型
 * @param id插入标签的id或name
 * @param value插入查询的值
 * @param textEntFunc文本输入回车的函数默认为查询函数searchAP()
 */
function addCommon(row, cell, obj, id, value, textEntFunc) {
	var text = "";
	if (!checkStr(textEntFunc)) textEntFunc = "searchAP()";
	var textEntEvent = "if(13==event.keyCode){" + textEntFunc + ";}";
	// 生成文本框
	if (obj == "label") {
		text = id;
	} else if (obj == "txt") {// 等于
		// 查询字段
		text = "<span><input type='hidden' class='searchField' value='" + id + "'>";
		// 查询操作
		text += "<input type='hidden' class='searchOper' value='EQUAL' >";
		// 查询内容
		text += "<input class='searchString col-xs-10 col-sm-5 ' id='" + id + "' name='" + id + "' type='text' value='' onkeyup='" + textEntEvent + "' style='width:99%'>";
		// 查询类型
		text += "<input type='hidden' class='searchType' value='string'></span>";
	} else if (obj == "txtlike") {// 模糊查询
		// 查询字段
		text = "<span><input type='hidden' class='searchField' value='" + id + "'>";
		// 查询操作
		text += "<input type='hidden' class='searchOper' value='LIKE' >";
		// 查询内容
		text += "<input class='searchString col-xs-10 col-sm-5 ' id='" + id + "' name='" + id + "' type='text' value='' onkeyup='" + textEntEvent + "' style='width:99%'>";
		// 查询类型
		text += "<input type='hidden' class='searchType' value='string'></span>";
	} else if (obj == "selectEnum") {// 下拉框
		// 查询字段
		text = "<span><input type='hidden' class='searchField' value='" + id + "'>";
		// 查询操作
		text += "<input type='hidden' class='searchOper' value='EQUAL' >";
		// 查询内容
		text += "<select class='searchString form-control ' id='" + id + "' name='" + id + "'>" + value + "</select>";
		// 查询类型
		text += "<input type='hidden' class='searchType' value='string'></span>";
	} else if (obj == "datastart") {// 起始时间大于等于
		// 查询的字段
		text = "<span><input type='hidden' class='searchField' value='" + id + "'>";
		// 查询的内容
		text += "<input class='searchString col-xs-10 col-sm-5 ' id='" + id + "' name='" + id + "' type='text' readonly='readonly' value='' onkeyup='" + textEntEvent + "' onmouseover = 'layDate(this);' style='width:99%' >";
		// 查询的操作
		text += "<input type='hidden' class='searchOper' value='GREATER'>";
		// 查询的类型
		text += "<input type='hidden' class='searchType' value='date'></span>";
	} else if (obj == "dataend") {// 结束时间小于等于
		// 查询的字段
		text = "<span><input type='hidden' class='searchField' value='" + id + "'>";
		// 查询的内容
		text += "<input class='searchString col-xs-10 col-sm-5 ' id='" + id + "' name='" + id + "' type='text' readonly='readonly' value='' onkeyup='" + textEntEvent + "' onmouseover = 'layDate(this);' style='width:99%' >";
		// 查询的操作
		text += "<input type='hidden' class='searchOper' value='SMALLER'>";
		// 查询的类型
		text += "<input type='hidden' class='searchType' value='date'></span>";
	}
	document.getElementById('searchHeader').rows[row].cells[cell].innerHTML = text;
}

/*layerDate时间点击时间*/
function layDate(that){
	laydate.render({
		 elem: that,type: 'datetime'
	});
}

/* 重置查询条件 */
function clearSearch() {
	$('input:text').val("");
	$("tbody tr td span").each(function(i) {
		$(".searchString", this).val("");
	});
}

/* 动态查询 */
function searchAchieve(url) {
	$(grid_selector).jqGrid('setGridParam', {
		url : url,
		postData : {'rules' : rules()},
		page : 1
	}).trigger("reloadGrid");
}

/* 获取查询条件 */
function rules() {
	var rules = "";
	$("tbody tr td span").each(
		function(i) {
			var searchField = $(".searchField", this).val();
			var searchString = $(".searchString", this).val();
			var searchOper = $(".searchOper", this).val();
			var searchType = $(".searchType", this).val();
			if (searchField && searchString && searchOper && searchType) {
				if (i == 0) {
					rules += '{"field":"' + searchField + '","op":"' + searchOper + '","data":"' + searchString + '","type":"' + searchType + '"}';
				} else {
					rules += ',{"field":"' + searchField + '","op":"' + searchOper + '","data":"' + searchString + '","type":"' + searchType + '"}';
				}
			}
		});
	return "[" + rules + "]";
}

/* 页面弹窗 */
function openDialogView(title, url, width, height) {
	layer.open({
		title : title,
		type : 2,
		area : [ width, height ],
		fix : false,
		maxmin : true,
		content : url,
		success : function(layero, index) {
		},
		end : function() {
		}
	});
}

/* 关闭页面弹窗 */
function closeLayer() {
	parent.layer.close(parent.layer.getFrameIndex(window.name));
}

/* 表单校验 */
function checkFrom() {
	$('span').remove(".mark");// 删除所有错误标示
	var flag = true;
	var positive = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;// 正数
	var integer = /^\+?[1-9]\d*$/;
	var percentage = /^([1-9]{1}[0-9]{0,1}|0|100)(.d{1,2}){0,1}%$/;// 百分比
	var phone = /^((13|15|18|14|17)+\d{9})$/;// 手机号
	$("#inputFrom .required").each(/* required 非空校验 */
		function(i) {
			$(this).css({'border-color' : ''});
			if (checkStr(!$(this).val())) {
				$(this).css({'border-color' : 'red'});
				$(this).after("<span class = 'mark' style='color: red;' >不能为空</span>");
				flag = false;
			}
		}
	);
	$("#inputFrom .positive").each(/* positive 正数校验(正整数和小数) */
		function(i) {
			$(this).css({'border-color' : ''});
			if (!positive.test($(this).val())) {
				$(this).css({'border-color' : 'red'});
				$(this).after("<span class = 'mark' style='color: red;' >不是正数</span>");
				flag = false;
			}
		}
	);
	$("#inputFrom .integer").each(/* positive 正整数 */
		function(i) {
			$(this).css({'border-color' : ''});
			if (!integer.test($(this).val())) {
				$(this).css({'border-color' : 'red'});
				$(this).after("<span class = 'mark' style='color: red;' >不是正整数</span>");
				flag = false;
			}
		}
	);
	$("#inputFrom .percentage").each(/* percentage 百分比 */
		function(i) {
			$(this).css({'border-color' : ''});
			if (!percentage.test($(this).val())) {
				$(this).css({'border-color' : 'red'});
				$(this).after("<span class = 'mark' style='color: red;' >不是百分数</span>");
				flag = false;
			}
		}
	);
	$("#inputFrom .phone").each(/* phone 手机号 */
		function(i) {
			$(this).css({'border-color' : ''});
			if (!phone.test($(this).val())) {
				$(this).css({'border-color' : 'red'});
				$(this).after("<span class = 'mark' style='color: red;' >手机号格式错误</span>");
				flag = false;
			}
		}
	);
	return flag;
}

/* 查看单个图片 */
function seeImage(url) {
	var obj = {"title" : "","id" : "","start" : 0,"data" : [ {"alt" : "","pid" : "","src" : url,"thumb" : url} ]};
	layer.photos({photos : obj,shift : 5});
}

/* 查看图片集合 */
function seeImages(url) {
	var cbok = function(data) {
		if (checkStr(data)) {
			layer.photos({
				photos : data,
				shift : 5
			});
		}
	};
	var options = {
		url : sys_cxt + "/" + url,
		data : {},
	};
	http_get(options, cbok);
}

/**
 * js的Map
 */
function Map() {
	this.elements = new Array();
	this.size = function() {
		return this.elements.length;
	}
	this.isEmpty = function() {
		return (this.elements.length < 1);
	}
	this.clear = function() {
		this.elements = new Array();
	}
	this.put = function(_key, _value) {
		this.elements.push({
			key : _key,
			value : _value
		});
	}
	this.remove = function(_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}
	this.get = function(_key) {
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	}
	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	}
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}
	this.values = function() {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	}
	this.keys = function() {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	}
}

/**
 * 动态表格通用字段
 */
var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";

/**
 * 弹出仓宽高
 */
var width = "70%";
var height = "70%";

// 删除行
// jqGrid("delRowData", rowid)
// rowid为选中行的id
function delRow() {
	var selectedRowIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
	var len = selectedRowIds.length;
	for (var i = 0; i < len; i++) {
		$(grid_selector).jqGrid("delRowData", selectedRowIds[0]);
	}
}
// 添加行
// jqGrid("addRowData", rowid, data, position)
// rowid为新行的id,data为新行的数据,position为新增行的位置
function addRow() {
	$(grid_selector).jqGrid("addRowData", new Date().getTime(), dataRow, "last");
}
// 行编辑解锁
// jqGrid("editRow", rowid ,true)
// rowid为新行的id,true表示锁定,false表示解锁
function editRow(rowid) {
	$(grid_selector).jqGrid('editRow', rowid, false);
}
/* 播放器 */
function playBack(url) {
	var content = "<video  class='video-js vjs-default-skin' controls preload='none' width='800px' height='480px' poster='http://video-js.zencoder.com/oceans-clip.png' data-setup='{}'><source src=" + encodeURI(url) + " type='video/mp4' /></video>";
	layer.open({
		type : 1,
		title : "播放",
		// skin: 'layui-layer-rim', //加上边框
		area : [ '800px', '530px' ], // 宽高
		content : content
	});
}

/* post提交 */
function http_post(options, cbok) {
	$.ajax({
		type : "POST",
		url : options.url,
		async : true,
		global : false,
		timeout : 100000,
		data : options.data,
		dataType : 'JSON',
		success : function(data) {
			layer.closeAll();
			if(data.code == 0){
				cbok(data.data);
			}else if(data.code == 404){
				layer.alert('无此操作权限');
			}else{
				layer.alert(checkStr(data.msg) ? data.msg : "操作失败");
			}
		},
		error : function(e) {
			layer.alert('系统异常');
		}
	});
}

/* get提交 */
function http_get(options, cbok) {
	$.ajax({
		type : "GET",
		url : options.url,
		async : true,
		global : false,
		timeout : 100000,
		data : options.data,
		dataType : 'JSON',
		success : function(data) {
			layer.closeAll();
			if(data.code == 0){
				cbok(data.data);
			}else if(data.code == 404){
				layer.alert('无此操作权限');
			}else{
				layer.alert(checkStr(data.msg) ? data.msg : "操作失败");
			}
		},
		error : function(e) {
			layer.alert('系统异常');
		}
	});
}

/* 元转分 */
Number.prototype.yuanTofen = function() {
	var price = parseFloat((this.toString() + "").replace(/[^\d\.-]/g, "")).toFixed(2)+ "";
	return Number(price.replace('.', ''));
};

/* 分转元 */
Number.prototype.fenToyuan = function() {
	var price = '' + this;
	if (price.length == 1) price = '0' + price;
	return Number(price.substr(0, price.length - 2) + '.'+ price.substr(price.length - 2)).fMoney(2);
};

Number.prototype.fMoney = function(decimalsNum) {
	decimalsNum = decimalsNum > 0 && decimalsNum <= 20 ? decimalsNum : 2;
	var s = parseFloat((this.toString() + "").replace(/[^\d\.-]/g, "")).toFixed(decimalsNum)+ "";
	var l = s.split(".")[0].split("").reverse();
	var r = s.split(".")[1];
	var t = "";
	for (var i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
};

/* 获取url中的参数 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)return unescape(r[2]);
	return null;
}