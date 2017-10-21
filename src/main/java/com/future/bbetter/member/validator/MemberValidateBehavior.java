package com.future.bbetter.member.validator;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDTO;

public interface MemberValidateBehavior {
	void validate(MemberDTO memberDTO) throws ValidateFailureException;
}
