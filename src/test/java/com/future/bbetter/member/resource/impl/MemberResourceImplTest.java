package com.future.bbetter.member.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.authentication.password.Password;
import com.future.bbetter.member.constant.MEMBER;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.resource.MemberResource;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(MemberResourceImpl.class)
public class MemberResourceImplTest {
	
	@Autowired
	private TestEntityManager entityMgr;

	@Autowired
	private MemberResource memRs;
	
	@Test
	public void whenAddMember_thenCanFoundOneRecord() {
		// given
		String name = "陳小宏";
		String email = "test1@gmail.com";
		String address = "Taiwan";
		Date birthday= new Date();
		Integer gender = MEMBER.GENDER_MALE.value;
		String password = "0000";
		MemberDto memberDto = new MemberDto();
		memberDto.setName(name);
		memberDto.setEmail(email);
		memberDto.setAddress(address);
		memberDto.setBirthday(birthday);
		memberDto.setGender(gender);
		memberDto.setPassword(password);
		
		// when
		MemberDto result = memRs.addMember(memberDto);

		// then
		Member found = entityMgr.find(Member.class, result.getMemberId());
		assertThat(found.getName()).isEqualTo(memberDto.getName());
		assertThat(found.getAddress()).isEqualTo(memberDto.getAddress());
		assertThat(found.getGender()).isEqualTo(memberDto.getGender());
		assertThat(found.getEmail()).isEqualTo(memberDto.getEmail());
		assertThat(found.getBirthday()).isEqualTo(memberDto.getBirthday());
		assertThat(found.getMoney()).isEqualTo(new BigDecimal(0));
		String hashPassword = found.getPassword();
		boolean isValidatePassword = Password.isValidatePassword(password, hashPassword);
		assertThat(true).isEqualTo(isValidatePassword);
	}
}
