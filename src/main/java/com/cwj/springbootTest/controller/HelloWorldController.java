package com.cwj.springbootTest.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
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
	@RequestMapping(value={"/","index"})
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
}
