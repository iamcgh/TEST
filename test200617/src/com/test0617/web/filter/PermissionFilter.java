package com.test0617.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName = "/PermissionFilter",urlPatterns= {"/admin/UpdateStatusServlet","/admin/UserListServlet"})
public class PermissionFilter implements Filter {
	
	


    public PermissionFilter() {
    }


	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//获取参数
		System.out.println("我开始工作了");
		String attr = (String) req.getSession().getAttribute("role");
		if(attr != null && attr.equals("admin")) {
			//放行
			chain.doFilter(request, response);
			System.out.println("放行");
		} else {
			//重定向到登录页
			res.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("过滤器初始化中...");
	}

}
