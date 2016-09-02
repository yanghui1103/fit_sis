<%@ page language="java" contentType="text/html; charset=UTF-8"  session="false"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="ie-comp">
<link rel="icon" href="images/icon.gif" type="images/gif" />
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<link href="themes/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body style="position: fixed; width: 100%; height: 100%">
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a><img src="themes/default/images/login_logo.gif" /></a>
			</h1>
			<div class="login_headerContent">
				<h2 class="login_title">
					<img src="themes/default/images/login_title.jpg" />
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="userLoginStyleStaff.action" method=post>
					<p>
						<label>帐号：</label> <input type="text" id="j_username"
							name="j_username" size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label> <input type="password" id="j_password"
							name="j_password" size="20" maxlength=6 class="login_input" />
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" onclick="Save();" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
				<ul class="helpList">
					<li><a></a></li>
				</ul>
			</div>
		</div>
		<div id="login_footer">IE11浏览器
		</div>
	</div>
</body>
</html>