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
 * 注册控制器
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取参数
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		// 2.对参数进行校验
		System.out.println("拿到的用户名是"+userName);
		if (userName != null && !userName.trim().equals("") && password != null && !password.trim().equals("")
				&& email != null && !email.trim().equals("")) {
			//检测用户名是否可用
			RegisterService registerService = new RegisterService();
			boolean isExist = registerService.checkUserName(userName);
			if(isExist) {
				//用户名不可注册（已经存在）
				request.setAttribute("registError", "该用户名已被注册");
				response.sendRedirect(request.getContextPath()+"/register.jsp");
				return;
			}
			// 参数正确，新增对象
			User user = new User();
			user.setUserName(userName);
			user.setPassword(MD5Utils.md5(password));//密码加密
			user.setEmail(email);
			user.setStatus(0);
			user.setHasChecked(0);// 未激活
			// code 激活的码
			String code = UUID.randomUUID().toString();
			user.setCode(code);

			// 将用户传递给service层

			boolean flag = false;
			flag = registerService.regist(user);
			// 发送邮箱
			// 注册成功
			if (flag) {
				// 如果注册成功则使用邮箱进行激活操作
				String emailMsg = "<div>亲爱的用户：<br/></div><div>您好！<br/></div><div>恭喜您，已经成功注册[MY-USER]账号，请点击以下链接，即可激活该帐号：<br/></div><div><a href='http://localhost:8080/test200617/ActiveServlet?code="
						+ code + "'>http://localhost:8080/test200617/ActiveServlet?code="+code+"&id="+user.getId()+"</a><br/></div><div>此邮件由系统自动发送，请勿回复。<br/></div><div>--[MY-USER] Team<br/></div>";
				//System.out.println("进来了吗？？？？？？");
				try {
					MailUtils.sendMail(user.getEmail(), emailMsg);
					//System.out.println("发送邮箱了,发给了"+user.getEmail());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				response.sendRedirect(request.getContextPath() + "/registSuccess.jsp");

			}
			// 注册失败
			else {
				request.getRequestDispatcher("/registError.jsp").forward(request, response);
			}

		} else {
			//参数不正确
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
