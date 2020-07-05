package com.test0617.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员登录控制器
 */
@WebServlet("/admin/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminLoginServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		System.out.println("拿到的管理员账号为"+account);
		//进行参数的检测(判空)
		if(account != null && !account.trim().equals("") && password != null && !password.trim().equals("")) {
			if(account.equals("admin") && password.equals("123456")) {
				//放行
				request.getSession().setAttribute("role", "admin");//设置标识,用于过滤器判断
				request.getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
			} else {
				//账号密码错误
				request.setAttribute("loginError", "账号密码错误");
				request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("loginError", "账号密码不能为空");
			request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		}

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
