package com.test0617.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����Ա��¼������
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
		System.out.println("�õ��Ĺ���Ա�˺�Ϊ"+account);
		//���в����ļ��(�п�)
		if(account != null && !account.trim().equals("") && password != null && !password.trim().equals("")) {
			if(account.equals("admin") && password.equals("123456")) {
				//����
				request.getSession().setAttribute("role", "admin");//���ñ�ʶ,���ڹ������ж�
				request.getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
			} else {
				//�˺��������
				request.setAttribute("loginError", "�˺��������");
				request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("loginError", "�˺����벻��Ϊ��");
			request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		}

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
