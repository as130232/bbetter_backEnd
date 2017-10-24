package com.future.bbetter.exception.customize;

public class ThirdVerificationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ThirdVerificationException(String str){
		super(str);   
    }

	@Override
	public String toString() {
		return getMessage();
	}
	
}