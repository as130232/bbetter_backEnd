package com.future.bbetter.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.exception.customize.ThirdVerificationException;
import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.exception.model.ErrorInfo;

@ControllerAdvice
@RestController
public class GobalExceptionHandler {
	/**
	 * 參數驗證失敗
	 * @author Charles
	 * @date 2017年9月17日 上午11:05:59
	 */
	@ExceptionHandler(value = ValidateFailureException.class)
	//狀態碼用來表示此次請求的結果(成功、失敗、其他原因等)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ErrorInfo<String> validateHandler(HttpServletRequest req, HttpServletResponse res, ValidateFailureException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		//此處的Code為錯誤碼，暫時先存狀態碼，未來討論錯誤碼
		error.setCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}

	/**
	 * 數據庫中找不到該值
	 * @author Charles
	 * @date 2017年9月17日 上午11:05:59
	 */
	@ExceptionHandler(value = DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorInfo<String> dataNotFoundHandler(HttpServletRequest req, HttpServletResponse res,
			DataNotFoundException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		error.setCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}

	/**
	 * 登入時驗證失敗
	 * @author Charles
	 * @date 2017年9月17日 上午11:05:59
	 */
	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorInfo<String> AuthenticationHandler(HttpServletRequest req, HttpServletResponse res,
			AuthenticationException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		error.setCode(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}
	
	
	/**
	 * 第三方驗證失敗
	 * @author Charles
	 * @date 2017年10月15日 下午10:14:47
	 */
	@ExceptionHandler(value = ThirdVerificationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorInfo<String> ThirdVerificationExceptionHandler(HttpServletRequest req, HttpServletResponse res,
			ThirdVerificationException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		error.setCode(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}
	
	
	/**
	 * 新增或更新資料失敗
	 * @author Charles
	 * @date 2017年10月21日 下午10:14:47
	 */
	@ExceptionHandler(value = InsertOrUpdateDataFailureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorInfo<String> InsertOrUpdateDataFailExceptionHandler(HttpServletRequest req, HttpServletResponse res,
			InsertOrUpdateDataFailureException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}
}
