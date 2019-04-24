package com.cwj.springbootTest.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.cwj.springbootTest.exception.CheckException;
import com.cwj.springbootTest.exception.UnloginException;
import com.cwj.springbootTest.utilBean.ResultBean;

/**
 * 
 * @author 武杰
 * 配置对返回结果进行封装，实现统一的返回结果（包括异常请求）
 */
@Configuration
@Aspect
public class MyAopConfig {
	private static final Logger lg = LoggerFactory.getLogger(MyAopConfig.class);
	
	@Pointcut("execution (public com.cwj.springbootTest.utilBean.ResultBean *(..))")
	public void handlerControllerMethod() {
	}
	
	@Around("handlerControllerMethod()")
	public Object packResultBeanMethod(ProceedingJoinPoint pjp) {
		long startTime = System.currentTimeMillis();

		ResultBean<?> result;

		try {
			result = (ResultBean<?>) pjp.proceed();
			lg.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
		} catch (Throwable e) {
			result = handlerException(pjp, e);
		}

		return result;
	}

	private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
		ResultBean<?> result = new ResultBean<>();

		// 已知异常
		if (e instanceof CheckException) {
			result.setMsg(e.getLocalizedMessage());
			result.setCode(ResultBean.FAIL);
		} else if (e instanceof UnloginException) {
			result.setMsg("Unlogin");
			result.setCode(ResultBean.NO_LOGIN);
		} else {
			lg.error(pjp.getSignature() + " error ", e);
			// TODO 未知的异常，应该格外注意，可以发送邮件通知等
			result.setMsg(e.toString());
			result.setCode(ResultBean.FAIL);
		}

		return result;
	}
}
