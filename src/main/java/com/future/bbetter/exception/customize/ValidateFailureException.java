package com.future.bbetter.exception.customize;

public class ValidateFailureException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ValidateFailureException(String str){
		super(str);   
    }

	@Override
	public String toString() {
		return getMessage();
	}
	
}
