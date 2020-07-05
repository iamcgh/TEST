package com.test0617.service;

import java.sql.SQLException;

import com.test0617.dao.UserDao;
import com.test0617.pojo.User;

public class RegisterService {
	
	/**
	 * 检测用户名是否可用
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName) {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user == null) {
			//该用户名不存在，可注册
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 注册
	 * @param user
	 */
	public boolean regist(User user) {
		UserDao dao = new UserDao();
		//对值进行初始化
		user.setErrorTimes(0);
		user.setHasChecked(0);//设置为未激活
		user.setStatus(0);//设置为可用状态
		try {
			dao.save(user);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean active(String code,Integer id) {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.getById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user != null) {
			if(user.getCode().equals(code)) {
				//验证码正确
				user.setHasChecked(1);//设置为已激活
				try {
					dao.updateById(user);
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				return true;//激活成功
			} else {
				return false;//激活失败
			}
		} else {
			return false;//没有这个用户
		}
		
	}

}
