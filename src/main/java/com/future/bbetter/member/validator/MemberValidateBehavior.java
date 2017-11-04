package com.future.bbetter.member.validator;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDto;

public interface MemberValidateBehavior {
	void validate(MemberDto memberDto) throws ValidateFailureException;
}
