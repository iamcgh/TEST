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
		//��ȡ����
		System.out.println("�ҿ�ʼ������");
		String attr = (String) req.getSession().getAttribute("role");
		if(attr != null && attr.equals("admin")) {
			//����
			chain.doFilter(request, response);
			System.out.println("����");
		} else {
			//�ض��򵽵�¼ҳ
			res.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("��������ʼ����...");
	}

}
