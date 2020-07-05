package com.test0617.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test0617.service.UserService;


@WebServlet("/admin/UpdateStatusServlet")
public class UpdateStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateStatusServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		//�Բ�������У��
		System.out.println("�õ���idΪ"+idStr);
		if(idStr != null && !idStr.trim().equals("")) {
			int id = Integer.valueOf(idStr);
			UserService service = new UserService();
			service.updateStatusById(id);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
