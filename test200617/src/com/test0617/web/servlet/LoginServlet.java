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
 * ��¼
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    }

	/**
	 * ��¼
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String code = request.getParameter("code"); 
		//1.���в�������֤
		System.out.println("��ȡ�����û���Ϊ��"+userName);
		
		if(userName!= null && !userName.trim().equals("") && 
				password != null && !password.trim().equals("") &&
				code != null && !code.trim().equals("")) {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(password);
			//2.���е�¼
			LoginService ls = new LoginService();
			//2.1���������У��
			if(ls.checkPassword(userName, password)) {
				//����У��ɹ�
				//2.2 ��ȡ�û���Ϣ
				User userInfo = ls.getUserInfo(userName);
				//2.3���û�״̬����У��
				if(ls.checkStatus(userInfo)) {
					//���û�����
					//2.4���û��Ƿ��Ѿ�������м��
					if(ls.checkHasChecked(userInfo)) {
						//���û��Ѿ�����
						//2.5���֮ǰ�ĵ�¼ʧ�ܴ���
						ls.clearErrorTimes(userInfo);
						//2.6����֤�����У��
						boolean flag = checkCode(code,request);
						if(flag) {
							//��֤����ȷ
							//2.6�����¼������ҳ�����ת
							request.setAttribute("user", userInfo);
							request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response); 
						} else {
							//��֤�����
							System.out.println("��֤�����");
							request.setAttribute("loginError", "��֤�����");
							request.getRequestDispatcher("/login.jsp").forward(request, response);
						}
						 
					}
				} else {
					//���˺��Ѿ����
					request.setAttribute("loginError", "���˺��Ѿ����");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				
			} else {
				//����У��ʧ��
				//1.������¼ʧ�ܴ���
				ls.addErrorTimes(userName);
				//System.out.println("��ǰ�û��Ѿ���¼����");
				//2.��תҳ��
				request.setAttribute("loginError", "�û������������<br/>(ע�⣺���������������ᱻ����)");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}


	private boolean checkCode(String code,HttpServletRequest request) {
		String realCode = (String) request.getSession().getAttribute("verifyCode");//��ȡ�洢��session����֤��
		if(realCode != null && realCode.toLowerCase().equals(code.toLowerCase())) {
			//��֤����ȷ
			return true;
		} else {
			return false;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
