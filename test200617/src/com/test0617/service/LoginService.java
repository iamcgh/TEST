package com.test0617.service;

import java.sql.SQLException;

import com.test0617.dao.UserDao;
import com.test0617.pojo.User;
import com.test0617.utils.MD5Utils;

public class LoginService {
	
	/*public User Login(User user) {
		//�õ��û���������
		String userName = user.getUserName();
		String password = user.getPassword();
		
		//�����ݿ����õ��û���Ϣ
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//������û�������
		if(userInfo == null) {
			// return null;
		} else {
			//�û�����
			//��������ƥ��
			if(password.equals(userInfo.getPassword())) {
				//ƥ��ɹ�����������ȷ
				//��¼�����������
				userInfo.setErrorTiems(0);
				try {
					dao.updateById(userInfo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//�����û���Ϣ
				return userInfo;
			}
		}

		return null;//����null����¼ʧ��
	}*/
	
	//��������Ƿ���ȷ
	public boolean checkPassword(String userName,String password) {
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(userInfo != null) {
			//���û�����
			//���������ƥ��
			if(MD5Utils.md5(password).equals(userInfo.getPassword())) {
				//ƥ��ɹ���������ȷ
				return true;
			}
		}
		
		return false;//�����������false
		
	}
	

	
	
	//����У������ɹ����ȡ�û���Ϣ
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
	
	//��������û��˺�״̬
	public boolean checkStatus(User user) {
		Integer status = user.getStatus();
		if(status == 1) {
			//���û��Ѿ������
			return false;
		} else {
			//���û�����
			return true;
		}
	}
	
	//��������Ƿ��Ѿ�����
	public boolean checkHasChecked(User user) {
		Integer hasChecked = user.getHasChecked();
		if(hasChecked == 0) {
			return false;//δ����
		} else {
			return true;
		}
	}
	
	//���ڵ�¼�ɹ��������¼ʧ�ܴ���
	public void clearErrorTimes(User user) {
		user.setErrorTimes(0);
		try {
			new UserDao().updateById(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//���ӵ�¼ʧ�ܴ���
	public void addErrorTimes(String userName) {
		//��ȡ���û�
		UserDao dao = new UserDao();
		User userInfo = null;
		try {
			userInfo = dao.getByUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(userInfo == null) {
			//���û�������
		} else {
		    //���û����ڣ���������¼ʧ�ܴ���
			//1.��ȡ�û��Ѿ���¼ʧ�ܴ���
			int times = userInfo.getErrorTimes();
			if(times == 2) {
				//������
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
