package com.test0617.pojo;



public class User {

	private Integer id;
	
	private String userName;
	
	private String password;
	
	private Integer status;//用户状态
	
	private String email;
	
	private Integer errorTimes;
	
	private String code;//邮箱激活码
	
	private Integer hasChecked;//是否已经激活

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getHasChecked() {
		return hasChecked;
	}

	public void setHasChecked(Integer hasChecked) {
		this.hasChecked = hasChecked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", status=" + status
				+ ", email=" + email + ", errorTimes=" + errorTimes + ", code=" + code + ", hasChecked=" + hasChecked
				+ "]";
	}





	



	
	
	
	
}
