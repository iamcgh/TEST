package com.test0617.service;

import java.sql.SQLException;

import com.test0617.dao.UserDao;
import com.test0617.pojo.User;
import com.test0617.utils.MD5Utils;

public class LoginService {
	
	/*public User Login(User user) {
		//拿到用户名跟密码
		String userName = user.getUserName();
		String password = user.getPassword();
		
		//到数据库中拿到用户信息
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//如果该用户不存在
		if(userInfo == null) {
			// return null;
		} else {
			//用户存在
			//进行密码匹配
			if(password.equals(userInfo.getPassword())) {
				//匹配成功，则密码正确
				//登录错误次数清零
				userInfo.setErrorTiems(0);
				try {
					dao.updateById(userInfo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//返回用户信息
				return userInfo;
			}
		}

		return null;//返回null，登录失败
	}*/
	
	//检查密码是否正确
	public boolean checkPassword(String userName,String password) {
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(userInfo != null) {
			//该用户存在
			//进行密码的匹配
			if(MD5Utils.md5(password).equals(userInfo.getPassword())) {
				//匹配成功，密码正确
				return true;
			}
		}
		
		return false;//其他情况返回false
		
	}
	

	
	
	//用于校验密码成功后获取用户信息
	public User getUserInfo(String userName) {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}
	
	//用来检测用户账号状态
	public boolean checkStatus(User user) {
		Integer status = user.getStatus();
		if(status == 1) {
			//该用户已经被封禁
			return false;
		} else {
			//该用户可用
			return true;
		}
	}
	
	//用来检测是否已经激活
	public boolean checkHasChecked(User user) {
		Integer hasChecked = user.getHasChecked();
		if(hasChecked == 0) {
			return false;//未激活
		} else {
			return true;
		}
	}
	
	//用于登录成功后清除登录失败次数
	public void clearErrorTimes(User user) {
		user.setErrorTimes(0);
		try {
			new UserDao().updateById(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//增加登录失败次数
	public void addErrorTimes(String userName) {
		//获取该用户
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(userInfo == null) {
			//该用户不存在
		} else {
		    //该用户存在，则新增登录失败次数
			//1.获取用户已经登录失败次数
			int times = userInfo.getErrorTimes();
			if(times == 2) {
				//将其封禁
				userInfo.setStatus(1);
			}
			userInfo.setErrorTimes(times+1);
			try {
				dao.updateById(userInfo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
