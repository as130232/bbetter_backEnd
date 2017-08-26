package com.future.bbetter.member.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class MemberEntity {
	private Long memberId;
	private String email;
	private String password;
	private String salt;
	private String name;
	private Integer gender;
	private Integer money;
	private Date birthday;
	private String address;
	private Date createdate;
	private Date updatedate;
	
	public MemberEntity() {
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Override
	public String toString() {
		return "MemberEntity [memberId=" + memberId + ", email=" + email + ", password=" + password + ", salt=" + salt
				+ ", name=" + name + ", gender=" + gender + ", money=" + money + ", birthday=" + birthday + ", address="
				+ address + ", createdate=" + createdate + ", updatedate=" + updatedate + "]";
	}
	
	
}
