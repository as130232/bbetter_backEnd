package com.future.bbetter.exception.customize;

public class InsertOrUpdateDataFailureException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InsertOrUpdateDataFailureException(String str){
		super(str);   
    }

	@Override
	public String toString() {
		return getMessage();
	}

}
