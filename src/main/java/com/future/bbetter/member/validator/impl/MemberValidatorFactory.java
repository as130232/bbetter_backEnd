package com.future.bbetter.member.validator.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.bbetter.member.constant.MEMBER;
import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.validator.MemberValidateBehavior;
import com.future.bbetter.member.validator.MemberValidator;
import com.future.bbetter.member.validator.ValidateException;
import com.future.bbetter.member.validator.constant.VALIDATE_BEHAVIOR;

@Component
public class MemberValidatorFactory {
	
	@Autowired
	private ValidateEmail validateEmail;
	@Autowired
	private ValidatePassword validatePassword;
	@Autowired
	private ValidateGender validateGender;
	
	public List<MemberValidator> getMemberValidators(List<VALIDATE_BEHAVIOR> validates) {
		List<MemberValidator> memberValidators = new ArrayList<>();
		validates.stream().forEach(validate -> {
			MemberValidateBehavior memberValidateBehavior = this.getMemberValidator(validate);
			memberValidators.add(new MemberValidator(memberValidateBehavior));
		});
		return memberValidators;
	}
	
	private MemberValidateBehavior getMemberValidator(VALIDATE_BEHAVIOR validate) {
		
		if(validate.equals(VALIDATE_BEHAVIOR.EMAIL)) {
			return validateEmail;
		}else if(validate.equals(VALIDATE_BEHAVIOR.PASSWORD)) {
			return validatePassword;
		}else if(validate.equals(VALIDATE_BEHAVIOR.GENDER)) {
			return validateGender;
		}
		return null;
	}

	@Component
	public class ValidateEmail implements MemberValidateBehavior {
		@Override
		public void validate(MemberDTO memberDTO) throws ValidateException {
			//檢測信箱
			String email = memberDTO.getEmail();
			if (email == null) {
				throw new ValidateException("email is null");
			}else if(email.indexOf("@") == -1 || email.matches(" ")) {
				throw new ValidateException("信箱格式不正確");
			}
			
		}
	}
	
	@Component
	public class ValidatePassword implements MemberValidateBehavior {

		@Override
		public void validate(MemberDTO memberDTO) throws ValidateException{
			//檢測信箱
			String password = memberDTO.getPassword();
			if(password == null || password.isEmpty()){
				throw new ValidateException("password is null");
			}
		}
	}
	
	@Component
	public class ValidateGender implements MemberValidateBehavior {
		@Override
		public void validate(MemberDTO memberDTO) throws ValidateException{
			//檢測信箱
			Integer gender = memberDTO.getGender();
			if(MEMBER.GENDER_MALE.value < gender && MEMBER.GENDER_FEMALE.value > gender || gender == null) {
				String errorMsg = "性別格式不正確";
				throw new ValidateException(errorMsg);
			}
		}
	}
}
