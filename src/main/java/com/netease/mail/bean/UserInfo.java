package com.netease.mail.bean;

/**
 * 邮箱帐号实体类
 * @author hzmayanli
 *
 */
public class UserInfo {
	public UserInfo() {
		super();
	}
	
	private String email;//邮箱地址
	private String pwd;	//邮箱密码
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
