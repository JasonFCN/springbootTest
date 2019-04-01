package com.cwj.springbootTest.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cwj.springbootTest.service.EmailSendService;

@Controller
public class HelloWorldController {
	
	@Autowired
	EmailSendService EmailSendService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello_";
	}
	@RequestMapping("/index")
	public String index(@RequestParam(value = "JSESSIONID", required = false)String sessionID,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		System.out.println("into Index");
		Object principal = SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", principal);
		System.out.println(principal);
		request.setAttribute("message", "你好");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("JSESSIONID")){
					request.setAttribute("sessionID", cookies[i].getValue());
				}else{
					if(cookies[i].getName().equals("csrftoken")){
						request.setAttribute("csrftoken", cookies[i].getValue());
					}
				}
			}
		}
		
		//return "../static/index";
		return "index";
	}
	@RequestMapping("/emailPage")
	public String emailPage(Model model){
		model.addAttribute("message", "你好");
		return "index";
	}
	@RequestMapping("/sendEmail")
	public String sendEmail(@RequestParam(value = "files", required = false)MultipartFile[] files){
		EmailSendService.attachmentsMailSend("957616331@qq.com", "元宵节日快乐!", "Dear Chen,Happy Lantern Festival! ", files);
		return "success";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
	    // 登录失败从request中获取shiro处理的异常信息。
	    // shiroLoginFailure:就是shiro异常类的全类名.

		System.out.println("登录.......");
		
	    String exception = (String) request.getAttribute("shiroLoginFailure");
	    String msg = "";
	    if (exception != null) {
	        if (UnknownAccountException.class.getName().equals(exception)) {
	            System.out.println("UnknownAccountException -- > 账号不存在：");
	            msg = "UnknownAccountException -- > 账号不存在：";
	        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
	            System.out.println("IncorrectCredentialsException -- > 密码不正确：");
	            msg = "IncorrectCredentialsException -- > 密码不正确：";
	        } else if ("kaptchaValidateFailed".equals(exception)) {
	            System.out.println("kaptchaValidateFailed -- > 验证码错误");
	            msg = "kaptchaValidateFailed -- > 验证码错误";
	        } else {
	            msg = "else >> "+exception;
	            System.out.println("else -- >" + exception);
	        }
	    }
	    map.put("msg", msg);
	    // 此方法不处理登录成功,由shiro进行处理
	    return "../static/login";	    
	    //方式2
		/*String msg = "";
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if((username!=null && password!=null)){
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            Subject subject= SecurityUtils.getSubject();
            try{
                subject.login(token);
            }catch (AuthenticationException e){
            	String exception = e.getClass().getSimpleName();
            	if (UnknownAccountException.class.getName().equals(exception)) {
    	            System.out.println("UnknownAccountException -- > 账号不存在：");
    	            msg = "UnknownAccountException -- > 账号不存在：";
    	        }
            	if (IncorrectCredentialsException.class.getName().equals(exception)) {
    	            System.out.println("IncorrectCredentialsException -- > 密码不正确：");
    	            msg = "IncorrectCredentialsException -- > 密码不正确：";
    	        }
            	if ("kaptchaValidateFailed".equals(exception)) {
    	            System.out.println("kaptchaValidateFailed -- > 验证码错误");
    	            msg = "kaptchaValidateFailed -- > 验证码错误";
    	        } 
            }
            if( !subject.isAuthenticated()){
                System.out.println("认证成功");
                map.put("msg", msg);
                return "../static/login";
            }
        }
	    return "../static/login";
	    */
	    
	}
}
