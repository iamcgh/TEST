package com.test0617.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.tomcat.jdbc.naming.GenericNamingResourcesFactory;

import com.test0617.pojo.User;
import com.test0617.utils.DataSourceUtils;

public class UserDao {
	
	public User save(User user) throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.获取所有属性
        int i = -1;
        BigInteger num = null;
        
		// Integer id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		Integer status = user.getStatus();
		String email = user.getEmail();
		Integer errorTimes = user.getErrorTimes();
		String code = user.getCode();	
		Integer hasChecked = user.getHasChecked();
		//3.编写sql
		String sql = "insert into test_user(user_name,password,status,email,error_times,code,has_checked)"
				+" values(?,?,?,?,?,?,?)";
		String sql2="select @@identity";
		//4.执行sql语句
		i = qr.update(sql, userName,password,status,email,errorTimes,code,hasChecked);
		num = (BigInteger) qr.query(sql2, new ScalarHandler(1));
		//将id值封装回去
		user.setId(num.intValue());
		//返回user对象
		return user;
	}
	
	//分页查询列表信息
	public List<User> listPages(int start,int end) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from test_user limit ?,?";
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class,new BasicRowProcessor(new GenerousBeanProcessor())),start,end);
		return list;
	}
	
	//根据用户id更新用户信息
	public void updateById(User user) throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.获取所有属性
		Integer id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		String email = user.getEmail();
		String code = user.getCode();
		Integer status = user.getStatus();
		Integer errorTimes = user.getErrorTimes();
		Integer hasChecked = user.getHasChecked();
		//3.编写sql
		String sql = "update test_user set user_name=?,password=?,status=?,email=?,error_times=?,code=?,has_checked=? where id=?";
		//4.执行sql语句
		qr.update(sql, userName,password,status,email,errorTimes,code,hasChecked,id);
	}
	
	
	public User getByUserName(String userName) throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.编写sql
		String sql = "select * from test_user where user_name=?";
		//3.执行sql语句
		User user = qr.query(sql, new  BeanHandler<User>(User.class,new BasicRowProcessor(new GenerousBeanProcessor())),userName);
		//4.返回查询对象
		return user;
	}
	
	public void deleteById(Integer id) throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.编写sql
		String sql = "delete from test_user where id=?";
		//3.执行sql语句
		qr.update(sql, id);
	}
	
	
	public User getById(int id) throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.编写sql
		String sql = "select * from test_user where id=?";
		//3.执行sql语句
		User user = qr.query(sql, new  BeanHandler<User>(User.class,new BasicRowProcessor(new GenerousBeanProcessor())),id);
		//4.返回查询对象
		return user;
	}
	
	public int count() throws SQLException {
		//1.获取执行对象
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//2.编写sql
		String sql = "select count(*) from test_user";
		//3.执行sql语句
		Object obj = qr.query(sql, new ScalarHandler());
		int count = Integer.parseInt(String.valueOf(obj));
		//4.返回查询对象
		return count;
	}
	
	
	
	
	public static void main(String[] args) {
		/*User user = new User();
		user.setUserName("admin222");
		user.setPassword("123");
		try {
			user = new UserDao().save(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user);*/
		User user = new User();
		try {
			user = new UserDao().getByUserName("admin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user);
		/*List<User> list = null;
		try {
			list = new UserDao().listPages(0, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(list);*/
		
		/*try {
			new UserDao().deleteById(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}


}
