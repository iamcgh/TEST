package com.test0617.web.servlet;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test0617.pojo.User;
import com.test0617.service.LoginService;



/**
 * 登录
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    }

	/**
	 * 登录
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String code = request.getParameter("code"); 
		//1.进行参数的验证
		System.out.println("获取到的用户名为："+userName);
		
		if(userName!= null && !userName.trim().equals("") && 
				password != null && !password.trim().equals("") &&
				code != null && !code.trim().equals("")) {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(password);
			//2.进行登录
			LoginService ls = new LoginService();
			//2.1对密码进行校验
			if(ls.checkPassword(userName, password)) {
				//密码校验成功
				//2.2 获取用户信息
				User userInfo = ls.getUserInfo(userName);
				//2.3对用户状态进行校验
				if(ls.checkStatus(userInfo)) {
					//该用户可用
					//2.4对用户是否已经激活进行检测
					if(ls.checkHasChecked(userInfo)) {
						//该用户已经激活
						//2.5清除之前的登录失败次数
						ls.clearErrorTimes(userInfo);
						//2.6对验证码进行校验
						boolean flag = checkCode(code,request);
						if(flag) {
							//验证码正确
							//2.6允许登录，进行页面的跳转
							request.setAttribute("user", userInfo);
							request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response); 
						} else {
							//验证码错误
							System.out.println("验证码错误");
							request.setAttribute("loginError", "验证码错误");
							request.getRequestDispatcher("/login.jsp").forward(request, response);
						}
						 
					}
				} else {
					//该账号已经封禁
					request.setAttribute("loginError", "该账号已经封禁");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				
			} else {
				//密码校验失败
				//1.新增登录失败次数
				ls.addErrorTimes(userName);
				//System.out.println("当前用户已经登录错误");
				//2.跳转页面
				request.setAttribute("loginError", "用户名或密码错误<br/>(注意：连续三次输错密码会被禁用)");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}


	private boolean checkCode(String code,HttpServletRequest request) {
		String realCode = (String) request.getSession().getAttribute("verifyCode");//获取存储在session的验证码
		if(realCode != null && realCode.toLowerCase().equals(code.toLowerCase())) {
			//验证码正确
			return true;
		} else {
			return false;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
