package com.test0617.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.test0617.pojo.User;
import com.test0617.service.UserService;


@WebServlet("/admin/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserListServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		if(page != null && limit != null) {
			if(page <= 0) {
				page = 0;
			}
			//进行查询
			UserService service = new UserService();
			List<User> list = service.pageList(page.intValue(), limit.intValue());
			int count = service.count();
			Map<String,Object> res = new HashMap<>();
			String jsonOfData = "";
			if(list != null) {
				//返回json数据
				
				//封装数据
				res.put("data",list);
				res.put("total", count);
				res.put("message", "查询成功");
				res.put("code", "20000");
			} else {
				//查询失败
				res.put("data","");
				res.put("total","0");
				res.put("message", "查询失败");
				res.put("code", "20001");
			}
			//将数据转化为json格式
			jsonOfData = new Gson().toJson(res);
			//将数据返回前端
			/* 设置格式为text/json*/
			response.setContentType("text/json");
			/*设置字符集为'UTF-8'*/
			response.setCharacterEncoding("utf-8");
			
			PrintWriter out = response.getWriter();
			out.print(jsonOfData);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
