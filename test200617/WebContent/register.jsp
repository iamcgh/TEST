<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
 <link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<!--<div class="container">
	<h2>用户注册</h2>
	<form action="RegisterServlet" method="post">
	<label>账号：<input id="userName" type="text" placeholder="请输入账号" name="user_name"/></label><br/>
	<label>密码：<input id="password" type="password" placeholder="请输入密码" name="password"/></label><br/>
	<label>确认密码：<input id="check_password" type="password" placeholder="请确认密码" name="check_password"/></label><br/>
	<label>邮箱：<input id="email" type="text" placeholder="请输入邮箱地址" name="email"/></label><br/>
	<input type="submit" value="立刻注册"/><br/>
	</form>
</div>-->
	<form action="RegisterServlet" method="post" class="reg-form">
		<h1>注册</h1>
		<div class="txtb">
			<input type="text" name="userName" id="userName" /> <span data-placeholder="用户名"></span>
		</div>
		<div class="txtb">
			<input type="password" name="password" id="password" /> <span data-placeholder="密码"></span>
		</div>

		<div class="txtb">
			<input type="text" name="check_password" id="check_password" /> <span data-placeholder="确认密码"></span>
		</div>

		<div class="txtb">
			<input type="text" name="email" id="email" /> <span data-placeholder="邮箱"></span>
		</div>

		<input type="submit" value="注册" class="regbtn" />

		<div class="bottom-text">
			已有账号？<a href="login.jsp">马上登录</a>
		</div>
	</form>



	<script type="text/javascript">
		function regist() {
			var httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
			var url = "http://localhost:8080/test200617/RegisterServlet";
			httpRequest.open('POST', 'url', true); //第二步：打开连接
			httpRequest.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
			httpRequest.send('user_name');//发送请求 将情头体写在send中
			/**
			 * 获取数据后的处理程序
			 */
			httpRequest.onreadystatechange = function() {//请求后的回调接口，可将请求成功后要执行的程序写在其中
				if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
					var json = httpRequest.responseText;//获取到服务端返回的数据
					console.log(json);
				}
			};
		}
	</script>


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