package com.brilliantreform.sc.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseExceptionController {

	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception e) {
		return "new/jsp/500";
	}
}
