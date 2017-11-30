package com.future.bbetter.exception.customize;

/***
 * 用來標示未支援的操作例外
 * @author alfred <alfreadx@gmail.com>
 *
 */
public class UnsupportedOperationException extends RuntimeException{

	private static final long serialVersionUID = -5505902596428814145L;

	public UnsupportedOperationException(String str) {
		super(str);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
	
}
