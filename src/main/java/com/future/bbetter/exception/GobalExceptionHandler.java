package com.future.bbetter.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.exception.model.ErrorInfo;

@ControllerAdvice
@RestController
public class GobalExceptionHandler {

	@ExceptionHandler(value = ValidateFailException.class)
	//狀態碼用來表示此次請求的結果(成功、失敗、其他原因等)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ErrorInfo<String> validateHandler(HttpServletRequest req, HttpServletResponse res, ValidateFailException e) {
		ErrorInfo<String> error = new ErrorInfo<>();
		//此處的Code為錯誤碼，暫時先存狀態碼，未來討論錯誤碼
		error.setCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(e.getMessage());
		error.setUrl(req.getRequestURL().toString());
		return error;
	}

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

}
