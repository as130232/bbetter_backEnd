package com.future.bbetter.exception.customize;

/***
 * 用來標示尚未實作方法的例外
 * @author alfred <alfreadx@gmail.com>
 *
 */
public class UnimplementedException extends RuntimeException{

	private static final long serialVersionUID = -4982541850437388366L;

	public UnimplementedException(String msg) {
		super(msg);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
