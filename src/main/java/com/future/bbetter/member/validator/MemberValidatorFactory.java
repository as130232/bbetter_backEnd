package com.future.bbetter.member.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.constant.MEMBER;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.validator.constant.VALIDATE_BEHAVIOR;

@Component
public class MemberValidatorFactory {
	@Autowired
	private MemberRepository memberRepository;
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
		public void validate(MemberDto memberDto) throws ValidateFailureException {
			//檢測信箱
			String email = memberDto.getEmail();
			if (email == null) {
				throw new ValidateFailureException("信箱不可為空");
			}
			if(email.indexOf("@") == -1 || email.matches(" ")) {
				throw new ValidateFailureException("信箱格式不正確");
			}
			Member member = memberRepository.findByEmail(email);
			if(member != null) {
				throw new ValidateFailureException("該信箱已存在");
			}
			
		}
	}
	
	@Component
	public class ValidatePassword implements MemberValidateBehavior {
		@Override
		public void validate(MemberDto memberDto) throws ValidateFailureException{
			//檢測信箱
			String password = memberDto.getPassword();
			if(password == null || password.isEmpty()){
				throw new ValidateFailureException("密碼不可為空");
			}
		}
	}
	
	@Component
	public class ValidateGender implements MemberValidateBehavior {
		@Override
		public void validate(MemberDto memberDto) throws ValidateFailureException{
			//檢測信箱
			Integer gender = memberDto.getGender();
			if(MEMBER.GENDER_MALE.value > gender || MEMBER.GENDER_FEMALE.value < gender || gender == null) {
				String errorMsg = "性別格式不正確";
				throw new ValidateFailureException(errorMsg);
			}
		}
	}
}
