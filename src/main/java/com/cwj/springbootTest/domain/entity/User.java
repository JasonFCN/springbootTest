package com.cwj.springbootTest.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.cwj.springbootTest.enums.UserType;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 2L;
	
	public interface AddFormUser{}
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message="用户名不能为空",groups={AddFormUser.class})
	private String userName;
	
	@NotBlank(message="密码不能为空",groups={AddFormUser.class})
	private String passWord;
	
	private String salt;//加密密码的盐
	private String email;
	@NotBlank(message="昵称不能为空",groups={AddFormUser.class})
	private String nickName;
	private String regTime;
	@Enumerated(EnumType.STRING) 
	private UserType userType;
	private Boolean isUsable;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Boolean getIsUsable() {
		return isUsable;
	}
	public void setIsUsable(Boolean isUsable) {
		this.isUsable = isUsable;
	}
	public String getCredentialsSalt() {
		// TODO Auto-generated method stub
		return userName+salt;
	}
}
