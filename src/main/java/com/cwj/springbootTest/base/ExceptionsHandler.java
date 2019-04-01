package com.cwj.springbootTest.base;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsHandler {
	@ExceptionHandler(UnauthorizedException.class)
	// 可以直接写@ExceptionHandler,不指明异常类，会自动映射
	public ModelAndView customGenericExceptionHnadler(UnauthorizedException exception) { // 还可以声明接收其他任意参数
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("errCode", "500");
		modelAndView.addObject("errMsg", "权限不足");
		return modelAndView;
	}
}
