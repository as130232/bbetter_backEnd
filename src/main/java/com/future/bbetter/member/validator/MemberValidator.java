package com.future.bbetter.member.validator;

import com.future.bbetter.member.model.MemberDTO;

public class MemberValidator {
	
	private MemberValidateBehavior validateBehavior;
	
	public void validate(MemberDTO memberDTO) throws ValidateException {
		validateBehavior.validate(memberDTO);
	}
	
	public MemberValidator(MemberValidateBehavior validateBehavior) {
		this.validateBehavior = validateBehavior;
	}
}
