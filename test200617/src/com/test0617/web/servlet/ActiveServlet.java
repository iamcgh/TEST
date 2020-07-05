package com.test0617.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test0617.service.RegisterService;

/**
 * Servlet implementation class ActiveServlet
 */
@WebServlet("/ActiveServlet")
public class ActiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ActiveServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String idStr = request.getParameter("id");
		if(idStr != null && !idStr.trim().equals("")) {
			int id = Integer.valueOf(idStr);
			if(code != null && !code.trim().equals("")) {
				RegisterService service = new RegisterService();
				boolean isSuccess = service.active(code, id);//激活用户
				response.setContentType("text/html;Charset=UTF-8");
				if(isSuccess) {
					//激活成功
					response.sendRedirect(request.getContextPath() + "/login.jsp");
				} else {
					response.getWriter().write("激活失败！");
				}
			}
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
