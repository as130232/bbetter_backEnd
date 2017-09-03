package com.future.bbetter.exception.customize;

public class DataNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String str){
		super(str);   
    }

	@Override
	public String toString() {
		return getMessage();
	}
	
}
