package com.future.bbetter.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	private String email;
	private String password;
	private String salt;
	private String name;
	private Integer gender;
	@Column(name="money", columnDefinition="Decimal(10,2) default '0.00'")
	private Double money = 0.00D;
	private Date birthday;
	private String address;
	@Column(name="createdate", columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date createdate;
	@Column(name="updatedate", columnDefinition="TIMESTAMP CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,")
	private Date updatedate;
	
	public Member() {
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
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
