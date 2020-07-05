package com.test0617.web.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test0617.pojo.User;
import com.test0617.service.RegisterService;
import com.test0617.utils.MD5Utils;
import com.test0617.utils.MailUtils;

/**
 * ע�������
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ����
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		// 2.�Բ�������У��
		System.out.println("�õ����û�����"+userName);
		if (userName != null && !userName.trim().equals("") && password != null && !password.trim().equals("")
				&& email != null && !email.trim().equals("")) {
			//����û����Ƿ����
			RegisterService registerService = new RegisterService();
			boolean isExist = registerService.checkUserName(userName);
			if(isExist) {
				//�û�������ע�ᣨ�Ѿ����ڣ�
				request.setAttribute("registError", "���û����ѱ�ע��");
				response.sendRedirect(request.getContextPath()+"/register.jsp");
				return;
			}
			// ������ȷ����������
			User user = new User();
			user.setUserName(userName);
			user.setPassword(MD5Utils.md5(password));//�������
			user.setEmail(email);
			user.setStatus(0);
			user.setHasChecked(0);// δ����
			// code �������
			String code = UUID.randomUUID().toString();
			user.setCode(code);

			// ���û����ݸ�service��

			boolean flag = false;
			flag = registerService.regist(user);
			// ��������
			// ע��ɹ�
			if (flag) {
				// ���ע��ɹ���ʹ��������м������
				String emailMsg = "<div>�װ����û���<br/></div><div>���ã�<br/></div><div>��ϲ�����Ѿ��ɹ�ע��[MY-USER]�˺ţ������������ӣ����ɼ�����ʺţ�<br/></div><div><a href='http://localhost:8080/test200617/ActiveServlet?code="
						+ code + "'>http://localhost:8080/test200617/ActiveServlet?code="+code+"&id="+user.getId()+"</a><br/></div><div>���ʼ���ϵͳ�Զ����ͣ�����ظ���<br/></div><div>--[MY-USER] Team<br/></div>";
				//System.out.println("�������𣿣���������");
				try {
					MailUtils.sendMail(user.getEmail(), emailMsg);
					//System.out.println("����������,������"+user.getEmail());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				response.sendRedirect(request.getContextPath() + "/registSuccess.jsp");

			}
			// ע��ʧ��
			else {
				request.getRequestDispatcher("/registError.jsp").forward(request, response);
			}

		} else {
			//��������ȷ
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
