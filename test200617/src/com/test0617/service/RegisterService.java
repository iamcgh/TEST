package com.test0617.service;

import java.sql.SQLException;

import com.test0617.dao.UserDao;
import com.test0617.pojo.User;

public class RegisterService {
	
	/**
	 * ����û����Ƿ����
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
			//���û��������ڣ���ע��
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ע��
	 * @param user
	 */
	public boolean regist(User user) {
		UserDao dao = new UserDao();
		//��ֵ���г�ʼ��
		user.setErrorTimes(0);
		user.setHasChecked(0);//����Ϊδ����
		user.setStatus(0);//����Ϊ����״̬
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
				//��֤����ȷ
				user.setHasChecked(1);//����Ϊ�Ѽ���
				try {
					dao.updateById(user);
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				return true;//����ɹ�
			} else {
				return false;//����ʧ��
			}
		} else {
			return false;//û������û�
		}
		
	}

}
