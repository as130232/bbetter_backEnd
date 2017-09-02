package com.future.bbetter.member.model;

import java.util.Date;
import java.util.List;

public class MemberDTO {
	
	//member
	private Long memberId;
	private String email;
	private String name;
	private Integer gender;
	private Double money;
	private Date birthday;
	private String address;
	
	//好友列表
	private List<Member> friends;
	
	//新增會員時輸入的密碼(明碼)
	private String password;
	
	public MemberDTO() {
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
	
	
	public String getPassword() {
		return password;
	}

//	防止BeanUtil 再複製Bean的時候存值，用於新增會員時前台將密碼資訊帶至後台
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public List<Member> getFriends() {
		return friends;
	}

	public void setFriends(List<Member> friends) {
		this.friends = friends;
	}

}
