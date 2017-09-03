package com.future.bbetter.member.validator;

import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.member.model.MemberDTO;

public interface MemberValidateBehavior {
	void validate(MemberDTO memberDTO) throws ValidateFailException;
}
