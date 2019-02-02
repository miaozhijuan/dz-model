<#include "/common/includes.ftl">
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>互动深世界</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" type="text/css" href="${ctx}/login/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/login/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/login/css/camera.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/login/css/matrix-login.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/login/css/font-awesome.css" />
</head>
<body>
	<div style="width: 100%; text-align: center; margin: 0 auto; position: absolute;">
		<!-- 登录 -->
		<div id="loginbox">
			<div class="control-group normal_text">
				<h3>互动深世界科技有限公司</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg">
							<i>
								<img height="37" src="${ctx }/login/images/user.png" />
							</i>
						</span>
						<input type="text" name="name" id="name" value="" placeholder="请输入用户名" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly">
							<i>
								<img height="37" src="${ctx }/login/images/suo.png" />
							</i>
						</span>
						<input type="password" name="pwd" id="pwd" placeholder="请输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value="" />
					</div>
				</div>
			</div>
			<div style="float: right; padding-right: 10%;">
				<div style="float: left; margin-top: 3px; margin-right: 2px;">
					<font color="white">记住密码</font>
				</div>
				<div style="float: left;">
					<input name="form-field-checkbox" id="saveid" type="checkbox" onclick="saveCookie();" style="padding-top: 0px;" />
				</div>
			</div>
			<div class="form-actions">
				<div style="width: 86%; padding-left: 8%;">
					<div style="float: left; padding-top: 2px;">
						<i>
							<img src="${ctx }/login/images/yan.png" />
						</i>
					</div>
					<div style="float: left;" class="codediv">
						<input type="text" name="code" id="code" class="login_code" style="height: 20px; padding-top: 4px; padding-bottom: 4px;" />
					</div>
					<div style="float: left;">
						<i>
							<img height="30px;" width="100px" onClick="this.src='${ctx}/authority/main/loginCode.casual?timestamp='+Math.random()" alt="点击更换" title="点击更换"
								src="${ctx}/authority/main/loginCode.casual?timestamp=" +(new Date().getTime()) />
						</i>
					</div>
					<span class="pull-right">
						<a href="javascript:;" onclick="login()" class="flip-link btn btn-info" id="to-recover">登录</a>
					</span>
				</div>
			</div>
			<div class="controls">
				<div class="main_input_box">
					<font color="white">
						<span id="nameerr">互动深世界 2018</span>
					</font>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<!-- 背景图片 -->
			<div data-src="${ctx }/login/images/banner_slide_01.jpg"></div>
			<div data-src="${ctx }/login/images/banner_slide_02.jpg"></div>
			<div data-src="${ctx }/login/images/banner_slide_03.jpg"></div>
			<div data-src="${ctx }/login/images/banner_slide_04.jpg"></div>
			<div data-src="${ctx }/login/images/banner_slide_05.jpg"></div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/login/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${ctx}/login/js/jquery.tips.js"></script>
<script type="text/javascript" src="${ctx}/login/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${ctx}/login/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/login/js/camera.min.js"></script>
<script type="text/javascript" src="${ctx}/login/js/templatemo_script.js"></script>
<script type="text/javascript" src="${ctx}/login/js/ban.js"></script>
<script type="text/javascript" src="${ctx}/js/pc/common.js"></script>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
<script type="text/javascript">
	<!-- 操作cookie -->
	var name = $.cookie('name');
	var pwd = $.cookie('pwd');
	if (checkStr(name) && checkStr(pwd)) {
		content("name").value = name;
		content("pwd").value = pwd;
		content("saveid").checked = true;
		content("code").focus();
	}
	<!-- 保存cookie -->
	function saveCookie() {
		if(content("saveid").checked){
			$.cookie('name', content("name").value, {
				expires : 7
			});
			$.cookie('pwd', content("pwd").value, {
				expires : 7
			});
		}else{
			$.cookie('name', '', {
				expires : -1
			});
			$.cookie('pwd', '', {
				expires : -1
			});
		}
	}
	<!-- 回车登陆 -->
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			login();
		}
	});
	<!-- 登陆 -->
	function login() {
		var name = content("name").value;
		var pwd = content("pwd").value;
		var code = content("code").value;
		if (!checkStr(name)) {
			$("#name").tips({
				side : 2,
				msg : '用户名不能为空',
				bg : '#AE81FF',
				time : 3
			});
			content("name").focus();
			return false;
		}
		if (!checkStr(pwd)) {
			$("#pwd").tips({
				side : 2,
				msg : '密码不能为空',
				bg : '#AE81FF',
				time : 3
			});
			content("pwd").focus();
			return false;
		}
		if (!checkStr(code)) {
			$("#code").tips({
				side : 1,
				msg : '验证码不能为空',
				bg : '#AE81FF',
				time : 3
			});
			content("code").focus();
			return false;
		}
		var cbok = function(data) {
			sessionStorage.setItem("iList", data);
			window.location.href = "${ctx}/authority/main/index.do";
		};
		var options = {
			url : "${ctx}/authority/main/loginIn.casual",
			data : {
				name : name,
				pwd : pwd,
				code : code
			},
		};
		http_post(options, cbok);
	}
</script>
</html>