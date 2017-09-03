package com.future.bbetter.member.validator;

public class ValidateException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ValidateException(String str){
		super(str);   
    }

	@Override
	public String toString() {
		return getMessage();
	}
	
}
