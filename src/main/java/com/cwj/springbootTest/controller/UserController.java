package com.cwj.springbootTest.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cwj.springbootTest.config.shiro.ShiroConfig;
import com.cwj.springbootTest.domain.entity.User;
import com.cwj.springbootTest.enums.UserType;
import com.cwj.springbootTest.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("addUserPage")
	public String addUserPage(HttpServletRequest request, @ModelAttribute("user") User user) {
		return "user/add";
	}
	/*
	 * @RequestMapping("/login") public String login(HttpServletRequest request,
	 * Map<String, Object> map) throws Exception{ //
	 * 登录失败从request中获取shiro处理的异常信息。 // shiroLoginFailure:就是shiro异常类的全类名.
	 * System.out.println("登录.......");
	 * 
	 * String exception = (String) request.getAttribute("shiroLoginFailure");
	 * String msg = ""; if (exception != null) { if
	 * (UnknownAccountException.class.getName().equals(exception)) {
	 * System.out.println("UnknownAccountException -- > 账号不存在："); msg =
	 * "UnknownAccountException -- > 账号不存在："; } else if
	 * (IncorrectCredentialsException.class.getName().equals(exception)) {
	 * System.out.println("IncorrectCredentialsException -- > 密码不正确："); msg =
	 * "IncorrectCredentialsException -- > 密码不正确："; } else if
	 * ("kaptchaValidateFailed".equals(exception)) {
	 * System.out.println("kaptchaValidateFailed -- > 验证码错误"); msg =
	 * "kaptchaValidateFailed -- > 验证码错误"; } else { msg = "else >> "+exception;
	 * System.out.println("else -- >" + exception); } } map.put("msg", msg); //
	 * 此方法不处理登录成功,由shiro进行处理 return "login"; }
	 */
	@RequestMapping("/loginPage")
	public ModelAndView loginPage(){
		User user = new User(); 
		return new ModelAndView("login").addObject("failuser", user);
	}
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, User user, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassWord())) {
			return new ModelAndView("login").addObject("message", "用户名或密码不能为空").addObject("failuser", user);
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord());
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (Exception ex) {
			ModelAndView modelAndView = new ModelAndView("login");
			if (ex instanceof UnknownAccountException || ex instanceof IncorrectCredentialsException) {
				modelAndView.addObject("message", "用户名或密码不正确").addObject("failuser", user);
			}else
			if (ex instanceof LockedAccountException) {
				modelAndView.addObject("message", "该账户已锁定").addObject("failuser", user);
			}else
			if (ex instanceof ExcessiveAttemptsException) {
				modelAndView.addObject("message", "频繁尝试登录失败").addObject("failuser", user);
			}else
			if (ex instanceof AuthenticationException) {
				modelAndView.addObject("message", "AuthenticationException").addObject("failuser", user);
			}
			return modelAndView;
		}
		return new ModelAndView("redirect:/index");
	}
	@RequestMapping("logout")
	public String logout(){
		return "redirect:/index";
	}
	
	@RequestMapping("list")
	public String list(HttpServletRequest request){
		List<User> users = userService.findAll();
		request.setAttribute("users", users);
		return "user/list";
	}
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id,HttpServletRequest request){
		User user = userService.getById(id);
		request.setAttribute("user", user);
		return "user/edit";
	}
	@RequestMapping("saveUser")
	public ModelAndView save(HttpServletRequest request,String pageName, @Validated(value = User.AddFormUser.class) User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			request.getSession().setAttribute("message", bindingResult);
			if("add".equals(pageName)){
				return new ModelAndView("/user/add").addObject("message", bindingResult);
			}else{
				return new ModelAndView("/user/edit").addObject("message", bindingResult);
			}
		}
		if("add".equals(pageName)){
			String salt = UUID.randomUUID().toString().replaceAll("-", "");
			String passWord = new SimpleHash(ShiroConfig.HASHALGORITHMNAME, user.getUserName(), ByteSource.Util.bytes(user.getUserName()+salt), ShiroConfig.HASHITERATIONS).toHex();
			user.setIsUsable(Boolean.TRUE);
			user.setPassWord(passWord);
			user.setRegTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			user.setSalt(salt);
			user.setUserType(UserType.STUDENT);
		}
		userService.save(user);
		return new ModelAndView("redirect:/user/list");
	}
	
	@RequestMapping("hasUserName")
	@ResponseBody
	public String hasUserName(String userName){
		User user = userService.findByUserName(userName);
		return user==null?"0":"1";
	}
	public static void main(String[] args) {
		String passWord = new SimpleHash(ShiroConfig.HASHALGORITHMNAME, "123123", ByteSource.Util.bytes("admins6d915d6807d3486185d44764619e3433"), ShiroConfig.HASHITERATIONS).toHex();
		System.out.println(passWord);
	}
}
