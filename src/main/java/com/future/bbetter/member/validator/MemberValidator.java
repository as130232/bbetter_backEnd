package com.future.bbetter.member.validator;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDto;

public class MemberValidator {
	
	private MemberValidateBehavior validateBehavior;
	
	public void validate(MemberDto memberDto) throws ValidateFailureException {
		validateBehavior.validate(memberDto);
	}
	
	public MemberValidator(MemberValidateBehavior validateBehavior) {
		this.validateBehavior = validateBehavior;
	}
}
