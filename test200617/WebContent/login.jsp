<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标题</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<!--<div class="container">
		<div>
			<h2>用户登录</h2>
		</div>

		<div>
			<form action="LoginServlet" method="post">
				<label>账号：<input type="text" placeholder="请输入账号"
					name="user_name" /></label><br /> <label>密码：<input
					type="password" placeholder="请输入密码" name="password" /></label><br /> <input
					class="verifyInput" name="code" placeholder="请输入验证码">
				<img class="verifyCode" onclick="changeCode()" src="getVerifyCode">
				<input type="submit" value="登录" /><br /> <span><a
					href="register.jsp">没有账号，马上注册</a></span>
			</form>
		</div>

		<span>${requestScope.loginError }</span>
	</div>-->
	<form action="LoginServlet" method="post" class="login-form">
      <h1>登录</h1>
      <div class="txtb">
        <input type="text" name="user_name" id="user_name" />
        <span data-placeholder="用户名"></span>
      </div>
      <div class="txtb">
        <input type="password" name="password" id="password" />
        <span data-placeholder="密码"></span>
      </div>

      <div class="txtb">
        <input type="text" name="code" id="code" style="width: 60%;" />
        <span data-placeholder="验证码"></span>
        <img  class="verifyCode" onclick="changeCode()" src="getVerifyCode" style="width: 100px;height:40px" />
      </div>

      <input type="submit" value="登录" class="logbtn" />
      
      <div class="info-tips" style="text-align:center;margin-top:5px;color:red;font-size:15px;"><span>${requestScope.loginError }</span></div>

      <div class="bottom-text">没有账号？<a href="register.jsp">马上注册</a></div>
    </form>
	<script type="text/javascript">
		function changeCode() {
			var src = "getVerifyCode?" + new Date().getTime(); //加时间戳，防止浏览器利用缓存
			document.querySelector('.verifyCode').src = src;//重新绑定图片
		}
	</script>

	<!-- 非功能性代码 -->
	<script type="text/javascript">
		var inputsEles = document.querySelectorAll(".txtb input");
		for (var i = 0; i < inputsEles.length; i++) {
			inputsEles[i].addEventListener("focus", function(event) {
				console.log(event.target);
				event.target.classList.add("focus");
			}, false);
			inputsEles[i].addEventListener("blur", function(event) {
				if (event.target.value == "") {
					event.target.classList.remove("focus");
				}
			}, false);
		}
	</script>
</body>
</html>