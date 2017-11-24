package com.future.bbetter.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.validator.constant.VALIDATE_BEHAVIOR;
import com.future.bbetter.member.validator.MemberValidator;
import com.future.bbetter.member.validator.MemberValidatorFactory;
import com.future.bbetter.member.validator.MemberValidatorFactory.ValidateGender;
import com.future.bbetter.member.validator.MemberValidatorFactory.ValidatePassword;

@Service
public class MemberService {
	
	@Autowired
	private MemberValidatorFactory memberValidatorFactory;
	
	/**
	 * 檢查用戶輸入的值是否正確
	 * @author Charles
	 * @date 2017年9月2日 下午5:30:47
	 * @param memberDTO
	 * @return List<String>
	 */
	public List<String> checkAddUser(MemberDto memberDto) {
		List<String> errorList = new ArrayList<>();

		//建立需要檢查的會員驗證行為清單
		List<VALIDATE_BEHAVIOR> memberValidates = new ArrayList<>();
		memberValidates.add(VALIDATE_BEHAVIOR.EMAIL);
		memberValidates.add(VALIDATE_BEHAVIOR.PASSWORD);
		memberValidates.add(VALIDATE_BEHAVIOR.GENDER);
		//藉由會員驗證器工廠產生對應驗證器清單
		List<MemberValidator> memberValidators = memberValidatorFactory.getMemberValidators(memberValidates);
		
		//將會員DTO傳入驗證行為中檢查，若有錯誤會跳出Exception，並將錯誤記錄在errorList中
		memberValidators.stream().forEach(validator -> {
			try {
				validator.validate(memberDto);
			} catch (ValidateFailureException e) {
				errorList.add(e.toString());
			}
		});
		return errorList;
		
//		List<String> errorList = new ArrayList<>();
//		//檢測信箱
//		String email = memberDTO.getEmail();
//		boolean isEmailQualified = this.checkIsEmailQualified(email);
//		if(isEmailQualified) {
//			String errorMsg = "信箱格式不正確";
//			errorList.add(errorMsg);
//		}
//		 
//		//檢查密碼
//		String password = memberDTO.getPassword();
//		boolean isPasswordlQualified = this.checkIsPasswordQualified(password);
//		if(isPasswordlQualified) {
//			String errorMsg = "密碼格式不正確";
//			errorList.add(errorMsg);
//		}
//		
//		//檢查性別
//		Integer gender = memberDTO.getGender();
//		if(MEMBER.GENDER_MALE.value != gender || MEMBER.GENDER_FEMALE.value != gender || gender == null) {
//			String errorMsg = "性別格式不正確";
//			errorList.add(errorMsg);
//		}
		//return errorList;
	}
	
	private boolean checkIsEmailQualified(String email) {
		boolean isEmailQualified = true;
		if(email == null){
			isEmailQualified = false;
		}
		if(!email.matches("@") || email.matches(" ")) {
			isEmailQualified = false;
		}
		return isEmailQualified;
	}
	
	private boolean checkIsPasswordQualified(String password) {
		boolean isPasswordlQualified = true;
		if(password == null){
			isPasswordlQualified = false;
		}
		return isPasswordlQualified;
	}
}
