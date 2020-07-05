package com.test0617.service;

import java.sql.SQLException;
import java.util.List;

import com.test0617.dao.UserDao;
import com.test0617.pojo.User;

public class UserService {
	
	/**
	 * ��ҳ��ѯ�û��б�
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<User> pageList(int page,int limit) {
		UserDao dao = new UserDao();
		List<User> list = null;
		try {
			list = dao.listPages(page * limit, (page+1) * limit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * ���û�״̬���и��²���
	 * @param id
	 * @return
	 */
	public boolean updateStatusById(int id) {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.getById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user != null) {
			//�и��û�
			//��״̬���и���
			int status = user.getStatus();
			user.setStatus(status == 0? 1:0);
			user.setErrorTimes(0);//��������ã�Ӧ�����¼��������������Ժ�û���ʧЧ
			try {
				dao.updateById(user);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return false;
		
	}

	public int count() {
		UserDao dao = new UserDao();
		int count = 0;
		try {
			count = dao.count();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


}
