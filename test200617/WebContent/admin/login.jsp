<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录页面</title>
    <link rel="stylesheet" href="../css/admin_login.css" />
    <link href="../asset/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body>
	<!--<div>
	<form action="AdminLoginServlet" method="post">
		<label>账号：<input type="text" placeholder="请输入账号" name="account"/></label><br/>
		<label>密码：<input type="password" placeholder="请输入密码" name="password"/></label><br/>
		<input type="submit" value="登录"/>
		<input type="reset" value="重置"/>
	</form>
	<span>${requestScope.errorInfo }</span>
</div>-->
	<div class="show-login-btn">
		<i class="fa fa-sign-in" aria-hidden="true"></i> 显示登录入口
	</div>
	<div class="login-box">
		<div class="hide-login-btn">
			<i class="fa fa-times" aria-hidden="true"></i>
		</div>
		<form action="AdminLoginServlet" method="post" class="login-form">
			<h1>欢迎使用</h1>
			<input class="txtb" type="text" name="account" placeholder="账号" /> <input
				class="txtb" type="password" name="password" placeholder="密码" /> <input
				class="logbtn" type="submit" name="" value="登录" />
		</form>
	</div>
	<script>
		document.querySelector(".show-login-btn").addEventListener(
				"click",
				function() {
					document.querySelector(".login-box").classList
							.add("showed");
				}, false);

		document.querySelector(".hide-login-btn").addEventListener(
				"click",
				function() {
					document.querySelector(".login-box").classList
							.remove("showed");
				}, false);
	</script>
</body>
</html>